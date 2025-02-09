package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipesMapCloning implements IRecipe {

public static final int EaZy = 1256;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000087";

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
		int var3 = 0;
		ItemStack var4 = null;

		for (int var5 = 0; var5 < p_77569_1_.getSizeInventory(); ++var5) {
			final ItemStack var6 = p_77569_1_.getStackInSlot(var5);

			if (var6 != null) {
				if (var6.getItem() == Items.filled_map) {
					if (var4 != null) {
						return false;
					}

					var4 = var6;
				} else {
					if (var6.getItem() != Items.map) {
						return false;
					}

					++var3;
				}
			}
		}

		return var4 != null && var3 > 0;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
		int var2 = 0;
		ItemStack var3 = null;

		for (int var4 = 0; var4 < p_77572_1_.getSizeInventory(); ++var4) {
			final ItemStack var5 = p_77572_1_.getStackInSlot(var4);

			if (var5 != null) {
				if (var5.getItem() == Items.filled_map) {
					if (var3 != null) {
						return null;
					}

					var3 = var5;
				} else {
					if (var5.getItem() != Items.map) {
						return null;
					}

					++var2;
				}
			}
		}

		if (var3 != null && var2 >= 1) {
			final ItemStack var6 = new ItemStack(Items.filled_map, var2 + 1, var3.getMetadata());

			if (var3.hasDisplayName()) {
				var6.setStackDisplayName(var3.getDisplayName());
			}

			return var6;
		} else {
			return null;
		}
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return 9;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
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
}
