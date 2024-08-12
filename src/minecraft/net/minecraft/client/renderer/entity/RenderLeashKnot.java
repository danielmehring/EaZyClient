package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render {

public static final int EaZy = 763;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation leashKnotTextures = new ResourceLocation("textures/entity/lead_knot.png");
	private final ModelLeashKnot leashKnotModel = new ModelLeashKnot();
	// private static final String __OBFID = "http://https://fuckuskid00001010";

	public RenderLeashKnot(final RenderManager p_i46158_1_) {
		super(p_i46158_1_);
	}

	public void func_180559_a(final EntityLeashKnot p_180559_1_, final double p_180559_2_, final double p_180559_4_,
			final double p_180559_6_, final float p_180559_8_, final float p_180559_9_) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		GlStateManager.translate((float) p_180559_2_, (float) p_180559_4_, (float) p_180559_6_);
		final float var10 = 0.0625F;
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		GlStateManager.enableAlpha();
		bindEntityTexture(p_180559_1_);
		leashKnotModel.render(p_180559_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
		GlStateManager.popMatrix();
		super.doRender(p_180559_1_, p_180559_2_, p_180559_4_, p_180559_6_, p_180559_8_, p_180559_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityLeashKnot p_110775_1_) {
		return leashKnotTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityLeashKnot) p_110775_1_);
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
		func_180559_a((EntityLeashKnot) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
