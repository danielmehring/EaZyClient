package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class NoKnockBack extends Module {

	public static NoKnockBack mod;

	public static final int EaZy = 147;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public NoKnockBack() {
		super("NoKnockBack", 37, "kb", Category.COMBAT, "Avoid knockback.");
		Client.setmgr.rSetting(new Setting("H", this, 0.0f, 0.0f, 100.0f, true));
		Client.setmgr.rSetting(new Setting("V", this, 0.0f, 0.0f, 100.0f, true));
		mod = this;
	}

	private String suffix;

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "#Wir gegen Rückstoß [H:" + (int) Client.setmgr.getSettingByName(this, "H").getValFloat()
						+ "%, V:" + (int) Client.setmgr.getSettingByName(this, "V").getValFloat() + "%]";
			} else {
				suffix = "#Wir gegen Rückstoß";
			}
		} else {

			if (Configs.suffix) {
				suffix = "NoKnockBack [H:" + (int) Client.setmgr.getSettingByName(this, "H").getValFloat() + "%, V:"
						+ (int) Client.setmgr.getSettingByName(this, "V").getValFloat() + "%]";
			} else {
				suffix = "NoKnockBack";
			}

		}

		return suffix;
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
		if (mc.thePlayer.hurtTime == 8 && YesCheat.enabled && YesCheat.mode == Mode.Gomme) {
			// double asd = 0.2;
			// double yaw = Math.toRadians(Minecraft.thePlayer.rotationYawHead);
			// final double dX = -Math.sin(yaw) * asd;
			// final double dZ = Math.cos(yaw) * asd;
			mc.thePlayer.motionX *= 0.2;
			mc.thePlayer.motionZ *= 0.2;
		}
		super.onUpdate();
	}

	public EventTarget onReceivePacket(final EventReceivePacket event) {
		if (Minecraft.thePlayer.posY >= 0 && !(YesCheat.enabled && YesCheat.mode == Mode.Gomme)) {
			if (event.getPacket() instanceof S12PacketEntityVelocity && Minecraft.theWorld
					.getEntityByID(((S12PacketEntityVelocity) event.getPacket()).entityID()).equals(Minecraft.thePlayer)
					|| event.getPacket() instanceof S27PacketExplosion) {
				if (event.getPacket() instanceof S12PacketEntityVelocity) {
					if (Client.setmgr.getSettingByName(this, "H").getValFloat() > 0
							|| Client.setmgr.getSettingByName(this, "V").getValFloat() > 0) {
						((S12PacketEntityVelocity) event.getPacket()).x *= Client.setmgr.getSettingByName(this, "H")
								.getValFloat() / 100.0;
						((S12PacketEntityVelocity) event.getPacket()).z *= Client.setmgr.getSettingByName(this, "H")
								.getValFloat() / 100.0;
						((S12PacketEntityVelocity) event.getPacket()).y *= Client.setmgr.getSettingByName(this, "V")
								.getValFloat() / 100.0;
					} else {
						event.setCancelled(true);
					}
				}
				if (event.getPacket() instanceof S27PacketExplosion) {
					if (Client.setmgr.getSettingByName(this, "H").getValFloat() > 0
							|| Client.setmgr.getSettingByName(this, "V").getValFloat() > 0) {
						((S27PacketExplosion) event.getPacket()).x *= Client.setmgr.getSettingByName(this, "H")
								.getValFloat() / 100.0;
						((S27PacketExplosion) event.getPacket()).y *= Client.setmgr.getSettingByName(this, "V")
								.getValFloat() / 100.0;
						((S27PacketExplosion) event.getPacket()).z *= Client.setmgr.getSettingByName(this, "V")
								.getValFloat() / 100.0;
					} else {
						event.setCancelled(true);
					}
				}
			}
		}
		return null;
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
