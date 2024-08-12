package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer implements Packet {

	public static final int EaZy = 1380;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	public boolean onGround;
	protected boolean hasPosition;
	protected boolean hasRotation;
	public boolean isScaffoldOrTower;
	public boolean isRegen;
	// private static final String __OBFID = "http://https://fuckuskid00001360";

	public C03PacketPlayer() {}

	public C03PacketPlayer(final boolean p_i45256_1_) {
		onGround = p_i45256_1_;
	}

	public C03PacketPlayer(final boolean p_i45256_1_, final boolean scafftower, final boolean regen) {
		onGround = p_i45256_1_;
		isScaffoldOrTower = scafftower;
		isRegen = regen;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processPlayer(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		onGround = data.readUnsignedByte() != 0;
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(onGround ? 1 : 0);
	}

	public double getPositionX() {
		return x;
	}

	public double getPositionY() {
		return y;
	}

	public double getPositionZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public boolean func_149465_i() {
		return onGround;
	}

	public boolean func_149466_j() {
		return hasPosition;
	}

	public boolean getHasRotation() {
		return hasRotation;
	}

	public boolean getHasPosition() {
		return hasPosition;
	}

	public void func_149469_a(final boolean p_149469_1_) {
		hasPosition = p_149469_1_;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}

	public static class C04PacketPlayerPosition extends C03PacketPlayer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001361";

		public C04PacketPlayerPosition() {
			hasPosition = true;
		}

		public C04PacketPlayerPosition(final double p_i45942_1_, final double p_i45942_3_, final double p_i45942_5_,
				final boolean p_i45942_7_) {
			x = p_i45942_1_;
			y = p_i45942_3_;
			z = p_i45942_5_;
			onGround = p_i45942_7_;
			hasPosition = true;
		}

		public C04PacketPlayerPosition(final double p_i45942_1_, final double p_i45942_3_, final double p_i45942_5_,
				final boolean p_i45942_7_, final boolean scafftower) {
			x = p_i45942_1_;
			y = p_i45942_3_;
			z = p_i45942_5_;
			onGround = p_i45942_7_;
			hasPosition = true;
			isScaffoldOrTower = scafftower;
		}

		@Override
		public void readPacketData(final PacketBuffer data) throws IOException {
			x = data.readDouble();
			y = data.readDouble();
			z = data.readDouble();
			super.readPacketData(data);
		}

		@Override
		public void writePacketData(final PacketBuffer data) throws IOException {
			data.writeDouble(x);
			data.writeDouble(y);
			data.writeDouble(z);
			super.writePacketData(data);
		}

		@Override
		public void processPacket(final INetHandler handler) {
			super.processPacket((INetHandlerPlayServer) handler);
		}
	}

	public static class C05PacketPlayerLook extends C03PacketPlayer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001363";

		public C05PacketPlayerLook() {
			hasRotation = true;
		}

		// TODO

		public C05PacketPlayerLook(final float p_i45255_1_, final float p_i45255_2_, final boolean p_i45255_3_) {
			yaw = p_i45255_1_;
			pitch = p_i45255_2_;
			onGround = p_i45255_3_;
			hasRotation = true;
		}

		public C05PacketPlayerLook(final float p_i45255_1_, final float p_i45255_2_, final boolean p_i45255_3_,
				final boolean scafftower) {
			yaw = p_i45255_1_;
			pitch = p_i45255_2_;
			onGround = p_i45255_3_;
			hasRotation = true;
			isScaffoldOrTower = scafftower;
		}

		@Override
		public void readPacketData(final PacketBuffer data) throws IOException {
			yaw = data.readFloat();
			pitch = data.readFloat();
			super.readPacketData(data);
		}

		@Override
		public void writePacketData(final PacketBuffer data) throws IOException {
			data.writeFloat(yaw);
			data.writeFloat(pitch);
			super.writePacketData(data);
		}

		@Override
		public void processPacket(final INetHandler handler) {
			super.processPacket((INetHandlerPlayServer) handler);
		}
	}

	public static class C06PacketPlayerPosLook extends C03PacketPlayer {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001362";

		public C06PacketPlayerPosLook() {
			hasPosition = true;
			hasRotation = true;
		}

		public C06PacketPlayerPosLook(final double p_i45941_1_, final double p_i45941_3_, final double p_i45941_5_,
				final float p_i45941_7_, final float p_i45941_8_, final boolean p_i45941_9_) {
			x = p_i45941_1_;
			y = p_i45941_3_;
			z = p_i45941_5_;
			yaw = p_i45941_7_;
			pitch = p_i45941_8_;
			onGround = p_i45941_9_;
			hasRotation = true;
			hasPosition = true;
		}

		public C06PacketPlayerPosLook(final double p_i45941_1_, final double p_i45941_3_, final double p_i45941_5_,
				final float p_i45941_7_, final float p_i45941_8_, final boolean p_i45941_9_, final boolean scafftower) {
			x = p_i45941_1_;
			y = p_i45941_3_;
			z = p_i45941_5_;
			yaw = p_i45941_7_;
			pitch = p_i45941_8_;
			onGround = p_i45941_9_;
			hasRotation = true;
			hasPosition = true;
			isScaffoldOrTower = scafftower;
		}

		@Override
		public void readPacketData(final PacketBuffer data) throws IOException {
			x = data.readDouble();
			y = data.readDouble();
			z = data.readDouble();
			yaw = data.readFloat();
			pitch = data.readFloat();
			super.readPacketData(data);
		}

		@Override
		public void writePacketData(final PacketBuffer data) throws IOException {
			data.writeDouble(x);
			data.writeDouble(y);
			data.writeDouble(z);
			data.writeFloat(yaw);
			data.writeFloat(pitch);
			super.writePacketData(data);
		}

		@Override
		public void processPacket(final INetHandler handler) {
			super.processPacket((INetHandlerPlayServer) handler);
		}
	}
}
