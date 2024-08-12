package net.minecraft.util;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public class ObjectIntIdentityMap implements IObjectIntIterable {

public static final int EaZy = 1642;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IdentityHashMap field_148749_a = new IdentityHashMap(512);
	private final List field_148748_b = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001203";

	public void put(final Object key, final int value) {
		field_148749_a.put(key, value);

		while (field_148748_b.size() <= value) {
			field_148748_b.add((Object) null);
		}

		field_148748_b.set(value, key);
	}

	public int get(final Object key) {
		final Integer var2 = (Integer) field_148749_a.get(key);
		return var2 == null ? -1 : var2;
	}

	public final Object getByValue(final int value) {
		return value >= 0 && value < field_148748_b.size() ? field_148748_b.get(value) : null;
	}

	@Override
	public Iterator iterator() {
		return Iterators.filter(field_148748_b.iterator(), Predicates.notNull());
	}

	public List getObjectList() {
		return field_148748_b;
	}
}
