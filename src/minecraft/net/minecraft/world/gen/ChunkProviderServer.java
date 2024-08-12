package net.minecraft.world.gen;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class ChunkProviderServer implements IChunkProvider {

public static final int EaZy = 1730;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final Set droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap());

	/** a dummy chunk, returned in place of an actual chunk. */
	private final Chunk dummyChunk;

	/**
	 * chunk generator object. Calls to load nonexistent chunks are forwarded to
	 * this object.
	 */
	private final IChunkProvider serverChunkGenerator;
	private final IChunkLoader chunkLoader;

	/**
	 * if set, this flag forces a request to load a chunk to load the chunk
	 * rather than defaulting to the dummy if possible
	 */
	public boolean chunkLoadOverride = true;

	/** map of chunk Id's to Chunk instances */
	private final LongHashMap id2ChunkMap = new LongHashMap();
	private final List loadedChunks = Lists.newArrayList();
	private final WorldServer worldObj;
	// private static final String __OBFID = "http://https://fuckuskid00001436";

	public ChunkProviderServer(final WorldServer p_i1520_1_, final IChunkLoader p_i1520_2_,
			final IChunkProvider p_i1520_3_) {
		dummyChunk = new EmptyChunk(p_i1520_1_, 0, 0);
		worldObj = p_i1520_1_;
		chunkLoader = p_i1520_2_;
		serverChunkGenerator = p_i1520_3_;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
		return id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(p_73149_1_, p_73149_2_));
	}

	public List func_152380_a() {
		return loadedChunks;
	}

	public void dropChunk(final int p_73241_1_, final int p_73241_2_) {
		if (worldObj.provider.canRespawnHere()) {
			if (!worldObj.chunkExists(p_73241_1_, p_73241_2_)) {
				droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_));
			}
		} else {
			droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_));
		}
	}

	/**
	 * marks all chunks for unload, ignoring those near the spawn
	 */
	public void unloadAllChunks() {
		final Iterator var1 = loadedChunks.iterator();

		while (var1.hasNext()) {
			final Chunk var2 = (Chunk) var1.next();
			dropChunk(var2.xPosition, var2.zPosition);
		}
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(final int p_73158_1_, final int p_73158_2_) {
		final long var3 = ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_);
		droppedChunksSet.remove(var3);
		Chunk var5 = (Chunk) id2ChunkMap.getValueByKey(var3);

		if (var5 == null) {
			var5 = loadChunkFromFile(p_73158_1_, p_73158_2_);

			if (var5 == null) {
				if (serverChunkGenerator == null) {
					var5 = dummyChunk;
				} else {
					try {
						var5 = serverChunkGenerator.provideChunk(p_73158_1_, p_73158_2_);
					} catch (final Throwable var9) {
						final CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
						final CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
						var8.addCrashSection("Location", String.format("%d,%d",
								new Object[] { p_73158_1_, p_73158_2_}));
						var8.addCrashSection("Position hash", var3);
						var8.addCrashSection("Generator", serverChunkGenerator.makeString());
						throw new ReportedException(var7);
					}
				}
			}

			id2ChunkMap.add(var3, var5);
			loadedChunks.add(var5);
			var5.onChunkLoad();
			var5.populateChunk(this, this, p_73158_1_, p_73158_2_);
		}

		return var5;
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
		final Chunk var3 = (Chunk) id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
		return var3 == null
				? !worldObj.isFindingSpawnPoint() && !chunkLoadOverride ? dummyChunk : loadChunk(p_73154_1_, p_73154_2_)
				: var3;
	}

	private Chunk loadChunkFromFile(final int p_73239_1_, final int p_73239_2_) {
		if (chunkLoader == null) {
			return null;
		} else {
			try {
				final Chunk var3 = chunkLoader.loadChunk(worldObj, p_73239_1_, p_73239_2_);

				if (var3 != null) {
					var3.setLastSaveTime(worldObj.getTotalWorldTime());

					if (serverChunkGenerator != null) {
						serverChunkGenerator.func_180514_a(var3, p_73239_1_, p_73239_2_);
					}
				}

				return var3;
			} catch (final Exception var4) {
				logger.error("Couldn\'t load chunk", var4);
				return null;
			}
		}
	}

	private void saveChunkExtraData(final Chunk p_73243_1_) {
		if (chunkLoader != null) {
			try {
				chunkLoader.saveExtraChunkData(worldObj, p_73243_1_);
			} catch (final Exception var3) {
				logger.error("Couldn\'t save entities", var3);
			}
		}
	}

	private void saveChunkData(final Chunk p_73242_1_) {
		if (chunkLoader != null) {
			try {
				p_73242_1_.setLastSaveTime(worldObj.getTotalWorldTime());
				chunkLoader.saveChunk(worldObj, p_73242_1_);
			} catch (final IOException var3) {
				logger.error("Couldn\'t save chunk", var3);
			} catch (final MinecraftException var4) {
				logger.error("Couldn\'t save chunk; already in use by another instance of Minecraft?", var4);
			}
		}
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
		final Chunk var4 = provideChunk(p_73153_2_, p_73153_3_);

		if (!var4.isTerrainPopulated()) {
			var4.func_150809_p();

			if (serverChunkGenerator != null) {
				serverChunkGenerator.populate(p_73153_1_, p_73153_2_, p_73153_3_);
				var4.setChunkModified();
			}
		}
	}

	@Override
	public boolean func_177460_a(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_,
			final int p_177460_4_) {
		if (serverChunkGenerator != null
				&& serverChunkGenerator.func_177460_a(p_177460_1_, p_177460_2_, p_177460_3_, p_177460_4_)) {
			final Chunk var5 = provideChunk(p_177460_3_, p_177460_4_);
			var5.setChunkModified();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Two modes of operation: if passed true, save all Chunks in one go. If
	 * passed false, save up to two chunks. Return true if all chunks have been
	 * saved.
	 */
	@Override
	public boolean saveChunks(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
		int var3 = 0;

		for (int var4 = 0; var4 < loadedChunks.size(); ++var4) {
			final Chunk var5 = (Chunk) loadedChunks.get(var4);

			if (p_73151_1_) {
				saveChunkExtraData(var5);
			}

			if (var5.needsSaving(p_73151_1_)) {
				saveChunkData(var5);
				var5.setModified(false);
				++var3;

				if (var3 == 24 && !p_73151_1_) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently unimplemented.
	 */
	@Override
	public void saveExtraData() {
		if (chunkLoader != null) {
			chunkLoader.saveExtraData();
		}
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to
	 * unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		if (!worldObj.disableLevelSaving) {
			for (int var1 = 0; var1 < 100; ++var1) {
				if (!droppedChunksSet.isEmpty()) {
					final Long var2 = (Long) droppedChunksSet.iterator().next();
					final Chunk var3 = (Chunk) id2ChunkMap.getValueByKey(var2);

					if (var3 != null) {
						var3.onChunkUnload();
						saveChunkData(var3);
						saveChunkExtraData(var3);
						id2ChunkMap.remove(var2);
						loadedChunks.remove(var3);
					}

					droppedChunksSet.remove(var2);
				}
			}

			if (chunkLoader != null) {
				chunkLoader.chunkTick();
			}
		}

		return serverChunkGenerator.unloadQueuedChunks();
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return !worldObj.disableLevelSaving;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "ServerChunkCache: " + id2ChunkMap.getNumHashElements() + " Drop: " + droppedChunksSet.size();
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		return serverChunkGenerator.func_177458_a(p_177458_1_, p_177458_2_);
	}

	@Override
	public BlockPos func_180513_a(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
		return serverChunkGenerator.func_180513_a(worldIn, p_180513_2_, p_180513_3_);
	}

	@Override
	public int getLoadedChunkCount() {
		return id2ChunkMap.getNumHashElements();
	}

	@Override
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}
}
