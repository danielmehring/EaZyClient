package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import optifine.Config;
import shadersmod.client.Shaders;

public abstract class RenderLiving extends RendererLivingEntity {

public static final int EaZy = 765;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001015";

	public RenderLiving(final RenderManager p_i46153_1_, final ModelBase p_i46153_2_, final float p_i46153_3_) {
		super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
	}

	/**
	 * Test if the entity name must be rendered
	 */
	protected boolean canRenderName(final EntityLiving targetEntity) {
		return super.canRenderName(targetEntity) && (targetEntity.getAlwaysRenderNameTagForRender()
				|| targetEntity.hasCustomName() && targetEntity == renderManager.field_147941_i);
	}

	public boolean func_177104_a(final EntityLiving p_177104_1_, final ICamera p_177104_2_, final double p_177104_3_,
			final double p_177104_5_, final double p_177104_7_) {
		if (super.func_177071_a(p_177104_1_, p_177104_2_, p_177104_3_, p_177104_5_, p_177104_7_)) {
			return true;
		} else if (p_177104_1_.getLeashed() && p_177104_1_.getLeashedToEntity() != null) {
			final Entity var9 = p_177104_1_.getLeashedToEntity();
			return p_177104_2_.isBoundingBoxInFrustum(var9.getEntityBoundingBox());
		} else {
			return false;
		}
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		func_110827_b(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void func_177105_a(final EntityLiving p_177105_1_, final float p_177105_2_) {
		final int var3 = p_177105_1_.getBrightnessForRender(p_177105_2_);
		final int var4 = var3 % 65536;
		final int var5 = var3 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var4 / 1.0F, var5 / 1.0F);
	}

	private double func_110828_a(final double start, final double end, final double pct) {
		return start + (end - start) * pct;
	}

	protected void func_110827_b(final EntityLiving p_110827_1_, double p_110827_2_, double p_110827_4_,
			double p_110827_6_, final float p_110827_8_, final float p_110827_9_) {
		if (!Config.isShaders() || !Shaders.isShadowPass) {
			final Entity var10 = p_110827_1_.getLeashedToEntity();

			if (var10 != null) {
				p_110827_4_ -= (1.6D - p_110827_1_.height) * 0.5D;
				final Tessellator var11 = Tessellator.getInstance();
				final WorldRenderer var12 = var11.getWorldRenderer();
				final double var13 = func_110828_a(var10.prevRotationYaw, var10.rotationYaw, p_110827_9_ * 0.5F)
						* 0.01745329238474369D;
				final double var15 = func_110828_a(var10.prevRotationPitch, var10.rotationPitch, p_110827_9_ * 0.5F)
						* 0.01745329238474369D;
				double var17 = Math.cos(var13);
				double var19 = Math.sin(var13);
				double var21 = Math.sin(var15);

				if (var10 instanceof EntityHanging) {
					var17 = 0.0D;
					var19 = 0.0D;
					var21 = -1.0D;
				}

				final double var23 = Math.cos(var15);
				final double var25 = func_110828_a(var10.prevPosX, var10.posX, p_110827_9_) - var17 * 0.7D
						- var19 * 0.5D * var23;
				final double var27 = func_110828_a(var10.prevPosY + var10.getEyeHeight() * 0.7D,
						var10.posY + var10.getEyeHeight() * 0.7D, p_110827_9_) - var21 * 0.5D - 0.25D;
				final double var29 = func_110828_a(var10.prevPosZ, var10.posZ, p_110827_9_) - var19 * 0.7D
						+ var17 * 0.5D * var23;
				final double var31 = func_110828_a(p_110827_1_.prevRenderYawOffset, p_110827_1_.renderYawOffset,
						p_110827_9_) * 0.01745329238474369D + Math.PI / 2D;
				var17 = Math.cos(var31) * p_110827_1_.width * 0.4D;
				var19 = Math.sin(var31) * p_110827_1_.width * 0.4D;
				final double var33 = func_110828_a(p_110827_1_.prevPosX, p_110827_1_.posX, p_110827_9_) + var17;
				final double var35 = func_110828_a(p_110827_1_.prevPosY, p_110827_1_.posY, p_110827_9_);
				final double var37 = func_110828_a(p_110827_1_.prevPosZ, p_110827_1_.posZ, p_110827_9_) + var19;
				p_110827_2_ += var17;
				p_110827_6_ += var19;
				final double var39 = (float) (var25 - var33);
				final double var41 = (float) (var27 - var35);
				final double var43 = (float) (var29 - var37);
				GlStateManager.disableTexture2D();
				GlStateManager.disableLighting();
				GlStateManager.disableCull();

				if (Config.isShaders()) {
					Shaders.beginLeash();
				}

				var12.startDrawing(5);
				int var48;
				float var49;

				for (var48 = 0; var48 <= 24; ++var48) {
					if (var48 % 2 == 0) {
						var12.func_178960_a(0.5F, 0.4F, 0.3F, 1.0F);
					} else {
						var12.func_178960_a(0.35F, 0.28F, 0.21000001F, 1.0F);
					}

					var49 = var48 / 24.0F;
					var12.addVertex(p_110827_2_ + var39 * var49 + 0.0D,
							p_110827_4_ + var41 * (var49 * var49 + var49) * 0.5D + ((24.0F - var48) / 18.0F + 0.125F),
							p_110827_6_ + var43 * var49);
					var12.addVertex(p_110827_2_ + var39 * var49 + 0.025D, p_110827_4_
							+ var41 * (var49 * var49 + var49) * 0.5D + ((24.0F - var48) / 18.0F + 0.125F) + 0.025D,
							p_110827_6_ + var43 * var49);
				}

				var11.draw();
				var12.startDrawing(5);

				for (var48 = 0; var48 <= 24; ++var48) {
					if (var48 % 2 == 0) {
						var12.func_178960_a(0.5F, 0.4F, 0.3F, 1.0F);
					} else {
						var12.func_178960_a(0.35F, 0.28F, 0.21000001F, 1.0F);
					}

					var49 = var48 / 24.0F;
					var12.addVertex(p_110827_2_ + var39 * var49 + 0.0D, p_110827_4_
							+ var41 * (var49 * var49 + var49) * 0.5D + ((24.0F - var48) / 18.0F + 0.125F) + 0.025D,
							p_110827_6_ + var43 * var49);
					var12.addVertex(p_110827_2_ + var39 * var49 + 0.025D,
							p_110827_4_ + var41 * (var49 * var49 + var49) * 0.5D + ((24.0F - var48) / 18.0F + 0.125F),
							p_110827_6_ + var43 * var49 + 0.025D);
				}

				var11.draw();

				if (Config.isShaders()) {
					Shaders.endLeash();
				}

				GlStateManager.enableLighting();
				GlStateManager.enableTexture2D();
				GlStateManager.enableCull();
			}
		}
	}

	/**
	 * Test if the entity name must be rendered
	 */
	@Override
	protected boolean canRenderName(final EntityLivingBase targetEntity) {
		return this.canRenderName((EntityLiving) targetEntity);
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
		this.doRender((EntityLiving) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	protected boolean func_177070_b(final Entity p_177070_1_) {
		return this.canRenderName((EntityLiving) p_177070_1_);
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
		this.doRender((EntityLiving) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	public boolean func_177071_a(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_,
			final double p_177071_5_, final double p_177071_7_) {
		return func_177104_a((EntityLiving) p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_);
	}
}
