package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C0CPacketInput implements Packet {

public static final int EaZy = 1386;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Positive for left strafe, negative for right */
	private float strafeSpeed;

	/** Positive for forward, negative for backward */
	private float forwardSpeed;
	private boolean jumping;
	private boolean sneaking;
	// private static final String __OBFID = "http://https://fuckuskid00001367";

	public C0CPacketInput() {}

	public C0CPacketInput(final float p_i45261_1_, final float p_i45261_2_, final boolean p_i45261_3_,
			final boolean p_i45261_4_) {
		strafeSpeed = p_i45261_1_;
		forwardSpeed = p_i45261_2_;
		jumping = p_i45261_3_;
		sneaking = p_i45261_4_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		strafeSpeed = data.readFloat();
		forwardSpeed = data.readFloat();
		final byte var2 = data.readByte();
		jumping = (var2 & 1) > 0;
		sneaking = (var2 & 2) > 0;
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeFloat(strafeSpeed);
		data.writeFloat(forwardSpeed);
		byte var2 = 0;

		if (jumping) {
			var2 = (byte) (var2 | 1);
		}

		if (sneaking) {
			var2 = (byte) (var2 | 2);
		}

		data.writeByte(var2);
	}

	public void func_180766_a(final INetHandlerPlayServer p_180766_1_) {
		p_180766_1_.processInput(this);
	}

	public float getStrafeSpeed() {
		return strafeSpeed;
	}

	public float getForwardSpeed() {
		return forwardSpeed;
	}

	public boolean isJumping() {
		return jumping;
	}

	public boolean isSneaking() {
		return sneaking;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180766_a((INetHandlerPlayServer) handler);
	}
}
