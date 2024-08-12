package net.minecraft.world.biome;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenSpikes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeEndDecorator extends BiomeDecorator {

public static final int EaZy = 1675;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected WorldGenerator spikeGen;
	// private static final String __OBFID = "http://https://fuckuskid00000188";

	public BiomeEndDecorator() {
		spikeGen = new WorldGenSpikes(Blocks.end_stone);
	}

	@Override
	protected void genDecorations(final BiomeGenBase p_150513_1_) {
		generateOres();

		if (randomGenerator.nextInt(5) == 0) {
			final int var2 = randomGenerator.nextInt(16) + 8;
			final int var3 = randomGenerator.nextInt(16) + 8;
			spikeGen.generate(currentWorld, randomGenerator,
					currentWorld.func_175672_r(field_180294_c.add(var2, 0, var3)));
		}

		if (field_180294_c.getX() == 0 && field_180294_c.getZ() == 0) {
			final EntityDragon var4 = new EntityDragon(currentWorld);
			var4.setLocationAndAngles(0.0D, 128.0D, 0.0D, randomGenerator.nextFloat() * 360.0F, 0.0F);
			currentWorld.spawnEntityInWorld(var4);
		}
	}
}
