package me.EaZy.client.modules;

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
import me.EaZy.client.Module;
import me.EaZy.client.events.EventPostUpdate;
import me.EaZy.client.events.EventPreUpdate;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.EntityUtils;
import me.EaZy.client.utils.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;

public class SmoothAim extends Module {

	public static SmoothAim mod;

	public static final int EaZy = 2052;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public SmoothAim() {
		super("SmoothAim", 0, "", Category.COMBAT);
		Client.setmgr.rSetting(new Setting("Range", this, 4.0f, 0, 6, false));
		Client.setmgr.rSetting(new Setting("MaxYaw", this, 360, 0, 360, true));
		Client.setmgr.rSetting(new Setting("MaxPitch", this, 360, 0, 360, true));
		Client.setmgr.rSetting(new Setting("Speed", this, 10, 1, 50, false));
		Client.setmgr.rSetting(new Setting("Random", this, false));
		Client.setmgr.rSetting(new Setting("IDCheck", this, true));
		Client.setmgr.rSetting(new Setting("Invisibles", this, false));
		Client.setmgr.rSetting(new Setting("HurtCheck", this, false));
		Client.setmgr.rSetting(new Setting("TabCheck", this, false));
		Client.setmgr.rSetting(new Setting("SpeedCheck", this, false));
		Client.setmgr.rSetting(new Setting("OnlyIngame", this, false));
		Client.setmgr.rSetting(new Setting("WallDamage", this, false));
		Client.setmgr.rSetting(new Setting("PremiCheck", this, false));
		Client.setmgr.rSetting(new Setting("HyPixel", this, false));
		Client.setmgr.rSetting(new Setting("Mineplex", this, false));
		Client.setmgr.rSetting(new Setting("WallCheck", this, true));
		Client.setmgr.rSetting(new Setting("ImpossibleName", this, true));
		mod = this;
	}

	public static EntityPlayer target = null;

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
		super.onUpdate();
	}

	@Override
	public void onRender() {
		if (!isToggled()) {
			return;
		}
		super.onRender();
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();

	}

	public EntityPlayer getClosestEntity() {
		try {
			EntityPlayer closestEntity = null;
			for (final Object o : Minecraft.theWorld.playerEntities) {
				if (!(o instanceof EntityPlayer)) {
					continue;
				}
				final EntityPlayer en = (EntityPlayer) o;
				if (isValid(en)) {
					if (closestEntity == null) {
						closestEntity = en;
					} else if (closestEntity.getDistanceToEntity(Minecraft.thePlayer) > en
							.getDistanceToEntity(Minecraft.thePlayer)) {
						closestEntity = en;
					}
				}
			}
			return closestEntity;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
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
		if (Minecraft.thePlayer.getDistanceToEntity(ep) > Client.setmgr.getSettingByName(this, "Range").getValFloat()) {
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
				Client.setmgr.getSettingByName(this, "MaxYaw").getValFloat())
				&& EntityUtils.isinAngle(Minecraft.thePlayer.rotationPitch, EntityUtils.getRotationsNeeded(ep)[1],
						Client.setmgr.getSettingByName(this, "MaxPitch").getValFloat());
	}

	public EventTarget onPreUpdate(final EventPreUpdate event) {
		EntityPlayer ep = getClosestEntity();
		if (ep == null) {
			target = null;
		} else {
			target = ep;
		}
		return null;
	}

	public EventTarget onPostUpdate(final EventPostUpdate event) {
		if (target != null) {
			rotate(target);
		}
		return null;
	}

	public void rotate(EntityPlayer entity) {
		final float[] rotations = EntityUtils.getRotationsNeeded(target);
		if (rotations != null) {
			float speed = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
			float yaw = rotations[0];
			float pitch = rotations[1];
			if (Client.setmgr.getSettingByName(this, "Random").getValBoolean()) {
				yaw = (float) (rotations[0] + (Math.random() > 0.5 ? Math.random() : -Math.random()) / 2.0);
				pitch = (float) (rotations[1] + (Math.random() > 0.5 ? Math.random() : -Math.random()) / 2.0);
			}
			float factor = Math.max(1, Math.abs(yaw - Minecraft.thePlayer.rotationYaw) / 100.0f);
			float yawChange = (float) (speed - Math.random()) * factor;
			float pitchChange = (float) (speed - Math.random());
			Minecraft.thePlayer.rotationYaw = EntityUtils.limitAngleChange(Minecraft.thePlayer.rotationYaw, yaw,
					yawChange);
			Minecraft.thePlayer.rotationPitch = EntityUtils.limitAngleChange(Minecraft.thePlayer.rotationPitch, pitch,
					pitchChange);
		}
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
