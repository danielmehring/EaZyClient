package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

import java.io.IOException;

public class S41PacketServerDifficulty implements Packet {

public static final int EaZy = 1464;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private EnumDifficulty field_179833_a;
	private boolean field_179832_b;
	// private static final String __OBFID = "http://https://fuckuskid00002303";

	public S41PacketServerDifficulty() {}

	public S41PacketServerDifficulty(final EnumDifficulty p_i45987_1_, final boolean p_i45987_2_) {
		field_179833_a = p_i45987_1_;
		field_179832_b = p_i45987_2_;
	}

	public void func_179829_a(final INetHandlerPlayClient p_179829_1_) {
		p_179829_1_.func_175101_a(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179833_a = EnumDifficulty.getDifficultyEnum(data.readUnsignedByte());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_179833_a.getDifficultyId());
	}

	public boolean func_179830_a() {
		return field_179832_b;
	}

	public EnumDifficulty func_179831_b() {
		return field_179833_a;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179829_a((INetHandlerPlayClient) handler);
	}
}
