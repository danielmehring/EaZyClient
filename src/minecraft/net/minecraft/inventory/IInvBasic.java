package net.minecraft.inventory;

public interface IInvBasic {
	/**
	 * Called by InventoryBasic.onInventoryChanged() on a array that is never
	 * filled.
	 */
	void onInventoryChanged(InventoryBasic var1);
}
