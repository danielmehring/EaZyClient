package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature {

public static final int EaZy = 1176;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Coordinates of where the bat spawned. */
	private BlockPos spawnPosition;
	// private static final String __OBFID = "http://https://fuckuskid00001637";

	public EntityBat(final World worldIn) {
		super(worldIn);
		setSize(0.5F, 0.9F);
		setIsBatHanging(true);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.1F;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	@Override
	protected float getSoundPitch() {
		return super.getSoundPitch() * 0.95F;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return getIsBatHanging() && rand.nextInt(4) != 0 ? null : "mob.bat.idle";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.bat.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.bat.death";
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(final Entity p_82167_1_) {}

	@Override
	protected void collideWithNearbyEntities() {}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
	}

	public boolean getIsBatHanging() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setIsBatHanging(final boolean p_82236_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_82236_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 1));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -2));
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (getIsBatHanging()) {
			motionX = motionY = motionZ = 0.0D;
			posY = MathHelper.floor_double(posY) + 1.0D - height;
		} else {
			motionY *= 0.6000000238418579D;
		}
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		final BlockPos var1 = new BlockPos(this);
		final BlockPos var2 = var1.offsetUp();

		if (getIsBatHanging()) {
			if (!worldObj.getBlockState(var2).getBlock().isNormalCube()) {
				setIsBatHanging(false);
				worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, var1, 0);
			} else {
				if (rand.nextInt(200) == 0) {
					rotationYawHead = rand.nextInt(360);
				}

				if (worldObj.getClosestPlayerToEntity(this, 4.0D) != null) {
					setIsBatHanging(false);
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, var1, 0);
				}
			}
		} else {
			if (spawnPosition != null && (!worldObj.isAirBlock(spawnPosition) || spawnPosition.getY() < 1)) {
				spawnPosition = null;
			}

			if (spawnPosition == null || rand.nextInt(30) == 0
					|| spawnPosition.distanceSq((int) posX, (int) posY, (int) posZ) < 4.0D) {
				spawnPosition = new BlockPos((int) posX + rand.nextInt(7) - rand.nextInt(7),
						(int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
			}

			final double var3 = spawnPosition.getX() + 0.5D - posX;
			final double var5 = spawnPosition.getY() + 0.1D - posY;
			final double var7 = spawnPosition.getZ() + 0.5D - posZ;
			motionX += (Math.signum(var3) * 0.5D - motionX) * 0.10000000149011612D;
			motionY += (Math.signum(var5) * 0.699999988079071D - motionY) * 0.10000000149011612D;
			motionZ += (Math.signum(var7) * 0.5D - motionZ) * 0.10000000149011612D;
			final float var9 = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
			final float var10 = MathHelper.wrapAngleTo180_float(var9 - rotationYaw);
			moveForward = 0.5F;
			rotationYaw += var10;

			if (rand.nextInt(100) == 0 && worldObj.getBlockState(var2).getBlock().isNormalCube()) {
				setIsBatHanging(true);
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

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	@Override
	protected void func_180433_a(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_,
			final BlockPos p_180433_5_) {}

	/**
	 * Return whether this entity should NOT trigger a pressure plate or a
	 * tripwire.
	 */
	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			if (!worldObj.isRemote && getIsBatHanging()) {
				setIsBatHanging(false);
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		dataWatcher.updateObject(16, tagCompund.getByte("BatFlags"));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setByte("BatFlags", dataWatcher.getWatchableObjectByte(16));
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		final BlockPos var1 = new BlockPos(posX, getEntityBoundingBox().minY, posZ);

		if (var1.getY() >= 63) {
			return false;
		} else {
			final int var2 = worldObj.getLightFromNeighbors(var1);
			byte var3 = 4;

			if (func_175569_a(worldObj.getCurrentDate())) {
				var3 = 7;
			} else if (rand.nextBoolean()) {
				return false;
			}

			return var2 > rand.nextInt(var3) ? false : super.getCanSpawnHere();
		}
	}

	private boolean func_175569_a(final Calendar p_175569_1_) {
		return p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20
				|| p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3;
	}

	@Override
	public float getEyeHeight() {
		return height / 2.0F;
	}
}
