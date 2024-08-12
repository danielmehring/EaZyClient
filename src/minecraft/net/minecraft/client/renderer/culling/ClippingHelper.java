package net.minecraft.client.renderer.culling;

public class ClippingHelper {

public static final int EaZy = 708;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float[][] frustum = new float[6][4];
	public float[] field_178625_b = new float[16];
	public float[] field_178626_c = new float[16];
	public float[] clippingMatrix = new float[16];
	// private static final String __OBFID = "http://https://fuckuskid00000977";

	private float dot(final float[] p_178624_1_, final float p_178624_2_, final float p_178624_4_,
			final float p_178624_6_) {
		return p_178624_1_[0] * p_178624_2_ + p_178624_1_[1] * p_178624_4_ + p_178624_1_[2] * p_178624_6_
				+ p_178624_1_[3];
	}

	/**
	 * Returns true if the box is inside all 6 clipping planes, otherwise
	 * returns false.
	 */
	public boolean isBoxInFrustum(final double p_78553_1_, final double p_78553_3_, final double p_78553_5_,
			final double p_78553_7_, final double p_78553_9_, final double p_78553_11_) {
		final float minXf = (float) p_78553_1_;
		final float minYf = (float) p_78553_3_;
		final float minZf = (float) p_78553_5_;
		final float maxXf = (float) p_78553_7_;
		final float maxYf = (float) p_78553_9_;
		final float maxZf = (float) p_78553_11_;

		for (int var13 = 0; var13 < 6; ++var13) {
			final float[] var14 = frustum[var13];

			if (dot(var14, minXf, minYf, minZf) <= 0.0F && dot(var14, maxXf, minYf, minZf) <= 0.0F
					&& dot(var14, minXf, maxYf, minZf) <= 0.0F && dot(var14, maxXf, maxYf, minZf) <= 0.0F
					&& dot(var14, minXf, minYf, maxZf) <= 0.0F && dot(var14, maxXf, minYf, maxZf) <= 0.0F
					&& dot(var14, minXf, maxYf, maxZf) <= 0.0F && dot(var14, maxXf, maxYf, maxZf) <= 0.0F) {
				return false;
			}
		}

		return true;
	}
}
