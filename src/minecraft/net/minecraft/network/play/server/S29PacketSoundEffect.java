package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;

import org.apache.commons.lang3.Validate;

public class S29PacketSoundEffect implements Packet {

public static final int EaZy = 1440;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String field_149219_a;
	private int field_149217_b;
	private int field_149218_c = Integer.MAX_VALUE;
	private int field_149215_d;
	private float field_149216_e;
	private int field_149214_f;
	// private static final String __OBFID = "http://https://fuckuskid00001309";

	public S29PacketSoundEffect() {}

	public S29PacketSoundEffect(final String p_i45200_1_, final double p_i45200_2_, final double p_i45200_4_,
			final double p_i45200_6_, final float p_i45200_8_, float p_i45200_9_) {
		Validate.notNull(p_i45200_1_, "name", new Object[0]);
		field_149219_a = p_i45200_1_;
		field_149217_b = (int) (p_i45200_2_ * 8.0D);
		field_149218_c = (int) (p_i45200_4_ * 8.0D);
		field_149215_d = (int) (p_i45200_6_ * 8.0D);
		field_149216_e = p_i45200_8_;
		field_149214_f = (int) (p_i45200_9_ * 63.0F);
		p_i45200_9_ = MathHelper.clamp_float(p_i45200_9_, 0.0F, 255.0F);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149219_a = data.readStringFromBuffer(256);
		field_149217_b = data.readInt();
		field_149218_c = data.readInt();
		field_149215_d = data.readInt();
		field_149216_e = data.readFloat();
		field_149214_f = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(field_149219_a);
		data.writeInt(field_149217_b);
		data.writeInt(field_149218_c);
		data.writeInt(field_149215_d);
		data.writeFloat(field_149216_e);
		data.writeByte(field_149214_f);
	}

	public String func_149212_c() {
		return field_149219_a;
	}

	public double func_149207_d() {
		return field_149217_b / 8.0F;
	}

	public double func_149211_e() {
		return field_149218_c / 8.0F;
	}

	public double func_149210_f() {
		return field_149215_d / 8.0F;
	}

	public float func_149208_g() {
		return field_149216_e;
	}

	public float func_149209_h() {
		return field_149214_f / 63.0F;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleSoundEffect(this);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
