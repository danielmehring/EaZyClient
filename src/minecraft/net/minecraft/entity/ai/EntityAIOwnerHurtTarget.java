package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAIOwnerHurtTarget extends EntityAITarget {

public static final int EaZy = 1076;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	EntityTameable theEntityTameable;
	EntityLivingBase theTarget;
	private int field_142050_e;
	// private static final String __OBFID = "http://https://fuckuskid00001625";

	public EntityAIOwnerHurtTarget(final EntityTameable p_i1668_1_) {
		super(p_i1668_1_, false);
		theEntityTameable = p_i1668_1_;
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (!theEntityTameable.isTamed()) {
			return false;
		} else {
			final EntityLivingBase var1 = theEntityTameable.func_180492_cm();

			if (var1 == null) {
				return false;
			} else {
				theTarget = var1.getLastAttacker();
				final int var2 = var1.getLastAttackerTime();
				return var2 != field_142050_e && isSuitableTarget(theTarget, false)
						&& theEntityTameable.func_142018_a(theTarget, var1);
			}
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		taskOwner.setAttackTarget(theTarget);
		final EntityLivingBase var1 = theEntityTameable.func_180492_cm();

		if (var1 != null) {
			field_142050_e = var1.getLastAttackerTime();
		}

		super.startExecuting();
	}
}
