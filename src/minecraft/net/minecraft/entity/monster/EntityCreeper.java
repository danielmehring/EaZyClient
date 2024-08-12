package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityCreeper extends EntityMob {

public static final int EaZy = 1153;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Time when this creeper was last in an active state (Messed up code here,
	 * probably causes creeper animation to go weird)
	 */
	private int lastActiveTime;

	/**
	 * The amount of time since the creeper was close enough to the player to
	 * ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;

	/** Explosion radius for this creeper. */
	private int explosionRadius = 3;
	private int field_175494_bm = 0;
	// private static final String __OBFID = "http://https://fuckuskid00001684";

	public EntityCreeper(final World worldIn) {
		super(worldIn);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAICreeperSwell(this));
		tasks.addTask(2, field_175455_a);
		tasks.addTask(3, new EntityAIAvoidEntity(this, new Predicate() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002224";
			public boolean func_179958_a(final Entity p_179958_1_) {
				return p_179958_1_ instanceof EntityOcelot;
			}

			@Override
			public boolean apply(final Object p_apply_1_) {
				return func_179958_a((Entity) p_apply_1_);
			}
		}, 6.0F, 1.0D, 1.2D));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
		tasks.addTask(5, new EntityAIWander(this, 0.8D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	 * The maximum height from where the entity is alowed to jump (used in
	 * pathfinder)
	 */
	@Override
	public int getMaxFallHeight() {
		return getAttackTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		super.fall(distance, damageMultiplier);
		timeSinceIgnited = (int) (timeSinceIgnited + distance * 1.5F);

		if (timeSinceIgnited > fuseTime - 5) {
			timeSinceIgnited = fuseTime - 5;
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) -1);
		dataWatcher.addObject(17, (byte) 0);
		dataWatcher.addObject(18, (byte) 0);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);

		if (dataWatcher.getWatchableObjectByte(17) == 1) {
			tagCompound.setBoolean("powered", true);
		}

		tagCompound.setShort("Fuse", (short) fuseTime);
		tagCompound.setByte("ExplosionRadius", (byte) explosionRadius);
		tagCompound.setBoolean("ignited", func_146078_ca());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		dataWatcher.updateObject(17, (byte) (tagCompund.getBoolean("powered") ? 1 : 0));

		if (tagCompund.hasKey("Fuse", 99)) {
			fuseTime = tagCompund.getShort("Fuse");
		}

		if (tagCompund.hasKey("ExplosionRadius", 99)) {
			explosionRadius = tagCompund.getByte("ExplosionRadius");
		}

		if (tagCompund.getBoolean("ignited")) {
			func_146079_cb();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (isEntityAlive()) {
			lastActiveTime = timeSinceIgnited;

			if (func_146078_ca()) {
				setCreeperState(1);
			}

			final int var1 = getCreeperState();

			if (var1 > 0 && timeSinceIgnited == 0) {
				playSound("creeper.primed", 1.0F, 0.5F);
			}

			timeSinceIgnited += var1;

			if (timeSinceIgnited < 0) {
				timeSinceIgnited = 0;
			}

			if (timeSinceIgnited >= fuseTime) {
				timeSinceIgnited = fuseTime;
				func_146077_cc();
			}
		}

		super.onUpdate();
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.creeper.say";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.creeper.death";
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		super.onDeath(cause);

		if (cause.getEntity() instanceof EntitySkeleton) {
			final int var2 = Item.getIdFromItem(Items.record_13);
			final int var3 = Item.getIdFromItem(Items.record_wait);
			final int var4 = var2 + rand.nextInt(var3 - var2 + 1);
			dropItem(Item.getItemById(var4), 1);
		} else if (cause.getEntity() instanceof EntityCreeper && cause.getEntity() != this
				&& ((EntityCreeper) cause.getEntity()).getPowered()
				&& ((EntityCreeper) cause.getEntity()).isAIEnabled()) {
			((EntityCreeper) cause.getEntity()).func_175493_co();
			entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
		}
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		return true;
	}

	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean getPowered() {
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash
	 * when it is ignited.
	 */
	public float getCreeperFlashIntensity(final float p_70831_1_) {
		return (lastActiveTime + (timeSinceIgnited - lastActiveTime) * p_70831_1_) / (fuseTime - 2);
	}

	@Override
	protected Item getDropItem() {
		return Items.gunpowder;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState() {
		return dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(final int p_70829_1_) {
		dataWatcher.updateObject(16, (byte) p_70829_1_);
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(final EntityLightningBolt lightningBolt) {
		super.onStruckByLightning(lightningBolt);
		dataWatcher.updateObject(17, (byte) 1);
	}

	/**
	 * Called when a player interacts with a mob. e.g. gets milk from a cow,
	 * gets into the saddle on a pig.
	 */
	@Override
	protected boolean interact(final EntityPlayer p_70085_1_) {
		final ItemStack var2 = p_70085_1_.inventory.getCurrentItem();

		if (var2 != null && var2.getItem() == Items.flint_and_steel) {
			worldObj.playSoundEffect(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.ignite", 1.0F,
					rand.nextFloat() * 0.4F + 0.8F);
			p_70085_1_.swingItem();

			if (!worldObj.isRemote) {
				func_146079_cb();
				var2.damageItem(1, p_70085_1_);
				return true;
			}
		}

		return super.interact(p_70085_1_);
	}

	private void func_146077_cc() {
		if (!worldObj.isRemote) {
			final boolean var1 = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			final float var2 = getPowered() ? 2.0F : 1.0F;
			worldObj.createExplosion(this, posX, posY, posZ, explosionRadius * var2, var1);
			setDead();
		}
	}

	public boolean func_146078_ca() {
		return dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void func_146079_cb() {
		dataWatcher.updateObject(18, (byte) 1);
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return field_175494_bm < 1 && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot");
	}

	public void func_175493_co() {
		++field_175494_bm;
	}
}
