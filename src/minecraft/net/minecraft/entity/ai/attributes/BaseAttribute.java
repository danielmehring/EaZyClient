package net.minecraft.entity.ai.attributes;

public abstract class BaseAttribute implements IAttribute {

public static final int EaZy = 1035;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final IAttribute field_180373_a;
	private final String unlocalizedName;
	private final double defaultValue;
	private boolean shouldWatch;
	// private static final String __OBFID = "http://https://fuckuskid00001565";

	protected BaseAttribute(final IAttribute p_i45892_1_, final String p_i45892_2_, final double p_i45892_3_) {
		field_180373_a = p_i45892_1_;
		unlocalizedName = p_i45892_2_;
		defaultValue = p_i45892_3_;

		if (p_i45892_2_ == null) {
			throw new IllegalArgumentException("Name cannot be null!");
		}
	}

	@Override
	public String getAttributeUnlocalizedName() {
		return unlocalizedName;
	}

	@Override
	public double getDefaultValue() {
		return defaultValue;
	}

	@Override
	public boolean getShouldWatch() {
		return shouldWatch;
	}

	public BaseAttribute setShouldWatch(final boolean p_111112_1_) {
		shouldWatch = p_111112_1_;
		return this;
	}

	@Override
	public IAttribute func_180372_d() {
		return field_180373_a;
	}

	@Override
	public int hashCode() {
		return unlocalizedName.hashCode();
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		return p_equals_1_ instanceof IAttribute
				&& unlocalizedName.equals(((IAttribute) p_equals_1_).getAttributeUnlocalizedName());
	}
}
