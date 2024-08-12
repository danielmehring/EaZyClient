package me.EaZy.client.modules;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.utils.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.Timer;

public class AAATestModule extends Module {

	public static AAATestModule mod;

	public static final int EaZy = 89;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public AAATestModule() {
		super("AAATestModule", 0, "", Category.HIDDEN, "Description not set.");
		mod = this;
	}

	@Override
	public String getRenderName() {
		return "AAATestModule";
	}

	public static int i1 = 0;
	public static int i2 = 0;
	public static double d1 = 0;
	public static double d2 = 0;
	public static boolean b1 = false;
	public static boolean b2 = false;
	Location l = new Location(0, 0, 0);

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


		// for (Object o : mc.theWorld.loadedEntityList) {
		// if (!(o instanceof EntityPlayer)) {
		// continue;
		// }
		// EntityPlayer e = (EntityPlayer) o;
		// if (!e.equals(mc.thePlayer)) {
		// System.out.println(e.getClass());
		// }
		// }

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
		d1 = -0.00;
		// String s = "";
		// for (int i = 0; i < 128; i++) {
		// s += (char) (Math.random() * 65535) + "\u0000";
		// }
		// yee = new PacketBuffer(Unpooled.buffer().writeBytes(s.getBytes()));
		i1 = 0;
		EventManager.register(this);
		msg("§2Test-Module enaled!");
		super.onEnable();
	}

	public EventTarget onSendPacket(final EventSendPacket e) {
		return null;
	}

	public EventTarget onReceivePacket(final EventReceivePacket e) {
		return null;
	}

	@Override
	public void onDisable() {
		b1 = false;
		EventManager.unregister(this);
		msg("§4Test-Module disabled!");
		Minecraft.thePlayer.speedInAir = 0.02f;
		Timer.timerSpeed = 1.0f;
		try {
			Minecraft.gameSettings.keyBindForward.pressed = Keyboard
					.isKeyDown(Minecraft.gameSettings.keyBindForward.getKeyCode());
			Minecraft.gameSettings.keyBindSprint.pressed = Keyboard
					.isKeyDown(Minecraft.gameSettings.keyBindSprint.getKeyCode());
		} catch (final Exception localException) {}
		super.onDisable();
	}
}
