package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemLead;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockFence extends Block {

public static final int EaZy = 302;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Whether this fence connects in the northern direction */
	public static final PropertyBool NORTH = PropertyBool.create("north");

	/** Whether this fence connects in the eastern direction */
	public static final PropertyBool EAST = PropertyBool.create("east");

	/** Whether this fence connects in the southern direction */
	public static final PropertyBool SOUTH = PropertyBool.create("south");

	/** Whether this fence connects in the western direction */
	public static final PropertyBool WEST = PropertyBool.create("west");
	// private static final String __OBFID = "http://https://fuckuskid00000242";

	public BlockFence(final Material p_i45721_1_) {
		super(p_i45721_1_);
		setDefaultState(blockState.getBaseState().withProperty(NORTH, false)
				.withProperty(EAST, false).withProperty(SOUTH, false)
				.withProperty(WEST, false));
		setCreativeTab(CreativeTabs.tabDecorations);
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
		final boolean var7 = func_176524_e(worldIn, pos.offsetNorth());
		final boolean var8 = func_176524_e(worldIn, pos.offsetSouth());
		final boolean var9 = func_176524_e(worldIn, pos.offsetWest());
		final boolean var10 = func_176524_e(worldIn, pos.offsetEast());
		float var11 = 0.375F;
		float var12 = 0.625F;
		float var13 = 0.375F;
		float var14 = 0.625F;

		if (var7) {
			var13 = 0.0F;
		}

		if (var8) {
			var14 = 1.0F;
		}

		if (var7 || var8) {
			setBlockBounds(var11, 0.0F, var13, var12, 1.5F, var14);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}

		var13 = 0.375F;
		var14 = 0.625F;

		if (var9) {
			var11 = 0.0F;
		}

		if (var10) {
			var12 = 1.0F;
		}

		if (var9 || var10 || !var7 && !var8) {
			setBlockBounds(var11, 0.0F, var13, var12, 1.5F, var14);
			super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
		}

		if (var7) {
			var13 = 0.0F;
		}

		if (var8) {
			var14 = 1.0F;
		}

		setBlockBounds(var11, 0.0F, var13, var12, 1.0F, var14);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final boolean var3 = func_176524_e(access, pos.offsetNorth());
		final boolean var4 = func_176524_e(access, pos.offsetSouth());
		final boolean var5 = func_176524_e(access, pos.offsetWest());
		final boolean var6 = func_176524_e(access, pos.offsetEast());
		float var7 = 0.375F;
		float var8 = 0.625F;
		float var9 = 0.375F;
		float var10 = 0.625F;

		if (var3) {
			var9 = 0.0F;
		}

		if (var4) {
			var10 = 1.0F;
		}

		if (var5) {
			var7 = 0.0F;
		}

		if (var6) {
			var8 = 1.0F;
		}

		setBlockBounds(var7, 0.0F, var9, var8, 1.0F, var10);
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
		return false;
	}

	public boolean func_176524_e(final IBlockAccess p_176524_1_, final BlockPos p_176524_2_) {
		final Block var3 = p_176524_1_.getBlockState(p_176524_2_).getBlock();
		return var3 == Blocks.barrier ? false
				: (!(var3 instanceof BlockFence) || var3.blockMaterial != blockMaterial)
						&& !(var3 instanceof BlockFenceGate)
								? var3.blockMaterial.isOpaque() && var3.isFullCube()
										? var3.blockMaterial != Material.gourd : false
								: true;
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return true;
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		return worldIn.isRemote ? true : ItemLead.func_180618_a(playerIn, worldIn, pos);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return 0;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
		return state.withProperty(NORTH, func_176524_e(worldIn, pos.offsetNorth()))
				.withProperty(EAST, func_176524_e(worldIn, pos.offsetEast()))
				.withProperty(SOUTH, func_176524_e(worldIn, pos.offsetSouth()))
				.withProperty(WEST, func_176524_e(worldIn, pos.offsetWest()));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { NORTH, EAST, WEST, SOUTH });
	}
}
