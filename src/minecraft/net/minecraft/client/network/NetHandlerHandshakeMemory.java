package net.minecraft.client.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.IChatComponent;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {

public static final int EaZy = 628;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final MinecraftServer field_147385_a;
	private final NetworkManager field_147384_b;
	// private static final String __OBFID = "http://https://fuckuskid00001445";

	public NetHandlerHandshakeMemory(final MinecraftServer p_i45287_1_, final NetworkManager p_i45287_2_) {
		field_147385_a = p_i45287_1_;
		field_147384_b = p_i45287_2_;
	}

	/**
	 * There are two recognized intentions for initiating a handshake: logging
	 * in and acquiring server status. The NetworkManager's protocol will be
	 * reconfigured according to the specified intention, although a
	 * login-intention must pass a versioncheck or receive a disconnect
	 * otherwise
	 */
	@Override
	public void processHandshake(final C00Handshake packetIn) {
		field_147384_b.setConnectionState(packetIn.getRequestedState());
		field_147384_b.setNetHandler(new NetHandlerLoginServer(field_147385_a, field_147384_b));
	}

	/**
	 * Invoked when disconnecting, the parameter is a ChatComponent describing
	 * the reason for termination
	 */
	@Override
	public void onDisconnect(final IChatComponent reason) {}
}
