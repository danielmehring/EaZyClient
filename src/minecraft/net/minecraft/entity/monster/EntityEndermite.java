package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite extends EntityMob {

public static final int EaZy = 1155;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int lifetime = 0;
	private boolean playerSpawned = false;
	// private static final String __OBFID = "http://https://fuckuskid00002219";

	public EntityEndermite(final World worldIn) {
		super(worldIn);
		experienceValue = 3;
		setSize(0.4F, 0.3F);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(3, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	public float getEyeHeight() {
		return 0.1F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.silverfish.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.silverfish.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.silverfish.kill";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.silverfish.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		lifetime = tagCompund.getInteger("Lifetime");
		playerSpawned = tagCompund.getBoolean("PlayerSpawned");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Lifetime", lifetime);
		tagCompound.setBoolean("PlayerSpawned", playerSpawned);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		renderYawOffset = rotationYaw;
		super.onUpdate();
	}

	public boolean isSpawnedByPlayer() {
		return playerSpawned;
	}

	public void setSpawnedByPlayer(final boolean p_175496_1_) {
		playerSpawned = p_175496_1_;
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (worldObj.isRemote) {
			for (int var1 = 0; var1 < 2; ++var1) {
				worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * width,
						posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width,
						(rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D,
						new int[0]);
			}
		} else {
			if (!isNoDespawnRequired()) {
				++lifetime;
			}

			if (lifetime >= 2400) {
				setDead();
			}
		}
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			final EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 5.0D);
			return var1 == null;
		} else {
			return false;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}
}
