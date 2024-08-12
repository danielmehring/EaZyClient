package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S2CPacketSpawnGlobalEntity implements Packet {

public static final int EaZy = 1443;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149059_a;
	private int field_149057_b;
	private int field_149058_c;
	private int field_149055_d;
	private int field_149056_e;
	// private static final String __OBFID = "http://https://fuckuskid00001278";

	public S2CPacketSpawnGlobalEntity() {}

	public S2CPacketSpawnGlobalEntity(final Entity p_i45191_1_) {
		field_149059_a = p_i45191_1_.getEntityId();
		field_149057_b = MathHelper.floor_double(p_i45191_1_.posX * 32.0D);
		field_149058_c = MathHelper.floor_double(p_i45191_1_.posY * 32.0D);
		field_149055_d = MathHelper.floor_double(p_i45191_1_.posZ * 32.0D);

		if (p_i45191_1_ instanceof EntityLightningBolt) {
			field_149056_e = 1;
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149059_a = data.readVarIntFromBuffer();
		field_149056_e = data.readByte();
		field_149057_b = data.readInt();
		field_149058_c = data.readInt();
		field_149055_d = data.readInt();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149059_a);
		data.writeByte(field_149056_e);
		data.writeInt(field_149057_b);
		data.writeInt(field_149058_c);
		data.writeInt(field_149055_d);
	}

	public void func_180720_a(final INetHandlerPlayClient p_180720_1_) {
		p_180720_1_.handleSpawnGlobalEntity(this);
	}

	public int func_149052_c() {
		return field_149059_a;
	}

	public int func_149051_d() {
		return field_149057_b;
	}

	public int func_149050_e() {
		return field_149058_c;
	}

	public int func_149049_f() {
		return field_149055_d;
	}

	public int func_149053_g() {
		return field_149056_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180720_a((INetHandlerPlayClient) handler);
	}
}
