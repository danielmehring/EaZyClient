package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;

public class RenderWither extends RenderLiving {

public static final int EaZy = 790;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation(
			"textures/entity/wither/wither_invulnerable.png");
	private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
	// private static final String __OBFID = "http://https://fuckuskid00001034";

	public RenderWither(final RenderManager p_i46130_1_) {
		super(p_i46130_1_, new ModelWither(0.0F), 1.0F);
		addLayer(new LayerWitherAura(this));
	}

	public void func_180591_a(final EntityWither p_180591_1_, final double p_180591_2_, final double p_180591_4_,
			final double p_180591_6_, final float p_180591_8_, final float p_180591_9_) {
		BossStatus.setBossStatus(p_180591_1_, true);
		super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityWither p_110775_1_) {
		final int var2 = p_110775_1_.getInvulTime();
		return var2 > 0 && (var2 > 80 || var2 / 5 % 2 != 1) ? invulnerableWitherTextures : witherTextures;
	}

	protected void func_180592_a(final EntityWither p_180592_1_, final float p_180592_2_) {
		float var3 = 2.0F;
		final int var4 = p_180592_1_.getInvulTime();

		if (var4 > 0) {
			var3 -= (var4 - p_180592_2_) / 220.0F * 0.5F;
		}

		GlStateManager.scale(var3, var3, var3);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180591_a((EntityWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		func_180592_a((EntityWither) p_77041_1_, p_77041_2_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180591_a((EntityWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityWither) p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		func_180591_a((EntityWither) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
