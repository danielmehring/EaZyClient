package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {

public static final int EaZy = 1298;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000035";

	public ItemFlintAndSteel() {
		maxStackSize = 1;
		setMaxDamage(64);
		setCreativeTab(CreativeTabs.tabTools);
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
		pos = pos.offset(side);

		if (!playerIn.func_175151_a(pos, side, stack)) {
			return false;
		} else {
			if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
				worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "fire.ignite", 1.0F,
						itemRand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
			}

			stack.damageItem(1, playerIn);
			return true;
		}
	}
}
