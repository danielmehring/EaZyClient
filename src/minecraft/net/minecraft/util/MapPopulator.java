package net.minecraft.util;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.google.common.collect.Maps;

public class MapPopulator {

public static final int EaZy = 1630;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002318";

	/**
	 * Create a Map from the given keys and values. This method creates a
	 * LinkedHashMap.
	 * 
	 * @param keys
	 *            the keys for the map, in order
	 * @param values
	 *            the values for the map, in order
	 */
	public static Map createMap(final Iterable keys, final Iterable values) {
		return populateMap(keys, values, Maps.newLinkedHashMap());
	}

	/**
	 * Populate the given Map with the given keys and values.
	 */
	public static Map populateMap(final Iterable keys, final Iterable values, final Map map) {
		final Iterator var3 = values.iterator();
		final Iterator var4 = keys.iterator();

		while (var4.hasNext()) {
			final Object var5 = var4.next();
			map.put(var5, var3.next());
		}

		if (var3.hasNext()) {
			throw new NoSuchElementException();
		} else {
			return map;
		}
	}
}
