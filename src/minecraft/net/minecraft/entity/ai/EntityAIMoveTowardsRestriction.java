package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsRestriction extends EntityAIBase {

public static final int EaZy = 1069;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private final double movementSpeed;
	// private static final String __OBFID = "http://https://fuckuskid00001598";

	public EntityAIMoveTowardsRestriction(final EntityCreature p_i2347_1_, final double p_i2347_2_) {
		theEntity = p_i2347_1_;
		movementSpeed = p_i2347_2_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (theEntity.isWithinHomeDistanceCurrentPosition()) {
			return false;
		} else {
			final BlockPos var1 = theEntity.func_180486_cf();
			final Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7,
					new Vec3(var1.getX(), var1.getY(), var1.getZ()));

			if (var2 == null) {
				return false;
			} else {
				movePosX = var2.xCoord;
				movePosY = var2.yCoord;
				movePosZ = var2.zCoord;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !theEntity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, movementSpeed);
	}
}
