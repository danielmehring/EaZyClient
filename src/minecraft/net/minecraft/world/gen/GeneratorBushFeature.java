package net.minecraft.world.gen;

import net.minecraft.block.BlockBush;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GeneratorBushFeature extends WorldGenerator {

public static final int EaZy = 1775;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockBush field_175908_a;
	// private static final String __OBFID = "http://https://fuckuskid00002000";

	public GeneratorBushFeature(final BlockBush p_i45633_1_) {
		field_175908_a = p_i45633_1_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		for (int var4 = 0; var4 < 64; ++var4) {
			final BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8),
					p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

			if (worldIn.isAirBlock(var5) && (!worldIn.provider.getHasNoSky() || var5.getY() < 255)
					&& field_175908_a.canBlockStay(worldIn, var5, field_175908_a.getDefaultState())) {
				worldIn.setBlockState(var5, field_175908_a.getDefaultState(), 2);
			}
		}

		return true;
	}
}
