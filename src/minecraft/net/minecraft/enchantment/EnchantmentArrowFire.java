package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowFire extends Enchantment {

public static final int EaZy = 1015;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000099";

	public EnchantmentArrowFire(final int p_i45777_1_, final ResourceLocation p_i45777_2_, final int p_i45777_3_) {
		super(p_i45777_1_, p_i45777_2_, p_i45777_3_, EnumEnchantmentType.BOW);
		setName("arrowFire");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 20;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return 50;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 1;
	}
}
