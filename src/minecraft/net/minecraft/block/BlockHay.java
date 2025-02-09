package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {

public static final int EaZy = 316;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000256";

	public BlockHay() {
		super(Material.grass);
		setDefaultState(blockState.getBaseState().withProperty(field_176298_M, EnumFacing.Axis.Y));
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		EnumFacing.Axis var2 = EnumFacing.Axis.Y;
		final int var3 = meta & 12;

		if (var3 == 4) {
			var2 = EnumFacing.Axis.X;
		} else if (var3 == 8) {
			var2 = EnumFacing.Axis.Z;
		}

		return getDefaultState().withProperty(field_176298_M, var2);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		int var2 = 0;
		final EnumFacing.Axis var3 = (EnumFacing.Axis) state.getValue(field_176298_M);

		if (var3 == EnumFacing.Axis.X) {
			var2 |= 4;
		} else if (var3 == EnumFacing.Axis.Z) {
			var2 |= 8;
		}

		return var2;
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176298_M });
	}

	@Override
	protected ItemStack createStackedBlock(final IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}

	@Override
	public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX,
			final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
		return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(field_176298_M,
				facing.getAxis());
	}
}
