package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCake extends Block {

public static final int EaZy = 269;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
	// private static final String __OBFID = "http://https://fuckuskid00000211";

	protected BlockCake() {
		super(Material.cake);
		setDefaultState(blockState.getBaseState().withProperty(BITES, 0));
		setTickRandomly(true);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
		final float var3 = 0.0625F;
		final float var4 = (1 + ((Integer) access.getBlockState(pos).getValue(BITES)) * 2) / 16.0F;
		final float var5 = 0.5F;
		setBlockBounds(var4, 0.0F, var3, 1.0F - var3, var5, 1.0F - var3);
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender() {
		final float var1 = 0.0625F;
		final float var2 = 0.5F;
		setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
		final float var4 = 0.0625F;
		final float var5 = (1 + ((Integer) state.getValue(BITES)) * 2) / 16.0F;
		final float var6 = 0.5F;
		return new AxisAlignedBB(pos.getX() + var5, pos.getY(), pos.getZ() + var4, pos.getX() + 1 - var4,
				pos.getY() + var6, pos.getZ() + 1 - var4);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
		return getCollisionBoundingBox(worldIn, pos, worldIn.getBlockState(pos));
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
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		eatCake(worldIn, pos, state, playerIn);
		return true;
	}

	@Override
	public void onBlockClicked(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
		eatCake(worldIn, pos, worldIn.getBlockState(pos), playerIn);
	}

	private void eatCake(final World worldIn, final BlockPos p_180682_2_, final IBlockState p_180682_3_,
			final EntityPlayer p_180682_4_) {
		if (p_180682_4_.canEat(false)) {
			p_180682_4_.getFoodStats().addStats(2, 0.1F);
			final int var5 = ((Integer) p_180682_3_.getValue(BITES));

			if (var5 < 6) {
				worldIn.setBlockState(p_180682_2_, p_180682_3_.withProperty(BITES, var5 + 1), 3);
			} else {
				worldIn.setBlockToAir(p_180682_2_);
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!canBlockStay(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		}
	}

	private boolean canBlockStay(final World worldIn, final BlockPos p_176588_2_) {
		return worldIn.getBlockState(p_176588_2_.offsetDown()).getBlock().getMaterial().isSolid();
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return null;
	}

	@Override
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Items.cake;
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
		return getDefaultState().withProperty(BITES, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(BITES));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { BITES });
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		return (7 - ((Integer) worldIn.getBlockState(pos).getValue(BITES))) * 2;
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}
}
