package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block {

public static final int EaZy = 345;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176326_a = PropertyDirection.create("facing");
	public static final PropertyEnum field_176325_b = PropertyEnum.create("type",
			BlockPistonExtension.EnumPistonType.class);
	public static final PropertyBool field_176327_M = PropertyBool.create("short");
	// private static final String __OBFID = "http://https://fuckuskid00000367";

	public BlockPistonExtension() {
		super(Material.piston);
		setDefaultState(blockState.getBaseState().withProperty(field_176326_a, EnumFacing.NORTH)
				.withProperty(field_176325_b, BlockPistonExtension.EnumPistonType.DEFAULT)
				.withProperty(field_176327_M, false));
		setStepSound(soundTypePiston);
		setHardness(0.5F);
	}

	@Override
	public void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn) {
		if (playerIn.capabilities.isCreativeMode) {
			final EnumFacing var5 = (EnumFacing) state.getValue(field_176326_a);

			if (var5 != null) {
				final BlockPos var6 = pos.offset(var5.getOpposite());
				final Block var7 = worldIn.getBlockState(var6).getBlock();

				if (var7 == Blocks.piston || var7 == Blocks.sticky_piston) {
					worldIn.setBlockToAir(var6);
				}
			}
		}

		super.onBlockHarvested(worldIn, pos, state, playerIn);
	}

	@Override
	public void breakBlock(final World worldIn, BlockPos pos, final IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		final EnumFacing var4 = ((EnumFacing) state.getValue(field_176326_a)).getOpposite();
		pos = pos.offset(var4);
		final IBlockState var5 = worldIn.getBlockState(pos);

		if ((var5.getBlock() == Blocks.piston || var5.getBlock() == Blocks.sticky_piston)
				&& ((Boolean) var5.getValue(BlockPistonBase.EXTENDED))) {
			var5.getBlock().dropBlockAsItem(worldIn, pos, var5, 0);
			worldIn.setBlockToAir(pos);
		}
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
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return false;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(final World worldIn, final BlockPos pos, final EnumFacing side) {
		return false;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	/**
	 * Add all collision boxes of this Block to the list that intersect with the
	 * given mask.
	 * 
	 * @param collidingEntity
	 *            the Entity colliding with this Block
	 */
	@Override
	public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state,
			final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
		func_176324_d(state);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		func_176323_e(state);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	private void func_176323_e(final IBlockState p_176323_1_) {
		switch (BlockPistonExtension.SwitchEnumFacing.field_177247_a[((EnumFacing) p_176323_1_.getValue(field_176326_a))
				.ordinal()]) {
			case 1:
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
				break;

			case 2:
				setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
				break;

			case 3:
				setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
				break;

			case 4:
				setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
				break;

			case 5:
				setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
				break;

			case 6:
				setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		func_176324_d(access.getBlockState(pos));
	}

	public void func_176324_d(final IBlockState p_176324_1_) {
		final EnumFacing var3 = (EnumFacing) p_176324_1_.getValue(field_176326_a);

		if (var3 != null) {
			switch (BlockPistonExtension.SwitchEnumFacing.field_177247_a[var3.ordinal()]) {
				case 1:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
					break;

				case 2:
					setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;

				case 3:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
					break;

				case 4:
					setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
					break;

				case 5:
					setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
					break;

				case 6:
					setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final EnumFacing var5 = (EnumFacing) state.getValue(field_176326_a);
		final BlockPos var6 = pos.offset(var5.getOpposite());
		final IBlockState var7 = worldIn.getBlockState(var6);

		if (var7.getBlock() != Blocks.piston && var7.getBlock() != Blocks.sticky_piston) {
			worldIn.setBlockToAir(pos);
		} else {
			var7.getBlock().onNeighborBlockChange(worldIn, var6, var7, neighborBlock);
		}
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	public static EnumFacing func_176322_b(final int p_176322_0_) {
		final int var1 = p_176322_0_ & 7;
		return var1 > 5 ? null : EnumFacing.getFront(var1);
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY
				? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176326_a, func_176322_b(meta)).withProperty(field_176325_b,
				(meta & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY
						: BlockPistonExtension.EnumPistonType.DEFAULT);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(field_176326_a)).getIndex();

		if (state.getValue(field_176325_b) == BlockPistonExtension.EnumPistonType.STICKY) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176326_a, field_176325_b, field_176327_M });
	}

	public static enum EnumPistonType implements IStringSerializable {
		DEFAULT("DEFAULT", 0, "normal"), STICKY("STICKY", 1, "sticky");
		private final String field_176714_c;

		private EnumPistonType(final String p_i45666_1_, final int p_i45666_2_, final String p_i45666_3_) {
			field_176714_c = p_i45666_3_;
		}

		@Override
		public String toString() {
			return field_176714_c;
		}

		@Override
		public String getName() {
			return field_176714_c;
		}
	}

	static final class SwitchEnumFacing {
		static final int[] field_177247_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002036";

		static {
			try {
				field_177247_a[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_177247_a[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_177247_a[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_177247_a[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_177247_a[EnumFacing.WEST.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_177247_a[EnumFacing.EAST.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
