package net.minecraft.block.properties;

import com.google.common.base.Objects;

public abstract class PropertyHelper implements IProperty {

public static final int EaZy = 419;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Class valueClass;
	private final String name;
	// private static final String __OBFID = "http://https://fuckuskid00002018";

	protected PropertyHelper(final String name, final Class valueClass) {
		this.valueClass = valueClass;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * The class of the values of this property
	 */
	@Override
	public Class getValueClass() {
		return valueClass;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", name).add("clazz", valueClass).add("values", getAllowedValues())
				.toString();
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
			final PropertyHelper var2 = (PropertyHelper) p_equals_1_;
			return valueClass.equals(var2.valueClass) && name.equals(var2.name);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 31 * valueClass.hashCode() + name.hashCode();
	}
}
