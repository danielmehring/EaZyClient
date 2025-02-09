package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class ShapelessRecipes implements IRecipe {

public static final int EaZy = 1261;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;

	/** Is a List of ItemStack that composes the recipe. */
	private final List recipeItems;
	// private static final String __OBFID = "http://https://fuckuskid00000094";

	public ShapelessRecipes(final ItemStack p_i1918_1_, final List p_i1918_2_) {
		recipeOutput = p_i1918_1_;
		recipeItems = p_i1918_2_;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return recipeOutput;
	}

	@Override
	public ItemStack[] func_179532_b(final InventoryCrafting p_179532_1_) {
		final ItemStack[] var2 = new ItemStack[p_179532_1_.getSizeInventory()];

		for (int var3 = 0; var3 < var2.length; ++var3) {
			final ItemStack var4 = p_179532_1_.getStackInSlot(var3);

			if (var4 != null && var4.getItem().hasContainerItem()) {
				var2[var3] = new ItemStack(var4.getItem().getContainerItem());
			}
		}

		return var2;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
		final ArrayList var3 = Lists.newArrayList(recipeItems);

		for (int var4 = 0; var4 < p_77569_1_.func_174923_h(); ++var4) {
			for (int var5 = 0; var5 < p_77569_1_.func_174922_i(); ++var5) {
				final ItemStack var6 = p_77569_1_.getStackInRowAndColumn(var5, var4);

				if (var6 != null) {
					boolean var7 = false;
					final Iterator var8 = var3.iterator();

					while (var8.hasNext()) {
						final ItemStack var9 = (ItemStack) var8.next();

						if (var6.getItem() == var9.getItem()
								&& (var9.getMetadata() == 32767 || var6.getMetadata() == var9.getMetadata())) {
							var7 = true;
							var3.remove(var9);
							break;
						}
					}

					if (!var7) {
						return false;
					}
				}
			}
		}

		return var3.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
		return recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return recipeItems.size();
	}
}
