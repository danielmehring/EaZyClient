package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class WorldGenBlockBlob extends WorldGenerator {

public static final int EaZy = 1735;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Block field_150545_a;
	private final int field_150544_b;
	// private static final String __OBFID = "http://https://fuckuskid00000402";

	public WorldGenBlockBlob(final Block p_i45450_1_, final int p_i45450_2_) {
		super(false);
		field_150545_a = p_i45450_1_;
		field_150544_b = p_i45450_2_;
	}

	@Override
	public boolean generate(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
		while (true) {
			if (p_180709_3_.getY() > 3) {
				label47:
				{
					if (!worldIn.isAirBlock(p_180709_3_.offsetDown())) {
						final Block var4 = worldIn.getBlockState(p_180709_3_.offsetDown()).getBlock();

						if (var4 == Blocks.grass || var4 == Blocks.dirt || var4 == Blocks.stone) {
							break label47;
						}
					}

					p_180709_3_ = p_180709_3_.offsetDown();
					continue;
				}
			}

			if (p_180709_3_.getY() <= 3) {
				return false;
			}

			final int var12 = field_150544_b;

			for (int var5 = 0; var12 >= 0 && var5 < 3; ++var5) {
				final int var6 = var12 + p_180709_2_.nextInt(2);
				final int var7 = var12 + p_180709_2_.nextInt(2);
				final int var8 = var12 + p_180709_2_.nextInt(2);
				final float var9 = (var6 + var7 + var8) * 0.333F + 0.5F;
				final Iterator var10 = BlockPos
						.getAllInBox(p_180709_3_.add(-var6, -var7, -var8), p_180709_3_.add(var6, var7, var8))
						.iterator();

				while (var10.hasNext()) {
					final BlockPos var11 = (BlockPos) var10.next();

					if (var11.distanceSq(p_180709_3_) <= var9 * var9) {
						worldIn.setBlockState(var11, field_150545_a.getDefaultState(), 4);
					}
				}

				p_180709_3_ = p_180709_3_.add(-(var12 + 1) + p_180709_2_.nextInt(2 + var12 * 2),
						0 - p_180709_2_.nextInt(2), -(var12 + 1) + p_180709_2_.nextInt(2 + var12 * 2));
			}

			return true;
		}
	}
}
