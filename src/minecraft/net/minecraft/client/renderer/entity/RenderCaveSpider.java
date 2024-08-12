package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider {

public static final int EaZy = 744;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation caveSpiderTextures = new ResourceLocation(
			"textures/entity/spider/cave_spider.png");
	// private static final String __OBFID = "http://https://fuckuskid00000982";

	public RenderCaveSpider(final RenderManager p_i46189_1_) {
		super(p_i46189_1_);
		shadowSize *= 0.7F;
	}

	protected void func_180585_a(final EntityCaveSpider p_180585_1_, final float p_180585_2_) {
		GlStateManager.scale(0.7F, 0.7F, 0.7F);
	}

	protected ResourceLocation func_180586_a(final EntityCaveSpider p_180586_1_) {
		return caveSpiderTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final EntitySpider p_110775_1_) {
		return func_180586_a((EntityCaveSpider) p_110775_1_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		func_180585_a((EntityCaveSpider) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180586_a((EntityCaveSpider) p_110775_1_);
	}
}
