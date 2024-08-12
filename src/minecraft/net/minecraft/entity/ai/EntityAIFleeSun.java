package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAIFleeSun extends EntityAIBase {

public static final int EaZy = 1055;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityCreature theCreature;
	private double shelterX;
	private double shelterY;
	private double shelterZ;
	private final double movementSpeed;
	private final World theWorld;
	// private static final String __OBFID = "http://https://fuckuskid00001583";

	public EntityAIFleeSun(final EntityCreature p_i1623_1_, final double p_i1623_2_) {
		theCreature = p_i1623_1_;
		movementSpeed = p_i1623_2_;
		theWorld = p_i1623_1_.worldObj;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!theWorld.isDaytime()) {
			return false;
		} else if (!theCreature.isBurning()) {
			return false;
		} else if (!theWorld.isAgainstSky(
				new BlockPos(theCreature.posX, theCreature.getEntityBoundingBox().minY, theCreature.posZ))) {
			return false;
		} else {
			final Vec3 var1 = findPossibleShelter();

			if (var1 == null) {
				return false;
			} else {
				shelterX = var1.xCoord;
				shelterY = var1.yCoord;
				shelterZ = var1.zCoord;
				return true;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !theCreature.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theCreature.getNavigator().tryMoveToXYZ(shelterX, shelterY, shelterZ, movementSpeed);
	}

	private Vec3 findPossibleShelter() {
		final Random var1 = theCreature.getRNG();
		final BlockPos var2 = new BlockPos(theCreature.posX, theCreature.getEntityBoundingBox().minY, theCreature.posZ);

		for (int var3 = 0; var3 < 10; ++var3) {
			final BlockPos var4 = var2.add(var1.nextInt(20) - 10, var1.nextInt(6) - 3, var1.nextInt(20) - 10);

			if (!theWorld.isAgainstSky(var4) && theCreature.func_180484_a(var4) < 0.0F) {
				return new Vec3(var4.getX(), var4.getY(), var4.getZ());
			}
		}

		return null;
	}
}
