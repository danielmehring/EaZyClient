package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectedParser {

public static final int EaZy = 1885;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String context = null;
	private static final MatchBlock[] NO_MATCH_BLOCKS = new MatchBlock[0];

	public ConnectedParser(final String context) {
		this.context = context;
	}

	public String parseName(final String path) {
		String str = path;
		final int pos = path.lastIndexOf(47);

		if (pos >= 0) {
			str = path.substring(pos + 1);
		}

		final int pos2 = str.lastIndexOf(46);

		if (pos2 >= 0) {
			str = str.substring(0, pos2);
		}

		return str;
	}

	public String parseBasePath(final String path) {
		final int pos = path.lastIndexOf(47);
		return pos < 0 ? "" : path.substring(0, pos);
	}

	public MatchBlock[] parseMatchBlocks(final String propMatchBlocks) {
		if (propMatchBlocks == null) {
			return null;
		} else {
			final ArrayList list = new ArrayList();
			final String[] blockStrs = Config.tokenize(propMatchBlocks, " ");

			for (final String blockStr : blockStrs) {
				final MatchBlock[] mbs1 = parseMatchBlock(blockStr);

				if (mbs1 == null) {
					return NO_MATCH_BLOCKS;
				}

				list.addAll(Arrays.asList(mbs1));
			}

			final MatchBlock[] var7 = (MatchBlock[]) list.toArray(new MatchBlock[list.size()]);
			return var7;
		}
	}

	public MatchBlock[] parseMatchBlock(String blockStr) {
		if (blockStr == null) {
			return null;
		} else {
			blockStr = blockStr.trim();

			if (blockStr.length() <= 0) {
				return null;
			} else {
				final String[] parts = Config.tokenize(blockStr, ":");
				String domain = "minecraft";
				byte var14;

				if (parts.length > 1 && isFullBlockName(parts)) {
					domain = parts[0];
					var14 = 1;
				} else {
					domain = "minecraft";
					var14 = 0;
				}

				final String blockPart = parts[var14];
				final String[] params = Arrays.copyOfRange(parts, var14 + 1, parts.length);
				final Block[] blocks = parseBlockPart(domain, blockPart);

				if (blocks == null) {
					return null;
				} else {
					final MatchBlock[] datas = new MatchBlock[blocks.length];

					for (int i = 0; i < blocks.length; ++i) {
						final Block block = blocks[i];
						final int blockId = Block.getIdFromBlock(block);
						int[] metadatas = null;

						if (params.length > 0) {
							metadatas = parseBlockMetadatas(block, params);

							if (metadatas == null) {
								return null;
							}
						}

						final MatchBlock bd = new MatchBlock(blockId, metadatas);
						datas[i] = bd;
					}

					return datas;
				}
			}
		}
	}

	public boolean isFullBlockName(final String[] parts) {
		if (parts.length < 2) {
			return false;
		} else {
			final String part1 = parts[1];
			return part1.length() < 1 ? false : startsWithDigit(part1) ? false : !part1.contains("=");
		}
	}

	public boolean startsWithDigit(final String str) {
		if (str == null) {
			return false;
		} else if (str.length() < 1) {
			return false;
		} else {
			final char ch = str.charAt(0);
			return Character.isDigit(ch);
		}
	}

	public Block[] parseBlockPart(final String domain, final String blockPart) {
		if (startsWithDigit(blockPart)) {
			final int[] var8 = parseIntList(blockPart);

			if (var8 == null) {
				return null;
			} else {
				final Block[] var9 = new Block[var8.length];

				for (int var10 = 0; var10 < var8.length; ++var10) {
					final int id = var8[var10];
					final Block block1 = Block.getBlockById(id);

					if (block1 == null) {
						warn("Block not found for id: " + id);
						return null;
					}

					var9[var10] = block1;
				}

				return var9;
			}
		} else {
			final String fullName = domain + ":" + blockPart;
			final Block block = Block.getBlockFromName(fullName);

			if (block == null) {
				warn("Block not found for name: " + fullName);
				return null;
			} else {
				final Block[] blocks = new Block[] { block };
				return blocks;
			}
		}
	}

	public int[] parseBlockMetadatas(final Block block, final String[] params) {
		if (params.length <= 0) {
			return null;
		} else {
			final String param0 = params[0];

			if (startsWithDigit(param0)) {
				final int[] var19 = parseIntList(param0);
				return var19;
			} else {
				final IBlockState stateDefault = block.getDefaultState();
				final Collection properties = stateDefault.getPropertyNames();
				final HashMap mapPropValues = new HashMap();

				for (final String metadatas : params) {
					if (metadatas.length() > 0) {
						final String[] i = Config.tokenize(metadatas, "=");

						if (i.length != 2) {
							warn("Invalid block property: " + metadatas);
							return null;
						}

						final String e = i[0];
						final String valStr = i[1];
						final IProperty prop = ConnectedProperties.getProperty(e, properties);

						if (prop == null) {
							warn("Property not found: " + e + ", block: " + block);
							return null;
						}

						Object list = mapPropValues.get(e);

						if (list == null) {
							list = new ArrayList();
							mapPropValues.put(prop, list);
						}

						final String[] vals = Config.tokenize(valStr, ",");

						for (final String val : vals) {
							final Comparable propVal = parsePropertyValue(prop, val);

							if (propVal == null) {
								warn("Property value not found: " + val + ", property: " + e + ", block: " + block);
								return null;
							}

							((List) list).add(propVal);
						}
					}
				}

				if (mapPropValues.isEmpty()) {
					return null;
				} else {
					final ArrayList var20 = new ArrayList();
					int var23;

					for (int var21 = 0; var21 < 16; ++var21) {
						var23 = var21;

						try {
							final IBlockState var24 = getStateFromMeta(block, var23);

							if (matchState(var24, mapPropValues)) {
								var20.add(var23);
							}
						} catch (final IllegalArgumentException var18) {
						}
					}

					if (var20.size() == 16) {
						return null;
					} else {
						final int[] var22 = new int[var20.size()];

						for (var23 = 0; var23 < var22.length; ++var23) {
							var22[var23] = ((Integer) var20.get(var23));
						}

						return var22;
					}
				}
			}
		}
	}

	private IBlockState getStateFromMeta(final Block block, final int md) {
		try {
			IBlockState e = block.getStateFromMeta(md);

			if (block == Blocks.double_plant && md > 7) {
				final IBlockState bsLow = block.getStateFromMeta(md & 7);
				e = e.withProperty(BlockDoublePlant.VARIANT_PROP, bsLow.getValue(BlockDoublePlant.VARIANT_PROP));
			}

			return e;
		} catch (final IllegalArgumentException var5) {
			return block.getDefaultState();
		}
	}

	public static Comparable parsePropertyValue(final IProperty prop, final String valStr) {
		final Class valueClass = prop.getValueClass();
		Comparable valueObj = parseValue(valStr, valueClass);

		if (valueObj == null) {
			final Collection propertyValues = prop.getAllowedValues();
			valueObj = getPropertyValue(valStr, propertyValues);
		}

		return valueObj;
	}

	public static Comparable getPropertyValue(final String value, final Collection propertyValues) {
		final Iterator it = propertyValues.iterator();
		Comparable obj;

		do {
			if (!it.hasNext()) {
				return null;
			}

			obj = (Comparable) it.next();
		}
		while (!String.valueOf(obj).equals(value));

		return obj;
	}

	public static Comparable parseValue(final String str, final Class cls) {
		return cls == String.class ? str
				: cls == Boolean.class ? Boolean.valueOf(str)
						: cls == Float.class ? Float.valueOf(str)
								: cls == Double.class ? Double.valueOf(str)
										: cls == Integer.class ? Integer.valueOf(str)
												: cls == Long.class ? Long.valueOf(str) : null;
	}

	public boolean matchState(final IBlockState bs, final Map<IProperty, List<Comparable>> mapPropValues) {
		final Set keys = mapPropValues.keySet();
		final Iterator it = keys.iterator();
		List vals;
		Comparable bsVal;

		do {
			if (!it.hasNext()) {
				return true;
			}

			final IProperty prop = (IProperty) it.next();
			vals = mapPropValues.get(prop);
			bsVal = bs.getValue(prop);

			if (bsVal == null) {
				return false;
			}
		}
		while (vals.contains(bsVal));

		return false;
	}

	public BiomeGenBase[] parseBiomes(final String str) {
		if (str == null) {
			return null;
		} else {
			final String[] biomeNames = Config.tokenize(str, " ");
			final ArrayList list = new ArrayList();

			for (final String biomeName : biomeNames) {
				final BiomeGenBase biome = findBiome(biomeName);

				if (biome == null) {
					warn("Biome not found: " + biomeName);
				} else {
					list.add(biome);
				}
			}

			final BiomeGenBase[] var7 = (BiomeGenBase[]) list.toArray(new BiomeGenBase[list.size()]);
			return var7;
		}
	}

	public BiomeGenBase findBiome(String biomeName) {
		biomeName = biomeName.toLowerCase();

		if (biomeName.equals("nether")) {
			return BiomeGenBase.hell;
		} else {
			final BiomeGenBase[] biomeList = BiomeGenBase.getBiomeGenArray();

			for (final BiomeGenBase biome : biomeList) {
				if (biome != null) {
					final String name = biome.biomeName.replace(" ", "").toLowerCase();

					if (name.equals(biomeName)) {
						return biome;
					}
				}
			}

			return null;
		}
	}

	public int parseInt(final String str) {
		if (str == null) {
			return -1;
		} else {
			final int num = Config.parseInt(str, -1);

			if (num < 0) {
				warn("Invalid number: " + str);
			}

			return num;
		}
	}

	public int parseInt(final String str, final int defVal) {
		if (str == null) {
			return defVal;
		} else {
			final int num = Config.parseInt(str, -1);

			if (num < 0) {
				warn("Invalid number: " + str);
				return defVal;
			} else {
				return num;
			}
		}
	}

	public int[] parseIntList(final String str) {
		if (str == null) {
			return null;
		} else {
			final ArrayList list = new ArrayList();
			final String[] intStrs = Config.tokenize(str, " ,");

			for (final String i : intStrs) {
				if (i.contains("-")) {
					final String[] val = Config.tokenize(i, "-");

					if (val.length != 2) {
						warn("Invalid interval: " + i + ", when parsing: " + str);
					} else {
						final int min = Config.parseInt(val[0], -1);
						final int max = Config.parseInt(val[1], -1);

						if (min >= 0 && max >= 0 && min <= max) {
							for (int n = min; n <= max; ++n) {
								list.add(n);
							}
						} else {
							warn("Invalid interval: " + i + ", when parsing: " + str);
						}
					}
				} else {
					final int var12 = Config.parseInt(i, -1);

					if (var12 < 0) {
						warn("Invalid number: " + i + ", when parsing: " + str);
					} else {
						list.add(var12);
					}
				}
			}

			final int[] var10 = new int[list.size()];

			for (int var11 = 0; var11 < var10.length; ++var11) {
				var10[var11] = ((Integer) list.get(var11));
			}

			return var10;
		}
	}

	public boolean[] parseFaces(final String str, final boolean[] defVal) {
		if (str == null) {
			return defVal;
		} else {
			final EnumSet setFaces = EnumSet.allOf(EnumFacing.class);
			final String[] faceStrs = Config.tokenize(str, " ,");

			for (final String i : faceStrs) {
				if (i.equals("sides")) {
					setFaces.add(EnumFacing.NORTH);
					setFaces.add(EnumFacing.SOUTH);
					setFaces.add(EnumFacing.WEST);
					setFaces.add(EnumFacing.EAST);
				} else if (i.equals("all")) {
					setFaces.addAll(Arrays.asList(EnumFacing.VALUES));
				} else {
					final EnumFacing face = parseFace(i);

					if (face != null) {
						setFaces.add(face);
					}
				}
			}

			final boolean[] var8 = new boolean[EnumFacing.VALUES.length];

			for (int var9 = 0; var9 < var8.length; ++var9) {
				var8[var9] = setFaces.contains(EnumFacing.VALUES[var9]);
			}

			return var8;
		}
	}

	public EnumFacing parseFace(String str) {
		str = str.toLowerCase();

		if (!str.equals("bottom") && !str.equals("down")) {
			if (!str.equals("top") && !str.equals("up")) {
				if (str.equals("north")) {
					return EnumFacing.NORTH;
				} else if (str.equals("south")) {
					return EnumFacing.SOUTH;
				} else if (str.equals("east")) {
					return EnumFacing.EAST;
				} else if (str.equals("west")) {
					return EnumFacing.WEST;
				} else {
					Config.warn("Unknown face: " + str);
					return null;
				}
			} else {
				return EnumFacing.UP;
			}
		} else {
			return EnumFacing.DOWN;
		}
	}

	public void dbg(final String str) {
		Config.dbg("" + context + ": " + str);
	}

	public void warn(final String str) {
		Config.warn("" + context + ": " + str);
	}

	public RangeListInt parseRangeListInt(final String str) {
		if (str == null) {
			return null;
		} else {
			final RangeListInt list = new RangeListInt();
			final String[] parts = Config.tokenize(str, " ,");

			for (final String part : parts) {
				final RangeInt ri = parseRangeInt(part);

				if (ri == null) {
					return null;
				}

				list.addRange(ri);
			}

			return list;
		}
	}

	private RangeInt parseRangeInt(final String str) {
		if (str == null) {
			return null;
		} else if (str.indexOf(45) >= 0) {
			final String[] val1 = Config.tokenize(str, "-");

			if (val1.length != 2) {
				warn("Invalid range: " + str);
				return null;
			} else {
				final int min = Config.parseInt(val1[0], -1);
				final int max = Config.parseInt(val1[1], -1);

				if (min >= 0 && max >= 0) {
					return new RangeInt(min, max);
				} else {
					warn("Invalid range: " + str);
					return null;
				}
			}
		} else {
			final int val = Config.parseInt(str, -1);

			if (val < 0) {
				warn("Invalid integer: " + str);
				return null;
			} else {
				return new RangeInt(val, val);
			}
		}
	}

	public static boolean parseBoolean(final String str) {
		return str == null ? false : str.toLowerCase().equals("true");
	}

	public static int parseColor(String str, final int defVal) {
		if (str == null) {
			return defVal;
		} else {
			str = str.trim();

			try {
				final int e = Integer.parseInt(str, 16) & 16777215;
				return e;
			} catch (final NumberFormatException var3) {
				return defVal;
			}
		}
	}
}
