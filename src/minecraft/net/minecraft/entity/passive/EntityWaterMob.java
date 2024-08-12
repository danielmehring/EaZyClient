package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals {

public static final int EaZy = 1188;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001653";

	public EntityWaterMob(final World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return true;
	}

	/**
	 * Whether or not the current entity is in lava
	 */
	@Override
	public boolean handleLavaMovement() {
		return worldObj.checkNoEntityCollision(getEntityBoundingBox(), this);
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
		return true;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
		return 1 + worldObj.rand.nextInt(3);
	}

	/**
	 * Gets called every tick from main Entity class
	 */
	@Override
	public void onEntityUpdate() {
		int var1 = getAir();
		super.onEntityUpdate();

		if (isEntityAlive() && !isInWater()) {
			--var1;
			setAir(var1);

			if (getAir() == -20) {
				setAir(0);
				attackEntityFrom(DamageSource.drown, 2.0F);
			}
		} else {
			setAir(300);
		}
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}
}
