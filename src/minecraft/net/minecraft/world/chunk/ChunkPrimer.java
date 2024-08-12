package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer {

public static final int EaZy = 1701;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final short[] data = new short[65536];
	private final IBlockState defaultState;
	// private static final String __OBFID = "http://https://fuckuskid00002007";

	public ChunkPrimer() {
		defaultState = Blocks.air.getDefaultState();
	}

	public IBlockState getBlockState(final int x, final int y, final int z) {
		final int var4 = x << 12 | z << 8 | y;
		return this.getBlockState(var4);
	}

	public IBlockState getBlockState(final int index) {
		if (index >= 0 && index < data.length) {
			final IBlockState var2 = (IBlockState) Block.BLOCK_STATE_IDS.getByValue(data[index]);
			return var2 != null ? var2 : defaultState;
		} else {
			throw new IndexOutOfBoundsException("The coordinate is out of range");
		}
	}

	public void setBlockState(final int x, final int y, final int z, final IBlockState state) {
		final int var5 = x << 12 | z << 8 | y;
		this.setBlockState(var5, state);
	}

	public void setBlockState(final int index, final IBlockState state) {
		if (index >= 0 && index < data.length) {
			data[index] = (short) Block.BLOCK_STATE_IDS.get(state);
		} else {
			throw new IndexOutOfBoundsException("The coordinate is out of range");
		}
	}
}
