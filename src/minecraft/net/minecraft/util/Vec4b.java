package net.minecraft.util;

public class Vec4b {

public static final int EaZy = 1662;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final byte field_176117_a;
	private final byte field_176115_b;
	private final byte field_176116_c;
	private final byte field_176114_d;
	// private static final String __OBFID = "http://https://fuckuskid00001964";

	public Vec4b(final byte p_i45555_1_, final byte p_i45555_2_, final byte p_i45555_3_, final byte p_i45555_4_) {
		field_176117_a = p_i45555_1_;
		field_176115_b = p_i45555_2_;
		field_176116_c = p_i45555_3_;
		field_176114_d = p_i45555_4_;
	}

	public Vec4b(final Vec4b p_i45556_1_) {
		field_176117_a = p_i45556_1_.field_176117_a;
		field_176115_b = p_i45556_1_.field_176115_b;
		field_176116_c = p_i45556_1_.field_176116_c;
		field_176114_d = p_i45556_1_.field_176114_d;
	}

	public byte func_176110_a() {
		return field_176117_a;
	}

	public byte func_176112_b() {
		return field_176115_b;
	}

	public byte func_176113_c() {
		return field_176116_c;
	}

	public byte func_176111_d() {
		return field_176114_d;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof Vec4b)) {
			return false;
		} else {
			final Vec4b var2 = (Vec4b) p_equals_1_;
			return field_176117_a != var2.field_176117_a ? false
					: field_176114_d != var2.field_176114_d ? false
							: field_176115_b != var2.field_176115_b ? false : field_176116_c == var2.field_176116_c;
		}
	}

	@Override
	public int hashCode() {
		final byte var1 = field_176117_a;
		int var2 = 31 * var1 + field_176115_b;
		var2 = 31 * var2 + field_176116_c;
		var2 = 31 * var2 + field_176114_d;
		return var2;
	}
}
