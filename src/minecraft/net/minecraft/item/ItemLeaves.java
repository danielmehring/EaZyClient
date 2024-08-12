package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {

public static final int EaZy = 1304;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockLeaves field_150940_b;
	// private static final String __OBFID = "http://https://fuckuskid00000046";

	public ItemLeaves(final BlockLeaves p_i45344_1_) {
		super(p_i45344_1_);
		field_150940_b = p_i45344_1_;
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
		return damage | 4;
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		return field_150940_b.getRenderColor(field_150940_b.getStateFromMeta(stack.getMetadata()));
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return super.getUnlocalizedName() + "." + field_150940_b.func_176233_b(stack.getMetadata()).func_176840_c();
	}
}
