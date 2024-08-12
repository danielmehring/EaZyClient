package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlock extends Item {

public static final int EaZy = 1273;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Block block;
	// private static final String __OBFID = "http://https://fuckuskid00001772";

	public ItemBlock(final Block block) {
		this.block = block;
	}

	/**
	 * Sets the unlocalized name of this item to the string passed as the
	 * parameter, prefixed by "item."
	 */
	@Override
	public ItemBlock setUnlocalizedName(final String unlocalizedName) {
		super.setUnlocalizedName(unlocalizedName);
		return this;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 * 
	 * @param pos
	 *            The block being right-clicked
	 * @param side
	 *            The side being right-clicked
	 */
	@Override
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos,
			EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final IBlockState var9 = worldIn.getBlockState(pos);
		final Block var10 = var9.getBlock();

		if (var10 == Blocks.snow_layer && ((Integer) var9.getValue(BlockSnow.LAYERS_PROP)) < 1) {
			side = EnumFacing.UP;
		} else if (!var10.isReplaceable(worldIn, pos)) {
			pos = pos.offset(side);
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!playerIn.func_175151_a(pos, side, stack)) {
			return false;
		} else if (pos.getY() == 255 && block.getMaterial().isSolid()) {
			return false;
		} else if (worldIn.canBlockBePlaced(block, pos, false, side, (Entity) null, stack)) {
			final int var11 = getMetadata(stack.getMetadata());
			IBlockState var12 = block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, var11, playerIn);

			if (worldIn.setBlockState(pos, var12, 3)) {
				var12 = worldIn.getBlockState(pos);

				if (var12.getBlock() == block) {
					setTileEntityNBT(worldIn, pos, stack);
					block.onBlockPlacedBy(worldIn, pos, var12, playerIn, stack);
				}

				worldIn.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
						block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F,
						block.stepSound.getFrequency() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public static boolean setTileEntityNBT(final World worldIn, final BlockPos p_179224_1_,
			final ItemStack p_179224_2_) {
		if (p_179224_2_.hasTagCompound() && p_179224_2_.getTagCompound().hasKey("BlockEntityTag", 10)) {
			final TileEntity var3 = worldIn.getTileEntity(p_179224_1_);

			if (var3 != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				final NBTTagCompound var5 = (NBTTagCompound) var4.copy();
				var3.writeToNBT(var4);
				final NBTTagCompound var6 = (NBTTagCompound) p_179224_2_.getTagCompound().getTag("BlockEntityTag");
				var4.merge(var6);
				var4.setInteger("x", p_179224_1_.getX());
				var4.setInteger("y", p_179224_1_.getY());
				var4.setInteger("z", p_179224_1_.getZ());

				if (!var4.equals(var5)) {
					var3.readFromNBT(var4);
					var3.markDirty();
					return true;
				}
			}
		}

		return false;
	}

	public boolean canPlaceBlockOnSide(final World worldIn, BlockPos p_179222_2_, EnumFacing p_179222_3_,
			final EntityPlayer p_179222_4_, final ItemStack p_179222_5_) {
		final Block var6 = worldIn.getBlockState(p_179222_2_).getBlock();

		if (var6 == Blocks.snow_layer) {
			p_179222_3_ = EnumFacing.UP;
		} else if (!var6.isReplaceable(worldIn, p_179222_2_)) {
			p_179222_2_ = p_179222_2_.offset(p_179222_3_);
		}

		return worldIn.canBlockBePlaced(block, p_179222_2_, false, p_179222_3_, (Entity) null, p_179222_5_);
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an
	 * ItemStack so different stacks can have different names based on their
	 * damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(final ItemStack stack) {
		return block.getUnlocalizedName();
	}

	/**
	 * Returns the unlocalized name of this item.
	 */
	@Override
	public String getUnlocalizedName() {
		return block.getUnlocalizedName();
	}

	/**
	 * gets the CreativeTab this item is displayed on
	 */
	@Override
	public CreativeTabs getCreativeTab() {
		return block.getCreativeTabToDisplayOn();
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye
	 * returns 16 items)
	 * 
	 * @param subItems
	 *            The List of sub-items. This is a List of ItemStacks.
	 */
	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
		block.getSubBlocks(itemIn, tab, subItems);
	}

	public Block getBlock() {
		return block;
	}

}
