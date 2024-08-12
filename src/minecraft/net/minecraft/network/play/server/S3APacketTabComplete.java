package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S3APacketTabComplete implements Packet {

public static final int EaZy = 1457;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String[] field_149632_a;
	// private static final String __OBFID = "http://https://fuckuskid00001288";

	public S3APacketTabComplete() {}

	public S3APacketTabComplete(final String[] p_i45178_1_) {
		field_149632_a = p_i45178_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149632_a = new String[data.readVarIntFromBuffer()];

		for (int var2 = 0; var2 < field_149632_a.length; ++var2) {
			field_149632_a[var2] = data.readStringFromBuffer(32767);
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149632_a.length);
		final String[] var2 = field_149632_a;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final String var5 = var2[var4];
			data.writeString(var5);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleTabComplete(this);
	}

	public String[] func_149630_c() {
		return field_149632_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
