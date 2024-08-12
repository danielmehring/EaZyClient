package net.minecraft.world.biome;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class WorldChunkManager {

public static final int EaZy = 1695;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private GenLayer genBiomes;

	/** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
	private GenLayer biomeIndexLayer;

	/** The biome list. */
	private final BiomeCache biomeCache;

	/** A list of biomes that the player can spawn in. */
	private final List biomesToSpawnIn;

	protected WorldChunkManager() {
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = Lists.newArrayList();
		biomesToSpawnIn.add(BiomeGenBase.forest);
		biomesToSpawnIn.add(BiomeGenBase.plains);
		biomesToSpawnIn.add(BiomeGenBase.taiga);
		biomesToSpawnIn.add(BiomeGenBase.taigaHills);
		biomesToSpawnIn.add(BiomeGenBase.forestHills);
		biomesToSpawnIn.add(BiomeGenBase.jungle);
		biomesToSpawnIn.add(BiomeGenBase.jungleHills);
	}

	public WorldChunkManager(final long p_i45744_1_, final WorldType p_i45744_3_, final String p_i45744_4_) {
		this();
		final GenLayer[] var5 = GenLayer.func_180781_a(p_i45744_1_, p_i45744_3_, p_i45744_4_);
		genBiomes = var5[0];
		biomeIndexLayer = var5[1];
	}

	public WorldChunkManager(final World worldIn) {
		this(worldIn.getSeed(), worldIn.getWorldInfo().getTerrainType(), worldIn.getWorldInfo().getGeneratorOptions());
	}

	/**
	 * Gets the list of valid biomes for the player to spawn in.
	 */
	public List getBiomesToSpawnIn() {
		return biomesToSpawnIn;
	}

	public BiomeGenBase func_180631_a(final BlockPos p_180631_1_) {
		return func_180300_a(p_180631_1_, (BiomeGenBase) null);
	}

	public BiomeGenBase func_180300_a(final BlockPos p_180300_1_, final BiomeGenBase p_180300_2_) {
		return biomeCache.func_180284_a(p_180300_1_.getX(), p_180300_1_.getZ(), p_180300_2_);
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args:
	 * listToReuse, x, z, width, length.
	 */
	public float[] getRainfall(float[] p_76936_1_, final int p_76936_2_, final int p_76936_3_, final int p_76936_4_,
			final int p_76936_5_) {
		IntCache.resetIntCache();

		if (p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_) {
			p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
		}

		final int[] var6 = biomeIndexLayer.getInts(p_76936_2_, p_76936_3_, p_76936_4_, p_76936_5_);

		for (int var7 = 0; var7 < p_76936_4_ * p_76936_5_; ++var7) {
			try {
				float var8 = BiomeGenBase.getBiomeFromBiomeList(var6[var7], BiomeGenBase.field_180279_ad)
						.getIntRainfall() / 65536.0F;

				if (var8 > 1.0F) {
					var8 = 1.0F;
				}

				p_76936_1_[var7] = var8;
			} catch (final Throwable var11) {
				final CrashReport var9 = CrashReport.makeCrashReport(var11, "Invalid Biome id");
				final CrashReportCategory var10 = var9.makeCategory("DownfallBlock");
				var10.addCrashSection("biome id", var7);
				var10.addCrashSection("downfalls[] size", p_76936_1_.length);
				var10.addCrashSection("x", p_76936_2_);
				var10.addCrashSection("z", p_76936_3_);
				var10.addCrashSection("w", p_76936_4_);
				var10.addCrashSection("h", p_76936_5_);
				throw new ReportedException(var9);
			}
		}

		return p_76936_1_;
	}

	/**
	 * Return an adjusted version of a given temperature based on the y height
	 */
	public float getTemperatureAtHeight(final float p_76939_1_, final int p_76939_2_) {
		return p_76939_1_;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, final int p_76937_2_, final int p_76937_3_,
			final int p_76937_4_, final int p_76937_5_) {
		IntCache.resetIntCache();

		if (p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_) {
			p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
		}

		final int[] var6 = genBiomes.getInts(p_76937_2_, p_76937_3_, p_76937_4_, p_76937_5_);

		try {
			for (int var7 = 0; var7 < p_76937_4_ * p_76937_5_; ++var7) {
				p_76937_1_[var7] = BiomeGenBase.getBiomeFromBiomeList(var6[var7], BiomeGenBase.field_180279_ad);
			}

			return p_76937_1_;
		} catch (final Throwable var10) {
			final CrashReport var8 = CrashReport.makeCrashReport(var10, "Invalid Biome id");
			final CrashReportCategory var9 = var8.makeCategory("RawBiomeBlock");
			var9.addCrashSection("biomes[] size", p_76937_1_.length);
			var9.addCrashSection("x", p_76937_2_);
			var9.addCrashSection("z", p_76937_3_);
			var9.addCrashSection("w", p_76937_4_);
			var9.addCrashSection("h", p_76937_5_);
			throw new ReportedException(var8);
		}
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like
	 * temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
	 * x, z, width, depth
	 */
	public BiomeGenBase[] loadBlockGeneratorData(final BiomeGenBase[] p_76933_1_, final int p_76933_2_,
			final int p_76933_3_, final int p_76933_4_, final int p_76933_5_) {
		return getBiomeGenAt(p_76933_1_, p_76933_2_, p_76933_3_, p_76933_4_, p_76933_5_, true);
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x,
	 * y, width, length, cacheFlag (if false, don't check biomeCache to avoid
	 * infinite loop in BiomeCacheBlock)
	 */
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, final int p_76931_2_, final int p_76931_3_,
			final int p_76931_4_, final int p_76931_5_, final boolean p_76931_6_) {
		IntCache.resetIntCache();

		if (p_76931_1_ == null || p_76931_1_.length < p_76931_4_ * p_76931_5_) {
			p_76931_1_ = new BiomeGenBase[p_76931_4_ * p_76931_5_];
		}

		if (p_76931_6_ && p_76931_4_ == 16 && p_76931_5_ == 16 && (p_76931_2_ & 15) == 0 && (p_76931_3_ & 15) == 0) {
			final BiomeGenBase[] var9 = biomeCache.getCachedBiomes(p_76931_2_, p_76931_3_);
			System.arraycopy(var9, 0, p_76931_1_, 0, p_76931_4_ * p_76931_5_);
			return p_76931_1_;
		} else {
			final int[] var7 = biomeIndexLayer.getInts(p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);

			for (int var8 = 0; var8 < p_76931_4_ * p_76931_5_; ++var8) {
				p_76931_1_[var8] = BiomeGenBase.getBiomeFromBiomeList(var7[var8], BiomeGenBase.field_180279_ad);
			}

			return p_76931_1_;
		}
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	public boolean areBiomesViable(final int p_76940_1_, final int p_76940_2_, final int p_76940_3_,
			final List p_76940_4_) {
		IntCache.resetIntCache();
		final int var5 = p_76940_1_ - p_76940_3_ >> 2;
		final int var6 = p_76940_2_ - p_76940_3_ >> 2;
		final int var7 = p_76940_1_ + p_76940_3_ >> 2;
		final int var8 = p_76940_2_ + p_76940_3_ >> 2;
		final int var9 = var7 - var5 + 1;
		final int var10 = var8 - var6 + 1;
		final int[] var11 = genBiomes.getInts(var5, var6, var9, var10);

		try {
			for (int var12 = 0; var12 < var9 * var10; ++var12) {
				final BiomeGenBase var16 = BiomeGenBase.getBiome(var11[var12]);

				if (!p_76940_4_.contains(var16)) {
					return false;
				}
			}

			return true;
		} catch (final Throwable var15) {
			final CrashReport var13 = CrashReport.makeCrashReport(var15, "Invalid Biome id");
			final CrashReportCategory var14 = var13.makeCategory("Layer");
			var14.addCrashSection("Layer", genBiomes.toString());
			var14.addCrashSection("x", p_76940_1_);
			var14.addCrashSection("z", p_76940_2_);
			var14.addCrashSection("radius", p_76940_3_);
			var14.addCrashSection("allowed", p_76940_4_);
			throw new ReportedException(var13);
		}
	}

	public BlockPos findBiomePosition(final int x, final int z, final int range, final List biomes,
			final Random random) {
		IntCache.resetIntCache();
		final int var6 = x - range >> 2;
		final int var7 = z - range >> 2;
		final int var8 = x + range >> 2;
		final int var9 = z + range >> 2;
		final int var10 = var8 - var6 + 1;
		final int var11 = var9 - var7 + 1;
		final int[] var12 = genBiomes.getInts(var6, var7, var10, var11);
		BlockPos var13 = null;
		int var14 = 0;

		for (int var15 = 0; var15 < var10 * var11; ++var15) {
			final int var16 = var6 + var15 % var10 << 2;
			final int var17 = var7 + var15 / var10 << 2;
			final BiomeGenBase var18 = BiomeGenBase.getBiome(var12[var15]);

			if (biomes.contains(var18) && (var13 == null || random.nextInt(var14 + 1) == 0)) {
				var13 = new BlockPos(var16, 0, var17);
				++var14;
			}
		}

		return var13;
	}

	/**
	 * Calls the WorldChunkManager's biomeCache.cleanupCache()
	 */
	public void cleanupCache() {
		biomeCache.cleanupCache();
	}
}
