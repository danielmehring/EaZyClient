package net.minecraft.util;

import java.util.Random;
import java.util.UUID;

public class MathHelper {

public static final int EaZy = 1631;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final float field_180189_a = sqrt_float(2.0F);
	public static final float PI = (float) Math.PI;
	public static final float PI2 = (float) Math.PI * 2F;
	public static final float PId2 = (float) Math.PI / 2F;
	public static final float deg2Rad = 0.017453292F;
	private static final float[] SIN_TABLE_FAST = new float[4096];
	public static boolean fastMath = false;

	/**
	 * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive),
	 * with steps of 2*PI / 65536.
	 */
	private static final float[] SIN_TABLE = new float[65536];

	/**
	 * Though it looks like an array, this is really more like a mapping. Key
	 * (index of this array) is the upper 5 bits of the result of multiplying a
	 * 32-bit unsigned integer by the B(2, 5) De Bruijn sequence 0x077CB531.
	 * Value (value stored in the array) is the unique index (from the right) of
	 * the leftmost one-bit in a 32-bit unsigned integer that can cause the
	 * upper 5 bits to get that value. Used for highly optimized "find the
	 * log-base-2 of this number" calculations.
	 */
	private static final int[] multiplyDeBruijnBitPosition;
	// private static final String __OBFID = "http://https://fuckuskid00001496";

	/**
	 * sin looked up in a table
	 */
	public static float sin(final float p_76126_0_) {
		return fastMath ? SIN_TABLE_FAST[(int) (p_76126_0_ * 651.8986F) & 4095]
				: SIN_TABLE[(int) (p_76126_0_ * 10430.378F) & 65535];
	}

	/**
	 * cos looked up in the sin table with the appropriate offset
	 */
	public static float cos(final float p_76134_0_) {
		return fastMath ? SIN_TABLE_FAST[(int) ((p_76134_0_ + (float) Math.PI / 2F) * 651.8986F) & 4095]
				: SIN_TABLE[(int) (p_76134_0_ * 10430.378F + 16384.0F) & 65535];
	}

	public static float sqrt_float(final float p_76129_0_) {
		return (float) Math.sqrt(p_76129_0_);
	}

	public static float sqrt_double(final double p_76133_0_) {
		return (float) Math.sqrt(p_76133_0_);
	}

	/**
	 * Returns the greatest integer less than or equal to the float argument
	 */
	public static int floor_float(final float p_76141_0_) {
		final int var1 = (int) p_76141_0_;
		return p_76141_0_ < var1 ? var1 - 1 : var1;
	}

	/**
	 * returns par0 cast as an int, and no greater than Integer.MAX_VALUE-1024
	 */
	public static int truncateDoubleToInt(final double p_76140_0_) {
		return (int) (p_76140_0_ + 1024.0D) - 1024;
	}

	/**
	 * Returns the greatest integer less than or equal to the double argument
	 */
	public static int floor_double(final double p_76128_0_) {
		final int var2 = (int) p_76128_0_;
		return p_76128_0_ < var2 ? var2 - 1 : var2;
	}

	/**
	 * Long version of floor_double
	 */
	public static long floor_double_long(final double p_76124_0_) {
		final long var2 = (long) p_76124_0_;
		return p_76124_0_ < var2 ? var2 - 1L : var2;
	}

	public static int func_154353_e(final double p_154353_0_) {
		return (int) (p_154353_0_ >= 0.0D ? p_154353_0_ : -p_154353_0_ + 1.0D);
	}

	public static float abs(final float p_76135_0_) {
		return p_76135_0_ >= 0.0F ? p_76135_0_ : -p_76135_0_;
	}

	/**
	 * Returns the unsigned value of an int.
	 */
	public static int abs_int(final int p_76130_0_) {
		return p_76130_0_ >= 0 ? p_76130_0_ : -p_76130_0_;
	}

	public static int ceiling_float_int(final float p_76123_0_) {
		final int var1 = (int) p_76123_0_;
		return p_76123_0_ > var1 ? var1 + 1 : var1;
	}

	public static int ceiling_double_int(final double p_76143_0_) {
		final int var2 = (int) p_76143_0_;
		return p_76143_0_ > var2 ? var2 + 1 : var2;
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower
	 * and upper limits given by the second and third parameters.
	 */
	public static int clamp_int(final int p_76125_0_, final int p_76125_1_, final int p_76125_2_) {
		return p_76125_0_ < p_76125_1_ ? p_76125_1_ : p_76125_0_ > p_76125_2_ ? p_76125_2_ : p_76125_0_;
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower
	 * and upper limits given by the second and third parameters
	 */
	public static float clamp_float(final float p_76131_0_, final float p_76131_1_, final float p_76131_2_) {
		return p_76131_0_ < p_76131_1_ ? p_76131_1_ : p_76131_0_ > p_76131_2_ ? p_76131_2_ : p_76131_0_;
	}

	public static double clamp_double(final double p_151237_0_, final double p_151237_2_, final double p_151237_4_) {
		return p_151237_0_ < p_151237_2_ ? p_151237_2_ : p_151237_0_ > p_151237_4_ ? p_151237_4_ : p_151237_0_;
	}

	public static double denormalizeClamp(final double p_151238_0_, final double p_151238_2_,
			final double p_151238_4_) {
		return p_151238_4_ < 0.0D ? p_151238_0_
				: p_151238_4_ > 1.0D ? p_151238_2_ : p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_;
	}

	/**
	 * Maximum of the absolute value of two numbers.
	 */
	public static double abs_max(double p_76132_0_, double p_76132_2_) {
		if (p_76132_0_ < 0.0D) {
			p_76132_0_ = -p_76132_0_;
		}

		if (p_76132_2_ < 0.0D) {
			p_76132_2_ = -p_76132_2_;
		}

		return p_76132_0_ > p_76132_2_ ? p_76132_0_ : p_76132_2_;
	}

	/**
	 * Buckets an integer with specifed bucket sizes. Args: i, bucketSize
	 */
	public static int bucketInt(final int p_76137_0_, final int p_76137_1_) {
		return p_76137_0_ < 0 ? -((-p_76137_0_ - 1) / p_76137_1_) - 1 : p_76137_0_ / p_76137_1_;
	}

	public static int getRandomIntegerInRange(final Random p_76136_0_, final int p_76136_1_, final int p_76136_2_) {
		return p_76136_1_ >= p_76136_2_ ? p_76136_1_ : p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_;
	}

	public static float randomFloatClamp(final Random p_151240_0_, final float p_151240_1_, final float p_151240_2_) {
		return p_151240_1_ >= p_151240_2_ ? p_151240_1_
				: p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_;
	}

	public static double getRandomDoubleInRange(final Random p_82716_0_, final double p_82716_1_,
			final double p_82716_3_) {
		return p_82716_1_ >= p_82716_3_ ? p_82716_1_ : p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_;
	}

	public static double average(final long[] p_76127_0_) {
		long var1 = 0L;
		final long[] var3 = p_76127_0_;
		final int var4 = p_76127_0_.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final long var6 = var3[var5];
			var1 += var6;
		}

		return (double) var1 / (double) p_76127_0_.length;
	}

	public static boolean func_180185_a(final float p_180185_0_, final float p_180185_1_) {
		return abs(p_180185_1_ - p_180185_0_) < 1.0E-5F;
	}

	public static int func_180184_b(final int p_180184_0_, final int p_180184_1_) {
		return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
	}

	/**
	 * the angle is reduced to an angle between -180 and +180 by mod, and a 360
	 * check
	 */
	public static float wrapAngleTo180_float(float p_76142_0_) {
		p_76142_0_ %= 360.0F;

		if (p_76142_0_ >= 180.0F) {
			p_76142_0_ -= 360.0F;
		}

		if (p_76142_0_ < -180.0F) {
			p_76142_0_ += 360.0F;
		}

		return p_76142_0_;
	}

	/**
	 * the angle is reduced to an angle between -180 and +180 by mod, and a 360
	 * check
	 */
	public static double wrapAngleTo180_double(double p_76138_0_) {
		p_76138_0_ %= 360.0D;

		if (p_76138_0_ >= 180.0D) {
			p_76138_0_ -= 360.0D;
		}

		if (p_76138_0_ < -180.0D) {
			p_76138_0_ += 360.0D;
		}

		return p_76138_0_;
	}

	/**
	 * parses the string as integer or returns the second parameter if it fails
	 */
	public static int parseIntWithDefault(final String p_82715_0_, final int p_82715_1_) {
		try {
			return Integer.parseInt(p_82715_0_);
		} catch (final Throwable var3) {
			return p_82715_1_;
		}
	}

	/**
	 * parses the string as integer or returns the second parameter if it fails.
	 * this value is capped to par2
	 */
	public static int parseIntWithDefaultAndMax(final String p_82714_0_, final int p_82714_1_, final int p_82714_2_) {
		return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_));
	}

	/**
	 * parses the string as double or returns the second parameter if it fails.
	 */
	public static double parseDoubleWithDefault(final String p_82712_0_, final double p_82712_1_) {
		try {
			return Double.parseDouble(p_82712_0_);
		} catch (final Throwable var4) {
			return p_82712_1_;
		}
	}

	public static double parseDoubleWithDefaultAndMax(final String p_82713_0_, final double p_82713_1_,
			final double p_82713_3_) {
		return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
	}

	/**
	 * Returns the input value rounded up to the next highest power of two.
	 */
	public static int roundUpToPowerOfTwo(final int p_151236_0_) {
		int var1 = p_151236_0_ - 1;
		var1 |= var1 >> 1;
		var1 |= var1 >> 2;
		var1 |= var1 >> 4;
		var1 |= var1 >> 8;
		var1 |= var1 >> 16;
		return var1 + 1;
	}

	/**
	 * Is the given value a power of two? (1, 2, 4, 8, 16, ...)
	 */
	private static boolean isPowerOfTwo(final int p_151235_0_) {
		return p_151235_0_ != 0 && (p_151235_0_ & p_151235_0_ - 1) == 0;
	}

	/**
	 * Uses a B(2, 5) De Bruijn sequence and a lookup table to efficiently
	 * calculate the log-base-two of the given value. Optimized for cases where
	 * the input value is a power-of-two. If the input value is not a
	 * power-of-two, then subtract 1 from the return value.
	 */
	private static int calculateLogBaseTwoDeBruijn(int p_151241_0_) {
		p_151241_0_ = isPowerOfTwo(p_151241_0_) ? p_151241_0_ : roundUpToPowerOfTwo(p_151241_0_);
		return multiplyDeBruijnBitPosition[(int) (p_151241_0_ * 125613361L >> 27) & 31];
	}

	/**
	 * Efficiently calculates the floor of the base-2 log of an integer value.
	 * This is effectively the index of the highest bit that is set. For
	 * example, if the number in binary is 0...100101, this will return 5.
	 */
	public static int calculateLogBaseTwo(final int p_151239_0_) {
		return calculateLogBaseTwoDeBruijn(p_151239_0_) - (isPowerOfTwo(p_151239_0_) ? 0 : 1);
	}

	public static int func_154354_b(final int p_154354_0_, int p_154354_1_) {
		if (p_154354_1_ == 0) {
			return 0;
		} else if (p_154354_0_ == 0) {
			return p_154354_1_;
		} else {
			if (p_154354_0_ < 0) {
				p_154354_1_ *= -1;
			}

			final int var2 = p_154354_0_ % p_154354_1_;
			return var2 == 0 ? p_154354_0_ : p_154354_0_ + p_154354_1_ - var2;
		}
	}

	public static int func_180183_b(final float p_180183_0_, final float p_180183_1_, final float p_180183_2_) {
		return func_180181_b(floor_float(p_180183_0_ * 255.0F), floor_float(p_180183_1_ * 255.0F),
				floor_float(p_180183_2_ * 255.0F));
	}

	public static int func_180181_b(final int p_180181_0_, final int p_180181_1_, final int p_180181_2_) {
		int var3 = (p_180181_0_ << 8) + p_180181_1_;
		var3 = (var3 << 8) + p_180181_2_;
		return var3;
	}

	public static int func_180188_d(final int p_180188_0_, final int p_180188_1_) {
		final int var2 = (p_180188_0_ & 16711680) >> 16;
		final int var3 = (p_180188_1_ & 16711680) >> 16;
		final int var4 = (p_180188_0_ & 65280) >> 8;
		final int var5 = (p_180188_1_ & 65280) >> 8;
		final int var6 = (p_180188_0_ & 255) >> 0;
		final int var7 = (p_180188_1_ & 255) >> 0;
		final int var8 = (int) ((float) var2 * (float) var3 / 255.0F);
		final int var9 = (int) ((float) var4 * (float) var5 / 255.0F);
		final int var10 = (int) ((float) var6 * (float) var7 / 255.0F);
		return p_180188_0_ & -16777216 | var8 << 16 | var9 << 8 | var10;
	}

	public static long func_180186_a(final Vec3i pos) {
		return func_180187_c(pos.getX(), pos.getY(), pos.getZ());
	}

	public static long func_180187_c(final int x, final int y, final int z) {
		long var3 = x * 3129871 ^ z * 116129781L ^ y;
		var3 = var3 * var3 * 42317861L + var3 * 11L;
		return var3;
	}

	public static UUID func_180182_a(final Random p_180182_0_) {
		final long var1 = p_180182_0_.nextLong() & -61441L | 16384L;
		final long var3 = p_180182_0_.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
		return new UUID(var1, var3);
	}

	static {
		int i;

		for (i = 0; i < 65536; ++i) {
			SIN_TABLE[i] = (float) Math.sin(i * Math.PI * 2.0D / 65536.0D);
		}

		multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13,
				23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };

		for (i = 0; i < 4096; ++i) {
			SIN_TABLE_FAST[i] = (float) Math.sin((i + 0.5F) / 4096.0F * ((float) Math.PI * 2F));
		}

		for (i = 0; i < 360; i += 90) {
			SIN_TABLE_FAST[(int) (i * 11.377778F) & 4095] = (float) Math.sin(i * 0.017453292F);
		}
	}
}
