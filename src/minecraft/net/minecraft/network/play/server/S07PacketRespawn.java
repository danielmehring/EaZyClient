package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import java.io.IOException;

public class S07PacketRespawn implements Packet {

public static final int EaZy = 1409;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149088_a;
	private EnumDifficulty field_149086_b;
	private WorldSettings.GameType field_149087_c;
	private WorldType field_149085_d;
	// private static final String __OBFID = "http://https://fuckuskid00001322";

	public S07PacketRespawn() {}

	public S07PacketRespawn(final int p_i45213_1_, final EnumDifficulty p_i45213_2_, final WorldType p_i45213_3_,
			final WorldSettings.GameType p_i45213_4_) {
		field_149088_a = p_i45213_1_;
		field_149086_b = p_i45213_2_;
		field_149087_c = p_i45213_4_;
		field_149085_d = p_i45213_3_;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleRespawn(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149088_a = data.readInt();
		field_149086_b = EnumDifficulty.getDifficultyEnum(data.readUnsignedByte());
		field_149087_c = WorldSettings.GameType.getByID(data.readUnsignedByte());
		field_149085_d = WorldType.parseWorldType(data.readStringFromBuffer(16));

		if (field_149085_d == null) {
			field_149085_d = WorldType.DEFAULT;
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(field_149088_a);
		data.writeByte(field_149086_b.getDifficultyId());
		data.writeByte(field_149087_c.getID());
		data.writeString(field_149085_d.getWorldTypeName());
	}

	public int func_149082_c() {
		return field_149088_a;
	}

	public EnumDifficulty func_149081_d() {
		return field_149086_b;
	}

	public WorldSettings.GameType func_149083_e() {
		return field_149087_c;
	}

	public WorldType func_149080_f() {
		return field_149085_d;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
