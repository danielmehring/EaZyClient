package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;

public abstract class EntityAIDoorInteract extends EntityAIBase {

public static final int EaZy = 1051;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected EntityLiving theEntity;
	protected BlockPos field_179507_b;

	/** The wooden door block */
	protected BlockDoor doorBlock;

	/**
	 * If is true then the Entity has stopped Door Interaction and compoleted
	 * the task.
	 */
	boolean hasStoppedDoorInteraction;
	float entityPositionX;
	float entityPositionZ;
	// private static final String __OBFID = "http://https://fuckuskid00001581";

	public EntityAIDoorInteract(final EntityLiving p_i1621_1_) {
		field_179507_b = BlockPos.ORIGIN;
		theEntity = p_i1621_1_;

		if (!(p_i1621_1_.getNavigator() instanceof PathNavigateGround)) {
			throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!theEntity.isCollidedHorizontally) {
			return false;
		} else {
			final PathNavigateGround var1 = (PathNavigateGround) theEntity.getNavigator();
			final PathEntity var2 = var1.getPath();

			if (var2 != null && !var2.isFinished() && var1.func_179686_g()) {
				for (int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2,
						var2.getCurrentPathLength()); ++var3) {
					final PathPoint var4 = var2.getPathPointFromIndex(var3);
					field_179507_b = new BlockPos(var4.xCoord, var4.yCoord + 1, var4.zCoord);

					if (theEntity.getDistanceSq(field_179507_b.getX(), theEntity.posY,
							field_179507_b.getZ()) <= 2.25D) {
						doorBlock = func_179506_a(field_179507_b);

						if (doorBlock != null) {
							return true;
						}
					}
				}

				field_179507_b = new BlockPos(theEntity).offsetUp();
				doorBlock = func_179506_a(field_179507_b);
				return doorBlock != null;
			} else {
				return false;
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !hasStoppedDoorInteraction;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		hasStoppedDoorInteraction = false;
		entityPositionX = (float) (field_179507_b.getX() + 0.5F - theEntity.posX);
		entityPositionZ = (float) (field_179507_b.getZ() + 0.5F - theEntity.posZ);
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final float var1 = (float) (field_179507_b.getX() + 0.5F - theEntity.posX);
		final float var2 = (float) (field_179507_b.getZ() + 0.5F - theEntity.posZ);
		final float var3 = entityPositionX * var1 + entityPositionZ * var2;

		if (var3 < 0.0F) {
			hasStoppedDoorInteraction = true;
		}
	}

	private BlockDoor func_179506_a(final BlockPos p_179506_1_) {
		final Block var2 = theEntity.worldObj.getBlockState(p_179506_1_).getBlock();
		return var2 instanceof BlockDoor && var2.getMaterial() == Material.wood ? (BlockDoor) var2 : null;
	}
}
