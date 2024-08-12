package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFishingSpeed extends Enchantment {

public static final int EaZy = 1023;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000117";

	protected EnchantmentFishingSpeed(final int p_i45769_1_, final ResourceLocation p_i45769_2_, final int p_i45769_3_,
			final EnumEnchantmentType p_i45769_4_) {
		super(p_i45769_1_, p_i45769_2_, p_i45769_3_, p_i45769_4_);
		setName("fishingSpeed");
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
}
