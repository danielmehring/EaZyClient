package net.minecraft.client.renderer.block.model;

import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ModelBlock {

public static final int EaZy = 684;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger LOGGER = LogManager.getLogger();
	static final Gson SERIALIZER = new GsonBuilder()
			.registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
			.registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
			.registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer())
			.registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
			.registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
			.registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).create();
	private final List elements;
	private final boolean ambientOcclusion;
	private final boolean field_178322_i;
	private final ItemCameraTransforms itemCameraTransforms;
	public String field_178317_b;
	protected final Map textures;
	protected ModelBlock parent;
	protected ResourceLocation parentLocation;
	// private static final String __OBFID = "http://https://fuckuskid00002503";

	public static ModelBlock deserialize(final Reader p_178307_0_) {
		return SERIALIZER.fromJson(p_178307_0_, ModelBlock.class);
	}

	public static ModelBlock deserialize(final String p_178294_0_) {
		return deserialize(new StringReader(p_178294_0_));
	}

	protected ModelBlock(final List p_i46225_1_, final Map p_i46225_2_, final boolean p_i46225_3_,
			final boolean p_i46225_4_, final ItemCameraTransforms p_i46225_5_) {
		this((ResourceLocation) null, p_i46225_1_, p_i46225_2_, p_i46225_3_, p_i46225_4_, p_i46225_5_);
	}

	protected ModelBlock(final ResourceLocation p_i46226_1_, final Map p_i46226_2_, final boolean p_i46226_3_,
			final boolean p_i46226_4_, final ItemCameraTransforms p_i46226_5_) {
		this(p_i46226_1_, Collections.emptyList(), p_i46226_2_, p_i46226_3_, p_i46226_4_, p_i46226_5_);
	}

	private ModelBlock(final ResourceLocation p_i46227_1_, final List p_i46227_2_, final Map p_i46227_3_,
			final boolean p_i46227_4_, final boolean p_i46227_5_, final ItemCameraTransforms p_i46227_6_) {
		field_178317_b = "";
		elements = p_i46227_2_;
		field_178322_i = p_i46227_4_;
		ambientOcclusion = p_i46227_5_;
		textures = p_i46227_3_;
		parentLocation = p_i46227_1_;
		itemCameraTransforms = p_i46227_6_;
	}

	public List getElements() {
		return hasParent() ? parent.getElements() : elements;
	}

	private boolean hasParent() {
		return parent != null;
	}

	public boolean func_178309_b() {
		return hasParent() ? parent.func_178309_b() : field_178322_i;
	}

	public boolean isAmbientOcclusionEnabled() {
		return ambientOcclusion;
	}

	public boolean isResolved() {
		return parentLocation == null || parent != null && parent.isResolved();
	}

	public void getParentFromMap(final Map p_178299_1_) {
		if (parentLocation != null) {
			parent = (ModelBlock) p_178299_1_.get(parentLocation);
		}
	}

	public boolean isTexturePresent(final String p_178300_1_) {
		return !"missingno".equals(this.resolveTextureName(p_178300_1_));
	}

	public String resolveTextureName(String p_178308_1_) {
		if (!isTextureName(p_178308_1_)) {
			p_178308_1_ = '#' + p_178308_1_;
		}

		return this.resolveTextureName(p_178308_1_, new ModelBlock.Bookkeep(null));
	}

	private String resolveTextureName(final String p_178302_1_, final ModelBlock.Bookkeep p_178302_2_) {
		if (isTextureName(p_178302_1_)) {
			if (this == p_178302_2_.field_178323_b) {
				LOGGER.warn(
						"Unable to resolve texture due to upward reference: " + p_178302_1_ + " in " + field_178317_b);
				return "missingno";
			} else {
				String var3 = (String) textures.get(p_178302_1_.substring(1));

				if (var3 == null && hasParent()) {
					var3 = parent.resolveTextureName(p_178302_1_, p_178302_2_);
				}

				p_178302_2_.field_178323_b = this;

				if (var3 != null && isTextureName(var3)) {
					var3 = p_178302_2_.model.resolveTextureName(var3, p_178302_2_);
				}

				return var3 != null && !isTextureName(var3) ? var3 : "missingno";
			}
		} else {
			return p_178302_1_;
		}
	}

	private boolean isTextureName(final String p_178304_1_) {
		return p_178304_1_.charAt(0) == 35;
	}

	public ResourceLocation getParentLocation() {
		return parentLocation;
	}

	public ModelBlock getRootModel() {
		return hasParent() ? parent.getRootModel() : this;
	}

	public ItemTransformVec3f getThirdPersonTransform() {
		return parent != null && itemCameraTransforms.field_178355_b == ItemTransformVec3f.field_178366_a
				? parent.getThirdPersonTransform() : itemCameraTransforms.field_178355_b;
	}

	public ItemTransformVec3f getFirstPersonTransform() {
		return parent != null && itemCameraTransforms.field_178356_c == ItemTransformVec3f.field_178366_a
				? parent.getFirstPersonTransform() : itemCameraTransforms.field_178356_c;
	}

	public ItemTransformVec3f getHeadTransform() {
		return parent != null && itemCameraTransforms.field_178353_d == ItemTransformVec3f.field_178366_a
				? parent.getHeadTransform() : itemCameraTransforms.field_178353_d;
	}

	public ItemTransformVec3f getInGuiTransform() {
		return parent != null && itemCameraTransforms.field_178354_e == ItemTransformVec3f.field_178366_a
				? parent.getInGuiTransform() : itemCameraTransforms.field_178354_e;
	}

	public static void func_178312_b(final Map p_178312_0_) {
		final Iterator var1 = p_178312_0_.values().iterator();

		while (var1.hasNext()) {
			final ModelBlock var2 = (ModelBlock) var1.next();

			try {
				ModelBlock var3 = var2.parent;

				for (ModelBlock var4 = var3.parent; var3 != var4; var4 = var4.parent.parent) {
					var3 = var3.parent;
				}

				throw new ModelBlock.LoopException();
			} catch (final NullPointerException var5) {
			}
		}
	}

	final class Bookkeep {
		public final ModelBlock model;
		public ModelBlock field_178323_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002501";

		private Bookkeep() {
			model = ModelBlock.this;
		}

		Bookkeep(final Object p_i46224_2_) {
			this();
		}
	}

	public static class Deserializer implements JsonDeserializer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002500";

		public ModelBlock func_178327_a(final JsonElement p_178327_1_, final Type p_178327_2_,
				final JsonDeserializationContext p_178327_3_) {
			final JsonObject var4 = p_178327_1_.getAsJsonObject();
			final List var5 = getModelElements(p_178327_3_, var4);
			final String var6 = getParent(var4);
			final boolean var7 = StringUtils.isEmpty(var6);
			final boolean var8 = var5.isEmpty();

			if (var8 && var7) {
				throw new JsonParseException("BlockModel requires either elements or parent, found neither");
			} else if (!var7 && !var8) {
				throw new JsonParseException("BlockModel requires either elements or parent, found both");
			} else {
				final Map var9 = getTextures(var4);
				final boolean var10 = getAmbientOcclusionEnabled(var4);
				ItemCameraTransforms var11 = ItemCameraTransforms.field_178357_a;

				if (var4.has("display")) {
					final JsonObject var12 = JsonUtils.getJsonObject(var4, "display");
					var11 = (ItemCameraTransforms) p_178327_3_.deserialize(var12, ItemCameraTransforms.class);
				}

				return var8 ? new ModelBlock(new ResourceLocation(var6), var9, var10, true, var11)
						: new ModelBlock(var5, var9, var10, true, var11);
			}
		}

		private Map getTextures(final JsonObject p_178329_1_) {
			final HashMap var2 = Maps.newHashMap();

			if (p_178329_1_.has("textures")) {
				final JsonObject var3 = p_178329_1_.getAsJsonObject("textures");
				final Iterator var4 = var3.entrySet().iterator();

				while (var4.hasNext()) {
					final Entry var5 = (Entry) var4.next();
					var2.put(var5.getKey(), ((JsonElement) var5.getValue()).getAsString());
				}
			}

			return var2;
		}

		private String getParent(final JsonObject p_178326_1_) {
			return JsonUtils.getJsonObjectStringFieldValueOrDefault(p_178326_1_, "parent", "");
		}

		protected boolean getAmbientOcclusionEnabled(final JsonObject p_178328_1_) {
			return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(p_178328_1_, "ambientocclusion", true);
		}

		protected List getModelElements(final JsonDeserializationContext p_178325_1_, final JsonObject p_178325_2_) {
			final ArrayList var3 = Lists.newArrayList();

			if (p_178325_2_.has("elements")) {
				final Iterator var4 = JsonUtils.getJsonObjectJsonArrayField(p_178325_2_, "elements").iterator();

				while (var4.hasNext()) {
					final JsonElement var5 = (JsonElement) var4.next();
					var3.add(p_178325_1_.deserialize(var5, BlockPart.class));
				}
			}

			return var3;
		}

		@Override
		public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_,
				final JsonDeserializationContext p_deserialize_3_) {
			return func_178327_a(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
		}
	}

	public static class LoopException extends RuntimeException {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002499";
	}
}
