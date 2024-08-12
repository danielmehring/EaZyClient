package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
	ResourceLocation getSoundLocation();

	boolean canRepeat();

	int getRepeatDelay();

	float getVolume();

	float getPitch();

	float getXPosF();

	float getYPosF();

	float getZPosF();

	ISound.AttenuationType getAttenuationType();

	public static enum AttenuationType {
		NONE("NONE", 0, 0), LINEAR("LINEAR", 1, 2);
		private final int type;

		private AttenuationType(final String p_i45110_1_, final int p_i45110_2_, final int p_i45110_3_) {
			type = p_i45110_3_;
		}

		public int getTypeInt() {
			return type;
		}
	}
}
