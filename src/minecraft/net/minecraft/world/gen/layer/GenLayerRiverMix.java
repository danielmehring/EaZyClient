package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRiverMix extends GenLayer {

public static final int EaZy = 1791;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GenLayer biomePatternGeneratorChain;
	private final GenLayer riverPatternGeneratorChain;
	// private static final String __OBFID = "http://https://fuckuskid00000567";

	public GenLayerRiverMix(final long p_i2129_1_, final GenLayer p_i2129_3_, final GenLayer p_i2129_4_) {
		super(p_i2129_1_);
		biomePatternGeneratorChain = p_i2129_3_;
		riverPatternGeneratorChain = p_i2129_4_;
	}

	/**
	 * Initialize layer's local worldGenSeed based on its own baseSeed and the
	 * world's global seed (passed in as an argument).
	 */
	@Override
	public void initWorldGenSeed(final long p_75905_1_) {
		biomePatternGeneratorChain.initWorldGenSeed(p_75905_1_);
		riverPatternGeneratorChain.initWorldGenSeed(p_75905_1_);
		super.initWorldGenSeed(p_75905_1_);
	}

	/**
	 * Returns a list of integer values generated by this layer. These may be
	 * interpreted as temperatures, rainfall amounts, or biomeList[] indices
	 * based on the particular GenLayer subclass.
	 */
	@Override
	public int[] getInts(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
		final int[] var5 = biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
		final int[] var6 = riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
		final int[] var7 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int var8 = 0; var8 < areaWidth * areaHeight; ++var8) {
			if (var5[var8] != BiomeGenBase.ocean.biomeID && var5[var8] != BiomeGenBase.deepOcean.biomeID) {
				if (var6[var8] == BiomeGenBase.river.biomeID) {
					if (var5[var8] == BiomeGenBase.icePlains.biomeID) {
						var7[var8] = BiomeGenBase.frozenRiver.biomeID;
					} else if (var5[var8] != BiomeGenBase.mushroomIsland.biomeID
							&& var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID) {
						var7[var8] = var6[var8] & 255;
					} else {
						var7[var8] = BiomeGenBase.mushroomIslandShore.biomeID;
					}
				} else {
					var7[var8] = var5[var8];
				}
			} else {
				var7[var8] = var5[var8];
			}
		}

		return var7;
	}
}
