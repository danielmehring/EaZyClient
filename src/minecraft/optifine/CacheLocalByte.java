package optifine;

public class CacheLocalByte {

public static final int EaZy = 1878;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int maxX = 18;
	private int maxY = 128;
	private int maxZ = 18;
	private int offsetX = 0;
	private int offsetY = 0;
	private int offsetZ = 0;
	private byte[][][] cache = null;
	private byte[] lastZs = null;
	private int lastDz = 0;

	public CacheLocalByte(final int maxX, final int maxY, final int maxZ) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		cache = new byte[maxX][maxY][maxZ];
		resetCache();
	}

	public void resetCache() {
		for (int x = 0; x < maxX; ++x) {
			final byte[][] ys = cache[x];

			for (int y = 0; y < maxY; ++y) {
				final byte[] zs = ys[y];

				for (int z = 0; z < maxZ; ++z) {
					zs[z] = -1;
				}
			}
		}
	}

	public void setOffset(final int x, final int y, final int z) {
		offsetX = x;
		offsetY = y;
		offsetZ = z;
		resetCache();
	}

	public byte get(final int x, final int y, final int z) {
		try {
			lastZs = cache[x - offsetX][y - offsetY];
			lastDz = z - offsetZ;
			return lastZs[lastDz];
		} catch (final ArrayIndexOutOfBoundsException var5) {
			var5.printStackTrace();
			return (byte) -1;
		}
	}

	public void setLast(final byte val) {
		try {
			lastZs[lastDz] = val;
		} catch (final Exception var3) {
			var3.printStackTrace();
		}
	}
}
