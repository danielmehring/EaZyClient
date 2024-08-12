package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAICreeperSwell extends EntityAIBase {

public static final int EaZy = 1049;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The creeper that is swelling. */
	EntityCreeper swellingCreeper;

	/**
	 * The creeper's attack target. This is used for the changing of the
	 * creeper's state.
	 */
	EntityLivingBase creeperAttackTarget;
	// private static final String __OBFID = "http://https://fuckuskid00001614";

	public EntityAICreeperSwell(final EntityCreeper p_i1655_1_) {
		swellingCreeper = p_i1655_1_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final EntityLivingBase var1 = swellingCreeper.getAttackTarget();
		return swellingCreeper.getCreeperState() > 0
				|| var1 != null && swellingCreeper.getDistanceSqToEntity(var1) < 9.0D;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		swellingCreeper.getNavigator().clearPathEntity();
		creeperAttackTarget = swellingCreeper.getAttackTarget();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		creeperAttackTarget = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		if (creeperAttackTarget == null) {
			swellingCreeper.setCreeperState(-1);
		} else if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49.0D) {
			swellingCreeper.setCreeperState(-1);
		} else if (!swellingCreeper.getEntitySenses().canSee(creeperAttackTarget)) {
			swellingCreeper.setCreeperState(-1);
		} else {
			swellingCreeper.setCreeperState(1);
		}
	}
}
