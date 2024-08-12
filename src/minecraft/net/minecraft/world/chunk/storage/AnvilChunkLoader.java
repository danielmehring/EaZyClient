package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {

public static final int EaZy = 1705;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final List chunksToRemove = Lists.newArrayList();
	private final Set pendingAnvilChunksCoordinates = Sets.newHashSet();
	private final Object syncLockObject = new Object();

	/** Save directory for chunks using the Anvil format */
	private final File chunkSaveLocation;
	// private static final String __OBFID = "http://https://fuckuskid00000384";

	public AnvilChunkLoader(final File p_i2003_1_) {
		chunkSaveLocation = p_i2003_1_;
	}

	/**
	 * Loads the specified(XZ) chunk into the specified world.
	 */
	@Override
	public Chunk loadChunk(final World worldIn, final int x, final int z) throws IOException {
		NBTTagCompound var4 = null;
		final ChunkCoordIntPair var5 = new ChunkCoordIntPair(x, z);
		synchronized (syncLockObject) {
			if (pendingAnvilChunksCoordinates.contains(var5)) {
				for (int var7 = 0; var7 < chunksToRemove.size(); ++var7) {
					if (((AnvilChunkLoader.PendingChunk) chunksToRemove.get(var7)).chunkCoordinate.equals(var5)) {
						var4 = ((AnvilChunkLoader.PendingChunk) chunksToRemove.get(var7)).nbtTags;
						break;
					}
				}
			}
		}

		if (var4 == null) {
			final DataInputStream var10 = RegionFileCache.getChunkInputStream(chunkSaveLocation, x, z);

			if (var10 == null) {
				return null;
			}

			var4 = CompressedStreamTools.read(var10);
		}

		return checkedReadChunkFromNBT(worldIn, x, z, var4);
	}

	/**
	 * Wraps readChunkFromNBT. Checks the coordinates and several NBT tags.
	 */
	protected Chunk checkedReadChunkFromNBT(final World worldIn, final int p_75822_2_, final int p_75822_3_,
			final NBTTagCompound p_75822_4_) {
		if (!p_75822_4_.hasKey("Level", 10)) {
			logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing level data, skipping");
			return null;
		} else if (!p_75822_4_.getCompoundTag("Level").hasKey("Sections", 9)) {
			logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing block data, skipping");
			return null;
		} else {
			Chunk var5 = readChunkFromNBT(worldIn, p_75822_4_.getCompoundTag("Level"));

			if (!var5.isAtLocation(p_75822_2_, p_75822_3_)) {
				logger.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_
						+ " is in the wrong location; relocating. (Expected " + p_75822_2_ + ", " + p_75822_3_
						+ ", got " + var5.xPosition + ", " + var5.zPosition + ")");
				p_75822_4_.setInteger("xPos", p_75822_2_);
				p_75822_4_.setInteger("zPos", p_75822_3_);
				var5 = readChunkFromNBT(worldIn, p_75822_4_.getCompoundTag("Level"));
			}

			return var5;
		}
	}

	@Override
	public void saveChunk(final World worldIn, final Chunk chunkIn) throws MinecraftException, IOException {
		worldIn.checkSessionLock();

		try {
			final NBTTagCompound var3 = new NBTTagCompound();
			final NBTTagCompound var4 = new NBTTagCompound();
			var3.setTag("Level", var4);
			writeChunkToNBT(chunkIn, worldIn, var4);
			addChunkToPending(chunkIn.getChunkCoordIntPair(), var3);
		} catch (final Exception var5) {
			var5.printStackTrace();
		}
	}

	protected void addChunkToPending(final ChunkCoordIntPair p_75824_1_, final NBTTagCompound p_75824_2_) {
		synchronized (syncLockObject) {
			if (pendingAnvilChunksCoordinates.contains(p_75824_1_)) {
				for (int var4 = 0; var4 < chunksToRemove.size(); ++var4) {
					if (((AnvilChunkLoader.PendingChunk) chunksToRemove.get(var4)).chunkCoordinate.equals(p_75824_1_)) {
						chunksToRemove.set(var4, new AnvilChunkLoader.PendingChunk(p_75824_1_, p_75824_2_));
						return;
					}
				}
			}

			chunksToRemove.add(new AnvilChunkLoader.PendingChunk(p_75824_1_, p_75824_2_));
			pendingAnvilChunksCoordinates.add(p_75824_1_);
			ThreadedFileIOBase.func_178779_a().queueIO(this);
		}
	}

	/**
	 * Returns a boolean stating if the write was unsuccessful.
	 */
	@Override
	public boolean writeNextIO() {
		AnvilChunkLoader.PendingChunk var1 = null;
		synchronized (syncLockObject) {
			if (chunksToRemove.isEmpty()) {
				return false;
			}

			var1 = (AnvilChunkLoader.PendingChunk) chunksToRemove.remove(0);
			pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
		}

		if (var1 != null) {
			try {
				writeChunkNBTTags(var1);
			} catch (final Exception var4) {
				var4.printStackTrace();
			}
		}

		return true;
	}

	private void writeChunkNBTTags(final AnvilChunkLoader.PendingChunk p_75821_1_) throws IOException {
		final DataOutputStream var2 = RegionFileCache.getChunkOutputStream(chunkSaveLocation,
				p_75821_1_.chunkCoordinate.chunkXPos, p_75821_1_.chunkCoordinate.chunkZPos);
		CompressedStreamTools.write(p_75821_1_.nbtTags, var2);
		var2.close();
	}

	/**
	 * Save extra data associated with this Chunk not normally saved during
	 * autosave, only during chunk unload. Currently unused.
	 */
	@Override
	public void saveExtraChunkData(final World worldIn, final Chunk chunkIn) {}

	/**
	 * Called every World.tick()
	 */
	@Override
	public void chunkTick() {}

	/**
	 * Save extra data not associated with any Chunk. Not saved during autosave,
	 * only during world unload. Currently unused.
	 */
	@Override
	public void saveExtraData() {
		while (writeNextIO()) {
		}
	}

	/**
	 * Writes the Chunk passed as an argument to the NBTTagCompound also passed,
	 * using the World argument to retrieve the Chunk's last update time.
	 */
	private void writeChunkToNBT(final Chunk p_75820_1_, final World worldIn, final NBTTagCompound p_75820_3_) {
		p_75820_3_.setByte("V", (byte) 1);
		p_75820_3_.setInteger("xPos", p_75820_1_.xPosition);
		p_75820_3_.setInteger("zPos", p_75820_1_.zPosition);
		p_75820_3_.setLong("LastUpdate", worldIn.getTotalWorldTime());
		p_75820_3_.setIntArray("HeightMap", p_75820_1_.getHeightMap());
		p_75820_3_.setBoolean("TerrainPopulated", p_75820_1_.isTerrainPopulated());
		p_75820_3_.setBoolean("LightPopulated", p_75820_1_.isLightPopulated());
		p_75820_3_.setLong("InhabitedTime", p_75820_1_.getInhabitedTime());
		final ExtendedBlockStorage[] var4 = p_75820_1_.getBlockStorageArray();
		final NBTTagList var5 = new NBTTagList();
		final boolean var6 = !worldIn.provider.getHasNoSky();
		final ExtendedBlockStorage[] var7 = var4;
		int var8 = var4.length;
		NBTTagCompound var11;

		for (int var9 = 0; var9 < var8; ++var9) {
			final ExtendedBlockStorage var10 = var7[var9];

			if (var10 != null) {
				var11 = new NBTTagCompound();
				var11.setByte("Y", (byte) (var10.getYLocation() >> 4 & 255));
				final byte[] var12 = new byte[var10.getData().length];
				final NibbleArray var13 = new NibbleArray();
				NibbleArray var14 = null;

				for (int var15 = 0; var15 < var10.getData().length; ++var15) {
					final char var16 = var10.getData()[var15];
					final int var17 = var15 & 15;
					final int var18 = var15 >> 8 & 15;
					final int var19 = var15 >> 4 & 15;

					if (var16 >> 12 != 0) {
						if (var14 == null) {
							var14 = new NibbleArray();
						}

						var14.set(var17, var18, var19, var16 >> 12);
					}

					var12[var15] = (byte) (var16 >> 4 & 255);
					var13.set(var17, var18, var19, var16 & 15);
				}

				var11.setByteArray("Blocks", var12);
				var11.setByteArray("Data", var13.getData());

				if (var14 != null) {
					var11.setByteArray("Add", var14.getData());
				}

				var11.setByteArray("BlockLight", var10.getBlocklightArray().getData());

				if (var6) {
					var11.setByteArray("SkyLight", var10.getSkylightArray().getData());
				} else {
					var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().getData().length]);
				}

				var5.appendTag(var11);
			}
		}

		p_75820_3_.setTag("Sections", var5);
		p_75820_3_.setByteArray("Biomes", p_75820_1_.getBiomeArray());
		p_75820_1_.setHasEntities(false);
		final NBTTagList var20 = new NBTTagList();
		Iterator var22;

		for (var8 = 0; var8 < p_75820_1_.getEntityLists().length; ++var8) {
			var22 = p_75820_1_.getEntityLists()[var8].iterator();

			while (var22.hasNext()) {
				final Entity var24 = (Entity) var22.next();
				var11 = new NBTTagCompound();

				if (var24.writeToNBTOptional(var11)) {
					p_75820_1_.setHasEntities(true);
					var20.appendTag(var11);
				}
			}
		}

		p_75820_3_.setTag("Entities", var20);
		final NBTTagList var21 = new NBTTagList();
		var22 = p_75820_1_.getTileEntityMap().values().iterator();

		while (var22.hasNext()) {
			final TileEntity var25 = (TileEntity) var22.next();
			var11 = new NBTTagCompound();
			var25.writeToNBT(var11);
			var21.appendTag(var11);
		}

		p_75820_3_.setTag("TileEntities", var21);
		final List var23 = worldIn.getPendingBlockUpdates(p_75820_1_, false);

		if (var23 != null) {
			final long var26 = worldIn.getTotalWorldTime();
			final NBTTagList var27 = new NBTTagList();
			final Iterator var28 = var23.iterator();

			while (var28.hasNext()) {
				final NextTickListEntry var29 = (NextTickListEntry) var28.next();
				final NBTTagCompound var30 = new NBTTagCompound();
				final ResourceLocation var31 = (ResourceLocation) Block.blockRegistry
						.getNameForObject(var29.func_151351_a());
				var30.setString("i", var31 == null ? "" : var31.toString());
				var30.setInteger("x", var29.field_180282_a.getX());
				var30.setInteger("y", var29.field_180282_a.getY());
				var30.setInteger("z", var29.field_180282_a.getZ());
				var30.setInteger("t", (int) (var29.scheduledTime - var26));
				var30.setInteger("p", var29.priority);
				var27.appendTag(var30);
			}

			p_75820_3_.setTag("TileTicks", var27);
		}
	}

	/**
	 * Reads the data stored in the passed NBTTagCompound and creates a Chunk
	 * with that data in the passed World. Returns the created Chunk.
	 */
	private Chunk readChunkFromNBT(final World worldIn, final NBTTagCompound p_75823_2_) {
		final int var3 = p_75823_2_.getInteger("xPos");
		final int var4 = p_75823_2_.getInteger("zPos");
		final Chunk var5 = new Chunk(worldIn, var3, var4);
		var5.setHeightMap(p_75823_2_.getIntArray("HeightMap"));
		var5.setTerrainPopulated(p_75823_2_.getBoolean("TerrainPopulated"));
		var5.setLightPopulated(p_75823_2_.getBoolean("LightPopulated"));
		var5.setInhabitedTime(p_75823_2_.getLong("InhabitedTime"));
		final NBTTagList var6 = p_75823_2_.getTagList("Sections", 10);
		final byte var7 = 16;
		final ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
		final boolean var9 = !worldIn.provider.getHasNoSky();

		for (int var10 = 0; var10 < var6.tagCount(); ++var10) {
			final NBTTagCompound var11 = var6.getCompoundTagAt(var10);
			final byte var12 = var11.getByte("Y");
			final ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
			final byte[] var14 = var11.getByteArray("Blocks");
			final NibbleArray var15 = new NibbleArray(var11.getByteArray("Data"));
			final NibbleArray var16 = var11.hasKey("Add", 7) ? new NibbleArray(var11.getByteArray("Add")) : null;
			final char[] var17 = new char[var14.length];

			for (int var18 = 0; var18 < var17.length; ++var18) {
				final int var19 = var18 & 15;
				final int var20 = var18 >> 8 & 15;
				final int var21 = var18 >> 4 & 15;
				final int var22 = var16 != null ? var16.get(var19, var20, var21) : 0;
				var17[var18] = (char) (var22 << 12 | (var14[var18] & 255) << 4 | var15.get(var19, var20, var21));
			}

			var13.setData(var17);
			var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight")));

			if (var9) {
				var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight")));
			}

			var13.removeInvalidBlocks();
			var8[var12] = var13;
		}

		var5.setStorageArrays(var8);

		if (p_75823_2_.hasKey("Biomes", 7)) {
			var5.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
		}

		final NBTTagList var23 = p_75823_2_.getTagList("Entities", 10);

		if (var23 != null) {
			for (int var24 = 0; var24 < var23.tagCount(); ++var24) {
				final NBTTagCompound var26 = var23.getCompoundTagAt(var24);
				final Entity var29 = EntityList.createEntityFromNBT(var26, worldIn);
				var5.setHasEntities(true);

				if (var29 != null) {
					var5.addEntity(var29);
					Entity var32 = var29;

					for (NBTTagCompound var35 = var26; var35.hasKey("Riding",
							10); var35 = var35.getCompoundTag("Riding")) {
						final Entity var37 = EntityList.createEntityFromNBT(var35.getCompoundTag("Riding"), worldIn);

						if (var37 != null) {
							var5.addEntity(var37);
							var32.mountEntity(var37);
						}

						var32 = var37;
					}
				}
			}
		}

		final NBTTagList var25 = p_75823_2_.getTagList("TileEntities", 10);

		if (var25 != null) {
			for (int var27 = 0; var27 < var25.tagCount(); ++var27) {
				final NBTTagCompound var30 = var25.getCompoundTagAt(var27);
				final TileEntity var33 = TileEntity.createAndLoadEntity(var30);

				if (var33 != null) {
					var5.addTileEntity(var33);
				}
			}
		}

		if (p_75823_2_.hasKey("TileTicks", 9)) {
			final NBTTagList var28 = p_75823_2_.getTagList("TileTicks", 10);

			if (var28 != null) {
				for (int var31 = 0; var31 < var28.tagCount(); ++var31) {
					final NBTTagCompound var34 = var28.getCompoundTagAt(var31);
					Block var36;

					if (var34.hasKey("i", 8)) {
						var36 = Block.getBlockFromName(var34.getString("i"));
					} else {
						var36 = Block.getBlockById(var34.getInteger("i"));
					}

					worldIn.func_180497_b(
							new BlockPos(var34.getInteger("x"), var34.getInteger("y"), var34.getInteger("z")), var36,
							var34.getInteger("t"), var34.getInteger("p"));
				}
			}
		}

		return var5;
	}

	static class PendingChunk {
		public final ChunkCoordIntPair chunkCoordinate;
		public final NBTTagCompound nbtTags;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000385";

		public PendingChunk(final ChunkCoordIntPair p_i2002_1_, final NBTTagCompound p_i2002_2_) {
			chunkCoordinate = p_i2002_1_;
			nbtTags = p_i2002_2_;
		}
	}
}
