package net.minecraft.world.gen.feature;

import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenMegaJungle extends WorldGenHugeTrees {

public static final int EaZy = 1756;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000420";

	public WorldGenMegaJungle(final boolean p_i45456_1_, final int p_i45456_2_, final int p_i45456_3_,
			final int p_i45456_4_, final int p_i45456_5_) {
		super(p_i45456_1_, p_i45456_2_, p_i45456_3_, p_i45456_4_, p_i45456_5_);
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		final int var4 = func_150533_a(p_180709_2_);

		if (!func_175929_a(worldIn, p_180709_2_, p_180709_3_, var4)) {
			return false;
		} else {
			func_175930_c(worldIn, p_180709_3_.offsetUp(var4), 2);

			for (int var5 = p_180709_3_.getY() + var4 - 2 - p_180709_2_.nextInt(4); var5 > p_180709_3_.getY()
					+ var4 / 2; var5 -= 2 + p_180709_2_.nextInt(4)) {
				final float var6 = p_180709_2_.nextFloat() * (float) Math.PI * 2.0F;
				int var7 = p_180709_3_.getX() + (int) (0.5F + MathHelper.cos(var6) * 4.0F);
				int var8 = p_180709_3_.getZ() + (int) (0.5F + MathHelper.sin(var6) * 4.0F);
				int var9;

				for (var9 = 0; var9 < 5; ++var9) {
					var7 = p_180709_3_.getX() + (int) (1.5F + MathHelper.cos(var6) * var9);
					var8 = p_180709_3_.getZ() + (int) (1.5F + MathHelper.sin(var6) * var9);
					func_175905_a(worldIn, new BlockPos(var7, var5 - 3 + var9 / 2, var8), Blocks.log, woodMetadata);
				}

				var9 = 1 + p_180709_2_.nextInt(2);
				final int var10 = var5;

				for (int var11 = var5 - var9; var11 <= var10; ++var11) {
					final int var12 = var11 - var10;
					func_175928_b(worldIn, new BlockPos(var7, var11, var8), 1 - var12);
				}
			}

			for (int var13 = 0; var13 < var4; ++var13) {
				final BlockPos var14 = p_180709_3_.offsetUp(var13);

				if (func_175931_a(worldIn.getBlockState(var14).getBlock().getMaterial())) {
					func_175905_a(worldIn, var14, Blocks.log, woodMetadata);

					if (var13 > 0) {
						func_175932_b(worldIn, p_180709_2_, var14.offsetWest(), BlockVine.field_176275_S);
						func_175932_b(worldIn, p_180709_2_, var14.offsetNorth(), BlockVine.field_176272_Q);
					}
				}

				if (var13 < var4 - 1) {
					final BlockPos var15 = var14.offsetEast();

					if (func_175931_a(worldIn.getBlockState(var15).getBlock().getMaterial())) {
						func_175905_a(worldIn, var15, Blocks.log, woodMetadata);

						if (var13 > 0) {
							func_175932_b(worldIn, p_180709_2_, var15.offsetEast(), BlockVine.field_176271_T);
							func_175932_b(worldIn, p_180709_2_, var15.offsetNorth(), BlockVine.field_176272_Q);
						}
					}

					final BlockPos var16 = var14.offsetSouth().offsetEast();

					if (func_175931_a(worldIn.getBlockState(var16).getBlock().getMaterial())) {
						func_175905_a(worldIn, var16, Blocks.log, woodMetadata);

						if (var13 > 0) {
							func_175932_b(worldIn, p_180709_2_, var16.offsetEast(), BlockVine.field_176271_T);
							func_175932_b(worldIn, p_180709_2_, var16.offsetSouth(), BlockVine.field_176276_R);
						}
					}

					final BlockPos var17 = var14.offsetSouth();

					if (func_175931_a(worldIn.getBlockState(var17).getBlock().getMaterial())) {
						func_175905_a(worldIn, var17, Blocks.log, woodMetadata);

						if (var13 > 0) {
							func_175932_b(worldIn, p_180709_2_, var17.offsetWest(), BlockVine.field_176275_S);
							func_175932_b(worldIn, p_180709_2_, var17.offsetSouth(), BlockVine.field_176276_R);
						}
					}
				}
			}

			return true;
		}
	}

	private boolean func_175931_a(final Material p_175931_1_) {
		return p_175931_1_ == Material.air || p_175931_1_ == Material.leaves;
	}

	private void func_175932_b(final World worldIn, final Random p_175932_2_, final BlockPos p_175932_3_,
			final int p_175932_4_) {
		if (p_175932_2_.nextInt(3) > 0 && worldIn.isAirBlock(p_175932_3_)) {
			func_175905_a(worldIn, p_175932_3_, Blocks.vine, p_175932_4_);
		}
	}

	private void func_175930_c(final World worldIn, final BlockPos p_175930_2_, final int p_175930_3_) {
		final byte var4 = 2;

		for (int var5 = -var4; var5 <= 0; ++var5) {
			func_175925_a(worldIn, p_175930_2_.offsetUp(var5), p_175930_3_ + 1 - var5);
		}
	}
}
