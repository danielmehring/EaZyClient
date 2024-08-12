package me.EaZy.client.modules.glides;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.AAATestModule;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class HypixelFly extends Module {

	public static HypixelFly mod;

	public static final int EaZy = 2069;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public HypixelFly() {
		super("HypixelFly", 0, "", Category.GLIDE);
		mod = this;
	}

	public double startY = 0;
	public int stage = 0;

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

		mc.thePlayer.motionY = 0;
		mc.thePlayer.posY = startY;
		mc.thePlayer.jumpMovementFactor = 0.293f;
		mc.thePlayer.motionX = 0;
		mc.thePlayer.motionZ = 0;
		super.onUpdate();
	}

	@Override
	public void onRender() {
		super.onRender();
	}

	public EventTarget onSendPacket(final EventSendPacket event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			final C03PacketPlayer player = (C03PacketPlayer) event.getPacket();
			player.onGround = true;
			if (!event.getPacket().getClass().equals(C03PacketPlayer.class)
					&& (event.getPacket() instanceof C04PacketPlayerPosition
							|| event.getPacket() instanceof C05PacketPlayerLook
							|| event.getPacket() instanceof C06PacketPlayerPosLook)) {
				player.y = startY + (stage % 3 == 0 ? 0.00000001 : 0);
				stage++;
			}
		}
		return null;
	}

	@Override
	public void onEnable() {
		if (mc.thePlayer.onGround) {
			startY = mc.thePlayer.posY;
			EventManager.register(this);
			super.onEnable();
		} else {
			msg("§cPlease stand on the ground.");
			Client.disable(mod.getName());
		}
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		startY = 0;
	}

}
