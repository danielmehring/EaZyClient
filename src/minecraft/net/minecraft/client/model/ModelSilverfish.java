package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSilverfish extends ModelBase {

public static final int EaZy = 603;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The body parts of the silverfish's model. */
	private final ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];

	/** The wings (dust-looking sprites) on the silverfish's model. */
	private final ModelRenderer[] silverfishWings;
	private final float[] field_78170_c = new float[7];

	/** The widths, heights, and lengths for the silverfish model boxes. */
	private static final int[][] silverfishBoxLength = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 },
			{ 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };

	/** The texture positions for the silverfish's model's boxes. */
	private static final int[][] silverfishTexturePositions = new int[][] { { 0, 0 }, { 0, 4 }, { 0, 9 }, { 0, 16 },
			{ 0, 22 }, { 11, 0 }, { 13, 4 } };
	// private static final String __OBFID = "http://https://fuckuskid00000855";

	public ModelSilverfish() {
		float var1 = -3.5F;

		for (int var2 = 0; var2 < silverfishBodyParts.length; ++var2) {
			silverfishBodyParts[var2] = new ModelRenderer(this, silverfishTexturePositions[var2][0],
					silverfishTexturePositions[var2][1]);
			silverfishBodyParts[var2].addBox(silverfishBoxLength[var2][0] * -0.5F, 0.0F,
					silverfishBoxLength[var2][2] * -0.5F, silverfishBoxLength[var2][0], silverfishBoxLength[var2][1],
					silverfishBoxLength[var2][2]);
			silverfishBodyParts[var2].setRotationPoint(0.0F, 24 - silverfishBoxLength[var2][1], var1);
			field_78170_c[var2] = var1;

			if (var2 < silverfishBodyParts.length - 1) {
				var1 += (silverfishBoxLength[var2][2] + silverfishBoxLength[var2 + 1][2]) * 0.5F;
			}
		}

		silverfishWings = new ModelRenderer[3];
		silverfishWings[0] = new ModelRenderer(this, 20, 0);
		silverfishWings[0].addBox(-5.0F, 0.0F, silverfishBoxLength[2][2] * -0.5F, 10, 8, silverfishBoxLength[2][2]);
		silverfishWings[0].setRotationPoint(0.0F, 16.0F, field_78170_c[2]);
		silverfishWings[1] = new ModelRenderer(this, 20, 11);
		silverfishWings[1].addBox(-3.0F, 0.0F, silverfishBoxLength[4][2] * -0.5F, 6, 4, silverfishBoxLength[4][2]);
		silverfishWings[1].setRotationPoint(0.0F, 20.0F, field_78170_c[4]);
		silverfishWings[2] = new ModelRenderer(this, 20, 18);
		silverfishWings[2].addBox(-3.0F, 0.0F, silverfishBoxLength[4][2] * -0.5F, 6, 5, silverfishBoxLength[1][2]);
		silverfishWings[2].setRotationPoint(0.0F, 19.0F, field_78170_c[1]);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		int var8;

		for (var8 = 0; var8 < silverfishBodyParts.length; ++var8) {
			silverfishBodyParts[var8].render(p_78088_7_);
		}

		for (var8 = 0; var8 < silverfishWings.length; ++var8) {
			silverfishWings[var8].render(p_78088_7_);
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
		for (int var8 = 0; var8 < silverfishBodyParts.length; ++var8) {
			silverfishBodyParts[var8].rotateAngleY = MathHelper.cos(p_78087_3_ * 0.9F + var8 * 0.15F * (float) Math.PI)
					* (float) Math.PI * 0.05F * (1 + Math.abs(var8 - 2));
			silverfishBodyParts[var8].rotationPointX = MathHelper.sin(
					p_78087_3_ * 0.9F + var8 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.2F * Math.abs(var8 - 2);
		}

		silverfishWings[0].rotateAngleY = silverfishBodyParts[2].rotateAngleY;
		silverfishWings[1].rotateAngleY = silverfishBodyParts[4].rotateAngleY;
		silverfishWings[1].rotationPointX = silverfishBodyParts[4].rotationPointX;
		silverfishWings[2].rotateAngleY = silverfishBodyParts[1].rotateAngleY;
		silverfishWings[2].rotationPointX = silverfishBodyParts[1].rotationPointX;
	}
}
