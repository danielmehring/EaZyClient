package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalker extends Enchantment {

public static final int EaZy = 1031;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002155";

	public EnchantmentWaterWalker(final int p_i45762_1_, final ResourceLocation p_i45762_2_, final int p_i45762_3_) {
		super(p_i45762_1_, p_i45762_2_, p_i45762_3_, EnumEnchantmentType.ARMOR_FEET);
		setName("waterWalker");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return p_77321_1_ * 10;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return getMinEnchantability(p_77317_1_) + 15;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 3;
	}
}
