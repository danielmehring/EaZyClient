package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase {

public static final int EaZy = 1088;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityVillager villager;
	// private static final String __OBFID = "http://https://fuckuskid00001617";

	public EntityAITradePlayer(final EntityVillager p_i1658_1_) {
		villager = p_i1658_1_;
		setMutexBits(5);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!villager.isEntityAlive()) {
			return false;
		} else if (villager.isInWater()) {
			return false;
		} else if (!villager.onGround) {
			return false;
		} else if (villager.velocityChanged) {
			return false;
		} else {
			final EntityPlayer var1 = villager.getCustomer();
			return var1 == null ? false
					: villager.getDistanceSqToEntity(var1) > 16.0D ? false : var1.openContainer instanceof Container;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		villager.getNavigator().clearPathEntity();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		villager.setCustomer((EntityPlayer) null);
	}
}
