package net.minecraft.network.handshake.client;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake implements Packet {

	public static final int EaZy = 1354;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int protocolVersion;
	private String ip;
	private int port;
	private EnumConnectionState requestedState;
	// private static final String __OBFID = "http://https://fuckuskid00001372";

	public C00Handshake() {}

	public C00Handshake(final int p_i45266_1_, final String p_i45266_2_, final int p_i45266_3_,
			final EnumConnectionState p_i45266_4_) {
		protocolVersion = p_i45266_1_;
		ip = p_i45266_2_;

		if (Client.faker) {
			this.ip = this.ip + "\000" + (Client.fakedIP.isEmpty() ? "127.0.0.1" : Client.fakedIP) + "\000" + Client.mc.session.playerID;
		}

		port = p_i45266_3_;
		requestedState = p_i45266_4_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		protocolVersion = data.readVarIntFromBuffer();
		ip = data.readStringFromBuffer(255);
		port = data.readUnsignedShort();
		requestedState = EnumConnectionState.getById(data.readVarIntFromBuffer());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(protocolVersion);
		data.writeString(ip);
		data.writeShort(port);
		data.writeVarIntToBuffer(requestedState.getId());
	}

	public void func_180770_a(final INetHandlerHandshakeServer p_180770_1_) {
		p_180770_1_.processHandshake(this);
	}

	public EnumConnectionState getRequestedState() {
		return requestedState;
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180770_a((INetHandlerHandshakeServer) handler);
	}
}
