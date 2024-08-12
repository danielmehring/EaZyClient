package net.minecraft.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;

public class ChunkProviderFlat implements IChunkProvider {

public static final int EaZy = 1727;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final World worldObj;
	private final Random random;
	private final IBlockState[] cachedBlockIDs = new IBlockState[256];
	private final FlatGeneratorInfo flatWorldGenInfo;
	private final List structureGenerators = Lists.newArrayList();
	private final boolean hasDecoration;
	private final boolean hasDungeons;
	private WorldGenLakes waterLakeGenerator;
	private WorldGenLakes lavaLakeGenerator;
	// private static final String __OBFID = "http://https://fuckuskid00000391";

	public ChunkProviderFlat(final World worldIn, final long p_i2004_2_, final boolean p_i2004_4_,
			final String p_i2004_5_) {
		worldObj = worldIn;
		random = new Random(p_i2004_2_);
		flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_i2004_5_);

		if (p_i2004_4_) {
			final Map var6 = flatWorldGenInfo.getWorldFeatures();

			if (var6.containsKey("village")) {
				final Map var7 = (Map) var6.get("village");

				if (!var7.containsKey("size")) {
					var7.put("size", "1");
				}

				structureGenerators.add(new MapGenVillage(var7));
			}

			if (var6.containsKey("biome_1")) {
				structureGenerators.add(new MapGenScatteredFeature((Map) var6.get("biome_1")));
			}

			if (var6.containsKey("mineshaft")) {
				structureGenerators.add(new MapGenMineshaft((Map) var6.get("mineshaft")));
			}

			if (var6.containsKey("stronghold")) {
				structureGenerators.add(new MapGenStronghold((Map) var6.get("stronghold")));
			}

			if (var6.containsKey("oceanmonument")) {
				structureGenerators.add(new StructureOceanMonument((Map) var6.get("oceanmonument")));
			}
		}

		if (flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
			waterLakeGenerator = new WorldGenLakes(Blocks.water);
		}

		if (flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
			lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
		}

		hasDungeons = flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
		boolean var11 = true;
		final Iterator var12 = flatWorldGenInfo.getFlatLayers().iterator();

		while (var12.hasNext()) {
			final FlatLayerInfo var8 = (FlatLayerInfo) var12.next();

			for (int var9 = var8.getMinY(); var9 < var8.getMinY() + var8.getLayerCount(); ++var9) {
				final IBlockState var10 = var8.func_175900_c();

				if (var10.getBlock() != Blocks.air) {
					var11 = false;
					cachedBlockIDs[var9] = var10;
				}
			}
		}

		hasDecoration = var11 ? false : flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
	}

	/**
	 * Will return back a chunk, if it doesn't exist and its not a MP client it
	 * will generates all the blocks for the specified chunk from the map seed
	 * and chunk seed
	 */
	@Override
	public Chunk provideChunk(final int p_73154_1_, final int p_73154_2_) {
		final ChunkPrimer var3 = new ChunkPrimer();
		int var7;

		for (int var4 = 0; var4 < cachedBlockIDs.length; ++var4) {
			final IBlockState var5 = cachedBlockIDs[var4];

			if (var5 != null) {
				for (int var6 = 0; var6 < 16; ++var6) {
					for (var7 = 0; var7 < 16; ++var7) {
						var3.setBlockState(var6, var4, var7, var5);
					}
				}
			}
		}

		final Iterator var8 = structureGenerators.iterator();

		while (var8.hasNext()) {
			final MapGenBase var10 = (MapGenBase) var8.next();
			var10.func_175792_a(this, worldObj, p_73154_1_, p_73154_2_, var3);
		}

		final Chunk var9 = new Chunk(worldObj, var3, p_73154_1_, p_73154_2_);
		final BiomeGenBase[] var11 = worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null,
				p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		final byte[] var12 = var9.getBiomeArray();

		for (var7 = 0; var7 < var12.length; ++var7) {
			var12[var7] = (byte) var11[var7].biomeID;
		}

		var9.generateSkylightMap();
		return var9;
	}

	/**
	 * Checks to see if a chunk exists at x, y
	 */
	@Override
	public boolean chunkExists(final int p_73149_1_, final int p_73149_2_) {
		return true;
	}

	/**
	 * Populates chunk with ores etc etc
	 */
	@Override
	public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
		final int var4 = p_73153_2_ * 16;
		final int var5 = p_73153_3_ * 16;
		final BlockPos var6 = new BlockPos(var4, 0, var5);
		final BiomeGenBase var7 = worldObj.getBiomeGenForCoords(new BlockPos(var4 + 16, 0, var5 + 16));
		boolean var8 = false;
		random.setSeed(worldObj.getSeed());
		final long var9 = random.nextLong() / 2L * 2L + 1L;
		final long var11 = random.nextLong() / 2L * 2L + 1L;
		random.setSeed(p_73153_2_ * var9 + p_73153_3_ * var11 ^ worldObj.getSeed());
		final ChunkCoordIntPair var13 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
		final Iterator var14 = structureGenerators.iterator();

		while (var14.hasNext()) {
			final MapGenStructure var15 = (MapGenStructure) var14.next();
			final boolean var16 = var15.func_175794_a(worldObj, random, var13);

			if (var15 instanceof MapGenVillage) {
				var8 |= var16;
			}
		}

		if (waterLakeGenerator != null && !var8 && random.nextInt(4) == 0) {
			waterLakeGenerator.generate(worldObj, random,
					var6.add(random.nextInt(16) + 8, random.nextInt(256), random.nextInt(16) + 8));
		}

		if (lavaLakeGenerator != null && !var8 && random.nextInt(8) == 0) {
			final BlockPos var17 = var6.add(random.nextInt(16) + 8, random.nextInt(random.nextInt(248) + 8),
					random.nextInt(16) + 8);

			if (var17.getY() < 63 || random.nextInt(10) == 0) {
				lavaLakeGenerator.generate(worldObj, random, var17);
			}
		}

		if (hasDungeons) {
			for (int var18 = 0; var18 < 8; ++var18) {
				new WorldGenDungeons().generate(worldObj, random,
						var6.add(random.nextInt(16) + 8, random.nextInt(256), random.nextInt(16) + 8));
			}
		}

		if (hasDecoration) {
			var7.func_180624_a(worldObj, random, new BlockPos(var4, 0, var5));
		}
	}

	@Override
	public boolean func_177460_a(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_,
			final int p_177460_4_) {
		return false;
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
		return false;
	}

	/**
	 * Returns if the IChunkProvider supports saving.
	 */
	@Override
	public boolean canSave() {
		return true;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "FlatLevelSource";
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		final BiomeGenBase var3 = worldObj.getBiomeGenForCoords(p_177458_2_);
		return var3.getSpawnableList(p_177458_1_);
	}

	@Override
	public BlockPos func_180513_a(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
		if ("Stronghold".equals(p_180513_2_)) {
			final Iterator var4 = structureGenerators.iterator();

			while (var4.hasNext()) {
				final MapGenStructure var5 = (MapGenStructure) var4.next();

				if (var5 instanceof MapGenStronghold) {
					return var5.func_180706_b(worldIn, p_180513_3_);
				}
			}
		}

		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
		final Iterator var4 = structureGenerators.iterator();

		while (var4.hasNext()) {
			final MapGenStructure var5 = (MapGenStructure) var4.next();
			var5.func_175792_a(this, worldObj, p_180514_2_, p_180514_3_, (ChunkPrimer) null);
		}
	}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}
}
