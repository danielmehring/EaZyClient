package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemEnderPearl extends Item {

public static final int EaZy = 1291;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000027";

	public ItemEnderPearl() {
		maxStackSize = 16;
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode) {
			return itemStackIn;
		} else {
			--itemStackIn.stackSize;
			worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
				worldIn.spawnEntityInWorld(new EntityEnderPearl(worldIn, playerIn));
			}

			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemStackIn;
		}
	}
}
