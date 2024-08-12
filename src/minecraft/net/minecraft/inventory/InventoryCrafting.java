package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryCrafting implements IInventory {

public static final int EaZy = 1231;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** List of the stacks in the crafting matrix. */
	private final ItemStack[] stackList;

	/** the width of the crafting inventory */
	private final int inventoryWidth;
	private final int field_174924_c;

	/**
	 * Class containing the callbacks for the events on_GUIClosed and
	 * on_CraftMaxtrixChanged.
	 */
	private final Container eventHandler;
	// private static final String __OBFID = "http://https://fuckuskid00001743";

	public InventoryCrafting(final Container p_i1807_1_, final int p_i1807_2_, final int p_i1807_3_) {
		final int var4 = p_i1807_2_ * p_i1807_3_;
		stackList = new ItemStack[var4];
		eventHandler = p_i1807_1_;
		inventoryWidth = p_i1807_2_;
		field_174924_c = p_i1807_3_;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return stackList.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot(final int slotIn) {
		return slotIn >= getSizeInventory() ? null : stackList[slotIn];
	}

	/**
	 * Returns the itemstack in the slot specified (Top left is 0, 0). Args:
	 * row, column
	 */
	public ItemStack getStackInRowAndColumn(final int p_70463_1_, final int p_70463_2_) {
		return p_70463_1_ >= 0 && p_70463_1_ < inventoryWidth && p_70463_2_ >= 0 && p_70463_2_ <= field_174924_c
				? getStackInSlot(p_70463_1_ + p_70463_2_ * inventoryWidth) : null;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return "container.crafting";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName())
				: new ChatComponentTranslation(getName(), new Object[0]);
	}

	/**
	 * When some containers are closed they call this on each slot, then drop
	 * whatever it returns as an EntityItem - like when you close a workbench
	 * GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(final int index) {
		if (stackList[index] != null) {
			final ItemStack var2 = stackList[index];
			stackList[index] = null;
			return var2;
		} else {
			return null;
		}
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number
	 * (second arg) of items and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(final int index, final int count) {
		if (stackList[index] != null) {
			ItemStack var3;

			if (stackList[index].stackSize <= count) {
				var3 = stackList[index];
				stackList[index] = null;
				eventHandler.onCraftMatrixChanged(this);
				return var3;
			} else {
				var3 = stackList[index].splitStack(count);

				if (stackList[index].stackSize == 0) {
					stackList[index] = null;
				}

				eventHandler.onCraftMatrixChanged(this);
				return var3;
			}
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
		stackList[index] = stack;
		eventHandler.onCraftMatrixChanged(this);
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
		return true;
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
	public void clearInventory() {
		for (int var1 = 0; var1 < stackList.length; ++var1) {
			stackList[var1] = null;
		}
	}

	public int func_174923_h() {
		return field_174924_c;
	}

	public int func_174922_i() {
		return inventoryWidth;
	}
}
