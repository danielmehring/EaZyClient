package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class EntityAIMoveThroughVillage extends EntityAIBase {

public static final int EaZy = 1067;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theEntity;
	private final double movementSpeed;

	/** The PathNavigate of our entity. */
	private PathEntity entityPathNavigate;
	private VillageDoorInfo doorInfo;
	private final boolean isNocturnal;
	private final List doorList = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001597";

	public EntityAIMoveThroughVillage(final EntityCreature p_i1638_1_, final double p_i1638_2_,
			final boolean p_i1638_4_) {
		theEntity = p_i1638_1_;
		movementSpeed = p_i1638_2_;
		isNocturnal = p_i1638_4_;
		setMutexBits(1);

		if (!(p_i1638_1_.getNavigator() instanceof PathNavigateGround)) {
			throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		func_75414_f();

		if (isNocturnal && theEntity.worldObj.isDaytime()) {
			return false;
		} else {
			final Village var1 = theEntity.worldObj.getVillageCollection().func_176056_a(new BlockPos(theEntity), 0);

			if (var1 == null) {
				return false;
			} else {
				doorInfo = func_75412_a(var1);

				if (doorInfo == null) {
					return false;
				} else {
					final PathNavigateGround var2 = (PathNavigateGround) theEntity.getNavigator();
					final boolean var3 = var2.func_179686_g();
					var2.func_179688_b(false);
					entityPathNavigate = var2.func_179680_a(doorInfo.func_179852_d());
					var2.func_179688_b(var3);

					if (entityPathNavigate != null) {
						return true;
					} else {
						final Vec3 var4 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 10, 7,
								new Vec3(doorInfo.func_179852_d().getX(), doorInfo.func_179852_d().getY(),
										doorInfo.func_179852_d().getZ()));

						if (var4 == null) {
							return false;
						} else {
							var2.func_179688_b(false);
							entityPathNavigate = theEntity.getNavigator().getPathToXYZ(var4.xCoord, var4.yCoord,
									var4.zCoord);
							var2.func_179688_b(var3);
							return entityPathNavigate != null;
						}
					}
				}
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		if (theEntity.getNavigator().noPath()) {
			return false;
		} else {
			final float var1 = theEntity.width + 4.0F;
			return theEntity.getDistanceSq(doorInfo.func_179852_d()) > var1 * var1;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theEntity.getNavigator().setPath(entityPathNavigate, movementSpeed);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		if (theEntity.getNavigator().noPath() || theEntity.getDistanceSq(doorInfo.func_179852_d()) < 16.0D) {
			doorList.add(doorInfo);
		}
	}

	private VillageDoorInfo func_75412_a(final Village p_75412_1_) {
		VillageDoorInfo var2 = null;
		int var3 = Integer.MAX_VALUE;
		final List var4 = p_75412_1_.getVillageDoorInfoList();
		final Iterator var5 = var4.iterator();

		while (var5.hasNext()) {
			final VillageDoorInfo var6 = (VillageDoorInfo) var5.next();
			final int var7 = var6.getDistanceSquared(MathHelper.floor_double(theEntity.posX),
					MathHelper.floor_double(theEntity.posY), MathHelper.floor_double(theEntity.posZ));

			if (var7 < var3 && !func_75413_a(var6)) {
				var2 = var6;
				var3 = var7;
			}
		}

		return var2;
	}

	private boolean func_75413_a(final VillageDoorInfo p_75413_1_) {
		final Iterator var2 = doorList.iterator();
		VillageDoorInfo var3;

		do {
			if (!var2.hasNext()) {
				return false;
			}

			var3 = (VillageDoorInfo) var2.next();
		}
		while (!p_75413_1_.func_179852_d().equals(var3.func_179852_d()));

		return true;
	}

	private void func_75414_f() {
		if (doorList.size() > 15) {
			doorList.remove(0);
		}
	}
}
