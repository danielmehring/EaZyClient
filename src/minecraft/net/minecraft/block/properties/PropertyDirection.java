package net.minecraft.block.properties;

import net.minecraft.util.EnumFacing;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class PropertyDirection extends PropertyEnum {

public static final int EaZy = 417;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002016";

	protected PropertyDirection(final String name, final Collection values) {
		super(name, EnumFacing.class, values);
	}

	/**
	 * Create a new PropertyDirection with the given name
	 */
	public static PropertyDirection create(final String name) {
		return create(name, Predicates.alwaysTrue());
	}

	/**
	 * Create a new PropertyDirection with all directions that match the given
	 * Predicate
	 */
	public static PropertyDirection create(final String name, final Predicate filter) {
		return create(name, Collections2.filter(Lists.newArrayList(EnumFacing.values()), filter));
	}

	/**
	 * Create a new PropertyDirection for the given direction values
	 */
	public static PropertyDirection create(final String name, final Collection values) {
		return new PropertyDirection(name, values);
	}
}
