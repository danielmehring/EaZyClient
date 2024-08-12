package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Vector3f;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemModelGenerator {

public static final int EaZy = 682;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final List LAYERS = Lists
			.newArrayList(new String[] { "layer0", "layer1", "layer2", "layer3", "layer4" });
	// private static final String __OBFID = "http://https://fuckuskid00002488";

	public ModelBlock func_178392_a(final TextureMap p_178392_1_, final ModelBlock p_178392_2_) {
		final HashMap var3 = Maps.newHashMap();
		final ArrayList var4 = Lists.newArrayList();

		for (int var5 = 0; var5 < LAYERS.size(); ++var5) {
			final String var6 = (String) LAYERS.get(var5);

			if (!p_178392_2_.isTexturePresent(var6)) {
				break;
			}

			final String var7 = p_178392_2_.resolveTextureName(var6);
			var3.put(var6, var7);
			final TextureAtlasSprite var8 = p_178392_1_.getAtlasSprite(new ResourceLocation(var7).toString());
			var4.addAll(func_178394_a(var5, var6, var8));
		}

		if (var4.isEmpty()) {
			return null;
		} else {
			var3.put("particle", p_178392_2_.isTexturePresent("particle") ? p_178392_2_.resolveTextureName("particle")
					: (String) var3.get("layer0"));
			return new ModelBlock(var4, var3, false, false,
					new ItemCameraTransforms(p_178392_2_.getThirdPersonTransform(),
							p_178392_2_.getFirstPersonTransform(), p_178392_2_.getHeadTransform(),
							p_178392_2_.getInGuiTransform()));
		}
	}

	private List func_178394_a(final int p_178394_1_, final String p_178394_2_, final TextureAtlasSprite p_178394_3_) {
		final HashMap var4 = Maps.newHashMap();
		var4.put(EnumFacing.SOUTH, new BlockPartFace((EnumFacing) null, p_178394_1_, p_178394_2_,
				new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0)));
		var4.put(EnumFacing.NORTH, new BlockPartFace((EnumFacing) null, p_178394_1_, p_178394_2_,
				new BlockFaceUV(new float[] { 16.0F, 0.0F, 0.0F, 16.0F }, 0)));
		final ArrayList var5 = Lists.newArrayList();
		var5.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), var4,
				(BlockPartRotation) null, true));
		var5.addAll(func_178397_a(p_178394_3_, p_178394_2_, p_178394_1_));
		return var5;
	}

	private List func_178397_a(final TextureAtlasSprite p_178397_1_, final String p_178397_2_, final int p_178397_3_) {
		final float var4 = p_178397_1_.getIconWidth();
		final float var5 = p_178397_1_.getIconHeight();
		final ArrayList var6 = Lists.newArrayList();
		final Iterator var7 = func_178393_a(p_178397_1_).iterator();

		while (var7.hasNext()) {
			final ItemModelGenerator.Span var8 = (ItemModelGenerator.Span) var7.next();
			float var9 = 0.0F;
			float var10 = 0.0F;
			float var11 = 0.0F;
			float var12 = 0.0F;
			float var13 = 0.0F;
			float var14 = 0.0F;
			float var15 = 0.0F;
			float var16 = 0.0F;
			float var17 = 0.0F;
			float var18 = 0.0F;
			final float var19 = var8.func_178385_b();
			final float var20 = var8.func_178384_c();
			final float var21 = var8.func_178381_d();
			final ItemModelGenerator.SpanFacing var22 = var8.func_178383_a();

			switch (ItemModelGenerator.SwitchSpanFacing.field_178390_a[var22.ordinal()]) {
				case 1:
					var13 = var19;
					var9 = var19;
					var11 = var14 = var20 + 1.0F;
					var15 = var21;
					var10 = var21;
					var16 = var21;
					var12 = var21;
					var17 = 16.0F / var4;
					var18 = 16.0F / (var5 - 1.0F);
					break;

				case 2:
					var16 = var21;
					var15 = var21;
					var13 = var19;
					var9 = var19;
					var11 = var14 = var20 + 1.0F;
					var10 = var21 + 1.0F;
					var12 = var21 + 1.0F;
					var17 = 16.0F / var4;
					var18 = 16.0F / (var5 - 1.0F);
					break;

				case 3:
					var13 = var21;
					var9 = var21;
					var14 = var21;
					var11 = var21;
					var16 = var19;
					var10 = var19;
					var12 = var15 = var20 + 1.0F;
					var17 = 16.0F / (var4 - 1.0F);
					var18 = 16.0F / var5;
					break;

				case 4:
					var14 = var21;
					var13 = var21;
					var9 = var21 + 1.0F;
					var11 = var21 + 1.0F;
					var16 = var19;
					var10 = var19;
					var12 = var15 = var20 + 1.0F;
					var17 = 16.0F / (var4 - 1.0F);
					var18 = 16.0F / var5;
			}

			final float var23 = 16.0F / var4;
			final float var24 = 16.0F / var5;
			var9 *= var23;
			var11 *= var23;
			var10 *= var24;
			var12 *= var24;
			var10 = 16.0F - var10;
			var12 = 16.0F - var12;
			var13 *= var17;
			var14 *= var17;
			var15 *= var18;
			var16 *= var18;
			final HashMap var25 = Maps.newHashMap();
			var25.put(var22.func_178367_a(), new BlockPartFace((EnumFacing) null, p_178397_3_, p_178397_2_,
					new BlockFaceUV(new float[] { var13, var15, var14, var16 }, 0)));

			switch (ItemModelGenerator.SwitchSpanFacing.field_178390_a[var22.ordinal()]) {
				case 1:
					var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var11, var10, 8.5F), var25,
							(BlockPartRotation) null, true));
					break;

				case 2:
					var6.add(new BlockPart(new Vector3f(var9, var12, 7.5F), new Vector3f(var11, var12, 8.5F), var25,
							(BlockPartRotation) null, true));
					break;

				case 3:
					var6.add(new BlockPart(new Vector3f(var9, var10, 7.5F), new Vector3f(var9, var12, 8.5F), var25,
							(BlockPartRotation) null, true));
					break;

				case 4:
					var6.add(new BlockPart(new Vector3f(var11, var10, 7.5F), new Vector3f(var11, var12, 8.5F), var25,
							(BlockPartRotation) null, true));
			}
		}

		return var6;
	}

	private List func_178393_a(final TextureAtlasSprite p_178393_1_) {
		final int var2 = p_178393_1_.getIconWidth();
		final int var3 = p_178393_1_.getIconHeight();
		final ArrayList var4 = Lists.newArrayList();

		for (int var5 = 0; var5 < p_178393_1_.getFrameCount(); ++var5) {
			final int[] var6 = p_178393_1_.getFrameTextureData(var5)[0];

			for (int var7 = 0; var7 < var3; ++var7) {
				for (int var8 = 0; var8 < var2; ++var8) {
					final boolean var9 = !func_178391_a(var6, var8, var7, var2, var3);
					func_178396_a(ItemModelGenerator.SpanFacing.UP, var4, var6, var8, var7, var2, var3, var9);
					func_178396_a(ItemModelGenerator.SpanFacing.DOWN, var4, var6, var8, var7, var2, var3, var9);
					func_178396_a(ItemModelGenerator.SpanFacing.LEFT, var4, var6, var8, var7, var2, var3, var9);
					func_178396_a(ItemModelGenerator.SpanFacing.RIGHT, var4, var6, var8, var7, var2, var3, var9);
				}
			}
		}

		return var4;
	}

	private void func_178396_a(final ItemModelGenerator.SpanFacing p_178396_1_, final List p_178396_2_,
			final int[] p_178396_3_, final int p_178396_4_, final int p_178396_5_, final int p_178396_6_,
			final int p_178396_7_, final boolean p_178396_8_) {
		final boolean var9 = func_178391_a(p_178396_3_, p_178396_4_ + p_178396_1_.func_178372_b(),
				p_178396_5_ + p_178396_1_.func_178371_c(), p_178396_6_, p_178396_7_) && p_178396_8_;

		if (var9) {
			func_178395_a(p_178396_2_, p_178396_1_, p_178396_4_, p_178396_5_);
		}
	}

	private void func_178395_a(final List p_178395_1_, final ItemModelGenerator.SpanFacing p_178395_2_,
			final int p_178395_3_, final int p_178395_4_) {
		ItemModelGenerator.Span var5 = null;
		final Iterator var6 = p_178395_1_.iterator();

		while (var6.hasNext()) {
			final ItemModelGenerator.Span var7 = (ItemModelGenerator.Span) var6.next();

			if (var7.func_178383_a() == p_178395_2_) {
				final int var8 = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;

				if (var7.func_178381_d() == var8) {
					var5 = var7;
					break;
				}
			}
		}

		final int var9 = p_178395_2_.func_178369_d() ? p_178395_4_ : p_178395_3_;
		final int var10 = p_178395_2_.func_178369_d() ? p_178395_3_ : p_178395_4_;

		if (var5 == null) {
			p_178395_1_.add(new ItemModelGenerator.Span(p_178395_2_, var10, var9));
		} else {
			var5.func_178382_a(var10);
		}
	}

	private boolean func_178391_a(final int[] p_178391_1_, final int p_178391_2_, final int p_178391_3_,
			final int p_178391_4_, final int p_178391_5_) {
		return p_178391_2_ >= 0 && p_178391_3_ >= 0 && p_178391_2_ < p_178391_4_ && p_178391_3_ < p_178391_5_
				? (p_178391_1_[p_178391_3_ * p_178391_4_ + p_178391_2_] >> 24 & 255) == 0 : true;
	}

	static class Span {
		private final ItemModelGenerator.SpanFacing field_178389_a;
		private int field_178387_b;
		private int field_178388_c;
		private final int field_178386_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002486";

		public Span(final ItemModelGenerator.SpanFacing p_i46216_1_, final int p_i46216_2_, final int p_i46216_3_) {
			field_178389_a = p_i46216_1_;
			field_178387_b = p_i46216_2_;
			field_178388_c = p_i46216_2_;
			field_178386_d = p_i46216_3_;
		}

		public void func_178382_a(final int p_178382_1_) {
			if (p_178382_1_ < field_178387_b) {
				field_178387_b = p_178382_1_;
			} else if (p_178382_1_ > field_178388_c) {
				field_178388_c = p_178382_1_;
			}
		}

		public ItemModelGenerator.SpanFacing func_178383_a() {
			return field_178389_a;
		}

		public int func_178385_b() {
			return field_178387_b;
		}

		public int func_178384_c() {
			return field_178388_c;
		}

		public int func_178381_d() {
			return field_178386_d;
		}
	}

	static enum SpanFacing {
		UP("UP", 0, EnumFacing.UP, 0, -1), DOWN("DOWN", 1, EnumFacing.DOWN, 0, 1), LEFT("LEFT", 2, EnumFacing.EAST, -1,
				0), RIGHT("RIGHT", 3, EnumFacing.WEST, 1, 0);
		private final EnumFacing field_178376_e;
		private final int field_178373_f;
		private final int field_178374_g;

		private SpanFacing(final String p_i46215_1_, final int p_i46215_2_, final EnumFacing p_i46215_3_,
				final int p_i46215_4_, final int p_i46215_5_) {
			field_178376_e = p_i46215_3_;
			field_178373_f = p_i46215_4_;
			field_178374_g = p_i46215_5_;
		}

		public EnumFacing func_178367_a() {
			return field_178376_e;
		}

		public int func_178372_b() {
			return field_178373_f;
		}

		public int func_178371_c() {
			return field_178374_g;
		}

		private boolean func_178369_d() {
			return this == DOWN || this == UP;
		}
	}

	static final class SwitchSpanFacing {
		static final int[] field_178390_a = new int[ItemModelGenerator.SpanFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002487";

		static {
			try {
				field_178390_a[ItemModelGenerator.SpanFacing.UP.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178390_a[ItemModelGenerator.SpanFacing.DOWN.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178390_a[ItemModelGenerator.SpanFacing.LEFT.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178390_a[ItemModelGenerator.SpanFacing.RIGHT.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
