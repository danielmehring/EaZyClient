package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockMelon extends Block {

public static final int EaZy = 328;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000267";

	protected BlockMelon() {
		super(Material.gourd);
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
		return Items.melon;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 3 + random.nextInt(5);
	}

	/**
	 * Get the quantity dropped based on the given fortune level
	 */
	@Override
	public int quantityDroppedWithBonus(final int fortune, final Random random) {
		return Math.min(9, quantityDropped(random) + random.nextInt(1 + fortune));
	}
}
