package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete implements Packet {

public static final int EaZy = 1394;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String message;
	private BlockPos field_179710_b;
	// private static final String __OBFID = "http://https://fuckuskid00001346";

	public C14PacketTabComplete() {}

	public C14PacketTabComplete(final String msg) {
		this(msg, (BlockPos) null);
	}

	public C14PacketTabComplete(final String p_i45948_1_, final BlockPos p_i45948_2_) {
		message = p_i45948_1_;
		field_179710_b = p_i45948_2_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		message = data.readStringFromBuffer(32767);
		final boolean var2 = data.readBoolean();

		if (var2) {
			field_179710_b = data.readBlockPos();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(StringUtils.substring(message, 0, 32767));
		final boolean var2 = field_179710_b != null;
		data.writeBoolean(var2);

		if (var2) {
			data.writeBlockPos(field_179710_b);
		}
	}

	public void func_180756_a(final INetHandlerPlayServer p_180756_1_) {
		p_180756_1_.processTabComplete(this);
	}

	public String getMessage() {
		return message;
	}

	public BlockPos getBlockPos() {
		return field_179710_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180756_a((INetHandlerPlayServer) handler);
	}
}
