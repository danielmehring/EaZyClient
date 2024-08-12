package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityLiving {

public static final int EaZy = 1112;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001545";

	public EntityFlying(final World worldIn) {
		super(worldIn);
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	@Override
	protected void func_180433_a(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_,
			final BlockPos p_180433_5_) {}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
		if (isInWater()) {
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.800000011920929D;
			motionY *= 0.800000011920929D;
			motionZ *= 0.800000011920929D;
		} else if (func_180799_ab()) {
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		} else {
			float var3 = 0.91F;

			if (onGround) {
				var3 = worldObj.getBlockState(new BlockPos(MathHelper.floor_double(posX),
						MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)))
						.getBlock().slipperiness * 0.91F;
			}

			final float var4 = 0.16277136F / (var3 * var3 * var3);
			moveFlying(p_70612_1_, p_70612_2_, onGround ? 0.1F * var4 : 0.02F);
			var3 = 0.91F;

			if (onGround) {
				var3 = worldObj.getBlockState(new BlockPos(MathHelper.floor_double(posX),
						MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)))
						.getBlock().slipperiness * 0.91F;
			}

			moveEntity(motionX, motionY, motionZ);
			motionX *= var3;
			motionY *= var3;
			motionZ *= var3;
		}

		prevLimbSwingAmount = limbSwingAmount;
		final double var8 = posX - prevPosX;
		final double var5 = posZ - prevPosZ;
		float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;

		if (var7 > 1.0F) {
			var7 = 1.0F;
		}

		limbSwingAmount += (var7 - limbSwingAmount) * 0.4F;
		limbSwing += limbSwingAmount;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder() {
		return false;
	}
}
