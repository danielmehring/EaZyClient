package net.minecraft.world.gen.layer;

public class GenLayerFuzzyZoom extends GenLayerZoom {

public static final int EaZy = 1784;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000556";

	public GenLayerFuzzyZoom(final long p_i2123_1_, final GenLayer p_i2123_3_) {
		super(p_i2123_1_, p_i2123_3_);
	}

	/**
	 * returns the most frequently occurring number of the set, or a random
	 * number from those provided
	 */
	@Override
	protected int selectModeOrRandom(final int p_151617_1_, final int p_151617_2_, final int p_151617_3_,
			final int p_151617_4_) {
		return selectRandom(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ });
	}
}
