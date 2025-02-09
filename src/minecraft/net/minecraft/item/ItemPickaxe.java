package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

import com.google.common.collect.Sets;

public class ItemPickaxe extends ItemTool {

public static final int EaZy = 1312;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Set effectiveBlocks = Sets.newHashSet(new Block[] { Blocks.activator_rail, Blocks.coal_ore,
			Blocks.cobblestone, Blocks.detector_rail, Blocks.diamond_block, Blocks.diamond_ore,
			Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice,
			Blocks.iron_block, Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore,
			Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, Blocks.redstone_ore,
			Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab });
	// private static final String __OBFID = "http://https://fuckuskid00000053";

	protected ItemPickaxe(final Item.ToolMaterial p_i45347_1_) {
		super(2.0F, p_i45347_1_, effectiveBlocks);
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean canHarvestBlock(final Block blockIn) {
		return blockIn == Blocks.obsidian ? toolMaterial.getHarvestLevel() == 3
				: blockIn != Blocks.diamond_block && blockIn != Blocks.diamond_ore ? blockIn != Blocks.emerald_ore
						&& blockIn != Blocks.emerald_block
								? blockIn != Blocks.gold_block && blockIn != Blocks.gold_ore
										? blockIn != Blocks.iron_block && blockIn != Blocks.iron_ore
												? blockIn != Blocks.lapis_block && blockIn != Blocks.lapis_ore
														? blockIn != Blocks.redstone_ore
																&& blockIn != Blocks.lit_redstone_ore
																		? blockIn.getMaterial() == Material.rock ? true
																				: blockIn.getMaterial() == Material.iron
																						? true
																						: blockIn
																								.getMaterial() == Material.anvil
																		: toolMaterial.getHarvestLevel() >= 2
														: toolMaterial.getHarvestLevel() >= 1
												: toolMaterial.getHarvestLevel() >= 1
										: toolMaterial.getHarvestLevel() >= 2
								: toolMaterial.getHarvestLevel() >= 2
						: toolMaterial.getHarvestLevel() >= 2;
	}

	@Override
	public float getStrVsBlock(final ItemStack stack, final Block p_150893_2_) {
		return p_150893_2_.getMaterial() != Material.iron && p_150893_2_.getMaterial() != Material.anvil
				&& p_150893_2_.getMaterial() != Material.rock ? super.getStrVsBlock(stack, p_150893_2_)
						: efficiencyOnProperMaterial;
	}
}
