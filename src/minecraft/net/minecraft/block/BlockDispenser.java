package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDispenser extends BlockContainer {

public static final int EaZy = 286;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

	/** Registry for all dispense behaviors. */
	public static final RegistryDefaulted dispenseBehaviorRegistry = new RegistryDefaulted(
			new BehaviorDefaultDispenseItem());
	protected Random rand = new Random();
	// private static final String __OBFID = "http://https://fuckuskid00000229";

	protected BlockDispenser() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, false));
		setCreativeTab(CreativeTabs.tabRedstone);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(final World worldIn) {
		return 4;
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		setDefaultDirection(worldIn, pos, state);
	}

	private void setDefaultDirection(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing var4 = (EnumFacing) state.getValue(FACING);
			final boolean var5 = worldIn.getBlockState(pos.offsetNorth()).getBlock().isFullBlock();
			final boolean var6 = worldIn.getBlockState(pos.offsetSouth()).getBlock().isFullBlock();

			if (var4 == EnumFacing.NORTH && var5 && !var6) {
				var4 = EnumFacing.SOUTH;
			} else if (var4 == EnumFacing.SOUTH && var6 && !var5) {
				var4 = EnumFacing.NORTH;
			} else {
				final boolean var7 = worldIn.getBlockState(pos.offsetWest()).getBlock().isFullBlock();
				final boolean var8 = worldIn.getBlockState(pos.offsetEast()).getBlock().isFullBlock();

				if (var4 == EnumFacing.WEST && var7 && !var8) {
					var4 = EnumFacing.EAST;
				} else if (var4 == EnumFacing.EAST && var8 && !var7) {
					var4 = EnumFacing.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, var4).withProperty(TRIGGERED, false),
					2);
		}
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			final TileEntity var9 = worldIn.getTileEntity(pos);

			if (var9 instanceof TileEntityDispenser) {
				playerIn.displayGUIChest((TileEntityDispenser) var9);
			}

			return true;
		}
	}

	protected void func_176439_d(final World worldIn, final BlockPos p_176439_2_) {
		final BlockSourceImpl var3 = new BlockSourceImpl(worldIn, p_176439_2_);
		final TileEntityDispenser var4 = (TileEntityDispenser) var3.getBlockTileEntity();

		if (var4 != null) {
			final int var5 = var4.func_146017_i();

			if (var5 < 0) {
				worldIn.playAuxSFX(1001, p_176439_2_, 0);
			} else {
				final ItemStack var6 = var4.getStackInSlot(var5);
				final IBehaviorDispenseItem var7 = func_149940_a(var6);

				if (var7 != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
					final ItemStack var8 = var7.dispense(var3, var6);
					var4.setInventorySlotContents(var5, var8.stackSize == 0 ? null : var8);
				}
			}
		}
	}

	protected IBehaviorDispenseItem func_149940_a(final ItemStack p_149940_1_) {
		return (IBehaviorDispenseItem) dispenseBehaviorRegistry
				.getObject(p_149940_1_ == null ? null : p_149940_1_.getItem());
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		final boolean var5 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.offsetUp());
		final boolean var6 = ((Boolean) state.getValue(TRIGGERED));

		if (var5 && !var6) {
			worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true), 4);
		} else if (!var5 && var6) {
			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, false), 4);
		}
	}

	@Override
	public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
		if (!worldIn.isRemote) {
			func_176439_d(worldIn, pos);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new TileEntityDispenser();
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return getDefaultState().withProperty(FACING, BlockPistonBase.func_180695_a(worldIn, pos, placer))
				.withProperty(TRIGGERED, false);
	}

	@Override
	public void onBlockPlacedBy(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityLivingBase placer, final ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.func_180695_a(worldIn, pos, placer)), 2);

		if (stack.hasDisplayName()) {
			final TileEntity var6 = worldIn.getTileEntity(pos);

			if (var6 instanceof TileEntityDispenser) {
				((TileEntityDispenser) var6).func_146018_a(stack.getDisplayName());
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		final TileEntity var4 = worldIn.getTileEntity(pos);

		if (var4 instanceof TileEntityDispenser) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityDispenser) var4);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * Get the position where the dispenser at the given Coordinates should
	 * dispense to.
	 */
	public static IPosition getDispensePosition(final IBlockSource coords) {
		final EnumFacing var1 = getFacing(coords.getBlockMetadata());
		final double var2 = coords.getX() + 0.7D * var1.getFrontOffsetX();
		final double var4 = coords.getY() + 0.7D * var1.getFrontOffsetY();
		final double var6 = coords.getZ() + 0.7D * var1.getFrontOffsetZ();
		return new PositionImpl(var2, var4, var6);
	}

	/**
	 * Get the facing of a dispenser with the given metadata
	 */
	public static EnumFacing getFacing(final int meta) {
		return EnumFacing.getFront(meta & 7);
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		return Container.calcRedstoneFromInventory(worldIn.getTileEntity(pos));
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
		return getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(TRIGGERED, (meta & 8) > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		final byte var2 = 0;
		int var3 = var2 | ((EnumFacing) state.getValue(FACING)).getIndex();

		if (((Boolean) state.getValue(TRIGGERED))) {
			var3 |= 8;
		}

		return var3;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { FACING, TRIGGERED });
	}
}
