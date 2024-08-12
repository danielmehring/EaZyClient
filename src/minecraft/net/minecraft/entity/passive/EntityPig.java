package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityPig extends EntityAnimal {

public static final int EaZy = 1182;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** AI task for player control. */
	private final EntityAIControlledByPlayer aiControlledByPlayer;
	// private static final String __OBFID = "http://https://fuckuskid00001647";

	public EntityPig(final World worldIn) {
		super(worldIn);
		setSize(0.9F, 0.9F);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		tasks.addTask(2, aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.3F));
		tasks.addTask(3, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot_on_a_stick, false));
		tasks.addTask(4, new EntityAITempt(this, 1.2D, Items.carrot, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	 * returns true if all the conditions for steering the entity are met. For
	 * pigs, this is true if it is being ridden by a player and the player is
	 * holding a carrot-on-a-stick
	 */
	@Override
	public boolean canBeSteered() {
		final ItemStack var1 = ((EntityPlayer) riddenByEntity).getHeldItem();
		return var1 != null && var1.getItem() == Items.carrot_on_a_stick;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Saddle", getSaddled());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setSaddled(tagCompund.getBoolean("Saddle"));
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.pig.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.pig.say";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.pig.death";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.pig.step", 0.15F, 1.0F);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	public boolean interact(final EntityPlayer p_70085_1_) {
		if (super.interact(p_70085_1_)) {
			return true;
		} else if (getSaddled() && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == p_70085_1_)) {
			p_70085_1_.mountEntity(this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected Item getDropItem() {
		return isBurning() ? Items.cooked_porkchop : Items.porkchop;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + p_70628_2_);

		for (int var4 = 0; var4 < var3; ++var4) {
			if (isBurning()) {
				dropItem(Items.cooked_porkchop, 1);
			} else {
				dropItem(Items.porkchop, 1);
			}
		}

		if (getSaddled()) {
			dropItem(Items.saddle, 1);
		}
	}

	/**
	 * Returns true if the pig is saddled.
	 */
	public boolean getSaddled() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * Set or remove the saddle of the pig.
	 */
	public void setSaddled(final boolean p_70900_1_) {
		if (p_70900_1_) {
			dataWatcher.updateObject(16, (byte) 1);
		} else {
			dataWatcher.updateObject(16, (byte) 0);
		}
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
		if (!worldObj.isRemote) {
			final EntityPigZombie var2 = new EntityPigZombie(worldObj);
			var2.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
			var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			worldObj.spawnEntityInWorld(var2);
			setDead();
		}
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		super.fall(distance, damageMultiplier);

		if (distance > 5.0F && riddenByEntity instanceof EntityPlayer) {
			((EntityPlayer) riddenByEntity).triggerAchievement(AchievementList.flyPig);
		}
	}

	@Override
	public EntityPig createChild(final EntityAgeable p_90011_1_) {
		return new EntityPig(worldObj);
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(final ItemStack p_70877_1_) {
		return p_70877_1_ != null && p_70877_1_.getItem() == Items.carrot;
	}

	/**
	 * Return the AI task for player control.
	 */
	public EntityAIControlledByPlayer getAIControlledByPlayer() {
		return aiControlledByPlayer;
	}

}
