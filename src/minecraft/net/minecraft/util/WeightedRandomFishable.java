package net.minecraft.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class WeightedRandomFishable extends WeightedRandom.Item {

public static final int EaZy = 1665;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ItemStack returnStack;
	private float maxDamagePercent;
	private boolean enchantable;
	// private static final String __OBFID = "http://https://fuckuskid00001664";

	public WeightedRandomFishable(final ItemStack p_i45317_1_, final int p_i45317_2_) {
		super(p_i45317_2_);
		returnStack = p_i45317_1_;
	}

	public ItemStack getItemStack(final Random p_150708_1_) {
		final ItemStack var2 = returnStack.copy();

		if (maxDamagePercent > 0.0F) {
			final int var3 = (int) (maxDamagePercent * returnStack.getMaxDamage());
			int var4 = var2.getMaxDamage() - p_150708_1_.nextInt(p_150708_1_.nextInt(var3) + 1);

			if (var4 > var3) {
				var4 = var3;
			}

			if (var4 < 1) {
				var4 = 1;
			}

			var2.setItemDamage(var4);
		}

		if (enchantable) {
			EnchantmentHelper.addRandomEnchantment(p_150708_1_, var2, 30);
		}

		return var2;
	}

	public WeightedRandomFishable setMaxDamagePercent(final float p_150709_1_) {
		maxDamagePercent = p_150709_1_;
		return this;
	}

	public WeightedRandomFishable setEnchantable() {
		enchantable = true;
		return this;
	}
}
