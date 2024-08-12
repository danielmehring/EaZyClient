package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving {

public static final int EaZy = 759;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177114_e = new ResourceLocation("textures/entity/guardian.png");
	private static final ResourceLocation field_177116_j = new ResourceLocation("textures/entity/guardian_elder.png");
	private static final ResourceLocation field_177117_k = new ResourceLocation("textures/entity/guardian_beam.png");
	int field_177115_a;
	// private static final String __OBFID = "http://https://fuckuskid00002443";

	public RenderGuardian(final RenderManager p_i46171_1_) {
		super(p_i46171_1_, new ModelGuardian(), 0.5F);
		field_177115_a = ((ModelGuardian) mainModel).func_178706_a();
	}

	public boolean func_177113_a(final EntityGuardian p_177113_1_, final ICamera p_177113_2_, final double p_177113_3_,
			final double p_177113_5_, final double p_177113_7_) {
		if (super.func_177104_a(p_177113_1_, p_177113_2_, p_177113_3_, p_177113_5_, p_177113_7_)) {
			return true;
		} else {
			if (p_177113_1_.func_175474_cn()) {
				final EntityLivingBase var9 = p_177113_1_.func_175466_co();

				if (var9 != null) {
					final Vec3 var10 = func_177110_a(var9, var9.height * 0.5D, 1.0F);
					final Vec3 var11 = func_177110_a(p_177113_1_, p_177113_1_.getEyeHeight(), 1.0F);

					if (p_177113_2_.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(var11.xCoord, var11.yCoord,
							var11.zCoord, var10.xCoord, var10.yCoord, var10.zCoord))) {
						return true;
					}
				}
			}

			return false;
		}
	}

	private Vec3 func_177110_a(final EntityLivingBase p_177110_1_, final double p_177110_2_, final float p_177110_4_) {
		final double var5 = p_177110_1_.lastTickPosX + (p_177110_1_.posX - p_177110_1_.lastTickPosX) * p_177110_4_;
		final double var7 = p_177110_2_ + p_177110_1_.lastTickPosY
				+ (p_177110_1_.posY - p_177110_1_.lastTickPosY) * p_177110_4_;
		final double var9 = p_177110_1_.lastTickPosZ + (p_177110_1_.posZ - p_177110_1_.lastTickPosZ) * p_177110_4_;
		return new Vec3(var5, var7, var9);
	}

	public void func_177109_a(final EntityGuardian p_177109_1_, final double p_177109_2_, final double p_177109_4_,
			final double p_177109_6_, final float p_177109_8_, final float p_177109_9_) {
		if (field_177115_a != ((ModelGuardian) mainModel).func_178706_a()) {
			mainModel = new ModelGuardian();
			field_177115_a = ((ModelGuardian) mainModel).func_178706_a();
		}

		super.doRender(p_177109_1_, p_177109_2_, p_177109_4_, p_177109_6_, p_177109_8_, p_177109_9_);
		final EntityLivingBase var10 = p_177109_1_.func_175466_co();

		if (var10 != null) {
			final float var11 = p_177109_1_.func_175477_p(p_177109_9_);
			final Tessellator var12 = Tessellator.getInstance();
			final WorldRenderer var13 = var12.getWorldRenderer();
			bindTexture(field_177117_k);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
			GlStateManager.disableLighting();
			GlStateManager.disableCull();
			GlStateManager.disableBlend();
			GlStateManager.depthMask(true);
			final float var14 = 240.0F;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var14, var14);
			GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
			final float var15 = p_177109_1_.worldObj.getTotalWorldTime() + p_177109_9_;
			final float var16 = var15 * 0.5F % 1.0F;
			final float var17 = p_177109_1_.getEyeHeight();
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) p_177109_2_, (float) p_177109_4_ + var17, (float) p_177109_6_);
			final Vec3 var18 = func_177110_a(var10, var10.height * 0.5D, p_177109_9_);
			final Vec3 var19 = func_177110_a(p_177109_1_, var17, p_177109_9_);
			Vec3 var20 = var18.subtract(var19);
			final double var21 = var20.lengthVector() + 1.0D;
			var20 = var20.normalize();
			final float var23 = (float) Math.acos(var20.yCoord);
			final float var24 = (float) Math.atan2(var20.zCoord, var20.xCoord);
			GlStateManager.rotate(((float) Math.PI / 2F + -var24) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(var23 * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			final byte var25 = 1;
			final double var26 = var15 * 0.05D * (1.0D - (var25 & 1) * 2.5D);
			var13.startDrawingQuads();
			final float var28 = var11 * var11;
			var13.func_178961_b(64 + (int) (var28 * 240.0F), 32 + (int) (var28 * 192.0F), 128 - (int) (var28 * 64.0F),
					255);
			final double var29 = var25 * 0.2D;
			final double var31 = var29 * 1.41D;
			final double var33 = 0.0D + Math.cos(var26 + 2.356194490192345D) * var31;
			final double var35 = 0.0D + Math.sin(var26 + 2.356194490192345D) * var31;
			final double var37 = 0.0D + Math.cos(var26 + Math.PI / 4D) * var31;
			final double var39 = 0.0D + Math.sin(var26 + Math.PI / 4D) * var31;
			final double var41 = 0.0D + Math.cos(var26 + 3.9269908169872414D) * var31;
			final double var43 = 0.0D + Math.sin(var26 + 3.9269908169872414D) * var31;
			final double var45 = 0.0D + Math.cos(var26 + 5.497787143782138D) * var31;
			final double var47 = 0.0D + Math.sin(var26 + 5.497787143782138D) * var31;
			final double var49 = 0.0D + Math.cos(var26 + Math.PI) * var29;
			final double var51 = 0.0D + Math.sin(var26 + Math.PI) * var29;
			final double var53 = 0.0D + Math.cos(var26 + 0.0D) * var29;
			final double var55 = 0.0D + Math.sin(var26 + 0.0D) * var29;
			final double var57 = 0.0D + Math.cos(var26 + Math.PI / 2D) * var29;
			final double var59 = 0.0D + Math.sin(var26 + Math.PI / 2D) * var29;
			final double var61 = 0.0D + Math.cos(var26 + Math.PI * 3D / 2D) * var29;
			final double var63 = 0.0D + Math.sin(var26 + Math.PI * 3D / 2D) * var29;
			final double var67 = 0.0D;
			final double var69 = 0.4999D;
			final double var71 = -1.0F + var16;
			final double var73 = var21 * (0.5D / var29) + var71;
			var13.addVertexWithUV(var49, var21, var51, var69, var73);
			var13.addVertexWithUV(var49, 0.0D, var51, var69, var71);
			var13.addVertexWithUV(var53, 0.0D, var55, var67, var71);
			var13.addVertexWithUV(var53, var21, var55, var67, var73);
			var13.addVertexWithUV(var57, var21, var59, var69, var73);
			var13.addVertexWithUV(var57, 0.0D, var59, var69, var71);
			var13.addVertexWithUV(var61, 0.0D, var63, var67, var71);
			var13.addVertexWithUV(var61, var21, var63, var67, var73);
			double var75 = 0.0D;

			if (p_177109_1_.ticksExisted % 2 == 0) {
				var75 = 0.5D;
			}

			var13.addVertexWithUV(var33, var21, var35, 0.5D, var75 + 0.5D);
			var13.addVertexWithUV(var37, var21, var39, 1.0D, var75 + 0.5D);
			var13.addVertexWithUV(var45, var21, var47, 1.0D, var75);
			var13.addVertexWithUV(var41, var21, var43, 0.5D, var75);
			var12.draw();
			GlStateManager.popMatrix();
		}
	}

	protected void func_177112_a(final EntityGuardian p_177112_1_, final float p_177112_2_) {
		if (p_177112_1_.func_175461_cl()) {
			GlStateManager.scale(2.35F, 2.35F, 2.35F);
		}
	}

	protected ResourceLocation func_177111_a(final EntityGuardian p_177111_1_) {
		return p_177111_1_.func_175461_cl() ? field_177116_j : field_177114_e;
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
		func_177109_a((EntityGuardian) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	public boolean func_177104_a(final EntityLiving p_177104_1_, final ICamera p_177104_2_, final double p_177104_3_,
			final double p_177104_5_, final double p_177104_7_) {
		return func_177113_a((EntityGuardian) p_177104_1_, p_177104_2_, p_177104_3_, p_177104_5_, p_177104_7_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		func_177112_a((EntityGuardian) p_77041_1_, p_77041_2_);
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
		func_177109_a((EntityGuardian) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_177111_a((EntityGuardian) p_110775_1_);
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
		func_177109_a((EntityGuardian) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	@Override
	public boolean func_177071_a(final Entity p_177071_1_, final ICamera p_177071_2_, final double p_177071_3_,
			final double p_177071_5_, final double p_177071_7_) {
		return func_177113_a((EntityGuardian) p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_);
	}
}
