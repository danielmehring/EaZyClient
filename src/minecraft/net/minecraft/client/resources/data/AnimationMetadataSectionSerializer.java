package net.minecraft.client.resources.data;

import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AnimationMetadataSectionSerializer extends BaseMetadataSectionSerializer implements JsonSerializer {

public static final int EaZy = 857;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001107";

	@Override
	public AnimationMetadataSection deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		final ArrayList var4 = Lists.newArrayList();
		final JsonObject var5 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "metadata section");
		final int var6 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "frametime", 1);

		if (var6 != 1) {
			Validate.inclusiveBetween(1L, 2147483647L, var6, "Invalid default frame time");
		}

		int var8;

		if (var5.has("frames")) {
			try {
				final JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var5, "frames");

				for (var8 = 0; var8 < var7.size(); ++var8) {
					final JsonElement var9 = var7.get(var8);
					final AnimationFrame var10 = parseAnimationFrame(var8, var9);

					if (var10 != null) {
						var4.add(var10);
					}
				}
			} catch (final ClassCastException var11) {
				throw new JsonParseException("Invalid animation->frames: expected array, was " + var5.get("frames"),
						var11);
			}
		}

		final int var12 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "width", -1);
		var8 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "height", -1);

		if (var12 != -1) {
			Validate.inclusiveBetween(1L, 2147483647L, var12, "Invalid width");
		}

		if (var8 != -1) {
			Validate.inclusiveBetween(1L, 2147483647L, var8, "Invalid height");
		}

		final boolean var13 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var5, "interpolate", false);
		return new AnimationMetadataSection(var4, var12, var8, var6, var13);
	}

	private AnimationFrame parseAnimationFrame(final int p_110492_1_, final JsonElement p_110492_2_) {
		if (p_110492_2_.isJsonPrimitive()) {
			return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(p_110492_2_, "frames[" + p_110492_1_ + "]"));
		} else if (p_110492_2_.isJsonObject()) {
			final JsonObject var3 = JsonUtils.getElementAsJsonObject(p_110492_2_, "frames[" + p_110492_1_ + "]");
			final int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var3, "time", -1);

			if (var3.has("time")) {
				Validate.inclusiveBetween(1L, 2147483647L, var4, "Invalid frame time");
			}

			final int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var3, "index");
			Validate.inclusiveBetween(0L, 2147483647L, var5, "Invalid frame index");
			return new AnimationFrame(var5, var4);
		} else {
			return null;
		}
	}

	public JsonElement serialize(final AnimationMetadataSection p_serialize_1_, final Type p_serialize_2_,
			final JsonSerializationContext p_serialize_3_) {
		final JsonObject var4 = new JsonObject();
		var4.addProperty("frametime", p_serialize_1_.getFrameTime());

		if (p_serialize_1_.getFrameWidth() != -1) {
			var4.addProperty("width", p_serialize_1_.getFrameWidth());
		}

		if (p_serialize_1_.getFrameHeight() != -1) {
			var4.addProperty("height", p_serialize_1_.getFrameHeight());
		}

		if (p_serialize_1_.getFrameCount() > 0) {
			final JsonArray var5 = new JsonArray();

			for (int var6 = 0; var6 < p_serialize_1_.getFrameCount(); ++var6) {
				if (p_serialize_1_.frameHasTime(var6)) {
					final JsonObject var7 = new JsonObject();
					var7.addProperty("index", p_serialize_1_.getFrameIndex(var6));
					var7.addProperty("time", p_serialize_1_.getFrameTimeSingle(var6));
					var5.add(var7);
				} else {
					var5.add(new JsonPrimitive(p_serialize_1_.getFrameIndex(var6)));
				}
			}

			var4.add("frames", var5);
		}

		return var4;
	}

	/**
	 * The name of this section type as it appears in JSON.
	 */
	@Override
	public String getSectionName() {
		return "animation";
	}

	@Override
	public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_,
			final JsonSerializationContext p_serialize_3_) {
		return this.serialize((AnimationMetadataSection) p_serialize_1_, p_serialize_2_, p_serialize_3_);
	}
}
