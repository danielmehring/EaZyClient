package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenVines extends WorldGenerator {

public static final int EaZy = 1771;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000439";

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
		for (; p_180709_3_.getY() < 128; p_180709_3_ = p_180709_3_.offsetUp()) {
			if (worldIn.isAirBlock(p_180709_3_)) {
				final EnumFacing[] var4 = EnumFacing.Plane.HORIZONTAL.facings();
				final int var5 = var4.length;

				for (int var6 = 0; var6 < var5; ++var6) {
					final EnumFacing var7 = var4[var6];

					if (Blocks.vine.canPlaceBlockOnSide(worldIn, p_180709_3_, var7)) {
						final IBlockState var8 = Blocks.vine.getDefaultState()
								.withProperty(BlockVine.field_176273_b, var7 == EnumFacing.NORTH)
								.withProperty(BlockVine.field_176278_M, var7 == EnumFacing.EAST)
								.withProperty(BlockVine.field_176279_N, var7 == EnumFacing.SOUTH)
								.withProperty(BlockVine.field_176280_O, var7 == EnumFacing.WEST);
						worldIn.setBlockState(p_180709_3_, var8, 2);
						break;
					}
				}
			} else {
				p_180709_3_ = p_180709_3_.add(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), 0,
						p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
			}
		}

		return true;
	}
}
