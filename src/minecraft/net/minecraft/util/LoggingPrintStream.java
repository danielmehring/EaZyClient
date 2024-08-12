package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream extends PrintStream {

public static final int EaZy = 1628;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger LOGGER = LogManager.getLogger();
	private final String domain;
	// private static final String __OBFID = "http://https://fuckuskid00002275";

	public LoggingPrintStream(final String p_i45927_1_, final OutputStream p_i45927_2_) {
		super(p_i45927_2_);
		domain = p_i45927_1_;
	}

	@Override
	public void println(final String p_println_1_) {
		logString(p_println_1_);
	}

	@Override
	public void println(final Object p_println_1_) {
		logString(String.valueOf(p_println_1_));
	}

	private void logString(final String p_179882_1_) {
		final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
		final StackTraceElement var3 = var2[Math.min(3, var2.length)];
		LOGGER.info("[{}]@.({}:{}): {}",
				new Object[] { domain, var3.getFileName(), var3.getLineNumber(), p_179882_1_ });
	}
}
