package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockLilyPad extends BlockBush {

public static final int EaZy = 325;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000332";

	protected BlockLilyPad() {
		final float var1 = 0.5F;
		final float var2 = 0.015625F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Add all collision boxes of this Block to the list that intersect with the
	 * given mask.
	 * 
	 * @param collidingEntity
	 *            the Entity colliding with this Block
	 */
	@Override
	public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state,
			final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
		if (collidingEntity == null || !(collidingEntity instanceof EntityBoat)) {
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return new AxisAlignedBB(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX,
				pos.getY() + maxY, pos.getZ() + maxZ);
	}

	@Override
	public int getBlockColor() {
		return 7455580;
	}

	@Override
	public int getRenderColor(final IBlockState state) {
		return 7455580;
	}

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		return 2129968;
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	@Override
	protected boolean canPlaceBlockOn(final Block ground) {
		return ground == Blocks.water;
	}

	@Override
	public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
		if (p_180671_2_.getY() >= 0 && p_180671_2_.getY() < 256) {
			final IBlockState var4 = worldIn.getBlockState(p_180671_2_.offsetDown());
			return var4.getBlock().getMaterial() == Material.water
					&& ((Integer) var4.getValue(BlockLiquid.LEVEL)) == 0;
		} else {
			return false;
		}
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return 0;
	}
}
