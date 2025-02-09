package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenHellLava extends WorldGenerator {

public static final int EaZy = 1750;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150553_a;
	private final boolean field_94524_b;
	// private static final String __OBFID = "http://https://fuckuskid00000414";

	public WorldGenHellLava(final Block p_i45453_1_, final boolean p_i45453_2_) {
		field_150553_a = p_i45453_1_;
		field_94524_b = p_i45453_2_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		if (worldIn.getBlockState(p_180709_3_.offsetUp()).getBlock() != Blocks.netherrack) {
			return false;
		} else if (worldIn.getBlockState(p_180709_3_).getBlock().getMaterial() != Material.air
				&& worldIn.getBlockState(p_180709_3_).getBlock() != Blocks.netherrack) {
			return false;
		} else {
			int var4 = 0;

			if (worldIn.getBlockState(p_180709_3_.offsetWest()).getBlock() == Blocks.netherrack) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetEast()).getBlock() == Blocks.netherrack) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetNorth()).getBlock() == Blocks.netherrack) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetSouth()).getBlock() == Blocks.netherrack) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock() == Blocks.netherrack) {
				++var4;
			}

			int var5 = 0;

			if (worldIn.isAirBlock(p_180709_3_.offsetWest())) {
				++var5;
			}

			if (worldIn.isAirBlock(p_180709_3_.offsetEast())) {
				++var5;
			}

			if (worldIn.isAirBlock(p_180709_3_.offsetNorth())) {
				++var5;
			}

			if (worldIn.isAirBlock(p_180709_3_.offsetSouth())) {
				++var5;
			}

			if (worldIn.isAirBlock(p_180709_3_.offsetDown())) {
				++var5;
			}

			if (!field_94524_b && var4 == 4 && var5 == 1 || var4 == 5) {
				worldIn.setBlockState(p_180709_3_, field_150553_a.getDefaultState(), 2);
				worldIn.func_175637_a(field_150553_a, p_180709_3_, p_180709_2_);
			}

			return true;
		}
	}
}
