package net.minecraft.util;

public class RegistryDefaulted extends RegistrySimple {

public static final int EaZy = 1643;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Default object for this registry, returned when an object is not found.
	 */
	private final Object defaultObject;
	// private static final String __OBFID = "http://https://fuckuskid00001198";

	public RegistryDefaulted(final Object p_i1366_1_) {
		defaultObject = p_i1366_1_;
	}

	@Override
	public Object getObject(final Object p_82594_1_) {
		final Object var2 = super.getObject(p_82594_1_);
		return var2 == null ? defaultObject : var2;
	}
}
