package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {

public static final int EaZy = 1322;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000064";

	public ItemSign() {
		maxStackSize = 16;
		setCreativeTab(CreativeTabs.tabDecorations);
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
		if (side == EnumFacing.DOWN) {
			return false;
		} else if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
			return false;
		} else {
			pos = pos.offset(side);

			if (!playerIn.func_175151_a(pos, side, stack)) {
				return false;
			} else if (!Blocks.standing_sign.canPlaceBlockAt(worldIn, pos)) {
				return false;
			} else if (worldIn.isRemote) {
				return true;
			} else {
				if (side == EnumFacing.UP) {
					final int var9 = MathHelper.floor_double((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D)
							& 15;
					worldIn.setBlockState(pos, Blocks.standing_sign.getDefaultState()
							.withProperty(BlockStandingSign.ROTATION_PROP, var9), 3);
				} else {
					worldIn.setBlockState(pos,
							Blocks.wall_sign.getDefaultState().withProperty(BlockWallSign.field_176412_a, side), 3);
				}

				--stack.stackSize;
				final TileEntity var10 = worldIn.getTileEntity(pos);

				if (var10 instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, pos, stack)) {
					playerIn.func_175141_a((TileEntitySign) var10);
				}

				return true;
			}
		}
	}
}
