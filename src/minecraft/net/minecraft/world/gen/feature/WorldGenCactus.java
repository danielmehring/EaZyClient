package net.minecraft.world.gen.feature;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator {

public static final int EaZy = 1736;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000404";

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		for (int var4 = 0; var4 < 10; ++var4) {
			final BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8),
					p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

			if (worldIn.isAirBlock(var5)) {
				final int var6 = 1 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1);

				for (int var7 = 0; var7 < var6; ++var7) {
					if (Blocks.cactus.canBlockStay(worldIn, var5)) {
						worldIn.setBlockState(var5.offsetUp(var7), Blocks.cactus.getDefaultState(), 2);
					}
				}
			}
		}

		return true;
	}
}
