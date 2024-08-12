package net.minecraft.network.status.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

import java.io.IOException;

public class S01PacketPong implements Packet {

public static final int EaZy = 1480;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private long clientTime;
	// private static final String __OBFID = "http://https://fuckuskid00001383";

	public S01PacketPong() {}

	public S01PacketPong(final long time) {
		clientTime = time;
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

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerStatusClient handler) {
		handler.handlePong(this);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerStatusClient) handler);
	}
}
