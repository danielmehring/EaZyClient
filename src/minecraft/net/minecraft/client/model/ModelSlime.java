package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSlime extends ModelBase {

public static final int EaZy = 606;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The slime's bodies, both the inside box and the outside box */
	ModelRenderer slimeBodies;

	/** The slime's right eye */
	ModelRenderer slimeRightEye;

	/** The slime's left eye */
	ModelRenderer slimeLeftEye;

	/** The slime's mouth */
	ModelRenderer slimeMouth;
	// private static final String __OBFID = "http://https://fuckuskid00000858";

	public ModelSlime(final int p_i1157_1_) {
		slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
		slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);

		if (p_i1157_1_ > 0) {
			slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
			slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
			slimeRightEye = new ModelRenderer(this, 32, 0);
			slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
			slimeLeftEye = new ModelRenderer(this, 32, 4);
			slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
			slimeMouth = new ModelRenderer(this, 32, 8);
			slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		slimeBodies.render(p_78088_7_);

		if (slimeRightEye != null) {
			slimeRightEye.render(p_78088_7_);
			slimeLeftEye.render(p_78088_7_);
			slimeMouth.render(p_78088_7_);
		}
	}
}
