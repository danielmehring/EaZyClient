package me.EaZy.client.gui;

import org.apache.commons.codec.binary.Base64;

public class iUZVFUA {

    public static final int EaZy = 77;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static String get(final String s) {
        return rot13(new String(Base64.decodeBase64(rot13(s))));
    }

    private static String rot13(final String value) {

        final char[] values = value.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }
}
