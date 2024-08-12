package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {

public static final int EaZy = 1278;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000048";

	public ItemBucketMilk() {
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Override
	public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
		if (!playerIn.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!worldIn.isRemote) {
			playerIn.clearActivePotions();
		}

		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return stack.stackSize <= 0 ? new ItemStack(Items.bucket) : stack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.DRINK;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}
