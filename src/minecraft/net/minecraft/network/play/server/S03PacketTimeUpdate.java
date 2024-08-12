package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S03PacketTimeUpdate implements Packet {

public static final int EaZy = 1405;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private long field_149369_a;
	private long field_149368_b;
	// private static final String __OBFID = "http://https://fuckuskid00001337";

	public S03PacketTimeUpdate() {}

	public S03PacketTimeUpdate(final long p_i45230_1_, final long p_i45230_3_, final boolean p_i45230_5_) {
		field_149369_a = p_i45230_1_;
		field_149368_b = p_i45230_3_;

		if (!p_i45230_5_) {
			field_149368_b = -field_149368_b;

			if (field_149368_b == 0L) {
				field_149368_b = -1L;
			}
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149369_a = data.readLong();
		field_149368_b = data.readLong();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeLong(field_149369_a);
		data.writeLong(field_149368_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleTimeUpdate(this);
	}

	public long func_149366_c() {
		return field_149369_a;
	}

	public long func_149365_d() {
		return field_149368_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
