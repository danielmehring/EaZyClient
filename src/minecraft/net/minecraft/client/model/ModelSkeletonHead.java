package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSkeletonHead extends ModelBase {

public static final int EaZy = 605;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public ModelRenderer skeletonHead;
	// private static final String __OBFID = "http://https://fuckuskid00000856";

	public ModelSkeletonHead() {
		this(0, 35, 64, 64);
	}

	public ModelSkeletonHead(final int p_i1155_1_, final int p_i1155_2_, final int p_i1155_3_, final int p_i1155_4_) {
		textureWidth = p_i1155_3_;
		textureHeight = p_i1155_4_;
		skeletonHead = new ModelRenderer(this, p_i1155_1_, p_i1155_2_);
		skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		skeletonHead.render(p_78088_7_);
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
		skeletonHead.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
		skeletonHead.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
	}
}
