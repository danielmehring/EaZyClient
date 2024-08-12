package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S19PacketEntityStatus implements Packet {

public static final int EaZy = 1425;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149164_a;
	private byte field_149163_b;
	// private static final String __OBFID = "http://https://fuckuskid00001299";

	public S19PacketEntityStatus() {}

	public S19PacketEntityStatus(final Entity p_i46335_1_, final byte p_i46335_2_) {
		field_149164_a = p_i46335_1_.getEntityId();
		field_149163_b = p_i46335_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149164_a = data.readInt();
		field_149163_b = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(field_149164_a);
		data.writeByte(field_149163_b);
	}

	public void func_180736_a(final INetHandlerPlayClient p_180736_1_) {
		p_180736_1_.handleEntityStatus(this);
	}

	public Entity func_149161_a(final World worldIn) {
		return worldIn.getEntityByID(field_149164_a);
	}

	public byte func_149160_c() {
		return field_149163_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180736_a((INetHandlerPlayClient) handler);
	}
}
