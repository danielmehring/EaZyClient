package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

public class WeightedBakedModel implements IBakedModel {

public static final int EaZy = 893;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final int totalWeight;
	private final List models;
	private final IBakedModel baseModel;
	// private static final String __OBFID = "http://https://fuckuskid00002384";

	public WeightedBakedModel(final List p_i46073_1_) {
		models = p_i46073_1_;
		totalWeight = WeightedRandom.getTotalWeight(p_i46073_1_);
		baseModel = ((WeightedBakedModel.MyWeighedRandomItem) p_i46073_1_.get(0)).model;
	}

	@Override
	public List func_177551_a(final EnumFacing p_177551_1_) {
		return baseModel.func_177551_a(p_177551_1_);
	}

	@Override
	public List func_177550_a() {
		return baseModel.func_177550_a();
	}

	@Override
	public boolean isGui3d() {
		return baseModel.isGui3d();
	}

	@Override
	public boolean isAmbientOcclusionEnabled() {
		return baseModel.isAmbientOcclusionEnabled();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return baseModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getTexture() {
		return baseModel.getTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return baseModel.getItemCameraTransforms();
	}

	public IBakedModel func_177564_a(final long p_177564_1_) {
		return ((WeightedBakedModel.MyWeighedRandomItem) WeightedRandom.func_180166_a(models,
				Math.abs((int) p_177564_1_ >> 16) % totalWeight)).model;
	}

	public static class Builder {
		private final List field_177678_a = Lists.newArrayList();
		// private static final String __OBFID =
		// "http://https://fuckuskid00002383";

		public WeightedBakedModel.Builder add(final IBakedModel p_177677_1_, final int p_177677_2_) {
			field_177678_a.add(new WeightedBakedModel.MyWeighedRandomItem(p_177677_1_, p_177677_2_));
			return this;
		}

		public WeightedBakedModel build() {
			Collections.sort(field_177678_a);
			return new WeightedBakedModel(field_177678_a);
		}

		public IBakedModel first() {
			return ((WeightedBakedModel.MyWeighedRandomItem) field_177678_a.get(0)).model;
		}
	}

	static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable {
		protected final IBakedModel model;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002382";

		public MyWeighedRandomItem(final IBakedModel p_i46072_1_, final int p_i46072_2_) {
			super(p_i46072_2_);
			model = p_i46072_1_;
		}

		public int func_177634_a(final WeightedBakedModel.MyWeighedRandomItem p_177634_1_) {
			return ComparisonChain.start().compare(p_177634_1_.itemWeight, itemWeight)
					.compare(func_177635_a(), p_177634_1_.func_177635_a()).result();
		}

		protected int func_177635_a() {
			int var1 = model.func_177550_a().size();
			final EnumFacing[] var2 = EnumFacing.values();
			final int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final EnumFacing var5 = var2[var4];
				var1 += model.func_177551_a(var5).size();
			}

			return var1;
		}

		@Override
		public String toString() {
			return "MyWeighedRandomItem{weight=" + itemWeight + ", model=" + model + '}';
		}

		@Override
		public int compareTo(final Object p_compareTo_1_) {
			return func_177634_a((WeightedBakedModel.MyWeighedRandomItem) p_compareTo_1_);
		}
	}
}
