package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockGravel extends BlockFalling {

public static final int EaZy = 311;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000252";

	/**
	 * Get the Item that this Block should drop when harvested.
	 * 
	 * @param fortune
	 *            the level of the Fortune enchantment on the player's tool
	 */
	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, int fortune) {
		if (fortune > 3) {
			fortune = 3;
		}

		return rand.nextInt(10 - fortune * 3) == 0 ? Items.flint : Item.getItemFromBlock(this);
	}
}
