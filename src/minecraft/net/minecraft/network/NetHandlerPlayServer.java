package net.minecraft.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;

public class NetHandlerPlayServer implements INetHandlerPlayServer, IUpdatePlayerListBox {

	public static final int EaZy = 1365;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	public final NetworkManager netManager;
	private final MinecraftServer serverController;
	public EntityPlayerMP playerEntity;
	private int networkTickCount;
	private int field_175090_f;

	/**
	 * Used to keep track of how the player is floating while gamerules should
	 * prevent that. Surpassing 80 ticks means kick
	 */
	private int floatingTickCount;
	private int field_147378_h;
	private long lastPingTime;
	private long lastSentPingPacket;

	/**
	 * Incremented by 20 each time a user sends a chat message, decreased by one
	 * every tick. Non-ops kicked when over 200
	 */
	private int chatSpamThresholdCount;
	private int itemDropThreshold;
	private final IntHashMap field_147372_n = new IntHashMap();
	private double lastPosX;
	private double lastPosY;
	private double lastPosZ;
	private boolean hasMoved = true;

	public NetHandlerPlayServer(final MinecraftServer server, final NetworkManager networkManagerIn,
			final EntityPlayerMP playerIn) {
		serverController = server;
		netManager = networkManagerIn;
		networkManagerIn.setNetHandler(this);
		playerEntity = playerIn;
		playerIn.playerNetServerHandler = this;
	}

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		++networkTickCount;
		serverController.theProfiler.startSection("keepAlive");

		if (networkTickCount - lastSentPingPacket > 40L) {
			lastSentPingPacket = networkTickCount;
			lastPingTime = currentTimeMillis();
			field_147378_h = (int) lastPingTime;
			sendPacket(new S00PacketKeepAlive(field_147378_h));
		}

		serverController.theProfiler.endSection();

		if (chatSpamThresholdCount > 0) {
			--chatSpamThresholdCount;
		}

		if (itemDropThreshold > 0) {
			--itemDropThreshold;
		}

		if (playerEntity.getLastActiveTime() > 0L && serverController.getMaxPlayerIdleMinutes() > 0
				&& MinecraftServer.getCurrentTimeMillis()
						- playerEntity.getLastActiveTime() > serverController.getMaxPlayerIdleMinutes() * 1000 * 60) {
			kickPlayerFromServer("You have been idle for too long!");
		}
	}

	public NetworkManager getNetworkManager() {
		return netManager;
	}

	/**
	 * Kick a player from the server with a reason
	 */
	public void kickPlayerFromServer(final String reason) {
		final ChatComponentText var2 = new ChatComponentText(reason);
		netManager.sendPacket(new S40PacketDisconnect(var2), new GenericFutureListener() {
			@Override
			public void operationComplete(final Future p_operationComplete_1_) {
				netManager.closeChannel(var2);
			}
		}, new GenericFutureListener[0]);
		netManager.disableAutoRead();
		Futures.getUnchecked(serverController.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				netManager.checkDisconnected();
			}
		}));
	}

	/**
	 * Processes player movement input. Includes walking, strafing, jumping,
	 * sneaking; excludes riding and toggling flying/sprinting
	 */
	@Override
	public void processInput(final C0CPacketInput packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(),
				packetIn.isSneaking());
	}

	/**
	 * Processes clients perspective on player positioning and/or orientation
	 */
	@Override
	public void processPlayer(final C03PacketPlayer packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final WorldServer var2 = serverController.worldServerForDimension(playerEntity.dimension);
		if (!playerEntity.playerConqueredTheEnd) {
			final double var3 = playerEntity.posX;
			final double var5 = playerEntity.posY;
			final double var7 = playerEntity.posZ;
			double var9 = 0.0D;
			final double var11 = packetIn.getPositionX() - lastPosX;
			final double var13 = packetIn.getPositionY() - lastPosY;
			final double var15 = packetIn.getPositionZ() - lastPosZ;

			if (packetIn.func_149466_j()) {
				var9 = var11 * var11 + var13 * var13 + var15 * var15;

				if (!hasMoved && var9 < 0.25D) {
					hasMoved = true;
				}
			}

			if (hasMoved) {
				field_175090_f = networkTickCount;
				double var19;
				double var21;
				double var23;

				if (playerEntity.ridingEntity != null) {
					float var47 = playerEntity.rotationYaw;
					float var18 = playerEntity.rotationPitch;
					playerEntity.ridingEntity.updateRiderPosition();
					var19 = playerEntity.posX;
					var21 = playerEntity.posY;
					var23 = playerEntity.posZ;

					if (packetIn.getHasRotation()) {
						var47 = packetIn.getYaw();
						var18 = packetIn.getPitch();
					}

					playerEntity.onGround = packetIn.func_149465_i();
					playerEntity.onUpdateEntity();
					playerEntity.setPositionAndRotation(var19, var21, var23, var47, var18);

					if (playerEntity.ridingEntity != null) {
						playerEntity.ridingEntity.updateRiderPosition();
					}

					serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);

					if (playerEntity.ridingEntity != null) {
						if (var9 > 4.0D) {
							final Entity var48 = playerEntity.ridingEntity;
							playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(var48));
							setPlayerLocation(playerEntity.posX, playerEntity.posY, playerEntity.posZ,
									playerEntity.rotationYaw, playerEntity.rotationPitch);
						}

						playerEntity.ridingEntity.isAirBorne = true;
					}

					if (hasMoved) {
						lastPosX = playerEntity.posX;
						lastPosY = playerEntity.posY;
						lastPosZ = playerEntity.posZ;
					}

					var2.updateEntity(playerEntity);
					return;
				}

				if (playerEntity.isPlayerSleeping()) {
					playerEntity.onUpdateEntity();
					playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw,
							playerEntity.rotationPitch);
					var2.updateEntity(playerEntity);
					return;
				}

				final double var17 = playerEntity.posY;
				lastPosX = playerEntity.posX;
				lastPosY = playerEntity.posY;
				lastPosZ = playerEntity.posZ;
				var19 = playerEntity.posX;
				var21 = playerEntity.posY;
				var23 = playerEntity.posZ;
				float var25 = playerEntity.rotationYaw;
				float var26 = playerEntity.rotationPitch;

				if (packetIn.func_149466_j() && packetIn.getPositionY() == -999.0D) {
					packetIn.func_149469_a(false);
				}

				if (packetIn.func_149466_j()) {
					var19 = packetIn.getPositionX();
					var21 = packetIn.getPositionY();
					var23 = packetIn.getPositionZ();

					if (Math.abs(packetIn.getPositionX()) > 3.0E7D || Math.abs(packetIn.getPositionZ()) > 3.0E7D) {
						kickPlayerFromServer("Illegal position");
						return;
					}
				}

				if (packetIn.getHasRotation()) {
					var25 = packetIn.getYaw();
					var26 = packetIn.getPitch();
				}

				playerEntity.onUpdateEntity();
				playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, var25, var26);

				if (!hasMoved) {
					return;
				}

				double var27 = var19 - playerEntity.posX;
				double var29 = var21 - playerEntity.posY;
				double var31 = var23 - playerEntity.posZ;
				final double var33 = Math.min(Math.abs(var27), Math.abs(playerEntity.motionX));
				final double var35 = Math.min(Math.abs(var29), Math.abs(playerEntity.motionY));
				final double var37 = Math.min(Math.abs(var31), Math.abs(playerEntity.motionZ));
				double var39 = var33 * var33 + var35 * var35 + var37 * var37;

				if (var39 > 100.0D && (!serverController.isSinglePlayer()
						|| !serverController.getServerOwner().equals(playerEntity.getName()))) {
					logger.warn(playerEntity.getName() + " moved too quickly! " + var27 + "," + var29 + "," + var31
							+ " (" + var33 + ", " + var35 + ", " + var37 + ")");
					setPlayerLocation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw,
							playerEntity.rotationPitch);
					return;
				}

				final float var41 = 0.0625F;
				final boolean var42 = var2.getCollidingBoundingBoxes(playerEntity,
						playerEntity.getEntityBoundingBox().contract(var41, var41, var41)).isEmpty();

				if (playerEntity.onGround && !packetIn.func_149465_i() && var29 > 0.0D) {
					playerEntity.jump();
				}

				playerEntity.moveEntity(var27, var29, var31);
				playerEntity.onGround = packetIn.func_149465_i();
				final double var43 = var29;
				var27 = var19 - playerEntity.posX;
				var29 = var21 - playerEntity.posY;

				if (var29 > -0.5D || var29 < 0.5D) {
					var29 = 0.0D;
				}

				var31 = var23 - playerEntity.posZ;
				var39 = var27 * var27 + var29 * var29 + var31 * var31;
				boolean var45 = false;

				if (var39 > 0.0625D && !playerEntity.isPlayerSleeping()
						&& !playerEntity.theItemInWorldManager.isCreative()) {
					var45 = true;
					logger.warn(playerEntity.getName() + " moved wrongly!");
				}

				playerEntity.setPositionAndRotation(var19, var21, var23, var25, var26);
				playerEntity.addMovementStat(playerEntity.posX - var3, playerEntity.posY - var5,
						playerEntity.posZ - var7);

				if (!playerEntity.noClip) {
					final boolean var46 = var2.getCollidingBoundingBoxes(playerEntity,
							playerEntity.getEntityBoundingBox().contract(var41, var41, var41)).isEmpty();

					if (var42 && (var45 || !var46) && !playerEntity.isPlayerSleeping()) {
						setPlayerLocation(lastPosX, lastPosY, lastPosZ, var25, var26);
						return;
					}
				}

				final AxisAlignedBB var49 = playerEntity.getEntityBoundingBox().expand(var41, var41, var41)
						.addCoord(0.0D, -0.55D, 0.0D);

				if (!serverController.isFlightAllowed() && !playerEntity.capabilities.allowFlying
						&& !var2.checkBlockCollision(var49)) {
					if (var43 >= -0.03125D) {
						++floatingTickCount;

						if (floatingTickCount > 80) {
							logger.warn(playerEntity.getName() + " was kicked for floating too long!");
							kickPlayerFromServer("Flying is not enabled on this server");
							return;
						}
					}
				} else {
					floatingTickCount = 0;
				}

				playerEntity.onGround = packetIn.func_149465_i();
				serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);
				playerEntity.handleFalling(playerEntity.posY - var17, packetIn.func_149465_i());
			} else if (networkTickCount - field_175090_f > 20) {
				setPlayerLocation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw, playerEntity.rotationPitch);
			}
		}
	}

	public void setPlayerLocation(final double x, final double y, final double z, final float yaw, final float pitch) {
		func_175089_a(x, y, z, yaw, pitch, Collections.emptySet());
	}

	public void func_175089_a(final double p_175089_1_, final double p_175089_3_, final double p_175089_5_,
			final float p_175089_7_, final float p_175089_8_, final Set p_175089_9_) {
		hasMoved = false;
		lastPosX = p_175089_1_;
		lastPosY = p_175089_3_;
		lastPosZ = p_175089_5_;

		if (p_175089_9_.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
			lastPosX += playerEntity.posX;
		}

		if (p_175089_9_.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
			lastPosY += playerEntity.posY;
		}

		if (p_175089_9_.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
			lastPosZ += playerEntity.posZ;
		}

		float var10 = p_175089_7_;
		float var11 = p_175089_8_;

		if (p_175089_9_.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
			var10 = p_175089_7_ + playerEntity.rotationYaw;
		}

		if (p_175089_9_.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
			var11 = p_175089_8_ + playerEntity.rotationPitch;
		}

		playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, var10, var11);
		playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(p_175089_1_, p_175089_3_, p_175089_5_,
				p_175089_7_, p_175089_8_, p_175089_9_));
	}

	/**
	 * Processes the player initiating/stopping digging on a particular spot, as
	 * well as a player dropping items?. (0: initiated, 1: reinitiated, 2? , 3-4
	 * drop item (respectively without or with player control), 5: stopped;
	 * x,y,z, side clicked on;)
	 */
	@Override
	public void processPlayerDigging(final C07PacketPlayerDigging packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final WorldServer var2 = serverController.worldServerForDimension(playerEntity.dimension);
		final BlockPos var3 = packetIn.getBlockPos();
		playerEntity.markPlayerActive();

		switch (NetHandlerPlayServer.SwitchAction.field_180224_a[packetIn.getAction().ordinal()]) {
		case 1:
			if (!playerEntity.isSpectatorMode()) {
				playerEntity.dropOneItem(false);
			}

			return;

		case 2:
			if (!playerEntity.isSpectatorMode()) {
				playerEntity.dropOneItem(true);
			}

			return;

		case 3:
			playerEntity.stopUsingItem();
			return;

		case 4:
		case 5:
		case 6:
			final double var4 = playerEntity.posX - (var3.getX() + 0.5D);
			final double var6 = playerEntity.posY - (var3.getY() + 0.5D) + 1.5D;
			final double var8 = playerEntity.posZ - (var3.getZ() + 0.5D);
			final double var10 = var4 * var4 + var6 * var6 + var8 * var8;

			if (var10 > 36.0D) {
				return;
			} else if (var3.getY() >= serverController.getBuildLimit()) {
				return;
			} else {
				if (packetIn.getAction() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
					if (!serverController.isBlockProtected(var2, var3, playerEntity)
							&& var2.getWorldBorder().contains(var3)) {
						playerEntity.theItemInWorldManager.func_180784_a(var3, packetIn.getFacing());
					} else {
						playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
					}
				} else {
					if (packetIn.getAction() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
						playerEntity.theItemInWorldManager.func_180785_a(var3);
					} else if (packetIn.getAction() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
						playerEntity.theItemInWorldManager.func_180238_e();
					}

					if (var2.getBlockState(var3).getBlock().getMaterial() != Material.air) {
						playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
					}
				}

				return;
			}

		default:
			throw new IllegalArgumentException("Invalid player action");
		}
	}

	/**
	 * Processes block placement and block activation (anvil, furnace, etc.)
	 */
	@Override
	public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final WorldServer var2 = serverController.worldServerForDimension(playerEntity.dimension);
		ItemStack var3 = playerEntity.inventory.getCurrentItem();
		boolean var4 = false;
		final BlockPos var5 = packetIn.func_179724_a();
		final EnumFacing var6 = EnumFacing.getFront(packetIn.getPlacedBlockDirection());
		playerEntity.markPlayerActive();

		if (packetIn.getPlacedBlockDirection() == 255) {
			if (var3 == null) {
				return;
			}

			playerEntity.theItemInWorldManager.tryUseItem(playerEntity, var2, var3);
		} else if (var5.getY() >= serverController.getBuildLimit() - 1
				&& (var6 == EnumFacing.UP || var5.getY() >= serverController.getBuildLimit())) {
			final ChatComponentTranslation var7 = new ChatComponentTranslation("build.tooHigh",
					new Object[] { serverController.getBuildLimit() });
			var7.getChatStyle().setColor(EnumChatFormatting.RED);
			playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var7));
			var4 = true;
		} else {
			if (hasMoved
					&& playerEntity.getDistanceSq(var5.getX() + 0.5D, var5.getY() + 0.5D, var5.getZ() + 0.5D) < 64.0D
					&& !serverController.isBlockProtected(var2, var5, playerEntity)
					&& var2.getWorldBorder().contains(var5)) {
				playerEntity.theItemInWorldManager.func_180236_a(playerEntity, var2, var3, var5, var6,
						packetIn.getPlacedBlockOffsetX(), packetIn.getPlacedBlockOffsetY(),
						packetIn.getPlacedBlockOffsetZ());
			}

			var4 = true;
		}

		if (var4) {
			playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5));
			playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5.offset(var6)));
		}

		var3 = playerEntity.inventory.getCurrentItem();

		if (var3 != null && var3.stackSize == 0) {
			playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = null;
			var3 = null;
		}

		if (var3 == null || var3.getMaxItemUseDuration() == 0) {
			playerEntity.isChangingQuantityOnly = true;
			playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = ItemStack
					.copyItemStack(playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem]);
			final Slot var8 = playerEntity.openContainer.getSlotFromInventory(playerEntity.inventory,
					playerEntity.inventory.currentItem);
			playerEntity.openContainer.detectAndSendChanges();
			playerEntity.isChangingQuantityOnly = false;

			if (!ItemStack.areItemStacksEqual(playerEntity.inventory.getCurrentItem(), packetIn.getStack())) {
				sendPacket(new S2FPacketSetSlot(playerEntity.openContainer.windowId, var8.slotNumber,
						playerEntity.inventory.getCurrentItem()));
			}
		}
	}

	@Override
	public void processPacketSpectate(final C18PacketSpectate p_175088_1_) {
		PacketThreadUtil.handlePacketWhileBaum(p_175088_1_, this, playerEntity.getServerForPlayer());

		if (playerEntity.isSpectatorMode()) {
			Entity var2 = null;
			final WorldServer[] var3 = serverController.worldServers;
			final int var4 = var3.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				final WorldServer var6 = var3[var5];

				if (var6 != null) {
					var2 = p_175088_1_.func_179727_a(var6);

					if (var2 != null) {
						break;
					}
				}
			}

			if (var2 != null) {
				playerEntity.func_175399_e(playerEntity);
				playerEntity.mountEntity((Entity) null);

				if (var2.worldObj != playerEntity.worldObj) {
					final WorldServer var7 = playerEntity.getServerForPlayer();
					final WorldServer var8 = (WorldServer) var2.worldObj;
					playerEntity.dimension = var2.dimension;
					sendPacket(new S07PacketRespawn(playerEntity.dimension, var7.getDifficulty(),
							var7.getWorldInfo().getTerrainType(), playerEntity.theItemInWorldManager.getGameType()));
					var7.removePlayerEntityDangerously(playerEntity);
					playerEntity.isDead = false;
					playerEntity.setLocationAndAngles(var2.posX, var2.posY, var2.posZ, var2.rotationYaw,
							var2.rotationPitch);

					if (playerEntity.isEntityAlive()) {
						var7.updateEntityWithOptionalForce(playerEntity, false);
						var8.spawnEntityInWorld(playerEntity);
						var8.updateEntityWithOptionalForce(playerEntity, false);
					}

					playerEntity.setWorld(var8);
					serverController.getConfigurationManager().func_72375_a(playerEntity, var7);
					playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
					playerEntity.theItemInWorldManager.setWorld(var8);
					serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(playerEntity, var8);
					serverController.getConfigurationManager().syncPlayerInventory(playerEntity);
				} else {
					playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
				}
			}
		}
	}

	@Override
	public void func_175086_a(final C19PacketResourcePackStatus p_175086_1_) {}

	/**
	 * Invoked when disconnecting, the parameter is a ChatComponent describing
	 * the reason for termination
	 */
	@Override
	public void onDisconnect(final IChatComponent reason) {
		logger.info(playerEntity.getName() + " lost connection: " + reason);
		serverController.refreshStatusNextTick();
		final ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left",
				new Object[] { playerEntity.getDisplayName() });
		var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		serverController.getConfigurationManager().sendChatMsg(var2);
		playerEntity.mountEntityAndWakeUp();
		serverController.getConfigurationManager().playerLoggedOut(playerEntity);

		if (serverController.isSinglePlayer() && playerEntity.getName().equals(serverController.getServerOwner())) {
			logger.info("Stopping singleplayer server as player logged out");
			serverController.initiateShutdown();
		}
	}

	public void sendPacket(final Packet packetIn) {
		if (packetIn instanceof S02PacketChat) {
			final S02PacketChat var2 = (S02PacketChat) packetIn;
			final EntityPlayer.EnumChatVisibility var3 = playerEntity.getChatVisibility();

			if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN) {
				return;
			}

			if (var3 == EntityPlayer.EnumChatVisibility.SYSTEM && !var2.isChat()) {
				return;
			}
		}

		try {
			netManager.sendPacket(packetIn);
		} catch (final Throwable var5) {
			final CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
			final CrashReportCategory var4 = var6.makeCategory("Packet being sent");
			var4.addCrashSectionCallable("Packet class", new Callable() {
				public String func_180225_a() {
					return packetIn.getClass().getCanonicalName();
				}

				@Override
				public Object call() {
					return func_180225_a();
				}
			});
			throw new ReportedException(var6);
		}
	}

	/**
	 * Updates which quickbar slot is selected
	 */
	@Override
	public void processHeldItemChange(final C09PacketHeldItemChange packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());

		if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
			playerEntity.inventory.currentItem = packetIn.getSlotId();
			playerEntity.markPlayerActive();
		} else {
			logger.warn(playerEntity.getName() + " tried to set an invalid carried item");
		}
	}

	/**
	 * Process chat messages (broadcast back to clients) and commands (executes)
	 */
	@Override
	public void processChatMessage(final C01PacketChatMessage packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());

		if (playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
			final ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
			var4.getChatStyle().setColor(EnumChatFormatting.RED);
			sendPacket(new S02PacketChat(var4));
		} else {
			playerEntity.markPlayerActive();
			String var2 = packetIn.getMessage();
			var2 = StringUtils.normalizeSpace(var2);

			for (int var3 = 0; var3 < var2.length(); ++var3) {
				if (!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3))) {
					kickPlayerFromServer("Illegal characters in chat");
					return;
				}
			}

			if (var2.startsWith("/")) {
				handleSlashCommand(var2);
			} else {
				final ChatComponentTranslation var5 = new ChatComponentTranslation("chat.type.text",
						new Object[] { playerEntity.getDisplayName(), var2 });
				serverController.getConfigurationManager().sendChatMsgImpl(var5, false);
			}

			chatSpamThresholdCount += 20;

			if (chatSpamThresholdCount > 200
					&& !serverController.getConfigurationManager().canSendCommands(playerEntity.getGameProfile())) {
				kickPlayerFromServer("disconnect.spam");
			}
		}
	}

	/**
	 * Handle commands that start with a /
	 */
	private void handleSlashCommand(final String command) {
		serverController.getCommandManager().executeCommand(playerEntity, command);
	}

	@Override
	public void func_175087_a(final C0APacketAnimation p_175087_1_) {
		PacketThreadUtil.handlePacketWhileBaum(p_175087_1_, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();
		playerEntity.swingItem();
	}

	/**
	 * Processes a range of action-types: sneaking, sprinting, waking from
	 * sleep, opening the inventory or setting jump height of the horse the
	 * player is riding
	 */
	@Override
	public void processEntityAction(final C0BPacketEntityAction packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();

		switch (NetHandlerPlayServer.SwitchAction.field_180222_b[packetIn.func_180764_b().ordinal()]) {
		case 1:
			playerEntity.setSneaking(true);
			break;

		case 2:
			playerEntity.setSneaking(false);
			break;

		case 3:
			playerEntity.setSprinting(true);
			break;

		case 4:
			playerEntity.setSprinting(false);
			break;

		case 5:
			playerEntity.wakeUpPlayer(false, true, true);
			hasMoved = false;
			break;

		case 6:
			if (playerEntity.ridingEntity instanceof EntityHorse) {
				((EntityHorse) playerEntity.ridingEntity).setJumpPower(packetIn.func_149512_e());
			}

			break;

		case 7:
			if (playerEntity.ridingEntity instanceof EntityHorse) {
				((EntityHorse) playerEntity.ridingEntity).openGUI(playerEntity);
			}

			break;

		default:
			throw new IllegalArgumentException("Invalid client command!");
		}
	}

	/**
	 * Processes interactions ((un)leashing, opening command block GUI) and
	 * attacks on an entity with players currently equipped item
	 */
	@Override
	public void processUseEntity(final C02PacketUseEntity packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final WorldServer var2 = serverController.worldServerForDimension(playerEntity.dimension);
		final Entity var3 = packetIn.getEntityFromWorld(var2);
		playerEntity.markPlayerActive();

		if (var3 != null) {
			final boolean var4 = playerEntity.canEntityBeSeen(var3);
			double var5 = 36.0D;

			if (!var4) {
				var5 = 9.0D;
			}

			if (playerEntity.getDistanceSqToEntity(var3) < var5) {
				if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT) {
					playerEntity.interactWith(var3);
				} else if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
					var3.func_174825_a(playerEntity, packetIn.func_179712_b());
				} else if (packetIn.getAction() == C02PacketUseEntity.Action.ATTACK) {
					if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow
							|| var3 == playerEntity) {
						kickPlayerFromServer("Attempting to attack an invalid entity");
						serverController
								.logWarning("Player " + playerEntity.getName() + " tried to attack an invalid entity");
						return;
					}

					playerEntity.attackTargetEntityWithCurrentItem(var3);
				}
			}
		}
	}

	/**
	 * Processes the client status updates: respawn attempt from player, opening
	 * statistics or achievements, or acquiring 'open inventory' achievement
	 */
	@Override
	public void processClientStatus(final C16PacketClientStatus packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();
		final C16PacketClientStatus.EnumState var2 = packetIn.getStatus();

		switch (NetHandlerPlayServer.SwitchAction.field_180223_c[var2.ordinal()]) {
		case 1:
			if (playerEntity.playerConqueredTheEnd) {
				playerEntity = serverController.getConfigurationManager().recreatePlayerEntity(playerEntity, 0, true);
			} else if (playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
				if (serverController.isSinglePlayer()
						&& playerEntity.getName().equals(serverController.getServerOwner())) {
					playerEntity.playerNetServerHandler
							.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
					serverController.deleteWorldAndStopServer();
				} else {
					final UserListBansEntry var3 = new UserListBansEntry(playerEntity.getGameProfile(), (Date) null,
							"(You just lost the game)", (Date) null, "Death in Hardcore");
					serverController.getConfigurationManager().getBannedPlayers().addEntry(var3);
					playerEntity.playerNetServerHandler
							.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
				}
			} else {
				if (playerEntity.getHealth() > 0.0F) {
					return;
				}

				playerEntity = serverController.getConfigurationManager().recreatePlayerEntity(playerEntity, 0, false);
			}

			break;

		case 2:
			playerEntity.getStatFile().func_150876_a(playerEntity);
			break;

		case 3:
			playerEntity.triggerAchievement(AchievementList.openInventory);
		}
	}

	/**
	 * Processes the client closing windows (container)
	 */
	@Override
	public void processCloseWindow(final C0DPacketCloseWindow packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.closeContainer();
	}

	/**
	 * Executes a container/inventory slot manipulation as indicated by the
	 * packet. Sends the serverside result if they didn't match the indicated
	 * result and prevents further manipulation by the player until he confirms
	 * that it has the same open container/inventory
	 */
	@Override
	public void processClickWindow(final C0EPacketClickWindow packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();

		if (playerEntity.openContainer.windowId == packetIn.getWindowId()
				&& playerEntity.openContainer.getCanCraft(playerEntity)) {
			if (playerEntity.isSpectatorMode()) {
				final ArrayList var2 = Lists.newArrayList();

				for (int var3 = 0; var3 < playerEntity.openContainer.inventorySlots.size(); ++var3) {
					var2.add(((Slot) playerEntity.openContainer.inventorySlots.get(var3)).getStack());
				}

				playerEntity.updateCraftingInventory(playerEntity.openContainer, var2);
			} else {
				final ItemStack var5 = playerEntity.openContainer.slotClick(packetIn.getSlotId(),
						packetIn.getUsedButton(), packetIn.getMode(), playerEntity);

				if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), var5)) {
					playerEntity.playerNetServerHandler.sendPacket(
							new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
					playerEntity.isChangingQuantityOnly = true;
					playerEntity.openContainer.detectAndSendChanges();
					playerEntity.updateHeldItem();
					playerEntity.isChangingQuantityOnly = false;
				} else {
					field_147372_n.addKey(playerEntity.openContainer.windowId, packetIn.getActionNumber());
					playerEntity.playerNetServerHandler.sendPacket(
							new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
					playerEntity.openContainer.setCanCraft(playerEntity, false);
					final ArrayList var6 = Lists.newArrayList();

					for (int var4 = 0; var4 < playerEntity.openContainer.inventorySlots.size(); ++var4) {
						var6.add(((Slot) playerEntity.openContainer.inventorySlots.get(var4)).getStack());
					}

					playerEntity.updateCraftingInventory(playerEntity.openContainer, var6);
				}
			}
		}
	}

	/**
	 * Enchants the item identified by the packet given some convoluted
	 * conditions (matching window, which should/shouldn't be in use?)
	 */
	@Override
	public void processEnchantItem(final C11PacketEnchantItem packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();

		if (playerEntity.openContainer.windowId == packetIn.getId()
				&& playerEntity.openContainer.getCanCraft(playerEntity) && !playerEntity.isSpectatorMode()) {
			playerEntity.openContainer.enchantItem(playerEntity, packetIn.getButton());
			playerEntity.openContainer.detectAndSendChanges();
		}
	}

	/**
	 * Update the server with an ItemStack in a slot.
	 */
	@Override
	public void processCreativeInventoryAction(final C10PacketCreativeInventoryAction packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());

		if (playerEntity.theItemInWorldManager.isCreative()) {
			final boolean var2 = packetIn.getSlotId() < 0;
			final ItemStack var3 = packetIn.getStack();

			if (var3 != null && var3.hasTagCompound() && var3.getTagCompound().hasKey("BlockEntityTag", 10)) {
				final NBTTagCompound var4 = var3.getTagCompound().getCompoundTag("BlockEntityTag");

				if (var4.hasKey("x") && var4.hasKey("y") && var4.hasKey("z")) {
					final BlockPos var5 = new BlockPos(var4.getInteger("x"), var4.getInteger("y"),
							var4.getInteger("z"));
					final TileEntity var6 = playerEntity.worldObj.getTileEntity(var5);

					if (var6 != null) {
						final NBTTagCompound var7 = new NBTTagCompound();
						var6.writeToNBT(var7);
						var7.removeTag("x");
						var7.removeTag("y");
						var7.removeTag("z");
						var3.setTagInfo("BlockEntityTag", var7);
					}
				}
			}

			final boolean var8 = packetIn.getSlotId() >= 1
					&& packetIn.getSlotId() < 36 + InventoryPlayer.getHotbarSize();
			final boolean var9 = var3 == null || var3.getItem() != null;
			final boolean var10 = var3 == null || var3.getMetadata() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;

			if (var8 && var9 && var10) {
				if (var3 == null) {
					playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), (ItemStack) null);
				} else {
					playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), var3);
				}

				playerEntity.inventoryContainer.setCanCraft(playerEntity, true);
			} else if (var2 && var9 && var10 && itemDropThreshold < 200) {
				itemDropThreshold += 20;
				final EntityItem var11 = playerEntity.dropPlayerItemWithRandomChoice(var3, true);

				if (var11 != null) {
					var11.setAgeToCreativeDespawnTime();
				}
			}
		}
	}

	/**
	 * Received in response to the server requesting to confirm that the
	 * client-side open container matches the servers' after a mismatched
	 * container-slot manipulation. It will unlock the player's ability to
	 * manipulate the container contents
	 */
	@Override
	public void processConfirmTransaction(final C0FPacketConfirmTransaction packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final Short var2 = (Short) field_147372_n.lookup(playerEntity.openContainer.windowId);

		if (var2 != null && packetIn.getUid() == var2 && playerEntity.openContainer.windowId == packetIn.getId()
				&& !playerEntity.openContainer.getCanCraft(playerEntity) && !playerEntity.isSpectatorMode()) {
			playerEntity.openContainer.setCanCraft(playerEntity, true);
		}
	}

	@Override
	public void processUpdateSign(final C12PacketUpdateSign packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.markPlayerActive();
		final WorldServer var2 = serverController.worldServerForDimension(playerEntity.dimension);
		final BlockPos var3 = packetIn.func_179722_a();

		if (var2.isBlockLoaded(var3)) {
			final TileEntity var4 = var2.getTileEntity(var3);

			if (!(var4 instanceof TileEntitySign)) {
				return;
			}

			final TileEntitySign var5 = (TileEntitySign) var4;

			if (!var5.getIsEditable() || var5.func_145911_b() != playerEntity) {
				serverController
						.logWarning("Player " + playerEntity.getName() + " just tried to change non-editable sign");
				return;
			}

			System.arraycopy(packetIn.func_180768_b(), 0, var5.signText, 0, 4);
			var5.markDirty();
			var2.markBlockForUpdate(var3);
		}
	}

	/**
	 * Updates a players' ping statistics
	 */
	@Override
	public void processKeepAlive(final C00PacketKeepAlive packetIn) {
		if (packetIn.getKey() == field_147378_h) {
			final int var2 = (int) (currentTimeMillis() - lastPingTime);
			playerEntity.ping = (playerEntity.ping * 3 + var2) / 4;
		}
	}

	private long currentTimeMillis() {
		return System.nanoTime() / 1000000L;
	}

	/**
	 * Processes a player starting/stopping flying
	 */
	@Override
	public void processPlayerAbilities(final C13PacketPlayerAbilities packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.capabilities.isFlying = packetIn.isFlying() && playerEntity.capabilities.allowFlying;
	}

	/**
	 * Retrieves possible tab completions for the requested command string and
	 * sends them to the client
	 */
	@Override
	public void processTabComplete(final C14PacketTabComplete packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = serverController
				.getTabComplete(playerEntity, packetIn.getMessage(), packetIn.getBlockPos()).iterator();

		while (var3.hasNext()) {
			final String var4 = (String) var3.next();
			var2.add(var4);
		}

		playerEntity.playerNetServerHandler
				.sendPacket(new S3APacketTabComplete((String[]) var2.toArray(new String[var2.size()])));
	}

	/**
	 * Updates serverside copy of client settings: language, render distance,
	 * chat visibility, chat colours, difficulty, and whether to show the cape
	 */
	@Override
	public void processClientSettings(final C15PacketClientSettings packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		playerEntity.handleClientSettings(packetIn);
	}

	/**
	 * Synchronizes serverside and clientside book contents and signing
	 */
	@Override
	public void processVanilla250Packet(final C17PacketCustomPayload packetIn) {
		PacketThreadUtil.handlePacketWhileBaum(packetIn, this, playerEntity.getServerForPlayer());
		PacketBuffer var2;
		ItemStack var3;
		ItemStack var4;

		if ("MC|BEdit".equals(packetIn.getChannelName())) {
			var2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));

			try {
				var3 = var2.readItemStackFromBuffer();

				if (var3 == null) {
					return;
				}

				if (!ItemWritableBook.validBookPageTagContents(var3.getTagCompound())) {
					throw new IOException("Invalid book tag!");
				}

				var4 = playerEntity.inventory.getCurrentItem();

				if (var4 != null) {
					if (var3.getItem() == Items.writable_book && var3.getItem() == var4.getItem()) {
						var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
					}

					return;
				}
			} catch (final Exception var38) {
				logger.error("Couldn\'t handle book info", var38);
				return;
			}
			finally {
				var2.release();
			}

			return;
		} else if ("MC|BSign".equals(packetIn.getChannelName())) {
			var2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));

			try {
				var3 = var2.readItemStackFromBuffer();

				if (var3 != null) {
					if (!ItemEditableBook.validBookTagContents(var3.getTagCompound())) {
						throw new IOException("Invalid book tag!");
					}

					var4 = playerEntity.inventory.getCurrentItem();

					if (var4 == null) {
						return;
					}

					if (var3.getItem() == Items.written_book && var4.getItem() == Items.writable_book) {
						var4.setTagInfo("author", new NBTTagString(playerEntity.getName()));
						var4.setTagInfo("title", new NBTTagString(var3.getTagCompound().getString("title")));
						var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
						var4.setItem(Items.written_book);
					}

					return;
				}
			} catch (final Exception var36) {
				logger.error("Couldn\'t sign book", var36);
				return;
			}
			finally {
				var2.release();
			}

			return;
		} else if ("MC|TrSel".equals(packetIn.getChannelName())) {
			try {
				final int var40 = packetIn.getBufferData().readInt();
				final Container var42 = playerEntity.openContainer;

				if (var42 instanceof ContainerMerchant) {
					((ContainerMerchant) var42).setCurrentRecipeIndex(var40);
				}
			} catch (final Exception var35) {
				logger.error("Couldn\'t select trade", var35);
			}
		} else if ("MC|AdvCdm".equals(packetIn.getChannelName())) {
			if (!serverController.isCommandBlockEnabled()) {
				playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
			} else if (playerEntity.canCommandSenderUseCommand(2, "") && playerEntity.capabilities.isCreativeMode) {
				var2 = packetIn.getBufferData();

				try {
					final byte var43 = var2.readByte();
					CommandBlockLogic var46 = null;

					if (var43 == 0) {
						final TileEntity var5 = playerEntity.worldObj
								.getTileEntity(new BlockPos(var2.readInt(), var2.readInt(), var2.readInt()));

						if (var5 instanceof TileEntityCommandBlock) {
							var46 = ((TileEntityCommandBlock) var5).getCommandBlockLogic();
						}
					} else if (var43 == 1) {
						final Entity var48 = playerEntity.worldObj.getEntityByID(var2.readInt());

						if (var48 instanceof EntityMinecartCommandBlock) {
							var46 = ((EntityMinecartCommandBlock) var48).func_145822_e();
						}
					}

					final String var49 = var2.readStringFromBuffer(var2.readableBytes());
					final boolean var6 = var2.readBoolean();

					if (var46 != null) {
						var46.setCommand(var49);
						var46.func_175573_a(var6);

						if (!var6) {
							var46.func_145750_b((IChatComponent) null);
						}

						var46.func_145756_e();
						playerEntity.addChatMessage(
								new ChatComponentTranslation("advMode.setCommand.success", new Object[] { var49 }));
					}
				} catch (final Exception var33) {
					logger.error("Couldn\'t set command block", var33);
				}
				finally {
					var2.release();
				}
			} else {
				playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
			}
		} else if ("MC|Beacon".equals(packetIn.getChannelName())) {
			if (playerEntity.openContainer instanceof ContainerBeacon) {
				try {
					var2 = packetIn.getBufferData();
					final int var44 = var2.readInt();
					final int var47 = var2.readInt();
					final ContainerBeacon var50 = (ContainerBeacon) playerEntity.openContainer;
					final Slot var51 = var50.getSlot(0);

					if (var51.getHasStack()) {
						var51.decrStackSize(1);
						final IInventory var7 = var50.func_180611_e();
						var7.setField(1, var44);
						var7.setField(2, var47);
						var7.markDirty();
					}
				} catch (final Exception var32) {
					logger.error("Couldn\'t set beacon", var32);
				}
			}
		} else if ("MC|ItemName".equals(packetIn.getChannelName())
				&& playerEntity.openContainer instanceof ContainerRepair) {
			final ContainerRepair var41 = (ContainerRepair) playerEntity.openContainer;

			if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1) {
				final String var45 = ChatAllowedCharacters
						.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));

				if (var45.length() <= 30) {
					var41.updateItemName(var45);
				}
			} else {
				var41.updateItemName("");
			}
		}
	}

	static final class SwitchAction {
		static final int[] field_180224_a;

		static final int[] field_180222_b;

		static final int[] field_180223_c = new int[C16PacketClientStatus.EnumState.values().length];
		static {
			try {
				field_180223_c[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var16) {}

			try {
				field_180223_c[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
			} catch (final NoSuchFieldError var15) {}

			try {
				field_180223_c[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
			} catch (final NoSuchFieldError var14) {}

			field_180222_b = new int[C0BPacketEntityAction.Action.values().length];

			try {
				field_180222_b[C0BPacketEntityAction.Action.START_SNEAKING.ordinal()] = 1;
			} catch (final NoSuchFieldError var13) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = 2;
			} catch (final NoSuchFieldError var12) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.START_SPRINTING.ordinal()] = 3;
			} catch (final NoSuchFieldError var11) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = 4;
			} catch (final NoSuchFieldError var10) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = 5;
			} catch (final NoSuchFieldError var9) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.RIDING_JUMP.ordinal()] = 6;
			} catch (final NoSuchFieldError var8) {}

			try {
				field_180222_b[C0BPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = 7;
			} catch (final NoSuchFieldError var7) {}

			field_180224_a = new int[C07PacketPlayerDigging.Action.values().length];

			try {
				field_180224_a[C07PacketPlayerDigging.Action.DROP_ITEM.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {}

			try {
				field_180224_a[C07PacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {}

			try {
				field_180224_a[C07PacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {}

			try {
				field_180224_a[C07PacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_180224_a[C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_180224_a[C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
