package net.minecraft.block.state.pattern;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FactoryBlockPattern {

public static final int EaZy = 429;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Joiner field_177667_a = Joiner.on(",");
	private final List field_177665_b = Lists.newArrayList();
	private final Map field_177666_c = Maps.newHashMap();
	private int field_177663_d;
	private int field_177664_e;
	// private static final String __OBFID = "http://https://fuckuskid00002021";

	private FactoryBlockPattern() {
		field_177666_c.put(' ', Predicates.alwaysTrue());
	}

	public FactoryBlockPattern aisle(final String... p_177659_1_) {
		if (!ArrayUtils.isEmpty(p_177659_1_) && !StringUtils.isEmpty(p_177659_1_[0])) {
			if (field_177665_b.isEmpty()) {
				field_177663_d = p_177659_1_.length;
				field_177664_e = p_177659_1_[0].length();
			}

			if (p_177659_1_.length != field_177663_d) {
				throw new IllegalArgumentException("Expected aisle with height of " + field_177663_d
						+ ", but was given one with a height of " + p_177659_1_.length + ")");
			} else {
				final String[] var2 = p_177659_1_;
				final int var3 = p_177659_1_.length;

				for (int var4 = 0; var4 < var3; ++var4) {
					final String var5 = var2[var4];

					if (var5.length() != field_177664_e) {
						throw new IllegalArgumentException(
								"Not all rows in the given aisle are the correct width (expected " + field_177664_e
										+ ", found one with " + var5.length() + ")");
					}

					final char[] var6 = var5.toCharArray();
					final int var7 = var6.length;

					for (int var8 = 0; var8 < var7; ++var8) {
						final char var9 = var6[var8];

						if (!field_177666_c.containsKey(var9)) {
							field_177666_c.put(var9, (Object) null);
						}
					}
				}

				field_177665_b.add(p_177659_1_);
				return this;
			}
		} else {
			throw new IllegalArgumentException("Empty pattern for aisle");
		}
	}

	public static FactoryBlockPattern start() {
		return new FactoryBlockPattern();
	}

	public FactoryBlockPattern where(final char p_177662_1_, final Predicate p_177662_2_) {
		field_177666_c.put(p_177662_1_, p_177662_2_);
		return this;
	}

	public BlockPattern build() {
		return new BlockPattern(func_177658_c());
	}

	private Predicate[][][] func_177658_c() {
		func_177657_d();
		final Predicate[][][] var1 = (Predicate[][][]) Array.newInstance(Predicate.class,
				new int[] { field_177665_b.size(), field_177663_d, field_177664_e });

		for (int var2 = 0; var2 < field_177665_b.size(); ++var2) {
			for (int var3 = 0; var3 < field_177663_d; ++var3) {
				for (int var4 = 0; var4 < field_177664_e; ++var4) {
					var1[var2][var3][var4] = (Predicate) field_177666_c
							.get(((String[]) field_177665_b.get(var2))[var3].charAt(var4));
				}
			}
		}

		return var1;
	}

	private void func_177657_d() {
		final ArrayList var1 = Lists.newArrayList();
		final Iterator var2 = field_177666_c.entrySet().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();

			if (var3.getValue() == null) {
				var1.add(var3.getKey());
			}
		}

		if (!var1.isEmpty()) {
			throw new IllegalStateException(
					"Predicates for character(s) " + field_177667_a.join(var1) + " are missing");
		}
	}
}
