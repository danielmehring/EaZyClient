package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RandomPositionGenerator {

public static final int EaZy = 1099;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * used to store a driection when the user passes a point to move towards or
	 * away from. WARNING: NEVER THREAD SAFE. MULTIPLE findTowards and findAway
	 * calls, will share this var
	 */
	private static Vec3 staticVector = new Vec3(0.0D, 0.0D, 0.0D);
	// private static final String __OBFID = "http://https://fuckuskid00001629";

	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks
	 */
	public static Vec3 findRandomTarget(final EntityCreature p_75463_0_, final int p_75463_1_, final int p_75463_2_) {
		return findRandomTargetBlock(p_75463_0_, p_75463_1_, p_75463_2_, (Vec3) null);
	}

	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks in the
	 * direction of the point par3
	 */
	public static Vec3 findRandomTargetBlockTowards(final EntityCreature p_75464_0_, final int p_75464_1_,
			final int p_75464_2_, final Vec3 p_75464_3_) {
		staticVector = p_75464_3_.subtract(p_75464_0_.posX, p_75464_0_.posY, p_75464_0_.posZ);
		return findRandomTargetBlock(p_75464_0_, p_75464_1_, p_75464_2_, staticVector);
	}

	/**
	 * finds a random target within par1(x,z) and par2 (y) blocks in the reverse
	 * direction of the point par3
	 */
	public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature p_75461_0_, final int p_75461_1_,
			final int p_75461_2_, final Vec3 p_75461_3_) {
		staticVector = new Vec3(p_75461_0_.posX, p_75461_0_.posY, p_75461_0_.posZ).subtract(p_75461_3_);
		return findRandomTargetBlock(p_75461_0_, p_75461_1_, p_75461_2_, staticVector);
	}

	/**
	 * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance,
	 * ignores those not in the direction of par3Vec3, then points to the tile
	 * for which creature.getBlockPathWeight returns the highest number
	 */
	private static Vec3 findRandomTargetBlock(final EntityCreature p_75462_0_, final int p_75462_1_,
			final int p_75462_2_, final Vec3 p_75462_3_) {
		final Random var4 = p_75462_0_.getRNG();
		boolean var5 = false;
		int var6 = 0;
		int var7 = 0;
		int var8 = 0;
		float var9 = -99999.0F;
		boolean var10;

		if (p_75462_0_.hasHome()) {
			final double var11 = p_75462_0_.func_180486_cf().distanceSq(MathHelper.floor_double(p_75462_0_.posX),
					MathHelper.floor_double(p_75462_0_.posY), MathHelper.floor_double(p_75462_0_.posZ)) + 4.0D;
			final double var13 = p_75462_0_.getMaximumHomeDistance() + p_75462_1_;
			var10 = var11 < var13 * var13;
		} else {
			var10 = false;
		}

		for (int var17 = 0; var17 < 10; ++var17) {
			int var12 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;
			int var18 = var4.nextInt(2 * p_75462_2_ + 1) - p_75462_2_;
			int var14 = var4.nextInt(2 * p_75462_1_ + 1) - p_75462_1_;

			if (p_75462_3_ == null || var12 * p_75462_3_.xCoord + var14 * p_75462_3_.zCoord >= 0.0D) {
				BlockPos var15;

				if (p_75462_0_.hasHome() && p_75462_1_ > 1) {
					var15 = p_75462_0_.func_180486_cf();

					if (p_75462_0_.posX > var15.getX()) {
						var12 -= var4.nextInt(p_75462_1_ / 2);
					} else {
						var12 += var4.nextInt(p_75462_1_ / 2);
					}

					if (p_75462_0_.posZ > var15.getZ()) {
						var14 -= var4.nextInt(p_75462_1_ / 2);
					} else {
						var14 += var4.nextInt(p_75462_1_ / 2);
					}
				}

				var12 += MathHelper.floor_double(p_75462_0_.posX);
				var18 += MathHelper.floor_double(p_75462_0_.posY);
				var14 += MathHelper.floor_double(p_75462_0_.posZ);
				var15 = new BlockPos(var12, var18, var14);

				if (!var10 || p_75462_0_.func_180485_d(var15)) {
					final float var16 = p_75462_0_.func_180484_a(var15);

					if (var16 > var9) {
						var9 = var16;
						var6 = var12;
						var7 = var18;
						var8 = var14;
						var5 = true;
					}
				}
			}
		}

		if (var5) {
			return new Vec3(var6, var7, var8);
		} else {
			return null;
		}
	}
}
