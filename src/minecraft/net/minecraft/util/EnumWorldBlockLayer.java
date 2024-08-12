package net.minecraft.util;

public enum EnumWorldBlockLayer {
	SOLID("SOLID", 0, "Solid"), CUTOUT_MIPPED("CUTOUT_MIPPED", 1, "Mipped Cutout"), CUTOUT("CUTOUT", 2,
			"Cutout"), TRANSLUCENT("TRANSLUCENT", 3, "Translucent");
	private final String field_180338_e;

	private EnumWorldBlockLayer(final String p_i45755_1_, final int p_i45755_2_, final String p_i45755_3_) {
		field_180338_e = p_i45755_3_;
	}

	@Override
	public String toString() {
		return field_180338_e;
	}
}
