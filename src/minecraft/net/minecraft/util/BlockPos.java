package net.minecraft.util;

import net.minecraft.entity.Entity;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

public class BlockPos extends Vec3i {

public static final int EaZy = 1591;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The BlockPos with all coordinates 0 */
	public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
	private static final int field_177990_b = 1
			+ MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
	private static final int field_177991_c = field_177990_b;
	private static final int field_177989_d = 64 - field_177990_b - field_177991_c;
	private static final int field_177987_f = 0 + field_177991_c;
	private static final int field_177988_g = field_177987_f + field_177989_d;
	private static final long field_177994_h = (1L << field_177990_b) - 1L;
	private static final long field_177995_i = (1L << field_177989_d) - 1L;
	private static final long field_177993_j = (1L << field_177991_c) - 1L;
	// private static final String __OBFID = "http://https://fuckuskid00002334";

	public BlockPos(final int x, final int y, final int z) {
		super(x, y, z);
	}

	public BlockPos(final double x, final double y, final double z) {
		super(x, y, z);
	}

	public BlockPos(final Entity p_i46032_1_) {
		this(p_i46032_1_.posX, p_i46032_1_.posY, p_i46032_1_.posZ);
	}

	public BlockPos(final Vec3 p_i46033_1_) {
		this(p_i46033_1_.xCoord, p_i46033_1_.yCoord, p_i46033_1_.zCoord);
	}

	public BlockPos(final Vec3i p_i46034_1_) {
		this(p_i46034_1_.getX(), p_i46034_1_.getY(), p_i46034_1_.getZ());
	}

	/**
	 * Add the given coordinates to the coordinates of this BlockPos
	 * 
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param z
	 *            Z coordinate
	 */
	public BlockPos add(final double x, final double y, final double z) {
		return new BlockPos(getX() + x, getY() + y, getZ() + z);
	}

	/**
	 * Add the given coordinates to the coordinates of this BlockPos
	 * 
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param z
	 *            Z coordinate
	 */
	public BlockPos add(final int x, final int y, final int z) {
		return new BlockPos(getX() + x, getY() + y, getZ() + z);
	}

	/**
	 * Add the given Vector to this BlockPos
	 */
	public BlockPos add(final Vec3i vec) {
		return new BlockPos(getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ());
	}

	/**
	 * Subtract the given Vector from this BlockPos
	 */
	public BlockPos subtract(final Vec3i vec) {
		return new BlockPos(getX() - vec.getX(), getY() - vec.getY(), getZ() - vec.getZ());
	}

	/**
	 * Multiply every coordinate by the given factor
	 */
	public BlockPos multiply(final int factor) {
		return new BlockPos(getX() * factor, getY() * factor, getZ() * factor);
	}

	/**
	 * Offset this BlockPos 1 block up
	 */
	public BlockPos offsetUp() {
		return this.offsetUp(1);
	}

	/**
	 * Offset this BlockPos n blocks up
	 */
	public BlockPos offsetUp(final int n) {
		return this.offset(EnumFacing.UP, n);
	}

	/**
	 * Offset this BlockPos 1 block down
	 */
	public BlockPos offsetDown() {
		return this.offsetDown(1);
	}

	/**
	 * Offset this BlockPos n blocks down
	 */
	public BlockPos offsetDown(final int n) {
		return this.offset(EnumFacing.DOWN, n);
	}

	/**
	 * Offset this BlockPos 1 block in northern direction
	 */
	public BlockPos offsetNorth() {
		return this.offsetNorth(1);
	}

	/**
	 * Offset this BlockPos n blocks in northern direction
	 */
	public BlockPos offsetNorth(final int n) {
		return this.offset(EnumFacing.NORTH, n);
	}

	/**
	 * Offset this BlockPos 1 block in southern direction
	 */
	public BlockPos offsetSouth() {
		return this.offsetSouth(1);
	}

	/**
	 * Offset this BlockPos n blocks in southern direction
	 */
	public BlockPos offsetSouth(final int n) {
		return this.offset(EnumFacing.SOUTH, n);
	}

	/**
	 * Offset this BlockPos 1 block in western direction
	 */
	public BlockPos offsetWest() {
		return this.offsetWest(1);
	}

	/**
	 * Offset this BlockPos n blocks in western direction
	 */
	public BlockPos offsetWest(final int n) {
		return this.offset(EnumFacing.WEST, n);
	}

	/**
	 * Offset this BlockPos 1 block in eastern direction
	 */
	public BlockPos offsetEast() {
		return this.offsetEast(1);
	}

	/**
	 * Offset this BlockPos n blocks in eastern direction
	 */
	public BlockPos offsetEast(final int n) {
		return this.offset(EnumFacing.EAST, n);
	}

	/**
	 * Offset this BlockPos 1 block in the given direction
	 */
	public BlockPos offset(final EnumFacing facing) {
		return this.offset(facing, 1);
	}

	/**
	 * Offset this BlockPos n blocks in the given direction
	 */
	public BlockPos offset(final EnumFacing facing, final int n) {
		return new BlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n,
				getZ() + facing.getFrontOffsetZ() * n);
	}

	/**
	 * Calculate the cross product of this BlockPos and the given Vector.
	 * Version of crossProduct that returns a BlockPos instead of a Vec3i
	 */
	public BlockPos crossProductBP(final Vec3i vec) {
		return new BlockPos(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(),
				getX() * vec.getY() - getY() * vec.getX());
	}

	/**
	 * Serialize this BlockPos into a long value
	 */
	public long toLong() {
		return (getX() & field_177994_h) << field_177988_g | (getY() & field_177995_i) << field_177987_f
				| (getZ() & field_177993_j) << 0;
	}

	/**
	 * Create a BlockPos from a serialized long value (created by toLong)
	 */
	public static BlockPos fromLong(final long serialized) {
		final int var2 = (int) (serialized << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
		final int var3 = (int) (serialized << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
		final int var4 = (int) (serialized << 64 - field_177991_c >> 64 - field_177991_c);
		return new BlockPos(var2, var3, var4);
	}

	/**
	 * Create an Iterable that returns all positions in the box specified by the
	 * given corners
	 * 
	 * @param from
	 *            The first corner (inclusive)
	 * @param to
	 *            the second corner (exclusive)
	 */
	public static Iterable getAllInBox(final BlockPos from, final BlockPos to) {
		final BlockPos var2 = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
				Math.min(from.getZ(), to.getZ()));
		final BlockPos var3 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
				Math.max(from.getZ(), to.getZ()));
		return new Iterable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002333";
			@Override
			public Iterator iterator() {
				return new AbstractIterator() {
					private BlockPos lastReturned = null;

					// private static final String __OBFID =
					// "http://https://fuckuskid00002332";
					protected BlockPos computeNext0() {
						if (lastReturned == null) {
							lastReturned = var2;
							return lastReturned;
						} else if (lastReturned.equals(var3)) {
							return (BlockPos) endOfData();
						} else {
							int var1 = lastReturned.getX();
							int var2x = lastReturned.getY();
							int var3x = lastReturned.getZ();

							if (var1 < var3.getX()) {
								++var1;
							} else if (var2x < var3.getY()) {
								var1 = var2.getX();
								++var2x;
							} else if (var3x < var3.getZ()) {
								var1 = var2.getX();
								var2x = var2.getY();
								++var3x;
							}

							lastReturned = new BlockPos(var1, var2x, var3x);
							return lastReturned;
						}
					}

					@Override
					protected Object computeNext() {
						return computeNext0();
					}
				};
			}
		};
	}

	/**
	 * Like getAllInBox but reuses a single MutableBlockPos instead. If this
	 * method is used, the resulting BlockPos instances can only be used inside
	 * the iteration loop.
	 * 
	 * @param from
	 *            The first corner (inclusive)
	 * @param to
	 *            the second corner (exclusive)
	 */
	public static Iterable getAllInBoxMutable(final BlockPos from, final BlockPos to) {
		final BlockPos var2 = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
				Math.min(from.getZ(), to.getZ()));
		final BlockPos var3 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
				Math.max(from.getZ(), to.getZ()));
		return new Iterable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002331";
			@Override
			public Iterator iterator() {
				return new AbstractIterator() {
					private BlockPos.MutableBlockPos theBlockPos = null;

					// private static final String __OBFID =
					// "http://https://fuckuskid00002330";
					protected BlockPos.MutableBlockPos computeNext0() {
						if (theBlockPos == null) {
							theBlockPos = new BlockPos.MutableBlockPos(var2.getX(), var2.getY(), var2.getZ(), null);
							return theBlockPos;
						} else if (theBlockPos.equals(var3)) {
							return (BlockPos.MutableBlockPos) endOfData();
						} else {
							int var1 = theBlockPos.getX();
							int var2xx = theBlockPos.getY();
							int var3x = theBlockPos.getZ();

							if (var1 < var3.getX()) {
								++var1;
							} else if (var2xx < var3.getY()) {
								var1 = var2.getX();
								++var2xx;
							} else if (var3x < var3.getZ()) {
								var1 = var2.getX();
								var2xx = var2.getY();
								++var3x;
							}

							theBlockPos.x = var1;
							theBlockPos.y = var2xx;
							theBlockPos.z = var3x;
							return theBlockPos;
						}
					}

					@Override
					protected Object computeNext() {
						return computeNext0();
					}
				};
			}
		};
	}

	/**
	 * Calculate the cross product of this and the given Vector
	 */
	@Override
	public Vec3i crossProduct(final Vec3i vec) {
		return crossProductBP(vec);
	}

	public static final class MutableBlockPos extends BlockPos {
		public int x;
		public int y;
		public int z;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002329";

		private MutableBlockPos(final int x_, final int y_, final int z_) {
			super(0, 0, 0);
			x = x_;
			y = y_;
			z = z_;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public int getZ() {
			return z;
		}

		@Override
		public Vec3i crossProduct(final Vec3i vec) {
			return super.crossProductBP(vec);
		}

		MutableBlockPos(final int p_i46025_1_, final int p_i46025_2_, final int p_i46025_3_, final Object p_i46025_4_) {
			this(p_i46025_1_, p_i46025_2_, p_i46025_3_);
		}
	}
}
