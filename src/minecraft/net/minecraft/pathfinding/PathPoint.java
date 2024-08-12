package net.minecraft.pathfinding;

import net.minecraft.util.MathHelper;

public class PathPoint {

public static final int EaZy = 1489;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The x coordinate of this point */
	public final int xCoord;

	/** The y coordinate of this point */
	public final int yCoord;

	/** The z coordinate of this point */
	public final int zCoord;

	/** A hash of the coordinates used to identify this point */
	private final int hash;

	/** The index of this point in its assigned path */
	int index = -1;

	/** The distance along the path to this point */
	float totalPathDistance;

	/** The linear distance to the next point */
	float distanceToNext;

	/** The distance to the target */
	float distanceToTarget;

	/** The point preceding this in its assigned path */
	PathPoint previous;

	/** True if the pathfinder has already visited this point */
	public boolean visited;
	// private static final String __OBFID = "http://https://fuckuskid00000574";

	public PathPoint(final int p_i2135_1_, final int p_i2135_2_, final int p_i2135_3_) {
		xCoord = p_i2135_1_;
		yCoord = p_i2135_2_;
		zCoord = p_i2135_3_;
		hash = makeHash(p_i2135_1_, p_i2135_2_, p_i2135_3_);
	}

	public static int makeHash(final int p_75830_0_, final int p_75830_1_, final int p_75830_2_) {
		return p_75830_1_ & 255 | (p_75830_0_ & 32767) << 8 | (p_75830_2_ & 32767) << 24
				| (p_75830_0_ < 0 ? Integer.MIN_VALUE : 0) | (p_75830_2_ < 0 ? 32768 : 0);
	}

	/**
	 * Returns the linear distance to another path point
	 */
	public float distanceTo(final PathPoint p_75829_1_) {
		final float var2 = p_75829_1_.xCoord - xCoord;
		final float var3 = p_75829_1_.yCoord - yCoord;
		final float var4 = p_75829_1_.zCoord - zCoord;
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	/**
	 * Returns the squared distance to another path point
	 */
	public float distanceToSquared(final PathPoint p_75832_1_) {
		final float var2 = p_75832_1_.xCoord - xCoord;
		final float var3 = p_75832_1_.yCoord - yCoord;
		final float var4 = p_75832_1_.zCoord - zCoord;
		return var2 * var2 + var3 * var3 + var4 * var4;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof PathPoint)) {
			return false;
		} else {
			final PathPoint var2 = (PathPoint) p_equals_1_;
			return hash == var2.hash && xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord;
		}
	}

	@Override
	public int hashCode() {
		return hash;
	}

	/**
	 * Returns true if this point has already been assigned to a path
	 */
	public boolean isAssigned() {
		return index >= 0;
	}

	@Override
	public String toString() {
		return xCoord + ", " + yCoord + ", " + zCoord;
	}
}
