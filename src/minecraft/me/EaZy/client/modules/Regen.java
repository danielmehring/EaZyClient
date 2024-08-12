package me.EaZy.client.modules;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;

public class Regen extends Module {

	public static Regen mod;

	public static final int EaZy = 156;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Regen() {
		super("Regen", 0, "", Category.PLAYER, "Regenerate health faster.");
		Client.setmgr.rSetting(new Setting("Packets", this, 100, 50, 250, true));
		mod = this;
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "SchnellereErholung";
		} else {
			return super.getRenderName();
		}
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
		if (!Minecraft.thePlayer.capabilities.isCreativeMode && Minecraft.thePlayer.onGround
				&& Minecraft.thePlayer.getFoodStats().getFoodLevel() > 17 && Minecraft.thePlayer.getHealth() < 20.0f
				&& Minecraft.thePlayer.getHealth() != 0.0f) {
			for (int i = 0; i < (int) Client.setmgr.getSettingByName(this, "Packets").getValFloat(); i++) {
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true, false, true));
			}

		}
		super.onUpdate();
	}

}
