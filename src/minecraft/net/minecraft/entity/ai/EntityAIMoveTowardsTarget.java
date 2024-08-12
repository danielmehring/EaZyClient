package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {

public static final int EaZy = 1070;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theEntity;
	private EntityLivingBase targetEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private final double speed;

	/**
	 * If the distance to the target entity is further than this, this AI task
	 * will not run.
	 */
	private final float maxTargetDistance;
	// private static final String __OBFID = "http://https://fuckuskid00001599";

	public EntityAIMoveTowardsTarget(final EntityCreature p_i1640_1_, final double p_i1640_2_, final float p_i1640_4_) {
		theEntity = p_i1640_1_;
		speed = p_i1640_2_;
		maxTargetDistance = p_i1640_4_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		targetEntity = theEntity.getAttackTarget();

		if (targetEntity == null) {
			return false;
		} else if (targetEntity.getDistanceSqToEntity(theEntity) > maxTargetDistance * maxTargetDistance) {
			return false;
		} else {
			final Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7,
					new Vec3(targetEntity.posX, targetEntity.posY, targetEntity.posZ));

			if (var1 == null) {
				return false;
			} else {
				movePosX = var1.xCoord;
				movePosY = var1.yCoord;
				movePosZ = var1.zCoord;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !theEntity.getNavigator().noPath() && targetEntity.isEntityAlive()
				&& targetEntity.getDistanceSqToEntity(theEntity) < maxTargetDistance * maxTargetDistance;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		targetEntity = null;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, speed);
	}
}
