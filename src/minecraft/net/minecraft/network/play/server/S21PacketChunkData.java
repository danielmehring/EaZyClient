package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

public class S21PacketChunkData implements Packet {

public static final int EaZy = 1432;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_149284_a;
	private int field_149282_b;
	private S21PacketChunkData.Extracted field_179758_c;
	private boolean field_149279_g;
	// private static final String __OBFID = "http://https://fuckuskid00001304";

	public S21PacketChunkData() {}

	public S21PacketChunkData(final Chunk chunk, final boolean p_i45196_2_, final int p_i45196_3_) {
		field_149284_a = chunk.xPosition;
		field_149282_b = chunk.zPosition;
		field_149279_g = p_i45196_2_;
		field_179758_c = func_179756_a(chunk, p_i45196_2_, !chunk.getWorld().provider.getHasNoSky(),
				p_i45196_3_);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149284_a = data.readInt();
		field_149282_b = data.readInt();
		field_149279_g = data.readBoolean();
		field_179758_c = new S21PacketChunkData.Extracted();
		field_179758_c.field_150280_b = data.readShort();
		field_179758_c.field_150282_a = data.readByteArray();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeInt(field_149284_a);
		data.writeInt(field_149282_b);
		data.writeBoolean(field_149279_g);
		data.writeShort((short) (field_179758_c.field_150280_b & 65535));
		data.writeByteArray(field_179758_c.field_150282_a);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleChunkData(this);
	}

	public byte[] func_149272_d() {
		return field_179758_c.field_150282_a;
	}

	protected static int func_180737_a(final int p_180737_0_, final boolean p_180737_1_, final boolean p_180737_2_) {
		final int var3 = p_180737_0_ * 2 * 16 * 16 * 16;
		final int var4 = p_180737_0_ * 16 * 16 * 16 / 2;
		final int var5 = p_180737_1_ ? p_180737_0_ * 16 * 16 * 16 / 2 : 0;
		final int var6 = p_180737_2_ ? 256 : 0;
		return var3 + var4 + var5 + var6;
	}

	public static S21PacketChunkData.Extracted func_179756_a(final Chunk p_179756_0_, final boolean p_179756_1_,
			final boolean p_179756_2_, final int p_179756_3_) {
		final ExtendedBlockStorage[] var4 = p_179756_0_.getBlockStorageArray();
		final S21PacketChunkData.Extracted var5 = new S21PacketChunkData.Extracted();
		final ArrayList var6 = Lists.newArrayList();
		int var7;

		for (var7 = 0; var7 < var4.length; ++var7) {
			final ExtendedBlockStorage var8 = var4[var7];

			if (var8 != null && (!p_179756_1_ || !var8.isEmpty()) && (p_179756_3_ & 1 << var7) != 0) {
				var5.field_150280_b |= 1 << var7;
				var6.add(var8);
			}
		}

		var5.field_150282_a = new byte[func_180737_a(Integer.bitCount(var5.field_150280_b), p_179756_2_, p_179756_1_)];
		var7 = 0;
		Iterator var15 = var6.iterator();
		ExtendedBlockStorage var9;

		while (var15.hasNext()) {
			var9 = (ExtendedBlockStorage) var15.next();
			final char[] var10 = var9.getData();
			final char[] var11 = var10;
			final int var12 = var10.length;

			for (int var13 = 0; var13 < var12; ++var13) {
				final char var14 = var11[var13];
				var5.field_150282_a[var7++] = (byte) (var14 & 255);
				var5.field_150282_a[var7++] = (byte) (var14 >> 8 & 255);
			}
		}

		for (var15 = var6.iterator(); var15
				.hasNext(); var7 = func_179757_a(var9.getBlocklightArray().getData(), var5.field_150282_a, var7)) {
			var9 = (ExtendedBlockStorage) var15.next();
		}

		if (p_179756_2_) {
			for (var15 = var6.iterator(); var15
					.hasNext(); var7 = func_179757_a(var9.getSkylightArray().getData(), var5.field_150282_a, var7)) {
				var9 = (ExtendedBlockStorage) var15.next();
			}
		}

		if (p_179756_1_) {
			func_179757_a(p_179756_0_.getBiomeArray(), var5.field_150282_a, var7);
		}

		return var5;
	}

	private static int func_179757_a(final byte[] p_179757_0_, final byte[] p_179757_1_, final int p_179757_2_) {
		System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
		return p_179757_2_ + p_179757_0_.length;
	}

	public int func_149273_e() {
		return field_149284_a;
	}

	public int func_149271_f() {
		return field_149282_b;
	}

	public int func_149276_g() {
		return field_179758_c.field_150280_b;
	}

	public boolean func_149274_i() {
		return field_149279_g;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}

	public static class Extracted {
		public byte[] field_150282_a;
		public int field_150280_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001305";
	}
}
