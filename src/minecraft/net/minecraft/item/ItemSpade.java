package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Set;

import com.google.common.collect.Sets;

public class ItemSpade extends ItemTool {

public static final int EaZy = 1329;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Set field_150916_c = Sets
			.newHashSet(new Block[] { Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel,
					Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand });
	// private static final String __OBFID = "http://https://fuckuskid00000063";

	public ItemSpade(final Item.ToolMaterial p_i45353_1_) {
		super(1.0F, p_i45353_1_, field_150916_c);
	}

	/**
	 * Check whether this Item can harvest the given Block
	 */
	@Override
	public boolean canHarvestBlock(final Block blockIn) {
		return blockIn == Blocks.snow_layer ? true : blockIn == Blocks.snow;
	}
}
