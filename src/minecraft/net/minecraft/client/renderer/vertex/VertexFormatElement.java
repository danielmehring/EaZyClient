package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {

public static final int EaZy = 849;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_177381_a = LogManager.getLogger();
	private final VertexFormatElement.EnumType field_177379_b;
	private final VertexFormatElement.EnumUseage field_177380_c;
	private final int field_177377_d;
	private final int field_177378_e;
	private int field_177376_f;
	// private static final String __OBFID = "http://https://fuckuskid00002399";

	public VertexFormatElement(final int p_i46096_1_, final VertexFormatElement.EnumType p_i46096_2_,
			final VertexFormatElement.EnumUseage p_i46096_3_, final int p_i46096_4_) {
		if (!func_177372_a(p_i46096_1_, p_i46096_3_)) {
			field_177381_a.warn(
					"Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
			field_177380_c = VertexFormatElement.EnumUseage.UV;
		} else {
			field_177380_c = p_i46096_3_;
		}

		field_177379_b = p_i46096_2_;
		field_177377_d = p_i46096_1_;
		field_177378_e = p_i46096_4_;
		field_177376_f = 0;
	}

	public void func_177371_a(final int p_177371_1_) {
		field_177376_f = p_177371_1_;
	}

	public int func_177373_a() {
		return field_177376_f;
	}

	private final boolean func_177372_a(final int p_177372_1_, final VertexFormatElement.EnumUseage p_177372_2_) {
		return p_177372_1_ == 0 || p_177372_2_ == VertexFormatElement.EnumUseage.UV;
	}

	public final VertexFormatElement.EnumType func_177367_b() {
		return field_177379_b;
	}

	public final VertexFormatElement.EnumUseage func_177375_c() {
		return field_177380_c;
	}

	public final int func_177370_d() {
		return field_177378_e;
	}

	public final int func_177369_e() {
		return field_177377_d;
	}

	@Override
	public String toString() {
		return field_177378_e + "," + field_177380_c.func_177384_a() + "," + field_177379_b.func_177396_b();
	}

	public final int func_177368_f() {
		return field_177379_b.func_177395_a() * field_177378_e;
	}

	public final boolean func_177374_g() {
		return field_177380_c == VertexFormatElement.EnumUseage.POSITION;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
			final VertexFormatElement var2 = (VertexFormatElement) p_equals_1_;
			return field_177378_e != var2.field_177378_e ? false
					: field_177377_d != var2.field_177377_d ? false
							: field_177376_f != var2.field_177376_f ? false
									: field_177379_b != var2.field_177379_b ? false
											: field_177380_c == var2.field_177380_c;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int var1 = field_177379_b.hashCode();
		var1 = 31 * var1 + field_177380_c.hashCode();
		var1 = 31 * var1 + field_177377_d;
		var1 = 31 * var1 + field_177378_e;
		var1 = 31 * var1 + field_177376_f;
		return var1;
	}

	public static enum EnumType {
		FLOAT("FLOAT", 0, 4, "Float", 5126), UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121), BYTE("BYTE", 2, 1, "Byte",
				5120), USHORT("USHORT", 3, 2, "Unsigned Short", 5123), SHORT("SHORT", 4, 2, "Short",
						5122), UINT("UINT", 5, 4, "Unsigned Int", 5125), INT("INT", 6, 4, "Int", 5124);
		private final int field_177407_h;
		private final String field_177408_i;
		private final int field_177405_j;

		private EnumType(final String p_i46095_1_, final int p_i46095_2_, final int p_i46095_3_,
				final String p_i46095_4_, final int p_i46095_5_) {
			field_177407_h = p_i46095_3_;
			field_177408_i = p_i46095_4_;
			field_177405_j = p_i46095_5_;
		}

		public int func_177395_a() {
			return field_177407_h;
		}

		public String func_177396_b() {
			return field_177408_i;
		}

		public int func_177397_c() {
			return field_177405_j;
		}
	}

	public static enum EnumUseage {
		POSITION("POSITION", 0, "Position"), NORMAL("NORMAL", 1, "Normal"), COLOR("COLOR", 2, "Vertex Color"), UV("UV",
				3, "UV"), MATRIX("MATRIX", 4, "Bone Matrix"), BLEND_WEIGHT("BLEND_WEIGHT", 5,
						"Blend Weight"), PADDING("PADDING", 6, "Padding");
		private final String field_177392_h;

		private EnumUseage(final String p_i46094_1_, final int p_i46094_2_, final String p_i46094_3_) {
			field_177392_h = p_i46094_3_;
		}

		public String func_177384_a() {
			return field_177392_h;
		}
	}
}
