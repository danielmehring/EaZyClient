package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPistonBase extends Block {

public static final int EaZy = 344;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool EXTENDED = PropertyBool.create("extended");

	/** This piston is the sticky one? */
	private final boolean isSticky;
	// private static final String __OBFID = "http://https://fuckuskid00000366";

	public BlockPistonBase(final boolean p_i45443_1_) {
		super(Material.piston);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXTENDED, false));
		isSticky = p_i45443_1_;
		setStepSound(soundTypePiston);
		setHardness(0.5F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, func_180695_a(worldIn, pos, placer)), 2);

		if (!worldIn.isRemote) {
			func_176316_e(worldIn, pos, state);
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.isRemote) {
			func_176316_e(worldIn, pos, state);
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
			func_176316_e(worldIn, pos, state);
		}
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, func_180695_a(worldIn, pos, placer)).withProperty(EXTENDED, false);
	}

	private void func_176316_e(final World worldIn, final BlockPos p_176316_2_, final IBlockState p_176316_3_) {
		final EnumFacing var4 = (EnumFacing) p_176316_3_.getValue(FACING);
		final boolean var5 = func_176318_b(worldIn, p_176316_2_, var4);

		if (var5 && !((Boolean) p_176316_3_.getValue(EXTENDED))) {
			if (new BlockPistonStructureHelper(worldIn, p_176316_2_, var4, true).func_177253_a()) {
				worldIn.addBlockEvent(p_176316_2_, this, 0, var4.getIndex());
			}
		} else if (!var5 && ((Boolean) p_176316_3_.getValue(EXTENDED))) {
			worldIn.setBlockState(p_176316_2_, p_176316_3_.withProperty(EXTENDED, false), 2);
			worldIn.addBlockEvent(p_176316_2_, this, 1, var4.getIndex());
		}
	}

	private boolean func_176318_b(final World worldIn, final BlockPos p_176318_2_, final EnumFacing p_176318_3_) {
		final EnumFacing[] var4 = EnumFacing.values();
		final int var5 = var4.length;
		int var6;

		for (var6 = 0; var6 < var5; ++var6) {
			final EnumFacing var7 = var4[var6];

			if (var7 != p_176318_3_ && worldIn.func_175709_b(p_176318_2_.offset(var7), var7)) {
				return true;
			}
		}

		if (worldIn.func_175709_b(p_176318_2_, EnumFacing.NORTH)) {
			return true;
		} else {
			final BlockPos var9 = p_176318_2_.offsetUp();
			final EnumFacing[] var10 = EnumFacing.values();
			var6 = var10.length;

			for (int var11 = 0; var11 < var6; ++var11) {
				final EnumFacing var8 = var10[var11];

				if (var8 != EnumFacing.DOWN && worldIn.func_175709_b(var9.offset(var8), var8)) {
					return true;
				}
			}

			return false;
		}
	}

	/**
	 * Called on both Client and Server when World#addBlockEvent is called
	 */
	@Override
	public boolean onBlockEventReceived(final World worldIn, final BlockPos pos, final IBlockState state,
			final int eventID, final int eventParam) {
		final EnumFacing var6 = (EnumFacing) state.getValue(FACING);

		if (!worldIn.isRemote) {
			final boolean var7 = func_176318_b(worldIn, pos, var6);

			if (var7 && eventID == 1) {
				worldIn.setBlockState(pos, state.withProperty(EXTENDED, true), 2);
				return false;
			}

			if (!var7 && eventID == 0) {
				return false;
			}
		}

		if (eventID == 0) {
			if (!func_176319_a(worldIn, pos, var6, true)) {
				return false;
			}

			worldIn.setBlockState(pos, state.withProperty(EXTENDED, true), 2);
			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.out", 0.5F,
					worldIn.rand.nextFloat() * 0.25F + 0.6F);
		} else if (eventID == 1) {
			final TileEntity var13 = worldIn.getTileEntity(pos.offset(var6));

			if (var13 instanceof TileEntityPiston) {
				((TileEntityPiston) var13).clearPistonTileEntity();
			}

			worldIn.setBlockState(pos, Blocks.piston_extension.getDefaultState()
					.withProperty(BlockPistonMoving.field_176426_a, var6)
					.withProperty(BlockPistonMoving.field_176425_b, isSticky
							? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT),
					3);
			worldIn.setTileEntity(pos,
					BlockPistonMoving.func_176423_a(getStateFromMeta(eventParam), var6, false, true));

			if (isSticky) {
				final BlockPos var8 = pos.add(var6.getFrontOffsetX() * 2, var6.getFrontOffsetY() * 2,
						var6.getFrontOffsetZ() * 2);
				final Block var9 = worldIn.getBlockState(var8).getBlock();
				boolean var10 = false;

				if (var9 == Blocks.piston_extension) {
					final TileEntity var11 = worldIn.getTileEntity(var8);

					if (var11 instanceof TileEntityPiston) {
						final TileEntityPiston var12 = (TileEntityPiston) var11;

						if (var12.func_174930_e() == var6 && var12.isExtending()) {
							var12.clearPistonTileEntity();
							var10 = true;
						}
					}
				}

				if (!var10 && var9.getMaterial() != Material.air
						&& func_180696_a(var9, worldIn, var8, var6.getOpposite(), false)
						&& (var9.getMobilityFlag() == 0 || var9 == Blocks.piston || var9 == Blocks.sticky_piston)) {
					func_176319_a(worldIn, pos, var6, false);
				}
			} else {
				worldIn.setBlockToAir(pos.offset(var6));
			}

			worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.in", 0.5F,
					worldIn.rand.nextFloat() * 0.15F + 0.6F);
		}

		return true;
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final IBlockState var3 = access.getBlockState(pos);

		if (var3.getBlock() == this && ((Boolean) var3.getValue(EXTENDED))) {
			final EnumFacing var5 = (EnumFacing) var3.getValue(FACING);

			if (var5 != null) {
				switch (BlockPistonBase.SwitchEnumFacing.field_177243_a[var5.ordinal()]) {
					case 1:
						setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
						break;

					case 2:
						setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
						break;

					case 3:
						setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
						break;

					case 4:
						setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
						break;

					case 5:
						setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
						break;

					case 6:
						setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
				}
			}
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		setBlockBoundsBasedOnState(worldIn, pos);
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	public static EnumFacing func_176317_b(final int p_176317_0_) {
		final int var1 = p_176317_0_ & 7;
		return var1 > 5 ? null : EnumFacing.getFront(var1);
	}

	public static EnumFacing func_180695_a(final World worldIn, final BlockPos p_180695_1_,
			final EntityLivingBase p_180695_2_) {
		if (MathHelper.abs((float) p_180695_2_.posX - p_180695_1_.getX()) < 2.0F
				&& MathHelper.abs((float) p_180695_2_.posZ - p_180695_1_.getZ()) < 2.0F) {
			final double var3 = p_180695_2_.posY + p_180695_2_.getEyeHeight();

			if (var3 - p_180695_1_.getY() > 2.0D) {
				return EnumFacing.UP;
			}

			if (p_180695_1_.getY() - var3 > 0.0D) {
				return EnumFacing.DOWN;
			}
		}

		return p_180695_2_.func_174811_aO().getOpposite();
	}

	public static boolean func_180696_a(final Block p_180696_0_, final World worldIn, final BlockPos p_180696_2_,
			final EnumFacing p_180696_3_, final boolean p_180696_4_) {
		if (p_180696_0_ == Blocks.obsidian) {
			return false;
		} else if (!worldIn.getWorldBorder().contains(p_180696_2_)) {
			return false;
		} else if (p_180696_2_.getY() >= 0 && (p_180696_3_ != EnumFacing.DOWN || p_180696_2_.getY() != 0)) {
			if (p_180696_2_.getY() <= worldIn.getHeight() - 1
					&& (p_180696_3_ != EnumFacing.UP || p_180696_2_.getY() != worldIn.getHeight() - 1)) {
				if (p_180696_0_ != Blocks.piston && p_180696_0_ != Blocks.sticky_piston) {
					if (p_180696_0_.getBlockHardness(worldIn, p_180696_2_) == -1.0F) {
						return false;
					}

					if (p_180696_0_.getMobilityFlag() == 2) {
						return false;
					}

					if (p_180696_0_.getMobilityFlag() == 1) {
						return p_180696_4_;
					}
				} else if (((Boolean) worldIn.getBlockState(p_180696_2_).getValue(EXTENDED))) {
					return false;
				}

				return !(p_180696_0_ instanceof ITileEntityProvider);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean func_176319_a(final World worldIn, final BlockPos p_176319_2_, final EnumFacing p_176319_3_,
			final boolean p_176319_4_) {
		if (!p_176319_4_) {
			worldIn.setBlockToAir(p_176319_2_.offset(p_176319_3_));
		}

		final BlockPistonStructureHelper var5 = new BlockPistonStructureHelper(worldIn, p_176319_2_, p_176319_3_,
				p_176319_4_);
		final List var6 = var5.func_177254_c();
		final List var7 = var5.func_177252_d();

		if (!var5.func_177253_a()) {
			return false;
		} else {
			int var8 = var6.size() + var7.size();
			final Block[] var9 = new Block[var8];
			final EnumFacing var10 = p_176319_4_ ? p_176319_3_ : p_176319_3_.getOpposite();
			int var11;
			BlockPos var12;

			for (var11 = var7.size() - 1; var11 >= 0; --var11) {
				var12 = (BlockPos) var7.get(var11);
				final Block var13 = worldIn.getBlockState(var12).getBlock();
				var13.dropBlockAsItem(worldIn, var12, worldIn.getBlockState(var12), 0);
				worldIn.setBlockToAir(var12);
				--var8;
				var9[var8] = var13;
			}

			IBlockState var19;

			for (var11 = var6.size() - 1; var11 >= 0; --var11) {
				var12 = (BlockPos) var6.get(var11);
				var19 = worldIn.getBlockState(var12);
				final Block var14 = var19.getBlock();
				var14.getMetaFromState(var19);
				worldIn.setBlockToAir(var12);
				var12 = var12.offset(var10);
				worldIn.setBlockState(var12,
						Blocks.piston_extension.getDefaultState().withProperty(FACING, p_176319_3_), 4);
				worldIn.setTileEntity(var12, BlockPistonMoving.func_176423_a(var19, p_176319_3_, p_176319_4_, false));
				--var8;
				var9[var8] = var14;
			}

			final BlockPos var16 = p_176319_2_.offset(p_176319_3_);

			if (p_176319_4_) {
				final BlockPistonExtension.EnumPistonType var17 = isSticky ? BlockPistonExtension.EnumPistonType.STICKY
						: BlockPistonExtension.EnumPistonType.DEFAULT;
				var19 = Blocks.piston_head.getDefaultState()
						.withProperty(BlockPistonExtension.field_176326_a, p_176319_3_)
						.withProperty(BlockPistonExtension.field_176325_b, var17);
				final IBlockState var20 = Blocks.piston_extension.getDefaultState()
						.withProperty(BlockPistonMoving.field_176426_a, p_176319_3_).withProperty(
								BlockPistonMoving.field_176425_b, isSticky ? BlockPistonExtension.EnumPistonType.STICKY
										: BlockPistonExtension.EnumPistonType.DEFAULT);
				worldIn.setBlockState(var16, var20, 4);
				worldIn.setTileEntity(var16, BlockPistonMoving.func_176423_a(var19, p_176319_3_, true, false));
			}

			int var18;

			for (var18 = var7.size() - 1; var18 >= 0; --var18) {
				worldIn.notifyNeighborsOfStateChange((BlockPos) var7.get(var18), var9[var8++]);
			}

			for (var18 = var6.size() - 1; var18 >= 0; --var18) {
				worldIn.notifyNeighborsOfStateChange((BlockPos) var6.get(var18), var9[var8++]);
			}

			if (p_176319_4_) {
				worldIn.notifyNeighborsOfStateChange(var16, Blocks.piston_head);
				worldIn.notifyNeighborsOfStateChange(p_176319_2_, this);
			}

			return true;
		}
	}

	/**
	 * Possibly modify the given BlockState before rendering it on an Entity
	 * (Minecarts, Endermen, ...)
	 */
	@Override
	public IBlockState getStateForEntityRender(final IBlockState state) {
		return getDefaultState().withProperty(FACING, EnumFacing.UP);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(FACING, func_176317_b(meta)).withProperty(EXTENDED, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(FACING)).getIndex();

		if (((Boolean) state.getValue(EXTENDED))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, EXTENDED });
	}

	static final class SwitchEnumFacing {
		static final int[] field_177243_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002037";

		static {
			try {
				field_177243_a[EnumFacing.DOWN.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_177243_a[EnumFacing.UP.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_177243_a[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_177243_a[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_177243_a[EnumFacing.WEST.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_177243_a[EnumFacing.EAST.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
