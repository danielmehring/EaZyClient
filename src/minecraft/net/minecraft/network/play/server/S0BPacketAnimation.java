package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;

public class S0BPacketAnimation implements Packet {

public static final int EaZy = 1413;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int entityId;
	private int type;
	// private static final String __OBFID = "http://https://fuckuskid00001282";

	public S0BPacketAnimation() {}

	public S0BPacketAnimation(final Entity ent, final int animationType) {
		entityId = ent.getEntityId();
		type = animationType;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		entityId = data.readVarIntFromBuffer();
		type = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(entityId);
		data.writeByte(type);
	}

	public void func_180723_a(final INetHandlerPlayClient p_180723_1_) {
		p_180723_1_.handleAnimation(this);
	}

	public int func_148978_c() {
		return entityId;
	}

	public int func_148977_d() {
		return type;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180723_a((INetHandlerPlayClient) handler);
	}
}
