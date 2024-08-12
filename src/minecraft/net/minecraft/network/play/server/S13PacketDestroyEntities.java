package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S13PacketDestroyEntities implements Packet {

public static final int EaZy = 1421;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int[] field_149100_a;
	// private static final String __OBFID = "http://https://fuckuskid00001320";

	public S13PacketDestroyEntities() {}

	public S13PacketDestroyEntities(final int... p_i45211_1_) {
		field_149100_a = p_i45211_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149100_a = new int[data.readVarIntFromBuffer()];

		for (int var2 = 0; var2 < field_149100_a.length; ++var2) {
			field_149100_a[var2] = data.readVarIntFromBuffer();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149100_a.length);

		for (final int element : field_149100_a) {
			data.writeVarIntToBuffer(element);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleDestroyEntities(this);
	}

	public int[] func_149098_c() {
		return field_149100_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
