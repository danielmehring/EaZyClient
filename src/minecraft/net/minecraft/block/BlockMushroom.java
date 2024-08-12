package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import java.util.Iterator;
import java.util.Random;

public class BlockMushroom extends BlockBush implements IGrowable {

public static final int EaZy = 330;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000272";

	protected BlockMushroom() {
		final float var1 = 0.2F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
		setTickRandomly(true);
	}

	@Override
	public void updateTick(final World worldIn, BlockPos pos, final IBlockState state, final Random rand) {
		if (rand.nextInt(25) == 0) {
			int var5 = 5;
			final Iterator var7 = BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4)).iterator();

			while (var7.hasNext()) {
				final BlockPos var8 = (BlockPos) var7.next();

				if (worldIn.getBlockState(var8).getBlock() == this) {
					--var5;

					if (var5 <= 0) {
						return;
					}
				}
			}

			BlockPos var9 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

			for (int var10 = 0; var10 < 4; ++var10) {
				if (worldIn.isAirBlock(var9) && canBlockStay(worldIn, var9, getDefaultState())) {
					pos = var9;
				}

				var9 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
			}

			if (worldIn.isAirBlock(var9) && canBlockStay(worldIn, var9, getDefaultState())) {
				worldIn.setBlockState(var9, getDefaultState(), 2);
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos, getDefaultState());
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	@Override
	protected boolean canPlaceBlockOn(final Block ground) {
		return ground.isFullBlock();
	}

	@Override
	public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
		if (p_180671_2_.getY() >= 0 && p_180671_2_.getY() < 256) {
			final IBlockState var4 = worldIn.getBlockState(p_180671_2_.offsetDown());
			return var4.getBlock() == Blocks.mycelium ? true
					: var4.getBlock() == Blocks.dirt && var4.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL
							? true : worldIn.getLight(p_180671_2_) < 13 && canPlaceBlockOn(var4.getBlock());
		} else {
			return false;
		}
	}

	public boolean func_176485_d(final World worldIn, final BlockPos p_176485_2_, final IBlockState p_176485_3_,
			final Random p_176485_4_) {
		worldIn.setBlockToAir(p_176485_2_);
		WorldGenBigMushroom var5 = null;

		if (this == Blocks.brown_mushroom) {
			var5 = new WorldGenBigMushroom(0);
		} else if (this == Blocks.red_mushroom) {
			var5 = new WorldGenBigMushroom(1);
		}

		if (var5 != null && var5.generate(worldIn, p_176485_4_, p_176485_2_)) {
			return true;
		} else {
			worldIn.setBlockState(p_176485_2_, p_176485_3_, 3);
			return false;
		}
	}

	@Override
	public boolean isStillGrowing(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_,
			final boolean p_176473_4_) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_,
			final IBlockState p_180670_4_) {
		return p_180670_2_.nextFloat() < 0.4D;
	}

	@Override
	public void grow(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_,
			final IBlockState p_176474_4_) {
		func_176485_d(worldIn, p_176474_3_, p_176474_4_, p_176474_2_);
	}
}
