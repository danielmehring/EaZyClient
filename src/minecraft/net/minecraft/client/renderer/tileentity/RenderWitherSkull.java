package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;

public class RenderWitherSkull extends Render {

public static final int EaZy = 831;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation(
			"textures/entity/wither/wither_invulnerable.png");
	private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");

	/** The Skeleton's head model. */
	private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
	// private static final String __OBFID = "http://https://fuckuskid00001035";

	public RenderWitherSkull(final RenderManager p_i46129_1_) {
		super(p_i46129_1_);
	}

	private float func_82400_a(final float p_82400_1_, final float p_82400_2_, final float p_82400_3_) {
		float var4;

		for (var4 = p_82400_2_ - p_82400_1_; var4 < -180.0F; var4 += 360.0F) {
		}

		while (var4 >= 180.0F) {
			var4 -= 360.0F;
		}

		return p_82400_1_ + p_82400_3_ * var4;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityWitherSkull p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		final float var10 = func_82400_a(p_76986_1_.prevRotationYaw, p_76986_1_.rotationYaw, p_76986_9_);
		final float var11 = p_76986_1_.prevRotationPitch
				+ (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
		GlStateManager.translate((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		final float var12 = 0.0625F;
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		GlStateManager.enableAlpha();
		bindEntityTexture(p_76986_1_);
		skeletonHeadModel.render(p_76986_1_, 0.0F, 0.0F, 0.0F, var10, var11, var12);
		GlStateManager.popMatrix();
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	protected ResourceLocation func_180564_a(final EntityWitherSkull p_180564_1_) {
		return p_180564_1_.isInvulnerable() ? invulnerableWitherTextures : witherTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180564_a((EntityWitherSkull) p_110775_1_);
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
		this.doRender((EntityWitherSkull) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
