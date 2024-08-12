package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.List;

public class BlockStainedGlassPane extends BlockPane {

public static final int EaZy = 385;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176245_a = PropertyEnum.create("color", EnumDyeColor.class);
	// private static final String __OBFID = "http://https://fuckuskid00000313";

	public BlockStainedGlassPane() {
		super(Material.glass, false);
		setDefaultState(blockState.getBaseState().withProperty(NORTH, false)
				.withProperty(EAST, false).withProperty(SOUTH, false)
				.withProperty(WEST, false).withProperty(field_176245_a, EnumDyeColor.WHITE));
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Get the damage value that this Block should drop
	 */
	@Override
	public int damageDropped(final IBlockState state) {
		return ((EnumDyeColor) state.getValue(field_176245_a)).func_176765_a();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
		for (int var4 = 0; var4 < EnumDyeColor.values().length; ++var4) {
			list.add(new ItemStack(itemIn, 1, var4));
		}
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176245_a, EnumDyeColor.func_176764_b(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((EnumDyeColor) state.getValue(field_176245_a)).func_176765_a();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { NORTH, EAST, WEST, SOUTH, field_176245_a });
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			BlockBeacon.func_176450_d(worldIn, pos);
		}
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (!worldIn.isRemote) {
			BlockBeacon.func_176450_d(worldIn, pos);
		}
	}
}
