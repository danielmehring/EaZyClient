package me.EaZy.client.alts;

public class EmailAllowedCharacters {

    public static final int EaZy = 26;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static boolean isAllowedCharacter(final char character) {
        return character >= ' ' && character != '';
    }

    public static String filterAllowedCharacters(final String input) {
        final StringBuilder var1 = new StringBuilder();
        final char[] var2 = input.toCharArray();
        final int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            final char var5 = var2[var4];
            if (EmailAllowedCharacters.isAllowedCharacter(var5)) {
                var1.append(var5);
            }
            ++var4;
        }
        return var1.toString();
    }
}
