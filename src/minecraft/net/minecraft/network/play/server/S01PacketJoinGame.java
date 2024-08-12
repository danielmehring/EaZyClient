package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S01PacketJoinGame implements Packet {

public static final int EaZy = 1403;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int entityID;
	private boolean field_149204_b;
	private WorldSettings.GameType gameType;
	private int field_149202_d;
	private EnumDifficulty difficulty;
	private int field_149200_f;
	private WorldType worldType;
	private boolean field_179745_h;
	// private static final String __OBFID = "http://https://fuckuskid00001310";

	public S01PacketJoinGame() {}

	public S01PacketJoinGame(final int p_i45976_1_, final WorldSettings.GameType p_i45976_2_, final boolean p_i45976_3_,
			final int p_i45976_4_, final EnumDifficulty p_i45976_5_, final int p_i45976_6_, final WorldType p_i45976_7_,
			final boolean p_i45976_8_) {
		entityID = p_i45976_1_;
		field_149202_d = p_i45976_4_;
		difficulty = p_i45976_5_;
		gameType = p_i45976_2_;
		field_149200_f = p_i45976_6_;
		field_149204_b = p_i45976_3_;
		worldType = p_i45976_7_;
		field_179745_h = p_i45976_8_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		entityID = data.readInt();
		final short var2 = data.readUnsignedByte();
		field_149204_b = (var2 & 8) == 8;
		final int var3 = var2 & -9;
		gameType = WorldSettings.GameType.getByID(var3);
		field_149202_d = data.readByte();
		difficulty = EnumDifficulty.getDifficultyEnum(data.readUnsignedByte());
		field_149200_f = data.readUnsignedByte();
		worldType = WorldType.parseWorldType(data.readStringFromBuffer(16));

		if (worldType == null) {
			worldType = WorldType.DEFAULT;
		}

		field_179745_h = data.readBoolean();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(entityID);
		int var2 = gameType.getID();

		if (field_149204_b) {
			var2 |= 8;
		}

		data.writeByte(var2);
		data.writeByte(field_149202_d);
		data.writeByte(difficulty.getDifficultyId());
		data.writeByte(field_149200_f);
		data.writeString(worldType.getWorldTypeName());
		data.writeBoolean(field_179745_h);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleJoinGame(this);
	}

	public int getEntityID() {
		return entityID;
	}

	public boolean func_149195_d() {
		return field_149204_b;
	}

	public WorldSettings.GameType getGameMode() {
		return gameType;
	}

	public int func_149194_f() {
		return field_149202_d;
	}

	public EnumDifficulty getDifficulty() {
		return difficulty;
	}

	public int func_149193_h() {
		return field_149200_f;
	}

	public WorldType getWorldType() {
		return worldType;
	}

	public boolean func_179744_h() {
		return field_179745_h;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
