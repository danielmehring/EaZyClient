package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBarrier extends Block {

public static final int EaZy = 257;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002139";

	protected BlockBarrier() {
		super(Material.barrier);
		setBlockUnbreakable();
		setResistance(6000001.0F);
		disableStats();
		translucent = true;
		setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * Returns the default ambient occlusion value based on block opacity
	 */
	@Override
	public float getAmbientOcclusionLightValue() {
		return 1.0F;
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 * 
	 * @param chance
	 *            The chance that each Item is actually spawned (1.0 = always,
	 *            0.0 = never)
	 * @param fortune
	 *            The player's fortune level
	 */
	@Override
	public void dropBlockAsItemWithChance(final World worldIn, final BlockPos pos, final IBlockState state,
			final float chance, final int fortune) {}
}
