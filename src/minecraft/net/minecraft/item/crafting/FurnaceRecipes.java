package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class FurnaceRecipes {

public static final int EaZy = 1244;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();

	/** The list of smelting results. */
	private final Map smeltingList = Maps.newHashMap();
	private final Map experienceList = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000085";

	public static FurnaceRecipes instance() {
		return smeltingBase;
	}

	private FurnaceRecipes() {
		addSmeltingRecipeForBlock(Blocks.iron_ore, new ItemStack(Items.iron_ingot), 0.7F);
		addSmeltingRecipeForBlock(Blocks.gold_ore, new ItemStack(Items.gold_ingot), 1.0F);
		addSmeltingRecipeForBlock(Blocks.diamond_ore, new ItemStack(Items.diamond), 1.0F);
		addSmeltingRecipeForBlock(Blocks.sand, new ItemStack(Blocks.glass), 0.1F);
		addSmelting(Items.porkchop, new ItemStack(Items.cooked_porkchop), 0.35F);
		addSmelting(Items.beef, new ItemStack(Items.cooked_beef), 0.35F);
		addSmelting(Items.chicken, new ItemStack(Items.cooked_chicken), 0.35F);
		addSmelting(Items.rabbit, new ItemStack(Items.cooked_rabbit), 0.35F);
		addSmelting(Items.mutton, new ItemStack(Items.cooked_mutton), 0.35F);
		addSmeltingRecipeForBlock(Blocks.cobblestone, new ItemStack(Blocks.stone), 0.1F);
		addSmeltingRecipe(new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.DEFAULT_META),
				new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.CRACKED_META), 0.1F);
		addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3F);
		addSmeltingRecipeForBlock(Blocks.clay, new ItemStack(Blocks.hardened_clay), 0.35F);
		addSmeltingRecipeForBlock(Blocks.cactus, new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()),
				0.2F);
		addSmeltingRecipeForBlock(Blocks.log, new ItemStack(Items.coal, 1, 1), 0.15F);
		addSmeltingRecipeForBlock(Blocks.log2, new ItemStack(Items.coal, 1, 1), 0.15F);
		addSmeltingRecipeForBlock(Blocks.emerald_ore, new ItemStack(Items.emerald), 1.0F);
		addSmelting(Items.potato, new ItemStack(Items.baked_potato), 0.35F);
		addSmeltingRecipeForBlock(Blocks.netherrack, new ItemStack(Items.netherbrick), 0.1F);
		addSmeltingRecipe(new ItemStack(Blocks.sponge, 1, 1), new ItemStack(Blocks.sponge, 1, 0), 0.15F);
		final ItemFishFood.FishType[] var1 = ItemFishFood.FishType.values();
		final int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			final ItemFishFood.FishType var4 = var1[var3];

			if (var4.getCookable()) {
				addSmeltingRecipe(new ItemStack(Items.fish, 1, var4.getItemDamage()),
						new ItemStack(Items.cooked_fish, 1, var4.getItemDamage()), 0.35F);
			}
		}

		addSmeltingRecipeForBlock(Blocks.coal_ore, new ItemStack(Items.coal), 0.1F);
		addSmeltingRecipeForBlock(Blocks.redstone_ore, new ItemStack(Items.redstone), 0.7F);
		addSmeltingRecipeForBlock(Blocks.lapis_ore, new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
				0.2F);
		addSmeltingRecipeForBlock(Blocks.quartz_ore, new ItemStack(Items.quartz), 0.2F);
	}

	public void addSmeltingRecipeForBlock(final Block p_151393_1_, final ItemStack p_151393_2_,
			final float p_151393_3_) {
		addSmelting(Item.getItemFromBlock(p_151393_1_), p_151393_2_, p_151393_3_);
	}

	public void addSmelting(final Item p_151396_1_, final ItemStack p_151396_2_, final float p_151396_3_) {
		addSmeltingRecipe(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
	}

	public void addSmeltingRecipe(final ItemStack p_151394_1_, final ItemStack p_151394_2_, final float p_151394_3_) {
		smeltingList.put(p_151394_1_, p_151394_2_);
		experienceList.put(p_151394_2_, p_151394_3_);
	}

	/**
	 * Returns the smelting result of an item.
	 */
	public ItemStack getSmeltingResult(final ItemStack p_151395_1_) {
		final Iterator var2 = smeltingList.entrySet().iterator();
		Entry var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (Entry) var2.next();
		}
		while (!func_151397_a(p_151395_1_, (ItemStack) var3.getKey()));

		return (ItemStack) var3.getValue();
	}

	private boolean func_151397_a(final ItemStack p_151397_1_, final ItemStack p_151397_2_) {
		return p_151397_2_.getItem() == p_151397_1_.getItem()
				&& (p_151397_2_.getMetadata() == 32767 || p_151397_2_.getMetadata() == p_151397_1_.getMetadata());
	}

	public Map getSmeltingList() {
		return smeltingList;
	}

	public float getSmeltingExperience(final ItemStack p_151398_1_) {
		final Iterator var2 = experienceList.entrySet().iterator();
		Entry var3;

		do {
			if (!var2.hasNext()) {
				return 0.0F;
			}

			var3 = (Entry) var2.next();
		}
		while (!func_151397_a(p_151398_1_, (ItemStack) var3.getKey()));

		return ((Float) var3.getValue());
	}
}
