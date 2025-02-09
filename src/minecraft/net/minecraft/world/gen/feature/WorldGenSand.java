package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenSand extends WorldGenerator {

public static final int EaZy = 1762;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150517_a;

	/** The maximum radius used when generating a patch of blocks. */
	private final int radius;
	// private static final String __OBFID = "http://https://fuckuskid00000431";

	public WorldGenSand(final Block p_i45462_1_, final int p_i45462_2_) {
		field_150517_a = p_i45462_1_;
		radius = p_i45462_2_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		if (worldIn.getBlockState(p_180709_3_).getBlock().getMaterial() != Material.water) {
			return false;
		} else {
			final int var4 = p_180709_2_.nextInt(radius - 2) + 2;
			final byte var5 = 2;

			for (int var6 = p_180709_3_.getX() - var4; var6 <= p_180709_3_.getX() + var4; ++var6) {
				for (int var7 = p_180709_3_.getZ() - var4; var7 <= p_180709_3_.getZ() + var4; ++var7) {
					final int var8 = var6 - p_180709_3_.getX();
					final int var9 = var7 - p_180709_3_.getZ();

					if (var8 * var8 + var9 * var9 <= var4 * var4) {
						for (int var10 = p_180709_3_.getY() - var5; var10 <= p_180709_3_.getY() + var5; ++var10) {
							final BlockPos var11 = new BlockPos(var6, var10, var7);
							final Block var12 = worldIn.getBlockState(var11).getBlock();

							if (var12 == Blocks.dirt || var12 == Blocks.grass) {
								worldIn.setBlockState(var11, field_150517_a.getDefaultState(), 2);
							}
						}
					}
				}
			}

			return true;
		}
	}
}
