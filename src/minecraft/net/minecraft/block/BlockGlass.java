package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

import java.util.Random;

public class BlockGlass extends BlockBreakable {

public static final int EaZy = 308;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000249";

	public BlockGlass(final Material p_i45408_1_, final boolean p_i45408_2_) {
		super(p_i45408_1_, p_i45408_2_);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(final Random random) {
		return 0;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}
}
