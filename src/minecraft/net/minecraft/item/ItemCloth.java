package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemCloth extends ItemBlock {

public static final int EaZy = 1280;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000075";

	public ItemCloth(final Block p_i45358_1_) {
		super(p_i45358_1_);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	/**
	 * Converts the given ItemStack damage value into a metadata value to be
	 * placed in the world when this Item is placed as a Block (mostly used with
	 * ItemBlocks).
	 */
	@Override
	public int getMetadata(final int damage) {
		return damage;
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return super.getUnlocalizedName() + "." + EnumDyeColor.func_176764_b(stack.getMetadata()).func_176762_d();
	}
}
