package net.minecraft.item.crafting;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class RecipesArmorDyes implements IRecipe {

public static final int EaZy = 1250;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000079";

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(final InventoryCrafting p_77569_1_, final World worldIn) {
		ItemStack var3 = null;
		final ArrayList var4 = Lists.newArrayList();

		for (int var5 = 0; var5 < p_77569_1_.getSizeInventory(); ++var5) {
			final ItemStack var6 = p_77569_1_.getStackInSlot(var5);

			if (var6 != null) {
				if (var6.getItem() instanceof ItemArmor) {
					final ItemArmor var7 = (ItemArmor) var6.getItem();

					if (var7.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || var3 != null) {
						return false;
					}

					var3 = var6;
				} else {
					if (var6.getItem() != Items.dye) {
						return false;
					}

					var4.add(var6);
				}
			}
		}

		return var3 != null && !var4.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(final InventoryCrafting p_77572_1_) {
		ItemStack var2 = null;
		final int[] var3 = new int[3];
		int var4 = 0;
		int var5 = 0;
		ItemArmor var6 = null;
		int var7;
		int var9;
		float var10;
		float var11;
		int var17;

		for (var7 = 0; var7 < p_77572_1_.getSizeInventory(); ++var7) {
			final ItemStack var8 = p_77572_1_.getStackInSlot(var7);

			if (var8 != null) {
				if (var8.getItem() instanceof ItemArmor) {
					var6 = (ItemArmor) var8.getItem();

					if (var6.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || var2 != null) {
						return null;
					}

					var2 = var8.copy();
					var2.stackSize = 1;

					if (var6.hasColor(var8)) {
						var9 = var6.getColor(var2);
						var10 = (var9 >> 16 & 255) / 255.0F;
						var11 = (var9 >> 8 & 255) / 255.0F;
						final float var12 = (var9 & 255) / 255.0F;
						var4 = (int) (var4 + Math.max(var10, Math.max(var11, var12)) * 255.0F);
						var3[0] = (int) (var3[0] + var10 * 255.0F);
						var3[1] = (int) (var3[1] + var11 * 255.0F);
						var3[2] = (int) (var3[2] + var12 * 255.0F);
						++var5;
					}
				} else {
					if (var8.getItem() != Items.dye) {
						return null;
					}

					final float[] var14 = EntitySheep.func_175513_a(EnumDyeColor.func_176766_a(var8.getMetadata()));
					final int var15 = (int) (var14[0] * 255.0F);
					final int var16 = (int) (var14[1] * 255.0F);
					var17 = (int) (var14[2] * 255.0F);
					var4 += Math.max(var15, Math.max(var16, var17));
					var3[0] += var15;
					var3[1] += var16;
					var3[2] += var17;
					++var5;
				}
			}
		}

		if (var6 == null) {
			return null;
		} else {
			var7 = var3[0] / var5;
			int var13 = var3[1] / var5;
			var9 = var3[2] / var5;
			var10 = (float) var4 / (float) var5;
			var11 = Math.max(var7, Math.max(var13, var9));
			var7 = (int) (var7 * var10 / var11);
			var13 = (int) (var13 * var10 / var11);
			var9 = (int) (var9 * var10 / var11);
			var17 = (var7 << 8) + var13;
			var17 = (var17 << 8) + var9;
			var6.func_82813_b(var2, var17);
			return var2;
		}
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize() {
		return 10;
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
