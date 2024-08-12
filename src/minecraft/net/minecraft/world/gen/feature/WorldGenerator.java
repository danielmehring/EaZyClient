package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenerator {

public static final int EaZy = 1743;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Sets wither or not the generator should notify blocks of blocks it
	 * changes. When the world is first generated, this is false, when saplings
	 * grow, this is true.
	 */
	private final boolean doBlockNotify;
	// private static final String __OBFID = "http://https://fuckuskid00000409";

	public WorldGenerator() {
		this(false);
	}

	public WorldGenerator(final boolean p_i2013_1_) {
		doBlockNotify = p_i2013_1_;
	}

	public abstract boolean generate(World var1, Random var2, BlockPos var3);

	public void func_175904_e() {}

	protected void func_175906_a(final World worldIn, final BlockPos p_175906_2_, final Block p_175906_3_) {
		func_175905_a(worldIn, p_175906_2_, p_175906_3_, 0);
	}

	protected void func_175905_a(final World worldIn, final BlockPos p_175905_2_, final Block p_175905_3_,
			final int p_175905_4_) {
		func_175903_a(worldIn, p_175905_2_, p_175905_3_.getStateFromMeta(p_175905_4_));
	}

	protected void func_175903_a(final World worldIn, final BlockPos p_175903_2_, final IBlockState p_175903_3_) {
		if (doBlockNotify) {
			worldIn.setBlockState(p_175903_2_, p_175903_3_, 3);
		} else {
			worldIn.setBlockState(p_175903_2_, p_175903_3_, 2);
		}
	}
}
