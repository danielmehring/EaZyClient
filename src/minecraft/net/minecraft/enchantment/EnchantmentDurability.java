package net.minecraft.enchantment;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class EnchantmentDurability extends Enchantment {

public static final int EaZy = 1021;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000103";

	protected EnchantmentDurability(final int p_i45773_1_, final ResourceLocation p_i45773_2_, final int p_i45773_3_) {
		super(p_i45773_1_, p_i45773_2_, p_i45773_3_, EnumEnchantmentType.BREAKABLE);
		setName("durability");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 5 + (p_77321_1_ - 1) * 8;
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

	@Override
	public boolean canApply(final ItemStack p_92089_1_) {
		return p_92089_1_.isItemStackDamageable() ? true : super.canApply(p_92089_1_);
	}

	/**
	 * Used by ItemStack.attemptDamageItem. Randomly determines if a point of
	 * damage should be negated using the enchantment level (par1). If the
	 * ItemStack is Armor then there is a flat 60% chance for damage to be
	 * negated no matter the enchantment level, otherwise there is a 1-(par/1)
	 * chance for damage to be negated.
	 */
	public static boolean negateDamage(final ItemStack p_92097_0_, final int p_92097_1_, final Random p_92097_2_) {
		return p_92097_0_.getItem() instanceof ItemArmor && p_92097_2_.nextFloat() < 0.6F ? false
				: p_92097_2_.nextInt(p_92097_1_ + 1) > 0;
	}
}
