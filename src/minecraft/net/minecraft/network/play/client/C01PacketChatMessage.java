package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;

import me.EaZy.client.utils.UnicodeUtils;

public class C01PacketChatMessage implements Packet {

	public static final int EaZy = 1378;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private String message;
	// private static final String __OBFID = "http://https://fuckuskid00001347";

	public C01PacketChatMessage() {}

	public C01PacketChatMessage(String messageIn) {
		messageIn = EnumChatFormatting.getTextWithoutFormattingCodes(messageIn);
		messageIn = UnicodeUtils.getUnicodeText(messageIn);
		if (messageIn.length() > 100) {
			messageIn = messageIn.substring(0, 100);
		}
		message = messageIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		if (!message.isEmpty())
			message = data.readStringFromBuffer(100);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		if (!message.isEmpty())
			data.writeString(message);
	}

	public void func_180757_a(final INetHandlerPlayServer p_180757_1_) {
		if (!message.isEmpty())
			p_180757_1_.processChatMessage(this);
	}

	public String getMessage() {
		return message;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		if (!message.isEmpty())
			func_180757_a((INetHandlerPlayServer) handler);
	}
}
