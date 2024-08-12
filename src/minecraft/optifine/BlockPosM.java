package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

public class BlockPosM extends BlockPos {

public static final int EaZy = 1876;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int mx;
	private int my;
	private int mz;
	private final int level;
	private BlockPosM[] facings;
	private boolean needsUpdate;

	public BlockPosM(final int x, final int y, final int z) {
		this(x, y, z, 0);
	}

	public BlockPosM(final double xIn, final double yIn, final double zIn) {
		this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
	}

	public BlockPosM(final int x, final int y, final int z, final int level) {
		super(0, 0, 0);
		mx = x;
		my = y;
		mz = z;
		this.level = level;
	}

	/**
	 * Get the X coordinate
	 */
	@Override
	public int getX() {
		return mx;
	}

	/**
	 * Get the Y coordinate
	 */
	@Override
	public int getY() {
		return my;
	}

	/**
	 * Get the Z coordinate
	 */
	@Override
	public int getZ() {
		return mz;
	}

	public void setXyz(final int x, final int y, final int z) {
		mx = x;
		my = y;
		mz = z;
		needsUpdate = true;
	}

	public void setXyz(final double xIn, final double yIn, final double zIn) {
		this.setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
	}

	/**
	 * Offset this BlockPos 1 block in the given direction
	 */
	@Override
	public BlockPos offset(final EnumFacing facing) {
		if (level <= 0) {
			return super.offset(facing, 1);
		} else {
			if (facings == null) {
				facings = new BlockPosM[EnumFacing.VALUES.length];
			}

			if (needsUpdate) {
				update();
			}

			final int index = facing.getIndex();
			BlockPosM bpm = facings[index];

			if (bpm == null) {
				final int nx = mx + facing.getFrontOffsetX();
				final int ny = my + facing.getFrontOffsetY();
				final int nz = mz + facing.getFrontOffsetZ();
				bpm = new BlockPosM(nx, ny, nz, level - 1);
				facings[index] = bpm;
			}

			return bpm;
		}
	}

	/**
	 * Offset this BlockPos n blocks in the given direction
	 */
	@Override
	public BlockPos offset(final EnumFacing facing, final int n) {
		return n == 1 ? this.offset(facing) : super.offset(facing, n);
	}

	private void update() {
		for (int i = 0; i < 6; ++i) {
			final BlockPosM bpm = facings[i];

			if (bpm != null) {
				final EnumFacing facing = EnumFacing.VALUES[i];
				final int nx = mx + facing.getFrontOffsetX();
				final int ny = my + facing.getFrontOffsetY();
				final int nz = mz + facing.getFrontOffsetZ();
				bpm.setXyz(nx, ny, nz);
			}
		}

		needsUpdate = false;
	}

	public static Iterable getAllInBoxMutable(final BlockPos from, final BlockPos to) {
		final BlockPos posFrom = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()),
				Math.min(from.getZ(), to.getZ()));
		final BlockPos posTo = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()),
				Math.max(from.getZ(), to.getZ()));
		return new Iterable() {
			@Override
			public Iterator iterator() {
				return new AbstractIterator() {
					private BlockPosM theBlockPosM = null;

					protected BlockPosM computeNext0() {
						if (theBlockPosM == null) {
							theBlockPosM = new BlockPosM(posFrom.getX(), posFrom.getY(), posFrom.getZ(), 3);
							return theBlockPosM;
						} else if (theBlockPosM.equals(posTo)) {
							return (BlockPosM) endOfData();
						} else {
							int bx = theBlockPosM.getX();
							int by = theBlockPosM.getY();
							int bz = theBlockPosM.getZ();

							if (bx < posTo.getX()) {
								++bx;
							} else if (by < posTo.getY()) {
								bx = posFrom.getX();
								++by;
							} else if (bz < posTo.getZ()) {
								bx = posFrom.getX();
								by = posFrom.getY();
								++bz;
							}

							theBlockPosM.setXyz(bx, by, bz);
							return theBlockPosM;
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

	public BlockPos getImmutable() {
		return new BlockPos(getX(), getY(), getZ());
	}
}
