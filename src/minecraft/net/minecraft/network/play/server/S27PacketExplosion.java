package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class S27PacketExplosion implements Packet {

public static final int EaZy = 1438;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private double field_149158_a;
	private double field_149156_b;
	private double field_149157_c;
	private float field_149154_d;
	private List field_149155_e;
	public float x;
	public float y;
	public float z;
	// private static final String __OBFID = "http://https://fuckuskid00001300";

	public S27PacketExplosion() {}

	public S27PacketExplosion(final double p_i45193_1_, final double p_i45193_3_, final double p_i45193_5_,
			final float p_i45193_7_, final List p_i45193_8_, final Vec3 p_i45193_9_) {
		field_149158_a = p_i45193_1_;
		field_149156_b = p_i45193_3_;
		field_149157_c = p_i45193_5_;
		field_149154_d = p_i45193_7_;
		field_149155_e = Lists.newArrayList(p_i45193_8_);

		if (p_i45193_9_ != null) {
			x = (float) p_i45193_9_.xCoord;
			y = (float) p_i45193_9_.yCoord;
			z = (float) p_i45193_9_.zCoord;
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149158_a = data.readFloat();
		field_149156_b = data.readFloat();
		field_149157_c = data.readFloat();
		field_149154_d = data.readFloat();
		final int var2 = data.readInt();
		field_149155_e = Lists.newArrayListWithCapacity(var2);
		final int var3 = (int) field_149158_a;
		final int var4 = (int) field_149156_b;
		final int var5 = (int) field_149157_c;

		for (int var6 = 0; var6 < var2; ++var6) {
			final int var7 = data.readByte() + var3;
			final int var8 = data.readByte() + var4;
			final int var9 = data.readByte() + var5;
			field_149155_e.add(new BlockPos(var7, var8, var9));
		}

		x = data.readFloat();
		y = data.readFloat();
		z = data.readFloat();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeFloat((float) field_149158_a);
		data.writeFloat((float) field_149156_b);
		data.writeFloat((float) field_149157_c);
		data.writeFloat(field_149154_d);
		data.writeInt(field_149155_e.size());
		final int var2 = (int) field_149158_a;
		final int var3 = (int) field_149156_b;
		final int var4 = (int) field_149157_c;
		final Iterator var5 = field_149155_e.iterator();

		while (var5.hasNext()) {
			final BlockPos var6 = (BlockPos) var5.next();
			final int var7 = var6.getX() - var2;
			final int var8 = var6.getY() - var3;
			final int var9 = var6.getZ() - var4;
			data.writeByte(var7);
			data.writeByte(var8);
			data.writeByte(var9);
		}

		data.writeFloat(x);
		data.writeFloat(y);
		data.writeFloat(z);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleExplosion(this);
	}

	public float func_149149_c() {
		return x;
	}

	public float func_149144_d() {
		return y;
	}

	public float func_149147_e() {
		return z;
	}

	public double func_149148_f() {
		return field_149158_a;
	}

	public double func_149143_g() {
		return field_149156_b;
	}

	public double func_149145_h() {
		return field_149157_c;
	}

	public float func_149146_i() {
		return field_149154_d;
	}

	public List func_149150_j() {
		return field_149155_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
