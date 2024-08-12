package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3DPacketDisplayScoreboard implements Packet {

public static final int EaZy = 1460;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149374_a;
	private String field_149373_b;
	// private static final String __OBFID = "http://https://fuckuskid00001325";

	public S3DPacketDisplayScoreboard() {}

	public S3DPacketDisplayScoreboard(final int p_i45216_1_, final ScoreObjective p_i45216_2_) {
		field_149374_a = p_i45216_1_;

		if (p_i45216_2_ == null) {
			field_149373_b = "";
		} else {
			field_149373_b = p_i45216_2_.getName();
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149374_a = data.readByte();
		field_149373_b = data.readStringFromBuffer(16);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_149374_a);
		data.writeString(field_149373_b);
	}

	public void func_180747_a(final INetHandlerPlayClient p_180747_1_) {
		p_180747_1_.handleDisplayScoreboard(this);
	}

	public int func_149371_c() {
		return field_149374_a;
	}

	public String func_149370_d() {
		return field_149373_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180747_a((INetHandlerPlayClient) handler);
	}
}
