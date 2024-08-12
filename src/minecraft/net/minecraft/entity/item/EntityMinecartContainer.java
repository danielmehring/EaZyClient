package net.minecraft.entity.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {

public static final int EaZy = 1143;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private ItemStack[] minecartContainerItems = new ItemStack[36];

	/**
	 * When set to true, the minecart will drop all items when setDead() is
	 * called. When false (such as when travelling dimensions) it preserves its
	 * contents.
	 */
	private boolean dropContentsWhenDead = true;
	// private static final String __OBFID = "http://https://fuckuskid00001674";

	public EntityMinecartContainer(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartContainer(final World worldIn, final double p_i1717_2_, final double p_i1717_4_,
			final double p_i1717_6_) {
		super(worldIn, p_i1717_2_, p_i1717_4_, p_i1717_6_);
	}

	@Override
	public void killMinecart(final DamageSource p_94095_1_) {
		super.killMinecart(p_94095_1_);
		InventoryHelper.func_180176_a(worldObj, this, this);
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(final int slotIn) {
		return minecartContainerItems[slotIn];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(final int index, final int count) {
		if (minecartContainerItems[index] != null) {
			ItemStack var3;

			if (minecartContainerItems[index].stackSize <= count) {
				var3 = minecartContainerItems[index];
				minecartContainerItems[index] = null;
				return var3;
			} else {
				var3 = minecartContainerItems[index].splitStack(count);

				if (minecartContainerItems[index].stackSize == 0) {
					minecartContainerItems[index] = null;
				}

				return var3;
			}
		} else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(final int index) {
		if (minecartContainerItems[index] != null) {
			final ItemStack var2 = minecartContainerItems[index];
			minecartContainerItems[index] = null;
			return var2;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(final int index, final ItemStack stack) {
		minecartContainerItems[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	@Override
	public void markDirty() {}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(final EntityPlayer playerIn) {
		return isDead ? false : playerIn.getDistanceSqToEntity(this) <= 64.0D;
	}

	@Override
	public void openInventory(final EntityPlayer playerIn) {}

	@Override
	public void closeInventory(final EntityPlayer playerIn) {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(final int index, final ItemStack stack) {
		return true;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return hasCustomName() ? getCustomNameTag() : "container.minecart";
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be
	 * 64, possibly will be extended. *Isn't this more of a set than a get?*
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/**
	 * Teleports the entity to another dimension. Params: Dimension number to
	 * teleport to
	 */
	@Override
	public void travelToDimension(final int dimensionId) {
		dropContentsWhenDead = false;
		super.travelToDimension(dimensionId);
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		if (dropContentsWhenDead) {
			InventoryHelper.func_180176_a(worldObj, this, this);
		}

		super.setDead();
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		final NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < minecartContainerItems.length; ++var3) {
			if (minecartContainerItems[var3] != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				minecartContainerItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		tagCompound.setTag("Items", var2);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		final NBTTagList var2 = tagCompund.getTagList("Items", 10);
		minecartContainerItems = new ItemStack[getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			final int var5 = var4.getByte("Slot") & 255;

			if (var5 >= 0 && var5 < minecartContainerItems.length) {
				minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	/**
	 * First layer of player interaction
	 */
	@Override
	public boolean interactFirst(final EntityPlayer playerIn) {
		if (!worldObj.isRemote) {
			playerIn.displayGUIChest(this);
		}

		return true;
	}

	@Override
	protected void applyDrag() {
		final int var1 = 15 - Container.calcRedstoneFromInventory(this);
		final float var2 = 0.98F + var1 * 0.001F;
		motionX *= var2;
		motionY *= 0.0D;
		motionZ *= var2;
	}

	@Override
	public int getField(final int id) {
		return 0;
	}

	@Override
	public void setField(final int id, final int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public void setLockCode(final LockCode code) {}

	@Override
	public LockCode getLockCode() {
		return LockCode.EMPTY_CODE;
	}

	@Override
	public void clearInventory() {
		for (int var1 = 0; var1 < minecartContainerItems.length; ++var1) {
			minecartContainerItems[var1] = null;
		}
	}
}
