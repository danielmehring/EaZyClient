package net.minecraft.client.resources.model;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.StringUtils;

public class ModelResourceLocation extends ResourceLocation {

public static final int EaZy = 890;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_177519_c;
	// private static final String __OBFID = "http://https://fuckuskid00002387";

	protected ModelResourceLocation(final int p_i46078_1_, final String... p_i46078_2_) {
		super(0, new String[] { p_i46078_2_[0], p_i46078_2_[1] });
		field_177519_c = StringUtils.isEmpty(p_i46078_2_[2]) ? "normal" : p_i46078_2_[2].toLowerCase();
	}

	public ModelResourceLocation(final String p_i46079_1_) {
		this(0, func_177517_b(p_i46079_1_));
	}

	public ModelResourceLocation(final ResourceLocation p_i46080_1_, final String p_i46080_2_) {
		this(p_i46080_1_.toString(), p_i46080_2_);
	}

	public ModelResourceLocation(final String p_i46081_1_, final String p_i46081_2_) {
		this(0, func_177517_b(p_i46081_1_ + '#' + (p_i46081_2_ == null ? "normal" : p_i46081_2_)));
	}

	protected static String[] func_177517_b(final String p_177517_0_) {
		final String[] var1 = new String[] { null, p_177517_0_, null };
		final int var2 = p_177517_0_.indexOf(35);
		String var3 = p_177517_0_;

		if (var2 >= 0) {
			var1[2] = p_177517_0_.substring(var2 + 1, p_177517_0_.length());

			if (var2 > 1) {
				var3 = p_177517_0_.substring(0, var2);
			}
		}

		System.arraycopy(ResourceLocation.func_177516_a(var3), 0, var1, 0, 2);
		return var1;
	}

	public String func_177518_c() {
		return field_177519_c;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ instanceof ModelResourceLocation && super.equals(p_equals_1_)) {
			final ModelResourceLocation var2 = (ModelResourceLocation) p_equals_1_;
			return field_177519_c.equals(var2.field_177519_c);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 31 * super.hashCode() + field_177519_c.hashCode();
	}

	@Override
	public String toString() {
		return super.toString() + '#' + field_177519_c;
	}
}
