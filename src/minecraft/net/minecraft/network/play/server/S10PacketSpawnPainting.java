package net.minecraft.network.play.server;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.io.IOException;

public class S10PacketSpawnPainting implements Packet {

public static final int EaZy = 1418;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148973_a;
	private BlockPos field_179838_b;
	private EnumFacing field_179839_c;
	private String field_148968_f;
	// private static final String __OBFID = "http://https://fuckuskid00001280";

	public S10PacketSpawnPainting() {}

	public S10PacketSpawnPainting(final EntityPainting p_i45170_1_) {
		field_148973_a = p_i45170_1_.getEntityId();
		field_179838_b = p_i45170_1_.func_174857_n();
		field_179839_c = p_i45170_1_.field_174860_b;
		field_148968_f = p_i45170_1_.art.title;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148973_a = data.readVarIntFromBuffer();
		field_148968_f = data.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
		field_179838_b = data.readBlockPos();
		field_179839_c = EnumFacing.getHorizontal(data.readUnsignedByte());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_148973_a);
		data.writeString(field_148968_f);
		data.writeBlockPos(field_179838_b);
		data.writeByte(field_179839_c.getHorizontalIndex());
	}

	public void func_180722_a(final INetHandlerPlayClient p_180722_1_) {
		p_180722_1_.handleSpawnPainting(this);
	}

	public int func_148965_c() {
		return field_148973_a;
	}

	public BlockPos func_179837_b() {
		return field_179838_b;
	}

	public EnumFacing func_179836_c() {
		return field_179839_c;
	}

	public String func_148961_h() {
		return field_148968_f;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180722_a((INetHandlerPlayClient) handler);
	}
}
