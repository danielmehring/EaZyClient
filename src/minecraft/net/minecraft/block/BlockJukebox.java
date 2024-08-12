package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {

public static final int EaZy = 320;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");
	// private static final String __OBFID = "http://https://fuckuskid00000260";

	protected BlockJukebox() {
		super(Material.wood);
		setDefaultState(blockState.getBaseState().withProperty(HAS_RECORD, false));
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (((Boolean) state.getValue(HAS_RECORD))) {
			dropRecord(worldIn, pos, state);
			state = state.withProperty(HAS_RECORD, false);
			worldIn.setBlockState(pos, state, 2);
			return true;
		} else {
			return false;
		}
	}

	public void insertRecord(final World worldIn, final BlockPos pos, final IBlockState state,
			final ItemStack recordStack) {
		if (!worldIn.isRemote) {
			final TileEntity var5 = worldIn.getTileEntity(pos);

			if (var5 instanceof BlockJukebox.TileEntityJukebox) {
				((BlockJukebox.TileEntityJukebox) var5)
						.setRecord(new ItemStack(recordStack.getItem(), 1, recordStack.getMetadata()));
				worldIn.setBlockState(pos, state.withProperty(HAS_RECORD, true), 2);
			}
		}
	}

	private void dropRecord(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			final TileEntity var4 = worldIn.getTileEntity(pos);

			if (var4 instanceof BlockJukebox.TileEntityJukebox) {
				final BlockJukebox.TileEntityJukebox var5 = (BlockJukebox.TileEntityJukebox) var4;
				final ItemStack var6 = var5.getRecord();

				if (var6 != null) {
					worldIn.playAuxSFX(1005, pos, 0);
					worldIn.func_175717_a(pos, (String) null);
					var5.setRecord((ItemStack) null);
					final float var7 = 0.7F;
					final double var8 = worldIn.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					final double var10 = worldIn.rand.nextFloat() * var7 + (1.0F - var7) * 0.2D + 0.6D;
					final double var12 = worldIn.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
					final ItemStack var14 = var6.copy();
					final EntityItem var15 = new EntityItem(worldIn, pos.getX() + var8, pos.getY() + var10,
							pos.getZ() + var12, var14);
					var15.setDefaultPickupDelay();
					worldIn.spawnEntityInWorld(var15);
				}
			}
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		dropRecord(worldIn, pos, state);
		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {
		if (!worldIn.isRemote) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing
	 * the block.
	 */
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new BlockJukebox.TileEntityJukebox();
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(final World worldIn, final BlockPos pos) {
		final TileEntity var3 = worldIn.getTileEntity(pos);

		if (var3 instanceof BlockJukebox.TileEntityJukebox) {
			final ItemStack var4 = ((BlockJukebox.TileEntityJukebox) var3).getRecord();

			if (var4 != null) {
				return Item.getIdFromItem(var4.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
			}
		}

		return 0;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 3;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(HAS_RECORD, meta > 0);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Boolean) state.getValue(HAS_RECORD)) ? 1 : 0;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { HAS_RECORD });
	}

	public static class TileEntityJukebox extends TileEntity {
		private ItemStack record;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000261";

		@Override
		public void readFromNBT(final NBTTagCompound compound) {
			super.readFromNBT(compound);

			if (compound.hasKey("RecordItem", 10)) {
				setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
			} else if (compound.getInteger("Record") > 0) {
				setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record")), 1, 0));
			}
		}

		@Override
		public void writeToNBT(final NBTTagCompound compound) {
			super.writeToNBT(compound);

			if (getRecord() != null) {
				compound.setTag("RecordItem", getRecord().writeToNBT(new NBTTagCompound()));
			}
		}

		public ItemStack getRecord() {
			return record;
		}

		public void setRecord(final ItemStack recordStack) {
			record = recordStack;
			markDirty();
		}
	}
}
