package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S48PacketResourcePackSend implements Packet {

public static final int EaZy = 1471;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String url;
	private String hash;
	// private static final String __OBFID = "http://https://fuckuskid00002293";

	public S48PacketResourcePackSend() {}

	public S48PacketResourcePackSend(final String url, final String hash) {
		this.url = url;
		this.hash = hash;

		if (hash.length() > 40) {
			throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		url = data.readStringFromBuffer(32767);
		hash = data.readStringFromBuffer(40);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(url);
		data.writeString(hash);
	}

	public void processPacket(final INetHandlerPlayClient handler) {
		handler.func_175095_a(this);
	}

	public String func_179783_a() {
		return url;
	}

	public String func_179784_b() {
		return hash;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
