package net.minecraft.world.storage;

import net.minecraft.world.WorldSettings;

public class SaveFormatComparator implements Comparable {

public static final int EaZy = 1846;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** the file name of this save */
	private final String fileName;

	/** the displayed name of this save file */
	private final String displayName;
	private final long lastTimePlayed;
	private final long sizeOnDisk;
	private final boolean requiresConversion;

	/** Instance of EnumGameType. */
	private final WorldSettings.GameType theEnumGameType;
	private final boolean hardcore;
	private final boolean cheatsEnabled;
	// private static final String __OBFID = "http://https://fuckuskid00000601";

	public SaveFormatComparator(final String p_i2161_1_, final String p_i2161_2_, final long p_i2161_3_,
			final long p_i2161_5_, final WorldSettings.GameType p_i2161_7_, final boolean p_i2161_8_,
			final boolean p_i2161_9_, final boolean p_i2161_10_) {
		fileName = p_i2161_1_;
		displayName = p_i2161_2_;
		lastTimePlayed = p_i2161_3_;
		sizeOnDisk = p_i2161_5_;
		theEnumGameType = p_i2161_7_;
		requiresConversion = p_i2161_8_;
		hardcore = p_i2161_9_;
		cheatsEnabled = p_i2161_10_;
	}

	/**
	 * return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * return the display name of the save
	 */
	public String getDisplayName() {
		return displayName;
	}

	public long func_154336_c() {
		return sizeOnDisk;
	}

	public boolean requiresConversion() {
		return requiresConversion;
	}

	public long getLastTimePlayed() {
		return lastTimePlayed;
	}

	public int compareTo(final SaveFormatComparator p_compareTo_1_) {
		return lastTimePlayed < p_compareTo_1_.lastTimePlayed ? 1
				: lastTimePlayed > p_compareTo_1_.lastTimePlayed ? -1 : fileName.compareTo(p_compareTo_1_.fileName);
	}

	/**
	 * Gets the EnumGameType.
	 */
	public WorldSettings.GameType getEnumGameType() {
		return theEnumGameType;
	}

	public boolean isHardcoreModeEnabled() {
		return hardcore;
	}

	/**
	 * @return {@code true} if cheats are enabled for this world
	 */
	public boolean getCheatsEnabled() {
		return cheatsEnabled;
	}

	@Override
	public int compareTo(final Object p_compareTo_1_) {
		return this.compareTo((SaveFormatComparator) p_compareTo_1_);
	}
}
