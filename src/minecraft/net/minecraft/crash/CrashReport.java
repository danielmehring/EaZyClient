package net.minecraft.crash;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import me.EaZy.client.main.Client;
import me.EaZy.client.utils.HWID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ReportedException;
import net.minecraft.world.gen.layer.IntCache;
import optifine.CrashReporter;
import optifine.Reflector;

public class CrashReport {

	public static final int EaZy = 1002;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();

	/** Description of the crash report. */
	private final String description;

	/** The Throwable that is the "cause" for this crash and Crash Report. */
	private final Throwable cause;

	/** Category of crash */
	private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");

	/** Holds the keys and values of all crash report sections. */
	private final List crashReportSections = Lists.newArrayList();

	/** File of crash report. */
	private File crashReportFile;
	private boolean field_85059_f = true;
	private StackTraceElement[] stacktrace = new StackTraceElement[0];
	private boolean reported = false;

	public CrashReport(final String descriptionIn, final Throwable causeThrowable) {
		description = descriptionIn;
		cause = causeThrowable;
		populateEnvironment();
	}

	/**
	 * Populates this crash report with initial information about the running
	 * server and operating system / java environment
	 */
	private void populateEnvironment() {
		theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable() {
			@Override
			public String call() {
				return "1.8";
			}
		});
		theReportCategory.addCrashSectionCallable("Operating System", new Callable() {
			@Override
			public String call() {
				return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version "
						+ System.getProperty("os.version");
			}
		});
		theReportCategory.addCrashSectionCallable("Java Version", new Callable() {
			@Override
			public String call() {
				return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
			}
		});
		theReportCategory.addCrashSectionCallable("Java VM Version", new Callable() {
			@Override
			public String call() {
				return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), "
						+ System.getProperty("java.vm.vendor");
			}
		});
		theReportCategory.addCrashSectionCallable("Memory", new Callable() {
			@Override
			public String call() {
				final Runtime var1 = Runtime.getRuntime();
				final long var2 = var1.maxMemory();
				final long var4 = var1.totalMemory();
				final long var6 = var1.freeMemory();
				final long var8 = var2 / 1024L / 1024L;
				final long var10 = var4 / 1024L / 1024L;
				final long var12 = var6 / 1024L / 1024L;
				return var6 + " bytes (" + var12 + " MB) / " + var4 + " bytes (" + var10 + " MB) up to " + var2
						+ " bytes (" + var8 + " MB)";
			}
		});
		theReportCategory.addCrashSectionCallable("JVM Flags", new Callable() {
			@Override
			public String call() {
				final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
				final List var2 = var1.getInputArguments();
				int var3 = 0;
				final StringBuilder var4 = new StringBuilder();
				final Iterator var5 = var2.iterator();

				while (var5.hasNext()) {
					final String var6 = (String) var5.next();

					if (var6.startsWith("-X")) {
						if (var3++ > 0) {
							var4.append(" ");
						}

						var4.append(var6);
					}
				}

				return String.format("%d total; %s", new Object[] { var3, var4.toString() });
			}
		});
		theReportCategory.addCrashSectionCallable("IntCache", new Callable() {
			@Override
			public String call() {
				return IntCache.getCacheSizes();
			}
		});
		theReportCategory.addCrashSectionCallable("EaZy Version", new Callable() {
			@Override
			public String call() {
				return Double.toString(Client.currentVersionforUpdater);
			}
		});
		theReportCategory.addCrashSectionCallable("Error Code", new Callable() {
			@Override
			public String call() {
				return HWID.get();
			}
		});

		if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
			final Object instance = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
			Reflector.callString(instance, Reflector.FMLCommonHandler_enhanceCrashReport,
					new Object[] { this, theReportCategory });
		}
	}

	/**
	 * Returns the description of the Crash Report.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the Throwable object that is the cause for the crash and Crash
	 * Report.
	 */
	public Throwable getCrashCause() {
		return cause;
	}

	/**
	 * Gets the various sections of the crash report into the given
	 * StringBuilder
	 */
	public void getSectionsInStringBuilder(final StringBuilder builder) {
		if ((stacktrace == null || stacktrace.length <= 0) && crashReportSections.size() > 0) {
			stacktrace = ArrayUtils.subarray(((CrashReportCategory) crashReportSections.get(0)).getStackTrace(), 0, 1);
		}

		if (stacktrace != null && stacktrace.length > 0) {
			builder.append("-- Head --\n");
			builder.append("Stacktrace:\n");
			final StackTraceElement[] var6 = stacktrace;
			final int var7 = var6.length;

			for (int var4 = 0; var4 < var7; ++var4) {
				final StackTraceElement var5 = var6[var4];
				int i = -1;
				try {
					final Field f = ClassLoader.getSystemClassLoader().loadClass(var5.getClassName())
							.getDeclaredField("EaZy");
					f.setAccessible(true);
					i = f.getInt(CrashReport.class);
				} catch (final Exception e) {}
				if (i == -1) {
					builder.append("\t").append("at ").append(var5.toString());
				} else {
					builder.append("\t").append("at ").append("[" + i + ":" + var5.getLineNumber() + "]");
				}
				builder.append("\n");
			}

			builder.append("\n");
		}

		final Iterator var61 = crashReportSections.iterator();

		while (var61.hasNext()) {
			final CrashReportCategory var71 = (CrashReportCategory) var61.next();
			var71.appendToStringBuilder(builder);
			builder.append("\n\n");
		}

		theReportCategory.appendToStringBuilder(builder);
	}

	/**
	 * Gets the stack trace of the Throwable that caused this crash report, or
	 * if that fails, the cause .toString().
	 */
	public String getCauseStackTraceOrString() {
		StringWriter var1 = null;
		PrintWriter var2 = null;
		Object var3 = cause;

		if (((Throwable) var3).getMessage() == null) {
			if (var3 instanceof NullPointerException) {
				var3 = new NullPointerException(description);
			} else if (var3 instanceof StackOverflowError) {
				var3 = new StackOverflowError(description);
			} else if (var3 instanceof OutOfMemoryError) {
				var3 = new OutOfMemoryError(description);
			}

			((Throwable) var3).setStackTrace(cause.getStackTrace());
		}

		String var4 = ((Throwable) var3).toString();

		try {
			var1 = new StringWriter();
			var2 = new PrintWriter(var1);
			// ((Throwable) var3).printStackTrace(var2);

			final StringBuilder builder = new StringBuilder();
			final StackTraceElement[] var6 = ((Throwable) var3).getStackTrace();
			final int var7 = var6.length;

			for (int var41 = 0; var41 < var7; ++var41) {
				final StackTraceElement var5 = var6[var41];
				int i = -1;
				try {
					final Field f = ClassLoader.getSystemClassLoader().loadClass(var5.getClassName())
							.getDeclaredField("EaZy");
					f.setAccessible(true);
					i = f.getInt(CrashReport.class);
				} catch (final Exception e) {}
				if (i == -1) {
					builder.append("\t").append("at ").append(var5.toString());
				} else {
					builder.append("\t").append("at ").append("[" + i + ":" + var5.getLineNumber() + "]");
				}
				builder.append("\n");
			}

			builder.append("\n");

			var4 = builder.toString();
		}
		finally {
			IOUtils.closeQuietly(var1);
			IOUtils.closeQuietly(var2);
		}

		return var4;
	}

	/**
	 * Gets the complete report with headers, stack trace, and different
	 * sections as a string.
	 */
	public String getCompleteReport() {
		if (!reported) {
			reported = true;
			CrashReporter.onCrashReport(this, theReportCategory);
		}

		final StringBuilder var1 = new StringBuilder();
		var1.append("---- Minecraft Crash Report ----\n");
		Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[] { var1 });
		Reflector.call(Reflector.CoreModManager_onCrash, new Object[] { var1 });
		var1.append("// ");
		var1.append(getWittyComment());
		var1.append("\n\n");
		var1.append("Time: ");
		var1.append(new SimpleDateFormat().format(new Date()));
		var1.append("\n");
		var1.append("Description: ");
		var1.append(description);
		var1.append("\n\n");
		var1.append(getCauseStackTraceOrString());
		var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");

		for (int var2 = 0; var2 < 87; ++var2) {
			var1.append("-");
		}

		var1.append("\n\n");
		getSectionsInStringBuilder(var1);
		return var1.toString();
	}

	/**
	 * Gets the file this crash report is saved into.
	 */
	public File getFile() {
		return crashReportFile;
	}

	/**
	 * Saves this CrashReport to the given file and returns a value indicating
	 * whether we were successful at doing so.
	 */
	public boolean saveToFile(final File toFile) {
		if (crashReportFile != null) {
			return false;
		} else {
			if (toFile.getParentFile() != null) {
				toFile.getParentFile().mkdirs();
			}

			try {
				final FileWriter var3 = new FileWriter(toFile);
				var3.write(getCompleteReport());
				var3.close();
				crashReportFile = toFile;
				return true;
			} catch (final Throwable var31) {
				logger.error("Could not save crash report to " + toFile, var31);
				return false;
			}
		}
	}

	public CrashReportCategory getCategory() {
		return theReportCategory;
	}

	/**
	 * Creates a CrashReportCategory
	 */
	public CrashReportCategory makeCategory(final String name) {
		return makeCategoryDepth(name, 1);
	}

	/**
	 * Creates a CrashReportCategory for the given stack trace depth
	 */
	public CrashReportCategory makeCategoryDepth(final String categoryName, final int stacktraceLength) {
		final CrashReportCategory var3 = new CrashReportCategory(this, categoryName);

		if (field_85059_f) {
			final int var4 = var3.getPrunedStackTrace(stacktraceLength);
			final StackTraceElement[] var5 = cause.getStackTrace();
			StackTraceElement var6 = null;
			StackTraceElement var7 = null;
			final int var8 = var5.length - var4;

			if (var8 < 0) {
				System.out.println("Negative index in crash report handler (" + var5.length + "/" + var4 + ")");
			}

			if (var5 != null && 0 <= var8 && var8 < var5.length) {
				var6 = var5[var8];

				if (var5.length + 1 - var4 < var5.length) {
					var7 = var5[var5.length + 1 - var4];
				}
			}

			field_85059_f = var3.firstTwoElementsOfStackTraceMatch(var6, var7);

			if (var4 > 0 && !crashReportSections.isEmpty()) {
				final CrashReportCategory var9 = (CrashReportCategory) crashReportSections
						.get(crashReportSections.size() - 1);
				var9.trimStackTraceEntriesFromBottom(var4);
			} else if (var5 != null && var5.length >= var4 && 0 <= var8 && var8 < var5.length) {
				stacktrace = new StackTraceElement[var8];
				System.arraycopy(var5, 0, stacktrace, 0, stacktrace.length);
			} else {
				field_85059_f = false;
			}
		}

		crashReportSections.add(var3);
		return var3;
	}

	/**
	 * Gets a random witty comment for inclusion in this CrashReport
	 */
	private static String getWittyComment() {
		final String[] var0 = new String[] { "Who set us up the TNT?",
				"Everything\'s going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?",
				"Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I\'m sorry, Dave.",
				"I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...",
				"Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.",
				"You should try our sister game, Minceraft!", "Don\'t be sad. I\'ll do better next time, I promise!",
				"Don\'t be sad, have a hug! <3", "I just don\'t know what went wrong :(", "Shall we play a game?",
				"Quite honestly, I wouldn\'t worry myself about that.", "I bet Cylons wouldn\'t have this problem.",
				"Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?",
				"Hi. I\'m Minecraft, and I\'m a crashaholic.", "Ooh. Shiny.", "This doesn\'t make any sense!",
				"Why is it breaking :(", "Don\'t do that.", "Ouch. That hurt :(", "You\'re mean.",
				"This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!",
				"But it works on my machine." };

		try {
			return var0[(int) (System.nanoTime() % var0.length)];
		} catch (final Throwable var2) {
			return "Witty comment unavailable :(";
		}
	}

	/**
	 * Creates a crash report for the exception
	 */
	public static CrashReport makeCrashReport(final Throwable causeIn, final String descriptionIn) {
		CrashReport var2;

		if (causeIn instanceof ReportedException) {
			var2 = ((ReportedException) causeIn).getCrashReport();
		} else {
			var2 = new CrashReport(descriptionIn + " ### " + Minecraft.mcProfiler.getNameOfLastSection(), causeIn);
		}

		return var2;
	}
}
