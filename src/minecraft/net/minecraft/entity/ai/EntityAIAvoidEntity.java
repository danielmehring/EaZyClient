package net.minecraft.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityAIAvoidEntity extends EntityAIBase {

public static final int EaZy = 1044;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public final Predicate field_179509_a = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001575";
		public boolean func_180419_a(final Entity p_180419_1_) {
			return p_180419_1_.isEntityAlive() && theEntity.getEntitySenses().canSee(p_180419_1_);
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_180419_a((Entity) p_apply_1_);
		}
	};

	/** The entity we are attached to */
	protected EntityCreature theEntity;
	private final double farSpeed;
	private final double nearSpeed;
	protected Entity closestLivingEntity;
	private final float field_179508_f;

	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;

	/** The PathNavigate of our entity */
	private final PathNavigate entityPathNavigate;
	private final Predicate field_179510_i;
	// private static final String __OBFID = "http://https://fuckuskid00001574";

	public EntityAIAvoidEntity(final EntityCreature p_i45890_1_, final Predicate p_i45890_2_, final float p_i45890_3_,
			final double p_i45890_4_, final double p_i45890_6_) {
		theEntity = p_i45890_1_;
		field_179510_i = p_i45890_2_;
		field_179508_f = p_i45890_3_;
		farSpeed = p_i45890_4_;
		nearSpeed = p_i45890_6_;
		entityPathNavigate = p_i45890_1_.getNavigator();
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final List var1 = theEntity.worldObj.func_175674_a(theEntity,
				theEntity.getEntityBoundingBox().expand(field_179508_f, 3.0D, field_179508_f),
				Predicates.and(new Predicate[] { IEntitySelector.field_180132_d, field_179509_a, field_179510_i }));

		if (var1.isEmpty()) {
			return false;
		} else {
			closestLivingEntity = (Entity) var1.get(0);
			final Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theEntity, 16, 7,
					new Vec3(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));

			if (var2 == null) {
				return false;
			} else if (closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < closestLivingEntity
					.getDistanceSqToEntity(theEntity)) {
				return false;
			} else {
				entityPathEntity = entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
				return entityPathEntity == null ? false : entityPathEntity.isDestinationSame(var2);
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		entityPathNavigate.setPath(entityPathEntity, farSpeed);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		if (theEntity.getDistanceSqToEntity(closestLivingEntity) < 49.0D) {
			theEntity.getNavigator().setSpeed(nearSpeed);
		} else {
			theEntity.getNavigator().setSpeed(farSpeed);
		}
	}
}
