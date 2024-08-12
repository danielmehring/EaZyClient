package net.minecraft.network.login.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

import java.io.IOException;
import java.security.PublicKey;

public class S01PacketEncryptionRequest implements Packet {

public static final int EaZy = 1362;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String hashedServerId;
	private PublicKey publicKey;
	private byte[] field_149611_c;
	// private static final String __OBFID = "http://https://fuckuskid00001376";

	public S01PacketEncryptionRequest() {}

	public S01PacketEncryptionRequest(final String serverId, final PublicKey key, final byte[] p_i45268_3_) {
		hashedServerId = serverId;
		publicKey = key;
		field_149611_c = p_i45268_3_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		hashedServerId = data.readStringFromBuffer(20);
		publicKey = CryptManager.decodePublicKey(data.readByteArray());
		field_149611_c = data.readByteArray();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(hashedServerId);
		data.writeByteArray(publicKey.getEncoded());
		data.writeByteArray(field_149611_c);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerLoginClient handler) {
		handler.handleEncryptionRequest(this);
	}

	public String getServerId() {
		return hashedServerId;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public byte[] getVerifyToken() {
		return field_149611_c;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerLoginClient) handler);
	}
}
