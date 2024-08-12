package net.minecraft.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Random;

public class ChunkProviderEnd implements IChunkProvider {

public static final int EaZy = 1726;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Random endRNG;
	private final NoiseGeneratorOctaves noiseGen1;
	private final NoiseGeneratorOctaves noiseGen2;
	private final NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	private final World endWorld;
	private double[] densities;

	/** The biomes that are used to generate the chunk */
	private BiomeGenBase[] biomesForGeneration;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
	// private static final String __OBFID = "http://https://fuckuskid00000397";

	public ChunkProviderEnd(final World worldIn, final long p_i2007_2_) {
		endWorld = worldIn;
		endRNG = new Random(p_i2007_2_);
		noiseGen1 = new NoiseGeneratorOctaves(endRNG, 16);
		noiseGen2 = new NoiseGeneratorOctaves(endRNG, 16);
		noiseGen3 = new NoiseGeneratorOctaves(endRNG, 8);
		noiseGen4 = new NoiseGeneratorOctaves(endRNG, 10);
		noiseGen5 = new NoiseGeneratorOctaves(endRNG, 16);
	}

	public void func_180520_a(final int p_180520_1_, final int p_180520_2_, final ChunkPrimer p_180520_3_) {
		final byte var4 = 2;
		final int var5 = var4 + 1;
		final byte var6 = 33;
		final int var7 = var4 + 1;
		densities = initializeNoiseField(densities, p_180520_1_ * var4, 0, p_180520_2_ * var4, var5, var6, var7);

		for (int var8 = 0; var8 < var4; ++var8) {
			for (int var9 = 0; var9 < var4; ++var9) {
				for (int var10 = 0; var10 < 32; ++var10) {
					final double var11 = 0.25D;
					double var13 = densities[((var8 + 0) * var7 + var9 + 0) * var6 + var10 + 0];
					double var15 = densities[((var8 + 0) * var7 + var9 + 1) * var6 + var10 + 0];
					double var17 = densities[((var8 + 1) * var7 + var9 + 0) * var6 + var10 + 0];
					double var19 = densities[((var8 + 1) * var7 + var9 + 1) * var6 + var10 + 0];
					final double var21 = (densities[((var8 + 0) * var7 + var9 + 0) * var6 + var10 + 1] - var13) * var11;
					final double var23 = (densities[((var8 + 0) * var7 + var9 + 1) * var6 + var10 + 1] - var15) * var11;
					final double var25 = (densities[((var8 + 1) * var7 + var9 + 0) * var6 + var10 + 1] - var17) * var11;
					final double var27 = (densities[((var8 + 1) * var7 + var9 + 1) * var6 + var10 + 1] - var19) * var11;

					for (int var29 = 0; var29 < 4; ++var29) {
						final double var30 = 0.125D;
						double var32 = var13;
						double var34 = var15;
						final double var36 = (var17 - var13) * var30;
						final double var38 = (var19 - var15) * var30;

						for (int var40 = 0; var40 < 8; ++var40) {
							final double var41 = 0.125D;
							double var43 = var32;
							final double var45 = (var34 - var32) * var41;

							for (int var47 = 0; var47 < 8; ++var47) {
								IBlockState var48 = null;

								if (var43 > 0.0D) {
									var48 = Blocks.end_stone.getDefaultState();
								}

								final int var49 = var40 + var8 * 8;
								final int var50 = var29 + var10 * 4;
								final int var51 = var47 + var9 * 8;
								p_180520_3_.setBlockState(var49, var50, var51, var48);
								var43 += var45;
							}

							var32 += var36;
							var34 += var38;
						}

						var13 += var21;
						var15 += var23;
						var17 += var25;
						var19 += var27;
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public void func_180519_a(final ChunkPrimer p_180519_1_) {
		for (int var2 = 0; var2 < 16; ++var2) {
			for (int var3 = 0; var3 < 16; ++var3) {
				final byte var4 = 1;
				int var5 = -1;
				IBlockState var6 = Blocks.end_stone.getDefaultState();
				IBlockState var7 = Blocks.end_stone.getDefaultState();

				for (int var8 = 127; var8 >= 0; --var8) {
					final IBlockState var9 = p_180519_1_.getBlockState(var2, var8, var3);

					if (var9.getBlock().getMaterial() == Material.air) {
						var5 = -1;
					} else if (var9.getBlock() == Blocks.stone) {
						if (var5 == -1) {
							if (var4 <= 0) {
								var6 = Blocks.air.getDefaultState();
								var7 = Blocks.end_stone.getDefaultState();
							}

							var5 = var4;

							if (var8 >= 0) {
								p_180519_1_.setBlockState(var2, var8, var3, var6);
							} else {
								p_180519_1_.setBlockState(var2, var8, var3, var7);
							}
						} else if (var5 > 0) {
							--var5;
							p_180519_1_.setBlockState(var2, var8, var3, var7);
						}
					}
				}
			}
		}
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
		endRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
		final ChunkPrimer var3 = new ChunkPrimer();
		biomesForGeneration = endWorld.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration,
				p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		func_180520_a(p_73154_1_, p_73154_2_, var3);
		func_180519_a(var3);
		final Chunk var4 = new Chunk(endWorld, var3, p_73154_1_, p_73154_2_);
		final byte[] var5 = var4.getBiomeArray();

		for (int var6 = 0; var6 < var5.length; ++var6) {
			var5[var6] = (byte) biomesForGeneration[var6].biomeID;
		}

		var4.generateSkylightMap();
		return var4;
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the
	 * [empty] noise array, the position, and the size.
	 */
	private double[] initializeNoiseField(double[] p_73187_1_, final int p_73187_2_, final int p_73187_3_,
			final int p_73187_4_, final int p_73187_5_, final int p_73187_6_, final int p_73187_7_) {
		if (p_73187_1_ == null) {
			p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
		}

		double var8 = 684.412D;
		final double var10 = 684.412D;
		noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D,
				1.121D, 0.5D);
		noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D,
				200.0D, 0.5D);
		var8 *= 2.0D;
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_,
				p_73187_6_, p_73187_7_, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_,
				p_73187_6_, p_73187_7_, var8, var10, var8);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_,
				p_73187_6_, p_73187_7_, var8, var10, var8);
		int var12 = 0;

		for (int var13 = 0; var13 < p_73187_5_; ++var13) {
			for (int var14 = 0; var14 < p_73187_7_; ++var14) {
				final float var15 = (var13 + p_73187_2_) / 1.0F;
				final float var16 = (var14 + p_73187_4_) / 1.0F;
				float var17 = 100.0F - MathHelper.sqrt_float(var15 * var15 + var16 * var16) * 8.0F;

				if (var17 > 80.0F) {
					var17 = 80.0F;
				}

				if (var17 < -100.0F) {
					var17 = -100.0F;
				}

				for (int var18 = 0; var18 < p_73187_6_; ++var18) {
					double var19 = 0.0D;
					final double var21 = noiseData2[var12] / 512.0D;
					final double var23 = noiseData3[var12] / 512.0D;
					final double var25 = (noiseData1[var12] / 10.0D + 1.0D) / 2.0D;

					if (var25 < 0.0D) {
						var19 = var21;
					} else if (var25 > 1.0D) {
						var19 = var23;
					} else {
						var19 = var21 + (var23 - var21) * var25;
					}

					var19 -= 8.0D;
					var19 += var17;
					byte var27 = 2;
					double var28;

					if (var18 > p_73187_6_ / 2 - var27) {
						var28 = (var18 - (p_73187_6_ / 2 - var27)) / 64.0F;
						var28 = MathHelper.clamp_double(var28, 0.0D, 1.0D);
						var19 = var19 * (1.0D - var28) + -3000.0D * var28;
					}

					var27 = 8;

					if (var18 < var27) {
						var28 = (var27 - var18) / (var27 - 1.0F);
						var19 = var19 * (1.0D - var28) + -30.0D * var28;
					}

					p_73187_1_[var12] = var19;
					++var12;
				}
			}
		}

		return p_73187_1_;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
		BlockFalling.fallInstantly = true;
		final BlockPos var4 = new BlockPos(p_73153_2_ * 16, 0, p_73153_3_ * 16);
		endWorld.getBiomeGenForCoords(var4.add(16, 0, 16)).func_180624_a(endWorld, endWorld.rand, var4);
		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean func_177460_a(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_,
			final int p_177460_4_) {
		return false;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If
	 * passed false, save up to two chunks. Return true if all chunks have been
	 * saved.
	 */
	@Override
	public boolean saveChunks(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
		return true;
	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently unimplemented.
	 */
	@Override
	public void saveExtraData() {}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "RandomLevelSource";
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		return endWorld.getBiomeGenForCoords(p_177458_2_).getSpawnableList(p_177458_1_);
	}

	@Override
	public BlockPos func_180513_a(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}
}
