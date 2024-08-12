package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityBoat extends Entity {

public static final int EaZy = 1132;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** true if no player in boat */
	private boolean isBoatEmpty;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	// private static final String __OBFID = "http://https://fuckuskid00001667";

	public EntityBoat(final World worldIn) {
		super(worldIn);
		isBoatEmpty = true;
		speedMultiplier = 0.07D;
		preventEntitySpawning = true;
		setSize(1.5F, 0.6F);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, 0);
		dataWatcher.addObject(18, 1);
		dataWatcher.addObject(19, 0.0F);
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox(final Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox() {
		return getEntityBoundingBox();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntityBoat(final World worldIn, final double p_i1705_2_, final double p_i1705_4_, final double p_i1705_6_) {
		this(worldIn);
		setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = p_i1705_2_;
		prevPosY = p_i1705_4_;
		prevPosZ = p_i1705_6_;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	@Override
	public double getMountedYOffset() {
		return height * 0.0D - 0.30000001192092896D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else if (!worldObj.isRemote && !isDead) {
			if (riddenByEntity != null && riddenByEntity == source.getEntity()
					&& source instanceof EntityDamageSourceIndirect) {
				return false;
			} else {
				setForwardDirection(-getForwardDirection());
				setTimeSinceHit(10);
				setDamageTaken(getDamageTaken() + amount * 10.0F);
				setBeenAttacked();
				final boolean var3 = source.getEntity() instanceof EntityPlayer
						&& ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if (var3 || getDamageTaken() > 40.0F) {
					if (riddenByEntity != null) {
						riddenByEntity.mountEntity(this);
					}

					if (!var3) {
						dropItemWithOffset(Items.boat, 1, 0.0F);
					}

					setDead();
				}

				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@Override
	public void performHurtAnimation() {
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		if (p_180426_10_ && riddenByEntity != null) {
			prevPosX = posX = p_180426_1_;
			prevPosY = posY = p_180426_3_;
			prevPosZ = posZ = p_180426_5_;
			rotationYaw = p_180426_7_;
			rotationPitch = p_180426_8_;
			boatPosRotationIncrements = 0;
			setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
			motionX = velocityX = 0.0D;
			motionY = velocityY = 0.0D;
			motionZ = velocityZ = 0.0D;
		} else {
			if (isBoatEmpty) {
				boatPosRotationIncrements = p_180426_9_ + 5;
			} else {
				final double var11 = p_180426_1_ - posX;
				final double var13 = p_180426_3_ - posY;
				final double var15 = p_180426_5_ - posZ;
				final double var17 = var11 * var11 + var13 * var13 + var15 * var15;

				if (var17 <= 1.0D) {
					return;
				}

				boatPosRotationIncrements = 3;
			}

			boatX = p_180426_1_;
			boatY = p_180426_3_;
			boatZ = p_180426_5_;
			boatYaw = p_180426_7_;
			boatPitch = p_180426_8_;
			motionX = velocityX;
			motionY = velocityY;
			motionZ = velocityZ;
		}
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	public void setVelocity(final double x, final double y, final double z) {
		velocityX = motionX = x;
		velocityY = motionY = y;
		velocityZ = motionZ = z;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (getTimeSinceHit() > 0) {
			setTimeSinceHit(getTimeSinceHit() - 1);
		}

		if (getDamageTaken() > 0.0F) {
			setDamageTaken(getDamageTaken() - 1.0F);
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		final byte var1 = 5;
		double var2 = 0.0D;

		for (int var4 = 0; var4 < var1; ++var4) {
			final double var5 = getEntityBoundingBox().minY
					+ (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * (var4 + 0) / var1 - 0.125D;
			final double var7 = getEntityBoundingBox().minY
					+ (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * (var4 + 1) / var1 - 0.125D;
			final AxisAlignedBB var9 = new AxisAlignedBB(getEntityBoundingBox().minX, var5, getEntityBoundingBox().minZ,
					getEntityBoundingBox().maxX, var7, getEntityBoundingBox().maxZ);

			if (worldObj.isAABBInMaterial(var9, Material.water)) {
				var2 += 1.0D / var1;
			}
		}

		final double var19 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		double var6;
		double var8;
		int var10;

		if (var19 > 0.2975D) {
			var6 = Math.cos(rotationYaw * Math.PI / 180.0D);
			var8 = Math.sin(rotationYaw * Math.PI / 180.0D);

			for (var10 = 0; var10 < 1.0D + var19 * 60.0D; ++var10) {
				final double var11 = rand.nextFloat() * 2.0F - 1.0F;
				final double var13 = (rand.nextInt(2) * 2 - 1) * 0.7D;
				double var15;
				double var17;

				if (rand.nextBoolean()) {
					var15 = posX - var6 * var11 * 0.8D + var8 * var13;
					var17 = posZ - var8 * var11 * 0.8D - var6 * var13;
					worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, posY - 0.125D, var17, motionX,
							motionY, motionZ, new int[0]);
				} else {
					var15 = posX + var6 + var8 * var11 * 0.7D;
					var17 = posZ + var8 - var6 * var11 * 0.7D;
					worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, var15, posY - 0.125D, var17, motionX,
							motionY, motionZ, new int[0]);
				}
			}
		}

		double var24;
		double var26;

		if (worldObj.isRemote && isBoatEmpty) {
			if (boatPosRotationIncrements > 0) {
				var6 = posX + (boatX - posX) / boatPosRotationIncrements;
				var8 = posY + (boatY - posY) / boatPosRotationIncrements;
				var24 = posZ + (boatZ - posZ) / boatPosRotationIncrements;
				var26 = MathHelper.wrapAngleTo180_double(boatYaw - rotationYaw);
				rotationYaw = (float) (rotationYaw + var26 / boatPosRotationIncrements);
				rotationPitch = (float) (rotationPitch + (boatPitch - rotationPitch) / boatPosRotationIncrements);
				--boatPosRotationIncrements;
				setPosition(var6, var8, var24);
				setRotation(rotationYaw, rotationPitch);
			} else {
				var6 = posX + motionX;
				var8 = posY + motionY;
				var24 = posZ + motionZ;
				setPosition(var6, var8, var24);

				if (onGround) {
					motionX *= 0.5D;
					motionY *= 0.5D;
					motionZ *= 0.5D;
				}

				motionX *= 0.9900000095367432D;
				motionY *= 0.949999988079071D;
				motionZ *= 0.9900000095367432D;
			}
		} else {
			if (var2 < 1.0D) {
				var6 = var2 * 2.0D - 1.0D;
				motionY += 0.03999999910593033D * var6;
			} else {
				if (motionY < 0.0D) {
					motionY /= 2.0D;
				}

				motionY += 0.007000000216066837D;
			}

			if (riddenByEntity instanceof EntityLivingBase) {
				final EntityLivingBase var20 = (EntityLivingBase) riddenByEntity;
				final float var21 = riddenByEntity.rotationYaw + -var20.moveStrafing * 90.0F;
				motionX += -Math.sin(var21 * (float) Math.PI / 180.0F) * speedMultiplier * var20.moveForward
						* 0.05000000074505806D;
				motionZ += Math.cos(var21 * (float) Math.PI / 180.0F) * speedMultiplier * var20.moveForward
						* 0.05000000074505806D;
			}

			var6 = Math.sqrt(motionX * motionX + motionZ * motionZ);

			if (var6 > 0.35D) {
				var8 = 0.35D / var6;
				motionX *= var8;
				motionZ *= var8;
				var6 = 0.35D;
			}

			if (var6 > var19 && speedMultiplier < 0.35D) {
				speedMultiplier += (0.35D - speedMultiplier) / 35.0D;

				if (speedMultiplier > 0.35D) {
					speedMultiplier = 0.35D;
				}
			} else {
				speedMultiplier -= (speedMultiplier - 0.07D) / 35.0D;

				if (speedMultiplier < 0.07D) {
					speedMultiplier = 0.07D;
				}
			}

			int var22;

			for (var22 = 0; var22 < 4; ++var22) {
				final int var23 = MathHelper.floor_double(posX + (var22 % 2 - 0.5D) * 0.8D);
				var10 = MathHelper.floor_double(posZ + (var22 / 2 - 0.5D) * 0.8D);

				for (int var25 = 0; var25 < 2; ++var25) {
					final int var12 = MathHelper.floor_double(posY) + var25;
					final BlockPos var27 = new BlockPos(var23, var12, var10);
					final Block var14 = worldObj.getBlockState(var27).getBlock();

					if (var14 == Blocks.snow_layer) {
						worldObj.setBlockToAir(var27);
						isCollidedHorizontally = false;
					} else if (var14 == Blocks.waterlily) {
						worldObj.destroyBlock(var27, true);
						isCollidedHorizontally = false;
					}
				}
			}

			if (onGround) {
				motionX *= 0.5D;
				motionY *= 0.5D;
				motionZ *= 0.5D;
			}

			moveEntity(motionX, motionY, motionZ);

			if (isCollidedHorizontally && var19 > 0.2D) {
				if (!worldObj.isRemote && !isDead) {
					setDead();

					for (var22 = 0; var22 < 3; ++var22) {
						dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
					}

					for (var22 = 0; var22 < 2; ++var22) {
						dropItemWithOffset(Items.stick, 1, 0.0F);
					}
				}
			} else {
				motionX *= 0.9900000095367432D;
				motionY *= 0.949999988079071D;
				motionZ *= 0.9900000095367432D;
			}

			rotationPitch = 0.0F;
			var8 = rotationYaw;
			var24 = prevPosX - posX;
			var26 = prevPosZ - posZ;

			if (var24 * var24 + var26 * var26 > 0.001D) {
				var8 = (float) (Math.atan2(var26, var24) * 180.0D / Math.PI);
			}

			double var28 = MathHelper.wrapAngleTo180_double(var8 - rotationYaw);

			if (var28 > 20.0D) {
				var28 = 20.0D;
			}

			if (var28 < -20.0D) {
				var28 = -20.0D;
			}

			rotationYaw = (float) (rotationYaw + var28);
			setRotation(rotationYaw, rotationPitch);

			if (!worldObj.isRemote) {
				final List var16 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
						getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

				if (var16 != null && !var16.isEmpty()) {
					for (int var29 = 0; var29 < var16.size(); ++var29) {
						final Entity var18 = (Entity) var16.get(var29);

						if (var18 != riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat) {
							var18.applyEntityCollision(this);
						}
					}
				}

				if (riddenByEntity != null && riddenByEntity.isDead) {
					riddenByEntity = null;
				}
			}
		}
	}

	@Override
	public void updateRiderPosition() {
		if (riddenByEntity != null) {
			final double var1 = Math.cos(rotationYaw * Math.PI / 180.0D) * 0.4D;
			final double var3 = Math.sin(rotationYaw * Math.PI / 180.0D) * 0.4D;
			riddenByEntity.setPosition(posX + var1, posY + getMountedYOffset() + riddenByEntity.getYOffset(),
					posZ + var3);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != playerIn) {
			return true;
		} else {
			if (!worldObj.isRemote) {
				playerIn.mountEntity(this);
			}

			return true;
		}
	}

	@Override
	protected void func_180433_a(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_,
			final BlockPos p_180433_5_) {
		if (p_180433_3_) {
			if (fallDistance > 3.0F) {
				fall(fallDistance, 1.0F);

				if (!worldObj.isRemote && !isDead) {
					setDead();
					int var6;

					for (var6 = 0; var6 < 3; ++var6) {
						dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
					}

					for (var6 = 0; var6 < 2; ++var6) {
						dropItemWithOffset(Items.stick, 1, 0.0F);
					}
				}

				fallDistance = 0.0F;
			}
		} else if (worldObj.getBlockState(new BlockPos(this).offsetDown()).getBlock().getMaterial() != Material.water
				&& p_180433_1_ < 0.0D) {
			fallDistance = (float) (fallDistance - p_180433_1_);
		}
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(final float p_70266_1_) {
		dataWatcher.updateObject(19, p_70266_1_);
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(final int p_70265_1_) {
		dataWatcher.updateObject(17, p_70265_1_);
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(final int p_70269_1_) {
		dataWatcher.updateObject(18, p_70269_1_);
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return dataWatcher.getWatchableObjectInt(18);
	}

	/**
	 * true if no player in boat
	 */
	public void setIsBoatEmpty(final boolean p_70270_1_) {
		isBoatEmpty = p_70270_1_;
	}
}
