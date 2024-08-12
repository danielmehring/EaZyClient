package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomMobsRule {

public static final int EaZy = 1952;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ResourceLocation baseResLoc = null;
	private int[] skins = null;
	private ResourceLocation[] resourceLocations = null;
	private int[] weights = null;
	private BiomeGenBase[] biomes = null;
	private RangeListInt heights = null;
	public int[] sumWeights = null;
	public int sumAllWeights = 1;

	public RandomMobsRule(final ResourceLocation baseResLoc, final int[] skins, final int[] weights,
			final BiomeGenBase[] biomes, final RangeListInt heights) {
		this.baseResLoc = baseResLoc;
		this.skins = skins;
		this.weights = weights;
		this.biomes = biomes;
		this.heights = heights;
	}

	public boolean isValid(final String path) {
		resourceLocations = new ResourceLocation[skins.length];
		final ResourceLocation locMcp = RandomMobs.getMcpatcherLocation(baseResLoc);

		if (locMcp == null) {
			Config.warn("Invalid path: " + baseResLoc.getResourcePath());
			return false;
		} else {
			int sum;
			int i;

			for (sum = 0; sum < resourceLocations.length; ++sum) {
				i = skins[sum];

				if (i <= 1) {
					resourceLocations[sum] = baseResLoc;
				} else {
					final ResourceLocation i1 = RandomMobs.getLocationIndexed(locMcp, i);

					if (i1 == null) {
						Config.warn("Invalid path: " + baseResLoc.getResourcePath());
						return false;
					}

					if (!Config.hasResource(i1)) {
						Config.warn("Texture not found: " + i1.getResourcePath());
						return false;
					}

					resourceLocations[sum] = i1;
				}
			}

			if (weights != null) {
				int[] var6;

				if (weights.length > resourceLocations.length) {
					Config.warn("More weights defined than skins, trimming weights: " + path);
					var6 = new int[resourceLocations.length];
					System.arraycopy(weights, 0, var6, 0, var6.length);
					weights = var6;
				}

				if (weights.length < resourceLocations.length) {
					Config.warn("Less weights defined than skins, expanding weights: " + path);
					var6 = new int[resourceLocations.length];
					System.arraycopy(weights, 0, var6, 0, weights.length);
					i = MathUtils.getAverage(weights);

					for (int var7 = weights.length; var7 < var6.length; ++var7) {
						var6[var7] = i;
					}

					weights = var6;
				}

				sumWeights = new int[weights.length];
				sum = 0;

				for (i = 0; i < weights.length; ++i) {
					if (weights[i] < 0) {
						Config.warn("Invalid weight: " + weights[i]);
						return false;
					}

					sum += weights[i];
					sumWeights[i] = sum;
				}

				sumAllWeights = sum;

				if (sumAllWeights <= 0) {
					Config.warn("Invalid sum of all weights: " + sum);
					sumAllWeights = 1;
				}
			}

			return true;
		}
	}

	public boolean matches(final EntityLiving el) {
		return !Matches.biome(el.spawnBiome, biomes) ? false
				: heights != null && el.spawnPosition != null ? heights.isInRange(el.spawnPosition.getY()) : true;
	}

	public ResourceLocation getTextureLocation(final ResourceLocation loc, final int randomId) {
		int index = 0;

		if (weights == null) {
			index = randomId % resourceLocations.length;
		} else {
			final int randWeight = randomId % sumAllWeights;

			for (int i = 0; i < sumWeights.length; ++i) {
				if (sumWeights[i] > randWeight) {
					index = i;
					break;
				}
			}
		}

		return resourceLocations[index];
	}
}
