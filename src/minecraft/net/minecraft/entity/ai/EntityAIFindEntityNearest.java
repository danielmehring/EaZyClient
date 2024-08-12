package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicate;

public class EntityAIFindEntityNearest extends EntityAIBase {

public static final int EaZy = 1053;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_179444_a = LogManager.getLogger();
	private EntityLiving field_179442_b;
	private final Predicate field_179443_c;
	private final EntityAINearestAttackableTarget.Sorter field_179440_d;
	private EntityLivingBase field_179441_e;
	private Class field_179439_f;
	// private static final String __OBFID = "http://https://fuckuskid00002250";

	public EntityAIFindEntityNearest(final EntityLiving p_i45884_1_, final Class p_i45884_2_) {
		field_179442_b = p_i45884_1_;
		field_179439_f = p_i45884_2_;

		if (p_i45884_1_ instanceof EntityCreature) {
			field_179444_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
		}

		field_179443_c = new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002249";
			public boolean func_179876_a(final EntityLivingBase p_179876_1_) {
				double var2 = EntityAIFindEntityNearest.this.func_179438_f();

				if (p_179876_1_.isSneaking()) {
					var2 *= 0.800000011920929D;
				}

				return p_179876_1_.isInvisible() ? false
						: p_179876_1_.getDistanceToEntity(field_179442_b) > var2 ? false
								: EntityAITarget.func_179445_a(field_179442_b, p_179876_1_, false, true);
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_179876_a((EntityLivingBase) p_apply_1_);
			}
		};
		field_179440_d = new EntityAINearestAttackableTarget.Sorter(p_i45884_1_);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		final double var1 = func_179438_f();
		final List var3 = field_179442_b.worldObj.func_175647_a(field_179439_f,
				field_179442_b.getEntityBoundingBox().expand(var1, 4.0D, var1), field_179443_c);
		Collections.sort(var3, field_179440_d);

		if (var3.isEmpty()) {
			return false;
		} else {
			field_179441_e = (EntityLivingBase) var3.get(0);
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		final EntityLivingBase var1 = field_179442_b.getAttackTarget();

		if (var1 == null) {
			return false;
		} else if (!var1.isEntityAlive()) {
			return false;
		} else {
			final double var2 = func_179438_f();
			return field_179442_b.getDistanceSqToEntity(var1) > var2 * var2 ? false
					: !(var1 instanceof EntityPlayerMP) || !((EntityPlayerMP) var1).theItemInWorldManager.isCreative();
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		field_179442_b.setAttackTarget(field_179441_e);
		super.startExecuting();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		field_179442_b.setAttackTarget((EntityLivingBase) null);
		super.startExecuting();
	}

	protected double func_179438_f() {
		final IAttributeInstance var1 = field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
		return var1 == null ? 16.0D : var1.getAttributeValue();
	}
}
