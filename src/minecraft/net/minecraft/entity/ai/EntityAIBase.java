package net.minecraft.entity.ai;

public abstract class EntityAIBase {

public static final int EaZy = 1045;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * A bitmask telling which other tasks may not run concurrently. The test is
	 * a simple bitwise AND - if it yields zero, the two tasks may run
	 * concurrently, if not - they must run exclusively from each other.
	 */
	private int mutexBits;
	// private static final String __OBFID = "http://https://fuckuskid00001587";

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public abstract boolean shouldExecute();

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return shouldExecute();
	}

	/**
	 * Determine if this AI Task is interruptible by a higher (= lower value)
	 * priority task. All vanilla AITask have this value set to true.
	 */
	public boolean isInterruptible() {
		return true;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {}

	/**
	 * Resets the task
	 */
	public void resetTask() {}

	/**
	 * Updates the task
	 */
	public void updateTask() {}

	/**
	 * Sets a bitmask telling which other tasks may not run concurrently. The
	 * test is a simple bitwise AND - if it yields zero, the two tasks may run
	 * concurrently, if not - they must run exclusively from each other.
	 */
	public void setMutexBits(final int p_75248_1_) {
		mutexBits = p_75248_1_;
	}

	/**
	 * Get a bitmask telling which other tasks may not run concurrently. The
	 * test is a simple bitwise AND - if it yields zero, the two tasks may run
	 * concurrently, if not - they must run exclusively from each other.
	 */
	public int getMutexBits() {
		return mutexBits;
	}
}
