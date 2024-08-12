package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {

public static final int EaZy = 1027;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000120";

	public EnchantmentOxygen(final int p_i45766_1_, final ResourceLocation p_i45766_2_, final int p_i45766_3_) {
		super(p_i45766_1_, p_i45766_2_, p_i45766_3_, EnumEnchantmentType.ARMOR_HEAD);
		setName("oxygen");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 10 * p_77321_1_;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return getMinEnchantability(p_77317_1_) + 30;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 3;
	}
}
