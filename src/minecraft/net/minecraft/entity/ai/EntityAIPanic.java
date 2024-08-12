package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.Vec3;

public class EntityAIPanic extends EntityAIBase {

public static final int EaZy = 1077;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theEntityCreature;
	protected double speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;
	// private static final String __OBFID = "http://https://fuckuskid00001604";

	public EntityAIPanic(final EntityCreature p_i1645_1_, final double p_i1645_2_) {
		theEntityCreature = p_i1645_1_;
		speed = p_i1645_2_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (theEntityCreature.getAITarget() == null && !theEntityCreature.isBurning()) {
			return false;
		} else {
			final Vec3 var1 = RandomPositionGenerator.findRandomTarget(theEntityCreature, 5, 4);

			if (var1 == null) {
				return false;
			} else {
				randPosX = var1.xCoord;
				randPosY = var1.yCoord;
				randPosZ = var1.zCoord;
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theEntityCreature.getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !theEntityCreature.getNavigator().noPath();
	}
}
