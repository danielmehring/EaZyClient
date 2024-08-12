package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render {

public static final int EaZy = 755;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float scale;
	// private static final String __OBFID = "http://https://fuckuskid00000995";

	public RenderFireball(final RenderManager p_i46176_1_, final float p_i46176_2_) {
		super(p_i46176_1_);
		scale = p_i46176_2_;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityFireball p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		GlStateManager.pushMatrix();
		bindEntityTexture(p_76986_1_);
		GlStateManager.translate((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GlStateManager.enableRescaleNormal();
		final float var10 = scale;
		GlStateManager.scale(var10 / 1.0F, var10 / 1.0F, var10 / 1.0F);
		final TextureAtlasSprite var11 = Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.getParticleIcon(Items.fire_charge);
		final Tessellator var12 = Tessellator.getInstance();
		final WorldRenderer var13 = var12.getWorldRenderer();
		final float var14 = var11.getMinU();
		final float var15 = var11.getMaxU();
		final float var16 = var11.getMinV();
		final float var17 = var11.getMaxV();
		final float var18 = 1.0F;
		final float var19 = 0.5F;
		final float var20 = 0.25F;
		GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var13.startDrawingQuads();
		var13.func_178980_d(0.0F, 1.0F, 0.0F);
		var13.addVertexWithUV(0.0F - var19, 0.0F - var20, 0.0D, var14, var17);
		var13.addVertexWithUV(var18 - var19, 0.0F - var20, 0.0D, var15, var17);
		var13.addVertexWithUV(var18 - var19, 1.0F - var20, 0.0D, var15, var16);
		var13.addVertexWithUV(0.0F - var19, 1.0F - var20, 0.0D, var14, var16);
		var12.draw();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	protected ResourceLocation func_180556_a(final EntityFireball p_180556_1_) {
		return TextureMap.locationBlocksTexture;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180556_a((EntityFireball) p_110775_1_);
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
		this.doRender((EntityFireball) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
