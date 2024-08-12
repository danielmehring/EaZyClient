package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
	IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001200";
		@Override
		public ItemStack dispense(final IBlockSource source, final ItemStack stack) {
			return stack;
		}
	};

	/**
	 * Dispenses the specified ItemStack from a dispenser.
	 */
	ItemStack dispense(IBlockSource var1, ItemStack var2);
}
