package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

import com.google.common.collect.Lists;

public class BiomeGenMutated extends BiomeGenBase {

public static final int EaZy = 1686;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected BiomeGenBase baseBiome;
	// private static final String __OBFID = "http://https://fuckuskid00000178";

	public BiomeGenMutated(final int p_i45381_1_, final BiomeGenBase p_i45381_2_) {
		super(p_i45381_1_);
		baseBiome = p_i45381_2_;
		func_150557_a(p_i45381_2_.color, true);
		biomeName = p_i45381_2_.biomeName + " M";
		topBlock = p_i45381_2_.topBlock;
		fillerBlock = p_i45381_2_.fillerBlock;
		fillerBlockMetadata = p_i45381_2_.fillerBlockMetadata;
		minHeight = p_i45381_2_.minHeight;
		maxHeight = p_i45381_2_.maxHeight;
		temperature = p_i45381_2_.temperature;
		rainfall = p_i45381_2_.rainfall;
		waterColorMultiplier = p_i45381_2_.waterColorMultiplier;
		enableSnow = p_i45381_2_.enableSnow;
		enableRain = p_i45381_2_.enableRain;
		spawnableCreatureList = Lists.newArrayList(p_i45381_2_.spawnableCreatureList);
		spawnableMonsterList = Lists.newArrayList(p_i45381_2_.spawnableMonsterList);
		spawnableCaveCreatureList = Lists.newArrayList(p_i45381_2_.spawnableCaveCreatureList);
		spawnableWaterCreatureList = Lists.newArrayList(p_i45381_2_.spawnableWaterCreatureList);
		temperature = p_i45381_2_.temperature;
		rainfall = p_i45381_2_.rainfall;
		minHeight = p_i45381_2_.minHeight + 0.1F;
		maxHeight = p_i45381_2_.maxHeight + 0.2F;
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		baseBiome.theBiomeDecorator.func_180292_a(worldIn, p_180624_2_, this, p_180624_3_);
	}

	@Override
	public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
			final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
		baseBiome.genTerrainBlocks(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
	}

	/**
	 * returns the chance a creature has to spawn.
	 */
	@Override
	public float getSpawningChance() {
		return baseBiome.getSpawningChance();
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return baseBiome.genBigTreeChance(p_150567_1_);
	}

	@Override
	public int func_180625_c(final BlockPos p_180625_1_) {
		return baseBiome.func_180625_c(p_180625_1_);
	}

	@Override
	public int func_180627_b(final BlockPos p_180627_1_) {
		return baseBiome.func_180627_b(p_180627_1_);
	}

	@Override
	public Class getBiomeClass() {
		return baseBiome.getBiomeClass();
	}

	/**
	 * returns true if the biome specified is equal to this biome
	 */
	@Override
	public boolean isEqualTo(final BiomeGenBase p_150569_1_) {
		return baseBiome.isEqualTo(p_150569_1_);
	}

	@Override
	public BiomeGenBase.TempCategory getTempCategory() {
		return baseBiome.getTempCategory();
	}
}
