package net.minecraft.network.login.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

import java.io.IOException;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class S02PacketLoginSuccess implements Packet {

public static final int EaZy = 1363;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private GameProfile profile;
	// private static final String __OBFID = "http://https://fuckuskid00001375";

	public S02PacketLoginSuccess() {}

	public S02PacketLoginSuccess(final GameProfile profileIn) {
		profile = profileIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		final String var2 = data.readStringFromBuffer(36);
		final String var3 = data.readStringFromBuffer(16);
		final UUID var4 = UUID.fromString(var2);
		profile = new GameProfile(var4, var3);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		final UUID var2 = profile.getId();
		data.writeString(var2 == null ? "" : var2.toString());
		data.writeString(profile.getName());
	}

	public void func_180771_a(final INetHandlerLoginClient p_180771_1_) {
		p_180771_1_.handleLoginSuccess(this);
	}

	public GameProfile func_179730_a() {
		return profile;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180771_a((INetHandlerLoginClient) handler);
	}
}
