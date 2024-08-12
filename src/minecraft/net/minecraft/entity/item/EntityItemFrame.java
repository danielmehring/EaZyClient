package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class EntityItemFrame extends EntityHanging {

public static final int EaZy = 1140;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Chance for this item frame's item to drop from the frame. */
	private float itemDropChance = 1.0F;
	// private static final String __OBFID = "http://https://fuckuskid00001547";

	public EntityItemFrame(final World worldIn) {
		super(worldIn);
	}

	public EntityItemFrame(final World worldIn, final BlockPos p_i45852_2_, final EnumFacing p_i45852_3_) {
		super(worldIn, p_i45852_2_);
		func_174859_a(p_i45852_3_);
	}

	@Override
	protected void entityInit() {
		getDataWatcher().addObjectByDataType(8, 5);
		getDataWatcher().addObject(9, (byte) 0);
	}

	@Override
	public float getCollisionBorderSize() {
		return 0.0F;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else if (!source.isExplosion() && getDisplayedItem() != null) {
			if (!worldObj.isRemote) {
				func_146065_b(source.getEntity(), false);
				setDisplayedItem((ItemStack) null);
			}

			return true;
		} else {
			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public int getWidthPixels() {
		return 12;
	}

	@Override
	public int getHeightPixels() {
		return 12;
	}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	@Override
	public boolean isInRangeToRenderDist(final double distance) {
		double var3 = 16.0D;
		var3 *= 64.0D * renderDistanceWeight;
		return distance < var3 * var3;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	@Override
	public void onBroken(final Entity p_110128_1_) {
		func_146065_b(p_110128_1_, true);
	}

	public void func_146065_b(final Entity p_146065_1_, final boolean p_146065_2_) {
		if (worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			ItemStack var3 = getDisplayedItem();

			if (p_146065_1_ instanceof EntityPlayer) {
				final EntityPlayer var4 = (EntityPlayer) p_146065_1_;

				if (var4.capabilities.isCreativeMode) {
					removeFrameFromMap(var3);
					return;
				}
			}

			if (p_146065_2_) {
				entityDropItem(new ItemStack(Items.item_frame), 0.0F);
			}

			if (var3 != null && rand.nextFloat() < itemDropChance) {
				var3 = var3.copy();
				removeFrameFromMap(var3);
				entityDropItem(var3, 0.0F);
			}
		}
	}

	/**
	 * Removes the dot representing this frame's position from the map when the
	 * item frame is broken.
	 */
	private void removeFrameFromMap(final ItemStack p_110131_1_) {
		if (p_110131_1_ != null) {
			if (p_110131_1_.getItem() == Items.filled_map) {
				final MapData var2 = ((ItemMap) p_110131_1_.getItem()).getMapData(p_110131_1_, worldObj);
				var2.playersVisibleOnMap.remove("frame-" + getEntityId());
			}

			p_110131_1_.setItemFrame((EntityItemFrame) null);
		}
	}

	public ItemStack getDisplayedItem() {
		return getDataWatcher().getWatchableObjectItemStack(8);
	}

	public void setDisplayedItem(final ItemStack p_82334_1_) {
		func_174864_a(p_82334_1_, true);
	}

	private void func_174864_a(ItemStack p_174864_1_, final boolean p_174864_2_) {
		if (p_174864_1_ != null) {
			p_174864_1_ = p_174864_1_.copy();
			p_174864_1_.stackSize = 1;
			p_174864_1_.setItemFrame(this);
		}

		getDataWatcher().updateObject(8, p_174864_1_);
		getDataWatcher().setObjectWatched(8);

		if (p_174864_2_ && field_174861_a != null) {
			worldObj.updateComparatorOutputLevel(field_174861_a, Blocks.air);
		}
	}

	/**
	 * Return the rotation of the item currently on this frame.
	 */
	public int getRotation() {
		return getDataWatcher().getWatchableObjectByte(9);
	}

	public void setItemRotation(final int p_82336_1_) {
		func_174865_a(p_82336_1_, true);
	}

	private void func_174865_a(final int p_174865_1_, final boolean p_174865_2_) {
		getDataWatcher().updateObject(9, (byte) (p_174865_1_ % 8));

		if (p_174865_2_ && field_174861_a != null) {
			worldObj.updateComparatorOutputLevel(field_174861_a, Blocks.air);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		if (getDisplayedItem() != null) {
			tagCompound.setTag("Item", getDisplayedItem().writeToNBT(new NBTTagCompound()));
			tagCompound.setByte("ItemRotation", (byte) getRotation());
			tagCompound.setFloat("ItemDropChance", itemDropChance);
		}

		super.writeEntityToNBT(tagCompound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		final NBTTagCompound var2 = tagCompund.getCompoundTag("Item");

		if (var2 != null && !var2.hasNoTags()) {
			func_174864_a(ItemStack.loadItemStackFromNBT(var2), false);
			func_174865_a(tagCompund.getByte("ItemRotation"), false);

			if (tagCompund.hasKey("ItemDropChance", 99)) {
				itemDropChance = tagCompund.getFloat("ItemDropChance");
			}

			if (tagCompund.hasKey("Direction")) {
				func_174865_a(getRotation() * 2, false);
			}
		}

		super.readEntityFromNBT(tagCompund);
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		if (getDisplayedItem() == null) {
			final ItemStack var2 = playerIn.getHeldItem();

			if (var2 != null && !worldObj.isRemote) {
				setDisplayedItem(var2);

				if (!playerIn.capabilities.isCreativeMode && --var2.stackSize <= 0) {
					playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
				}
			}
		} else if (!worldObj.isRemote) {
			setItemRotation(getRotation() + 1);
		}

		return true;
	}

	public int func_174866_q() {
		return getDisplayedItem() == null ? 0 : getRotation() % 8 + 1;
	}
}
