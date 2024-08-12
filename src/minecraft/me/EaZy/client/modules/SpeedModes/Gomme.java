package me.EaZy.client.modules.SpeedModes;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import io.netty.buffer.Unpooled;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.Location;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import net.minecraft.util.Timer;

public class Gomme extends Module {

	public static Gomme mod;

	public static final int EaZy = 2066;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Gomme() {
		super("Gomme", 0, "", Category.SPEED);
		mod = this;
	}

	public static int stage = 0;
	public static boolean jumpedFromGround = false;
	public static boolean hasNoHurt = true;

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
		if (mc.thePlayer.onGround && !hasNoHurt) {
			hasNoHurt = true;
		}
		if (mc.thePlayer.hurtTime > 0) {
			hasNoHurt = false;
		}
		if (hasNoHurt) {
			if (PlayerUtil.isPlayerMoving()) {
				if (!(Minecraft.thePlayer.isInWater() || Minecraft.thePlayer.capabilities.isFlying)) {
					if (!mc.gameSettings.keyBindJump.pressed) {
						if (mc.thePlayer.onGround) {
							mc.thePlayer.motionY = 0.3851;
							stage = 0;
							jumpedFromGround = true;
						} else {
							if (jumpedFromGround && mc.thePlayer.fallDistance <= 0.83424) {
								stage++;
								if (stage == 1)
									mc.thePlayer.motionY = 0.284;
								else if (stage == 2)
									mc.thePlayer.motionY = 0.185;
								else if (stage == 3)
									mc.thePlayer.motionY = 0.088;
								else if (stage == 4)
									mc.thePlayer.motionY = -0.077;
								else if (stage == 5)
									mc.thePlayer.motionY = -0.168;
								else if (stage == 6)
									mc.thePlayer.motionY = -0.258;
							}
						}
					}
					if (!(Minecraft.gameSettings.keyBindLeft.getIsKeyPressed()
							|| Minecraft.gameSettings.keyBindRight.getIsKeyPressed()
							|| Minecraft.gameSettings.keyBindBack.getIsKeyPressed())) {
						if (!Minecraft.thePlayer.onGround) {
							Minecraft.thePlayer.motionX = 0.0;
							Minecraft.thePlayer.motionZ = 0.0;
						}
						Minecraft.thePlayer.jumpMovementFactor = 0.303f;
					} else {
						if (!Minecraft.thePlayer.onGround) {
							Minecraft.thePlayer.motionX = 0.0;
							Minecraft.thePlayer.motionZ = 0.0;
						}
						Minecraft.thePlayer.jumpMovementFactor = 0.255f;
					}
				}
			} else {
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionZ = 0;
				jumpedFromGround = false;
			}
		}

		super.onUpdate();
	}
}
