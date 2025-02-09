package net.minecraft.entity.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {

public static final int EaZy = 1192;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private String translator = "en_US";

	/**
	 * The NetServerHandler assigned to this player by the
	 * ServerConfigurationManager.
	 */
	public NetHandlerPlayServer playerNetServerHandler;

	/** Reference to the MinecraftServer object. */
	public final MinecraftServer mcServer;

	/** The ItemInWorldManager belonging to this player */
	public final ItemInWorldManager theItemInWorldManager;

	/** player X position as seen by PlayerManager */
	public double managedPosX;

	/** player Z position as seen by PlayerManager */
	public double managedPosZ;

	/** LinkedList that holds the loaded chunks. */
	public final List loadedChunks = Lists.newLinkedList();

	/** entities added to this list will be packet29'd to the player */
	private final List destroyedItemsNetCache = Lists.newLinkedList();
	private final StatisticsFile statsFile;
	private float field_130068_bO = Float.MIN_VALUE;

	/** amount of health the client was last set to */
	private float lastHealth = -1.0E8F;

	/** set to foodStats.GetFoodLevel */
	private int lastFoodLevel = -99999999;

	/** set to foodStats.getSaturationLevel() == 0.0F each tick */
	private boolean wasHungry = true;

	/** Amount of experience the client was last set to */
	private int lastExperience = -99999999;
	private int respawnInvulnerabilityTicks = 60;
	private EntityPlayer.EnumChatVisibility chatVisibility;
	private boolean chatColours = true;
	private long playerLastActiveTime = System.currentTimeMillis();
	private Entity field_175401_bS = null;

	/**
	 * The currently in use window ID. Incremented every time a window is
	 * opened.
	 */
	private int currentWindowId;

	/**
	 * set to true when player is moving quantity of items from one inventory to
	 * another(crafting) but item in either slot is not changed
	 */
	public boolean isChangingQuantityOnly;
	public int ping;

	/**
	 * Set when a player beats the ender dragon, used to respawn the player at
	 * the spawn point while retaining inventory and XP
	 */
	public boolean playerConqueredTheEnd;
	// private static final String __OBFID = "http://https://fuckuskid00001440";

	public EntityPlayerMP(final MinecraftServer server, final WorldServer worldIn, final GameProfile profile,
			final ItemInWorldManager interactionManager) {
		super(worldIn, profile);
		interactionManager.thisPlayerMP = this;
		theItemInWorldManager = interactionManager;
		BlockPos var5 = worldIn.getSpawnPoint();

		if (!worldIn.provider.getHasNoSky()
				&& worldIn.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
			int var6 = Math.max(5, server.getSpawnProtectionSize() - 6);
			final int var7 = MathHelper
					.floor_double(worldIn.getWorldBorder().getClosestDistance(var5.getX(), var5.getZ()));

			if (var7 < var6) {
				var6 = var7;
			}

			if (var7 <= 1) {
				var6 = 1;
			}

			var5 = worldIn.func_175672_r(var5.add(rand.nextInt(var6 * 2) - var6, 0, rand.nextInt(var6 * 2) - var6));
		}

		mcServer = server;
		statsFile = server.getConfigurationManager().getPlayerStatsFile(this);
		stepHeight = 0.0F;
		func_174828_a(var5, 0.0F, 0.0F);

		while (!worldIn.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty() && posY < 255.0D) {
			setPosition(posX, posY + 1.0D, posZ);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("playerGameType", 99)) {
			if (MinecraftServer.getServer().getForceGamemode()) {
				theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
			} else {
				theItemInWorldManager
						.setGameType(WorldSettings.GameType.getByID(tagCompund.getInteger("playerGameType")));
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("playerGameType", theItemInWorldManager.getGameType().getID());
	}

	/**
	 * Add experience levels to this player.
	 */
	@Override
	public void addExperienceLevel(final int p_82242_1_) {
		super.addExperienceLevel(p_82242_1_);
		lastExperience = -1;
	}

	@Override
	public void func_71013_b(final int p_71013_1_) {
		super.func_71013_b(p_71013_1_);
		lastExperience = -1;
	}

	public void addSelfToInternalCraftingInventory() {
		openContainer.onCraftGuiOpened(this);
	}

	@Override
	public void func_152111_bt() {
		super.func_152111_bt();
		playerNetServerHandler
				.sendPacket(new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
	}

	@Override
	public void func_152112_bu() {
		super.func_152112_bu();
		playerNetServerHandler
				.sendPacket(new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		theItemInWorldManager.updateBlockRemoving();
		--respawnInvulnerabilityTicks;

		if (hurtResistantTime > 0) {
			--hurtResistantTime;
		}

		openContainer.detectAndSendChanges();

		if (!worldObj.isRemote && !openContainer.canInteractWith(this)) {
			closeScreen();
			openContainer = inventoryContainer;
		}

		while (!destroyedItemsNetCache.isEmpty()) {
			final int var1 = Math.min(destroyedItemsNetCache.size(), Integer.MAX_VALUE);
			final int[] var2 = new int[var1];
			final Iterator var3 = destroyedItemsNetCache.iterator();
			int var4 = 0;

			while (var3.hasNext() && var4 < var1) {
				var2[var4++] = ((Integer) var3.next());
				var3.remove();
			}

			playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
		}

		if (!loadedChunks.isEmpty()) {
			final ArrayList var6 = Lists.newArrayList();
			final Iterator var8 = loadedChunks.iterator();
			final ArrayList var9 = Lists.newArrayList();
			Chunk var5;

			while (var8.hasNext() && var6.size() < 10) {
				final ChunkCoordIntPair var10 = (ChunkCoordIntPair) var8.next();

				if (var10 != null) {
					if (worldObj.isBlockLoaded(new BlockPos(var10.chunkXPos << 4, 0, var10.chunkZPos << 4))) {
						var5 = worldObj.getChunkFromChunkCoords(var10.chunkXPos, var10.chunkZPos);

						if (var5.isPopulated()) {
							var6.add(var5);
							var9.addAll(((WorldServer) worldObj).func_147486_a(var10.chunkXPos * 16, 0,
									var10.chunkZPos * 16, var10.chunkXPos * 16 + 16, 256, var10.chunkZPos * 16 + 16));
							var8.remove();
						}
					}
				} else {
					var8.remove();
				}
			}

			if (!var6.isEmpty()) {
				if (var6.size() == 1) {
					playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk) var6.get(0), true, 65535));
				} else {
					playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var6));
				}

				Iterator var11 = var9.iterator();

				while (var11.hasNext()) {
					final TileEntity var12 = (TileEntity) var11.next();
					sendTileEntityUpdate(var12);
				}

				var11 = var6.iterator();

				while (var11.hasNext()) {
					var5 = (Chunk) var11.next();
					getServerForPlayer().getEntityTracker().func_85172_a(this, var5);
				}
			}
		}

		final Entity var7 = func_175398_C();

		if (var7 != this) {
			if (!var7.isEntityAlive()) {
				func_175399_e(this);
			} else {
				setPositionAndRotation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
				mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);

				if (isSneaking()) {
					func_175399_e(this);
				}
			}
		}
	}

	public void onUpdateEntity() {
		try {
			super.onUpdate();

			for (int var1 = 0; var1 < inventory.getSizeInventory(); ++var1) {
				final ItemStack var6 = inventory.getStackInSlot(var1);

				if (var6 != null && var6.getItem().isMap()) {
					final Packet var8 = ((ItemMapBase) var6.getItem()).createMapDataPacket(var6, worldObj, this);

					if (var8 != null) {
						playerNetServerHandler.sendPacket(var8);
					}
				}
			}

			if (getHealth() != lastHealth || lastFoodLevel != foodStats.getFoodLevel()
					|| foodStats.getSaturationLevel() == 0.0F != wasHungry) {
				playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(getHealth(), foodStats.getFoodLevel(),
						foodStats.getSaturationLevel()));
				lastHealth = getHealth();
				lastFoodLevel = foodStats.getFoodLevel();
				wasHungry = foodStats.getSaturationLevel() == 0.0F;
			}

			if (getHealth() + getAbsorptionAmount() != field_130068_bO) {
				field_130068_bO = getHealth() + getAbsorptionAmount();
				final Collection var5 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
				final Iterator var7 = var5.iterator();

				while (var7.hasNext()) {
					final ScoreObjective var9 = (ScoreObjective) var7.next();
					getWorldScoreboard().getValueFromObjective(getName(), var9)
							.func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
				}
			}

			if (experienceTotal != lastExperience) {
				lastExperience = experienceTotal;
				playerNetServerHandler
						.sendPacket(new S1FPacketSetExperience(experience, experienceTotal, experienceLevel));
			}

			if (ticksExisted % 20 * 5 == 0 && !getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
				func_147098_j();
			}
		} catch (final Throwable var4) {
			final CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
			final CrashReportCategory var3 = var2.makeCategory("Player being ticked");
			addEntityCrashInfo(var3);
			throw new ReportedException(var2);
		}
	}

	protected void func_147098_j() {
		final BiomeGenBase var1 = worldObj
				.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)));
		final String var2 = var1.biomeName;
		JsonSerializableSet var3 = (JsonSerializableSet) getStatFile().func_150870_b(AchievementList.exploreAllBiomes);

		if (var3 == null) {
			var3 = (JsonSerializableSet) getStatFile().func_150872_a(AchievementList.exploreAllBiomes,
					new JsonSerializableSet());
		}

		var3.add(var2);

		if (getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes)
				&& var3.size() >= BiomeGenBase.explorationBiomesList.size()) {
			final HashSet var4 = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
			final Iterator var5 = var3.iterator();

			while (var5.hasNext()) {
				final String var6 = (String) var5.next();
				final Iterator var7 = var4.iterator();

				while (var7.hasNext()) {
					final BiomeGenBase var8 = (BiomeGenBase) var7.next();

					if (var8.biomeName.equals(var6)) {
						var7.remove();
					}
				}

				if (var4.isEmpty()) {
					break;
				}
			}

			if (var4.isEmpty()) {
				triggerAchievement(AchievementList.exploreAllBiomes);
			}
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		if (worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
			final Team var2 = getTeam();

			if (var2 != null && var2.func_178771_j() != Team.EnumVisible.ALWAYS) {
				if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
					mcServer.getConfigurationManager().func_177453_a(this, getCombatTracker().func_151521_b());
				} else if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
					mcServer.getConfigurationManager().func_177452_b(this, getCombatTracker().func_151521_b());
				}
			} else {
				mcServer.getConfigurationManager().sendChatMsg(getCombatTracker().func_151521_b());
			}
		}

		if (!worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			inventory.dropAllItems();
		}

		final Collection var6 = worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
		final Iterator var3 = var6.iterator();

		while (var3.hasNext()) {
			final ScoreObjective var4 = (ScoreObjective) var3.next();
			final Score var5 = getWorldScoreboard().getValueFromObjective(getName(), var4);
			var5.func_96648_a();
		}

		final EntityLivingBase var7 = func_94060_bK();

		if (var7 != null) {
			final EntityList.EntityEggInfo var8 = (EntityList.EntityEggInfo) EntityList.entityEggs
					.get(EntityList.getEntityID(var7));

			if (var8 != null) {
				triggerAchievement(var8.field_151513_e);
			}

			var7.addToPlayerScore(this, scoreValue);
		}

		triggerAchievement(StatList.deathsStat);
		func_175145_a(StatList.timeSinceDeathStat);
		getCombatTracker().func_94549_h();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			final boolean var3 = mcServer.isDedicatedServer() && func_175400_cq() && "fall".equals(source.damageType);

			if (!var3 && respawnInvulnerabilityTicks > 0 && source != DamageSource.outOfWorld) {
				return false;
			} else {
				if (source instanceof EntityDamageSource) {
					final Entity var4 = source.getEntity();

					if (var4 instanceof EntityPlayer && !canAttackPlayer((EntityPlayer) var4)) {
						return false;
					}

					if (var4 instanceof EntityArrow) {
						final EntityArrow var5 = (EntityArrow) var4;

						if (var5.shootingEntity instanceof EntityPlayer
								&& !canAttackPlayer((EntityPlayer) var5.shootingEntity)) {
							return false;
						}
					}
				}

				return super.attackEntityFrom(source, amount);
			}
		}
	}

	@Override
	public boolean canAttackPlayer(final EntityPlayer other) {
		return !func_175400_cq() ? false : super.canAttackPlayer(other);
	}

	private boolean func_175400_cq() {
		return mcServer.isPVPEnabled();
	}

	/**
	 * Teleports the entity to another dimension. Params: Dimension number to
	 * teleport to
	 */
	@Override
	public void travelToDimension(int dimensionId) {
		if (dimension == 1 && dimensionId == 1) {
			triggerAchievement(AchievementList.theEnd2);
			worldObj.removeEntity(this);
			playerConqueredTheEnd = true;
			playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
		} else {
			if (dimension == 0 && dimensionId == 1) {
				triggerAchievement(AchievementList.theEnd);
				final BlockPos var2 = mcServer.worldServerForDimension(dimensionId).func_180504_m();

				if (var2 != null) {
					playerNetServerHandler.setPlayerLocation(var2.getX(), var2.getY(), var2.getZ(), 0.0F, 0.0F);
				}

				dimensionId = 1;
			} else {
				triggerAchievement(AchievementList.portal);
			}

			mcServer.getConfigurationManager().transferPlayerToDimension(this, dimensionId);
			lastExperience = -1;
			lastHealth = -1.0F;
			lastFoodLevel = -1;
		}
	}

	@Override
	public boolean func_174827_a(final EntityPlayerMP p_174827_1_) {
		return p_174827_1_.isSpectatorMode() ? func_175398_C() == this
				: isSpectatorMode() ? false : super.func_174827_a(p_174827_1_);
	}

	private void sendTileEntityUpdate(final TileEntity p_147097_1_) {
		if (p_147097_1_ != null) {
			final Packet var2 = p_147097_1_.getDescriptionPacket();

			if (var2 != null) {
				playerNetServerHandler.sendPacket(var2);
			}
		}
	}

	/**
	 * Called whenever an item is picked up from walking over it. Args:
	 * pickedUpEntity, stackSize
	 */
	@Override
	public void onItemPickup(final Entity p_71001_1_, final int p_71001_2_) {
		super.onItemPickup(p_71001_1_, p_71001_2_);
		openContainer.detectAndSendChanges();
	}

	@Override
	public EntityPlayer.EnumStatus func_180469_a(final BlockPos p_180469_1_) {
		final EntityPlayer.EnumStatus var2 = super.func_180469_a(p_180469_1_);

		if (var2 == EntityPlayer.EnumStatus.OK) {
			final S0APacketUseBed var3 = new S0APacketUseBed(this, p_180469_1_);
			getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, var3);
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
			playerNetServerHandler.sendPacket(var3);
		}

		return var2;
	}

	/**
	 * Wake up the player if they're sleeping.
	 */
	@Override
	public void wakeUpPlayer(final boolean p_70999_1_, final boolean updateWorldFlag, final boolean setSpawn) {
		if (isPlayerSleeping()) {
			getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
		}

		super.wakeUpPlayer(p_70999_1_, updateWorldFlag, setSpawn);

		if (playerNetServerHandler != null) {
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	/**
	 * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
	 */
	@Override
	public void mountEntity(final Entity entityIn) {
		final Entity var2 = ridingEntity;
		super.mountEntity(entityIn);

		if (entityIn != var2) {
			playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, ridingEntity));
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}

	@Override
	protected void func_180433_a(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_,
			final BlockPos p_180433_5_) {}

	/**
	 * process player falling based on movement packet
	 */
	public void handleFalling(final double p_71122_1_, final boolean p_71122_3_) {
		final int var4 = MathHelper.floor_double(posX);
		final int var5 = MathHelper.floor_double(posY - 0.20000000298023224D);
		final int var6 = MathHelper.floor_double(posZ);
		BlockPos var7 = new BlockPos(var4, var5, var6);
		Block var8 = worldObj.getBlockState(var7).getBlock();

		if (var8.getMaterial() == Material.air) {
			final Block var9 = worldObj.getBlockState(var7.offsetDown()).getBlock();

			if (var9 instanceof BlockFence || var9 instanceof BlockWall || var9 instanceof BlockFenceGate) {
				var7 = var7.offsetDown();
				var8 = worldObj.getBlockState(var7).getBlock();
			}
		}

		super.func_180433_a(p_71122_1_, p_71122_3_, var8, var7);
	}

	@Override
	public void func_175141_a(final TileEntitySign p_175141_1_) {
		p_175141_1_.func_145912_a(this);
		playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(p_175141_1_.getPos()));
	}

	/**
	 * get the next window id to use
	 */
	private void getNextWindowId() {
		currentWindowId = currentWindowId % 100 + 1;
	}

	@Override
	public void displayGui(final IInteractionObject guiOwner) {
		getNextWindowId();
		playerNetServerHandler
				.sendPacket(new S2DPacketOpenWindow(currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
		openContainer = guiOwner.createContainer(inventory, this);
		openContainer.windowId = currentWindowId;
		openContainer.onCraftGuiOpened(this);
	}

	/**
	 * Displays the GUI for interacting with a chest inventory. Args:
	 * chestInventory
	 */
	@Override
	public void displayGUIChest(final IInventory chestInventory) {
		if (openContainer != inventoryContainer) {
			closeScreen();
		}

		if (chestInventory instanceof ILockableContainer) {
			final ILockableContainer var2 = (ILockableContainer) chestInventory;

			if (var2.isLocked() && !func_175146_a(var2.getLockCode()) && !isSpectatorMode()) {
				playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked",
						new Object[] { chestInventory.getDisplayName() }), (byte) 2));
				playerNetServerHandler
						.sendPacket(new S29PacketSoundEffect("random.door_close", posX, posY, posZ, 1.0F, 1.0F));
				return;
			}
		}

		getNextWindowId();

		if (chestInventory instanceof IInteractionObject) {
			playerNetServerHandler.sendPacket(
					new S2DPacketOpenWindow(currentWindowId, ((IInteractionObject) chestInventory).getGuiID(),
							chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
			openContainer = ((IInteractionObject) chestInventory).createContainer(inventory, this);
		} else {
			playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, "minecraft:container",
					chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
			openContainer = new ContainerChest(inventory, chestInventory, this);
		}

		openContainer.windowId = currentWindowId;
		openContainer.onCraftGuiOpened(this);
	}

	@Override
	public void displayVillagerTradeGui(final IMerchant villager) {
		getNextWindowId();
		openContainer = new ContainerMerchant(inventory, villager, worldObj);
		openContainer.windowId = currentWindowId;
		openContainer.onCraftGuiOpened(this);
		final InventoryMerchant var2 = ((ContainerMerchant) openContainer).getMerchantInventory();
		final IChatComponent var3 = villager.getDisplayName();
		playerNetServerHandler.sendPacket(
				new S2DPacketOpenWindow(currentWindowId, "minecraft:villager", var3, var2.getSizeInventory()));
		final MerchantRecipeList var4 = villager.getRecipes(this);

		if (var4 != null) {
			final PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
			var5.writeInt(currentWindowId);
			var4.func_151391_a(var5);
			playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
		}
	}

	@Override
	public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
		if (openContainer != inventoryContainer) {
			closeScreen();
		}

		getNextWindowId();
		playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, "EntityHorse",
				p_110298_2_.getDisplayName(), p_110298_2_.getSizeInventory(), p_110298_1_.getEntityId()));
		openContainer = new ContainerHorseInventory(inventory, p_110298_2_, p_110298_1_, this);
		openContainer.windowId = currentWindowId;
		openContainer.onCraftGuiOpened(this);
	}

	/**
	 * Displays the GUI for interacting with a book.
	 */
	@Override
	public void displayGUIBook(final ItemStack bookStack) {
		final Item var2 = bookStack.getItem();

		if (var2 == Items.written_book) {
			playerNetServerHandler
					.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
		}
	}

	/**
	 * Sends the contents of an inventory slot to the client-side Container.
	 * This doesn't have to match the actual contents of that slot. Args:
	 * Container, slot number, slot contents
	 */
	@Override
	public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
		if (!(p_71111_1_.getSlot(p_71111_2_) instanceof SlotCrafting)) {
			if (!isChangingQuantityOnly) {
				playerNetServerHandler.sendPacket(new S2FPacketSetSlot(p_71111_1_.windowId, p_71111_2_, p_71111_3_));
			}
		}
	}

	public void sendContainerToPlayer(final Container p_71120_1_) {
		updateCraftingInventory(p_71120_1_, p_71120_1_.getInventory());
	}

	/**
	 * update the crafting window inventory with the items in the list
	 */
	@Override
	public void updateCraftingInventory(final Container p_71110_1_, final List p_71110_2_) {
		playerNetServerHandler.sendPacket(new S30PacketWindowItems(p_71110_1_.windowId, p_71110_2_));
		playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, inventory.getItemStack()));
	}

	/**
	 * Sends two ints to the client-side Container. Used for furnace burning
	 * time, smelting progress, brewing progress, and enchanting level. Normally
	 * the first int identifies which variable to update, and the second
	 * contains the new value. Both are truncated to shorts in non-local SMP.
	 */
	@Override
	public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
		playerNetServerHandler.sendPacket(new S31PacketWindowProperty(p_71112_1_.windowId, p_71112_2_, p_71112_3_));
	}

	@Override
	public void func_175173_a(final Container p_175173_1_, final IInventory p_175173_2_) {
		for (int var3 = 0; var3 < p_175173_2_.getFieldCount(); ++var3) {
			playerNetServerHandler
					.sendPacket(new S31PacketWindowProperty(p_175173_1_.windowId, var3, p_175173_2_.getField(var3)));
		}
	}

	/**
	 * set current crafting inventory back to the 2x2 square
	 */
	@Override
	public void closeScreen() {
		playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(openContainer.windowId));
		closeContainer();
	}

	/**
	 * updates item held by mouse
	 */
	public void updateHeldItem() {
		if (!isChangingQuantityOnly) {
			playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, inventory.getItemStack()));
		}
	}

	/**
	 * Closes the container the player currently has open.
	 */
	public void closeContainer() {
		openContainer.onContainerClosed(this);
		openContainer = inventoryContainer;
	}

	public void setEntityActionState(final float p_110430_1_, final float p_110430_2_, final boolean p_110430_3_,
			final boolean p_110430_4_) {
		if (ridingEntity != null) {
			if (p_110430_1_ >= -1.0F && p_110430_1_ <= 1.0F) {
				moveStrafing = p_110430_1_;
			}

			if (p_110430_2_ >= -1.0F && p_110430_2_ <= 1.0F) {
				moveForward = p_110430_2_;
			}

			isJumping = p_110430_3_;
			setSneaking(p_110430_4_);
		}
	}

	/**
	 * Adds a value to a statistic field.
	 */
	@Override
	public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {
		if (p_71064_1_ != null) {
			statsFile.func_150871_b(this, p_71064_1_, p_71064_2_);
			final Iterator var3 = getWorldScoreboard().func_96520_a(p_71064_1_.func_150952_k()).iterator();

			while (var3.hasNext()) {
				final ScoreObjective var4 = (ScoreObjective) var3.next();
				getWorldScoreboard().getValueFromObjective(getName(), var4).increseScore(p_71064_2_);
			}

			if (statsFile.func_150879_e()) {
				statsFile.func_150876_a(this);
			}
		}
	}

	@Override
	public void func_175145_a(final StatBase p_175145_1_) {
		if (p_175145_1_ != null) {
			statsFile.func_150873_a(this, p_175145_1_, 0);
			final Iterator var2 = getWorldScoreboard().func_96520_a(p_175145_1_.func_150952_k()).iterator();

			while (var2.hasNext()) {
				final ScoreObjective var3 = (ScoreObjective) var2.next();
				getWorldScoreboard().getValueFromObjective(getName(), var3).setScorePoints(0);
			}

			if (statsFile.func_150879_e()) {
				statsFile.func_150876_a(this);
			}
		}
	}

	public void mountEntityAndWakeUp() {
		if (riddenByEntity != null) {
			riddenByEntity.mountEntity(this);
		}

		if (sleeping) {
			wakeUpPlayer(true, false, false);
		}
	}

	/**
	 * this function is called when a players inventory is sent to him,
	 * lastHealth is updated on any dimension transitions, then reset.
	 */
	public void setPlayerHealthUpdated() {
		lastHealth = -1.0E8F;
	}

	@Override
	public void addChatComponentMessage(final IChatComponent p_146105_1_) {
		playerNetServerHandler.sendPacket(new S02PacketChat(p_146105_1_));
	}

	/**
	 * Used for when item use count runs out, ie: eating completed
	 */
	@Override
	protected void onItemUseFinish() {
		playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte) 9));
		super.onItemUseFinish();
	}

	/**
	 * sets the itemInUse when the use item button is clicked. Args: itemstack,
	 * int maxItemUseDuration
	 */
	@Override
	public void setItemInUse(final ItemStack p_71008_1_, final int p_71008_2_) {
		super.setItemInUse(p_71008_1_, p_71008_2_);

		if (p_71008_1_ != null && p_71008_1_.getItem() != null
				&& p_71008_1_.getItem().getItemUseAction(p_71008_1_) == EnumAction.EAT) {
			getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
		}
	}

	/**
	 * Copies the values from the given player into this player if boolean par2
	 * is true. Always clones Ender Chest Inventory.
	 */
	@Override
	public void clonePlayer(final EntityPlayer p_71049_1_, final boolean p_71049_2_) {
		super.clonePlayer(p_71049_1_, p_71049_2_);
		lastExperience = -1;
		lastHealth = -1.0F;
		lastFoodLevel = -1;
		destroyedItemsNetCache.addAll(((EntityPlayerMP) p_71049_1_).destroyedItemsNetCache);
	}

	@Override
	protected void onNewPotionEffect(final PotionEffect p_70670_1_) {
		super.onNewPotionEffect(p_70670_1_);
		playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), p_70670_1_));
	}

	@Override
	protected void onChangedPotionEffect(final PotionEffect p_70695_1_, final boolean p_70695_2_) {
		super.onChangedPotionEffect(p_70695_1_, p_70695_2_);
		playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), p_70695_1_));
	}

	@Override
	protected void onFinishedPotionEffect(final PotionEffect p_70688_1_) {
		super.onFinishedPotionEffect(p_70688_1_);
		playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(getEntityId(), p_70688_1_));
	}

	/**
	 * Sets the position of the entity and updates the 'last' variables
	 */
	@Override
	public void setPositionAndUpdate(final double p_70634_1_, final double p_70634_3_, final double p_70634_5_) {
		playerNetServerHandler.setPlayerLocation(p_70634_1_, p_70634_3_, p_70634_5_, rotationYaw, rotationPitch);
	}

	/**
	 * Called when the player performs a critical hit on the Entity. Args:
	 * entity that was hit critically
	 */
	@Override
	public void onCriticalHit(final Entity p_71009_1_) {
		getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71009_1_, 4));
	}

	@Override
	public void onEnchantmentCritical(final Entity p_71047_1_) {
		getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71047_1_, 5));
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	@Override
	public void sendPlayerAbilities() {
		if (playerNetServerHandler != null) {
			playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(capabilities));
			func_175135_B();
		}
	}

	public WorldServer getServerForPlayer() {
		return (WorldServer) worldObj;
	}

	/**
	 * Sets the player's game mode and sends it to them.
	 */
	@Override
	public void setGameType(final WorldSettings.GameType gameType) {
		theItemInWorldManager.setGameType(gameType);
		playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, gameType.getID()));

		if (gameType == WorldSettings.GameType.SPECTATOR) {
			mountEntity((Entity) null);
		} else {
			func_175399_e(this);
		}

		sendPlayerAbilities();
		func_175136_bO();
	}

	@Override
	public boolean isSpectatorMode() {
		return theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR;
	}

	/**
	 * Notifies this sender of some sort of information. This is for messages
	 * intended to display to the user. Used for typical output (like "you asked
	 * for whether or not this game rule is set, so here's your answer" ),
	 * warnings (like "I fetched this block for you by ID, but I'd like you to
	 * know that every time you do this, I die a little inside "), and errors
	 * (like "it's not called iron_pixacke, silly").
	 */
	@Override
	public void addChatMessage(final IChatComponent message) {
		playerNetServerHandler.sendPacket(new S02PacketChat(message));
	}

	/**
	 * Returns true if the command sender is allowed to use the given command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
		if ("seed".equals(command) && !mcServer.isDedicatedServer()) {
			return true;
		} else if (!"tell".equals(command) && !"help".equals(command) && !"me".equals(command)
				&& !"trigger".equals(command)) {
			if (mcServer.getConfigurationManager().canSendCommands(getGameProfile())) {
				final UserListOpsEntry var3 = (UserListOpsEntry) mcServer.getConfigurationManager().getOppedPlayers()
						.getEntry(getGameProfile());
				return var3 != null ? var3.func_152644_a() >= permissionLevel
						: mcServer.getOpPermissionLevel() >= permissionLevel;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * Gets the player's IP address. Used in /banip.
	 */
	public String getPlayerIP() {
		String var1 = playerNetServerHandler.netManager.getRemoteAddress().toString();
		var1 = var1.substring(var1.indexOf("/") + 1);
		var1 = var1.substring(0, var1.indexOf(":"));
		return var1;
	}

	public void handleClientSettings(final C15PacketClientSettings p_147100_1_) {
		translator = p_147100_1_.getLang();
		chatVisibility = p_147100_1_.getChatVisibility();
		chatColours = p_147100_1_.isColorsEnabled();
		getDataWatcher().updateObject(10, (byte) p_147100_1_.getView());
	}

	public EntityPlayer.EnumChatVisibility getChatVisibility() {
		return chatVisibility;
	}

	public void func_175397_a(final String p_175397_1_, final String p_175397_2_) {
		playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(p_175397_1_, p_175397_2_));
	}

	@Override
	public BlockPos getPosition() {
		return new BlockPos(posX, posY + 0.5D, posZ);
	}

	public void markPlayerActive() {
		playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
	}

	/**
	 * Gets the stats file for reading achievements
	 */
	public StatisticsFile getStatFile() {
		return statsFile;
	}

	public void func_152339_d(final Entity p_152339_1_) {
		if (p_152339_1_ instanceof EntityPlayer) {
			playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
		} else {
			destroyedItemsNetCache.add(p_152339_1_.getEntityId());
		}
	}

	@Override
	protected void func_175135_B() {
		if (isSpectatorMode()) {
			func_175133_bi();
			setInvisible(true);
		} else {
			super.func_175135_B();
		}

		getServerForPlayer().getEntityTracker().func_180245_a(this);
	}

	public Entity func_175398_C() {
		return field_175401_bS == null ? this : field_175401_bS;
	}

	public void func_175399_e(final Entity p_175399_1_) {
		final Entity var2 = func_175398_C();
		field_175401_bS = p_175399_1_ == null ? this : p_175399_1_;

		if (var2 != field_175401_bS) {
			playerNetServerHandler.sendPacket(new S43PacketCamera(field_175401_bS));
			setPositionAndUpdate(field_175401_bS.posX, field_175401_bS.posY, field_175401_bS.posZ);
		}
	}

	/**
	 * Attacks for the player the targeted entity with the currently equipped
	 * item. The equipped item has hitEntity called on it. Args: targetEntity
	 */
	@Override
	public void attackTargetEntityWithCurrentItem(final Entity targetEntity) {
		if (theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
			func_175399_e(targetEntity);
		} else {
			super.attackTargetEntityWithCurrentItem(targetEntity);
		}
	}

	public long getLastActiveTime() {
		return playerLastActiveTime;
	}

	public IChatComponent func_175396_E() {
		return null;
	}
}
