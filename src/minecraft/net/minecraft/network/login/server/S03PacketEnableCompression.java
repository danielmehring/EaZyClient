package net.minecraft.network.login.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

import java.io.IOException;

public class S03PacketEnableCompression implements Packet {

public static final int EaZy = 1364;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_179733_a;
	// private static final String __OBFID = "http://https://fuckuskid00002279";

	public S03PacketEnableCompression() {}

	public S03PacketEnableCompression(final int p_i45929_1_) {
		field_179733_a = p_i45929_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179733_a = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_179733_a);
	}

	public void func_179732_a(final INetHandlerLoginClient p_179732_1_) {
		p_179732_1_.func_180464_a(this);
	}

	public int func_179731_a() {
		return field_179733_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179732_a((INetHandlerLoginClient) handler);
	}
}
