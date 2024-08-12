package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomColormap implements CustomColors.IColorizer {

public static final int EaZy = 1890;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String name = null;
	public String basePath = null;
	private int format = -1;
	private MatchBlock[] matchBlocks = null;
	private String source = null;
	private int color = -1;
	private int yVariance = 0;
	private int yOffset = 0;
	private int width = 0;
	private int height = 0;
	private int[] colors = null;
	private float[][] colorsRgb = null;
	public static final String KEY_FORMAT = "format";
	public static final String KEY_BLOCKS = "blocks";
	public static final String KEY_SOURCE = "source";
	public static final String KEY_COLOR = "color";
	public static final String KEY_Y_VARIANCE = "yVariance";
	public static final String KEY_Y_OFFSET = "yOffset";

	public CustomColormap(final Properties props, final String path, final int width, final int height) {
		final ConnectedParser cp = new ConnectedParser("Colormap");
		name = cp.parseName(path);
		basePath = cp.parseBasePath(path);
		format = parseFormat(props.getProperty("format"));
		matchBlocks = cp.parseMatchBlocks(props.getProperty("blocks"));
		source = parseTexture(props.getProperty("source"), path, basePath);
		color = ConnectedParser.parseColor(props.getProperty("color"), -1);
		yVariance = cp.parseInt(props.getProperty("yVariance"), 0);
		yOffset = cp.parseInt(props.getProperty("yOffset"), 0);
		this.width = width;
		this.height = height;
	}

	private int parseFormat(final String str) {
		if (str == null) {
			return 0;
		} else if (str.equals("vanilla")) {
			return 0;
		} else if (str.equals("grid")) {
			return 1;
		} else if (str.equals("fixed")) {
			return 2;
		} else {
			warn("Unknown format: " + str);
			return -1;
		}
	}

	public boolean isValid(final String path) {
		if (format != 0 && format != 1) {
			if (format != 2) {
				return false;
			}

			if (color < 0) {
				color = 16777215;
			}
		} else {
			if (source == null) {
				warn("Source not defined: " + path);
				return false;
			}

			readColors();

			if (colors == null) {
				return false;
			}

			if (color < 0) {
				if (format == 0) {
					color = this.getColor(127, 127);
				}

				if (format == 1) {
					color = getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
				}
			}
		}

		return true;
	}

	public boolean isValidMatchBlocks(final String path) {
		if (matchBlocks == null) {
			matchBlocks = detectMatchBlocks();

			if (matchBlocks == null) {
				warn("Match blocks not defined: " + path);
				return false;
			}
		}

		return true;
	}

	private MatchBlock[] detectMatchBlocks() {
		final Block block = Block.getBlockFromName(name);

		if (block != null) {
			return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
		} else {
			final Pattern p = Pattern.compile("^block([0-9]+).*$");
			final Matcher m = p.matcher(name);

			if (m.matches()) {
				final String cp = m.group(1);
				final int mbs = Config.parseInt(cp, -1);

				if (mbs >= 0) {
					return new MatchBlock[] { new MatchBlock(mbs) };
				}
			}

			final ConnectedParser cp1 = new ConnectedParser("Colormap");
			final MatchBlock[] mbs1 = cp1.parseMatchBlock(name);
			return mbs1 != null ? mbs1 : null;
		}
	}

	private void readColors() {
		try {
			colors = null;

			if (source == null) {
				return;
			}

			final String e = source + ".png";
			final ResourceLocation loc = new ResourceLocation(e);
			final InputStream is = Config.getResourceStream(loc);

			if (is == null) {
				return;
			}

			final BufferedImage img = TextureUtil.func_177053_a(is);

			if (img == null) {
				return;
			}

			final int imgWidth = img.getWidth();
			final int imgHeight = img.getHeight();
			final boolean widthOk = width < 0 || width == imgWidth;
			final boolean heightOk = height < 0 || height == imgHeight;

			if (!widthOk || !heightOk) {
				dbg("Non-standard palette size: " + imgWidth + "x" + imgHeight + ", should be: " + width + "x" + height
						+ ", path: " + e);
			}

			width = imgWidth;
			height = imgHeight;

			if (width <= 0 || height <= 0) {
				warn("Invalid palette size: " + imgWidth + "x" + imgHeight + ", path: " + e);
				return;
			}

			colors = new int[imgWidth * imgHeight];
			img.getRGB(0, 0, imgWidth, imgHeight, colors, 0, imgWidth);
		} catch (final IOException var9) {
			var9.printStackTrace();
		}
	}

	private static void dbg(final String str) {
		Config.dbg("CustomColors: " + str);
	}

	private static void warn(final String str) {
		Config.warn("CustomColors: " + str);
	}

	private static String parseTexture(String texStr, final String path, final String basePath) {
		String str;

		if (texStr != null) {
			str = ".png";

			if (texStr.endsWith(str)) {
				texStr = texStr.substring(0, texStr.length() - str.length());
			}

			texStr = fixTextureName(texStr, basePath);
			return texStr;
		} else {
			str = path;
			final int pos = path.lastIndexOf(47);

			if (pos >= 0) {
				str = path.substring(pos + 1);
			}

			final int pos2 = str.lastIndexOf(46);

			if (pos2 >= 0) {
				str = str.substring(0, pos2);
			}

			str = fixTextureName(str, basePath);
			return str;
		}
	}

	private static String fixTextureName(String iconName, final String basePath) {
		iconName = TextureUtils.fixResourcePath(iconName, basePath);

		if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("mcpatcher/")) {
			iconName = basePath + "/" + iconName;
		}

		if (iconName.endsWith(".png")) {
			iconName = iconName.substring(0, iconName.length() - 4);
		}

		final String pathBlocks = "textures/blocks/";

		if (iconName.startsWith(pathBlocks)) {
			iconName = iconName.substring(pathBlocks.length());
		}

		if (iconName.startsWith("/")) {
			iconName = iconName.substring(1);
		}

		return iconName;
	}

	public boolean matchesBlock(final BlockStateBase blockState) {
		return Matches.block(blockState, matchBlocks);
	}

	public int getColorRandom() {
		if (format == 2) {
			return color;
		} else {
			final int index = CustomColors.random.nextInt(colors.length);
			return colors[index];
		}
	}

	public int getColor(int index) {
		index = Config.limit(index, 0, colors.length);
		return colors[index] & 16777215;
	}

	public int getColor(int cx, int cy) {
		cx = Config.limit(cx, 0, width - 1);
		cy = Config.limit(cy, 0, height - 1);
		return colors[cy * width + cx] & 16777215;
	}

	public float[][] getColorsRgb() {
		if (colorsRgb == null) {
			colorsRgb = toRgb(colors);
		}

		return colorsRgb;
	}

	@Override
	public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
		final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
		return this.getColor(biome, blockPos);
	}

	@Override
	public boolean isColorConstant() {
		return format == 2;
	}

	public int getColor(final BiomeGenBase biome, final BlockPos blockPos) {
		return format == 0 ? getColorVanilla(biome, blockPos) : format == 1 ? getColorGrid(biome, blockPos) : color;
	}

	public int getColorSmooth(final IBlockAccess blockAccess, final double x, final double y, final double z,
			final int radius) {
		if (format == 2) {
			return color;
		} else {
			final int x0 = MathHelper.floor_double(x);
			final int y0 = MathHelper.floor_double(y);
			final int z0 = MathHelper.floor_double(z);
			int sumRed = 0;
			int sumGreen = 0;
			int sumBlue = 0;
			int count = 0;
			final BlockPosM blockPosM = new BlockPosM(0, 0, 0);
			int r;
			int g;
			int b;

			for (r = x0 - radius; r <= x0 + radius; ++r) {
				for (g = z0 - radius; g <= z0 + radius; ++g) {
					blockPosM.setXyz(r, y0, g);
					b = this.getColor(blockAccess, blockPosM);
					sumRed += b >> 16 & 255;
					sumGreen += b >> 8 & 255;
					sumBlue += b & 255;
					++count;
				}
			}

			r = sumRed / count;
			g = sumGreen / count;
			b = sumBlue / count;
			return r << 16 | g << 8 | b;
		}
	}

	private int getColorVanilla(final BiomeGenBase biome, final BlockPos blockPos) {
		final double temperature = MathHelper.clamp_float(biome.func_180626_a(blockPos), 0.0F, 1.0F);
		double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
		rainfall *= temperature;
		final int cx = (int) ((1.0D - temperature) * (width - 1));
		final int cy = (int) ((1.0D - rainfall) * (height - 1));
		return this.getColor(cx, cy);
	}

	private int getColorGrid(final BiomeGenBase biome, final BlockPos blockPos) {
		final int cx = biome.biomeID;
		int cy = blockPos.getY() - yOffset;

		if (yVariance > 0) {
			final int seed = blockPos.getX() << 16 + blockPos.getZ();
			final int rand = Config.intHash(seed);
			final int range = yVariance * 2 + 1;
			final int diff = (rand & 255) % range - yVariance;
			cy += diff;
		}

		return this.getColor(cx, cy);
	}

	public int getLength() {
		return format == 2 ? 1 : colors.length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private static float[][] toRgb(final int[] cols) {
		final float[][] colsRgb = new float[cols.length][3];

		for (int i = 0; i < cols.length; ++i) {
			final int col = cols[i];
			final float rf = (col >> 16 & 255) / 255.0F;
			final float gf = (col >> 8 & 255) / 255.0F;
			final float bf = (col & 255) / 255.0F;
			final float[] colRgb = colsRgb[i];
			colRgb[0] = rf;
			colRgb[1] = gf;
			colRgb[2] = bf;
		}

		return colsRgb;
	}

	public void addMatchBlock(final MatchBlock mb) {
		if (matchBlocks == null) {
			matchBlocks = new MatchBlock[0];
		}

		matchBlocks = (MatchBlock[]) Config.addObjectToArray(matchBlocks, mb);
	}

	public void addMatchBlock(final int blockId, final int metadata) {
		final MatchBlock mb = getMatchBlock(blockId);

		if (mb != null) {
			if (metadata >= 0) {
				mb.addMetadata(metadata);
			}
		} else {
			this.addMatchBlock(new MatchBlock(blockId, metadata));
		}
	}

	private MatchBlock getMatchBlock(final int blockId) {
		if (matchBlocks == null) {
			return null;
		} else {
			for (final MatchBlock mb : matchBlocks) {
				if (mb.getBlockId() == blockId) {
					return mb;
				}
			}

			return null;
		}
	}

	public int[] getMatchBlockIds() {
		if (matchBlocks == null) {
			return null;
		} else {
			final HashSet setIds = new HashSet();

			for (final MatchBlock ids : matchBlocks) {
				if (ids.getBlockId() >= 0) {
					setIds.add(ids.getBlockId());
				}
			}

			final Integer[] var5 = (Integer[]) setIds.toArray(new Integer[setIds.size()]);
			final int[] var6 = new int[var5.length];

			for (int i = 0; i < var5.length; ++i) {
				var6[i] = var5[i];
			}

			return var6;
		}
	}

	@Override
	public String toString() {
		return "" + basePath + "/" + name + ", blocks: " + Config.arrayToString(matchBlocks) + ", source: " + source;
	}
}
