package net.minecraft.profiler;

import net.minecraft.util.HttpUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.google.common.collect.Maps;

public class PlayerUsageSnooper {

public static final int EaZy = 1498;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map field_152773_a = Maps.newHashMap();
	private final Map field_152774_b = Maps.newHashMap();
	private final String uniqueID = UUID.randomUUID().toString();

	/** URL of the server to send the report to */
	private final URL serverUrl;
	private final IPlayerUsage playerStatsCollector;

	/** set to fire the snooperThread every 15 mins */
	private final Timer threadTrigger = new Timer("Snooper Timer", true);
	private final Object syncLock = new Object();
	private final long minecraftStartTimeMilis;
	private boolean isRunning;

	/** incremented on every getSelfCounterFor */
	private int selfCounter;
	// private static final String __OBFID = "http://https://fuckuskid00001515";

	public PlayerUsageSnooper(final String p_i1563_1_, final IPlayerUsage p_i1563_2_, final long p_i1563_3_) {
		try {
			serverUrl = new URL("http://snoop.minecraft.net/" + p_i1563_1_ + "?version=" + 2);
		} catch (final MalformedURLException var6) {
			throw new IllegalArgumentException();
		}

		playerStatsCollector = p_i1563_2_;
		minecraftStartTimeMilis = p_i1563_3_;
	}

	/**
	 * Note issuing start multiple times is not an error.
	 */
	public void startSnooper() {
		if (!isRunning) {
			isRunning = true;
			func_152766_h();
			threadTrigger.schedule(new TimerTask() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00001516";
				@Override
				public void run() {
					if (playerStatsCollector.isSnooperEnabled()) {
						HashMap var1;

						synchronized (syncLock) {
							var1 = Maps.newHashMap(field_152774_b);

							if (selfCounter == 0) {
								var1.putAll(field_152773_a);
							}

							var1.put("snooper_count",
                                                                PlayerUsageSnooper.access$308(PlayerUsageSnooper.this));
							var1.put("snooper_token", uniqueID);
						}

						HttpUtil.postMap(serverUrl, var1, true);
					}
				}
			}, 0L, 900000L);
		}
	}

	private void func_152766_h() {
		addJvmArgsToSnooper();
		addClientStat("snooper_token", uniqueID);
		addStatToSnooper("snooper_token", uniqueID);
		addStatToSnooper("os_name", System.getProperty("os.name"));
		addStatToSnooper("os_version", System.getProperty("os.version"));
		addStatToSnooper("os_architecture", System.getProperty("os.arch"));
		addStatToSnooper("java_version", System.getProperty("java.version"));
		addStatToSnooper("version", "1.8");
		playerStatsCollector.addServerTypeToSnooper(this);
	}

	private void addJvmArgsToSnooper() {
		final RuntimeMXBean var1 = ManagementFactory.getRuntimeMXBean();
		final List var2 = var1.getInputArguments();
		int var3 = 0;
		final Iterator var4 = var2.iterator();

		while (var4.hasNext()) {
			final String var5 = (String) var4.next();

			if (var5.startsWith("-X")) {
				addClientStat("jvm_arg[" + var3++ + "]", var5);
			}
		}

		addClientStat("jvm_args", var3);
	}

	public void addMemoryStatsToSnooper() {
		addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
		addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
		addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
		addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
		playerStatsCollector.addServerStatsToSnooper(this);
	}

	public void addClientStat(final String p_152768_1_, final Object p_152768_2_) {
		synchronized (syncLock) {
			field_152774_b.put(p_152768_1_, p_152768_2_);
		}
	}

	public void addStatToSnooper(final String p_152767_1_, final Object p_152767_2_) {
		synchronized (syncLock) {
			field_152773_a.put(p_152767_1_, p_152767_2_);
		}
	}

	public Map getCurrentStats() {
		final LinkedHashMap var1 = Maps.newLinkedHashMap();
		synchronized (syncLock) {
			addMemoryStatsToSnooper();
			Iterator var3 = field_152773_a.entrySet().iterator();
			Entry var4;

			while (var3.hasNext()) {
				var4 = (Entry) var3.next();
				var1.put(var4.getKey(), var4.getValue().toString());
			}

			var3 = field_152774_b.entrySet().iterator();

			while (var3.hasNext()) {
				var4 = (Entry) var3.next();
				var1.put(var4.getKey(), var4.getValue().toString());
			}

			return var1;
		}
	}

	public boolean isSnooperRunning() {
		return isRunning;
	}

	public void stopSnooper() {
		threadTrigger.cancel();
	}

	public String getUniqueID() {
		return uniqueID;
	}

	/**
	 * Returns the saved value of System#currentTimeMillis when the game started
	 */
	public long getMinecraftStartTimeMillis() {
		return minecraftStartTimeMilis;
	}

	static int access$308(final PlayerUsageSnooper p_access$308_0_) {
		return p_access$308_0_.selfCounter++;
	}
}
