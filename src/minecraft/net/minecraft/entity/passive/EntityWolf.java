package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityWolf extends EntityTameable {

public static final int EaZy = 1189;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Float used to smooth the rotation of the wolf head */
	private float headRotationCourse;
	private float headRotationCourseOld;

	/** true is the wolf is wet else false */
	private boolean isWet;

	/** True if the wolf is shaking else False */
	private boolean isShaking;

	/**
	 * This time increases while wolf is shaking and emitting water particles.
	 */
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;
	// private static final String __OBFID = "http://https://fuckuskid00001654";

	public EntityWolf(final World worldIn) {
		super(worldIn);
		setSize(0.6F, 0.8F);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		tasks.addTask(6, new EntityAIMate(this, 1.0D));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIBeg(this, 8.0F));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(9, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002229";
			public boolean func_180094_a(final Entity p_180094_1_) {
				return p_180094_1_ instanceof EntitySheep || p_180094_1_ instanceof EntityRabbit;
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_180094_a((Entity) p_apply_1_);
			}
		}));
		targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
		setTamed(false);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);

		if (isTamed()) {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		}

		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
	}

	/**
	 * Sets the active target the Task system uses for tracking
	 */
	@Override
	public void setAttackTarget(final EntityLivingBase p_70624_1_) {
		super.setAttackTarget(p_70624_1_);

		if (p_70624_1_ == null) {
			setAngry(false);
		} else if (!isTamed()) {
			setAngry(true);
		}
	}

	@Override
	protected void updateAITasks() {
		dataWatcher.updateObject(18, getHealth());
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, getHealth());
		dataWatcher.addObject(19, (byte) 0);
		dataWatcher.addObject(20, (byte) EnumDyeColor.RED.func_176765_a());
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.wolf.step", 0.15F, 1.0F);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Angry", isAngry());
		tagCompound.setByte("CollarColor", (byte) func_175546_cu().getDyeColorDamage());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setAngry(tagCompund.getBoolean("Angry"));

		if (tagCompund.hasKey("CollarColor", 99)) {
			func_175547_a(EnumDyeColor.func_176766_a(tagCompund.getByte("CollarColor")));
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return isAngry() ? "mob.wolf.growl"
				: rand.nextInt(3) == 0 ? isTamed() && dataWatcher.getWatchableObjectFloat(18) < 10.0F ? "mob.wolf.whine"
						: "mob.wolf.panting" : "mob.wolf.bark";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.wolf.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.wolf.death";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemById(-1);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (!worldObj.isRemote && isWet && !isShaking && !hasPath() && onGround) {
			isShaking = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
			worldObj.setEntityState(this, (byte) 8);
		}

		if (!worldObj.isRemote && getAttackTarget() == null && isAngry()) {
			setAngry(false);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		headRotationCourseOld = headRotationCourse;

		if (func_70922_bv()) {
			headRotationCourse += (1.0F - headRotationCourse) * 0.4F;
		} else {
			headRotationCourse += (0.0F - headRotationCourse) * 0.4F;
		}

		if (isWet()) {
			isWet = true;
			isShaking = false;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		} else if ((isWet || isShaking) && isShaking) {
			if (timeWolfIsShaking == 0.0F) {
				playSound("mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}

			prevTimeWolfIsShaking = timeWolfIsShaking;
			timeWolfIsShaking += 0.05F;

			if (prevTimeWolfIsShaking >= 2.0F) {
				isWet = false;
				isShaking = false;
				prevTimeWolfIsShaking = 0.0F;
				timeWolfIsShaking = 0.0F;
			}

			if (timeWolfIsShaking > 0.4F) {
				final float var1 = (float) getEntityBoundingBox().minY;
				final int var2 = (int) (MathHelper.sin((timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

				for (int var3 = 0; var3 < var2; ++var3) {
					final float var4 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
					final float var5 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
					worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + var4, var1 + 0.8F, posZ + var5,
							motionX, motionY, motionZ, new int[0]);
				}
			}
		}
	}

	/**
	 * True if the wolf is wet
	 */
	public boolean isWolfWet() {
		return isWet;
	}

	/**
	 * Used when calculating the amount of shading to apply while the wolf is
	 * wet.
	 */
	public float getShadingWhileWet(final float p_70915_1_) {
		return 0.75F
				+ (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
	}

	public float getShakeAngle(final float p_70923_1_, final float p_70923_2_) {
		float var3 = (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_)
				/ 1.8F;

		if (var3 < 0.0F) {
			var3 = 0.0F;
		} else if (var3 > 1.0F) {
			var3 = 1.0F;
		}

		return MathHelper.sin(var3 * (float) Math.PI) * MathHelper.sin(var3 * (float) Math.PI * 11.0F) * 0.15F
				* (float) Math.PI;
	}

	public float getInterestedAngle(final float p_70917_1_) {
		return (headRotationCourseOld + (headRotationCourse - headRotationCourseOld) * p_70917_1_) * 0.15F
				* (float) Math.PI;
	}

	@Override
	public float getEyeHeight() {
		return height * 0.8F;
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return isSitting() ? 20 : super.getVerticalFaceSpeed();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			final Entity var3 = source.getEntity();
			aiSit.setSitting(false);

			if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		final boolean var2 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this),
				(int) getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());

		if (var2) {
			func_174815_a(this, p_70652_1_);
		}

		return var2;
	}

	@Override
	public void setTamed(final boolean p_70903_1_) {
		super.setTamed(p_70903_1_);

		if (p_70903_1_) {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		}

		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (isTamed()) {
			if (var2 != null) {
				if (var2.getItem() instanceof ItemFood) {
					final ItemFood var3 = (ItemFood) var2.getItem();

					if (var3.isWolfsFavoriteMeat() && dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
						if (!p_70085_1_.capabilities.isCreativeMode) {
							--var2.stackSize;
						}

						heal(var3.getHealAmount(var2));

						if (var2.stackSize <= 0) {
							p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem,
									(ItemStack) null);
						}

						return true;
					}
				} else if (var2.getItem() == Items.dye) {
					final EnumDyeColor var4 = EnumDyeColor.func_176766_a(var2.getMetadata());

					if (var4 != func_175546_cu()) {
						func_175547_a(var4);

						if (!p_70085_1_.capabilities.isCreativeMode && --var2.stackSize <= 0) {
							p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem,
									(ItemStack) null);
						}

						return true;
					}
				}
			}

			if (func_152114_e(p_70085_1_) && !worldObj.isRemote && !isBreedingItem(var2)) {
				aiSit.setSitting(!isSitting());
				isJumping = false;
				navigator.clearPathEntity();
				setAttackTarget((EntityLivingBase) null);
			}
		} else if (var2 != null && var2.getItem() == Items.bone && !isAngry()) {
			if (!p_70085_1_.capabilities.isCreativeMode) {
				--var2.stackSize;
			}

			if (var2.stackSize <= 0) {
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
			}

			if (!worldObj.isRemote) {
				if (rand.nextInt(3) == 0) {
					setTamed(true);
					navigator.clearPathEntity();
					setAttackTarget((EntityLivingBase) null);
					aiSit.setSitting(true);
					setHealth(20.0F);
					func_152115_b(p_70085_1_.getUniqueID().toString());
					playTameEffect(true);
					worldObj.setEntityState(this, (byte) 7);
				} else {
					playTameEffect(false);
					worldObj.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.interact(p_70085_1_);
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 8) {
			isShaking = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public float getTailRotation() {
		return isAngry() ? 1.5393804F
				: isTamed() ? (0.55F - (20.0F - dataWatcher.getWatchableObjectFloat(18)) * 0.02F) * (float) Math.PI
						: (float) Math.PI / 5F;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(final ItemStack p_70877_1_) {
		return p_70877_1_ == null ? false
				: !(p_70877_1_.getItem() instanceof ItemFood) ? false
						: ((ItemFood) p_70877_1_.getItem()).isWolfsFavoriteMeat();
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk() {
		return 8;
	}

	/**
	 * Determines whether this wolf is angry or not.
	 */
	public boolean isAngry() {
		return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}

	/**
	 * Sets whether this wolf is angry or not.
	 */
	public void setAngry(final boolean p_70916_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70916_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 2));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -3));
		}
	}

	public EnumDyeColor func_175546_cu() {
		return EnumDyeColor.func_176766_a(dataWatcher.getWatchableObjectByte(20) & 15);
	}

	public void func_175547_a(final EnumDyeColor p_175547_1_) {
		dataWatcher.updateObject(20, (byte) (p_175547_1_.getDyeColorDamage() & 15));
	}

	@Override
	public EntityWolf createChild(final EntityAgeable p_90011_1_) {
		final EntityWolf var2 = new EntityWolf(worldObj);
		final String var3 = func_152113_b();

		if (var3 != null && var3.trim().length() > 0) {
			var2.func_152115_b(var3);
			var2.setTamed(true);
		}

		return var2;
	}

	public void func_70918_i(final boolean p_70918_1_) {
		if (p_70918_1_) {
			dataWatcher.updateObject(19, (byte) 1);
		} else {
			dataWatcher.updateObject(19, (byte) 0);
		}
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(final EntityAnimal p_70878_1_) {
		if (p_70878_1_ == this) {
			return false;
		} else if (!isTamed()) {
			return false;
		} else if (!(p_70878_1_ instanceof EntityWolf)) {
			return false;
		} else {
			final EntityWolf var2 = (EntityWolf) p_70878_1_;
			return !var2.isTamed() ? false : var2.isSitting() ? false : isInLove() && var2.isInLove();
		}
	}

	public boolean func_70922_bv() {
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !isTamed() && ticksExisted > 2400;
	}

	@Override
	public boolean func_142018_a(final EntityLivingBase p_142018_1_, final EntityLivingBase p_142018_2_) {
		if (!(p_142018_1_ instanceof EntityCreeper) && !(p_142018_1_ instanceof EntityGhast)) {
			if (p_142018_1_ instanceof EntityWolf) {
				final EntityWolf var3 = (EntityWolf) p_142018_1_;

				if (var3.isTamed() && var3.func_180492_cm() == p_142018_2_) {
					return false;
				}
			}

			return p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer
					&& !((EntityPlayer) p_142018_2_).canAttackPlayer((EntityPlayer) p_142018_1_) ? false
							: !(p_142018_1_ instanceof EntityHorse) || !((EntityHorse) p_142018_1_).isTame();
		} else {
			return false;
		}
	}

	@Override
	public boolean allowLeashing() {
		return !isAngry() && super.allowLeashing();
	}

}
