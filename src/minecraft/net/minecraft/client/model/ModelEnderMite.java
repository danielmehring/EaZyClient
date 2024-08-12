package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEnderMite extends ModelBase {

public static final int EaZy = 584;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final int[][] field_178716_a = new int[][] { { 4, 3, 2 }, { 6, 4, 5 }, { 3, 3, 1 }, { 1, 2, 1 } };
	private static final int[][] field_178714_b = new int[][] { { 0, 0 }, { 0, 5 }, { 0, 14 }, { 0, 18 } };
	private static final int field_178715_c = field_178716_a.length;
	private final ModelRenderer[] field_178713_d;
	// private static final String __OBFID = "http://https://fuckuskid00002629";

	public ModelEnderMite() {
		field_178713_d = new ModelRenderer[field_178715_c];
		float var1 = -3.5F;

		for (int var2 = 0; var2 < field_178713_d.length; ++var2) {
			field_178713_d[var2] = new ModelRenderer(this, field_178714_b[var2][0], field_178714_b[var2][1]);
			field_178713_d[var2].addBox(field_178716_a[var2][0] * -0.5F, 0.0F, field_178716_a[var2][2] * -0.5F,
					field_178716_a[var2][0], field_178716_a[var2][1], field_178716_a[var2][2]);
			field_178713_d[var2].setRotationPoint(0.0F, 24 - field_178716_a[var2][1], var1);

			if (var2 < field_178713_d.length - 1) {
				var1 += (field_178716_a[var2][2] + field_178716_a[var2 + 1][2]) * 0.5F;
			}
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

		for (final ModelRenderer element : field_178713_d) {
			element.render(p_78088_7_);
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
		for (int var8 = 0; var8 < field_178713_d.length; ++var8) {
			field_178713_d[var8].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + var8 * 0.15F * (float) Math.PI)
					* (float) Math.PI * 0.01F * (1 + Math.abs(var8 - 2));
			field_178713_d[var8].rotationPointX = MathHelper.sin(p_78087_3_ * 0.9F + var8 * 0.15F * (float) Math.PI)
					* (float) Math.PI * 0.1F * Math.abs(var8 - 2);
		}
	}
}
