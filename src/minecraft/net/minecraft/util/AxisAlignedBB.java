package net.minecraft.util;

public class AxisAlignedBB {

public static final int EaZy = 1590;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public final double minX;
	public final double minY;
	public final double minZ;
	public final double maxX;
	public final double maxY;
	public final double maxZ;
	// private static final String __OBFID = "http://https://fuckuskid00000607";

	public AxisAlignedBB(final double x1, final double y1, final double z1, final double x2, final double y2,
			final double z2) {
		minX = Math.min(x1, x2);
		minY = Math.min(y1, y2);
		minZ = Math.min(z1, z2);
		maxX = Math.max(x1, x2);
		maxY = Math.max(y1, y2);
		maxZ = Math.max(z1, z2);
	}

	public AxisAlignedBB(final BlockPos p_i45554_1_, final BlockPos p_i45554_2_) {
		minX = p_i45554_1_.getX();
		minY = p_i45554_1_.getY();
		minZ = p_i45554_1_.getZ();
		maxX = p_i45554_2_.getX();
		maxY = p_i45554_2_.getY();
		maxZ = p_i45554_2_.getZ();
	}

	/**
	 * Adds the coordinates to the bounding box extending it if the point lies
	 * outside the current ranges. Args: x, y, z
	 */
	public AxisAlignedBB addCoord(final double x, final double y, final double z) {
		double var7 = minX;
		double var9 = minY;
		double var11 = minZ;
		double var13 = maxX;
		double var15 = maxY;
		double var17 = maxZ;

		if (x < 0.0D) {
			var7 += x;
		} else if (x > 0.0D) {
			var13 += x;
		}

		if (y < 0.0D) {
			var9 += y;
		} else if (y > 0.0D) {
			var15 += y;
		}

		if (z < 0.0D) {
			var11 += z;
		} else if (z > 0.0D) {
			var17 += z;
		}

		return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
	}

	/**
	 * Returns a bounding box expanded by the specified vector (if negative
	 * numbers are given it will shrink). Args: x, y, z
	 */
	public AxisAlignedBB expand(final double x, final double y, final double z) {
		final double var7 = minX - x;
		final double var9 = minY - y;
		final double var11 = minZ - z;
		final double var13 = maxX + x;
		final double var15 = maxY + y;
		final double var17 = maxZ + z;
		return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
	}

	public AxisAlignedBB union(final AxisAlignedBB other) {
		final double var2 = Math.min(minX, other.minX);
		final double var4 = Math.min(minY, other.minY);
		final double var6 = Math.min(minZ, other.minZ);
		final double var8 = Math.max(maxX, other.maxX);
		final double var10 = Math.max(maxY, other.maxY);
		final double var12 = Math.max(maxZ, other.maxZ);
		return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
	}

	/**
	 * returns an AABB with corners x1, y1, z1 and x2, y2, z2
	 */
	public static AxisAlignedBB fromBounds(final double p_178781_0_, final double p_178781_2_, final double p_178781_4_,
			final double p_178781_6_, final double p_178781_8_, final double p_178781_10_) {
		final double var12 = Math.min(p_178781_0_, p_178781_6_);
		final double var14 = Math.min(p_178781_2_, p_178781_8_);
		final double var16 = Math.min(p_178781_4_, p_178781_10_);
		final double var18 = Math.max(p_178781_0_, p_178781_6_);
		final double var20 = Math.max(p_178781_2_, p_178781_8_);
		final double var22 = Math.max(p_178781_4_, p_178781_10_);
		return new AxisAlignedBB(var12, var14, var16, var18, var20, var22);
	}

	/**
	 * Offsets the current bounding box by the specified coordinates. Args: x,
	 * y, z
	 */
	public AxisAlignedBB offset(final double x, final double y, final double z) {
		return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and Z
	 * dimensions, calculate the offset between them in the X dimension. return
	 * var2 if the bounding boxes do not overlap or if var2 is closer to 0 then
	 * the calculated offset. Otherwise return the calculated offset.
	 */
	public double calculateXOffset(final AxisAlignedBB other, double p_72316_2_) {
		if (other.maxY > minY && other.minY < maxY && other.maxZ > minZ && other.minZ < maxZ) {
			double var4;

			if (p_72316_2_ > 0.0D && other.maxX <= minX) {
				var4 = minX - other.maxX;

				if (var4 < p_72316_2_) {
					p_72316_2_ = var4;
				}
			} else if (p_72316_2_ < 0.0D && other.minX >= maxX) {
				var4 = maxX - other.minX;

				if (var4 > p_72316_2_) {
					p_72316_2_ = var4;
				}
			}

			return p_72316_2_;
		} else {
			return p_72316_2_;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the X and Z
	 * dimensions, calculate the offset between them in the Y dimension. return
	 * var2 if the bounding boxes do not overlap or if var2 is closer to 0 then
	 * the calculated offset. Otherwise return the calculated offset.
	 */
	public double calculateYOffset(final AxisAlignedBB other, double p_72323_2_) {
		if (other.maxX > minX && other.minX < maxX && other.maxZ > minZ && other.minZ < maxZ) {
			double var4;

			if (p_72323_2_ > 0.0D && other.maxY <= minY) {
				var4 = minY - other.maxY;

				if (var4 < p_72323_2_) {
					p_72323_2_ = var4;
				}
			} else if (p_72323_2_ < 0.0D && other.minY >= maxY) {
				var4 = maxY - other.minY;

				if (var4 > p_72323_2_) {
					p_72323_2_ = var4;
				}
			}

			return p_72323_2_;
		} else {
			return p_72323_2_;
		}
	}

	/**
	 * if instance and the argument bounding boxes overlap in the Y and X
	 * dimensions, calculate the offset between them in the Z dimension. return
	 * var2 if the bounding boxes do not overlap or if var2 is closer to 0 then
	 * the calculated offset. Otherwise return the calculated offset.
	 */
	public double calculateZOffset(final AxisAlignedBB other, double p_72322_2_) {
		if (other.maxX > minX && other.minX < maxX && other.maxY > minY && other.minY < maxY) {
			double var4;

			if (p_72322_2_ > 0.0D && other.maxZ <= minZ) {
				var4 = minZ - other.maxZ;

				if (var4 < p_72322_2_) {
					p_72322_2_ = var4;
				}
			} else if (p_72322_2_ < 0.0D && other.minZ >= maxZ) {
				var4 = maxZ - other.minZ;

				if (var4 > p_72322_2_) {
					p_72322_2_ = var4;
				}
			}

			return p_72322_2_;
		} else {
			return p_72322_2_;
		}
	}

	/**
	 * Returns whether the given bounding box intersects with this one. Args:
	 * axisAlignedBB
	 */
	public boolean intersectsWith(final AxisAlignedBB other) {
		return other.maxX > minX && other.minX < maxX
				? other.maxY > minY && other.minY < maxY ? other.maxZ > minZ && other.minZ < maxZ : false : false;
	}

	/**
	 * Returns if the supplied Vec3D is completely inside the bounding box
	 */
	public boolean isVecInside(final Vec3 vec) {
		return vec.xCoord > minX && vec.xCoord < maxX
				? vec.yCoord > minY && vec.yCoord < maxY ? vec.zCoord > minZ && vec.zCoord < maxZ : false : false;
	}

	/**
	 * Returns the average length of the edges of the bounding box.
	 */
	public double getAverageEdgeLength() {
		final double var1 = maxX - minX;
		final double var3 = maxY - minY;
		final double var5 = maxZ - minZ;
		return (var1 + var3 + var5) / 3.0D;
	}

	/**
	 * Returns a bounding box that is inset by the specified amounts
	 */
	public AxisAlignedBB contract(final double x, final double y, final double z) {
		final double var7 = minX + x;
		final double var9 = minY + y;
		final double var11 = minZ + z;
		final double var13 = maxX - x;
		final double var15 = maxY - y;
		final double var17 = maxZ - z;
		return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
	}

	public MovingObjectPosition calculateIntercept(final Vec3 p_72327_1_, final Vec3 p_72327_2_) {
		Vec3 var3 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, minX);
		Vec3 var4 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, maxX);
		Vec3 var5 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, minY);
		Vec3 var6 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, maxY);
		Vec3 var7 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, minZ);
		Vec3 var8 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, maxZ);

		if (!isVecInYZ(var3)) {
			var3 = null;
		}

		if (!isVecInYZ(var4)) {
			var4 = null;
		}

		if (!isVecInXZ(var5)) {
			var5 = null;
		}

		if (!isVecInXZ(var6)) {
			var6 = null;
		}

		if (!isVecInXY(var7)) {
			var7 = null;
		}

		if (!isVecInXY(var8)) {
			var8 = null;
		}

		Vec3 var9 = null;

		if (var3 != null) {
			var9 = var3;
		}

		if (var4 != null && (var9 == null || p_72327_1_.squareDistanceTo(var4) < p_72327_1_.squareDistanceTo(var9))) {
			var9 = var4;
		}

		if (var5 != null && (var9 == null || p_72327_1_.squareDistanceTo(var5) < p_72327_1_.squareDistanceTo(var9))) {
			var9 = var5;
		}

		if (var6 != null && (var9 == null || p_72327_1_.squareDistanceTo(var6) < p_72327_1_.squareDistanceTo(var9))) {
			var9 = var6;
		}

		if (var7 != null && (var9 == null || p_72327_1_.squareDistanceTo(var7) < p_72327_1_.squareDistanceTo(var9))) {
			var9 = var7;
		}

		if (var8 != null && (var9 == null || p_72327_1_.squareDistanceTo(var8) < p_72327_1_.squareDistanceTo(var9))) {
			var9 = var8;
		}

		if (var9 == null) {
			return null;
		} else {
			EnumFacing var10 = null;

			if (var9 == var3) {
				var10 = EnumFacing.WEST;
			} else if (var9 == var4) {
				var10 = EnumFacing.EAST;
			} else if (var9 == var5) {
				var10 = EnumFacing.DOWN;
			} else if (var9 == var6) {
				var10 = EnumFacing.UP;
			} else if (var9 == var7) {
				var10 = EnumFacing.NORTH;
			} else {
				var10 = EnumFacing.SOUTH;
			}

			return new MovingObjectPosition(var9, var10);
		}
	}

	/**
	 * Checks if the specified vector is within the YZ dimensions of the
	 * bounding box. Args: Vec3D
	 */
	private boolean isVecInYZ(final Vec3 vec) {
		return vec == null ? false
				: vec.yCoord >= minY && vec.yCoord <= maxY && vec.zCoord >= minZ && vec.zCoord <= maxZ;
	}

	/**
	 * Checks if the specified vector is within the XZ dimensions of the
	 * bounding box. Args: Vec3D
	 */
	private boolean isVecInXZ(final Vec3 vec) {
		return vec == null ? false
				: vec.xCoord >= minX && vec.xCoord <= maxX && vec.zCoord >= minZ && vec.zCoord <= maxZ;
	}

	/**
	 * Checks if the specified vector is within the XY dimensions of the
	 * bounding box. Args: Vec3D
	 */
	private boolean isVecInXY(final Vec3 vec) {
		return vec == null ? false
				: vec.xCoord >= minX && vec.xCoord <= maxX && vec.yCoord >= minY && vec.yCoord <= maxY;
	}

	@Override
	public String toString() {
		return "box[" + minX + ", " + minY + ", " + minZ + " -> " + maxX + ", " + maxY + ", " + maxZ + "]";
	}
}
