package net.minecraft.util;

public class StatCollector {

public static final int EaZy = 1652;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static StringTranslate localizedName = StringTranslate.getInstance();

	/**
	 * A StringTranslate instance using the hardcoded default locale (en_US).
	 * Used as a fallback in case the shared StringTranslate singleton instance
	 * fails to translate a key.
	 */
	private static StringTranslate fallbackTranslator = new StringTranslate();
	// private static final String __OBFID = "http://https://fuckuskid00001211";

	/**
	 * Translates a Stat name
	 */
	public static String translateToLocal(final String p_74838_0_) {
		return localizedName.translateKey(p_74838_0_);
	}

	/**
	 * Translates a Stat name with format args
	 */
	public static String translateToLocalFormatted(final String p_74837_0_, final Object... p_74837_1_) {
		return localizedName.translateKeyFormat(p_74837_0_, p_74837_1_);
	}

	/**
	 * Translates a Stat name using the fallback (hardcoded en_US) locale. Looks
	 * like it's only intended to be used if translateToLocal fails.
	 */
	public static String translateToFallback(final String p_150826_0_) {
		return fallbackTranslator.translateKey(p_150826_0_);
	}

	/**
	 * Determines whether or not translateToLocal will find a translation for
	 * the given key.
	 */
	public static boolean canTranslate(final String p_94522_0_) {
		return localizedName.isKeyTranslated(p_94522_0_);
	}

	/**
	 * Gets the time, in milliseconds since epoch, that the translation mapping
	 * was last updated
	 */
	public static long getLastTranslationUpdateTimeInMilliseconds() {
		return localizedName.getLastUpdateTimeInMilliseconds();
	}
}
