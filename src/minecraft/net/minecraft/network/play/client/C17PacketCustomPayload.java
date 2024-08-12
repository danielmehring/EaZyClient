package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C17PacketCustomPayload implements Packet {

public static final int EaZy = 1397;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String channel;
	private PacketBuffer data;
	// private static final String __OBFID = "http://https://fuckuskid00001356";

	public C17PacketCustomPayload() {}

	public C17PacketCustomPayload(final String p_i45945_1_, final PacketBuffer p_i45945_2_) {
		channel = p_i45945_1_;
		data = p_i45945_2_;

		if (p_i45945_2_.writerIndex() > 32767) {
			throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		channel = data.readStringFromBuffer(20);
		final int var2 = data.readableBytes();

		if (var2 >= 0 && var2 <= 32767) {
			this.data = new PacketBuffer(data.readBytes(var2));
		} else {
			throw new IOException("Payload may not be larger than 32767 bytes");
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(channel);
		data.writeBytes(this.data);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processVanilla250Packet(this);
	}

	public String getChannelName() {
		return channel;
	}

	public PacketBuffer getBufferData() {
		return data;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
