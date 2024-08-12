package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C0FPacketConfirmTransaction implements Packet {

public static final int EaZy = 1389;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int id;
	private short uid;
	private boolean accepted;
	// private static final String __OBFID = "http://https://fuckuskid00001351";

	public C0FPacketConfirmTransaction() {}

	public C0FPacketConfirmTransaction(final int p_i45244_1_, final short p_i45244_2_, final boolean p_i45244_3_) {
		id = p_i45244_1_;
		uid = p_i45244_2_;
		accepted = p_i45244_3_;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processConfirmTransaction(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		id = data.readByte();
		uid = data.readShort();
		accepted = data.readByte() != 0;
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(id);
		data.writeShort(uid);
		data.writeByte(accepted ? 1 : 0);
	}

	public int getId() {
		return id;
	}

	public short getUid() {
		return uid;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
