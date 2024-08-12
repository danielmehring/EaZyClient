package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CreativeCrafting implements ICrafting {

public static final int EaZy = 530;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public CreativeCrafting(final Minecraft mc) {}

	/**
	 * update the crafting window inventory with the items in the list
	 */
	@Override
	public void updateCraftingInventory(final Container p_71110_1_, final List p_71110_2_) {}

	/**
	 * Sends the contents of an inventory slot to the client-side Container.
	 * This doesn't have to match the actual contents of that slot. Args:
	 * Container, slot number, slot contents
	 */
	@Override
	public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
		Minecraft.playerController.sendSlotPacket(p_71111_3_, p_71111_2_);
	}

	/**
	 * Sends two ints to the client-side Container. Used for furnace burning
	 * time, smelting progress, brewing progress, and enchanting level. Normally
	 * the first int identifies which variable to update, and the second
	 * contains the new value. Both are truncated to shorts in non-local SMP.
	 */
	@Override
	public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {}

	@Override
	public void func_175173_a(final Container p_175173_1_, final IInventory p_175173_2_) {}
}
