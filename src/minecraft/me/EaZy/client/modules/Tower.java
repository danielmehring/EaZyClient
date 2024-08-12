package me.EaZy.client.modules;

import java.util.ArrayList;
import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.EntityUtils;
import me.EaZy.client.utils.InventoryUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

public class Tower extends Module {
	public static Tower mod;
	public static final int EaZy = 189;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Tower() {
		super("Tower", 46, "", Category.WORLD, "Build up very fast.");
		final ArrayList<String> options = new ArrayList<>();
		options.add("SlowJump");
		options.add("TimerJump");
		options.add("Gomme");
		options.add("Motion");
		Client.setmgr.rSetting(new Setting("Mode", this, "TimerJump", options));
		Client.setmgr.rSetting(new Setting("Silent", this, true));
		Client.setmgr.rSetting(new Setting("Spoof", this, false));
		mod = this;
	}

	private String suffix;
	private static String renderName = "TimerJump";
	private boolean isPerformingJump = false;
	private int delay = 0;
	private static final int jumpDelay = 1;

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "Turmbauer [" + renderName + "]";
			} else {
				suffix = "Turmbauer";
			}
		} else {
			if (Configs.suffix) {
				suffix = "Tower [" + renderName + "]";
			} else {
				suffix = "Tower";
			}
		}
		return suffix;
	}

	private void Swing() {
		if (Client.setmgr.getSettingByName(this, "Silent").getValBoolean()) {
			Minecraft.thePlayer.sendQueue.netManager.sendPacket(new C0APacketAnimation());
		} else {
			Minecraft.thePlayer.swingItem();
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
		if (delay > 0) {
			--delay;
		}
		boolean hasInHand = Minecraft.thePlayer.getHeldItem() != null
				&& Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBlock;
		boolean spoof = Client.setmgr.getSettingByName(this, "Spoof").getValBoolean();
		boolean needsSpoof = false;
		int blockSlot = -1;
		if (!hasInHand && spoof) {
			blockSlot = InventoryUtil.findBlockInHotbar();
			if (blockSlot == -1) {
				return;
			} else {
				hasInHand = true;
				needsSpoof = true;
			}
		}
		String mode = Client.setmgr.getSettingByName(this, "Mode").getValString();
		if (hasInHand && !Minecraft.thePlayer.movementInput.sneak) {
			BlockPos floorPos;
			if (mode.equalsIgnoreCase("Gomme")) {
				renderName = "Gomme";
				if (Minecraft.thePlayer.onGround) {
					Minecraft.thePlayer.jump();
				}
				if (Minecraft.thePlayer.onGround && !Minecraft.thePlayer.isOnLadder()) {
					Minecraft.thePlayer.jump();
					Minecraft.thePlayer.motionY = 0.405;
				}
				if (Minecraft.thePlayer.motionY > 0) {
					Minecraft.thePlayer.motionY /= 1.025f;
				}
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
				}
				if (BlockUtils.getBlock(floorPos = EntityUtils
						.getBlockPosReallyBeneathEntity(Minecraft.thePlayer)) instanceof BlockAir) {
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
							Minecraft.thePlayer.rotationYaw, 90.0f, Minecraft.thePlayer.onGround));
					if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
							Minecraft.thePlayer.inventory.getCurrentItem(), floorPos.offsetDown(), EnumFacing.UP,
							new Vec3(floorPos.getX(), floorPos.getY(), floorPos.getZ()))) {
						Swing();
					}
				}
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager
							.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				}
			}
			if (mode.equalsIgnoreCase("TimerJump")) {
				renderName = "TimerJump";
				if (Minecraft.thePlayer.onGround && delay <= 0) {
					if (!YesCheat.enabled) {
						Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.5,
								Minecraft.thePlayer.posZ);
						Minecraft.thePlayer.motionY = 0.5;
						isPerformingJump = true;
					} else {
						Timer.timerSpeed = 1.6f;
						Minecraft.thePlayer.jump();
						isPerformingJump = true;
					}
				}
				if (isPerformingJump && delay <= 0 && BlockUtils.getBlock(floorPos = EntityUtils
						.getBlockPosReallyBeneathEntity(Minecraft.thePlayer)) instanceof BlockAir) {
					Minecraft.thePlayer.sendQueue.netManager.sendPacket(
							new C03PacketPlayer.C05PacketPlayerLook(Minecraft.thePlayer.rotationYaw, 90.0f, true));
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
					}
					if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
							Minecraft.thePlayer.inventory.getCurrentItem(), floorPos.offsetDown(), EnumFacing.UP,
							new Vec3(floorPos.getX(), floorPos.getY(), floorPos.getZ())))
						Swing();
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager
								.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
					}
					isPerformingJump = false;
					Timer.timerSpeed = 1.0f;
					Minecraft.thePlayer.motionY = -0.10000000149011612;
					if (YesCheat.enabled) {
						delay = Tower.jumpDelay;
					}
				}
			}
			if (mode.equalsIgnoreCase("SlowJump")) {
				renderName = "SlowJump";
				if (Minecraft.thePlayer.onGround && delay <= 0) {
					if (!YesCheat.enabled) {
						Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.5,
								Minecraft.thePlayer.posZ);
						Minecraft.thePlayer.motionY = 0.5;
						isPerformingJump = true;
					} else {
						Timer.timerSpeed = 1.0f;
						Minecraft.thePlayer.jump();
						isPerformingJump = true;
					}
				}
				if (isPerformingJump && delay <= 0 && BlockUtils.getBlock(floorPos = EntityUtils
						.getBlockPosReallyBeneathEntity(Minecraft.thePlayer)) instanceof BlockAir) {
					Minecraft.thePlayer.sendQueue.netManager.sendPacket(
							new C03PacketPlayer.C05PacketPlayerLook(Minecraft.thePlayer.rotationYaw, 90.0f, true));
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
					}
					if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
							Minecraft.thePlayer.inventory.getCurrentItem(), floorPos.offsetDown(), EnumFacing.UP,
							new Vec3(floorPos.getX(), floorPos.getY(), floorPos.getZ())))
						Swing();
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager
								.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
					}
					isPerformingJump = false;
					Timer.timerSpeed = 1.0f;
					Minecraft.thePlayer.motionY = -0.10000000149011612;
					if (YesCheat.enabled) {
						delay = Tower.jumpDelay;
					}
				}
			}
			if (mode.equalsIgnoreCase("Motion")) {
				renderName = "Motion";
				if (Minecraft.thePlayer.onGround && delay <= 0) {
					Timer.timerSpeed = 1.0f;
					Minecraft.thePlayer.motionY = 1.0f;
					isPerformingJump = true;
				}
				if (!Minecraft.thePlayer.onGround) {
					Minecraft.thePlayer.motionY = -0.0719217f;
				}
				if (isPerformingJump && delay <= 0 && BlockUtils.getBlock(floorPos = EntityUtils
						.getBlockPosReallyBeneathEntity(Minecraft.thePlayer)) instanceof BlockAir) {
					Minecraft.thePlayer.sendQueue.netManager.sendPacket(
							new C03PacketPlayer.C05PacketPlayerLook(Minecraft.thePlayer.rotationYaw, 90.0f, true));
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
					}
					if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
							Minecraft.thePlayer.inventory.getCurrentItem(), floorPos.offsetDown(), EnumFacing.UP,
							new Vec3(floorPos.getX(), floorPos.getY(), floorPos.getZ())))
						Swing();
					if (needsSpoof) {
						mc.thePlayer.sendQueue.netManager
								.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
					}
					isPerformingJump = false;
					Timer.timerSpeed = 1.0f;
				}
			}
		} else if (hasInHand && Minecraft.thePlayer.movementInput.sneak) {
			Timer.timerSpeed = 1.0f;
		}
		super.onUpdate();
	}

	@Override
	public void onEnable() {
		delay = 0;
		isPerformingJump = false;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		Timer.timerSpeed = 1.0f;
		super.onDisable();
	}
}
