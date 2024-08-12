package net.minecraft.nbt;

public class NBTSizeTracker {

public static final int EaZy = 1338;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001902";
		@Override
		public void read(final long bits) {}
	};
	private final long max;
	private long read;
	// private static final String __OBFID = "http://https://fuckuskid00001903";

	public NBTSizeTracker(final long max) {
		this.max = max;
	}

	/**
	 * Tracks the reading of the given amount of bits(!)
	 */
	public void read(final long bits) {
		read += bits / 8L;

		if (read > max) {
			throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + read
					+ "bytes where max allowed: " + max);
		}
	}
}
