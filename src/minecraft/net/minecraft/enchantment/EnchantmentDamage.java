package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamage extends Enchantment {

public static final int EaZy = 1018;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Holds the name to be translated of each protection type. */
	private static final String[] protectionName = new String[] { "all", "undead", "arthropods" };

	/**
	 * Holds the base factor of enchantability needed to be able to use the
	 * enchant.
	 */
	private static final int[] baseEnchantability = new int[] { 1, 5, 5 };

	/**
	 * Holds how much each level increased the enchantability factor to be able
	 * to use this enchant.
	 */
	private static final int[] levelEnchantability = new int[] { 11, 8, 8 };

	/**
	 * Used on the formula of base enchantability, this is the 'window' factor
	 * of values to be able to use thing enchant.
	 */
	private static final int[] thresholdEnchantability = new int[] { 20, 20, 20 };

	/**
	 * Defines the type of damage of the enchantment, 0 = all, 1 = undead, 3 =
	 * arthropods
	 */
	public final int damageType;
	// private static final String __OBFID = "http://https://fuckuskid00000102";

	public EnchantmentDamage(final int p_i45774_1_, final ResourceLocation p_i45774_2_, final int p_i45774_3_,
			final int p_i45774_4_) {
		super(p_i45774_1_, p_i45774_2_, p_i45774_3_, EnumEnchantmentType.WEAPON);
		damageType = p_i45774_4_;
	}

	/**
	 * Returns the minimal value of enchantability needed on the enchantment
	 * level passed.
	 */
	@Override
	public int getMinEnchantability(final int p_77321_1_) {
		return baseEnchantability[damageType] + (p_77321_1_ - 1) * levelEnchantability[damageType];
	}

	/**
	 * Returns the maximum value of enchantability nedded on the enchantment
	 * level passed.
	 */
	@Override
	public int getMaxEnchantability(final int p_77317_1_) {
		return getMinEnchantability(p_77317_1_) + thresholdEnchantability[damageType];
	}

	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public float func_152376_a(final int p_152376_1_, final EnumCreatureAttribute p_152376_2_) {
		return damageType == 0 ? p_152376_1_ * 1.25F
				: damageType == 1 && p_152376_2_ == EnumCreatureAttribute.UNDEAD ? p_152376_1_ * 2.5F
						: damageType == 2 && p_152376_2_ == EnumCreatureAttribute.ARTHROPOD ? p_152376_1_ * 2.5F : 0.0F;
	}

	/**
	 * Return the name of key in translation table of this enchantment.
	 */
	@Override
	public String getName() {
		return "enchantment.damage." + protectionName[damageType];
	}

	/**
	 * Determines if the enchantment passed can be applyied together with this
	 * enchantment.
	 */
	@Override
	public boolean canApplyTogether(final Enchantment p_77326_1_) {
		return !(p_77326_1_ instanceof EnchantmentDamage);
	}

	@Override
	public boolean canApply(final ItemStack p_92089_1_) {
		return p_92089_1_.getItem() instanceof ItemAxe ? true : super.canApply(p_92089_1_);
	}

	@Override
	public void func_151368_a(final EntityLivingBase p_151368_1_, final Entity p_151368_2_, final int p_151368_3_) {
		if (p_151368_2_ instanceof EntityLivingBase) {
			final EntityLivingBase var4 = (EntityLivingBase) p_151368_2_;

			if (damageType == 2 && var4.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
				final int var5 = 20 + p_151368_1_.getRNG().nextInt(10 * p_151368_3_);
				var4.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, var5, 3));
			}
		}
	}
}
