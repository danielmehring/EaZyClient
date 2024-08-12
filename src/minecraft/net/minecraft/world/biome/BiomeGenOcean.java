package net.minecraft.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeGenOcean extends BiomeGenBase {

public static final int EaZy = 1687;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000179";

	public BiomeGenOcean(final int p_i1985_1_) {
		super(p_i1985_1_);
		spawnableCreatureList.clear();
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory() {
		return BiomeGenBase.TempCategory.OCEAN;
	}

	@Override
	public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
			final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
		super.genTerrainBlocks(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
	}
}
