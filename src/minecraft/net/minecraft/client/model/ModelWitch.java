package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWitch extends ModelVillager {

public static final int EaZy = 611;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public boolean field_82900_g;
	private final ModelRenderer field_82901_h = new ModelRenderer(this).setTextureSize(64, 128);
	private final ModelRenderer witchHat;
	// private static final String __OBFID = "http://https://fuckuskid00000866";

	public ModelWitch(final float p_i46361_1_) {
		super(p_i46361_1_, 0.0F, 64, 128);
		field_82901_h.setRotationPoint(0.0F, -2.0F, 0.0F);
		field_82901_h.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
		villagerNose.addChild(field_82901_h);
		witchHat = new ModelRenderer(this).setTextureSize(64, 128);
		witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
		witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
		villagerHead.addChild(witchHat);
		final ModelRenderer var2 = new ModelRenderer(this).setTextureSize(64, 128);
		var2.setRotationPoint(1.75F, -4.0F, 2.0F);
		var2.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
		var2.rotateAngleX = -0.05235988F;
		var2.rotateAngleZ = 0.02617994F;
		witchHat.addChild(var2);
		final ModelRenderer var3 = new ModelRenderer(this).setTextureSize(64, 128);
		var3.setRotationPoint(1.75F, -4.0F, 2.0F);
		var3.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
		var3.rotateAngleX = -0.10471976F;
		var3.rotateAngleZ = 0.05235988F;
		var2.addChild(var3);
		final ModelRenderer var4 = new ModelRenderer(this).setTextureSize(64, 128);
		var4.setRotationPoint(1.75F, -2.0F, 2.0F);
		var4.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
		var4.rotateAngleX = -0.20943952F;
		var4.rotateAngleZ = 0.10471976F;
		var3.addChild(var4);
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
		villagerNose.offsetX = villagerNose.offsetY = villagerNose.offsetZ = 0.0F;
		final float var8 = 0.01F * (p_78087_7_.getEntityId() % 10);
		villagerNose.rotateAngleX = MathHelper.sin(p_78087_7_.ticksExisted * var8) * 4.5F * (float) Math.PI / 180.0F;
		villagerNose.rotateAngleY = 0.0F;
		villagerNose.rotateAngleZ = MathHelper.cos(p_78087_7_.ticksExisted * var8) * 2.5F * (float) Math.PI / 180.0F;

		if (field_82900_g) {
			villagerNose.rotateAngleX = -0.9F;
			villagerNose.offsetZ = -0.09375F;
			villagerNose.offsetY = 0.1875F;
		}
	}
}
