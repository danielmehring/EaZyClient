package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMegaPineTree extends WorldGenHugeTrees {

public static final int EaZy = 1757;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean field_150542_e;
	// private static final String __OBFID = "http://https://fuckuskid00000421";

	public WorldGenMegaPineTree(final boolean p_i45457_1_, final boolean p_i45457_2_) {
		super(p_i45457_1_, 13, 15, BlockPlanks.EnumType.SPRUCE.func_176839_a(),
				BlockPlanks.EnumType.SPRUCE.func_176839_a());
		field_150542_e = p_i45457_2_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		final int var4 = func_150533_a(p_180709_2_);

		if (!func_175929_a(worldIn, p_180709_2_, p_180709_3_, var4)) {
			return false;
		} else {
			func_150541_c(worldIn, p_180709_3_.getX(), p_180709_3_.getZ(), p_180709_3_.getY() + var4, 0, p_180709_2_);

			for (int var5 = 0; var5 < var4; ++var5) {
				Block var6 = worldIn.getBlockState(p_180709_3_.offsetUp(var5)).getBlock();

				if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
					func_175905_a(worldIn, p_180709_3_.offsetUp(var5), Blocks.log, woodMetadata);
				}

				if (var5 < var4 - 1) {
					var6 = worldIn.getBlockState(p_180709_3_.add(1, var5, 0)).getBlock();

					if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
						func_175905_a(worldIn, p_180709_3_.add(1, var5, 0), Blocks.log, woodMetadata);
					}

					var6 = worldIn.getBlockState(p_180709_3_.add(1, var5, 1)).getBlock();

					if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
						func_175905_a(worldIn, p_180709_3_.add(1, var5, 1), Blocks.log, woodMetadata);
					}

					var6 = worldIn.getBlockState(p_180709_3_.add(0, var5, 1)).getBlock();

					if (var6.getMaterial() == Material.air || var6.getMaterial() == Material.leaves) {
						func_175905_a(worldIn, p_180709_3_.add(0, var5, 1), Blocks.log, woodMetadata);
					}
				}
			}

			return true;
		}
	}

	private void func_150541_c(final World worldIn, final int p_150541_2_, final int p_150541_3_, final int p_150541_4_,
			final int p_150541_5_, final Random p_150541_6_) {
		final int var7 = p_150541_6_.nextInt(5) + (field_150542_e ? baseHeight : 3);
		int var8 = 0;

		for (int var9 = p_150541_4_ - var7; var9 <= p_150541_4_; ++var9) {
			final int var10 = p_150541_4_ - var9;
			final int var11 = p_150541_5_ + MathHelper.floor_float((float) var10 / (float) var7 * 3.5F);
			func_175925_a(worldIn, new BlockPos(p_150541_2_, var9, p_150541_3_),
					var11 + (var10 > 0 && var11 == var8 && (var9 & 1) == 0 ? 1 : 0));
			var8 = var11;
		}
	}

	@Override
	public void func_180711_a(final World worldIn, final Random p_180711_2_, final BlockPos p_180711_3_) {
		func_175933_b(worldIn, p_180711_3_.offsetWest().offsetNorth());
		func_175933_b(worldIn, p_180711_3_.offsetEast(2).offsetNorth());
		func_175933_b(worldIn, p_180711_3_.offsetWest().offsetSouth(2));
		func_175933_b(worldIn, p_180711_3_.offsetEast(2).offsetSouth(2));

		for (int var4 = 0; var4 < 5; ++var4) {
			final int var5 = p_180711_2_.nextInt(64);
			final int var6 = var5 % 8;
			final int var7 = var5 / 8;

			if (var6 == 0 || var6 == 7 || var7 == 0 || var7 == 7) {
				func_175933_b(worldIn, p_180711_3_.add(-3 + var6, 0, -3 + var7));
			}
		}
	}

	private void func_175933_b(final World worldIn, final BlockPos p_175933_2_) {
		for (int var3 = -2; var3 <= 2; ++var3) {
			for (int var4 = -2; var4 <= 2; ++var4) {
				if (Math.abs(var3) != 2 || Math.abs(var4) != 2) {
					func_175934_c(worldIn, p_175933_2_.add(var3, 0, var4));
				}
			}
		}
	}

	private void func_175934_c(final World worldIn, final BlockPos p_175934_2_) {
		for (int var3 = 2; var3 >= -3; --var3) {
			final BlockPos var4 = p_175934_2_.offsetUp(var3);
			final Block var5 = worldIn.getBlockState(var4).getBlock();

			if (var5 == Blocks.grass || var5 == Blocks.dirt) {
				func_175905_a(worldIn, var4, Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata());
				break;
			}

			if (var5.getMaterial() != Material.air && var3 < 0) {
				break;
			}
		}
	}
}
