package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S09PacketHeldItemChange implements Packet {

public static final int EaZy = 1411;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149387_a;
	// private static final String __OBFID = "http://https://fuckuskid00001324";

	public S09PacketHeldItemChange() {}

	public S09PacketHeldItemChange(final int p_i45215_1_) {
		field_149387_a = p_i45215_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149387_a = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_149387_a);
	}

	public void func_180746_a(final INetHandlerPlayClient p_180746_1_) {
		p_180746_1_.handleHeldItemChange(this);
	}

	public int func_149385_c() {
		return field_149387_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180746_a((INetHandlerPlayClient) handler);
	}
}
