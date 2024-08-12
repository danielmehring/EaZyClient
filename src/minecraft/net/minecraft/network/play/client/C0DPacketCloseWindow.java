package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C0DPacketCloseWindow implements Packet {

public static final int EaZy = 1387;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int windowId;
	// private static final String __OBFID = "http://https://fuckuskid00001354";

	public C0DPacketCloseWindow() {}

	public C0DPacketCloseWindow(final int p_i45247_1_) {
		windowId = p_i45247_1_;
	}

	public void func_180759_a(final INetHandlerPlayServer p_180759_1_) {
		p_180759_1_.processCloseWindow(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		windowId = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(windowId);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180759_a((INetHandlerPlayServer) handler);
	}
}
