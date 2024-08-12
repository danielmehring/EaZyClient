/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * com.mojang.authlib.GameProfile org.lwjgl.input.Keyboard
 */
package me.EaZy.client.modules;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class Blink extends Module {

	public static Blink mod;

	public static final int EaZy = 98;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private String suffix;

	private static final ArrayList<Packet> outgoingPackets = new ArrayList();
	private static final ArrayList<S08PacketPlayerPosLook> incomingPackets = new ArrayList();
	private EntityOtherPlayerMP fakePlayer = null;
	private double oldX;
	private double oldY;
	private double oldZ;
	private static long blinkTime;
	private static long lastTime;

	public Blink() {
		super("Blink", 48, "", Category.PLAYER, "Let's you teleport!");
		Client.setmgr.rSetting(new Setting("Rewi", this, true));
		mod = this;
	}

	@Override
	public String getRenderName() {
		if (Configs.suffix && outgoingPackets.isEmpty()) {
			suffix = "Blink [" + blinkTime + "ms, P: 0]";
		} else if (outgoingPackets.isEmpty()) {
			suffix = "Blink";
		}

		if (!outgoingPackets.isEmpty()) {
			if (Configs.suffix) {
				suffix = "Blink [" + blinkTime + "ms, P: " + Integer.toString(outgoingPackets.size()) + "]";
			} else {
				suffix = "Blink";
			}
			return suffix;
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
		if ((Minecraft.currentScreen instanceof GuiGameOver || Minecraft.currentScreen instanceof GuiDownloadTerrain)
				&& isToggled()) {
			Client.toggle(getName());
		}
		super.onUpdate();
	}

	public EventTarget onRecivePacket(final EventReceivePacket e) {
		if (e.getPacket() instanceof S08PacketPlayerPosLook) {
			incomingPackets.add((S08PacketPlayerPosLook) e.getPacket());
			e.setCancelled(true);
		}
		return null;
	}

	public EventTarget onSendPacket(final EventSendPacket e) {
		if (e.getPacket() instanceof C0BPacketEntityAction) {
			outgoingPackets.add(e.getPacket());
			e.setCancelled(true);
		}
		return null;
	}

	@Override
	public void onEnable() {
		lastTime = System.currentTimeMillis();
		oldX = Minecraft.thePlayer.posX;
		oldY = Minecraft.thePlayer.posY;
		oldZ = Minecraft.thePlayer.posZ;
		fakePlayer = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.thePlayer.getGameProfile());
		fakePlayer.clonePlayer(Minecraft.thePlayer, true);
		fakePlayer.copyLocationAndAnglesFrom(Minecraft.thePlayer);
		fakePlayer.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
		Minecraft.theWorld.addEntityToWorld(-69, fakePlayer);
		EventManager.register(this);
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		if (Keyboard.isKeyDown(157)) {
			cancel();
			Minecraft.theWorld.removeEntityFromWorld(-69);
			fakePlayer = null;
			blinkTime = 0;
		} else {
			outgoingPackets.forEach((packet) -> {
				Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
			});
			outgoingPackets.clear();
			Minecraft.theWorld.removeEntityFromWorld(-69);
			fakePlayer = null;
			blinkTime = 0;
		}
	}

	public static void addToBlinkQueue(final Packet packet) {
		if (Minecraft.thePlayer.posX != Minecraft.thePlayer.prevPosX
				|| Minecraft.thePlayer.posZ != Minecraft.thePlayer.prevPosZ
				|| Minecraft.thePlayer.posY != Minecraft.thePlayer.prevPosY) {
			blinkTime += System.currentTimeMillis() - lastTime;
			outgoingPackets.add(packet);
		}
		lastTime = System.currentTimeMillis();
	}

	private void cancel() {
		outgoingPackets.clear();
		Minecraft.thePlayer.setPositionAndRotation(oldX, oldY, oldZ, fakePlayer.rotationYaw, fakePlayer.rotationPitch);
		incomingPackets.forEach((packet) -> {
			Minecraft.thePlayer.sendQueue.handlePlayerPosLook(packet);
		});
		outgoingPackets.clear();
	}
}
