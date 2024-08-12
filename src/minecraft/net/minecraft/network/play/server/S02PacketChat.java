package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

public class S02PacketChat implements Packet {

public static final int EaZy = 1404;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IChatComponent chatComponent;
	private byte field_179842_b;
	// private static final String __OBFID = "http://https://fuckuskid00001289";

	public S02PacketChat() {}

	public S02PacketChat(final IChatComponent component) {
		this(component, (byte) 1);
	}

	public S02PacketChat(final IChatComponent p_i45986_1_, final byte p_i45986_2_) {
		chatComponent = p_i45986_1_;
		field_179842_b = p_i45986_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		chatComponent = data.readChatComponent();
		field_179842_b = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeChatComponent(chatComponent);
		data.writeByte(field_179842_b);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleChat(this);
	}

	public IChatComponent func_148915_c() {
		return chatComponent;
	}

	public boolean isChat() {
		return field_179842_b == 1 || field_179842_b == 2;
	}

	public byte func_179841_c() {
		return field_179842_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
