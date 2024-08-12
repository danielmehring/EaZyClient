package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

import optifine.Config;
import shadersmod.client.Shaders;

public class LayerSpiderEyes implements LayerRenderer {

public static final int EaZy = 734;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177150_a = new ResourceLocation("textures/entity/spider_eyes.png");
	private final RenderSpider field_177149_b;
	// private static final String __OBFID = "http://https://fuckuskid00002410";

	public LayerSpiderEyes(final RenderSpider p_i46109_1_) {
		field_177149_b = p_i46109_1_;
	}

	public void func_177148_a(final EntitySpider p_177148_1_, final float p_177148_2_, final float p_177148_3_,
			final float p_177148_4_, final float p_177148_5_, final float p_177148_6_, final float p_177148_7_,
			final float p_177148_8_) {
		field_177149_b.bindTexture(field_177150_a);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(1, 1);

		if (p_177148_1_.isInvisible()) {
			GlStateManager.depthMask(false);
		} else {
			GlStateManager.depthMask(true);
		}

		final char var9 = 61680;
		int var10 = var9 % 65536;
		int var11 = var9 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0F, var11 / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (Config.isShaders()) {
			Shaders.beginSpiderEyes();
		}

		field_177149_b.getMainModel().render(p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_,
				p_177148_7_, p_177148_8_);
		final int var12 = p_177148_1_.getBrightnessForRender(p_177148_4_);
		var10 = var12 % 65536;
		var11 = var12 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0F, var11 / 1.0F);
		field_177149_b.func_177105_a(p_177148_1_, p_177148_4_);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		func_177148_a((EntitySpider) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
