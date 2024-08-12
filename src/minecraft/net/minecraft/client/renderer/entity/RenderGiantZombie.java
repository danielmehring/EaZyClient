package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

public class RenderGiantZombie extends RenderLiving {

public static final int EaZy = 758;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");

	/** Scale of the model to use */
	private final float scale;
	// private static final String __OBFID = "http://https://fuckuskid00000998";

	public RenderGiantZombie(final RenderManager p_i46173_1_, final ModelBase p_i46173_2_, final float p_i46173_3_,
			final float p_i46173_4_) {
		super(p_i46173_1_, p_i46173_2_, p_i46173_3_ * p_i46173_4_);
		scale = p_i46173_4_;
		addLayer(new LayerHeldItem(this));
		addLayer(new LayerBipedArmor(this) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002444";
			@Override
			protected void func_177177_a() {
				field_177189_c = new ModelZombie(0.5F, true);
				field_177186_d = new ModelZombie(1.0F, true);
			}
		});
	}

	@Override
	public void func_82422_c() {
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityGiantZombie p_77041_1_, final float p_77041_2_) {
		GlStateManager.scale(scale, scale, scale);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityGiantZombie p_110775_1_) {
		return zombieTextures;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntityGiantZombie) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityGiantZombie) p_110775_1_);
	}
}
