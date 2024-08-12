package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class BlockRedstoneWire extends Block {

public static final int EaZy = 367;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum NORTH = PropertyEnum.create("north", BlockRedstoneWire.EnumAttachPosition.class);
	public static final PropertyEnum EAST = PropertyEnum.create("east", BlockRedstoneWire.EnumAttachPosition.class);
	public static final PropertyEnum SOUTH = PropertyEnum.create("south", BlockRedstoneWire.EnumAttachPosition.class);
	public static final PropertyEnum WEST = PropertyEnum.create("west", BlockRedstoneWire.EnumAttachPosition.class);
	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	private boolean canProvidePower = true;
	private final Set field_150179_b = Sets.newHashSet();
	// private static final String __OBFID = "http://https://fuckuskid00000295";

	public BlockRedstoneWire() {
		super(Material.circuits);
		setDefaultState(blockState.getBaseState().withProperty(NORTH, BlockRedstoneWire.EnumAttachPosition.NONE)
				.withProperty(EAST, BlockRedstoneWire.EnumAttachPosition.NONE)
				.withProperty(SOUTH, BlockRedstoneWire.EnumAttachPosition.NONE)
				.withProperty(WEST, BlockRedstoneWire.EnumAttachPosition.NONE).withProperty(POWER, 0));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		state = state.withProperty(WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}

	private BlockRedstoneWire.EnumAttachPosition getAttachPosition(final IBlockAccess p_176341_1_,
			final BlockPos p_176341_2_, final EnumFacing p_176341_3_) {
		final BlockPos var4 = p_176341_2_.offset(p_176341_3_);
		final Block var5 = p_176341_1_.getBlockState(p_176341_2_.offset(p_176341_3_)).getBlock();

		if (!func_176343_a(p_176341_1_.getBlockState(var4), p_176341_3_)
				&& (var5.isSolidFullCube() || !func_176346_d(p_176341_1_.getBlockState(var4.offsetDown())))) {
			final Block var6 = p_176341_1_.getBlockState(p_176341_2_.offsetUp()).getBlock();
			return !var6.isSolidFullCube() && var5.isSolidFullCube()
					&& func_176346_d(p_176341_1_.getBlockState(var4.offsetUp()))
							? BlockRedstoneWire.EnumAttachPosition.UP : BlockRedstoneWire.EnumAttachPosition.NONE;
		} else {
			return BlockRedstoneWire.EnumAttachPosition.SIDE;
		}
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

	@Override
	public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
		final IBlockState var4 = worldIn.getBlockState(pos);
		return var4.getBlock() != this ? super.colorMultiplier(worldIn, pos, renderPass)
				: func_176337_b(((Integer) var4.getValue(POWER)));
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return World.doesBlockHaveSolidTopSurface(worldIn, pos.offsetDown())
				|| worldIn.getBlockState(pos.offsetDown()).getBlock() == Blocks.glowstone;
	}

	private IBlockState updateSurroundingRedstone(final World worldIn, final BlockPos p_176338_2_,
			IBlockState p_176338_3_) {
		p_176338_3_ = func_176345_a(worldIn, p_176338_2_, p_176338_2_, p_176338_3_);
		final ArrayList var4 = Lists.newArrayList(field_150179_b);
		field_150179_b.clear();
		final Iterator var5 = var4.iterator();

		while (var5.hasNext()) {
			final BlockPos var6 = (BlockPos) var5.next();
			worldIn.notifyNeighborsOfStateChange(var6, this);
		}

		return p_176338_3_;
	}

	private IBlockState func_176345_a(final World worldIn, final BlockPos p_176345_2_, final BlockPos p_176345_3_,
			IBlockState p_176345_4_) {
		final IBlockState var5 = p_176345_4_;
		final int var6 = ((Integer) p_176345_4_.getValue(POWER));
		final byte var7 = 0;
		int var14 = func_176342_a(worldIn, p_176345_3_, var7);
		canProvidePower = false;
		final int var8 = worldIn.func_175687_A(p_176345_2_);
		canProvidePower = true;

		if (var8 > 0 && var8 > var14 - 1) {
			var14 = var8;
		}

		int var9 = 0;
		final Iterator var10 = EnumFacing.Plane.HORIZONTAL.iterator();

		while (var10.hasNext()) {
			final EnumFacing var11 = (EnumFacing) var10.next();
			final BlockPos var12 = p_176345_2_.offset(var11);
			final boolean var13 = var12.getX() != p_176345_3_.getX() || var12.getZ() != p_176345_3_.getZ();

			if (var13) {
				var9 = func_176342_a(worldIn, var12, var9);
			}

			if (worldIn.getBlockState(var12).getBlock().isNormalCube()
					&& !worldIn.getBlockState(p_176345_2_.offsetUp()).getBlock().isNormalCube()) {
				if (var13 && p_176345_2_.getY() >= p_176345_3_.getY()) {
					var9 = func_176342_a(worldIn, var12.offsetUp(), var9);
				}
			} else if (!worldIn.getBlockState(var12).getBlock().isNormalCube() && var13
					&& p_176345_2_.getY() <= p_176345_3_.getY()) {
				var9 = func_176342_a(worldIn, var12.offsetDown(), var9);
			}
		}

		if (var9 > var14) {
			var14 = var9 - 1;
		} else if (var14 > 0) {
			--var14;
		} else {
			var14 = 0;
		}

		if (var8 > var14 - 1) {
			var14 = var8;
		}

		if (var6 != var14) {
			p_176345_4_ = p_176345_4_.withProperty(POWER, var14);

			if (worldIn.getBlockState(p_176345_2_) == var5) {
				worldIn.setBlockState(p_176345_2_, p_176345_4_, 2);
			}

			field_150179_b.add(p_176345_2_);
			final EnumFacing[] var15 = EnumFacing.values();
			final int var16 = var15.length;

			for (int var17 = 0; var17 < var16; ++var17) {
				final EnumFacing var18 = var15[var17];
				field_150179_b.add(p_176345_2_.offset(var18));
			}
		}

		return p_176345_4_;
	}

	private void func_176344_d(final World worldIn, final BlockPos p_176344_2_) {
		if (worldIn.getBlockState(p_176344_2_).getBlock() == this) {
			worldIn.notifyNeighborsOfStateChange(p_176344_2_, this);
			final EnumFacing[] var3 = EnumFacing.values();
			final int var4 = var3.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				final EnumFacing var6 = var3[var5];
				worldIn.notifyNeighborsOfStateChange(p_176344_2_.offset(var6), this);
			}
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			updateSurroundingRedstone(worldIn, pos, state);
			Iterator var4 = EnumFacing.Plane.VERTICAL.iterator();
			EnumFacing var5;

			while (var4.hasNext()) {
				var5 = (EnumFacing) var4.next();
				worldIn.notifyNeighborsOfStateChange(pos.offset(var5), this);
			}

			var4 = EnumFacing.Plane.HORIZONTAL.iterator();

			while (var4.hasNext()) {
				var5 = (EnumFacing) var4.next();
				func_176344_d(worldIn, pos.offset(var5));
			}

			var4 = EnumFacing.Plane.HORIZONTAL.iterator();

			while (var4.hasNext()) {
				var5 = (EnumFacing) var4.next();
				final BlockPos var6 = pos.offset(var5);

				if (worldIn.getBlockState(var6).getBlock().isNormalCube()) {
					func_176344_d(worldIn, var6.offsetUp());
				} else {
					func_176344_d(worldIn, var6.offsetDown());
				}
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.breakBlock(worldIn, pos, state);

		if (!worldIn.isRemote) {
			final EnumFacing[] var4 = EnumFacing.values();
			final int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				final EnumFacing var7 = var4[var6];
				worldIn.notifyNeighborsOfStateChange(pos.offset(var7), this);
			}

			updateSurroundingRedstone(worldIn, pos, state);
			Iterator var8 = EnumFacing.Plane.HORIZONTAL.iterator();
			EnumFacing var9;

			while (var8.hasNext()) {
				var9 = (EnumFacing) var8.next();
				func_176344_d(worldIn, pos.offset(var9));
			}

			var8 = EnumFacing.Plane.HORIZONTAL.iterator();

			while (var8.hasNext()) {
				var9 = (EnumFacing) var8.next();
				final BlockPos var10 = pos.offset(var9);

				if (worldIn.getBlockState(var10).getBlock().isNormalCube()) {
					func_176344_d(worldIn, var10.offsetUp());
				} else {
					func_176344_d(worldIn, var10.offsetDown());
				}
			}
		}
	}

	private int func_176342_a(final World worldIn, final BlockPos p_176342_2_, final int p_176342_3_) {
		if (worldIn.getBlockState(p_176342_2_).getBlock() != this) {
			return p_176342_3_;
		} else {
			final int var4 = ((Integer) worldIn.getBlockState(p_176342_2_).getValue(POWER));
			return var4 > p_176342_3_ ? var4 : p_176342_3_;
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			if (canPlaceBlockAt(worldIn, pos)) {
				updateSurroundingRedstone(worldIn, pos, state);
			} else {
				dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.redstone;
	}

	@Override
	public int isProvidingStrongPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		return !canProvidePower ? 0 : isProvidingWeakPower(worldIn, pos, state, side);
	}

	@Override
	public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state,
			final EnumFacing side) {
		if (!canProvidePower) {
			return 0;
		} else {
			final int var5 = ((Integer) state.getValue(POWER));

			if (var5 == 0) {
				return 0;
			} else if (side == EnumFacing.UP) {
				return var5;
			} else {
				final EnumSet var6 = EnumSet.noneOf(EnumFacing.class);
				final Iterator var7 = EnumFacing.Plane.HORIZONTAL.iterator();

				while (var7.hasNext()) {
					final EnumFacing var8 = (EnumFacing) var7.next();

					if (func_176339_d(worldIn, pos, var8)) {
						var6.add(var8);
					}
				}

				if (side.getAxis().isHorizontal() && var6.isEmpty()) {
					return var5;
				} else if (var6.contains(side) && !var6.contains(side.rotateYCCW()) && !var6.contains(side.rotateY())) {
					return var5;
				} else {
					return 0;
				}
			}
		}
	}

	private boolean func_176339_d(final IBlockAccess p_176339_1_, final BlockPos p_176339_2_,
			final EnumFacing p_176339_3_) {
		final BlockPos var4 = p_176339_2_.offset(p_176339_3_);
		final IBlockState var5 = p_176339_1_.getBlockState(var4);
		final Block var6 = var5.getBlock();
		final boolean var7 = var6.isNormalCube();
		final boolean var8 = p_176339_1_.getBlockState(p_176339_2_.offsetUp()).getBlock().isNormalCube();
		return !var8 && var7 && func_176340_e(p_176339_1_, var4.offsetUp()) ? true
				: func_176343_a(var5, p_176339_3_) ? true
						: var6 == Blocks.powered_repeater && var5.getValue(BlockDirectional.AGE) == p_176339_3_ ? true
								: !var7 && func_176340_e(p_176339_1_, var4.offsetDown());
	}

	protected static boolean func_176340_e(final IBlockAccess p_176340_0_, final BlockPos p_176340_1_) {
		return func_176346_d(p_176340_0_.getBlockState(p_176340_1_));
	}

	protected static boolean func_176346_d(final IBlockState p_176346_0_) {
		return func_176343_a(p_176346_0_, (EnumFacing) null);
	}

	protected static boolean func_176343_a(final IBlockState p_176343_0_, final EnumFacing p_176343_1_) {
		final Block var2 = p_176343_0_.getBlock();

		if (var2 == Blocks.redstone_wire) {
			return true;
		} else if (Blocks.unpowered_repeater.func_149907_e(var2)) {
			final EnumFacing var3 = (EnumFacing) p_176343_0_.getValue(BlockDirectional.AGE);
			return var3 == p_176343_1_ || var3.getOpposite() == p_176343_1_;
		} else {
			return var2.canProvidePower() && p_176343_1_ != null;
		}
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this
	 * change based on its state.
	 */
	@Override
	public boolean canProvidePower() {
		return canProvidePower;
	}

	private int func_176337_b(final int p_176337_1_) {
		final float var2 = p_176337_1_ / 15.0F;
		float var3 = var2 * 0.6F + 0.4F;

		if (p_176337_1_ == 0) {
			var3 = 0.3F;
		}

		float var4 = var2 * var2 * 0.7F - 0.5F;
		float var5 = var2 * var2 * 0.6F - 0.7F;

		if (var4 < 0.0F) {
			var4 = 0.0F;
		}

		if (var5 < 0.0F) {
			var5 = 0.0F;
		}

		final int var6 = MathHelper.clamp_int((int) (var3 * 255.0F), 0, 255);
		final int var7 = MathHelper.clamp_int((int) (var4 * 255.0F), 0, 255);
		final int var8 = MathHelper.clamp_int((int) (var5 * 255.0F), 0, 255);
		return -16777216 | var6 << 16 | var7 << 8 | var8;
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		final int var5 = ((Integer) state.getValue(POWER));

		if (var5 != 0) {
			final double var6 = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
			final double var8 = pos.getY() + 0.0625F;
			final double var10 = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
			final float var12 = var5 / 15.0F;
			final float var13 = var12 * 0.6F + 0.4F;
			final float var14 = Math.max(0.0F, var12 * var12 * 0.7F - 0.5F);
			final float var15 = Math.max(0.0F, var12 * var12 * 0.6F - 0.7F);
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, var6, var8, var10, var13, var14, var15, new int[0]);
		}
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.redstone;
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
		return getDefaultState().withProperty(POWER, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(POWER));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, POWER });
	}

	static enum EnumAttachPosition implements IStringSerializable {
		UP("UP", 0, "up"), SIDE("SIDE", 1, "side"), NONE("NONE", 2, "none");
		private final String field_176820_d;

		private EnumAttachPosition(final String p_i45689_1_, final int p_i45689_2_, final String p_i45689_3_) {
			field_176820_d = p_i45689_3_;
		}

		@Override
		public String toString() {
			return getName();
		}

		@Override
		public String getName() {
			return field_176820_d;
		}
	}
}
