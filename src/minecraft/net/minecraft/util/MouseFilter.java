package net.minecraft.util;

public class MouseFilter {

public static final int EaZy = 1637;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float field_76336_a;
	private float field_76334_b;
	private float field_76335_c;
	// private static final String __OBFID = "http://https://fuckuskid00001500";

	/**
	 * Smooths mouse input
	 */
	public float smooth(float p_76333_1_, final float p_76333_2_) {
		field_76336_a += p_76333_1_;
		p_76333_1_ = (field_76336_a - field_76334_b) * p_76333_2_;
		field_76335_c += (p_76333_1_ - field_76335_c) * 0.5F;

		if (p_76333_1_ > 0.0F && p_76333_1_ > field_76335_c || p_76333_1_ < 0.0F && p_76333_1_ < field_76335_c) {
			p_76333_1_ = field_76335_c;
		}

		field_76334_b += p_76333_1_;
		return p_76333_1_;
	}

	public void func_180179_a() {
		field_76336_a = 0.0F;
		field_76334_b = 0.0F;
		field_76335_c = 0.0F;
	}
}
