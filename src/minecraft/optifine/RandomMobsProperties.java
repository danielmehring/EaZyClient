package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Properties;

public class RandomMobsProperties {

public static final int EaZy = 1951;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String name = null;
	public String basePath = null;
	public ResourceLocation[] resourceLocations = null;
	public RandomMobsRule[] rules = null;

	public RandomMobsProperties(final String path, final ResourceLocation[] variants) {
		final ConnectedParser cp = new ConnectedParser("RandomMobs");
		name = cp.parseName(path);
		basePath = cp.parseBasePath(path);
		resourceLocations = variants;
	}

	public RandomMobsProperties(final Properties props, final String path, final ResourceLocation baseResLoc) {
		final ConnectedParser cp = new ConnectedParser("RandomMobs");
		name = cp.parseName(path);
		basePath = cp.parseBasePath(path);
		rules = parseRules(props, baseResLoc, cp);
	}

	public ResourceLocation getTextureLocation(final ResourceLocation loc, final EntityLiving el) {
		int randomId;

		if (rules != null) {
			for (randomId = 0; randomId < rules.length; ++randomId) {
				final RandomMobsRule index = rules[randomId];

				if (index.matches(el)) {
					return index.getTextureLocation(loc, el.randomMobsId);
				}
			}
		}

		if (resourceLocations != null) {
			randomId = el.randomMobsId;
			final int var5 = randomId % resourceLocations.length;
			return resourceLocations[var5];
		} else {
			return loc;
		}
	}

	private RandomMobsRule[] parseRules(final Properties props, final ResourceLocation baseResLoc,
			final ConnectedParser cp) {
		final ArrayList list = new ArrayList();
		final int count = props.size();

		for (int rules = 0; rules < count; ++rules) {
			final int index = rules + 1;
			final String valSkins = props.getProperty("skins." + index);

			if (valSkins != null) {
				final int[] skins = cp.parseIntList(valSkins);
				final int[] weights = cp.parseIntList(props.getProperty("weights." + index));
				final BiomeGenBase[] biomes = cp.parseBiomes(props.getProperty("biomes." + index));
				RangeListInt heights = cp.parseRangeListInt(props.getProperty("heights." + index));

				if (heights == null) {
					heights = parseMinMaxHeight(props, index);
				}

				final RandomMobsRule rule = new RandomMobsRule(baseResLoc, skins, weights, biomes, heights);
				list.add(rule);
			}
		}

		final RandomMobsRule[] var14 = (RandomMobsRule[]) list.toArray(new RandomMobsRule[list.size()]);
		return var14;
	}

	private RangeListInt parseMinMaxHeight(final Properties props, final int index) {
		final String minHeightStr = props.getProperty("minHeight." + index);
		final String maxHeightStr = props.getProperty("maxHeight." + index);

		if (minHeightStr == null && maxHeightStr == null) {
			return null;
		} else {
			int minHeight = 0;

			if (minHeightStr != null) {
				minHeight = Config.parseInt(minHeightStr, -1);

				if (minHeight < 0) {
					Config.warn("Invalid minHeight: " + minHeightStr);
					return null;
				}
			}

			int maxHeight = 256;

			if (maxHeightStr != null) {
				maxHeight = Config.parseInt(maxHeightStr, -1);

				if (maxHeight < 0) {
					Config.warn("Invalid maxHeight: " + maxHeightStr);
					return null;
				}
			}

			if (maxHeight < 0) {
				Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
				return null;
			} else {
				final RangeListInt list = new RangeListInt();
				list.addRange(new RangeInt(minHeight, maxHeight));
				return list;
			}
		}
	}

	public boolean isValid(final String path) {
		if (resourceLocations == null && rules == null) {
			Config.warn("No skins specified: " + path);
			return false;
		} else {
			int i;

			if (rules != null) {
				for (i = 0; i < rules.length; ++i) {
					final RandomMobsRule loc = rules[i];

					if (!loc.isValid(path)) {
						return false;
					}
				}
			}

			if (resourceLocations != null) {
				for (i = 0; i < resourceLocations.length; ++i) {
					final ResourceLocation var4 = resourceLocations[i];

					if (!Config.hasResource(var4)) {
						Config.warn("Texture not found: " + var4.getResourcePath());
						return false;
					}
				}
			}

			return true;
		}
	}
}
