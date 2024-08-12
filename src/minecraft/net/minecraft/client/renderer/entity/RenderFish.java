package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render {

public static final int EaZy = 756;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
	// private static final String __OBFID = "http://https://fuckuskid00000996";

	public RenderFish(final RenderManager p_i46175_1_) {
		super(p_i46175_1_);
	}

	public void func_180558_a(final EntityFishHook p_180558_1_, final double p_180558_2_, final double p_180558_4_,
			final double p_180558_6_, final float p_180558_8_, final float p_180558_9_) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) p_180558_2_, (float) p_180558_4_, (float) p_180558_6_);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		bindEntityTexture(p_180558_1_);
		final Tessellator var10 = Tessellator.getInstance();
		final WorldRenderer var11 = var10.getWorldRenderer();
		final byte var12 = 1;
		final byte var13 = 2;
		final float var14 = (var12 * 8 + 0) / 128.0F;
		final float var15 = (var12 * 8 + 8) / 128.0F;
		final float var16 = (var13 * 8 + 0) / 128.0F;
		final float var17 = (var13 * 8 + 8) / 128.0F;
		final float var18 = 1.0F;
		final float var19 = 0.5F;
		final float var20 = 0.5F;
		GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var11.startDrawingQuads();
		var11.func_178980_d(0.0F, 1.0F, 0.0F);
		var11.addVertexWithUV(0.0F - var19, 0.0F - var20, 0.0D, var14, var17);
		var11.addVertexWithUV(var18 - var19, 0.0F - var20, 0.0D, var15, var17);
		var11.addVertexWithUV(var18 - var19, 1.0F - var20, 0.0D, var15, var16);
		var11.addVertexWithUV(0.0F - var19, 1.0F - var20, 0.0D, var14, var16);
		var10.draw();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

		if (p_180558_1_.angler != null) {
			final float var21 = p_180558_1_.angler.getSwingProgress(p_180558_9_);
			final float var22 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float) Math.PI);
			Vec3 var23 = new Vec3(-0.36D, 0.03D, 0.35D);
			var23 = var23.rotatePitch(-(p_180558_1_.angler.prevRotationPitch
					+ (p_180558_1_.angler.rotationPitch - p_180558_1_.angler.prevRotationPitch) * p_180558_9_)
					* (float) Math.PI / 180.0F);
			var23 = var23.rotateYaw(-(p_180558_1_.angler.prevRotationYaw
					+ (p_180558_1_.angler.rotationYaw - p_180558_1_.angler.prevRotationYaw) * p_180558_9_)
					* (float) Math.PI / 180.0F);
			var23 = var23.rotateYaw(var22 * 0.5F);
			var23 = var23.rotatePitch(-var22 * 0.7F);
			double var24 = p_180558_1_.angler.prevPosX
					+ (p_180558_1_.angler.posX - p_180558_1_.angler.prevPosX) * p_180558_9_ + var23.xCoord;
			double var26 = p_180558_1_.angler.prevPosY
					+ (p_180558_1_.angler.posY - p_180558_1_.angler.prevPosY) * p_180558_9_ + var23.yCoord;
			double var28 = p_180558_1_.angler.prevPosZ
					+ (p_180558_1_.angler.posZ - p_180558_1_.angler.prevPosZ) * p_180558_9_ + var23.zCoord;
			double var30 = p_180558_1_.angler.getEyeHeight();

			if (renderManager.options != null && renderManager.options.thirdPersonView > 0
					|| p_180558_1_.angler != Minecraft.thePlayer) {
				final float var32 = (p_180558_1_.angler.prevRenderYawOffset
						+ (p_180558_1_.angler.renderYawOffset - p_180558_1_.angler.prevRenderYawOffset) * p_180558_9_)
						* (float) Math.PI / 180.0F;
				final double var33 = MathHelper.sin(var32);
				final double var35 = MathHelper.cos(var32);
				var24 = p_180558_1_.angler.prevPosX
						+ (p_180558_1_.angler.posX - p_180558_1_.angler.prevPosX) * p_180558_9_ - var35 * 0.35D
						- var33 * 0.8D;
				var26 = p_180558_1_.angler.prevPosY + var30
						+ (p_180558_1_.angler.posY - p_180558_1_.angler.prevPosY) * p_180558_9_ - 0.45D;
				var28 = p_180558_1_.angler.prevPosZ
						+ (p_180558_1_.angler.posZ - p_180558_1_.angler.prevPosZ) * p_180558_9_ - var33 * 0.35D
						+ var35 * 0.8D;
				var30 = p_180558_1_.angler.isSneaking() ? -0.1875D : 0.0D;
			}

			final double var47 = p_180558_1_.prevPosX + (p_180558_1_.posX - p_180558_1_.prevPosX) * p_180558_9_;
			final double var34 = p_180558_1_.prevPosY + (p_180558_1_.posY - p_180558_1_.prevPosY) * p_180558_9_ + 0.25D;
			final double var36 = p_180558_1_.prevPosZ + (p_180558_1_.posZ - p_180558_1_.prevPosZ) * p_180558_9_;
			final double var38 = (float) (var24 - var47);
			final double var40 = (float) (var26 - var34) + var30;
			final double var42 = (float) (var28 - var36);
			GlStateManager.disableTexture2D();
			GlStateManager.disableLighting();
			var11.startDrawing(3);
			var11.func_178991_c(0);
			final byte var44 = 16;

			for (int var45 = 0; var45 <= var44; ++var45) {
				final float var46 = (float) var45 / (float) var44;
				var11.addVertex(p_180558_2_ + var38 * var46,
						p_180558_4_ + var40 * (var46 * var46 + var46) * 0.5D + 0.25D, p_180558_6_ + var42 * var46);
			}

			var10.draw();
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
			super.doRender(p_180558_1_, p_180558_2_, p_180558_4_, p_180558_6_, p_180558_8_, p_180558_9_);
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityFishHook p_110775_1_) {
		return field_110792_a;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityFishHook) p_110775_1_);
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
		func_180558_a((EntityFishHook) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
