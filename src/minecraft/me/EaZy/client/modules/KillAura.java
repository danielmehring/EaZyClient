package me.EaZy.client.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventPostUpdate;
import me.EaZy.client.events.EventPreUpdate;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.EntityUtils;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings;
import optifine.Reflector;

public class KillAura extends Module {

	public static KillAura mod;

	public static final int EaZy = 132;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static int delay = 0;

	public static Entity target = null;
	public static Entity targetForRender = null;

	private String suffix;

	public KillAura() {
		super(new String(
				new byte[] { 0b1001011, 0b1101001, 0b1101100, 0b1101100, 0b1000001, 0b1110101, 0b1110010, 0b1100001 }),
				19, new String(new byte[] { 0b1100001, 0b1110101, 0b1110010, 0b1100001 }), Category.COMBAT,
				new String(new byte[] { 0b1000001, 0b1110100, 0b1110100, 0b1100001, 0b1100011, 0b1101011, 0b1110011,
						0b100000, 0b1100101, 0b1101110, 0b1110100, 0b1101001, 0b1110100, 0b1101001, 0b1100101,
						0b1110011, 0b100000, 0b1100001, 0b1110010, 0b1101111, 0b1110101, 0b1101110, 0b1100100, 0b100000,
						0b1111001, 0b1101111, 0b1110101, 0b101110 }));
		Client.setmgr.rSetting(new Setting("Players", this, true));
		Client.setmgr.rSetting(new Setting("Mobs", this, false));
		Client.setmgr.rSetting(new Setting("Delay", this, 1, 1, 10, true));
		Client.setmgr.rSetting(new Setting("Range", this, 4.5f, 0, 6, false));
		Client.setmgr.rSetting(new Setting("Yaw", this, 360, 0, 360, true));
		Client.setmgr.rSetting(new Setting("Pitch", this, 360, 0, 360, true));
		final ArrayList<String> targetMode = new ArrayList<>();
		targetMode.add("Nearest");
		targetMode.add("LowLife");
		targetMode.add("LowEquip");
		Client.setmgr.rSetting(new Setting("Crack", this, true));
		Client.setmgr.rSetting(new Setting("CrackSize", this, 1, 1, 50, true));
		Client.setmgr.rSetting(new Setting("Target-Mode", this, "Nearest", targetMode));
		Client.setmgr.rSetting(new Setting("Mark Target", this, false));
		Client.setmgr.rSetting(new Setting("IDCheck", this, true));
		Client.setmgr.rSetting(new Setting("Invisibles", this, false));
		Client.setmgr.rSetting(new Setting("HurtCheck", this, false));
		Client.setmgr.rSetting(new Setting("TabCheck", this, false));
		Client.setmgr.rSetting(new Setting("SpeedCheck", this, false));
		Client.setmgr.rSetting(new Setting("TPAura", this, false));
		Client.setmgr.rSetting(new Setting("OnlyIngame", this, false));
		Client.setmgr.rSetting(new Setting("WallDamage", this, false));
		Client.setmgr.rSetting(new Setting("PremiCheck", this, false));
		Client.setmgr.rSetting(new Setting("RayTrace", this, true));
		Client.setmgr.rSetting(new Setting("RandomCPS", this, false));
		Client.setmgr.rSetting(new Setting("HyPixel", this, false));
		Client.setmgr.rSetting(new Setting("Mineplex", this, false));
		Client.setmgr.rSetting(new Setting("WallCheck", this, true));
		Client.setmgr.rSetting(new Setting("ImpossibleName", this, true));
		mod = this;

	}

	// public ArrayList<String> bots = new ArrayList<>();
	// public boolean checkingGomme = false;
	public static boolean shouldGommeCheck = false;
	// public long lastiabhjsfdiasf = 0;

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "ToetendeAusstrahlung [" + Client.setmgr.getSettingByName(this, "Range").getValFloat() + "]";

			} else {
				suffix = "ToetendeAusstrahlung";
			}
		} else {
			if (Configs.suffix) {
				suffix = new String(new byte[] { 117, 107, 117, 115, 75, 104 }).substring(4, 5)
						+ new String(new byte[] { 107, 105, 110, 98, 115, 104 }).substring(1, 2)
						+ new String(new byte[] { 98, 101, 108, 111, 106, 105 }).substring(2, 3)
						+ new String(new byte[] { 101, 113, 110, 108, 105, 118 }).substring(3, 4)
						+ new String(new byte[] { 121, 65, 98, 114, 107, 107 }).substring(1, 2)
						+ new String(new byte[] { 117, 119, 110, 105, 119, 118 }).substring(0, 1)
						+ new String(new byte[] { 101, 99, 110, 114, 114, 101 }).substring(3, 4)
						+ new String(new byte[] { 103, 118, 97, 119, 120, 105 }).substring(2, 3)
						+ new String(new byte[] { 32, 110, 103, 101, 112, 120 }).substring(0, 1)
						+ new String(new byte[] { 110, 102, 97, 91, 115, 116 }).substring(3, 4)
						+ Client.setmgr.getSettingByName(this, "Range").getValFloat()
						+ new String(new byte[] { 0b1011101 });

			} else {
				suffix = new String(new byte[] { 97, 98, 106, 75, 103, 97 }).substring(3, 4)
						+ new String(new byte[] { 111, 105, 121, 120, 99, 110 }).substring(1, 2)
						+ new String(new byte[] { 99, 121, 108, 110, 120, 119 }).substring(2, 3)
						+ new String(new byte[] { 121, 106, 108, 115, 108, 114 }).substring(2, 3)
						+ new String(new byte[] { 116, 65, 102, 111, 107, 116 }).substring(1, 2)
						+ new String(new byte[] { 97, 114, 108, 117, 112, 104 }).substring(3, 4)
						+ new String(new byte[] { 97, 97, 114, 103, 101, 111 }).substring(2, 3)
						+ new String(new byte[] { 104, 98, 97, 97, 104, 97 }).substring(3, 4);
			}

		}
		return suffix;
	}

	@Override
	public void onDisable() {
		EntityUtils.setLookChanged(false);
		EventManager.unregister(this);
		super.onDisable();
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		// try {
		// if
		// (Minecraft.thePlayer.sendQueue.netManager.channel.remoteAddress().toString().toLowerCase()
		// .contains("gomme")
		// ||
		// Minecraft.thePlayer.sendQueue.netManager.channel.remoteAddress().toString().toLowerCase()
		// .contains("gammel")) {
		// performGommeCheck();
		// }
		// } catch (Exception e) {}
		super.onEnable();
	}

	public EventTarget onPreUpdate(final EventPreUpdate event) {
		if (Client.setmgr.getSettingByName(this, "Players").getValBoolean()) {
			Entity ep = getTargetEntity();
			if (Client.setmgr.getSettingByName(this, "RayTrace").getValBoolean()) {
				ep = rayTraceEntity(ep);
			}

			if (Client.setmgr.getSettingByName(this, "Crack").getValBoolean()) {
				if (isValidEntity((EntityLivingBase) ep)) {
					for (int i = 0; i < Client.setmgr.getSettingByName(this, "CrackSize").getValFloat(); i++) {
						Minecraft.thePlayer.onCriticalHit(ep);
					}
				}
			}
			if (ep == null) {
				target = null;
				EntityUtils.setLookChanged(false);
			} else {
				target = targetForRender = ep;
				if (YesCheat.enabled) {
					face((EntityLivingBase) target);
				}
			}
		}
		return null;
	}

	// public void performGommeCheck() {
	// shouldGommeCheck = false;
	// checkingGomme = true;
	// lastiabhjsfdiasf = System.currentTimeMillis();
	// bots.clear();
	// Minecraft.thePlayer.sendQueue.netManager.sendPacket(new
	// C14PacketTabComplete("/stats "));
	// }

	// public EventTarget onReceivePacketForGommeCheck(final EventReceivePacket
	// event) {
	// if (event.getPacket() instanceof S3APacketTabComplete && checkingGomme) {
	// final S3APacketTabComplete packet = (S3APacketTabComplete)
	// event.getPacket();
	// final ArrayList<String> players = new ArrayList<>();
	// for (final String asd : packet.func_149630_c()) {
	// players.add(asd);
	// }
	//
	// if (players.isEmpty()) {
	// checkingGomme = false;
	// event.setCancelled(true);
	// return null;
	// }
	//
	// final Ordering field_175252_a = Ordering.from(new
	// PlayerComparator(null));
	// final List var5 =
	// field_175252_a.sortedCopy(Minecraft.thePlayer.sendQueue.getPlayerInfo());
	// final Iterator var8 = var5.iterator();
	//
	// while (var8.hasNext()) {
	// final String s = ((NetworkPlayerInfo) var8.next()).gameProfile.getName();
	// if (!players.contains(s)) {
	// bots.add(s);
	// }
	// }
	// checkingGomme = false;
	// lastiabhjsfdiasf = 0;
	// event.setCancelled(true);
	// }
	// return null;
	// }

	public EventTarget onPostUpdate(final EventPostUpdate event) {
		if (Client.setmgr.getSettingByName(this, "Players").getValBoolean()) {
			if (target != null) {
				if (Client.setmgr.getSettingByName(this, "RandomCPS").getValBoolean() && Math.random() > 0.75) {
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
					Minecraft.thePlayer.sendQueue
							.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
				}
				if (!(delay <= Client.setmgr.getSettingByName(this, "Delay").getValFloat())) {
					if (Client.setmgr.getSettingByName(this, "TPAura").getValBoolean()) {
						Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								target.posX, target.posY + 0.1, target.posZ, true));
					}
					attack(target);
					if (Client.setmgr.getSettingByName(this, "TPAura").getValBoolean()) {
						Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
					}
				}
			}
		}
		return null;
	}

	@Override
	public void onUpdate() {
		if (!isToggled()) {
			if (togglecmd) {
				setToggled(true);
				onEnable();
			}
			return;
		}
		if (isToggled() && !togglecmd) {
			setToggled(false);
			onDisable();
			return;
		}

		// if (shouldGommeCheck) {
		// performGommeCheck();
		// }
		//
		// if (checkingGomme && System.currentTimeMillis() - lastiabhjsfdiasf >
		// 3000) {
		// lastiabhjsfdiasf = 0;
		// checkingGomme = false;
		// }

		++delay;

		if (Client.setmgr.getSettingByName(this, "Mobs").getValBoolean()) {
			Minecraft.theWorld.loadedEntityList.stream()
					.filter((o) -> !(!(o instanceof EntityLivingBase) || o instanceof EntityPlayer))
					.filter((o) -> (!(delay <= Client.setmgr.getSettingByName(this, "Delay").getValFloat())))
					.map((o) -> (EntityLivingBase) o).map((ep) -> {
						attackEntity((EntityLivingBase) ep);
						return ep;
					}).filter((ep) -> (Client.setmgr.getSettingByName(this, "Crack").getValBoolean()))
					.forEachOrdered((ep) -> {
						for (int i = 0; i < Client.setmgr.getSettingByName(this, "CrackSize").getValFloat(); i++) {
							if (isValidEntity((EntityLivingBase) ep)) {
								Minecraft.thePlayer.onCriticalHit((Entity) ep);
							}
						}
					});
		}
		super.onUpdate();

	}

	private void face(final EntityLivingBase ep) {
		EntityUtils.faceEntityPacket(ep, Client.setmgr.getSettingByName(this, "Yaw").getValFloat(),
				Client.setmgr.getSettingByName(this, "Pitch").getValFloat());
	}

	private void attackEntity(final EntityLivingBase ep) {
		if (isValidEntity(ep)) {
			targetForRender = (EntityLivingBase) ep;
			if (YesCheat.enabled) {
				face(ep);
			}
			final boolean wasBlocking = GameSettings.isKeyDown(Minecraft.gameSettings.keyBindUseItem)
					&& Minecraft.thePlayer.getCurrentEquippedItem() != null
					&& Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
			if (wasBlocking) {
				Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
			}
			Minecraft.thePlayer.swingItem();

			Minecraft.playerController.attackEntity(Minecraft.thePlayer, ep);
			if (Client.setmgr.getSettingByName(this, "RandomCPS").getValBoolean() && Math.random() > 0.75) {
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
				Minecraft.thePlayer.sendQueue
						.addToSendQueue(new C02PacketUseEntity(ep, C02PacketUseEntity.Action.ATTACK));
			}
			if (wasBlocking) {
				Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1),
						255, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
			}
			delay = 0;

		} else {
			targetForRender = null;
		}
	}

	private void attack(final Entity ep) {
		final boolean wasBlocking = GameSettings.isKeyDown(Minecraft.gameSettings.keyBindUseItem)
				&& Minecraft.thePlayer.getCurrentEquippedItem() != null
				&& Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
		if (wasBlocking) {
			Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
					C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
		}
		Minecraft.thePlayer.swingItem();
		Minecraft.playerController.attackEntity(Minecraft.thePlayer, ep);

		if (wasBlocking) {
			Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
					Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
		}
		delay = 0;
	}

	@Override
	public void onRender() {
		if (targetForRender == null || !Client.setmgr.getSettingByName(this, "Mark Target").getValBoolean()) {
			return;
		}
		Color c = Client.getColor(0l);
		RenderUtils.drawEntityESP(targetForRender.posX - mc.thePlayer.posX,
				targetForRender.posY + targetForRender.height - mc.thePlayer.posY,
				targetForRender.posZ - mc.thePlayer.posZ, (double) targetForRender.width, 0.05f, c, 0.25f, c, 1.0f,
				1.0f);
		System.out.println("asd");
		super.onRender();
	}

	private boolean isValidEntity(final EntityLivingBase ep) {
		if (Client.setmgr.getSettingByName(this, "OnlyIngame").getValBoolean() && Minecraft.currentScreen != null) {
			return false;
		}
		if (Minecraft.thePlayer.isDead) {
			return false;
		}
		if (ep == null) {
			return false;
		}
		if (ep.isPlayerSleeping() || ep.getName().contains(" ") || ep.getName().contains("#")) {
			return false;
		}
		if (ep instanceof EntityPlayerSP) {
			return false;
		}
		if (ep.isDead || ep.getHealth() <= 0) {
			return false;
		}
		if (ep.getHealth() == 0) {
			return false;
		}
		if (!ep.isEntityAlive()) {
			return false;
		}
		if (ep instanceof EntityArmorStand) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "TPAura").getValBoolean() ? false
				: Minecraft.thePlayer.getDistanceToEntity(ep) > Client.setmgr.getSettingByName(this, "Range")
						.getValFloat()) {
			return false;
		}
		if (ep.hurtTime > 0 && Client.setmgr.getSettingByName(this, "HurtCheck").getValBoolean()) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "IDCheck").getValBoolean()) {
			if (ep.getEntityId() - 1073641823 > 0) {
				return false;
			}
		}
		if (!Client.setmgr.getSettingByName(this, "Invisibles").getValBoolean()) {
			if (ep.isInvisible()) {
				return false;
			}
		}

		return EntityUtils.isinAngle(Minecraft.thePlayer.rotationYaw, EntityUtils.getRotationsNeeded(ep)[0],
				Client.setmgr.getSettingByName(this, "Yaw").getValFloat())
				&& EntityUtils.isinAngle(Minecraft.thePlayer.rotationPitch, EntityUtils.getRotationsNeeded(ep)[1],
						Client.setmgr.getSettingByName(this, "Pitch").getValFloat());
	}

	private static class PlayerComparator implements Comparator {

		private PlayerComparator() {}

		private int func_178952_a(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
			final ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
			final ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
			return ComparisonChain.start()
					.compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR,
							p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR)
					.compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "")
					.compare(p_178952_1_.func_178845_a().getName(), p_178952_2_.func_178845_a().getName()).result();
		}

		@Override
		public int compare(final Object p_compare_1_, final Object p_compare_2_) {
			return func_178952_a((NetworkPlayerInfo) p_compare_1_, (NetworkPlayerInfo) p_compare_2_);
		}

		private PlayerComparator(final Object p_i45528_1_) {
			this();
		}
	}

	private boolean isValid(final EntityPlayer ep) {
		// if (checkingGomme) {
		// return false;
		// }
		if (Minecraft.currentScreen != null && Minecraft.currentScreen instanceof GuiGameOver) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "OnlyIngame").getValBoolean()
				&& (Minecraft.currentScreen != null && !(Minecraft.currentScreen instanceof ClickGUI))) {
			return false;
		}
		if (ep == null) {
			return false;
		}
		if (ep instanceof EntityPlayerSP) {
			return false;
		}
		if (ep.isDead || ep.getHealth() <= 0) {
			return false;
		}
		if (!ep.isEntityAlive()) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "TPAura").getValBoolean() ? false
				: Minecraft.thePlayer.getDistanceToEntity(ep) > Client.setmgr.getSettingByName(this, "Range")
						.getValFloat()) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "WallCheck").getValBoolean()) {
			if (!ep.canEntityBeSeen(Minecraft.thePlayer)) {
				return false;
			}
		}
		if (ep.getName().contains("§") && Client.setmgr.getSettingByName(this, "ImpossibleName").getValBoolean()) {
			return false;
		}
		if (!Client.setmgr.getSettingByName(this, "Invisibles").getValBoolean()) {
			if (ep.isInvisible()) {
				return false;
			}
		}
		if (Client.setmgr.getSettingByName(this, "PremiCheck").getValBoolean()) {
			if (ep.getGameProfile().getProperties().size() == 0) {
				return false;
			}
		}
		if (Client.setmgr.getSettingByName(this, "Mineplex").getValBoolean()) {
			if (!((EntityOtherPlayerMP) ep).hasYawHeadChanged) {
				return false;
			}
		}
		// if (bots.contains(ep.getName())) {
		// return false;
		// }
		if (Client.setmgr.getSettingByName(this, "TabCheck").getValBoolean()) {
			final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
			final Ordering ordered = Ordering.from(new PlayerComparator(null));
			final List orderedList = ordered.sortedCopy(var4.getPlayerInfo());
			final Iterator var8 = orderedList.iterator();

			final ArrayList<String> names = new ArrayList<>();

			while (var8.hasNext()) {
				final NetworkPlayerInfo var9 = (NetworkPlayerInfo) var8.next();

				names.add(EnumChatFormatting.getTextWithoutFormattingCodes(var9.getPlayerNameForReal()));
			}

			if (!names.contains(
					EnumChatFormatting.getTextWithoutFormattingCodes(ep.getDisplayName().getFormattedText()))) {
				return false;
			}
		}
		// if (YesCheat.mode == Mode.Gomme && ep.getHealth() < 20)
		// return false;
		// if (YesCheat.mode == Mode.Gomme &&
		// !ep.getDisplayName().getFormattedText().startsWith("§")) {
		// return false;
		// }
		if (Client.setmgr.getSettingByName(this, "OnlyIngame").getValBoolean() && ep.getName().contains("§")
				&& ep.getName().length() == 11) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "SpeedCheck").getValBoolean() && ep.tooFastDown) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "WallDamage").getValBoolean() && ep.wallDmgd) {
			return false;
		}
		if (Friends.contains(ep)) {
			return false;
		}
		if (ep.hurtTime > 0 && Client.setmgr.getSettingByName(this, "HurtCheck").getValBoolean()) {
			return false;
		}
		if (Client.setmgr.getSettingByName(this, "IDCheck").getValBoolean()) {
			if (ep.getEntityId() - 1073641823 > 0) {
				return false;
			}
		}
		if (Client.setmgr.getSettingByName(this, "HyPixel").getValBoolean()) {
			if (ep.getGameProfile().getProperties().containsKey("textures")) {
				if (ep.getGameProfile().getProperties().get("textures").iterator().next().getValue().equals(
						"eyJ0aW1lc3RhbXAiOjE0ODAwMzA1MzY0MTMsInByb2ZpbGVJZCI6ImZmM2EzMGY5YjdiYTQyMjM4ZTc5NjU4ZThiNmE1MjI5IiwicHJvZmlsZU5hbWUiOiJDcnlwdGtlZXBlciIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNiMTgxNWI0MGZjOWVlMWFkMDc3YTMyNjNjMzlmYWE2YTNhODg3ZjFlMmFhMjM1YzJiZGUyZmI3ZDM2YSJ9LCJDQVBFIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWMzY2E3ZWUyYTQ5OGYxYjVkMjU4ZDVmYTkyN2U2M2U0MzMxNDNhZGQ1NTM4Y2Y2M2I2YTliNzhhZTczNSJ9fX0=")) {
					return false;
				}
			}
		}
		return EntityUtils.isinAngle(Minecraft.thePlayer.rotationYaw, EntityUtils.getRotationsNeeded(ep)[0],
				Client.setmgr.getSettingByName(this, "Yaw").getValFloat())
				&& EntityUtils.isinAngle(Minecraft.thePlayer.rotationPitch, EntityUtils.getRotationsNeeded(ep)[1],
						Client.setmgr.getSettingByName(this, "Pitch").getValFloat());
	}

	private Entity rayTraceEntity(final Entity ep) {
		if (ep == null) {
			return null;
		}
		Entity pointedEntity;
		final double reachDistFromPlyController = Client.setmgr.getSettingByName(this, "Range").getValFloat();
		final Vec3 var7 = Minecraft.thePlayer.func_174824_e(Minecraft.timer.renderPartialTicks);

		final float[] asd = EntityUtils.getRotations(ep);

		final float var3 = MathHelper.cos(-asd[0] * 0.017453292F - (float) Math.PI);
		final float var4 = MathHelper.sin(-asd[0] * 0.017453292F - (float) Math.PI);
		final float var5 = -MathHelper.cos(-asd[1] * 0.017453292F);
		final float var6 = MathHelper.sin(-asd[1] * 0.017453292F);
		final Vec3 var8 = new Vec3(var4 * var5, var6, var3 * var5);

		final Vec3 var9 = var7.addVector(var8.xCoord * reachDistFromPlyController,
				var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController);
		pointedEntity = null;
		final float var11 = 1.0F;
		final List var12 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(Minecraft.thePlayer,
				Minecraft.thePlayer
						.getEntityBoundingBox().addCoord(var8.xCoord * reachDistFromPlyController,
								var8.yCoord * reachDistFromPlyController, var8.zCoord * reachDistFromPlyController)
						.expand(var11, var11, var11));
		double var13 = reachDistFromPlyController;
		for (int var15 = 0; var15 < var12.size(); ++var15) {
			final Entity var16 = (Entity) var12.get(var15);

			// if (var16.isEntityEqual(ep)) {
			// continue;
			// }

			if (var16.canBeCollidedWith()) {
				final float var17 = var16.getCollisionBorderSize();
				final AxisAlignedBB var18 = var16.getEntityBoundingBox().expand(var17, var17, var17);
				final MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);

				if (var18.isVecInside(var7)) {
					if (0.0D < var13 || var13 == 0.0D) {
						pointedEntity = var16;
						var13 = 0.0D;
					}
				} else if (var19 != null) {
					final double var20 = var7.distanceTo(var19.hitVec);

					if (var20 < var13 || var13 == 0.0D) {
						boolean canRiderInteract = false;

						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							canRiderInteract = Reflector.callBoolean(var16, Reflector.ForgeEntity_canRiderInteract,
									new Object[0]);
						}

						if (var16 == Minecraft.thePlayer.ridingEntity && !canRiderInteract) {
							if (var13 == 0.0D) {
								pointedEntity = var16;
							}
						} else {
							pointedEntity = var16;
							var13 = var20;
						}
					}
				}
			}
		}

		if (pointedEntity != null && (var13 < reachDistFromPlyController)) {
			if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
				return pointedEntity;
			}
		}
		return null;
	}

	public EntityPlayer getTargetEntity() {
		try {
			EntityPlayer targetEntity = null;
			switch (Client.setmgr.getSettingByName(this, "Target-Mode").getValString()) {
			case "Nearest": {
				for (final Object o : Minecraft.theWorld.playerEntities) {
					if (o == null || !(o instanceof EntityPlayer)) {
						continue;
					}
					final EntityPlayer en = (EntityPlayer) o;
					if (isValid(en)) {
						if (targetEntity == null) {
							targetEntity = en;
						} else if (targetEntity.getDistanceToEntity(Minecraft.thePlayer) > en
								.getDistanceToEntity(Minecraft.thePlayer)) {
							targetEntity = en;
						}
					}
				}
				return targetEntity;
			}
			case "LowLife": {
				boolean allSameLife = true;
				for (final Object o : Minecraft.theWorld.playerEntities) {
					if (o == null || !(o instanceof EntityPlayer)) {
						continue;
					}
					final EntityPlayer en = (EntityPlayer) o;
					if (isValid(en)) {
						if (targetEntity == null) {
							targetEntity = en;
						} else if (targetEntity.getHealth() > en.getHealth()) {
							targetEntity = en;
							allSameLife = false;
						}
					}
				}
				if (allSameLife) {
					targetEntity = null;
					for (final Object o : Minecraft.theWorld.playerEntities) {
						if (o == null || !(o instanceof EntityPlayer)) {
							continue;
						}
						final EntityPlayer en = (EntityPlayer) o;
						if (isValid(en)) {
							if (targetEntity == null) {
								targetEntity = en;
							} else if (targetEntity.getDistanceToEntity(Minecraft.thePlayer) > en
									.getDistanceToEntity(Minecraft.thePlayer)) {
								targetEntity = en;
							}
						}
					}
				}
				return targetEntity;
			}
			case "LowEquip": {
				boolean allSameEquip = true;
				for (final Object o : Minecraft.theWorld.playerEntities) {
					if (o == null || !(o instanceof EntityPlayer)) {
						continue;
					}
					final EntityPlayer en = (EntityPlayer) o;
					if (isValid(en)) {
						if (targetEntity == null) {
							targetEntity = en;
						} else if (targetEntity.getTotalArmorValue() > en.getTotalArmorValue()) {
							targetEntity = en;
							allSameEquip = false;
						}
					}
				}
				if (allSameEquip) {
					targetEntity = null;
					for (final Object o : Minecraft.theWorld.playerEntities) {
						if (o == null || !(o instanceof EntityPlayer)) {
							continue;
						}
						final EntityPlayer en = (EntityPlayer) o;
						if (isValid(en)) {
							if (targetEntity == null) {
								targetEntity = en;
							} else if (targetEntity.getDistanceToEntity(Minecraft.thePlayer) > en
									.getDistanceToEntity(Minecraft.thePlayer)) {
								targetEntity = en;
							}
						}
					}
				}
				return targetEntity;
			}
			default: {
				System.out.println(
						"Shouldn't happen lol: " + Client.setmgr.getSettingByName(this, "Target-Mode").getValString());
			}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static float[] getRotationsNeeded(final Entity entity) {
		double diffY;
		if (entity == null) {
			return null;
		}
		final double diffX = entity.posX - Minecraft.thePlayer.posX;
		final double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
		if (entity instanceof EntityLivingBase && !entity.isInvisible()) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight()
					- (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0
					- (Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight());
		}
		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
		return new float[] {
				Minecraft.thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.thePlayer.rotationYaw),
				Minecraft.thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.thePlayer.rotationPitch) };
	}
}
