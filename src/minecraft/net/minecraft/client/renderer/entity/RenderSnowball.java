package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball extends Render {

public static final int EaZy = 782;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Item field_177084_a;
	private final RenderItem field_177083_e;
	// private static final String __OBFID = "http://https://fuckuskid00001008";

	public RenderSnowball(final RenderManager p_i46137_1_, final Item p_i46137_2_, final RenderItem p_i46137_3_) {
		super(p_i46137_1_);
		field_177084_a = p_i46137_2_;
		field_177083_e = p_i46137_3_;
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
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		bindTexture(TextureMap.locationBlocksTexture);
		field_177083_e.func_175043_b(func_177082_d(p_76986_1_));
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public ItemStack func_177082_d(final Entity p_177082_1_) {
		return new ItemStack(field_177084_a, 1, 0);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return TextureMap.locationBlocksTexture;
	}
}
