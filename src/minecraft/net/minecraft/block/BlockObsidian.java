package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockObsidian extends Block {

public static final int EaZy = 338;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000279";

	public BlockObsidian() {
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
		return Item.getItemFromBlock(Blocks.obsidian);
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(final IBlockState state) {
		return MapColor.obsidianColor;
	}
}
