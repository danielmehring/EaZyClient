package net.minecraft.network.status.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C01PacketPing implements Packet {

public static final int EaZy = 1475;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private long clientTime;
	// private static final String __OBFID = "http://https://fuckuskid00001392";

	public C01PacketPing() {}

	public C01PacketPing(final long p_i45276_1_) {
		clientTime = p_i45276_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		clientTime = data.readLong();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeLong(clientTime);
	}

	public void func_180774_a(final INetHandlerStatusServer p_180774_1_) {
		p_180774_1_.processPing(this);
	}

	public long getClientTime() {
		return clientTime;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180774_a((INetHandlerStatusServer) handler);
	}
}
