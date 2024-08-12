package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block {

public static final int EaZy = 265;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing");
	public static final PropertyBool POWERED_PROP = PropertyBool.create("powered");
	private final boolean wooden;
	// private static final String __OBFID = "http://https://fuckuskid00000209";

	protected BlockButton(final boolean wooden) {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH).withProperty(POWERED_PROP, false));
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabRedstone);
		this.wooden = wooden;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		return null;
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return wooden ? 30 : 20;
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
		return worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube();
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		final EnumFacing[] var3 = EnumFacing.values();
		final int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final EnumFacing var6 = var3[var5];

			if (worldIn.getBlockState(pos.offset(var6)).getBlock().isNormalCube()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock().isNormalCube()
				? getDefaultState().withProperty(FACING_PROP, facing).withProperty(POWERED_PROP, false)
				: getDefaultState().withProperty(FACING_PROP, EnumFacing.DOWN).withProperty(POWERED_PROP, false);
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (func_176583_e(worldIn, pos, state)) {
			final EnumFacing var5 = (EnumFacing) state.getValue(FACING_PROP);

			if (!worldIn.getBlockState(pos.offset(var5.getOpposite())).getBlock().isNormalCube()) {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	private boolean func_176583_e(final World worldIn, final BlockPos p_176583_2_, final IBlockState p_176583_3_) {
		if (!canPlaceBlockAt(worldIn, p_176583_2_)) {
			dropBlockAsItem(worldIn, p_176583_2_, p_176583_3_, 0);
			worldIn.setBlockToAir(p_176583_2_);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		func_180681_d(access.getBlockState(pos));
	}

	private void func_180681_d(final IBlockState p_180681_1_) {
		final EnumFacing var2 = (EnumFacing) p_180681_1_.getValue(FACING_PROP);
		final boolean var3 = ((Boolean) p_180681_1_.getValue(POWERED_PROP));
		final float var6 = (var3 ? 1 : 2) / 16.0F;
		switch (BlockButton.SwitchEnumFacing.field_180420_a[var2.ordinal()]) {
			case 1:
				setBlockBounds(0.0F, 0.375F, 0.3125F, var6, 0.625F, 0.6875F);
				break;

			case 2:
				setBlockBounds(1.0F - var6, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
				break;

			case 3:
				setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, var6);
				break;

			case 4:
				setBlockBounds(0.3125F, 0.375F, 1.0F - var6, 0.6875F, 0.625F, 1.0F);
				break;

			case 5:
				setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + var6, 0.625F);
				break;

			case 6:
				setBlockBounds(0.3125F, 1.0F - var6, 0.375F, 0.6875F, 1.0F, 0.625F);
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (((Boolean) state.getValue(POWERED_PROP))) {
			return true;
		} else {
			worldIn.setBlockState(pos, state.withProperty(POWERED_PROP, true), 3);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F,
					0.6F);
			func_176582_b(worldIn, pos, (EnumFacing) state.getValue(FACING_PROP));
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			return true;
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (((Boolean) state.getValue(POWERED_PROP))) {
			func_176582_b(worldIn, pos, (EnumFacing) state.getValue(FACING_PROP));
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return ((Boolean) state.getValue(POWERED_PROP)) ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return !((Boolean) state.getValue(POWERED_PROP)) ? 0
				: state.getValue(FACING_PROP) == side ? 15 : 0;
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
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops
	 * to grow, etc.)
	 */
	@Override
	public void randomTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!worldIn.isRemote) {
			if (((Boolean) state.getValue(POWERED_PROP))) {
				if (wooden) {
					func_180680_f(worldIn, pos, state);
				} else {
					worldIn.setBlockState(pos, state.withProperty(POWERED_PROP, false));
					func_176582_b(worldIn, pos, (EnumFacing) state.getValue(FACING_PROP));
					worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click",
							0.3F, 0.5F);
					worldIn.markBlockRangeForRenderUpdate(pos, pos);
				}
			}
		}
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		final float var1 = 0.1875F;
		final float var2 = 0.125F;
		final float var3 = 0.125F;
		setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		if (!worldIn.isRemote) {
			if (wooden) {
				if (!((Boolean) state.getValue(POWERED_PROP))) {
					func_180680_f(worldIn, pos, state);
				}
			}
		}
	}

	private void func_180680_f(final World worldIn, final BlockPos p_180680_2_, final IBlockState p_180680_3_) {
		func_180681_d(p_180680_3_);
		final List var4 = worldIn.getEntitiesWithinAABB(EntityArrow.class,
				new AxisAlignedBB(p_180680_2_.getX() + minX, p_180680_2_.getY() + minY, p_180680_2_.getZ() + minZ,
						p_180680_2_.getX() + maxX, p_180680_2_.getY() + maxY, p_180680_2_.getZ() + maxZ));
		final boolean var5 = !var4.isEmpty();
		final boolean var6 = ((Boolean) p_180680_3_.getValue(POWERED_PROP));

		if (var5 && !var6) {
			worldIn.setBlockState(p_180680_2_, p_180680_3_.withProperty(POWERED_PROP, true));
			func_176582_b(worldIn, p_180680_2_, (EnumFacing) p_180680_3_.getValue(FACING_PROP));
			worldIn.markBlockRangeForRenderUpdate(p_180680_2_, p_180680_2_);
			worldIn.playSoundEffect(p_180680_2_.getX() + 0.5D, p_180680_2_.getY() + 0.5D, p_180680_2_.getZ() + 0.5D,
					"random.click", 0.3F, 0.6F);
		}

		if (!var5 && var6) {
			worldIn.setBlockState(p_180680_2_, p_180680_3_.withProperty(POWERED_PROP, false));
			func_176582_b(worldIn, p_180680_2_, (EnumFacing) p_180680_3_.getValue(FACING_PROP));
			worldIn.markBlockRangeForRenderUpdate(p_180680_2_, p_180680_2_);
			worldIn.playSoundEffect(p_180680_2_.getX() + 0.5D, p_180680_2_.getY() + 0.5D, p_180680_2_.getZ() + 0.5D,
					"random.click", 0.3F, 0.5F);
		}

		if (var5) {
			worldIn.scheduleUpdate(p_180680_2_, this, tickRate(worldIn));
		}
	}

	private void func_176582_b(final World worldIn, final BlockPos p_176582_2_, final EnumFacing p_176582_3_) {
		worldIn.notifyNeighborsOfStateChange(p_176582_2_, this);
		worldIn.notifyNeighborsOfStateChange(p_176582_2_.offset(p_176582_3_.getOpposite()), this);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		EnumFacing var2;

		switch (meta & 7) {
			case 0:
				var2 = EnumFacing.DOWN;
				break;

			case 1:
				var2 = EnumFacing.EAST;
				break;

			case 2:
				var2 = EnumFacing.WEST;
				break;

			case 3:
				var2 = EnumFacing.SOUTH;
				break;

			case 4:
				var2 = EnumFacing.NORTH;
				break;

			case 5:
			default:
				var2 = EnumFacing.UP;
		}

		return getDefaultState().withProperty(FACING_PROP, var2).withProperty(POWERED_PROP, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		int var2;

		switch (BlockButton.SwitchEnumFacing.field_180420_a[((EnumFacing) state.getValue(FACING_PROP)).ordinal()]) {
			case 1:
				var2 = 1;
				break;

			case 2:
				var2 = 2;
				break;

			case 3:
				var2 = 3;
				break;

			case 4:
				var2 = 4;
				break;

			case 5:
			default:
				var2 = 5;
				break;

			case 6:
				var2 = 0;
		}

		if (((Boolean) state.getValue(POWERED_PROP))) {
			var2 |= 8;
		}

		return var2;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING_PROP, POWERED_PROP });
	}

	static final class SwitchEnumFacing {
		static final int[] field_180420_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002131";

		static {
			try {
				field_180420_a[EnumFacing.EAST.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_180420_a[EnumFacing.WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_180420_a[EnumFacing.SOUTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180420_a[EnumFacing.NORTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180420_a[EnumFacing.UP.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180420_a[EnumFacing.DOWN.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
