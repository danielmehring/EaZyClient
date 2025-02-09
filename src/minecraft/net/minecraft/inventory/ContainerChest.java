package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {

public static final int EaZy = 1217;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IInventory lowerChestInventory;
	private final int numRows;
	// private static final String __OBFID = "http://https://fuckuskid00001742";

	public ContainerChest(final IInventory p_i45801_1_, final IInventory p_i45801_2_, final EntityPlayer p_i45801_3_) {
		lowerChestInventory = p_i45801_2_;
		numRows = p_i45801_2_.getSizeInventory() / 9;
		p_i45801_2_.openInventory(p_i45801_3_);
		final int var4 = (numRows - 4) * 18;
		int var5;
		int var6;

		for (var5 = 0; var5 < numRows; ++var5) {
			for (var6 = 0; var6 < 9; ++var6) {
				addSlotToContainer(new Slot(p_i45801_2_, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
			}
		}

		for (var5 = 0; var5 < 3; ++var5) {
			for (var6 = 0; var6 < 9; ++var6) {
				addSlotToContainer(new Slot(p_i45801_1_, var6 + var5 * 9 + 9, 8 + var6 * 18, 103 + var5 * 18 + var4));
			}
		}

		for (var5 = 0; var5 < 9; ++var5) {
			addSlotToContainer(new Slot(p_i45801_1_, var5, 8 + var5 * 18, 161 + var4));
		}
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn) {
		return lowerChestInventory.isUseableByPlayer(playerIn);
	}

	/**
	 * Take a stack from the specified inventory slot.
	 */
	@Override
	public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
		ItemStack var3 = null;
		final Slot var4 = (Slot) inventorySlots.get(index);

		if (var4 != null && var4.getHasStack()) {
			final ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (index < numRows * 9) {
				if (!mergeItemStack(var5, numRows * 9, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(var5, 0, numRows * 9, false)) {
				return null;
			}

			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}
		}

		return var3;
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(final EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		lowerChestInventory.closeInventory(p_75134_1_);
	}

	/**
	 * Return this chest container's lower chest inventory.
	 */
	public IInventory getLowerChestInventory() {
		return lowerChestInventory;
	}
}
