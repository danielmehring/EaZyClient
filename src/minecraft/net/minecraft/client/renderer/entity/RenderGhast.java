package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.ResourceLocation;

public class RenderGhast extends RenderLiving {

public static final int EaZy = 757;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
	private static final ResourceLocation ghastShootingTextures = new ResourceLocation(
			"textures/entity/ghast/ghast_shooting.png");
	// private static final String __OBFID = "http://https://fuckuskid00000997";

	public RenderGhast(final RenderManager p_i46174_1_) {
		super(p_i46174_1_, new ModelGhast(), 0.5F);
	}

	protected ResourceLocation func_180576_a(final EntityGhast p_180576_1_) {
		return p_180576_1_.func_110182_bF() ? ghastShootingTextures : ghastTextures;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityGhast p_77041_1_, final float p_77041_2_) {
		final float var3 = 1.0F;
		final float var4 = (8.0F + var3) / 2.0F;
		final float var5 = (8.0F + 1.0F / var3) / 2.0F;
		GlStateManager.scale(var5, var4, var5);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntityGhast) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180576_a((EntityGhast) p_110775_1_);
	}
}
