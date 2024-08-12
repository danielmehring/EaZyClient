package me.EaZy.client.modules;

import java.util.Arrays;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.NoFlyKick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class Freecam extends Module {

	public static Freecam mod;

	public static final int EaZy = 2050;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Freecam() {
		super("Freecam", 0, "", Category.MOVEMENT);
		Client.setmgr.rSetting(new Setting("Speed", this, 0.3f, 0.0f, 10.0f, false));
		mod = this;
	}

	private EntityOtherPlayerMP fakePlayer = null;

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
		if (!Minecraft.thePlayer.isInWater()) {
			Minecraft.thePlayer.onGround = false;
			Minecraft.thePlayer.capabilities.isFlying = false;
			Minecraft.thePlayer.motionX = 0.0;
			Minecraft.thePlayer.motionY = 0.0;
			Minecraft.thePlayer.motionZ = 0.0;
			Minecraft.thePlayer.jumpMovementFactor = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
			if (Minecraft.gameSettings.keyBindSneak.pressed) {
				Minecraft.thePlayer.motionY -= Client.setmgr.getSettingByName(this, "Speed").getValFloat();
			}
			if (Minecraft.gameSettings.keyBindJump.pressed) {
				Minecraft.thePlayer.motionY += Client.setmgr.getSettingByName(this, "Speed").getValFloat();
			}
		} else {
			mc.thePlayer.capabilities.isFlying = true;
		}
		mc.thePlayer.noClip = true;
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
		fakePlayer = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.thePlayer.getGameProfile());
		fakePlayer.clonePlayer(Minecraft.thePlayer, true);
		fakePlayer.copyLocationAndAnglesFrom(Minecraft.thePlayer);
		fakePlayer.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
		Minecraft.theWorld.addEntityToWorld(-42, fakePlayer);
		EventManager.register(this);
		super.onEnable();

	}

	public EventTarget onSendPacket(final EventSendPacket e) {
		if (e.getPacket() instanceof C03PacketPlayer) {
			C03PacketPlayer p = (C03PacketPlayer) e.getPacket();
			if (p.getHasPosition() && p.getHasRotation()) { // C06
				p.x = fakePlayer.posX;
				p.y = fakePlayer.posY;
				p.z = fakePlayer.posZ;
				p.yaw = fakePlayer.rotationYawHead;
				p.pitch = fakePlayer.rotationPitch;
				p.onGround = fakePlayer.onGround;
			} else if (p.getHasPosition() && !p.getHasRotation()) { // C04
				p.x = fakePlayer.posX;
				p.y = fakePlayer.posY;
				p.z = fakePlayer.posZ;
				p.onGround = fakePlayer.onGround;
			} else if (!p.getHasPosition() && p.getHasRotation()) { // C05
				p.yaw = fakePlayer.rotationYawHead;
				p.pitch = fakePlayer.rotationPitch;
				p.onGround = fakePlayer.onGround;
			} else { // C03
				p.onGround = fakePlayer.onGround;
			}
		} else if (e.getPacket() instanceof C0BPacketEntityAction
				|| e.getPacket() instanceof C13PacketPlayerAbilities) {
			e.setCancelled(true);
		}
		return null;
	}

	public EventTarget onReceivePacket(final EventReceivePacket e) {
		if (e.getPacket() instanceof S08PacketPlayerPosLook && mc.thePlayer.sendQueue.doneLoadingTerrain) {
			S08PacketPlayerPosLook p = (S08PacketPlayerPosLook) e.getPacket();
			if (!(p.x == 0 && p.y == 0 && p.z == 0 && p.getYaw() == 0 && p.getPitch() == 0)) {
				fakePlayer.posX = p.x;
				fakePlayer.posY = p.y;
				fakePlayer.posZ = p.z;
				fakePlayer.rotationYawHead = p.getYaw();
				fakePlayer.rotationYaw = p.getYaw();
				fakePlayer.rotationPitch = p.getPitch();
				msg("§2You were teleported! Freecam set your position to the teleport position!");
				e.setCancelled(true);
			}
		}
		return null;
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		Minecraft.thePlayer.capabilities.isFlying = false;
		Minecraft.thePlayer.motionX = 0.0;
		Minecraft.thePlayer.motionY = 0.0;
		Minecraft.thePlayer.motionZ = 0.0;
		mc.thePlayer.noClip = false;
		Minecraft.theWorld.removeEntityFromWorld(-42);
		mc.thePlayer.setPositionAndRotation(fakePlayer.posX, fakePlayer.posY, fakePlayer.posZ,
				fakePlayer.rotationYawHead, fakePlayer.rotationPitch);
		fakePlayer = null;
		Minecraft.thePlayer.speedInAir = 0.02f;
		super.onDisable();
	}
}
