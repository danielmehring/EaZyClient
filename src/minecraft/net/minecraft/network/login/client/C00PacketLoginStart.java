package net.minecraft.network.login.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

public class C00PacketLoginStart implements Packet {

	public static final int EaZy = 1357;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private GameProfile profile;
	// private static final String __OBFID = "http://https://fuckuskid00001379";

	public C00PacketLoginStart() {}

	public C00PacketLoginStart(final GameProfile profileIn) {
		profile = profileIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		profile = new GameProfile((UUID) null, data.readStringFromBuffer(16));
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(profile.getName());
	}

	public void func_180773_a(final INetHandlerLoginServer p_180773_1_) {
		p_180773_1_.processLoginStart(this);
	}

	public GameProfile getProfile() {
		return profile;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180773_a((INetHandlerLoginServer) handler);
	}
}
