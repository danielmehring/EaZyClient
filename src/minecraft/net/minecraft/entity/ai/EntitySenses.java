package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import java.util.List;

import com.google.common.collect.Lists;

public class EntitySenses {

public static final int EaZy = 1098;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	EntityLiving entityObj;

	/** Cache of entities which we can see */
	List seenEntities = Lists.newArrayList();

	/** Cache of entities which we cannot see */
	List unseenEntities = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001628";

	public EntitySenses(final EntityLiving p_i1672_1_) {
		entityObj = p_i1672_1_;
	}

	/**
	 * Clears canSeeCachePositive and canSeeCacheNegative.
	 */
	public void clearSensingCache() {
		seenEntities.clear();
		unseenEntities.clear();
	}

	/**
	 * Checks, whether 'our' entity can see the entity given as argument (true)
	 * or not (false), caching the result.
	 */
	public boolean canSee(final Entity p_75522_1_) {
		if (seenEntities.contains(p_75522_1_)) {
			return true;
		} else if (unseenEntities.contains(p_75522_1_)) {
			return false;
		} else {
			entityObj.worldObj.theProfiler.startSection("canSee");
			final boolean var2 = entityObj.canEntityBeSeen(p_75522_1_);
			entityObj.worldObj.theProfiler.endSection();

			if (var2) {
				seenEntities.add(p_75522_1_);
			} else {
				unseenEntities.add(p_75522_1_);
			}

			return var2;
		}
	}
}
