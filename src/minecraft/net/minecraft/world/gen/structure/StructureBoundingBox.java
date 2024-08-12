package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

import com.google.common.base.Objects;

public class StructureBoundingBox {

public static final int EaZy = 1816;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The first x coordinate of a bounding box. */
	public int minX;

	/** The first y coordinate of a bounding box. */
	public int minY;

	/** The first z coordinate of a bounding box. */
	public int minZ;

	/** The second x coordinate of a bounding box. */
	public int maxX;

	/** The second y coordinate of a bounding box. */
	public int maxY;

	/** The second z coordinate of a bounding box. */
	public int maxZ;
	// private static final String __OBFID = "http://https://fuckuskid00000442";

	public StructureBoundingBox() {}

	public StructureBoundingBox(final int[] p_i43000_1_) {
		if (p_i43000_1_.length == 6) {
			minX = p_i43000_1_[0];
			minY = p_i43000_1_[1];
			minZ = p_i43000_1_[2];
			maxX = p_i43000_1_[3];
			maxY = p_i43000_1_[4];
			maxZ = p_i43000_1_[5];
		}
	}

	/**
	 * returns a new StructureBoundingBox with MAX values
	 */
	public static StructureBoundingBox getNewBoundingBox() {
		return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	public static StructureBoundingBox func_175897_a(final int p_175897_0_, final int p_175897_1_,
			final int p_175897_2_, final int p_175897_3_, final int p_175897_4_, final int p_175897_5_,
			final int p_175897_6_, final int p_175897_7_, final int p_175897_8_, final EnumFacing p_175897_9_) {
		switch (StructureBoundingBox.SwitchEnumFacing.field_175895_a[p_175897_9_.ordinal()]) {
			case 1:
				return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_,
						p_175897_2_ - p_175897_8_ + 1 + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_,
						p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_5_);

			case 2:
				return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_,
						p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_,
						p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);

			case 3:
				return new StructureBoundingBox(p_175897_0_ - p_175897_8_ + 1 + p_175897_5_, p_175897_1_ + p_175897_4_,
						p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_5_,
						p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);

			case 4:
				return new StructureBoundingBox(p_175897_0_ + p_175897_5_, p_175897_1_ + p_175897_4_,
						p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_8_ - 1 + p_175897_5_,
						p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);

			default:
				return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_,
						p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_,
						p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);
		}
	}

	public static StructureBoundingBox func_175899_a(final int p_175899_0_, final int p_175899_1_,
			final int p_175899_2_, final int p_175899_3_, final int p_175899_4_, final int p_175899_5_) {
		return new StructureBoundingBox(Math.min(p_175899_0_, p_175899_3_), Math.min(p_175899_1_, p_175899_4_),
				Math.min(p_175899_2_, p_175899_5_), Math.max(p_175899_0_, p_175899_3_),
				Math.max(p_175899_1_, p_175899_4_), Math.max(p_175899_2_, p_175899_5_));
	}

	public StructureBoundingBox(final StructureBoundingBox p_i2031_1_) {
		minX = p_i2031_1_.minX;
		minY = p_i2031_1_.minY;
		minZ = p_i2031_1_.minZ;
		maxX = p_i2031_1_.maxX;
		maxY = p_i2031_1_.maxY;
		maxZ = p_i2031_1_.maxZ;
	}

	public StructureBoundingBox(final int p_i2032_1_, final int p_i2032_2_, final int p_i2032_3_, final int p_i2032_4_,
			final int p_i2032_5_, final int p_i2032_6_) {
		minX = p_i2032_1_;
		minY = p_i2032_2_;
		minZ = p_i2032_3_;
		maxX = p_i2032_4_;
		maxY = p_i2032_5_;
		maxZ = p_i2032_6_;
	}

	public StructureBoundingBox(final Vec3i p_i45626_1_, final Vec3i p_i45626_2_) {
		minX = Math.min(p_i45626_1_.getX(), p_i45626_2_.getX());
		minY = Math.min(p_i45626_1_.getY(), p_i45626_2_.getY());
		minZ = Math.min(p_i45626_1_.getZ(), p_i45626_2_.getZ());
		maxX = Math.max(p_i45626_1_.getX(), p_i45626_2_.getX());
		maxY = Math.max(p_i45626_1_.getY(), p_i45626_2_.getY());
		maxZ = Math.max(p_i45626_1_.getZ(), p_i45626_2_.getZ());
	}

	public StructureBoundingBox(final int p_i2033_1_, final int p_i2033_2_, final int p_i2033_3_,
			final int p_i2033_4_) {
		minX = p_i2033_1_;
		minZ = p_i2033_2_;
		maxX = p_i2033_3_;
		maxZ = p_i2033_4_;
		minY = 1;
		maxY = 512;
	}

	/**
	 * Discover if bounding box can fit within the current bounding box object.
	 */
	public boolean intersectsWith(final StructureBoundingBox p_78884_1_) {
		return maxX >= p_78884_1_.minX && minX <= p_78884_1_.maxX && maxZ >= p_78884_1_.minZ && minZ <= p_78884_1_.maxZ
				&& maxY >= p_78884_1_.minY && minY <= p_78884_1_.maxY;
	}

	/**
	 * Discover if a coordinate is inside the bounding box area.
	 */
	public boolean intersectsWith(final int p_78885_1_, final int p_78885_2_, final int p_78885_3_,
			final int p_78885_4_) {
		return maxX >= p_78885_1_ && minX <= p_78885_3_ && maxZ >= p_78885_2_ && minZ <= p_78885_4_;
	}

	/**
	 * Expands a bounding box's dimensions to include the supplied bounding box.
	 */
	public void expandTo(final StructureBoundingBox p_78888_1_) {
		minX = Math.min(minX, p_78888_1_.minX);
		minY = Math.min(minY, p_78888_1_.minY);
		minZ = Math.min(minZ, p_78888_1_.minZ);
		maxX = Math.max(maxX, p_78888_1_.maxX);
		maxY = Math.max(maxY, p_78888_1_.maxY);
		maxZ = Math.max(maxZ, p_78888_1_.maxZ);
	}

	/**
	 * Offsets the current bounding box by the specified coordinates. Args: x,
	 * y, z
	 */
	public void offset(final int p_78886_1_, final int p_78886_2_, final int p_78886_3_) {
		minX += p_78886_1_;
		minY += p_78886_2_;
		minZ += p_78886_3_;
		maxX += p_78886_1_;
		maxY += p_78886_2_;
		maxZ += p_78886_3_;
	}

	public boolean func_175898_b(final Vec3i p_175898_1_) {
		return p_175898_1_.getX() >= minX && p_175898_1_.getX() <= maxX && p_175898_1_.getZ() >= minZ
				&& p_175898_1_.getZ() <= maxZ && p_175898_1_.getY() >= minY && p_175898_1_.getY() <= maxY;
	}

	public Vec3i func_175896_b() {
		return new Vec3i(maxX - minX, maxY - minY, maxZ - minZ);
	}

	/**
	 * Get dimension of the bounding box in the x direction.
	 */
	public int getXSize() {
		return maxX - minX + 1;
	}

	/**
	 * Get dimension of the bounding box in the y direction.
	 */
	public int getYSize() {
		return maxY - minY + 1;
	}

	/**
	 * Get dimension of the bounding box in the z direction.
	 */
	public int getZSize() {
		return maxZ - minZ + 1;
	}

	public Vec3i func_180717_f() {
		return new BlockPos(minX + (maxX - minX + 1) / 2, minY + (maxY - minY + 1) / 2, minZ + (maxZ - minZ + 1) / 2);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("x0", minX).add("y0", minY).add("z0", minZ).add("x1", maxX)
				.add("y1", maxY).add("z1", maxZ).toString();
	}

	public NBTTagIntArray func_151535_h() {
		return new NBTTagIntArray(new int[] { minX, minY, minZ, maxX, maxY, maxZ });
	}

	static final class SwitchEnumFacing {
		static final int[] field_175895_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00001999";

		static {
			try {
				field_175895_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_175895_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_175895_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_175895_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
