package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityArmorStand extends EntityLivingBase {

public static final int EaZy = 1131;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
	private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
	private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
	private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
	private final ItemStack[] contents;
	private boolean canInteract;
	private long field_175437_i;
	private int disabledSlots;
	private Rotations headRotation;
	private Rotations bodyRotation;
	private Rotations leftArmRotation;
	private Rotations rightArmRotation;
	private Rotations leftLegRotation;
	private Rotations rightLegRotation;
	// private static final String __OBFID = "http://https://fuckuskid00002228";

	public EntityArmorStand(final World worldIn) {
		super(worldIn);
		contents = new ItemStack[5];
		headRotation = DEFAULT_HEAD_ROTATION;
		bodyRotation = DEFAULT_BODY_ROTATION;
		leftArmRotation = DEFAULT_LEFTARM_ROTATION;
		rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
		leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
		rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
		func_174810_b(true);
		noClip = hasNoGravity();
		setSize(0.5F, 1.975F);
	}

	public EntityArmorStand(final World worldIn, final double p_i45855_2_, final double p_i45855_4_,
			final double p_i45855_6_) {
		this(worldIn);
		setPosition(p_i45855_2_, p_i45855_4_, p_i45855_6_);
	}

	/**
	 * Returns whether the entity is in a server world
	 */
	@Override
	public boolean isServerWorld() {
		return super.isServerWorld() && !hasNoGravity();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(10, (byte) 0);
		dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
		dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
		dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
		dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
		dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
		dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem() {
		return contents[0];
	}

	/**
	 * 0: Tool in Hand; 1-4: Armor
	 */
	@Override
	public ItemStack getEquipmentInSlot(final int p_71124_1_) {
		return contents[p_71124_1_];
	}

	@Override
	public ItemStack getCurrentArmor(final int p_82169_1_) {
		return contents[p_82169_1_ + 1];
	}

	/**
	 * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is
	 * armor. Params: Item, slot
	 */
	@Override
	public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {
		contents[slotIn] = itemStackIn;
	}

	/**
	 * returns the inventory of this entity (only used in EntityPlayerMP it
	 * seems)
	 */
	@Override
	public ItemStack[] getInventory() {
		return contents;
	}

	@Override
	public boolean func_174820_d(final int p_174820_1_, final ItemStack p_174820_2_) {
		int var3;

		if (p_174820_1_ == 99) {
			var3 = 0;
		} else {
			var3 = p_174820_1_ - 100 + 1;

			if (var3 < 0 || var3 >= contents.length) {
				return false;
			}
		}

		if (p_174820_2_ != null && EntityLiving.getArmorPosition(p_174820_2_) != var3
				&& (var3 != 4 || !(p_174820_2_.getItem() instanceof ItemBlock))) {
			return false;
		} else {
			setCurrentItemOrArmor(var3, p_174820_2_);
			return true;
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		final NBTTagList var2 = new NBTTagList();

		for (final ItemStack content : contents) {
			final NBTTagCompound var4 = new NBTTagCompound();

			if (content != null) {
				content.writeToNBT(var4);
			}

			var2.appendTag(var4);
		}

		tagCompound.setTag("Equipment", var2);

		if (getAlwaysRenderNameTag() && (getCustomNameTag() == null || getCustomNameTag().length() == 0)) {
			tagCompound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
		}

		tagCompound.setBoolean("Invisible", isInvisible());
		tagCompound.setBoolean("Small", isSmall());
		tagCompound.setBoolean("ShowArms", getShowArms());
		tagCompound.setInteger("DisabledSlots", disabledSlots);
		tagCompound.setBoolean("NoGravity", hasNoGravity());
		tagCompound.setBoolean("NoBasePlate", hasNoBasePlate());
		tagCompound.setTag("Pose", readPoseFromNBT());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);

		if (tagCompund.hasKey("Equipment", 9)) {
			final NBTTagList var2 = tagCompund.getTagList("Equipment", 10);

			for (int var3 = 0; var3 < contents.length; ++var3) {
				contents[var3] = ItemStack.loadItemStackFromNBT(var2.getCompoundTagAt(var3));
			}
		}

		setInvisible(tagCompund.getBoolean("Invisible"));
		setSmall(tagCompund.getBoolean("Small"));
		setShowArms(tagCompund.getBoolean("ShowArms"));
		disabledSlots = tagCompund.getInteger("DisabledSlots");
		setNoGravity(tagCompund.getBoolean("NoGravity"));
		setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
		noClip = hasNoGravity();
		final NBTTagCompound var4 = tagCompund.getCompoundTag("Pose");
		writePoseToNBT(var4);
	}

	private void writePoseToNBT(final NBTTagCompound p_175416_1_) {
		final NBTTagList var2 = p_175416_1_.getTagList("Head", 5);

		if (var2.tagCount() > 0) {
			setHeadRotation(new Rotations(var2));
		} else {
			setHeadRotation(DEFAULT_HEAD_ROTATION);
		}

		final NBTTagList var3 = p_175416_1_.getTagList("Body", 5);

		if (var3.tagCount() > 0) {
			setBodyRotation(new Rotations(var3));
		} else {
			setBodyRotation(DEFAULT_BODY_ROTATION);
		}

		final NBTTagList var4 = p_175416_1_.getTagList("LeftArm", 5);

		if (var4.tagCount() > 0) {
			setLeftArmRotation(new Rotations(var4));
		} else {
			setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);
		}

		final NBTTagList var5 = p_175416_1_.getTagList("RightArm", 5);

		if (var5.tagCount() > 0) {
			setRightArmRotation(new Rotations(var5));
		} else {
			setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);
		}

		final NBTTagList var6 = p_175416_1_.getTagList("LeftLeg", 5);

		if (var6.tagCount() > 0) {
			setLeftLegRotation(new Rotations(var6));
		} else {
			setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);
		}

		final NBTTagList var7 = p_175416_1_.getTagList("RightLeg", 5);

		if (var7.tagCount() > 0) {
			setRightLegRotation(new Rotations(var7));
		} else {
			setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
		}
	}

	private NBTTagCompound readPoseFromNBT() {
		final NBTTagCompound var1 = new NBTTagCompound();

		if (!DEFAULT_HEAD_ROTATION.equals(headRotation)) {
			var1.setTag("Head", headRotation.func_179414_a());
		}

		if (!DEFAULT_BODY_ROTATION.equals(bodyRotation)) {
			var1.setTag("Body", bodyRotation.func_179414_a());
		}

		if (!DEFAULT_LEFTARM_ROTATION.equals(leftArmRotation)) {
			var1.setTag("LeftArm", leftArmRotation.func_179414_a());
		}

		if (!DEFAULT_RIGHTARM_ROTATION.equals(rightArmRotation)) {
			var1.setTag("RightArm", rightArmRotation.func_179414_a());
		}

		if (!DEFAULT_LEFTLEG_ROTATION.equals(leftLegRotation)) {
			var1.setTag("LeftLeg", leftLegRotation.func_179414_a());
		}

		if (!DEFAULT_RIGHTLEG_ROTATION.equals(rightLegRotation)) {
			var1.setTag("RightLeg", rightLegRotation.func_179414_a());
		}

		return var1;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(final Entity p_82167_1_) {}

	@Override
	protected void collideWithNearbyEntities() {
		final List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox());

		if (var1 != null && !var1.isEmpty()) {
			for (int var2 = 0; var2 < var1.size(); ++var2) {
				final Entity var3 = (Entity) var1.get(var2);

				if (var3 instanceof EntityMinecart
						&& ((EntityMinecart) var3).func_180456_s() == EntityMinecart.EnumMinecartType.RIDEABLE
						&& getDistanceSqToEntity(var3) <= 0.2D) {
					var3.applyEntityCollision(this);
				}
			}
		}
	}

	@Override
	public boolean func_174825_a(final EntityPlayer p_174825_1_, final Vec3 p_174825_2_) {
		if (!worldObj.isRemote && !p_174825_1_.isSpectatorMode()) {
			byte var3 = 0;
			final ItemStack var4 = p_174825_1_.getCurrentEquippedItem();
			final boolean var5 = var4 != null;

			if (var5 && var4.getItem() instanceof ItemArmor) {
				final ItemArmor var6 = (ItemArmor) var4.getItem();

				if (var6.armorType == 3) {
					var3 = 1;
				} else if (var6.armorType == 2) {
					var3 = 2;
				} else if (var6.armorType == 1) {
					var3 = 3;
				} else if (var6.armorType == 0) {
					var3 = 4;
				}
			}

			if (var5 && (var4.getItem() == Items.skull || var4.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
				var3 = 4;
			}

			byte var14 = 0;
			final boolean var15 = isSmall();
			final double var16 = var15 ? p_174825_2_.yCoord * 2.0D : p_174825_2_.yCoord;

			if (var16 >= 0.1D && var16 < 0.1D + (var15 ? 0.8D : 0.45D) && contents[1] != null) {
				var14 = 1;
			} else if (var16 >= 0.9D + (var15 ? 0.3D : 0.0D) && var16 < 0.9D + (var15 ? 1.0D : 0.7D)
					&& contents[3] != null) {
				var14 = 3;
			} else if (var16 >= 0.4D && var16 < 0.4D + (var15 ? 1.0D : 0.8D) && contents[2] != null) {
				var14 = 2;
			} else if (var16 >= 1.6D && contents[4] != null) {
				var14 = 4;
			}

			final boolean var18 = contents[var14] != null;

			if ((disabledSlots & 1 << var14) != 0 || (disabledSlots & 1 << var3) != 0) {
				var14 = var3;

				if ((disabledSlots & 1 << var3) != 0) {
					if ((disabledSlots & 1) != 0) {
						return true;
					}

					var14 = 0;
				}
			}

			if (var5 && var3 == 0 && !getShowArms()) {
				return true;
			} else {
				if (var5) {
					func_175422_a(p_174825_1_, var3);
				} else if (var18) {
					func_175422_a(p_174825_1_, var14);
				}

				return true;
			}
		} else {
			return true;
		}
	}

	private void func_175422_a(final EntityPlayer p_175422_1_, final int p_175422_2_) {
		final ItemStack var3 = contents[p_175422_2_];

		if (var3 == null || (disabledSlots & 1 << p_175422_2_ + 8) == 0) {
			if (var3 != null || (disabledSlots & 1 << p_175422_2_ + 16) == 0) {
				final int var4 = p_175422_1_.inventory.currentItem;
				final ItemStack var5 = p_175422_1_.inventory.getStackInSlot(var4);
				ItemStack var6;

				if (p_175422_1_.capabilities.isCreativeMode
						&& (var3 == null || var3.getItem() == Item.getItemFromBlock(Blocks.air)) && var5 != null) {
					var6 = var5.copy();
					var6.stackSize = 1;
					setCurrentItemOrArmor(p_175422_2_, var6);
				} else if (var5 != null && var5.stackSize > 1) {
					if (var3 == null) {
						var6 = var5.copy();
						var6.stackSize = 1;
						setCurrentItemOrArmor(p_175422_2_, var6);
						--var5.stackSize;
					}
				} else {
					setCurrentItemOrArmor(p_175422_2_, var5);
					p_175422_1_.inventory.setInventorySlotContents(var4, var3);
				}
			}
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (!worldObj.isRemote && !canInteract) {
			if (DamageSource.outOfWorld.equals(source)) {
				setDead();
				return false;
			} else if (func_180431_b(source)) {
				return false;
			} else if (source.isExplosion()) {
				dropContents();
				setDead();
				return false;
			} else if (DamageSource.inFire.equals(source)) {
				if (!isBurning()) {
					setFire(5);
				} else {
					func_175406_a(0.15F);
				}

				return false;
			} else if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
				func_175406_a(4.0F);
				return false;
			} else {
				final boolean var3 = "arrow".equals(source.getDamageType());
				final boolean var4 = "player".equals(source.getDamageType());

				if (!var4 && !var3) {
					return false;
				} else {
					if (source.getSourceOfDamage() instanceof EntityArrow) {
						source.getSourceOfDamage().setDead();
					}

					if (source.getEntity() instanceof EntityPlayer
							&& !((EntityPlayer) source.getEntity()).capabilities.allowEdit) {
						return false;
					} else if (source.func_180136_u()) {
						playParticles();
						setDead();
						return false;
					} else {
						final long var5 = worldObj.getTotalWorldTime();

						if (var5 - field_175437_i > 5L && !var3) {
							field_175437_i = var5;
						} else {
							dropBlock();
							playParticles();
							setDead();
						}

						return false;
					}
				}
			}
		} else {
			return false;
		}
	}

	private void playParticles() {
		if (worldObj instanceof WorldServer) {
			((WorldServer) worldObj).func_175739_a(EnumParticleTypes.BLOCK_DUST, posX, posY + height / 1.5D, posZ, 10,
					width / 4.0F, height / 4.0F, width / 4.0F, 0.05D,
					new int[] { Block.getStateId(Blocks.planks.getDefaultState()) });
		}
	}

	private void func_175406_a(final float p_175406_1_) {
		float var2 = getHealth();
		var2 -= p_175406_1_;

		if (var2 <= 0.5F) {
			dropContents();
			setDead();
		} else {
			setHealth(var2);
		}
	}

	private void dropBlock() {
		Block.spawnAsEntity(worldObj, new BlockPos(this), new ItemStack(Items.armor_stand));
		dropContents();
	}

	private void dropContents() {
		for (int var1 = 0; var1 < contents.length; ++var1) {
			if (contents[var1] != null && contents[var1].stackSize > 0) {
				if (contents[var1] != null) {
					Block.spawnAsEntity(worldObj, new BlockPos(this).offsetUp(), contents[var1]);
				}

				contents[var1] = null;
			}
		}
	}

	@Override
	protected float func_110146_f(final float p_110146_1_, final float p_110146_2_) {
		prevRenderYawOffset = prevRotationYaw;
		renderYawOffset = rotationYaw;
		return 0.0F;
	}

	@Override
	public float getEyeHeight() {
		return isChild() ? height * 0.5F : height * 0.9F;
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
		if (!hasNoGravity()) {
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();
		final Rotations var1 = dataWatcher.getWatchableObjectRotations(11);

		if (!headRotation.equals(var1)) {
			setHeadRotation(var1);
		}

		final Rotations var2 = dataWatcher.getWatchableObjectRotations(12);

		if (!bodyRotation.equals(var2)) {
			setBodyRotation(var2);
		}

		final Rotations var3 = dataWatcher.getWatchableObjectRotations(13);

		if (!leftArmRotation.equals(var3)) {
			setLeftArmRotation(var3);
		}

		final Rotations var4 = dataWatcher.getWatchableObjectRotations(14);

		if (!rightArmRotation.equals(var4)) {
			setRightArmRotation(var4);
		}

		final Rotations var5 = dataWatcher.getWatchableObjectRotations(15);

		if (!leftLegRotation.equals(var5)) {
			setLeftLegRotation(var5);
		}

		final Rotations var6 = dataWatcher.getWatchableObjectRotations(16);

		if (!rightLegRotation.equals(var6)) {
			setRightLegRotation(var6);
		}
	}

	@Override
	protected void func_175135_B() {
		setInvisible(canInteract);
	}

	@Override
	public void setInvisible(final boolean invisible) {
		canInteract = invisible;
		super.setInvisible(invisible);
	}

	/**
	 * If Animal, checks if the age timer is negative
	 */
	@Override
	public boolean isChild() {
		return isSmall();
	}

	@Override
	public void func_174812_G() {
		setDead();
	}

	@Override
	public boolean func_180427_aV() {
		return isInvisible();
	}

	private void setSmall(final boolean p_175420_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(10);

		if (p_175420_1_) {
			var2 = (byte) (var2 | 1);
		} else {
			var2 &= -2;
		}

		dataWatcher.updateObject(10, var2);
	}

	public boolean isSmall() {
		return (dataWatcher.getWatchableObjectByte(10) & 1) != 0;
	}

	private void setNoGravity(final boolean p_175425_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(10);

		if (p_175425_1_) {
			var2 = (byte) (var2 | 2);
		} else {
			var2 &= -3;
		}

		dataWatcher.updateObject(10, var2);
	}

	public boolean hasNoGravity() {
		return (dataWatcher.getWatchableObjectByte(10) & 2) != 0;
	}

	private void setShowArms(final boolean p_175413_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(10);

		if (p_175413_1_) {
			var2 = (byte) (var2 | 4);
		} else {
			var2 &= -5;
		}

		dataWatcher.updateObject(10, var2);
	}

	public boolean getShowArms() {
		return (dataWatcher.getWatchableObjectByte(10) & 4) != 0;
	}

	private void setNoBasePlate(final boolean p_175426_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(10);

		if (p_175426_1_) {
			var2 = (byte) (var2 | 8);
		} else {
			var2 &= -9;
		}

		dataWatcher.updateObject(10, var2);
	}

	public boolean hasNoBasePlate() {
		return (dataWatcher.getWatchableObjectByte(10) & 8) != 0;
	}

	public void setHeadRotation(final Rotations p_175415_1_) {
		headRotation = p_175415_1_;
		dataWatcher.updateObject(11, p_175415_1_);
	}

	public void setBodyRotation(final Rotations p_175424_1_) {
		bodyRotation = p_175424_1_;
		dataWatcher.updateObject(12, p_175424_1_);
	}

	public void setLeftArmRotation(final Rotations p_175405_1_) {
		leftArmRotation = p_175405_1_;
		dataWatcher.updateObject(13, p_175405_1_);
	}

	public void setRightArmRotation(final Rotations p_175428_1_) {
		rightArmRotation = p_175428_1_;
		dataWatcher.updateObject(14, p_175428_1_);
	}

	public void setLeftLegRotation(final Rotations p_175417_1_) {
		leftLegRotation = p_175417_1_;
		dataWatcher.updateObject(15, p_175417_1_);
	}

	public void setRightLegRotation(final Rotations p_175427_1_) {
		rightLegRotation = p_175427_1_;
		dataWatcher.updateObject(16, p_175427_1_);
	}

	public Rotations getHeadRotation() {
		return headRotation;
	}

	public Rotations getBodyRotation() {
		return bodyRotation;
	}

	public Rotations getLeftArmRotation() {
		return leftArmRotation;
	}

	public Rotations getRightArmRotation() {
		return rightArmRotation;
	}

	public Rotations getLeftLegRotation() {
		return leftLegRotation;
	}

	public Rotations getRightLegRotation() {
		return rightLegRotation;
	}
}
