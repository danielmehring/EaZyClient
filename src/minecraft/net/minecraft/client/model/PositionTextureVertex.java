package net.minecraft.client.model;

import net.minecraft.util.Vec3;

public class PositionTextureVertex {

public static final int EaZy = 616;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public Vec3 vector3D;
	public float texturePositionX;
	public float texturePositionY;
	// private static final String __OBFID = "http://https://fuckuskid00000862";

	public PositionTextureVertex(final float p_i1158_1_, final float p_i1158_2_, final float p_i1158_3_,
			final float p_i1158_4_, final float p_i1158_5_) {
		this(new Vec3(p_i1158_1_, p_i1158_2_, p_i1158_3_), p_i1158_4_, p_i1158_5_);
	}

	public PositionTextureVertex setTexturePosition(final float p_78240_1_, final float p_78240_2_) {
		return new PositionTextureVertex(this, p_78240_1_, p_78240_2_);
	}

	public PositionTextureVertex(final PositionTextureVertex p_i46363_1_, final float p_i46363_2_,
			final float p_i46363_3_) {
		vector3D = p_i46363_1_.vector3D;
		texturePositionX = p_i46363_2_;
		texturePositionY = p_i46363_3_;
	}

	public PositionTextureVertex(final Vec3 p_i1160_1_, final float p_i1160_2_, final float p_i1160_3_) {
		vector3D = p_i1160_1_;
		texturePositionX = p_i1160_2_;
		texturePositionY = p_i1160_3_;
	}
}
