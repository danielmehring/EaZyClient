package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMagmaCube;

public class ModelMagmaCube extends ModelBase {

public static final int EaZy = 592;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	ModelRenderer[] segments = new ModelRenderer[8];
	ModelRenderer core;
	// private static final String __OBFID = "http://https://fuckuskid00000842";

	public ModelMagmaCube() {
		for (int var1 = 0; var1 < segments.length; ++var1) {
			byte var2 = 0;
			int var3 = var1;

			if (var1 == 2) {
				var2 = 24;
				var3 = 10;
			} else if (var1 == 3) {
				var2 = 24;
				var3 = 19;
			}

			segments[var1] = new ModelRenderer(this, var2, var3);
			segments[var1].addBox(-4.0F, 16 + var1, -4.0F, 8, 1, 8);
		}

		core = new ModelRenderer(this, 0, 16);
		core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the
	 * setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_,
			final float p_78086_4_) {
		final EntityMagmaCube var5 = (EntityMagmaCube) p_78086_1_;
		float var6 = var5.prevSquishFactor + (var5.squishFactor - var5.prevSquishFactor) * p_78086_4_;

		if (var6 < 0.0F) {
			var6 = 0.0F;
		}

		for (int var7 = 0; var7 < segments.length; ++var7) {
			segments[var7].rotationPointY = -(4 - var7) * var6 * 1.7F;
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		core.render(p_78088_7_);

		for (final ModelRenderer segment : segments) {
			segment.render(p_78088_7_);
		}
	}
}
