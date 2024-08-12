package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombieVillager extends ModelBiped {

public static final int EaZy = 615;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000865";

	public ModelZombieVillager() {
		this(0.0F, 0.0F, false);
	}

	public ModelZombieVillager(final float p_i1165_1_, final float p_i1165_2_, final boolean p_i1165_3_) {
		super(p_i1165_1_, 0.0F, 64, p_i1165_3_ ? 32 : 64);

		if (p_i1165_3_) {
			bipedHead = new ModelRenderer(this, 0, 0);
			bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1165_1_);
			bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
		} else {
			bipedHead = new ModelRenderer(this);
			bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
			bipedHead.setTextureOffset(0, 32).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1165_1_);
			bipedHead.setTextureOffset(24, 32).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i1165_1_);
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
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		final float var8 = MathHelper.sin(swingProgress * (float) Math.PI);
		final float var9 = MathHelper.sin((1.0F - (1.0F - swingProgress) * (1.0F - swingProgress)) * (float) Math.PI);
		bipedRightArm.rotateAngleZ = 0.0F;
		bipedLeftArm.rotateAngleZ = 0.0F;
		bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F);
		bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F;
		bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
		bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
		bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
		bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
		bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	}
}
