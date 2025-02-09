package net.minecraft.util;

public class Vec3 {

public static final int EaZy = 1660;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** X coordinate of Vec3D */
	public final double xCoord;

	/** Y coordinate of Vec3D */
	public final double yCoord;

	/** Z coordinate of Vec3D */
	public final double zCoord;
	// private static final String __OBFID = "http://https://fuckuskid00000612";

	public Vec3(double x, double y, double z) {
		if (x == -0.0D) {
			x = 0.0D;
		}

		if (y == -0.0D) {
			y = 0.0D;
		}

		if (z == -0.0D) {
			z = 0.0D;
		}

		xCoord = x;
		yCoord = y;
		zCoord = z;
	}

	/**
	 * Returns a new vector with the result of the specified vector minus this.
	 */
	public Vec3 subtractReverse(final Vec3 vec) {
		return new Vec3(vec.xCoord - xCoord, vec.yCoord - yCoord, vec.zCoord - zCoord);
	}

	/**
	 * Normalizes the vector to a length of 1 (except if it is the zero vector)
	 */
	public Vec3 normalize() {
		final double var1 = MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
		return var1 < 1.0E-4D ? new Vec3(0.0D, 0.0D, 0.0D) : new Vec3(xCoord / var1, yCoord / var1, zCoord / var1);
	}

	public double dotProduct(final Vec3 vec) {
		return xCoord * vec.xCoord + yCoord * vec.yCoord + zCoord * vec.zCoord;
	}

	/**
	 * Returns a new vector with the result of this vector x the specified
	 * vector.
	 */
	public Vec3 crossProduct(final Vec3 vec) {
		return new Vec3(yCoord * vec.zCoord - zCoord * vec.yCoord, zCoord * vec.xCoord - xCoord * vec.zCoord,
				xCoord * vec.yCoord - yCoord * vec.xCoord);
	}

	public Vec3 subtract(final Vec3 p_178788_1_) {
		return this.subtract(p_178788_1_.xCoord, p_178788_1_.yCoord, p_178788_1_.zCoord);
	}

	public Vec3 subtract(final double p_178786_1_, final double p_178786_3_, final double p_178786_5_) {
		return addVector(-p_178786_1_, -p_178786_3_, -p_178786_5_);
	}

	public Vec3 add(final Vec3 p_178787_1_) {
		return addVector(p_178787_1_.xCoord, p_178787_1_.yCoord, p_178787_1_.zCoord);
	}

	/**
	 * Adds the specified x,y,z vector components to this vector and returns the
	 * resulting vector. Does not change this vector.
	 */
	public Vec3 addVector(final double x, final double y, final double z) {
		return new Vec3(xCoord + x, yCoord + y, zCoord + z);
	}

	/**
	 * Euclidean distance between this and the specified vector, returned as
	 * double.
	 */
	public double distanceTo(final Vec3 vec) {
		final double var2 = vec.xCoord - xCoord;
		final double var4 = vec.yCoord - yCoord;
		final double var6 = vec.zCoord - zCoord;
		return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
	}

	/**
	 * The square of the Euclidean distance between this and the specified
	 * vector.
	 */
	public double squareDistanceTo(final Vec3 vec) {
		final double var2 = vec.xCoord - xCoord;
		final double var4 = vec.yCoord - yCoord;
		final double var6 = vec.zCoord - zCoord;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	/**
	 * Returns the length of the vector.
	 */
	public double lengthVector() {
		return MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
	}

	/**
	 * Returns a new vector with x value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vec3 getIntermediateWithXValue(final Vec3 vec, final double x) {
		final double var4 = vec.xCoord - xCoord;
		final double var6 = vec.yCoord - yCoord;
		final double var8 = vec.zCoord - zCoord;

		if (var4 * var4 < 1.0000000116860974E-7D) {
			return null;
		} else {
			final double var10 = (x - xCoord) / var4;
			return var10 >= 0.0D && var10 <= 1.0D
					? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}

	/**
	 * Returns a new vector with y value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vec3 getIntermediateWithYValue(final Vec3 vec, final double y) {
		final double var4 = vec.xCoord - xCoord;
		final double var6 = vec.yCoord - yCoord;
		final double var8 = vec.zCoord - zCoord;

		if (var6 * var6 < 1.0000000116860974E-7D) {
			return null;
		} else {
			final double var10 = (y - yCoord) / var6;
			return var10 >= 0.0D && var10 <= 1.0D
					? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}

	/**
	 * Returns a new vector with z value equal to the second parameter, along
	 * the line between this vector and the passed in vector, or null if not
	 * possible.
	 */
	public Vec3 getIntermediateWithZValue(final Vec3 vec, final double z) {
		final double var4 = vec.xCoord - xCoord;
		final double var6 = vec.yCoord - yCoord;
		final double var8 = vec.zCoord - zCoord;

		if (var8 * var8 < 1.0000000116860974E-7D) {
			return null;
		} else {
			final double var10 = (z - zCoord) / var8;
			return var10 >= 0.0D && var10 <= 1.0D
					? new Vec3(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}

	@Override
	public String toString() {
		return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
	}

	public Vec3 rotatePitch(final float p_178789_1_) {
		final float var2 = MathHelper.cos(p_178789_1_);
		final float var3 = MathHelper.sin(p_178789_1_);
		final double var4 = xCoord;
		final double var6 = yCoord * var2 + zCoord * var3;
		final double var8 = zCoord * var2 - yCoord * var3;
		return new Vec3(var4, var6, var8);
	}

	public Vec3 rotateYaw(final float p_178785_1_) {
		final float var2 = MathHelper.cos(p_178785_1_);
		final float var3 = MathHelper.sin(p_178785_1_);
		final double var4 = xCoord * var2 + zCoord * var3;
		final double var6 = yCoord;
		final double var8 = zCoord * var2 - xCoord * var3;
		return new Vec3(var4, var6, var8);
	}
}
