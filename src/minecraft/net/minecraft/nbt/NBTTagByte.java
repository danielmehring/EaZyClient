package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTPrimitive {

public static final int EaZy = 1339;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The byte value for the tag. */
	private byte data;
	// private static final String __OBFID = "http://https://fuckuskid00001214";

	NBTTagByte() {}

	public NBTTagByte(final byte data) {
		this.data = data;
	}

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	@Override
	void write(final DataOutput output) throws IOException {
		output.writeByte(data);
	}

	@Override
	void read(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
		sizeTracker.read(8L);
		data = input.readByte();
	}

	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 1;
	}

	@Override
	public String toString() {
		return "" + data + "b";
	}

	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		return new NBTTagByte(data);
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (super.equals(p_equals_1_)) {
			final NBTTagByte var2 = (NBTTagByte) p_equals_1_;
			return data == var2.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() ^ data;
	}

	@Override
	public long getLong() {
		return data;
	}

	@Override
	public int getInt() {
		return data;
	}

	@Override
	public short getShort() {
		return data;
	}

	@Override
	public byte getByte() {
		return data;
	}

	@Override
	public double getDouble() {
		return data;
	}

	@Override
	public float getFloat() {
		return data;
	}
}
