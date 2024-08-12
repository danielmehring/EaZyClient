package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemAnvilBlock extends ItemMultiTexture {

public static final int EaZy = 1266;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001764";

	public ItemAnvilBlock(final Block p_i1826_1_) {
		super(p_i1826_1_, p_i1826_1_, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
	}

	/**
	 * Converts the given ItemStack damage value into a metadata value to be
	 * placed in the world when this Item is placed as a Block (mostly used with
	 * ItemBlocks).
	 */
	@Override
	public int getMetadata(final int damage) {
		return damage << 2;
	}
}
