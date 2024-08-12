package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipes implements IRecipe {

public static final int EaZy = 1260;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** How many horizontal slots this recipe is wide. */
	private final int recipeWidth;

	/** How many vertical slots this recipe uses. */
	private final int recipeHeight;

	/** Is a array of ItemStack that composes the recipe. */
	private final ItemStack[] recipeItems;

	/** Is the ItemStack that you get when craft the recipe. */
	private final ItemStack recipeOutput;
	private boolean field_92101_f;
	// private static final String __OBFID = "http://https://fuckuskid00000093";

	public ShapedRecipes(final int p_i1917_1_, final int p_i1917_2_, final ItemStack[] p_i1917_3_,
			final ItemStack p_i1917_4_) {
		recipeWidth = p_i1917_1_;
		recipeHeight = p_i1917_2_;
		recipeItems = p_i1917_3_;
		recipeOutput = p_i1917_4_;
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
		for (int var3 = 0; var3 <= 3 - recipeWidth; ++var3) {
			for (int var4 = 0; var4 <= 3 - recipeHeight; ++var4) {
				if (checkMatch(p_77569_1_, var3, var4, true)) {
					return true;
				}

				if (checkMatch(p_77569_1_, var3, var4, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	private boolean checkMatch(final InventoryCrafting p_77573_1_, final int p_77573_2_, final int p_77573_3_,
			final boolean p_77573_4_) {
		for (int var5 = 0; var5 < 3; ++var5) {
			for (int var6 = 0; var6 < 3; ++var6) {
				final int var7 = var5 - p_77573_2_;
				final int var8 = var6 - p_77573_3_;
				ItemStack var9 = null;

				if (var7 >= 0 && var8 >= 0 && var7 < recipeWidth && var8 < recipeHeight) {
					if (p_77573_4_) {
						var9 = recipeItems[recipeWidth - var7 - 1 + var8 * recipeWidth];
					} else {
						var9 = recipeItems[var7 + var8 * recipeWidth];
					}
				}

				final ItemStack var10 = p_77573_1_.getStackInRowAndColumn(var5, var6);

				if (var10 != null || var9 != null) {
					if (var10 == null && var9 != null || var10 != null && var9 == null) {
						return false;
					}

					if (var9.getItem() != var10.getItem()) {
						return false;
					}

					if (var9.getMetadata() != 32767 && var9.getMetadata() != var10.getMetadata()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
		final ItemStack var2 = getRecipeOutput().copy();

		if (field_92101_f) {
			for (int var3 = 0; var3 < p_77572_1_.getSizeInventory(); ++var3) {
				final ItemStack var4 = p_77572_1_.getStackInSlot(var3);

				if (var4 != null && var4.hasTagCompound()) {
					var2.setTagCompound((NBTTagCompound) var4.getTagCompound().copy());
				}
			}
		}

		return var2;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return recipeWidth * recipeHeight;
	}

	public ShapedRecipes func_92100_c() {
		field_92101_f = true;
		return this;
	}
}
