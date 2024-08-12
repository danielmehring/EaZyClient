package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSnow extends Block {

public static final int EaZy = 379;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger LAYERS_PROP = PropertyInteger.create("layers", 1, 8);
	// private static final String __OBFID = "http://https://fuckuskid00000309";

	protected BlockSnow() {
		super(Material.snow);
		setDefaultState(blockState.getBaseState().withProperty(LAYERS_PROP, 1));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBoundsForItemRender();
	}

	@Override
	public boolean isPassable(final IBlockAccess blockAccess, final BlockPos pos) {
		return ((Integer) blockAccess.getBlockState(pos).getValue(LAYERS_PROP)) < 5;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		final int var4 = ((Integer) state.getValue(LAYERS_PROP)) - 1;
		final float var5 = 0.125F;
		return new AxisAlignedBB(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX,
				pos.getY() + var4 * var5, pos.getZ() + maxZ);
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
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		getBoundsForLayers(0);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final IBlockState var3 = access.getBlockState(pos);
		getBoundsForLayers(((Integer) var3.getValue(LAYERS_PROP)));
	}

	protected void getBoundsForLayers(final int p_150154_1_) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, p_150154_1_ / 8.0F, 1.0F);
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		final IBlockState var3 = worldIn.getBlockState(pos.offsetDown());
		final Block var4 = var3.getBlock();
		return var4 != Blocks.ice && var4 != Blocks.packed_ice ? var4.getMaterial() == Material.leaves ? true
				: var4 == this && ((Integer) var3.getValue(LAYERS_PROP)) == 7 ? true
						: var4.isOpaqueCube() && var4.blockMaterial.blocksMovement()
				: false;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		checkAndDropBlock(worldIn, pos, state);
	}

	private boolean checkAndDropBlock(final World worldIn, final BlockPos p_176314_2_, final IBlockState p_176314_3_) {
		if (!canPlaceBlockAt(worldIn, p_176314_2_)) {
			dropBlockAsItem(worldIn, p_176314_2_, p_176314_3_, 0);
			worldIn.setBlockToAir(p_176314_2_);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void harvestBlock(final World worldIn, final EntityPlayer playerIn, final BlockPos pos,
			final IBlockState state, final TileEntity te) {
		spawnAsEntity(worldIn, pos,
				new ItemStack(Items.snowball, ((Integer) state.getValue(LAYERS_PROP)) + 1, 0));
		worldIn.setBlockToAir(pos);
		playerIn.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Items.snowball;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
			dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
		return side == EnumFacing.UP ? true : super.shouldSideBeRendered(worldIn, pos, side);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(LAYERS_PROP, (meta & 7) + 1);
	}

	/**
	 * Whether this Block can be replaced directly by other blocks (true for
	 * e.g. tall grass)
	 */
	@Override
	public boolean isReplaceable(final World worldIn, final BlockPos pos) {
		return ((Integer) worldIn.getBlockState(pos).getValue(LAYERS_PROP)) == 1;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(LAYERS_PROP)) - 1;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { LAYERS_PROP });
	}
}
