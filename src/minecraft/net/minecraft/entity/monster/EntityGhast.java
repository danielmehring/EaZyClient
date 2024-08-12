package net.minecraft.entity.monster;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Random;

public class EntityGhast extends EntityFlying implements IMob {

public static final int EaZy = 1156;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The explosion radius of spawned fireballs. */
	private int explosionStrength = 1;
	// private static final String __OBFID = "http://https://fuckuskid00001689";

	public EntityGhast(final World worldIn) {
		super(worldIn);
		setSize(4.0F, 4.0F);
		isImmuneToFire = true;
		experienceValue = 5;
		moveHelper = new EntityGhast.GhastMoveHelper();
		tasks.addTask(5, new EntityGhast.AIRandomFly());
		tasks.addTask(7, new EntityGhast.AILookAround());
		tasks.addTask(7, new EntityGhast.AIFireballAttack());
		targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
	}

	public boolean func_110182_bF() {
		return dataWatcher.getWatchableObjectByte(16) != 0;
	}

	public void func_175454_a(final boolean p_175454_1_) {
		dataWatcher.updateObject(16, (byte) (p_175454_1_ ? 1 : 0));
	}

	public int func_175453_cd() {
		return explosionStrength;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote && worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
			setDead();
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer) {
			super.attackEntityFrom(source, 1000.0F);
			((EntityPlayer) source.getEntity()).triggerAchievement(AchievementList.ghast);
			return true;
		} else {
			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.ghast.moan";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.ghast.scream";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.ghast.death";
	}

	@Override
	protected Item getDropItem() {
		return Items.gunpowder;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		int var3 = rand.nextInt(2) + rand.nextInt(1 + p_70628_2_);
		int var4;

		for (var4 = 0; var4 < var3; ++var4) {
			dropItem(Items.ghast_tear, 1);
		}

		var3 = rand.nextInt(3) + rand.nextInt(1 + p_70628_2_);

		for (var4 = 0; var4 < var3; ++var4) {
			dropItem(Items.gunpowder, 1);
		}
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 10.0F;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return rand.nextInt(20) == 0 && super.getCanSpawnHere() && worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", explosionStrength);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("ExplosionPower", 99)) {
			explosionStrength = tagCompund.getInteger("ExplosionPower");
		}
	}

	@Override
	public float getEyeHeight() {
		return 2.6F;
	}

	class AIFireballAttack extends EntityAIBase {
		private final EntityGhast field_179470_b = EntityGhast.this;
		public int field_179471_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002215";

		@Override
		public boolean shouldExecute() {
			return field_179470_b.getAttackTarget() != null;
		}

		@Override
		public void startExecuting() {
			field_179471_a = 0;
		}

		@Override
		public void resetTask() {
			field_179470_b.func_175454_a(false);
		}

		@Override
		public void updateTask() {
			final EntityLivingBase var1 = field_179470_b.getAttackTarget();
			final double var2 = 64.0D;

			if (var1.getDistanceSqToEntity(field_179470_b) < var2 * var2 && field_179470_b.canEntityBeSeen(var1)) {
				final World var4 = field_179470_b.worldObj;
				++field_179471_a;

				if (field_179471_a == 10) {
					var4.playAuxSFXAtEntity((EntityPlayer) null, 1007, new BlockPos(field_179470_b), 0);
				}

				if (field_179471_a == 20) {
					final double var5 = 4.0D;
					final Vec3 var7 = field_179470_b.getLook(1.0F);
					final double var8 = var1.posX - (field_179470_b.posX + var7.xCoord * var5);
					final double var10 = var1.getEntityBoundingBox().minY + var1.height / 2.0F
							- (0.5D + field_179470_b.posY + field_179470_b.height / 2.0F);
					final double var12 = var1.posZ - (field_179470_b.posZ + var7.zCoord * var5);
					var4.playAuxSFXAtEntity((EntityPlayer) null, 1008, new BlockPos(field_179470_b), 0);
					final EntityLargeFireball var14 = new EntityLargeFireball(var4, field_179470_b, var8, var10, var12);
					var14.field_92057_e = field_179470_b.func_175453_cd();
					var14.posX = field_179470_b.posX + var7.xCoord * var5;
					var14.posY = field_179470_b.posY + field_179470_b.height / 2.0F + 0.5D;
					var14.posZ = field_179470_b.posZ + var7.zCoord * var5;
					var4.spawnEntityInWorld(var14);
					field_179471_a = -40;
				}
			} else if (field_179471_a > 0) {
				--field_179471_a;
			}

			field_179470_b.func_175454_a(field_179471_a > 10);
		}
	}

	class AILookAround extends EntityAIBase {
		private final EntityGhast field_179472_a = EntityGhast.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002217";

		public AILookAround() {
			setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			return true;
		}

		@Override
		public void updateTask() {
			if (field_179472_a.getAttackTarget() == null) {
				field_179472_a.renderYawOffset = field_179472_a.rotationYaw = -((float) Math
						.atan2(field_179472_a.motionX, field_179472_a.motionZ)) * 180.0F / (float) Math.PI;
			} else {
				final EntityLivingBase var1 = field_179472_a.getAttackTarget();
				final double var2 = 64.0D;

				if (var1.getDistanceSqToEntity(field_179472_a) < var2 * var2) {
					final double var4 = var1.posX - field_179472_a.posX;
					final double var6 = var1.posZ - field_179472_a.posZ;
					field_179472_a.renderYawOffset = field_179472_a.rotationYaw = -((float) Math.atan2(var4, var6))
							* 180.0F / (float) Math.PI;
				}
			}
		}
	}

	class AIRandomFly extends EntityAIBase {
		private final EntityGhast field_179454_a = EntityGhast.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002214";

		public AIRandomFly() {
			setMutexBits(1);
		}

		@Override
		public boolean shouldExecute() {
			final EntityMoveHelper var1 = field_179454_a.getMoveHelper();

			if (!var1.isUpdating()) {
				return true;
			} else {
				final double var2 = var1.func_179917_d() - field_179454_a.posX;
				final double var4 = var1.func_179919_e() - field_179454_a.posY;
				final double var6 = var1.func_179918_f() - field_179454_a.posZ;
				final double var8 = var2 * var2 + var4 * var4 + var6 * var6;
				return var8 < 1.0D || var8 > 3600.0D;
			}
		}

		@Override
		public boolean continueExecuting() {
			return false;
		}

		@Override
		public void startExecuting() {
			final Random var1 = field_179454_a.getRNG();
			final double var2 = field_179454_a.posX + (var1.nextFloat() * 2.0F - 1.0F) * 16.0F;
			final double var4 = field_179454_a.posY + (var1.nextFloat() * 2.0F - 1.0F) * 16.0F;
			final double var6 = field_179454_a.posZ + (var1.nextFloat() * 2.0F - 1.0F) * 16.0F;
			field_179454_a.getMoveHelper().setMoveTo(var2, var4, var6, 1.0D);
		}
	}

	class GhastMoveHelper extends EntityMoveHelper {
		private final EntityGhast field_179927_g = EntityGhast.this;
		private int field_179928_h;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002216";

		public GhastMoveHelper() {
			super(EntityGhast.this);
		}

		@Override
		public void onUpdateMoveHelper() {
			if (update) {
				final double var1 = posX - field_179927_g.posX;
				final double var3 = posY - field_179927_g.posY;
				final double var5 = posZ - field_179927_g.posZ;
				double var7 = var1 * var1 + var3 * var3 + var5 * var5;

				if (field_179928_h-- <= 0) {
					field_179928_h += field_179927_g.getRNG().nextInt(5) + 2;
					var7 = MathHelper.sqrt_double(var7);

					if (func_179926_b(posX, posY, posZ, var7)) {
						field_179927_g.motionX += var1 / var7 * 0.1D;
						field_179927_g.motionY += var3 / var7 * 0.1D;
						field_179927_g.motionZ += var5 / var7 * 0.1D;
					} else {
						update = false;
					}
				}
			}
		}

		private boolean func_179926_b(final double p_179926_1_, final double p_179926_3_, final double p_179926_5_,
				final double p_179926_7_) {
			final double var9 = (p_179926_1_ - field_179927_g.posX) / p_179926_7_;
			final double var11 = (p_179926_3_ - field_179927_g.posY) / p_179926_7_;
			final double var13 = (p_179926_5_ - field_179927_g.posZ) / p_179926_7_;
			AxisAlignedBB var15 = field_179927_g.getEntityBoundingBox();

			for (int var16 = 1; var16 < p_179926_7_; ++var16) {
				var15 = var15.offset(var9, var11, var13);

				if (!field_179927_g.worldObj.getCollidingBoundingBoxes(field_179927_g, var15).isEmpty()) {
					return false;
				}
			}

			return true;
		}
	}
}
