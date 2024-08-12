package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;

import java.lang.reflect.Type;

import javax.vecmath.Vector3f;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ItemTransformVec3f {

public static final int EaZy = 683;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final ItemTransformVec3f field_178366_a = new ItemTransformVec3f(new Vector3f(), new Vector3f(),
			new Vector3f(1.0F, 1.0F, 1.0F));
	public final Vector3f field_178364_b;
	public final Vector3f field_178365_c;
	public final Vector3f field_178363_d;
	// private static final String __OBFID = "http://https://fuckuskid00002484";

	public ItemTransformVec3f(final Vector3f p_i46214_1_, final Vector3f p_i46214_2_, final Vector3f p_i46214_3_) {
		field_178364_b = new Vector3f(p_i46214_1_);
		field_178365_c = new Vector3f(p_i46214_2_);
		field_178363_d = new Vector3f(p_i46214_3_);
	}

	static class Deserializer implements JsonDeserializer {
		private static final Vector3f field_178362_a = new Vector3f(0.0F, 0.0F, 0.0F);
		private static final Vector3f field_178360_b = new Vector3f(0.0F, 0.0F, 0.0F);
		private static final Vector3f field_178361_c = new Vector3f(1.0F, 1.0F, 1.0F);
		// private static final String __OBFID =
		// "http://https://fuckuskid00002483";

		public ItemTransformVec3f func_178359_a(final JsonElement p_178359_1_, final Type p_178359_2_,
				final JsonDeserializationContext p_178359_3_) {
			final JsonObject var4 = p_178359_1_.getAsJsonObject();
			final Vector3f var5 = func_178358_a(var4, "rotation", field_178362_a);
			final Vector3f var6 = func_178358_a(var4, "translation", field_178360_b);
			var6.scale(0.0625F);
			MathHelper.clamp_double(var6.x, -1.5D, 1.5D);
			MathHelper.clamp_double(var6.y, -1.5D, 1.5D);
			MathHelper.clamp_double(var6.z, -1.5D, 1.5D);
			final Vector3f var7 = func_178358_a(var4, "scale", field_178361_c);
			MathHelper.clamp_double(var7.x, -1.5D, 1.5D);
			MathHelper.clamp_double(var7.y, -1.5D, 1.5D);
			MathHelper.clamp_double(var7.z, -1.5D, 1.5D);
			return new ItemTransformVec3f(var5, var6, var7);
		}

		private Vector3f func_178358_a(final JsonObject p_178358_1_, final String p_178358_2_,
				final Vector3f p_178358_3_) {
			if (!p_178358_1_.has(p_178358_2_)) {
				return p_178358_3_;
			} else {
				final JsonArray var4 = JsonUtils.getJsonObjectJsonArrayField(p_178358_1_, p_178358_2_);

				if (var4.size() != 3) {
					throw new JsonParseException("Expected 3 " + p_178358_2_ + " values, found: " + var4.size());
				} else {
					final float[] var5 = new float[3];

					for (int var6 = 0; var6 < var5.length; ++var6) {
						var5[var6] = JsonUtils.getJsonElementFloatValue(var4.get(var6), p_178358_2_ + "[" + var6 + "]");
					}

					return new Vector3f(var5);
				}
			}
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return func_178359_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}
	}
}
