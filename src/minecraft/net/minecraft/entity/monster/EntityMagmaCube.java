package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends EntitySlime {

public static final int EaZy = 1161;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001691";

	public EntityMagmaCube(final World worldIn) {
		super(worldIn);
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Whether or not the current entity is in lava
	 */
	@Override
	public boolean handleLavaMovement() {
		return worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)
				&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()
				&& !worldObj.isAnyLiquid(getEntityBoundingBox());
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue() {
		return getSlimeSize() * 3;
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return 1.0F;
	}

	@Override
	protected EnumParticleTypes func_180487_n() {
		return EnumParticleTypes.FLAME;
	}

	@Override
	protected EntitySlime createInstance() {
		return new EntityMagmaCube(worldObj);
	}

	@Override
	protected Item getDropItem() {
		return Items.magma_cream;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final Item var3 = getDropItem();

		if (var3 != null && getSlimeSize() > 1) {
			int var4 = rand.nextInt(4) - 2;

			if (p_70628_2_ > 0) {
				var4 += rand.nextInt(p_70628_2_ + 1);
			}

			for (int var5 = 0; var5 < var4; ++var5) {
				dropItem(var3, 1);
			}
		}
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire
	 * effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return false;
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	@Override
	protected int getJumpDelay() {
		return super.getJumpDelay() * 4;
	}

	@Override
	protected void alterSquishAmount() {
		squishAmount *= 0.9F;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jump() {
		motionY = 0.42F + getSlimeSize() * 0.1F;
		isAirBorne = true;
	}

	@Override
	protected void func_180466_bG() {
		motionY = 0.22F + getSlimeSize() * 0.05F;
		isAirBorne = true;
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the
	 * slime's size)
	 */
	@Override
	protected boolean canDamagePlayer() {
		return true;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the
	 * slime.
	 */
	@Override
	protected int getAttackStrength() {
		return super.getAttackStrength() + 2;
	}

	/**
	 * Returns the name of the sound played when the slime jumps.
	 */
	@Override
	protected String getJumpSound() {
		return getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
	}

	/**
	 * Returns true if the slime makes a sound when it lands after a jump (based
	 * upon the slime's size)
	 */
	@Override
	protected boolean makesSoundOnLand() {
		return true;
	}
}
