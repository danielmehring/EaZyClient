package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockWall;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CraftingManager {

public static final int EaZy = 1243;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The static instance of this class */
	private static final CraftingManager instance = new CraftingManager();

	/** A list of all the recipes added */
	private final List recipes = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00000090";

	/**
	 * Returns the static instance of this class
	 */
	public static CraftingManager getInstance() {
		return instance;
	}

	private CraftingManager() {
		new RecipesTools().addRecipes(this);
		new RecipesWeapons().addRecipes(this);
		new RecipesIngots().addRecipes(this);
		new RecipesFood().addRecipes(this);
		new RecipesCrafting().addRecipes(this);
		new RecipesArmor().addRecipes(this);
		new RecipesDyes().addRecipes(this);
		recipes.add(new RecipesArmorDyes());
		recipes.add(new RecipeBookCloning());
		recipes.add(new RecipesMapCloning());
		recipes.add(new RecipesMapExtending());
		recipes.add(new RecipeFireworks());
		recipes.add(new RecipeRepairItem());
		new RecipesBanners().func_179534_a(this);
		addRecipe(new ItemStack(Items.paper, 3), new Object[] { "###", '#', Items.reeds });
		addShapelessRecipe(new ItemStack(Items.book, 1),
				new Object[] { Items.paper, Items.paper, Items.paper, Items.leather });
		addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[] { Items.book,
				new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), Items.feather });
		addRecipe(new ItemStack(Blocks.oak_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.birch_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.spruce_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.jungle_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.acacia_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.dark_oak_fence, 3), new Object[] { "W#W", "W#W", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.NORMAL.func_176657_a()),
				new Object[] { "###", "###", '#', Blocks.cobblestone });
		addRecipe(new ItemStack(Blocks.cobblestone_wall, 6, BlockWall.EnumType.MOSSY.func_176657_a()),
				new Object[] { "###", "###", '#', Blocks.mossy_cobblestone });
		addRecipe(new ItemStack(Blocks.nether_brick_fence, 6), new Object[] { "###", "###", '#', Blocks.nether_brick });
		addRecipe(new ItemStack(Blocks.oak_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.birch_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.spruce_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.jungle_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.acacia_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.dark_oak_fence_gate, 1), new Object[] { "#W#", "#W#", '#', Items.stick, 'W',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.jukebox, 1),
				new Object[] { "###", "#X#", "###", '#', Blocks.planks, 'X', Items.diamond });
		addRecipe(new ItemStack(Items.lead, 2),
				new Object[] { "~~ ", "~O ", "  ~", '~', Items.string, 'O', Items.slime_ball });
		addRecipe(new ItemStack(Blocks.noteblock, 1),
				new Object[] { "###", "#X#", "###", '#', Blocks.planks, 'X', Items.redstone });
		addRecipe(new ItemStack(Blocks.bookshelf, 1),
				new Object[] { "###", "XXX", "###", '#', Blocks.planks, 'X', Items.book });
		addRecipe(new ItemStack(Blocks.snow, 1), new Object[] { "##", "##", '#', Items.snowball });
		addRecipe(new ItemStack(Blocks.snow_layer, 6), new Object[] { "###", '#', Blocks.snow });
		addRecipe(new ItemStack(Blocks.clay, 1), new Object[] { "##", "##", '#', Items.clay_ball });
		addRecipe(new ItemStack(Blocks.brick_block, 1), new Object[] { "##", "##", '#', Items.brick });
		addRecipe(new ItemStack(Blocks.glowstone, 1), new Object[] { "##", "##", '#', Items.glowstone_dust });
		addRecipe(new ItemStack(Blocks.quartz_block, 1), new Object[] { "##", "##", '#', Items.quartz });
		addRecipe(new ItemStack(Blocks.wool, 1), new Object[] { "##", "##", '#', Items.string });
		addRecipe(new ItemStack(Blocks.tnt, 1),
				new Object[] { "X#X", "#X#", "X#X", 'X', Items.gunpowder, '#', Blocks.sand });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.COBBLESTONE.func_176624_a()),
				new Object[] { "###", '#', Blocks.cobblestone });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.STONE.func_176624_a()),
				new Object[] { "###", '#', new ItemStack(Blocks.stone, BlockStone.EnumType.STONE.getMetaFromState()) });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SAND.func_176624_a()),
				new Object[] { "###", '#', Blocks.sandstone });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.BRICK.func_176624_a()),
				new Object[] { "###", '#', Blocks.brick_block });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.SMOOTHBRICK.func_176624_a()),
				new Object[] { "###", '#', Blocks.stonebrick });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.NETHERBRICK.func_176624_a()),
				new Object[] { "###", '#', Blocks.nether_brick });
		addRecipe(new ItemStack(Blocks.stone_slab, 6, BlockStoneSlab.EnumType.QUARTZ.func_176624_a()),
				new Object[] { "###", '#', Blocks.quartz_block });
		addRecipe(new ItemStack(Blocks.stone_slab2, 6, BlockStoneSlabNew.EnumType.RED_SANDSTONE.func_176915_a()),
				new Object[] { "###", '#', Blocks.red_sandstone });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, 0),
				new Object[] { "###", '#', new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks.EnumType.BIRCH.func_176839_a()), new Object[] {
				"###", '#', new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks.EnumType.SPRUCE.func_176839_a()), new Object[] {
				"###", '#', new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, BlockPlanks.EnumType.JUNGLE.func_176839_a()), new Object[] {
				"###", '#', new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4),
				new Object[] { "###", '#',
						new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.wooden_slab, 6, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4),
				new Object[] { "###", '#',
						new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.ladder, 3), new Object[] { "# #", "###", "# #", '#', Items.stick });
		addRecipe(new ItemStack(Items.oak_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Items.spruce_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Items.birch_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Items.jungle_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Items.acacia_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.ACACIA.func_176839_a()) });
		addRecipe(new ItemStack(Items.dark_oak_door, 3), new Object[] { "##", "##", "##", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.DARK_OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.trapdoor, 2), new Object[] { "###", "###", '#', Blocks.planks });
		addRecipe(new ItemStack(Items.iron_door, 3), new Object[] { "##", "##", "##", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Blocks.iron_trapdoor, 1), new Object[] { "##", "##", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Items.sign, 3),
				new Object[] { "###", "###", " X ", '#', Blocks.planks, 'X', Items.stick });
		addRecipe(new ItemStack(Items.cake, 1), new Object[] { "AAA", "BEB", "CCC", 'A', Items.milk_bucket, 'B',
				Items.sugar, 'C', Items.wheat, 'E', Items.egg });
		addRecipe(new ItemStack(Items.sugar, 1), new Object[] { "#", '#', Items.reeds });
		addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.OAK.func_176839_a()),
				new Object[] { "#", '#', new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.SPRUCE.func_176839_a()),
				new Object[] { "#", '#', new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.BIRCH.func_176839_a()),
				new Object[] { "#", '#', new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.planks, 4, BlockPlanks.EnumType.JUNGLE.func_176839_a()),
				new Object[] { "#", '#', new ItemStack(Blocks.log, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4), new Object[] {
				"#", '#', new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.ACACIA.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.planks, 4, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4), new Object[] {
				"#", '#', new ItemStack(Blocks.log2, 1, BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4) });
		addRecipe(new ItemStack(Items.stick, 4), new Object[] { "#", "#", '#', Blocks.planks });
		addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "#", 'X', Items.coal, '#', Items.stick });
		addRecipe(new ItemStack(Blocks.torch, 4),
				new Object[] { "X", "#", 'X', new ItemStack(Items.coal, 1, 1), '#', Items.stick });
		addRecipe(new ItemStack(Items.bowl, 4), new Object[] { "# #", " # ", '#', Blocks.planks });
		addRecipe(new ItemStack(Items.glass_bottle, 3), new Object[] { "# #", " # ", '#', Blocks.glass });
		addRecipe(new ItemStack(Blocks.rail, 16),
				new Object[] { "X X", "X#X", "X X", 'X', Items.iron_ingot, '#', Items.stick });
		addRecipe(new ItemStack(Blocks.golden_rail, 6),
				new Object[] { "X X", "X#X", "XRX", 'X', Items.gold_ingot, 'R', Items.redstone, '#', Items.stick });
		addRecipe(new ItemStack(Blocks.activator_rail, 6), new Object[] { "XSX", "X#X", "XSX", 'X', Items.iron_ingot,
				'#', Blocks.redstone_torch, 'S', Items.stick });
		addRecipe(new ItemStack(Blocks.detector_rail, 6), new Object[] { "X X", "X#X", "XRX", 'X', Items.iron_ingot,
				'R', Items.redstone, '#', Blocks.stone_pressure_plate });
		addRecipe(new ItemStack(Items.minecart, 1), new Object[] { "# #", "###", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Items.cauldron, 1), new Object[] { "# #", "# #", "###", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Items.brewing_stand, 1),
				new Object[] { " B ", "###", '#', Blocks.cobblestone, 'B', Items.blaze_rod });
		addRecipe(new ItemStack(Blocks.lit_pumpkin, 1),
				new Object[] { "A", "B", 'A', Blocks.pumpkin, 'B', Blocks.torch });
		addRecipe(new ItemStack(Items.chest_minecart, 1),
				new Object[] { "A", "B", 'A', Blocks.chest, 'B', Items.minecart });
		addRecipe(new ItemStack(Items.furnace_minecart, 1),
				new Object[] { "A", "B", 'A', Blocks.furnace, 'B', Items.minecart });
		addRecipe(new ItemStack(Items.tnt_minecart, 1),
				new Object[] { "A", "B", 'A', Blocks.tnt, 'B', Items.minecart });
		addRecipe(new ItemStack(Items.hopper_minecart, 1),
				new Object[] { "A", "B", 'A', Blocks.hopper, 'B', Items.minecart });
		addRecipe(new ItemStack(Items.boat, 1), new Object[] { "# #", "###", '#', Blocks.planks });
		addRecipe(new ItemStack(Items.bucket, 1), new Object[] { "# #", " # ", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Items.flower_pot, 1), new Object[] { "# #", " # ", '#', Items.brick });
		addShapelessRecipe(new ItemStack(Items.flint_and_steel, 1),
				new Object[] { new ItemStack(Items.iron_ingot, 1), new ItemStack(Items.flint, 1) });
		addRecipe(new ItemStack(Items.bread, 1), new Object[] { "###", '#', Items.wheat });
		addRecipe(new ItemStack(Blocks.oak_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.OAK.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.birch_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.BIRCH.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.spruce_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.SPRUCE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.jungle_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, BlockPlanks.EnumType.JUNGLE.func_176839_a()) });
		addRecipe(new ItemStack(Blocks.acacia_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.ACACIA.func_176839_a() - 4) });
		addRecipe(new ItemStack(Blocks.dark_oak_stairs, 4), new Object[] { "#  ", "## ", "###", '#',
				new ItemStack(Blocks.planks, 1, 4 + BlockPlanks.EnumType.DARK_OAK.func_176839_a() - 4) });
		addRecipe(new ItemStack(Items.fishing_rod, 1),
				new Object[] { "  #", " #X", "# X", '#', Items.stick, 'X', Items.string });
		addRecipe(new ItemStack(Items.carrot_on_a_stick, 1),
				new Object[] { "# ", " X", '#', Items.fishing_rod, 'X', Items.carrot }).func_92100_c();
		addRecipe(new ItemStack(Blocks.stone_stairs, 4), new Object[] { "#  ", "## ", "###", '#', Blocks.cobblestone });
		addRecipe(new ItemStack(Blocks.brick_stairs, 4), new Object[] { "#  ", "## ", "###", '#', Blocks.brick_block });
		addRecipe(new ItemStack(Blocks.stone_brick_stairs, 4),
				new Object[] { "#  ", "## ", "###", '#', Blocks.stonebrick });
		addRecipe(new ItemStack(Blocks.nether_brick_stairs, 4),
				new Object[] { "#  ", "## ", "###", '#', Blocks.nether_brick });
		addRecipe(new ItemStack(Blocks.sandstone_stairs, 4),
				new Object[] { "#  ", "## ", "###", '#', Blocks.sandstone });
		addRecipe(new ItemStack(Blocks.red_sandstone_stairs, 4),
				new Object[] { "#  ", "## ", "###", '#', Blocks.red_sandstone });
		addRecipe(new ItemStack(Blocks.quartz_stairs, 4),
				new Object[] { "#  ", "## ", "###", '#', Blocks.quartz_block });
		addRecipe(new ItemStack(Items.painting, 1),
				new Object[] { "###", "#X#", "###", '#', Items.stick, 'X', Blocks.wool });
		addRecipe(new ItemStack(Items.item_frame, 1),
				new Object[] { "###", "#X#", "###", '#', Items.stick, 'X', Items.leather });
		addRecipe(new ItemStack(Items.golden_apple, 1, 0),
				new Object[] { "###", "#X#", "###", '#', Items.gold_ingot, 'X', Items.apple });
		addRecipe(new ItemStack(Items.golden_apple, 1, 1),
				new Object[] { "###", "#X#", "###", '#', Blocks.gold_block, 'X', Items.apple });
		addRecipe(new ItemStack(Items.golden_carrot, 1, 0),
				new Object[] { "###", "#X#", "###", '#', Items.gold_nugget, 'X', Items.carrot });
		addRecipe(new ItemStack(Items.speckled_melon, 1),
				new Object[] { "###", "#X#", "###", '#', Items.gold_nugget, 'X', Items.melon });
		addRecipe(new ItemStack(Blocks.lever, 1), new Object[] { "X", "#", '#', Blocks.cobblestone, 'X', Items.stick });
		addRecipe(new ItemStack(Blocks.tripwire_hook, 2),
				new Object[] { "I", "S", "#", '#', Blocks.planks, 'S', Items.stick, 'I', Items.iron_ingot });
		addRecipe(new ItemStack(Blocks.redstone_torch, 1),
				new Object[] { "X", "#", '#', Items.stick, 'X', Items.redstone });
		addRecipe(new ItemStack(Items.repeater, 1), new Object[] { "#X#", "III", '#', Blocks.redstone_torch, 'X',
				Items.redstone, 'I', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetaFromState()) });
		addRecipe(new ItemStack(Items.comparator, 1), new Object[] { " # ", "#X#", "III", '#', Blocks.redstone_torch,
				'X', Items.quartz, 'I', new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetaFromState()) });
		addRecipe(new ItemStack(Items.clock, 1),
				new Object[] { " # ", "#X#", " # ", '#', Items.gold_ingot, 'X', Items.redstone });
		addRecipe(new ItemStack(Items.compass, 1),
				new Object[] { " # ", "#X#", " # ", '#', Items.iron_ingot, 'X', Items.redstone });
		addRecipe(new ItemStack(Items.map, 1),
				new Object[] { "###", "#X#", "###", '#', Items.paper, 'X', Items.compass });
		addRecipe(new ItemStack(Blocks.stone_button, 1), new Object[] { "#", '#',
				new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetaFromState()) });
		addRecipe(new ItemStack(Blocks.wooden_button, 1), new Object[] { "#", '#', Blocks.planks });
		addRecipe(new ItemStack(Blocks.stone_pressure_plate, 1), new Object[] { "##", '#',
				new ItemStack(Blocks.stone, 1, BlockStone.EnumType.STONE.getMetaFromState()) });
		addRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), new Object[] { "##", '#', Blocks.planks });
		addRecipe(new ItemStack(Blocks.heavy_weighted_pressure_plate, 1), new Object[] { "##", '#', Items.iron_ingot });
		addRecipe(new ItemStack(Blocks.light_weighted_pressure_plate, 1), new Object[] { "##", '#', Items.gold_ingot });
		addRecipe(new ItemStack(Blocks.dispenser, 1),
				new Object[] { "###", "#X#", "#R#", '#', Blocks.cobblestone, 'X', Items.bow, 'R', Items.redstone });
		addRecipe(new ItemStack(Blocks.dropper, 1),
				new Object[] { "###", "# #", "#R#", '#', Blocks.cobblestone, 'R', Items.redstone });
		addRecipe(new ItemStack(Blocks.piston, 1), new Object[] { "TTT", "#X#", "#R#", '#', Blocks.cobblestone, 'X',
				Items.iron_ingot, 'R', Items.redstone, 'T', Blocks.planks });
		addRecipe(new ItemStack(Blocks.sticky_piston, 1),
				new Object[] { "S", "P", 'S', Items.slime_ball, 'P', Blocks.piston });
		addRecipe(new ItemStack(Items.bed, 1), new Object[] { "###", "XXX", '#', Blocks.wool, 'X', Blocks.planks });
		addRecipe(new ItemStack(Blocks.enchanting_table, 1),
				new Object[] { " B ", "D#D", "###", '#', Blocks.obsidian, 'B', Items.book, 'D', Items.diamond });
		addRecipe(new ItemStack(Blocks.anvil, 1),
				new Object[] { "III", " i ", "iii", 'I', Blocks.iron_block, 'i', Items.iron_ingot });
		addRecipe(new ItemStack(Items.leather), new Object[] { "##", "##", '#', Items.rabbit_hide });
		addShapelessRecipe(new ItemStack(Items.ender_eye, 1), new Object[] { Items.ender_pearl, Items.blaze_powder });
		addShapelessRecipe(new ItemStack(Items.fire_charge, 3),
				new Object[] { Items.gunpowder, Items.blaze_powder, Items.coal });
		addShapelessRecipe(new ItemStack(Items.fire_charge, 3),
				new Object[] { Items.gunpowder, Items.blaze_powder, new ItemStack(Items.coal, 1, 1) });
		addRecipe(new ItemStack(Blocks.daylight_detector),
				new Object[] { "GGG", "QQQ", "WWW", 'G', Blocks.glass, 'Q', Items.quartz, 'W', Blocks.wooden_slab });
		addRecipe(new ItemStack(Blocks.hopper),
				new Object[] { "I I", "ICI", " I ", 'I', Items.iron_ingot, 'C', Blocks.chest });
		addRecipe(new ItemStack(Items.armor_stand, 1), new Object[] { "///", " / ", "/_/", '/', Items.stick, '_',
				new ItemStack(Blocks.stone_slab, 1, BlockStoneSlab.EnumType.STONE.func_176624_a()) });
		Collections.sort(recipes, new Comparator() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000091";
			public int compare(final IRecipe p_compare_1_, final IRecipe p_compare_2_) {
				return p_compare_1_ instanceof ShapelessRecipes && p_compare_2_ instanceof ShapedRecipes ? 1
						: p_compare_2_ instanceof ShapelessRecipes && p_compare_1_ instanceof ShapedRecipes ? -1
								: p_compare_2_.getRecipeSize() < p_compare_1_.getRecipeSize() ? -1
										: p_compare_2_.getRecipeSize() > p_compare_1_.getRecipeSize() ? 1 : 0;
			}

			@Override
			public int compare(final Object p_compare_1_, final Object p_compare_2_) {
				return this.compare((IRecipe) p_compare_1_, (IRecipe) p_compare_2_);
			}
		});
	}

	public ShapedRecipes addRecipe(final ItemStack p_92103_1_, final Object... p_92103_2_) {
		String var3 = "";
		int var4 = 0;
		int var5 = 0;
		int var6 = 0;

		if (p_92103_2_[var4] instanceof String[]) {
			final String[] var7 = (String[]) p_92103_2_[var4++];

			for (final String var9 : var7) {
				++var6;
				var5 = var9.length();
				var3 = var3 + var9;
			}
		} else {
			while (p_92103_2_[var4] instanceof String) {
				final String var11 = (String) p_92103_2_[var4++];
				++var6;
				var5 = var11.length();
				var3 = var3 + var11;
			}
		}

		HashMap var12;

		for (var12 = Maps.newHashMap(); var4 < p_92103_2_.length; var4 += 2) {
			final Character var13 = (Character) p_92103_2_[var4];
			ItemStack var15 = null;

			if (p_92103_2_[var4 + 1] instanceof Item) {
				var15 = new ItemStack((Item) p_92103_2_[var4 + 1]);
			} else if (p_92103_2_[var4 + 1] instanceof Block) {
				var15 = new ItemStack((Block) p_92103_2_[var4 + 1], 1, 32767);
			} else if (p_92103_2_[var4 + 1] instanceof ItemStack) {
				var15 = (ItemStack) p_92103_2_[var4 + 1];
			}

			var12.put(var13, var15);
		}

		final ItemStack[] var14 = new ItemStack[var5 * var6];

		for (int var16 = 0; var16 < var5 * var6; ++var16) {
			final char var10 = var3.charAt(var16);

			if (var12.containsKey(var10)) {
				var14[var16] = ((ItemStack) var12.get(var10)).copy();
			} else {
				var14[var16] = null;
			}
		}

		final ShapedRecipes var17 = new ShapedRecipes(var5, var6, var14, p_92103_1_);
		recipes.add(var17);
		return var17;
	}

	public void addShapelessRecipe(final ItemStack p_77596_1_, final Object... p_77596_2_) {
		final ArrayList var3 = Lists.newArrayList();
		final Object[] var4 = p_77596_2_;
		final int var5 = p_77596_2_.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			final Object var7 = var4[var6];

			if (var7 instanceof ItemStack) {
				var3.add(((ItemStack) var7).copy());
			} else if (var7 instanceof Item) {
				var3.add(new ItemStack((Item) var7));
			} else {
				if (!(var7 instanceof Block)) {
					throw new IllegalArgumentException(
							"Invalid shapeless recipe: unknown type " + var7.getClass().getName() + "!");
				}

				var3.add(new ItemStack((Block) var7));
			}
		}

		recipes.add(new ShapelessRecipes(p_77596_1_, var3));
	}

	public void func_180302_a(final IRecipe p_180302_1_) {
		recipes.add(p_180302_1_);
	}

	public ItemStack findMatchingRecipe(final InventoryCrafting p_82787_1_, final World worldIn) {
		final Iterator var3 = recipes.iterator();
		IRecipe var4;

		do {
			if (!var3.hasNext()) {
				return null;
			}

			var4 = (IRecipe) var3.next();
		}
		while (!var4.matches(p_82787_1_, worldIn));

		return var4.getCraftingResult(p_82787_1_);
	}

	public ItemStack[] func_180303_b(final InventoryCrafting p_180303_1_, final World worldIn) {
		final Iterator var3 = recipes.iterator();

		while (var3.hasNext()) {
			final IRecipe var4 = (IRecipe) var3.next();

			if (var4.matches(p_180303_1_, worldIn)) {
				return var4.func_179532_b(p_180303_1_);
			}
		}

		final ItemStack[] var5 = new ItemStack[p_180303_1_.getSizeInventory()];

		for (int var6 = 0; var6 < var5.length; ++var6) {
			var5[var6] = p_180303_1_.getStackInSlot(var6);
		}

		return var5;
	}

	/**
	 * returns the List<> of all recipes
	 */
	public List getRecipeList() {
		return recipes;
	}
}
