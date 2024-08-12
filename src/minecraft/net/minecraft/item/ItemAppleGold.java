package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class ItemAppleGold extends ItemFood {

public static final int EaZy = 1267;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000037";

	public ItemAppleGold(final int p_i45341_1_, final float p_i45341_2_, final boolean p_i45341_3_) {
		super(p_i45341_1_, p_i45341_2_, p_i45341_3_);
		setHasSubtypes(true);
	}

	@Override
	public boolean hasEffect(final ItemStack stack) {
		return stack.getMetadata() > 0;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(final ItemStack stack) {
		return stack.getMetadata() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
	}

	@Override
	protected void onFoodEaten(final ItemStack p_77849_1_, final World worldIn, final EntityPlayer p_77849_3_) {
		if (!worldIn.isRemote) {
			p_77849_3_.addPotionEffect(new PotionEffect(Potion.absorption.id, 2400, 0));
		}

		if (p_77849_1_.getMetadata() > 0) {
			if (!worldIn.isRemote) {
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
			}
		} else {
			super.onFoodEaten(p_77849_1_, worldIn, p_77849_3_);
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 * 
	 * @param subItems
	 *            The List of sub-items. This is a List of ItemStacks.
	 */
	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
		subItems.add(new ItemStack(itemIn, 1, 0));
		subItems.add(new ItemStack(itemIn, 1, 1));
	}
}
