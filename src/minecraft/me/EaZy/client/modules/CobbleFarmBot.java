package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.EntityUtil;
import me.EaZy.client.utils.InventoryUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class CobbleFarmBot extends Module {

	public static CobbleFarmBot mod;

	public static final int EaZy = 2057;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public CobbleFarmBot() {
		super("CobbleFarmBot", 0, "FarmBot", Category.OTHER);
		Client.setmgr.rSetting(new Setting("Range", this, 4, 1, 6, false));
		Client.setmgr.rSetting(new Setting("AutoSwitch", this, true));
		mod = this;
	}

	public boolean breaking = false;
	public BlockPos pos = new BlockPos(0, 0, 0);

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
		MovingObjectPosition mop = EntityUtil.rayTraceBlocks(Client.setmgr.getSettingByName(this, "Range").getValFloat());
		if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
			if (!breaking) {
				if (Client.setmgr.getSettingByName(this, "AutoSwitch").getValBoolean()) {
					if (!(mc.thePlayer.getCurrentEquippedItem() != null)
							|| !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPickaxe)) {
						int i = InventoryUtil.findPickaxeInHotbar();
						if (i != -1) {
							Minecraft.thePlayer.inventory.currentItem = i;
							mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
						} else {
							return;
						}
					}
				}
				mc.clickMouse();
				mc.sendClickBlockToController(true);
				pos = mop.getBlockPos();

				breaking = true;
			} else {
				final BlockPos block = mop.getBlockPos();

				if (Minecraft.theWorld.getBlockState(block).getBlock().getMaterial() != Material.air
						&& mc.playerController.onPlayerDamageBlock(block, mop.facing)) {
					Minecraft.effectRenderer.func_180532_a(block, mop.facing);
					Minecraft.thePlayer.swingItem();
				}
			}
		} else if (mop == null || BlockUtils.getBlock(pos) instanceof BlockAir) {
			breaking = false;
		}
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		breaking = false;
		super.onDisable();
	}

}
