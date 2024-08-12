package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block {

public static final int EaZy = 397;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176284_a = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool field_176283_b = PropertyBool.create("open");
	public static final PropertyEnum field_176285_M = PropertyEnum.create("half", BlockTrapDoor.DoorHalf.class);
	// private static final String __OBFID = "http://https://fuckuskid00000327";

	protected BlockTrapDoor(final Material p_i45434_1_) {
		super(p_i45434_1_);
		setDefaultState(blockState.getBaseState().withProperty(field_176284_a, EnumFacing.NORTH)
				.withProperty(field_176283_b, false)
				.withProperty(field_176285_M, BlockTrapDoor.DoorHalf.BOTTOM));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isPassable(final IBlockAccess blockAccess, final BlockPos pos) {
		return !((Boolean) blockAccess.getBlockState(pos).getValue(field_176283_b));
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getSelectedBoundingBox(worldIn, pos);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		func_180693_d(access.getBlockState(pos));
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
	}

	public void func_180693_d(final IBlockState p_180693_1_) {
		if (p_180693_1_.getBlock() == this) {
			final boolean var2 = p_180693_1_.getValue(field_176285_M) == BlockTrapDoor.DoorHalf.TOP;
			final Boolean var3 = (Boolean) p_180693_1_.getValue(field_176283_b);
			final EnumFacing var4 = (EnumFacing) p_180693_1_.getValue(field_176284_a);
			if (var2) {
				setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
			} else {
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
			}

			if (var3) {
				if (var4 == EnumFacing.NORTH) {
					setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
				}

				if (var4 == EnumFacing.SOUTH) {
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
				}

				if (var4 == EnumFacing.WEST) {
					setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				}

				if (var4 == EnumFacing.EAST) {
					setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (blockMaterial == Material.iron) {
			return true;
		} else {
			state = state.cycleProperty(field_176283_b);
			worldIn.setBlockState(pos, state, 2);
			worldIn.playAuxSFXAtEntity(playerIn,
					((Boolean) state.getValue(field_176283_b)) ? 1003 : 1006, pos, 0);
			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			final BlockPos var5 = pos.offset(((EnumFacing) state.getValue(field_176284_a)).getOpposite());

			if (!isValidSupportBlock(worldIn.getBlockState(var5).getBlock())) {
				worldIn.setBlockToAir(pos);
				dropBlockAsItem(worldIn, pos, state, 0);
			} else {
				final boolean var6 = worldIn.isBlockPowered(pos);

				if (var6 || neighborBlock.canProvidePower()) {
					final boolean var7 = ((Boolean) state.getValue(field_176283_b));

					if (var7 != var6) {
						worldIn.setBlockState(pos, state.withProperty(field_176283_b, var6), 2);
						worldIn.playAuxSFXAtEntity((EntityPlayer) null, var6 ? 1003 : 1006, pos, 0);
					}
				}
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit.
	 * 
	 * @param start
	 *            The start vector
	 * @param end
	 *            The end vector
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(final World worldIn, final BlockPos pos, final Vec3 start,
			final Vec3 end) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.collisionRayTrace(worldIn, pos, start, end);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		IBlockState var9 = getDefaultState();

		if (facing.getAxis().isHorizontal()) {
			var9 = var9.withProperty(field_176284_a, facing).withProperty(field_176283_b, false);
			var9 = var9.withProperty(field_176285_M,
					hitY > 0.5F ? BlockTrapDoor.DoorHalf.TOP : BlockTrapDoor.DoorHalf.BOTTOM);
		}

		return var9;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(final World worldIn, final BlockPos pos, final EnumFacing side) {
		return !side.getAxis().isVertical()
				&& isValidSupportBlock(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
	}

	protected static EnumFacing func_176281_b(final int p_176281_0_) {
		switch (p_176281_0_ & 3) {
			case 0:
				return EnumFacing.NORTH;

			case 1:
				return EnumFacing.SOUTH;

			case 2:
				return EnumFacing.WEST;

			case 3:
			default:
				return EnumFacing.EAST;
		}
	}

	protected static int func_176282_a(final EnumFacing p_176282_0_) {
		switch (BlockTrapDoor.SwitchEnumFacing.field_177058_a[p_176282_0_.ordinal()]) {
			case 1:
				return 0;

			case 2:
				return 1;

			case 3:
				return 2;

			case 4:
			default:
				return 3;
		}
	}

	private static boolean isValidSupportBlock(final Block p_150119_0_) {
		return p_150119_0_.blockMaterial.isOpaque() && p_150119_0_.isFullCube() || p_150119_0_ == Blocks.glowstone
				|| p_150119_0_ instanceof BlockSlab || p_150119_0_ instanceof BlockStairs;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176284_a, func_176281_b(meta))
				.withProperty(field_176283_b, (meta & 4) != 0).withProperty(field_176285_M,
						(meta & 8) == 0 ? BlockTrapDoor.DoorHalf.BOTTOM : BlockTrapDoor.DoorHalf.TOP);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | func_176282_a((EnumFacing) state.getValue(field_176284_a));

		if (((Boolean) state.getValue(field_176283_b))) {
			var3 |= 4;
		}

		if (state.getValue(field_176285_M) == BlockTrapDoor.DoorHalf.TOP) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176284_a, field_176283_b, field_176285_M });
	}

	public static enum DoorHalf implements IStringSerializable {
		TOP("TOP", 0, "top"), BOTTOM("BOTTOM", 1, "bottom");
		private final String field_176671_c;

		private DoorHalf(final String p_i45674_1_, final int p_i45674_2_, final String p_i45674_3_) {
			field_176671_c = p_i45674_3_;
		}

		@Override
		public String toString() {
			return field_176671_c;
		}

		@Override
		public String getName() {
			return field_176671_c;
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_177058_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002052";

		static {
			try {
				field_177058_a[EnumFacing.NORTH.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_177058_a[EnumFacing.SOUTH.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_177058_a[EnumFacing.WEST.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_177058_a[EnumFacing.EAST.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
