package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving {

public static final int EaZy = 785;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation squidTextures = new ResourceLocation("textures/entity/squid.png");
	// private static final String __OBFID = "http://https://fuckuskid00001028";

	public RenderSquid(final RenderManager p_i46138_1_, final ModelBase p_i46138_2_, final float p_i46138_3_) {
		super(p_i46138_1_, p_i46138_2_, p_i46138_3_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntitySquid p_110775_1_) {
		return squidTextures;
	}

	protected void rotateCorpse(final EntitySquid p_77043_1_, final float p_77043_2_, final float p_77043_3_,
			final float p_77043_4_) {
		final float var5 = p_77043_1_.prevSquidPitch + (p_77043_1_.squidPitch - p_77043_1_.prevSquidPitch) * p_77043_4_;
		final float var6 = p_77043_1_.prevSquidYaw + (p_77043_1_.squidYaw - p_77043_1_.prevSquidYaw) * p_77043_4_;
		GlStateManager.translate(0.0F, 0.5F, 0.0F);
		GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(var5, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(var6, 0.0F, 1.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.2F, 0.0F);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(final EntitySquid p_77044_1_, final float p_77044_2_) {
		return p_77044_1_.lastTentacleAngle + (p_77044_1_.tentacleAngle - p_77044_1_.lastTentacleAngle) * p_77044_2_;
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
		return this.handleRotationFloat((EntitySquid) p_77044_1_, p_77044_2_);
	}

	@Override
	protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_,
			final float p_77043_4_) {
		this.rotateCorpse((EntitySquid) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntitySquid) p_110775_1_);
	}
}
