package net.minecraft.network.status.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class S00PacketServerInfo implements Packet {

	public static final int EaZy = 1479;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(ServerStatusResponse.MinecraftProtocolVersionIdentifier.class,
					new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer())
			.registerTypeAdapter(ServerStatusResponse.PlayerCountData.class,
					new ServerStatusResponse.PlayerCountData.Serializer())
			.registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer())
			.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer())
			.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer())
			.registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
	private ServerStatusResponse response;
	// private static final String __OBFID = "http://https://fuckuskid00001384";

	public S00PacketServerInfo() {}

	public S00PacketServerInfo(final ServerStatusResponse responseIn) {
		response = responseIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		response = GSON.fromJson(data.readStringFromBuffer(32767), ServerStatusResponse.class);
		if (EnumChatFormatting
				.getTextWithoutFormattingCodes(
						response.getServerDescription().getFormattedText().replace("\n", "").replace("\r", ""))
				.isEmpty()) {
			ServerStatusResponse prev = response;
			response = new ServerStatusResponse();
			response.setFavicon(prev.getFavicon());
			response.setPlayerCountData(prev.getPlayerCountData());
			response.setProtocolVersionInfo(prev.getProtocolVersionInfo());
			response.setServerDescription(new ChatComponentText("§4§lClient Crasher BLOCKED"));
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(GSON.toJson(response));
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerStatusClient handler) {
		handler.handleServerInfo(this);
	}

	public ServerStatusResponse func_149294_c() {
		return response;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerStatusClient) handler);
	}
}
