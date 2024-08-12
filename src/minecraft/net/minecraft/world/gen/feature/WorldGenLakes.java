package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator {

public static final int EaZy = 1754;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150556_a;
	// private static final String __OBFID = "http://https://fuckuskid00000418";

	public WorldGenLakes(final Block p_i45455_1_) {
		field_150556_a = p_i45455_1_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
		for (p_180709_3_ = p_180709_3_.add(-8, 0, -8); p_180709_3_.getY() > 5
				&& worldIn.isAirBlock(p_180709_3_); p_180709_3_ = p_180709_3_.offsetDown()) {
		}

		if (p_180709_3_.getY() <= 4) {
			return false;
		} else {
			p_180709_3_ = p_180709_3_.offsetDown(4);
			final boolean[] var4 = new boolean[2048];
			final int var5 = p_180709_2_.nextInt(4) + 4;
			int var6;

			for (var6 = 0; var6 < var5; ++var6) {
				final double var7 = p_180709_2_.nextDouble() * 6.0D + 3.0D;
				final double var9 = p_180709_2_.nextDouble() * 4.0D + 2.0D;
				final double var11 = p_180709_2_.nextDouble() * 6.0D + 3.0D;
				final double var13 = p_180709_2_.nextDouble() * (16.0D - var7 - 2.0D) + 1.0D + var7 / 2.0D;
				final double var15 = p_180709_2_.nextDouble() * (8.0D - var9 - 4.0D) + 2.0D + var9 / 2.0D;
				final double var17 = p_180709_2_.nextDouble() * (16.0D - var11 - 2.0D) + 1.0D + var11 / 2.0D;

				for (int var19 = 1; var19 < 15; ++var19) {
					for (int var20 = 1; var20 < 15; ++var20) {
						for (int var21 = 1; var21 < 7; ++var21) {
							final double var22 = (var19 - var13) / (var7 / 2.0D);
							final double var24 = (var21 - var15) / (var9 / 2.0D);
							final double var26 = (var20 - var17) / (var11 / 2.0D);
							final double var28 = var22 * var22 + var24 * var24 + var26 * var26;

							if (var28 < 1.0D) {
								var4[(var19 * 16 + var20) * 8 + var21] = true;
							}
						}
					}
				}
			}

			int var8;
			int var30;
			boolean var32;

			for (var6 = 0; var6 < 16; ++var6) {
				for (var30 = 0; var30 < 16; ++var30) {
					for (var8 = 0; var8 < 8; ++var8) {
						var32 = !var4[(var6 * 16 + var30) * 8 + var8]
								&& (var6 < 15 && var4[((var6 + 1) * 16 + var30) * 8 + var8]
										|| var6 > 0 && var4[((var6 - 1) * 16 + var30) * 8 + var8]
										|| var30 < 15 && var4[(var6 * 16 + var30 + 1) * 8 + var8]
										|| var30 > 0 && var4[(var6 * 16 + var30 - 1) * 8 + var8]
										|| var8 < 7 && var4[(var6 * 16 + var30) * 8 + var8 + 1]
										|| var8 > 0 && var4[(var6 * 16 + var30) * 8 + var8 - 1]);

						if (var32) {
							final Material var10 = worldIn.getBlockState(p_180709_3_.add(var6, var8, var30)).getBlock()
									.getMaterial();

							if (var8 >= 4 && var10.isLiquid()) {
								return false;
							}

							if (var8 < 4 && !var10.isSolid() && worldIn
									.getBlockState(p_180709_3_.add(var6, var8, var30)).getBlock() != field_150556_a) {
								return false;
							}
						}
					}
				}
			}

			for (var6 = 0; var6 < 16; ++var6) {
				for (var30 = 0; var30 < 16; ++var30) {
					for (var8 = 0; var8 < 8; ++var8) {
						if (var4[(var6 * 16 + var30) * 8 + var8]) {
							worldIn.setBlockState(p_180709_3_.add(var6, var8, var30),
									var8 >= 4 ? Blocks.air.getDefaultState() : field_150556_a.getDefaultState(), 2);
						}
					}
				}
			}

			for (var6 = 0; var6 < 16; ++var6) {
				for (var30 = 0; var30 < 16; ++var30) {
					for (var8 = 4; var8 < 8; ++var8) {
						if (var4[(var6 * 16 + var30) * 8 + var8]) {
							final BlockPos var33 = p_180709_3_.add(var6, var8 - 1, var30);

							if (worldIn.getBlockState(var33).getBlock() == Blocks.dirt
									&& worldIn.getLightFor(EnumSkyBlock.SKY, p_180709_3_.add(var6, var8, var30)) > 0) {
								final BiomeGenBase var34 = worldIn.getBiomeGenForCoords(var33);

								if (var34.topBlock.getBlock() == Blocks.mycelium) {
									worldIn.setBlockState(var33, Blocks.mycelium.getDefaultState(), 2);
								} else {
									worldIn.setBlockState(var33, Blocks.grass.getDefaultState(), 2);
								}
							}
						}
					}
				}
			}

			if (field_150556_a.getMaterial() == Material.lava) {
				for (var6 = 0; var6 < 16; ++var6) {
					for (var30 = 0; var30 < 16; ++var30) {
						for (var8 = 0; var8 < 8; ++var8) {
							var32 = !var4[(var6 * 16 + var30) * 8 + var8]
									&& (var6 < 15 && var4[((var6 + 1) * 16 + var30) * 8 + var8]
											|| var6 > 0 && var4[((var6 - 1) * 16 + var30) * 8 + var8]
											|| var30 < 15 && var4[(var6 * 16 + var30 + 1) * 8 + var8]
											|| var30 > 0 && var4[(var6 * 16 + var30 - 1) * 8 + var8]
											|| var8 < 7 && var4[(var6 * 16 + var30) * 8 + var8 + 1]
											|| var8 > 0 && var4[(var6 * 16 + var30) * 8 + var8 - 1]);

							if (var32 && (var8 < 4 || p_180709_2_.nextInt(2) != 0)
									&& worldIn.getBlockState(p_180709_3_.add(var6, var8, var30)).getBlock()
											.getMaterial().isSolid()) {
								worldIn.setBlockState(p_180709_3_.add(var6, var8, var30),
										Blocks.stone.getDefaultState(), 2);
							}
						}
					}
				}
			}

			if (field_150556_a.getMaterial() == Material.water) {
				for (var6 = 0; var6 < 16; ++var6) {
					for (var30 = 0; var30 < 16; ++var30) {
						final byte var31 = 4;

						if (worldIn.func_175675_v(p_180709_3_.add(var6, var31, var30))) {
							worldIn.setBlockState(p_180709_3_.add(var6, var31, var30), Blocks.ice.getDefaultState(), 2);
						}
					}
				}
			}

			return true;
		}
	}
}
