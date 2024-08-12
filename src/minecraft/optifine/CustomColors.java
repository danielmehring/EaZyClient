package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {

public static final int EaZy = 1891;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static CustomColormap waterColors = null;
	private static CustomColormap foliagePineColors = null;
	private static CustomColormap foliageBirchColors = null;
	private static CustomColormap swampFoliageColors = null;
	private static CustomColormap swampGrassColors = null;
	private static CustomColormap[] colorsBlockColormaps = null;
	private static CustomColormap[][] blockColormaps = null;
	private static CustomColormap skyColors = null;
	private static CustomColorFader skyColorFader = new CustomColorFader();
	private static CustomColormap fogColors = null;
	private static CustomColorFader fogColorFader = new CustomColorFader();
	private static CustomColormap underwaterColors = null;
	private static CustomColorFader underwaterColorFader = new CustomColorFader();
	private static CustomColormap[] lightMapsColorsRgb = null;
	private static int lightmapMinDimensionId = 0;
	private static float[][] sunRgbs = new float[16][3];
	private static float[][] torchRgbs = new float[16][3];
	private static CustomColormap redstoneColors = null;
	private static CustomColormap xpOrbColors = null;
	private static CustomColormap stemColors = null;
	private static CustomColormap stemMelonColors = null;
	private static CustomColormap stemPumpkinColors = null;
	private static CustomColormap myceliumParticleColors = null;
	private static boolean useDefaultGrassFoliageColors = true;
	private static int particleWaterColor = -1;
	private static int particlePortalColor = -1;
	private static int lilyPadColor = -1;
	private static int expBarTextColor = -1;
	private static int bossTextColor = -1;
	private static int signTextColor = -1;
	private static Vec3 fogColorNether = null;
	private static Vec3 fogColorEnd = null;
	private static Vec3 skyColorEnd = null;
	private static int[] spawnEggPrimaryColors = null;
	private static int[] spawnEggSecondaryColors = null;
	private static float[][] wolfCollarColors = null;
	private static float[][] sheepColors = null;
	private static int[] textColors = null;
	private static int[] mapColorsOriginal = null;
	private static int[] potionColors = null;
	private static final IBlockState BLOCK_STATE_DIRT = Blocks.dirt.getDefaultState();
	private static final IBlockState BLOCK_STATE_WATER = Blocks.water.getDefaultState();
	public static Random random = new Random();
	private static final CustomColors.IColorizer COLORIZER_GRASS = new CustomColors.IColorizer() {
		@Override
		public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
			final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
			return CustomColors.swampGrassColors != null && biome == BiomeGenBase.swampland
					? CustomColors.swampGrassColors.getColor(biome, blockPos) : biome.func_180627_b(blockPos);
		}

		@Override
		public boolean isColorConstant() {
			return false;
		}
	};
	private static final CustomColors.IColorizer COLORIZER_FOLIAGE = new CustomColors.IColorizer() {
		@Override
		public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
			final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
			return CustomColors.swampFoliageColors != null && biome == BiomeGenBase.swampland
					? CustomColors.swampFoliageColors.getColor(biome, blockPos) : biome.func_180625_c(blockPos);
		}

		@Override
		public boolean isColorConstant() {
			return false;
		}
	};
	private static final CustomColors.IColorizer COLORIZER_FOLIAGE_PINE = new CustomColors.IColorizer() {
		@Override
		public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
			return CustomColors.foliagePineColors != null
					? CustomColors.foliagePineColors.getColor(blockAccess, blockPos)
					: ColorizerFoliage.getFoliageColorPine();
		}

		@Override
		public boolean isColorConstant() {
			return CustomColors.foliagePineColors == null;
		}
	};
	private static final CustomColors.IColorizer COLORIZER_FOLIAGE_BIRCH = new CustomColors.IColorizer() {
		@Override
		public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
			return CustomColors.foliageBirchColors != null
					? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos)
					: ColorizerFoliage.getFoliageColorBirch();
		}

		@Override
		public boolean isColorConstant() {
			return CustomColors.foliageBirchColors == null;
		}
	};
	private static final CustomColors.IColorizer COLORIZER_WATER = new CustomColors.IColorizer() {
		@Override
		public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
			final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
			return CustomColors.waterColors != null ? CustomColors.waterColors.getColor(biome, blockPos)
					: Reflector.ForgeBiomeGenBase_getWaterColorMultiplier.exists() ? Reflector.callInt(biome,
							Reflector.ForgeBiomeGenBase_getWaterColorMultiplier, new Object[0])
							: biome.waterColorMultiplier;
		}

		@Override
		public boolean isColorConstant() {
			return false;
		}
	};

	public static void update() {
		waterColors = null;
		foliageBirchColors = null;
		foliagePineColors = null;
		swampGrassColors = null;
		swampFoliageColors = null;
		skyColors = null;
		fogColors = null;
		underwaterColors = null;
		redstoneColors = null;
		xpOrbColors = null;
		stemColors = null;
		myceliumParticleColors = null;
		lightMapsColorsRgb = null;
		particleWaterColor = -1;
		particlePortalColor = -1;
		lilyPadColor = -1;
		expBarTextColor = -1;
		bossTextColor = -1;
		signTextColor = -1;
		fogColorNether = null;
		fogColorEnd = null;
		skyColorEnd = null;
		colorsBlockColormaps = null;
		blockColormaps = null;
		useDefaultGrassFoliageColors = true;
		spawnEggPrimaryColors = null;
		spawnEggSecondaryColors = null;
		wolfCollarColors = null;
		sheepColors = null;
		textColors = null;
		setMapColors(mapColorsOriginal);
		potionColors = null;
		PotionHelper.clearPotionColorCache();
		final String mcpColormap = "mcpatcher/colormap/";
		final String[] waterPaths = new String[] { "water.png", "watercolorX.png" };
		waterColors = getCustomColors(mcpColormap, waterPaths, 256, 256);
		updateUseDefaultGrassFoliageColors();

		if (Config.isCustomColors()) {
			final String[] pinePaths = new String[] { "pine.png", "pinecolor.png" };
			foliagePineColors = getCustomColors(mcpColormap, pinePaths, 256, 256);
			final String[] birchPaths = new String[] { "birch.png", "birchcolor.png" };
			foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 256, 256);
			final String[] swampGrassPaths = new String[] { "swampgrass.png", "swampgrasscolor.png" };
			swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 256, 256);
			final String[] swampFoliagePaths = new String[] { "swampfoliage.png", "swampfoliagecolor.png" };
			swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 256, 256);
			final String[] sky0Paths = new String[] { "sky0.png", "skycolor0.png" };
			skyColors = getCustomColors(mcpColormap, sky0Paths, 256, 256);
			final String[] fog0Paths = new String[] { "fog0.png", "fogcolor0.png" };
			fogColors = getCustomColors(mcpColormap, fog0Paths, 256, 256);
			final String[] underwaterPaths = new String[] { "underwater.png", "underwatercolor.png" };
			underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 256, 256);
			final String[] redstonePaths = new String[] { "redstone.png", "redstonecolor.png" };
			redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16, 1);
			xpOrbColors = getCustomColors(mcpColormap + "xporb.png", -1, -1);
			final String[] stemPaths = new String[] { "stem.png", "stemcolor.png" };
			stemColors = getCustomColors(mcpColormap, stemPaths, 8, 1);
			stemPumpkinColors = getCustomColors(mcpColormap + "pumpkinstem.png", 8, 1);
			stemMelonColors = getCustomColors(mcpColormap + "melonstem.png", 8, 1);
			final String[] myceliumPaths = new String[] { "myceliumparticle.png", "myceliumparticlecolor.png" };
			myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1, -1);
			final Pair lightMaps = parseLightmapsRgb();
			lightMapsColorsRgb = (CustomColormap[]) lightMaps.getLeft();
			lightmapMinDimensionId = ((Integer) lightMaps.getRight());
			readColorProperties("mcpatcher/color.properties");
			blockColormaps = readBlockColormaps(new String[] { mcpColormap + "custom/", mcpColormap + "blocks/" },
					colorsBlockColormaps, 256, 256);
			updateUseDefaultGrassFoliageColors();
		}
	}

	private static Pair<CustomColormap[], Integer> parseLightmapsRgb() {
		final String lightmapPrefix = "mcpatcher/lightmap/world";
		final String lightmapSuffix = ".png";
		final String[] pathsLightmap = ResUtils.collectFiles(lightmapPrefix, lightmapSuffix);
		final HashMap mapLightmaps = new HashMap();
		int maxDimId;

		for (final String dimIds : pathsLightmap) {
			final String minDimId = StrUtils.removePrefixSuffix(dimIds, lightmapPrefix, lightmapSuffix);
			maxDimId = Config.parseInt(minDimId, Integer.MIN_VALUE);

			if (maxDimId == Integer.MIN_VALUE) {
				warn("Invalid dimension ID: " + minDimId + ", path: " + dimIds);
			} else {
				mapLightmaps.put(maxDimId, dimIds);
			}
		}

		final Set var15 = mapLightmaps.keySet();
		final Integer[] var16 = (Integer[]) var15.toArray(new Integer[var15.size()]);
		Arrays.sort(var16);

		if (var16.length <= 0) {
			return new ImmutablePair((Object) null, 0);
		} else {
			final int var17 = var16[0];
			maxDimId = var16[var16.length - 1];
			final int countDim = maxDimId - var17 + 1;
			final CustomColormap[] colormaps = new CustomColormap[countDim];

			for (final Integer dimId : var16) {
				final String path = (String) mapLightmaps.get(dimId);
				final CustomColormap colors = getCustomColors(path, -1, -1);

				if (colors != null) {
					if (colors.getWidth() < 16) {
						warn("Invalid lightmap width: " + colors.getWidth() + ", path: " + path);
					} else {
						final int lightmapIndex = dimId - var17;
						colormaps[lightmapIndex] = colors;
					}
				}
			}

			return new ImmutablePair(colormaps, var17);
		}
	}

	private static void readColorProperties(final String fileName) {
		try {
			final ResourceLocation e = new ResourceLocation(fileName);
			final InputStream in = Config.getResourceStream(e);

			if (in == null) {
				return;
			}

			dbg("Loading " + fileName);
			final Properties props = new Properties();
			props.load(in);
			in.close();
			particleWaterColor = readColor(props, new String[] { "particle.water", "drop.water" });
			particlePortalColor = readColor(props, "particle.portal");
			lilyPadColor = readColor(props, "lilypad");
			expBarTextColor = readColor(props, "text.xpbar");
			bossTextColor = readColor(props, "text.boss");
			signTextColor = readColor(props, "text.sign");
			fogColorNether = readColorVec3(props, "fog.nether");
			fogColorEnd = readColorVec3(props, "fog.end");
			skyColorEnd = readColorVec3(props, "sky.end");
			colorsBlockColormaps = readCustomColormaps(props, fileName);
			spawnEggPrimaryColors = readSpawnEggColors(props, fileName, "egg.shell.", "Spawn egg shell");
			spawnEggSecondaryColors = readSpawnEggColors(props, fileName, "egg.spots.", "Spawn egg spot");
			wolfCollarColors = readDyeColors(props, fileName, "collar.", "Wolf collar");
			sheepColors = readDyeColors(props, fileName, "sheep.", "Sheep");
			textColors = readTextColors(props, fileName, "text.code.", "Text");
			final int[] mapColors = readMapColors(props, fileName, "map.", "Map");

			if (mapColors != null) {
				if (mapColorsOriginal == null) {
					mapColorsOriginal = getMapColors();
				}

				setMapColors(mapColors);
			}

			potionColors = readPotionColors(props, fileName, "potion.", "Potion");
		} catch (final FileNotFoundException var5) {
			return;
		} catch (final IOException var6) {
			var6.printStackTrace();
		}
	}

	private static CustomColormap[] readCustomColormaps(final Properties props, final String fileName) {
		final ArrayList list = new ArrayList();
		final String palettePrefix = "palette.block.";
		final HashMap map = new HashMap();
		final Set keys = props.keySet();
		final Iterator propNames = keys.iterator();
		String name;

		while (propNames.hasNext()) {
			final String cms = (String) propNames.next();
			name = props.getProperty(cms);

			if (cms.startsWith(palettePrefix)) {
				map.put(cms, name);
			}
		}

		final String[] var17 = (String[]) map.keySet().toArray(new String[map.size()]);

		for (final String element : var17) {
			name = element;
			final String value = props.getProperty(name);
			dbg("Block palette: " + name + " = " + value);
			String path = name.substring(palettePrefix.length());
			final String basePath = TextureUtils.getBasePath(fileName);
			path = TextureUtils.fixResourcePath(path, basePath);
			final CustomColormap colors = getCustomColors(path, 256, 256);

			if (colors == null) {
				warn("Colormap not found: " + path);
			} else {
				final ConnectedParser cp = new ConnectedParser("CustomColors");
				final MatchBlock[] mbs = cp.parseMatchBlocks(value);

				if (mbs != null && mbs.length > 0) {
					for (final MatchBlock mb : mbs) {
						colors.addMatchBlock(mb);
					}

					list.add(colors);
				} else {
					warn("Invalid match blocks: " + value);
				}
			}
		}

		if (list.size() <= 0) {
			return null;
		} else {
			final CustomColormap[] var19 = (CustomColormap[]) list.toArray(new CustomColormap[list.size()]);
			return var19;
		}
	}

	private static CustomColormap[][] readBlockColormaps(final String[] basePaths, final CustomColormap[] basePalettes,
			final int width, final int height) {
		final String[] paths = ResUtils.collectFiles(basePaths, new String[] { ".properties" });
		Arrays.sort(paths);
		final ArrayList blockList = new ArrayList();
		int cmArr;

		for (cmArr = 0; cmArr < paths.length; ++cmArr) {
			final String cm = paths[cmArr];
			dbg("Block colormap: " + cm);

			try {
				final ResourceLocation e = new ResourceLocation("minecraft", cm);
				final InputStream in = Config.getResourceStream(e);

				if (in == null) {
					warn("File not found: " + cm);
				} else {
					final Properties props = new Properties();
					props.load(in);
					final CustomColormap cm1 = new CustomColormap(props, cm, width, height);

					if (cm1.isValid(cm) && cm1.isValidMatchBlocks(cm)) {
						addToBlockList(cm1, blockList);
					}
				}
			} catch (final FileNotFoundException var12) {
				warn("File not found: " + cm);
			} catch (final Exception var13) {
				var13.printStackTrace();
			}
		}

		if (basePalettes != null) {
			for (cmArr = 0; cmArr < basePalettes.length; ++cmArr) {
				final CustomColormap var15 = basePalettes[cmArr];
				addToBlockList(var15, blockList);
			}
		}

		if (blockList.size() <= 0) {
			return null;
		} else {
			final CustomColormap[][] var14 = blockListToArray(blockList);
			return var14;
		}
	}

	private static void addToBlockList(final CustomColormap cm, final List blockList) {
		final int[] ids = cm.getMatchBlockIds();

		if (ids != null && ids.length > 0) {
			for (final int blockId : ids) {
				if (blockId < 0) {
					warn("Invalid block ID: " + blockId);
				} else {
					addToList(cm, blockList, blockId);
				}
			}
		} else {
			warn("No match blocks: " + Config.arrayToString(ids));
		}
	}

	private static void addToList(final CustomColormap cm, final List list, final int id) {
		while (id >= list.size()) {
			list.add((Object) null);
		}

		Object subList = list.get(id);

		if (subList == null) {
			subList = new ArrayList();
			list.set(id, subList);
		}

		((List) subList).add(cm);
	}

	private static CustomColormap[][] blockListToArray(final List list) {
		final CustomColormap[][] colArr = new CustomColormap[list.size()][];

		for (int i = 0; i < list.size(); ++i) {
			final List subList = (List) list.get(i);

			if (subList != null) {
				final CustomColormap[] subArr = (CustomColormap[]) subList.toArray(new CustomColormap[subList.size()]);
				colArr[i] = subArr;
			}
		}

		return colArr;
	}

	private static int readColor(final Properties props, final String[] names) {
		for (final String name : names) {
			final int col = readColor(props, name);

			if (col >= 0) {
				return col;
			}
		}

		return -1;
	}

	private static int readColor(final Properties props, final String name) {
		String str = props.getProperty(name);

		if (str == null) {
			return -1;
		} else {
			str = str.trim();
			final int color = parseColor(str);

			if (color < 0) {
				warn("Invalid color: " + name + " = " + str);
				return color;
			} else {
				dbg(name + " = " + str);
				return color;
			}
		}
	}

	private static int parseColor(String str) {
		if (str == null) {
			return -1;
		} else {
			str = str.trim();

			try {
				final int e = Integer.parseInt(str, 16) & 16777215;
				return e;
			} catch (final NumberFormatException var2) {
				return -1;
			}
		}
	}

	private static Vec3 readColorVec3(final Properties props, final String name) {
		final int col = readColor(props, name);

		if (col < 0) {
			return null;
		} else {
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			final float redF = red / 255.0F;
			final float greenF = green / 255.0F;
			final float blueF = blue / 255.0F;
			return new Vec3(redF, greenF, blueF);
		}
	}

	private static CustomColormap getCustomColors(final String basePath, final String[] paths, final int width,
			final int height) {
		for (final String path2 : paths) {
			String path = path2;
			path = basePath + path;
			final CustomColormap cols = getCustomColors(path, width, height);

			if (cols != null) {
				return cols;
			}
		}

		return null;
	}

	public static CustomColormap getCustomColors(final String pathImage, final int width, final int height) {
		try {
			final ResourceLocation e = new ResourceLocation(pathImage);

			if (!Config.hasResource(e)) {
				return null;
			} else {
				dbg("Colormap " + pathImage);
				final Properties props = new Properties();
				String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
				final ResourceLocation locProps = new ResourceLocation(pathProps);

				if (Config.hasResource(locProps)) {
					final InputStream cm = Config.getResourceStream(locProps);
					props.load(cm);
					cm.close();
					dbg("Colormap properties: " + pathProps);
				} else {
					props.put("format", "vanilla");
					props.put("source", pathImage);
					pathProps = pathImage;
				}

				final CustomColormap cm1 = new CustomColormap(props, pathProps, width, height);
				return !cm1.isValid(pathProps) ? null : cm1;
			}
		} catch (final Exception var8) {
			var8.printStackTrace();
			return null;
		}
	}

	public static void updateUseDefaultGrassFoliageColors() {
		useDefaultGrassFoliageColors = foliageBirchColors == null && foliagePineColors == null
				&& swampGrassColors == null && swampFoliageColors == null && Config.isSwampColors()
				&& Config.isSmoothBiomes();
	}

	public static int getColorMultiplier(final BakedQuad quad, final Block block, final IBlockAccess blockAccess,
			BlockPos blockPos, final RenderEnv renderEnv) {
		if (blockColormaps != null) {
			IBlockState metadata = renderEnv.getBlockState();

			if (!quad.func_178212_b()) {
				if (block == Blocks.grass) {
					metadata = BLOCK_STATE_DIRT;
				}

				if (block == Blocks.redstone_wire) {
					return -1;
				}
			}

			if (block == Blocks.double_plant && renderEnv.getMetadata() >= 8) {
				blockPos = blockPos.offsetDown();
				metadata = blockAccess.getBlockState(blockPos);
			}

			final CustomColormap colorizer = getBlockColormap(metadata);

			if (colorizer != null) {
				if (Config.isSmoothBiomes() && !colorizer.isColorConstant()) {
					return getSmoothColorMultiplier(blockAccess, blockPos, colorizer,
							renderEnv.getColorizerBlockPosM());
				}

				return colorizer.getColor(blockAccess, blockPos);
			}
		}

		if (!quad.func_178212_b()) {
			return -1;
		} else if (block == Blocks.waterlily) {
			return getLilypadColorMultiplier(blockAccess, blockPos);
		} else if (block == Blocks.redstone_wire) {
			return getRedstoneColor(renderEnv.getBlockState());
		} else if (block instanceof BlockStem) {
			return getStemColorMultiplier(block, blockAccess, blockPos, renderEnv);
		} else if (useDefaultGrassFoliageColors) {
			return -1;
		} else {
			final int metadata1 = renderEnv.getMetadata();
			CustomColors.IColorizer colorizer1;

			if (block != Blocks.grass && block != Blocks.tallgrass && block != Blocks.double_plant) {
				if (block == Blocks.double_plant) {
					colorizer1 = COLORIZER_GRASS;

					if (metadata1 >= 8) {
						blockPos = blockPos.offsetDown();
					}
				} else if (block == Blocks.leaves) {
					switch (metadata1 & 3) {
						case 0:
							colorizer1 = COLORIZER_FOLIAGE;
							break;

						case 1:
							colorizer1 = COLORIZER_FOLIAGE_PINE;
							break;

						case 2:
							colorizer1 = COLORIZER_FOLIAGE_BIRCH;
							break;

						default:
							colorizer1 = COLORIZER_FOLIAGE;
					}
				} else if (block == Blocks.leaves2) {
					colorizer1 = COLORIZER_FOLIAGE;
				} else {
					if (block != Blocks.vine) {
						return -1;
					}

					colorizer1 = COLORIZER_FOLIAGE;
				}
			} else {
				colorizer1 = COLORIZER_GRASS;
			}

			return Config.isSmoothBiomes() && !colorizer1.isColorConstant()
					? getSmoothColorMultiplier(blockAccess, blockPos, colorizer1, renderEnv.getColorizerBlockPosM())
					: colorizer1.getColor(blockAccess, blockPos);
		}
	}

	protected static BiomeGenBase getColorBiome(final IBlockAccess blockAccess, final BlockPos blockPos) {
		BiomeGenBase biome = blockAccess.getBiomeGenForCoords(blockPos);

		if (biome == BiomeGenBase.swampland && !Config.isSwampColors()) {
			biome = BiomeGenBase.plains;
		}

		return biome;
	}

	private static CustomColormap getBlockColormap(final IBlockState blockState) {
		if (blockColormaps == null) {
			return null;
		} else if (!(blockState instanceof BlockStateBase)) {
			return null;
		} else {
			final BlockStateBase bs = (BlockStateBase) blockState;
			final int blockId = bs.getBlockId();

			if (blockId >= 0 && blockId < blockColormaps.length) {
				final CustomColormap[] cms = blockColormaps[blockId];

				if (cms == null) {
					return null;
				} else {
					for (final CustomColormap cm : cms) {
						if (cm.matchesBlock(bs)) {
							return cm;
						}
					}

					return null;
				}
			} else {
				return null;
			}
		}
	}

	private static int getSmoothColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos,
			final CustomColors.IColorizer colorizer, final BlockPosM blockPosM) {
		int sumRed = 0;
		int sumGreen = 0;
		int sumBlue = 0;
		final int x = blockPos.getX();
		final int y = blockPos.getY();
		final int z = blockPos.getZ();
		final BlockPosM posM = blockPosM;
		int r;
		int g;
		int b;

		for (r = x - 1; r <= x + 1; ++r) {
			for (g = z - 1; g <= z + 1; ++g) {
				posM.setXyz(r, y, g);
				b = colorizer.getColor(blockAccess, posM);
				sumRed += b >> 16 & 255;
				sumGreen += b >> 8 & 255;
				sumBlue += b & 255;
			}
		}

		r = sumRed / 9;
		g = sumGreen / 9;
		b = sumBlue / 9;
		return r << 16 | g << 8 | b;
	}

	public static int getFluidColor(final IBlockAccess blockAccess, final IBlockState blockState,
			final BlockPos blockPos, final RenderEnv renderEnv) {
		final Block block = blockState.getBlock();
		Object colorizer = getBlockColormap(blockState);

		if (colorizer == null && block.getMaterial() == Material.water) {
			colorizer = COLORIZER_WATER;
		}

		return colorizer == null ? block.colorMultiplier(blockAccess, blockPos)
				: Config.isSmoothBiomes() && !((CustomColors.IColorizer) colorizer).isColorConstant()
						? getSmoothColorMultiplier(blockAccess, blockPos, (CustomColors.IColorizer) colorizer,
								renderEnv.getColorizerBlockPosM())
						: ((CustomColors.IColorizer) colorizer).getColor(blockAccess, blockPos);
	}

	public static void updatePortalFX(final EntityFX fx) {
		if (particlePortalColor >= 0) {
			final int col = particlePortalColor;
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			final float redF = red / 255.0F;
			final float greenF = green / 255.0F;
			final float blueF = blue / 255.0F;
			fx.setRBGColorF(redF, greenF, blueF);
		}
	}

	public static void updateMyceliumFX(final EntityFX fx) {
		if (myceliumParticleColors != null) {
			final int col = myceliumParticleColors.getColorRandom();
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			final float redF = red / 255.0F;
			final float greenF = green / 255.0F;
			final float blueF = blue / 255.0F;
			fx.setRBGColorF(redF, greenF, blueF);
		}
	}

	private static int getRedstoneColor(final IBlockState blockState) {
		if (redstoneColors == null) {
			return -1;
		} else {
			final int level = getRedstoneLevel(blockState, 15);
			final int col = redstoneColors.getColor(level);
			return col;
		}
	}

	public static void updateReddustFX(final EntityFX fx, final IBlockAccess blockAccess, final double x,
			final double y, final double z) {
		if (redstoneColors != null) {
			final IBlockState state = blockAccess.getBlockState(new BlockPos(x, y, z));
			final int level = getRedstoneLevel(state, 15);
			final int col = redstoneColors.getColor(level);
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			final float redF = red / 255.0F;
			final float greenF = green / 255.0F;
			final float blueF = blue / 255.0F;
			fx.setRBGColorF(redF, greenF, blueF);
		}
	}

	private static int getRedstoneLevel(final IBlockState state, final int def) {
		final Block block = state.getBlock();

		if (!(block instanceof BlockRedstoneWire)) {
			return def;
		} else {
			final Comparable val = state.getValue(BlockRedstoneWire.POWER);

			if (!(val instanceof Integer)) {
				return def;
			} else {
				final Integer valInt = (Integer) val;
				return valInt;
			}
		}
	}

	public static int getXpOrbColor(final float timer) {
		if (xpOrbColors == null) {
			return -1;
		} else {
			final int index = (int) ((MathHelper.sin(timer) + 1.0F) * (xpOrbColors.getLength() - 1) / 2.0D);
			final int col = xpOrbColors.getColor(index);
			return col;
		}
	}

	public static void updateWaterFX(final EntityFX fx, final IBlockAccess blockAccess, final double x, final double y,
			final double z) {
		if (waterColors != null || blockColormaps != null) {
			final BlockPos blockPos = new BlockPos(x, y, z);
			final RenderEnv renderEnv = RenderEnv.getInstance(blockAccess, BLOCK_STATE_WATER, blockPos);
			final int col = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockPos, renderEnv);
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			float redF = red / 255.0F;
			float greenF = green / 255.0F;
			float blueF = blue / 255.0F;

			if (particleWaterColor >= 0) {
				final int redDrop = particleWaterColor >> 16 & 255;
				final int greenDrop = particleWaterColor >> 8 & 255;
				final int blueDrop = particleWaterColor & 255;
				redF *= redDrop / 255.0F;
				greenF *= greenDrop / 255.0F;
				blueF *= blueDrop / 255.0F;
			}

			fx.setRBGColorF(redF, greenF, blueF);
		}
	}

	private static int getLilypadColorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
		return lilyPadColor < 0 ? Blocks.waterlily.colorMultiplier(blockAccess, blockPos) : lilyPadColor;
	}

	private static Vec3 getFogColorNether(final Vec3 col) {
		return fogColorNether == null ? col : fogColorNether;
	}

	private static Vec3 getFogColorEnd(final Vec3 col) {
		return fogColorEnd == null ? col : fogColorEnd;
	}

	private static Vec3 getSkyColorEnd(final Vec3 col) {
		return skyColorEnd == null ? col : skyColorEnd;
	}

	public static Vec3 getSkyColor(final Vec3 skyColor3d, final IBlockAccess blockAccess, final double x,
			final double y, final double z) {
		if (skyColors == null) {
			return skyColor3d;
		} else {
			final int col = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			float redF = red / 255.0F;
			float greenF = green / 255.0F;
			float blueF = blue / 255.0F;
			final float cRed = (float) skyColor3d.xCoord / 0.5F;
			final float cGreen = (float) skyColor3d.yCoord / 0.66275F;
			final float cBlue = (float) skyColor3d.zCoord;
			redF *= cRed;
			greenF *= cGreen;
			blueF *= cBlue;
			final Vec3 newCol = skyColorFader.getColor(redF, greenF, blueF);
			return newCol;
		}
	}

	private static Vec3 getFogColor(final Vec3 fogColor3d, final IBlockAccess blockAccess, final double x,
			final double y, final double z) {
		if (fogColors == null) {
			return fogColor3d;
		} else {
			final int col = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			float redF = red / 255.0F;
			float greenF = green / 255.0F;
			float blueF = blue / 255.0F;
			final float cRed = (float) fogColor3d.xCoord / 0.753F;
			final float cGreen = (float) fogColor3d.yCoord / 0.8471F;
			final float cBlue = (float) fogColor3d.zCoord;
			redF *= cRed;
			greenF *= cGreen;
			blueF *= cBlue;
			final Vec3 newCol = fogColorFader.getColor(redF, greenF, blueF);
			return newCol;
		}
	}

	public static Vec3 getUnderwaterColor(final IBlockAccess blockAccess, final double x, final double y,
			final double z) {
		if (underwaterColors == null) {
			return null;
		} else {
			final int col = underwaterColors.getColorSmooth(blockAccess, x, y, z, 3);
			final int red = col >> 16 & 255;
			final int green = col >> 8 & 255;
			final int blue = col & 255;
			final float redF = red / 255.0F;
			final float greenF = green / 255.0F;
			final float blueF = blue / 255.0F;
			final Vec3 newCol = underwaterColorFader.getColor(redF, greenF, blueF);
			return newCol;
		}
	}

	private static int getStemColorMultiplier(final Block blockStem, final IBlockAccess blockAccess,
			final BlockPos blockPos, final RenderEnv renderEnv) {
		CustomColormap colors = stemColors;

		if (blockStem == Blocks.pumpkin_stem && stemPumpkinColors != null) {
			colors = stemPumpkinColors;
		}

		if (blockStem == Blocks.melon_stem && stemMelonColors != null) {
			colors = stemMelonColors;
		}

		if (colors == null) {
			return -1;
		} else {
			final int level = renderEnv.getMetadata();
			return colors.getColor(level);
		}
	}

	public static boolean updateLightmap(final World world, final float torchFlickerX, final int[] lmColors,
			final boolean nightvision) {
		if (world == null) {
			return false;
		} else if (lightMapsColorsRgb == null) {
			return false;
		} else {
			final int dimensionId = world.provider.getDimensionId();
			final int lightMapIndex = dimensionId - lightmapMinDimensionId;

			if (lightMapIndex >= 0 && lightMapIndex < lightMapsColorsRgb.length) {
				final CustomColormap lightMapRgb = lightMapsColorsRgb[lightMapIndex];

				if (lightMapRgb == null) {
					return false;
				} else {
					final int height = lightMapRgb.getHeight();

					if (nightvision && height < 64) {
						return false;
					} else {
						final int width = lightMapRgb.getWidth();

						if (width < 16) {
							warn("Invalid lightmap width: " + width + " for dimension: " + dimensionId);
							lightMapsColorsRgb[lightMapIndex] = null;
							return false;
						} else {
							int startIndex = 0;

							if (nightvision) {
								startIndex = width * 16 * 2;
							}

							float sun = 1.1666666F * (world.getSunBrightness(1.0F) - 0.2F);

							if (world.func_175658_ac() > 0) {
								sun = 1.0F;
							}

							sun = Config.limitTo1(sun);
							final float sunX = sun * (width - 1);
							final float torchX = Config.limitTo1(torchFlickerX + 0.5F) * (width - 1);
							final float gamma = Config.limitTo1(Config.getGameSettings().gammaSetting);
							final boolean hasGamma = gamma > 1.0E-4F;
							final float[][] colorsRgb = lightMapRgb.getColorsRgb();
							getLightMapColumn(colorsRgb, sunX, startIndex, width, sunRgbs);
							getLightMapColumn(colorsRgb, torchX, startIndex + 16 * width, width, torchRgbs);
							final float[] rgb = new float[3];

							for (int is = 0; is < 16; ++is) {
								for (int it = 0; it < 16; ++it) {
									int r;

									for (r = 0; r < 3; ++r) {
										float g = Config.limitTo1(sunRgbs[is][r] + torchRgbs[it][r]);

										if (hasGamma) {
											float b = 1.0F - g;
											b = 1.0F - b * b * b * b;
											g = gamma * b + (1.0F - gamma) * g;
										}

										rgb[r] = g;
									}

									r = (int) (rgb[0] * 255.0F);
									final int var22 = (int) (rgb[1] * 255.0F);
									final int var23 = (int) (rgb[2] * 255.0F);
									lmColors[is * 16 + it] = -16777216 | r << 16 | var22 << 8 | var23;
								}
							}

							return true;
						}
					}
				}
			} else {
				return false;
			}
		}
	}

	private static void getLightMapColumn(final float[][] origMap, final float x, final int offset, final int width,
			final float[][] colRgb) {
		final int xLow = (int) Math.floor(x);
		final int xHigh = (int) Math.ceil(x);

		if (xLow == xHigh) {
			for (int var14 = 0; var14 < 16; ++var14) {
				final float[] var15 = origMap[offset + var14 * width + xLow];
				final float[] var16 = colRgb[var14];

				for (int var17 = 0; var17 < 3; ++var17) {
					var16[var17] = var15[var17];
				}
			}
		} else {
			final float dLow = 1.0F - (x - xLow);
			final float dHigh = 1.0F - (xHigh - x);

			for (int y = 0; y < 16; ++y) {
				final float[] rgbLow = origMap[offset + y * width + xLow];
				final float[] rgbHigh = origMap[offset + y * width + xHigh];
				final float[] rgb = colRgb[y];

				for (int i = 0; i < 3; ++i) {
					rgb[i] = rgbLow[i] * dLow + rgbHigh[i] * dHigh;
				}
			}
		}
	}

	public static Vec3 getWorldFogColor(Vec3 fogVec, final WorldClient world, final Entity renderViewEntity,
			final float partialTicks) {
		final int worldType = world.provider.getDimensionId();

		switch (worldType) {
			case -1:
				fogVec = getFogColorNether(fogVec);
				break;

			case 0:

				fogVec = getFogColor(fogVec, Minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D,
						renderViewEntity.posZ);
				break;

			case 1:
				fogVec = getFogColorEnd(fogVec);
		}

		return fogVec;
	}

	public static Vec3 getWorldSkyColor(Vec3 skyVec, final WorldClient world, final Entity renderViewEntity,
			final float partialTicks) {
		final int worldType = world.provider.getDimensionId();

		switch (worldType) {
			case 0:

				skyVec = getSkyColor(skyVec, Minecraft.theWorld, renderViewEntity.posX, renderViewEntity.posY + 1.0D,
						renderViewEntity.posZ);
				break;

			case 1:
				skyVec = getSkyColorEnd(skyVec);
		}

		return skyVec;
	}

	private static int[] readSpawnEggColors(final Properties props, final String fileName, final String prefix,
			final String logName) {
		final ArrayList list = new ArrayList();
		final Set keys = props.keySet();
		int countColors = 0;
		final Iterator colors = keys.iterator();

		while (colors.hasNext()) {
			final String i = (String) colors.next();
			final String value = props.getProperty(i);

			if (i.startsWith(prefix)) {
				final String name = StrUtils.removePrefix(i, prefix);
				final int id = getEntityId(name);
				final int color = parseColor(value);

				if (id >= 0 && color >= 0) {
					while (list.size() <= id) {
						list.add(-1);
					}

					list.set(id, color);
					++countColors;
				} else {
					warn("Invalid spawn egg color: " + i + " = " + value);
				}
			}
		}

		if (countColors <= 0) {
			return null;
		} else {
			dbg(logName + " colors: " + countColors);
			final int[] var13 = new int[list.size()];

			for (int var14 = 0; var14 < var13.length; ++var14) {
				var13[var14] = ((Integer) list.get(var14));
			}

			return var13;
		}
	}

	private static int getSpawnEggColor(final ItemMonsterPlacer item, final ItemStack itemStack, final int layer,
			final int color) {
		final int id = itemStack.getMetadata();
		final int[] eggColors = layer == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;

		if (eggColors == null) {
			return color;
		} else if (id >= 0 && id < eggColors.length) {
			final int eggColor = eggColors[id];
			return eggColor < 0 ? color : eggColor;
		} else {
			return color;
		}
	}

	public static int getColorFromItemStack(final ItemStack itemStack, final int layer, final int color) {
		if (itemStack == null) {
			return color;
		} else {
			final Item item = itemStack.getItem();
			return item == null ? color
					: item instanceof ItemMonsterPlacer
							? getSpawnEggColor((ItemMonsterPlacer) item, itemStack, layer, color) : color;
		}
	}

	private static float[][] readDyeColors(final Properties props, final String fileName, final String prefix,
			final String logName) {
		final EnumDyeColor[] dyeValues = EnumDyeColor.values();
		final HashMap mapDyes = new HashMap();

		for (final EnumDyeColor countColors : dyeValues) {
			mapDyes.put(countColors.getName(), countColors);
		}

		final float[][] var16 = new float[dyeValues.length][];
		int var17 = 0;
		final Set keys = props.keySet();
		final Iterator iter = keys.iterator();

		while (iter.hasNext()) {
			final String key = (String) iter.next();
			final String value = props.getProperty(key);

			if (key.startsWith(prefix)) {
				String name = StrUtils.removePrefix(key, prefix);

				if (name.equals("lightBlue")) {
					name = "light_blue";
				}

				final EnumDyeColor dye = (EnumDyeColor) mapDyes.get(name);
				final int color = parseColor(value);

				if (dye != null && color >= 0) {
					final float[] rgb = new float[] { (color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F,
							(color & 255) / 255.0F };
					var16[dye.ordinal()] = rgb;
					++var17;
				} else {
					warn("Invalid color: " + key + " = " + value);
				}
			}
		}

		if (var17 <= 0) {
			return null;
		} else {
			dbg(logName + " colors: " + var17);
			return var16;
		}
	}

	private static float[] getDyeColors(final EnumDyeColor dye, final float[][] dyeColors, final float[] colors) {
		if (dyeColors == null) {
			return colors;
		} else if (dye == null) {
			return colors;
		} else {
			final float[] customColors = dyeColors[dye.ordinal()];
			return customColors == null ? colors : customColors;
		}
	}

	public static float[] getWolfCollarColors(final EnumDyeColor dye, final float[] colors) {
		return getDyeColors(dye, wolfCollarColors, colors);
	}

	public static float[] getSheepColors(final EnumDyeColor dye, final float[] colors) {
		return getDyeColors(dye, sheepColors, colors);
	}

	private static int[] readTextColors(final Properties props, final String fileName, final String prefix,
			final String logName) {
		final int[] colors = new int[32];
		Arrays.fill(colors, -1);
		int countColors = 0;
		final Set keys = props.keySet();
		final Iterator iter = keys.iterator();

		while (iter.hasNext()) {
			final String key = (String) iter.next();
			final String value = props.getProperty(key);

			if (key.startsWith(prefix)) {
				final String name = StrUtils.removePrefix(key, prefix);
				final int code = Config.parseInt(name, -1);
				final int color = parseColor(value);

				if (code >= 0 && code < colors.length && color >= 0) {
					colors[code] = color;
					++countColors;
				} else {
					warn("Invalid color: " + key + " = " + value);
				}
			}
		}

		if (countColors <= 0) {
			return null;
		} else {
			dbg(logName + " colors: " + countColors);
			return colors;
		}
	}

	public static int getTextColor(final int index, final int color) {
		if (textColors == null) {
			return color;
		} else if (index >= 0 && index < textColors.length) {
			final int customColor = textColors[index];
			return customColor < 0 ? color : customColor;
		} else {
			return color;
		}
	}

	private static int[] readMapColors(final Properties props, final String fileName, final String prefix,
			final String logName) {
		final int[] colors = new int[MapColor.mapColorArray.length];
		Arrays.fill(colors, -1);
		int countColors = 0;
		final Set keys = props.keySet();
		final Iterator iter = keys.iterator();

		while (iter.hasNext()) {
			final String key = (String) iter.next();
			final String value = props.getProperty(key);

			if (key.startsWith(prefix)) {
				final String name = StrUtils.removePrefix(key, prefix);
				final int index = getMapColorIndex(name);
				final int color = parseColor(value);

				if (index >= 0 && index < colors.length && color >= 0) {
					colors[index] = color;
					++countColors;
				} else {
					warn("Invalid color: " + key + " = " + value);
				}
			}
		}

		if (countColors <= 0) {
			return null;
		} else {
			dbg(logName + " colors: " + countColors);
			return colors;
		}
	}

	private static int[] readPotionColors(final Properties props, final String fileName, final String prefix,
			final String logName) {
		final int[] colors = new int[Potion.potionTypes.length];
		Arrays.fill(colors, -1);
		int countColors = 0;
		final Set keys = props.keySet();
		final Iterator iter = keys.iterator();

		while (iter.hasNext()) {
			final String key = (String) iter.next();
			final String value = props.getProperty(key);

			if (key.startsWith(prefix)) {
				final int index = getPotionId(key);
				final int color = parseColor(value);

				if (index >= 0 && index < colors.length && color >= 0) {
					colors[index] = color;
					++countColors;
				} else {
					warn("Invalid color: " + key + " = " + value);
				}
			}
		}

		if (countColors <= 0) {
			return null;
		} else {
			dbg(logName + " colors: " + countColors);
			return colors;
		}
	}

	private static int getPotionId(final String name) {
		if (name.equals("potion.water")) {
			return 0;
		} else {
			final Potion[] potions = Potion.potionTypes;

			for (final Potion potion : potions) {
				if (potion != null && potion.getName().equals(name)) {
					return potion.getId();
				}
			}

			return -1;
		}
	}

	public static int getPotionColor(final int potionId, final int color) {
		if (potionColors == null) {
			return color;
		} else if (potionId >= 0 && potionId < potionColors.length) {
			final int potionColor = potionColors[potionId];
			return potionColor < 0 ? color : potionColor;
		} else {
			return color;
		}
	}

	private static int getMapColorIndex(final String name) {
		return name == null ? -1
				: name.equals("air") ? MapColor.airColor.colorIndex
						: name.equals("grass") ? MapColor.grassColor.colorIndex
								: name.equals("sand") ? MapColor.sandColor.colorIndex
										: name.equals("cloth") ? MapColor.clothColor.colorIndex
												: name.equals("tnt") ? MapColor.tntColor.colorIndex
														: name.equals("ice") ? MapColor.iceColor.colorIndex
																: name.equals("iron") ? MapColor.ironColor.colorIndex
																		: name.equals("foliage")
																				? MapColor.foliageColor.colorIndex
																				: name.equals("snow")
																						? MapColor.snowColor.colorIndex
																						: name.equals("clay")
																								? MapColor.clayColor.colorIndex
																								: name.equals("dirt")
																										? MapColor.dirtColor.colorIndex
																										: name.equals(
																												"stone") ? MapColor.stoneColor.colorIndex
																														: name.equals(
																																"water") ? MapColor.waterColor.colorIndex
																																		: name.equals(
																																				"wood") ? MapColor.woodColor.colorIndex
																																						: name.equals(
																																								"quartz")
																																										? MapColor.quartzColor.colorIndex
																																										: name.equals(
																																												"adobe") ? MapColor.adobeColor.colorIndex
																																														: name.equals(
																																																"magenta")
																																																		? MapColor.magentaColor.colorIndex
																																																		: name.equals(
																																																				"lightBlue")
																																																						? MapColor.lightBlueColor.colorIndex
																																																						: name.equals(
																																																								"light_blue")
																																																										? MapColor.lightBlueColor.colorIndex
																																																										: name.equals(
																																																												"yellow")
																																																														? MapColor.yellowColor.colorIndex
																																																														: name.equals(
																																																																"lime") ? MapColor.limeColor.colorIndex
																																																																		: name.equals(
																																																																				"pink") ? MapColor.pinkColor.colorIndex
																																																																						: name.equals(
																																																																								"gray") ? MapColor.grayColor.colorIndex
																																																																										: name.equals(
																																																																												"silver")
																																																																														? MapColor.silverColor.colorIndex
																																																																														: name.equals(
																																																																																"cyan") ? MapColor.cyanColor.colorIndex
																																																																																		: name.equals(
																																																																																				"purple")
																																																																																						? MapColor.purpleColor.colorIndex
																																																																																						: name.equals(
																																																																																								"blue") ? MapColor.blueColor.colorIndex
																																																																																										: name.equals(
																																																																																												"brown") ? MapColor.brownColor.colorIndex
																																																																																														: name.equals(
																																																																																																"green") ? MapColor.greenColor.colorIndex
																																																																																																		: name.equals(
																																																																																																				"red") ? MapColor.redColor.colorIndex
																																																																																																						: name.equals(
																																																																																																								"black") ? MapColor.blackColor.colorIndex
																																																																																																										: name.equals(
																																																																																																												"gold") ? MapColor.goldColor.colorIndex
																																																																																																														: name.equals(
																																																																																																																"diamond")
																																																																																																																		? MapColor.diamondColor.colorIndex
																																																																																																																		: name.equals(
																																																																																																																				"lapis") ? MapColor.lapisColor.colorIndex
																																																																																																																						: name.equals(
																																																																																																																								"emerald")
																																																																																																																										? MapColor.emeraldColor.colorIndex
																																																																																																																										: name.equals(
																																																																																																																												"obsidian")
																																																																																																																														? MapColor.obsidianColor.colorIndex
																																																																																																																														: name.equals(
																																																																																																																																"netherrack")
																																																																																																																																		? MapColor.netherrackColor.colorIndex
																																																																																																																																		: -1;
	}

	private static int[] getMapColors() {
		final MapColor[] mapColors = MapColor.mapColorArray;
		final int[] colors = new int[mapColors.length];
		Arrays.fill(colors, -1);

		for (int i = 0; i < mapColors.length && i < colors.length; ++i) {
			final MapColor mapColor = mapColors[i];

			if (mapColor != null) {
				colors[i] = mapColor.colorValue;
			}
		}

		return colors;
	}

	private static void setMapColors(final int[] colors) {
		if (colors != null) {
			final MapColor[] mapColors = MapColor.mapColorArray;

			for (int i = 0; i < mapColors.length && i < colors.length; ++i) {
				final MapColor mapColor = mapColors[i];

				if (mapColor != null) {
					final int color = colors[i];

					if (color >= 0) {
						mapColor.colorValue = color;
					}
				}
			}
		}
	}

	private static int getEntityId(final String name) {
		if (name == null) {
			return -1;
		} else {
			final int id = EntityList.func_180122_a(name);

			if (id < 0) {
				return -1;
			} else {
				final String idName = EntityList.getStringFromID(id);
				return !Config.equals(name, idName) ? -1 : id;
			}
		}
	}

	private static void dbg(final String str) {
		Config.dbg("CustomColors: " + str);
	}

	private static void warn(final String str) {
		Config.warn("CustomColors: " + str);
	}

	public static int getExpBarTextColor(final int color) {
		return expBarTextColor < 0 ? color : expBarTextColor;
	}

	public static int getBossTextColor(final int color) {
		return bossTextColor < 0 ? color : bossTextColor;
	}

	public static int getSignTextColor(final int color) {
		return signTextColor < 0 ? color : signTextColor;
	}

	public interface IColorizer {
		int getColor(IBlockAccess var1, BlockPos var2);

		boolean isColorConstant();
	}
}
