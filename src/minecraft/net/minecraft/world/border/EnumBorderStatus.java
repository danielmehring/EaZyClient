package net.minecraft.world.border;

public enum EnumBorderStatus {
	GROWING("GROWING", 0, 4259712), SHRINKING("SHRINKING", 1, 16724016), STATIONARY("STATIONARY", 2, 2138367);
	private final int id;

	private EnumBorderStatus(final String p_i45647_1_, final int p_i45647_2_, final int id) {
		this.id = id;
	}

	public int func_177766_a() {
		return id;
	}
}
