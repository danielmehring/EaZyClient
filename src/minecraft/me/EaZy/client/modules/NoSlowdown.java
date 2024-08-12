package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.modules.YesCheat.Mode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module {

	public static NoSlowdown mod;

	public static final int EaZy = 150;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public NoSlowdown() {
		super("NoSlowdown", 82, "ns", Category.MOVEMENT, "Don't slow down\nwhile eating.");
		mod = this;
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}

	@Override
	public void onEnable() {

		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "KeineVerlangsamung";
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

		if (YesCheat.enabled && YesCheat.mode == Mode.Gomme) {
			if (mc.thePlayer.isInWater() || mc.thePlayer.capabilities.isFlying) {
				return;
			}
			if (mc.thePlayer.onGround) {
				if (mc.thePlayer.isUsingItem() && !mc.thePlayer.isSneaking()) {
					mc.thePlayer.motionX *= 1.5F;
					mc.thePlayer.motionZ *= 1.5F;
					mc.thePlayer.sendQueue.addToSendQueue(
							new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
				} else if (mc.thePlayer.isSneaking() && !mc.thePlayer.isUsingItem()) {
					mc.thePlayer.motionX *= 1.45F;
					mc.thePlayer.motionZ *= 1.45F;
					mc.thePlayer.sendQueue.addToSendQueue(
							new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
				} else if (mc.thePlayer.isSneaking() && mc.thePlayer.isUsingItem()) {
					mc.thePlayer.motionX *= 1.75F;
					mc.thePlayer.motionZ *= 1.75F;
					mc.thePlayer.sendQueue.addToSendQueue(
							new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
				}
			}
		} else {
			if (Minecraft.thePlayer.isBlocking()) {
				if (Minecraft.gameSettings.keyBindSprint.pressed) {
					Minecraft.thePlayer.setSprinting(true);
				}
				Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
				Minecraft.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1),
						255, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
			}
		}
		super.onUpdate();
	}

}
