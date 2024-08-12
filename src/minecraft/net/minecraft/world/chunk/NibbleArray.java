package net.minecraft.world.chunk;

public class NibbleArray {

public static final int EaZy = 1704;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Byte array of data stored in this holder. Possibly a light map or some
	 * chunk data. Data is accessed in 4-bit pieces.
	 */
	private final byte[] data;
	// private static final String __OBFID = "http://https://fuckuskid00000371";

	public NibbleArray() {
		data = new byte[2048];
	}

	public NibbleArray(final byte[] storageArray) {
		data = storageArray;

		if (storageArray.length != 2048) {
			throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
		}
	}

	/**
	 * Returns the nibble of data corresponding to the passed in x, y, z. y is
	 * at most 6 bits, z is at most 4.
	 */
	public int get(final int x, final int y, final int z) {
		return getFromIndex(getCoordinateIndex(x, y, z));
	}

	/**
	 * Arguments are x, y, z, val. Sets the nibble of data at x << 11 | z << 7 |
	 * y to val.
	 */
	public void set(final int x, final int y, final int z, final int value) {
		setIndex(getCoordinateIndex(x, y, z), value);
	}

	private int getCoordinateIndex(final int x, final int y, final int z) {
		return y << 8 | z << 4 | x;
	}

	public int getFromIndex(final int index) {
		final int var2 = func_177478_c(index);
		return func_177479_b(index) ? data[var2] & 15 : data[var2] >> 4 & 15;
	}

	public void setIndex(final int index, final int value) {
		final int var3 = func_177478_c(index);

		if (func_177479_b(index)) {
			data[var3] = (byte) (data[var3] & 240 | value & 15);
		} else {
			data[var3] = (byte) (data[var3] & 15 | (value & 15) << 4);
		}
	}

	private boolean func_177479_b(final int p_177479_1_) {
		return (p_177479_1_ & 1) == 0;
	}

	private int func_177478_c(final int p_177478_1_) {
		return p_177478_1_ >> 1;
	}

	public byte[] getData() {
		return data;
	}
}
