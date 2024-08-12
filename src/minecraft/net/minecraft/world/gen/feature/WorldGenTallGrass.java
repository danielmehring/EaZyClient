package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator {

public static final int EaZy = 1769;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IBlockState field_175907_a;
	// private static final String __OBFID = "http://https://fuckuskid00000437";

	public WorldGenTallGrass(final BlockTallGrass.EnumType p_i45629_1_) {
		field_175907_a = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.field_176497_a, p_i45629_1_);
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
		Block var4;

		while (((var4 = worldIn.getBlockState(p_180709_3_).getBlock()).getMaterial() == Material.air
				|| var4.getMaterial() == Material.leaves) && p_180709_3_.getY() > 0) {
			p_180709_3_ = p_180709_3_.offsetDown();
		}

		for (int var5 = 0; var5 < 128; ++var5) {
			final BlockPos var6 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8),
					p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));

			if (worldIn.isAirBlock(var6) && Blocks.tallgrass.canBlockStay(worldIn, var6, field_175907_a)) {
				worldIn.setBlockState(var6, field_175907_a, 2);
			}
		}

		return true;
	}
}
