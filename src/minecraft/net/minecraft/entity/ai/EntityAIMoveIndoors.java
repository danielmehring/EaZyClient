package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveIndoors extends EntityAIBase {

public static final int EaZy = 1066;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature entityObj;
	private VillageDoorInfo doorInfo;
	private int insidePosX = -1;
	private int insidePosZ = -1;
	// private static final String __OBFID = "http://https://fuckuskid00001596";

	public EntityAIMoveIndoors(final EntityCreature p_i1637_1_) {
		entityObj = p_i1637_1_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final BlockPos var1 = new BlockPos(entityObj);

		if ((!entityObj.worldObj.isDaytime() || entityObj.worldObj.isRaining()
				&& !entityObj.worldObj.getBiomeGenForCoords(var1).canSpawnLightningBolt())
				&& !entityObj.worldObj.provider.getHasNoSky()) {
			if (entityObj.getRNG().nextInt(50) != 0) {
				return false;
			} else if (insidePosX != -1 && entityObj.getDistanceSq(insidePosX, entityObj.posY, insidePosZ) < 4.0D) {
				return false;
			} else {
				final Village var2 = entityObj.worldObj.getVillageCollection().func_176056_a(var1, 14);

				if (var2 == null) {
					return false;
				} else {
					doorInfo = var2.func_179863_c(var1);
					return doorInfo != null;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !entityObj.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		insidePosX = -1;
		final BlockPos var1 = doorInfo.func_179856_e();
		final int var2 = var1.getX();
		final int var3 = var1.getY();
		final int var4 = var1.getZ();

		if (entityObj.getDistanceSq(var1) > 256.0D) {
			final Vec3 var5 = RandomPositionGenerator.findRandomTargetBlockTowards(entityObj, 14, 3,
					new Vec3(var2 + 0.5D, var3, var4 + 0.5D));

			if (var5 != null) {
				entityObj.getNavigator().tryMoveToXYZ(var5.xCoord, var5.yCoord, var5.zCoord, 1.0D);
			}
		} else {
			entityObj.getNavigator().tryMoveToXYZ(var2 + 0.5D, var3, var4 + 0.5D, 1.0D);
		}
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		insidePosX = doorInfo.func_179856_e().getX();
		insidePosZ = doorInfo.func_179856_e().getZ();
		doorInfo = null;
	}
}
