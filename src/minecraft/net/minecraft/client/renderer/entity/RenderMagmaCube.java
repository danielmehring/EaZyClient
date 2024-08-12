package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving {

public static final int EaZy = 766;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation magmaCubeTextures = new ResourceLocation(
			"textures/entity/slime/magmacube.png");
	// private static final String __OBFID = "http://https://fuckuskid00001009";

	public RenderMagmaCube(final RenderManager p_i46159_1_) {
		super(p_i46159_1_, new ModelMagmaCube(), 0.25F);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityMagmaCube p_110775_1_) {
		return magmaCubeTextures;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityMagmaCube p_77041_1_, final float p_77041_2_) {
		final int var3 = p_77041_1_.getSlimeSize();
		final float var4 = (p_77041_1_.prevSquishFactor
				+ (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (var3 * 0.5F + 1.0F);
		final float var5 = 1.0F / (var4 + 1.0F);
		final float var6 = var3;
		GlStateManager.scale(var5 * var6, 1.0F / var5 * var6, var5 * var6);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntityMagmaCube) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityMagmaCube) p_110775_1_);
	}
}
