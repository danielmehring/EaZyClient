package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class PlayerConfigurationParser {

public static final int EaZy = 1941;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String player = null;
	public static final String CONFIG_ITEMS = "items";
	public static final String ITEM_TYPE = "type";
	public static final String ITEM_ACTIVE = "active";

	public PlayerConfigurationParser(final String player) {
		this.player = player;
	}

	public PlayerConfiguration parsePlayerConfiguration(final JsonElement je) {
		if (je == null) {
			throw new JsonParseException("JSON object is null, player: " + player);
		} else {
			final JsonObject jo = (JsonObject) je;
			final PlayerConfiguration pc = new PlayerConfiguration();
			final JsonArray items = (JsonArray) jo.get("items");

			if (items != null) {
				for (int i = 0; i < items.size(); ++i) {
					final JsonObject item = (JsonObject) items.get(i);
					final boolean active = Json.getBoolean(item, "active", true);

					if (active) {
						final String type = Json.getString(item, "type");

						if (type == null) {
							Config.warn("Item type is null, player: " + player);
						} else {
							String modelPath = Json.getString(item, "model");

							if (modelPath == null) {
								modelPath = "items/" + type + "/model.cfg";
							}

							final PlayerItemModel model = downloadModel(modelPath);

							if (model != null) {
								if (!model.isUsePlayerTexture()) {
									String texturePath = Json.getString(item, "texture");

									if (texturePath == null) {
										texturePath = "items/" + type + "/users/" + player + ".png";
									}

									final BufferedImage image = downloadTextureImage(texturePath);

									if (image == null) {
										continue;
									}

									model.setTextureImage(image);
									final ResourceLocation loc = new ResourceLocation("optifine.net", texturePath);
									model.setTextureLocation(loc);
								}

								pc.addPlayerItemModel(model);
							}
						}
					}
				}
			}

			return pc;
		}
	}

	private BufferedImage downloadTextureImage(final String texturePath) {
		final String textureUrl = "http://s.optifine.net/" + texturePath;

		try {
			final byte[] e = HttpPipeline.get(textureUrl, Minecraft.getMinecraft().getProxy());
			final BufferedImage image = ImageIO.read(new ByteArrayInputStream(e));
			return image;
		} catch (final IOException var5) {
			Config.warn("Error loading item texture " + texturePath + ": " + var5.getClass().getName() + ": "
					+ var5.getMessage());
			return null;
		}
	}

	private PlayerItemModel downloadModel(final String modelPath) {
		final String modelUrl = "http://s.optifine.net/" + modelPath;

		try {
			final byte[] e = HttpPipeline.get(modelUrl, Minecraft.getMinecraft().getProxy());
			final String jsonStr = new String(e, "ASCII");
			final JsonParser jp = new JsonParser();
			final JsonObject jo = (JsonObject) jp.parse(jsonStr);
			new PlayerItemParser();
			final PlayerItemModel pim = PlayerItemParser.parseItemModel(jo);
			return pim;
		} catch (final Exception var9) {
			Config.warn("Error loading item model " + modelPath + ": " + var9.getClass().getName() + ": "
					+ var9.getMessage());
			return null;
		}
	}
}
