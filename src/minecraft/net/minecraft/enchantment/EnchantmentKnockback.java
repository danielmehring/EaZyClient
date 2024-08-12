package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentKnockback extends Enchantment {

public static final int EaZy = 1025;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000118";

	protected EnchantmentKnockback(final int p_i45768_1_, final ResourceLocation p_i45768_2_, final int p_i45768_3_) {
		super(p_i45768_1_, p_i45768_2_, p_i45768_3_, EnumEnchantmentType.WEAPON);
		setName("knockback");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 5 + 20 * (p_77321_1_ - 1);
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
		return 2;
	}
}
