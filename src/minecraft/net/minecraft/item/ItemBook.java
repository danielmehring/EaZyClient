package net.minecraft.item;

public class ItemBook extends Item {

public static final int EaZy = 1275;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001775";

	/**
	 * Checks isDamagable and if it cannot be stacked
	 */
	@Override
	public boolean isItemTool(final ItemStack stack) {
		return stack.stackSize == 1;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}
}
