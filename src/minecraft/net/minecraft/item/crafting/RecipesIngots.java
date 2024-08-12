package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesIngots {

public static final int EaZy = 1255;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Object[][] recipeItems;
	// private static final String __OBFID = "http://https://fuckuskid00000089";

	public RecipesIngots() {
		recipeItems = new Object[][] { { Blocks.gold_block, new ItemStack(Items.gold_ingot, 9) },
				{ Blocks.iron_block, new ItemStack(Items.iron_ingot, 9) },
				{ Blocks.diamond_block, new ItemStack(Items.diamond, 9) },
				{ Blocks.emerald_block, new ItemStack(Items.emerald, 9) },
				{ Blocks.lapis_block, new ItemStack(Items.dye, 9, EnumDyeColor.BLUE.getDyeColorDamage()) },
				{ Blocks.redstone_block, new ItemStack(Items.redstone, 9) },
				{ Blocks.coal_block, new ItemStack(Items.coal, 9, 0) },
				{ Blocks.hay_block, new ItemStack(Items.wheat, 9) },
				{ Blocks.slime_block, new ItemStack(Items.slime_ball, 9) } };
	}

	/**
	 * Adds the ingot recipes to the CraftingManager.
	 */
	public void addRecipes(final CraftingManager p_77590_1_) {
		for (final Object[] recipeItem : recipeItems) {
			final Block var3 = (Block) recipeItem[0];
			final ItemStack var4 = (ItemStack) recipeItem[1];
			p_77590_1_.addRecipe(new ItemStack(var3), new Object[] { "###", "###", "###", '#', var4 });
			p_77590_1_.addRecipe(var4, new Object[] { "#", '#', var3 });
		}

		p_77590_1_.addRecipe(new ItemStack(Items.gold_ingot),
				new Object[] { "###", "###", "###", '#', Items.gold_nugget });
		p_77590_1_.addRecipe(new ItemStack(Items.gold_nugget, 9), new Object[] { "#", '#', Items.gold_ingot });
	}
}
