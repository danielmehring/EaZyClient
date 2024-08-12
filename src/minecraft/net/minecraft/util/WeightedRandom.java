package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom {

public static final int EaZy = 1663;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001503";

	/**
	 * Returns the total weight of all items in a collection.
	 */
	public static int getTotalWeight(final Collection p_76272_0_) {
		int var1 = 0;
		WeightedRandom.Item var3;

		for (final Iterator var2 = p_76272_0_.iterator(); var2.hasNext(); var1 += var3.itemWeight) {
			var3 = (WeightedRandom.Item) var2.next();
		}

		return var1;
	}

	/**
	 * Returns a random choice from the input items, with a total weight value.
	 */
	public static WeightedRandom.Item getRandomItem(final Random p_76273_0_, final Collection p_76273_1_,
			final int p_76273_2_) {
		if (p_76273_2_ <= 0) {
			throw new IllegalArgumentException();
		} else {
			final int var3 = p_76273_0_.nextInt(p_76273_2_);
			return func_180166_a(p_76273_1_, var3);
		}
	}

	public static WeightedRandom.Item func_180166_a(final Collection p_180166_0_, int p_180166_1_) {
		final Iterator var2 = p_180166_0_.iterator();
		WeightedRandom.Item var3;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (WeightedRandom.Item) var2.next();
			p_180166_1_ -= var3.itemWeight;
		}
		while (p_180166_1_ >= 0);

		return var3;
	}

	/**
	 * Returns a random choice from the input items.
	 */
	public static WeightedRandom.Item getRandomItem(final Random p_76271_0_, final Collection p_76271_1_) {
		return getRandomItem(p_76271_0_, p_76271_1_, getTotalWeight(p_76271_1_));
	}

	public static class Item {
		protected int itemWeight;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001504";

		public Item(final int p_i1556_1_) {
			itemWeight = p_i1556_1_;
		}
	}
}
