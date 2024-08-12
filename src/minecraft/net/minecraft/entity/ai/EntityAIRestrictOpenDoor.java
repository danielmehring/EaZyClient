package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {

public static final int EaZy = 1079;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature entityObj;
	private VillageDoorInfo frontDoor;
	// private static final String __OBFID = "http://https://fuckuskid00001610";

	public EntityAIRestrictOpenDoor(final EntityCreature p_i1651_1_) {
		entityObj = p_i1651_1_;

		if (!(p_i1651_1_.getNavigator() instanceof PathNavigateGround)) {
			throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (entityObj.worldObj.isDaytime()) {
			return false;
		} else {
			final BlockPos var1 = new BlockPos(entityObj);
			final Village var2 = entityObj.worldObj.getVillageCollection().func_176056_a(var1, 16);

			if (var2 == null) {
				return false;
			} else {
				frontDoor = var2.func_179865_b(var1);
				return frontDoor == null ? false : frontDoor.func_179846_b(var1) < 2.25D;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return entityObj.worldObj.isDaytime() ? false
				: !frontDoor.func_179851_i() && frontDoor.func_179850_c(new BlockPos(entityObj));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		((PathNavigateGround) entityObj.getNavigator()).func_179688_b(false);
		((PathNavigateGround) entityObj.getNavigator()).func_179691_c(false);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		((PathNavigateGround) entityObj.getNavigator()).func_179688_b(true);
		((PathNavigateGround) entityObj.getNavigator()).func_179691_c(true);
		frontDoor = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		frontDoor.incrementDoorOpeningRestrictionCounter();
	}
}
