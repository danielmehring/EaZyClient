package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEnderChest extends BlockContainer {

public static final int EaZy = 296;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection field_176437_a = PropertyDirection.create("facing",
			EnumFacing.Plane.HORIZONTAL);
	// private static final String __OBFID = "http://https://fuckuskid00000238";

	protected BlockEnderChest() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(field_176437_a, EnumFacing.NORTH));
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 2;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(Blocks.obsidian);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 8;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(field_176437_a, placer.func_174811_aO().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(field_176437_a, placer.func_174811_aO().getOpposite()), 2);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final InventoryEnderChest var9 = playerIn.getInventoryEnderChest();
		final TileEntity var10 = worldIn.getTileEntity(pos);

		if (var9 != null && var10 instanceof TileEntityEnderChest) {
			if (worldIn.getBlockState(pos.offsetUp()).getBlock().isNormalCube()) {
				return true;
			} else if (worldIn.isRemote) {
				return true;
			} else {
				var9.setChestTileEntity((TileEntityEnderChest) var10);
				playerIn.displayGUIChest(var9);
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityEnderChest();
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		for (int var5 = 0; var5 < 3; ++var5) {
			final int var6 = rand.nextInt(2) * 2 - 1;
			final int var7 = rand.nextInt(2) * 2 - 1;
			final double var8 = pos.getX() + 0.5D + 0.25D * var6;
			final double var10 = pos.getY() + rand.nextFloat();
			final double var12 = pos.getZ() + 0.5D + 0.25D * var7;
			final double var14 = rand.nextFloat() * var6;
			final double var16 = (rand.nextFloat() - 0.5D) * 0.125D;
			final double var18 = rand.nextFloat() * var7;
			worldIn.spawnParticle(EnumParticleTypes.PORTAL, var8, var10, var12, var14, var16, var18, new int[0]);
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		EnumFacing var2 = EnumFacing.getFront(meta);

		if (var2.getAxis() == EnumFacing.Axis.Y) {
			var2 = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(field_176437_a, var2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumFacing) state.getValue(field_176437_a)).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176437_a });
	}
}
