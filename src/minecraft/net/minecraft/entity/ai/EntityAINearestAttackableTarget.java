package net.minecraft.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityAINearestAttackableTarget extends EntityAITarget {

public static final int EaZy = 1071;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Class targetClass;
	private final int targetChance;

	/** Instance of EntityAINearestAttackableTargetSorter. */
	protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;

	/**
	 * This filter is applied to the Entity search. Only matching entities will
	 * be targetted. (null -> no restrictions)
	 */
	protected Predicate targetEntitySelector;
	protected EntityLivingBase targetEntity;
	// private static final String __OBFID = "http://https://fuckuskid00001620";

	public EntityAINearestAttackableTarget(final EntityCreature p_i45878_1_, final Class p_i45878_2_,
			final boolean p_i45878_3_) {
		this(p_i45878_1_, p_i45878_2_, p_i45878_3_, false);
	}

	public EntityAINearestAttackableTarget(final EntityCreature p_i45879_1_, final Class p_i45879_2_,
			final boolean p_i45879_3_, final boolean p_i45879_4_) {
		this(p_i45879_1_, p_i45879_2_, 10, p_i45879_3_, p_i45879_4_, (Predicate) null);
	}

	public EntityAINearestAttackableTarget(final EntityCreature p_i45880_1_, final Class p_i45880_2_,
			final int p_i45880_3_, final boolean p_i45880_4_, final boolean p_i45880_5_, final Predicate p_i45880_6_) {
		super(p_i45880_1_, p_i45880_4_, p_i45880_5_);
		targetClass = p_i45880_2_;
		targetChance = p_i45880_3_;
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(p_i45880_1_);
		setMutexBits(1);
		targetEntitySelector = new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001621";
			public boolean func_179878_a(final EntityLivingBase p_179878_1_) {
				if (p_i45880_6_ != null && !p_i45880_6_.apply(p_179878_1_)) {
					return false;
				} else {
					if (p_179878_1_ instanceof EntityPlayer) {
						double var2 = EntityAINearestAttackableTarget.this.getTargetDistance();

						if (p_179878_1_.isSneaking()) {
							var2 *= 0.800000011920929D;
						}

						if (p_179878_1_.isInvisible()) {
							float var4 = ((EntityPlayer) p_179878_1_).getArmorVisibility();

							if (var4 < 0.1F) {
								var4 = 0.1F;
							}

							var2 *= 0.7F * var4;
						}

						if (p_179878_1_.getDistanceToEntity(EntityAINearestAttackableTarget.this.taskOwner) > var2) {
							return false;
						}
					}

					return EntityAINearestAttackableTarget.this.isSuitableTarget(p_179878_1_, false);
				}
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_179878_a((EntityLivingBase) p_apply_1_);
			}
		};
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) {
			return false;
		} else {
			final double var1 = getTargetDistance();
			final List var3 = taskOwner.worldObj.func_175647_a(targetClass,
					taskOwner.getEntityBoundingBox().expand(var1, 4.0D, var1),
					Predicates.and(targetEntitySelector, IEntitySelector.field_180132_d));
			Collections.sort(var3, theNearestAttackableTargetSorter);

			if (var3.isEmpty()) {
				return false;
			} else {
				targetEntity = (EntityLivingBase) var3.get(0);
				return true;
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}

	public static class Sorter implements Comparator {
		private final Entity theEntity;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001622";

		public Sorter(final Entity p_i1662_1_) {
			theEntity = p_i1662_1_;
		}

		public int compare(final Entity p_compare_1_, final Entity p_compare_2_) {
			final double var3 = theEntity.getDistanceSqToEntity(p_compare_1_);
			final double var5 = theEntity.getDistanceSqToEntity(p_compare_2_);
			return var3 < var5 ? -1 : var3 > var5 ? 1 : 0;
		}

		@Override
		public int compare(final Object p_compare_1_, final Object p_compare_2_) {
			return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
		}
	}
}
