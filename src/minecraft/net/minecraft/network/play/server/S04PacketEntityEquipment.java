package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S04PacketEntityEquipment implements Packet {

public static final int EaZy = 1406;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149394_a;
	private int field_149392_b;
	private ItemStack field_149393_c;
	// private static final String __OBFID = "http://https://fuckuskid00001330";

	public S04PacketEntityEquipment() {}

	public S04PacketEntityEquipment(final int p_i45221_1_, final int p_i45221_2_, final ItemStack p_i45221_3_) {
		field_149394_a = p_i45221_1_;
		field_149392_b = p_i45221_2_;
		field_149393_c = p_i45221_3_ == null ? null : p_i45221_3_.copy();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149394_a = data.readVarIntFromBuffer();
		field_149392_b = data.readShort();
		field_149393_c = data.readItemStackFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149394_a);
		data.writeShort(field_149392_b);
		data.writeItemStackToBuffer(field_149393_c);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleEntityEquipment(this);
	}

	public ItemStack func_149390_c() {
		return field_149393_c;
	}

	public int func_149389_d() {
		return field_149394_a;
	}

	public int func_149388_e() {
		return field_149392_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
