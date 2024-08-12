package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C19PacketResourcePackStatus implements Packet {

public static final int EaZy = 1399;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String field_179720_a;
	private C19PacketResourcePackStatus.Action field_179719_b;
	// private static final String __OBFID = "http://https://fuckuskid00002282";

	public C19PacketResourcePackStatus() {}

	public C19PacketResourcePackStatus(String p_i45935_1_, final C19PacketResourcePackStatus.Action p_i45935_2_) {
		if (p_i45935_1_.length() > 40) {
			p_i45935_1_ = p_i45935_1_.substring(0, 40);
		}

		field_179720_a = p_i45935_1_;
		field_179719_b = p_i45935_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179720_a = data.readStringFromBuffer(40);
		field_179719_b = (C19PacketResourcePackStatus.Action) data
				.readEnumValue(C19PacketResourcePackStatus.Action.class);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(field_179720_a);
		data.writeEnumValue(field_179719_b);
	}

	public void func_179718_a(final INetHandlerPlayServer p_179718_1_) {
		p_179718_1_.func_175086_a(this);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179718_a((INetHandlerPlayServer) handler);
	}

	public static enum Action {
		SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0), DECLINED("DECLINED", 1), FAILED_DOWNLOAD("FAILED_DOWNLOAD",
				2), ACCEPTED("ACCEPTED", 3);

		private Action(final String p_i45934_1_, final int p_i45934_2_) {}
	}
}
