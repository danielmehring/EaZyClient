package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator {

public static final int EaZy = 1755;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150521_a;
	// private static final String __OBFID = "http://https://fuckuskid00000434";

	public WorldGenLiquids(final Block p_i45465_1_) {
		field_150521_a = p_i45465_1_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		if (worldIn.getBlockState(p_180709_3_.offsetUp()).getBlock() != Blocks.stone) {
			return false;
		} else if (worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock() != Blocks.stone) {
			return false;
		} else if (worldIn.getBlockState(p_180709_3_).getBlock().getMaterial() != Material.air
				&& worldIn.getBlockState(p_180709_3_).getBlock() != Blocks.stone) {
			return false;
		} else {
			int var4 = 0;

			if (worldIn.getBlockState(p_180709_3_.offsetWest()).getBlock() == Blocks.stone) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetEast()).getBlock() == Blocks.stone) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetNorth()).getBlock() == Blocks.stone) {
				++var4;
			}

			if (worldIn.getBlockState(p_180709_3_.offsetSouth()).getBlock() == Blocks.stone) {
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

			if (var4 == 3 && var5 == 1) {
				worldIn.setBlockState(p_180709_3_, field_150521_a.getDefaultState(), 2);
				worldIn.func_175637_a(field_150521_a, p_180709_3_, p_180709_2_);
			}

			return true;
		}
	}
}
