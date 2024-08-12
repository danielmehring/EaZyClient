package net.minecraft.world.biome;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;

import java.util.List;

import com.google.common.collect.Lists;

public class BiomeCache {

public static final int EaZy = 1672;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Reference to the WorldChunkManager */
	private final WorldChunkManager chunkManager;

	/** The last time this BiomeCache was cleaned, in milliseconds. */
	private long lastCleanupTime;

	/**
	 * The map of keys to BiomeCacheBlocks. Keys are based on the chunk x, z
	 * coordinates as (x | z << 32).
	 */
	private final LongHashMap cacheMap = new LongHashMap();

	/** The list of cached BiomeCacheBlocks */
	private final List cache = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00000162";

	public BiomeCache(final WorldChunkManager p_i1973_1_) {
		chunkManager = p_i1973_1_;
	}

	/**
	 * Returns a biome cache block at location specified.
	 */
	public BiomeCache.Block getBiomeCacheBlock(int p_76840_1_, int p_76840_2_) {
		p_76840_1_ >>= 4;
		p_76840_2_ >>= 4;
		final long var3 = p_76840_1_ & 4294967295L | (p_76840_2_ & 4294967295L) << 32;
		BiomeCache.Block var5 = (BiomeCache.Block) cacheMap.getValueByKey(var3);

		if (var5 == null) {
			var5 = new BiomeCache.Block(p_76840_1_, p_76840_2_);
			cacheMap.add(var3, var5);
			cache.add(var5);
		}

		var5.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
		return var5;
	}

	public BiomeGenBase func_180284_a(final int p_180284_1_, final int p_180284_2_, final BiomeGenBase p_180284_3_) {
		final BiomeGenBase var4 = getBiomeCacheBlock(p_180284_1_, p_180284_2_).getBiomeGenAt(p_180284_1_, p_180284_2_);
		return var4 == null ? p_180284_3_ : var4;
	}

	/**
	 * Removes BiomeCacheBlocks from this cache that haven't been accessed in at
	 * least 30 seconds.
	 */
	public void cleanupCache() {
		final long var1 = MinecraftServer.getCurrentTimeMillis();
		final long var3 = var1 - lastCleanupTime;

		if (var3 > 7500L || var3 < 0L) {
			lastCleanupTime = var1;

			for (int var5 = 0; var5 < cache.size(); ++var5) {
				final BiomeCache.Block var6 = (BiomeCache.Block) cache.get(var5);
				final long var7 = var1 - var6.lastAccessTime;

				if (var7 > 30000L || var7 < 0L) {
					cache.remove(var5--);
					final long var9 = var6.xPosition & 4294967295L | (var6.zPosition & 4294967295L) << 32;
					cacheMap.remove(var9);
				}
			}
		}
	}

	/**
	 * Returns the array of cached biome types in the BiomeCacheBlock at the
	 * given location.
	 */
	public BiomeGenBase[] getCachedBiomes(final int p_76839_1_, final int p_76839_2_) {
		return getBiomeCacheBlock(p_76839_1_, p_76839_2_).biomes;
	}

	public class Block {
		public float[] rainfallValues = new float[256];
		public BiomeGenBase[] biomes = new BiomeGenBase[256];
		public int xPosition;
		public int zPosition;
		public long lastAccessTime;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000163";

		public Block(final int p_i1972_2_, final int p_i1972_3_) {
			xPosition = p_i1972_2_;
			zPosition = p_i1972_3_;
			chunkManager.getRainfall(rainfallValues, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16);
			chunkManager.getBiomeGenAt(biomes, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16, false);
		}

		public BiomeGenBase getBiomeGenAt(final int p_76885_1_, final int p_76885_2_) {
			return biomes[p_76885_1_ & 15 | (p_76885_2_ & 15) << 4];
		}
	}
}
