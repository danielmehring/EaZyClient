package net.minecraft.network.play.server;

import net.minecraft.block.Block;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S24PacketBlockAction implements Packet {

public static final int EaZy = 1435;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179826_a;
	private int field_148872_d;
	private int field_148873_e;
	private Block field_148871_f;
	// private static final String __OBFID = "http://https://fuckuskid00001286";

	public S24PacketBlockAction() {}

	public S24PacketBlockAction(final BlockPos p_i45989_1_, final Block p_i45989_2_, final int p_i45989_3_,
			final int p_i45989_4_) {
		field_179826_a = p_i45989_1_;
		field_148872_d = p_i45989_3_;
		field_148873_e = p_i45989_4_;
		field_148871_f = p_i45989_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179826_a = data.readBlockPos();
		field_148872_d = data.readUnsignedByte();
		field_148873_e = data.readUnsignedByte();
		field_148871_f = Block.getBlockById(data.readVarIntFromBuffer() & 4095);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179826_a);
		data.writeByte(field_148872_d);
		data.writeByte(field_148873_e);
		data.writeVarIntToBuffer(Block.getIdFromBlock(field_148871_f) & 4095);
	}

	public void func_180726_a(final INetHandlerPlayClient p_180726_1_) {
		p_180726_1_.handleBlockAction(this);
	}

	public BlockPos func_179825_a() {
		return field_179826_a;
	}

	/**
	 * instrument data for noteblocks
	 */
	public int getData1() {
		return field_148872_d;
	}

	/**
	 * pitch data for noteblocks
	 */
	public int getData2() {
		return field_148873_e;
	}

	public Block getBlockType() {
		return field_148871_f;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180726_a((INetHandlerPlayClient) handler);
	}
}
