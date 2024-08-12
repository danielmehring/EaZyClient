package net.minecraft.client.audio;

import java.util.List;

import com.google.common.collect.Lists;

public class SoundList {

public static final int EaZy = 445;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List soundList = Lists.newArrayList();

	/**
	 * if true it will override all the sounds from the resourcepacks loaded
	 * before
	 */
	private boolean replaceExisting;
	private SoundCategory category;
	// private static final String __OBFID = "http://https://fuckuskid00001121";

	public List getSoundList() {
		return soundList;
	}

	public boolean canReplaceExisting() {
		return replaceExisting;
	}

	public void setReplaceExisting(final boolean p_148572_1_) {
		replaceExisting = p_148572_1_;
	}

	public SoundCategory getSoundCategory() {
		return category;
	}

	public void setSoundCategory(final SoundCategory p_148571_1_) {
		category = p_148571_1_;
	}

	public static class SoundEntry {
		private String name;
		private float volume = 1.0F;
		private float pitch = 1.0F;
		private int field_148565_d = 1;
		private SoundList.SoundEntry.Type field_148566_e;
		private boolean field_148564_f;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001122";

		public SoundEntry() {
			field_148566_e = SoundList.SoundEntry.Type.FILE;
			field_148564_f = false;
		}

		public String getSoundEntryName() {
			return name;
		}

		public void setSoundEntryName(final String p_148561_1_) {
			name = p_148561_1_;
		}

		public float getSoundEntryVolume() {
			return volume;
		}

		public void setSoundEntryVolume(final float p_148553_1_) {
			volume = p_148553_1_;
		}

		public float getSoundEntryPitch() {
			return pitch;
		}

		public void setSoundEntryPitch(final float p_148559_1_) {
			pitch = p_148559_1_;
		}

		public int getSoundEntryWeight() {
			return field_148565_d;
		}

		public void setSoundEntryWeight(final int p_148554_1_) {
			field_148565_d = p_148554_1_;
		}

		public SoundList.SoundEntry.Type getSoundEntryType() {
			return field_148566_e;
		}

		public void setSoundEntryType(final SoundList.SoundEntry.Type p_148562_1_) {
			field_148566_e = p_148562_1_;
		}

		public boolean isStreaming() {
			return field_148564_f;
		}

		public void setStreaming(final boolean p_148557_1_) {
			field_148564_f = p_148557_1_;
		}

		public static enum Type {
			FILE("FILE", 0, "file"), SOUND_EVENT("SOUND_EVENT", 1, "event");
			private final String field_148583_c;

			private Type(final String p_i45109_1_, final int p_i45109_2_, final String p_i45109_3_) {
				field_148583_c = p_i45109_3_;
			}

			public static SoundList.SoundEntry.Type getType(final String p_148580_0_) {
				final SoundList.SoundEntry.Type[] var1 = values();
				final int var2 = var1.length;

				for (int var3 = 0; var3 < var2; ++var3) {
					final SoundList.SoundEntry.Type var4 = var1[var3];

					if (var4.field_148583_c.equals(p_148580_0_)) {
						return var4;
					}
				}

				return null;
			}
		}
	}
}
