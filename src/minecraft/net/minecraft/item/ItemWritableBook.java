package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemWritableBook extends Item {

public static final int EaZy = 1333;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000076";

	public ItemWritableBook() {
		setMaxStackSize(1);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
		playerIn.displayGUIBook(itemStackIn);
		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemStackIn;
	}

	/**
	 * this method returns true if the book's NBT Tag List "pages" is valid
	 */
	public static boolean validBookPageTagContents(final NBTTagCompound p_150930_0_) {
		if (p_150930_0_ == null) {
			return false;
		} else if (!p_150930_0_.hasKey("pages", 9)) {
			return false;
		} else {
			final NBTTagList var1 = p_150930_0_.getTagList("pages", 8);

			for (int var2 = 0; var2 < var1.tagCount(); ++var2) {
				final String var3 = var1.getStringTagAt(var2);

				if (var3 == null) {
					return false;
				}

				if (var3.length() > 32767) {
					return false;
				}
			}

			return true;
		}
	}
}
