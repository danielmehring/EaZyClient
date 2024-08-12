package net.minecraft.entity.item;

import java.util.Iterator;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityItem extends Entity {

public static final int EaZy = 1139;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/**
	 * The age of this EntityItem (used to animate it up and down as well as
	 * expire it)
	 */
	private int age;
	private int delayBeforeCanPickup;

	/** The health of this EntityItem. (For example, damage for tools) */
	private int health;
	private String thrower;
	private String owner;

	/** The EntityItem's random initial float height. */
	public float hoverStart;

	public EntityItem(final World worldIn, final double x, final double y, final double z) {
		super(worldIn);
		health = 5;
		hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		setSize(0.25F, 0.25F);
		setPosition(x, y, z);
		rotationYaw = (float) (Math.random() * 360.0D);
		motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
		motionY = 0.20000000298023224D;
		motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
	}

	public EntityItem(final World worldIn, final double x, final double y, final double z, final ItemStack stack) {
		this(worldIn, x, y, z);
		setEntityItemStack(stack);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	public EntityItem(final World worldIn) {
		super(worldIn);
		health = 5;
		hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		setSize(0.25F, 0.25F);
		setEntityItemStack(new ItemStack(Blocks.air, 0));
	}

	@Override
	protected void entityInit() {
		getDataWatcher().addObjectByDataType(10, 5);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (getEntityItem() == null) {
			setDead();
		} else {
			super.onUpdate();

			if (delayBeforeCanPickup > 0 && delayBeforeCanPickup != 32767) {
				--delayBeforeCanPickup;
			}

			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			motionY -= 0.03999999910593033D;
			noClip = pushOutOfBlocks(posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D, posZ);
			moveEntity(motionX, motionY, motionZ);
			final boolean var1 = (int) prevPosX != (int) posX || (int) prevPosY != (int) posY
					|| (int) prevPosZ != (int) posZ;

			if (var1 || ticksExisted % 25 == 0) {
				if (worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
					motionY = 0.20000000298023224D;
					motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
					motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
					playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
				}

				if (!worldObj.isRemote) {
					searchForOtherItemsNearby();
				}
			}

			float var2 = 0.98F;

			if (onGround) {
				var2 = worldObj.getBlockState(new BlockPos(MathHelper.floor_double(posX),
						MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper.floor_double(posZ)))
						.getBlock().slipperiness * 0.98F;
			}

			motionX *= var2;
			motionY *= 0.9800000190734863D;
			motionZ *= var2;

			if (onGround) {
				motionY *= -0.5D;
			}

			if (age != -32768) {
				++age;
			}

			handleWaterMovement();

			if (!worldObj.isRemote && age >= 6000) {
				setDead();
			}
		}
	}

	/**
	 * Looks for other itemstacks nearby and tries to stack them together
	 */
	private void searchForOtherItemsNearby() {
		final Iterator var1 = worldObj
				.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D)).iterator();

		while (var1.hasNext()) {
			final EntityItem var2 = (EntityItem) var1.next();
			combineItems(var2);
		}
	}

	/**
	 * Tries to merge this item with the item passed as the parameter. Returns
	 * true if successful. Either this item or the other item will be removed
	 * from the world.
	 */
	private boolean combineItems(final EntityItem other) {
		if (other == this) {
			return false;
		} else if (other.isEntityAlive() && isEntityAlive()) {
			final ItemStack var2 = getEntityItem();
			final ItemStack var3 = other.getEntityItem();

			if (delayBeforeCanPickup != 32767 && other.delayBeforeCanPickup != 32767) {
				if (age != -32768 && other.age != -32768) {
					if (var3.getItem() != var2.getItem()) {
						return false;
					} else if (var3.hasTagCompound() ^ var2.hasTagCompound()) {
						return false;
					} else if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
						return false;
					} else if (var3.getItem() == null) {
						return false;
					} else if (var3.getItem().getHasSubtypes() && var3.getMetadata() != var2.getMetadata()) {
						return false;
					} else if (var3.stackSize < var2.stackSize) {
						return other.combineItems(this);
					} else if (var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
						return false;
					} else {
						var3.stackSize += var2.stackSize;
						other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, delayBeforeCanPickup);
						other.age = Math.min(other.age, age);
						other.setEntityItemStack(var3);
						setDead();
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * sets the age of the item so that it'll despawn one minute after it has
	 * been dropped (instead of five). Used when items are dropped from players
	 * in creative mode
	 */
	public void setAgeToCreativeDespawnTime() {
		age = 4800;
	}

	/**
	 * Returns if this entity is in water and will end up adding the waters
	 * velocity to the entity
	 */
	@Override
	public boolean handleWaterMovement() {
		if (worldObj.handleMaterialAcceleration(getEntityBoundingBox(), Material.water, this)) {
			if (!inWater && !firstUpdate) {
				resetHeight();
			}

			inWater = true;
		} else {
			inWater = false;
		}

		return inWater;
	}

	/**
	 * Will deal the specified amount of damage to the entity if the entity
	 * isn't immune to fire damage. Args: amountDamage
	 */
	@Override
	protected void dealFireDamage(final int amount) {
		attackEntityFrom(DamageSource.inFire, amount);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else if (getEntityItem() != null && getEntityItem().getItem() == Items.nether_star && source.isExplosion()) {
			return false;
		} else {
			setBeenAttacked();
			health = (int) (health - amount);

			if (health <= 0) {
				setDead();
			}

			return false;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("Health", (byte) health);
		tagCompound.setShort("Age", (short) age);
		tagCompound.setShort("PickupDelay", (short) delayBeforeCanPickup);

		if (getThrower() != null) {
			tagCompound.setString("Thrower", thrower);
		}

		if (getOwner() != null) {
			tagCompound.setString("Owner", owner);
		}

		if (getEntityItem() != null) {
			tagCompound.setTag("Item", getEntityItem().writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		health = tagCompund.getShort("Health") & 255;
		age = tagCompund.getShort("Age");

		if (tagCompund.hasKey("PickupDelay")) {
			delayBeforeCanPickup = tagCompund.getShort("PickupDelay");
		}

		if (tagCompund.hasKey("Owner")) {
			owner = tagCompund.getString("Owner");
		}

		if (tagCompund.hasKey("Thrower")) {
			thrower = tagCompund.getString("Thrower");
		}

		final NBTTagCompound var2 = tagCompund.getCompoundTag("Item");
		setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));

		if (getEntityItem() == null) {
			setDead();
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(final EntityPlayer entityIn) {
		if (!worldObj.isRemote) {
			final ItemStack var2 = getEntityItem();
			final int var3 = var2.stackSize;

			if (delayBeforeCanPickup == 0 && (owner == null || 6000 - age <= 200 || owner.equals(entityIn.getName()))
					&& entityIn.inventory.addItemStackToInventory(var2)) {
				if (var2.getItem() == Item.getItemFromBlock(Blocks.log)) {
					entityIn.triggerAchievement(AchievementList.mineWood);
				}

				if (var2.getItem() == Item.getItemFromBlock(Blocks.log2)) {
					entityIn.triggerAchievement(AchievementList.mineWood);
				}

				if (var2.getItem() == Items.leather) {
					entityIn.triggerAchievement(AchievementList.killCow);
				}

				if (var2.getItem() == Items.diamond) {
					entityIn.triggerAchievement(AchievementList.diamonds);
				}

				if (var2.getItem() == Items.blaze_rod) {
					entityIn.triggerAchievement(AchievementList.blazeRod);
				}

				if (var2.getItem() == Items.diamond && getThrower() != null) {
					final EntityPlayer var4 = worldObj.getPlayerEntityByName(getThrower());

					if (var4 != null && var4 != entityIn) {
						var4.triggerAchievement(AchievementList.diamondsToYou);
					}
				}

				if (!isSlient()) {
					worldObj.playSoundAtEntity(entityIn, "random.pop", 0.2F,
							((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}

				entityIn.onItemPickup(this, var3);

				if (var2.stackSize <= 0) {
					setDead();
				}
			}
		}
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return hasCustomName() ? getCustomNameTag()
				: StatCollector.translateToLocal("item." + getEntityItem().getUnlocalizedName());
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem() {
		return false;
	}

	/**
	 * Teleports the entity to another dimension. Params: Dimension number to
	 * teleport to
	 */
	@Override
	public void travelToDimension(final int dimensionId) {
		super.travelToDimension(dimensionId);

		if (!worldObj.isRemote) {
			searchForOtherItemsNearby();
		}
	}

	/**
	 * Returns the ItemStack corresponding to the Entity (Note: if no item
	 * exists, will log an error but still return an ItemStack containing
	 * Block.stone)
	 */
	public ItemStack getEntityItem() {
		final ItemStack var1 = getDataWatcher().getWatchableObjectItemStack(10);

		if (var1 == null) {
			if (worldObj != null) {
				logger.error("Item entity " + getEntityId() + " has no item?!");
			}

			return new ItemStack(Blocks.stone);
		} else {
			return var1;
		}
	}

	/**
	 * Sets the ItemStack for this entity
	 */
	public void setEntityItemStack(final ItemStack stack) {
		getDataWatcher().updateObject(10, stack);
		getDataWatcher().setObjectWatched(10);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	public String getThrower() {
		return thrower;
	}

	public void setThrower(final String thrower) {
		this.thrower = thrower;
	}

	public int func_174872_o() {
		return age;
	}

	public void setDefaultPickupDelay() {
		delayBeforeCanPickup = 10;
	}

	public void setNoPickupDelay() {
		delayBeforeCanPickup = 0;
	}

	public void setInfinitePickupDelay() {
		delayBeforeCanPickup = 32767;
	}

	public void setPickupDelay(final int ticks) {
		delayBeforeCanPickup = ticks;
	}

	public boolean func_174874_s() {
		return delayBeforeCanPickup > 0;
	}

	public void func_174873_u() {
		age = -6000;
	}

	public void func_174870_v() {
		setInfinitePickupDelay();
		age = 5999;
	}
}
