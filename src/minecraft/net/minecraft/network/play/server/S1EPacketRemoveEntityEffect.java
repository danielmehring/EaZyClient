package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

import java.io.IOException;

public class S1EPacketRemoveEntityEffect implements Packet {

public static final int EaZy = 1429;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149079_a;
	private int field_149078_b;
	// private static final String __OBFID = "http://https://fuckuskid00001321";

	public S1EPacketRemoveEntityEffect() {}

	public S1EPacketRemoveEntityEffect(final int p_i45212_1_, final PotionEffect p_i45212_2_) {
		field_149079_a = p_i45212_1_;
		field_149078_b = p_i45212_2_.getPotionID();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149079_a = data.readVarIntFromBuffer();
		field_149078_b = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149079_a);
		data.writeByte(field_149078_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleRemoveEntityEffect(this);
	}

	public int func_149076_c() {
		return field_149079_a;
	}

	public int func_149075_d() {
		return field_149078_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
