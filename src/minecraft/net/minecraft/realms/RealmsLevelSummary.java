package net.minecraft.realms;

import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsLevelSummary implements Comparable {

public static final int EaZy = 1508;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final SaveFormatComparator levelSummary;
	// private static final String __OBFID = "http://https://fuckuskid00001857";

	public RealmsLevelSummary(final SaveFormatComparator p_i1109_1_) {
		levelSummary = p_i1109_1_;
	}

	public int getGameMode() {
		return levelSummary.getEnumGameType().getID();
	}

	public String getLevelId() {
		return levelSummary.getFileName();
	}

	public boolean hasCheats() {
		return levelSummary.getCheatsEnabled();
	}

	public boolean isHardcore() {
		return levelSummary.isHardcoreModeEnabled();
	}

	public boolean isRequiresConversion() {
		return levelSummary.requiresConversion();
	}

	public String getLevelName() {
		return levelSummary.getDisplayName();
	}

	public long getLastPlayed() {
		return levelSummary.getLastTimePlayed();
	}

	public int compareTo(final SaveFormatComparator p_compareTo_1_) {
		return levelSummary.compareTo(p_compareTo_1_);
	}

	public long getSizeOnDisk() {
		return levelSummary.func_154336_c();
	}

	public int compareTo(final RealmsLevelSummary p_compareTo_1_) {
		return levelSummary.getLastTimePlayed() < p_compareTo_1_.getLastPlayed() ? 1
				: levelSummary.getLastTimePlayed() > p_compareTo_1_.getLastPlayed() ? -1
						: levelSummary.getFileName().compareTo(p_compareTo_1_.getLevelId());
	}

	@Override
	public int compareTo(final Object p_compareTo_1_) {
		return this.compareTo((RealmsLevelSummary) p_compareTo_1_);
	}
}
