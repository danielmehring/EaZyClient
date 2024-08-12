package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityAnimal;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase {

public static final int EaZy = 1058;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The child that is following its parent. */
	EntityAnimal childAnimal;
	EntityAnimal parentAnimal;
	double field_75347_c;
	private int field_75345_d;
	// private static final String __OBFID = "http://https://fuckuskid00001586";

	public EntityAIFollowParent(final EntityAnimal p_i1626_1_, final double p_i1626_2_) {
		childAnimal = p_i1626_1_;
		field_75347_c = p_i1626_2_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (childAnimal.getGrowingAge() >= 0) {
			return false;
		} else {
			final List var1 = childAnimal.worldObj.getEntitiesWithinAABB(childAnimal.getClass(),
					childAnimal.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D));
			EntityAnimal var2 = null;
			double var3 = Double.MAX_VALUE;
			final Iterator var5 = var1.iterator();

			while (var5.hasNext()) {
				final EntityAnimal var6 = (EntityAnimal) var5.next();

				if (var6.getGrowingAge() >= 0) {
					final double var7 = childAnimal.getDistanceSqToEntity(var6);

					if (var7 <= var3) {
						var3 = var7;
						var2 = var6;
					}
				}
			}

			if (var2 == null) {
				return false;
			} else if (var3 < 9.0D) {
				return false;
			} else {
				parentAnimal = var2;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		if (childAnimal.getGrowingAge() >= 0) {
			return false;
		} else if (!parentAnimal.isEntityAlive()) {
			return false;
		} else {
			final double var1 = childAnimal.getDistanceSqToEntity(parentAnimal);
			return var1 >= 9.0D && var1 <= 256.0D;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		field_75345_d = 0;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		parentAnimal = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		if (--field_75345_d <= 0) {
			field_75345_d = 10;
			childAnimal.getNavigator().tryMoveToEntityLiving(parentAnimal, field_75347_c);
		}
	}
}
