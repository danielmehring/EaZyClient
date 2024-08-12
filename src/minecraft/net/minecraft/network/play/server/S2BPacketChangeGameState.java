package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S2BPacketChangeGameState implements Packet {

	public static final int EaZy = 1442;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static final String[] MESSAGE_NAMES = new String[] { "tile.bed.notValid" };
	private int state;
	private float field_149141_c;
	// private static final String __OBFID = "http://https://fuckuskid00001301";

	public S2BPacketChangeGameState() {}

	public S2BPacketChangeGameState(final int stateIn, final float p_i45194_2_) {
		state = stateIn;
		field_149141_c = p_i45194_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		state = data.readUnsignedByte();
		field_149141_c = data.readFloat();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(state);
		data.writeFloat(field_149141_c);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleChangeGameState(this);
	}

	public int func_149138_c() {
		return state;
	}

	public float func_149137_d() {
		return field_149141_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
