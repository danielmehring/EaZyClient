package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemPiston extends ItemBlock {

public static final int EaZy = 1313;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000054";

	public ItemPiston(final Block p_i45348_1_) {
		super(p_i45348_1_);
	}

	/**
	 * Converts the given ItemStack damage value into a metadata value to be
	 * placed in the world when this Item is placed as a Block (mostly used with
	 * ItemBlocks).
	 */
	@Override
	public int getMetadata(final int damage) {
		return 7;
	}
}
