package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

import java.io.IOException;
import java.util.Collection;

public class S34PacketMaps implements Packet {

public static final int EaZy = 1451;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int mapId;
	private byte field_179739_b;
	private Vec4b[] field_179740_c;
	private int field_179737_d;
	private int field_179738_e;
	private int field_179735_f;
	private int field_179736_g;
	private byte[] field_179741_h;
	// private static final String __OBFID = "http://https://fuckuskid00001311";

	public S34PacketMaps() {}

	public S34PacketMaps(final int p_i45975_1_, final byte p_i45975_2_, final Collection p_i45975_3_,
			final byte[] p_i45975_4_, final int p_i45975_5_, final int p_i45975_6_, final int p_i45975_7_,
			final int p_i45975_8_) {
		mapId = p_i45975_1_;
		field_179739_b = p_i45975_2_;
		field_179740_c = (Vec4b[]) p_i45975_3_.toArray(new Vec4b[p_i45975_3_.size()]);
		field_179737_d = p_i45975_5_;
		field_179738_e = p_i45975_6_;
		field_179735_f = p_i45975_7_;
		field_179736_g = p_i45975_8_;
		field_179741_h = new byte[p_i45975_7_ * p_i45975_8_];

		for (int var9 = 0; var9 < p_i45975_7_; ++var9) {
			for (int var10 = 0; var10 < p_i45975_8_; ++var10) {
				field_179741_h[var9 + var10 * p_i45975_7_] = p_i45975_4_[p_i45975_5_ + var9
						+ (p_i45975_6_ + var10) * 128];
			}
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		mapId = data.readVarIntFromBuffer();
		field_179739_b = data.readByte();
		field_179740_c = new Vec4b[data.readVarIntFromBuffer()];

		for (int var2 = 0; var2 < field_179740_c.length; ++var2) {
			final short var3 = data.readByte();
			field_179740_c[var2] = new Vec4b((byte) (var3 >> 4 & 15), data.readByte(), data.readByte(),
					(byte) (var3 & 15));
		}

		field_179735_f = data.readUnsignedByte();

		if (field_179735_f > 0) {
			field_179736_g = data.readUnsignedByte();
			field_179737_d = data.readUnsignedByte();
			field_179738_e = data.readUnsignedByte();
			field_179741_h = data.readByteArray();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(mapId);
		data.writeByte(field_179739_b);
		data.writeVarIntToBuffer(field_179740_c.length);
		final Vec4b[] var2 = field_179740_c;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final Vec4b var5 = var2[var4];
			data.writeByte((var5.func_176110_a() & 15) << 4 | var5.func_176111_d() & 15);
			data.writeByte(var5.func_176112_b());
			data.writeByte(var5.func_176113_c());
		}

		data.writeByte(field_179735_f);

		if (field_179735_f > 0) {
			data.writeByte(field_179736_g);
			data.writeByte(field_179737_d);
			data.writeByte(field_179738_e);
			data.writeByteArray(field_179741_h);
		}
	}

	public void func_180741_a(final INetHandlerPlayClient p_180741_1_) {
		p_180741_1_.handleMaps(this);
	}

	public int getMapId() {
		return mapId;
	}

	public void func_179734_a(final MapData p_179734_1_) {
		p_179734_1_.scale = field_179739_b;
		p_179734_1_.playersVisibleOnMap.clear();
		int var2;

		for (var2 = 0; var2 < field_179740_c.length; ++var2) {
			final Vec4b var3 = field_179740_c[var2];
			p_179734_1_.playersVisibleOnMap.put("icon-" + var2, var3);
		}

		for (var2 = 0; var2 < field_179735_f; ++var2) {
			for (int var4 = 0; var4 < field_179736_g; ++var4) {
				p_179734_1_.colors[field_179737_d + var2 + (field_179738_e + var4) * 128] = field_179741_h[var2
						+ var4 * field_179735_f];
			}
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180741_a((INetHandlerPlayClient) handler);
	}
}
