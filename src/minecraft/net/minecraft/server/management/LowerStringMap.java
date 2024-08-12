package net.minecraft.server.management;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

public class LowerStringMap implements Map {

public static final int EaZy = 1538;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map internalMap = Maps.newLinkedHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00001488";

	@Override
	public int size() {
		return internalMap.size();
	}

	@Override
	public boolean isEmpty() {
		return internalMap.isEmpty();
	}

	@Override
	public boolean containsKey(final Object p_containsKey_1_) {
		return internalMap.containsKey(p_containsKey_1_.toString().toLowerCase());
	}

	@Override
	public boolean containsValue(final Object p_containsValue_1_) {
		return internalMap.containsKey(p_containsValue_1_);
	}

	@Override
	public Object get(final Object p_get_1_) {
		return internalMap.get(p_get_1_.toString().toLowerCase());
	}

	public Object put(final String p_put_1_, final Object p_put_2_) {
		return internalMap.put(p_put_1_.toLowerCase(), p_put_2_);
	}

	@Override
	public Object remove(final Object p_remove_1_) {
		return internalMap.remove(p_remove_1_.toString().toLowerCase());
	}

	@Override
	public void putAll(final Map p_putAll_1_) {
		final Iterator var2 = p_putAll_1_.entrySet().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();
			this.put((String) var3.getKey(), var3.getValue());
		}
	}

	@Override
	public void clear() {
		internalMap.clear();
	}

	@Override
	public Set keySet() {
		return internalMap.keySet();
	}

	@Override
	public Collection values() {
		return internalMap.values();
	}

	@Override
	public Set entrySet() {
		return internalMap.entrySet();
	}

	@Override
	public Object put(final Object p_put_1_, final Object p_put_2_) {
		return this.put((String) p_put_1_, p_put_2_);
	}
}
