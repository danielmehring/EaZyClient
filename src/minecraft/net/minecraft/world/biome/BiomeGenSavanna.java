package net.minecraft.world.biome;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

import java.util.Random;

public class BiomeGenSavanna extends BiomeGenBase {

public static final int EaZy = 1690;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);
	// private static final String __OBFID = "http://https://fuckuskid00000182";

	protected BiomeGenSavanna(final int p_i45383_1_) {
		super(p_i45383_1_);
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
		theBiomeDecorator.treesPerChunk = 1;
		theBiomeDecorator.flowersPerChunk = 4;
		theBiomeDecorator.grassPerChunk = 20;
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return p_150567_1_.nextInt(5) > 0 ? field_150627_aC : worldGeneratorTrees;
	}

	@Override
	protected BiomeGenBase createMutatedBiome(final int p_180277_1_) {
		final BiomeGenSavanna.Mutated var2 = new BiomeGenSavanna.Mutated(p_180277_1_, this);
		var2.temperature = (temperature + 1.0F) * 0.5F;
		var2.minHeight = minHeight * 0.5F + 0.3F;
		var2.maxHeight = maxHeight * 0.5F + 1.2F;
		return var2;
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.GRASS);

		for (int var4 = 0; var4 < 7; ++var4) {
			final int var5 = p_180624_2_.nextInt(16) + 8;
			final int var6 = p_180624_2_.nextInt(16) + 8;
			final int var7 = p_180624_2_.nextInt(worldIn.getHorizon(p_180624_3_.add(var5, 0, var6)).getY() + 32);
			field_180280_ag.generate(worldIn, p_180624_2_, p_180624_3_.add(var5, var7, var6));
		}

		super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
	}

	public static class Mutated extends BiomeGenMutated {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000183";

		public Mutated(final int p_i45382_1_, final BiomeGenBase p_i45382_2_) {
			super(p_i45382_1_, p_i45382_2_);
			theBiomeDecorator.treesPerChunk = 2;
			theBiomeDecorator.flowersPerChunk = 2;
			theBiomeDecorator.grassPerChunk = 5;
		}

		@Override
		public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
				final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
			topBlock = Blocks.grass.getDefaultState();
			fillerBlock = Blocks.dirt.getDefaultState();

			if (p_180622_6_ > 1.75D) {
				topBlock = Blocks.stone.getDefaultState();
				fillerBlock = Blocks.stone.getDefaultState();
			} else if (p_180622_6_ > -0.5D) {
				topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT,
						BlockDirt.DirtType.COARSE_DIRT);
			}

			func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
		}

		@Override
		public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
			theBiomeDecorator.func_180292_a(worldIn, p_180624_2_, this, p_180624_3_);
		}
	}
}
