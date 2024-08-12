package net.minecraft.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

import java.util.List;
import java.util.Random;

public class ChunkProviderHell implements IChunkProvider {

public static final int EaZy = 1729;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Is the world that the nether is getting generated. */
	private final World worldObj;
	private final boolean field_177466_i;
	private final Random hellRNG;

	/**
	 * Holds the noise used to determine whether slowsand can be generated at a
	 * location
	 */
	private double[] slowsandNoise = new double[256];
	private double[] gravelNoise = new double[256];

	/**
	 * Holds the noise used to determine whether something other than netherrack
	 * can be generated at a location
	 */
	private double[] netherrackExclusivityNoise = new double[256];
	private double[] noiseField;

	/** A NoiseGeneratorOctaves used in generating nether terrain */
	private final NoiseGeneratorOctaves netherNoiseGen1;
	private final NoiseGeneratorOctaves netherNoiseGen2;
	private final NoiseGeneratorOctaves netherNoiseGen3;

	/** Determines whether slowsand or gravel can be generated at a location */
	private final NoiseGeneratorOctaves slowsandGravelNoiseGen;

	/**
	 * Determines whether something other than nettherack can be generated at a
	 * location
	 */
	private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
	public final NoiseGeneratorOctaves netherNoiseGen6;
	public final NoiseGeneratorOctaves netherNoiseGen7;
	private final WorldGenFire field_177470_t = new WorldGenFire();
	private final WorldGenGlowStone1 field_177469_u = new WorldGenGlowStone1();
	private final WorldGenGlowStone2 field_177468_v = new WorldGenGlowStone2();
	private final WorldGenerator field_177467_w;
	private final WorldGenHellLava field_177473_x;
	private final WorldGenHellLava field_177472_y;
	private final GeneratorBushFeature field_177471_z;
	private final GeneratorBushFeature field_177465_A;
	private final MapGenNetherBridge genNetherBridge;
	private final MapGenBase netherCaveGenerator;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
	// private static final String __OBFID = "http://https://fuckuskid00000392";

	public ChunkProviderHell(final World worldIn, final boolean p_i45637_2_, final long p_i45637_3_) {
		field_177467_w = new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 14,
				BlockHelper.forBlock(Blocks.netherrack));
		field_177473_x = new WorldGenHellLava(Blocks.flowing_lava, true);
		field_177472_y = new WorldGenHellLava(Blocks.flowing_lava, false);
		field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
		field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
		genNetherBridge = new MapGenNetherBridge();
		netherCaveGenerator = new MapGenCavesHell();
		worldObj = worldIn;
		field_177466_i = p_i45637_2_;
		hellRNG = new Random(p_i45637_3_);
		netherNoiseGen1 = new NoiseGeneratorOctaves(hellRNG, 16);
		netherNoiseGen2 = new NoiseGeneratorOctaves(hellRNG, 16);
		netherNoiseGen3 = new NoiseGeneratorOctaves(hellRNG, 8);
		slowsandGravelNoiseGen = new NoiseGeneratorOctaves(hellRNG, 4);
		netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(hellRNG, 4);
		netherNoiseGen6 = new NoiseGeneratorOctaves(hellRNG, 10);
		netherNoiseGen7 = new NoiseGeneratorOctaves(hellRNG, 16);
	}

	public void func_180515_a(final int p_180515_1_, final int p_180515_2_, final ChunkPrimer p_180515_3_) {
		final byte var4 = 4;
		final byte var5 = 32;
		final int var6 = var4 + 1;
		final byte var7 = 17;
		final int var8 = var4 + 1;
		noiseField = initializeNoiseField(noiseField, p_180515_1_ * var4, 0, p_180515_2_ * var4, var6, var7, var8);

		for (int var9 = 0; var9 < var4; ++var9) {
			for (int var10 = 0; var10 < var4; ++var10) {
				for (int var11 = 0; var11 < 16; ++var11) {
					final double var12 = 0.125D;
					double var14 = noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
					double var16 = noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
					double var18 = noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
					double var20 = noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
					final double var22 = (noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14)
							* var12;
					final double var24 = (noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16)
							* var12;
					final double var26 = (noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18)
							* var12;
					final double var28 = (noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20)
							* var12;

					for (int var30 = 0; var30 < 8; ++var30) {
						final double var31 = 0.25D;
						double var33 = var14;
						double var35 = var16;
						final double var37 = (var18 - var14) * var31;
						final double var39 = (var20 - var16) * var31;

						for (int var41 = 0; var41 < 4; ++var41) {
							final double var42 = 0.25D;
							double var44 = var33;
							final double var46 = (var35 - var33) * var42;

							for (int var48 = 0; var48 < 4; ++var48) {
								IBlockState var49 = null;

								if (var11 * 8 + var30 < var5) {
									var49 = Blocks.lava.getDefaultState();
								}

								if (var44 > 0.0D) {
									var49 = Blocks.netherrack.getDefaultState();
								}

								final int var50 = var41 + var9 * 4;
								final int var51 = var30 + var11 * 8;
								final int var52 = var48 + var10 * 4;
								p_180515_3_.setBlockState(var50, var51, var52, var49);
								var44 += var46;
							}

							var33 += var37;
							var35 += var39;
						}

						var14 += var22;
						var16 += var24;
						var18 += var26;
						var20 += var28;
					}
				}
			}
		}
	}

	public void func_180516_b(final int p_180516_1_, final int p_180516_2_, final ChunkPrimer p_180516_3_) {
		final byte var4 = 64;
		final double var5 = 0.03125D;
		slowsandNoise = slowsandGravelNoiseGen.generateNoiseOctaves(slowsandNoise, p_180516_1_ * 16, p_180516_2_ * 16,
				0, 16, 16, 1, var5, var5, 1.0D);
		gravelNoise = slowsandGravelNoiseGen.generateNoiseOctaves(gravelNoise, p_180516_1_ * 16, 109, p_180516_2_ * 16,
				16, 1, 16, var5, 1.0D, var5);
		netherrackExclusivityNoise = netherrackExculsivityNoiseGen.generateNoiseOctaves(netherrackExclusivityNoise,
				p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, var5 * 2.0D, var5 * 2.0D, var5 * 2.0D);

		for (int var7 = 0; var7 < 16; ++var7) {
			for (int var8 = 0; var8 < 16; ++var8) {
				final boolean var9 = slowsandNoise[var7 + var8 * 16] + hellRNG.nextDouble() * 0.2D > 0.0D;
				final boolean var10 = gravelNoise[var7 + var8 * 16] + hellRNG.nextDouble() * 0.2D > 0.0D;
				final int var11 = (int) (netherrackExclusivityNoise[var7 + var8 * 16] / 3.0D + 3.0D
						+ hellRNG.nextDouble() * 0.25D);
				int var12 = -1;
				IBlockState var13 = Blocks.netherrack.getDefaultState();
				IBlockState var14 = Blocks.netherrack.getDefaultState();

				for (int var15 = 127; var15 >= 0; --var15) {
					if (var15 < 127 - hellRNG.nextInt(5) && var15 > hellRNG.nextInt(5)) {
						final IBlockState var16 = p_180516_3_.getBlockState(var8, var15, var7);

						if (var16.getBlock() != null && var16.getBlock().getMaterial() != Material.air) {
							if (var16.getBlock() == Blocks.netherrack) {
								if (var12 == -1) {
									if (var11 <= 0) {
										var13 = null;
										var14 = Blocks.netherrack.getDefaultState();
									} else if (var15 >= var4 - 4 && var15 <= var4 + 1) {
										var13 = Blocks.netherrack.getDefaultState();
										var14 = Blocks.netherrack.getDefaultState();

										if (var10) {
											var13 = Blocks.gravel.getDefaultState();
											var14 = Blocks.netherrack.getDefaultState();
										}

										if (var9) {
											var13 = Blocks.soul_sand.getDefaultState();
											var14 = Blocks.soul_sand.getDefaultState();
										}
									}

									if (var15 < var4
											&& (var13 == null || var13.getBlock().getMaterial() == Material.air)) {
										var13 = Blocks.lava.getDefaultState();
									}

									var12 = var11;

									if (var15 >= var4 - 1) {
										p_180516_3_.setBlockState(var8, var15, var7, var13);
									} else {
										p_180516_3_.setBlockState(var8, var15, var7, var14);
									}
								} else if (var12 > 0) {
									--var12;
									p_180516_3_.setBlockState(var8, var15, var7, var14);
								}
							}
						} else {
							var12 = -1;
						}
					} else {
						p_180516_3_.setBlockState(var8, var15, var7, Blocks.bedrock.getDefaultState());
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
		hellRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
		final ChunkPrimer var3 = new ChunkPrimer();
		func_180515_a(p_73154_1_, p_73154_2_, var3);
		func_180516_b(p_73154_1_, p_73154_2_, var3);
		netherCaveGenerator.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);

		if (field_177466_i) {
			genNetherBridge.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
		}

		final Chunk var4 = new Chunk(worldObj, var3, p_73154_1_, p_73154_2_);
		final BiomeGenBase[] var5 = worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null,
				p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		final byte[] var6 = var4.getBiomeArray();

		for (int var7 = 0; var7 < var6.length; ++var7) {
			var6[var7] = (byte) var5[var7].biomeID;
		}

		var4.resetRelightChecks();
		return var4;
	}

	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the
	 * [empty] noise array, the position, and the size.
	 */
	private double[] initializeNoiseField(double[] p_73164_1_, final int p_73164_2_, final int p_73164_3_,
			final int p_73164_4_, final int p_73164_5_, final int p_73164_6_, final int p_73164_7_) {
		if (p_73164_1_ == null) {
			p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
		}

		final double var8 = 684.412D;
		final double var10 = 2053.236D;
		noiseData4 = netherNoiseGen6.generateNoiseOctaves(noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1,
				p_73164_7_, 1.0D, 0.0D, 1.0D);
		noiseData5 = netherNoiseGen7.generateNoiseOctaves(noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1,
				p_73164_7_, 100.0D, 0.0D, 100.0D);
		noiseData1 = netherNoiseGen3.generateNoiseOctaves(noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_,
				p_73164_6_, p_73164_7_, var8 / 80.0D, var10 / 60.0D, var8 / 80.0D);
		noiseData2 = netherNoiseGen1.generateNoiseOctaves(noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_,
				p_73164_6_, p_73164_7_, var8, var10, var8);
		noiseData3 = netherNoiseGen2.generateNoiseOctaves(noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_,
				p_73164_6_, p_73164_7_, var8, var10, var8);
		int var12 = 0;
		final double[] var13 = new double[p_73164_6_];
		int var14;

		for (var14 = 0; var14 < p_73164_6_; ++var14) {
			var13[var14] = Math.cos(var14 * Math.PI * 6.0D / p_73164_6_) * 2.0D;
			double var15 = var14;

			if (var14 > p_73164_6_ / 2) {
				var15 = p_73164_6_ - 1 - var14;
			}

			if (var15 < 4.0D) {
				var15 = 4.0D - var15;
				var13[var14] -= var15 * var15 * var15 * 10.0D;
			}
		}

		for (var14 = 0; var14 < p_73164_5_; ++var14) {
			for (int var31 = 0; var31 < p_73164_7_; ++var31) {
				final double var16 = 0.0D;

				for (int var18 = 0; var18 < p_73164_6_; ++var18) {
					double var19 = 0.0D;
					final double var21 = var13[var18];
					final double var23 = noiseData2[var12] / 512.0D;
					final double var25 = noiseData3[var12] / 512.0D;
					final double var27 = (noiseData1[var12] / 10.0D + 1.0D) / 2.0D;

					if (var27 < 0.0D) {
						var19 = var23;
					} else if (var27 > 1.0D) {
						var19 = var25;
					} else {
						var19 = var23 + (var25 - var23) * var27;
					}

					var19 -= var21;
					double var29;

					if (var18 > p_73164_6_ - 4) {
						var29 = (var18 - (p_73164_6_ - 4)) / 3.0F;
						var19 = var19 * (1.0D - var29) + -10.0D * var29;
					}

					if (var18 < var16) {
						var29 = (var16 - var18) / 4.0D;
						var29 = MathHelper.clamp_double(var29, 0.0D, 1.0D);
						var19 = var19 * (1.0D - var29) + -10.0D * var29;
					}

					p_73164_1_[var12] = var19;
					++var12;
				}
			}
		}

		return p_73164_1_;
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
		final ChunkCoordIntPair var5 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
		genNetherBridge.func_175794_a(worldObj, hellRNG, var5);
		int var6;

		for (var6 = 0; var6 < 8; ++var6) {
			field_177472_y.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(120) + 4, hellRNG.nextInt(16) + 8));
		}

		for (var6 = 0; var6 < hellRNG.nextInt(hellRNG.nextInt(10) + 1) + 1; ++var6) {
			field_177470_t.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(120) + 4, hellRNG.nextInt(16) + 8));
		}

		for (var6 = 0; var6 < hellRNG.nextInt(hellRNG.nextInt(10) + 1); ++var6) {
			field_177469_u.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(120) + 4, hellRNG.nextInt(16) + 8));
		}

		for (var6 = 0; var6 < 10; ++var6) {
			field_177468_v.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(128), hellRNG.nextInt(16) + 8));
		}

		if (hellRNG.nextBoolean()) {
			field_177471_z.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(128), hellRNG.nextInt(16) + 8));
		}

		if (hellRNG.nextBoolean()) {
			field_177465_A.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16) + 8, hellRNG.nextInt(128), hellRNG.nextInt(16) + 8));
		}

		for (var6 = 0; var6 < 16; ++var6) {
			field_177467_w.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16), hellRNG.nextInt(108) + 10, hellRNG.nextInt(16)));
		}

		for (var6 = 0; var6 < 16; ++var6) {
			field_177473_x.generate(worldObj, hellRNG,
					var4.add(hellRNG.nextInt(16), hellRNG.nextInt(108) + 10, hellRNG.nextInt(16)));
		}

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
		return "HellRandomLevelSource";
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		if (p_177458_1_ == EnumCreatureType.MONSTER) {
			if (genNetherBridge.func_175795_b(p_177458_2_)) {
				return genNetherBridge.getSpawnList();
			}

			if (genNetherBridge.func_175796_a(worldObj, p_177458_2_)
					&& worldObj.getBlockState(p_177458_2_.offsetDown()).getBlock() == Blocks.nether_brick) {
				return genNetherBridge.getSpawnList();
			}
		}

		final BiomeGenBase var3 = worldObj.getBiomeGenForCoords(p_177458_2_);
		return var3.getSpawnableList(p_177458_1_);
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
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
		genNetherBridge.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer) null);
	}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}
}
