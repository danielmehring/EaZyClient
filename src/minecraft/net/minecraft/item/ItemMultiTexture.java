package net.minecraft.item;

import net.minecraft.block.Block;

import com.google.common.base.Function;

public class ItemMultiTexture extends ItemBlock {

public static final int EaZy = 1310;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Block theBlock;
	protected final Function nameFunction;
	// private static final String __OBFID = "http://https://fuckuskid00000051";

	public ItemMultiTexture(final Block p_i45784_1_, final Block p_i45784_2_, final Function p_i45784_3_) {
		super(p_i45784_1_);
		theBlock = p_i45784_2_;
		nameFunction = p_i45784_3_;
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public ItemMultiTexture(final Block p_i45346_1_, final Block p_i45346_2_, final String[] p_i45346_3_) {
		this(p_i45346_1_, p_i45346_2_, new Function() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002161";
			public String apply(final ItemStack p_179541_1_) {
				int var2 = p_179541_1_.getMetadata();

				if (var2 < 0 || var2 >= p_i45346_3_.length) {
					var2 = 0;
				}

				return p_i45346_3_[var2];
			}

			@Override
			public Object apply(final Object p_apply_1_) {
				return this.apply((ItemStack) p_apply_1_);
			}
		});
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
		return super.getUnlocalizedName() + "." + (String) nameFunction.apply(stack);
	}
}
