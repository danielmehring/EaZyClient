package optifine;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class PropertiesOrdered extends Properties {

public static final int EaZy = 1949;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Set<Object> keysOrdered = new LinkedHashSet();

	@Override
	public synchronized Object put(final Object key, final Object value) {
		keysOrdered.add(key);
		return super.put(key, value);
	}

	@Override
	public Set<Object> keySet() {
		final Set keysParent = super.keySet();
		keysOrdered.retainAll(keysParent);
		return Collections.unmodifiableSet(keysOrdered);
	}

	@Override
	public synchronized Enumeration<Object> keys() {
		return Collections.enumeration(keySet());
	}
}
