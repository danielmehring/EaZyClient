package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S0DPacketCollectItem implements Packet {

public static final int EaZy = 1415;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149357_a;
	private int field_149356_b;
	// private static final String __OBFID = "http://https://fuckuskid00001339";

	public S0DPacketCollectItem() {}

	public S0DPacketCollectItem(final int p_i45232_1_, final int p_i45232_2_) {
		field_149357_a = p_i45232_1_;
		field_149356_b = p_i45232_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149357_a = data.readVarIntFromBuffer();
		field_149356_b = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149357_a);
		data.writeVarIntToBuffer(field_149356_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleCollectItem(this);
	}

	public int func_149354_c() {
		return field_149357_a;
	}

	public int func_149353_d() {
		return field_149356_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
