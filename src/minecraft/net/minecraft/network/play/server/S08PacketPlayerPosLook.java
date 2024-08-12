package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class S08PacketPlayerPosLook implements Packet {

public static final int EaZy = 1410;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public double x;
	public double y;
	public double z;
	public float rotationYawPacket;
	public float rotationPitchPacket;
	private Set field_179835_f;
	// private static final String __OBFID = "http://https://fuckuskid00001273";

	public S08PacketPlayerPosLook() {}

	public S08PacketPlayerPosLook(final double p_i45993_1_, final double p_i45993_3_, final double p_i45993_5_,
			final float p_i45993_7_, final float p_i45993_8_, final Set p_i45993_9_) {
		x = p_i45993_1_;
		y = p_i45993_3_;
		z = p_i45993_5_;
		rotationYawPacket = p_i45993_7_;
		rotationPitchPacket = p_i45993_8_;
		field_179835_f = p_i45993_9_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		x = data.readDouble();
		y = data.readDouble();
		z = data.readDouble();
		rotationYawPacket = data.readFloat();
		rotationPitchPacket = data.readFloat();
		field_179835_f = S08PacketPlayerPosLook.EnumFlags.func_180053_a(data.readUnsignedByte());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeDouble(x);
		data.writeDouble(y);
		data.writeDouble(z);
		data.writeFloat(rotationYawPacket);
		data.writeFloat(rotationPitchPacket);
		data.writeByte(S08PacketPlayerPosLook.EnumFlags.func_180056_a(field_179835_f));
	}

	public void func_180718_a(final INetHandlerPlayClient p_180718_1_) {
		p_180718_1_.handlePlayerPosLook(this);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return rotationYawPacket;
	}

	public float getPitch() {
		return rotationPitchPacket;
	}

	public Set func_179834_f() {
		return field_179835_f;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180718_a((INetHandlerPlayClient) handler);
	}

	public static enum EnumFlags {
		X("X", 0, 0), Y("Y", 1, 1), Z("Z", 2, 2), Y_ROT("Y_ROT", 3, 3), X_ROT("X_ROT", 4, 4);
		private int field_180058_f;

		private EnumFlags(final String p_i45992_1_, final int p_i45992_2_, final int p_i45992_3_) {
			field_180058_f = p_i45992_3_;
		}

		private int func_180055_a() {
			return 1 << field_180058_f;
		}

		private boolean func_180054_b(final int p_180054_1_) {
			return (p_180054_1_ & func_180055_a()) == func_180055_a();
		}

		public static Set func_180053_a(final int p_180053_0_) {
			final EnumSet var1 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
			final S08PacketPlayerPosLook.EnumFlags[] var2 = values();
			final int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final S08PacketPlayerPosLook.EnumFlags var5 = var2[var4];

				if (var5.func_180054_b(p_180053_0_)) {
					var1.add(var5);
				}
			}

			return var1;
		}

		public static int func_180056_a(final Set p_180056_0_) {
			int var1 = 0;
			S08PacketPlayerPosLook.EnumFlags var3;

			for (final Iterator var2 = p_180056_0_.iterator(); var2.hasNext(); var1 |= var3.func_180055_a()) {
				var3 = (S08PacketPlayerPosLook.EnumFlags) var2.next();
			}

			return var1;
		}
	}
}
