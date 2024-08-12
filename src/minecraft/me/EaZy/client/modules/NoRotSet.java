package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotSet extends Module {

	public static NoRotSet mod;

	public static final int EaZy = 148;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public NoRotSet() {
		super("NoRotSet", 0, "", Category.PLAYER, "Ignore Server-Rot\nPackets.");
		mod = this;
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

		super.onUpdate();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "KeineRotationenSetzen";
		} else {
			return super.getRenderName();
		}
	}

	public EventTarget onPacketReceive(final EventReceivePacket event) {
		if (mc.thePlayer.posY >= 0)
			if (event.getPacket() instanceof S08PacketPlayerPosLook) {
				final S08PacketPlayerPosLook p = (S08PacketPlayerPosLook) event.getPacket();
				if (!(p.rotationYawPacket == 90.0 || p.rotationYawPacket == 180.0
						|| p.rotationYawPacket == 270.0 || (p.rotationPitchPacket == 0.0 && p.rotationYawPacket == 0.0
								&& p.x == 0.0 && p.y == 0.0 && p.z == 0.0))
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
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
