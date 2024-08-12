package net.minecraft.profiler;

import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import optifine.Config;
import optifine.Lagometer;

public class Profiler {

public static final int EaZy = 1499;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/** List of parent sections */
	private final List sectionList = Lists.newArrayList();

	/** List of timestamps (System.nanoTime) */
	private final List timestampList = Lists.newArrayList();

	/** Flag profiling enabled */
	public boolean profilingEnabled;

	/** Current profiling section */
	private String profilingSection = "";

	/** Profiling map */
	private final Map profilingMap = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00001497";
	public boolean profilerGlobalEnabled = true;
	private boolean profilerLocalEnabled;
	private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
	private static final int HASH_TICK = "tick".hashCode();
	private static final int HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
	private static final int HASH_RENDER = "render".hashCode();
	private static final int HASH_DISPLAY = "display".hashCode();

	public Profiler() {
		profilerLocalEnabled = profilerGlobalEnabled;
	}

	/**
	 * Clear profiling.
	 */
	public void clearProfiling() {
		profilingMap.clear();
		profilingSection = "";
		sectionList.clear();
		profilerLocalEnabled = profilerGlobalEnabled;
	}

	/**
	 * Start section
	 */
	public void startSection(final String name) {
		int hashName;

		if (Lagometer.isActive()) {
			hashName = name.hashCode();

			if (hashName == HASH_SCHEDULED_EXECUTABLES && name.equals("scheduledExecutables")) {
				Lagometer.timerScheduledExecutables.start();
			} else if (hashName == HASH_TICK && name.equals("tick") && Config.isMinecraftThread()) {
				Lagometer.timerScheduledExecutables.end();
				Lagometer.timerTick.start();
			} else if (hashName == HASH_PRE_RENDER_ERRORS && name.equals("preRenderErrors")) {
				Lagometer.timerTick.end();
			}
		}

		if (Config.isFastRender()) {
			hashName = name.hashCode();

			if (hashName == HASH_RENDER && name.equals("render")) {
				GlStateManager.clearEnabled = false;
			} else if (hashName == HASH_DISPLAY && name.equals("display")) {
				GlStateManager.clearEnabled = true;
			}
		}

		if (profilerLocalEnabled) {
			if (profilingEnabled) {
				if (profilingSection.length() > 0) {
					profilingSection = profilingSection + ".";
				}

				profilingSection = profilingSection + name;
				sectionList.add(profilingSection);
				timestampList.add(System.nanoTime());
			}
		}
	}

	/**
	 * End section
	 */
	public void endSection() {
		if (profilerLocalEnabled) {
			if (profilingEnabled) {
				final long var1 = System.nanoTime();
				final long var3 = ((Long) timestampList.remove(timestampList.size() - 1));
				sectionList.remove(sectionList.size() - 1);
				final long var5 = var1 - var3;

				if (profilingMap.containsKey(profilingSection)) {
					profilingMap.put(profilingSection, ((Long) profilingMap.get(profilingSection)) + var5);
				} else {
					profilingMap.put(profilingSection, var5);
				}

				if (var5 > 100000000L) {
					logger.warn("Something\'s taking too long! \'" + profilingSection + "\' took aprox "
							+ var5 / 1000000.0D + " ms");
				}

				profilingSection = !sectionList.isEmpty() ? (String) sectionList.get(sectionList.size() - 1) : "";
			}
		}
	}

	/**
	 * Get profiling data
	 */
	public List getProfilingData(String p_76321_1_) {
		profilerLocalEnabled = profilerGlobalEnabled;

		if (!profilerLocalEnabled) {
			return new ArrayList(Arrays.asList(new Profiler.Result[] { new Profiler.Result("root", 0.0D, 0.0D) }));
		} else if (!profilingEnabled) {
			return null;
		} else {
			long var3 = profilingMap.containsKey("root") ? ((Long) profilingMap.get("root")) : 0L;
			final long var5 = profilingMap.containsKey(p_76321_1_) ? ((Long) profilingMap.get(p_76321_1_))
					: -1L;
			final ArrayList var7 = Lists.newArrayList();

			if (p_76321_1_.length() > 0) {
				p_76321_1_ = p_76321_1_ + ".";
			}

			long var8 = 0L;
			final Iterator var10 = profilingMap.keySet().iterator();

			while (var10.hasNext()) {
				final String var20 = (String) var10.next();

				if (var20.length() > p_76321_1_.length() && var20.startsWith(p_76321_1_)
						&& var20.indexOf(".", p_76321_1_.length() + 1) < 0) {
					var8 += ((Long) profilingMap.get(var20));
				}
			}

			final float var201 = var8;

			if (var8 < var5) {
				var8 = var5;
			}

			if (var3 < var8) {
				var3 = var8;
			}

			Iterator var21 = profilingMap.keySet().iterator();
			String var12;

			while (var21.hasNext()) {
				var12 = (String) var21.next();

				if (var12.length() > p_76321_1_.length() && var12.startsWith(p_76321_1_)
						&& var12.indexOf(".", p_76321_1_.length() + 1) < 0) {
					final long var13 = ((Long) profilingMap.get(var12));
					final double var15 = var13 * 100.0D / var8;
					final double var17 = var13 * 100.0D / var3;
					final String var19 = var12.substring(p_76321_1_.length());
					var7.add(new Profiler.Result(var19, var15, var17));
				}
			}

			var21 = profilingMap.keySet().iterator();

			while (var21.hasNext()) {
				var12 = (String) var21.next();
				profilingMap.put(var12, ((Long) profilingMap.get(var12)) * 950L / 1000L);
			}

			if (var8 > var201) {
				var7.add(new Profiler.Result("unspecified", (var8 - var201) * 100.0D / var8,
						(var8 - var201) * 100.0D / var3));
			}

			Collections.sort(var7);
			var7.add(0, new Profiler.Result(p_76321_1_, 100.0D, var8 * 100.0D / var3));
			return var7;
		}
	}

	/**
	 * End current section and start a new section
	 */
	public void endStartSection(final String name) {
		if (profilerLocalEnabled) {
			endSection();
			startSection(name);
		}
	}

	public String getNameOfLastSection() {
		return sectionList.isEmpty() ? "[UNKNOWN]" : (String) sectionList.get(sectionList.size() - 1);
	}

	public static final class Result implements Comparable {
		public double field_76332_a;
		public double field_76330_b;
		public String field_76331_c;

		public Result(final String p_i1554_1_, final double p_i1554_2_, final double p_i1554_4_) {
			field_76331_c = p_i1554_1_;
			field_76332_a = p_i1554_2_;
			field_76330_b = p_i1554_4_;
		}

		public int compareTo(final Profiler.Result p_compareTo_1_) {
			return p_compareTo_1_.field_76332_a < field_76332_a ? -1
					: p_compareTo_1_.field_76332_a > field_76332_a ? 1
							: p_compareTo_1_.field_76331_c.compareTo(field_76331_c);
		}

		public int func_76329_a() {
			return (field_76331_c.hashCode() & 11184810) + 4473924;
		}

		@Override
		public int compareTo(final Object p_compareTo_1_) {
			return this.compareTo((Profiler.Result) p_compareTo_1_);
		}
	}
}
