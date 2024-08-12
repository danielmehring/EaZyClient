package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {

public static final int EaZy = 1043;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	World worldObj;
	protected EntityCreature attacker;

	/**
	 * An amount of decrementing ticks that allows the entity to attack once the
	 * tick reaches 0.
	 */
	int attackTick;

	/** The speed with which the mob will approach the target */
	double speedTowardsTarget;

	/**
	 * When true, the mob will continue chasing its target, even if it can't
	 * find a path to them right now.
	 */
	boolean longMemory;

	/** The PathEntity of our entity. */
	PathEntity entityPathEntity;
	Class classTarget;
	private int field_75445_i;
	private double field_151497_i;
	private double field_151495_j;
	private double field_151496_k;
	// private static final String __OBFID = "http://https://fuckuskid00001595";

	public EntityAIAttackOnCollide(final EntityCreature p_i1635_1_, final Class p_i1635_2_, final double p_i1635_3_,
			final boolean p_i1635_5_) {
		this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
		classTarget = p_i1635_2_;
	}

	public EntityAIAttackOnCollide(final EntityCreature p_i1636_1_, final double p_i1636_2_, final boolean p_i1636_4_) {
		attacker = p_i1636_1_;
		worldObj = p_i1636_1_.worldObj;
		speedTowardsTarget = p_i1636_2_;
		longMemory = p_i1636_4_;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final EntityLivingBase var1 = attacker.getAttackTarget();

		if (var1 == null) {
			return false;
		} else if (!var1.isEntityAlive()) {
			return false;
		} else if (classTarget != null && !classTarget.isAssignableFrom(var1.getClass())) {
			return false;
		} else {
			entityPathEntity = attacker.getNavigator().getPathToEntityLiving(var1);
			return entityPathEntity != null;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase var1 = attacker.getAttackTarget();
		return var1 == null ? false
				: !var1.isEntityAlive() ? false
						: !longMemory ? !attacker.getNavigator().noPath() : attacker.func_180485_d(new BlockPos(var1));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		attacker.getNavigator().setPath(entityPathEntity, speedTowardsTarget);
		field_75445_i = 0;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		attacker.getNavigator().clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final EntityLivingBase var1 = attacker.getAttackTarget();
		attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
		final double var2 = attacker.getDistanceSq(var1.posX, var1.getEntityBoundingBox().minY, var1.posZ);
		final double var4 = func_179512_a(var1);
		--field_75445_i;

		if ((longMemory || attacker.getEntitySenses().canSee(var1)) && field_75445_i <= 0
				&& (field_151497_i == 0.0D && field_151495_j == 0.0D && field_151496_k == 0.0D
						|| var1.getDistanceSq(field_151497_i, field_151495_j, field_151496_k) >= 1.0D
						|| attacker.getRNG().nextFloat() < 0.05F)) {
			field_151497_i = var1.posX;
			field_151495_j = var1.getEntityBoundingBox().minY;
			field_151496_k = var1.posZ;
			field_75445_i = 4 + attacker.getRNG().nextInt(7);

			if (var2 > 1024.0D) {
				field_75445_i += 10;
			} else if (var2 > 256.0D) {
				field_75445_i += 5;
			}

			if (!attacker.getNavigator().tryMoveToEntityLiving(var1, speedTowardsTarget)) {
				field_75445_i += 15;
			}
		}

		attackTick = Math.max(attackTick - 1, 0);

		if (var2 <= var4 && attackTick <= 0) {
			attackTick = 20;

			if (attacker.getHeldItem() != null) {
				attacker.swingItem();
			}

			attacker.attackEntityAsMob(var1);
		}
	}

	protected double func_179512_a(final EntityLivingBase p_179512_1_) {
		return attacker.width * 2.0F * attacker.width * 2.0F + p_179512_1_.width;
	}
}
