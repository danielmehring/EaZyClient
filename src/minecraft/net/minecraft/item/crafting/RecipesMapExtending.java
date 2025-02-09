package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesMapExtending extends ShapedRecipes {

public static final int EaZy = 1257;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000088";

	public RecipesMapExtending() {
		super(3, 3,
				new ItemStack[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper),
						new ItemStack(Items.paper), new ItemStack(Items.filled_map, 0, 32767),
						new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper),
						new ItemStack(Items.paper) },
				new ItemStack(Items.map, 0, 0));
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
		if (!super.matches(p_77569_1_, worldIn)) {
			return false;
		} else {
			ItemStack var3 = null;

			for (int var4 = 0; var4 < p_77569_1_.getSizeInventory() && var3 == null; ++var4) {
				final ItemStack var5 = p_77569_1_.getStackInSlot(var4);

				if (var5 != null && var5.getItem() == Items.filled_map) {
					var3 = var5;
				}
			}

			if (var3 == null) {
				return false;
			} else {
				final MapData var6 = Items.filled_map.getMapData(var3, worldIn);
				return var6 == null ? false : var6.scale < 4;
			}
		}
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
		ItemStack var2 = null;

		for (int var3 = 0; var3 < p_77572_1_.getSizeInventory() && var2 == null; ++var3) {
			final ItemStack var4 = p_77572_1_.getStackInSlot(var3);

			if (var4 != null && var4.getItem() == Items.filled_map) {
				var2 = var4;
			}
		}

		var2 = var2.copy();
		var2.stackSize = 1;

		if (var2.getTagCompound() == null) {
			var2.setTagCompound(new NBTTagCompound());
		}

		var2.getTagCompound().setBoolean("map_is_scaling", true);
		return var2;
	}
}
