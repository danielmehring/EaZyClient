package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityThrowable extends Entity implements IProjectile {

	public static final int EaZy = 1204;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block field_174853_f;
	protected boolean field_174854_a;
	public int throwableShake;

	/** The entity that threw this throwable item. */
	private EntityLivingBase thrower;
	private String throwerName;
	private int ticksInGround;
	private int ticksInAir;
	// private static final String __OBFID = "http://https://fuckuskid00001723";

	public EntityThrowable(final World worldIn) {
		super(worldIn);
		setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	@Override
	public boolean isInRangeToRenderDist(final double distance) {
		double var3 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return distance < var3 * var3;
	}

	public EntityThrowable(final World worldIn, final EntityLivingBase p_i1777_2_) {
		super(worldIn);
		thrower = p_i1777_2_;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(p_i1777_2_.posX, p_i1777_2_.posY + p_i1777_2_.getEyeHeight(), p_i1777_2_.posZ,
				p_i1777_2_.rotationYaw, p_i1777_2_.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		final float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionY = -MathHelper.sin((rotationPitch + func_70183_g()) / 180.0F * (float) Math.PI) * var3;
		setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
	}

	public EntityThrowable(final World worldIn, final double p_i1778_2_, final double p_i1778_4_,
			final double p_i1778_6_) {
		super(worldIn);
		ticksInGround = 0;
		setSize(0.25F, 0.25F);
		setPosition(p_i1778_2_, p_i1778_4_, p_i1778_6_);
	}

	protected float func_70182_d() {
		return 1.5F;
	}

	protected float func_70183_g() {
		return 0.0F;
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
		p_70186_1_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
		p_70186_3_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
		p_70186_5_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
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
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();

		if (throwableShake > 0) {
			--throwableShake;
		}

		if (field_174854_a) {
			if (worldObj.getBlockState(new BlockPos(xTile, yTile, zTile)).getBlock() == field_174853_f) {
				++ticksInGround;

				if (ticksInGround == 1200) {
					setDead();
				}

				return;
			}

			field_174854_a = false;
			motionX *= rand.nextFloat() * 0.2F;
			motionY *= rand.nextFloat() * 0.2F;
			motionZ *= rand.nextFloat() * 0.2F;
			ticksInGround = 0;
			ticksInAir = 0;
		} else {
			++ticksInAir;
		}

		Vec3 var1 = new Vec3(posX, posY, posZ);
		Vec3 var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition var3 = worldObj.rayTraceBlocks(var1, var2);
		var1 = new Vec3(posX, posY, posZ);
		var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

		if (var3 != null) {
			var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		if (!worldObj.isRemote) {
			Entity var4 = null;
			final List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			final EntityLivingBase var8 = getThrower();

			for (int var9 = 0; var9 < var5.size(); ++var9) {
				final Entity var10 = (Entity) var5.get(var9);

				if (var10.canBeCollidedWith() && (var10 != var8 || ticksInAir >= 5)) {
					final float var11 = 0.3F;
					final AxisAlignedBB var12 = var10.getEntityBoundingBox().expand(var11, var11, var11);
					final MovingObjectPosition var13 = var12.calculateIntercept(var1, var2);

					if (var13 != null) {
						final double var14 = var1.distanceTo(var13.hitVec);

						if (var14 < var6 || var6 == 0.0D) {
							var4 = var10;
							var6 = var14;
						}
					}
				}
			}

			if (var4 != null) {
				var3 = new MovingObjectPosition(var4);
			}
		}

		if (var3 != null) {
			if (var3.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
					&& worldObj.getBlockState(var3.getBlockPos()).getBlock() == Blocks.portal) {
				setInPortal();
			} else {
				onImpact(var3);
			}
		}

		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		final float var16 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var16) * 180.0D / Math.PI); rotationPitch
				- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}

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
		float var17 = 0.99F;
		final float var18 = getGravityVelocity();

		if (isInWater()) {
			for (int var7 = 0; var7 < 4; ++var7) {
				final float var19 = 0.25F;
				worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var19, posY - motionY * var19,
						posZ - motionZ * var19, motionX, motionY, motionZ, new int[0]);
			}

			var17 = 0.8F;
		}

		motionX *= var17;
		motionY *= var17;
		motionZ *= var17;
		motionY -= var18;
		setPosition(posX, posY, posZ);
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	protected float getGravityVelocity() {
		return 0.03F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	protected abstract void onImpact(MovingObjectPosition var1);

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) xTile);
		tagCompound.setShort("yTile", (short) yTile);
		tagCompound.setShort("zTile", (short) zTile);
		final ResourceLocation var2 = (ResourceLocation) Block.blockRegistry.getNameForObject(field_174853_f);
		tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
		tagCompound.setByte("shake", (byte) throwableShake);
		tagCompound.setByte("inGround", (byte) (field_174854_a ? 1 : 0));

		if ((throwerName == null || throwerName.length() == 0) && thrower instanceof EntityPlayer) {
			throwerName = thrower.getName();
		}

		tagCompound.setString("ownerName", throwerName == null ? "" : throwerName);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		xTile = tagCompund.getShort("xTile");
		yTile = tagCompund.getShort("yTile");
		zTile = tagCompund.getShort("zTile");

		if (tagCompund.hasKey("inTile", 8)) {
			field_174853_f = Block.getBlockFromName(tagCompund.getString("inTile"));
		} else {
			field_174853_f = Block.getBlockById(tagCompund.getByte("inTile") & 255);
		}

		throwableShake = tagCompund.getByte("shake") & 255;
		field_174854_a = tagCompund.getByte("inGround") == 1;
		throwerName = tagCompund.getString("ownerName");

		if (throwerName != null && throwerName.length() == 0) {
			throwerName = null;
		}
	}

	public EntityLivingBase getThrower() {
		if (thrower == null && throwerName != null && throwerName.length() > 0) {
			thrower = worldObj.getPlayerEntityByName(throwerName);
		}

		return thrower;
	}
}
