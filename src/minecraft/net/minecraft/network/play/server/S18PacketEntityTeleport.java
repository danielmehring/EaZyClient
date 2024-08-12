package net.minecraft.network.play.server;

import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

public class S18PacketEntityTeleport implements Packet {

public static final int EaZy = 1423;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149458_a;
	private int field_149456_b;
	private int field_149457_c;
	private int field_149454_d;
	private byte field_149455_e;
	private byte field_149453_f;
	private boolean field_179698_g;
	// private static final String __OBFID = "http://https://fuckuskid00001340";

	public S18PacketEntityTeleport() {}

	public S18PacketEntityTeleport(final Entity p_i45233_1_) {
		field_149458_a = p_i45233_1_.getEntityId();
		field_149456_b = MathHelper.floor_double(p_i45233_1_.posX * 32.0D);
		field_149457_c = MathHelper.floor_double(p_i45233_1_.posY * 32.0D);
		field_149454_d = MathHelper.floor_double(p_i45233_1_.posZ * 32.0D);
		field_149455_e = (byte) (int) (p_i45233_1_.rotationYaw * 256.0F / 360.0F);
		field_149453_f = (byte) (int) (p_i45233_1_.rotationPitch * 256.0F / 360.0F);
		field_179698_g = p_i45233_1_.onGround;
	}

	public S18PacketEntityTeleport(final int p_i45949_1_, final int p_i45949_2_, final int p_i45949_3_,
			final int p_i45949_4_, final byte p_i45949_5_, final byte p_i45949_6_, final boolean p_i45949_7_) {
		field_149458_a = p_i45949_1_;
		field_149456_b = p_i45949_2_;
		field_149457_c = p_i45949_3_;
		field_149454_d = p_i45949_4_;
		field_149455_e = p_i45949_5_;
		field_149453_f = p_i45949_6_;
		field_179698_g = p_i45949_7_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149458_a = data.readVarIntFromBuffer();
		field_149456_b = data.readInt();
		field_149457_c = data.readInt();
		field_149454_d = data.readInt();
		field_149455_e = data.readByte();
		field_149453_f = data.readByte();
		field_179698_g = data.readBoolean();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_149458_a);
		data.writeInt(field_149456_b);
		data.writeInt(field_149457_c);
		data.writeInt(field_149454_d);
		data.writeByte(field_149455_e);
		data.writeByte(field_149453_f);
		data.writeBoolean(field_179698_g);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleEntityTeleport(this);
	}

	public int getID() {
		return field_149458_a;
	}

	public int getPosX() {
		return field_149456_b;
	}

	public int getPosY() {
		return field_149457_c;
	}

	public int getPosZ() {
		return field_149454_d;
	}

	public byte func_149450_g() {
		return field_149455_e;
	}

	public byte func_149447_h() {
		return field_149453_f;
	}

	public boolean onGround() {
		return field_179698_g;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
