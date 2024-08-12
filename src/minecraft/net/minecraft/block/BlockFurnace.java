package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFurnace extends BlockContainer {

public static final int EaZy = 307;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private final boolean isBurning;
	private static boolean field_149934_M;
	// private static final String __OBFID = "http://https://fuckuskid00000248";

	protected BlockFurnace(final boolean p_i45407_1_) {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		isBurning = p_i45407_1_;
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(Blocks.furnace);
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		func_176445_e(worldIn, pos, state);
	}

	private void func_176445_e(final World worldIn, final BlockPos p_176445_2_, final IBlockState p_176445_3_) {
		if (!worldIn.isRemote) {
			final Block var4 = worldIn.getBlockState(p_176445_2_.offsetNorth()).getBlock();
			final Block var5 = worldIn.getBlockState(p_176445_2_.offsetSouth()).getBlock();
			final Block var6 = worldIn.getBlockState(p_176445_2_.offsetWest()).getBlock();
			final Block var7 = worldIn.getBlockState(p_176445_2_.offsetEast()).getBlock();
			EnumFacing var8 = (EnumFacing) p_176445_3_.getValue(FACING);

			if (var8 == EnumFacing.NORTH && var4.isFullBlock() && !var5.isFullBlock()) {
				var8 = EnumFacing.SOUTH;
			} else if (var8 == EnumFacing.SOUTH && var5.isFullBlock() && !var4.isFullBlock()) {
				var8 = EnumFacing.NORTH;
			} else if (var8 == EnumFacing.WEST && var6.isFullBlock() && !var7.isFullBlock()) {
				var8 = EnumFacing.EAST;
			} else if (var8 == EnumFacing.EAST && var7.isFullBlock() && !var6.isFullBlock()) {
				var8 = EnumFacing.WEST;
			}

			worldIn.setBlockState(p_176445_2_, p_176445_3_.withProperty(FACING, var8), 2);
		}
	}

	@Override
	public void randomDisplayTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (isBurning) {
			final EnumFacing var5 = (EnumFacing) state.getValue(FACING);
			final double var6 = pos.getX() + 0.5D;
			final double var8 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			final double var10 = pos.getZ() + 0.5D;
			final double var12 = 0.52D;
			final double var14 = rand.nextDouble() * 0.6D - 0.3D;

			switch (BlockFurnace.SwitchEnumFacing.field_180356_a[var5.ordinal()]) {
				case 1:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 - var12, var8, var10 + var14, 0.0D, 0.0D,
							0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, var6 - var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;

				case 2:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var12, var8, var10 + var14, 0.0D, 0.0D,
							0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, var6 + var12, var8, var10 + var14, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;

				case 3:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 - var12, 0.0D, 0.0D,
							0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 - var12, 0.0D, 0.0D, 0.0D,
							new int[0]);
					break;

				case 4:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var6 + var14, var8, var10 + var12, 0.0D, 0.0D,
							0.0D, new int[0]);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, var6 + var14, var8, var10 + var12, 0.0D, 0.0D, 0.0D,
							new int[0]);
			}
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			final TileEntity var9 = worldIn.getTileEntity(pos);

			if (var9 instanceof TileEntityFurnace) {
				playerIn.displayGUIChest((TileEntityFurnace) var9);
			}

			return true;
		}
	}

	public static void func_176446_a(final boolean p_176446_0_, final World worldIn, final BlockPos p_176446_2_) {
		final IBlockState var3 = worldIn.getBlockState(p_176446_2_);
		final TileEntity var4 = worldIn.getTileEntity(p_176446_2_);
		field_149934_M = true;

		if (p_176446_0_) {
			worldIn.setBlockState(p_176446_2_,
					Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
			worldIn.setBlockState(p_176446_2_,
					Blocks.lit_furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
		} else {
			worldIn.setBlockState(p_176446_2_,
					Blocks.furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
			worldIn.setBlockState(p_176446_2_,
					Blocks.furnace.getDefaultState().withProperty(FACING, var3.getValue(FACING)), 3);
		}

		field_149934_M = false;

		if (var4 != null) {
			var4.validate();
			worldIn.setTileEntity(p_176446_2_, var4);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityFurnace();
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, placer.func_174811_aO().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.func_174811_aO().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			final TileEntity var6 = worldIn.getTileEntity(pos);

			if (var6 instanceof TileEntityFurnace) {
				((TileEntityFurnace) var6).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!field_149934_M) {
			final TileEntity var4 = worldIn.getTileEntity(pos);

			if (var4 instanceof TileEntityFurnace) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace) var4);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
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
	public Item getItem(final World worldIn, final BlockPos pos) {
		return Item.getItemFromBlock(Blocks.furnace);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	/**
	 * Possibly modify the given BlockState before rendering it on an Entity
	 * (Minecarts, Endermen, ...)
	 */
	@Override
	public IBlockState getStateForEntityRender(final IBlockState state) {
		return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
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

		return getDefaultState().withProperty(FACING, var2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING });
	}

	static final class SwitchEnumFacing {
		static final int[] field_180356_a = new int[EnumFacing.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002111";

		static {
			try {
				field_180356_a[EnumFacing.WEST.ordinal()] = 1;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180356_a[EnumFacing.EAST.ordinal()] = 2;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180356_a[EnumFacing.NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180356_a[EnumFacing.SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
