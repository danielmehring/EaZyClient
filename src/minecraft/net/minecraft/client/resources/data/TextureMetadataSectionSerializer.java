package net.minecraft.client.resources.data;

import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer {

public static final int EaZy = 869;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001115";

	@Override
	public TextureMetadataSection deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		final JsonObject var4 = p_deserialize_1_.getAsJsonObject();
		final boolean var5 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "blur", false);
		final boolean var6 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "clamp", false);
		final ArrayList var7 = Lists.newArrayList();

		if (var4.has("mipmaps")) {
			try {
				final JsonArray var8 = var4.getAsJsonArray("mipmaps");

				for (int var9 = 0; var9 < var8.size(); ++var9) {
					final JsonElement var10 = var8.get(var9);

					if (var10.isJsonPrimitive()) {
						try {
							var7.add(var10.getAsInt());
						} catch (final NumberFormatException var12) {
							throw new JsonParseException(
									"Invalid texture->mipmap->" + var9 + ": expected number, was " + var10, var12);
						}
					} else if (var10.isJsonObject()) {
						throw new JsonParseException(
								"Invalid texture->mipmap->" + var9 + ": expected number, was " + var10);
					}
				}
			} catch (final ClassCastException var13) {
				throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + var4.get("mipmaps"),
						var13);
			}
		}

		return new TextureMetadataSection(var5, var6, var7);
	}

	/**
	 * The name of this section type as it appears in JSON.
	 */
	@Override
	public String getSectionName() {
		return "texture";
	}

}
