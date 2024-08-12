package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerHell extends WorldChunkManager {

public static final int EaZy = 1696;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The biome generator object. */
	private final BiomeGenBase biomeGenerator;

	/** The rainfall in the world */
	private final float rainfall;
	// private static final String __OBFID = "http://https://fuckuskid00000169";

	public WorldChunkManagerHell(final BiomeGenBase p_i45374_1_, final float p_i45374_2_) {
		biomeGenerator = p_i45374_1_;
		rainfall = p_i45374_2_;
	}

	@Override
	public BiomeGenBase func_180631_a(final BlockPos p_180631_1_) {
		return biomeGenerator;
	}

	/**
	 * Returns an array of biomes for the location input.
	 */
	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, final int p_76937_2_, final int p_76937_3_,
			final int p_76937_4_, final int p_76937_5_) {
		if (p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_) {
			p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
		}

		Arrays.fill(p_76937_1_, 0, p_76937_4_ * p_76937_5_, biomeGenerator);
		return p_76937_1_;
	}

	/**
	 * Returns a list of rainfall values for the specified blocks. Args:
	 * listToReuse, x, z, width, length.
	 */
	@Override
	public float[] getRainfall(float[] p_76936_1_, final int p_76936_2_, final int p_76936_3_, final int p_76936_4_,
			final int p_76936_5_) {
		if (p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_) {
			p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
		}

		Arrays.fill(p_76936_1_, 0, p_76936_4_ * p_76936_5_, rainfall);
		return p_76936_1_;
	}

	/**
	 * Returns biomes to use for the blocks and loads the other data like
	 * temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
	 * x, z, width, depth
	 */
	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, final int p_76933_2_, final int p_76933_3_,
			final int p_76933_4_, final int p_76933_5_) {
		if (p_76933_1_ == null || p_76933_1_.length < p_76933_4_ * p_76933_5_) {
			p_76933_1_ = new BiomeGenBase[p_76933_4_ * p_76933_5_];
		}

		Arrays.fill(p_76933_1_, 0, p_76933_4_ * p_76933_5_, biomeGenerator);
		return p_76933_1_;
	}

	/**
	 * Return a list of biomes for the specified blocks. Args: listToReuse, x,
	 * y, width, length, cacheFlag (if false, don't check biomeCache to avoid
	 * infinite loop in BiomeCacheBlock)
	 */
	@Override
	public BiomeGenBase[] getBiomeGenAt(final BiomeGenBase[] p_76931_1_, final int p_76931_2_, final int p_76931_3_,
			final int p_76931_4_, final int p_76931_5_, final boolean p_76931_6_) {
		return loadBlockGeneratorData(p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
	}

	@Override
	public BlockPos findBiomePosition(final int x, final int z, final int range, final List biomes,
			final Random random) {
		return biomes.contains(biomeGenerator)
				? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1))
				: null;
	}

	/**
	 * checks given Chunk's Biomes against List of allowed ones
	 */
	@Override
	public boolean areBiomesViable(final int p_76940_1_, final int p_76940_2_, final int p_76940_3_,
			final List p_76940_4_) {
		return p_76940_4_.contains(biomeGenerator);
	}
}
