package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {

public static final int EaZy = 1061;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The entity that is leaping. */
	EntityLiving leaper;

	/** The entity that the leaper is leaping towards. */
	EntityLivingBase leapTarget;

	/** The entity's motionY after leaping. */
	float leapMotionY;
	// private static final String __OBFID = "http://https://fuckuskid00001591";

	public EntityAILeapAtTarget(final EntityLiving p_i1630_1_, final float p_i1630_2_) {
		leaper = p_i1630_1_;
		leapMotionY = p_i1630_2_;
		setMutexBits(5);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		leapTarget = leaper.getAttackTarget();

		if (leapTarget == null) {
			return false;
		} else {
			final double var1 = leaper.getDistanceSqToEntity(leapTarget);
			return var1 >= 4.0D && var1 <= 16.0D ? !leaper.onGround ? false : leaper.getRNG().nextInt(5) == 0 : false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !leaper.onGround;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		final double var1 = leapTarget.posX - leaper.posX;
		final double var3 = leapTarget.posZ - leaper.posZ;
		final float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		leaper.motionX += var1 / var5 * 0.5D * 0.800000011920929D + leaper.motionX * 0.20000000298023224D;
		leaper.motionZ += var3 / var5 * 0.5D * 0.800000011920929D + leaper.motionZ * 0.20000000298023224D;
		leaper.motionY = leapMotionY;
	}
}
