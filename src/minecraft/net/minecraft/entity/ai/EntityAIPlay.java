package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;

import java.util.Iterator;
import java.util.List;

public class EntityAIPlay extends EntityAIBase {

public static final int EaZy = 1078;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityVillager villagerObj;
	private EntityLivingBase targetVillager;
	private final double field_75261_c;
	private int playTime;
	// private static final String __OBFID = "http://https://fuckuskid00001605";

	public EntityAIPlay(final EntityVillager p_i1646_1_, final double p_i1646_2_) {
		villagerObj = p_i1646_1_;
		field_75261_c = p_i1646_2_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (villagerObj.getGrowingAge() >= 0) {
			return false;
		} else if (villagerObj.getRNG().nextInt(400) != 0) {
			return false;
		} else {
			final List var1 = villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class,
					villagerObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
			double var2 = Double.MAX_VALUE;
			final Iterator var4 = var1.iterator();

			while (var4.hasNext()) {
				final EntityVillager var5 = (EntityVillager) var4.next();

				if (var5 != villagerObj && !var5.isPlaying() && var5.getGrowingAge() < 0) {
					final double var6 = var5.getDistanceSqToEntity(villagerObj);

					if (var6 <= var2) {
						var2 = var6;
						targetVillager = var5;
					}
				}
			}

			if (targetVillager == null) {
				final Vec3 var8 = RandomPositionGenerator.findRandomTarget(villagerObj, 16, 3);

				if (var8 == null) {
					return false;
				}
			}

			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return playTime > 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		if (targetVillager != null) {
			villagerObj.setPlaying(true);
		}

		playTime = 1000;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		villagerObj.setPlaying(false);
		targetVillager = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		--playTime;

		if (targetVillager != null) {
			if (villagerObj.getDistanceSqToEntity(targetVillager) > 4.0D) {
				villagerObj.getNavigator().tryMoveToEntityLiving(targetVillager, field_75261_c);
			}
		} else if (villagerObj.getNavigator().noPath()) {
			final Vec3 var1 = RandomPositionGenerator.findRandomTarget(villagerObj, 16, 3);

			if (var1 == null) {
				return;
			}

			villagerObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, field_75261_c);
		}
	}
}
