package net.minecraft.network.play.server;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S11PacketSpawnExperienceOrb implements Packet {

public static final int EaZy = 1419;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148992_a;
	private int field_148990_b;
	private int field_148991_c;
	private int field_148988_d;
	private int field_148989_e;
	// private static final String __OBFID = "http://https://fuckuskid00001277";

	public S11PacketSpawnExperienceOrb() {}

	public S11PacketSpawnExperienceOrb(final EntityXPOrb p_i45167_1_) {
		field_148992_a = p_i45167_1_.getEntityId();
		field_148990_b = MathHelper.floor_double(p_i45167_1_.posX * 32.0D);
		field_148991_c = MathHelper.floor_double(p_i45167_1_.posY * 32.0D);
		field_148988_d = MathHelper.floor_double(p_i45167_1_.posZ * 32.0D);
		field_148989_e = p_i45167_1_.getXpValue();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148992_a = data.readVarIntFromBuffer();
		field_148990_b = data.readInt();
		field_148991_c = data.readInt();
		field_148988_d = data.readInt();
		field_148989_e = data.readShort();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_148992_a);
		data.writeInt(field_148990_b);
		data.writeInt(field_148991_c);
		data.writeInt(field_148988_d);
		data.writeShort(field_148989_e);
	}

	public void func_180719_a(final INetHandlerPlayClient p_180719_1_) {
		p_180719_1_.handleSpawnExperienceOrb(this);
	}

	public int func_148985_c() {
		return field_148992_a;
	}

	public int func_148984_d() {
		return field_148990_b;
	}

	public int func_148983_e() {
		return field_148991_c;
	}

	public int func_148982_f() {
		return field_148988_d;
	}

	public int func_148986_g() {
		return field_148989_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180719_a((INetHandlerPlayClient) handler);
	}
}
