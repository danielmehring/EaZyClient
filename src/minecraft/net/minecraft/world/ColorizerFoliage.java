package net.minecraft.world;

public class ColorizerFoliage {

public static final int EaZy = 1716;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Color buffer for foliage */
	private static int[] foliageBuffer = new int[65536];
	// private static final String __OBFID = "http://https://fuckuskid00000135";

	public static void setFoliageBiomeColorizer(final int[] p_77467_0_) {
		foliageBuffer = p_77467_0_;
	}

	/**
	 * Gets foliage color from temperature and humidity. Args: temperature,
	 * humidity
	 */
	public static int getFoliageColor(final double p_77470_0_, double p_77470_2_) {
		p_77470_2_ *= p_77470_0_;
		final int var4 = (int) ((1.0D - p_77470_0_) * 255.0D);
		final int var5 = (int) ((1.0D - p_77470_2_) * 255.0D);
		return foliageBuffer[var5 << 8 | var4];
	}

	/**
	 * Gets the foliage color for pine type (metadata 1) trees
	 */
	public static int getFoliageColorPine() {
		return 6396257;
	}

	/**
	 * Gets the foliage color for birch type (metadata 2) trees
	 */
	public static int getFoliageColorBirch() {
		return 8431445;
	}

	public static int getFoliageColorBasic() {
		return 4764952;
	}
}
