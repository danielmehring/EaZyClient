package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {

public static final int EaZy = 1242;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Merchant's inventory. */
	private final InventoryMerchant theMerchantInventory;

	/** The Player whos trying to buy/sell stuff. */
	private final EntityPlayer thePlayer;
	private int field_75231_g;

	/** "Instance" of the Merchant. */
	private final IMerchant theMerchant;
	// private static final String __OBFID = "http://https://fuckuskid00001758";

	public SlotMerchantResult(final EntityPlayer p_i1822_1_, final IMerchant p_i1822_2_,
			final InventoryMerchant p_i1822_3_, final int p_i1822_4_, final int p_i1822_5_, final int p_i1822_6_) {
		super(p_i1822_3_, p_i1822_4_, p_i1822_5_, p_i1822_6_);
		thePlayer = p_i1822_1_;
		theMerchant = p_i1822_2_;
		theMerchantInventory = p_i1822_3_;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(final ItemStack stack) {
		return false;
	}

	/**
	 * Decrease the size of the stack in slot (first int arg) by the amount of
	 * the second int arg. Returns the new stack.
	 */
	@Override
	public ItemStack decrStackSize(final int p_75209_1_) {
		if (getHasStack()) {
			field_75231_g += Math.min(p_75209_1_, getStack().stackSize);
		}

		return super.decrStackSize(p_75209_1_);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood. Typically increases an internal count then calls
	 * onCrafting(item).
	 */
	@Override
	protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
		field_75231_g += p_75210_2_;
		this.onCrafting(p_75210_1_);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood.
	 */
	@Override
	protected void onCrafting(final ItemStack p_75208_1_) {
		p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, field_75231_g);
		field_75231_g = 0;
	}

	@Override
	public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
		this.onCrafting(stack);
		final MerchantRecipe var3 = theMerchantInventory.getCurrentRecipe();

		if (var3 != null) {
			ItemStack var4 = theMerchantInventory.getStackInSlot(0);
			ItemStack var5 = theMerchantInventory.getStackInSlot(1);

			if (doTrade(var3, var4, var5) || doTrade(var3, var5, var4)) {
				theMerchant.useRecipe(var3);
				playerIn.triggerAchievement(StatList.timesTradedWithVillagerStat);

				if (var4 != null && var4.stackSize <= 0) {
					var4 = null;
				}

				if (var5 != null && var5.stackSize <= 0) {
					var5 = null;
				}

				theMerchantInventory.setInventorySlotContents(0, var4);
				theMerchantInventory.setInventorySlotContents(1, var5);
			}
		}
	}

	private boolean doTrade(final MerchantRecipe trade, final ItemStack firstItem, final ItemStack secondItem) {
		final ItemStack var4 = trade.getItemToBuy();
		final ItemStack var5 = trade.getSecondItemToBuy();

		if (firstItem != null && firstItem.getItem() == var4.getItem()) {
			if (var5 != null && secondItem != null && var5.getItem() == secondItem.getItem()) {
				firstItem.stackSize -= var4.stackSize;
				secondItem.stackSize -= var5.stackSize;
				return true;
			}

			if (var5 == null && secondItem == null) {
				firstItem.stackSize -= var4.stackSize;
				return true;
			}
		}

		return false;
	}
}
