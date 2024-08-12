package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {

public static final int EaZy = 1226;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private final World worldObj;
	private final BlockPos field_178145_h;
	// private static final String __OBFID = "http://https://fuckuskid00001744";

	public ContainerWorkbench(final InventoryPlayer p_i45800_1_, final World worldIn, final BlockPos p_i45800_3_) {
		worldObj = worldIn;
		field_178145_h = p_i45800_3_;
		addSlotToContainer(new SlotCrafting(p_i45800_1_.player, craftMatrix, craftResult, 0, 124, 35));
		int var4;
		int var5;

		for (var4 = 0; var4 < 3; ++var4) {
			for (var5 = 0; var5 < 3; ++var5) {
				addSlotToContainer(new Slot(craftMatrix, var5 + var4 * 3, 30 + var5 * 18, 17 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 3; ++var4) {
			for (var5 = 0; var5 < 9; ++var5) {
				addSlotToContainer(new Slot(p_i45800_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
			}
		}

		for (var4 = 0; var4 < 9; ++var4) {
			addSlotToContainer(new Slot(p_i45800_1_, var4, 8 + var4 * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(final IInventory p_75130_1_) {
		craftResult.setInventorySlotContents(0,
				CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(final EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);

		if (!worldObj.isRemote) {
			for (int var2 = 0; var2 < 9; ++var2) {
				final ItemStack var3 = craftMatrix.getStackInSlotOnClosing(var2);

				if (var3 != null) {
					p_75134_1_.dropPlayerItemWithRandomChoice(var3, false);
				}
			}
		}
	}

	@Override
	public boolean canInteractWith(final EntityPlayer playerIn) {
		return worldObj.getBlockState(field_178145_h).getBlock() != Blocks.crafting_table ? false
				: playerIn.getDistanceSq(field_178145_h.getX() + 0.5D, field_178145_h.getY() + 0.5D,
						field_178145_h.getZ() + 0.5D) <= 64.0D;
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

			if (index == 0) {
				if (!mergeItemStack(var5, 10, 46, true)) {
					return null;
				}

				var4.onSlotChange(var5, var3);
			} else if (index >= 10 && index < 37) {
				if (!mergeItemStack(var5, 37, 46, false)) {
					return null;
				}
			} else if (index >= 37 && index < 46) {
				if (!mergeItemStack(var5, 10, 37, false)) {
					return null;
				}
			} else if (!mergeItemStack(var5, 10, 46, false)) {
				return null;
			}

			if (var5.stackSize == 0) {
				var4.putStack((ItemStack) null);
			} else {
				var4.onSlotChanged();
			}

			if (var5.stackSize == var3.stackSize) {
				return null;
			}

			var4.onPickupFromSlot(playerIn, var5);
		}

		return var3;
	}

	@Override
	public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
		return p_94530_2_.inventory != craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
	}
}
