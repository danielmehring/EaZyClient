package net.minecraft.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class WorldGenAbstractTree extends WorldGenerator {

public static final int EaZy = 1732;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000399";

	public WorldGenAbstractTree(final boolean p_i45448_1_) {
		super(p_i45448_1_);
	}

	protected boolean func_150523_a(final Block p_150523_1_) {
		return p_150523_1_.getMaterial() == Material.air || p_150523_1_.getMaterial() == Material.leaves
				|| p_150523_1_ == Blocks.grass || p_150523_1_ == Blocks.dirt || p_150523_1_ == Blocks.log
				|| p_150523_1_ == Blocks.log2 || p_150523_1_ == Blocks.sapling || p_150523_1_ == Blocks.vine;
	}

	public void func_180711_a(final World worldIn, final Random p_180711_2_, final BlockPos p_180711_3_) {}

	protected void func_175921_a(final World worldIn, final BlockPos p_175921_2_) {
		if (worldIn.getBlockState(p_175921_2_).getBlock() != Blocks.dirt) {
			func_175903_a(worldIn, p_175921_2_, Blocks.dirt.getDefaultState());
		}
	}
}
