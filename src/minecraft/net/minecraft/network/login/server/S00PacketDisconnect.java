package net.minecraft.network.login.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

public class S00PacketDisconnect implements Packet {

public static final int EaZy = 1361;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IChatComponent reason;
	// private static final String __OBFID = "http://https://fuckuskid00001377";

	public S00PacketDisconnect() {}

	public S00PacketDisconnect(final IChatComponent reasonIn) {
		reason = reasonIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		reason = data.readChatComponent();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeChatComponent(reason);
	}

	public void func_180772_a(final INetHandlerLoginClient p_180772_1_) {
		p_180772_1_.handleDisconnect(this);
	}

	public IChatComponent func_149603_c() {
		return reason;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180772_a((INetHandlerLoginClient) handler);
	}
}
