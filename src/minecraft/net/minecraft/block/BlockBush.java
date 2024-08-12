package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBush extends Block {

public static final int EaZy = 264;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000208";

	protected BlockBush(final Material materialIn) {
		super(materialIn);
		setTickRandomly(true);
		final float var2 = 0.2F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 3.0F, 0.5F + var2);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	protected BlockBush() {
		this(Material.plants);
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos)
				&& canPlaceBlockOn(worldIn.getBlockState(pos.offsetDown()).getBlock());
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	protected boolean canPlaceBlockOn(final Block ground) {
		return ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
		func_176475_e(worldIn, pos, state);
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		func_176475_e(worldIn, pos, state);
	}

	protected void func_176475_e(final World worldIn, final BlockPos p_176475_2_, final IBlockState p_176475_3_) {
		if (!canBlockStay(worldIn, p_176475_2_, p_176475_3_)) {
			dropBlockAsItem(worldIn, p_176475_2_, p_176475_3_, 0);
			worldIn.setBlockState(p_176475_2_, Blocks.air.getDefaultState(), 3);
		}
	}

	public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
		return canPlaceBlockOn(worldIn.getBlockState(p_180671_2_.offsetDown()).getBlock());
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
}
