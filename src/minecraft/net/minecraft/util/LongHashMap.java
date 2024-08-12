package net.minecraft.util;

public class LongHashMap {

public static final int EaZy = 1629;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** the array of all elements in the hash */
	private transient LongHashMap.Entry[] hashArray = new LongHashMap.Entry[4096];

	/** the number of elements in the hash array */
	private transient int numHashElements;
	private int field_180201_c;

	/**
	 * the maximum amount of elements in the hash (probably 3/4 the size due to
	 * meh hashing function)
	 */
	private int capacity = 3072;

	public LongHashMap() {
		field_180201_c = hashArray.length - 1;
	}

	/**
	 * returns the hashed key given the original key
	 */
	private static int getHashedKey(final long originalKey) {
		return (int) (originalKey ^ originalKey >>> 27);
	}

	/**
	 * gets the index in the hash given the array length and the hashed key
	 */
	private static int getHashIndex(final int p_76158_0_, final int p_76158_1_) {
		return p_76158_0_ & p_76158_1_;
	}

	public int getNumHashElements() {
		return numHashElements;
	}

	/**
	 * get the value from the map given the key
	 */
	public Object getValueByKey(final long p_76164_1_) {
		final int var3 = getHashedKey(p_76164_1_);

		for (LongHashMap.Entry var4 = hashArray[getHashIndex(var3,
				field_180201_c)]; var4 != null; var4 = var4.nextEntry) {
			if (var4.key == p_76164_1_) {
				return var4.value;
			}
		}

		return null;
	}

	public boolean containsItem(final long p_76161_1_) {
		return getEntry(p_76161_1_) != null;
	}

	final LongHashMap.Entry getEntry(final long p_76160_1_) {
		final int var3 = getHashedKey(p_76160_1_);

		for (LongHashMap.Entry var4 = hashArray[getHashIndex(var3,
				field_180201_c)]; var4 != null; var4 = var4.nextEntry) {
			if (var4.key == p_76160_1_) {
				return var4;
			}
		}

		return null;
	}

	/**
	 * Add a key-value pair.
	 */
	public void add(final long p_76163_1_, final Object p_76163_3_) {
		final int var4 = getHashedKey(p_76163_1_);
		final int var5 = getHashIndex(var4, field_180201_c);

		for (LongHashMap.Entry var6 = hashArray[var5]; var6 != null; var6 = var6.nextEntry) {
			if (var6.key == p_76163_1_) {
				var6.value = p_76163_3_;
				return;
			}
		}

		createKey(var4, p_76163_1_, p_76163_3_, var5);
	}

	/**
	 * resizes the table
	 */
	private void resizeTable(final int p_76153_1_) {
		final LongHashMap.Entry[] var2 = hashArray;
		final int var3 = var2.length;

		if (var3 == 1073741824) {
			capacity = Integer.MAX_VALUE;
		} else {
			final LongHashMap.Entry[] var4 = new LongHashMap.Entry[p_76153_1_];
			copyHashTableTo(var4);
			hashArray = var4;
			field_180201_c = hashArray.length - 1;
			final float var10001 = p_76153_1_;
			this.getClass();
			capacity = (int) (var10001 * 0.75F);
		}
	}

	/**
	 * copies the hash table to the specified array
	 */
	private void copyHashTableTo(final LongHashMap.Entry[] p_76154_1_) {
		final LongHashMap.Entry[] var2 = hashArray;
		final int var3 = p_76154_1_.length;

		for (int var4 = 0; var4 < var2.length; ++var4) {
			LongHashMap.Entry var5 = var2[var4];

			if (var5 != null) {
				var2[var4] = null;
				LongHashMap.Entry var6;

				do {
					var6 = var5.nextEntry;
					final int var7 = getHashIndex(var5.hash, var3 - 1);
					var5.nextEntry = p_76154_1_[var7];
					p_76154_1_[var7] = var5;
					var5 = var6;
				}
				while (var6 != null);
			}
		}
	}

	/**
	 * calls the removeKey method and returns removed object
	 */
	public Object remove(final long p_76159_1_) {
		final LongHashMap.Entry var3 = removeKey(p_76159_1_);
		return var3 == null ? null : var3.value;
	}

	/**
	 * removes the key from the hash linked list
	 */
	final LongHashMap.Entry removeKey(final long p_76152_1_) {
		final int var3 = getHashedKey(p_76152_1_);
		final int var4 = getHashIndex(var3, field_180201_c);
		LongHashMap.Entry var5 = hashArray[var4];
		LongHashMap.Entry var6;
		LongHashMap.Entry var7;

		for (var6 = var5; var6 != null; var6 = var7) {
			var7 = var6.nextEntry;

			if (var6.key == p_76152_1_) {
				--numHashElements;

				if (var5 == var6) {
					hashArray[var4] = var7;
				} else {
					var5.nextEntry = var7;
				}

				return var6;
			}

			var5 = var6;
		}

		return var6;
	}

	/**
	 * creates the key in the hash table
	 */
	private void createKey(final int p_76156_1_, final long p_76156_2_, final Object p_76156_4_, final int p_76156_5_) {
		final LongHashMap.Entry var6 = hashArray[p_76156_5_];
		hashArray[p_76156_5_] = new LongHashMap.Entry(p_76156_1_, p_76156_2_, p_76156_4_, var6);

		if (numHashElements++ >= capacity) {
			resizeTable(2 * hashArray.length);
		}
	}

	public double getKeyDistribution() {
		int countValid = 0;

		for (final Entry element : hashArray) {
			if (element != null) {
				++countValid;
			}
		}

		return 1.0D * countValid / numHashElements;
	}

	static class Entry {
		final long key;
		Object value;
		LongHashMap.Entry nextEntry;
		final int hash;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001493";

		Entry(final int p_i1553_1_, final long p_i1553_2_, final Object p_i1553_4_,
				final LongHashMap.Entry p_i1553_5_) {
			value = p_i1553_4_;
			nextEntry = p_i1553_5_;
			key = p_i1553_2_;
			hash = p_i1553_1_;
		}

		public final long getKey() {
			return key;
		}

		public final Object getValue() {
			return value;
		}

		@Override
		public final boolean equals(final Object p_equals_1_) {
			if (!(p_equals_1_ instanceof LongHashMap.Entry)) {
				return false;
			} else {
				final LongHashMap.Entry var2 = (LongHashMap.Entry) p_equals_1_;
				final Long var3 = getKey();
				final Long var4 = var2.getKey();

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
			return LongHashMap.getHashedKey(key);
		}

		@Override
		public final String toString() {
			return getKey() + "=" + getValue();
		}
	}
}
