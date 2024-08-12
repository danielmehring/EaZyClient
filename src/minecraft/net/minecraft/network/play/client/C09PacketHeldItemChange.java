package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C09PacketHeldItemChange implements Packet {

public static final int EaZy = 1383;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int slotId;
	// private static final String __OBFID = "http://https://fuckuskid00001368";

	public C09PacketHeldItemChange() {}

	public C09PacketHeldItemChange(final int p_i45262_1_) {
		slotId = p_i45262_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		slotId = data.readShort();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeShort(slotId);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processHeldItemChange(this);
	}

	public int getSlotId() {
		return slotId;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
