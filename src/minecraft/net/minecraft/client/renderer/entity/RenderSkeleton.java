package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderSkeleton extends RenderBiped {

public static final int EaZy = 780;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation skeletonTextures = new ResourceLocation(
			"textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation witherSkeletonTextures = new ResourceLocation(
			"textures/entity/skeleton/wither_skeleton.png");
	// private static final String __OBFID = "http://https://fuckuskid00001023";

	public RenderSkeleton(final RenderManager p_i46143_1_) {
		super(p_i46143_1_, new ModelSkeleton(), 0.5F);
		addLayer(new LayerHeldItem(this));
		addLayer(new LayerBipedArmor(this) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002431";
			@Override
			protected void func_177177_a() {
				field_177189_c = new ModelSkeleton(0.5F, true);
				field_177186_d = new ModelSkeleton(1.0F, true);
			}
		});
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntitySkeleton p_77041_1_, final float p_77041_2_) {
		if (p_77041_1_.getSkeletonType() == 1) {
			GlStateManager.scale(1.2F, 1.2F, 1.2F);
		}
	}

	@Override
	public void func_82422_c() {
		GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation func_180577_a(final EntitySkeleton p_180577_1_) {
		return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
		return func_180577_a((EntitySkeleton) p_110775_1_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntitySkeleton) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return func_180577_a((EntitySkeleton) p_110775_1_);
	}
}
