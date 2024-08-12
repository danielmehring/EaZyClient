package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFireball extends Item {

public static final int EaZy = 1293;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000029";

	public ItemFireball() {
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos,
			final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			pos = pos.offset(side);

			if (!playerIn.func_175151_a(pos, side, stack)) {
				return false;
			} else {
				if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
					worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
							"item.fireCharge.use", 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
					worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
				}

				if (!playerIn.capabilities.isCreativeMode) {
					--stack.stackSize;
				}

				return true;
			}
		}
	}
}
