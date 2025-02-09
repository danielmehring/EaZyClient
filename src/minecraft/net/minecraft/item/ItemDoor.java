package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {

public static final int EaZy = 1283;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_179236_a;
	// private static final String __OBFID = "http://https://fuckuskid00000020";

	public ItemDoor(final Block p_i45788_1_) {
		field_179236_a = p_i45788_1_;
		setCreativeTab(CreativeTabs.tabRedstone);
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
		if (side != EnumFacing.UP) {
			return false;
		} else {
			final IBlockState var9 = worldIn.getBlockState(pos);
			final Block var10 = var9.getBlock();

			if (!var10.isReplaceable(worldIn, pos)) {
				pos = pos.offset(side);
			}

			if (!playerIn.func_175151_a(pos, side, stack)) {
				return false;
			} else if (!field_179236_a.canPlaceBlockAt(worldIn, pos)) {
				return false;
			} else {
				func_179235_a(worldIn, pos, EnumFacing.fromAngle(playerIn.rotationYaw), field_179236_a);
				--stack.stackSize;
				return true;
			}
		}
	}

	public static void func_179235_a(final World worldIn, final BlockPos p_179235_1_, final EnumFacing p_179235_2_,
			final Block p_179235_3_) {
		final BlockPos var4 = p_179235_1_.offset(p_179235_2_.rotateY());
		final BlockPos var5 = p_179235_1_.offset(p_179235_2_.rotateYCCW());
		final int var6 = (worldIn.getBlockState(var5).getBlock().isNormalCube() ? 1 : 0)
				+ (worldIn.getBlockState(var5.offsetUp()).getBlock().isNormalCube() ? 1 : 0);
		final int var7 = (worldIn.getBlockState(var4).getBlock().isNormalCube() ? 1 : 0)
				+ (worldIn.getBlockState(var4.offsetUp()).getBlock().isNormalCube() ? 1 : 0);
		final boolean var8 = worldIn.getBlockState(var5).getBlock() == p_179235_3_
				|| worldIn.getBlockState(var5.offsetUp()).getBlock() == p_179235_3_;
		final boolean var9 = worldIn.getBlockState(var4).getBlock() == p_179235_3_
				|| worldIn.getBlockState(var4.offsetUp()).getBlock() == p_179235_3_;
		boolean var10 = false;

		if (var8 && !var9 || var7 > var6) {
			var10 = true;
		}

		final BlockPos var11 = p_179235_1_.offsetUp();
		final IBlockState var12 = p_179235_3_.getDefaultState().withProperty(BlockDoor.FACING_PROP, p_179235_2_)
				.withProperty(BlockDoor.HINGEPOSITION_PROP,
						var10 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);
		worldIn.setBlockState(p_179235_1_, var12.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.LOWER), 2);
		worldIn.setBlockState(var11, var12.withProperty(BlockDoor.HALF_PROP, BlockDoor.EnumDoorHalf.UPPER), 2);
		worldIn.notifyNeighborsOfStateChange(p_179235_1_, p_179235_3_);
		worldIn.notifyNeighborsOfStateChange(var11, p_179235_3_);
	}
}
