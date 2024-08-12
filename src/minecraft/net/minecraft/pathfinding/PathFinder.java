package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class PathFinder {

public static final int EaZy = 1484;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The path being generated */
	private final Path path = new Path();

	/** Selection of path points to add to the path */
	private final PathPoint[] pathOptions = new PathPoint[32];
	private final NodeProcessor field_176190_c;
	// private static final String __OBFID = "http://https://fuckuskid00000576";

	public PathFinder(final NodeProcessor p_i45557_1_) {
		field_176190_c = p_i45557_1_;
	}

	public PathEntity func_176188_a(final IBlockAccess p_176188_1_, final Entity p_176188_2_, final Entity p_176188_3_,
			final float p_176188_4_) {
		return func_176189_a(p_176188_1_, p_176188_2_, p_176188_3_.posX, p_176188_3_.getEntityBoundingBox().minY,
				p_176188_3_.posZ, p_176188_4_);
	}

	public PathEntity func_180782_a(final IBlockAccess p_180782_1_, final Entity p_180782_2_,
			final BlockPos p_180782_3_, final float p_180782_4_) {
		return func_176189_a(p_180782_1_, p_180782_2_, p_180782_3_.getX() + 0.5F, p_180782_3_.getY() + 0.5F,
				p_180782_3_.getZ() + 0.5F, p_180782_4_);
	}

	private PathEntity func_176189_a(final IBlockAccess p_176189_1_, final Entity p_176189_2_, final double p_176189_3_,
			final double p_176189_5_, final double p_176189_7_, final float p_176189_9_) {
		path.clearPath();
		field_176190_c.func_176162_a(p_176189_1_, p_176189_2_);
		final PathPoint var10 = field_176190_c.func_176161_a(p_176189_2_);
		final PathPoint var11 = field_176190_c.func_176160_a(p_176189_2_, p_176189_3_, p_176189_5_, p_176189_7_);
		final PathEntity var12 = func_176187_a(p_176189_2_, var10, var11, p_176189_9_);
		field_176190_c.func_176163_a();
		return var12;
	}

	private PathEntity func_176187_a(final Entity p_176187_1_, final PathPoint p_176187_2_, final PathPoint p_176187_3_,
			final float p_176187_4_) {
		p_176187_2_.totalPathDistance = 0.0F;
		p_176187_2_.distanceToNext = p_176187_2_.distanceToSquared(p_176187_3_);
		p_176187_2_.distanceToTarget = p_176187_2_.distanceToNext;
		path.clearPath();
		path.addPoint(p_176187_2_);
		PathPoint var5 = p_176187_2_;

		while (!path.isPathEmpty()) {
			final PathPoint var6 = path.dequeue();

			if (var6.equals(p_176187_3_)) {
				return createEntityPath(p_176187_2_, p_176187_3_);
			}

			if (var6.distanceToSquared(p_176187_3_) < var5.distanceToSquared(p_176187_3_)) {
				var5 = var6;
			}

			var6.visited = true;
			final int var7 = field_176190_c.func_176164_a(pathOptions, p_176187_1_, var6, p_176187_3_, p_176187_4_);

			for (int var8 = 0; var8 < var7; ++var8) {
				final PathPoint var9 = pathOptions[var8];
				final float var10 = var6.totalPathDistance + var6.distanceToSquared(var9);

				if (var10 < p_176187_4_ * 2.0F && (!var9.isAssigned() || var10 < var9.totalPathDistance)) {
					var9.previous = var6;
					var9.totalPathDistance = var10;
					var9.distanceToNext = var9.distanceToSquared(p_176187_3_);

					if (var9.isAssigned()) {
						path.changeDistance(var9, var9.totalPathDistance + var9.distanceToNext);
					} else {
						var9.distanceToTarget = var9.totalPathDistance + var9.distanceToNext;
						path.addPoint(var9);
					}
				}
			}
		}

		if (var5 == p_176187_2_) {
			return null;
		} else {
			return createEntityPath(p_176187_2_, var5);
		}
	}

	/**
	 * Returns a new PathEntity for a given start and end point
	 */
	private PathEntity createEntityPath(final PathPoint p_75853_1_, final PathPoint p_75853_2_) {
		int var3 = 1;
		PathPoint var4;

		for (var4 = p_75853_2_; var4.previous != null; var4 = var4.previous) {
			++var3;
		}

		final PathPoint[] var5 = new PathPoint[var3];
		var4 = p_75853_2_;
		--var3;

		for (var5[var3] = p_75853_2_; var4.previous != null; var5[var3] = var4) {
			var4 = var4.previous;
			--var3;
		}

		return new PathEntity(var5);
	}
}
