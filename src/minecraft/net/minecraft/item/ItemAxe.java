package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Set;

import com.google.common.collect.Sets;

public class ItemAxe extends ItemTool {

public static final int EaZy = 1270;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Set field_150917_c = Sets.newHashSet(new Block[] { Blocks.planks, Blocks.bookshelf, Blocks.log,
			Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder });
	// private static final String __OBFID = "http://https://fuckuskid00001770";

	protected ItemAxe(final Item.ToolMaterial p_i45327_1_) {
		super(3.0F, p_i45327_1_, field_150917_c);
	}

	@Override
	public float getStrVsBlock(final ItemStack stack, final Block p_150893_2_) {
		return p_150893_2_.getMaterial() != Material.wood && p_150893_2_.getMaterial() != Material.plants
				&& p_150893_2_.getMaterial() != Material.vine ? super.getStrVsBlock(stack, p_150893_2_)
						: efficiencyOnProperMaterial;
	}
}
