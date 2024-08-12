package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree {

public static final int EaZy = 1751;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The base height of the tree */
	protected final int baseHeight;

	/** Sets the metadata for the wood blocks used */
	protected final int woodMetadata;

	/** Sets the metadata for the leaves used in huge trees */
	protected final int leavesMetadata;
	protected int field_150538_d;
	// private static final String __OBFID = "http://https://fuckuskid00000423";

	public WorldGenHugeTrees(final boolean p_i45458_1_, final int p_i45458_2_, final int p_i45458_3_,
			final int p_i45458_4_, final int p_i45458_5_) {
		super(p_i45458_1_);
		baseHeight = p_i45458_2_;
		field_150538_d = p_i45458_3_;
		woodMetadata = p_i45458_4_;
		leavesMetadata = p_i45458_5_;
	}

	protected int func_150533_a(final Random p_150533_1_) {
		int var2 = p_150533_1_.nextInt(3) + baseHeight;

		if (field_150538_d > 1) {
			var2 += p_150533_1_.nextInt(field_150538_d);
		}

		return var2;
	}

	private boolean func_175926_c(final World worldIn, final BlockPos p_175926_2_, final int p_175926_3_) {
		boolean var4 = true;

		if (p_175926_2_.getY() >= 1 && p_175926_2_.getY() + p_175926_3_ + 1 <= 256) {
			for (int var5 = 0; var5 <= 1 + p_175926_3_; ++var5) {
				byte var6 = 2;

				if (var5 == 0) {
					var6 = 1;
				} else if (var5 >= 1 + p_175926_3_ - 2) {
					var6 = 2;
				}

				for (int var7 = -var6; var7 <= var6 && var4; ++var7) {
					for (int var8 = -var6; var8 <= var6 && var4; ++var8) {
						if (p_175926_2_.getY() + var5 < 0 || p_175926_2_.getY() + var5 >= 256 || !func_150523_a(
								worldIn.getBlockState(p_175926_2_.add(var7, var5, var8)).getBlock())) {
							var4 = false;
						}
					}
				}
			}

			return var4;
		} else {
			return false;
		}
	}

	private boolean func_175927_a(final BlockPos p_175927_1_, final World worldIn) {
		final BlockPos var3 = p_175927_1_.offsetDown();
		final Block var4 = worldIn.getBlockState(var3).getBlock();

		if ((var4 == Blocks.grass || var4 == Blocks.dirt) && p_175927_1_.getY() >= 2) {
			func_175921_a(worldIn, var3);
			func_175921_a(worldIn, var3.offsetEast());
			func_175921_a(worldIn, var3.offsetSouth());
			func_175921_a(worldIn, var3.offsetSouth().offsetEast());
			return true;
		} else {
			return false;
		}
	}

	protected boolean func_175929_a(final World worldIn, final Random p_175929_2_, final BlockPos p_175929_3_,
			final int p_175929_4_) {
		return func_175926_c(worldIn, p_175929_3_, p_175929_4_) && func_175927_a(p_175929_3_, worldIn);
	}

	protected void func_175925_a(final World worldIn, final BlockPos p_175925_2_, final int p_175925_3_) {
		final int var4 = p_175925_3_ * p_175925_3_;

		for (int var5 = -p_175925_3_; var5 <= p_175925_3_ + 1; ++var5) {
			for (int var6 = -p_175925_3_; var6 <= p_175925_3_ + 1; ++var6) {
				final int var7 = var5 - 1;
				final int var8 = var6 - 1;

				if (var5 * var5 + var6 * var6 <= var4 || var7 * var7 + var8 * var8 <= var4
						|| var5 * var5 + var8 * var8 <= var4 || var7 * var7 + var6 * var6 <= var4) {
					final BlockPos var9 = p_175925_2_.add(var5, 0, var6);
					final Material var10 = worldIn.getBlockState(var9).getBlock().getMaterial();

					if (var10 == Material.air || var10 == Material.leaves) {
						func_175905_a(worldIn, var9, Blocks.leaves, leavesMetadata);
					}
				}
			}
		}
	}

	protected void func_175928_b(final World worldIn, final BlockPos p_175928_2_, final int p_175928_3_) {
		final int var4 = p_175928_3_ * p_175928_3_;

		for (int var5 = -p_175928_3_; var5 <= p_175928_3_; ++var5) {
			for (int var6 = -p_175928_3_; var6 <= p_175928_3_; ++var6) {
				if (var5 * var5 + var6 * var6 <= var4) {
					final BlockPos var7 = p_175928_2_.add(var5, 0, var6);
					final Material var8 = worldIn.getBlockState(var7).getBlock().getMaterial();

					if (var8 == Material.air || var8 == Material.leaves) {
						func_175905_a(worldIn, var7, Blocks.leaves, leavesMetadata);
					}
				}
			}
		}
	}
}
