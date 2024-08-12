package net.minecraft.network.login.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.SecretKey;

public class C01PacketEncryptionResponse implements Packet {

public static final int EaZy = 1358;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private byte[] field_149302_a = new byte[0];
	private byte[] field_149301_b = new byte[0];
	// private static final String __OBFID = "http://https://fuckuskid00001380";

	public C01PacketEncryptionResponse() {}

	public C01PacketEncryptionResponse(final SecretKey p_i45271_1_, final PublicKey p_i45271_2_,
			final byte[] p_i45271_3_) {
		field_149302_a = CryptManager.encryptData(p_i45271_2_, p_i45271_1_.getEncoded());
		field_149301_b = CryptManager.encryptData(p_i45271_2_, p_i45271_3_);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_149302_a = data.readByteArray();
		field_149301_b = data.readByteArray();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByteArray(field_149302_a);
		data.writeByteArray(field_149301_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerLoginServer handler) {
		handler.processEncryptionResponse(this);
	}

	public SecretKey func_149300_a(final PrivateKey key) {
		return CryptManager.decryptSharedKey(key, field_149302_a);
	}

	public byte[] func_149299_b(final PrivateKey p_149299_1_) {
		return p_149299_1_ == null ? field_149301_b : CryptManager.decryptData(p_149299_1_, field_149301_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerLoginServer) handler);
	}
}
