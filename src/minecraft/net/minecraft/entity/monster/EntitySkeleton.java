package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

import java.util.Calendar;

import com.google.common.base.Predicate;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob {

public static final int EaZy = 1165;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private final EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class,
			1.2D, false);
	// private static final String __OBFID = "http://https://fuckuskid00001697";

	public EntitySkeleton(final World worldIn) {
		super(worldIn);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIRestrictSun(this));
		tasks.addTask(2, field_175455_a);
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002203";
			public boolean func_179945_a(final Entity p_179945_1_) {
				return p_179945_1_ instanceof EntityWolf;
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_179945_a((Entity) p_apply_1_);
			}
		}, 6.0F, 1.0D, 1.2D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));

		if (worldIn != null && !worldIn.isRemote) {
			setCombatTask();
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(13, (byte) 0);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.skeleton.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.skeleton.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.skeleton.death";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.skeleton.step", 0.15F, 1.0F);
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		if (super.attackEntityAsMob(p_70652_1_)) {
			if (getSkeletonType() == 1 && p_70652_1_ instanceof EntityLivingBase) {
				((EntityLivingBase) p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (worldObj.isDaytime() && !worldObj.isRemote) {
			final float var1 = getBrightness(1.0F);
			final BlockPos var2 = new BlockPos(posX, Math.round(posY), posZ);

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.isAgainstSky(var2)) {
				boolean var3 = true;
				final ItemStack var4 = getEquipmentInSlot(4);

				if (var4 != null) {
					if (var4.isItemStackDamageable()) {
						var4.setItemDamage(var4.getItemDamage() + rand.nextInt(2));

						if (var4.getItemDamage() >= var4.getMaxDamage()) {
							renderBrokenItemStack(var4);
							setCurrentItemOrArmor(4, (ItemStack) null);
						}
					}

					var3 = false;
				}

				if (var3) {
					setFire(8);
				}
			}
		}

		if (worldObj.isRemote && getSkeletonType() == 1) {
			setSize(0.72F, 2.535F);
		}

		super.onLivingUpdate();
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	@Override
	public void updateRidden() {
		super.updateRidden();

		if (ridingEntity instanceof EntityCreature) {
			final EntityCreature var1 = (EntityCreature) ridingEntity;
			renderYawOffset = var1.renderYawOffset;
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		super.onDeath(cause);

		if (cause.getSourceOfDamage() instanceof EntityArrow && cause.getEntity() instanceof EntityPlayer) {
			final EntityPlayer var2 = (EntityPlayer) cause.getEntity();
			final double var3 = var2.posX - posX;
			final double var5 = var2.posZ - posZ;

			if (var3 * var3 + var5 * var5 >= 2500.0D) {
				var2.triggerAchievement(AchievementList.snipeSkeleton);
			}
		} else if (cause.getEntity() instanceof EntityCreeper && ((EntityCreeper) cause.getEntity()).getPowered()
				&& ((EntityCreeper) cause.getEntity()).isAIEnabled()) {
			((EntityCreeper) cause.getEntity()).func_175493_co();
			entityDropItem(new ItemStack(Items.skull, 1, getSkeletonType() == 1 ? 1 : 0), 0.0F);
		}
	}

	@Override
	protected Item getDropItem() {
		return Items.arrow;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		int var3;
		int var4;

		if (getSkeletonType() == 1) {
			var3 = rand.nextInt(3 + p_70628_2_) - 1;

			for (var4 = 0; var4 < var3; ++var4) {
				dropItem(Items.coal, 1);
			}
		} else {
			var3 = rand.nextInt(3 + p_70628_2_);

			for (var4 = 0; var4 < var3; ++var4) {
				dropItem(Items.arrow, 1);
			}
		}

		var3 = rand.nextInt(3 + p_70628_2_);

		for (var4 = 0; var4 < var3; ++var4) {
			dropItem(Items.bone, 1);
		}
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	@Override
	protected void addRandomArmor() {
		if (getSkeletonType() == 1) {
			entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
		}
	}

	@Override
	protected void func_180481_a(final DifficultyInstance p_180481_1_) {
		super.func_180481_a(p_180481_1_);
		setCurrentItemOrArmor(0, new ItemStack(Items.bow));
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
		p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);

		if (worldObj.provider instanceof WorldProviderHell && getRNG().nextInt(5) > 0) {
			tasks.addTask(4, aiAttackOnCollide);
			setSkeletonType(1);
			setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		} else {
			tasks.addTask(4, aiArrowAttack);
			func_180481_a(p_180482_1_);
			func_180483_b(p_180482_1_);
		}

		setCanPickUpLoot(rand.nextFloat() < 0.55F * p_180482_1_.func_180170_c());

		if (getEquipmentInSlot(4) == null) {
			final Calendar var3 = worldObj.getCurrentDate();

			if (var3.get(2) + 1 == 10 && var3.get(5) == 31 && rand.nextFloat() < 0.25F) {
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		return p_180482_2_;
	}

	/**
	 * sets this entity's combat AI.
	 */
	public void setCombatTask() {
		tasks.removeTask(aiAttackOnCollide);
		tasks.removeTask(aiArrowAttack);
		final ItemStack var1 = getHeldItem();

		if (var1 != null && var1.getItem() == Items.bow) {
			tasks.addTask(4, aiArrowAttack);
		} else {
			tasks.addTask(4, aiAttackOnCollide);
		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
		final EntityArrow var3 = new EntityArrow(worldObj, this, p_82196_1_, 1.6F,
				14 - worldObj.getDifficulty().getDifficultyId() * 4);
		final int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		final int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		var3.setDamage(
				p_82196_2_ * 2.0F + rand.nextGaussian() * 0.25D + worldObj.getDifficulty().getDifficultyId() * 0.11F);

		if (var4 > 0) {
			var3.setDamage(var3.getDamage() + var4 * 0.5D + 0.5D);
		}

		if (var5 > 0) {
			var3.setKnockbackStrength(var5);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0
				|| getSkeletonType() == 1) {
			var3.setFire(100);
		}

		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(var3);
	}

	/**
	 * Return this skeleton's type.
	 */
	public int getSkeletonType() {
		return dataWatcher.getWatchableObjectByte(13);
	}

	/**
	 * Set this skeleton's type.
	 */
	public void setSkeletonType(final int p_82201_1_) {
		dataWatcher.updateObject(13, (byte) p_82201_1_);
		isImmuneToFire = p_82201_1_ == 1;

		if (p_82201_1_ == 1) {
			setSize(0.72F, 2.535F);
		} else {
			setSize(0.6F, 1.95F);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("SkeletonType", 99)) {
			final byte var2 = tagCompund.getByte("SkeletonType");
			setSkeletonType(var2);
		}

		setCombatTask();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setByte("SkeletonType", (byte) getSkeletonType());
	}

	/**
	 * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is
	 * armor. Params: Item, slot
	 */
	@Override
	public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {
		super.setCurrentItemOrArmor(slotIn, itemStackIn);

		if (!worldObj.isRemote && slotIn == 0) {
			setCombatTask();
		}
	}

	@Override
	public float getEyeHeight() {
		return getSkeletonType() == 1 ? super.getEyeHeight() : 1.74F;
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getYOffset() {
		return super.getYOffset() - 0.5D;
	}
}
