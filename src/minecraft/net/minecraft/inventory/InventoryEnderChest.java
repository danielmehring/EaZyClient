package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {

public static final int EaZy = 1233;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private TileEntityEnderChest associatedChest;
	// private static final String __OBFID = "http://https://fuckuskid00001759";

	public InventoryEnderChest() {
		super("container.enderchest", false, 27);
	}

	public void setChestTileEntity(final TileEntityEnderChest chestTileEntity) {
		associatedChest = chestTileEntity;
	}

	public void loadInventoryFromNBT(final NBTTagList p_70486_1_) {
		int var2;

		for (var2 = 0; var2 < getSizeInventory(); ++var2) {
			setInventorySlotContents(var2, (ItemStack) null);
		}

		for (var2 = 0; var2 < p_70486_1_.tagCount(); ++var2) {
			final NBTTagCompound var3 = p_70486_1_.getCompoundTagAt(var2);
			final int var4 = var3.getByte("Slot") & 255;

			if (var4 >= 0 && var4 < getSizeInventory()) {
				setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
			}
		}
	}

	public NBTTagList saveInventoryToNBT() {
		final NBTTagList var1 = new NBTTagList();

		for (int var2 = 0; var2 < getSizeInventory(); ++var2) {
			final ItemStack var3 = getStackInSlot(var2);

			if (var3 != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var2);
				var3.writeToNBT(var4);
				var1.appendTag(var4);
			}
		}

		return var1;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes
	 * with Container
	 */
	@Override
	public boolean isUseableByPlayer(final EntityPlayer playerIn) {
		return associatedChest != null && !associatedChest.func_145971_a(playerIn) ? false
				: super.isUseableByPlayer(playerIn);
	}

	@Override
	public void openInventory(final EntityPlayer playerIn) {
		if (associatedChest != null) {
			associatedChest.func_145969_a();
		}

		super.openInventory(playerIn);
	}

	@Override
	public void closeInventory(final EntityPlayer playerIn) {
		if (associatedChest != null) {
			associatedChest.func_145970_b();
		}

		super.closeInventory(playerIn);
		associatedChest = null;
	}
}
