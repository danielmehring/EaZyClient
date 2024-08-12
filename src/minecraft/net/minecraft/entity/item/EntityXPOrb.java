package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {

public static final int EaZy = 1150;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * A constantly increasing value that RenderXPOrb uses to control the colour
	 * shifting (Green / yellow)
	 */
	public int xpColor;

	/** The age of the XP orb in ticks. */
	public int xpOrbAge;
	public int field_70532_c;

	/** The health of this XP orb. */
	private int xpOrbHealth = 5;

	/** This is how much XP this orb has. */
	private int xpValue;

	/** The closest EntityPlayer to this orb. */
	private EntityPlayer closestPlayer;

	/** Threshold color for tracking players */
	private int xpTargetColor;
	// private static final String __OBFID = "http://https://fuckuskid00001544";

	public EntityXPOrb(final World worldIn, final double p_i1585_2_, final double p_i1585_4_, final double p_i1585_6_,
			final int p_i1585_8_) {
		super(worldIn);
		setSize(0.5F, 0.5F);
		setPosition(p_i1585_2_, p_i1585_4_, p_i1585_6_);
		rotationYaw = (float) (Math.random() * 360.0D);
		motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
		motionY = (float) (Math.random() * 0.2D) * 2.0F;
		motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F;
		xpValue = p_i1585_8_;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	public EntityXPOrb(final World worldIn) {
		super(worldIn);
		setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		float var2 = 0.5F;
		var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
		final int var3 = super.getBrightnessForRender(p_70070_1_);
		int var4 = var3 & 255;
		final int var5 = var3 >> 16 & 255;
		var4 += (int) (var2 * 15.0F * 16.0F);

		if (var4 > 240) {
			var4 = 240;
		}

		return var4 | var5 << 16;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (field_70532_c > 0) {
			--field_70532_c;
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.029999999329447746D;

		if (worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
			motionY = 0.20000000298023224D;
			motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
			playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
		}

		pushOutOfBlocks(posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D, posZ);
		final double var1 = 8.0D;

		if (xpTargetColor < xpColor - 20 + getEntityId() % 100) {
			if (closestPlayer == null || closestPlayer.getDistanceSqToEntity(this) > var1 * var1) {
				closestPlayer = worldObj.getClosestPlayerToEntity(this, var1);
			}

			xpTargetColor = xpColor;
		}

		if (closestPlayer != null && closestPlayer.isSpectatorMode()) {
			closestPlayer = null;
		}

		if (closestPlayer != null) {
			final double var3 = (closestPlayer.posX - posX) / var1;
			final double var5 = (closestPlayer.posY + closestPlayer.getEyeHeight() - posY) / var1;
			final double var7 = (closestPlayer.posZ - posZ) / var1;
			final double var9 = Math.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
			double var11 = 1.0D - var9;

			if (var11 > 0.0D) {
				var11 *= var11;
				motionX += var3 / var9 * var11 * 0.1D;
				motionY += var5 / var9 * var11 * 0.1D;
				motionZ += var7 / var9 * var11 * 0.1D;
			}
		}

		moveEntity(motionX, motionY, motionZ);
		float var13 = 0.98F;

		if (onGround) {
			var13 = worldObj
					.getBlockState(new BlockPos(MathHelper.floor_double(posX),
							MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)))
					.getBlock().slipperiness * 0.98F;
		}

		motionX *= var13;
		motionY *= 0.9800000190734863D;
		motionZ *= var13;

		if (onGround) {
			motionY *= -0.8999999761581421D;
		}

		++xpColor;
		++xpOrbAge;

		if (xpOrbAge >= 6000) {
			setDead();
		}
	}

	/**
	 * Returns if this entity is in water and will end up adding the waters
	 * velocity to the entity
	 */
	@Override
	public boolean handleWaterMovement() {
		return worldObj.handleMaterialAcceleration(getEntityBoundingBox(), Material.water, this);
	}

	/**
	 * Will deal the specified amount of damage to the entity if the entity
	 * isn't immune to fire damage. Args: amountDamage
	 */
	@Override
	protected void dealFireDamage(final int amount) {
		attackEntityFrom(DamageSource.inFire, amount);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			setBeenAttacked();
			xpOrbHealth = (int) (xpOrbHealth - amount);

			if (xpOrbHealth <= 0) {
				setDead();
			}

			return false;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("Health", (byte) xpOrbHealth);
		tagCompound.setShort("Age", (short) xpOrbAge);
		tagCompound.setShort("Value", (short) xpValue);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		xpOrbHealth = tagCompund.getShort("Health") & 255;
		xpOrbAge = tagCompund.getShort("Age");
		xpValue = tagCompund.getShort("Value");
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(final EntityPlayer entityIn) {
		if (!worldObj.isRemote) {
			if (field_70532_c == 0 && entityIn.xpCooldown == 0) {
				entityIn.xpCooldown = 2;
				worldObj.playSoundAtEntity(entityIn, "random.orb", 0.1F,
						0.5F * ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.8F));
				entityIn.onItemPickup(this, 1);
				entityIn.addExperience(xpValue);
				setDead();
			}
		}
	}

	/**
	 * Returns the XP value of this XP orb.
	 */
	public int getXpValue() {
		return xpValue;
	}

	/**
	 * Returns a number from 1 to 10 based on how much XP this orb is worth.
	 * This is used by RenderXPOrb to determine what texture to use.
	 */
	public int getTextureByXP() {
		return xpValue >= 2477 ? 10
				: xpValue >= 1237 ? 9
						: xpValue >= 617 ? 8
								: xpValue >= 307 ? 7
										: xpValue >= 149 ? 6
												: xpValue >= 73 ? 5
														: xpValue >= 37 ? 4
																: xpValue >= 17 ? 3
																		: xpValue >= 7 ? 2 : xpValue >= 3 ? 1 : 0;
	}

	/**
	 * Get a fragment of the maximum experience points value for the supplied
	 * value of experience points value.
	 */
	public static int getXPSplit(final int p_70527_0_) {
		return p_70527_0_ >= 2477 ? 2477
				: p_70527_0_ >= 1237 ? 1237
						: p_70527_0_ >= 617 ? 617
								: p_70527_0_ >= 307 ? 307
										: p_70527_0_ >= 149 ? 149
												: p_70527_0_ >= 73 ? 73
														: p_70527_0_ >= 37 ? 37
																: p_70527_0_ >= 17 ? 17
																		: p_70527_0_ >= 7 ? 7 : p_70527_0_ >= 3 ? 3 : 1;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}
}
