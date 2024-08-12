package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import java.util.Random;

public class BlockPackedIce extends Block {

public static final int EaZy = 342;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000283";

	public BlockPackedIce() {
		super(Material.packedIce);
		slipperiness = 0.98F;
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}
}
