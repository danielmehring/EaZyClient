package net.minecraft.entity.monster;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {

public static final int EaZy = 1158;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001644";

	public EntityGolem(final World worldIn) {
		super(worldIn);
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "none";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "none";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "none";
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	@Override
	public int getTalkInterval() {
		return 120;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return false;
	}
}
