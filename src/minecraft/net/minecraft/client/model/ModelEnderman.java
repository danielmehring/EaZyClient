package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {

public static final int EaZy = 583;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Is the enderman carrying a block? */
	public boolean isCarrying;

	/** Is the enderman attacking an entity? */
	public boolean isAttacking;
	// private static final String __OBFID = "http://https://fuckuskid00000838";

	public ModelEnderman(final float p_i46305_1_) {
		super(0.0F, -14.0F, 64, 32);
		final float var2 = -14.0F;
		bipedHeadwear = new ModelRenderer(this, 0, 16);
		bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46305_1_ - 0.5F);
		bipedHeadwear.setRotationPoint(0.0F, 0.0F + var2, 0.0F);
		bipedBody = new ModelRenderer(this, 32, 16);
		bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46305_1_);
		bipedBody.setRotationPoint(0.0F, 0.0F + var2, 0.0F);
		bipedRightArm = new ModelRenderer(this, 56, 0);
		bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
		bipedRightArm.setRotationPoint(-3.0F, 2.0F + var2, 0.0F);
		bipedLeftArm = new ModelRenderer(this, 56, 0);
		bipedLeftArm.mirror = true;
		bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
		bipedLeftArm.setRotationPoint(5.0F, 2.0F + var2, 0.0F);
		bipedRightLeg = new ModelRenderer(this, 56, 0);
		bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
		bipedRightLeg.setRotationPoint(-2.0F, 12.0F + var2, 0.0F);
		bipedLeftLeg = new ModelRenderer(this, 56, 0);
		bipedLeftLeg.mirror = true;
		bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
		bipedLeftLeg.setRotationPoint(2.0F, 12.0F + var2, 0.0F);
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
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		bipedHead.showModel = true;
		final float var8 = -14.0F;
		bipedBody.rotateAngleX = 0.0F;
		bipedBody.rotationPointY = var8;
		bipedBody.rotationPointZ = -0.0F;
		bipedRightLeg.rotateAngleX -= 0.0F;
		bipedLeftLeg.rotateAngleX -= 0.0F;
		bipedRightArm.rotateAngleX = (float) (bipedRightArm.rotateAngleX * 0.5D);
		bipedLeftArm.rotateAngleX = (float) (bipedLeftArm.rotateAngleX * 0.5D);
		bipedRightLeg.rotateAngleX = (float) (bipedRightLeg.rotateAngleX * 0.5D);
		bipedLeftLeg.rotateAngleX = (float) (bipedLeftLeg.rotateAngleX * 0.5D);
		final float var9 = 0.4F;

		if (bipedRightArm.rotateAngleX > var9) {
			bipedRightArm.rotateAngleX = var9;
		}

		if (bipedLeftArm.rotateAngleX > var9) {
			bipedLeftArm.rotateAngleX = var9;
		}

		if (bipedRightArm.rotateAngleX < -var9) {
			bipedRightArm.rotateAngleX = -var9;
		}

		if (bipedLeftArm.rotateAngleX < -var9) {
			bipedLeftArm.rotateAngleX = -var9;
		}

		if (bipedRightLeg.rotateAngleX > var9) {
			bipedRightLeg.rotateAngleX = var9;
		}

		if (bipedLeftLeg.rotateAngleX > var9) {
			bipedLeftLeg.rotateAngleX = var9;
		}

		if (bipedRightLeg.rotateAngleX < -var9) {
			bipedRightLeg.rotateAngleX = -var9;
		}

		if (bipedLeftLeg.rotateAngleX < -var9) {
			bipedLeftLeg.rotateAngleX = -var9;
		}

		if (isCarrying) {
			bipedRightArm.rotateAngleX = -0.5F;
			bipedLeftArm.rotateAngleX = -0.5F;
			bipedRightArm.rotateAngleZ = 0.05F;
			bipedLeftArm.rotateAngleZ = -0.05F;
		}

		bipedRightArm.rotationPointZ = 0.0F;
		bipedLeftArm.rotationPointZ = 0.0F;
		bipedRightLeg.rotationPointZ = 0.0F;
		bipedLeftLeg.rotationPointZ = 0.0F;
		bipedRightLeg.rotationPointY = 9.0F + var8;
		bipedLeftLeg.rotationPointY = 9.0F + var8;
		bipedHead.rotationPointZ = -0.0F;
		bipedHead.rotationPointY = var8 + 1.0F;
		bipedHeadwear.rotationPointX = bipedHead.rotationPointX;
		bipedHeadwear.rotationPointY = bipedHead.rotationPointY;
		bipedHeadwear.rotationPointZ = bipedHead.rotationPointZ;
		bipedHeadwear.rotateAngleX = bipedHead.rotateAngleX;
		bipedHeadwear.rotateAngleY = bipedHead.rotateAngleY;
		bipedHeadwear.rotateAngleZ = bipedHead.rotateAngleZ;

		if (isAttacking) {
			final float var10 = 1.0F;
			bipedHead.rotationPointY -= var10 * 5.0F;
		}
	}
}
