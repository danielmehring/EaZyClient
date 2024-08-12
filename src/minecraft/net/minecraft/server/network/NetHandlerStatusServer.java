package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {

public static final int EaZy = 1554;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final MinecraftServer server;
	private final NetworkManager networkManager;
	// private static final String __OBFID = "http://https://fuckuskid00001464";

	public NetHandlerStatusServer(final MinecraftServer serverIn, final NetworkManager netManager) {
		server = serverIn;
		networkManager = netManager;
	}

	/**
	 * Invoked when disconnecting, the parameter is a ChatComponent describing
	 * the reason for termination
	 */
	@Override
	public void onDisconnect(final IChatComponent reason) {}

	@Override
	public void processServerQuery(final C00PacketServerQuery packetIn) {
		networkManager.sendPacket(new S00PacketServerInfo(server.getServerStatusResponse()));
	}

	@Override
	public void processPing(final C01PacketPing packetIn) {
		networkManager.sendPacket(new S01PacketPong(packetIn.getClientTime()));
	}
}
