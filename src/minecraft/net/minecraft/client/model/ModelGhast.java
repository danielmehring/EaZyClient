package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class ModelGhast extends ModelBase {

public static final int EaZy = 585;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	ModelRenderer body;
	ModelRenderer[] tentacles = new ModelRenderer[9];
	// private static final String __OBFID = "http://https://fuckuskid00000839";

	public ModelGhast() {
		final byte var1 = -16;
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
		body.rotationPointY += 24 + var1;
		final Random var2 = new Random(1660L);

		for (int var3 = 0; var3 < tentacles.length; ++var3) {
			tentacles[var3] = new ModelRenderer(this, 0, 0);
			final float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			final float var5 = (var3 / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
			final int var6 = var2.nextInt(7) + 8;
			tentacles[var3].addBox(-1.0F, 0.0F, -1.0F, 2, var6, 2);
			tentacles[var3].rotationPointX = var4;
			tentacles[var3].rotationPointZ = var5;
			tentacles[var3].rotationPointY = 31 + var1;
		}
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_,
			final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
		for (int var8 = 0; var8 < tentacles.length; ++var8) {
			tentacles[var8].rotateAngleX = 0.2F * MathHelper.sin(p_78087_3_ * 0.3F + var8) + 0.4F;
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 0.6F, 0.0F);
		body.render(p_78088_7_);
		final ModelRenderer[] var8 = tentacles;
		final int var9 = var8.length;

		for (int var10 = 0; var10 < var9; ++var10) {
			final ModelRenderer var11 = var8[var10];
			var11.render(p_78088_7_);
		}

		GlStateManager.popMatrix();
	}
}
