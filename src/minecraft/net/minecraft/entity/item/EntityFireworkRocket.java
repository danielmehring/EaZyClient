package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {

public static final int EaZy = 1138;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The age of the firework in ticks. */
	private int fireworkAge;

	/**
	 * The lifetime of the firework in ticks. When the age reaches the lifetime
	 * the firework explodes.
	 */
	private int lifetime;
	// private static final String __OBFID = "http://https://fuckuskid00001718";

	public EntityFireworkRocket(final World worldIn) {
		super(worldIn);
		setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObjectByDataType(8, 5);
	}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	@Override
	public boolean isInRangeToRenderDist(final double distance) {
		return distance < 4096.0D;
	}

	public EntityFireworkRocket(final World worldIn, final double p_i1763_2_, final double p_i1763_4_,
			final double p_i1763_6_, final ItemStack p_i1763_8_) {
		super(worldIn);
		fireworkAge = 0;
		setSize(0.25F, 0.25F);
		setPosition(p_i1763_2_, p_i1763_4_, p_i1763_6_);
		int var9 = 1;

		if (p_i1763_8_ != null && p_i1763_8_.hasTagCompound()) {
			dataWatcher.updateObject(8, p_i1763_8_);
			final NBTTagCompound var10 = p_i1763_8_.getTagCompound();
			final NBTTagCompound var11 = var10.getCompoundTag("Fireworks");

			if (var11 != null) {
				var9 += var11.getByte("Flight");
			}
		}

		motionX = rand.nextGaussian() * 0.001D;
		motionZ = rand.nextGaussian() * 0.001D;
		motionY = 0.05D;
		lifetime = 10 * var9 + rand.nextInt(6) + rand.nextInt(7);
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
		motionX *= 1.15D;
		motionZ *= 1.15D;
		motionY += 0.04D;
		moveEntity(motionX, motionY, motionZ);
		final float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI); rotationPitch
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

		if (fireworkAge == 0 && !isSlient()) {
			worldObj.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
		}

		++fireworkAge;

		if (worldObj.isRemote && fireworkAge % 2 < 2) {
			worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY - 0.3D, posZ,
					rand.nextGaussian() * 0.05D, -motionY * 0.5D, rand.nextGaussian() * 0.05D, new int[0]);
		}

		if (!worldObj.isRemote && fireworkAge > lifetime) {
			worldObj.setEntityState(this, (byte) 17);
			setDead();
		}
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 17 && worldObj.isRemote) {
			final ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);
			NBTTagCompound var3 = null;

			if (var2 != null && var2.hasTagCompound()) {
				var3 = var2.getTagCompound().getCompoundTag("Fireworks");
			}

			worldObj.makeFireworks(posX, posY, posZ, motionX, motionY, motionZ, var3);
		}

		super.handleHealthUpdate(p_70103_1_);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setInteger("Life", fireworkAge);
		tagCompound.setInteger("LifeTime", lifetime);
		final ItemStack var2 = dataWatcher.getWatchableObjectItemStack(8);

		if (var2 != null) {
			final NBTTagCompound var3 = new NBTTagCompound();
			var2.writeToNBT(var3);
			tagCompound.setTag("FireworksItem", var3);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		fireworkAge = tagCompund.getInteger("Life");
		lifetime = tagCompund.getInteger("LifeTime");
		final NBTTagCompound var2 = tagCompund.getCompoundTag("FireworksItem");

		if (var2 != null) {
			final ItemStack var3 = ItemStack.loadItemStackFromNBT(var2);

			if (var3 != null) {
				dataWatcher.updateObject(8, var3);
			}
		}
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(final float p_70013_1_) {
		return super.getBrightness(p_70013_1_);
	}

	@Override
	public int getBrightnessForRender(final float p_70070_1_) {
		return super.getBrightnessForRender(p_70070_1_);
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}
}
