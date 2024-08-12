package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.ResourceLocation;

import optifine.Config;
import shadersmod.client.Shaders;

public class LayerEnderDragonEyes implements LayerRenderer {

public static final int EaZy = 722;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
	private final RenderDragon dragonRenderer;
	// private static final String __OBFID = "http://https://fuckuskid00002419";

	public LayerEnderDragonEyes(final RenderDragon p_i46118_1_) {
		dragonRenderer = p_i46118_1_;
	}

	public void func_177210_a(final EntityDragon p_177210_1_, final float p_177210_2_, final float p_177210_3_,
			final float p_177210_4_, final float p_177210_5_, final float p_177210_6_, final float p_177210_7_,
			final float p_177210_8_) {
		dragonRenderer.bindTexture(TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(1, 1);
		GlStateManager.disableLighting();
		GlStateManager.depthFunc(514);
		final char var9 = 61680;
		final int var10 = var9 % 65536;
		final int var11 = var9 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0F, var11 / 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (Config.isShaders()) {
			Shaders.beginSpiderEyes();
		}

		dragonRenderer.getMainModel().render(p_177210_1_, p_177210_2_, p_177210_3_, p_177210_5_, p_177210_6_,
				p_177210_7_, p_177210_8_);
		dragonRenderer.func_177105_a(p_177210_1_, p_177210_4_);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthFunc(515);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	@Override
	public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_,
			final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_,
			final float p_177141_8_) {
		func_177210_a((EntityDragon) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
