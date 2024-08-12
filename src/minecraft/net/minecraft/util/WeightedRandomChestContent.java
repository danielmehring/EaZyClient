package net.minecraft.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class WeightedRandomChestContent extends WeightedRandom.Item {

public static final int EaZy = 1664;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The Item/Block ID to generate in the Chest. */
	private final ItemStack theItemId;

	/** The minimum chance of item generating. */
	private final int theMinimumChanceToGenerateItem;

	/** The maximum chance of item generating. */
	private final int theMaximumChanceToGenerateItem;
	// private static final String __OBFID = "http://https://fuckuskid00001505";

	public WeightedRandomChestContent(final Item p_i45311_1_, final int p_i45311_2_, final int p_i45311_3_,
			final int p_i45311_4_, final int p_i45311_5_) {
		super(p_i45311_5_);
		theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
		theMinimumChanceToGenerateItem = p_i45311_3_;
		theMaximumChanceToGenerateItem = p_i45311_4_;
	}

	public WeightedRandomChestContent(final ItemStack p_i1558_1_, final int p_i1558_2_, final int p_i1558_3_,
			final int p_i1558_4_) {
		super(p_i1558_4_);
		theItemId = p_i1558_1_;
		theMinimumChanceToGenerateItem = p_i1558_2_;
		theMaximumChanceToGenerateItem = p_i1558_3_;
	}

	public static void generateChestContents(final Random p_177630_0_, final List p_177630_1_,
			final IInventory p_177630_2_, final int p_177630_3_) {
		for (int var4 = 0; var4 < p_177630_3_; ++var4) {
			final WeightedRandomChestContent var5 = (WeightedRandomChestContent) WeightedRandom
					.getRandomItem(p_177630_0_, p_177630_1_);
			final int var6 = var5.theMinimumChanceToGenerateItem + p_177630_0_
					.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);

			if (var5.theItemId.getMaxStackSize() >= var6) {
				final ItemStack var7 = var5.theItemId.copy();
				var7.stackSize = var6;
				p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var7);
			} else {
				for (int var9 = 0; var9 < var6; ++var9) {
					final ItemStack var8 = var5.theItemId.copy();
					var8.stackSize = 1;
					p_177630_2_.setInventorySlotContents(p_177630_0_.nextInt(p_177630_2_.getSizeInventory()), var8);
				}
			}
		}
	}

	public static void func_177631_a(final Random p_177631_0_, final List p_177631_1_,
			final TileEntityDispenser p_177631_2_, final int p_177631_3_) {
		for (int var4 = 0; var4 < p_177631_3_; ++var4) {
			final WeightedRandomChestContent var5 = (WeightedRandomChestContent) WeightedRandom
					.getRandomItem(p_177631_0_, p_177631_1_);
			final int var6 = var5.theMinimumChanceToGenerateItem + p_177631_0_
					.nextInt(var5.theMaximumChanceToGenerateItem - var5.theMinimumChanceToGenerateItem + 1);

			if (var5.theItemId.getMaxStackSize() >= var6) {
				final ItemStack var7 = var5.theItemId.copy();
				var7.stackSize = var6;
				p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var7);
			} else {
				for (int var9 = 0; var9 < var6; ++var9) {
					final ItemStack var8 = var5.theItemId.copy();
					var8.stackSize = 1;
					p_177631_2_.setInventorySlotContents(p_177631_0_.nextInt(p_177631_2_.getSizeInventory()), var8);
				}
			}
		}
	}

	public static List func_177629_a(final List p_177629_0_, final WeightedRandomChestContent... p_177629_1_) {
		final ArrayList var2 = Lists.newArrayList(p_177629_0_);
		Collections.addAll(var2, p_177629_1_);
		return var2;
	}
}
