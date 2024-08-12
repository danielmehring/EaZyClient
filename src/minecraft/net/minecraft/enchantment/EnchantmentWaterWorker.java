package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWorker extends Enchantment {

public static final int EaZy = 1032;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000124";

	public EnchantmentWaterWorker(final int p_i45761_1_, final ResourceLocation p_i45761_2_, final int p_i45761_3_) {
		super(p_i45761_1_, p_i45761_2_, p_i45761_3_, EnumEnchantmentType.ARMOR_HEAD);
		setName("waterWorker");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 1;
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return getMinEnchantability(p_77317_1_) + 40;
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 1;
	}
}
