package net.minecraft.world;

public enum EnumSkyBlock {
	SKY("SKY", 0, 15), BLOCK("BLOCK", 1, 0);
	public final int defaultLightValue;

	private EnumSkyBlock(final String p_i1961_1_, final int p_i1961_2_, final int p_i1961_3_) {
		defaultLightValue = p_i1961_3_;
	}
}
