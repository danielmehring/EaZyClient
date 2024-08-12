package net.minecraft.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotFurnaceOutput extends Slot {

public static final int EaZy = 1241;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The player that is using the GUI where this slot resides. */
	private final EntityPlayer thePlayer;
	private int field_75228_b;
	// private static final String __OBFID = "http://https://fuckuskid00002183";

	public SlotFurnaceOutput(final EntityPlayer p_i45793_1_, final IInventory p_i45793_2_, final int p_i45793_3_,
			final int p_i45793_4_, final int p_i45793_5_) {
		super(p_i45793_2_, p_i45793_3_, p_i45793_4_, p_i45793_5_);
		thePlayer = p_i45793_1_;
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
			field_75228_b += Math.min(p_75209_1_, getStack().stackSize);
		}

		return super.decrStackSize(p_75209_1_);
	}

	@Override
	public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
		this.onCrafting(stack);
		super.onPickupFromSlot(playerIn, stack);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood. Typically increases an internal count then calls
	 * onCrafting(item).
	 */
	@Override
	protected void onCrafting(final ItemStack p_75210_1_, final int p_75210_2_) {
		field_75228_b += p_75210_2_;
		this.onCrafting(p_75210_1_);
	}

	/**
	 * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
	 * not ore and wood.
	 */
	@Override
	protected void onCrafting(final ItemStack p_75208_1_) {
		p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, field_75228_b);

		if (!thePlayer.worldObj.isRemote) {
			int var2 = field_75228_b;
			final float var3 = FurnaceRecipes.instance().getSmeltingExperience(p_75208_1_);
			int var4;

			if (var3 == 0.0F) {
				var2 = 0;
			} else if (var3 < 1.0F) {
				var4 = MathHelper.floor_float(var2 * var3);

				if (var4 < MathHelper.ceiling_float_int(var2 * var3) && Math.random() < var2 * var3 - var4) {
					++var4;
				}

				var2 = var4;
			}

			while (var2 > 0) {
				var4 = EntityXPOrb.getXPSplit(var2);
				var2 -= var4;
				thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(thePlayer.worldObj, thePlayer.posX,
						thePlayer.posY + 0.5D, thePlayer.posZ + 0.5D, var4));
			}
		}

		field_75228_b = 0;

		if (p_75208_1_.getItem() == Items.iron_ingot) {
			thePlayer.triggerAchievement(AchievementList.acquireIron);
		}

		if (p_75208_1_.getItem() == Items.cooked_fish) {
			thePlayer.triggerAchievement(AchievementList.cookFish);
		}
	}
}
