package net.minecraft.item;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class ItemRecord extends Item {

public static final int EaZy = 1315;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Map field_150928_b = Maps.newHashMap();

	/** The name of the record. */
	public final String recordName;
	// private static final String __OBFID = "http://https://fuckuskid00000057";

	protected ItemRecord(final String p_i45350_1_) {
		recordName = p_i45350_1_;
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabMisc);
		field_150928_b.put("records." + p_i45350_1_, this);
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
	public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn,
			final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		final IBlockState var9 = worldIn.getBlockState(pos);

		if (var9.getBlock() == Blocks.jukebox && !((Boolean) var9.getValue(BlockJukebox.HAS_RECORD))) {
			if (worldIn.isRemote) {
				return true;
			} else {
				((BlockJukebox) Blocks.jukebox).insertRecord(worldIn, pos, var9, stack);
				worldIn.playAuxSFXAtEntity((EntityPlayer) null, 1005, pos, Item.getIdFromItem(this));
				--stack.stackSize;
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover
	 * description
	 * 
	 * @param tooltip
	 *            All lines to display in the Item's tooltip. This is a List of
	 *            Strings.
	 * @param advanced
	 *            Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip,
			final boolean advanced) {
		tooltip.add(getRecordNameLocal());
	}

	public String getRecordNameLocal() {
		return StatCollector.translateToLocal("item.record." + recordName + ".desc");
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(final ItemStack stack) {
		return EnumRarity.RARE;
	}

	/**
	 * Return the record item corresponding to the given name.
	 */
	public static ItemRecord getRecord(final String p_150926_0_) {
		return (ItemRecord) field_150928_b.get(p_150926_0_);
	}
}
