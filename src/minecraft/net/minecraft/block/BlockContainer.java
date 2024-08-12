package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {

public static final int EaZy = 280;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000193";

	protected BlockContainer(final Material materialIn) {
		super(materialIn);
		isBlockContainer = true;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	/**
	 * Called on both Client and Server when World#addBlockEvent is called
	 */
	@Override
	public boolean onBlockEventReceived(final World worldIn, final BlockPos pos, final IBlockState state,
			final int eventID, final int eventParam) {
		super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
		final TileEntity var6 = worldIn.getTileEntity(pos);
		return var6 == null ? false : var6.receiveClientEvent(eventID, eventParam);
	}
}
