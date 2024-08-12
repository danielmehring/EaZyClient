package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer {

public static final int EaZy = 717;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderPlayer playerRenderer;
	// private static final String __OBFID = "http://https://fuckuskid00002425";

	public LayerCape(final RenderPlayer p_i46123_1_) {
		playerRenderer = p_i46123_1_;
	}

	public void doRenderLayer(final AbstractClientPlayer player, final float p_177166_2_, final float p_177166_3_,
			final float p_177166_4_, final float p_177166_5_, final float p_177166_6_, final float p_177166_7_,
			final float p_177166_8_) {
		if (player.hasCape() && !player.isInvisible() && player.func_175148_a(EnumPlayerModelParts.CAPE)
				&& player.getLocationCape() != null) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			playerRenderer.bindTexture(player.getLocationCape());
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, 0.0F, 0.125F);
			final double var9 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * p_177166_4_
					- (player.prevPosX + (player.posX - player.prevPosX) * p_177166_4_);
			final double var11 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * p_177166_4_
					- (player.prevPosY + (player.posY - player.prevPosY) * p_177166_4_);
			final double var13 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * p_177166_4_
					- (player.prevPosZ + (player.posZ - player.prevPosZ) * p_177166_4_);
			final float var15 = player.prevRenderYawOffset
					+ (player.renderYawOffset - player.prevRenderYawOffset) * p_177166_4_;
			final double var16 = MathHelper.sin(var15 * (float) Math.PI / 180.0F);
			final double var18 = -MathHelper.cos(var15 * (float) Math.PI / 180.0F);
			float var20 = (float) var11 * 10.0F;
			var20 = MathHelper.clamp_float(var20, -6.0F, 32.0F);
			float var21 = (float) (var9 * var16 + var13 * var18) * 100.0F;
			final float var22 = (float) (var9 * var18 - var13 * var16) * 100.0F;

			if (var21 < 0.0F) {
				var21 = 0.0F;
			}

			if (var21 > 165.0F) {
				var21 = 165.0F;
			}

			final float var23 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * p_177166_4_;
			var20 += MathHelper
					.sin((player.prevDistanceWalkedModified
							+ (player.distanceWalkedModified - player.prevDistanceWalkedModified) * p_177166_4_) * 6.0F)
					* 32.0F * var23;

			if (player.isSneaking()) {
				var20 += 25.0F;
				GlStateManager.translate(0.0F, 0.142F, -0.0178F);
			}

			GlStateManager.rotate(6.0F + var21 / 2.0F + var20, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(var22 / 2.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(-var22 / 2.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
			playerRenderer.func_177136_g().func_178728_c(0.0625F);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		this.doRenderLayer((AbstractClientPlayer) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_,
				p_177141_6_, p_177141_7_, p_177141_8_);
	}
}
