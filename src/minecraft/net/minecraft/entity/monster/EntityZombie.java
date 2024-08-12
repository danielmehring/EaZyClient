package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityZombie extends EntityMob {

public static final int EaZy = 1170;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected static final IAttribute field_110186_bp = new RangedAttribute((IAttribute) null,
			"zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).setDescription("Spawn Reinforcements Chance");
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID,
			"Baby speed boost", 0.5D, 1);
	private final EntityAIBreakDoor field_146075_bs = new EntityAIBreakDoor(this);

	/**
	 * Ticker used to determine the time remaining for this zombie to convert
	 * into a villager when cured.
	 */
	private int conversionTime;
	private boolean field_146076_bu = false;
	private float field_146074_bv = -1.0F;
	private float field_146073_bw;
	// private static final String __OBFID = "http://https://fuckuskid00001702";

	public EntityZombie(final World worldIn) {
		super(worldIn);
		((PathNavigateGround) getNavigator()).func_179688_b(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(2, field_175455_a);
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		func_175456_n();
		setSize(0.6F, 1.95F);
	}

	protected void func_175456_n() {
		tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0D, true));
		tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
		getAttributeMap().registerAttribute(field_110186_bp).setBaseValue(rand.nextDouble() * 0.10000000149011612D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(12, (byte) 0);
		getDataWatcher().addObject(13, (byte) 0);
		getDataWatcher().addObject(14, (byte) 0);
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue() {
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20) {
			var1 = 20;
		}

		return var1;
	}

	public boolean func_146072_bX() {
		return field_146076_bu;
	}

	public void func_146070_a(final boolean p_146070_1_) {
		if (field_146076_bu != p_146070_1_) {
			field_146076_bu = p_146070_1_;

			if (p_146070_1_) {
				tasks.addTask(1, field_146075_bs);
			} else {
				tasks.removeTask(field_146075_bs);
			}
		}
	}

	/**
	 * If Animal, checks if the age timer is negative
	 */
	@Override
	public boolean isChild() {
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(final EntityPlayer p_70693_1_) {
		if (isChild()) {
			experienceValue = (int) (experienceValue * 2.5F);
		}

		return super.getExperiencePoints(p_70693_1_);
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(final boolean p_82227_1_) {
		getDataWatcher().updateObject(12, (byte) (p_82227_1_ ? 1 : 0));

		if (worldObj != null && !worldObj.isRemote) {
			final IAttributeInstance var2 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			var2.removeModifier(babySpeedBoostModifier);

			if (p_82227_1_) {
				var2.applyModifier(babySpeedBoostModifier);
			}
		}

		func_146071_k(p_82227_1_);
	}

	/**
	 * Return whether this zombie is a villager.
	 */
	public boolean isVillager() {
		return getDataWatcher().getWatchableObjectByte(13) == 1;
	}

	/**
	 * Set whether this zombie is a villager.
	 */
	public void setVillager(final boolean p_82229_1_) {
		getDataWatcher().updateObject(13, (byte) (p_82229_1_ ? 1 : 0));
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (worldObj.isDaytime() && !worldObj.isRemote && !isChild()) {
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

		if (isRiding() && getAttackTarget() != null && ridingEntity instanceof EntityChicken) {
			((EntityLiving) ridingEntity).getNavigator().setPath(getNavigator().getPath(), 1.5D);
		}

		super.onLivingUpdate();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (super.attackEntityFrom(source, amount)) {
			EntityLivingBase var3 = getAttackTarget();

			if (var3 == null && source.getEntity() instanceof EntityLivingBase) {
				var3 = (EntityLivingBase) source.getEntity();
			}

			if (var3 != null && worldObj.getDifficulty() == EnumDifficulty.HARD
					&& rand.nextFloat() < getEntityAttribute(field_110186_bp).getAttributeValue()) {
				final int var4 = MathHelper.floor_double(posX);
				final int var5 = MathHelper.floor_double(posY);
				final int var6 = MathHelper.floor_double(posZ);
				final EntityZombie var7 = new EntityZombie(worldObj);

				for (int var8 = 0; var8 < 50; ++var8) {
					final int var9 = var4 + MathHelper.getRandomIntegerInRange(rand, 7, 40)
							* MathHelper.getRandomIntegerInRange(rand, -1, 1);
					final int var10 = var5 + MathHelper.getRandomIntegerInRange(rand, 7, 40)
							* MathHelper.getRandomIntegerInRange(rand, -1, 1);
					final int var11 = var6 + MathHelper.getRandomIntegerInRange(rand, 7, 40)
							* MathHelper.getRandomIntegerInRange(rand, -1, 1);

					if (World.doesBlockHaveSolidTopSurface(worldObj, new BlockPos(var9, var10 - 1, var11))
							&& worldObj.getLightFromNeighbors(new BlockPos(var9, var10, var11)) < 10) {
						var7.setPosition(var9, var10, var11);

						if (!worldObj.func_175636_b(var9, var10, var11, 7.0D)
								&& worldObj.checkNoEntityCollision(var7.getEntityBoundingBox(), var7)
								&& worldObj.getCollidingBoundingBoxes(var7, var7.getEntityBoundingBox()).isEmpty()
								&& !worldObj.isAnyLiquid(var7.getEntityBoundingBox())) {
							worldObj.spawnEntityInWorld(var7);
							var7.setAttackTarget(var3);
							var7.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var7)),
									(IEntityLivingData) null);
							getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier(
									"Zombie reinforcement caller charge", -0.05000000074505806D, 0));
							var7.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier(
									"Zombie reinforcement callee charge", -0.05000000074505806D, 0));
							break;
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (!worldObj.isRemote && isConverting()) {
			final int var1 = getConversionTimeBoost();
			conversionTime -= var1;

			if (conversionTime <= 0) {
				convertToVillager();
			}
		}

		super.onUpdate();
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		final boolean var2 = super.attackEntityAsMob(p_70652_1_);

		if (var2) {
			final int var3 = worldObj.getDifficulty().getDifficultyId();

			if (getHeldItem() == null && isBurning() && rand.nextFloat() < var3 * 0.3F) {
				p_70652_1_.setFire(2 * var3);
			}
		}

		return var2;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.zombie.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem() {
		return Items.rotten_flesh;
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	@Override
	protected void addRandomArmor() {
		switch (rand.nextInt(3)) {
			case 0:
				dropItem(Items.iron_ingot, 1);
				break;

			case 1:
				dropItem(Items.carrot, 1);
				break;

			case 2:
				dropItem(Items.potato, 1);
		}
	}

	@Override
	protected void func_180481_a(final DifficultyInstance p_180481_1_) {
		super.func_180481_a(p_180481_1_);

		if (rand.nextFloat() < (worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
			final int var2 = rand.nextInt(3);

			if (var2 == 0) {
				setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
			} else {
				setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		if (isChild()) {
			tagCompound.setBoolean("IsBaby", true);
		}

		if (isVillager()) {
			tagCompound.setBoolean("IsVillager", true);
		}

		tagCompound.setInteger("ConversionTime", isConverting() ? conversionTime : -1);
		tagCompound.setBoolean("CanBreakDoors", func_146072_bX());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.getBoolean("IsBaby")) {
			setChild(true);
		}

		if (tagCompund.getBoolean("IsVillager")) {
			setVillager(true);
		}

		if (tagCompund.hasKey("ConversionTime", 99) && tagCompund.getInteger("ConversionTime") > -1) {
			startConversion(tagCompund.getInteger("ConversionTime"));
		}

		func_146070_a(tagCompund.getBoolean("CanBreakDoors"));
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(final EntityLivingBase entityLivingIn) {
		super.onKillEntity(entityLivingIn);

		if ((worldObj.getDifficulty() == EnumDifficulty.NORMAL || worldObj.getDifficulty() == EnumDifficulty.HARD)
				&& entityLivingIn instanceof EntityVillager) {
			if (worldObj.getDifficulty() != EnumDifficulty.HARD && rand.nextBoolean()) {
				return;
			}

			final EntityZombie var2 = new EntityZombie(worldObj);
			var2.copyLocationAndAnglesFrom(entityLivingIn);
			worldObj.removeEntity(entityLivingIn);
			var2.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var2)), (IEntityLivingData) null);
			var2.setVillager(true);

			if (entityLivingIn.isChild()) {
				var2.setChild(true);
			}

			worldObj.spawnEntityInWorld(var2);
			worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, new BlockPos((int) posX, (int) posY, (int) posZ), 0);
		}
	}

	@Override
	public float getEyeHeight() {
		float var1 = 1.74F;

		if (isChild()) {
			var1 = (float) (var1 - 0.81D);
		}

		return var1;
	}

	@Override
	protected boolean func_175448_a(final ItemStack p_175448_1_) {
		return p_175448_1_.getItem() == Items.egg && isChild() && isRiding() ? false : super.func_175448_a(p_175448_1_);
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
		Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);
		final float var3 = p_180482_1_.func_180170_c();
		setCanPickUpLoot(rand.nextFloat() < 0.55F * var3);

		if (p_180482_2_1 == null) {
			p_180482_2_1 = new EntityZombie.GroupData(worldObj.rand.nextFloat() < 0.05F,
					worldObj.rand.nextFloat() < 0.05F, null);
		}

		if (p_180482_2_1 instanceof EntityZombie.GroupData) {
			final EntityZombie.GroupData var4 = (EntityZombie.GroupData) p_180482_2_1;

			if (var4.field_142046_b) {
				setVillager(true);
			}

			if (var4.field_142048_a) {
				setChild(true);

				if (worldObj.rand.nextFloat() < 0.05D) {
					final List var5 = worldObj.func_175647_a(EntityChicken.class,
							getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D), IEntitySelector.field_152785_b);

					if (!var5.isEmpty()) {
						final EntityChicken var6 = (EntityChicken) var5.get(0);
						var6.func_152117_i(true);
						mountEntity(var6);
					}
				} else if (worldObj.rand.nextFloat() < 0.05D) {
					final EntityChicken var10 = new EntityChicken(worldObj);
					var10.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					var10.func_180482_a(p_180482_1_, (IEntityLivingData) null);
					var10.func_152117_i(true);
					worldObj.spawnEntityInWorld(var10);
					mountEntity(var10);
				}
			}
		}

		func_146070_a(rand.nextFloat() < var3 * 0.1F);
		func_180481_a(p_180482_1_);
		func_180483_b(p_180482_1_);

		if (getEquipmentInSlot(4) == null) {
			final Calendar var8 = worldObj.getCurrentDate();

			if (var8.get(2) + 1 == 10 && var8.get(5) == 31 && rand.nextFloat() < 0.25F) {
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(
				new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.05000000074505806D, 0));
		final double var9 = rand.nextDouble() * 1.5D * var3;

		if (var9 > 1.0D) {
			getEntityAttribute(SharedMonsterAttributes.followRange)
					.applyModifier(new AttributeModifier("Random zombie-spawn bonus", var9, 2));
		}

		if (rand.nextFloat() < var3 * 0.05F) {
			getEntityAttribute(field_110186_bp)
					.applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 0.25D + 0.5D, 0));
			getEntityAttribute(SharedMonsterAttributes.maxHealth)
					.applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 3.0D + 1.0D, 2));
			func_146070_a(true);
		}

		return (IEntityLivingData) p_180482_2_1;
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.getCurrentEquippedItem();

		if (var2 != null && var2.getItem() == Items.golden_apple && var2.getMetadata() == 0 && isVillager()
				&& this.isPotionActive(Potion.weakness)) {
			if (!p_70085_1_.capabilities.isCreativeMode) {
				--var2.stackSize;
			}

			if (var2.stackSize <= 0) {
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
			}

			if (!worldObj.isRemote) {
				startConversion(rand.nextInt(2401) + 3600);
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Starts converting this zombie into a villager. The zombie converts into a
	 * villager after the specified time in ticks.
	 */
	protected void startConversion(final int p_82228_1_) {
		conversionTime = p_82228_1_;
		getDataWatcher().updateObject(14, (byte) 1);
		removePotionEffect(Potion.weakness.id);
		addPotionEffect(new PotionEffect(Potion.damageBoost.id, p_82228_1_,
				Math.min(worldObj.getDifficulty().getDifficultyId() - 1, 0)));
		worldObj.setEntityState(this, (byte) 16);
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 16) {
			if (!isSlient()) {
				worldObj.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "mob.zombie.remedy", 1.0F + rand.nextFloat(),
						rand.nextFloat() * 0.7F + 0.3F, false);
			}
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !isConverting();
	}

	/**
	 * Returns whether this zombie is in the process of converting to a villager
	 */
	public boolean isConverting() {
		return getDataWatcher().getWatchableObjectByte(14) == 1;
	}

	/**
	 * Convert this zombie into a villager.
	 */
	protected void convertToVillager() {
		final EntityVillager var1 = new EntityVillager(worldObj);
		var1.copyLocationAndAnglesFrom(this);
		var1.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var1)), (IEntityLivingData) null);
		var1.setLookingForHome();

		if (isChild()) {
			var1.setGrowingAge(-24000);
		}

		worldObj.removeEntity(this);
		worldObj.spawnEntityInWorld(var1);
		var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
		worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1017, new BlockPos((int) posX, (int) posY, (int) posZ), 0);
	}

	/**
	 * Return the amount of time decremented from conversionTime every tick.
	 */
	protected int getConversionTimeBoost() {
		int var1 = 1;

		if (rand.nextFloat() < 0.01F) {
			int var2 = 0;

			for (int var3 = (int) posX - 4; var3 < (int) posX + 4 && var2 < 14; ++var3) {
				for (int var4 = (int) posY - 4; var4 < (int) posY + 4 && var2 < 14; ++var4) {
					for (int var5 = (int) posZ - 4; var5 < (int) posZ + 4 && var2 < 14; ++var5) {
						final Block var6 = worldObj.getBlockState(new BlockPos(var3, var4, var5)).getBlock();

						if (var6 == Blocks.iron_bars || var6 == Blocks.bed) {
							if (rand.nextFloat() < 0.3F) {
								++var1;
							}

							++var2;
						}
					}
				}
			}
		}

		return var1;
	}

	public void func_146071_k(final boolean p_146071_1_) {
		func_146069_a(p_146071_1_ ? 0.5F : 1.0F);
	}

	/**
	 * Sets the width and height of the entity. Args: width, height
	 */
	@Override
	protected final void setSize(final float width, final float height) {
		final boolean var3 = field_146074_bv > 0.0F && field_146073_bw > 0.0F;
		field_146074_bv = width;
		field_146073_bw = height;

		if (!var3) {
			func_146069_a(1.0F);
		}
	}

	protected final void func_146069_a(final float p_146069_1_) {
		super.setSize(field_146074_bv * p_146069_1_, field_146073_bw * p_146069_1_);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getYOffset() {
		return super.getYOffset() - 0.5D;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		super.onDeath(cause);

		if (cause.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie)
				&& ((EntityCreeper) cause.getEntity()).getPowered()
				&& ((EntityCreeper) cause.getEntity()).isAIEnabled()) {
			((EntityCreeper) cause.getEntity()).func_175493_co();
			entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
		}
	}

	class GroupData implements IEntityLivingData {
		public boolean field_142048_a;
		public boolean field_142046_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001704";

		private GroupData(final boolean p_i2348_2_, final boolean p_i2348_3_) {
			field_142048_a = false;
			field_142046_b = false;
			field_142048_a = p_i2348_2_;
			field_142046_b = p_i2348_3_;
		}

		GroupData(final boolean p_i2349_2_, final boolean p_i2349_3_, final Object p_i2349_4_) {
			this(p_i2349_2_, p_i2349_3_);
		}
	}
}
