package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;

public class ModelSheep2 extends ModelQuadruped {

public static final int EaZy = 601;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float field_78153_i;
	// private static final String __OBFID = "http://https://fuckuskid00000853";

	public ModelSheep2() {
		super(12, 0.0F);
		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
		head.setRotationPoint(0.0F, 6.0F, -8.0F);
		body = new ModelRenderer(this, 28, 8);
		body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
		body.setRotationPoint(0.0F, 5.0F, 2.0F);
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the
	 * setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_,
			final float p_78086_4_) {
		super.setLivingAnimations(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
		head.rotationPointY = 6.0F + ((EntitySheep) p_78086_1_).getHeadRotationPointY(p_78086_4_) * 9.0F;
		field_78153_i = ((EntitySheep) p_78086_1_).getHeadRotationAngleX(p_78086_4_);
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
		head.rotateAngleX = field_78153_i;
	}
}
