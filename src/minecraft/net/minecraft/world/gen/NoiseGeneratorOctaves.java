package net.minecraft.world.gen;

import net.minecraft.util.MathHelper;

import java.util.Random;

public class NoiseGeneratorOctaves extends NoiseGenerator {

public static final int EaZy = 1804;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Collection of noise generation functions. Output is combined to produce
	 * different octaves of noise.
	 */
	private final NoiseGeneratorImproved[] generatorCollection;
	private final int octaves;
	// private static final String __OBFID = "http://https://fuckuskid00000535";

	public NoiseGeneratorOctaves(final Random p_i2111_1_, final int p_i2111_2_) {
		octaves = p_i2111_2_;
		generatorCollection = new NoiseGeneratorImproved[p_i2111_2_];

		for (int var3 = 0; var3 < p_i2111_2_; ++var3) {
			generatorCollection[var3] = new NoiseGeneratorImproved(p_i2111_1_);
		}
	}

	/**
	 * pars:(par2,3,4=noiseOffset ; so that adjacent noise segments connect)
	 * (pars5,6,7=x,y,zArraySize),(pars8,10,12 = x,y,z noiseScale)
	 */
	public double[] generateNoiseOctaves(double[] p_76304_1_, final int p_76304_2_, final int p_76304_3_,
			final int p_76304_4_, final int p_76304_5_, final int p_76304_6_, final int p_76304_7_,
			final double p_76304_8_, final double p_76304_10_, final double p_76304_12_) {
		if (p_76304_1_ == null) {
			p_76304_1_ = new double[p_76304_5_ * p_76304_6_ * p_76304_7_];
		} else {
			for (int var14 = 0; var14 < p_76304_1_.length; ++var14) {
				p_76304_1_[var14] = 0.0D;
			}
		}

		double var27 = 1.0D;

		for (int var16 = 0; var16 < octaves; ++var16) {
			double var17 = p_76304_2_ * var27 * p_76304_8_;
			final double var19 = p_76304_3_ * var27 * p_76304_10_;
			double var21 = p_76304_4_ * var27 * p_76304_12_;
			long var23 = MathHelper.floor_double_long(var17);
			long var25 = MathHelper.floor_double_long(var21);
			var17 -= var23;
			var21 -= var25;
			var23 %= 16777216L;
			var25 %= 16777216L;
			var17 += var23;
			var21 += var25;
			generatorCollection[var16].populateNoiseArray(p_76304_1_, var17, var19, var21, p_76304_5_, p_76304_6_,
					p_76304_7_, p_76304_8_ * var27, p_76304_10_ * var27, p_76304_12_ * var27, var27);
			var27 /= 2.0D;
		}

		return p_76304_1_;
	}

	/**
	 * Bouncer function to the main one with some default arguments.
	 */
	public double[] generateNoiseOctaves(final double[] p_76305_1_, final int p_76305_2_, final int p_76305_3_,
			final int p_76305_4_, final int p_76305_5_, final double p_76305_6_, final double p_76305_8_,
			final double p_76305_10_) {
		return this.generateNoiseOctaves(p_76305_1_, p_76305_2_, 10, p_76305_3_, p_76305_4_, 1, p_76305_5_, p_76305_6_,
				1.0D, p_76305_8_);
	}
}
