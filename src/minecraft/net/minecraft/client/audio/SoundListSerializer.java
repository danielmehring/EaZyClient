package net.minecraft.client.audio;

import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

import org.apache.commons.lang3.Validate;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SoundListSerializer implements JsonDeserializer {

public static final int EaZy = 446;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001124";

	public SoundList deserialize1(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		final JsonObject var4 = JsonUtils.getElementAsJsonObject(p_deserialize_1_, "entry");
		final SoundList var5 = new SoundList();
		var5.setReplaceExisting(JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "replace", false));
		final SoundCategory var6 = SoundCategory.func_147154_a(JsonUtils.getJsonObjectStringFieldValueOrDefault(var4,
				"category", SoundCategory.MASTER.getCategoryName()));
		var5.setSoundCategory(var6);
		Validate.notNull(var6, "Invalid category", new Object[0]);

		if (var4.has("sounds")) {
			final JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var4, "sounds");

			for (int var8 = 0; var8 < var7.size(); ++var8) {
				final JsonElement var9 = var7.get(var8);
				final SoundList.SoundEntry var10 = new SoundList.SoundEntry();

				if (JsonUtils.jsonElementTypeIsString(var9)) {
					var10.setSoundEntryName(JsonUtils.getJsonElementStringValue(var9, "sound"));
				} else {
					final JsonObject var11 = JsonUtils.getElementAsJsonObject(var9, "sound");
					var10.setSoundEntryName(JsonUtils.getJsonObjectStringFieldValue(var11, "name"));

					if (var11.has("type")) {
						final SoundList.SoundEntry.Type var12 = SoundList.SoundEntry.Type
								.getType(JsonUtils.getJsonObjectStringFieldValue(var11, "type"));
						Validate.notNull(var12, "Invalid type", new Object[0]);
						var10.setSoundEntryType(var12);
					}

					float var13;

					if (var11.has("volume")) {
						var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "volume");
						Validate.isTrue(var13 > 0.0F, "Invalid volume", new Object[0]);
						var10.setSoundEntryVolume(var13);
					}

					if (var11.has("pitch")) {
						var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "pitch");
						Validate.isTrue(var13 > 0.0F, "Invalid pitch", new Object[0]);
						var10.setSoundEntryPitch(var13);
					}

					if (var11.has("weight")) {
						final int var14 = JsonUtils.getJsonObjectIntegerFieldValue(var11, "weight");
						Validate.isTrue(var14 > 0, "Invalid weight", new Object[0]);
						var10.setSoundEntryWeight(var14);
					}

					if (var11.has("stream")) {
						var10.setStreaming(JsonUtils.getJsonObjectBooleanFieldValue(var11, "stream"));
					}
				}

				var5.getSoundList().add(var10);
			}
		}

		return var5;
	}

	@Override
	public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
			final JsonDeserializationContext p_deserialize_3_) {
		return deserialize1(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
	}
}
