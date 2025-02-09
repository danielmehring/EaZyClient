package net.minecraft.entity.ai;

import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowGolem extends EntityAIBase {

public static final int EaZy = 1056;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityVillager theVillager;
	private EntityIronGolem theGolem;
	private int takeGolemRoseTick;
	private boolean tookGolemRose;
	// private static final String __OBFID = "http://https://fuckuskid00001615";

	public EntityAIFollowGolem(final EntityVillager p_i1656_1_) {
		theVillager = p_i1656_1_;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (theVillager.getGrowingAge() >= 0) {
			return false;
		} else if (!theVillager.worldObj.isDaytime()) {
			return false;
		} else {
			final List var1 = theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class,
					theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));

			if (var1.isEmpty()) {
				return false;
			} else {
				final Iterator var2 = var1.iterator();

				while (var2.hasNext()) {
					final EntityIronGolem var3 = (EntityIronGolem) var2.next();

					if (var3.getHoldRoseTick() > 0) {
						theGolem = var3;
						break;
					}
				}

				return theGolem != null;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return theGolem.getHoldRoseTick() > 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		takeGolemRoseTick = theVillager.getRNG().nextInt(320);
		tookGolemRose = false;
		theGolem.getNavigator().clearPathEntity();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		theGolem = null;
		theVillager.getNavigator().clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		theVillager.getLookHelper().setLookPositionWithEntity(theGolem, 30.0F, 30.0F);

		if (theGolem.getHoldRoseTick() == takeGolemRoseTick) {
			theVillager.getNavigator().tryMoveToEntityLiving(theGolem, 0.5D);
			tookGolemRose = true;
		}

		if (tookGolemRose && theVillager.getDistanceSqToEntity(theGolem) < 4.0D) {
			theGolem.setHoldingRose(false);
			theVillager.getNavigator().clearPathEntity();
		}
	}
}
