package me.EaZy.client.modules;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ShitUtils;
import me.EaZy.client.utils.ShitUtils2;
import me.EaZy.client.utils.ShitUtils3;
import me.EaZy.client.utils.holoimage.Image;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class FastPlace extends Module {

	public static FastPlace mod;

	public static final int EaZy = 111;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public FastPlace() {
		super("FastPlace", 80, "fp", Category.WORLD, "Place blocks faster.");
		Client.setmgr.rSetting(new Setting("HIGHSPEED", this, false));
		mod = this;
	}

	@Override
	public void onDisable() {
		Minecraft.rightClickDelayTimer = 6;
		super.onDisable();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "SchnellPlazierung";
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
		Minecraft.rightClickDelayTimer = 0;
		if (Client.setmgr.getSettingByName(this, "HIGHSPEED").getValBoolean() && Minecraft.gameSettings.keyBindUseItem.pressed) {
			final ItemStack var2 = Minecraft.thePlayer.inventory.getCurrentItem();

			if (mc.objectMouseOver == null) {
				System.err.println("Null returned as \'hitResult\', this shouldn\'t happen!");
			} else {
				switch (SwitchEnumMinecartType.field_152390_a[mc.objectMouseOver.typeOfHit.ordinal()]) {
				case 2:
					final BlockPos var3 = mc.objectMouseOver.getBlockPos();

					if (Minecraft.theWorld.getBlockState(var3).getBlock().getMaterial() != Material.air) {
						final int var4 = var2 != null ? var2.stackSize : 0;

						for (int i = 0; i < 3; i++)
							if (mc.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld, var2, var3,
									mc.objectMouseOver.facing, mc.objectMouseOver.hitVec)) {
								Minecraft.thePlayer.swingItem();
								if ((mc.thePlayer.getHeldItem() != null)
										&& (mc.thePlayer.getHeldItem().getDisplayName().startsWith("ImageStand #"))) {
									Image.giveNextArmorstand();
								}
								if ((mc.thePlayer.getHeldItem() != null)
										&& (mc.thePlayer.getHeldItem().getDisplayName().equals("§6HoloSpammer"))) {
									ShitUtils.nextHoloSpammer();
								}
								if ((mc.thePlayer.getHeldItem() != null)
										&& (mc.thePlayer.getHeldItem().getDisplayName().equals("§6MotionHoloSpammer"))) {
									ShitUtils2.nextHoloSpammer();
								}
								if ((mc.thePlayer.getHeldItem() != null)
										&& (mc.thePlayer.getHeldItem().getDisplayName().equals("§6ColoredHoloSpammer"))) {
									ShitUtils3.nextHoloSpammer();
								}
							}
					}
				}
			}
		}
		super.onUpdate();
	}

	static final class SwitchEnumMinecartType {

		static final int[] field_152390_a;

		static final int[] field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];

		static {
			try {
				field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
			} catch (final NoSuchFieldError var8) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var7) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
			} catch (final NoSuchFieldError var6) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
			} catch (final NoSuchFieldError var5) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
			} catch (final NoSuchFieldError var4) {}

			field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
			} catch (final NoSuchFieldError var1) {}
		}
	}

}
