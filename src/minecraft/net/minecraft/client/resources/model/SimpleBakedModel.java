package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class SimpleBakedModel implements IBakedModel {

public static final int EaZy = 892;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final List field_177563_a;
	protected final List field_177561_b;
	protected final boolean field_177562_c;
	protected final boolean ambientOcclusion;
	protected final TextureAtlasSprite texture;
	protected final ItemCameraTransforms field_177558_f;
	// private static final String __OBFID = "http://https://fuckuskid00002386";

	public SimpleBakedModel(final List p_i46077_1_, final List p_i46077_2_, final boolean p_i46077_3_,
			final boolean p_i46077_4_, final TextureAtlasSprite p_i46077_5_, final ItemCameraTransforms p_i46077_6_) {
		field_177563_a = p_i46077_1_;
		field_177561_b = p_i46077_2_;
		field_177562_c = p_i46077_3_;
		ambientOcclusion = p_i46077_4_;
		texture = p_i46077_5_;
		field_177558_f = p_i46077_6_;
	}

	@Override
	public List func_177551_a(final EnumFacing p_177551_1_) {
		return (List) field_177561_b.get(p_177551_1_.ordinal());
	}

	@Override
	public List func_177550_a() {
		return field_177563_a;
	}

	@Override
	public boolean isGui3d() {
		return field_177562_c;
	}

	@Override
	public boolean isAmbientOcclusionEnabled() {
		return ambientOcclusion;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getTexture() {
		return texture;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return field_177558_f;
	}

	public static class Builder {
		private final List field_177656_a;
		private final List field_177654_b;
		private final boolean field_177655_c;
		private TextureAtlasSprite field_177652_d;
		private final boolean field_177653_e;
		private final ItemCameraTransforms field_177651_f;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002385";

		public Builder(final ModelBlock p_i46074_1_) {
			this(p_i46074_1_.func_178309_b(), p_i46074_1_.isAmbientOcclusionEnabled(),
					new ItemCameraTransforms(p_i46074_1_.getThirdPersonTransform(),
							p_i46074_1_.getFirstPersonTransform(), p_i46074_1_.getHeadTransform(),
							p_i46074_1_.getInGuiTransform()));
		}

		public Builder(final IBakedModel p_i46075_1_, final TextureAtlasSprite p_i46075_2_) {
			this(p_i46075_1_.isGui3d(), p_i46075_1_.isAmbientOcclusionEnabled(), p_i46075_1_.getItemCameraTransforms());
			field_177652_d = p_i46075_1_.getTexture();
			final EnumFacing[] var3 = EnumFacing.values();
			final int var4 = var3.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				final EnumFacing var6 = var3[var5];
				func_177649_a(p_i46075_1_, p_i46075_2_, var6);
			}

			func_177647_a(p_i46075_1_, p_i46075_2_);
		}

		private void func_177649_a(final IBakedModel p_177649_1_, final TextureAtlasSprite p_177649_2_,
				final EnumFacing p_177649_3_) {
			final Iterator var4 = p_177649_1_.func_177551_a(p_177649_3_).iterator();

			while (var4.hasNext()) {
				final BakedQuad var5 = (BakedQuad) var4.next();
				func_177650_a(p_177649_3_, new BreakingFour(var5, p_177649_2_));
			}
		}

		private void func_177647_a(final IBakedModel p_177647_1_, final TextureAtlasSprite p_177647_2_) {
			final Iterator var3 = p_177647_1_.func_177550_a().iterator();

			while (var3.hasNext()) {
				final BakedQuad var4 = (BakedQuad) var3.next();
				func_177648_a(new BreakingFour(var4, p_177647_2_));
			}
		}

		private Builder(final boolean p_i46076_1_, final boolean p_i46076_2_, final ItemCameraTransforms p_i46076_3_) {
			field_177656_a = Lists.newArrayList();
			field_177654_b = Lists.newArrayListWithCapacity(6);
			final EnumFacing[] var4 = EnumFacing.values();
			final int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				field_177654_b.add(Lists.newArrayList());
			}

			field_177655_c = p_i46076_1_;
			field_177653_e = p_i46076_2_;
			field_177651_f = p_i46076_3_;
		}

		public SimpleBakedModel.Builder func_177650_a(final EnumFacing p_177650_1_, final BakedQuad p_177650_2_) {
			((List) field_177654_b.get(p_177650_1_.ordinal())).add(p_177650_2_);
			return this;
		}

		public SimpleBakedModel.Builder func_177648_a(final BakedQuad p_177648_1_) {
			field_177656_a.add(p_177648_1_);
			return this;
		}

		public SimpleBakedModel.Builder func_177646_a(final TextureAtlasSprite p_177646_1_) {
			field_177652_d = p_177646_1_;
			return this;
		}

		public IBakedModel func_177645_b() {
			if (field_177652_d == null) {
				throw new RuntimeException("Missing particle!");
			} else {
				return new SimpleBakedModel(field_177656_a, field_177654_b, field_177655_c, field_177653_e,
						field_177652_d, field_177651_f);
			}
		}
	}
}
