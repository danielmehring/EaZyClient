package net.minecraft.block.properties;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;

public class PropertyBool extends PropertyHelper {

public static final int EaZy = 416;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ImmutableSet allowedValues = ImmutableSet.of(true, false);
	// private static final String __OBFID = "http://https://fuckuskid00002017";

	protected PropertyBool(final String name) {
		super(name, Boolean.class);
	}

	@Override
	public Collection getAllowedValues() {
		return allowedValues;
	}

	public static PropertyBool create(final String name) {
		return new PropertyBool(name);
	}

	/**
	 * Synthetic method called by getName
	 */
	public String getName0(final Boolean value) {
		return value.toString();
	}

	/**
	 * Get the name for the given value.
	 */
	@Override
	public String getName(final Comparable value) {
		return getName0((Boolean) value);
	}
}
