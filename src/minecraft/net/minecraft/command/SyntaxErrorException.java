package net.minecraft.command;

public class SyntaxErrorException extends CommandException {

public static final int EaZy = 1000;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001189";

	public SyntaxErrorException() {
		this("commands.generic.snytax", new Object[0]);
	}

	public SyntaxErrorException(final String p_i1361_1_, final Object... p_i1361_2_) {
		super(p_i1361_1_, p_i1361_2_);
	}
}
