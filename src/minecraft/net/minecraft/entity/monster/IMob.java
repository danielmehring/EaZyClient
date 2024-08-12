package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

import com.google.common.base.Predicate;

public interface IMob extends IAnimals {
	/** Entity selector for IMob types. */
	Predicate mobSelector = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001688";
		public boolean func_179983_a(final Entity p_179983_1_) {
			return p_179983_1_ instanceof IMob;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_179983_a((Entity) p_apply_1_);
		}
	};
	Predicate field_175450_e = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002218";
		public boolean func_179982_a(final Entity p_179982_1_) {
			return p_179982_1_ instanceof IMob && !p_179982_1_.isInvisible();
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_179982_a((Entity) p_apply_1_);
		}
	};
}
