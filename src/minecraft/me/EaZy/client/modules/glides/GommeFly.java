package me.EaZy.client.modules.glides;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class GommeFly extends Module {

	public static GommeFly mod;

	public static final int EaZy = 2053;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public GommeFly() {
		super("GommeFly", 0, "", Category.GLIDE);
		Client.setmgr.rSetting(new Setting("Delay", this, 3, 2, 10, true));
		mod = this;
	}

	public static int setBackDelay = 0;
	public static boolean backwardsMoveFix = false;

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

		if (setBackDelay > 0) {
			setBackDelay--;
			return;
		}

		boolean change = mc.thePlayer.ticksExisted
				% (int) Client.setmgr.getSettingByName(this, "Delay").getValFloat() == 0;
		if (!backwardsMoveFix) {
			change = false;
			backwardsMoveFix = true;
			double yaw = Math.toRadians(Minecraft.thePlayer.rotationYawHead);
			if (!Double.isNaN(yaw)) {

				double val = 0.2973;
				double xChange = -Math.sin(yaw) * val;
				double zChange = Math.cos(yaw) * val;

				mc.thePlayer.motionX = xChange;
				mc.thePlayer.motionZ = zChange;

				if (mc.thePlayer.onGround)
					mc.thePlayer.motionY = 0.387;
			}
			return;
		}
		double yaw = Math
				.toRadians(change ? Minecraft.thePlayer.rotationYawHead + 180 : Minecraft.thePlayer.rotationYawHead);
		if (!Double.isNaN(yaw)) {

			double val = change ? 0.255 : 0.2973;
			double xChange = -Math.sin(yaw) * val;
			double zChange = Math.cos(yaw) * val;

			mc.thePlayer.motionX = xChange;
			mc.thePlayer.motionZ = zChange;

			if (mc.thePlayer.onGround)
				mc.thePlayer.motionY = 0.387;
		}

		super.onUpdate();
	}

	@Override
	public void onEnable() {
		backwardsMoveFix = true;
		EventManager.register(this);
		super.onEnable();

	}

	public EventTarget onSendPacket(final EventSendPacket e) {
		if (e.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance > 2.0f) {
			C03PacketPlayer p = (C03PacketPlayer) e.getPacket();
			p.onGround = true;
			e.setPacket(p);
		}
		return null;
	}

	Location l = new Location(0, 0, 0);

	public EventTarget onReceivePacket(final EventReceivePacket e) {
		if (e.getPacket() instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook p = (S08PacketPlayerPosLook) e.getPacket();
			l = new Location(p.x, p.y, p.z);

			double yaw = Math.toRadians(Minecraft.thePlayer.rotationYawHead);
			if (!Double.isNaN(yaw)) {
				boolean xPositive = -Math.sin(yaw) > 0;
				boolean zPositive = Math.cos(yaw) > 0;
				boolean _xPos = mc.thePlayer.posX - p.x > 0;
				boolean _zPos = mc.thePlayer.posZ - p.z > 0;
				backwardsMoveFix = xPositive != _xPos && zPositive != _zPos;
			}

			if (!(p.rotationYawPacket == 90.0 || p.rotationYawPacket == 180.0 || p.rotationYawPacket == 270.0)
					|| !Minecraft.thePlayer.sendQueue.doneLoadingTerrain) {
				Minecraft.thePlayer.setPosition(p.x, p.y, p.z);
				Minecraft.thePlayer.sendQueue.netManager.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.getEntityBoundingBox().minY,
						Minecraft.thePlayer.posZ, p.rotationYawPacket, p.rotationPitchPacket, false));
				if (!Minecraft.thePlayer.sendQueue.doneLoadingTerrain) {
					Minecraft.thePlayer.prevPosX = Minecraft.thePlayer.posX;
					Minecraft.thePlayer.prevPosY = Minecraft.thePlayer.posY;
					Minecraft.thePlayer.prevPosZ = Minecraft.thePlayer.posZ;
					Minecraft.thePlayer.sendQueue.doneLoadingTerrain = true;
					mc.displayGuiScreen((GuiScreen) null);
				}
				e.setCancelled(true);
			}

		}
		if (e.getPacket() instanceof S12PacketEntityVelocity && Minecraft.theWorld
				.getEntityByID(((S12PacketEntityVelocity) e.getPacket()).entityID()).equals(Minecraft.thePlayer)) {
			setBackDelay = 1;
		}
		return null;
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
