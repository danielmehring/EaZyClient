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
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import com.google.common.base.Predicate;

public class BlockHopper extends BlockContainer {

public static final int EaZy = 317;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176430_a = PropertyDirection.create("facing", new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002106";
		public boolean func_180180_a(final EnumFacing p_180180_1_) {
			return p_180180_1_ != EnumFacing.UP;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180180_a((EnumFacing) p_apply_1_);
		}
	});
	public static final PropertyBool field_176429_b = PropertyBool.create("enabled");
	// private static final String __OBFID = "http://https://fuckuskid00000257";

	public BlockHopper() {
		super(Material.iron);
		setDefaultState(blockState.getBaseState().withProperty(field_176430_a, EnumFacing.DOWN)
				.withProperty(field_176429_b, true));
		setCreativeTab(CreativeTabs.tabRedstone);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
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
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		final float var7 = 0.125F;
		setBlockBounds(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		EnumFacing var9 = facing.getOpposite();

		if (var9 == EnumFacing.UP) {
			var9 = EnumFacing.DOWN;
		}

		return getDefaultState().withProperty(field_176430_a, var9).withProperty(field_176429_b, true);
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityHopper();
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

		if (stack.hasDisplayName()) {
			final TileEntity var6 = worldIn.getTileEntity(pos);

			if (var6 instanceof TileEntityHopper) {
				((TileEntityHopper) var6).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		func_176427_e(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			final TileEntity var9 = worldIn.getTileEntity(pos);

			if (var9 instanceof TileEntityHopper) {
				playerIn.displayGUIChest((TileEntityHopper) var9);
			}

			return true;
		}
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		func_176427_e(worldIn, pos, state);
	}

	private void func_176427_e(final World worldIn, final BlockPos p_176427_2_, final IBlockState p_176427_3_) {
		final boolean var4 = !worldIn.isBlockPowered(p_176427_2_);

		if (var4 != ((Boolean) p_176427_3_.getValue(field_176429_b))) {
			worldIn.setBlockState(p_176427_2_, p_176427_3_.withProperty(field_176429_b, var4), 4);
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final TileEntity var4 = worldIn.getTileEntity(pos);

		if (var4 instanceof TileEntityHopper) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityHopper) var4);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	public static EnumFacing func_176428_b(final int p_176428_0_) {
		return EnumFacing.getFront(p_176428_0_ & 7);
	}

	/**
	 * Get's the hopper's active status from the 8-bit of the metadata. Note
	 * that the metadata stores whether the block is powered, so this returns
	 * true when that bit is 0.
	 */
	public static boolean getActiveStateFromMetadata(final int p_149917_0_) {
		return (p_149917_0_ & 8) != 8;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		return Container.calcRedstoneFromInventory(worldIn.getTileEntity(pos));
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
		return getDefaultState().withProperty(field_176430_a, func_176428_b(meta)).withProperty(field_176429_b,
                        getActiveStateFromMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(field_176430_a)).getIndex();

		if (!((Boolean) state.getValue(field_176429_b))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176430_a, field_176429_b });
	}
}
