package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey extends RegistryNamespaced {

public static final int EaZy = 1645;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Object field_148760_d;
	private Object field_148761_e;
	// private static final String __OBFID = "http://https://fuckuskid00001196";

	public RegistryNamespacedDefaultedByKey(final Object p_i46017_1_) {
		field_148760_d = p_i46017_1_;
	}

	@Override
	public void register(final int p_177775_1_, final Object p_177775_2_, final Object p_177775_3_) {
		if (field_148760_d.equals(p_177775_2_)) {
			field_148761_e = p_177775_3_;
		}

		super.register(p_177775_1_, p_177775_2_, p_177775_3_);
	}

	/**
	 * validates that this registry's key is non-null
	 */
	public void validateKey() {
		Validate.notNull(field_148760_d);
	}

	@Override
	public Object getObject(final Object p_82594_1_) {
		final Object var2 = super.getObject(p_82594_1_);
		return var2 == null ? field_148761_e : var2;
	}

	/**
	 * Gets the object identified by the given ID.
	 */
	@Override
	public Object getObjectById(final int p_148754_1_) {
		final Object var2 = super.getObjectById(p_148754_1_);
		return var2 == null ? field_148761_e : var2;
	}
}
