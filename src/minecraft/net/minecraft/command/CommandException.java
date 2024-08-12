package net.minecraft.command;

public class CommandException extends Exception {

public static final int EaZy = 936;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Object[] errorObjects;
	// private static final String __OBFID = "http://https://fuckuskid00001187";

	public CommandException(final String p_i1359_1_, final Object... p_i1359_2_) {
		super(p_i1359_1_);
		errorObjects = p_i1359_2_;
	}

	public Object[] getErrorOjbects() {
		return errorObjects;
	}
}
