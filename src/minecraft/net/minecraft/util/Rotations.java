package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {

public static final int EaZy = 1649;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final float field_179419_a;
	protected final float field_179417_b;
	protected final float field_179418_c;
	// private static final String __OBFID = "http://https://fuckuskid00002316";

	public Rotations(final float p_i46009_1_, final float p_i46009_2_, final float p_i46009_3_) {
		field_179419_a = p_i46009_1_;
		field_179417_b = p_i46009_2_;
		field_179418_c = p_i46009_3_;
	}

	public Rotations(final NBTTagList p_i46010_1_) {
		field_179419_a = p_i46010_1_.getFloat(0);
		field_179417_b = p_i46010_1_.getFloat(1);
		field_179418_c = p_i46010_1_.getFloat(2);
	}

	public NBTTagList func_179414_a() {
		final NBTTagList var1 = new NBTTagList();
		var1.appendTag(new NBTTagFloat(field_179419_a));
		var1.appendTag(new NBTTagFloat(field_179417_b));
		var1.appendTag(new NBTTagFloat(field_179418_c));
		return var1;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof Rotations)) {
			return false;
		} else {
			final Rotations var2 = (Rotations) p_equals_1_;
			return field_179419_a == var2.field_179419_a && field_179417_b == var2.field_179417_b
					&& field_179418_c == var2.field_179418_c;
		}
	}

	public float func_179415_b() {
		return field_179419_a;
	}

	public float func_179416_c() {
		return field_179417_b;
	}

	public float func_179413_d() {
		return field_179418_c;
	}
}
