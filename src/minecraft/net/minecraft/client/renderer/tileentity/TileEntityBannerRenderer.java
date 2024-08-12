package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer {

public static final int EaZy = 832;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Map field_178466_c = Maps.newHashMap();
	private static final ResourceLocation field_178464_d = new ResourceLocation("textures/entity/banner_base.png");
	private final ModelBanner field_178465_e = new ModelBanner();
	// private static final String __OBFID = "http://https://fuckuskid00002473";

	public void func_180545_a(final TileEntityBanner p_180545_1_, final double p_180545_2_, final double p_180545_4_,
			final double p_180545_6_, final float p_180545_8_, final int p_180545_9_) {
		final boolean var10 = p_180545_1_.getWorld() != null;
		final boolean var11 = !var10 || p_180545_1_.getBlockType() == Blocks.standing_banner;
		final int var12 = var10 ? p_180545_1_.getBlockMetadata() : 0;
		final long var13 = var10 ? p_180545_1_.getWorld().getTotalWorldTime() : 0L;
		GlStateManager.pushMatrix();
		final float var15 = 0.6666667F;
		float var17;

		if (var11) {
			GlStateManager.translate((float) p_180545_2_ + 0.5F, (float) p_180545_4_ + 0.75F * var15,
					(float) p_180545_6_ + 0.5F);
			final float var16 = var12 * 360 / 16.0F;
			GlStateManager.rotate(-var16, 0.0F, 1.0F, 0.0F);
			field_178465_e.bannerStand.showModel = true;
		} else {
			var17 = 0.0F;

			if (var12 == 2) {
				var17 = 180.0F;
			}

			if (var12 == 4) {
				var17 = 90.0F;
			}

			if (var12 == 5) {
				var17 = -90.0F;
			}

			GlStateManager.translate((float) p_180545_2_ + 0.5F, (float) p_180545_4_ - 0.25F * var15,
					(float) p_180545_6_ + 0.5F);
			GlStateManager.rotate(-var17, 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
			field_178465_e.bannerStand.showModel = false;
		}

		final BlockPos var19 = p_180545_1_.getPos();
		var17 = (float) (var19.getX() * 7 + var19.getY() * 9 + var19.getZ() * 13) + (float) var13 + p_180545_8_;
		field_178465_e.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(var17 * (float) Math.PI * 0.02F))
				* (float) Math.PI;
		GlStateManager.enableRescaleNormal();
		final ResourceLocation var18 = func_178463_a(p_180545_1_);

		if (var18 != null) {
			bindTexture(var18);
			GlStateManager.pushMatrix();
			GlStateManager.scale(var15, -var15, -var15);
			field_178465_e.func_178687_a();
			GlStateManager.popMatrix();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	private ResourceLocation func_178463_a(final TileEntityBanner p_178463_1_) {
		final String var2 = p_178463_1_.func_175116_e();

		if (var2.isEmpty()) {
			return null;
		} else {
			TileEntityBannerRenderer.TimedBannerTexture var3 = (TileEntityBannerRenderer.TimedBannerTexture) field_178466_c
					.get(var2);

			if (var3 == null) {
				if (field_178466_c.size() >= 256) {
					final long var4 = System.currentTimeMillis();
					final Iterator var6 = field_178466_c.keySet().iterator();

					while (var6.hasNext()) {
						final String var7 = (String) var6.next();
						final TileEntityBannerRenderer.TimedBannerTexture var8 = (TileEntityBannerRenderer.TimedBannerTexture) field_178466_c
								.get(var7);

						if (var4 - var8.field_178472_a > 60000L) {

							Minecraft.getTextureManager().deleteTexture(var8.field_178471_b);
							var6.remove();
						}
					}

					if (field_178466_c.size() >= 256) {
						return null;
					}
				}

				final List var9 = p_178463_1_.func_175114_c();
				final List var5 = p_178463_1_.func_175110_d();
				final ArrayList var10 = Lists.newArrayList();
				final Iterator var11 = var9.iterator();

				while (var11.hasNext()) {
					final TileEntityBanner.EnumBannerPattern var12 = (TileEntityBanner.EnumBannerPattern) var11.next();
					var10.add("textures/entity/banner/" + var12.func_177271_a() + ".png");
				}

				var3 = new TileEntityBannerRenderer.TimedBannerTexture(null);
				var3.field_178471_b = new ResourceLocation(var2);

				Minecraft.getTextureManager().loadTexture(var3.field_178471_b,
						new LayeredColorMaskTexture(field_178464_d, var10, var5));
				field_178466_c.put(var2, var3);
			}

			var3.field_178472_a = System.currentTimeMillis();
			return var3.field_178471_b;
		}
	}

	@Override
	public void renderTileEntityAt(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_,
			final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
		func_180545_a((TileEntityBanner) p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
	}

	static class TimedBannerTexture {
		public long field_178472_a;
		public ResourceLocation field_178471_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002471";

		private TimedBannerTexture() {}

		TimedBannerTexture(final Object p_i46209_1_) {
			this();
		}
	}
}
