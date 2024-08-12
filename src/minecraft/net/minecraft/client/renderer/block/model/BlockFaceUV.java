package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BlockFaceUV {

public static final int EaZy = 675;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float[] field_178351_a;
	public final int field_178350_b;
	// private static final String __OBFID = "http://https://fuckuskid00002505";

	public BlockFaceUV(final float[] p_i46228_1_, final int p_i46228_2_) {
		field_178351_a = p_i46228_1_;
		field_178350_b = p_i46228_2_;
	}

	public float func_178348_a(final int p_178348_1_) {
		if (field_178351_a == null) {
			throw new NullPointerException("uvs");
		} else {
			final int var2 = func_178347_d(p_178348_1_);
			return var2 != 0 && var2 != 1 ? field_178351_a[2] : field_178351_a[0];
		}
	}

	public float func_178346_b(final int p_178346_1_) {
		if (field_178351_a == null) {
			throw new NullPointerException("uvs");
		} else {
			final int var2 = func_178347_d(p_178346_1_);
			return var2 != 0 && var2 != 3 ? field_178351_a[3] : field_178351_a[1];
		}
	}

	private int func_178347_d(final int p_178347_1_) {
		return (p_178347_1_ + field_178350_b / 90) % 4;
	}

	public int func_178345_c(final int p_178345_1_) {
		return (p_178345_1_ + 4 - field_178350_b / 90) % 4;
	}

	public void func_178349_a(final float[] p_178349_1_) {
		if (field_178351_a == null) {
			field_178351_a = p_178349_1_;
		}
	}

	static class Deserializer implements JsonDeserializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002504";

		public BlockFaceUV func_178293_a(final JsonElement p_178293_1_, final Type p_178293_2_,
				final JsonDeserializationContext p_178293_3_) {
			final JsonObject var4 = p_178293_1_.getAsJsonObject();
			final float[] var5 = func_178292_b(var4);
			final int var6 = func_178291_a(var4);
			return new BlockFaceUV(var5, var6);
		}

		protected int func_178291_a(final JsonObject p_178291_1_) {
			final int var2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178291_1_, "rotation", 0);

			if (var2 >= 0 && var2 % 90 == 0 && var2 / 90 <= 3) {
				return var2;
			} else {
				throw new JsonParseException("Invalid rotation " + var2 + " found, only 0/90/180/270 allowed");
			}
		}

		private float[] func_178292_b(final JsonObject p_178292_1_) {
			if (!p_178292_1_.has("uv")) {
				return null;
			} else {
				final JsonArray var2 = JsonUtils.getJsonObjectJsonArrayField(p_178292_1_, "uv");

				if (var2.size() != 4) {
					throw new JsonParseException("Expected 4 uv values, found: " + var2.size());
				} else {
					final float[] var3 = new float[4];

					for (int var4 = 0; var4 < var3.length; ++var4) {
						var3[var4] = JsonUtils.getJsonElementFloatValue(var2.get(var4), "uv[" + var4 + "]");
					}

					return var3;
				}
			}
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return func_178293_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}
	}
}
