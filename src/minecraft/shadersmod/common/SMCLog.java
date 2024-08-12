package shadersmod.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public abstract class SMCLog {

public static final int EaZy = 2029;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final String smcLogName = "SMC";
	public static final Logger logger = new SMCLog.SMCLogger("SMC");
	public static final Level SMCINFO = new SMCLog.SMCLevel("INF", 850, (SMCLog.NamelessClass763038833) null);
	public static final Level SMCCONFIG = new SMCLog.SMCLevel("CFG", 840, (SMCLog.NamelessClass763038833) null);
	public static final Level SMCFINE = new SMCLog.SMCLevel("FNE", 830, (SMCLog.NamelessClass763038833) null);
	public static final Level SMCFINER = new SMCLog.SMCLevel("FNR", 820, (SMCLog.NamelessClass763038833) null);
	public static final Level SMCFINEST = new SMCLog.SMCLevel("FNT", 810, (SMCLog.NamelessClass763038833) null);

	public static void log(final Level level, final String message) {
		if (logger.isLoggable(level)) {
			logger.log(level, message);
		}
	}

	public static void severe(final String message) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, message);
		}
	}

	public static void warning(final String message) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, message);
		}
	}

	public static void info(final String message) {
		if (logger.isLoggable(SMCINFO)) {
			logger.log(SMCINFO, message);
		}
	}

	public static void config(final String message) {
		if (logger.isLoggable(SMCCONFIG)) {
			logger.log(SMCCONFIG, message);
		}
	}

	public static void fine(final String message) {
		if (logger.isLoggable(SMCFINE)) {
			logger.log(SMCFINE, message);
		}
	}

	public static void finer(final String message) {
		if (logger.isLoggable(SMCFINER)) {
			logger.log(SMCFINER, message);
		}
	}

	public static void finest(final String message) {
		if (logger.isLoggable(SMCFINEST)) {
			logger.log(SMCFINEST, message);
		}
	}

	public static void log(final Level level, final String format, final Object... args) {
		if (logger.isLoggable(level)) {
			logger.log(level, String.format(format, args));
		}
	}

	public static void severe(final String format, final Object... args) {
		if (logger.isLoggable(Level.SEVERE)) {
			logger.log(Level.SEVERE, String.format(format, args));
		}
	}

	public static void warning(final String format, final Object... args) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, String.format(format, args));
		}
	}

	public static void info(final String format, final Object... args) {
		if (logger.isLoggable(SMCINFO)) {
			logger.log(SMCINFO, String.format(format, args));
		}
	}

	public static void config(final String format, final Object... args) {
		if (logger.isLoggable(SMCCONFIG)) {
			logger.log(SMCCONFIG, String.format(format, args));
		}
	}

	public static void fine(final String format, final Object... args) {
		if (logger.isLoggable(SMCFINE)) {
			logger.log(SMCFINE, String.format(format, args));
		}
	}

	public static void finer(final String format, final Object... args) {
		if (logger.isLoggable(SMCFINER)) {
			logger.log(SMCFINER, String.format(format, args));
		}
	}

	public static void finest(final String format, final Object... args) {
		if (logger.isLoggable(SMCFINEST)) {
			logger.log(SMCFINEST, String.format(format, args));
		}
	}

	static class NamelessClass763038833 {}

	private static class SMCFormatter extends Formatter {
		int tzOffset = Calendar.getInstance().getTimeZone().getRawOffset();

		@Override
		public String format(final LogRecord record) {
			final StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append("Shaders").append("]");

			if (record.getLevel() != SMCLog.SMCINFO) {
				sb.append("[").append(record.getLevel()).append("]");
			}

			sb.append(" ");
			sb.append(record.getMessage()).append("\n");
			return sb.toString();
		}
	}

	private static class SMCLevel extends Level {
		private SMCLevel(final String name, final int value) {
			super(name, value);
		}

		SMCLevel(final String x0, final int x1, final SMCLog.NamelessClass763038833 x2) {
			this(x0, x1);
		}
	}

	private static class SMCLogger extends Logger {
		SMCLogger(final String name) {
			super(name, (String) null);
			setUseParentHandlers(false);
			final SMCLog.SMCFormatter formatter = new SMCLog.SMCFormatter();
			final ConsoleHandler handler = new ConsoleHandler();
			handler.setFormatter(formatter);
			handler.setLevel(Level.ALL);
			addHandler(handler);

			try {
				final FileOutputStream e = new FileOutputStream("logs/shadersmod.log", false);
				final StreamHandler handler1 = new StreamHandler(e, formatter) {

					@Override
					public synchronized void publish(final LogRecord record) {
						super.publish(record);
						flush();
					}
				};
				handler1.setFormatter(formatter);
				handler1.setLevel(Level.ALL);
				addHandler(handler1);
			} catch (final IOException var5) {
				var5.printStackTrace();
			}

			setLevel(Level.ALL);
		}
	}
}
