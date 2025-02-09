package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDigging extends Enchantment {

public static final int EaZy = 1020;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000104";

	protected EnchantmentDigging(final int p_i45772_1_, final ResourceLocation p_i45772_2_, final int p_i45772_3_) {
		super(p_i45772_1_, p_i45772_2_, p_i45772_3_, EnumEnchantmentType.DIGGER);
		setName("digging");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 1 + 10 * (p_77321_1_ - 1);
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return super.getMinEnchantability(p_77317_1_) + 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public boolean canApply(final ItemStack p_92089_1_) {
		return p_92089_1_.getItem() == Items.shears ? true : super.canApply(p_92089_1_);
	}
}
