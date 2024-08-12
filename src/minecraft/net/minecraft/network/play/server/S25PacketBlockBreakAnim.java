package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

import java.io.IOException;

public class S25PacketBlockBreakAnim implements Packet {

public static final int EaZy = 1436;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int breakerId;
	private BlockPos position;
	private int progress;
	// private static final String __OBFID = "http://https://fuckuskid00001284";

	public S25PacketBlockBreakAnim() {}

	public S25PacketBlockBreakAnim(final int breakerId, final BlockPos pos, final int progress) {
		this.breakerId = breakerId;
		position = pos;
		this.progress = progress;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		breakerId = data.readVarIntFromBuffer();
		position = data.readBlockPos();
		progress = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(breakerId);
		data.writeBlockPos(position);
		data.writeByte(progress);
	}

	public void handle(final INetHandlerPlayClient handler) {
		handler.handleBlockBreakAnim(this);
	}

	public int func_148845_c() {
		return breakerId;
	}

	public BlockPos func_179821_b() {
		return position;
	}

	public int func_148846_g() {
		return progress;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		handle((INetHandlerPlayClient) handler);
	}
}
