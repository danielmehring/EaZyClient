package net.minecraft.client.util;

import java.nio.FloatBuffer;
import java.util.Comparator;

public class QuadComparator implements Comparator {

public static final int EaZy = 924;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final float field_147630_a;
	private final float field_147628_b;
	private final float field_147629_c;
	private final FloatBuffer field_147627_d;
	private final int field_178079_e;
	// private static final String __OBFID = "http://https://fuckuskid00000958";

	public QuadComparator(final FloatBuffer p_i46247_1_, final float p_i46247_2_, final float p_i46247_3_,
			final float p_i46247_4_, final int p_i46247_5_) {
		field_147627_d = p_i46247_1_;
		field_147630_a = p_i46247_2_;
		field_147628_b = p_i46247_3_;
		field_147629_c = p_i46247_4_;
		field_178079_e = p_i46247_5_;
	}

	public int compare(final Integer p_compare_1_, final Integer p_compare_2_) {
		final float var3 = field_147627_d.get(p_compare_1_) - field_147630_a;
		final float var4 = field_147627_d.get(p_compare_1_ + 1) - field_147628_b;
		final float var5 = field_147627_d.get(p_compare_1_ + 2) - field_147629_c;
		final float var6 = field_147627_d.get(p_compare_1_ + field_178079_e + 0) - field_147630_a;
		final float var7 = field_147627_d.get(p_compare_1_ + field_178079_e + 1) - field_147628_b;
		final float var8 = field_147627_d.get(p_compare_1_ + field_178079_e + 2) - field_147629_c;
		final float var9 = field_147627_d.get(p_compare_1_ + field_178079_e * 2 + 0) - field_147630_a;
		final float var10 = field_147627_d.get(p_compare_1_ + field_178079_e * 2 + 1) - field_147628_b;
		final float var11 = field_147627_d.get(p_compare_1_ + field_178079_e * 2 + 2) - field_147629_c;
		final float var12 = field_147627_d.get(p_compare_1_ + field_178079_e * 3 + 0) - field_147630_a;
		final float var13 = field_147627_d.get(p_compare_1_ + field_178079_e * 3 + 1) - field_147628_b;
		final float var14 = field_147627_d.get(p_compare_1_ + field_178079_e * 3 + 2) - field_147629_c;
		final float var15 = field_147627_d.get(p_compare_2_) - field_147630_a;
		final float var16 = field_147627_d.get(p_compare_2_ + 1) - field_147628_b;
		final float var17 = field_147627_d.get(p_compare_2_ + 2) - field_147629_c;
		final float var18 = field_147627_d.get(p_compare_2_ + field_178079_e + 0) - field_147630_a;
		final float var19 = field_147627_d.get(p_compare_2_ + field_178079_e + 1) - field_147628_b;
		final float var20 = field_147627_d.get(p_compare_2_ + field_178079_e + 2) - field_147629_c;
		final float var21 = field_147627_d.get(p_compare_2_ + field_178079_e * 2 + 0) - field_147630_a;
		final float var22 = field_147627_d.get(p_compare_2_ + field_178079_e * 2 + 1) - field_147628_b;
		final float var23 = field_147627_d.get(p_compare_2_ + field_178079_e * 2 + 2) - field_147629_c;
		final float var24 = field_147627_d.get(p_compare_2_ + field_178079_e * 3 + 0) - field_147630_a;
		final float var25 = field_147627_d.get(p_compare_2_ + field_178079_e * 3 + 1) - field_147628_b;
		final float var26 = field_147627_d.get(p_compare_2_ + field_178079_e * 3 + 2) - field_147629_c;
		final float var27 = (var3 + var6 + var9 + var12) * 0.25F;
		final float var28 = (var4 + var7 + var10 + var13) * 0.25F;
		final float var29 = (var5 + var8 + var11 + var14) * 0.25F;
		final float var30 = (var15 + var18 + var21 + var24) * 0.25F;
		final float var31 = (var16 + var19 + var22 + var25) * 0.25F;
		final float var32 = (var17 + var20 + var23 + var26) * 0.25F;
		final float var33 = var27 * var27 + var28 * var28 + var29 * var29;
		final float var34 = var30 * var30 + var31 * var31 + var32 * var32;
		return Float.compare(var34, var33);
	}

	@Override
	public int compare(final Object p_compare_1_, final Object p_compare_2_) {
		return this.compare((Integer) p_compare_1_, (Integer) p_compare_2_);
	}
}
