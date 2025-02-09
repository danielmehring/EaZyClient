package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

public static final int EaZy = 1336;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final String[] NBT_TYPES = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE",
			"BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
	// private static final String __OBFID = "http://https://fuckuskid00001229";

	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	abstract void write(DataOutput var1) throws IOException;

	abstract void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException;

	@Override
	public abstract String toString();

	/**
	 * Gets the type byte for the tag.
	 */
	public abstract byte getId();

	/**
	 * Creates a new NBTBase object that corresponds with the passed in id.
	 */
	protected static NBTBase createNewByType(final byte id) {
		switch (id) {
			case 0:
				return new NBTTagEnd();

			case 1:
				return new NBTTagByte();

			case 2:
				return new NBTTagShort();

			case 3:
				return new NBTTagInt();

			case 4:
				return new NBTTagLong();

			case 5:
				return new NBTTagFloat();

			case 6:
				return new NBTTagDouble();

			case 7:
				return new NBTTagByteArray();

			case 8:
				return new NBTTagString();

			case 9:
				return new NBTTagList();

			case 10:
				return new NBTTagCompound();

			case 11:
				return new NBTTagIntArray();

			default:
				return null;
		}
	}

	/**
	 * Creates a clone of the tag.
	 */
	public abstract NBTBase copy();

	/**
	 * Return whether this compound has no tags.
	 */
	public boolean hasNoTags() {
		return false;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof NBTBase)) {
			return false;
		} else {
			final NBTBase var2 = (NBTBase) p_equals_1_;
			return getId() == var2.getId();
		}
	}

	@Override
	public int hashCode() {
		return getId();
	}

	protected String getString() {
		return toString();
	}

	public abstract static class NBTPrimitive extends NBTBase {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001230";

		public abstract long getLong();

		public abstract int getInt();

		public abstract short getShort();

		public abstract byte getByte();

		public abstract double getDouble();

		public abstract float getFloat();
	}
}
