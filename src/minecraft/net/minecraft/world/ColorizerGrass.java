package net.minecraft.world;

public class ColorizerGrass {

public static final int EaZy = 1717;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Color buffer for grass */
	private static int[] grassBuffer = new int[65536];
	// private static final String __OBFID = "http://https://fuckuskid00000138";

	public static void setGrassBiomeColorizer(final int[] p_77479_0_) {
		grassBuffer = p_77479_0_;
	}

	/**
	 * Gets grass color from temperature and humidity. Args: temperature,
	 * humidity
	 */
	public static int getGrassColor(final double p_77480_0_, double p_77480_2_) {
		p_77480_2_ *= p_77480_0_;
		final int var4 = (int) ((1.0D - p_77480_0_) * 255.0D);
		final int var5 = (int) ((1.0D - p_77480_2_) * 255.0D);
		final int var6 = var5 << 8 | var4;
		return var6 > grassBuffer.length ? -65281 : grassBuffer[var6];
	}
}
