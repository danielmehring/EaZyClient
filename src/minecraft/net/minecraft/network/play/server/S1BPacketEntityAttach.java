package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S1BPacketEntityAttach implements Packet {

public static final int EaZy = 1426;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149408_a;
	private int field_149406_b;
	private int field_149407_c;
	// private static final String __OBFID = "http://https://fuckuskid00001327";

	public S1BPacketEntityAttach() {}

	public S1BPacketEntityAttach(final int p_i45218_1_, final Entity p_i45218_2_, final Entity p_i45218_3_) {
		field_149408_a = p_i45218_1_;
		field_149406_b = p_i45218_2_.getEntityId();
		field_149407_c = p_i45218_3_ != null ? p_i45218_3_.getEntityId() : -1;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149406_b = data.readInt();
		field_149407_c = data.readInt();
		field_149408_a = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(field_149406_b);
		data.writeInt(field_149407_c);
		data.writeByte(field_149408_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleEntityAttach(this);
	}

	public int func_149404_c() {
		return field_149408_a;
	}

	public int func_149403_d() {
		return field_149406_b;
	}

	public int func_149402_e() {
		return field_149407_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
