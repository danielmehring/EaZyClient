package net.minecraft.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.List;
import java.util.Random;

public class ItemEnchantedBook extends Item {

public static final int EaZy = 1289;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000025";

	@Override
	public boolean hasEffect(final ItemStack stack) {
		return true;
	}

	/**
	 * Checks isDamagable and if it cannot be stacked
	 */
	@Override
	public boolean isItemTool(final ItemStack stack) {
		return false;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(final ItemStack stack) {
		return func_92110_g(stack).tagCount() > 0 ? EnumRarity.UNCOMMON : super.getRarity(stack);
	}

	public NBTTagList func_92110_g(final ItemStack p_92110_1_) {
		final NBTTagCompound var2 = p_92110_1_.getTagCompound();
		return var2 != null && var2.hasKey("StoredEnchantments", 9) ? (NBTTagList) var2.getTag("StoredEnchantments")
				: new NBTTagList();
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 * 
	 * @param tooltip
	 *            All lines to display in the Item's tooltip. This is a List of
	 *            Strings.
	 * @param advanced
	 *            Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip,
			final boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		final NBTTagList var5 = func_92110_g(stack);

		if (var5 != null) {
			for (int var6 = 0; var6 < var5.tagCount(); ++var6) {
				final short var7 = var5.getCompoundTagAt(var6).getShort("id");
				final short var8 = var5.getCompoundTagAt(var6).getShort("lvl");

				if (Enchantment.byID(var7) != null) {
					tooltip.add(Enchantment.byID(var7).getTranslatedName(var8));
				}
			}
		}
	}

	/**
	 * Adds an stored enchantment to an enchanted book ItemStack
	 */
	public void addEnchantment(final ItemStack p_92115_1_, final EnchantmentData p_92115_2_) {
		final NBTTagList var3 = func_92110_g(p_92115_1_);
		boolean var4 = true;

		for (int var5 = 0; var5 < var3.tagCount(); ++var5) {
			final NBTTagCompound var6 = var3.getCompoundTagAt(var5);

			if (var6.getShort("id") == p_92115_2_.enchantmentobj.effectId) {
				if (var6.getShort("lvl") < p_92115_2_.enchantmentLevel) {
					var6.setShort("lvl", (short) p_92115_2_.enchantmentLevel);
				}

				var4 = false;
				break;
			}
		}

		if (var4) {
			final NBTTagCompound var7 = new NBTTagCompound();
			var7.setShort("id", (short) p_92115_2_.enchantmentobj.effectId);
			var7.setShort("lvl", (short) p_92115_2_.enchantmentLevel);
			var3.appendTag(var7);
		}

		if (!p_92115_1_.hasTagCompound()) {
			p_92115_1_.setTagCompound(new NBTTagCompound());
		}

		p_92115_1_.getTagCompound().setTag("StoredEnchantments", var3);
	}

	/**
	 * Returns the ItemStack of an enchanted version of this item.
	 */
	public ItemStack getEnchantedItemStack(final EnchantmentData p_92111_1_) {
		final ItemStack var2 = new ItemStack(this);
		addEnchantment(var2, p_92111_1_);
		return var2;
	}

	public void func_92113_a(final Enchantment p_92113_1_, final List p_92113_2_) {
		for (int var3 = p_92113_1_.getMinLevel(); var3 <= p_92113_1_.getMaxLevel(); ++var3) {
			p_92113_2_.add(getEnchantedItemStack(new EnchantmentData(p_92113_1_, var3)));
		}
	}

	public WeightedRandomChestContent getRandomEnchantedBook(final Random p_92114_1_) {
		return func_92112_a(p_92114_1_, 1, 1, 1);
	}

	public WeightedRandomChestContent func_92112_a(final Random p_92112_1_, final int p_92112_2_, final int p_92112_3_,
			final int p_92112_4_) {
		final ItemStack var5 = new ItemStack(Items.book, 1, 0);
		EnchantmentHelper.addRandomEnchantment(p_92112_1_, var5, 30);
		return new WeightedRandomChestContent(var5, p_92112_2_, p_92112_3_, p_92112_4_);
	}
}
