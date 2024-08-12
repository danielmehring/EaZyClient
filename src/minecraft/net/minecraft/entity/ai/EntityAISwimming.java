package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAISwimming extends EntityAIBase {

public static final int EaZy = 1083;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityLiving theEntity;
	// private static final String __OBFID = "http://https://fuckuskid00001584";

	public EntityAISwimming(final EntityLiving p_i1624_1_) {
		theEntity = p_i1624_1_;
		setMutexBits(4);
		((PathNavigateGround) p_i1624_1_.getNavigator()).func_179693_d(true);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return theEntity.isInWater() || theEntity.func_180799_ab();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		if (theEntity.getRNG().nextFloat() < 0.8F) {
			theEntity.getJumpHelper().setJumping();
		}
	}
}
