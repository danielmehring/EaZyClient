package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;

public class EntityHorse extends EntityAnimal implements IInvBasic {

public static final int EaZy = 1179;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Predicate horseBreedingSelector = new Predicate() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001642";
		public boolean func_179873_a(final Entity p_179873_1_) {
			return p_179873_1_ instanceof EntityHorse && ((EntityHorse) p_179873_1_).func_110205_ce();
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_179873_a((Entity) p_apply_1_);
		}
	};
	private static final IAttribute horseJumpStrength = new RangedAttribute((IAttribute) null, "horse.jumpStrength",
			0.7D, 0.0D, 2.0D).setDescription("Jump Strength").setShouldWatch(true);
	private static final String[] horseArmorTextures = new String[] { null,
			"textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png",
			"textures/entity/horse/armor/horse_armor_diamond.png" };
	private static final String[] field_110273_bx = new String[] { "", "meo", "goo", "dio" };
	private static final int[] armorValues = new int[] { 0, 5, 7, 11 };
	private static final String[] horseTextures = new String[] { "textures/entity/horse/horse_white.png",
			"textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png",
			"textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png",
			"textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
	private static final String[] field_110269_bA = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
	private static final String[] horseMarkingTextures = new String[] { null,
			"textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png",
			"textures/entity/horse/horse_markings_whitedots.png",
			"textures/entity/horse/horse_markings_blackdots.png" };
	private static final String[] field_110292_bC = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
	private int eatingHaystackCounter;
	private int openMouthCounter;
	private int jumpRearingCounter;
	public int field_110278_bp;
	public int field_110279_bq;
	protected boolean horseJumping;
	private AnimalChest horseChest;
	private boolean hasReproduced;

	/**
	 * "The higher this value, the more likely the horse is to be tamed next
	 * time a player rides it."
	 */
	protected int temper;
	protected float jumpPower;
	private boolean field_110294_bI;
	private float headLean;
	private float prevHeadLean;
	private float rearingAmount;
	private float prevRearingAmount;
	private float mouthOpenness;
	private float prevMouthOpenness;

	/** Used to determine the sound that the horse should make when it steps */
	private int gallopTime;
	private String field_110286_bQ;
	private final String[] field_110280_bR = new String[3];
	private boolean field_175508_bO = false;
	// private static final String __OBFID = "http://https://fuckuskid00001641";

	public EntityHorse(final World worldIn) {
		super(worldIn);
		setSize(1.4F, 1.6F);
		isImmuneToFire = false;
		setChested(false);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.2D));
		tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
		tasks.addTask(6, new EntityAIWander(this, 0.7D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		func_110226_cD();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(19, (byte) 0);
		dataWatcher.addObject(20, 0);
		dataWatcher.addObject(21, String.valueOf(""));
		dataWatcher.addObject(22, 0);
	}

	public void setHorseType(final int p_110214_1_) {
		dataWatcher.updateObject(19, (byte) p_110214_1_);
		func_110230_cF();
	}

	/**
	 * Returns the horse type. 0 = Normal, 1 = Donkey, 2 = Mule, 3 = Undead
	 * Horse, 4 = Skeleton Horse
	 */
	public int getHorseType() {
		return dataWatcher.getWatchableObjectByte(19);
	}

	public void setHorseVariant(final int p_110235_1_) {
		dataWatcher.updateObject(20, p_110235_1_);
		func_110230_cF();
	}

	public int getHorseVariant() {
		return dataWatcher.getWatchableObjectInt(20);
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		if (hasCustomName()) {
			return getCustomNameTag();
		} else {
			final int var1 = getHorseType();

			switch (var1) {
				case 0:
				default:
					return StatCollector.translateToLocal("entity.horse.name");

				case 1:
					return StatCollector.translateToLocal("entity.donkey.name");

				case 2:
					return StatCollector.translateToLocal("entity.mule.name");

				case 3:
					return StatCollector.translateToLocal("entity.zombiehorse.name");

				case 4:
					return StatCollector.translateToLocal("entity.skeletonhorse.name");
			}
		}
	}

	private boolean getHorseWatchableBoolean(final int p_110233_1_) {
		return (dataWatcher.getWatchableObjectInt(16) & p_110233_1_) != 0;
	}

	private void setHorseWatchableBoolean(final int p_110208_1_, final boolean p_110208_2_) {
		final int var3 = dataWatcher.getWatchableObjectInt(16);

		if (p_110208_2_) {
			dataWatcher.updateObject(16, var3 | p_110208_1_);
		} else {
			dataWatcher.updateObject(16, var3 & ~p_110208_1_);
		}
	}

	public boolean isAdultHorse() {
		return !isChild();
	}

	public boolean isTame() {
		return getHorseWatchableBoolean(2);
	}

	public boolean func_110253_bW() {
		return isAdultHorse();
	}

	public String func_152119_ch() {
		return dataWatcher.getWatchableObjectString(21);
	}

	public void func_152120_b(final String p_152120_1_) {
		dataWatcher.updateObject(21, p_152120_1_);
	}

	public float getHorseSize() {
		final int var1 = getGrowingAge();
		return var1 >= 0 ? 1.0F : 0.5F + (-24000 - var1) / -24000.0F * 0.5F;
	}

	/**
	 * "Sets the scale for an ageable entity according to the boolean parameter,
	 * which says if it's a child."
	 */
	@Override
	public void setScaleForAge(final boolean p_98054_1_) {
		if (p_98054_1_) {
			setScale(getHorseSize());
		} else {
			setScale(1.0F);
		}
	}

	public boolean isHorseJumping() {
		return horseJumping;
	}

	public void setHorseTamed(final boolean p_110234_1_) {
		setHorseWatchableBoolean(2, p_110234_1_);
	}

	public void setHorseJumping(final boolean p_110255_1_) {
		horseJumping = p_110255_1_;
	}

	@Override
	public boolean allowLeashing() {
		return !isUndead() && super.allowLeashing();
	}

	@Override
	protected void func_142017_o(final float p_142017_1_) {
		if (p_142017_1_ > 6.0F && isEatingHaystack()) {
			setEatingHaystack(false);
		}
	}

	public boolean isChested() {
		return getHorseWatchableBoolean(8);
	}

	public int func_110241_cb() {
		return dataWatcher.getWatchableObjectInt(22);
	}

	/**
	 * 0 = iron, 1 = gold, 2 = diamond
	 */
	private int getHorseArmorIndex(final ItemStack p_110260_1_) {
		if (p_110260_1_ == null) {
			return 0;
		} else {
			final Item var2 = p_110260_1_.getItem();
			return var2 == Items.iron_horse_armor ? 1
					: var2 == Items.golden_horse_armor ? 2 : var2 == Items.diamond_horse_armor ? 3 : 0;
		}
	}

	public boolean isEatingHaystack() {
		return getHorseWatchableBoolean(32);
	}

	public boolean isRearing() {
		return getHorseWatchableBoolean(64);
	}

	public boolean func_110205_ce() {
		return getHorseWatchableBoolean(16);
	}

	public boolean getHasReproduced() {
		return hasReproduced;
	}

	/**
	 * Set horse armor stack (for example: new
	 * ItemStack(Items.iron_horse_armor))
	 */
	public void setHorseArmorStack(final ItemStack p_146086_1_) {
		dataWatcher.updateObject(22, getHorseArmorIndex(p_146086_1_));
		func_110230_cF();
	}

	public void func_110242_l(final boolean p_110242_1_) {
		setHorseWatchableBoolean(16, p_110242_1_);
	}

	public void setChested(final boolean p_110207_1_) {
		setHorseWatchableBoolean(8, p_110207_1_);
	}

	public void setHasReproduced(final boolean p_110221_1_) {
		hasReproduced = p_110221_1_;
	}

	public void setHorseSaddled(final boolean p_110251_1_) {
		setHorseWatchableBoolean(4, p_110251_1_);
	}

	public int getTemper() {
		return temper;
	}

	public void setTemper(final int p_110238_1_) {
		temper = p_110238_1_;
	}

	public int increaseTemper(final int p_110198_1_) {
		final int var2 = MathHelper.clamp_int(getTemper() + p_110198_1_, 0, getMaxTemper());
		setTemper(var2);
		return var2;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		final Entity var3 = source.getEntity();
		return riddenByEntity != null && riddenByEntity.equals(var3) ? false : super.attackEntityFrom(source, amount);
	}

	/**
	 * Returns the current armor value as determined by a call to
	 * InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue() {
		return armorValues[func_110241_cb()];
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return riddenByEntity == null;
	}

	public boolean prepareChunkForSpawn() {
		final int var1 = MathHelper.floor_double(posX);
		final int var2 = MathHelper.floor_double(posZ);
		worldObj.getBiomeGenForCoords(new BlockPos(var1, 0, var2));
		return true;
	}

	public void dropChests() {
		if (!worldObj.isRemote && isChested()) {
			dropItem(Item.getItemFromBlock(Blocks.chest), 1);
			setChested(false);
		}
	}

	private void func_110266_cB() {
		openHorseMouth();

		if (!isSlient()) {
			worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
		}
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		if (distance > 1.0F) {
			playSound("mob.horse.land", 0.4F, 1.0F);
		}

		final int var3 = MathHelper.ceiling_float_int((distance * 0.5F - 3.0F) * damageMultiplier);

		if (var3 > 0) {
			attackEntityFrom(DamageSource.fall, var3);

			if (riddenByEntity != null) {
				riddenByEntity.attackEntityFrom(DamageSource.fall, var3);
			}

			final Block var4 = worldObj.getBlockState(new BlockPos(posX, posY - 0.2D - prevRotationYaw, posZ))
					.getBlock();

			if (var4.getMaterial() != Material.air && !isSlient()) {
				final Block.SoundType var5 = var4.stepSound;
				worldObj.playSoundAtEntity(this, var5.getStepSound(), var5.getVolume() * 0.5F,
						var5.getFrequency() * 0.75F);
			}
		}
	}

	private int func_110225_cC() {
		final int var1 = getHorseType();
		return isChested() && (var1 == 1 || var1 == 2) ? 17 : 2;
	}

	private void func_110226_cD() {
		final AnimalChest var1 = horseChest;
		horseChest = new AnimalChest("HorseChest", func_110225_cC());
		horseChest.func_110133_a(getName());

		if (var1 != null) {
			var1.func_110132_b(this);
			final int var2 = Math.min(var1.getSizeInventory(), horseChest.getSizeInventory());

			for (int var3 = 0; var3 < var2; ++var3) {
				final ItemStack var4 = var1.getStackInSlot(var3);

				if (var4 != null) {
					horseChest.setInventorySlotContents(var3, var4.copy());
				}
			}
		}

		horseChest.func_110134_a(this);
		func_110232_cE();
	}

	private void func_110232_cE() {
		if (!worldObj.isRemote) {
			setHorseSaddled(horseChest.getStackInSlot(0) != null);

			if (canWearArmor()) {
				setHorseArmorStack(horseChest.getStackInSlot(1));
			}
		}
	}

	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never
	 * filled.
	 */
	@Override
	public void onInventoryChanged(final InventoryBasic p_76316_1_) {
		final int var2 = func_110241_cb();
		final boolean var3 = isHorseSaddled();
		func_110232_cE();

		if (ticksExisted > 20) {
			if (var2 == 0 && var2 != func_110241_cb()) {
				playSound("mob.horse.armor", 0.5F, 1.0F);
			} else if (var2 != func_110241_cb()) {
				playSound("mob.horse.armor", 0.5F, 1.0F);
			}

			if (!var3 && isHorseSaddled()) {
				playSound("mob.horse.leather", 0.5F, 1.0F);
			}
		}
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		prepareChunkForSpawn();
		return super.getCanSpawnHere();
	}

	protected EntityHorse getClosestHorse(final Entity p_110250_1_, final double p_110250_2_) {
		double var4 = Double.MAX_VALUE;
		Entity var6 = null;
		final List var7 = worldObj.func_175674_a(p_110250_1_,
				p_110250_1_.getEntityBoundingBox().addCoord(p_110250_2_, p_110250_2_, p_110250_2_),
				horseBreedingSelector);
		final Iterator var8 = var7.iterator();

		while (var8.hasNext()) {
			final Entity var9 = (Entity) var8.next();
			final double var10 = var9.getDistanceSq(p_110250_1_.posX, p_110250_1_.posY, p_110250_1_.posZ);

			if (var10 < var4) {
				var6 = var9;
				var4 = var10;
			}
		}

		return (EntityHorse) var6;
	}

	public double getHorseJumpStrength() {
		return getEntityAttribute(horseJumpStrength).getAttributeValue();
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		openHorseMouth();
		final int var1 = getHorseType();
		return var1 == 3 ? "mob.horse.zombie.death"
				: var1 == 4 ? "mob.horse.skeleton.death"
						: var1 != 1 && var1 != 2 ? "mob.horse.death" : "mob.horse.donkey.death";
	}

	@Override
	protected Item getDropItem() {
		final boolean var1 = rand.nextInt(4) == 0;
		final int var2 = getHorseType();
		return var2 == 4 ? Items.bone : var2 == 3 ? var1 ? null : Items.rotten_flesh : Items.leather;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		openHorseMouth();

		if (rand.nextInt(3) == 0) {
			makeHorseRear();
		}

		final int var1 = getHorseType();
		return var1 == 3 ? "mob.horse.zombie.hit"
				: var1 == 4 ? "mob.horse.skeleton.hit"
						: var1 != 1 && var1 != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit";
	}

	public boolean isHorseSaddled() {
		return getHorseWatchableBoolean(4);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		openHorseMouth();

		if (rand.nextInt(10) == 0 && !isMovementBlocked()) {
			makeHorseRear();
		}

		final int var1 = getHorseType();
		return var1 == 3 ? "mob.horse.zombie.idle"
				: var1 == 4 ? "mob.horse.skeleton.idle"
						: var1 != 1 && var1 != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle";
	}

	protected String getAngrySoundName() {
		openHorseMouth();
		makeHorseRear();
		final int var1 = getHorseType();
		return var1 != 3 && var1 != 4 ? var1 != 1 && var1 != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry" : null;
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		Block.SoundType var3 = p_180429_2_.stepSound;

		if (worldObj.getBlockState(p_180429_1_.offsetUp()).getBlock() == Blocks.snow_layer) {
			var3 = Blocks.snow_layer.stepSound;
		}

		if (!p_180429_2_.getMaterial().isLiquid()) {
			final int var4 = getHorseType();

			if (riddenByEntity != null && var4 != 1 && var4 != 2) {
				++gallopTime;

				if (gallopTime > 5 && gallopTime % 3 == 0) {
					playSound("mob.horse.gallop", var3.getVolume() * 0.15F, var3.getFrequency());

					if (var4 == 0 && rand.nextInt(10) == 0) {
						playSound("mob.horse.breathe", var3.getVolume() * 0.6F, var3.getFrequency());
					}
				} else if (gallopTime <= 5) {
					playSound("mob.horse.wood", var3.getVolume() * 0.15F, var3.getFrequency());
				}
			} else if (var3 == Block.soundTypeWood) {
				playSound("mob.horse.wood", var3.getVolume() * 0.15F, var3.getFrequency());
			} else {
				playSound("mob.horse.soft", var3.getVolume() * 0.15F, var3.getFrequency());
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(horseJumpStrength);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22499999403953552D);
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	public int getMaxTemper() {
		return 100;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.8F;
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	@Override
	public int getTalkInterval() {
		return 400;
	}

	public boolean func_110239_cn() {
		return getHorseType() == 0 || func_110241_cb() > 0;
	}

	private void func_110230_cF() {
		field_110286_bQ = null;
	}

	public boolean func_175507_cI() {
		return field_175508_bO;
	}

	private void setHorseTexturePaths() {
		field_110286_bQ = "horse/";
		field_110280_bR[0] = null;
		field_110280_bR[1] = null;
		field_110280_bR[2] = null;
		final int var1 = getHorseType();
		final int var2 = getHorseVariant();
		int var3;

		if (var1 == 0) {
			var3 = var2 & 255;
			final int var4 = (var2 & 65280) >> 8;

			if (var3 >= horseTextures.length) {
				field_175508_bO = false;
				return;
			}

			field_110280_bR[0] = horseTextures[var3];
			field_110286_bQ = field_110286_bQ + field_110269_bA[var3];

			if (var4 >= horseMarkingTextures.length) {
				field_175508_bO = false;
				return;
			}

			field_110280_bR[1] = horseMarkingTextures[var4];
			field_110286_bQ = field_110286_bQ + field_110292_bC[var4];
		} else {
			field_110280_bR[0] = "";
			field_110286_bQ = field_110286_bQ + "_" + var1 + "_";
		}

		var3 = func_110241_cb();

		if (var3 >= horseArmorTextures.length) {
			field_175508_bO = false;
		} else {
			field_110280_bR[2] = horseArmorTextures[var3];
			field_110286_bQ = field_110286_bQ + field_110273_bx[var3];
			field_175508_bO = true;
		}
	}

	public String getHorseTexture() {
		if (field_110286_bQ == null) {
			setHorseTexturePaths();
		}

		return field_110286_bQ;
	}

	public String[] getVariantTexturePaths() {
		if (field_110286_bQ == null) {
			setHorseTexturePaths();
		}

		return field_110280_bR;
	}

	public void openGUI(final EntityPlayer p_110199_1_) {
		if (!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == p_110199_1_) && isTame()) {
			horseChest.func_110133_a(getName());
			p_110199_1_.displayGUIHorse(this, horseChest);
		}
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (var2 != null && var2.getItem() == Items.spawn_egg) {
			return super.interact(p_70085_1_);
		} else if (!isTame() && isUndead()) {
			return false;
		} else if (isTame() && isAdultHorse() && p_70085_1_.isSneaking()) {
			openGUI(p_70085_1_);
			return true;
		} else if (func_110253_bW() && riddenByEntity != null) {
			return super.interact(p_70085_1_);
		} else {
			if (var2 != null) {
				boolean var3 = false;

				if (canWearArmor()) {
					byte var4 = -1;

					if (var2.getItem() == Items.iron_horse_armor) {
						var4 = 1;
					} else if (var2.getItem() == Items.golden_horse_armor) {
						var4 = 2;
					} else if (var2.getItem() == Items.diamond_horse_armor) {
						var4 = 3;
					}

					if (var4 >= 0) {
						if (!isTame()) {
							makeHorseRearWithSound();
							return true;
						}

						openGUI(p_70085_1_);
						return true;
					}
				}

				if (!var3 && !isUndead()) {
					float var7 = 0.0F;
					short var5 = 0;
					byte var6 = 0;

					if (var2.getItem() == Items.wheat) {
						var7 = 2.0F;
						var5 = 20;
						var6 = 3;
					} else if (var2.getItem() == Items.sugar) {
						var7 = 1.0F;
						var5 = 30;
						var6 = 3;
					} else if (Block.getBlockFromItem(var2.getItem()) == Blocks.hay_block) {
						var7 = 20.0F;
						var5 = 180;
					} else if (var2.getItem() == Items.apple) {
						var7 = 3.0F;
						var5 = 60;
						var6 = 3;
					} else if (var2.getItem() == Items.golden_carrot) {
						var7 = 4.0F;
						var5 = 60;
						var6 = 5;

						if (isTame() && getGrowingAge() == 0) {
							var3 = true;
							setInLove(p_70085_1_);
						}
					} else if (var2.getItem() == Items.golden_apple) {
						var7 = 10.0F;
						var5 = 240;
						var6 = 10;

						if (isTame() && getGrowingAge() == 0) {
							var3 = true;
							setInLove(p_70085_1_);
						}
					}

					if (getHealth() < getMaxHealth() && var7 > 0.0F) {
						heal(var7);
						var3 = true;
					}

					if (!isAdultHorse() && var5 > 0) {
						addGrowth(var5);
						var3 = true;
					}

					if (var6 > 0 && (var3 || !isTame()) && var6 < getMaxTemper()) {
						var3 = true;
						increaseTemper(var6);
					}

					if (var3) {
						func_110266_cB();
					}
				}

				if (!isTame() && !var3) {
					if (var2 != null && var2.interactWithEntity(p_70085_1_, this)) {
						return true;
					}

					makeHorseRearWithSound();
					return true;
				}

				if (!var3 && canCarryChest() && !isChested() && var2.getItem() == Item.getItemFromBlock(Blocks.chest)) {
					setChested(true);
					playSound("mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
					var3 = true;
					func_110226_cD();
				}

				if (!var3 && func_110253_bW() && !isHorseSaddled() && var2.getItem() == Items.saddle) {
					openGUI(p_70085_1_);
					return true;
				}

				if (var3) {
					if (!p_70085_1_.capabilities.isCreativeMode && --var2.stackSize == 0) {
						p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem,
								(ItemStack) null);
					}

					return true;
				}
			}

			if (func_110253_bW() && riddenByEntity == null) {
				if (var2 != null && var2.interactWithEntity(p_70085_1_, this)) {
					return true;
				} else {
					func_110237_h(p_70085_1_);
					return true;
				}
			} else {
				return super.interact(p_70085_1_);
			}
		}
	}

	private void func_110237_h(final EntityPlayer p_110237_1_) {
		p_110237_1_.rotationYaw = rotationYaw;
		p_110237_1_.rotationPitch = rotationPitch;
		setEatingHaystack(false);
		setRearing(false);

		if (!worldObj.isRemote) {
			p_110237_1_.mountEntity(this);
		}
	}

	/**
	 * Return true if the horse entity can wear an armor
	 */
	public boolean canWearArmor() {
		return getHorseType() == 0;
	}

	/**
	 * Return true if the horse entity can carry a chest.
	 */
	public boolean canCarryChest() {
		final int var1 = getHorseType();
		return var1 == 2 || var1 == 1;
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isMovementBlocked() {
		return riddenByEntity != null && isHorseSaddled() ? true : isEatingHaystack() || isRearing();
	}

	/**
	 * Used to know if the horse can be leashed, if he can mate, or if we can
	 * interact with him
	 */
	public boolean isUndead() {
		final int var1 = getHorseType();
		return var1 == 3 || var1 == 4;
	}

	/**
	 * Return true if the horse entity is sterile (Undead || Mule)
	 */
	public boolean isSterile() {
		return isUndead() || getHorseType() == 2;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(final ItemStack p_70877_1_) {
		return false;
	}

	private void func_110210_cH() {
		field_110278_bp = 1;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		super.onDeath(cause);

		if (!worldObj.isRemote) {
			dropChestItems();
		}
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (rand.nextInt(200) == 0) {
			func_110210_cH();
		}

		super.onLivingUpdate();

		if (!worldObj.isRemote) {
			if (rand.nextInt(900) == 0 && deathTime == 0) {
				heal(1.0F);
			}

			if (!isEatingHaystack() && riddenByEntity == null && rand.nextInt(300) == 0
					&& worldObj
							.getBlockState(new BlockPos(MathHelper.floor_double(posX),
									MathHelper.floor_double(posY) - 1, MathHelper.floor_double(posZ)))
							.getBlock() == Blocks.grass) {
				setEatingHaystack(true);
			}

			if (isEatingHaystack() && ++eatingHaystackCounter > 50) {
				eatingHaystackCounter = 0;
				setEatingHaystack(false);
			}

			if (func_110205_ce() && !isAdultHorse() && !isEatingHaystack()) {
				final EntityHorse var1 = getClosestHorse(this, 16.0D);

				if (var1 != null && getDistanceSqToEntity(var1) > 4.0D) {
					navigator.getPathToEntityLiving(var1);
				}
			}
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (worldObj.isRemote && dataWatcher.hasObjectChanged()) {
			dataWatcher.func_111144_e();
			func_110230_cF();
		}

		if (openMouthCounter > 0 && ++openMouthCounter > 30) {
			openMouthCounter = 0;
			setHorseWatchableBoolean(128, false);
		}

		if (!worldObj.isRemote && jumpRearingCounter > 0 && ++jumpRearingCounter > 20) {
			jumpRearingCounter = 0;
			setRearing(false);
		}

		if (field_110278_bp > 0 && ++field_110278_bp > 8) {
			field_110278_bp = 0;
		}

		if (field_110279_bq > 0) {
			++field_110279_bq;

			if (field_110279_bq > 300) {
				field_110279_bq = 0;
			}
		}

		prevHeadLean = headLean;

		if (isEatingHaystack()) {
			headLean += (1.0F - headLean) * 0.4F + 0.05F;

			if (headLean > 1.0F) {
				headLean = 1.0F;
			}
		} else {
			headLean += (0.0F - headLean) * 0.4F - 0.05F;

			if (headLean < 0.0F) {
				headLean = 0.0F;
			}
		}

		prevRearingAmount = rearingAmount;

		if (isRearing()) {
			prevHeadLean = headLean = 0.0F;
			rearingAmount += (1.0F - rearingAmount) * 0.4F + 0.05F;

			if (rearingAmount > 1.0F) {
				rearingAmount = 1.0F;
			}
		} else {
			field_110294_bI = false;
			rearingAmount += (0.8F * rearingAmount * rearingAmount * rearingAmount - rearingAmount) * 0.6F - 0.05F;

			if (rearingAmount < 0.0F) {
				rearingAmount = 0.0F;
			}
		}

		prevMouthOpenness = mouthOpenness;

		if (getHorseWatchableBoolean(128)) {
			mouthOpenness += (1.0F - mouthOpenness) * 0.7F + 0.05F;

			if (mouthOpenness > 1.0F) {
				mouthOpenness = 1.0F;
			}
		} else {
			mouthOpenness += (0.0F - mouthOpenness) * 0.7F - 0.05F;

			if (mouthOpenness < 0.0F) {
				mouthOpenness = 0.0F;
			}
		}
	}

	private void openHorseMouth() {
		if (!worldObj.isRemote) {
			openMouthCounter = 1;
			setHorseWatchableBoolean(128, true);
		}
	}

	/**
	 * Return true if the horse entity ready to mate. (no rider, not riding,
	 * tame, adult, not steril...)
	 */
	private boolean canMate() {
		return riddenByEntity == null && ridingEntity == null && isTame() && isAdultHorse() && !isSterile()
				&& getHealth() >= getMaxHealth() && isInLove();
	}

	@Override
	public void setEating(final boolean eating) {
		setHorseWatchableBoolean(32, eating);
	}

	public void setEatingHaystack(final boolean p_110227_1_) {
		setEating(p_110227_1_);
	}

	public void setRearing(final boolean p_110219_1_) {
		if (p_110219_1_) {
			setEatingHaystack(false);
		}

		setHorseWatchableBoolean(64, p_110219_1_);
	}

	private void makeHorseRear() {
		if (!worldObj.isRemote) {
			jumpRearingCounter = 1;
			setRearing(true);
		}
	}

	public void makeHorseRearWithSound() {
		makeHorseRear();
		final String var1 = getAngrySoundName();

		if (var1 != null) {
			playSound(var1, getSoundVolume(), getSoundPitch());
		}
	}

	public void dropChestItems() {
		dropItemsInChest(this, horseChest);
		dropChests();
	}

	private void dropItemsInChest(final Entity p_110240_1_, final AnimalChest p_110240_2_) {
		if (p_110240_2_ != null && !worldObj.isRemote) {
			for (int var3 = 0; var3 < p_110240_2_.getSizeInventory(); ++var3) {
				final ItemStack var4 = p_110240_2_.getStackInSlot(var3);

				if (var4 != null) {
					entityDropItem(var4, 0.0F);
				}
			}
		}
	}

	public boolean setTamedBy(final EntityPlayer p_110263_1_) {
		func_152120_b(p_110263_1_.getUniqueID().toString());
		setHorseTamed(true);
		return true;
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
		if (riddenByEntity != null && riddenByEntity instanceof EntityLivingBase && isHorseSaddled()) {
			prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
			rotationPitch = riddenByEntity.rotationPitch * 0.5F;
			setRotation(rotationYaw, rotationPitch);
			rotationYawHead = renderYawOffset = rotationYaw;
			p_70612_1_ = ((EntityLivingBase) riddenByEntity).moveStrafing * 0.5F;
			p_70612_2_ = ((EntityLivingBase) riddenByEntity).moveForward;

			if (p_70612_2_ <= 0.0F) {
				p_70612_2_ *= 0.25F;
				gallopTime = 0;
			}

			if (onGround && jumpPower == 0.0F && isRearing() && !field_110294_bI) {
				p_70612_1_ = 0.0F;
				p_70612_2_ = 0.0F;
			}

			if (jumpPower > 0.0F && !isHorseJumping() && onGround) {
				motionY = getHorseJumpStrength() * jumpPower;

				if (this.isPotionActive(Potion.jump)) {
					motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
				}

				setHorseJumping(true);
				isAirBorne = true;

				if (p_70612_2_ > 0.0F) {
					final float var3 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
					final float var4 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);
					motionX += -0.4F * var3 * jumpPower;
					motionZ += 0.4F * var4 * jumpPower;
					playSound("mob.horse.jump", 0.4F, 1.0F);
				}

				jumpPower = 0.0F;
			}

			stepHeight = 1.0F;
			jumpMovementFactor = getAIMoveSpeed() * 0.1F;

			if (!worldObj.isRemote) {
				setAIMoveSpeed((float) getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

			if (onGround) {
				jumpPower = 0.0F;
				setHorseJumping(false);
			}

			prevLimbSwingAmount = limbSwingAmount;
			final double var8 = posX - prevPosX;
			final double var5 = posZ - prevPosZ;
			float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;

			if (var7 > 1.0F) {
				var7 = 1.0F;
			}

			limbSwingAmount += (var7 - limbSwingAmount) * 0.4F;
			limbSwing += limbSwingAmount;
		} else {
			stepHeight = 0.5F;
			jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("EatingHaystack", isEatingHaystack());
		tagCompound.setBoolean("ChestedHorse", isChested());
		tagCompound.setBoolean("HasReproduced", getHasReproduced());
		tagCompound.setBoolean("Bred", func_110205_ce());
		tagCompound.setInteger("Type", getHorseType());
		tagCompound.setInteger("Variant", getHorseVariant());
		tagCompound.setInteger("Temper", getTemper());
		tagCompound.setBoolean("Tame", isTame());
		tagCompound.setString("OwnerUUID", func_152119_ch());

		if (isChested()) {
			final NBTTagList var2 = new NBTTagList();

			for (int var3 = 2; var3 < horseChest.getSizeInventory(); ++var3) {
				final ItemStack var4 = horseChest.getStackInSlot(var3);

				if (var4 != null) {
					final NBTTagCompound var5 = new NBTTagCompound();
					var5.setByte("Slot", (byte) var3);
					var4.writeToNBT(var5);
					var2.appendTag(var5);
				}
			}

			tagCompound.setTag("Items", var2);
		}

		if (horseChest.getStackInSlot(1) != null) {
			tagCompound.setTag("ArmorItem", horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
		}

		if (horseChest.getStackInSlot(0) != null) {
			tagCompound.setTag("SaddleItem", horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setEatingHaystack(tagCompund.getBoolean("EatingHaystack"));
		func_110242_l(tagCompund.getBoolean("Bred"));
		setChested(tagCompund.getBoolean("ChestedHorse"));
		setHasReproduced(tagCompund.getBoolean("HasReproduced"));
		setHorseType(tagCompund.getInteger("Type"));
		setHorseVariant(tagCompund.getInteger("Variant"));
		setTemper(tagCompund.getInteger("Temper"));
		setHorseTamed(tagCompund.getBoolean("Tame"));
		String var2 = "";

		if (tagCompund.hasKey("OwnerUUID", 8)) {
			var2 = tagCompund.getString("OwnerUUID");
		} else {
			final String var3 = tagCompund.getString("Owner");
			var2 = PreYggdrasilConverter.func_152719_a(var3);
		}

		if (var2.length() > 0) {
			func_152120_b(var2);
		}

		final IAttributeInstance var8 = getAttributeMap().getAttributeInstanceByName("Speed");

		if (var8 != null) {
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var8.getBaseValue() * 0.25D);
		}

		if (isChested()) {
			final NBTTagList var4 = tagCompund.getTagList("Items", 10);
			func_110226_cD();

			for (int var5 = 0; var5 < var4.tagCount(); ++var5) {
				final NBTTagCompound var6 = var4.getCompoundTagAt(var5);
				final int var7 = var6.getByte("Slot") & 255;

				if (var7 >= 2 && var7 < horseChest.getSizeInventory()) {
					horseChest.setInventorySlotContents(var7, ItemStack.loadItemStackFromNBT(var6));
				}
			}
		}

		ItemStack var9;

		if (tagCompund.hasKey("ArmorItem", 10)) {
			var9 = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("ArmorItem"));

			if (var9 != null && func_146085_a(var9.getItem())) {
				horseChest.setInventorySlotContents(1, var9);
			}
		}

		if (tagCompund.hasKey("SaddleItem", 10)) {
			var9 = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("SaddleItem"));

			if (var9 != null && var9.getItem() == Items.saddle) {
				horseChest.setInventorySlotContents(0, var9);
			}
		} else if (tagCompund.getBoolean("Saddle")) {
			horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
		}

		func_110232_cE();
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(final EntityAnimal p_70878_1_) {
		if (p_70878_1_ == this) {
			return false;
		} else if (p_70878_1_.getClass() != this.getClass()) {
			return false;
		} else {
			final EntityHorse var2 = (EntityHorse) p_70878_1_;

			if (canMate() && var2.canMate()) {
				final int var3 = getHorseType();
				final int var4 = var2.getHorseType();
				return var3 == var4 || var3 == 0 && var4 == 1 || var3 == 1 && var4 == 0;
			} else {
				return false;
			}
		}
	}

	@Override
	public EntityAgeable createChild(final EntityAgeable p_90011_1_) {
		final EntityHorse var2 = (EntityHorse) p_90011_1_;
		final EntityHorse var3 = new EntityHorse(worldObj);
		final int var4 = getHorseType();
		final int var5 = var2.getHorseType();
		int var6 = 0;

		if (var4 == var5) {
			var6 = var4;
		} else if (var4 == 0 && var5 == 1 || var4 == 1 && var5 == 0) {
			var6 = 2;
		}

		if (var6 == 0) {
			final int var8 = rand.nextInt(9);
			int var7;

			if (var8 < 4) {
				var7 = getHorseVariant() & 255;
			} else if (var8 < 8) {
				var7 = var2.getHorseVariant() & 255;
			} else {
				var7 = rand.nextInt(7);
			}

			final int var9 = rand.nextInt(5);

			if (var9 < 2) {
				var7 |= getHorseVariant() & 65280;
			} else if (var9 < 4) {
				var7 |= var2.getHorseVariant() & 65280;
			} else {
				var7 |= rand.nextInt(5) << 8 & 65280;
			}

			var3.setHorseVariant(var7);
		}

		var3.setHorseType(var6);
		final double var13 = getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()
				+ p_90011_1_.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() + func_110267_cL();
		var3.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(var13 / 3.0D);
		final double var14 = getEntityAttribute(horseJumpStrength).getBaseValue()
				+ p_90011_1_.getEntityAttribute(horseJumpStrength).getBaseValue() + func_110245_cM();
		var3.getEntityAttribute(horseJumpStrength).setBaseValue(var14 / 3.0D);
		final double var11 = getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()
				+ p_90011_1_.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()
				+ func_110203_cN();
		var3.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(var11 / 3.0D);
		return var3;
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
		Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);
		int var4 = 0;
		int var8;

		if (p_180482_2_1 instanceof EntityHorse.GroupData) {
			var8 = ((EntityHorse.GroupData) p_180482_2_1).field_111107_a;
			var4 = ((EntityHorse.GroupData) p_180482_2_1).field_111106_b & 255 | rand.nextInt(5) << 8;
		} else {
			if (rand.nextInt(10) == 0) {
				var8 = 1;
			} else {
				final int var5 = rand.nextInt(7);
				final int var6 = rand.nextInt(5);
				var8 = 0;
				var4 = var5 | var6 << 8;
			}

			p_180482_2_1 = new EntityHorse.GroupData(var8, var4);
		}

		setHorseType(var8);
		setHorseVariant(var4);

		if (rand.nextInt(5) == 0) {
			setGrowingAge(-24000);
		}

		if (var8 != 4 && var8 != 3) {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(func_110267_cL());

			if (var8 == 0) {
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(func_110203_cN());
			} else {
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.17499999701976776D);
			}
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
		}

		if (var8 != 2 && var8 != 1) {
			getEntityAttribute(horseJumpStrength).setBaseValue(func_110245_cM());
		} else {
			getEntityAttribute(horseJumpStrength).setBaseValue(0.5D);
		}

		setHealth(getMaxHealth());
		return (IEntityLivingData) p_180482_2_1;
	}

	public float getGrassEatingAmount(final float p_110258_1_) {
		return prevHeadLean + (headLean - prevHeadLean) * p_110258_1_;
	}

	public float getRearingAmount(final float p_110223_1_) {
		return prevRearingAmount + (rearingAmount - prevRearingAmount) * p_110223_1_;
	}

	public float func_110201_q(final float p_110201_1_) {
		return prevMouthOpenness + (mouthOpenness - prevMouthOpenness) * p_110201_1_;
	}

	public void setJumpPower(int p_110206_1_) {
		if (isHorseSaddled()) {
			if (p_110206_1_ < 0) {
				p_110206_1_ = 0;
			} else {
				field_110294_bI = true;
				makeHorseRear();
			}

			if (p_110206_1_ >= 90) {
				jumpPower = 1.0F;
			} else {
				jumpPower = 0.4F + 0.4F * p_110206_1_ / 90.0F;
			}
		}
	}

	/**
	 * "Spawns particles for the horse entity. par1 tells whether to spawn
	 * hearts. If it is false, it spawns smoke."
	 */
	protected void spawnHorseParticles(final boolean p_110216_1_) {
		final EnumParticleTypes var2 = p_110216_1_ ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;

		for (int var3 = 0; var3 < 7; ++var3) {
			final double var4 = rand.nextGaussian() * 0.02D;
			final double var6 = rand.nextGaussian() * 0.02D;
			final double var8 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(var2, posX + rand.nextFloat() * width * 2.0F - width,
					posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6,
					var8, new int[0]);
		}
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 7) {
			spawnHorseParticles(true);
		} else if (p_70103_1_ == 6) {
			spawnHorseParticles(false);
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	@Override
	public void updateRiderPosition() {
		super.updateRiderPosition();

		if (prevRearingAmount > 0.0F) {
			final float var1 = MathHelper.sin(renderYawOffset * (float) Math.PI / 180.0F);
			final float var2 = MathHelper.cos(renderYawOffset * (float) Math.PI / 180.0F);
			final float var3 = 0.7F * prevRearingAmount;
			final float var4 = 0.15F * prevRearingAmount;
			riddenByEntity.setPosition(posX + var3 * var1,
					posY + getMountedYOffset() + riddenByEntity.getYOffset() + var4, posZ - var3 * var2);

			if (riddenByEntity instanceof EntityLivingBase) {
				((EntityLivingBase) riddenByEntity).renderYawOffset = renderYawOffset;
			}
		}
	}

	private float func_110267_cL() {
		return 15.0F + rand.nextInt(8) + rand.nextInt(9);
	}

	private double func_110245_cM() {
		return 0.4000000059604645D + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D;
	}

	private double func_110203_cN() {
		return (0.44999998807907104D + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D)
				* 0.25D;
	}

	public static boolean func_146085_a(final Item p_146085_0_) {
		return p_146085_0_ == Items.iron_horse_armor || p_146085_0_ == Items.golden_horse_armor
				|| p_146085_0_ == Items.diamond_horse_armor;
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder() {
		return false;
	}

	@Override
	public float getEyeHeight() {
		return height;
	}

	@Override
	public boolean func_174820_d(final int p_174820_1_, final ItemStack p_174820_2_) {
		if (p_174820_1_ == 499 && canCarryChest()) {
			if (p_174820_2_ == null && isChested()) {
				setChested(false);
				func_110226_cD();
				return true;
			}

			if (p_174820_2_ != null && p_174820_2_.getItem() == Item.getItemFromBlock(Blocks.chest) && !isChested()) {
				setChested(true);
				func_110226_cD();
				return true;
			}
		}

		final int var3 = p_174820_1_ - 400;

		if (var3 >= 0 && var3 < 2 && var3 < horseChest.getSizeInventory()) {
			if (var3 == 0 && p_174820_2_ != null && p_174820_2_.getItem() != Items.saddle) {
				return false;
			} else if (var3 == 1 && (p_174820_2_ != null && !func_146085_a(p_174820_2_.getItem()) || !canWearArmor())) {
				return false;
			} else {
				horseChest.setInventorySlotContents(var3, p_174820_2_);
				func_110232_cE();
				return true;
			}
		} else {
			final int var4 = p_174820_1_ - 500 + 2;

			if (var4 >= 2 && var4 < horseChest.getSizeInventory()) {
				horseChest.setInventorySlotContents(var4, p_174820_2_);
				return true;
			} else {
				return false;
			}
		}
	}

	public static class GroupData implements IEntityLivingData {
		public int field_111107_a;
		public int field_111106_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001643";

		public GroupData(final int p_i1684_1_, final int p_i1684_2_) {
			field_111107_a = p_i1684_1_;
			field_111106_b = p_i1684_2_;
		}
	}
}
