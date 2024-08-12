package net.minecraft.util;

public class Util {

public static final int EaZy = 1659;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001633";

	public static Util.EnumOS getOSType() {
		final String var0 = System.getProperty("os.name").toLowerCase();
		return var0.contains("win") ? Util.EnumOS.WINDOWS
				: var0.contains("mac") ? Util.EnumOS.OSX
						: var0.contains("solaris") ? Util.EnumOS.SOLARIS
								: var0.contains("sunos") ? Util.EnumOS.SOLARIS
										: var0.contains("linux") ? Util.EnumOS.LINUX
												: var0.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
	}

	public static enum EnumOS {
		LINUX("LINUX", 0), SOLARIS("SOLARIS", 1), WINDOWS("WINDOWS", 2), OSX("OSX", 3), UNKNOWN("UNKNOWN", 4);

		private EnumOS(final String p_i1357_1_, final int p_i1357_2_) {}
	}
}
