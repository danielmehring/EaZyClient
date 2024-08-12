package net.minecraft.world.gen.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

import java.util.concurrent.Callable;

public abstract class GenLayer {

public static final int EaZy = 1776;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** seed from World#getWorldSeed that is used in the LCG prng */
	private long worldGenSeed;

	/** parent GenLayer that was provided via the constructor */
	protected GenLayer parent;

	/**
	 * final part of the LCG prng that uses the chunk X, Z coords along with the
	 * other two seeds to generate pseudorandom numbers
	 */
	private long chunkSeed;

	/** base seed to the LCG prng provided via the constructor */
	protected long baseSeed;
	// private static final String __OBFID = "http://https://fuckuskid00000559";

	public static GenLayer[] func_180781_a(final long p_180781_0_, final WorldType p_180781_2_,
			final String p_180781_3_) {
		final GenLayerIsland var4 = new GenLayerIsland(1L);
		final GenLayerFuzzyZoom var13 = new GenLayerFuzzyZoom(2000L, var4);
		GenLayerAddIsland var14 = new GenLayerAddIsland(1L, var13);
		GenLayerZoom var15 = new GenLayerZoom(2001L, var14);
		var14 = new GenLayerAddIsland(2L, var15);
		var14 = new GenLayerAddIsland(50L, var14);
		var14 = new GenLayerAddIsland(70L, var14);
		final GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2L, var14);
		final GenLayerAddSnow var17 = new GenLayerAddSnow(2L, var16);
		var14 = new GenLayerAddIsland(3L, var17);
		GenLayerEdge var18 = new GenLayerEdge(2L, var14, GenLayerEdge.Mode.COOL_WARM);
		var18 = new GenLayerEdge(2L, var18, GenLayerEdge.Mode.HEAT_ICE);
		var18 = new GenLayerEdge(3L, var18, GenLayerEdge.Mode.SPECIAL);
		var15 = new GenLayerZoom(2002L, var18);
		var15 = new GenLayerZoom(2003L, var15);
		var14 = new GenLayerAddIsland(4L, var15);
		final GenLayerAddMushroomIsland var19 = new GenLayerAddMushroomIsland(5L, var14);
		final GenLayerDeepOcean var20 = new GenLayerDeepOcean(4L, var19);
		final GenLayer var21 = GenLayerZoom.magnify(1000L, var20, 0);
		ChunkProviderSettings var5 = null;
		int var6 = 4;
		int var7 = var6;

		if (p_180781_2_ == WorldType.CUSTOMIZED && p_180781_3_.length() > 0) {
			var5 = ChunkProviderSettings.Factory.func_177865_a(p_180781_3_).func_177864_b();
			var6 = var5.field_177780_G;
			var7 = var5.field_177788_H;
		}

		if (p_180781_2_ == WorldType.LARGE_BIOMES) {
			var6 = 6;
		}

		GenLayer var8 = GenLayerZoom.magnify(1000L, var21, 0);
		final GenLayerRiverInit var22 = new GenLayerRiverInit(100L, var8);
		final GenLayerBiome var9 = new GenLayerBiome(200L, var21, p_180781_2_, p_180781_3_);
		final GenLayer var25 = GenLayerZoom.magnify(1000L, var9, 2);
		final GenLayerBiomeEdge var26 = new GenLayerBiomeEdge(1000L, var25);
		final GenLayer var10 = GenLayerZoom.magnify(1000L, var22, 2);
		final GenLayerHills var27 = new GenLayerHills(1000L, var26, var10);
		var8 = GenLayerZoom.magnify(1000L, var22, 2);
		var8 = GenLayerZoom.magnify(1000L, var8, var7);
		final GenLayerRiver var23 = new GenLayerRiver(1L, var8);
		final GenLayerSmooth var24 = new GenLayerSmooth(1000L, var23);
		Object var28 = new GenLayerRareBiome(1001L, var27);

		for (int var11 = 0; var11 < var6; ++var11) {
			var28 = new GenLayerZoom((long) (1000 + var11), (GenLayer) var28);

			if (var11 == 0) {
				var28 = new GenLayerAddIsland(3L, (GenLayer) var28);
			}

			if (var11 == 1 || var6 == 1) {
				var28 = new GenLayerShore(1000L, (GenLayer) var28);
			}
		}

		final GenLayerSmooth var29 = new GenLayerSmooth(1000L, (GenLayer) var28);
		final GenLayerRiverMix var30 = new GenLayerRiverMix(100L, var29, var24);
		final GenLayerVoronoiZoom var12 = new GenLayerVoronoiZoom(10L, var30);
		var30.initWorldGenSeed(p_180781_0_);
		var12.initWorldGenSeed(p_180781_0_);
		return new GenLayer[] { var30, var12, var30 };
	}

	public GenLayer(final long p_i2125_1_) {
		baseSeed = p_i2125_1_;
		baseSeed *= baseSeed * 6364136223846793005L + 1442695040888963407L;
		baseSeed += p_i2125_1_;
		baseSeed *= baseSeed * 6364136223846793005L + 1442695040888963407L;
		baseSeed += p_i2125_1_;
		baseSeed *= baseSeed * 6364136223846793005L + 1442695040888963407L;
		baseSeed += p_i2125_1_;
	}

	/**
	 * Initialize layer's local worldGenSeed based on its own baseSeed and the
	 * world's global seed (passed in as an argument).
	 */
	public void initWorldGenSeed(final long p_75905_1_) {
		worldGenSeed = p_75905_1_;

		if (parent != null) {
			parent.initWorldGenSeed(p_75905_1_);
		}

		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
		worldGenSeed *= worldGenSeed * 6364136223846793005L + 1442695040888963407L;
		worldGenSeed += baseSeed;
	}

	/**
	 * Initialize layer's current chunkSeed based on the local worldGenSeed and
	 * the (x,z) chunk coordinates.
	 */
	public void initChunkSeed(final long p_75903_1_, final long p_75903_3_) {
		chunkSeed = worldGenSeed;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += p_75903_1_;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += p_75903_3_;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += p_75903_1_;
		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += p_75903_3_;
	}

	/**
	 * returns a LCG pseudo random number from [0, x). Args: int x
	 */
	protected int nextInt(final int p_75902_1_) {
		int var2 = (int) ((chunkSeed >> 24) % p_75902_1_);

		if (var2 < 0) {
			var2 += p_75902_1_;
		}

		chunkSeed *= chunkSeed * 6364136223846793005L + 1442695040888963407L;
		chunkSeed += worldGenSeed;
		return var2;
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be
	 * interpreted as temperatures, rainfall amounts, or biomeList[] indices
	 * based on the particular GenLayer subclass.
	 */
	public abstract int[] getInts(int var1, int var2, int var3, int var4);

	protected static boolean biomesEqualOrMesaPlateau(final int biomeIDA, final int biomeIDB) {
		if (biomeIDA == biomeIDB) {
			return true;
		} else if (biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDA != BiomeGenBase.mesaPlateau.biomeID) {
			final BiomeGenBase var2 = BiomeGenBase.getBiome(biomeIDA);
			final BiomeGenBase var3 = BiomeGenBase.getBiome(biomeIDB);

			try {
				return var2 != null && var3 != null ? var2.isEqualTo(var3) : false;
			} catch (final Throwable var7) {
				final CrashReport var5 = CrashReport.makeCrashReport(var7, "Comparing biomes");
				final CrashReportCategory var6 = var5.makeCategory("Biomes being compared");
				var6.addCrashSection("Biome A ID", biomeIDA);
				var6.addCrashSection("Biome B ID", biomeIDB);
				var6.addCrashSectionCallable("Biome A", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00000560";
					@Override
					public String call() {
						return String.valueOf(var2);
					}
				});
				var6.addCrashSectionCallable("Biome B", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00000561";
					@Override
					public String call() {
						return String.valueOf(var3);
					}
				});
				throw new ReportedException(var5);
			}
		} else {
			return biomeIDB == BiomeGenBase.mesaPlateau_F.biomeID || biomeIDB == BiomeGenBase.mesaPlateau.biomeID;
		}
	}

	/**
	 * returns true if the biomeId is one of the various ocean biomes.
	 */
	protected static boolean isBiomeOceanic(final int p_151618_0_) {
		return p_151618_0_ == BiomeGenBase.ocean.biomeID || p_151618_0_ == BiomeGenBase.deepOcean.biomeID
				|| p_151618_0_ == BiomeGenBase.frozenOcean.biomeID;
	}

	/**
	 * selects a random integer from a set of provided integers
	 */
	protected int selectRandom(final int... p_151619_1_) {
		return p_151619_1_[nextInt(p_151619_1_.length)];
	}

	/**
	 * returns the most frequently occurring number of the set, or a random
	 * number from those provided
	 */
	protected int selectModeOrRandom(final int p_151617_1_, final int p_151617_2_, final int p_151617_3_,
			final int p_151617_4_) {
		return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_
				: p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_
						: p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_
								: p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_
										: p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_
												: p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_
														: p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_
																? p_151617_1_
																: p_151617_2_ == p_151617_3_
																		&& p_151617_1_ != p_151617_4_
																				? p_151617_2_
																				: p_151617_2_ == p_151617_4_
																						&& p_151617_1_ != p_151617_3_
																								? p_151617_2_
																								: p_151617_3_ == p_151617_4_
																										&& p_151617_1_ != p_151617_2_
																												? p_151617_3_
																												: selectRandom(
																														new int[] {
																																p_151617_1_,
																																p_151617_2_,
																																p_151617_3_,
																																p_151617_4_ });
	}
}
