package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {

public static final int EaZy = 1340;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The byte array stored in the tag. */
	private byte[] data;
	// private static final String __OBFID = "http://https://fuckuskid00001213";

	NBTTagByteArray() {}

	public NBTTagByteArray(final byte[] data) {
		this.data = data;
	}

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	@Override
	void write(final DataOutput output) throws IOException {
		output.writeInt(data.length);
		output.write(data);
	}

	@Override
	void read(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
		final int var4 = input.readInt();
		sizeTracker.read(8 * var4);
		data = new byte[var4];
		input.readFully(data);
	}

	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 7;
	}

	@Override
	public String toString() {
		return "[" + data.length + " bytes]";
	}

	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		final byte[] var1 = new byte[data.length];
		System.arraycopy(data, 0, var1, 0, data.length);
		return new NBTTagByteArray(var1);
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		return super.equals(p_equals_1_) ? Arrays.equals(data, ((NBTTagByteArray) p_equals_1_).data) : false;
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(data);
	}

	public byte[] getByteArray() {
		return data;
	}
}
