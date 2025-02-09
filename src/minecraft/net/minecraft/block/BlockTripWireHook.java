package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

import com.google.common.base.Objects;

public class BlockTripWireHook extends Block {

public static final int EaZy = 399;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176264_a = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool field_176263_b = PropertyBool.create("powered");
	public static final PropertyBool field_176265_M = PropertyBool.create("attached");
	public static final PropertyBool field_176266_N = PropertyBool.create("suspended");
	// private static final String __OBFID = "http://https://fuckuskid00000329";

	public BlockTripWireHook() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(field_176264_a, EnumFacing.NORTH)
				.withProperty(field_176263_b, false)
				.withProperty(field_176265_M, false)
				.withProperty(field_176266_N, false));
		setCreativeTab(CreativeTabs.tabRedstone);
		setTickRandomly(true);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		return state.withProperty(field_176266_N, !World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown()));
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
		return side.getAxis().isHorizontal()
				&& worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube();
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		final Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();
		EnumFacing var4;

		do {
			if (!var3.hasNext()) {
				return false;
			}

			var4 = (EnumFacing) var3.next();
		}
		while (!worldIn.getBlockState(pos.offset(var4)).getBlock().isNormalCube());

		return true;
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		IBlockState var9 = getDefaultState().withProperty(field_176263_b, false)
				.withProperty(field_176265_M, false)
				.withProperty(field_176266_N, false);

		if (facing.getAxis().isHorizontal()) {
			var9 = var9.withProperty(field_176264_a, facing);
		}

		return var9;
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		func_176260_a(worldIn, pos, state, false, false, -1, (IBlockState) null);
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (neighborBlock != this) {
			if (func_176261_e(worldIn, pos, state)) {
				final EnumFacing var5 = (EnumFacing) state.getValue(field_176264_a);

				if (!worldIn.getBlockState(pos.offset(var5.getOpposite())).getBlock().isNormalCube()) {
					dropBlockAsItem(worldIn, pos, state, 0);
					worldIn.setBlockToAir(pos);
				}
			}
		}
	}

	public void func_176260_a(final World worldIn, final BlockPos p_176260_2_, final IBlockState p_176260_3_,
			final boolean p_176260_4_, final boolean p_176260_5_, final int p_176260_6_,
			final IBlockState p_176260_7_) {
		final EnumFacing var8 = (EnumFacing) p_176260_3_.getValue(field_176264_a);
		final boolean var9 = ((Boolean) p_176260_3_.getValue(field_176265_M));
		final boolean var10 = ((Boolean) p_176260_3_.getValue(field_176263_b));
		final boolean var11 = !World.doesBlockHaveSolidTopSurface(worldIn, p_176260_2_.offsetDown());
		boolean var12 = !p_176260_4_;
		boolean var13 = false;
		int var14 = 0;
		final IBlockState[] var15 = new IBlockState[42];
		BlockPos var17;

		for (int var16 = 1; var16 < 42; ++var16) {
			var17 = p_176260_2_.offset(var8, var16);
			IBlockState var18 = worldIn.getBlockState(var17);

			if (var18.getBlock() == Blocks.tripwire_hook) {
				if (var18.getValue(field_176264_a) == var8.getOpposite()) {
					var14 = var16;
				}

				break;
			}

			if (var18.getBlock() != Blocks.tripwire && var16 != p_176260_6_) {
				var15[var16] = null;
				var12 = false;
			} else {
				if (var16 == p_176260_6_) {
					var18 = Objects.firstNonNull(p_176260_7_, var18);
				}

				final boolean var19 = !((Boolean) var18.getValue(BlockTripWire.field_176295_N));
				final boolean var20 = ((Boolean) var18.getValue(BlockTripWire.field_176293_a));
				final boolean var21 = ((Boolean) var18.getValue(BlockTripWire.field_176290_b));
				var12 &= var21 == var11;
				var13 |= var19 && var20;
				var15[var16] = var18;

				if (var16 == p_176260_6_) {
					worldIn.scheduleUpdate(p_176260_2_, this, tickRate(worldIn));
					var12 &= var19;
				}
			}
		}

		var12 &= var14 > 1;
		var13 &= var12;
		final IBlockState var22 = getDefaultState().withProperty(field_176265_M, var12)
				.withProperty(field_176263_b, var13);

		if (var14 > 0) {
			var17 = p_176260_2_.offset(var8, var14);
			final EnumFacing var24 = var8.getOpposite();
			worldIn.setBlockState(var17, var22.withProperty(field_176264_a, var24), 3);
			func_176262_b(worldIn, var17, var24);
			func_180694_a(worldIn, var17, var12, var13, var9, var10);
		}

		func_180694_a(worldIn, p_176260_2_, var12, var13, var9, var10);

		if (!p_176260_4_) {
			worldIn.setBlockState(p_176260_2_, var22.withProperty(field_176264_a, var8), 3);

			if (p_176260_5_) {
				func_176262_b(worldIn, p_176260_2_, var8);
			}
		}

		if (var9 != var12) {
			for (int var23 = 1; var23 < var14; ++var23) {
				final BlockPos var25 = p_176260_2_.offset(var8, var23);
				final IBlockState var26 = var15[var23];

				if (var26 != null && worldIn.getBlockState(var25).getBlock() != Blocks.air) {
					worldIn.setBlockState(var25, var26.withProperty(field_176265_M, var12), 3);
				}
			}
		}
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops
	 * to grow, etc.)
	 */
	@Override
	public void randomTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		func_176260_a(worldIn, pos, state, false, true, -1, (IBlockState) null);
	}

	private void func_180694_a(final World worldIn, final BlockPos p_180694_2_, final boolean p_180694_3_,
			final boolean p_180694_4_, final boolean p_180694_5_, final boolean p_180694_6_) {
		if (p_180694_4_ && !p_180694_6_) {
			worldIn.playSoundEffect(p_180694_2_.getX() + 0.5D, p_180694_2_.getY() + 0.1D, p_180694_2_.getZ() + 0.5D,
					"random.click", 0.4F, 0.6F);
		} else if (!p_180694_4_ && p_180694_6_) {
			worldIn.playSoundEffect(p_180694_2_.getX() + 0.5D, p_180694_2_.getY() + 0.1D, p_180694_2_.getZ() + 0.5D,
					"random.click", 0.4F, 0.5F);
		} else if (p_180694_3_ && !p_180694_5_) {
			worldIn.playSoundEffect(p_180694_2_.getX() + 0.5D, p_180694_2_.getY() + 0.1D, p_180694_2_.getZ() + 0.5D,
					"random.click", 0.4F, 0.7F);
		} else if (!p_180694_3_ && p_180694_5_) {
			worldIn.playSoundEffect(p_180694_2_.getX() + 0.5D, p_180694_2_.getY() + 0.1D, p_180694_2_.getZ() + 0.5D,
					"random.bowhit", 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
		}
	}

	private void func_176262_b(final World worldIn, final BlockPos p_176262_2_, final EnumFacing p_176262_3_) {
		worldIn.notifyNeighborsOfStateChange(p_176262_2_, this);
		worldIn.notifyNeighborsOfStateChange(p_176262_2_.offset(p_176262_3_.getOpposite()), this);
	}

	private boolean func_176261_e(final World worldIn, final BlockPos p_176261_2_, final IBlockState p_176261_3_) {
		if (!canPlaceBlockAt(worldIn, p_176261_2_)) {
			dropBlockAsItem(worldIn, p_176261_2_, p_176261_3_, 0);
			worldIn.setBlockToAir(p_176261_2_);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final float var3 = 0.1875F;

		switch (BlockTripWireHook.SwitchEnumFacing.field_177056_a[((EnumFacing) access.getBlockState(pos)
				.getValue(field_176264_a)).ordinal()]) {
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
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final boolean var4 = ((Boolean) state.getValue(field_176265_M));
		final boolean var5 = ((Boolean) state.getValue(field_176263_b));

		if (var4 || var5) {
			func_176260_a(worldIn, pos, state, true, false, -1, (IBlockState) null);
		}

		if (var5) {
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(
					pos.offset(((EnumFacing) state.getValue(field_176264_a)).getOpposite()), this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return ((Boolean) state.getValue(field_176263_b)) ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return !((Boolean) state.getValue(field_176263_b)) ? 0
				: state.getValue(field_176264_a) == side ? 15 : 0;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176264_a, EnumFacing.getHorizontal(meta & 3))
				.withProperty(field_176263_b, (meta & 8) > 0)
				.withProperty(field_176265_M, (meta & 4) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(field_176264_a)).getHorizontalIndex();

		if (((Boolean) state.getValue(field_176263_b))) {
			var3 |= 8;
		}

		if (((Boolean) state.getValue(field_176265_M))) {
			var3 |= 4;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176264_a, field_176263_b, field_176265_M, field_176266_N });
	}

	static final class SwitchEnumFacing {
		static final int[] field_177056_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002050";

		static {
			try {
				field_177056_a[EnumFacing.EAST.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_177056_a[EnumFacing.WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_177056_a[EnumFacing.SOUTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_177056_a[EnumFacing.NORTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
