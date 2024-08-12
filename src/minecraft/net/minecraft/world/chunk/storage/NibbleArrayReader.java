package net.minecraft.world.chunk.storage;

public class NibbleArrayReader {

public static final int EaZy = 1711;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public final byte[] data;
	private final int depthBits;
	private final int depthBitsPlusFour;
	// private static final String __OBFID = "http://https://fuckuskid00000376";

	public NibbleArrayReader(final byte[] dataIn, final int depthBitsIn) {
		data = dataIn;
		depthBits = depthBitsIn;
		depthBitsPlusFour = depthBitsIn + 4;
	}

	public int get(final int p_76686_1_, final int p_76686_2_, final int p_76686_3_) {
		final int var4 = p_76686_1_ << depthBitsPlusFour | p_76686_3_ << depthBits | p_76686_2_;
		final int var5 = var4 >> 1;
		final int var6 = var4 & 1;
		return var6 == 0 ? data[var5] & 15 : data[var5] >> 4 & 15;
	}
}
