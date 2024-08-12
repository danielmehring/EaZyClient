package net.minecraft.world.biome;

import net.minecraft.init.Blocks;

public class BiomeGenBeach extends BiomeGenBase {

public static final int EaZy = 1677;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000157";

	public BiomeGenBeach(final int p_i1969_1_) {
		super(p_i1969_1_);
		spawnableCreatureList.clear();
		topBlock = Blocks.sand.getDefaultState();
		fillerBlock = Blocks.sand.getDefaultState();
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 0;
		theBiomeDecorator.reedsPerChunk = 0;
		theBiomeDecorator.cactiPerChunk = 0;
	}
}
