package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandom;

public class EnchantmentData extends WeightedRandom.Item {

public static final int EaZy = 1019;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Enchantment object associated with this EnchantmentData */
	public final Enchantment enchantmentobj;

	/** Enchantment level associated with this EnchantmentData */
	public final int enchantmentLevel;
	// private static final String __OBFID = "http://https://fuckuskid00000115";

	public EnchantmentData(final Enchantment p_i1930_1_, final int p_i1930_2_) {
		super(p_i1930_1_.getWeight());
		enchantmentobj = p_i1930_1_;
		enchantmentLevel = p_i1930_2_;
	}
}
