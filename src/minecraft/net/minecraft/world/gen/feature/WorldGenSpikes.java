package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSpikes extends WorldGenerator {

public static final int EaZy = 1765;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150520_a;
	// private static final String __OBFID = "http://https://fuckuskid00000433";

	public WorldGenSpikes(final Block p_i45464_1_) {
		field_150520_a = p_i45464_1_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		if (worldIn.isAirBlock(p_180709_3_)
				&& worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock() == field_150520_a) {
			final int var4 = p_180709_2_.nextInt(32) + 6;
			final int var5 = p_180709_2_.nextInt(4) + 1;
			int var6;
			int var7;
			int var8;
			int var9;

			for (var6 = p_180709_3_.getX() - var5; var6 <= p_180709_3_.getX() + var5; ++var6) {
				for (var7 = p_180709_3_.getZ() - var5; var7 <= p_180709_3_.getZ() + var5; ++var7) {
					var8 = var6 - p_180709_3_.getX();
					var9 = var7 - p_180709_3_.getZ();

					if (var8 * var8 + var9 * var9 <= var5 * var5 + 1
							&& worldIn.getBlockState(new BlockPos(var6, p_180709_3_.getY() - 1, var7))
									.getBlock() != field_150520_a) {
						return false;
					}
				}
			}

			for (var6 = p_180709_3_.getY(); var6 < p_180709_3_.getY() + var4 && var6 < 256; ++var6) {
				for (var7 = p_180709_3_.getX() - var5; var7 <= p_180709_3_.getX() + var5; ++var7) {
					for (var8 = p_180709_3_.getZ() - var5; var8 <= p_180709_3_.getZ() + var5; ++var8) {
						var9 = var7 - p_180709_3_.getX();
						final int var10 = var8 - p_180709_3_.getZ();

						if (var9 * var9 + var10 * var10 <= var5 * var5 + 1) {
							worldIn.setBlockState(new BlockPos(var7, var6, var8), Blocks.obsidian.getDefaultState(), 2);
						}
					}
				}
			}

			final EntityEnderCrystal var11 = new EntityEnderCrystal(worldIn);
			var11.setLocationAndAngles(p_180709_3_.getX() + 0.5F, p_180709_3_.getY() + var4, p_180709_3_.getZ() + 0.5F,
					p_180709_2_.nextFloat() * 360.0F, 0.0F);
			worldIn.spawnEntityInWorld(var11);
			worldIn.setBlockState(p_180709_3_.offsetUp(var4), Blocks.bedrock.getDefaultState(), 2);
			return true;
		} else {
			return false;
		}
	}
}
