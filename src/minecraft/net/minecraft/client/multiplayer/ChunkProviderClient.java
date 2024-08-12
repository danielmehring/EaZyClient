package net.minecraft.client.multiplayer;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class ChunkProviderClient implements IChunkProvider {

public static final int EaZy = 619;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/**
	 * The completely empty chunk used by ChunkProviderClient when chunkMapping
	 * doesn't contain the requested coordinates.
	 */
	private final Chunk blankChunk;

	/**
	 * The mapping between ChunkCoordinates and Chunks that ChunkProviderClient
	 * maintains.
	 */
	private final LongHashMap chunkMapping = new LongHashMap();

	/**
	 * This may have been intended to be an iterable version of all currently
	 * loaded chunks (MultiplayerChunkCache), with identical contents to
	 * chunkMapping's values. However it is never actually added to.
	 */
	private final List chunkListing = Lists.newArrayList();

	/** Reference to the World object. */
	private final World worldObj;
	// private static final String __OBFID = "http://https://fuckuskid00000880";

	public ChunkProviderClient(final World worldIn) {
		blankChunk = new EmptyChunk(worldIn, 0, 0);
		worldObj = worldIn;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
		return true;
	}

	/**
	 * Unload chunk from ChunkProviderClient's hashmap. Called in response to a
	 * Packet50PreChunk with its mode field set to false
	 */
	public void unloadChunk(final int p_73234_1_, final int p_73234_2_) {
		final Chunk var3 = provideChunk(p_73234_1_, p_73234_2_);

		if (!var3.isEmpty()) {
			var3.onChunkUnload();
		}

		chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(p_73234_1_, p_73234_2_));
		chunkListing.remove(var3);
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
		final Chunk var3 = new Chunk(worldObj, p_73158_1_, p_73158_2_);
		chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_), var3);
		chunkListing.add(var3);
		var3.func_177417_c(true);
		return var3;
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
		final Chunk var3 = (Chunk) chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
		return var3 == null ? blankChunk : var3;
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If
	 * passed false, save up to two chunks. Return true if all chunks have been
	 * saved.
	 */
	@Override
	public boolean saveChunks(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
		return true;
	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently unimplemented.
	 */
	@Override
	public void saveExtraData() {}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		final long var1 = System.currentTimeMillis();
		final Iterator var3 = chunkListing.iterator();

		while (var3.hasNext()) {
			final Chunk var4 = (Chunk) var3.next();
			var4.func_150804_b(System.currentTimeMillis() - var1 > 5L);
		}

		if (System.currentTimeMillis() - var1 > 100L) {
			logger.info("Warning: Clientside chunk ticking took {} ms",
					new Object[] { System.currentTimeMillis() - var1});
		}

		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return false;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {}

	@Override
	public boolean func_177460_a(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_,
			final int p_177460_4_) {
		return false;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "MultiplayerChunkCache: " + chunkMapping.getNumHashElements() + ", " + chunkListing.size();
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		return null;
	}

	@Override
	public BlockPos func_180513_a(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return chunkListing.size();
	}

	@Override
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}
}
