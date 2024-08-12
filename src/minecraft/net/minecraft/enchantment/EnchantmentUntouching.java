package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentUntouching extends Enchantment {

public static final int EaZy = 1030;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000123";

	protected EnchantmentUntouching(final int p_i45763_1_, final ResourceLocation p_i45763_2_, final int p_i45763_3_) {
		super(p_i45763_1_, p_i45763_2_, p_i45763_3_, EnumEnchantmentType.DIGGER);
		setName("untouching");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 15;
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
		return 1;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(final Enchantment p_77326_1_) {
		return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != fortune.effectId;
	}

	@Override
	public boolean canApply(final ItemStack p_92089_1_) {
		return p_92089_1_.getItem() == Items.shears ? true : super.canApply(p_92089_1_);
	}
}
