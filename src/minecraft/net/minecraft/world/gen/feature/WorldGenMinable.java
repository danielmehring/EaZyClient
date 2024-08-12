package net.minecraft.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import com.google.common.base.Predicate;

public class WorldGenMinable extends WorldGenerator {

public static final int EaZy = 1759;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IBlockState oreBlock;

	/** The number of blocks to generate. */
	private final int numberOfBlocks;
	private final Predicate field_175919_c;
	// private static final String __OBFID = "http://https://fuckuskid00000426";

	public WorldGenMinable(final IBlockState p_i45630_1_, final int p_i45630_2_) {
		this(p_i45630_1_, p_i45630_2_, BlockHelper.forBlock(Blocks.stone));
	}

	public WorldGenMinable(final IBlockState p_i45631_1_, final int p_i45631_2_, final Predicate p_i45631_3_) {
		oreBlock = p_i45631_1_;
		numberOfBlocks = p_i45631_2_;
		field_175919_c = p_i45631_3_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
		final float var4 = p_180709_2_.nextFloat() * (float) Math.PI;
		final double var5 = p_180709_3_.getX() + 8 + MathHelper.sin(var4) * numberOfBlocks / 8.0F;
		final double var7 = p_180709_3_.getX() + 8 - MathHelper.sin(var4) * numberOfBlocks / 8.0F;
		final double var9 = p_180709_3_.getZ() + 8 + MathHelper.cos(var4) * numberOfBlocks / 8.0F;
		final double var11 = p_180709_3_.getZ() + 8 - MathHelper.cos(var4) * numberOfBlocks / 8.0F;
		final double var13 = p_180709_3_.getY() + p_180709_2_.nextInt(3) - 2;
		final double var15 = p_180709_3_.getY() + p_180709_2_.nextInt(3) - 2;

		for (int var17 = 0; var17 < numberOfBlocks; ++var17) {
			final float var18 = (float) var17 / (float) numberOfBlocks;
			final double var19 = var5 + (var7 - var5) * var18;
			final double var21 = var13 + (var15 - var13) * var18;
			final double var23 = var9 + (var11 - var9) * var18;
			final double var25 = p_180709_2_.nextDouble() * numberOfBlocks / 16.0D;
			final double var27 = (MathHelper.sin((float) Math.PI * var18) + 1.0F) * var25 + 1.0D;
			final double var29 = (MathHelper.sin((float) Math.PI * var18) + 1.0F) * var25 + 1.0D;
			final int var31 = MathHelper.floor_double(var19 - var27 / 2.0D);
			final int var32 = MathHelper.floor_double(var21 - var29 / 2.0D);
			final int var33 = MathHelper.floor_double(var23 - var27 / 2.0D);
			final int var34 = MathHelper.floor_double(var19 + var27 / 2.0D);
			final int var35 = MathHelper.floor_double(var21 + var29 / 2.0D);
			final int var36 = MathHelper.floor_double(var23 + var27 / 2.0D);

			for (int var37 = var31; var37 <= var34; ++var37) {
				final double var38 = (var37 + 0.5D - var19) / (var27 / 2.0D);

				if (var38 * var38 < 1.0D) {
					for (int var40 = var32; var40 <= var35; ++var40) {
						final double var41 = (var40 + 0.5D - var21) / (var29 / 2.0D);

						if (var38 * var38 + var41 * var41 < 1.0D) {
							for (int var43 = var33; var43 <= var36; ++var43) {
								final double var44 = (var43 + 0.5D - var23) / (var27 / 2.0D);

								if (var38 * var38 + var41 * var41 + var44 * var44 < 1.0D) {
									final BlockPos var46 = new BlockPos(var37, var40, var43);

									if (field_175919_c.apply(worldIn.getBlockState(var46))) {
										worldIn.setBlockState(var46, oreBlock, 2);
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
