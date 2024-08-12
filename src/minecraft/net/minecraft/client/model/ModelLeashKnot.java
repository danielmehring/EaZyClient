package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelLeashKnot extends ModelBase {

public static final int EaZy = 591;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public ModelRenderer field_110723_a;
	// private static final String __OBFID = "http://https://fuckuskid00000843";

	public ModelLeashKnot() {
		this(0, 0, 32, 32);
	}

	public ModelLeashKnot(final int p_i46365_1_, final int p_i46365_2_, final int p_i46365_3_, final int p_i46365_4_) {
		textureWidth = p_i46365_3_;
		textureHeight = p_i46365_4_;
		field_110723_a = new ModelRenderer(this, p_i46365_1_, p_i46365_2_);
		field_110723_a.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
		field_110723_a.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		field_110723_a.render(p_78088_7_);
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
		field_110723_a.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
		field_110723_a.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
	}
}
