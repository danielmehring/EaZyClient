package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class SoundPoolEntry {

public static final int EaZy = 448;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ResourceLocation field_148656_a;
	private final boolean field_148654_b;
	private double field_148655_c;
	private double field_148653_d;
	// private static final String __OBFID = "http://https://fuckuskid00001140";

	public SoundPoolEntry(final ResourceLocation p_i45113_1_, final double p_i45113_2_, final double p_i45113_4_,
			final boolean p_i45113_6_) {
		field_148656_a = p_i45113_1_;
		field_148655_c = p_i45113_2_;
		field_148653_d = p_i45113_4_;
		field_148654_b = p_i45113_6_;
	}

	public SoundPoolEntry(final SoundPoolEntry p_i45114_1_) {
		field_148656_a = p_i45114_1_.field_148656_a;
		field_148655_c = p_i45114_1_.field_148655_c;
		field_148653_d = p_i45114_1_.field_148653_d;
		field_148654_b = p_i45114_1_.field_148654_b;
	}

	public ResourceLocation getSoundPoolEntryLocation() {
		return field_148656_a;
	}

	public double getPitch() {
		return field_148655_c;
	}

	public void setPitch(final double p_148651_1_) {
		field_148655_c = p_148651_1_;
	}

	public double getVolume() {
		return field_148653_d;
	}

	public void setVolume(final double p_148647_1_) {
		field_148653_d = p_148647_1_;
	}

	public boolean isStreamingSound() {
		return field_148654_b;
	}
}
