package net.minecraft.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

import java.util.Random;

public class BiomeGenDesert extends BiomeGenBase {

public static final int EaZy = 1678;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000167";

	public BiomeGenDesert(final int p_i1977_1_) {
		super(p_i1977_1_);
		spawnableCreatureList.clear();
		topBlock = Blocks.sand.getDefaultState();
		fillerBlock = Blocks.sand.getDefaultState();
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 2;
		theBiomeDecorator.reedsPerChunk = 50;
		theBiomeDecorator.cactiPerChunk = 10;
		spawnableCreatureList.clear();
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);

		if (p_180624_2_.nextInt(1000) == 0) {
			final int var4 = p_180624_2_.nextInt(16) + 8;
			final int var5 = p_180624_2_.nextInt(16) + 8;
			final BlockPos var6 = worldIn.getHorizon(p_180624_3_.add(var4, 0, var5)).offsetUp();
			new WorldGenDesertWells().generate(worldIn, p_180624_2_, var6);
		}
	}
}
