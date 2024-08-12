package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

import java.io.IOException;

public class S1DPacketEntityEffect implements Packet {

public static final int EaZy = 1428;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149434_a;
	private byte field_149432_b;
	private byte field_149433_c;
	private int field_149431_d;
	private byte field_179708_e;
	// private static final String __OBFID = "http://https://fuckuskid00001343";

	public S1DPacketEntityEffect() {}

	public S1DPacketEntityEffect(final int p_i45237_1_, final PotionEffect p_i45237_2_) {
		field_149434_a = p_i45237_1_;
		field_149432_b = (byte) (p_i45237_2_.getPotionID() & 255);
		field_149433_c = (byte) (p_i45237_2_.getAmplifier() & 255);

		if (p_i45237_2_.getDuration() > 32767) {
			field_149431_d = 32767;
		} else {
			field_149431_d = p_i45237_2_.getDuration();
		}

		field_179708_e = (byte) (p_i45237_2_.func_180154_f() ? 1 : 0);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149434_a = data.readVarIntFromBuffer();
		field_149432_b = data.readByte();
		field_149433_c = data.readByte();
		field_149431_d = data.readVarIntFromBuffer();
		field_179708_e = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149434_a);
		data.writeByte(field_149432_b);
		data.writeByte(field_149433_c);
		data.writeVarIntToBuffer(field_149431_d);
		data.writeByte(field_179708_e);
	}

	public boolean func_149429_c() {
		return field_149431_d == 32767;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleEntityEffect(this);
	}

	public int func_149426_d() {
		return field_149434_a;
	}

	public byte func_149427_e() {
		return field_149432_b;
	}

	public byte func_149428_f() {
		return field_149433_c;
	}

	public int func_180755_e() {
		return field_149431_d;
	}

	public boolean func_179707_f() {
		return field_179708_e != 0;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
