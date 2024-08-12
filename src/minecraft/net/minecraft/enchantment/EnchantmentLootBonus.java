package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentLootBonus extends Enchantment {

public static final int EaZy = 1026;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000119";

	protected EnchantmentLootBonus(final int p_i45767_1_, final ResourceLocation p_i45767_2_, final int p_i45767_3_,
			final EnumEnchantmentType p_i45767_4_) {
		super(p_i45767_1_, p_i45767_2_, p_i45767_3_, p_i45767_4_);

		if (p_i45767_4_ == EnumEnchantmentType.DIGGER) {
			setName("lootBonusDigger");
		} else if (p_i45767_4_ == EnumEnchantmentType.FISHING_ROD) {
			setName("lootBonusFishing");
		} else {
			setName("lootBonus");
		}
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 15 + (p_77321_1_ - 1) * 9;
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
		return 3;
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(final Enchantment p_77326_1_) {
		return super.canApplyTogether(p_77326_1_) && p_77326_1_.effectId != silkTouch.effectId;
	}
}
