package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {

public static final int EaZy = 1805;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final NoiseGeneratorSimplex[] field_151603_a;
	private final int field_151602_b;
	// private static final String __OBFID = "http://https://fuckuskid00000536";

	public NoiseGeneratorPerlin(final Random p_i45470_1_, final int p_i45470_2_) {
		field_151602_b = p_i45470_2_;
		field_151603_a = new NoiseGeneratorSimplex[p_i45470_2_];

		for (int var3 = 0; var3 < p_i45470_2_; ++var3) {
			field_151603_a[var3] = new NoiseGeneratorSimplex(p_i45470_1_);
		}
	}

	public double func_151601_a(final double p_151601_1_, final double p_151601_3_) {
		double var5 = 0.0D;
		double var7 = 1.0D;

		for (int var9 = 0; var9 < field_151602_b; ++var9) {
			var5 += field_151603_a[var9].func_151605_a(p_151601_1_ * var7, p_151601_3_ * var7) / var7;
			var7 /= 2.0D;
		}

		return var5;
	}

	public double[] func_151599_a(final double[] p_151599_1_, final double p_151599_2_, final double p_151599_4_,
			final int p_151599_6_, final int p_151599_7_, final double p_151599_8_, final double p_151599_10_,
			final double p_151599_12_) {
		return func_151600_a(p_151599_1_, p_151599_2_, p_151599_4_, p_151599_6_, p_151599_7_, p_151599_8_, p_151599_10_,
				p_151599_12_, 0.5D);
	}

	public double[] func_151600_a(double[] p_151600_1_, final double p_151600_2_, final double p_151600_4_,
			final int p_151600_6_, final int p_151600_7_, final double p_151600_8_, final double p_151600_10_,
			final double p_151600_12_, final double p_151600_14_) {
		if (p_151600_1_ != null && p_151600_1_.length >= p_151600_6_ * p_151600_7_) {
			for (int var16 = 0; var16 < p_151600_1_.length; ++var16) {
				p_151600_1_[var16] = 0.0D;
			}
		} else {
			p_151600_1_ = new double[p_151600_6_ * p_151600_7_];
		}

		double var21 = 1.0D;
		double var18 = 1.0D;

		for (int var20 = 0; var20 < field_151602_b; ++var20) {
			field_151603_a[var20].func_151606_a(p_151600_1_, p_151600_2_, p_151600_4_, p_151600_6_, p_151600_7_,
					p_151600_8_ * var18 * var21, p_151600_10_ * var18 * var21, 0.55D / var21);
			var18 *= p_151600_12_;
			var21 *= p_151600_14_;
		}

		return p_151600_1_;
	}
}
