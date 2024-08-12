package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoor extends EntityAIDoorInteract {

public static final int EaZy = 1047;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int breakingTime;
	private int field_75358_j = -1;
	// private static final String __OBFID = "http://https://fuckuskid00001577";

	public EntityAIBreakDoor(final EntityLiving p_i1618_1_) {
		super(p_i1618_1_);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!super.shouldExecute()) {
			return false;
		} else if (!theEntity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
			return false;
		} else {
			return !BlockDoor.func_176514_f(theEntity.worldObj, field_179507_b);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		super.startExecuting();
		breakingTime = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final double var1 = theEntity.getDistanceSq(field_179507_b);
		boolean var3;

		if (breakingTime <= 240) {
			if (!BlockDoor.func_176514_f(theEntity.worldObj, field_179507_b) && var1 < 4.0D) {
				var3 = true;
				return var3;
			}
		}

		var3 = false;
		return var3;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		super.resetTask();
		theEntity.worldObj.sendBlockBreakProgress(theEntity.getEntityId(), field_179507_b, -1);
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		super.updateTask();

		if (theEntity.getRNG().nextInt(20) == 0) {
			theEntity.worldObj.playAuxSFX(1010, field_179507_b, 0);
		}

		++breakingTime;
		final int var1 = (int) (breakingTime / 240.0F * 10.0F);

		if (var1 != field_75358_j) {
			theEntity.worldObj.sendBlockBreakProgress(theEntity.getEntityId(), field_179507_b, var1);
			field_75358_j = var1;
		}

		if (breakingTime == 240 && theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
			theEntity.worldObj.setBlockToAir(field_179507_b);
			theEntity.worldObj.playAuxSFX(1012, field_179507_b, 0);
			theEntity.worldObj.playAuxSFX(2001, field_179507_b, Block.getIdFromBlock(doorBlock));
		}
	}
}
