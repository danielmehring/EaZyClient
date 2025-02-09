package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBanner extends ItemBlock {

public static final int EaZy = 1271;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002181";

	public ItemBanner() {
		super(Blocks.standing_banner);
		maxStackSize = 16;
		setCreativeTab(CreativeTabs.tabDecorations);
		setHasSubtypes(true);
		setMaxDamage(0);
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
			final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (side == EnumFacing.DOWN) {
			return false;
		} else if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
			return false;
		} else {
			pos = pos.offset(side);

			if (!playerIn.func_175151_a(pos, side, stack)) {
				return false;
			} else if (!Blocks.standing_banner.canPlaceBlockAt(worldIn, pos)) {
				return false;
			} else if (worldIn.isRemote) {
				return true;
			} else {
				if (side == EnumFacing.UP) {
					final int var9 = MathHelper.floor_double((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D)
							& 15;
					worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState()
							.withProperty(BlockStandingSign.ROTATION_PROP, var9), 3);
				} else {
					worldIn.setBlockState(pos,
							Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.field_176412_a, side), 3);
				}

				--stack.stackSize;
				final TileEntity var10 = worldIn.getTileEntity(pos);

				if (var10 instanceof TileEntityBanner) {
					((TileEntityBanner) var10).setItemValues(stack);
				}

				return true;
			}
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		String var2 = "item.banner.";
		final EnumDyeColor var3 = func_179225_h(stack);
		var2 = var2 + var3.func_176762_d() + ".name";
		return StatCollector.translateToLocal(var2);
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
		final NBTTagCompound var5 = stack.getSubCompound("BlockEntityTag", false);

		if (var5 != null && var5.hasKey("Patterns")) {
			final NBTTagList var6 = var5.getTagList("Patterns", 10);

			for (int var7 = 0; var7 < var6.tagCount() && var7 < 6; ++var7) {
				final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
				final EnumDyeColor var9 = EnumDyeColor.func_176766_a(var8.getInteger("Color"));
				final TileEntityBanner.EnumBannerPattern var10 = TileEntityBanner.EnumBannerPattern
						.func_177268_a(var8.getString("Pattern"));

				if (var10 != null) {
					tooltip.add(StatCollector
							.translateToLocal("item.banner." + var10.func_177271_a() + "." + var9.func_176762_d()));
				}
			}
		}
	}

	@Override
	public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
		if (renderPass == 0) {
			return 16777215;
		} else {
			final EnumDyeColor var3 = func_179225_h(stack);
			return var3.func_176768_e().colorValue;
		}
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
		final EnumDyeColor[] var4 = EnumDyeColor.values();
		final int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final EnumDyeColor var7 = var4[var6];
			subItems.add(new ItemStack(itemIn, 1, var7.getDyeColorDamage()));
		}
	}

	/**
	 * gets the CreativeTab this item is displayed on
	 */
	@Override
	public CreativeTabs getCreativeTab() {
		return CreativeTabs.tabDecorations;
	}

	private EnumDyeColor func_179225_h(final ItemStack p_179225_1_) {
		final NBTTagCompound var2 = p_179225_1_.getSubCompound("BlockEntityTag", false);
		EnumDyeColor var3 = null;

		if (var2 != null && var2.hasKey("Base")) {
			var3 = EnumDyeColor.func_176766_a(var2.getInteger("Base"));
		} else {
			var3 = EnumDyeColor.func_176766_a(p_179225_1_.getMetadata());
		}

		return var3;
	}
}
