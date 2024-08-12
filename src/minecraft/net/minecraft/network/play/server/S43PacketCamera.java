package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;

public class S43PacketCamera implements Packet {

public static final int EaZy = 1466;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int field_179781_a;
	// private static final String __OBFID = "http://https://fuckuskid00002289";

	public S43PacketCamera() {}

	public S43PacketCamera(final Entity p_i45960_1_) {
		field_179781_a = p_i45960_1_.getEntityId();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179781_a = data.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_179781_a);
	}

	public void func_179779_a(final INetHandlerPlayClient p_179779_1_) {
		p_179779_1_.func_175094_a(this);
	}

	public Entity func_179780_a(final World worldIn) {
		return worldIn.getEntityByID(field_179781_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179779_a((INetHandlerPlayClient) handler);
	}
}
