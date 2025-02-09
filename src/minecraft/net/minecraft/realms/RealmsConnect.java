package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {

public static final int EaZy = 1506;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger LOGGER = LogManager.getLogger();
	private final RealmsScreen onlineScreen;
	private volatile boolean aborted = false;
	private NetworkManager connection;
	// private static final String __OBFID = "http://https://fuckuskid00001844";

	public RealmsConnect(final RealmsScreen p_i1079_1_) {
		onlineScreen = p_i1079_1_;
	}

	public void connect(final String p_connect_1_, final int p_connect_2_) {
		new Thread("Realms-connect-task") {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001808";
			@Override
			public void run() {
				InetAddress var1 = null;

				try {
					var1 = InetAddress.getByName(p_connect_1_);

					if (aborted) {
						return;
					}

					connection = NetworkManager.provideLanClient(var1, p_connect_2_);

					if (aborted) {
						return;
					}

					connection.setNetHandler(
							new NetHandlerLoginClient(connection, Minecraft.getMinecraft(), onlineScreen.getProxy()));

					if (aborted) {
						return;
					}

					connection.sendPacket(new C00Handshake(47, p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));

					if (aborted) {
						return;
					}

					connection.sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
				} catch (final UnknownHostException var5) {
					if (aborted) {
						return;
					}

					RealmsConnect.LOGGER.error("Couldn\'t connect to world", var5);
					Realms.setScreen(new DisconnectedRealmsScreen(onlineScreen, "connect.failed",
							new ChatComponentTranslation("disconnect.genericReason",
									new Object[] { "Unknown host \'" + p_connect_1_ + "\'" })));
				} catch (final Exception var6) {
					if (aborted) {
						return;
					}

					RealmsConnect.LOGGER.error("Couldn\'t connect to world", var6);
					String var3 = var6.toString();

					if (var1 != null) {
						final String var4 = var1.toString() + ":" + p_connect_2_;
						var3 = var3.replaceAll(var4, "");
					}

					Realms.setScreen(new DisconnectedRealmsScreen(onlineScreen, "connect.failed",
							new ChatComponentTranslation("disconnect.genericReason", new Object[] { var3 })));
				}
			}
		}.start();
	}

	public void abort() {
		aborted = true;
	}

	public void tick() {
		if (connection != null) {
			if (connection.isChannelOpen()) {
				connection.processReceivedPackets();
			} else {
				connection.checkDisconnected();
			}
		}
	}
}
