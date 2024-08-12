package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

public class EmptyChunk extends Chunk {

public static final int EaZy = 1702;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000372";

	public EmptyChunk(final World worldIn, final int x, final int z) {
		super(worldIn, x, z);
	}

	/**
	 * Checks whether the chunk is at the X/Z location specified
	 */
	@Override
	public boolean isAtLocation(final int x, final int z) {
		return x == xPosition && z == zPosition;
	}

	/**
	 * Returns the value in the height map at this x, z coordinate in the chunk
	 */
	@Override
	public int getHeight(final int x, final int z) {
		return 0;
	}

	/**
	 * Generates the height map for a chunk from scratch
	 */
	@Override
	public void generateHeightMap() {}

	/**
	 * Generates the initial skylight map for the chunk upon generation or load.
	 */
	@Override
	public void generateSkylightMap() {}

	@Override
	public Block getBlock(final BlockPos pos) {
		return Blocks.air;
	}

	@Override
	public int getBlockLightOpacity(final BlockPos pos) {
		return 255;
	}

	@Override
	public int getBlockMetadata(final BlockPos pos) {
		return 0;
	}

	@Override
	public int getLightFor(final EnumSkyBlock p_177413_1_, final BlockPos p_177413_2_) {
		return p_177413_1_.defaultLightValue;
	}

	@Override
	public void setLightFor(final EnumSkyBlock p_177431_1_, final BlockPos p_177431_2_, final int p_177431_3_) {}

	@Override
	public int setLight(final BlockPos p_177443_1_, final int p_177443_2_) {
		return 0;
	}

	/**
	 * Adds an entity to the chunk. Args: entity
	 */
	@Override
	public void addEntity(final Entity entityIn) {}

	/**
	 * removes entity using its y chunk coordinate as its index
	 */
	@Override
	public void removeEntity(final Entity p_76622_1_) {}

	/**
	 * Removes entity at the specified index from the entity array.
	 */
	@Override
	public void removeEntityAtIndex(final Entity p_76608_1_, final int p_76608_2_) {}

	@Override
	public boolean canSeeSky(final BlockPos pos) {
		return false;
	}

	@Override
	public TileEntity func_177424_a(final BlockPos p_177424_1_, final Chunk.EnumCreateEntityType p_177424_2_) {
		return null;
	}

	@Override
	public void addTileEntity(final TileEntity tileEntityIn) {}

	@Override
	public void addTileEntity(final BlockPos pos, final TileEntity tileEntityIn) {}

	@Override
	public void removeTileEntity(final BlockPos pos) {}

	/**
	 * Called when this Chunk is loaded by the ChunkProvider
	 */
	@Override
	public void onChunkLoad() {}

	/**
	 * Called when this Chunk is unloaded by the ChunkProvider
	 */
	@Override
	public void onChunkUnload() {}

	/**
	 * Sets the isModified flag for this Chunk
	 */
	@Override
	public void setChunkModified() {}

	@Override
	public void func_177414_a(final Entity p_177414_1_, final AxisAlignedBB p_177414_2_, final List p_177414_3_,
			final Predicate p_177414_4_) {}

	@Override
	public void func_177430_a(final Class p_177430_1_, final AxisAlignedBB p_177430_2_, final List p_177430_3_,
			final Predicate p_177430_4_) {}

	/**
	 * Returns true if this Chunk needs to be saved
	 */
	@Override
	public boolean needsSaving(final boolean p_76601_1_) {
		return false;
	}

	@Override
	public Random getRandomWithSeed(final long seed) {
		return new Random(getWorld().getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611
				+ zPosition * zPosition * 4392871L + zPosition * 389711 ^ seed);
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	/**
	 * Returns whether the ExtendedBlockStorages containing levels (in blocks)
	 * from arg 1 to arg 2 are fully empty (true) or not (false).
	 */
	@Override
	public boolean getAreLevelsEmpty(final int p_76606_1_, final int p_76606_2_) {
		return true;
	}
}
