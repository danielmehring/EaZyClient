package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;

import java.util.List;

import optifine.Reflector;

public class ExtendedBlockStorage {

public static final int EaZy = 1709;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Contains the bottom-most Y block represented by this
	 * ExtendedBlockStorage. Typically a multiple of 16.
	 */
	private final int yBase;

	/**
	 * A total count of the number of non-air blocks in this block storage's
	 * Chunk.
	 */
	private int blockRefCount;

	/**
	 * Contains the number of blocks in this block storage's parent chunk that
	 * require random ticking. Used to cull the Chunk from random tick updates
	 * for performance reasons.
	 */
	private int tickRefCount;
	private char[] data;

	/** The NibbleArray containing a block of Block-light data. */
	private NibbleArray blocklightArray;

	/** The NibbleArray containing a block of Sky-light data. */
	private NibbleArray skylightArray;
	// private static final String __OBFID = "http://https://fuckuskid00000375";

	public ExtendedBlockStorage(final int y, final boolean storeSkylight) {
		yBase = y;
		data = new char[4096];
		blocklightArray = new NibbleArray();

		if (storeSkylight) {
			skylightArray = new NibbleArray();
		}
	}

	public IBlockState get(final int x, final int y, final int z) {
		final IBlockState var4 = (IBlockState) Block.BLOCK_STATE_IDS.getByValue(data[y << 8 | z << 4 | x]);
		return var4 != null ? var4 : Blocks.air.getDefaultState();
	}

	public void set(final int x, final int y, final int z, IBlockState state) {
		if (Reflector.IExtendedBlockState.isInstance(state)) {
			state = (IBlockState) Reflector.call(state, Reflector.IExtendedBlockState_getClean, new Object[0]);
		}

		final IBlockState var5 = get(x, y, z);
		final Block var6 = var5.getBlock();
		final Block var7 = state.getBlock();

		if (var6 != Blocks.air) {
			--blockRefCount;

			if (var6.getTickRandomly()) {
				--tickRefCount;
			}
		}

		if (var7 != Blocks.air) {
			++blockRefCount;

			if (var7.getTickRandomly()) {
				++tickRefCount;
			}
		}

		data[y << 8 | z << 4 | x] = (char) Block.BLOCK_STATE_IDS.get(state);
	}

	/**
	 * Returns the block for a location in a chunk, with the extended ID merged
	 * from a byte array and a NibbleArray to form a full 12-bit block ID.
	 */
	public Block getBlockByExtId(final int x, final int y, final int z) {
		return get(x, y, z).getBlock();
	}

	/**
	 * Returns the metadata associated with the block at the given coordinates
	 * in this ExtendedBlockStorage.
	 */
	public int getExtBlockMetadata(final int x, final int y, final int z) {
		final IBlockState var4 = get(x, y, z);
		return var4.getBlock().getMetaFromState(var4);
	}

	/**
	 * Returns whether or not this block storage's Chunk is fully empty, based
	 * on its internal reference count.
	 */
	public boolean isEmpty() {
		return blockRefCount == 0;
	}

	/**
	 * Returns whether or not this block storage's Chunk will require random
	 * ticking, used to avoid looping through random block ticks when there are
	 * no blocks that would randomly tick.
	 */
	public boolean getNeedsRandomTick() {
		return tickRefCount > 0;
	}

	/**
	 * Returns the Y location of this ExtendedBlockStorage.
	 */
	public int getYLocation() {
		return yBase;
	}

	/**
	 * Sets the saved Sky-light value in the extended block storage structure.
	 */
	public void setExtSkylightValue(final int x, final int y, final int z, final int value) {
		skylightArray.set(x, y, z, value);
	}

	/**
	 * Gets the saved Sky-light value in the extended block storage structure.
	 */
	public int getExtSkylightValue(final int x, final int y, final int z) {
		return skylightArray.get(x, y, z);
	}

	/**
	 * Sets the saved Block-light value in the extended block storage structure.
	 */
	public void setExtBlocklightValue(final int x, final int y, final int z, final int value) {
		blocklightArray.set(x, y, z, value);
	}

	/**
	 * Gets the saved Block-light value in the extended block storage structure.
	 */
	public int getExtBlocklightValue(final int x, final int y, final int z) {
		return blocklightArray.get(x, y, z);
	}

	public void removeInvalidBlocks() {
		final List blockStates = Block.BLOCK_STATE_IDS.getObjectList();
		final int maxStateId = blockStates.size();
		int localBlockRefCount = 0;
		int localTickRefCount = 0;

		for (int y = 0; y < 16; ++y) {
			final int by = y << 8;

			for (int z = 0; z < 16; ++z) {
				final int byz = by | z << 4;

				for (int x = 0; x < 16; ++x) {
					final char stateId = data[byz | x];

					if (stateId > 0) {
						++localBlockRefCount;

						if (stateId < maxStateId) {
							final IBlockState bs = (IBlockState) blockStates.get(stateId);

							if (bs != null) {
								final Block var4 = bs.getBlock();

								if (var4.getTickRandomly()) {
									++localTickRefCount;
								}
							}
						}
					}
				}
			}
		}

		blockRefCount = localBlockRefCount;
		tickRefCount = localTickRefCount;
	}

	public char[] getData() {
		return data;
	}

	public void setData(final char[] dataArray) {
		data = dataArray;
	}

	/**
	 * Returns the NibbleArray instance containing Block-light data.
	 */
	public NibbleArray getBlocklightArray() {
		return blocklightArray;
	}

	/**
	 * Returns the NibbleArray instance containing Sky-light data.
	 */
	public NibbleArray getSkylightArray() {
		return skylightArray;
	}

	/**
	 * Sets the NibbleArray instance used for Block-light values in this
	 * particular storage block.
	 */
	public void setBlocklightArray(final NibbleArray newBlocklightArray) {
		blocklightArray = newBlocklightArray;
	}

	/**
	 * Sets the NibbleArray instance used for Sky-light values in this
	 * particular storage block.
	 */
	public void setSkylightArray(final NibbleArray newSkylightArray) {
		skylightArray = newSkylightArray;
	}
}
