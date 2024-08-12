package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.io.IOException;

public class C07PacketPlayerDigging implements Packet {

public static final int EaZy = 1381;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179717_a;
	private EnumFacing field_179716_b;

	/** Status of the digging (started, ongoing, broken). */
	private C07PacketPlayerDigging.Action status;
	// private static final String __OBFID = "http://https://fuckuskid00001365";

	public C07PacketPlayerDigging() {}

	public C07PacketPlayerDigging(final C07PacketPlayerDigging.Action p_i45940_1_, final BlockPos p_i45940_2_,
			final EnumFacing p_i45940_3_) {
		status = p_i45940_1_;
		field_179717_a = p_i45940_2_;
		field_179716_b = p_i45940_3_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		status = (C07PacketPlayerDigging.Action) data.readEnumValue(C07PacketPlayerDigging.Action.class);
		field_179717_a = data.readBlockPos();
		field_179716_b = EnumFacing.getFront(data.readUnsignedByte());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeEnumValue(status);
		data.writeBlockPos(field_179717_a);
		data.writeByte(field_179716_b.getIndex());
	}

	public void func_180763_a(final INetHandlerPlayServer p_180763_1_) {
		p_180763_1_.processPlayerDigging(this);
	}

	public BlockPos getBlockPos() {
		return field_179717_a;
	}

	public EnumFacing getFacing() {
		return field_179716_b;
	}

	public C07PacketPlayerDigging.Action getAction() {
		return status;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180763_a((INetHandlerPlayServer) handler);
	}

	public static enum Action {
		START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0), ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK",
				1), STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2), DROP_ALL_ITEMS("DROP_ALL_ITEMS",
						3), DROP_ITEM("DROP_ITEM", 4), RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5);

		private Action(final String p_i45939_1_, final int p_i45939_2_) {}
	}
}
