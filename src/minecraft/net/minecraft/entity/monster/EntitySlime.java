package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntitySlime extends EntityLiving implements IMob {

public static final int EaZy = 1166;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean field_175452_bi;
	// private static final String __OBFID = "http://https://fuckuskid00001698";

	public EntitySlime(final World worldIn) {
		super(worldIn);
		moveHelper = new EntitySlime.SlimeMoveHelper();
		tasks.addTask(1, new EntitySlime.AISlimeFloat());
		tasks.addTask(2, new EntitySlime.AISlimeAttack());
		tasks.addTask(3, new EntitySlime.AISlimeFaceRandom());
		tasks.addTask(5, new EntitySlime.AISlimeHop());
		targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
		targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 1);
	}

	protected void setSlimeSize(final int p_70799_1_) {
		dataWatcher.updateObject(16, (byte) p_70799_1_);
		setSize(0.51000005F * p_70799_1_, 0.51000005F * p_70799_1_);
		setPosition(posX, posY, posZ);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(p_70799_1_ * p_70799_1_);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F + 0.1F * p_70799_1_);
		setHealth(getMaxHealth());
		experienceValue = p_70799_1_;
	}

	/**
	 * Returns the size of the slime.
	 */
	public int getSlimeSize() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Size", getSlimeSize() - 1);
		tagCompound.setBoolean("wasOnGround", field_175452_bi);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		int var2 = tagCompund.getInteger("Size");

		if (var2 < 0) {
			var2 = 0;
		}

		setSlimeSize(var2 + 1);
		field_175452_bi = tagCompund.getBoolean("wasOnGround");
	}

	protected EnumParticleTypes func_180487_n() {
		return EnumParticleTypes.SLIME;
	}

	/**
	 * Returns the name of the sound played when the slime jumps.
	 */
	protected String getJumpSound() {
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && getSlimeSize() > 0) {
			isDead = true;
		}

		squishFactor += (squishAmount - squishFactor) * 0.5F;
		prevSquishFactor = squishFactor;
		super.onUpdate();

		if (onGround && !field_175452_bi) {
			final int var1 = getSlimeSize();

			for (int var2 = 0; var2 < var1 * 8; ++var2) {
				final float var3 = rand.nextFloat() * (float) Math.PI * 2.0F;
				final float var4 = rand.nextFloat() * 0.5F + 0.5F;
				final float var5 = MathHelper.sin(var3) * var1 * 0.5F * var4;
				final float var6 = MathHelper.cos(var3) * var1 * 0.5F * var4;
				final World var10000 = worldObj;
				final EnumParticleTypes var10001 = func_180487_n();
				final double var10002 = posX + var5;
				final double var10004 = posZ + var6;
				var10000.spawnParticle(var10001, var10002, getEntityBoundingBox().minY, var10004, 0.0D, 0.0D, 0.0D,
						new int[0]);
			}

			if (makesSoundOnLand()) {
				playSound(getJumpSound(), getSoundVolume(),
						((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}

			squishAmount = -0.5F;
		} else if (!onGround && field_175452_bi) {
			squishAmount = 1.0F;
		}

		field_175452_bi = onGround;
		alterSquishAmount();
	}

	protected void alterSquishAmount() {
		squishAmount *= 0.6F;
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	protected int getJumpDelay() {
		return rand.nextInt(20) + 10;
	}

	protected EntitySlime createInstance() {
		return new EntitySlime(worldObj);
	}

	@Override
	public void func_145781_i(final int p_145781_1_) {
		if (p_145781_1_ == 16) {
			final int var2 = getSlimeSize();
			setSize(0.51000005F * var2, 0.51000005F * var2);
			rotationYaw = rotationYawHead;
			renderYawOffset = rotationYawHead;

			if (isInWater() && rand.nextInt(20) == 0) {
				resetHeight();
			}
		}

		super.func_145781_i(p_145781_1_);
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		final int var1 = getSlimeSize();

		if (!worldObj.isRemote && var1 > 1 && getHealth() <= 0.0F) {
			final int var2 = 2 + rand.nextInt(3);

			for (int var3 = 0; var3 < var2; ++var3) {
				final float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
				final float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
				final EntitySlime var6 = createInstance();

				if (hasCustomName()) {
					var6.setCustomNameTag(getCustomNameTag());
				}

				if (isNoDespawnRequired()) {
					var6.enablePersistence();
				}

				var6.setSlimeSize(var1 / 2);
				var6.setLocationAndAngles(posX + var4, posY + 0.5D, posZ + var5, rand.nextFloat() * 360.0F, 0.0F);
				worldObj.spawnEntityInWorld(var6);
			}
		}

		super.setDead();
	}

	/**
	 * Applies a velocity to each of the entities pushing them away from each
	 * other. Args: entity
	 */
	@Override
	public void applyEntityCollision(final Entity entityIn) {
		super.applyEntityCollision(entityIn);

		if (entityIn instanceof EntityIronGolem && canDamagePlayer()) {
			func_175451_e((EntityLivingBase) entityIn);
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(final EntityPlayer entityIn) {
		if (canDamagePlayer()) {
			func_175451_e(entityIn);
		}
	}

	protected void func_175451_e(final EntityLivingBase p_175451_1_) {
		final int var2 = getSlimeSize();

		if (canEntityBeSeen(p_175451_1_) && getDistanceSqToEntity(p_175451_1_) < 0.6D * var2 * 0.6D * var2
				&& p_175451_1_.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength())) {
			playSound("mob.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			func_174815_a(this, p_175451_1_);
		}
	}

	@Override
	public float getEyeHeight() {
		return 0.625F * height;
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the
	 * slime's size)
	 */
	protected boolean canDamagePlayer() {
		return getSlimeSize() > 1;
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the
	 * slime.
	 */
	protected int getAttackStrength() {
		return getSlimeSize();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}

	@Override
	protected Item getDropItem() {
		return getSlimeSize() == 1 ? Items.slime_ball : null;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		final Chunk var1 = worldObj
				.getChunkFromBlockCoords(new BlockPos(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)));

		if (worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && rand.nextInt(4) != 1) {
			return false;
		} else {
			if (worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
				final BiomeGenBase var2 = worldObj.getBiomeGenForCoords(
						new BlockPos(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)));

				if (var2 == BiomeGenBase.swampland && posY > 50.0D && posY < 70.0D && rand.nextFloat() < 0.5F
						&& rand.nextFloat() < worldObj.getCurrentMoonPhaseFactor()
						&& worldObj.getLightFromNeighbors(new BlockPos(this)) <= rand.nextInt(8)) {
					return super.getCanSpawnHere();
				}

				if (rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && posY < 40.0D) {
					return super.getCanSpawnHere();
				}
			}

			return false;
		}
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F * getSlimeSize();
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return 0;
	}

	/**
	 * Returns true if the slime makes a sound when it jumps (based upon the
	 * slime's size)
	 */
	protected boolean makesSoundOnJump() {
		return getSlimeSize() > 0;
	}

	/**
	 * Returns true if the slime makes a sound when it lands after a jump (based
	 * upon the slime's size)
	 */
	protected boolean makesSoundOnLand() {
		return getSlimeSize() > 2;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jump() {
		motionY = 0.41999998688697815D;
		isAirBorne = true;
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
		int var3 = rand.nextInt(3);

		if (var3 < 2 && rand.nextFloat() < 0.5F * p_180482_1_.func_180170_c()) {
			++var3;
		}

		final int var4 = 1 << var3;
		setSlimeSize(var4);
		return super.func_180482_a(p_180482_1_, p_180482_2_);
	}

	class AISlimeAttack extends EntityAIBase {
		private final EntitySlime field_179466_a = EntitySlime.this;
		private int field_179465_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002202";

		public AISlimeAttack() {
			setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			final EntityLivingBase var1 = field_179466_a.getAttackTarget();
			return var1 == null ? false : var1.isEntityAlive();
		}

		@Override
		public void startExecuting() {
			field_179465_b = 300;
			super.startExecuting();
		}

		@Override
		public boolean continueExecuting() {
			final EntityLivingBase var1 = field_179466_a.getAttackTarget();
			return var1 == null ? false : !var1.isEntityAlive() ? false : --field_179465_b > 0;
		}

		@Override
		public void updateTask() {
			field_179466_a.faceEntity(field_179466_a.getAttackTarget(), 10.0F, 10.0F);
			((EntitySlime.SlimeMoveHelper) field_179466_a.getMoveHelper()).func_179920_a(field_179466_a.rotationYaw,
					field_179466_a.canDamagePlayer());
		}
	}

	class AISlimeFaceRandom extends EntityAIBase {
		private final EntitySlime field_179461_a = EntitySlime.this;
		private float field_179459_b;
		private int field_179460_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002198";

		public AISlimeFaceRandom() {
			setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			return field_179461_a.getAttackTarget() == null
					&& (field_179461_a.onGround || field_179461_a.isInWater() || field_179461_a.func_180799_ab());
		}

		@Override
		public void updateTask() {
			if (--field_179460_c <= 0) {
				field_179460_c = 40 + field_179461_a.getRNG().nextInt(60);
				field_179459_b = field_179461_a.getRNG().nextInt(360);
			}

			((EntitySlime.SlimeMoveHelper) field_179461_a.getMoveHelper()).func_179920_a(field_179459_b, false);
		}
	}

	class AISlimeFloat extends EntityAIBase {
		private final EntitySlime field_179457_a = EntitySlime.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002201";

		public AISlimeFloat() {
			setMutexBits(5);
			((PathNavigateGround) getNavigator()).func_179693_d(true);
		}

		@Override
		public boolean shouldExecute() {
			return field_179457_a.isInWater() || field_179457_a.func_180799_ab();
		}

		@Override
		public void updateTask() {
			if (field_179457_a.getRNG().nextFloat() < 0.8F) {
				field_179457_a.getJumpHelper().setJumping();
			}

			((EntitySlime.SlimeMoveHelper) field_179457_a.getMoveHelper()).func_179921_a(1.2D);
		}
	}

	class AISlimeHop extends EntityAIBase {
		private final EntitySlime field_179458_a = EntitySlime.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002200";

		public AISlimeHop() {
			setMutexBits(5);
		}

		@Override
		public boolean shouldExecute() {
			return true;
		}

		@Override
		public void updateTask() {
			((EntitySlime.SlimeMoveHelper) field_179458_a.getMoveHelper()).func_179921_a(1.0D);
		}
	}

	class SlimeMoveHelper extends EntityMoveHelper {
		private float field_179922_g;
		private int field_179924_h;
		private final EntitySlime field_179925_i = EntitySlime.this;
		private boolean field_179923_j;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002199";

		public SlimeMoveHelper() {
			super(EntitySlime.this);
		}

		public void func_179920_a(final float p_179920_1_, final boolean p_179920_2_) {
			field_179922_g = p_179920_1_;
			field_179923_j = p_179920_2_;
		}

		public void func_179921_a(final double p_179921_1_) {
			speed = p_179921_1_;
			update = true;
		}

		@Override
		public void onUpdateMoveHelper() {
			entity.rotationYaw = limitAngle(entity.rotationYaw, field_179922_g, 30.0F);
			entity.rotationYawHead = entity.rotationYaw;
			entity.renderYawOffset = entity.rotationYaw;

			if (!update) {
				entity.setMoveForward(0.0F);
			} else {
				update = false;

				if (entity.onGround) {
					entity.setAIMoveSpeed((float) (speed
							* entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));

					if (field_179924_h-- <= 0) {
						field_179924_h = field_179925_i.getJumpDelay();

						if (field_179923_j) {
							field_179924_h /= 3;
						}

						field_179925_i.getJumpHelper().setJumping();

						if (field_179925_i.makesSoundOnJump()) {
							field_179925_i.playSound(field_179925_i.getJumpSound(), field_179925_i.getSoundVolume(),
									((field_179925_i.getRNG().nextFloat() - field_179925_i.getRNG().nextFloat()) * 0.2F
											+ 1.0F) * 0.8F);
						}
					} else {
						field_179925_i.moveStrafing = field_179925_i.moveForward = 0.0F;
						entity.setAIMoveSpeed(0.0F);
					}
				} else {
					entity.setAIMoveSpeed((float) (speed
							* entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
				}
			}
		}
	}
}
