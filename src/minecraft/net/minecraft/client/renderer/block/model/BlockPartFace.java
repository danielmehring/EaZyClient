package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BlockPartFace {

public static final int EaZy = 677;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final EnumFacing field_178246_a = null;
	public final EnumFacing field_178244_b;
	public final int field_178245_c;
	public final String field_178242_d;
	public final BlockFaceUV field_178243_e;
	// private static final String __OBFID = "http://https://fuckuskid00002508";

	public BlockPartFace(final EnumFacing p_i46230_1_, final int p_i46230_2_, final String p_i46230_3_,
			final BlockFaceUV p_i46230_4_) {
		field_178244_b = p_i46230_1_;
		field_178245_c = p_i46230_2_;
		field_178242_d = p_i46230_3_;
		field_178243_e = p_i46230_4_;
	}

	static class Deserializer implements JsonDeserializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002507";

		public BlockPartFace func_178338_a(final JsonElement p_178338_1_, final Type p_178338_2_,
				final JsonDeserializationContext p_178338_3_) {
			final JsonObject var4 = p_178338_1_.getAsJsonObject();
			final EnumFacing var5 = func_178339_c(var4);
			final int var6 = func_178337_a(var4);
			final String var7 = func_178340_b(var4);
			final BlockFaceUV var8 = (BlockFaceUV) p_178338_3_.deserialize(var4, BlockFaceUV.class);
			return new BlockPartFace(var5, var6, var7, var8);
		}

		protected int func_178337_a(final JsonObject p_178337_1_) {
			return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(p_178337_1_, "tintindex", -1);
		}

		private String func_178340_b(final JsonObject p_178340_1_) {
			return JsonUtils.getJsonObjectStringFieldValue(p_178340_1_, "texture");
		}

		private EnumFacing func_178339_c(final JsonObject p_178339_1_) {
			final String var2 = JsonUtils.getJsonObjectStringFieldValueOrDefault(p_178339_1_, "cullface", "");
			return EnumFacing.byName(var2);
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return func_178338_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}
	}
}
