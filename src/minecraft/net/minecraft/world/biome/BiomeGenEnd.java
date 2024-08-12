package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;

public class BiomeGenEnd extends BiomeGenBase {

public static final int EaZy = 1679;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000187";

	public BiomeGenEnd(final int p_i1990_1_) {
		super(p_i1990_1_);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
		topBlock = Blocks.dirt.getDefaultState();
		fillerBlock = Blocks.dirt.getDefaultState();
		theBiomeDecorator = new BiomeEndDecorator();
	}

	/**
	 * takes temperature, returns color
	 */
	@Override
	public int getSkyColorByTemp(final float p_76731_1_) {
		return 0;
	}
}
