package net.minecraft.block.properties;

import java.util.Collection;
import java.util.HashSet;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class PropertyInteger extends PropertyHelper {

public static final int EaZy = 420;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ImmutableSet allowedValues;
	// private static final String __OBFID = "http://https://fuckuskid00002014";

	protected PropertyInteger(final String name, final int min, final int max) {
		super(name, Integer.class);

		if (min < 0) {
			throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
		} else if (max <= min) {
			throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
		} else {
			final HashSet var4 = Sets.newHashSet();

			for (int var5 = min; var5 <= max; ++var5) {
				var4.add(var5);
			}

			allowedValues = ImmutableSet.copyOf(var4);
		}
	}

	@Override
	public Collection getAllowedValues() {
		return allowedValues;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
			if (!super.equals(p_equals_1_)) {
				return false;
			} else {
				final PropertyInteger var2 = (PropertyInteger) p_equals_1_;
				return allowedValues.equals(var2.allowedValues);
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int var1 = super.hashCode();
		var1 = 31 * var1 + allowedValues.hashCode();
		return var1;
	}

	public static PropertyInteger create(final String name, final int min, final int max) {
		return new PropertyInteger(name, min, max);
	}

	public String getName0(final Integer value) {
		return value.toString();
	}

	/**
	 * Get the name for the given value.
	 */
	@Override
	public String getName(final Comparable value) {
		return getName0((Integer) value);
	}
}
