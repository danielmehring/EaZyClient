package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;

public class LayerDeadmau5Head implements LayerRenderer {

public static final int EaZy = 720;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final RenderPlayer field_177208_a;
	// private static final String __OBFID = "http://https://fuckuskid00002421";

	public LayerDeadmau5Head(final RenderPlayer p_i46119_1_) {
		field_177208_a = p_i46119_1_;
	}

	public void func_177207_a(final AbstractClientPlayer p_177207_1_, final float p_177207_2_, final float p_177207_3_,
			final float p_177207_4_, final float p_177207_5_, final float p_177207_6_, final float p_177207_7_,
			final float p_177207_8_) {
		if (p_177207_1_.getName().equals("deadmau5") && p_177207_1_.hasSkin() && !p_177207_1_.isInvisible()) {
			field_177208_a.bindTexture(p_177207_1_.getLocationSkin());

			for (int var9 = 0; var9 < 2; ++var9) {
				final float var10 = p_177207_1_.prevRotationYaw
						+ (p_177207_1_.rotationYaw - p_177207_1_.prevRotationYaw) * p_177207_4_
						- (p_177207_1_.prevRenderYawOffset
								+ (p_177207_1_.renderYawOffset - p_177207_1_.prevRenderYawOffset) * p_177207_4_);
				final float var11 = p_177207_1_.prevRotationPitch
						+ (p_177207_1_.rotationPitch - p_177207_1_.prevRotationPitch) * p_177207_4_;
				GlStateManager.pushMatrix();
				GlStateManager.rotate(var10, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(var11, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.375F * (var9 * 2 - 1), 0.0F, 0.0F);
				GlStateManager.translate(0.0F, -0.375F, 0.0F);
				GlStateManager.rotate(-var11, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(-var10, 0.0F, 1.0F, 0.0F);
				final float var12 = 1.3333334F;
				GlStateManager.scale(var12, var12, var12);
				field_177208_a.func_177136_g().func_178727_b(0.0625F);
				GlStateManager.popMatrix();
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		func_177207_a((AbstractClientPlayer) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_,
				p_177141_6_, p_177141_7_, p_177141_8_);
	}
}
