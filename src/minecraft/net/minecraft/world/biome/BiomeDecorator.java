package net.minecraft.world.biome;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeDecorator {

public static final int EaZy = 1674;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The world the BiomeDecorator is currently decorating */
	protected World currentWorld;

	/** The Biome Decorator's random number generator. */
	protected Random randomGenerator;
	protected BlockPos field_180294_c;
	protected ChunkProviderSettings field_180293_d;

	/** The clay generator. */
	protected WorldGenerator clayGen = new WorldGenClay(4);

	/** The sand generator. */
	protected WorldGenerator sandGen;

	/** The gravel generator. */
	protected WorldGenerator gravelAsSandGen;

	/** The dirt generator. */
	protected WorldGenerator dirtGen;
	protected WorldGenerator gravelGen;
	protected WorldGenerator field_180296_j;
	protected WorldGenerator field_180297_k;
	protected WorldGenerator field_180295_l;
	protected WorldGenerator coalGen;
	protected WorldGenerator ironGen;

	/** Field that holds gold WorldGenMinable */
	protected WorldGenerator goldGen;
	protected WorldGenerator field_180299_p;
	protected WorldGenerator field_180298_q;

	/** Field that holds Lapis WorldGenMinable */
	protected WorldGenerator lapisGen;
	protected WorldGenFlowers yellowFlowerGen;

	/** Field that holds mushroomBrown WorldGenFlowers */
	protected WorldGenerator mushroomBrownGen;

	/** Field that holds mushroomRed WorldGenFlowers */
	protected WorldGenerator mushroomRedGen;

	/** Field that holds big mushroom generator */
	protected WorldGenerator bigMushroomGen;

	/** Field that holds WorldGenReed */
	protected WorldGenerator reedGen;

	/** Field that holds WorldGenCactus */
	protected WorldGenerator cactusGen;

	/** The water lily generation! */
	protected WorldGenerator waterlilyGen;

	/** Amount of waterlilys per chunk. */
	protected int waterlilyPerChunk;

	/**
	 * The number of trees to attempt to generate per chunk. Up to 10 in
	 * forests, none in deserts.
	 */
	protected int treesPerChunk;

	/**
	 * The number of yellow flower patches to generate per chunk. The game
	 * generates much less than this number, since it attempts to generate them
	 * at a random altitude.
	 */
	protected int flowersPerChunk;

	/** The amount of tall grass to generate per chunk. */
	protected int grassPerChunk;

	/**
	 * The number of dead bushes to generate per chunk. Used in deserts and
	 * swamps.
	 */
	protected int deadBushPerChunk;

	/**
	 * The number of extra mushroom patches per chunk. It generates 1/4 this
	 * number in brown mushroom patches, and 1/8 this number in red mushroom
	 * patches. These mushrooms go beyond the default base number of mushrooms.
	 */
	protected int mushroomsPerChunk;

	/**
	 * The number of reeds to generate per chunk. Reeds won't generate if the
	 * randomly selected placement is unsuitable.
	 */
	protected int reedsPerChunk;

	/**
	 * The number of cactus plants to generate per chunk. Cacti only work on
	 * sand.
	 */
	protected int cactiPerChunk;

	/**
	 * The number of sand patches to generate per chunk. Sand patches only
	 * generate when part of it is underwater.
	 */
	protected int sandPerChunk;

	/**
	 * The number of sand patches to generate per chunk. Sand patches only
	 * generate when part of it is underwater. There appear to be two separate
	 * fields for this.
	 */
	protected int sandPerChunk2;

	/**
	 * The number of clay patches to generate per chunk. Only generates when
	 * part of it is underwater.
	 */
	protected int clayPerChunk;

	/** Amount of big mushrooms per chunk */
	protected int bigMushroomsPerChunk;

	/** True if decorator should generate surface lava & water */
	public boolean generateLakes;
	// private static final String __OBFID = "http://https://fuckuskid00000164";

	public BiomeDecorator() {
		sandGen = new WorldGenSand(Blocks.sand, 7);
		gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
		yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
		mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
		mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
		bigMushroomGen = new WorldGenBigMushroom();
		reedGen = new WorldGenReed();
		cactusGen = new WorldGenCactus();
		waterlilyGen = new WorldGenWaterlily();
		flowersPerChunk = 2;
		grassPerChunk = 1;
		sandPerChunk = 1;
		sandPerChunk2 = 3;
		clayPerChunk = 1;
		generateLakes = true;
	}

	public void func_180292_a(final World worldIn, final Random p_180292_2_, final BiomeGenBase p_180292_3_,
			final BlockPos p_180292_4_) {
		if (currentWorld != null) {
			throw new RuntimeException("Already decorating");
		} else {
			currentWorld = worldIn;
			final String var5 = worldIn.getWorldInfo().getGeneratorOptions();

			if (var5 != null) {
				field_180293_d = ChunkProviderSettings.Factory.func_177865_a(var5).func_177864_b();
			} else {
				field_180293_d = ChunkProviderSettings.Factory.func_177865_a("").func_177864_b();
			}

			randomGenerator = p_180292_2_;
			field_180294_c = p_180292_4_;
			dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), field_180293_d.field_177789_I);
			gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), field_180293_d.field_177785_M);
			field_180296_j = new WorldGenMinable(
					Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.GRANITE),
					field_180293_d.field_177796_Q);
			field_180297_k = new WorldGenMinable(
					Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.DIORITE),
					field_180293_d.field_177792_U);
			field_180295_l = new WorldGenMinable(
					Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.ANDESITE),
					field_180293_d.field_177800_Y);
			coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), field_180293_d.field_177844_ac);
			ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), field_180293_d.field_177848_ag);
			goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), field_180293_d.field_177828_ak);
			field_180299_p = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), field_180293_d.field_177836_ao);
			field_180298_q = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), field_180293_d.field_177814_as);
			lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), field_180293_d.field_177822_aw);
			genDecorations(p_180292_3_);
			currentWorld = null;
			randomGenerator = null;
		}
	}

	protected void genDecorations(final BiomeGenBase p_150513_1_) {
		generateOres();
		int var2;
		int var3;
		int var4;

		for (var2 = 0; var2 < sandPerChunk2; ++var2) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			sandGen.generate(currentWorld, randomGenerator,
					currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
		}

		for (var2 = 0; var2 < clayPerChunk; ++var2) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			clayGen.generate(currentWorld, randomGenerator,
					currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
		}

		for (var2 = 0; var2 < sandPerChunk; ++var2) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			gravelAsSandGen.generate(currentWorld, randomGenerator,
					currentWorld.func_175672_r(field_180294_c.add(var3, 0, var4)));
		}

		var2 = treesPerChunk;

		if (randomGenerator.nextInt(10) == 0) {
			++var2;
		}

		int var5;
		BlockPos var7;

		for (var3 = 0; var3 < var2; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			final WorldGenAbstractTree var6 = p_150513_1_.genBigTreeChance(randomGenerator);
			var6.func_175904_e();
			var7 = currentWorld.getHorizon(field_180294_c.add(var4, 0, var5));

			if (var6.generate(currentWorld, randomGenerator, var7)) {
				var6.func_180711_a(currentWorld, randomGenerator, var7);
			}
		}

		for (var3 = 0; var3 < bigMushroomsPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			bigMushroomGen.generate(currentWorld, randomGenerator,
					currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)));
		}

		int var11;

		for (var3 = 0; var3 < flowersPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() + 32);
			var7 = field_180294_c.add(var4, var11, var5);
			final BlockFlower.EnumFlowerType var8 = p_150513_1_.pickRandomFlower(randomGenerator, var7);
			final BlockFlower var9 = var8.func_176964_a().func_180346_a();

			if (var9.getMaterial() != Material.air) {
				yellowFlowerGen.setGeneratedBlock(var9, var8);
				yellowFlowerGen.generate(currentWorld, randomGenerator, var7);
			}
		}

		for (var3 = 0; var3 < grassPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			p_150513_1_.getRandomWorldGenForGrass(randomGenerator).generate(currentWorld, randomGenerator,
					field_180294_c.add(var4, var11, var5));
		}

		for (var3 = 0; var3 < deadBushPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			new WorldGenDeadBush().generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
		}

		var3 = 0;

		while (var3 < waterlilyPerChunk) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			var7 = field_180294_c.add(var4, var11, var5);

			while (true) {
				if (var7.getY() > 0) {
					final BlockPos var13 = var7.offsetDown();

					if (currentWorld.isAirBlock(var13)) {
						var7 = var13;
						continue;
					}
				}

				waterlilyGen.generate(currentWorld, randomGenerator, var7);
				++var3;
				break;
			}
		}

		for (var3 = 0; var3 < mushroomsPerChunk; ++var3) {
			if (randomGenerator.nextInt(4) == 0) {
				var4 = randomGenerator.nextInt(16) + 8;
				var5 = randomGenerator.nextInt(16) + 8;
				final BlockPos var12 = currentWorld.getHorizon(field_180294_c.add(var4, 0, var5));
				mushroomBrownGen.generate(currentWorld, randomGenerator, var12);
			}

			if (randomGenerator.nextInt(8) == 0) {
				var4 = randomGenerator.nextInt(16) + 8;
				var5 = randomGenerator.nextInt(16) + 8;
				var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
				var7 = field_180294_c.add(var4, var11, var5);
				mushroomRedGen.generate(currentWorld, randomGenerator, var7);
			}
		}

		if (randomGenerator.nextInt(4) == 0) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
			mushroomBrownGen.generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
		}

		if (randomGenerator.nextInt(8) == 0) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
			mushroomRedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
		}

		for (var3 = 0; var3 < reedsPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
		}

		for (var3 = 0; var3 < 10; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
		}

		if (randomGenerator.nextInt(32) == 0) {
			var3 = randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var3, 0, var4)).getY() * 2);
			new WorldGenPumpkin().generate(currentWorld, randomGenerator, field_180294_c.add(var3, var5, var4));
		}

		for (var3 = 0; var3 < cactiPerChunk; ++var3) {
			var4 = randomGenerator.nextInt(16) + 8;
			var5 = randomGenerator.nextInt(16) + 8;
			var11 = randomGenerator.nextInt(currentWorld.getHorizon(field_180294_c.add(var4, 0, var5)).getY() * 2);
			cactusGen.generate(currentWorld, randomGenerator, field_180294_c.add(var4, var11, var5));
		}

		if (generateLakes) {
			BlockPos var10;

			for (var3 = 0; var3 < 50; ++var3) {
				var10 = field_180294_c.add(randomGenerator.nextInt(16) + 8,
						randomGenerator.nextInt(randomGenerator.nextInt(248) + 8), randomGenerator.nextInt(16) + 8);
				new WorldGenLiquids(Blocks.flowing_water).generate(currentWorld, randomGenerator, var10);
			}

			for (var3 = 0; var3 < 20; ++var3) {
				var10 = field_180294_c.add(randomGenerator.nextInt(16) + 8,
						randomGenerator.nextInt(randomGenerator.nextInt(randomGenerator.nextInt(240) + 8) + 8),
						randomGenerator.nextInt(16) + 8);
				new WorldGenLiquids(Blocks.flowing_lava).generate(currentWorld, randomGenerator, var10);
			}
		}
	}

	/**
	 * Standard ore generation helper. Generates most ores.
	 */
	protected void genStandardOre1(final int p_76795_1_, final WorldGenerator p_76795_2_, int p_76795_3_,
			int p_76795_4_) {
		int var5;

		if (p_76795_4_ < p_76795_3_) {
			var5 = p_76795_3_;
			p_76795_3_ = p_76795_4_;
			p_76795_4_ = var5;
		} else if (p_76795_4_ == p_76795_3_) {
			if (p_76795_3_ < 255) {
				++p_76795_4_;
			} else {
				--p_76795_3_;
			}
		}

		for (var5 = 0; var5 < p_76795_1_; ++var5) {
			final BlockPos var6 = field_180294_c.add(randomGenerator.nextInt(16),
					randomGenerator.nextInt(p_76795_4_ - p_76795_3_) + p_76795_3_, randomGenerator.nextInt(16));
			p_76795_2_.generate(currentWorld, randomGenerator, var6);
		}
	}

	/**
	 * Standard ore generation helper. Generates Lapis Lazuli.
	 */
	protected void genStandardOre2(final int p_76793_1_, final WorldGenerator p_76793_2_, final int p_76793_3_,
			final int p_76793_4_) {
		for (int var5 = 0; var5 < p_76793_1_; ++var5) {
			final BlockPos var6 = field_180294_c.add(randomGenerator.nextInt(16),
					randomGenerator.nextInt(p_76793_4_) + randomGenerator.nextInt(p_76793_4_) + p_76793_3_ - p_76793_4_,
					randomGenerator.nextInt(16));
			p_76793_2_.generate(currentWorld, randomGenerator, var6);
		}
	}

	/**
	 * Generates ores in the current chunk
	 */
	protected void generateOres() {
		genStandardOre1(field_180293_d.field_177790_J, dirtGen, field_180293_d.field_177791_K,
				field_180293_d.field_177784_L);
		genStandardOre1(field_180293_d.field_177786_N, gravelGen, field_180293_d.field_177787_O,
				field_180293_d.field_177797_P);
		genStandardOre1(field_180293_d.field_177795_V, field_180297_k, field_180293_d.field_177794_W,
				field_180293_d.field_177801_X);
		genStandardOre1(field_180293_d.field_177799_R, field_180296_j, field_180293_d.field_177798_S,
				field_180293_d.field_177793_T);
		genStandardOre1(field_180293_d.field_177802_Z, field_180295_l, field_180293_d.field_177846_aa,
				field_180293_d.field_177847_ab);
		genStandardOre1(field_180293_d.field_177845_ad, coalGen, field_180293_d.field_177851_ae,
				field_180293_d.field_177853_af);
		genStandardOre1(field_180293_d.field_177849_ah, ironGen, field_180293_d.field_177832_ai,
				field_180293_d.field_177834_aj);
		genStandardOre1(field_180293_d.field_177830_al, goldGen, field_180293_d.field_177840_am,
				field_180293_d.field_177842_an);
		genStandardOre1(field_180293_d.field_177838_ap, field_180299_p, field_180293_d.field_177818_aq,
				field_180293_d.field_177816_ar);
		genStandardOre1(field_180293_d.field_177812_at, field_180298_q, field_180293_d.field_177826_au,
				field_180293_d.field_177824_av);
		genStandardOre2(field_180293_d.field_177820_ax, lapisGen, field_180293_d.field_177807_ay,
				field_180293_d.field_177805_az);
	}
}
