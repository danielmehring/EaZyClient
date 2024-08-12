package net.minecraft.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

import java.util.Random;

public class BiomeGenSnow extends BiomeGenBase {

public static final int EaZy = 1691;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean field_150615_aC;
	private final WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
	private final WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);
	// private static final String __OBFID = "http://https://fuckuskid00000174";

	public BiomeGenSnow(final int p_i45378_1_, final boolean p_i45378_2_) {
		super(p_i45378_1_);
		field_150615_aC = p_i45378_2_;

		if (p_i45378_2_) {
			topBlock = Blocks.snow.getDefaultState();
		}

		spawnableCreatureList.clear();
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		if (field_150615_aC) {
			int var4;
			int var5;
			int var6;

			for (var4 = 0; var4 < 3; ++var4) {
				var5 = p_180624_2_.nextInt(16) + 8;
				var6 = p_180624_2_.nextInt(16) + 8;
				field_150616_aD.generate(worldIn, p_180624_2_, worldIn.getHorizon(p_180624_3_.add(var5, 0, var6)));
			}

			for (var4 = 0; var4 < 2; ++var4) {
				var5 = p_180624_2_.nextInt(16) + 8;
				var6 = p_180624_2_.nextInt(16) + 8;
				field_150617_aE.generate(worldIn, p_180624_2_, worldIn.getHorizon(p_180624_3_.add(var5, 0, var6)));
			}
		}

		super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return new WorldGenTaiga2(false);
	}

	@Override
	protected BiomeGenBase createMutatedBiome(final int p_180277_1_) {
		final BiomeGenBase var2 = new BiomeGenSnow(p_180277_1_, true).func_150557_a(13828095, true)
				.setBiomeName(biomeName + " Spikes").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F)
				.setHeight(new BiomeGenBase.Height(minHeight + 0.1F, maxHeight + 0.1F));
		var2.minHeight = minHeight + 0.3F;
		var2.maxHeight = maxHeight + 0.4F;
		return var2;
	}
}
