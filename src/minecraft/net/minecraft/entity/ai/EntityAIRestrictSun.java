package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase {

public static final int EaZy = 1080;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theEntity;
	// private static final String __OBFID = "http://https://fuckuskid00001611";

	public EntityAIRestrictSun(final EntityCreature p_i1652_1_) {
		theEntity = p_i1652_1_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return theEntity.worldObj.isDaytime();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		((PathNavigateGround) theEntity.getNavigator()).func_179685_e(true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		((PathNavigateGround) theEntity.getNavigator()).func_179685_e(false);
	}
}
