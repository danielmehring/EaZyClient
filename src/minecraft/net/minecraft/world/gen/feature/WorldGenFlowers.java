package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator {

public static final int EaZy = 1746;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockFlower flower;
	private IBlockState field_175915_b;
	// private static final String __OBFID = "http://https://fuckuskid00000410";

	public WorldGenFlowers(final BlockFlower p_i45632_1_, final BlockFlower.EnumFlowerType p_i45632_2_) {
		setGeneratedBlock(p_i45632_1_, p_i45632_2_);
	}

	public void setGeneratedBlock(final BlockFlower p_175914_1_, final BlockFlower.EnumFlowerType p_175914_2_) {
		flower = p_175914_1_;
		field_175915_b = p_175914_1_.getDefaultState().withProperty(p_175914_1_.func_176494_l(), p_175914_2_);
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		for (int var4 = 0; var4 < 64; ++var4) {
			final BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8),
					p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

			if (worldIn.isAirBlock(var5) && (!worldIn.provider.getHasNoSky() || var5.getY() < 255)
					&& flower.canBlockStay(worldIn, var5, field_175915_b)) {
				worldIn.setBlockState(var5, field_175915_b, 2);
			}
		}

		return true;
	}
}
