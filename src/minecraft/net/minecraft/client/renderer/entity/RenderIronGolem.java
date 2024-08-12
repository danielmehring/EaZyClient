package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerIronGolemFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;

public class RenderIronGolem extends RenderLiving {

public static final int EaZy = 761;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
	// private static final String __OBFID = "http://https://fuckuskid00001031";

	public RenderIronGolem(final RenderManager p_i46133_1_) {
		super(p_i46133_1_, new ModelIronGolem(), 0.5F);
		addLayer(new LayerIronGolemFlower(this));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(final EntityIronGolem p_110775_1_) {
		return ironGolemTextures;
	}

	protected void func_180588_a(final EntityIronGolem p_180588_1_, final float p_180588_2_, final float p_180588_3_,
			final float p_180588_4_) {
		super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);

		if (p_180588_1_.limbSwingAmount >= 0.01D) {
			final float var5 = 13.0F;
			final float var6 = p_180588_1_.limbSwing - p_180588_1_.limbSwingAmount * (1.0F - p_180588_4_) + 6.0F;
			final float var7 = (Math.abs(var6 % var5 - var5 * 0.5F) - var5 * 0.25F) / (var5 * 0.25F);
			GlStateManager.rotate(6.5F * var7, 0.0F, 0.0F, 1.0F);
		}
	}

	@Override
	protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_,
			final float p_77043_4_) {
		func_180588_a((EntityIronGolem) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
		return this.getEntityTexture((EntityIronGolem) p_110775_1_);
	}
}
