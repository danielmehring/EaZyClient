package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEnderCrystal extends Render {

public static final int EaZy = 829;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation enderCrystalTextures = new ResourceLocation(
			"textures/entity/endercrystal/endercrystal.png");
	private final ModelBase field_76995_b = new ModelEnderCrystal(0.0F, true);
	// private static final String __OBFID = "http://https://fuckuskid00000987";

	public RenderEnderCrystal(final RenderManager p_i46184_1_) {
		super(p_i46184_1_);
		shadowSize = 0.5F;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(final EntityEnderCrystal p_76986_1_, final double p_76986_2_, final double p_76986_4_,
			final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
		final float var10 = p_76986_1_.innerRotation + p_76986_9_;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		bindTexture(enderCrystalTextures);
		float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
		var11 += var11 * var11;
		field_76995_b.render(p_76986_1_, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	protected ResourceLocation func_180554_a(final EntityEnderCrystal p_180554_1_) {
		return enderCrystalTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180554_a((EntityEnderCrystal) p_110775_1_);
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
		this.doRender((EntityEnderCrystal) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
