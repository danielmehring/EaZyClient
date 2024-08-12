package net.minecraft.entity.boss;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob {

public static final int EaZy = 1101;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public double targetX;
	public double targetY;
	public double targetZ;

	/**
	 * Ring buffer array for the last 64 Y-positions and yaw rotations. Used to
	 * calculate offsets for the animations.
	 */
	public double[][] ringBuffer = new double[64][3];

	/**
	 * Index into the ring buffer. Incremented once per tick and restarts at 0
	 * once it reaches the end of the buffer.
	 */
	public int ringBufferIndex = -1;

	/** An array containing all body parts of this dragon */
	public EntityDragonPart[] dragonPartArray;

	/** The head bounding box of a dragon */
	public EntityDragonPart dragonPartHead;

	/** The body bounding box of a dragon */
	public EntityDragonPart dragonPartBody;
	public EntityDragonPart dragonPartTail1;
	public EntityDragonPart dragonPartTail2;
	public EntityDragonPart dragonPartTail3;
	public EntityDragonPart dragonPartWing1;
	public EntityDragonPart dragonPartWing2;

	/** Animation time at previous tick. */
	public float prevAnimTime;

	/**
	 * Animation time, used to control the speed of the animation cycles (wings
	 * flapping, jaw opening, etc.)
	 */
	public float animTime;

	/** Force selecting a new flight target at next tick if set to true. */
	public boolean forceNewTarget;

	/**
	 * Activated if the dragon is flying though obsidian, white stone or
	 * bedrock. Slows movement and animation speed.
	 */
	public boolean slowed;
	private Entity target;
	public int deathTicks;

	/** The current endercrystal that is healing this dragon */
	public EntityEnderCrystal healingEnderCrystal;
	// private static final String __OBFID = "http://https://fuckuskid00001659";

	public EntityDragon(final World worldIn) {
		super(worldIn);
		dragonPartArray = new EntityDragonPart[] { dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F),
				dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F),
				dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
				dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F),
				dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
		setHealth(getMaxHealth());
		setSize(16.0F, 8.0F);
		noClip = true;
		isImmuneToFire = true;
		targetY = 100.0D;
		ignoreFrustumCheck = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	/**
	 * Returns a double[3] array with movement offsets, used to calculate
	 * trailing tail/neck positions. [0] = yaw offset, [1] = y offset, [2] =
	 * unused, always 0. Parameters: buffer index offset, partial ticks.
	 */
	public double[] getMovementOffsets(final int p_70974_1_, float p_70974_2_) {
		if (getHealth() <= 0.0F) {
			p_70974_2_ = 0.0F;
		}

		p_70974_2_ = 1.0F - p_70974_2_;
		final int var3 = ringBufferIndex - p_70974_1_ * 1 & 63;
		final int var4 = ringBufferIndex - p_70974_1_ * 1 - 1 & 63;
		final double[] var5 = new double[3];
		double var6 = ringBuffer[var3][0];
		double var8 = MathHelper.wrapAngleTo180_double(ringBuffer[var4][0] - var6);
		var5[0] = var6 + var8 * p_70974_2_;
		var6 = ringBuffer[var3][1];
		var8 = ringBuffer[var4][1] - var6;
		var5[1] = var6 + var8 * p_70974_2_;
		var5[2] = ringBuffer[var3][2] + (ringBuffer[var4][2] - ringBuffer[var3][2]) * p_70974_2_;
		return var5;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		float var1;
		float var2;

		if (worldObj.isRemote) {
			var1 = MathHelper.cos(animTime * (float) Math.PI * 2.0F);
			var2 = MathHelper.cos(prevAnimTime * (float) Math.PI * 2.0F);

			if (var2 <= -0.3F && var1 >= -0.3F && !isSlient()) {
				worldObj.playSound(posX, posY, posZ, "mob.enderdragon.wings", 5.0F, 0.8F + rand.nextFloat() * 0.3F,
						false);
			}
		}

		prevAnimTime = animTime;
		float var3;

		if (getHealth() <= 0.0F) {
			var1 = (rand.nextFloat() - 0.5F) * 8.0F;
			var2 = (rand.nextFloat() - 0.5F) * 4.0F;
			var3 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX + var1, posY + 2.0D + var2, posZ + var3,
					0.0D, 0.0D, 0.0D, new int[0]);
		} else {
			updateDragonEnderCrystal();
			var1 = 0.2F / (MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 10.0F + 1.0F);
			var1 *= (float) Math.pow(2.0D, motionY);

			if (slowed) {
				animTime += var1 * 0.5F;
			} else {
				animTime += var1;
			}

			rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);

			if (ringBufferIndex < 0) {
				for (int var27 = 0; var27 < ringBuffer.length; ++var27) {
					ringBuffer[var27][0] = rotationYaw;
					ringBuffer[var27][1] = posY;
				}
			}

			if (++ringBufferIndex == ringBuffer.length) {
				ringBufferIndex = 0;
			}

			ringBuffer[ringBufferIndex][0] = rotationYaw;
			ringBuffer[ringBufferIndex][1] = posY;
			double var4;
			double var6;
			double var8;
			double var28;
			float var33;

			if (worldObj.isRemote) {
				if (newPosRotationIncrements > 0) {
					var28 = posX + (newPosX - posX) / newPosRotationIncrements;
					var4 = posY + (newPosY - posY) / newPosRotationIncrements;
					var6 = posZ + (newPosZ - posZ) / newPosRotationIncrements;
					var8 = MathHelper.wrapAngleTo180_double(newRotationYaw - rotationYaw);
					rotationYaw = (float) (rotationYaw + var8 / newPosRotationIncrements);
					rotationPitch = (float) (rotationPitch
							+ (newRotationPitch - rotationPitch) / newPosRotationIncrements);
					--newPosRotationIncrements;
					setPosition(var28, var4, var6);
					setRotation(rotationYaw, rotationPitch);
				}
			} else {
				var28 = targetX - posX;
				var4 = targetY - posY;
				var6 = targetZ - posZ;
				var8 = var28 * var28 + var4 * var4 + var6 * var6;
				double var16;

				if (target != null) {
					targetX = target.posX;
					targetZ = target.posZ;
					final double var10 = targetX - posX;
					final double var12 = targetZ - posZ;
					final double var14 = Math.sqrt(var10 * var10 + var12 * var12);
					var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;

					if (var16 > 10.0D) {
						var16 = 10.0D;
					}

					targetY = target.getEntityBoundingBox().minY + var16;
				} else {
					targetX += rand.nextGaussian() * 2.0D;
					targetZ += rand.nextGaussian() * 2.0D;
				}

				if (forceNewTarget || var8 < 100.0D || var8 > 22500.0D || isCollidedHorizontally
						|| isCollidedVertically) {
					setNewTarget();
				}

				var4 /= MathHelper.sqrt_double(var28 * var28 + var6 * var6);
				var33 = 0.6F;
				var4 = MathHelper.clamp_double(var4, -var33, var33);
				motionY += var4 * 0.10000000149011612D;
				rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
				final double var11 = 180.0D - Math.atan2(var28, var6) * 180.0D / Math.PI;
				double var13 = MathHelper.wrapAngleTo180_double(var11 - rotationYaw);

				if (var13 > 50.0D) {
					var13 = 50.0D;
				}

				if (var13 < -50.0D) {
					var13 = -50.0D;
				}

				final Vec3 var15 = new Vec3(targetX - posX, targetY - posY, targetZ - posZ).normalize();
				var16 = -MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);
				final Vec3 var18 = new Vec3(MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F), motionY, var16)
						.normalize();
				float var19 = ((float) var18.dotProduct(var15) + 0.5F) / 1.5F;

				if (var19 < 0.0F) {
					var19 = 0.0F;
				}

				randomYawVelocity *= 0.8F;
				final float var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
				double var21 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;

				if (var21 > 40.0D) {
					var21 = 40.0D;
				}

				randomYawVelocity = (float) (randomYawVelocity + var13 * (0.699999988079071D / var21 / var20));
				rotationYaw += randomYawVelocity * 0.1F;
				final float var23 = (float) (2.0D / (var21 + 1.0D));
				final float var24 = 0.06F;
				moveFlying(0.0F, -1.0F, var24 * (var19 * var23 + (1.0F - var23)));

				if (slowed) {
					moveEntity(motionX * 0.800000011920929D, motionY * 0.800000011920929D,
							motionZ * 0.800000011920929D);
				} else {
					moveEntity(motionX, motionY, motionZ);
				}

				final Vec3 var25 = new Vec3(motionX, motionY, motionZ).normalize();
				float var26 = ((float) var25.dotProduct(var18) + 1.0F) / 2.0F;
				var26 = 0.8F + 0.15F * var26;
				motionX *= var26;
				motionZ *= var26;
				motionY *= 0.9100000262260437D;
			}

			renderYawOffset = rotationYaw;
			dragonPartHead.width = dragonPartHead.height = 3.0F;
			dragonPartTail1.width = dragonPartTail1.height = 2.0F;
			dragonPartTail2.width = dragonPartTail2.height = 2.0F;
			dragonPartTail3.width = dragonPartTail3.height = 2.0F;
			dragonPartBody.height = 3.0F;
			dragonPartBody.width = 5.0F;
			dragonPartWing1.height = 2.0F;
			dragonPartWing1.width = 4.0F;
			dragonPartWing2.height = 3.0F;
			dragonPartWing2.width = 4.0F;
			var2 = (float) (getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F
					* (float) Math.PI;
			var3 = MathHelper.cos(var2);
			final float var29 = -MathHelper.sin(var2);
			final float var5 = rotationYaw * (float) Math.PI / 180.0F;
			final float var30 = MathHelper.sin(var5);
			final float var7 = MathHelper.cos(var5);
			dragonPartBody.onUpdate();
			dragonPartBody.setLocationAndAngles(posX + var30 * 0.5F, posY, posZ - var7 * 0.5F, 0.0F, 0.0F);
			dragonPartWing1.onUpdate();
			dragonPartWing1.setLocationAndAngles(posX + var7 * 4.5F, posY + 2.0D, posZ + var30 * 4.5F, 0.0F, 0.0F);
			dragonPartWing2.onUpdate();
			dragonPartWing2.setLocationAndAngles(posX - var7 * 4.5F, posY + 2.0D, posZ - var30 * 4.5F, 0.0F, 0.0F);

			if (!worldObj.isRemote && hurtTime == 0) {
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this,
						dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this,
						dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				attackEntitiesInList(worldObj.getEntitiesWithinAABBExcludingEntity(this,
						dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
			}

			final double[] var31 = getMovementOffsets(5, 1.0F);
			final double[] var9 = getMovementOffsets(0, 1.0F);
			var33 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F - randomYawVelocity * 0.01F);
			final float var35 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F - randomYawVelocity * 0.01F);
			dragonPartHead.onUpdate();
			dragonPartHead.setLocationAndAngles(posX + var33 * 5.5F * var3,
					posY + (var9[1] - var31[1]) * 1.0D + var29 * 5.5F, posZ - var35 * 5.5F * var3, 0.0F, 0.0F);

			for (int var32 = 0; var32 < 3; ++var32) {
				EntityDragonPart var34 = null;

				if (var32 == 0) {
					var34 = dragonPartTail1;
				}

				if (var32 == 1) {
					var34 = dragonPartTail2;
				}

				if (var32 == 2) {
					var34 = dragonPartTail3;
				}

				final double[] var36 = getMovementOffsets(12 + var32 * 2, 1.0F);
				final float var37 = rotationYaw * (float) Math.PI / 180.0F
						+ simplifyAngle(var36[0] - var31[0]) * (float) Math.PI / 180.0F * 1.0F;
				final float var38 = MathHelper.sin(var37);
				final float var39 = MathHelper.cos(var37);
				final float var40 = 1.5F;
				final float var41 = (var32 + 1) * 2.0F;
				var34.onUpdate();
				var34.setLocationAndAngles(posX - (var30 * var40 + var38 * var41) * var3,
						posY + (var36[1] - var31[1]) * 1.0D - (var41 + var40) * var29 + 1.5D,
						posZ + (var7 * var40 + var39 * var41) * var3, 0.0F, 0.0F);
			}

			if (!worldObj.isRemote) {
				slowed = destroyBlocksInAABB(dragonPartHead.getEntityBoundingBox())
						| destroyBlocksInAABB(dragonPartBody.getEntityBoundingBox());
			}
		}
	}

	/**
	 * Updates the state of the enderdragon's current endercrystal.
	 */
	private void updateDragonEnderCrystal() {
		if (healingEnderCrystal != null) {
			if (healingEnderCrystal.isDead) {
				if (!worldObj.isRemote) {
					attackEntityFromPart(dragonPartHead, DamageSource.setExplosionSource((Explosion) null), 10.0F);
				}

				healingEnderCrystal = null;
			} else if (ticksExisted % 10 == 0 && getHealth() < getMaxHealth()) {
				setHealth(getHealth() + 1.0F);
			}
		}

		if (rand.nextInt(10) == 0) {
			final float var1 = 32.0F;
			final List var2 = worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class,
					getEntityBoundingBox().expand(var1, var1, var1));
			EntityEnderCrystal var3 = null;
			double var4 = Double.MAX_VALUE;
			final Iterator var6 = var2.iterator();

			while (var6.hasNext()) {
				final EntityEnderCrystal var7 = (EntityEnderCrystal) var6.next();
				final double var8 = var7.getDistanceSqToEntity(this);

				if (var8 < var4) {
					var4 = var8;
					var3 = var7;
				}
			}

			healingEnderCrystal = var3;
		}
	}

	/**
	 * Pushes all entities inside the list away from the enderdragon.
	 */
	private void collideWithEntities(final List p_70970_1_) {
		final double var2 = (dragonPartBody.getEntityBoundingBox().minX + dragonPartBody.getEntityBoundingBox().maxX)
				/ 2.0D;
		final double var4 = (dragonPartBody.getEntityBoundingBox().minZ + dragonPartBody.getEntityBoundingBox().maxZ)
				/ 2.0D;
		final Iterator var6 = p_70970_1_.iterator();

		while (var6.hasNext()) {
			final Entity var7 = (Entity) var6.next();

			if (var7 instanceof EntityLivingBase) {
				final double var8 = var7.posX - var2;
				final double var10 = var7.posZ - var4;
				final double var12 = var8 * var8 + var10 * var10;
				var7.addVelocity(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
			}
		}
	}

	/**
	 * Attacks all entities inside this list, dealing 5 hearts of damage.
	 */
	private void attackEntitiesInList(final List p_70971_1_) {
		for (int var2 = 0; var2 < p_70971_1_.size(); ++var2) {
			final Entity var3 = (Entity) p_70971_1_.get(var2);

			if (var3 instanceof EntityLivingBase) {
				var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
				func_174815_a(this, var3);
			}
		}
	}

	/**
	 * Sets a new target for the flight AI. It can be a random coordinate or a
	 * nearby player.
	 */
	private void setNewTarget() {
		forceNewTarget = false;
		final ArrayList var1 = Lists.newArrayList(worldObj.playerEntities);
		final Iterator var2 = var1.iterator();

		while (var2.hasNext()) {
			if (((EntityPlayer) var2.next()).isSpectatorMode()) {
				var2.remove();
			}
		}

		if (rand.nextInt(2) == 0 && !var1.isEmpty()) {
			target = (Entity) var1.get(rand.nextInt(var1.size()));
		} else {
			boolean var3;

			do {
				targetX = 0.0D;
				targetY = 70.0F + rand.nextFloat() * 50.0F;
				targetZ = 0.0D;
				targetX += rand.nextFloat() * 120.0F - 60.0F;
				targetZ += rand.nextFloat() * 120.0F - 60.0F;
				final double var4 = posX - targetX;
				final double var6 = posY - targetY;
				final double var8 = posZ - targetZ;
				var3 = var4 * var4 + var6 * var6 + var8 * var8 > 100.0D;
			}
			while (!var3);

			target = null;
		}
	}

	/**
	 * Simplifies the value of a number by adding/subtracting 180 to the point
	 * that the number is between -180 and 180.
	 */
	private float simplifyAngle(final double p_70973_1_) {
		return (float) MathHelper.wrapAngleTo180_double(p_70973_1_);
	}

	/**
	 * Destroys all blocks that aren't associated with 'The End' inside the
	 * given bounding box.
	 */
	private boolean destroyBlocksInAABB(final AxisAlignedBB p_70972_1_) {
		final int var2 = MathHelper.floor_double(p_70972_1_.minX);
		final int var3 = MathHelper.floor_double(p_70972_1_.minY);
		final int var4 = MathHelper.floor_double(p_70972_1_.minZ);
		final int var5 = MathHelper.floor_double(p_70972_1_.maxX);
		final int var6 = MathHelper.floor_double(p_70972_1_.maxY);
		final int var7 = MathHelper.floor_double(p_70972_1_.maxZ);
		boolean var8 = false;
		boolean var9 = false;

		for (int var10 = var2; var10 <= var5; ++var10) {
			for (int var11 = var3; var11 <= var6; ++var11) {
				for (int var12 = var4; var12 <= var7; ++var12) {
					final Block var13 = worldObj.getBlockState(new BlockPos(var10, var11, var12)).getBlock();

					if (var13.getMaterial() != Material.air) {
						if (var13 != Blocks.barrier && var13 != Blocks.obsidian && var13 != Blocks.end_stone
								&& var13 != Blocks.bedrock && var13 != Blocks.command_block
								&& worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
							var9 = worldObj.setBlockToAir(new BlockPos(var10, var11, var12)) || var9;
						} else {
							var8 = true;
						}
					}
				}
			}
		}

		if (var9) {
			final double var16 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * rand.nextFloat();
			final double var17 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * rand.nextFloat();
			final double var14 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * rand.nextFloat();
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var16, var17, var14, 0.0D, 0.0D, 0.0D,
					new int[0]);
		}

		return var8;
	}

	@Override
	public boolean attackEntityFromPart(final EntityDragonPart p_70965_1_, final DamageSource p_70965_2_,
			float p_70965_3_) {
		if (p_70965_1_ != dragonPartHead) {
			p_70965_3_ = p_70965_3_ / 4.0F + 1.0F;
		}

		final float var4 = rotationYaw * (float) Math.PI / 180.0F;
		final float var5 = MathHelper.sin(var4);
		final float var6 = MathHelper.cos(var4);
		targetX = posX + var5 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		targetY = posY + rand.nextFloat() * 3.0F + 1.0D;
		targetZ = posZ - var6 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		target = null;

		if (p_70965_2_.getEntity() instanceof EntityPlayer || p_70965_2_.isExplosion()) {
			func_82195_e(p_70965_2_, p_70965_3_);
		}

		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (source instanceof EntityDamageSource && ((EntityDamageSource) source).func_180139_w()) {
			func_82195_e(source, amount);
		}

		return false;
	}

	protected boolean func_82195_e(final DamageSource p_82195_1_, final float p_82195_2_) {
		return super.attackEntityFrom(p_82195_1_, p_82195_2_);
	}

	@Override
	public void func_174812_G() {
		setDead();
	}

	/**
	 * handles entity death timer, experience orb and particle creation
	 */
	@Override
	protected void onDeathUpdate() {
		++deathTicks;

		if (deathTicks >= 180 && deathTicks <= 200) {
			final float var1 = (rand.nextFloat() - 0.5F) * 8.0F;
			final float var2 = (rand.nextFloat() - 0.5F) * 4.0F;
			final float var3 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + var1, posY + 2.0D + var2, posZ + var3, 0.0D,
					0.0D, 0.0D, new int[0]);
		}

		int var4;
		int var5;

		if (!worldObj.isRemote) {
			if (deathTicks > 150 && deathTicks % 5 == 0
					&& worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
				var4 = 1000;

				while (var4 > 0) {
					var5 = EntityXPOrb.getXPSplit(var4);
					var4 -= var5;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
				}
			}

			if (deathTicks == 1) {
				worldObj.func_175669_a(1018, new BlockPos(this), 0);
			}
		}

		moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		renderYawOffset = rotationYaw += 20.0F;

		if (deathTicks == 200 && !worldObj.isRemote) {
			var4 = 2000;

			while (var4 > 0) {
				var5 = EntityXPOrb.getXPSplit(var4);
				var4 -= var5;
				worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
			}

			func_175499_a(new BlockPos(posX, 64.0D, posZ));
			setDead();
		}
	}

	private void func_175499_a(final BlockPos p_175499_1_) {
		for (int var7 = -1; var7 <= 32; ++var7) {
			for (int var8 = -4; var8 <= 4; ++var8) {
				for (int var9 = -4; var9 <= 4; ++var9) {
					final double var10 = var8 * var8 + var9 * var9;

					if (var10 <= 12.25D) {
						final BlockPos var12 = p_175499_1_.add(var8, var7, var9);

						if (var7 < 0) {
							if (var10 <= 6.25D) {
								worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
							}
						} else if (var7 > 0) {
							worldObj.setBlockState(var12, Blocks.air.getDefaultState());
						} else if (var10 > 6.25D) {
							worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
						} else {
							worldObj.setBlockState(var12, Blocks.end_portal.getDefaultState());
						}
					}
				}
			}
		}

		worldObj.setBlockState(p_175499_1_, Blocks.bedrock.getDefaultState());
		worldObj.setBlockState(p_175499_1_.offsetUp(), Blocks.bedrock.getDefaultState());
		final BlockPos var13 = p_175499_1_.offsetUp(2);
		worldObj.setBlockState(var13, Blocks.bedrock.getDefaultState());
		worldObj.setBlockState(var13.offsetWest(),
				Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST));
		worldObj.setBlockState(var13.offsetEast(),
				Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST));
		worldObj.setBlockState(var13.offsetNorth(),
				Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH));
		worldObj.setBlockState(var13.offsetSouth(),
				Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH));
		worldObj.setBlockState(p_175499_1_.offsetUp(3), Blocks.bedrock.getDefaultState());
		worldObj.setBlockState(p_175499_1_.offsetUp(4), Blocks.dragon_egg.getDefaultState());
	}

	/**
	 * Makes the entity despawn if requirements are reached
	 */
	@Override
	protected void despawnEntity() {}

	/**
	 * Return the Entity parts making up this Entity (currently only for
	 * dragons)
	 */
	@Override
	public Entity[] getParts() {
		return dragonPartArray;
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public World func_82194_d() {
		return worldObj;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.enderdragon.growl";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.enderdragon.hit";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 5.0F;
	}
}
