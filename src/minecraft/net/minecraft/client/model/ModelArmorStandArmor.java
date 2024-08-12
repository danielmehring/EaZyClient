package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {

public static final int EaZy = 568;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002632";

	public ModelArmorStandArmor() {
		this(0.0F);
	}

	public ModelArmorStandArmor(final float p_i46307_1_) {
		this(p_i46307_1_, 64, 32);
	}

	protected ModelArmorStandArmor(final float p_i46308_1_, final int p_i46308_2_, final int p_i46308_3_) {
		super(p_i46308_1_, 0.0F, p_i46308_2_, p_i46308_3_);
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
		if (p_78087_7_ instanceof EntityArmorStand) {
			final EntityArmorStand var8 = (EntityArmorStand) p_78087_7_;
			bipedHead.rotateAngleX = 0.017453292F * var8.getHeadRotation().func_179415_b();
			bipedHead.rotateAngleY = 0.017453292F * var8.getHeadRotation().func_179416_c();
			bipedHead.rotateAngleZ = 0.017453292F * var8.getHeadRotation().func_179413_d();
			bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
			bipedBody.rotateAngleX = 0.017453292F * var8.getBodyRotation().func_179415_b();
			bipedBody.rotateAngleY = 0.017453292F * var8.getBodyRotation().func_179416_c();
			bipedBody.rotateAngleZ = 0.017453292F * var8.getBodyRotation().func_179413_d();
			bipedLeftArm.rotateAngleX = 0.017453292F * var8.getLeftArmRotation().func_179415_b();
			bipedLeftArm.rotateAngleY = 0.017453292F * var8.getLeftArmRotation().func_179416_c();
			bipedLeftArm.rotateAngleZ = 0.017453292F * var8.getLeftArmRotation().func_179413_d();
			bipedRightArm.rotateAngleX = 0.017453292F * var8.getRightArmRotation().func_179415_b();
			bipedRightArm.rotateAngleY = 0.017453292F * var8.getRightArmRotation().func_179416_c();
			bipedRightArm.rotateAngleZ = 0.017453292F * var8.getRightArmRotation().func_179413_d();
			bipedLeftLeg.rotateAngleX = 0.017453292F * var8.getLeftLegRotation().func_179415_b();
			bipedLeftLeg.rotateAngleY = 0.017453292F * var8.getLeftLegRotation().func_179416_c();
			bipedLeftLeg.rotateAngleZ = 0.017453292F * var8.getLeftLegRotation().func_179413_d();
			bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
			bipedRightLeg.rotateAngleX = 0.017453292F * var8.getRightLegRotation().func_179415_b();
			bipedRightLeg.rotateAngleY = 0.017453292F * var8.getRightLegRotation().func_179416_c();
			bipedRightLeg.rotateAngleZ = 0.017453292F * var8.getRightLegRotation().func_179413_d();
			bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
			func_178685_a(bipedHead, bipedHeadwear);
		}
	}
}
