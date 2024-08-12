package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class ChunkProviderDebug implements IChunkProvider {

public static final int EaZy = 1725;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final List field_177464_a = Lists.newArrayList();
	private static final int field_177462_b;
	private final World field_177463_c;
	// private static final String __OBFID = "http://https://fuckuskid00002002";

	public ChunkProviderDebug(final World worldIn) {
		field_177463_c = worldIn;
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

		for (int var4 = 0; var4 < 16; ++var4) {
			for (int var5 = 0; var5 < 16; ++var5) {
				final int var6 = p_73154_1_ * 16 + var4;
				var7 = p_73154_2_ * 16 + var5;
				var3.setBlockState(var4, 60, var5, Blocks.barrier.getDefaultState());
				final IBlockState var8 = func_177461_b(var6, var7);

				if (var8 != null) {
					var3.setBlockState(var4, 70, var5, var8);
				}
			}
		}

		final Chunk var9 = new Chunk(field_177463_c, var3, p_73154_1_, p_73154_2_);
		var9.generateSkylightMap();
		final BiomeGenBase[] var10 = field_177463_c.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[]) null,
				p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		final byte[] var11 = var9.getBiomeArray();

		for (var7 = 0; var7 < var11.length; ++var7) {
			var11[var7] = (byte) var10[var7].biomeID;
		}

		var9.generateSkylightMap();
		return var9;
	}

	public static IBlockState func_177461_b(int p_177461_0_, int p_177461_1_) {
		IBlockState var2 = null;

		if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
			p_177461_0_ /= 2;
			p_177461_1_ /= 2;

			if (p_177461_0_ <= field_177462_b && p_177461_1_ <= field_177462_b) {
				final int var3 = MathHelper.abs_int(p_177461_0_ * field_177462_b + p_177461_1_);

				if (var3 < field_177464_a.size()) {
					var2 = (IBlockState) field_177464_a.get(var3);
				}
			}
		}

		return var2;
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
	public void populate(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {}

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
		return "DebugLevelSource";
	}

	@Override
	public List func_177458_a(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
		final BiomeGenBase var3 = field_177463_c.getBiomeGenForCoords(p_177458_2_);
		return var3.getSpawnableList(p_177458_1_);
	}

	@Override
	public BlockPos func_180513_a(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
		return null;
	}

	@Override
	public int getLoadedChunkCount() {
		return 0;
	}

	@Override
	public void func_180514_a(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {}

	@Override
	public Chunk func_177459_a(final BlockPos p_177459_1_) {
		return provideChunk(p_177459_1_.getX() >> 4, p_177459_1_.getZ() >> 4);
	}

	static {
		final Iterator var0 = Block.blockRegistry.iterator();

		while (var0.hasNext()) {
			final Block var1 = (Block) var0.next();
			field_177464_a.addAll(var1.getBlockState().getValidStates());
		}

		field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float(field_177464_a.size()));
	}
}
