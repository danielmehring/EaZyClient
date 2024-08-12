package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C16PacketClientStatus implements Packet {

public static final int EaZy = 1396;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private C16PacketClientStatus.EnumState status;
	// private static final String __OBFID = "http://https://fuckuskid00001348";

	public C16PacketClientStatus() {}

	public C16PacketClientStatus(final C16PacketClientStatus.EnumState statusIn) {
		status = statusIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		status = (C16PacketClientStatus.EnumState) data.readEnumValue(C16PacketClientStatus.EnumState.class);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeEnumValue(status);
	}

	public void func_180758_a(final INetHandlerPlayServer p_180758_1_) {
		p_180758_1_.processClientStatus(this);
	}

	public C16PacketClientStatus.EnumState getStatus() {
		return status;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180758_a((INetHandlerPlayServer) handler);
	}

	public static enum EnumState {
		PERFORM_RESPAWN("PERFORM_RESPAWN", 0), REQUEST_STATS("REQUEST_STATS",
				1), OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2);

		private EnumState(final String p_i45947_1_, final int p_i45947_2_) {}
	}
}
