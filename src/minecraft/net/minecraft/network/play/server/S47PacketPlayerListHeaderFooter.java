package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

public class S47PacketPlayerListHeaderFooter implements Packet {

public static final int EaZy = 1470;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IChatComponent field_179703_a;
	private IChatComponent field_179702_b;
	// private static final String __OBFID = "http://https://fuckuskid00002285";

	public S47PacketPlayerListHeaderFooter() {}

	public S47PacketPlayerListHeaderFooter(final IChatComponent p_i45950_1_) {
		field_179703_a = p_i45950_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179703_a = data.readChatComponent();
		field_179702_b = data.readChatComponent();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeChatComponent(field_179703_a);
		data.writeChatComponent(field_179702_b);
	}

	public void func_179699_a(final INetHandlerPlayClient p_179699_1_) {
		p_179699_1_.func_175096_a(this);
	}

	public IChatComponent func_179700_a() {
		return field_179703_a;
	}

	public IChatComponent func_179701_b() {
		return field_179702_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_179699_a((INetHandlerPlayClient) handler);
	}
}
