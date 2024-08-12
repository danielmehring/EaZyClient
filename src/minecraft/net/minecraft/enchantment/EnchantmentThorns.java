package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class EnchantmentThorns extends Enchantment {

public static final int EaZy = 1029;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000122";

	public EnchantmentThorns(final int p_i45764_1_, final ResourceLocation p_i45764_2_, final int p_i45764_3_) {
		super(p_i45764_1_, p_i45764_2_, p_i45764_3_, EnumEnchantmentType.ARMOR_TORSO);
		setName("thorns");
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return 10 + 20 * (p_77321_1_ - 1);
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
		return p_92089_1_.getItem() instanceof ItemArmor ? true : super.canApply(p_92089_1_);
	}

	@Override
	public void func_151367_b(final EntityLivingBase p_151367_1_, final Entity p_151367_2_, final int p_151367_3_) {
		final Random var4 = p_151367_1_.getRNG();
		final ItemStack var5 = EnchantmentHelper.func_92099_a(Enchantment.thorns, p_151367_1_);

		if (func_92094_a(p_151367_3_, var4)) {
			p_151367_2_.attackEntityFrom(DamageSource.causeThornsDamage(p_151367_1_), func_92095_b(p_151367_3_, var4));
			p_151367_2_.playSound("damage.thorns", 0.5F, 1.0F);

			if (var5 != null) {
				var5.damageItem(3, p_151367_1_);
			}
		} else if (var5 != null) {
			var5.damageItem(1, p_151367_1_);
		}
	}

	public static boolean func_92094_a(final int p_92094_0_, final Random p_92094_1_) {
		return p_92094_0_ <= 0 ? false : p_92094_1_.nextFloat() < 0.15F * p_92094_0_;
	}

	public static int func_92095_b(final int p_92095_0_, final Random p_92095_1_) {
		return p_92095_0_ > 10 ? p_92095_0_ - 10 : 1 + p_92095_1_.nextInt(4);
	}
}
