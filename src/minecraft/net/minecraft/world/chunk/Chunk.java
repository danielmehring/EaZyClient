package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheratanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderDebug;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

public class Chunk {

public static final int EaZy = 1700;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Used to store block IDs, block MSBs, Sky-light maps, Block-light maps,
	 * and metadata. Each entry corresponds to a logical segment of 16x16x16
	 * blocks, stacked vertically.
	 */
	private final ExtendedBlockStorage[] storageArrays;

	/**
	 * Contains a 16x16 mapping on the X/Z plane of the biome ID to which each
	 * colum belongs.
	 */
	private final byte[] blockBiomeArray;

	/**
	 * A map, similar to heightMap, that tracks how far down precipitation can
	 * fall.
	 */
	private final int[] precipitationHeightMap;

	/** Which columns need their skylightMaps updated. */
	private final boolean[] updateSkylightColumns;

	/** Whether or not this Chunk is currently loaded into the World */
	private boolean isChunkLoaded;

	/** Reference to the World object. */
	private final World worldObj;
	private final int[] heightMap;

	/** The x coordinate of the chunk. */
	public final int xPosition;

	/** The z coordinate of the chunk. */
	public final int zPosition;
	private boolean isGapLightingUpdated;

	/** A Map of ChunkPositions to TileEntities in this chunk */
	private final Map chunkTileEntityMap;

	/**
	 * Array of Lists containing the entities in this Chunk. Each List
	 * represents a 16 block subchunk.
	 */
	private final ClassInheratanceMultiMap[] entityLists;

	/** Boolean value indicating if the terrain is populated. */
	private boolean isTerrainPopulated;
	private boolean isLightPopulated;
	private boolean field_150815_m;

	/**
	 * Set to true if the chunk has been modified and needs to be updated
	 * internally.
	 */
	private boolean isModified;

	/**
	 * Whether this Chunk has any Entities and thus requires saving on every
	 * tick
	 */
	private boolean hasEntities;

	/** The time according to World.worldTime when this chunk was last saved */
	private long lastSaveTime;

	/** Lowest value in the heightmap. */
	private int heightMapMinimum;

	/** the cumulative number of ticks players have been in this chunk */
	private long inhabitedTime;

	/**
	 * Contains the current round-robin relight check index, and is implied as
	 * the relight check location as well.
	 */
	private int queuedLightChecks;
	private final ConcurrentLinkedQueue field_177447_w;
	// private static final String __OBFID = "http://https://fuckuskid00000373";

	public Chunk(final World worldIn, final int x, final int z) {
		storageArrays = new ExtendedBlockStorage[16];
		blockBiomeArray = new byte[256];
		precipitationHeightMap = new int[256];
		updateSkylightColumns = new boolean[256];
		chunkTileEntityMap = Maps.newHashMap();
		queuedLightChecks = 4096;
		field_177447_w = Queues.newConcurrentLinkedQueue();
		entityLists = new ClassInheratanceMultiMap[16];
		worldObj = worldIn;
		xPosition = x;
		zPosition = z;
		heightMap = new int[256];

		for (int var4 = 0; var4 < entityLists.length; ++var4) {
			entityLists[var4] = new ClassInheratanceMultiMap(Entity.class);
		}

		Arrays.fill(precipitationHeightMap, -999);
		Arrays.fill(blockBiomeArray, (byte) -1);
	}

	public Chunk(final World worldIn, final ChunkPrimer primer, final int x, final int z) {
		this(worldIn, x, z);
		final short var5 = 256;
		final boolean var6 = !worldIn.provider.getHasNoSky();

		for (int var7 = 0; var7 < 16; ++var7) {
			for (int var8 = 0; var8 < 16; ++var8) {
				for (int var9 = 0; var9 < var5; ++var9) {
					final int var10 = var7 * var5 * 16 | var8 * var5 | var9;
					final IBlockState var11 = primer.getBlockState(var10);

					if (var11.getBlock().getMaterial() != Material.air) {
						final int var12 = var9 >> 4;

						if (storageArrays[var12] == null) {
							storageArrays[var12] = new ExtendedBlockStorage(var12 << 4, var6);
						}

						storageArrays[var12].set(var7, var9 & 15, var8, var11);
					}
				}
			}
		}
	}

	/**
	 * Checks whether the chunk is at the X/Z location specified
	 */
	public boolean isAtLocation(final int x, final int z) {
		return x == xPosition && z == zPosition;
	}

	public int getHeight(final BlockPos pos) {
		return this.getHeight(pos.getX() & 15, pos.getZ() & 15);
	}

	/**
	 * Returns the value in the height map at this x, z coordinate in the chunk
	 */
	public int getHeight(final int x, final int z) {
		return heightMap[z << 4 | x];
	}

	/**
	 * Returns the topmost ExtendedBlockStorage instance for this Chunk that
	 * actually contains a block.
	 */
	public int getTopFilledSegment() {
		for (int var1 = storageArrays.length - 1; var1 >= 0; --var1) {
			if (storageArrays[var1] != null) {
				return storageArrays[var1].getYLocation();
			}
		}

		return 0;
	}

	/**
	 * Returns the ExtendedBlockStorage array for this Chunk.
	 */
	public ExtendedBlockStorage[] getBlockStorageArray() {
		return storageArrays;
	}

	/**
	 * Generates the height map for a chunk from scratch
	 */
	protected void generateHeightMap() {
		final int var1 = getTopFilledSegment();
		heightMapMinimum = Integer.MAX_VALUE;

		for (int var2 = 0; var2 < 16; ++var2) {
			int var3 = 0;

			while (var3 < 16) {
				precipitationHeightMap[var2 + (var3 << 4)] = -999;
				int var4 = var1 + 16;

				while (true) {
					if (var4 > 0) {
						final Block var5 = getBlock0(var2, var4 - 1, var3);

						if (var5.getLightOpacity() == 0) {
							--var4;
							continue;
						}

						heightMap[var3 << 4 | var2] = var4;

						if (var4 < heightMapMinimum) {
							heightMapMinimum = var4;
						}
					}

					++var3;
					break;
				}
			}
		}

		isModified = true;
	}

	/**
	 * Generates the initial skylight map for the chunk upon generation or load.
	 */
	public void generateSkylightMap() {
		final int var1 = getTopFilledSegment();
		heightMapMinimum = Integer.MAX_VALUE;

		for (int var2 = 0; var2 < 16; ++var2) {
			int var3 = 0;

			while (var3 < 16) {
				precipitationHeightMap[var2 + (var3 << 4)] = -999;
				int var4 = var1 + 16;

				while (true) {
					if (var4 > 0) {
						if (this.getBlockLightOpacity(var2, var4 - 1, var3) == 0) {
							--var4;
							continue;
						}

						heightMap[var3 << 4 | var2] = var4;

						if (var4 < heightMapMinimum) {
							heightMapMinimum = var4;
						}
					}

					if (!worldObj.provider.getHasNoSky()) {
						var4 = 15;
						int var5 = var1 + 16 - 1;

						do {
							int var6 = this.getBlockLightOpacity(var2, var5, var3);

							if (var6 == 0 && var4 != 15) {
								var6 = 1;
							}

							var4 -= var6;

							if (var4 > 0) {
								final ExtendedBlockStorage var7 = storageArrays[var5 >> 4];

								if (var7 != null) {
									var7.setExtSkylightValue(var2, var5 & 15, var3, var4);
									worldObj.notifyLightSet(
											new BlockPos((xPosition << 4) + var2, var5, (zPosition << 4) + var3));
								}
							}

							--var5;
						}
						while (var5 > 0 && var4 > 0);
					}

					++var3;
					break;
				}
			}
		}

		isModified = true;
	}

	/**
	 * Propagates a given sky-visible block's light value downward and upward to
	 * neighboring blocks as necessary.
	 */
	private void propagateSkylightOcclusion(final int x, final int z) {
		updateSkylightColumns[x + z * 16] = true;
		isGapLightingUpdated = true;
	}

	private void recheckGaps(final boolean p_150803_1_) {
		worldObj.theProfiler.startSection("recheckGaps");

		if (worldObj.isAreaLoaded(new BlockPos(xPosition * 16 + 8, 0, zPosition * 16 + 8), 16)) {
			for (int var2 = 0; var2 < 16; ++var2) {
				for (int var3 = 0; var3 < 16; ++var3) {
					if (updateSkylightColumns[var2 + var3 * 16]) {
						updateSkylightColumns[var2 + var3 * 16] = false;
						final int var4 = this.getHeight(var2, var3);
						final int var5 = xPosition * 16 + var2;
						final int var6 = zPosition * 16 + var3;
						int var7 = Integer.MAX_VALUE;
						Iterator var8;
						EnumFacing var9;

						for (var8 = EnumFacing.Plane.HORIZONTAL.iterator(); var8
								.hasNext(); var7 = Math.min(var7, worldObj.getChunksLowestHorizon(
										var5 + var9.getFrontOffsetX(), var6 + var9.getFrontOffsetZ()))) {
							var9 = (EnumFacing) var8.next();
						}

						checkSkylightNeighborHeight(var5, var6, var7);
						var8 = EnumFacing.Plane.HORIZONTAL.iterator();

						while (var8.hasNext()) {
							var9 = (EnumFacing) var8.next();
							checkSkylightNeighborHeight(var5 + var9.getFrontOffsetX(), var6 + var9.getFrontOffsetZ(),
									var4);
						}

						if (p_150803_1_) {
							worldObj.theProfiler.endSection();
							return;
						}
					}
				}
			}

			isGapLightingUpdated = false;
		}

		worldObj.theProfiler.endSection();
	}

	/**
	 * Checks the height of a block next to a sky-visible block and schedules a
	 * lighting update as necessary.
	 */
	private void checkSkylightNeighborHeight(final int x, final int p_76599_2_, final int z) {
		final int var4 = worldObj.getHorizon(new BlockPos(x, 0, p_76599_2_)).getY();

		if (var4 > z) {
			updateSkylightNeighborHeight(x, p_76599_2_, z, var4 + 1);
		} else if (var4 < z) {
			updateSkylightNeighborHeight(x, p_76599_2_, var4, z + 1);
		}
	}

	private void updateSkylightNeighborHeight(final int x, final int z, final int startY, final int endY) {
		if (endY > startY && worldObj.isAreaLoaded(new BlockPos(x, 0, z), 16)) {
			for (int var5 = startY; var5 < endY; ++var5) {
				worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, var5, z));
			}

			isModified = true;
		}
	}

	/**
	 * Initiates the recalculation of both the block-light and sky-light for a
	 * given block inside a chunk.
	 */
	private void relightBlock(final int x, final int y, final int z) {
		final int var4 = heightMap[z << 4 | x] & 255;
		int var5 = var4;

		if (y > var4) {
			var5 = y;
		}

		while (var5 > 0 && this.getBlockLightOpacity(x, var5 - 1, z) == 0) {
			--var5;
		}

		if (var5 != var4) {
			worldObj.markBlocksDirtyVertical(x + xPosition * 16, z + zPosition * 16, var5, var4);
			heightMap[z << 4 | x] = var5;
			final int var6 = xPosition * 16 + x;
			final int var7 = zPosition * 16 + z;
			int var8;
			int var13;

			if (!worldObj.provider.getHasNoSky()) {
				ExtendedBlockStorage var9;

				if (var5 < var4) {
					for (var8 = var5; var8 < var4; ++var8) {
						var9 = storageArrays[var8 >> 4];

						if (var9 != null) {
							var9.setExtSkylightValue(x, var8 & 15, z, 15);
							worldObj.notifyLightSet(new BlockPos((xPosition << 4) + x, var8, (zPosition << 4) + z));
						}
					}
				} else {
					for (var8 = var4; var8 < var5; ++var8) {
						var9 = storageArrays[var8 >> 4];

						if (var9 != null) {
							var9.setExtSkylightValue(x, var8 & 15, z, 0);
							worldObj.notifyLightSet(new BlockPos((xPosition << 4) + x, var8, (zPosition << 4) + z));
						}
					}
				}

				var8 = 15;

				while (var5 > 0 && var8 > 0) {
					--var5;
					var13 = this.getBlockLightOpacity(x, var5, z);

					if (var13 == 0) {
						var13 = 1;
					}

					var8 -= var13;

					if (var8 < 0) {
						var8 = 0;
					}

					final ExtendedBlockStorage var10 = storageArrays[var5 >> 4];

					if (var10 != null) {
						var10.setExtSkylightValue(x, var5 & 15, z, var8);
					}
				}
			}

			var8 = heightMap[z << 4 | x];
			var13 = var4;
			int var14 = var8;

			if (var8 < var4) {
				var13 = var8;
				var14 = var4;
			}

			if (var8 < heightMapMinimum) {
				heightMapMinimum = var8;
			}

			if (!worldObj.provider.getHasNoSky()) {
				final Iterator var11 = EnumFacing.Plane.HORIZONTAL.iterator();

				while (var11.hasNext()) {
					final EnumFacing var12 = (EnumFacing) var11.next();
					updateSkylightNeighborHeight(var6 + var12.getFrontOffsetX(), var7 + var12.getFrontOffsetZ(), var13,
							var14);
				}

				updateSkylightNeighborHeight(var6, var7, var13, var14);
			}

			isModified = true;
		}
	}

	public int getBlockLightOpacity(final BlockPos pos) {
		return this.getBlock(pos).getLightOpacity();
	}

	private int getBlockLightOpacity(final int p_150808_1_, final int p_150808_2_, final int p_150808_3_) {
		return getBlock0(p_150808_1_, p_150808_2_, p_150808_3_).getLightOpacity();
	}

	/**
	 * Returns the block corresponding to the given coordinates inside a chunk.
	 */
	private Block getBlock0(final int x, final int y, final int z) {
		Block var4 = Blocks.air;

		if (y >= 0 && y >> 4 < storageArrays.length) {
			final ExtendedBlockStorage var5 = storageArrays[y >> 4];

			if (var5 != null) {
				try {
					var4 = var5.getBlockByExtId(x, y & 15, z);
				} catch (final Throwable var8) {
					final CrashReport var7 = CrashReport.makeCrashReport(var8, "Getting block");
					throw new ReportedException(var7);
				}
			}
		}

		return var4;
	}

	public Block getBlock(final int x, final int y, final int z) {
		try {
			return getBlock0(x & 15, y, z & 15);
		} catch (final ReportedException var6) {
			final CrashReportCategory var5 = var6.getCrashReport().makeCategory("Block being got");
			var5.addCrashSectionCallable("Location", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000374";
				@Override
				public String call() {
					return CrashReportCategory
							.getCoordinateInfo(new BlockPos(xPosition * 16 + x, y, zPosition * 16 + z));
				}
			});
			throw var6;
		}
	}

	public Block getBlock(final BlockPos pos) {
		try {
			return getBlock0(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
		} catch (final ReportedException var4) {
			final CrashReportCategory var3 = var4.getCrashReport().makeCategory("Block being got");
			var3.addCrashSectionCallable("Location", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002011";
				public String func_177455_a() {
					return CrashReportCategory.getCoordinateInfo(pos);
				}

				@Override
				public Object call() {
					return func_177455_a();
				}
			});
			throw var4;
		}
	}

	public IBlockState getBlockState(final BlockPos pos) {
		if (worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
			IBlockState var7 = null;

			if (pos.getY() == 60) {
				var7 = Blocks.barrier.getDefaultState();
			}

			if (pos.getY() == 70) {
				var7 = ChunkProviderDebug.func_177461_b(pos.getX(), pos.getZ());
			}

			return var7 == null ? Blocks.air.getDefaultState() : var7;
		} else {
			try {
				if (pos.getY() >= 0 && pos.getY() >> 4 < storageArrays.length) {
					final ExtendedBlockStorage var2 = storageArrays[pos.getY() >> 4];

					if (var2 != null) {
						final int var8 = pos.getX() & 15;
						final int var9 = pos.getY() & 15;
						final int var5 = pos.getZ() & 15;
						return var2.get(var8, var9, var5);
					}
				}

				return Blocks.air.getDefaultState();
			} catch (final Throwable var6) {
				final CrashReport var3 = CrashReport.makeCrashReport(var6, "Getting block state");
				final CrashReportCategory var4 = var3.makeCategory("Block being got");
				var4.addCrashSectionCallable("Location", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00002010";
					public String func_177448_a() {
						return CrashReportCategory.getCoordinateInfo(pos);
					}

					@Override
					public Object call() {
						return func_177448_a();
					}
				});
				throw new ReportedException(var3);
			}
		}
	}

	/**
	 * Return the metadata corresponding to the given coordinates inside a
	 * chunk.
	 */
	private int getBlockMetadata(final int p_76628_1_, final int p_76628_2_, final int p_76628_3_) {
		if (p_76628_2_ >> 4 >= storageArrays.length) {
			return 0;
		} else {
			final ExtendedBlockStorage var4 = storageArrays[p_76628_2_ >> 4];
			return var4 != null ? var4.getExtBlockMetadata(p_76628_1_, p_76628_2_ & 15, p_76628_3_) : 0;
		}
	}

	public int getBlockMetadata(final BlockPos pos) {
		return this.getBlockMetadata(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
	}

	public IBlockState setBlockState(final BlockPos p_177436_1_, final IBlockState p_177436_2_) {
		final int var3 = p_177436_1_.getX() & 15;
		final int var4 = p_177436_1_.getY();
		final int var5 = p_177436_1_.getZ() & 15;
		final int var6 = var5 << 4 | var3;

		if (var4 >= precipitationHeightMap[var6] - 1) {
			precipitationHeightMap[var6] = -999;
		}

		final int var7 = heightMap[var6];
		final IBlockState var8 = getBlockState(p_177436_1_);

		if (var8 == p_177436_2_) {
			return null;
		} else {
			final Block var9 = p_177436_2_.getBlock();
			final Block var10 = var8.getBlock();
			ExtendedBlockStorage var11 = storageArrays[var4 >> 4];
			boolean var12 = false;

			if (var11 == null) {
				if (var9 == Blocks.air) {
					return null;
				}

				var11 = storageArrays[var4 >> 4] = new ExtendedBlockStorage(var4 >> 4 << 4,
						!worldObj.provider.getHasNoSky());
				var12 = var4 >= var7;
			}

			var11.set(var3, var4 & 15, var5, p_177436_2_);

			if (var10 != var9) {
				if (!worldObj.isRemote) {
					var10.breakBlock(worldObj, p_177436_1_, var8);
				} else if (var10 instanceof ITileEntityProvider) {
					worldObj.removeTileEntity(p_177436_1_);
				}
			}

			if (var11.getBlockByExtId(var3, var4 & 15, var5) != var9) {
				return null;
			} else {
				if (var12) {
					generateSkylightMap();
				} else {
					final int var13 = var9.getLightOpacity();
					final int var14 = var10.getLightOpacity();

					if (var13 > 0) {
						if (var4 >= var7) {
							relightBlock(var3, var4 + 1, var5);
						}
					} else if (var4 == var7 - 1) {
						relightBlock(var3, var4, var5);
					}

					if (var13 != var14 && (var13 < var14 || getLightFor(EnumSkyBlock.SKY, p_177436_1_) > 0
							|| getLightFor(EnumSkyBlock.BLOCK, p_177436_1_) > 0)) {
						propagateSkylightOcclusion(var3, var5);
					}
				}

				TileEntity var15;

				if (var10 instanceof ITileEntityProvider) {
					var15 = func_177424_a(p_177436_1_, Chunk.EnumCreateEntityType.CHECK);

					if (var15 != null) {
						var15.updateContainingBlockInfo();
					}
				}

				if (!worldObj.isRemote && var10 != var9) {
					var9.onBlockAdded(worldObj, p_177436_1_, p_177436_2_);
				}

				if (var9 instanceof ITileEntityProvider) {
					var15 = func_177424_a(p_177436_1_, Chunk.EnumCreateEntityType.CHECK);

					if (var15 == null) {
						var15 = ((ITileEntityProvider) var9).createNewTileEntity(worldObj,
								var9.getMetaFromState(p_177436_2_));
						worldObj.setTileEntity(p_177436_1_, var15);
					}

					if (var15 != null) {
						var15.updateContainingBlockInfo();
					}
				}

				isModified = true;
				return var8;
			}
		}
	}

	public int getLightFor(final EnumSkyBlock p_177413_1_, final BlockPos p_177413_2_) {
		final int var3 = p_177413_2_.getX() & 15;
		final int var4 = p_177413_2_.getY();
		final int var5 = p_177413_2_.getZ() & 15;
		final ExtendedBlockStorage var6 = storageArrays[var4 >> 4];
		return var6 == null ? canSeeSky(p_177413_2_) ? p_177413_1_.defaultLightValue : 0
				: p_177413_1_ == EnumSkyBlock.SKY
						? worldObj.provider.getHasNoSky() ? 0 : var6.getExtSkylightValue(var3, var4 & 15, var5)
						: p_177413_1_ == EnumSkyBlock.BLOCK ? var6.getExtBlocklightValue(var3, var4 & 15, var5)
								: p_177413_1_.defaultLightValue;
	}

	public void setLightFor(final EnumSkyBlock p_177431_1_, final BlockPos p_177431_2_, final int p_177431_3_) {
		final int var4 = p_177431_2_.getX() & 15;
		final int var5 = p_177431_2_.getY();
		final int var6 = p_177431_2_.getZ() & 15;
		ExtendedBlockStorage var7 = storageArrays[var5 >> 4];

		if (var7 == null) {
			var7 = storageArrays[var5 >> 4] = new ExtendedBlockStorage(var5 >> 4 << 4,
					!worldObj.provider.getHasNoSky());
			generateSkylightMap();
		}

		isModified = true;

		if (p_177431_1_ == EnumSkyBlock.SKY) {
			if (!worldObj.provider.getHasNoSky()) {
				var7.setExtSkylightValue(var4, var5 & 15, var6, p_177431_3_);
			}
		} else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
			var7.setExtBlocklightValue(var4, var5 & 15, var6, p_177431_3_);
		}
	}

	public int setLight(final BlockPos p_177443_1_, final int p_177443_2_) {
		final int var3 = p_177443_1_.getX() & 15;
		final int var4 = p_177443_1_.getY();
		final int var5 = p_177443_1_.getZ() & 15;
		final ExtendedBlockStorage var6 = storageArrays[var4 >> 4];

		if (var6 == null) {
			return !worldObj.provider.getHasNoSky() && p_177443_2_ < EnumSkyBlock.SKY.defaultLightValue
					? EnumSkyBlock.SKY.defaultLightValue - p_177443_2_ : 0;
		} else {
			int var7 = worldObj.provider.getHasNoSky() ? 0 : var6.getExtSkylightValue(var3, var4 & 15, var5);
			var7 -= p_177443_2_;
			final int var8 = var6.getExtBlocklightValue(var3, var4 & 15, var5);

			if (var8 > var7) {
				var7 = var8;
			}

			return var7;
		}
	}

	/**
	 * Adds an entity to the chunk. Args: entity
	 */
	public void addEntity(final Entity entityIn) {
		hasEntities = true;
		final int var2 = MathHelper.floor_double(entityIn.posX / 16.0D);
		final int var3 = MathHelper.floor_double(entityIn.posZ / 16.0D);

		if (var2 != xPosition || var3 != zPosition) {
			logger.warn("Wrong location! (" + var2 + ", " + var3 + ") should be (" + xPosition + ", " + zPosition
					+ "), " + entityIn, new Object[] { entityIn });
			entityIn.setDead();
		}

		int var4 = MathHelper.floor_double(entityIn.posY / 16.0D);

		if (var4 < 0) {
			var4 = 0;
		}

		if (var4 >= entityLists.length) {
			var4 = entityLists.length - 1;
		}

		entityIn.addedToChunk = true;
		entityIn.chunkCoordX = xPosition;
		entityIn.chunkCoordY = var4;
		entityIn.chunkCoordZ = zPosition;
		entityLists[var4].add(entityIn);
	}

	/**
	 * removes entity using its y chunk coordinate as its index
	 */
	public void removeEntity(final Entity p_76622_1_) {
		removeEntityAtIndex(p_76622_1_, p_76622_1_.chunkCoordY);
	}

	/**
	 * Removes entity at the specified index from the entity array.
	 */
	public void removeEntityAtIndex(final Entity p_76608_1_, int p_76608_2_) {
		if (p_76608_2_ < 0) {
			p_76608_2_ = 0;
		}

		if (p_76608_2_ >= entityLists.length) {
			p_76608_2_ = entityLists.length - 1;
		}

		entityLists[p_76608_2_].remove(p_76608_1_);
	}

	public boolean canSeeSky(final BlockPos pos) {
		final int var2 = pos.getX() & 15;
		final int var3 = pos.getY();
		final int var4 = pos.getZ() & 15;
		return var3 >= heightMap[var4 << 4 | var2];
	}

	private TileEntity createNewTileEntity(final BlockPos pos) {
		final Block var2 = this.getBlock(pos);
		return !var2.hasTileEntity() ? null
				: ((ITileEntityProvider) var2).createNewTileEntity(worldObj, this.getBlockMetadata(pos));
	}

	public TileEntity func_177424_a(final BlockPos p_177424_1_, final Chunk.EnumCreateEntityType p_177424_2_) {
		TileEntity var3 = (TileEntity) chunkTileEntityMap.get(p_177424_1_);

		if (var3 == null) {
			if (p_177424_2_ == Chunk.EnumCreateEntityType.IMMEDIATE) {
				var3 = createNewTileEntity(p_177424_1_);
				worldObj.setTileEntity(p_177424_1_, var3);
			} else if (p_177424_2_ == Chunk.EnumCreateEntityType.QUEUED) {
				field_177447_w.add(p_177424_1_);
			}
		} else if (var3.isInvalid()) {
			chunkTileEntityMap.remove(p_177424_1_);
			return null;
		}

		return var3;
	}

	public void addTileEntity(final TileEntity tileEntityIn) {
		this.addTileEntity(tileEntityIn.getPos(), tileEntityIn);

		if (isChunkLoaded) {
			worldObj.addTileEntity(tileEntityIn);
		}
	}

	public void addTileEntity(final BlockPos pos, final TileEntity tileEntityIn) {
		tileEntityIn.setWorldObj(worldObj);
		tileEntityIn.setPos(pos);

		if (this.getBlock(pos) instanceof ITileEntityProvider) {
			if (chunkTileEntityMap.containsKey(pos)) {
				((TileEntity) chunkTileEntityMap.get(pos)).invalidate();
			}

			tileEntityIn.validate();
			chunkTileEntityMap.put(pos, tileEntityIn);
		}
	}

	public void removeTileEntity(final BlockPos pos) {
		if (isChunkLoaded) {
			final TileEntity var2 = (TileEntity) chunkTileEntityMap.remove(pos);

			if (var2 != null) {
				var2.invalidate();
			}
		}
	}

	/**
	 * Called when this Chunk is loaded by the ChunkProvider
	 */
	public void onChunkLoad() {
		isChunkLoaded = true;
		worldObj.addTileEntities(chunkTileEntityMap.values());

		for (final ClassInheratanceMultiMap entityList : entityLists) {
			final Iterator var2 = entityList.iterator();

			while (var2.hasNext()) {
				final Entity var3 = (Entity) var2.next();
				var3.onChunkLoad();
			}

			worldObj.loadEntities(entityList);
		}
	}

	/**
	 * Called when this Chunk is unloaded by the ChunkProvider
	 */
	public void onChunkUnload() {
		isChunkLoaded = false;
		final Iterator var1 = chunkTileEntityMap.values().iterator();

		while (var1.hasNext()) {
			final TileEntity var2 = (TileEntity) var1.next();
			worldObj.markTileEntityForRemoval(var2);
		}

		for (final ClassInheratanceMultiMap entityList : entityLists) {
			worldObj.unloadEntities(entityList);
		}
	}

	/**
	 * Sets the isModified flag for this Chunk
	 */
	public void setChunkModified() {
		isModified = true;
	}

	public void func_177414_a(final Entity p_177414_1_, final AxisAlignedBB p_177414_2_, final List p_177414_3_,
			final Predicate p_177414_4_) {
		int var5 = MathHelper.floor_double((p_177414_2_.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_177414_2_.maxY + 2.0D) / 16.0D);
		var5 = MathHelper.clamp_int(var5, 0, entityLists.length - 1);
		var6 = MathHelper.clamp_int(var6, 0, entityLists.length - 1);

		for (int var7 = var5; var7 <= var6; ++var7) {
			final Iterator var8 = entityLists[var7].iterator();

			while (var8.hasNext()) {
				Entity var9 = (Entity) var8.next();

				if (var9 != p_177414_1_ && var9.getEntityBoundingBox().intersectsWith(p_177414_2_)
						&& (p_177414_4_ == null || p_177414_4_.apply(var9))) {
					p_177414_3_.add(var9);
					final Entity[] var10 = var9.getParts();

					if (var10 != null) {
						for (final Entity element : var10) {
							var9 = element;

							if (var9 != p_177414_1_ && var9.getEntityBoundingBox().intersectsWith(p_177414_2_)
									&& (p_177414_4_ == null || p_177414_4_.apply(var9))) {
								p_177414_3_.add(var9);
							}
						}
					}
				}
			}
		}
	}

	public void func_177430_a(final Class p_177430_1_, final AxisAlignedBB p_177430_2_, final List p_177430_3_,
			final Predicate p_177430_4_) {
		int var5 = MathHelper.floor_double((p_177430_2_.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_177430_2_.maxY + 2.0D) / 16.0D);
		var5 = MathHelper.clamp_int(var5, 0, entityLists.length - 1);
		var6 = MathHelper.clamp_int(var6, 0, entityLists.length - 1);

		for (int var7 = var5; var7 <= var6; ++var7) {
			final Iterator var8 = entityLists[var7].func_180215_b(p_177430_1_).iterator();

			while (var8.hasNext()) {
				final Entity var9 = (Entity) var8.next();

				if (var9.getEntityBoundingBox().intersectsWith(p_177430_2_)
						&& (p_177430_4_ == null || p_177430_4_.apply(var9))) {
					p_177430_3_.add(var9);
				}
			}
		}
	}

	/**
	 * Returns true if this Chunk needs to be saved
	 */
	public boolean needsSaving(final boolean p_76601_1_) {
		if (p_76601_1_) {
			if (hasEntities && worldObj.getTotalWorldTime() != lastSaveTime || isModified) {
				return true;
			}
		} else if (hasEntities && worldObj.getTotalWorldTime() >= lastSaveTime + 600L) {
			return true;
		}

		return isModified;
	}

	public Random getRandomWithSeed(final long seed) {
		return new Random(worldObj.getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611
				+ zPosition * zPosition * 4392871L + zPosition * 389711 ^ seed);
	}

	public boolean isEmpty() {
		return false;
	}

	public void populateChunk(final IChunkProvider p_76624_1_, final IChunkProvider p_76624_2_, final int p_76624_3_,
			final int p_76624_4_) {
		final boolean var5 = p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1);
		final boolean var6 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_);
		final boolean var7 = p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1);
		final boolean var8 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_);
		final boolean var9 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ - 1);
		final boolean var10 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ + 1);
		final boolean var11 = p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1);
		final boolean var12 = p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1);

		if (var6 && var7 && var10) {
			if (!isTerrainPopulated) {
				p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_);
			} else {
				p_76624_1_.func_177460_a(p_76624_2_, this, p_76624_3_, p_76624_4_);
			}
		}

		Chunk var13;

		if (var8 && var7 && var11) {
			var13 = p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_);

			if (!var13.isTerrainPopulated) {
				p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
			} else {
				p_76624_1_.func_177460_a(p_76624_2_, var13, p_76624_3_ - 1, p_76624_4_);
			}
		}

		if (var5 && var6 && var12) {
			var13 = p_76624_1_.provideChunk(p_76624_3_, p_76624_4_ - 1);

			if (!var13.isTerrainPopulated) {
				p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
			} else {
				p_76624_1_.func_177460_a(p_76624_2_, var13, p_76624_3_, p_76624_4_ - 1);
			}
		}

		if (var9 && var5 && var8) {
			var13 = p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_ - 1);

			if (!var13.isTerrainPopulated) {
				p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
			} else {
				p_76624_1_.func_177460_a(p_76624_2_, var13, p_76624_3_ - 1, p_76624_4_ - 1);
			}
		}
	}

	public BlockPos func_177440_h(final BlockPos p_177440_1_) {
		final int var2 = p_177440_1_.getX() & 15;
		final int var3 = p_177440_1_.getZ() & 15;
		final int var4 = var2 | var3 << 4;
		BlockPos var5 = new BlockPos(p_177440_1_.getX(), precipitationHeightMap[var4], p_177440_1_.getZ());

		if (var5.getY() == -999) {
			final int var6 = getTopFilledSegment() + 15;
			var5 = new BlockPos(p_177440_1_.getX(), var6, p_177440_1_.getZ());
			int var7 = -1;

			while (var5.getY() > 0 && var7 == -1) {
				final Block var8 = this.getBlock(var5);
				final Material var9 = var8.getMaterial();

				if (!var9.blocksMovement() && !var9.isLiquid()) {
					var5 = var5.offsetDown();
				} else {
					var7 = var5.getY() + 1;
				}
			}

			precipitationHeightMap[var4] = var7;
		}

		return new BlockPos(p_177440_1_.getX(), precipitationHeightMap[var4], p_177440_1_.getZ());
	}

	public void func_150804_b(final boolean p_150804_1_) {
		if (isGapLightingUpdated && !worldObj.provider.getHasNoSky() && !p_150804_1_) {
			recheckGaps(worldObj.isRemote);
		}

		field_150815_m = true;

		if (!isLightPopulated && isTerrainPopulated) {
			func_150809_p();
		}

		while (!field_177447_w.isEmpty()) {
			final BlockPos var2 = (BlockPos) field_177447_w.poll();

			if (func_177424_a(var2, Chunk.EnumCreateEntityType.CHECK) == null && this.getBlock(var2).hasTileEntity()) {
				final TileEntity var3 = createNewTileEntity(var2);
				worldObj.setTileEntity(var2, var3);
				worldObj.markBlockRangeForRenderUpdate(var2, var2);
			}
		}
	}

	public boolean isPopulated() {
		return field_150815_m && isTerrainPopulated && isLightPopulated;
	}

	/**
	 * Gets a ChunkCoordIntPair representing the Chunk's position.
	 */
	public ChunkCoordIntPair getChunkCoordIntPair() {
		return new ChunkCoordIntPair(xPosition, zPosition);
	}

	/**
	 * Returns whether the ExtendedBlockStorages containing levels (in blocks)
	 * from arg 1 to arg 2 are fully empty (true) or not (false).
	 */
	public boolean getAreLevelsEmpty(int p_76606_1_, int p_76606_2_) {
		if (p_76606_1_ < 0) {
			p_76606_1_ = 0;
		}

		if (p_76606_2_ >= 256) {
			p_76606_2_ = 255;
		}

		for (int var3 = p_76606_1_; var3 <= p_76606_2_; var3 += 16) {
			final ExtendedBlockStorage var4 = storageArrays[var3 >> 4];

			if (var4 != null && !var4.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public void setStorageArrays(final ExtendedBlockStorage[] newStorageArrays) {
		if (storageArrays.length != newStorageArrays.length) {
			logger.warn("Could not set level chunk sections, array length is " + newStorageArrays.length
					+ " instead of " + storageArrays.length);
		} else {
			for (int var2 = 0; var2 < storageArrays.length; ++var2) {
				storageArrays[var2] = newStorageArrays[var2];
			}
		}
	}

	public void func_177439_a(final byte[] p_177439_1_, final int p_177439_2_, final boolean p_177439_3_) {
		int var4 = 0;
		final boolean var5 = !worldObj.provider.getHasNoSky();
		int var6;

		for (var6 = 0; var6 < storageArrays.length; ++var6) {
			if ((p_177439_2_ & 1 << var6) != 0) {
				if (storageArrays[var6] == null) {
					storageArrays[var6] = new ExtendedBlockStorage(var6 << 4, var5);
				}

				final char[] var7 = storageArrays[var6].getData();

				for (int var8 = 0; var8 < var7.length; ++var8) {
					var7[var8] = (char) ((p_177439_1_[var4 + 1] & 255) << 8 | p_177439_1_[var4] & 255);
					var4 += 2;
				}
			} else if (p_177439_3_ && storageArrays[var6] != null) {
				storageArrays[var6] = null;
			}
		}

		NibbleArray var10;

		for (var6 = 0; var6 < storageArrays.length; ++var6) {
			if ((p_177439_2_ & 1 << var6) != 0 && storageArrays[var6] != null) {
				var10 = storageArrays[var6].getBlocklightArray();
				System.arraycopy(p_177439_1_, var4, var10.getData(), 0, var10.getData().length);
				var4 += var10.getData().length;
			}
		}

		if (var5) {
			for (var6 = 0; var6 < storageArrays.length; ++var6) {
				if ((p_177439_2_ & 1 << var6) != 0 && storageArrays[var6] != null) {
					var10 = storageArrays[var6].getSkylightArray();
					System.arraycopy(p_177439_1_, var4, var10.getData(), 0, var10.getData().length);
					var4 += var10.getData().length;
				}
			}
		}

		if (p_177439_3_) {
			System.arraycopy(p_177439_1_, var4, blockBiomeArray, 0, blockBiomeArray.length);
		}

		for (var6 = 0; var6 < storageArrays.length; ++var6) {
			if (storageArrays[var6] != null && (p_177439_2_ & 1 << var6) != 0) {
				storageArrays[var6].removeInvalidBlocks();
			}
		}

		isLightPopulated = true;
		isTerrainPopulated = true;
		generateHeightMap();
		final Iterator var9 = chunkTileEntityMap.values().iterator();

		while (var9.hasNext()) {
			final TileEntity var11 = (TileEntity) var9.next();
			var11.updateContainingBlockInfo();
		}
	}

	public BiomeGenBase getBiome(final BlockPos pos, final WorldChunkManager chunkManager) {
		final int var3 = pos.getX() & 15;
		final int var4 = pos.getZ() & 15;
		int var5 = blockBiomeArray[var4 << 4 | var3] & 255;
		BiomeGenBase var6;

		if (var5 == 255) {
			var6 = chunkManager.func_180300_a(pos, BiomeGenBase.plains);
			var5 = var6.biomeID;
			blockBiomeArray[var4 << 4 | var3] = (byte) (var5 & 255);
		}

		var6 = BiomeGenBase.getBiome(var5);
		return var6 == null ? BiomeGenBase.plains : var6;
	}

	/**
	 * Returns an array containing a 16x16 mapping on the X/Z of block positions
	 * in this Chunk to biome IDs.
	 */
	public byte[] getBiomeArray() {
		return blockBiomeArray;
	}

	/**
	 * Accepts a 256-entry array that contains a 16x16 mapping on the X/Z plane
	 * of block positions in this Chunk to biome IDs.
	 */
	public void setBiomeArray(final byte[] biomeArray) {
		if (blockBiomeArray.length != biomeArray.length) {
			logger.warn("Could not set level chunk biomes, array length is " + biomeArray.length + " instead of "
					+ blockBiomeArray.length);
		} else {
			for (int var2 = 0; var2 < blockBiomeArray.length; ++var2) {
				blockBiomeArray[var2] = biomeArray[var2];
			}
		}
	}

	/**
	 * Resets the relight check index to 0 for this Chunk.
	 */
	public void resetRelightChecks() {
		queuedLightChecks = 0;
	}

	/**
	 * Called once-per-chunk-per-tick, and advances the round-robin relight
	 * check index by up to 8 blocks at a time. In a worst-case scenario, can
	 * potentially take up to 25.6 seconds, calculated via (4096/8)/20, to
	 * re-check all blocks in a chunk, which may explain lagging light updates
	 * on initial world generation.
	 */
	public void enqueueRelightChecks() {
		final BlockPos var1 = new BlockPos(xPosition << 4, 0, zPosition << 4);

		for (int var2 = 0; var2 < 8; ++var2) {
			if (queuedLightChecks >= 4096) {
				return;
			}

			final int var3 = queuedLightChecks % 16;
			final int var4 = queuedLightChecks / 16 % 16;
			final int var5 = queuedLightChecks / 256;
			++queuedLightChecks;

			for (int var6 = 0; var6 < 16; ++var6) {
				final BlockPos var7 = var1.add(var4, (var3 << 4) + var6, var5);
				final boolean var8 = var6 == 0 || var6 == 15 || var4 == 0 || var4 == 15 || var5 == 0 || var5 == 15;

				if (storageArrays[var3] == null && var8 || storageArrays[var3] != null
						&& storageArrays[var3].getBlockByExtId(var4, var6, var5).getMaterial() == Material.air) {
					final EnumFacing[] var9 = EnumFacing.values();
					final int var10 = var9.length;

					for (int var11 = 0; var11 < var10; ++var11) {
						final EnumFacing var12 = var9[var11];
						final BlockPos var13 = var7.offset(var12);

						if (worldObj.getBlockState(var13).getBlock().getLightValue() > 0) {
							worldObj.checkLight(var13);
						}
					}

					worldObj.checkLight(var7);
				}
			}
		}
	}

	public void func_150809_p() {
		isTerrainPopulated = true;
		isLightPopulated = true;
		final BlockPos var1 = new BlockPos(xPosition << 4, 0, zPosition << 4);

		if (!worldObj.provider.getHasNoSky()) {
			if (worldObj.isAreaLoaded(var1.add(-1, 0, -1), var1.add(16, 63, 16))) {
				label42:

				for (int var2 = 0; var2 < 16; ++var2) {
					for (int var3 = 0; var3 < 16; ++var3) {
						if (!func_150811_f(var2, var3)) {
							isLightPopulated = false;
							break label42;
						}
					}
				}

				if (isLightPopulated) {
					final Iterator var5 = EnumFacing.Plane.HORIZONTAL.iterator();

					while (var5.hasNext()) {
						final EnumFacing var6 = (EnumFacing) var5.next();
						final int var4 = var6.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? 16 : 1;
						worldObj.getChunkFromBlockCoords(var1.offset(var6, var4)).func_180700_a(var6.getOpposite());
					}

					func_177441_y();
				}
			} else {
				isLightPopulated = false;
			}
		}
	}

	private void func_177441_y() {
		for (int var1 = 0; var1 < updateSkylightColumns.length; ++var1) {
			updateSkylightColumns[var1] = true;
		}

		recheckGaps(false);
	}

	private void func_180700_a(final EnumFacing p_180700_1_) {
		if (isTerrainPopulated) {
			int var2;

			if (p_180700_1_ == EnumFacing.EAST) {
				for (var2 = 0; var2 < 16; ++var2) {
					func_150811_f(15, var2);
				}
			} else if (p_180700_1_ == EnumFacing.WEST) {
				for (var2 = 0; var2 < 16; ++var2) {
					func_150811_f(0, var2);
				}
			} else if (p_180700_1_ == EnumFacing.SOUTH) {
				for (var2 = 0; var2 < 16; ++var2) {
					func_150811_f(var2, 15);
				}
			} else if (p_180700_1_ == EnumFacing.NORTH) {
				for (var2 = 0; var2 < 16; ++var2) {
					func_150811_f(var2, 0);
				}
			}
		}
	}

	private boolean func_150811_f(final int p_150811_1_, final int p_150811_2_) {
		final BlockPos var3 = new BlockPos(xPosition << 4, 0, zPosition << 4);
		final int var4 = getTopFilledSegment();
		boolean var5 = false;
		boolean var6 = false;
		int var7;
		BlockPos var8;

		for (var7 = var4 + 16 - 1; var7 > 63 || var7 > 0 && !var6; --var7) {
			var8 = var3.add(p_150811_1_, var7, p_150811_2_);
			final int var9 = this.getBlockLightOpacity(var8);

			if (var9 == 255 && var7 < 63) {
				var6 = true;
			}

			if (!var5 && var9 > 0) {
				var5 = true;
			} else if (var5 && var9 == 0 && !worldObj.checkLight(var8)) {
				return false;
			}
		}

		for (; var7 > 0; --var7) {
			var8 = var3.add(p_150811_1_, var7, p_150811_2_);

			if (this.getBlock(var8).getLightValue() > 0) {
				worldObj.checkLight(var8);
			}
		}

		return true;
	}

	public boolean isLoaded() {
		return isChunkLoaded;
	}

	public void func_177417_c(final boolean p_177417_1_) {
		isChunkLoaded = p_177417_1_;
	}

	public World getWorld() {
		return worldObj;
	}

	public int[] getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(final int[] newHeightMap) {
		if (heightMap.length != newHeightMap.length) {
			logger.warn("Could not set level chunk heightmap, array length is " + newHeightMap.length + " instead of "
					+ heightMap.length);
		} else {
			for (int var2 = 0; var2 < heightMap.length; ++var2) {
				heightMap[var2] = newHeightMap[var2];
			}
		}
	}

	public Map getTileEntityMap() {
		return chunkTileEntityMap;
	}

	public ClassInheratanceMultiMap[] getEntityLists() {
		return entityLists;
	}

	public boolean isTerrainPopulated() {
		return isTerrainPopulated;
	}

	public void setTerrainPopulated(final boolean terrainPopulated) {
		isTerrainPopulated = terrainPopulated;
	}

	public boolean isLightPopulated() {
		return isLightPopulated;
	}

	public void setLightPopulated(final boolean lightPopulated) {
		isLightPopulated = lightPopulated;
	}

	public void setModified(final boolean modified) {
		isModified = modified;
	}

	public void setHasEntities(final boolean hasEntitiesIn) {
		hasEntities = hasEntitiesIn;
	}

	public void setLastSaveTime(final long saveTime) {
		lastSaveTime = saveTime;
	}

	public int getLowestHeight() {
		return heightMapMinimum;
	}

	public long getInhabitedTime() {
		return inhabitedTime;
	}

	public void setInhabitedTime(final long newInhabitedTime) {
		inhabitedTime = newInhabitedTime;
	}

	public static enum EnumCreateEntityType {
		IMMEDIATE("IMMEDIATE", 0), QUEUED("QUEUED", 1), CHECK("CHECK", 2);

		private EnumCreateEntityType(final String p_i45642_1_, final int p_i45642_2_) {}
	}
}
