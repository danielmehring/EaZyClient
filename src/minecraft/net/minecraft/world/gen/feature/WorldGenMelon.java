package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMelon extends WorldGenerator {

public static final int EaZy = 1758;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000424";

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		for (int var4 = 0; var4 < 64; ++var4) {
			final BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8),
					p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

			if (Blocks.melon_block.canPlaceBlockAt(worldIn, var5)
					&& worldIn.getBlockState(var5.offsetDown()).getBlock() == Blocks.grass) {
				worldIn.setBlockState(var5, Blocks.melon_block.getDefaultState(), 2);
			}
		}

		return true;
	}
}
