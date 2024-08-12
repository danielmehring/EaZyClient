package net.minecraft.client.resources.data;

import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

import org.apache.commons.lang3.Validate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer {

public static final int EaZy = 860;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001109";

	@Override
	public FontMetadataSection deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		final JsonObject var4 = p_deserialize_1_.getAsJsonObject();
		final float[] var5 = new float[256];
		final float[] var6 = new float[256];
		final float[] var7 = new float[256];
		float var8 = 1.0F;
		float var9 = 0.0F;
		float var10 = 0.0F;

		if (var4.has("characters")) {
			if (!var4.get("characters").isJsonObject()) {
				throw new JsonParseException(
						"Invalid font->characters: expected object, was " + var4.get("characters"));
			}

			final JsonObject var11 = var4.getAsJsonObject("characters");

			if (var11.has("default")) {
				if (!var11.get("default").isJsonObject()) {
					throw new JsonParseException(
							"Invalid font->characters->default: expected object, was " + var11.get("default"));
				}

				final JsonObject var12 = var11.getAsJsonObject("default");
				var8 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "width", var8);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var8, "Invalid default width");
				var9 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "spacing", var9);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var9, "Invalid default spacing");
				var10 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "left", var9);
				Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var10, "Invalid default left");
			}

			for (int var18 = 0; var18 < 256; ++var18) {
				final JsonElement var13 = var11.get(Integer.toString(var18));
				float var14 = var8;
				float var15 = var9;
				float var16 = var10;

				if (var13 != null) {
					final JsonObject var17 = JsonUtils.getElementAsJsonObject(var13, "characters[" + var18 + "]");
					var14 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "width", var8);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var14, "Invalid width");
					var15 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "spacing", var9);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var15, "Invalid spacing");
					var16 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "left", var10);
					Validate.inclusiveBetween(0.0D, 3.4028234663852886E38D, var16, "Invalid left");
				}

				var5[var18] = var14;
				var6[var18] = var15;
				var7[var18] = var16;
			}
		}

		return new FontMetadataSection(var5, var7, var6);
	}

	/**
	 * The name of this section type as it appears in JSON.
	 */
	@Override
	public String getSectionName() {
		return "font";
	}

}
