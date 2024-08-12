package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S46PacketSetCompressionLevel implements Packet {

public static final int EaZy = 1469;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_179761_a;
	// private static final String __OBFID = "http://https://fuckuskid00002300";

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179761_a = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_179761_a);
	}

	public void func_179759_a(final INetHandlerPlayClient p_179759_1_) {
		p_179759_1_.func_175100_a(this);
	}

	public int func_179760_a() {
		return field_179761_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179759_a((INetHandlerPlayClient) handler);
	}
}
