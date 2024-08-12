package net.minecraft.nbt;

import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase.NBTPrimitive {

public static final int EaZy = 1342;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The double value for the tag. */
	private double data;
	// private static final String __OBFID = "http://https://fuckuskid00001218";

	NBTTagDouble() {}

	public NBTTagDouble(final double data) {
		this.data = data;
	}

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	@Override
	void write(final DataOutput output) throws IOException {
		output.writeDouble(data);
	}

	@Override
	void read(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
		sizeTracker.read(64L);
		data = input.readDouble();
	}

	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 6;
	}

	@Override
	public String toString() {
		return "" + data + "d";
	}

	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		return new NBTTagDouble(data);
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (super.equals(p_equals_1_)) {
			final NBTTagDouble var2 = (NBTTagDouble) p_equals_1_;
			return data == var2.data;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final long var1 = Double.doubleToLongBits(data);
		return super.hashCode() ^ (int) (var1 ^ var1 >>> 32);
	}

	@Override
	public long getLong() {
		return (long) Math.floor(data);
	}

	@Override
	public int getInt() {
		return MathHelper.floor_double(data);
	}

	@Override
	public short getShort() {
		return (short) (MathHelper.floor_double(data) & 65535);
	}

	@Override
	public byte getByte() {
		return (byte) (MathHelper.floor_double(data) & 255);
	}

	@Override
	public double getDouble() {
		return data;
	}

	@Override
	public float getFloat() {
		return (float) data;
	}
}
