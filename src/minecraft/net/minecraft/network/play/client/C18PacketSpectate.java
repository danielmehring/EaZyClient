package net.minecraft.network.play.client;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.world.WorldServer;

import java.io.IOException;
import java.util.UUID;

public class C18PacketSpectate implements Packet {

public static final int EaZy = 1398;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private UUID field_179729_a;
	// private static final String __OBFID = "http://https://fuckuskid00002280";

	public C18PacketSpectate() {}

	public C18PacketSpectate(final UUID p_i45932_1_) {
		field_179729_a = p_i45932_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179729_a = data.readUuid();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeUuid(field_179729_a);
	}

	public void func_179728_a(final INetHandlerPlayServer p_179728_1_) {
		p_179728_1_.processPacketSpectate(this);
	}

	public Entity func_179727_a(final WorldServer p_179727_1_) {
		return p_179727_1_.getEntityFromUuid(field_179729_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179728_a((INetHandlerPlayServer) handler);
	}
}
