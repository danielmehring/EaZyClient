package me.EaZy.client.modules.glides;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.EntityUtil;
import me.EaZy.client.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AAC3Fly extends Module {

	public static AAC3Fly mod;

	public static final int EaZy = 2046;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public AAC3Fly() {
		super("AAC3Fly", 0, "AAC3TP", Category.GLIDE,
				new String(new byte[] { 0b1010100, 0b1100101, 0b1101100, 0b1100101, 0b1110000, 0b1101111, 0b1110010,
						0b1110100, 0b100000, 0b1100110, 0b1101111, 0b1110010, 0b100000, 0b1000001, 0b1000001, 0b1000011,
						0b101110 }));

		ArrayList<String> versions = new ArrayList<>();
		versions.add("3.1.5");
		versions.add("3.1.6-b2");
		Client.setmgr.rSetting(new Setting("Version", this, "3.1.6-b2", versions));

		Client.setmgr.rSetting(new Setting("Speed", this, 10.0f, 5, 10, false));
		Client.setmgr.rSetting(new Setting("Up", this, 1.2f, 1.2f, 3, false));
		mod = this;
	}

	private double _316b2_xChange;
	private double _316b2_zChange;

	private boolean _315_b1 = false;

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
		switch (Client.setmgr.getSettingByName(this, "Version").getValString()) {
		case "3.1.5": {
			if (mc.thePlayer.motionY < 0)
				_315_b1 = true;
			if (mc.thePlayer.onGround) {
				_315_b1 = false;
				mc.thePlayer.motionY = 0.387;
			}
			double d = 0.0898;
			if (_315_b1) {
				mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 2 == 0 ? d : -d;
			}
			break;
		}
		case "3.1.6-b2": {
			final double yaw = Math.toRadians(Minecraft.thePlayer.rotationYawHead);
			float val = Client.setmgr.getSettingByName(this, "Speed").getValFloat();
			_316b2_xChange = -Math.sin(yaw) * val;
			_316b2_zChange = Math.cos(yaw) * val;
			if (mc.thePlayer.isSneaking()) {
				mc.thePlayer.sendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ, false));
				mc.thePlayer.sendQueue.netManager
						.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + _316b2_xChange,
								mc.thePlayer.posY + Client.setmgr.getSettingByName(this, "Up").getValFloat(),
								mc.thePlayer.posZ + _316b2_zChange, true));
			}
			break;
		}
		default:
			break;
		}
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "AbgewandeltesKontraHack3Fly2";
		} else {
			return super.getRenderName();
		}
	}

	public EventTarget onPacketReceive(final EventReceivePacket event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook
				&& Client.setmgr.getSettingByName(this, "Version").getValString().equals("3.1.6-b2")) {
			final S08PacketPlayerPosLook p = (S08PacketPlayerPosLook) event.getPacket();
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
				event.setCancelled(true);
			}
		}
		return null;
	}

	@Override
	public void onEnable() {
		_315_b1 = false;
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
