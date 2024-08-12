package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIOpenDoor extends EntityAIDoorInteract {

public static final int EaZy = 1074;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** If the entity close the door */
	boolean closeDoor;

	/**
	 * The temporisation before the entity close the door (in ticks, always 20 =
	 * 1 second)
	 */
	int closeDoorTemporisation;
	// private static final String __OBFID = "http://https://fuckuskid00001603";

	public EntityAIOpenDoor(final EntityLiving p_i1644_1_, final boolean p_i1644_2_) {
		super(p_i1644_1_);
		theEntity = p_i1644_1_;
		closeDoor = p_i1644_2_;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return closeDoor && closeDoorTemporisation > 0 && super.continueExecuting();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		closeDoorTemporisation = 20;
		doorBlock.func_176512_a(theEntity.worldObj, field_179507_b, true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		if (closeDoor) {
			doorBlock.func_176512_a(theEntity.worldObj, field_179507_b, false);
		}
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		--closeDoorTemporisation;
		super.updateTask();
	}
}
