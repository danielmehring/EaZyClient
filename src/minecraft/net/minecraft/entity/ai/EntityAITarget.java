package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget extends EntityAIBase {

public static final int EaZy = 1084;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The entity that this task belongs to */
	protected final EntityCreature taskOwner;

	/**
	 * If true, EntityAI targets must be able to be seen (cannot be blocked by
	 * walls) to be suitable targets.
	 */
	protected boolean shouldCheckSight;

	/**
	 * When true, only entities that can be reached with minimal effort will be
	 * targetted.
	 */
	private final boolean nearbyOnly;

	/**
	 * When nearbyOnly is true: 0 -> No target, but OK to search; 1 -> Nearby
	 * target found; 2 -> Target too far.
	 */
	private int targetSearchStatus;

	/**
	 * When nearbyOnly is true, this throttles target searching to avoid
	 * excessive pathfinding.
	 */
	private int targetSearchDelay;

	/**
	 * If @shouldCheckSight is true, the number of ticks before the interuption
	 * of this AITastk when the entity does't see the target
	 */
	private int targetUnseenTicks;
	// private static final String __OBFID = "http://https://fuckuskid00001626";

	public EntityAITarget(final EntityCreature p_i1669_1_, final boolean p_i1669_2_) {
		this(p_i1669_1_, p_i1669_2_, false);
	}

	public EntityAITarget(final EntityCreature p_i1670_1_, final boolean p_i1670_2_, final boolean p_i1670_3_) {
		taskOwner = p_i1670_1_;
		shouldCheckSight = p_i1670_2_;
		nearbyOnly = p_i1670_3_;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase var1 = taskOwner.getAttackTarget();

		if (var1 == null) {
			return false;
		} else if (!var1.isEntityAlive()) {
			return false;
		} else {
			final Team var2 = taskOwner.getTeam();
			final Team var3 = var1.getTeam();

			if (var2 != null && var3 == var2) {
				return false;
			} else {
				final double var4 = getTargetDistance();

				if (taskOwner.getDistanceSqToEntity(var1) > var4 * var4) {
					return false;
				} else {
					if (shouldCheckSight) {
						if (taskOwner.getEntitySenses().canSee(var1)) {
							targetUnseenTicks = 0;
						} else if (++targetUnseenTicks > 60) {
							return false;
						}
					}

					return !(var1 instanceof EntityPlayer) || !((EntityPlayer) var1).capabilities.disableDamage;
				}
			}
		}
	}

	protected double getTargetDistance() {
		final IAttributeInstance var1 = taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
		return var1 == null ? 16.0D : var1.getAttributeValue();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		targetSearchStatus = 0;
		targetSearchDelay = 0;
		targetUnseenTicks = 0;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		taskOwner.setAttackTarget((EntityLivingBase) null);
	}

	public static boolean func_179445_a(final EntityLiving p_179445_0_, final EntityLivingBase p_179445_1_,
			final boolean p_179445_2_, final boolean p_179445_3_) {
		if (p_179445_1_ == null) {
			return false;
		} else if (p_179445_1_ == p_179445_0_) {
			return false;
		} else if (!p_179445_1_.isEntityAlive()) {
			return false;
		} else if (!p_179445_0_.canAttackClass(p_179445_1_.getClass())) {
			return false;
		} else {
			final Team var4 = p_179445_0_.getTeam();
			final Team var5 = p_179445_1_.getTeam();

			if (var4 != null && var5 == var4) {
				return false;
			} else {
				if (p_179445_0_ instanceof IEntityOwnable
						&& StringUtils.isNotEmpty(((IEntityOwnable) p_179445_0_).func_152113_b())) {
					if (p_179445_1_ instanceof IEntityOwnable && ((IEntityOwnable) p_179445_0_).func_152113_b()
							.equals(((IEntityOwnable) p_179445_1_).func_152113_b())) {
						return false;
					}

					if (p_179445_1_ == ((IEntityOwnable) p_179445_0_).getOwner()) {
						return false;
					}
				} else if (p_179445_1_ instanceof EntityPlayer && !p_179445_2_
						&& ((EntityPlayer) p_179445_1_).capabilities.disableDamage) {
					return false;
				}

				return !p_179445_3_ || p_179445_0_.getEntitySenses().canSee(p_179445_1_);
			}
		}
	}

	/**
	 * A method used to see if an entity is a suitable target through a number
	 * of checks. Args : entity, canTargetInvinciblePlayer
	 */
	protected boolean isSuitableTarget(final EntityLivingBase p_75296_1_, final boolean p_75296_2_) {
		if (!func_179445_a(taskOwner, p_75296_1_, p_75296_2_, shouldCheckSight)) {
			return false;
		} else if (!taskOwner.func_180485_d(new BlockPos(p_75296_1_))) {
			return false;
		} else {
			if (nearbyOnly) {
				if (--targetSearchDelay <= 0) {
					targetSearchStatus = 0;
				}

				if (targetSearchStatus == 0) {
					targetSearchStatus = canEasilyReach(p_75296_1_) ? 1 : 2;
				}

				if (targetSearchStatus == 2) {
					return false;
				}
			}

			return true;
		}
	}

	/**
	 * Checks to see if this entity can find a short path to the given target.
	 */
	private boolean canEasilyReach(final EntityLivingBase p_75295_1_) {
		targetSearchDelay = 10 + taskOwner.getRNG().nextInt(5);
		final PathEntity var2 = taskOwner.getNavigator().getPathToEntityLiving(p_75295_1_);

		if (var2 == null) {
			return false;
		} else {
			final PathPoint var3 = var2.getFinalPathPoint();

			if (var3 == null) {
				return false;
			} else {
				final int var4 = var3.xCoord - MathHelper.floor_double(p_75295_1_.posX);
				final int var5 = var3.zCoord - MathHelper.floor_double(p_75295_1_.posZ);
				return var4 * var4 + var5 * var5 <= 2.25D;
			}
		}
	}
}
