package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving {

public static final int EaZy = 771;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
	private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
	private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
	private static final ResourceLocation siameseOcelotTextures = new ResourceLocation(
			"textures/entity/cat/siamese.png");
	// private static final String __OBFID = "http://https://fuckuskid00001017";

	public RenderOcelot(final RenderManager p_i46151_1_, final ModelBase p_i46151_2_, final float p_i46151_3_) {
		super(p_i46151_1_, p_i46151_2_, p_i46151_3_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityOcelot p_110775_1_) {
		switch (p_110775_1_.getTameSkin()) {
			case 0:
			default:
				return ocelotTextures;

			case 1:
				return blackOcelotTextures;

			case 2:
				return redOcelotTextures;

			case 3:
				return siameseOcelotTextures;
		}
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(final EntityOcelot p_77041_1_, final float p_77041_2_) {
		super.preRenderCallback(p_77041_1_, p_77041_2_);

		if (p_77041_1_.isTamed()) {
			GlStateManager.scale(0.8F, 0.8F, 0.8F);
		}
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
		this.preRenderCallback((EntityOcelot) p_77041_1_, p_77041_2_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityOcelot) p_110775_1_);
	}
}
