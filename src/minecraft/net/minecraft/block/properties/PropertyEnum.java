package net.minecraft.block.properties;

import net.minecraft.util.IStringSerializable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PropertyEnum extends PropertyHelper {

public static final int EaZy = 418;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ImmutableSet allowedValues;

	/** Map of names to Enum values */
	private final Map nameToValue = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002015";

	protected PropertyEnum(final String name, final Class valueClass, final Collection allowedValues) {
		super(name, valueClass);
		this.allowedValues = ImmutableSet.copyOf(allowedValues);
		final Iterator var4 = allowedValues.iterator();

		while (var4.hasNext()) {
			final Enum var5 = (Enum) var4.next();
			final String var6 = ((IStringSerializable) var5).getName();

			if (nameToValue.containsKey(var6)) {
				throw new IllegalArgumentException("Multiple values have the same name \'" + var6 + "\'");
			}

			nameToValue.put(var6, var5);
		}
	}

	@Override
	public Collection getAllowedValues() {
		return allowedValues;
	}

	public String getName(final Enum value) {
		return ((IStringSerializable) value).getName();
	}

	/**
	 * Create a new PropertyEnum with all Enum constants of the given class.
	 */
	public static PropertyEnum create(final String name, final Class clazz) {
		return create(name, clazz, Predicates.alwaysTrue());
	}

	/**
	 * Create a new PropertyEnum with all Enum constants of the given class that
	 * match the given Predicate.
	 */
	public static PropertyEnum create(final String name, final Class clazz, final Predicate filter) {
		return create(name, clazz, Collections2.filter(Lists.newArrayList(clazz.getEnumConstants()), filter));
	}

	/**
	 * Create a new PropertyEnum with the specified values
	 */
	public static PropertyEnum create(final String name, final Class clazz, final Enum... values) {
		return create(name, clazz, Lists.newArrayList(values));
	}

	/**
	 * Create a new PropertyEnum with the specified values
	 */
	public static PropertyEnum create(final String name, final Class clazz, final Collection values) {
		return new PropertyEnum(name, clazz, values);
	}

	/**
	 * Get the name for the given value.
	 */
	@Override
	public String getName(final Comparable value) {
		return this.getName((Enum) value);
	}
}
