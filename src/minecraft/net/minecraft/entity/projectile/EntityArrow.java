package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class EntityArrow extends Entity implements IProjectile {

public static final int EaZy = 1196;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_145791_d = -1;
	private int field_145792_e = -1;
	private int field_145789_f = -1;
	private Block field_145790_g;
	private int inData;
	private boolean inGround;

	/** 1 if the player can pick up the arrow */
	public int canBePickedUp;

	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake;

	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 2.0D;

	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;
	// private static final String __OBFID = "http://https://fuckuskid00001715";

	public EntityArrow(final World worldIn) {
		super(worldIn);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}

	public EntityArrow(final World worldIn, final double p_i1754_2_, final double p_i1754_4_, final double p_i1754_6_) {
		super(worldIn);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
	}

	public EntityArrow(final World worldIn, final EntityLivingBase p_i1755_2_, final EntityLivingBase p_i1755_3_,
			final float p_i1755_4_, final float p_i1755_5_) {
		super(worldIn);
		renderDistanceWeight = 10.0D;
		shootingEntity = p_i1755_2_;

		if (p_i1755_2_ instanceof EntityPlayer) {
			canBePickedUp = 1;
		}

		posY = p_i1755_2_.posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
		final double var6 = p_i1755_3_.posX - p_i1755_2_.posX;
		final double var8 = p_i1755_3_.getEntityBoundingBox().minY + p_i1755_3_.height / 3.0F - posY;
		final double var10 = p_i1755_3_.posZ - p_i1755_2_.posZ;
		final double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D) {
			final float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			final float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			final double var16 = var6 / var12;
			final double var18 = var10 / var12;
			setLocationAndAngles(p_i1755_2_.posX + var16, posY, p_i1755_2_.posZ + var18, var14, var15);
			final float var20 = (float) (var12 * 0.20000000298023224D);
			setThrowableHeading(var6, var8 + var20, var10, p_i1755_4_, p_i1755_5_);
		}
	}

	public EntityArrow(final World worldIn, final EntityLivingBase p_i1756_2_, final float p_i1756_3_) {
		super(worldIn);
		renderDistanceWeight = 10.0D;
		shootingEntity = p_i1756_2_;

		if (p_i1756_2_ instanceof EntityPlayer) {
			canBePickedUp = 1;
		}

		setSize(0.5F, 0.5F);
		setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ,
				p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
		setThrowableHeading(motionX, motionY, motionZ, p_i1756_3_ * 1.5F, 1.0F);
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	@Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, final float p_70186_7_,
			final float p_70186_8_) {
		final float var9 = MathHelper
				.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
		p_70186_1_ /= var9;
		p_70186_3_ /= var9;
		p_70186_5_ /= var9;
		p_70186_1_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
		p_70186_3_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
		p_70186_5_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
		p_70186_1_ *= p_70186_7_;
		p_70186_3_ *= p_70186_7_;
		p_70186_5_ *= p_70186_7_;
		motionX = p_70186_1_;
		motionY = p_70186_3_;
		motionZ = p_70186_5_;
		final float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(p_70186_3_, var10) * 180.0D / Math.PI);
		ticksInGround = 0;
	}

	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
		setRotation(p_180426_7_, p_180426_8_);
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	public void setVelocity(final double x, final double y, final double z) {
		motionX = x;
		motionY = y;
		motionZ = z;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			final float var7 = MathHelper.sqrt_double(x * x + z * z);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(y, var7) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
			final float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}

		final BlockPos var18 = new BlockPos(field_145791_d, field_145792_e, field_145789_f);
		IBlockState var2 = worldObj.getBlockState(var18);
		final Block var3 = var2.getBlock();

		if (var3.getMaterial() != Material.air) {
			var3.setBlockBoundsBasedOnState(worldObj, var18);
			final AxisAlignedBB var4 = var3.getCollisionBoundingBox(worldObj, var18, var2);

			if (var4 != null && var4.isVecInside(new Vec3(posX, posY, posZ))) {
				inGround = true;
			}
		}

		if (arrowShake > 0) {
			--arrowShake;
		}

		if (inGround) {
			final int var20 = var3.getMetaFromState(var2);

			if (var3 == field_145790_g && var20 == inData) {
				++ticksInGround;

				if (ticksInGround >= 1200) {
					setDead();
				}
			} else {
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			}
		} else {
			++ticksInAir;
			Vec3 var19 = new Vec3(posX, posY, posZ);
			Vec3 var5 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var6 = worldObj.rayTraceBlocks(var19, var5, false, true, false);
			var19 = new Vec3(posX, posY, posZ);
			var5 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

			if (var6 != null) {
				var5 = new Vec3(var6.hitVec.xCoord, var6.hitVec.yCoord, var6.hitVec.zCoord);
			}

			Entity var7 = null;
			final List var8 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var9 = 0.0D;
			int var11;
			float var13;

			for (var11 = 0; var11 < var8.size(); ++var11) {
				final Entity var12 = (Entity) var8.get(var11);

				if (var12.canBeCollidedWith() && (var12 != shootingEntity || ticksInAir >= 5)) {
					var13 = 0.3F;
					final AxisAlignedBB var14 = var12.getEntityBoundingBox().expand(var13, var13, var13);
					final MovingObjectPosition var15 = var14.calculateIntercept(var19, var5);

					if (var15 != null) {
						final double var16 = var19.distanceTo(var15.hitVec);

						if (var16 < var9 || var9 == 0.0D) {
							var7 = var12;
							var9 = var16;
						}
					}
				}
			}

			if (var7 != null) {
				var6 = new MovingObjectPosition(var7);
			}

			if (var6 != null && var6.entityHit != null && var6.entityHit instanceof EntityPlayer) {
				final EntityPlayer var21 = (EntityPlayer) var6.entityHit;

				if (var21.capabilities.disableDamage || shootingEntity instanceof EntityPlayer
						&& !((EntityPlayer) shootingEntity).canAttackPlayer(var21)) {
					var6 = null;
				}
			}

			float var22;
			float var25;
			float var29;

			if (var6 != null) {
				if (var6.entityHit != null) {
					var22 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int var24 = MathHelper.ceiling_double_int(var22 * damage);

					if (getIsCritical()) {
						var24 += rand.nextInt(var24 / 2 + 2);
					}

					DamageSource var26;

					if (shootingEntity == null) {
						var26 = DamageSource.causeArrowDamage(this, this);
					} else {
						var26 = DamageSource.causeArrowDamage(this, shootingEntity);
					}

					if (isBurning() && !(var6.entityHit instanceof EntityEnderman)) {
						var6.entityHit.setFire(5);
					}

					if (var6.entityHit.attackEntityFrom(var26, var24)) {
						if (var6.entityHit instanceof EntityLivingBase) {
							final EntityLivingBase var27 = (EntityLivingBase) var6.entityHit;

							if (!worldObj.isRemote) {
								var27.setArrowCountInEntity(var27.getArrowCountInEntity() + 1);
							}

							if (knockbackStrength > 0) {
								var29 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);

								if (var29 > 0.0F) {
									var6.entityHit.addVelocity(
											motionX * knockbackStrength * 0.6000000238418579D / var29, 0.1D,
											motionZ * knockbackStrength * 0.6000000238418579D / var29);
								}
							}

							if (shootingEntity instanceof EntityLivingBase) {
								EnchantmentHelper.func_151384_a(var27, shootingEntity);
								EnchantmentHelper.func_151385_b((EntityLivingBase) shootingEntity, var27);
							}

							if (shootingEntity != null && var6.entityHit != shootingEntity
									&& var6.entityHit instanceof EntityPlayer
									&& shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) shootingEntity).playerNetServerHandler
										.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}

						playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));

						if (!(var6.entityHit instanceof EntityEnderman)) {
							setDead();
						}
					} else {
						motionX *= -0.10000000149011612D;
						motionY *= -0.10000000149011612D;
						motionZ *= -0.10000000149011612D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}
				} else {
					final BlockPos var23 = var6.getBlockPos();
					field_145791_d = var23.getX();
					field_145792_e = var23.getY();
					field_145789_f = var23.getZ();
					var2 = worldObj.getBlockState(var23);
					field_145790_g = var2.getBlock();
					inData = field_145790_g.getMetaFromState(var2);
					motionX = (float) (var6.hitVec.xCoord - posX);
					motionY = (float) (var6.hitVec.yCoord - posY);
					motionZ = (float) (var6.hitVec.zCoord - posZ);
					var25 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / var25 * 0.05000000074505806D;
					posY -= motionY / var25 * 0.05000000074505806D;
					posZ -= motionZ / var25 * 0.05000000074505806D;
					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					arrowShake = 7;
					setIsCritical(false);

					if (field_145790_g.getMaterial() != Material.air) {
						field_145790_g.onEntityCollidedWithBlock(worldObj, var23, var2, this);
					}
				}
			}

			if (getIsCritical()) {
				for (var11 = 0; var11 < 4; ++var11) {
					worldObj.spawnParticle(EnumParticleTypes.CRIT, posX + motionX * var11 / 4.0D,
							posY + motionY * var11 / 4.0D, posZ + motionZ * var11 / 4.0D, -motionX, -motionY + 0.2D,
							-motionZ, new int[0]);
				}
			}

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			var22 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

			for (rotationPitch = (float) (Math.atan2(motionY, var22) * 180.0D / Math.PI); rotationPitch
					- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
			}

			while (rotationPitch - prevRotationPitch >= 180.0F) {
				prevRotationPitch += 360.0F;
			}

			while (rotationYaw - prevRotationYaw < -180.0F) {
				prevRotationYaw -= 360.0F;
			}

			while (rotationYaw - prevRotationYaw >= 180.0F) {
				prevRotationYaw += 360.0F;
			}

			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			var25 = 0.99F;
			var13 = 0.05F;

			if (isInWater()) {
				for (int var28 = 0; var28 < 4; ++var28) {
					var29 = 0.25F;
					worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var29,
							posY - motionY * var29, posZ - motionZ * var29, motionX, motionY, motionZ, new int[0]);
				}

				var25 = 0.6F;
			}

			if (isWet()) {
				extinguish();
			}

			motionX *= var25;
			motionY *= var25;
			motionZ *= var25;
			motionY -= var13;
			setPosition(posX, posY, posZ);
			doBlockCollisions();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) field_145791_d);
		tagCompound.setShort("yTile", (short) field_145792_e);
		tagCompound.setShort("zTile", (short) field_145789_f);
		tagCompound.setShort("life", (short) ticksInGround);
		final ResourceLocation var2 = (ResourceLocation) Block.blockRegistry.getNameForObject(field_145790_g);
		tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
		tagCompound.setByte("inData", (byte) inData);
		tagCompound.setByte("shake", (byte) arrowShake);
		tagCompound.setByte("inGround", (byte) (inGround ? 1 : 0));
		tagCompound.setByte("pickup", (byte) canBePickedUp);
		tagCompound.setDouble("damage", damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		field_145791_d = tagCompund.getShort("xTile");
		field_145792_e = tagCompund.getShort("yTile");
		field_145789_f = tagCompund.getShort("zTile");
		ticksInGround = tagCompund.getShort("life");

		if (tagCompund.hasKey("inTile", 8)) {
			field_145790_g = Block.getBlockFromName(tagCompund.getString("inTile"));
		} else {
			field_145790_g = Block.getBlockById(tagCompund.getByte("inTile") & 255);
		}

		inData = tagCompund.getByte("inData") & 255;
		arrowShake = tagCompund.getByte("shake") & 255;
		inGround = tagCompund.getByte("inGround") == 1;

		if (tagCompund.hasKey("damage", 99)) {
			damage = tagCompund.getDouble("damage");
		}

		if (tagCompund.hasKey("pickup", 99)) {
			canBePickedUp = tagCompund.getByte("pickup");
		} else if (tagCompund.hasKey("player", 99)) {
			canBePickedUp = tagCompund.getBoolean("player") ? 1 : 0;
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(final EntityPlayer entityIn) {
		if (!worldObj.isRemote && inGround && arrowShake <= 0) {
			boolean var2 = canBePickedUp == 1 || canBePickedUp == 2 && entityIn.capabilities.isCreativeMode;

			if (canBePickedUp == 1 && !entityIn.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1))) {
				var2 = false;
			}

			if (var2) {
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				entityIn.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	public void setDamage(final double p_70239_1_) {
		damage = p_70239_1_;
	}

	public double getDamage() {
		return damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(final int p_70240_1_) {
		knockbackStrength = p_70240_1_;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public void setIsCritical(final boolean p_70243_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70243_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 1));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -2));
		}
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind
	 * it.
	 */
	public boolean getIsCritical() {
		final byte var1 = dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}
}
