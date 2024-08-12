package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlaze extends EntityMob {

public static final int EaZy = 1151;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Random offset used in floating behaviour */
	private float heightOffset = 0.5F;

	/** ticks until heightOffset is randomized */
	private int heightOffsetUpdateTime;
	// private static final String __OBFID = "http://https://fuckuskid00001682";

	public EntityBlaze(final World worldIn) {
		super(worldIn);
		isImmuneToFire = true;
		experienceValue = 10;
		tasks.addTask(4, new EntityBlaze.AIFireballAttack());
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.blaze.breathe";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.blaze.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.blaze.death";
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

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (!onGround && motionY < 0.0D) {
			motionY *= 0.6D;
		}

		if (worldObj.isRemote) {
			if (rand.nextInt(24) == 0 && !isSlient()) {
				worldObj.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.fire", 1.0F + rand.nextFloat(),
						rand.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int var1 = 0; var1 < 2; ++var1) {
				worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5D) * width,
						posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D,
						new int[0]);
			}
		}

		super.onLivingUpdate();
	}

	@Override
	protected void updateAITasks() {
		if (isWet()) {
			attackEntityFrom(DamageSource.drown, 1.0F);
		}

		--heightOffsetUpdateTime;

		if (heightOffsetUpdateTime <= 0) {
			heightOffsetUpdateTime = 100;
			heightOffset = 0.5F + (float) rand.nextGaussian() * 3.0F;
		}

		final EntityLivingBase var1 = getAttackTarget();

		if (var1 != null && var1.posY + var1.getEyeHeight() > posY + getEyeHeight() + heightOffset) {
			motionY += (0.30000001192092896D - motionY) * 0.30000001192092896D;
			isAirBorne = true;
		}

		super.updateAITasks();
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	@Override
	protected Item getDropItem() {
		return Items.blaze_rod;
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire
	 * effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return func_70845_n();
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		if (p_70628_1_) {
			final int var3 = rand.nextInt(2 + p_70628_2_);

			for (int var4 = 0; var4 < var3; ++var4) {
				dropItem(Items.blaze_rod, 1);
			}
		}
	}

	public boolean func_70845_n() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void func_70844_e(final boolean p_70844_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70844_1_) {
			var2 = (byte) (var2 | 1);
		} else {
			var2 &= -2;
		}

		dataWatcher.updateObject(16, var2);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	class AIFireballAttack extends EntityAIBase {
		private final EntityBlaze field_179469_a = EntityBlaze.this;
		private int field_179467_b;
		private int field_179468_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002225";

		public AIFireballAttack() {
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {
			final EntityLivingBase var1 = field_179469_a.getAttackTarget();
			return var1 != null && var1.isEntityAlive();
		}

		@Override
		public void startExecuting() {
			field_179467_b = 0;
		}

		@Override
		public void resetTask() {
			field_179469_a.func_70844_e(false);
		}

		@Override
		public void updateTask() {
			--field_179468_c;
			final EntityLivingBase var1 = field_179469_a.getAttackTarget();
			final double var2 = field_179469_a.getDistanceSqToEntity(var1);

			if (var2 < 4.0D) {
				if (field_179468_c <= 0) {
					field_179468_c = 20;
					field_179469_a.attackEntityAsMob(var1);
				}

				field_179469_a.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0D);
			} else if (var2 < 256.0D) {
				final double var4 = var1.posX - field_179469_a.posX;
				final double var6 = var1.getEntityBoundingBox().minY + var1.height / 2.0F
						- (field_179469_a.posY + field_179469_a.height / 2.0F);
				final double var8 = var1.posZ - field_179469_a.posZ;

				if (field_179468_c <= 0) {
					++field_179467_b;

					if (field_179467_b == 1) {
						field_179468_c = 60;
						field_179469_a.func_70844_e(true);
					} else if (field_179467_b <= 4) {
						field_179468_c = 6;
					} else {
						field_179468_c = 100;
						field_179467_b = 0;
						field_179469_a.func_70844_e(false);
					}

					if (field_179467_b > 1) {
						final float var10 = MathHelper.sqrt_float(MathHelper.sqrt_double(var2)) * 0.5F;
						field_179469_a.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1009, new BlockPos(
								(int) field_179469_a.posX, (int) field_179469_a.posY, (int) field_179469_a.posZ), 0);

						for (int var11 = 0; var11 < 1; ++var11) {
							final EntitySmallFireball var12 = new EntitySmallFireball(field_179469_a.worldObj,
									field_179469_a, var4 + field_179469_a.getRNG().nextGaussian() * var10, var6,
									var8 + field_179469_a.getRNG().nextGaussian() * var10);
							var12.posY = field_179469_a.posY + field_179469_a.height / 2.0F + 0.5D;
							field_179469_a.worldObj.spawnEntityInWorld(var12);
						}
					}
				}

				field_179469_a.getLookHelper().setLookPositionWithEntity(var1, 10.0F, 10.0F);
			} else {
				field_179469_a.getNavigator().clearPathEntity();
				field_179469_a.getMoveHelper().setMoveTo(var1.posX, var1.posY, var1.posZ, 1.0D);
			}

			super.updateTask();
		}
	}
}
