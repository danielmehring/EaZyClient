package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

import optifine.Config;
import shadersmod.client.Shaders;

public class LayerEndermanEyes implements LayerRenderer {

public static final int EaZy = 723;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177203_a = new ResourceLocation(
			"textures/entity/enderman/enderman_eyes.png");
	private final RenderEnderman field_177202_b;
	// private static final String __OBFID = "http://https://fuckuskid00002418";

	public LayerEndermanEyes(final RenderEnderman p_i46117_1_) {
		field_177202_b = p_i46117_1_;
	}

	public void func_177201_a(final EntityEnderman p_177201_1_, final float p_177201_2_, final float p_177201_3_,
			final float p_177201_4_, final float p_177201_5_, final float p_177201_6_, final float p_177201_7_,
			final float p_177201_8_) {
		field_177202_b.bindTexture(field_177203_a);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(1, 1);
		GlStateManager.disableLighting();

		if (p_177201_1_.isInvisible()) {
			GlStateManager.depthMask(false);
		} else {
			GlStateManager.depthMask(true);
		}

		final char var9 = 61680;
		final int var10 = var9 % 65536;
		final int var11 = var9 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0F, var11 / 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (Config.isShaders()) {
			Shaders.beginSpiderEyes();
		}

		field_177202_b.getMainModel().render(p_177201_1_, p_177201_2_, p_177201_3_, p_177201_5_, p_177201_6_,
				p_177201_7_, p_177201_8_);
		field_177202_b.func_177105_a(p_177201_1_, p_177201_4_);
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
		func_177201_a((EntityEnderman) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,
				p_177141_7_, p_177141_8_);
	}
}
