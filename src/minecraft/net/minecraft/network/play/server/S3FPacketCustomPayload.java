package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S3FPacketCustomPayload implements Packet {

public static final int EaZy = 1462;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String channel;
	private PacketBuffer data;
	// private static final String __OBFID = "http://https://fuckuskid00001297";

	public S3FPacketCustomPayload() {}

	public S3FPacketCustomPayload(final String channelName, final PacketBuffer dataIn) {
		channel = channelName;
		data = dataIn;

		if (dataIn.writerIndex() > 1048576) {
			throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		channel = data.readStringFromBuffer(20);
		final int var2 = data.readableBytes();

		if (var2 >= 0 && var2 <= 1048576) {
			this.data = new PacketBuffer(data.readBytes(var2));
		} else {
			throw new IOException("Payload may not be larger than 1048576 bytes");
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

	public void process(final INetHandlerPlayClient p_180734_1_) {
		p_180734_1_.handleCustomPayload(this);
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
		process((INetHandlerPlayClient) handler);
	}
}
