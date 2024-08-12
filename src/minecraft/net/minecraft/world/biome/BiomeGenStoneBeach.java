package net.minecraft.world.biome;

import net.minecraft.init.Blocks;

public class BiomeGenStoneBeach extends BiomeGenBase {

public static final int EaZy = 1692;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000184";

	public BiomeGenStoneBeach(final int p_i45384_1_) {
		super(p_i45384_1_);
		spawnableCreatureList.clear();
		topBlock = Blocks.stone.getDefaultState();
		fillerBlock = Blocks.stone.getDefaultState();
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 0;
		theBiomeDecorator.reedsPerChunk = 0;
		theBiomeDecorator.cactiPerChunk = 0;
	}
}
