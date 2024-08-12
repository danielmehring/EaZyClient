package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityOcelot extends EntityTameable {

public static final int EaZy = 1181;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private EntityAIAvoidEntity field_175545_bm;

	/**
	 * The tempt AI task for this mob, used to prevent taming while it is
	 * fleeing.
	 */
	private EntityAITempt aiTempt;
	// private static final String __OBFID = "http://https://fuckuskid00001646";

	public EntityOcelot(final World worldIn) {
		super(worldIn);
		setSize(0.6F, 0.7F);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, aiTempt = new EntityAITempt(this, 0.6D, Items.fish, true));
		tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
		tasks.addTask(6, new EntityAIOcelotSit(this, 0.8D));
		tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		tasks.addTask(8, new EntityAIOcelotAttack(this));
		tasks.addTask(9, new EntityAIMate(this, 0.8D));
		tasks.addTask(10, new EntityAIWander(this, 0.8D));
		tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate) null));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, (byte) 0);
	}

	@Override
	public void updateAITasks() {
		if (getMoveHelper().isUpdating()) {
			final double var1 = getMoveHelper().getSpeed();

			if (var1 == 0.6D) {
				setSneaking(true);
				setSprinting(false);
			} else if (var1 == 1.33D) {
				setSneaking(false);
				setSprinting(true);
			} else {
				setSneaking(false);
				setSprinting(false);
			}
		} else {
			setSneaking(false);
			setSprinting(false);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !isTamed() && ticksExisted > 2400;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("CatType", getTameSkin());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setTameSkin(tagCompund.getInteger("CatType"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return isTamed() ? isInLove() ? "mob.cat.purr" : rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow" : "";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.cat.hitt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.cat.hitt";
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
		return Items.leather;
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			aiSit.setSitting(false);
			return super.attackEntityFrom(source, amount);
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (isTamed()) {
			if (func_152114_e(p_70085_1_) && !worldObj.isRemote && !isBreedingItem(var2)) {
				aiSit.setSitting(!isSitting());
			}
		} else if (aiTempt.isRunning() && var2 != null && var2.getItem() == Items.fish
				&& p_70085_1_.getDistanceSqToEntity(this) < 9.0D) {
			if (!p_70085_1_.capabilities.isCreativeMode) {
				--var2.stackSize;
			}

			if (var2.stackSize <= 0) {
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
			}

			if (!worldObj.isRemote) {
				if (rand.nextInt(3) == 0) {
					setTamed(true);
					setTameSkin(1 + worldObj.rand.nextInt(3));
					func_152115_b(p_70085_1_.getUniqueID().toString());
					playTameEffect(true);
					aiSit.setSitting(true);
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

	public EntityOcelot func_180493_b(final EntityAgeable p_180493_1_) {
		final EntityOcelot var2 = new EntityOcelot(worldObj);

		if (isTamed()) {
			var2.func_152115_b(func_152113_b());
			var2.setTamed(true);
			var2.setTameSkin(getTameSkin());
		}

		return var2;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(final ItemStack p_70877_1_) {
		return p_70877_1_ != null && p_70877_1_.getItem() == Items.fish;
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
		} else if (!(p_70878_1_ instanceof EntityOcelot)) {
			return false;
		} else {
			final EntityOcelot var2 = (EntityOcelot) p_70878_1_;
			return !var2.isTamed() ? false : isInLove() && var2.isInLove();
		}
	}

	public int getTameSkin() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public void setTameSkin(final int p_70912_1_) {
		dataWatcher.updateObject(18, (byte) p_70912_1_);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return worldObj.rand.nextInt(3) != 0;
	}

	/**
	 * Whether or not the current entity is in lava
	 */
	@Override
	public boolean handleLavaMovement() {
		if (worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)
				&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()
				&& !worldObj.isAnyLiquid(getEntityBoundingBox())) {
			final BlockPos var1 = new BlockPos(posX, getEntityBoundingBox().minY, posZ);

			if (var1.getY() < 63) {
				return false;
			}

			final Block var2 = worldObj.getBlockState(var1.offsetDown()).getBlock();

			if (var2 == Blocks.grass || var2.getMaterial() == Material.leaves) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return hasCustomName() ? getCustomNameTag()
				: isTamed() ? StatCollector.translateToLocal("entity.Cat.name") : super.getName();
	}

	@Override
	public void setTamed(final boolean p_70903_1_) {
		super.setTamed(p_70903_1_);
	}

	@Override
	protected void func_175544_ck() {
		if (field_175545_bm == null) {
			field_175545_bm = new EntityAIAvoidEntity(this, new Predicate() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002243";
				public boolean func_179874_a(final Entity p_179874_1_) {
					return p_179874_1_ instanceof EntityPlayer;
				}

				@Override
				public boolean apply(final Object p_apply_1_) {
					return func_179874_a((Entity) p_apply_1_);
				}
			}, 16.0F, 0.8D, 1.33D);
		}

		tasks.removeTask(field_175545_bm);

		if (!isTamed()) {
			tasks.addTask(4, field_175545_bm);
		}
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
		p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);

		if (worldObj.rand.nextInt(7) == 0) {
			for (int var3 = 0; var3 < 2; ++var3) {
				final EntityOcelot var4 = new EntityOcelot(worldObj);
				var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				var4.setGrowingAge(-24000);
				worldObj.spawnEntityInWorld(var4);
			}
		}

		return p_180482_2_;
	}

	@Override
	public EntityAgeable createChild(final EntityAgeable p_90011_1_) {
		return func_180493_b(p_90011_1_);
	}
}
