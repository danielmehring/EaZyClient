package optifine;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

public class ConnectedProperties {

public static final int EaZy = 1886;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String name = null;
	public String basePath = null;
	public MatchBlock[] matchBlocks = null;
	public int[] metadatas = null;
	public String[] matchTiles = null;
	public int method = 0;
	public String[] tiles = null;
	public int connect = 0;
	public int faces = 63;
	public BiomeGenBase[] biomes = null;
	public int minHeight = 0;
	public int maxHeight = 1024;
	public int renderPass = 0;
	public boolean innerSeams = false;
	public int width = 0;
	public int height = 0;
	public int[] weights = null;
	public int symmetry = 1;
	public int[] sumWeights = null;
	public int sumAllWeights = 1;
	public TextureAtlasSprite[] matchTileIcons = null;
	public TextureAtlasSprite[] tileIcons = null;
	public static final int METHOD_NONE = 0;
	public static final int METHOD_CTM = 1;
	public static final int METHOD_HORIZONTAL = 2;
	public static final int METHOD_TOP = 3;
	public static final int METHOD_RANDOM = 4;
	public static final int METHOD_REPEAT = 5;
	public static final int METHOD_VERTICAL = 6;
	public static final int METHOD_FIXED = 7;
	public static final int METHOD_HORIZONTAL_VERTICAL = 8;
	public static final int METHOD_VERTICAL_HORIZONTAL = 9;
	public static final int CONNECT_NONE = 0;
	public static final int CONNECT_BLOCK = 1;
	public static final int CONNECT_TILE = 2;
	public static final int CONNECT_MATERIAL = 3;
	public static final int CONNECT_UNKNOWN = 128;
	public static final int FACE_BOTTOM = 1;
	public static final int FACE_TOP = 2;
	public static final int FACE_NORTH = 4;
	public static final int FACE_SOUTH = 8;
	public static final int FACE_WEST = 16;
	public static final int FACE_EAST = 32;
	public static final int FACE_SIDES = 60;
	public static final int FACE_ALL = 63;
	public static final int FACE_UNKNOWN = 128;
	public static final int SYMMETRY_NONE = 1;
	public static final int SYMMETRY_OPPOSITE = 2;
	public static final int SYMMETRY_ALL = 6;
	public static final int SYMMETRY_UNKNOWN = 128;

	public ConnectedProperties(final Properties props, final String path) {
		final ConnectedParser cp = new ConnectedParser("ConnectedTextures");
		name = cp.parseName(path);
		basePath = cp.parseBasePath(path);
		matchBlocks = cp.parseMatchBlocks(props.getProperty("matchBlocks"));
		metadatas = cp.parseIntList(props.getProperty("metadata"));
		matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
		method = parseMethod(props.getProperty("method"));
		tiles = parseTileNames(props.getProperty("tiles"));
		connect = parseConnect(props.getProperty("connect"));
		faces = parseFaces(props.getProperty("faces"));
		biomes = cp.parseBiomes(props.getProperty("biomes"));
		minHeight = cp.parseInt(props.getProperty("minHeight"), -1);
		maxHeight = cp.parseInt(props.getProperty("maxHeight"), 1024);
		renderPass = cp.parseInt(props.getProperty("renderPass"));
		innerSeams = ConnectedParser.parseBoolean(props.getProperty("innerSeams"));
		width = cp.parseInt(props.getProperty("width"));
		height = cp.parseInt(props.getProperty("height"));
		weights = cp.parseIntList(props.getProperty("weights"));
		symmetry = parseSymmetry(props.getProperty("symmetry"));
	}

	private String[] parseMatchTiles(final String str) {
		if (str == null) {
			return null;
		} else {
			final String[] names = Config.tokenize(str, " ");

			for (int i = 0; i < names.length; ++i) {
				String iconName = names[i];

				if (iconName.endsWith(".png")) {
					iconName = iconName.substring(0, iconName.length() - 4);
				}

				iconName = TextureUtils.fixResourcePath(iconName, basePath);
				names[i] = iconName;
			}

			return names;
		}
	}

	private String[] parseTileNames(final String str) {
		if (str == null) {
			return null;
		} else {
			final ArrayList list = new ArrayList();
			final String[] iconStrs = Config.tokenize(str, " ,");
			label67:

			for (int names = 0; names < iconStrs.length; ++names) {
				final String i = iconStrs[names];

				if (i.contains("-")) {
					final String[] iconName = Config.tokenize(i, "-");

					if (iconName.length == 2) {
						final int pathBlocks = Config.parseInt(iconName[0], -1);
						final int max = Config.parseInt(iconName[1], -1);

						if (pathBlocks >= 0 && max >= 0) {
							if (pathBlocks <= max) {
								int n = pathBlocks;

								while (true) {
									if (n > max) {
										continue label67;
									}

									list.add(String.valueOf(n));
									++n;
								}
							}

							Config.warn("Invalid interval: " + i + ", when parsing: " + str);
							continue;
						}
					}
				}

				list.add(i);
			}

			final String[] var10 = (String[]) list.toArray(new String[list.size()]);

			for (int var11 = 0; var11 < var10.length; ++var11) {
				String var12 = var10[var11];
				var12 = TextureUtils.fixResourcePath(var12, basePath);

				if (!var12.startsWith(basePath) && !var12.startsWith("textures/") && !var12.startsWith("mcpatcher/")) {
					var12 = basePath + "/" + var12;
				}

				if (var12.endsWith(".png")) {
					var12 = var12.substring(0, var12.length() - 4);
				}

				final String var13 = "textures/blocks/";

				if (var12.startsWith(var13)) {
					var12 = var12.substring(var13.length());
				}

				if (var12.startsWith("/")) {
					var12 = var12.substring(1);
				}

				var10[var11] = var12;
			}

			return var10;
		}
	}

	private static int parseSymmetry(final String str) {
		if (str == null) {
			return 1;
		} else if (str.equals("opposite")) {
			return 2;
		} else if (str.equals("all")) {
			return 6;
		} else {
			Config.warn("Unknown symmetry: " + str);
			return 1;
		}
	}

	private static int parseFaces(final String str) {
		if (str == null) {
			return 63;
		} else {
			final String[] faceStrs = Config.tokenize(str, " ,");
			int facesMask = 0;

			for (final String faceStr : faceStrs) {
				final int faceMask = parseFace(faceStr);
				facesMask |= faceMask;
			}

			return facesMask;
		}
	}

	private static int parseFace(String str) {
		str = str.toLowerCase();

		if (!str.equals("bottom") && !str.equals("down")) {
			if (!str.equals("top") && !str.equals("up")) {
				if (str.equals("north")) {
					return 4;
				} else if (str.equals("south")) {
					return 8;
				} else if (str.equals("east")) {
					return 32;
				} else if (str.equals("west")) {
					return 16;
				} else if (str.equals("sides")) {
					return 60;
				} else if (str.equals("all")) {
					return 63;
				} else {
					Config.warn("Unknown face: " + str);
					return 128;
				}
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}

	private static int parseConnect(final String str) {
		if (str == null) {
			return 0;
		} else if (str.equals("block")) {
			return 1;
		} else if (str.equals("tile")) {
			return 2;
		} else if (str.equals("material")) {
			return 3;
		} else {
			Config.warn("Unknown connect: " + str);
			return 128;
		}
	}

	public static IProperty getProperty(final String key, final Collection properties) {
		final Iterator it = properties.iterator();
		IProperty prop;

		do {
			if (!it.hasNext()) {
				return null;
			}

			prop = (IProperty) it.next();
		}
		while (!key.equals(prop.getName()));

		return prop;
	}

	private static int parseMethod(final String str) {
		if (str == null) {
			return 1;
		} else if (!str.equals("ctm") && !str.equals("glass")) {
			if (!str.equals("horizontal") && !str.equals("bookshelf")) {
				if (str.equals("vertical")) {
					return 6;
				} else if (str.equals("top")) {
					return 3;
				} else if (str.equals("random")) {
					return 4;
				} else if (str.equals("repeat")) {
					return 5;
				} else if (str.equals("fixed")) {
					return 7;
				} else if (!str.equals("horizontal+vertical") && !str.equals("h+v")) {
					if (!str.equals("vertical+horizontal") && !str.equals("v+h")) {
						Config.warn("Unknown method: " + str);
						return 0;
					} else {
						return 9;
					}
				} else {
					return 8;
				}
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}

	public boolean isValid(final String path) {
		if (name != null && name.length() > 0) {
			if (basePath == null) {
				Config.warn("No base path found: " + path);
				return false;
			} else {
				if (matchBlocks == null) {
					matchBlocks = detectMatchBlocks();
				}

				if (matchTiles == null && matchBlocks == null) {
					matchTiles = detectMatchTiles();
				}

				if (matchBlocks == null && matchTiles == null) {
					Config.warn("No matchBlocks or matchTiles specified: " + path);
					return false;
				} else if (method == 0) {
					Config.warn("No method: " + path);
					return false;
				} else if (tiles != null && tiles.length > 0) {
					if (connect == 0) {
						connect = detectConnect();
					}

					if (connect == 128) {
						Config.warn("Invalid connect in: " + path);
						return false;
					} else if (renderPass > 0) {
						Config.warn("Render pass not supported: " + renderPass);
						return false;
					} else if ((faces & 128) != 0) {
						Config.warn("Invalid faces in: " + path);
						return false;
					} else if ((symmetry & 128) != 0) {
						Config.warn("Invalid symmetry in: " + path);
						return false;
					} else {
						switch (method) {
							case 1:
								return isValidCtm(path);

							case 2:
								return isValidHorizontal(path);

							case 3:
								return isValidTop(path);

							case 4:
								return isValidRandom(path);

							case 5:
								return isValidRepeat(path);

							case 6:
								return isValidVertical(path);

							case 7:
								return isValidFixed(path);

							case 8:
								return isValidHorizontalVertical(path);

							case 9:
								return isValidVerticalHorizontal(path);

							default:
								Config.warn("Unknown method: " + path);
								return false;
						}
					}
				} else {
					Config.warn("No tiles specified: " + path);
					return false;
				}
			}
		} else {
			Config.warn("No name found: " + path);
			return false;
		}
	}

	private int detectConnect() {
		return matchBlocks != null ? 1 : matchTiles != null ? 2 : 128;
	}

	private MatchBlock[] detectMatchBlocks() {
		final int[] ids = detectMatchBlockIds();

		if (ids == null) {
			return null;
		} else {
			final MatchBlock[] mbs = new MatchBlock[ids.length];

			for (int i = 0; i < mbs.length; ++i) {
				mbs[i] = new MatchBlock(ids[i]);
			}

			return mbs;
		}
	}

	private int[] detectMatchBlockIds() {
		if (!name.startsWith("block")) {
			return null;
		} else {
			final int startPos = "block".length();
			int pos;

			for (pos = startPos; pos < name.length(); ++pos) {
				final char idStr = name.charAt(pos);

				if (idStr < 48 || idStr > 57) {
					break;
				}
			}

			if (pos == startPos) {
				return null;
			} else {
				final String var5 = name.substring(startPos, pos);
				final int id = Config.parseInt(var5, -1);
				return id < 0 ? null : new int[] { id };
			}
		}
	}

	private String[] detectMatchTiles() {
		final TextureAtlasSprite icon = getIcon(name);
		return icon == null ? null : new String[] { name };
	}

	private static TextureAtlasSprite getIcon(final String iconName) {
		final TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite icon = textureMapBlocks.getSpriteSafe(iconName);

		if (icon != null) {
			return icon;
		} else {
			icon = textureMapBlocks.getSpriteSafe("blocks/" + iconName);
			return icon;
		}
	}

	private boolean isValidCtm(final String path) {
		if (tiles == null) {
			tiles = parseTileNames("0-11 16-27 32-43 48-58");
		}

		if (tiles.length < 47) {
			Config.warn("Invalid tiles, must be at least 47: " + path);
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidHorizontal(final String path) {
		if (tiles == null) {
			tiles = parseTileNames("12-15");
		}

		if (tiles.length != 4) {
			Config.warn("Invalid tiles, must be exactly 4: " + path);
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidVertical(final String path) {
		if (tiles == null) {
			Config.warn("No tiles defined for vertical: " + path);
			return false;
		} else if (tiles.length != 4) {
			Config.warn("Invalid tiles, must be exactly 4: " + path);
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidHorizontalVertical(final String path) {
		if (tiles == null) {
			Config.warn("No tiles defined for horizontal+vertical: " + path);
			return false;
		} else if (tiles.length != 7) {
			Config.warn("Invalid tiles, must be exactly 7: " + path);
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidVerticalHorizontal(final String path) {
		if (tiles == null) {
			Config.warn("No tiles defined for vertical+horizontal: " + path);
			return false;
		} else if (tiles.length != 7) {
			Config.warn("Invalid tiles, must be exactly 7: " + path);
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidRandom(final String path) {
		if (tiles != null && tiles.length > 0) {
			if (weights != null) {
				int[] sum;

				if (weights.length > tiles.length) {
					Config.warn("More weights defined than tiles, trimming weights: " + path);
					sum = new int[tiles.length];
					System.arraycopy(weights, 0, sum, 0, sum.length);
					weights = sum;
				}

				int i;

				if (weights.length < tiles.length) {
					Config.warn("Less weights defined than tiles, expanding weights: " + path);
					sum = new int[tiles.length];
					System.arraycopy(weights, 0, sum, 0, weights.length);
					i = MathUtils.getAverage(weights);

					for (int i1 = weights.length; i1 < sum.length; ++i1) {
						sum[i1] = i;
					}

					weights = sum;
				}

				sumWeights = new int[weights.length];
				int var5 = 0;

				for (i = 0; i < weights.length; ++i) {
					var5 += weights[i];
					sumWeights[i] = var5;
				}

				sumAllWeights = var5;

				if (sumAllWeights <= 0) {
					Config.warn("Invalid sum of all weights: " + var5);
					sumAllWeights = 1;
				}
			}

			return true;
		} else {
			Config.warn("Tiles not defined: " + path);
			return false;
		}
	}

	private boolean isValidRepeat(final String path) {
		if (tiles == null) {
			Config.warn("Tiles not defined: " + path);
			return false;
		} else if (width > 0 && width <= 16) {
			if (height > 0 && height <= 16) {
				if (tiles.length != width * height) {
					Config.warn("Number of tiles does not equal width x height: " + path);
					return false;
				} else {
					return true;
				}
			} else {
				Config.warn("Invalid height: " + path);
				return false;
			}
		} else {
			Config.warn("Invalid width: " + path);
			return false;
		}
	}

	private boolean isValidFixed(final String path) {
		if (tiles == null) {
			Config.warn("Tiles not defined: " + path);
			return false;
		} else if (tiles.length != 1) {
			Config.warn("Number of tiles should be 1 for method: fixed.");
			return false;
		} else {
			return true;
		}
	}

	private boolean isValidTop(final String path) {
		if (tiles == null) {
			tiles = parseTileNames("66");
		}

		if (tiles.length != 1) {
			Config.warn("Invalid tiles, must be exactly 1: " + path);
			return false;
		} else {
			return true;
		}
	}

	public void updateIcons(final TextureMap textureMap) {
		if (matchTiles != null) {
			matchTileIcons = registerIcons(matchTiles, textureMap);
		}

		if (tiles != null) {
			tileIcons = registerIcons(tiles, textureMap);
		}
	}

	private static TextureAtlasSprite[] registerIcons(final String[] tileNames, final TextureMap textureMap) {
		if (tileNames == null) {
			return null;
		} else {
			final ArrayList iconList = new ArrayList();

			for (final String iconName : tileNames) {
				final ResourceLocation resLoc = new ResourceLocation(iconName);
				final String domain = resLoc.getResourceDomain();
				String path = resLoc.getResourcePath();

				if (!path.contains("/")) {
					path = "textures/blocks/" + path;
				}

				final String filePath = path + ".png";
				final ResourceLocation locFile = new ResourceLocation(domain, filePath);
				final boolean exists = Config.hasResource(locFile);

				if (!exists) {
					Config.warn("File not found: " + filePath);
				}

				final String prefixTextures = "textures/";
				String pathSprite = path;

				if (path.startsWith(prefixTextures)) {
					pathSprite = path.substring(prefixTextures.length());
				}

				final ResourceLocation locSprite = new ResourceLocation(domain, pathSprite);
				final TextureAtlasSprite icon = textureMap.func_174942_a(locSprite);
				iconList.add(icon);
			}

			final TextureAtlasSprite[] var15 = (TextureAtlasSprite[]) iconList
					.toArray(new TextureAtlasSprite[iconList.size()]);
			return var15;
		}
	}

	public boolean matchesBlockId(final int blockId) {
		return Matches.blockId(blockId, matchBlocks);
	}

	public boolean matchesBlock(final int blockId, final int metadata) {
		return !Matches.block(blockId, metadata, matchBlocks) ? false : Matches.metadata(metadata, metadatas);
	}

	public boolean matchesIcon(final TextureAtlasSprite icon) {
		return Matches.sprite(icon, matchTileIcons);
	}

	@Override
	public String toString() {
		return "CTM name: " + name + ", basePath: " + basePath + ", matchBlocks: " + Config.arrayToString(matchBlocks)
				+ ", matchTiles: " + Config.arrayToString(matchTiles);
	}

	public boolean matchesBiome(final BiomeGenBase biome) {
		return Matches.biome(biome, biomes);
	}

	public int getMetadataMax() {
		final byte max = -1;
		int var4 = getMax(metadatas, max);

		if (matchBlocks != null) {
			for (final MatchBlock mb : matchBlocks) {
				var4 = getMax(mb.getMetadatas(), var4);
			}
		}

		return var4;
	}

	private int getMax(final int[] mds, int max) {
		if (mds == null) {
			return max;
		} else {
			for (final int md : mds) {
				if (md > max) {
					max = md;
				}
			}

			return max;
		}
	}
}
