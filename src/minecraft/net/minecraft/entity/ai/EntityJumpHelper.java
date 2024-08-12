package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper {

public static final int EaZy = 1094;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityLiving entity;
	protected boolean isJumping;
	// private static final String __OBFID = "http://https://fuckuskid00001571";

	public EntityJumpHelper(final EntityLiving p_i1612_1_) {
		entity = p_i1612_1_;
	}

	public void setJumping() {
		isJumping = true;
	}

	/**
	 * Called to actually make the entity jump if isJumping is true.
	 */
	public void doJump() {
		entity.setJumping(isJumping);
		isJumping = false;
	}
}
