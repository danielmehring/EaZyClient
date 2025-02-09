package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S05PacketSpawnPosition implements Packet {

public static final int EaZy = 1407;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private BlockPos field_179801_a;
	// private static final String __OBFID = "http://https://fuckuskid00001336";

	public S05PacketSpawnPosition() {}

	public S05PacketSpawnPosition(final BlockPos p_i45956_1_) {
		field_179801_a = p_i45956_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179801_a = data.readBlockPos();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179801_a);
	}

	public void func_180752_a(final INetHandlerPlayClient p_180752_1_) {
		p_180752_1_.handleSpawnPosition(this);
	}

	public BlockPos func_179800_a() {
		return field_179801_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180752_a((INetHandlerPlayClient) handler);
	}
}
