package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;

import java.io.IOException;

public class S3BPacketScoreboardObjective implements Packet {

public static final int EaZy = 1458;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String field_149343_a;
	private String field_149341_b;
	private IScoreObjectiveCriteria.EnumRenderType field_179818_c;
	private int field_149342_c;
	// private static final String __OBFID = "http://https://fuckuskid00001333";

	public S3BPacketScoreboardObjective() {}

	public S3BPacketScoreboardObjective(final ScoreObjective p_i45224_1_, final int p_i45224_2_) {
		field_149343_a = p_i45224_1_.getName();
		field_149341_b = p_i45224_1_.getDisplayName();
		field_179818_c = p_i45224_1_.getCriteria().func_178790_c();
		field_149342_c = p_i45224_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149343_a = data.readStringFromBuffer(16);
		field_149342_c = data.readByte();

		if (field_149342_c == 0 || field_149342_c == 2) {
			field_149341_b = data.readStringFromBuffer(32);
			field_179818_c = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(data.readStringFromBuffer(16));
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(field_149343_a);
		data.writeByte(field_149342_c);

		if (field_149342_c == 0 || field_149342_c == 2) {
			data.writeString(field_149341_b);
			data.writeString(field_179818_c.func_178796_a());
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleScoreboardObjective(this);
	}

	public String func_149339_c() {
		return field_149343_a;
	}

	public String func_149337_d() {
		return field_149341_b;
	}

	public int func_149338_e() {
		return field_149342_c;
	}

	public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
		return field_179818_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
