package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPigZombie extends EntityZombie {

public static final int EaZy = 1163;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier field_110190_br = new AttributeModifier(field_110189_bq,
			"Attacking speed boost", 0.05D, 0).setSaved(false);

	/** Above zero if this PigZombie is Angry. */
	private int angerLevel;

	/** A random delay until this PigZombie next makes a sound. */
	private int randomSoundDelay;
	private UUID field_175459_bn;
	// private static final String __OBFID = "http://https://fuckuskid00001693";

	public EntityPigZombie(final World worldIn) {
		super(worldIn);
		isImmuneToFire = true;
	}

	@Override
	public void setRevengeTarget(final EntityLivingBase p_70604_1_) {
		super.setRevengeTarget(p_70604_1_);

		if (p_70604_1_ != null) {
			field_175459_bn = p_70604_1_.getUniqueID();
		}
	}

	@Override
	protected void func_175456_n() {
		targetTasks.addTask(1, new EntityPigZombie.AIHurtByAggressor());
		targetTasks.addTask(2, new EntityPigZombie.AITargetAggressor());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(field_110186_bp).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	protected void updateAITasks() {
		final IAttributeInstance var1 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (func_175457_ck()) {
			if (!isChild() && !var1.func_180374_a(field_110190_br)) {
				var1.applyModifier(field_110190_br);
			}

			--angerLevel;
		} else if (var1.func_180374_a(field_110190_br)) {
			var1.removeModifier(field_110190_br);
		}

		if (randomSoundDelay > 0 && --randomSoundDelay == 0) {
			playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F,
					((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}

		if (angerLevel > 0 && field_175459_bn != null && getAITarget() == null) {
			final EntityPlayer var2 = worldObj.getPlayerEntityByUUID(field_175459_bn);
			setRevengeTarget(var2);
			attackingPlayer = var2;
			recentlyHit = getRevengeTimer();
		}

		super.updateAITasks();
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	/**
	 * Whether or not the current entity is in lava
	 */
	@Override
	public boolean handleLavaMovement() {
		return worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)
				&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()
				&& !worldObj.isAnyLiquid(getEntityBoundingBox());
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setShort("Anger", (short) angerLevel);

		if (field_175459_bn != null) {
			tagCompound.setString("HurtBy", field_175459_bn.toString());
		} else {
			tagCompound.setString("HurtBy", "");
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		angerLevel = tagCompund.getShort("Anger");
		final String var2 = tagCompund.getString("HurtBy");

		if (var2.length() > 0) {
			field_175459_bn = UUID.fromString(var2);
			final EntityPlayer var3 = worldObj.getPlayerEntityByUUID(field_175459_bn);
			setRevengeTarget(var3);

			if (var3 != null) {
				attackingPlayer = var3;
				recentlyHit = getRevengeTimer();
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			final Entity var3 = source.getEntity();

			if (var3 instanceof EntityPlayer) {
				becomeAngryAt(var3);
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	/**
	 * Causes this PigZombie to become angry at the supplied Entity (which will
	 * be a player).
	 */
	private void becomeAngryAt(final Entity p_70835_1_) {
		angerLevel = 400 + rand.nextInt(400);
		randomSoundDelay = rand.nextInt(40);

		if (p_70835_1_ instanceof EntityLivingBase) {
			setRevengeTarget((EntityLivingBase) p_70835_1_);
		}
	}

	public boolean func_175457_ck() {
		return angerLevel > 0;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.zombiepig.zpig";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.zombiepig.zpighurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.zombiepig.zpigdeath";
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		int var3 = rand.nextInt(2 + p_70628_2_);
		int var4;

		for (var4 = 0; var4 < var3; ++var4) {
			dropItem(Items.rotten_flesh, 1);
		}

		var3 = rand.nextInt(2 + p_70628_2_);

		for (var4 = 0; var4 < var3; ++var4) {
			dropItem(Items.gold_nugget, 1);
		}
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		return false;
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	@Override
	protected void addRandomArmor() {
		dropItem(Items.gold_ingot, 1);
	}

	@Override
	protected void func_180481_a(final DifficultyInstance p_180481_1_) {
		setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
		super.func_180482_a(p_180482_1_, p_180482_2_);
		setVillager(false);
		return p_180482_2_;
	}

	class AIHurtByAggressor extends EntityAIHurtByTarget {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002206";

		public AIHurtByAggressor() {
			super(EntityPigZombie.this, true, new Class[0]);
		}

		@Override
		protected void func_179446_a(final EntityCreature p_179446_1_, final EntityLivingBase p_179446_2_) {
			super.func_179446_a(p_179446_1_, p_179446_2_);

			if (p_179446_1_ instanceof EntityPigZombie) {
				((EntityPigZombie) p_179446_1_).becomeAngryAt(p_179446_2_);
			}
		}
	}

	class AITargetAggressor extends EntityAINearestAttackableTarget {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002207";

		public AITargetAggressor() {
			super(EntityPigZombie.this, EntityPlayer.class, true);
		}

		@Override
		public boolean shouldExecute() {
			return ((EntityPigZombie) taskOwner).func_175457_ck() && super.shouldExecute();
		}
	}
}
