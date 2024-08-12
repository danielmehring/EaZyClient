package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {

public static final int EaZy = 1240;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002184";

	public SlotFurnaceFuel(final IInventory p_i45795_1_, final int p_i45795_2_, final int p_i45795_3_,
			final int p_i45795_4_) {
		super(p_i45795_1_, p_i45795_2_, p_i45795_3_, p_i45795_4_);
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(final ItemStack stack) {
		return TileEntityFurnace.isItemFuel(stack) || func_178173_c_(stack);
	}

	@Override
	public int func_178170_b(final ItemStack p_178170_1_) {
		return func_178173_c_(p_178170_1_) ? 1 : super.func_178170_b(p_178170_1_);
	}

	public static boolean func_178173_c_(final ItemStack p_178173_0_) {
		return p_178173_0_ != null && p_178173_0_.getItem() != null && p_178173_0_.getItem() == Items.bucket;
	}
}
