package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

import com.google.common.base.Predicate;

public class BlockTorch extends Block {

public static final int EaZy = 396;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING_PROP = PropertyDirection.create("facing", new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002054";
		public boolean func_176601_a(final EnumFacing p_176601_1_) {
			return p_176601_1_ != EnumFacing.DOWN;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_176601_a((EnumFacing) p_apply_1_);
		}
	});
	// private static final String __OBFID = "http://https://fuckuskid00000325";

	protected BlockTorch() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.UP));
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
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

	private boolean func_176594_d(final World worldIn, final BlockPos p_176594_2_) {
		if (World.doesBlockHaveSolidTopSurface(worldIn, p_176594_2_)) {
			return true;
		} else {
			final Block var3 = worldIn.getBlockState(p_176594_2_).getBlock();
			return var3 instanceof BlockFence || var3 == Blocks.glass || var3 == Blocks.cobblestone_wall
					|| var3 == Blocks.stained_glass;
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		final Iterator var3 = FACING_PROP.getAllowedValues().iterator();
		EnumFacing var4;

		do {
			if (!var3.hasNext()) {
				return false;
			}

			var4 = (EnumFacing) var3.next();
		}
		while (!func_176595_b(worldIn, pos, var4));

		return true;
	}

	private boolean func_176595_b(final World worldIn, final BlockPos p_176595_2_, final EnumFacing p_176595_3_) {
		final BlockPos var4 = p_176595_2_.offset(p_176595_3_.getOpposite());
		final boolean var5 = p_176595_3_.getAxis().isHorizontal();
		return var5 && worldIn.func_175677_d(var4, true)
				|| p_176595_3_.equals(EnumFacing.UP) && func_176594_d(worldIn, var4);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		if (func_176595_b(worldIn, pos, facing)) {
			return getDefaultState().withProperty(FACING_PROP, facing);
		} else {
			final Iterator var9 = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing var10;

			do {
				if (!var9.hasNext()) {
					return getDefaultState();
				}

				var10 = (EnumFacing) var9.next();
			}
			while (!worldIn.func_175677_d(pos.offset(var10.getOpposite()), true));

			return getDefaultState().withProperty(FACING_PROP, var10);
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		func_176593_f(worldIn, pos, state);
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		func_176592_e(worldIn, pos, state);
	}

	protected boolean func_176592_e(final World worldIn, final BlockPos p_176592_2_, final IBlockState p_176592_3_) {
		if (!func_176593_f(worldIn, p_176592_2_, p_176592_3_)) {
			return true;
		} else {
			final EnumFacing var4 = (EnumFacing) p_176592_3_.getValue(FACING_PROP);
			final EnumFacing.Axis var5 = var4.getAxis();
			final EnumFacing var6 = var4.getOpposite();
			boolean var7 = false;

			if (var5.isHorizontal() && !worldIn.func_175677_d(p_176592_2_.offset(var6), true)) {
				var7 = true;
			} else if (var5.isVertical() && !func_176594_d(worldIn, p_176592_2_.offset(var6))) {
				var7 = true;
			}

			if (var7) {
				dropBlockAsItem(worldIn, p_176592_2_, p_176592_3_, 0);
				worldIn.setBlockToAir(p_176592_2_);
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean func_176593_f(final World worldIn, final BlockPos p_176593_2_, final IBlockState p_176593_3_) {
		if (p_176593_3_.getBlock() == this
				&& func_176595_b(worldIn, p_176593_2_, (EnumFacing) p_176593_3_.getValue(FACING_PROP))) {
			return true;
		} else {
			if (worldIn.getBlockState(p_176593_2_).getBlock() == this) {
				dropBlockAsItem(worldIn, p_176593_2_, p_176593_3_, 0);
				worldIn.setBlockToAir(p_176593_2_);
			}

			return false;
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
		final EnumFacing var5 = (EnumFacing) worldIn.getBlockState(pos).getValue(FACING_PROP);
		float var6 = 0.15F;

		if (var5 == EnumFacing.EAST) {
			setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
		} else if (var5 == EnumFacing.WEST) {
			setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
		} else if (var5 == EnumFacing.SOUTH) {
			setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
		} else if (var5 == EnumFacing.NORTH) {
			setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
		} else {
			var6 = 0.1F;
			setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
		}

		return super.collisionRayTrace(worldIn, pos, start, end);
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		final EnumFacing var5 = (EnumFacing) state.getValue(FACING_PROP);
		final double var6 = pos.getX() + 0.5D;
		final double var8 = pos.getY() + 0.7D;
		final double var10 = pos.getZ() + 0.5D;
		final double var12 = 0.22D;
		final double var14 = 0.27D;

		if (var5.getAxis().isHorizontal()) {
			final EnumFacing var16 = var5.getOpposite();
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14 * var16.getFrontOffsetX(), var8 + var12,
					var10 + var14 * var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, var6 + var14 * var16.getFrontOffsetX(), var8 + var12,
					var10 + var14 * var16.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
		} else {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6, var8, var10, 0.0D, 0.0D, 0.0D, new int[0]);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, var6, var8, var10, 0.0D, 0.0D, 0.0D, new int[0]);
		}
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
		IBlockState var2 = getDefaultState();

		switch (meta) {
			case 1:
				var2 = var2.withProperty(FACING_PROP, EnumFacing.EAST);
				break;

			case 2:
				var2 = var2.withProperty(FACING_PROP, EnumFacing.WEST);
				break;

			case 3:
				var2 = var2.withProperty(FACING_PROP, EnumFacing.SOUTH);
				break;

			case 4:
				var2 = var2.withProperty(FACING_PROP, EnumFacing.NORTH);
				break;

			case 5:
			default:
				var2 = var2.withProperty(FACING_PROP, EnumFacing.UP);
		}

		return var2;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3;

		switch (BlockTorch.SwitchEnumFacing.field_176609_a[((EnumFacing) state.getValue(FACING_PROP)).ordinal()]) {
			case 1:
				var3 = var2 | 1;
				break;

			case 2:
				var3 = var2 | 2;
				break;

			case 3:
				var3 = var2 | 3;
				break;

			case 4:
				var3 = var2 | 4;
				break;

			case 5:
			case 6:
			default:
				var3 = var2 | 5;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING_PROP });
	}

	static final class SwitchEnumFacing {
		static final int[] field_176609_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002053";

		static {
			try {
				field_176609_a[EnumFacing.EAST.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_176609_a[EnumFacing.WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_176609_a[EnumFacing.SOUTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_176609_a[EnumFacing.NORTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_176609_a[EnumFacing.DOWN.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_176609_a[EnumFacing.UP.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
