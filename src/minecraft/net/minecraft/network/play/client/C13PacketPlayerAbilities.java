package net.minecraft.network.play.client;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C13PacketPlayerAbilities implements Packet {

public static final int EaZy = 1393;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean invulnerable;
	private boolean flying;
	private boolean allowFlying;
	private boolean creativeMode;
	private float flySpeed;
	private float walkSpeed;
	// private static final String __OBFID = "http://https://fuckuskid00001364";

	public C13PacketPlayerAbilities() {}

	public C13PacketPlayerAbilities(final PlayerCapabilities capabilities) {
		setInvulnerable(capabilities.disableDamage);
		setFlying(capabilities.isFlying);
		setAllowFlying(capabilities.allowFlying);
		setCreativeMode(capabilities.isCreativeMode);
		setFlySpeed(capabilities.getFlySpeed());
		setWalkSpeed(capabilities.getWalkSpeed());
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		final byte var2 = data.readByte();
		setInvulnerable((var2 & 1) > 0);
		setFlying((var2 & 2) > 0);
		setAllowFlying((var2 & 4) > 0);
		setCreativeMode((var2 & 8) > 0);
		setFlySpeed(data.readFloat());
		setWalkSpeed(data.readFloat());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		byte var2 = 0;

		if (isInvulnerable()) {
			var2 = (byte) (var2 | 1);
		}

		if (isFlying()) {
			var2 = (byte) (var2 | 2);
		}

		if (isAllowFlying()) {
			var2 = (byte) (var2 | 4);
		}

		if (isCreativeMode()) {
			var2 = (byte) (var2 | 8);
		}

		data.writeByte(var2);
		data.writeFloat(flySpeed);
		data.writeFloat(walkSpeed);
	}

	public void func_180761_a(final INetHandlerPlayServer p_180761_1_) {
		p_180761_1_.processPlayerAbilities(this);
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public void setInvulnerable(final boolean isInvulnerable) {
		invulnerable = isInvulnerable;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(final boolean isFlying) {
		flying = isFlying;
	}

	public boolean isAllowFlying() {
		return allowFlying;
	}

	public void setAllowFlying(final boolean isAllowFlying) {
		allowFlying = isAllowFlying;
	}

	public boolean isCreativeMode() {
		return creativeMode;
	}

	public void setCreativeMode(final boolean isCreativeMode) {
		creativeMode = isCreativeMode;
	}

	public void setFlySpeed(final float flySpeedIn) {
		flySpeed = flySpeedIn;
	}

	public void setWalkSpeed(final float walkSpeedIn) {
		walkSpeed = walkSpeedIn;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180761_a((INetHandlerPlayServer) handler);
	}
}
