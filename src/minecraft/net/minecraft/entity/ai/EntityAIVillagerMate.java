package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIVillagerMate extends EntityAIBase {

public static final int EaZy = 1090;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityVillager villagerObj;
	private EntityVillager mate;
	private final World worldObj;
	private int matingTimeout;
	Village villageObj;
	// private static final String __OBFID = "http://https://fuckuskid00001594";

	public EntityAIVillagerMate(final EntityVillager p_i1634_1_) {
		villagerObj = p_i1634_1_;
		worldObj = p_i1634_1_.worldObj;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (villagerObj.getGrowingAge() != 0) {
			return false;
		} else if (villagerObj.getRNG().nextInt(500) != 0) {
			return false;
		} else {
			villageObj = worldObj.getVillageCollection().func_176056_a(new BlockPos(villagerObj), 0);

			if (villageObj == null) {
				return false;
			} else if (checkSufficientDoorsPresentForNewVillager() && villagerObj.func_175550_n(true)) {
				final Entity var1 = worldObj.findNearestEntityWithinAABB(EntityVillager.class,
						villagerObj.getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D), villagerObj);

				if (var1 == null) {
					return false;
				} else {
					mate = (EntityVillager) var1;
					return mate.getGrowingAge() == 0 && mate.func_175550_n(true);
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		matingTimeout = 300;
		villagerObj.setMating(true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		villageObj = null;
		mate = null;
		villagerObj.setMating(false);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return matingTimeout >= 0 && checkSufficientDoorsPresentForNewVillager() && villagerObj.getGrowingAge() == 0
				&& villagerObj.func_175550_n(false);
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		--matingTimeout;
		villagerObj.getLookHelper().setLookPositionWithEntity(mate, 10.0F, 30.0F);

		if (villagerObj.getDistanceSqToEntity(mate) > 2.25D) {
			villagerObj.getNavigator().tryMoveToEntityLiving(mate, 0.25D);
		} else if (matingTimeout == 0 && mate.isMating()) {
			giveBirth();
		}

		if (villagerObj.getRNG().nextInt(35) == 0) {
			worldObj.setEntityState(villagerObj, (byte) 12);
		}
	}

	private boolean checkSufficientDoorsPresentForNewVillager() {
		if (!villageObj.isMatingSeason()) {
			return false;
		} else {
			final int var1 = (int) (villageObj.getNumVillageDoors() * 0.35D);
			return villageObj.getNumVillagers() < var1;
		}
	}

	private void giveBirth() {
		final EntityVillager var1 = villagerObj.func_180488_b(mate);
		mate.setGrowingAge(6000);
		villagerObj.setGrowingAge(6000);
		mate.func_175549_o(false);
		villagerObj.func_175549_o(false);
		var1.setGrowingAge(-24000);
		var1.setLocationAndAngles(villagerObj.posX, villagerObj.posY, villagerObj.posZ, 0.0F, 0.0F);
		worldObj.spawnEntityInWorld(var1);
		worldObj.setEntityState(var1, (byte) 12);
	}
}
