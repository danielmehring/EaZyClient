package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

public class BlockRailDetector extends BlockRailBase {

public static final int EaZy = 357;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176573_b = PropertyEnum.create("shape",
			BlockRailBase.EnumRailDirection.class, new Predicate() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002126";
				public boolean func_180344_a(final BlockRailBase.EnumRailDirection p_180344_1_) {
					return p_180344_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST
							&& p_180344_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST
							&& p_180344_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST
							&& p_180344_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST;
				}

				@Override
				public boolean apply(final Object p_apply_1_) {
					return func_180344_a((BlockRailBase.EnumRailDirection) p_apply_1_);
				}
			});
	public static final PropertyBool field_176574_M = PropertyBool.create("powered");
	// private static final String __OBFID = "http://https://fuckuskid00000225";

	public BlockRailDetector() {
		super(true);
		setDefaultState(blockState.getBaseState().withProperty(field_176574_M, false)
				.withProperty(field_176573_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
		setTickRandomly(true);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 20;
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
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		if (!worldIn.isRemote) {
			if (!((Boolean) state.getValue(field_176574_M))) {
				func_176570_e(worldIn, pos, state);
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
		if (!worldIn.isRemote && ((Boolean) state.getValue(field_176574_M))) {
			func_176570_e(worldIn, pos, state);
		}
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return ((Boolean) state.getValue(field_176574_M)) ? 15 : 0;
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return !((Boolean) state.getValue(field_176574_M)) ? 0 : side == EnumFacing.UP ? 15 : 0;
	}

	private void func_176570_e(final World worldIn, final BlockPos p_176570_2_, final IBlockState p_176570_3_) {
		final boolean var4 = ((Boolean) p_176570_3_.getValue(field_176574_M));
		boolean var5 = false;
		final List var6 = func_176571_a(worldIn, p_176570_2_, EntityMinecart.class, new Predicate[0]);

		if (!var6.isEmpty()) {
			var5 = true;
		}

		if (var5 && !var4) {
			worldIn.setBlockState(p_176570_2_, p_176570_3_.withProperty(field_176574_M, true), 3);
			worldIn.notifyNeighborsOfStateChange(p_176570_2_, this);
			worldIn.notifyNeighborsOfStateChange(p_176570_2_.offsetDown(), this);
			worldIn.markBlockRangeForRenderUpdate(p_176570_2_, p_176570_2_);
		}

		if (!var5 && var4) {
			worldIn.setBlockState(p_176570_2_, p_176570_3_.withProperty(field_176574_M, false), 3);
			worldIn.notifyNeighborsOfStateChange(p_176570_2_, this);
			worldIn.notifyNeighborsOfStateChange(p_176570_2_.offsetDown(), this);
			worldIn.markBlockRangeForRenderUpdate(p_176570_2_, p_176570_2_);
		}

		if (var5) {
			worldIn.scheduleUpdate(p_176570_2_, this, tickRate(worldIn));
		}

		worldIn.updateComparatorOutputLevel(p_176570_2_, this);
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		func_176570_e(worldIn, pos, state);
	}

	@Override
	public IProperty func_176560_l() {
		return field_176573_b;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		if (((Boolean) worldIn.getBlockState(pos).getValue(field_176574_M))) {
			final List var3 = func_176571_a(worldIn, pos, EntityMinecartCommandBlock.class, new Predicate[0]);

			if (!var3.isEmpty()) {
				return ((EntityMinecartCommandBlock) var3.get(0)).func_145822_e().getSuccessCount();
			}

			final List var4 = func_176571_a(worldIn, pos, EntityMinecart.class,
					new Predicate[] { IEntitySelector.selectInventories });

			if (!var4.isEmpty()) {
				return Container.calcRedstoneFromInventory((IInventory) var4.get(0));
			}
		}

		return 0;
	}

	protected List func_176571_a(final World worldIn, final BlockPos p_176571_2_, final Class p_176571_3_,
			final Predicate... p_176571_4_) {
		final AxisAlignedBB var5 = func_176572_a(p_176571_2_);
		return p_176571_4_.length != 1 ? worldIn.getEntitiesWithinAABB(p_176571_3_, var5)
				: worldIn.func_175647_a(p_176571_3_, var5, p_176571_4_[0]);
	}

	private AxisAlignedBB func_176572_a(final BlockPos p_176572_1_) {
		return new AxisAlignedBB(p_176572_1_.getX() + 0.2F, p_176572_1_.getY(), p_176572_1_.getZ() + 0.2F,
				p_176572_1_.getX() + 1 - 0.2F, p_176572_1_.getY() + 1 - 0.2F, p_176572_1_.getZ() + 1 - 0.2F);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176573_b, BlockRailBase.EnumRailDirection.func_177016_a(meta & 7))
				.withProperty(field_176574_M, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((BlockRailBase.EnumRailDirection) state.getValue(field_176573_b)).func_177015_a();

		if (((Boolean) state.getValue(field_176574_M))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176573_b, field_176574_M });
	}
}
