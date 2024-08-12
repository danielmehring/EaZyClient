/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.apache.logging.log4j.LogManager org.apache.logging.log4j.Logger
 */
package me.EaZy.client.utils;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

public class ServerPinger {

	public static final int EaZy = 245;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final AtomicInteger threadNumber = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	public ServerData server;
	private boolean done = false;
	private boolean failed = false;

	public void ping(final String ip) {
		this.ping(ip, 25565);
	}

	public void ping(final String ip, final int port) {
		server = new ServerData("", String.valueOf(ip) + ":" + port);
		new Thread("EaZy ServerPinger #" + threadNumber.incrementAndGet()) {

			@Override
			public void run() {
				final OldServerPinger pinger = new OldServerPinger();
				try {
					ServerPinger.logger.info("Pinging " + ip + ":" + port + "...");
					pinger.ping(server);
					ServerPinger.logger.info("Ping successful: " + ip + ":" + port);
				} catch (final UnknownHostException e) {
					ServerPinger.logger.info("Unknown host: " + ip + ":" + port);
					ServerPinger.access$0(ServerPinger.this, true);
				} catch (final Exception e2) {
					ServerPinger.logger.info("Ping failed: " + ip + ":" + port);
					ServerPinger.access$0(ServerPinger.this, true);
				}
				pinger.clearPendingNetworks();
				ServerPinger.access$1(ServerPinger.this, true);
			}
		}.start();
	}

	public boolean isStillPinging() {
		return !done;
	}

	public boolean isWorking() {
		return !failed;
	}

	public boolean isOtherVersion() {
		return server.version != 47;
	}

	private static /* synthetic */ void access$0(final ServerPinger serverPinger, final boolean bl) {
		serverPinger.failed = bl;
	}

	private static /* synthetic */ void access$1(final ServerPinger serverPinger, final boolean bl) {
		serverPinger.done = bl;
	}

}
