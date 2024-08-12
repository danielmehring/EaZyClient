package net.minecraft.network.status.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

import java.io.IOException;

public class C00PacketServerQuery implements Packet {

public static final int EaZy = 1474;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001393";

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {}

	public void func_180775_a(final INetHandlerStatusServer p_180775_1_) {
		p_180775_1_.processServerQuery(this);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180775_a((INetHandlerStatusServer) handler);
	}
}
