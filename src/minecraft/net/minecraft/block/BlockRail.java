package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {

public static final int EaZy = 355;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyEnum field_176565_b = PropertyEnum.create("shape",
			BlockRailBase.EnumRailDirection.class);
	// private static final String __OBFID = "http://https://fuckuskid00000293";

	protected BlockRail() {
		super(false);
		setDefaultState(
				blockState.getBaseState().withProperty(field_176565_b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
	}

	@Override
	protected void func_176561_b(final World worldIn, final BlockPos p_176561_2_, final IBlockState p_176561_3_,
			final Block p_176561_4_) {
		if (p_176561_4_.canProvidePower()
				&& new BlockRailBase.Rail(worldIn, p_176561_2_, p_176561_3_).countAdjacentRails() == 3) {
			func_176564_a(worldIn, p_176561_2_, p_176561_3_, false);
		}
	}

	@Override
	public IProperty func_176560_l() {
		return field_176565_b;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(field_176565_b, BlockRailBase.EnumRailDirection.func_177016_a(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((BlockRailBase.EnumRailDirection) state.getValue(field_176565_b)).func_177015_a();
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { field_176565_b });
	}
}
