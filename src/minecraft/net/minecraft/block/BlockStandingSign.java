package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockStandingSign extends BlockSign {

public static final int EaZy = 387;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final PropertyInteger ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
	// private static final String __OBFID = "http://https://fuckuskid00002060";

	public BlockStandingSign() {
		setDefaultState(blockState.getBaseState().withProperty(ROTATION_PROP, 0));
	}

	@Override
	public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state,
			final Block neighborBlock) {
		if (!worldIn.getBlockState(pos.offsetDown()).getBlock().getMaterial().isSolid()) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}

		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(final int meta) {
		return getDefaultState().withProperty(ROTATION_PROP, meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(final IBlockState state) {
		return ((Integer) state.getValue(ROTATION_PROP));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { ROTATION_PROP });
	}
}
