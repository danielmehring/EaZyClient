package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class ItemEmptyMap extends ItemMapBase {

public static final int EaZy = 1288;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000024";

	protected ItemEmptyMap() {
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		final ItemStack var4 = new ItemStack(Items.filled_map, 1, worldIn.getUniqueDataId("map"));
		final String var5 = "map_" + var4.getMetadata();
		final MapData var6 = new MapData(var5);
		worldIn.setItemData(var5, var6);
		var6.scale = 0;
		var6.func_176054_a(playerIn.posX, playerIn.posZ, var6.scale);
		var6.dimension = (byte) worldIn.provider.getDimensionId();
		var6.markDirty();
		--itemStackIn.stackSize;

		if (itemStackIn.stackSize <= 0) {
			return var4;
		} else {
			if (!playerIn.inventory.addItemStackToInventory(var4.copy())) {
				playerIn.dropPlayerItemWithRandomChoice(var4, false);
			}

			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemStackIn;
		}
	}
}
