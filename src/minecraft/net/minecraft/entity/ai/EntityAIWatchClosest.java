package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIWatchClosest extends EntityAIBase {

public static final int EaZy = 1092;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected EntityLiving theWatcher;

	/** The closest entity which is being watched by this one. */
	protected Entity closestEntity;

	/** This is the Maximum distance that the AI will look for the Entity */
	protected float maxDistanceForPlayer;
	private int lookTime;
	private final float field_75331_e;
	protected Class watchedClass;
	// private static final String __OBFID = "http://https://fuckuskid00001592";

	public EntityAIWatchClosest(final EntityLiving p_i1631_1_, final Class p_i1631_2_, final float p_i1631_3_) {
		theWatcher = p_i1631_1_;
		watchedClass = p_i1631_2_;
		maxDistanceForPlayer = p_i1631_3_;
		field_75331_e = 0.02F;
		setMutexBits(2);
	}

	public EntityAIWatchClosest(final EntityLiving p_i1632_1_, final Class p_i1632_2_, final float p_i1632_3_,
			final float p_i1632_4_) {
		theWatcher = p_i1632_1_;
		watchedClass = p_i1632_2_;
		maxDistanceForPlayer = p_i1632_3_;
		field_75331_e = p_i1632_4_;
		setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (theWatcher.getRNG().nextFloat() >= field_75331_e) {
			return false;
		} else {
			if (theWatcher.getAttackTarget() != null) {
				closestEntity = theWatcher.getAttackTarget();
			}

			if (watchedClass == EntityPlayer.class) {
				closestEntity = theWatcher.worldObj.getClosestPlayerToEntity(theWatcher, maxDistanceForPlayer);
			} else {
				closestEntity = theWatcher.worldObj.findNearestEntityWithinAABB(watchedClass,
						theWatcher.getEntityBoundingBox().expand(maxDistanceForPlayer, 3.0D, maxDistanceForPlayer),
						theWatcher);
			}

			return closestEntity != null;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !closestEntity.isEntityAlive() ? false
				: theWatcher.getDistanceSqToEntity(closestEntity) > maxDistanceForPlayer * maxDistanceForPlayer ? false
						: lookTime > 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		lookTime = 40 + theWatcher.getRNG().nextInt(40);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		closestEntity = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		theWatcher.getLookHelper().setLookPosition(closestEntity.posX,
				closestEntity.posY + closestEntity.getEyeHeight(), closestEntity.posZ, 10.0F,
				theWatcher.getVerticalFaceSpeed());
		--lookTime;
	}
}
