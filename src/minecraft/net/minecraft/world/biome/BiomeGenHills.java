package net.minecraft.world.biome;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenHills extends BiomeGenBase {

public static final int EaZy = 1682;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final WorldGenerator theWorldGenerator;
	private final WorldGenTaiga2 field_150634_aD;
	private final int field_150635_aE;
	private final int field_150636_aF;
	private final int field_150637_aG;
	private int field_150638_aH;
	// private static final String __OBFID = "http://https://fuckuskid00000168";

	protected BiomeGenHills(final int p_i45373_1_, final boolean p_i45373_2_) {
		super(p_i45373_1_);
		theWorldGenerator = new WorldGenMinable(Blocks.monster_egg.getDefaultState()
				.withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.STONE), 9);
		field_150634_aD = new WorldGenTaiga2(false);
		field_150635_aE = 0;
		field_150636_aF = 1;
		field_150637_aG = 2;
		field_150638_aH = field_150635_aE;

		if (p_i45373_2_) {
			theBiomeDecorator.treesPerChunk = 3;
			field_150638_aH = field_150636_aF;
		}
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return p_150567_1_.nextInt(3) > 0 ? field_150634_aD : super.genBigTreeChance(p_150567_1_);
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
		int var4 = 3 + p_180624_2_.nextInt(6);
		int var5;
		int var6;
		int var7;

		for (var5 = 0; var5 < var4; ++var5) {
			var6 = p_180624_2_.nextInt(16);
			var7 = p_180624_2_.nextInt(28) + 4;
			final int var8 = p_180624_2_.nextInt(16);
			final BlockPos var9 = p_180624_3_.add(var6, var7, var8);

			if (worldIn.getBlockState(var9).getBlock() == Blocks.stone) {
				worldIn.setBlockState(var9, Blocks.emerald_ore.getDefaultState(), 2);
			}
		}

		for (var4 = 0; var4 < 7; ++var4) {
			var5 = p_180624_2_.nextInt(16);
			var6 = p_180624_2_.nextInt(64);
			var7 = p_180624_2_.nextInt(16);
			theWorldGenerator.generate(worldIn, p_180624_2_, p_180624_3_.add(var5, var6, var7));
		}
	}

	@Override
	public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
			final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
		topBlock = Blocks.grass.getDefaultState();
		fillerBlock = Blocks.dirt.getDefaultState();

		if ((p_180622_6_ < -1.0D || p_180622_6_ > 2.0D) && field_150638_aH == field_150637_aG) {
			topBlock = Blocks.gravel.getDefaultState();
			fillerBlock = Blocks.gravel.getDefaultState();
		} else if (p_180622_6_ > 1.0D && field_150638_aH != field_150636_aF) {
			topBlock = Blocks.stone.getDefaultState();
			fillerBlock = Blocks.stone.getDefaultState();
		}

		func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
	}

	/**
	 * this creates a mutation specific to Hills biomes
	 */
	private BiomeGenHills mutateHills(final BiomeGenBase p_150633_1_) {
		field_150638_aH = field_150637_aG;
		func_150557_a(p_150633_1_.color, true);
		setBiomeName(p_150633_1_.biomeName + " M");
		setHeight(new BiomeGenBase.Height(p_150633_1_.minHeight, p_150633_1_.maxHeight));
		setTemperatureRainfall(p_150633_1_.temperature, p_150633_1_.rainfall);
		return this;
	}

	@Override
	protected BiomeGenBase createMutatedBiome(final int p_180277_1_) {
		return new BiomeGenHills(p_180277_1_, false).mutateHills(this);
	}
}
