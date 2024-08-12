package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S32PacketConfirmTransaction implements Packet {

public static final int EaZy = 1449;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148894_a;
	private short field_148892_b;
	private boolean field_148893_c;
	// private static final String __OBFID = "http://https://fuckuskid00001291";

	public S32PacketConfirmTransaction() {}

	public S32PacketConfirmTransaction(final int p_i45182_1_, final short p_i45182_2_, final boolean p_i45182_3_) {
		field_148894_a = p_i45182_1_;
		field_148892_b = p_i45182_2_;
		field_148893_c = p_i45182_3_;
	}

	public void func_180730_a(final INetHandlerPlayClient p_180730_1_) {
		p_180730_1_.handleConfirmTransaction(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148894_a = data.readUnsignedByte();
		field_148892_b = data.readShort();
		field_148893_c = data.readBoolean();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_148894_a);
		data.writeShort(field_148892_b);
		data.writeBoolean(field_148893_c);
	}

	public int func_148889_c() {
		return field_148894_a;
	}

	public short func_148890_d() {
		return field_148892_b;
	}

	public boolean func_148888_e() {
		return field_148893_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180730_a((INetHandlerPlayClient) handler);
	}
}
