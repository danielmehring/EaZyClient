package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

import java.io.IOException;
import java.util.List;

public class S26PacketMapChunkBulk implements Packet {

public static final int EaZy = 1437;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int[] field_149266_a;
	private int[] field_149264_b;
	private S21PacketChunkData.Extracted[] field_179755_c;
	private boolean field_149267_h;
	// private static final String __OBFID = "http://https://fuckuskid00001306";

	public S26PacketMapChunkBulk() {}

	public S26PacketMapChunkBulk(final List p_i45197_1_) {
		final int var2 = p_i45197_1_.size();
		field_149266_a = new int[var2];
		field_149264_b = new int[var2];
		field_179755_c = new S21PacketChunkData.Extracted[var2];
		field_149267_h = !((Chunk) p_i45197_1_.get(0)).getWorld().provider.getHasNoSky();

		for (int var3 = 0; var3 < var2; ++var3) {
			final Chunk var4 = (Chunk) p_i45197_1_.get(var3);
			final S21PacketChunkData.Extracted var5 = S21PacketChunkData.func_179756_a(var4, true, field_149267_h,
					65535);
			field_149266_a[var3] = var4.xPosition;
			field_149264_b[var3] = var4.zPosition;
			field_179755_c[var3] = var5;
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149267_h = data.readBoolean();
		final int var2 = data.readVarIntFromBuffer();
		field_149266_a = new int[var2];
		field_149264_b = new int[var2];
		field_179755_c = new S21PacketChunkData.Extracted[var2];
		int var3;

		for (var3 = 0; var3 < var2; ++var3) {
			field_149266_a[var3] = data.readInt();
			field_149264_b[var3] = data.readInt();
			field_179755_c[var3] = new S21PacketChunkData.Extracted();
			field_179755_c[var3].field_150280_b = data.readShort() & 65535;
			field_179755_c[var3].field_150282_a = new byte[S21PacketChunkData
					.func_180737_a(Integer.bitCount(field_179755_c[var3].field_150280_b), field_149267_h, true)];
		}

		for (var3 = 0; var3 < var2; ++var3) {
			data.readBytes(field_179755_c[var3].field_150282_a);
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBoolean(field_149267_h);
		data.writeVarIntToBuffer(field_179755_c.length);
		int var2;

		for (var2 = 0; var2 < field_149266_a.length; ++var2) {
			data.writeInt(field_149266_a[var2]);
			data.writeInt(field_149264_b[var2]);
			data.writeShort((short) (field_179755_c[var2].field_150280_b & 65535));
		}

		for (var2 = 0; var2 < field_149266_a.length; ++var2) {
			data.writeBytes(field_179755_c[var2].field_150282_a);
		}
	}

	public void func_180738_a(final INetHandlerPlayClient p_180738_1_) {
		p_180738_1_.handleMapChunkBulk(this);
	}

	public int func_149255_a(final int p_149255_1_) {
		return field_149266_a[p_149255_1_];
	}

	public int func_149253_b(final int p_149253_1_) {
		return field_149264_b[p_149253_1_];
	}

	public int func_149254_d() {
		return field_149266_a.length;
	}

	public byte[] func_149256_c(final int p_149256_1_) {
		return field_179755_c[p_149256_1_].field_150282_a;
	}

	public int func_179754_d(final int p_179754_1_) {
		return field_179755_c[p_179754_1_].field_150280_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180738_a((INetHandlerPlayClient) handler);
	}
}
