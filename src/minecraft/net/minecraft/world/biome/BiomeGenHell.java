package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;

public class BiomeGenHell extends BiomeGenBase {

public static final int EaZy = 1681;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000173";

	public BiomeGenHell(final int p_i1981_1_) {
		super(p_i1981_1_);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGhast.class, 50, 4, 4));
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
	}
}
