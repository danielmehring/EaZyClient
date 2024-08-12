package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {

public static final int EaZy = 1042;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The entity the AI instance has been applied to */
	private final EntityLiving entityHost;

	/**
	 * The entity (as a RangedAttackMob) the AI instance has been applied to.
	 */
	private final IRangedAttackMob rangedAttackEntityHost;
	private EntityLivingBase attackTarget;

	/**
	 * A decrementing tick that spawns a ranged attack once this value reaches
	 * 0. It is then set back to the maxRangedAttackTime.
	 */
	private int rangedAttackTime;
	private double entityMoveSpeed;
	private int field_75318_f;
	private int field_96561_g;

	/**
	 * The maximum time the AI has to wait before peforming another ranged
	 * attack.
	 */
	private int maxRangedAttackTime;
	private float field_96562_i;
	private float maxAttackDistance;
	// private static final String __OBFID = "http://https://fuckuskid00001609";

	public EntityAIArrowAttack(final IRangedAttackMob p_i1649_1_, final double p_i1649_2_, final int p_i1649_4_,
			final float p_i1649_5_) {
		this(p_i1649_1_, p_i1649_2_, p_i1649_4_, p_i1649_4_, p_i1649_5_);
	}

	public EntityAIArrowAttack(final IRangedAttackMob p_i1650_1_, final double p_i1650_2_, final int p_i1650_4_,
			final int p_i1650_5_, final float p_i1650_6_) {
		rangedAttackTime = -1;

		if (!(p_i1650_1_ instanceof EntityLivingBase)) {
			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		} else {
			rangedAttackEntityHost = p_i1650_1_;
			entityHost = (EntityLiving) p_i1650_1_;
			entityMoveSpeed = p_i1650_2_;
			field_96561_g = p_i1650_4_;
			maxRangedAttackTime = p_i1650_5_;
			field_96562_i = p_i1650_6_;
			maxAttackDistance = p_i1650_6_ * p_i1650_6_;
			setMutexBits(3);
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final EntityLivingBase var1 = entityHost.getAttackTarget();

		if (var1 == null) {
			return false;
		} else {
			attackTarget = var1;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return shouldExecute() || !entityHost.getNavigator().noPath();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		attackTarget = null;
		field_75318_f = 0;
		rangedAttackTime = -1;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		final double var1 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY,
				attackTarget.posZ);
		final boolean var3 = entityHost.getEntitySenses().canSee(attackTarget);

		if (var3) {
			++field_75318_f;
		} else {
			field_75318_f = 0;
		}

		if (var1 <= maxAttackDistance && field_75318_f >= 20) {
			entityHost.getNavigator().clearPathEntity();
		} else {
			entityHost.getNavigator().tryMoveToEntityLiving(attackTarget, entityMoveSpeed);
		}

		entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
		float var4;

		if (--rangedAttackTime == 0) {
			if (var1 > maxAttackDistance || !var3) {
				return;
			}

			var4 = MathHelper.sqrt_double(var1) / field_96562_i;
			final float var5 = MathHelper.clamp_float(var4, 0.1F, 1.0F);
			rangedAttackEntityHost.attackEntityWithRangedAttack(attackTarget, var5);
			rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
		} else if (rangedAttackTime < 0) {
			var4 = MathHelper.sqrt_double(var1) / field_96562_i;
			rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
		}
	}
}
