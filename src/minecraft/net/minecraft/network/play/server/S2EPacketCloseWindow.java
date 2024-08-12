package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2EPacketCloseWindow implements Packet {

public static final int EaZy = 1445;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148896_a;
	// private static final String __OBFID = "http://https://fuckuskid00001292";

	public S2EPacketCloseWindow() {}

	public S2EPacketCloseWindow(final int p_i45183_1_) {
		field_148896_a = p_i45183_1_;
	}

	public void func_180731_a(final INetHandlerPlayClient p_180731_1_) {
		p_180731_1_.handleCloseWindow(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148896_a = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_148896_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180731_a((INetHandlerPlayClient) handler);
	}
}
