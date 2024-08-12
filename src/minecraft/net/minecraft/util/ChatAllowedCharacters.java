package net.minecraft.util;

public class ChatAllowedCharacters {

public static final int EaZy = 1593;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * Array of the special characters that are allowed in any text drawing of
	 * Minecraft.
	 */
	public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\u0000', '\f', '`', '?',
			'*', '\\', '<', '>', '|', '\"', ':' };
	// private static final String __OBFID = "http://https://fuckuskid00001606";

	public static boolean isAllowedCharacter(final char character) {
		return character >= 32 && character != 127;
	}

	/**
	 * Filter string by only keeping those characters for which
	 * isAllowedCharacter() returns true.
	 */
	public static String filterAllowedCharacters(final String input) {
		final StringBuilder var1 = new StringBuilder();
		final char[] var2 = input.toCharArray();
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final char var5 = var2[var4];

			if (isAllowedCharacter(var5)) {
				var1.append(var5);
			}
		}

		return var1.toString();
	}
}
