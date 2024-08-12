package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class NaturalTextures {

public static final int EaZy = 1937;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

	public static void update() {
		propertiesByIndex = new NaturalProperties[0];

		if (Config.isNaturalTextures()) {
			final String fileName = "optifine/natural.properties";

			try {
				final ResourceLocation e = new ResourceLocation(fileName);

				if (!Config.hasResource(e)) {
					Config.dbg("NaturalTextures: configuration \"" + fileName + "\" not found");
					return;
				}

				final boolean defaultConfig = Config.isFromDefaultResourcePack(e);
				final InputStream in = Config.getResourceStream(e);
				final ArrayList list = new ArrayList(256);
				final String configStr = Config.readInputStream(in);
				in.close();
				final String[] configLines = Config.tokenize(configStr, "\n\r");

				if (defaultConfig) {
					Config.dbg("Natural Textures: Parsing default configuration \"" + fileName + "\"");
					Config.dbg("Natural Textures: Valid only for textures from default resource pack");
				} else {
					Config.dbg("Natural Textures: Parsing configuration \"" + fileName + "\"");
				}

				final TextureMap textureMapBlocks = TextureUtils.getTextureMapBlocks();

				for (final String configLine : configLines) {
					final String line = configLine.trim();

					if (!line.startsWith("#")) {
						final String[] strs = Config.tokenize(line, "=");

						if (strs.length != 2) {
							Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
						} else {
							final String key = strs[0].trim();
							final String type = strs[1].trim();
							final TextureAtlasSprite ts = textureMapBlocks.getSpriteSafe("minecraft:blocks/" + key);

							if (ts == null) {
								Config.warn("Natural Textures: Texture not found: \"" + fileName + "\" line: " + line);
							} else {
								final int tileNum = ts.getIndexInMap();

								if (tileNum < 0) {
									Config.warn("Natural Textures: Invalid \"" + fileName + "\" line: " + line);
								} else {
									if (defaultConfig && !Config.isFromDefaultResourcePack(
											new ResourceLocation("textures/blocks/" + key + ".png"))) {
										return;
									}

									final NaturalProperties props = new NaturalProperties(type);

									if (props.isValid()) {
										while (list.size() <= tileNum) {
											list.add((Object) null);
										}

										list.set(tileNum, props);
										Config.dbg("NaturalTextures: " + key + " = " + type);
									}
								}
							}
						}
					}
				}

				propertiesByIndex = (NaturalProperties[]) list.toArray(new NaturalProperties[list.size()]);
			} catch (final FileNotFoundException var17) {
				Config.warn("NaturalTextures: configuration \"" + fileName + "\" not found");
				return;
			} catch (final Exception var18) {
				var18.printStackTrace();
			}
		}
	}

	public static BakedQuad getNaturalTexture(final BlockPos blockPosIn, final BakedQuad quad) {
		final TextureAtlasSprite sprite = quad.getSprite();

		if (sprite == null) {
			return quad;
		} else {
			final NaturalProperties nps = getNaturalProperties(sprite);

			if (nps == null) {
				return quad;
			} else {
				final int side = ConnectedTextures.getSide(quad.getFace());
				final int rand = Config.getRandom(blockPosIn, side);
				int rotate = 0;
				boolean flipU = false;

				if (nps.rotation > 1) {
					rotate = rand & 3;
				}

				if (nps.rotation == 2) {
					rotate = rotate / 2 * 2;
				}

				if (nps.flip) {
					flipU = (rand & 4) != 0;
				}

				return nps.getQuad(quad, rotate, flipU);
			}
		}
	}

	public static NaturalProperties getNaturalProperties(final TextureAtlasSprite icon) {
		if (!(icon instanceof TextureAtlasSprite)) {
			return null;
		} else {
			final int tileNum = icon.getIndexInMap();

			if (tileNum >= 0 && tileNum < propertiesByIndex.length) {
				final NaturalProperties props = propertiesByIndex[tileNum];
				return props;
			} else {
				return null;
			}
		}
	}
}
