package net.minecraft.util;

import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable {

public static final int EaZy = 1625;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The set for this ForwardingSet to forward methods to. */
	private final Set underlyingSet = Sets.newHashSet();
	// private static final String __OBFID = "http://https://fuckuskid00001482";

	@Override
	public void func_152753_a(final JsonElement p_152753_1_) {
		if (p_152753_1_.isJsonArray()) {
			final Iterator var2 = p_152753_1_.getAsJsonArray().iterator();

			while (var2.hasNext()) {
				final JsonElement var3 = (JsonElement) var2.next();
				add(var3.getAsString());
			}
		}
	}

	/**
	 * Gets the JsonElement that can be serialized.
	 */
	@Override
	public JsonElement getSerializableElement() {
		final JsonArray var1 = new JsonArray();
		final Iterator var2 = iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();
			var1.add(new JsonPrimitive(var3));
		}

		return var1;
	}

	@Override
	protected Set delegate() {
		return underlyingSet;
	}

}
