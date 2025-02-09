package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SwimNodeProcessor extends NodeProcessor {

public static final int EaZy = 1835;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001966";

	@Override
	public void func_176162_a(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
		super.func_176162_a(p_176162_1_, p_176162_2_);
	}

	@Override
	public void func_176163_a() {
		super.func_176163_a();
	}

	@Override
	public PathPoint func_176161_a(final Entity p_176161_1_) {
		return func_176159_a(MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minX),
				MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minY + 0.5D),
				MathHelper.floor_double(p_176161_1_.getEntityBoundingBox().minZ));
	}

	@Override
	public PathPoint func_176160_a(final Entity p_176160_1_, final double p_176160_2_, final double p_176160_4_,
			final double p_176160_6_) {
		return func_176159_a(MathHelper.floor_double(p_176160_2_ - p_176160_1_.width / 2.0F),
				MathHelper.floor_double(p_176160_4_ + 0.5D),
				MathHelper.floor_double(p_176160_6_ - p_176160_1_.width / 2.0F));
	}

	@Override
	public int func_176164_a(final PathPoint[] p_176164_1_, final Entity p_176164_2_, final PathPoint p_176164_3_,
			final PathPoint p_176164_4_, final float p_176164_5_) {
		int var6 = 0;
		final EnumFacing[] var7 = EnumFacing.values();
		final int var8 = var7.length;

		for (int var9 = 0; var9 < var8; ++var9) {
			final EnumFacing var10 = var7[var9];
			final PathPoint var11 = func_176185_a(p_176164_2_, p_176164_3_.xCoord + var10.getFrontOffsetX(),
					p_176164_3_.yCoord + var10.getFrontOffsetY(), p_176164_3_.zCoord + var10.getFrontOffsetZ());

			if (var11 != null && !var11.visited && var11.distanceTo(p_176164_4_) < p_176164_5_) {
				p_176164_1_[var6++] = var11;
			}
		}

		return var6;
	}

	private PathPoint func_176185_a(final Entity p_176185_1_, final int p_176185_2_, final int p_176185_3_,
			final int p_176185_4_) {
		final int var5 = func_176186_b(p_176185_1_, p_176185_2_, p_176185_3_, p_176185_4_);
		return var5 == -1 ? func_176159_a(p_176185_2_, p_176185_3_, p_176185_4_) : null;
	}

	private int func_176186_b(final Entity p_176186_1_, final int p_176186_2_, final int p_176186_3_,
			final int p_176186_4_) {
		for (int var5 = p_176186_2_; var5 < p_176186_2_ + field_176168_c; ++var5) {
			for (int var6 = p_176186_3_; var6 < p_176186_3_ + field_176165_d; ++var6) {
				for (int var7 = p_176186_4_; var7 < p_176186_4_ + field_176166_e; ++var7) {
					final BlockPos var8 = new BlockPos(var5, var6, var7);
					final Block var9 = field_176169_a.getBlockState(var8).getBlock();

					if (var9.getMaterial() != Material.water) {
						return 0;
					}
				}
			}
		}

		return -1;
	}
}
