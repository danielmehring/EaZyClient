package net.minecraft.util;

public class IntHashMap {

public static final int EaZy = 1619;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** An array of HashEntries representing the heads of hash slot lists */
	private transient IntHashMap.Entry[] slots = new IntHashMap.Entry[16];

	/** The number of items stored in this map */
	private transient int count;

	/** The grow threshold */
	private int threshold = 12;

	/** The scale factor used to determine when to grow the table */
	private final float growFactor = 0.75F;
	// private static final String __OBFID = "http://https://fuckuskid00001490";

	/**
	 * Makes the passed in integer suitable for hashing by a number of shifts
	 */
	private static int computeHash(int p_76044_0_) {
		p_76044_0_ ^= p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12;
		return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
	}

	/**
	 * Computes the index of the slot for the hash and slot count passed in.
	 */
	private static int getSlotIndex(final int p_76043_0_, final int p_76043_1_) {
		return p_76043_0_ & p_76043_1_ - 1;
	}

	/**
	 * Returns the object associated to a key
	 */
	public Object lookup(final int p_76041_1_) {
		final int var2 = computeHash(p_76041_1_);

		for (IntHashMap.Entry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = var3.nextEntry) {
			if (var3.hashEntry == p_76041_1_) {
				return var3.valueEntry;
			}
		}

		return null;
	}

	/**
	 * Returns true if this hash table contains the specified item.
	 */
	public boolean containsItem(final int p_76037_1_) {
		return lookupEntry(p_76037_1_) != null;
	}

	/**
	 * Returns the internal entry for a key
	 */
	final IntHashMap.Entry lookupEntry(final int p_76045_1_) {
		final int var2 = computeHash(p_76045_1_);

		for (IntHashMap.Entry var3 = slots[getSlotIndex(var2, slots.length)]; var3 != null; var3 = var3.nextEntry) {
			if (var3.hashEntry == p_76045_1_) {
				return var3;
			}
		}

		return null;
	}

	/**
	 * Adds a key and associated value to this map
	 */
	public void addKey(final int p_76038_1_, final Object p_76038_2_) {
		final int var3 = computeHash(p_76038_1_);
		final int var4 = getSlotIndex(var3, slots.length);

		for (IntHashMap.Entry var5 = slots[var4]; var5 != null; var5 = var5.nextEntry) {
			if (var5.hashEntry == p_76038_1_) {
				var5.valueEntry = p_76038_2_;
				return;
			}
		}

		insert(var3, p_76038_1_, p_76038_2_, var4);
	}

	/**
	 * Increases the number of hash slots
	 */
	private void grow(final int p_76047_1_) {
		final IntHashMap.Entry[] var2 = slots;
		final int var3 = var2.length;

		if (var3 == 1073741824) {
			threshold = Integer.MAX_VALUE;
		} else {
			final IntHashMap.Entry[] var4 = new IntHashMap.Entry[p_76047_1_];
			copyTo(var4);
			slots = var4;
			threshold = (int) (p_76047_1_ * growFactor);
		}
	}

	/**
	 * Copies the hash slots to a new array
	 */
	private void copyTo(final IntHashMap.Entry[] p_76048_1_) {
		final IntHashMap.Entry[] var2 = slots;
		final int var3 = p_76048_1_.length;

		for (int var4 = 0; var4 < var2.length; ++var4) {
			IntHashMap.Entry var5 = var2[var4];

			if (var5 != null) {
				var2[var4] = null;
				IntHashMap.Entry var6;

				do {
					var6 = var5.nextEntry;
					final int var7 = getSlotIndex(var5.slotHash, var3);
					var5.nextEntry = p_76048_1_[var7];
					p_76048_1_[var7] = var5;
					var5 = var6;
				}
				while (var6 != null);
			}
		}
	}

	/**
	 * Removes the specified object from the map and returns it
	 */
	public Object removeObject(final int p_76049_1_) {
		final IntHashMap.Entry var2 = removeEntry(p_76049_1_);
		return var2 == null ? null : var2.valueEntry;
	}

	/**
	 * Removes the specified entry from the map and returns it
	 */
	final IntHashMap.Entry removeEntry(final int p_76036_1_) {
		final int var2 = computeHash(p_76036_1_);
		final int var3 = getSlotIndex(var2, slots.length);
		IntHashMap.Entry var4 = slots[var3];
		IntHashMap.Entry var5;
		IntHashMap.Entry var6;

		for (var5 = var4; var5 != null; var5 = var6) {
			var6 = var5.nextEntry;

			if (var5.hashEntry == p_76036_1_) {
				--count;

				if (var4 == var5) {
					slots[var3] = var6;
				} else {
					var4.nextEntry = var6;
				}

				return var5;
			}

			var4 = var5;
		}

		return var5;
	}

	/**
	 * Removes all entries from the map
	 */
	public void clearMap() {
		final IntHashMap.Entry[] var1 = slots;

		for (int var2 = 0; var2 < var1.length; ++var2) {
			var1[var2] = null;
		}

		count = 0;
	}

	/**
	 * Adds an object to a slot
	 */
	private void insert(final int p_76040_1_, final int p_76040_2_, final Object p_76040_3_, final int p_76040_4_) {
		final IntHashMap.Entry var5 = slots[p_76040_4_];
		slots[p_76040_4_] = new IntHashMap.Entry(p_76040_1_, p_76040_2_, p_76040_3_, var5);

		if (count++ >= threshold) {
			grow(2 * slots.length);
		}
	}

	static class Entry {
		final int hashEntry;
		Object valueEntry;
		IntHashMap.Entry nextEntry;
		final int slotHash;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001491";

		Entry(final int p_i1552_1_, final int p_i1552_2_, final Object p_i1552_3_, final IntHashMap.Entry p_i1552_4_) {
			valueEntry = p_i1552_3_;
			nextEntry = p_i1552_4_;
			hashEntry = p_i1552_2_;
			slotHash = p_i1552_1_;
		}

		public final int getHash() {
			return hashEntry;
		}

		public final Object getValue() {
			return valueEntry;
		}

		@Override
		public final boolean equals(final Object p_equals_1_) {
			if (!(p_equals_1_ instanceof IntHashMap.Entry)) {
				return false;
			} else {
				final IntHashMap.Entry var2 = (IntHashMap.Entry) p_equals_1_;
				final Integer var3 = getHash();
				final Integer var4 = var2.getHash();

				if (var3 == var4 || var3 != null && var3.equals(var4)) {
					final Object var5 = getValue();
					final Object var6 = var2.getValue();

					if (var5 == var6 || var5 != null && var5.equals(var6)) {
						return true;
					}
				}

				return false;
			}
		}

		@Override
		public final int hashCode() {
			return IntHashMap.computeHash(hashEntry);
		}

		@Override
		public final String toString() {
			return getHash() + "=" + getValue();
		}
	}
}
