package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityTameable;

import com.google.common.base.Predicate;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget {

public static final int EaZy = 1085;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityTameable theTameable;
	// private static final String __OBFID = "http://https://fuckuskid00001623";

	public EntityAITargetNonTamed(final EntityTameable p_i45876_1_, final Class p_i45876_2_, final boolean p_i45876_3_,
			final Predicate p_i45876_4_) {
		super(p_i45876_1_, p_i45876_2_, 10, p_i45876_3_, false, p_i45876_4_);
		theTameable = p_i45876_1_;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return !theTameable.isTamed() && super.shouldExecute();
	}
}
