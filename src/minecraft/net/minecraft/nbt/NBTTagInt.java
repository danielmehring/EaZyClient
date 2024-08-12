package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTPrimitive {

public static final int EaZy = 1345;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The integer value for the tag. */
	private int data;
	// private static final String __OBFID = "http://https://fuckuskid00001223";

	NBTTagInt() {}

	public NBTTagInt(final int data) {
		this.data = data;
	}

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	@Override
	void write(final DataOutput output) throws IOException {
		output.writeInt(data);
	}

	@Override
	void read(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
		sizeTracker.read(32L);
		data = input.readInt();
	}

	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 3;
	}

	@Override
	public String toString() {
		return "" + data;
	}

	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		return new NBTTagInt(data);
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (super.equals(p_equals_1_)) {
			final NBTTagInt var2 = (NBTTagInt) p_equals_1_;
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
		return (short) (data & 65535);
	}

	@Override
	public byte getByte() {
		return (byte) (data & 255);
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
