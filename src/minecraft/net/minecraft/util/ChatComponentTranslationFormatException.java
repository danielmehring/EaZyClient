package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException {

public static final int EaZy = 1600;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001271";

	public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final String message) {
		super(String.format("Error parsing: %s: %s", new Object[] { component, message }));
	}

	public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final int index) {
		super(String.format("Invalid index %d requested for %s", new Object[] { index, component }));
	}

	public ChatComponentTranslationFormatException(final ChatComponentTranslation component, final Throwable cause) {
		super(String.format("Error while parsing: %s", new Object[] { component }), cause);
	}
}
