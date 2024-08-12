package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class BlockRailPowered extends BlockRailBase {

public static final int EaZy = 358;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176568_b = PropertyEnum.create("shape",
			BlockRailBase.EnumRailDirection.class, new Predicate() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002080";
				public boolean func_180133_a(final BlockRailBase.EnumRailDirection p_180133_1_) {
					return p_180133_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST
							&& p_180133_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST
							&& p_180133_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST
							&& p_180133_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST;
				}

				@Override
				public boolean apply(final Object p_apply_1_) {
					return func_180133_a((BlockRailBase.EnumRailDirection) p_apply_1_);
				}
			});
	public static final PropertyBool field_176569_M = PropertyBool.create("powered");
	// private static final String __OBFID = "http://https://fuckuskid00000288";

	protected BlockRailPowered() {
		super(true);
		setDefaultState(
				blockState.getBaseState().withProperty(field_176568_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH)
						.withProperty(field_176569_M, false));
	}

	protected boolean func_176566_a(final World worldIn, final BlockPos p_176566_2_, final IBlockState p_176566_3_,
			final boolean p_176566_4_, final int p_176566_5_) {
		if (p_176566_5_ >= 8) {
			return false;
		} else {
			int var6 = p_176566_2_.getX();
			int var7 = p_176566_2_.getY();
			int var8 = p_176566_2_.getZ();
			boolean var9 = true;
			BlockRailBase.EnumRailDirection var10 = (BlockRailBase.EnumRailDirection) p_176566_3_
					.getValue(field_176568_b);

			switch (BlockRailPowered.SwitchEnumRailDirection.field_180121_a[var10.ordinal()]) {
				case 1:
					if (p_176566_4_) {
						++var8;
					} else {
						--var8;
					}

					break;

				case 2:
					if (p_176566_4_) {
						--var6;
					} else {
						++var6;
					}

					break;

				case 3:
					if (p_176566_4_) {
						--var6;
					} else {
						++var6;
						++var7;
						var9 = false;
					}

					var10 = BlockRailBase.EnumRailDirection.EAST_WEST;
					break;

				case 4:
					if (p_176566_4_) {
						--var6;
						++var7;
						var9 = false;
					} else {
						++var6;
					}

					var10 = BlockRailBase.EnumRailDirection.EAST_WEST;
					break;

				case 5:
					if (p_176566_4_) {
						++var8;
					} else {
						--var8;
						++var7;
						var9 = false;
					}

					var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
					break;

				case 6:
					if (p_176566_4_) {
						++var8;
						++var7;
						var9 = false;
					} else {
						--var8;
					}

					var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			return func_176567_a(worldIn, new BlockPos(var6, var7, var8), p_176566_4_, p_176566_5_, var10) ? true
					: var9 && func_176567_a(worldIn, new BlockPos(var6, var7 - 1, var8), p_176566_4_, p_176566_5_,
							var10);
		}
	}

	protected boolean func_176567_a(final World worldIn, final BlockPos p_176567_2_, final boolean p_176567_3_,
			final int p_176567_4_, final BlockRailBase.EnumRailDirection p_176567_5_) {
		final IBlockState var6 = worldIn.getBlockState(p_176567_2_);

		if (var6.getBlock() != this) {
			return false;
		} else {
			final BlockRailBase.EnumRailDirection var7 = (BlockRailBase.EnumRailDirection) var6
					.getValue(field_176568_b);
			return p_176567_5_ == BlockRailBase.EnumRailDirection.EAST_WEST
					&& (var7 == BlockRailBase.EnumRailDirection.NORTH_SOUTH
							|| var7 == BlockRailBase.EnumRailDirection.ASCENDING_NORTH
							|| var7 == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH)
									? false
									: p_176567_5_ == BlockRailBase.EnumRailDirection.NORTH_SOUTH
											&& (var7 == BlockRailBase.EnumRailDirection.EAST_WEST
													|| var7 == BlockRailBase.EnumRailDirection.ASCENDING_EAST
													|| var7 == BlockRailBase.EnumRailDirection.ASCENDING_WEST)
															? false
															: ((Boolean) var6.getValue(field_176569_M))
																	? worldIn.isBlockPowered(p_176567_2_) ? true
																			: func_176566_a(worldIn, p_176567_2_, var6,
																					p_176567_3_, p_176567_4_ + 1)
																	: false;
		}
	}

	@Override
	protected void func_176561_b(final World worldIn, final BlockPos p_176561_2_, final IBlockState p_176561_3_,
			final Block p_176561_4_) {
		final boolean var5 = ((Boolean) p_176561_3_.getValue(field_176569_M));
		final boolean var6 = worldIn.isBlockPowered(p_176561_2_)
				|| func_176566_a(worldIn, p_176561_2_, p_176561_3_, true, 0)
				|| func_176566_a(worldIn, p_176561_2_, p_176561_3_, false, 0);

		if (var6 != var5) {
			worldIn.setBlockState(p_176561_2_, p_176561_3_.withProperty(field_176569_M, var6), 3);
			worldIn.notifyNeighborsOfStateChange(p_176561_2_.offsetDown(), this);

			if (((BlockRailBase.EnumRailDirection) p_176561_3_.getValue(field_176568_b)).func_177018_c()) {
				worldIn.notifyNeighborsOfStateChange(p_176561_2_.offsetUp(), this);
			}
		}
	}

	@Override
	public IProperty func_176560_l() {
		return field_176568_b;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176568_b, BlockRailBase.EnumRailDirection.func_177016_a(meta & 7))
				.withProperty(field_176569_M, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((BlockRailBase.EnumRailDirection) state.getValue(field_176568_b)).func_177015_a();

		if (((Boolean) state.getValue(field_176569_M))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176568_b, field_176569_M });
	}

	static final class SwitchEnumRailDirection {
		static final int[] field_180121_a = new int[BlockRailBase.EnumRailDirection.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002079";

		static {
			try {
				field_180121_a[BlockRailBase.EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_180121_a[BlockRailBase.EnumRailDirection.EAST_WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180121_a[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
