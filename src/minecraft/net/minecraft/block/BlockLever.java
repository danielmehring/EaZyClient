package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;

public class BlockLever extends Block {

public static final int EaZy = 324;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum FACING = PropertyEnum.create("facing", BlockLever.EnumOrientation.class);
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	// private static final String __OBFID = "http://https://fuckuskid00000264";

	protected BlockLever() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(FACING, BlockLever.EnumOrientation.NORTH)
				.withProperty(POWERED, false));
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(final World worldIn, final BlockPos pos, final EnumFacing side) {
		return side == EnumFacing.UP && World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) ? true
				: func_176358_d(worldIn, pos.offset(side.getOpposite()));
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return func_176358_d(worldIn, pos.offsetWest()) ? true
				: func_176358_d(worldIn, pos.offsetEast()) ? true
						: func_176358_d(worldIn, pos.offsetNorth()) ? true
								: func_176358_d(worldIn, pos.offsetSouth()) ? true
										: World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()) ? true
												: func_176358_d(worldIn, pos.offsetUp());
	}

	protected boolean func_176358_d(final World worldIn, final BlockPos p_176358_2_) {
		return worldIn.getBlockState(p_176358_2_).getBlock().isNormalCube();
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		final IBlockState var9 = getDefaultState().withProperty(POWERED, false);

		if (func_176358_d(worldIn, pos.offset(facing.getOpposite()))) {
			return var9.withProperty(FACING, BlockLever.EnumOrientation.func_176856_a(facing, placer.func_174811_aO()));
		} else {
			final Iterator var10 = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing var11;

			do {
				if (!var10.hasNext()) {
					if (World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown())) {
						return var9.withProperty(FACING,
								BlockLever.EnumOrientation.func_176856_a(EnumFacing.UP, placer.func_174811_aO()));
					}

					return var9;
				}

				var11 = (EnumFacing) var10.next();
			}
			while (var11 == facing || !func_176358_d(worldIn, pos.offset(var11.getOpposite())));

			return var9.withProperty(FACING, BlockLever.EnumOrientation.func_176856_a(var11, placer.func_174811_aO()));
		}
	}

	public static int func_176357_a(final EnumFacing p_176357_0_) {
		switch (BlockLever.SwitchEnumFacing.FACING_LOOKUP[p_176357_0_.ordinal()]) {
			case 1:
				return 0;

			case 2:
				return 5;

			case 3:
				return 4;

			case 4:
				return 3;

			case 5:
				return 2;

			case 6:
				return 1;

			default:
				return -1;
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (func_176356_e(worldIn, pos) && !func_176358_d(worldIn,
				pos.offset(((BlockLever.EnumOrientation) state.getValue(FACING)).func_176852_c().getOpposite()))) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean func_176356_e(final World worldIn, final BlockPos p_176356_2_) {
		if (canPlaceBlockAt(worldIn, p_176356_2_)) {
			return true;
		} else {
			dropBlockAsItem(worldIn, p_176356_2_, worldIn.getBlockState(p_176356_2_), 0);
			worldIn.setBlockToAir(p_176356_2_);
			return false;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		float var3 = 0.1875F;

		switch (BlockLever.SwitchEnumFacing.ORIENTATION_LOOKUP[((BlockLever.EnumOrientation) access.getBlockState(pos)
				.getValue(FACING)).ordinal()]) {
			case 1:
				setBlockBounds(0.0F, 0.2F, 0.5F - var3, var3 * 2.0F, 0.8F, 0.5F + var3);
				break;

			case 2:
				setBlockBounds(1.0F - var3 * 2.0F, 0.2F, 0.5F - var3, 1.0F, 0.8F, 0.5F + var3);
				break;

			case 3:
				setBlockBounds(0.5F - var3, 0.2F, 0.0F, 0.5F + var3, 0.8F, var3 * 2.0F);
				break;

			case 4:
				setBlockBounds(0.5F - var3, 0.2F, 1.0F - var3 * 2.0F, 0.5F + var3, 0.8F, 1.0F);
				break;

			case 5:
			case 6:
				var3 = 0.25F;
				setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.6F, 0.5F + var3);
				break;

			case 7:
			case 8:
				var3 = 0.25F;
				setBlockBounds(0.5F - var3, 0.4F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			state = state.cycleProperty(POWERED);
			worldIn.setBlockState(pos, state, 3);
			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F,
					((Boolean) state.getValue(POWERED)) ? 0.6F : 0.5F);
			worldIn.notifyNeighborsOfStateChange(pos, this);
			final EnumFacing var9 = ((BlockLever.EnumOrientation) state.getValue(FACING)).func_176852_c();
			worldIn.notifyNeighborsOfStateChange(pos.offset(var9.getOpposite()), this);
			return true;
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (((Boolean) state.getValue(POWERED))) {
			worldIn.notifyNeighborsOfStateChange(pos, this);
			final EnumFacing var4 = ((BlockLever.EnumOrientation) state.getValue(FACING)).func_176852_c();
			worldIn.notifyNeighborsOfStateChange(pos.offset(var4.getOpposite()), this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return ((Boolean) state.getValue(POWERED)) ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return !((Boolean) state.getValue(POWERED)) ? 0
				: ((BlockLever.EnumOrientation) state.getValue(FACING)).func_176852_c() == side ? 15 : 0;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.func_176853_a(meta & 7))
				.withProperty(POWERED, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((BlockLever.EnumOrientation) state.getValue(FACING)).func_176855_a();

		if (((Boolean) state.getValue(POWERED))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, POWERED });
	}

	public static enum EnumOrientation implements IStringSerializable {
		DOWN_X("DOWN_X", 0, 0, "down_x", EnumFacing.DOWN), EAST("EAST", 1, 1, "east", EnumFacing.EAST), WEST("WEST", 2,
				2, "west", EnumFacing.WEST), SOUTH("SOUTH", 3, 3, "south", EnumFacing.SOUTH), NORTH("NORTH", 4, 4,
						"north", EnumFacing.NORTH), UP_Z("UP_Z", 5, 5, "up_z", EnumFacing.UP), UP_X("UP_X", 6, 6,
								"up_x", EnumFacing.UP), DOWN_Z("DOWN_Z", 7, 7, "down_z", EnumFacing.DOWN);
		private static final BlockLever.EnumOrientation[] field_176869_i = new BlockLever.EnumOrientation[values().length];
		private final int field_176866_j;
		private final String field_176867_k;
		private final EnumFacing field_176864_l;

		private EnumOrientation(final String p_i45709_1_, final int p_i45709_2_, final int p_i45709_3_,
				final String p_i45709_4_, final EnumFacing p_i45709_5_) {
			field_176866_j = p_i45709_3_;
			field_176867_k = p_i45709_4_;
			field_176864_l = p_i45709_5_;
		}

		public int func_176855_a() {
			return field_176866_j;
		}

		public EnumFacing func_176852_c() {
			return field_176864_l;
		}

		@Override
		public String toString() {
			return field_176867_k;
		}

		public static BlockLever.EnumOrientation func_176853_a(int p_176853_0_) {
			if (p_176853_0_ < 0 || p_176853_0_ >= field_176869_i.length) {
				p_176853_0_ = 0;
			}

			return field_176869_i[p_176853_0_];
		}

		public static BlockLever.EnumOrientation func_176856_a(final EnumFacing p_176856_0_,
				final EnumFacing p_176856_1_) {
			switch (BlockLever.SwitchEnumFacing.FACING_LOOKUP[p_176856_0_.ordinal()]) {
				case 1:
					switch (BlockLever.SwitchEnumFacing.AXIS_LOOKUP[p_176856_1_.getAxis().ordinal()]) {
						case 1:
							return DOWN_X;

						case 2:
							return DOWN_Z;

						default:
							throw new IllegalArgumentException(
									"Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
					}

				case 2:
					switch (BlockLever.SwitchEnumFacing.AXIS_LOOKUP[p_176856_1_.getAxis().ordinal()]) {
						case 1:
							return UP_X;

						case 2:
							return UP_Z;

						default:
							throw new IllegalArgumentException(
									"Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
					}

				case 3:
					return NORTH;

				case 4:
					return SOUTH;

				case 5:
					return WEST;

				case 6:
					return EAST;

				default:
					throw new IllegalArgumentException("Invalid facing: " + p_176856_0_);
			}
		}

		@Override
		public String getName() {
			return field_176867_k;
		}

		static {
			final BlockLever.EnumOrientation[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final BlockLever.EnumOrientation var3 = var0[var2];
				field_176869_i[var3.func_176855_a()] = var3;
			}
		}
	}

	static final class SwitchEnumFacing {
		static final int[] FACING_LOOKUP;

		static final int[] ORIENTATION_LOOKUP;

		static final int[] AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002103";

		static {
			try {
				AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
			} catch (final NoSuchFieldError var16) {
			}

			try {
				AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 2;
			} catch (final NoSuchFieldError var15) {
			}

			ORIENTATION_LOOKUP = new int[BlockLever.EnumOrientation.values().length];

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.EAST.ordinal()] = 1;
			} catch (final NoSuchFieldError var14) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var13) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.SOUTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var12) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.NORTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var11) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.UP_Z.ordinal()] = 5;
			} catch (final NoSuchFieldError var10) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.UP_X.ordinal()] = 6;
			} catch (final NoSuchFieldError var9) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.DOWN_X.ordinal()] = 7;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				ORIENTATION_LOOKUP[BlockLever.EnumOrientation.DOWN_Z.ordinal()] = 8;
			} catch (final NoSuchFieldError var7) {
			}

			FACING_LOOKUP = new int[EnumFacing.values().length];

			try {
				FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				FACING_LOOKUP[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
