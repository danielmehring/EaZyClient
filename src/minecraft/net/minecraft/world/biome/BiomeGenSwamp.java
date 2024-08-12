package net.minecraft.world.biome;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenSwamp extends BiomeGenBase {

public static final int EaZy = 1693;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000185";

	protected BiomeGenSwamp(final int p_i1988_1_) {
		super(p_i1988_1_);
		theBiomeDecorator.treesPerChunk = 2;
		theBiomeDecorator.flowersPerChunk = 1;
		theBiomeDecorator.deadBushPerChunk = 1;
		theBiomeDecorator.mushroomsPerChunk = 8;
		theBiomeDecorator.reedsPerChunk = 10;
		theBiomeDecorator.clayPerChunk = 1;
		theBiomeDecorator.waterlilyPerChunk = 4;
		theBiomeDecorator.sandPerChunk2 = 0;
		theBiomeDecorator.sandPerChunk = 0;
		theBiomeDecorator.grassPerChunk = 5;
		waterColorMultiplier = 14745518;
		spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(final Random p_150567_1_) {
		return worldGeneratorSwamp;
	}

	@Override
	public int func_180627_b(final BlockPos p_180627_1_) {
		final double var2 = field_180281_af.func_151601_a(p_180627_1_.getX() * 0.0225D, p_180627_1_.getZ() * 0.0225D);
		return var2 < -0.1D ? 5011004 : 6975545;
	}

	@Override
	public int func_180625_c(final BlockPos p_180625_1_) {
		return 6975545;
	}

	@Override
	public BlockFlower.EnumFlowerType pickRandomFlower(final Random p_180623_1_, final BlockPos p_180623_2_) {
		return BlockFlower.EnumFlowerType.BLUE_ORCHID;
	}

	@Override
	public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_,
			final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
		final double var8 = field_180281_af.func_151601_a(p_180622_4_ * 0.25D, p_180622_5_ * 0.25D);

		if (var8 > 0.0D) {
			final int var10 = p_180622_4_ & 15;
			final int var11 = p_180622_5_ & 15;

			for (int var12 = 255; var12 >= 0; --var12) {
				if (p_180622_3_.getBlockState(var11, var12, var10).getBlock().getMaterial() != Material.air) {
					if (var12 == 62 && p_180622_3_.getBlockState(var11, var12, var10).getBlock() != Blocks.water) {
						p_180622_3_.setBlockState(var11, var12, var10, Blocks.water.getDefaultState());

						if (var8 < 0.12D) {
							p_180622_3_.setBlockState(var11, var12 + 1, var10, Blocks.waterlily.getDefaultState());
						}
					}

					break;
				}
			}
		}

		func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
	}
}
