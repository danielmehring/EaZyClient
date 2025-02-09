package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenForest extends WorldGenAbstractTree {

public static final int EaZy = 1747;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final boolean field_150531_a;
	// private static final String __OBFID = "http://https://fuckuskid00000401";

	public WorldGenForest(final boolean p_i45449_1_, final boolean p_i45449_2_) {
		super(p_i45449_1_);
		field_150531_a = p_i45449_2_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		int var4 = p_180709_2_.nextInt(3) + 5;

		if (field_150531_a) {
			var4 += p_180709_2_.nextInt(7);
		}

		boolean var5 = true;

		if (p_180709_3_.getY() >= 1 && p_180709_3_.getY() + var4 + 1 <= 256) {
			int var8;
			int var9;

			for (int var6 = p_180709_3_.getY(); var6 <= p_180709_3_.getY() + 1 + var4; ++var6) {
				byte var7 = 1;

				if (var6 == p_180709_3_.getY()) {
					var7 = 0;
				}

				if (var6 >= p_180709_3_.getY() + 1 + var4 - 2) {
					var7 = 2;
				}

				for (var8 = p_180709_3_.getX() - var7; var8 <= p_180709_3_.getX() + var7 && var5; ++var8) {
					for (var9 = p_180709_3_.getZ() - var7; var9 <= p_180709_3_.getZ() + var7 && var5; ++var9) {
						if (var6 >= 0 && var6 < 256) {
							if (!func_150523_a(worldIn.getBlockState(new BlockPos(var8, var6, var9)).getBlock())) {
								var5 = false;
							}
						} else {
							var5 = false;
						}
					}
				}
			}

			if (!var5) {
				return false;
			} else {
				final Block var16 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();

				if ((var16 == Blocks.grass || var16 == Blocks.dirt || var16 == Blocks.farmland)
						&& p_180709_3_.getY() < 256 - var4 - 1) {
					func_175921_a(worldIn, p_180709_3_.offsetDown());
					int var17;

					for (var17 = p_180709_3_.getY() - 3 + var4; var17 <= p_180709_3_.getY() + var4; ++var17) {
						var8 = var17 - (p_180709_3_.getY() + var4);
						var9 = 1 - var8 / 2;

						for (int var10 = p_180709_3_.getX() - var9; var10 <= p_180709_3_.getX() + var9; ++var10) {
							final int var11 = var10 - p_180709_3_.getX();

							for (int var12 = p_180709_3_.getZ() - var9; var12 <= p_180709_3_.getZ() + var9; ++var12) {
								final int var13 = var12 - p_180709_3_.getZ();

								if (Math.abs(var11) != var9 || Math.abs(var13) != var9
										|| p_180709_2_.nextInt(2) != 0 && var8 != 0) {
									final BlockPos var14 = new BlockPos(var10, var17, var12);
									final Block var15 = worldIn.getBlockState(var14).getBlock();

									if (var15.getMaterial() == Material.air || var15.getMaterial() == Material.leaves) {
										func_175905_a(worldIn, var14, Blocks.leaves,
												BlockPlanks.EnumType.BIRCH.func_176839_a());
									}
								}
							}
						}
					}

					for (var17 = 0; var17 < var4; ++var17) {
						final Block var18 = worldIn.getBlockState(p_180709_3_.offsetUp(var17)).getBlock();

						if (var18.getMaterial() == Material.air || var18.getMaterial() == Material.leaves) {
							func_175905_a(worldIn, p_180709_3_.offsetUp(var17), Blocks.log,
									BlockPlanks.EnumType.BIRCH.func_176839_a());
						}
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}
