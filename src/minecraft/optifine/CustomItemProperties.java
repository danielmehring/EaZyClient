package optifine;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

public class CustomItemProperties {

public static final int EaZy = 1892;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public String name = null;
	public String basePath = null;
	public int type = 1;
	public int[] items = null;
	public String texture = null;
	public Map<String, String> mapTextures = null;
	public RangeListInt damage = null;
	public boolean damagePercent = false;
	public int damageMask = 0;
	public RangeListInt stackSize = null;
	public RangeListInt enchantmentIds = null;
	public RangeListInt enchantmentLevels = null;
	public NbtTagValue[] nbtTagValues = null;
	public int blend = 1;
	public float speed = 0.0F;
	public float rotation = 0.0F;
	public int layer = 0;
	public float duration = 1.0F;
	public int weight = 0;
	public ResourceLocation textureLocation = null;
	public Map mapTextureLocations = null;
	public TextureAtlasSprite sprite = null;
	public Map mapSprites = null;
	public IBakedModel model = null;
	public Map<String, IBakedModel> mapModels = null;
	private int textureWidth = 0;
	private int textureHeight = 0;
	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_ITEM = 1;
	public static final int TYPE_ENCHANTMENT = 2;
	public static final int TYPE_ARMOR = 3;

	public CustomItemProperties(final Properties props, final String path) {
		name = parseName(path);
		basePath = parseBasePath(path);
		type = parseType(props.getProperty("type"));
		items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
		mapTextures = parseTextures(props, basePath);
		texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"),
				path, basePath, type, mapTextures);
		final String damageStr = props.getProperty("damage");

		if (damageStr != null) {
			damagePercent = damageStr.contains("%");
			damageStr.replace("%", "");
			damage = parseRangeListInt(damageStr);
			damageMask = parseInt(props.getProperty("damageMask"), 0);
		}

		stackSize = parseRangeListInt(props.getProperty("stackSize"));
		enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"));
		enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
		nbtTagValues = parseNbtTagValues(props);
		blend = Blender.parseBlend(props.getProperty("blend"));
		speed = parseFloat(props.getProperty("speed"), 0.0F);
		rotation = parseFloat(props.getProperty("rotation"), 0.0F);
		layer = parseInt(props.getProperty("layer"), 0);
		weight = parseInt(props.getProperty("weight"), 0);
		duration = parseFloat(props.getProperty("duration"), 1.0F);
	}

	private static String parseName(final String path) {
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

	private static String parseBasePath(final String path) {
		final int pos = path.lastIndexOf(47);
		return pos < 0 ? "" : path.substring(0, pos);
	}

	private int parseType(final String str) {
		if (str == null) {
			return 1;
		} else if (str.equals("item")) {
			return 1;
		} else if (str.equals("enchantment")) {
			return 2;
		} else if (str.equals("armor")) {
			return 3;
		} else {
			Config.warn("Unknown method: " + str);
			return 0;
		}
	}

	private int[] parseItems(String str, final String str2) {
		if (str == null) {
			str = str2;
		}

		if (str == null) {
			return null;
		} else {
			str = str.trim();
			final TreeSet setItemIds = new TreeSet();
			final String[] tokens = Config.tokenize(str, " ");
			int i;
			label58:

			for (int integers = 0; integers < tokens.length; ++integers) {
				final String ints = tokens[integers];
				i = Config.parseInt(ints, -1);

				if (i >= 0) {
					setItemIds.add(i);
				} else {
					int id;

					if (ints.contains("-")) {
						final String[] item = Config.tokenize(ints, "-");

						if (item.length == 2) {
							id = Config.parseInt(item[0], -1);
							final int val2 = Config.parseInt(item[1], -1);

							if (id >= 0 && val2 >= 0) {
								final int min = Math.min(id, val2);
								final int max = Math.max(id, val2);
								int x = min;

								while (true) {
									if (x > max) {
										continue label58;
									}

									setItemIds.add(x);
									++x;
								}
							}
						}
					}

					final Item var16 = Item.getByNameOrId(ints);

					if (var16 == null) {
						Config.warn("Item not found: " + ints);
					} else {
						id = Item.getIdFromItem(var16);

						if (id < 0) {
							Config.warn("Item not found: " + ints);
						} else {
							setItemIds.add(id);
						}
					}
				}
			}

			final Integer[] var14 = (Integer[]) setItemIds.toArray(new Integer[setItemIds.size()]);
			final int[] var15 = new int[var14.length];

			for (i = 0; i < var15.length; ++i) {
				var15[i] = var14[i];
			}

			return var15;
		}
	}

	private static String parseTexture(String texStr, final String texStr2, final String texStr3, final String path,
			final String basePath, final int type, final Map<String, String> mapTexs) {
		if (texStr == null) {
			texStr = texStr2;
		}

		if (texStr == null) {
			texStr = texStr3;
		}

		String str;

		if (texStr != null) {
			str = ".png";

			if (texStr.endsWith(str)) {
				texStr = texStr.substring(0, texStr.length() - str.length());
			}

			texStr = fixTextureName(texStr, basePath);
			return texStr;
		} else if (type == 3) {
			return null;
		} else {
			if (mapTexs != null) {
				str = mapTexs.get("texture.bow_standby");

				if (str != null) {
					return str;
				}
			}

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

	private static Map parseTextures(final Properties props, final String basePath) {
		final String prefix = "texture.";
		final Map mapProps = getMatchingProperties(props, prefix);

		if (mapProps.size() <= 0) {
			return null;
		} else {
			final Set keySet = mapProps.keySet();
			final LinkedHashMap mapTex = new LinkedHashMap();
			final Iterator it = keySet.iterator();

			while (it.hasNext()) {
				final String key = (String) it.next();
				String val = (String) mapProps.get(key);
				val = fixTextureName(val, basePath);
				mapTex.put(key, val);
			}

			return mapTex;
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

	private int parseInt(String str, final int defVal) {
		if (str == null) {
			return defVal;
		} else {
			str = str.trim();
			final int val = Config.parseInt(str, Integer.MIN_VALUE);

			if (val == Integer.MIN_VALUE) {
				Config.warn("Invalid integer: " + str);
				return defVal;
			} else {
				return val;
			}
		}
	}

	private float parseFloat(String str, final float defVal) {
		if (str == null) {
			return defVal;
		} else {
			str = str.trim();
			final float val = Config.parseFloat(str, Float.MIN_VALUE);

			if (val == Float.MIN_VALUE) {
				Config.warn("Invalid float: " + str);
				return defVal;
			} else {
				return val;
			}
		}
	}

	private RangeListInt parseRangeListInt(final String str) {
		if (str == null) {
			return null;
		} else {
			final String[] tokens = Config.tokenize(str, " ");
			final RangeListInt rangeList = new RangeListInt();

			for (final String token : tokens) {
				final RangeInt range = parseRangeInt(token);

				if (range == null) {
					Config.warn("Invalid range list: " + str);
					return null;
				}

				rangeList.addRange(range);
			}

			return rangeList;
		}
	}

	private RangeInt parseRangeInt(String str) {
		if (str == null) {
			return null;
		} else {
			str = str.trim();
			final int countMinus = str.length() - str.replace("-", "").length();

			if (countMinus > 1) {
				Config.warn("Invalid range: " + str);
				return null;
			} else {
				final String[] tokens = Config.tokenize(str, "- ");
				final int[] vals = new int[tokens.length];
				int min;

				for (min = 0; min < tokens.length; ++min) {
					final String max = tokens[min];
					final int val = Config.parseInt(max, -1);

					if (val < 0) {
						Config.warn("Invalid range: " + str);
						return null;
					}

					vals[min] = val;
				}

				if (vals.length == 1) {
					min = vals[0];

					if (str.startsWith("-")) {
						return new RangeInt(0, min);
					} else if (str.endsWith("-")) {
						return new RangeInt(min, 255);
					} else {
						return new RangeInt(min, min);
					}
				} else if (vals.length == 2) {
					min = Math.min(vals[0], vals[1]);
					final int var8 = Math.max(vals[0], vals[1]);
					return new RangeInt(min, var8);
				} else {
					Config.warn("Invalid range: " + str);
					return null;
				}
			}
		}
	}

	private NbtTagValue[] parseNbtTagValues(final Properties props) {
		final String PREFIX_NBT = "nbt.";
		final Map mapNbt = getMatchingProperties(props, PREFIX_NBT);

		if (mapNbt.size() <= 0) {
			return null;
		} else {
			final ArrayList listNbts = new ArrayList();
			final Set keySet = mapNbt.keySet();
			final Iterator nbts = keySet.iterator();

			while (nbts.hasNext()) {
				final String key = (String) nbts.next();
				final String val = (String) mapNbt.get(key);
				final String id = key.substring(PREFIX_NBT.length());
				final NbtTagValue nbt = new NbtTagValue(id, val);
				listNbts.add(nbt);
			}

			final NbtTagValue[] nbts1 = (NbtTagValue[]) listNbts.toArray(new NbtTagValue[listNbts.size()]);
			return nbts1;
		}
	}

	private static Map getMatchingProperties(final Properties props, final String keyPrefix) {
		final LinkedHashMap map = new LinkedHashMap();
		final Set keySet = props.keySet();
		final Iterator it = keySet.iterator();

		while (it.hasNext()) {
			final String key = (String) it.next();
			final String val = props.getProperty(key);

			if (key.startsWith(keyPrefix)) {
				map.put(key, val);
			}
		}

		return map;
	}

	public boolean isValid(final String path) {
		if (name != null && name.length() > 0) {
			if (basePath == null) {
				Config.warn("No base path found: " + path);
				return false;
			} else if (type == 0) {
				Config.warn("No type defined: " + path);
				return false;
			} else if ((type == 1 || type == 3) && items == null) {
				Config.warn("No items defined: " + path);
				return false;
			} else if (texture == null && mapTextures == null) {
				Config.warn("No texture specified: " + path);
				return false;
			} else if (type == 2 && enchantmentIds == null) {
				Config.warn("No enchantmentIDs specified: " + path);
				return false;
			} else {
				return true;
			}
		} else {
			Config.warn("No name found: " + path);
			return false;
		}
	}

	public void updateIcons(final TextureMap textureMap) {
		if (texture != null) {
			textureLocation = getTextureLocation(texture);

			if (type == 1) {
				final ResourceLocation keySet = getSpriteLocation(textureLocation);
				sprite = textureMap.func_174942_a(keySet);
			}
		}

		if (mapTextures != null) {
			mapTextureLocations = new HashMap();
			mapSprites = new HashMap();
			final Set keySet1 = mapTextures.keySet();
			final Iterator it = keySet1.iterator();

			while (it.hasNext()) {
				final String key = (String) it.next();
				final String val = mapTextures.get(key);
				final ResourceLocation locTex = getTextureLocation(val);
				mapTextureLocations.put(key, locTex);

				if (type == 1) {
					final ResourceLocation locSprite = getSpriteLocation(locTex);
					final TextureAtlasSprite icon = textureMap.func_174942_a(locSprite);
					mapSprites.put(key, icon);
				}
			}
		}
	}

	private ResourceLocation getTextureLocation(final String texName) {
		if (texName == null) {
			return null;
		} else {
			final ResourceLocation resLoc = new ResourceLocation(texName);
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

			return locFile;
		}
	}

	private ResourceLocation getSpriteLocation(final ResourceLocation resLoc) {
		String pathTex = resLoc.getResourcePath();
		pathTex = StrUtils.removePrefix(pathTex, "textures/");
		pathTex = StrUtils.removeSuffix(pathTex, ".png");
		final ResourceLocation locTex = new ResourceLocation(resLoc.getResourceDomain(), pathTex);
		return locTex;
	}

	public void updateModel(final TextureMap textureMap, final ItemModelGenerator itemModelGenerator) {
		final String[] textures = getModelTextures();
		final boolean useTint = isUseTint();
		model = makeBakedModel(textureMap, itemModelGenerator, textures, useTint);

		if (type == 1 && mapTextures != null) {
			final Set keySet = mapTextures.keySet();
			final Iterator it = keySet.iterator();

			while (it.hasNext()) {
				final String key = (String) it.next();
				final String tex = mapTextures.get(key);
				final String path = StrUtils.removePrefix(key, "texture.");

				if (path.startsWith("bow") || path.startsWith("fishing_rod")) {
					final String[] texNames = new String[] { tex };
					final IBakedModel modelTex = makeBakedModel(textureMap, itemModelGenerator, texNames, useTint);

					if (mapModels == null) {
						mapModels = new HashMap();
					}

					mapModels.put(path, modelTex);
				}
			}
		}
	}

	private boolean isUseTint() {
		return true;
	}

	private static IBakedModel makeBakedModel(final TextureMap textureMap, final ItemModelGenerator itemModelGenerator,
			final String[] textures, final boolean useTint) {
		final ModelBlock modelBlockBase = makeModelBlock(textures);
		final ModelBlock modelBlock = itemModelGenerator.func_178392_a(textureMap, modelBlockBase);
		final IBakedModel model = bakeModel(textureMap, modelBlock, useTint);
		return model;
	}

	private String[] getModelTextures() {
		if (type == 1 && items.length == 1) {
			final Item item = Item.getItemById(items[0]);
			String key;
			String texMain;

			if (item == Items.potionitem && damage != null && damage.getCountRanges() > 0) {
				final RangeInt itemArmor1 = damage.getRange(0);
				final int material1 = itemArmor1.getMin();
				final boolean type1 = (material1 & 16384) != 0;
				key = getMapTexture(mapTextures, "texture.potion_overlay", "items/potion_overlay");
				texMain = null;

				if (type1) {
					texMain = getMapTexture(mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
				} else {
					texMain = getMapTexture(mapTextures, "texture.potion_bottle_drinkable",
							"items/potion_bottle_drinkable");
				}

				return new String[] { key, texMain };
			}

			if (item instanceof ItemArmor) {
				final ItemArmor itemArmor = (ItemArmor) item;

				if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
					final String material = "leather";
					String type = "helmet";

					if (itemArmor.armorType == 0) {
						type = "helmet";
					}

					if (itemArmor.armorType == 1) {
						type = "chestplate";
					}

					if (itemArmor.armorType == 2) {
						type = "leggings";
					}

					if (itemArmor.armorType == 3) {
						type = "boots";
					}

					key = material + "_" + type;
					texMain = getMapTexture(mapTextures, "texture." + key, "items/" + key);
					final String texOverlay = getMapTexture(mapTextures, "texture." + key + "_overlay",
							"items/" + key + "_overlay");
					return new String[] { texMain, texOverlay };
				}
			}
		}

		return new String[] { texture };
	}

	private String getMapTexture(final Map<String, String> map, final String key, final String def) {
		if (map == null) {
			return def;
		} else {
			final String str = map.get(key);
			return str == null ? def : str;
		}
	}

	private static ModelBlock makeModelBlock(final String[] modelTextures) {
		final StringBuffer sb = new StringBuffer();
		sb.append("{\"parent\": \"builtin/generated\",\"textures\": {");

		for (int modelStr = 0; modelStr < modelTextures.length; ++modelStr) {
			final String model = modelTextures[modelStr];

			if (modelStr > 0) {
				sb.append(", ");
			}

			sb.append("\"layer" + modelStr + "\": \"" + model + "\"");
		}

		sb.append("}}");
		final String var4 = sb.toString();
		final ModelBlock var5 = ModelBlock.deserialize(var4);
		return var5;
	}

	private static IBakedModel bakeModel(final TextureMap textureMap, final ModelBlock modelBlockIn,
			final boolean useTint) {
		final ModelRotation modelRotationIn = ModelRotation.X0_Y0;
		final boolean uvLocked = false;
		final TextureAtlasSprite var4 = textureMap.getSpriteSafe(modelBlockIn.resolveTextureName("particle"));
		final SimpleBakedModel.Builder var5 = new SimpleBakedModel.Builder(modelBlockIn).func_177646_a(var4);
		final Iterator var6 = modelBlockIn.getElements().iterator();

		while (var6.hasNext()) {
			final BlockPart var7 = (BlockPart) var6.next();
			final Iterator var8 = var7.field_178240_c.keySet().iterator();

			while (var8.hasNext()) {
				final EnumFacing var9 = (EnumFacing) var8.next();
				BlockPartFace var10 = (BlockPartFace) var7.field_178240_c.get(var9);

				if (!useTint) {
					var10 = new BlockPartFace(var10.field_178244_b, -1, var10.field_178242_d, var10.field_178243_e);
				}

				final TextureAtlasSprite var11 = textureMap
						.getSpriteSafe(modelBlockIn.resolveTextureName(var10.field_178242_d));
				final BakedQuad quad = makeBakedQuad(var7, var10, var11, var9, modelRotationIn, uvLocked);

				if (var10.field_178244_b == null) {
					var5.func_177648_a(quad);
				} else {
					var5.func_177650_a(modelRotationIn.func_177523_a(var10.field_178244_b), quad);
				}
			}
		}

		return var5.func_177645_b();
	}

	private static BakedQuad makeBakedQuad(final BlockPart blockPart, final BlockPartFace blockPartFace,
			final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation,
			final boolean uvLocked) {
		final FaceBakery faceBakery = new FaceBakery();
		return faceBakery.func_178414_a(blockPart.field_178241_a, blockPart.field_178239_b, blockPartFace,
				textureAtlasSprite, enumFacing, modelRotation, blockPart.field_178237_d, uvLocked,
				blockPart.field_178238_e);
	}

	@Override
	public String toString() {
		return "" + basePath + "/" + name + ", type: " + type + ", items: [" + Config.arrayToString(items)
				+ "], textture: " + texture;
	}

	public float getTextureWidth(final TextureManager textureManager) {
		if (textureWidth <= 0) {
			if (textureLocation != null) {
				final ITextureObject tex = textureManager.getTexture(textureLocation);
				final int texId = tex.getGlTextureId();
				final int prevTexId = GlStateManager.getBoundTexture();
				GlStateManager.func_179144_i(texId);
				textureWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
				GlStateManager.func_179144_i(prevTexId);
			}

			if (textureWidth <= 0) {
				textureWidth = 16;
			}
		}

		return textureWidth;
	}

	public float getTextureHeight(final TextureManager textureManager) {
		if (textureHeight <= 0) {
			if (textureLocation != null) {
				final ITextureObject tex = textureManager.getTexture(textureLocation);
				final int texId = tex.getGlTextureId();
				final int prevTexId = GlStateManager.getBoundTexture();
				GlStateManager.func_179144_i(texId);
				textureHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
				GlStateManager.func_179144_i(prevTexId);
			}

			if (textureHeight <= 0) {
				textureHeight = 16;
			}
		}

		return textureHeight;
	}

	public IBakedModel getModel(final ModelResourceLocation modelLocation) {
		if (modelLocation != null && mapTextures != null) {
			final String modelPath = modelLocation.getResourcePath();

			if (mapModels != null) {
				final IBakedModel customModel = mapModels.get(modelPath);

				if (customModel != null) {
					return customModel;
				}
			}
		}

		return model;
	}
}
