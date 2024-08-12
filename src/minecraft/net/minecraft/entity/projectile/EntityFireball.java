package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public abstract class EntityFireball extends Entity {

public static final int EaZy = 1198;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_145795_e = -1;
	private int field_145793_f = -1;
	private int field_145794_g = -1;
	private Block field_145796_h;
	private boolean inGround;
	public EntityLivingBase shootingEntity;
	private int ticksAlive;
	private int ticksInAir;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	// private static final String __OBFID = "http://https://fuckuskid00001717";

	public EntityFireball(final World worldIn) {
		super(worldIn);
		setSize(1.0F, 1.0F);
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

	public EntityFireball(final World worldIn, final double p_i1760_2_, final double p_i1760_4_,
			final double p_i1760_6_, final double p_i1760_8_, final double p_i1760_10_, final double p_i1760_12_) {
		super(worldIn);
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, rotationYaw, rotationPitch);
		setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_);
		final double var14 = MathHelper
				.sqrt_double(p_i1760_8_ * p_i1760_8_ + p_i1760_10_ * p_i1760_10_ + p_i1760_12_ * p_i1760_12_);
		accelerationX = p_i1760_8_ / var14 * 0.1D;
		accelerationY = p_i1760_10_ / var14 * 0.1D;
		accelerationZ = p_i1760_12_ / var14 * 0.1D;
	}

	public EntityFireball(final World worldIn, final EntityLivingBase p_i1761_2_, double p_i1761_3_, double p_i1761_5_,
			double p_i1761_7_) {
		super(worldIn);
		shootingEntity = p_i1761_2_;
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i1761_2_.posX, p_i1761_2_.posY, p_i1761_2_.posZ, p_i1761_2_.rotationYaw,
				p_i1761_2_.rotationPitch);
		setPosition(posX, posY, posZ);
		motionX = motionY = motionZ = 0.0D;
		p_i1761_3_ += rand.nextGaussian() * 0.4D;
		p_i1761_5_ += rand.nextGaussian() * 0.4D;
		p_i1761_7_ += rand.nextGaussian() * 0.4D;
		final double var9 = MathHelper
				.sqrt_double(p_i1761_3_ * p_i1761_3_ + p_i1761_5_ * p_i1761_5_ + p_i1761_7_ * p_i1761_7_);
		accelerationX = p_i1761_3_ / var9 * 0.1D;
		accelerationY = p_i1761_5_ / var9 * 0.1D;
		accelerationZ = p_i1761_7_ / var9 * 0.1D;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (!worldObj.isRemote
				&& (shootingEntity != null && shootingEntity.isDead || !worldObj.isBlockLoaded(new BlockPos(this)))) {
			setDead();
		} else {
			super.onUpdate();
			setFire(1);

			if (inGround) {
				if (worldObj.getBlockState(new BlockPos(field_145795_e, field_145793_f, field_145794_g))
						.getBlock() == field_145796_h) {
					++ticksAlive;

					if (ticksAlive == 600) {
						setDead();
					}

					return;
				}

				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksAlive = 0;
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

			Entity var4 = null;
			final List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;

			for (int var8 = 0; var8 < var5.size(); ++var8) {
				final Entity var9 = (Entity) var5.get(var8);

				if (var9.canBeCollidedWith() && (!var9.isEntityEqual(shootingEntity) || ticksInAir >= 25)) {
					final float var10 = 0.3F;
					final AxisAlignedBB var11 = var9.getEntityBoundingBox().expand(var10, var10, var10);
					final MovingObjectPosition var12 = var11.calculateIntercept(var1, var2);

					if (var12 != null) {
						final double var13 = var1.distanceTo(var12.hitVec);

						if (var13 < var6 || var6 == 0.0D) {
							var4 = var9;
							var6 = var13;
						}
					}
				}
			}

			if (var4 != null) {
				var3 = new MovingObjectPosition(var4);
			}

			if (var3 != null) {
				onImpact(var3);
			}

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			final float var15 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) + 90.0F;

			for (rotationPitch = (float) (Math.atan2(var15, motionY) * 180.0D / Math.PI) - 90.0F; rotationPitch
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
			float var16 = getMotionFactor();

			if (isInWater()) {
				for (int var17 = 0; var17 < 4; ++var17) {
					final float var18 = 0.25F;
					worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var18,
							posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ, new int[0]);
				}

				var16 = 0.8F;
			}

			motionX += accelerationX;
			motionY += accelerationY;
			motionZ += accelerationZ;
			motionX *= var16;
			motionY *= var16;
			motionZ *= var16;
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D,
					new int[0]);
			setPosition(posX, posY, posZ);
		}
	}

	/**
	 * Return the motion factor for this projectile. The factor is multiplied by
	 * the original motion.
	 */
	protected float getMotionFactor() {
		return 0.95F;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected abstract void onImpact(MovingObjectPosition var1);

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) field_145795_e);
		tagCompound.setShort("yTile", (short) field_145793_f);
		tagCompound.setShort("zTile", (short) field_145794_g);
		final ResourceLocation var2 = (ResourceLocation) Block.blockRegistry.getNameForObject(field_145796_h);
		tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
		tagCompound.setByte("inGround", (byte) (inGround ? 1 : 0));
		tagCompound.setTag("direction", newDoubleNBTList(new double[] { motionX, motionY, motionZ }));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		field_145795_e = tagCompund.getShort("xTile");
		field_145793_f = tagCompund.getShort("yTile");
		field_145794_g = tagCompund.getShort("zTile");

		if (tagCompund.hasKey("inTile", 8)) {
			field_145796_h = Block.getBlockFromName(tagCompund.getString("inTile"));
		} else {
			field_145796_h = Block.getBlockById(tagCompund.getByte("inTile") & 255);
		}

		inGround = tagCompund.getByte("inGround") == 1;

		if (tagCompund.hasKey("direction", 9)) {
			final NBTTagList var2 = tagCompund.getTagList("direction", 6);
			motionX = var2.getDouble(0);
			motionY = var2.getDouble(1);
			motionZ = var2.getDouble(2);
		} else {
			setDead();
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public float getCollisionBorderSize() {
		return 1.0F;
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

			if (source.getEntity() != null) {
				final Vec3 var3 = source.getEntity().getLookVec();

				if (var3 != null) {
					motionX = var3.xCoord;
					motionY = var3.yCoord;
					motionZ = var3.zCoord;
					accelerationX = motionX * 0.1D;
					accelerationY = motionY * 0.1D;
					accelerationZ = motionZ * 0.1D;
				}

				if (source.getEntity() instanceof EntityLivingBase) {
					shootingEntity = (EntityLivingBase) source.getEntity();
				}

				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return 1.0F;
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return 15728880;
	}
}
