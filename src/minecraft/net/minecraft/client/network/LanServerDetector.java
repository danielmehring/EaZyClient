package net.minecraft.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class LanServerDetector {

public static final int EaZy = 627;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final AtomicInteger field_148551_a = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	// private static final String __OBFID = "http://https://fuckuskid00001133";

	public static class LanServer {
		private final String lanServerMotd;
		private final String lanServerIpPort;
		private long timeLastSeen;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001134";

		public LanServer(final String p_i1319_1_, final String p_i1319_2_) {
			lanServerMotd = p_i1319_1_;
			lanServerIpPort = p_i1319_2_;
			timeLastSeen = Minecraft.getSystemTime();
		}

		public String getServerMotd() {
			return lanServerMotd;
		}

		public String getServerIpPort() {
			return lanServerIpPort;
		}

		public void updateLastSeen() {
			timeLastSeen = Minecraft.getSystemTime();
		}
	}

	public static class LanServerList {
		private final List listOfLanServers = Lists.newArrayList();
		boolean wasUpdated;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001136";

		public synchronized boolean getWasUpdated() {
			return wasUpdated;
		}

		public synchronized void setWasNotUpdated() {
			wasUpdated = false;
		}

		public synchronized List getLanServers() {
			return Collections.unmodifiableList(listOfLanServers);
		}

		public synchronized void func_77551_a(final String p_77551_1_, final InetAddress p_77551_2_) {
			final String var3 = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
			String var4 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);

			if (var4 != null) {
				var4 = p_77551_2_.getHostAddress() + ":" + var4;
				boolean var5 = false;
				final Iterator var6 = listOfLanServers.iterator();

				while (var6.hasNext()) {
					final LanServerDetector.LanServer var7 = (LanServerDetector.LanServer) var6.next();

					if (var7.getServerIpPort().equals(var4)) {
						var7.updateLastSeen();
						var5 = true;
						break;
					}
				}

				if (!var5) {
					listOfLanServers.add(new LanServerDetector.LanServer(var3, var4));
					wasUpdated = true;
				}
			}
		}
	}

	public static class ThreadLanServerFind extends Thread {
		private final LanServerDetector.LanServerList localServerList;
		private final InetAddress broadcastAddress;
		private final MulticastSocket socket;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001135";

		public ThreadLanServerFind(final LanServerDetector.LanServerList p_i1320_1_) throws IOException {
			super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
			localServerList = p_i1320_1_;
			setDaemon(true);
			socket = new MulticastSocket(4445);
			broadcastAddress = InetAddress.getByName("224.0.2.60");
			socket.setSoTimeout(5000);
			socket.joinGroup(broadcastAddress);
		}

		@Override
		public void run() {
			final byte[] var2 = new byte[1024];

			while (!isInterrupted()) {
				final DatagramPacket var1 = new DatagramPacket(var2, var2.length);

				try {
					socket.receive(var1);
				} catch (final SocketTimeoutException var5) {
					continue;
				} catch (final IOException var6) {
					LanServerDetector.logger.error("Couldn\'t ping server", var6);
					break;
				}

				final String var3 = new String(var1.getData(), var1.getOffset(), var1.getLength());
				LanServerDetector.logger.debug(var1.getAddress() + ": " + var3);
				localServerList.func_77551_a(var3, var1.getAddress());
			}

			try {
				socket.leaveGroup(broadcastAddress);
			} catch (final IOException var4) {
			}

			socket.close();
		}
	}
}
