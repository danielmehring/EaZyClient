package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2FPacketSetSlot implements Packet {

public static final int EaZy = 1446;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149179_a;
	private int field_149177_b;
	private ItemStack field_149178_c;
	// private static final String __OBFID = "http://https://fuckuskid00001296";

	public S2FPacketSetSlot() {}

	public S2FPacketSetSlot(final int p_i45188_1_, final int p_i45188_2_, final ItemStack p_i45188_3_) {
		field_149179_a = p_i45188_1_;
		field_149177_b = p_i45188_2_;
		field_149178_c = p_i45188_3_ == null ? null : p_i45188_3_.copy();
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleSetSlot(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149179_a = data.readByte();
		field_149177_b = data.readShort();
		field_149178_c = data.readItemStackFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_149179_a);
		data.writeShort(field_149177_b);
		data.writeItemStackToBuffer(field_149178_c);
	}

	public int func_149175_c() {
		return field_149179_a;
	}

	public int func_149173_d() {
		return field_149177_b;
	}

	public ItemStack func_149174_e() {
		return field_149178_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
