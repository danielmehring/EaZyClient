package net.minecraft.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class MapGenBase {

public static final int EaZy = 1798;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The number of Chunks to gen-check in any given direction. */
	protected int range = 8;

	/** The RNG used by the MapGen classes. */
	protected Random rand = new Random();

	/** This world object. */
	protected World worldObj;
	// private static final String __OBFID = "http://https://fuckuskid00000394";

	public void func_175792_a(final IChunkProvider p_175792_1_, final World worldIn, final int p_175792_3_,
			final int p_175792_4_, final ChunkPrimer p_175792_5_) {
		final int var6 = range;
		worldObj = worldIn;
		rand.setSeed(worldIn.getSeed());
		final long var7 = rand.nextLong();
		final long var9 = rand.nextLong();

		for (int var11 = p_175792_3_ - var6; var11 <= p_175792_3_ + var6; ++var11) {
			for (int var12 = p_175792_4_ - var6; var12 <= p_175792_4_ + var6; ++var12) {
				final long var13 = var11 * var7;
				final long var15 = var12 * var9;
				rand.setSeed(var13 ^ var15 ^ worldIn.getSeed());
				func_180701_a(worldIn, var11, var12, p_175792_3_, p_175792_4_, p_175792_5_);
			}
		}
	}

	protected void func_180701_a(final World worldIn, final int p_180701_2_, final int p_180701_3_,
			final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {}
}
