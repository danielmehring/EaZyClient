package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class ModelBase {

public static final int EaZy = 570;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float swingProgress;
	public boolean isRiding;
	public boolean isChild = true;

	/**
	 * This is a list of all the boxes (ModelRenderer.class) in the current
	 * model.
	 */
	public List boxList = Lists.newArrayList();

	/** A mapping for all texture offsets */
	private final Map modelTextureMap = Maps.newHashMap();
	public int textureWidth = 64;
	public int textureHeight = 32;

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_,
			final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the
	 * setRotationAngles method.
	 */
	public void setLivingAnimations(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_,
			final float p_78086_4_) {}

	public ModelRenderer getRandomModelBox(final Random p_85181_1_) {
		return (ModelRenderer) boxList.get(p_85181_1_.nextInt(boxList.size()));
	}

	protected void setTextureOffset(final String p_78085_1_, final int p_78085_2_, final int p_78085_3_) {
		modelTextureMap.put(p_78085_1_, new TextureOffset(p_78085_2_, p_78085_3_));
	}

	public TextureOffset getTextureOffset(final String p_78084_1_) {
		return (TextureOffset) modelTextureMap.get(p_78084_1_);
	}

	public static void func_178685_a(final ModelRenderer p_178685_0_, final ModelRenderer p_178685_1_) {
		p_178685_1_.rotateAngleX = p_178685_0_.rotateAngleX;
		p_178685_1_.rotateAngleY = p_178685_0_.rotateAngleY;
		p_178685_1_.rotateAngleZ = p_178685_0_.rotateAngleZ;
		p_178685_1_.rotationPointX = p_178685_0_.rotationPointX;
		p_178685_1_.rotationPointY = p_178685_0_.rotationPointY;
		p_178685_1_.rotationPointZ = p_178685_0_.rotationPointZ;
	}

	public void setModelAttributes(final ModelBase p_178686_1_) {
		swingProgress = p_178686_1_.swingProgress;
		isRiding = p_178686_1_.isRiding;
		isChild = p_178686_1_.isChild;
	}
}
