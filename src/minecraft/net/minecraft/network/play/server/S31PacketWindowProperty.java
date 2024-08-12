package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S31PacketWindowProperty implements Packet {

public static final int EaZy = 1448;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149186_a;
	private int field_149184_b;
	private int field_149185_c;
	// private static final String __OBFID = "http://https://fuckuskid00001295";

	public S31PacketWindowProperty() {}

	public S31PacketWindowProperty(final int p_i45187_1_, final int p_i45187_2_, final int p_i45187_3_) {
		field_149186_a = p_i45187_1_;
		field_149184_b = p_i45187_2_;
		field_149185_c = p_i45187_3_;
	}

	public void func_180733_a(final INetHandlerPlayClient p_180733_1_) {
		p_180733_1_.handleWindowProperty(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149186_a = data.readUnsignedByte();
		field_149184_b = data.readShort();
		field_149185_c = data.readShort();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_149186_a);
		data.writeShort(field_149184_b);
		data.writeShort(field_149185_c);
	}

	public int func_149182_c() {
		return field_149186_a;
	}

	public int func_149181_d() {
		return field_149184_b;
	}

	public int func_149180_e() {
		return field_149185_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180733_a((INetHandlerPlayClient) handler);
	}
}
