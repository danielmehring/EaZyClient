package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S19PacketEntityHeadLook implements Packet {

public static final int EaZy = 1424;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149384_a;
	private byte field_149383_b;
	// private static final String __OBFID = "http://https://fuckuskid00001323";

	public S19PacketEntityHeadLook() {}

	public S19PacketEntityHeadLook(final Entity p_i45214_1_, final byte p_i45214_2_) {
		field_149384_a = p_i45214_1_.getEntityId();
		field_149383_b = p_i45214_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149384_a = data.readVarIntFromBuffer();
		field_149383_b = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149384_a);
		data.writeByte(field_149383_b);
	}

	public void func_180745_a(final INetHandlerPlayClient p_180745_1_) {
		p_180745_1_.handleEntityHeadLook(this);
	}

	public Entity func_149381_a(final World worldIn) {
		return worldIn.getEntityByID(field_149384_a);
	}

	public byte func_149380_c() {
		return field_149383_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180745_a((INetHandlerPlayClient) handler);
	}
}
