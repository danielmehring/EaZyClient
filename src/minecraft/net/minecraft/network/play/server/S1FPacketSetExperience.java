package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S1FPacketSetExperience implements Packet {

public static final int EaZy = 1430;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float field_149401_a;
	private int field_149399_b;
	private int field_149400_c;
	// private static final String __OBFID = "http://https://fuckuskid00001331";

	public S1FPacketSetExperience() {}

	public S1FPacketSetExperience(final float p_i45222_1_, final int p_i45222_2_, final int p_i45222_3_) {
		field_149401_a = p_i45222_1_;
		field_149399_b = p_i45222_2_;
		field_149400_c = p_i45222_3_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149401_a = data.readFloat();
		field_149400_c = data.readVarIntFromBuffer();
		field_149399_b = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeFloat(field_149401_a);
		data.writeVarIntToBuffer(field_149400_c);
		data.writeVarIntToBuffer(field_149399_b);
	}

	public void func_180749_a(final INetHandlerPlayClient p_180749_1_) {
		p_180749_1_.handleSetExperience(this);
	}

	public float func_149397_c() {
		return field_149401_a;
	}

	public int func_149396_d() {
		return field_149399_b;
	}

	public int func_149395_e() {
		return field_149400_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180749_a((INetHandlerPlayClient) handler);
	}
}
