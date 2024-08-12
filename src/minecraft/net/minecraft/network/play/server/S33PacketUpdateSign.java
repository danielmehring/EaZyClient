package net.minecraft.network.play.server;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.io.IOException;

public class S33PacketUpdateSign implements Packet {

	public static final int EaZy = 1450;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private BlockPos field_179705_b;
	private IChatComponent[] field_149349_d;
	// private static final String __OBFID = "http://https://fuckuskid00001338";

	public S33PacketUpdateSign() {}

	public S33PacketUpdateSign(final World worldIn, final BlockPos p_i45951_2_, final IChatComponent[] p_i45951_3_) {
		field_179705_b = p_i45951_2_;
		field_149349_d = new IChatComponent[] { p_i45951_3_[0], p_i45951_3_[1], p_i45951_3_[2], p_i45951_3_[3] };
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_179705_b = data.readBlockPos();
		field_149349_d = new IChatComponent[4];
		boolean breakit = false;
		for (int var2 = 0; var2 < 4; ++var2) {
			field_149349_d[var2] = data.readChatComponent();
			if (field_149349_d[var2] != null && field_149349_d[var2].getFormattedText().length() > 100) {
				breakit = true;
			}
		}
		if (breakit) {
			field_149349_d[0] = new ChatComponentText("");
			field_149349_d[1] = new ChatComponentText("~~ BLOCKED ~~");
			field_149349_d[2] = new ChatComponentText("Sign Crasher");
			field_149349_d[3] = new ChatComponentText("");
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeBlockPos(field_179705_b);

		for (int var2 = 0; var2 < 4; ++var2) {
			data.writeChatComponent(field_149349_d[var2]);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleUpdateSign(this);
	}

	public BlockPos func_179704_a() {
		return field_179705_b;
	}

	public IChatComponent[] func_180753_b() {
		return field_149349_d;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
