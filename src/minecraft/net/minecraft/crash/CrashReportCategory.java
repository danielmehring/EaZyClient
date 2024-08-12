package net.minecraft.crash;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;

public class CrashReportCategory {

public static final int EaZy = 1003;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String name;
	private final List children = Lists.newArrayList();
	private StackTraceElement[] stackTrace = new StackTraceElement[0];
	// private static final String __OBFID = "http://https://fuckuskid00001409";

	public CrashReportCategory(final CrashReport p_i1353_1_, final String name) {
		this.name = name;
	}

	public static String getCoordinateInfo(final double x, final double y, final double z) {
		return String.format("%.2f,%.2f,%.2f - %s", new Object[] { x, y, z, getCoordinateInfo(new BlockPos(x, y, z)) });
	}

	public static String getCoordinateInfo(final BlockPos pos) {
		final int var1 = pos.getX();
		final int var2 = pos.getY();
		final int var3 = pos.getZ();
		final StringBuilder var4 = new StringBuilder();

		try {
			var4.append(String.format("World: (%d,%d,%d)",
					new Object[] { var1, var2, var3}));
		} catch (final Throwable var17) {
			var4.append("(Error finding world loc)");
		}

		var4.append(", ");
		int var5;
		int var6;
		int var7;
		int var8;
		int var9;
		int var10;
		int var11;
		int var12;
		int var13;

		try {
			var5 = var1 >> 4;
			var6 = var3 >> 4;
			var7 = var1 & 15;
			var8 = var2 >> 4;
			var9 = var3 & 15;
			var10 = var5 << 4;
			var11 = var6 << 4;
			var12 = (var5 + 1 << 4) - 1;
			var13 = (var6 + 1 << 4) - 1;
			var4.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)",
					new Object[] { var7, var8, var9, var5, var6, var10, var11, var12, var13}));
		} catch (final Throwable var16) {
			var4.append("(Error finding chunk loc)");
		}

		var4.append(", ");

		try {
			var5 = var1 >> 9;
			var6 = var3 >> 9;
			var7 = var5 << 5;
			var8 = var6 << 5;
			var9 = (var5 + 1 << 5) - 1;
			var10 = (var6 + 1 << 5) - 1;
			var11 = var5 << 9;
			var12 = var6 << 9;
			var13 = (var5 + 1 << 9) - 1;
			final int var14 = (var6 + 1 << 9) - 1;
			var4.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
					new Object[] { var5, var6, var7, var8, var9, var10, var11, var12, var13, var14}));
		} catch (final Throwable var15) {
			var4.append("(Error finding world loc)");
		}

		return var4.toString();
	}

	/**
	 * Adds a Crashreport section with the given name with the value set to the
	 * result of the given Callable;
	 */
	public void addCrashSectionCallable(final String sectionName, final Callable callable) {
		try {
			addCrashSection(sectionName, callable.call());
		} catch (final Throwable var4) {
			addCrashSectionThrowable(sectionName, var4);
		}
	}

	/**
	 * Adds a Crashreport section with the given name with the given value
	 * (convered .toString())
	 */
	public void addCrashSection(final String sectionName, final Object value) {
		children.add(new CrashReportCategory.Entry(sectionName, value));
	}

	/**
	 * Adds a Crashreport section with the given name with the given Throwable
	 */
	public void addCrashSectionThrowable(final String sectionName, final Throwable throwable) {
		addCrashSection(sectionName, throwable);
	}

	/**
	 * Resets our stack trace according to the current trace, pruning the
	 * deepest 3 entries. The parameter indicates how many additional deepest
	 * entries to prune. Returns the number of entries in the resulting pruned
	 * stack trace.
	 */
	public int getPrunedStackTrace(final int size) {
		final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();

		if (var2.length <= 0) {
			return 0;
		} else {
			stackTrace = new StackTraceElement[var2.length - 3 - size];
			System.arraycopy(var2, 3 + size, stackTrace, 0, stackTrace.length);
			return stackTrace.length;
		}
	}

	/**
	 * Do the deepest two elements of our saved stack trace match the given
	 * elements, in order from the deepest?
	 */
	public boolean firstTwoElementsOfStackTraceMatch(final StackTraceElement s1, final StackTraceElement s2) {
		if (stackTrace.length != 0 && s1 != null) {
			final StackTraceElement var3 = stackTrace[0];

			if (var3.isNativeMethod() == s1.isNativeMethod() && var3.getClassName().equals(s1.getClassName())
					&& var3.getFileName().equals(s1.getFileName()) && var3.getMethodName().equals(s1.getMethodName())) {
				if (s2 != null != stackTrace.length > 1) {
					return false;
				} else if (s2 != null && !stackTrace[1].equals(s2)) {
					return false;
				} else {
					stackTrace[0] = s1;
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Removes the given number entries from the bottom of the stack trace.
	 */
	public void trimStackTraceEntriesFromBottom(final int amount) {
		final StackTraceElement[] var2 = new StackTraceElement[stackTrace.length - amount];
		System.arraycopy(stackTrace, 0, var2, 0, var2.length);
		stackTrace = var2;
	}

	public void appendToStringBuilder(final StringBuilder builder) {
		builder.append("-- ").append(name).append(" --\n");
		builder.append("Details:");
		final Iterator var2 = children.iterator();

		while (var2.hasNext()) {
			final CrashReportCategory.Entry var3 = (CrashReportCategory.Entry) var2.next();
			builder.append("\n\t");
			builder.append(var3.getKey());
			builder.append(": ");
			builder.append(var3.getValue());
		}

		if (stackTrace != null && stackTrace.length > 0) {
			builder.append("\nStacktrace:\n");
			final StackTraceElement[] var6 = stackTrace;
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
		}

		builder.append("\n");
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public static void addBlockInfo(final CrashReportCategory category, final BlockPos pos, final Block blockIn,
			final int blockData) {
		final int var4 = Block.getIdFromBlock(blockIn);
		category.addCrashSectionCallable("Block type", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001426";
			@Override
			public String call() {
				try {
					return String.format("ID #%d (%s // %s)", new Object[] { var4,
							blockIn.getUnlocalizedName(), blockIn.getClass().getCanonicalName() });
				} catch (final Throwable var2) {
					return "ID #" + var4;
				}
			}
		});
		category.addCrashSectionCallable("Block data value", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001441";
			@Override
			public String call() {
				if (blockData < 0) {
					return "Unknown? (Got " + blockData + ")";
				} else {
					final String var1 = String.format("%4s", new Object[] { Integer.toBinaryString(blockData) })
							.replace(" ", "0");
					return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { blockData, var1 });
				}
			}
		});
		category.addCrashSectionCallable("Block location", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001465";
			@Override
			public String call() {
				return CrashReportCategory.getCoordinateInfo(pos);
			}
		});
	}

	public static void addBlockInfo(final CrashReportCategory category, final BlockPos pos, final IBlockState state) {
		category.addCrashSectionCallable("Block", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002617";
			public String func_175753_a() {
				return state.toString();
			}

			@Override
			public Object call() {
				return func_175753_a();
			}
		});
		category.addCrashSectionCallable("Block location", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002616";
			public String func_175751_a() {
				return CrashReportCategory.getCoordinateInfo(pos);
			}

			@Override
			public Object call() {
				return func_175751_a();
			}
		});
	}

	static class Entry {
		private final String key;
		private final String value;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001489";

		public Entry(final String key, final Object value) {
			this.key = key;

			if (value == null) {
				this.value = "~~NULL~~";
			} else if (value instanceof Throwable) {
				final Throwable var3 = (Throwable) value;
				this.value = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
			} else {
				this.value = value.toString();
			}
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}
	}
}
