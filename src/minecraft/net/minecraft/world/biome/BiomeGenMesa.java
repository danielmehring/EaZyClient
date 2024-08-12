package net.minecraft.world.biome;

import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Arrays;
import java.util.Random;

public class BiomeGenMesa extends BiomeGenBase {

public static final int EaZy = 1684;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IBlockState[] field_150621_aC;
	private long field_150622_aD;
	private NoiseGeneratorPerlin field_150623_aE;
	private NoiseGeneratorPerlin field_150624_aF;
	private NoiseGeneratorPerlin field_150625_aG;
	private final boolean field_150626_aH;
	private final boolean field_150620_aI;
	// private static final String __OBFID = "http://https://fuckuskid00000176";

	public BiomeGenMesa(final int p_i45380_1_, final boolean p_i45380_2_, final boolean p_i45380_3_) {
		super(p_i45380_1_);
		field_150626_aH = p_i45380_2_;
		field_150620_aI = p_i45380_3_;
		setDisableRain();
		setTemperatureRainfall(2.0F, 0.0F);
		spawnableCreatureList.clear();
		topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT_PROP, BlockSand.EnumType.RED_SAND);
		fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 20;
		theBiomeDecorator.reedsPerChunk = 3;
		theBiomeDecorator.cactiPerChunk = 5;
		theBiomeDecorator.flowersPerChunk = 0;
		spawnableCreatureList.clear();

		if (p_i45380_3_) {
			theBiomeDecorator.treesPerChunk = 5;
		}
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return worldGeneratorTrees;
	}

	@Override
	public int func_180625_c(final BlockPos p_180625_1_) {
		return 10387789;
	}

	@Override
	public int func_180627_b(final BlockPos p_180627_1_) {
		return 9470285;
	}

	@Override
	public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
		super.func_180624_a(worldIn, p_180624_2_, p_180624_3_);
	}

	@Override
	public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
			final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
		if (field_150621_aC == null || field_150622_aD != worldIn.getSeed()) {
			func_150619_a(worldIn.getSeed());
		}

		if (field_150623_aE == null || field_150624_aF == null || field_150622_aD != worldIn.getSeed()) {
			final Random var8 = new Random(field_150622_aD);
			field_150623_aE = new NoiseGeneratorPerlin(var8, 4);
			field_150624_aF = new NoiseGeneratorPerlin(var8, 1);
		}

		field_150622_aD = worldIn.getSeed();
		double var22 = 0.0D;
		int var10;
		int var11;

		if (field_150626_aH) {
			var10 = (p_180622_4_ & -16) + (p_180622_5_ & 15);
			var11 = (p_180622_5_ & -16) + (p_180622_4_ & 15);
			final double var12 = Math.min(Math.abs(p_180622_6_),
					field_150623_aE.func_151601_a(var10 * 0.25D, var11 * 0.25D));

			if (var12 > 0.0D) {
				final double var14 = 0.001953125D;
				final double var16 = Math.abs(field_150624_aF.func_151601_a(var10 * var14, var11 * var14));
				var22 = var12 * var12 * 2.5D;
				final double var18 = Math.ceil(var16 * 50.0D) + 14.0D;

				if (var22 > var18) {
					var22 = var18;
				}

				var22 += 64.0D;
			}
		}

		var10 = p_180622_4_ & 15;
		var11 = p_180622_5_ & 15;
		IBlockState var13 = Blocks.stained_hardened_clay.getDefaultState();
		IBlockState var24 = fillerBlock;
		final int var15 = (int) (p_180622_6_ / 3.0D + 3.0D + p_180622_2_.nextDouble() * 0.25D);
		final boolean var25 = Math.cos(p_180622_6_ / 3.0D * Math.PI) > 0.0D;
		int var17 = -1;
		boolean var26 = false;

		for (int var19 = 255; var19 >= 0; --var19) {
			if (p_180622_3_.getBlockState(var11, var19, var10).getBlock().getMaterial() == Material.air
					&& var19 < (int) var22) {
				p_180622_3_.setBlockState(var11, var19, var10, Blocks.stone.getDefaultState());
			}

			if (var19 <= p_180622_2_.nextInt(5)) {
				p_180622_3_.setBlockState(var11, var19, var10, Blocks.bedrock.getDefaultState());
			} else {
				final IBlockState var20 = p_180622_3_.getBlockState(var11, var19, var10);

				if (var20.getBlock().getMaterial() == Material.air) {
					var17 = -1;
				} else if (var20.getBlock() == Blocks.stone) {
					IBlockState var21;

					if (var17 == -1) {
						var26 = false;

						if (var15 <= 0) {
							var13 = null;
							var24 = Blocks.stone.getDefaultState();
						} else if (var19 >= 59 && var19 <= 64) {
							var13 = Blocks.stained_hardened_clay.getDefaultState();
							var24 = fillerBlock;
						}

						if (var19 < 63 && (var13 == null || var13.getBlock().getMaterial() == Material.air)) {
							var13 = Blocks.water.getDefaultState();
						}

						var17 = var15 + Math.max(0, var19 - 63);

						if (var19 >= 62) {
							if (field_150620_aI && var19 > 86 + var15 * 2) {
								if (var25) {
									p_180622_3_.setBlockState(var11, var19, var10, Blocks.dirt.getDefaultState()
											.withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
								} else {
									p_180622_3_.setBlockState(var11, var19, var10, Blocks.grass.getDefaultState());
								}
							} else if (var19 > 66 + var15) {
								if (var19 >= 64 && var19 <= 127) {
									if (var25) {
										var21 = Blocks.hardened_clay.getDefaultState();
									} else {
										var21 = func_180629_a(p_180622_4_, var19, p_180622_5_);
									}
								} else {
									var21 = Blocks.stained_hardened_clay.getDefaultState()
											.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE);
								}

								p_180622_3_.setBlockState(var11, var19, var10, var21);
							} else {
								p_180622_3_.setBlockState(var11, var19, var10, topBlock);
								var26 = true;
							}
						} else {
							p_180622_3_.setBlockState(var11, var19, var10, var24);

							if (var24.getBlock() == Blocks.stained_hardened_clay) {
								p_180622_3_.setBlockState(var11, var19, var10, var24.getBlock().getDefaultState()
										.withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
							}
						}
					} else if (var17 > 0) {
						--var17;

						if (var26) {
							p_180622_3_.setBlockState(var11, var19, var10, Blocks.stained_hardened_clay
									.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.ORANGE));
						} else {
							var21 = func_180629_a(p_180622_4_, var19, p_180622_5_);
							p_180622_3_.setBlockState(var11, var19, var10, var21);
						}
					}
				}
			}
		}
	}

	private void func_150619_a(final long p_150619_1_) {
		field_150621_aC = new IBlockState[64];
		Arrays.fill(field_150621_aC, Blocks.hardened_clay.getDefaultState());
		final Random var3 = new Random(p_150619_1_);
		field_150625_aG = new NoiseGeneratorPerlin(var3, 1);
		int var4;

		for (var4 = 0; var4 < 64; ++var4) {
			var4 += var3.nextInt(5) + 1;

			if (var4 < 64) {
				field_150621_aC[var4] = Blocks.stained_hardened_clay.getDefaultState().withProperty(BlockColored.COLOR,
						EnumDyeColor.ORANGE);
			}
		}

		var4 = var3.nextInt(4) + 2;
		int var5;
		int var6;
		int var7;
		int var8;

		for (var5 = 0; var5 < var4; ++var5) {
			var6 = var3.nextInt(3) + 1;
			var7 = var3.nextInt(64);

			for (var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
				field_150621_aC[var7 + var8] = Blocks.stained_hardened_clay.getDefaultState()
						.withProperty(BlockColored.COLOR, EnumDyeColor.YELLOW);
			}
		}

		var5 = var3.nextInt(4) + 2;
		int var9;

		for (var6 = 0; var6 < var5; ++var6) {
			var7 = var3.nextInt(3) + 2;
			var8 = var3.nextInt(64);

			for (var9 = 0; var8 + var9 < 64 && var9 < var7; ++var9) {
				field_150621_aC[var8 + var9] = Blocks.stained_hardened_clay.getDefaultState()
						.withProperty(BlockColored.COLOR, EnumDyeColor.BROWN);
			}
		}

		var6 = var3.nextInt(4) + 2;

		for (var7 = 0; var7 < var6; ++var7) {
			var8 = var3.nextInt(3) + 1;
			var9 = var3.nextInt(64);

			for (int var10 = 0; var9 + var10 < 64 && var10 < var8; ++var10) {
				field_150621_aC[var9 + var10] = Blocks.stained_hardened_clay.getDefaultState()
						.withProperty(BlockColored.COLOR, EnumDyeColor.RED);
			}
		}

		var7 = var3.nextInt(3) + 3;
		var8 = 0;

		for (var9 = 0; var9 < var7; ++var9) {
			final byte var12 = 1;
			var8 += var3.nextInt(16) + 4;

			for (int var11 = 0; var8 + var11 < 64 && var11 < var12; ++var11) {
				field_150621_aC[var8 + var11] = Blocks.stained_hardened_clay.getDefaultState()
						.withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);

				if (var8 + var11 > 1 && var3.nextBoolean()) {
					field_150621_aC[var8 + var11 - 1] = Blocks.stained_hardened_clay.getDefaultState()
							.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
				}

				if (var8 + var11 < 63 && var3.nextBoolean()) {
					field_150621_aC[var8 + var11 + 1] = Blocks.stained_hardened_clay.getDefaultState()
							.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER);
				}
			}
		}
	}

	private IBlockState func_180629_a(final int p_180629_1_, final int p_180629_2_, final int p_180629_3_) {
		final int var4 = (int) Math
				.round(field_150625_aG.func_151601_a(p_180629_1_ * 1.0D / 512.0D, p_180629_1_ * 1.0D / 512.0D) * 2.0D);
		return field_150621_aC[(p_180629_2_ + var4 + 64) % 64];
	}

	@Override
	protected BiomeGenBase createMutatedBiome(final int p_180277_1_) {
		final boolean var2 = biomeID == BiomeGenBase.mesa.biomeID;
		final BiomeGenMesa var3 = new BiomeGenMesa(p_180277_1_, var2, field_150620_aI);

		if (!var2) {
			var3.setHeight(height_LowHills);
			var3.setBiomeName(biomeName + " M");
		} else {
			var3.setBiomeName(biomeName + " (Bryce)");
		}

		var3.func_150557_a(color, true);
		return var3;
	}
}
