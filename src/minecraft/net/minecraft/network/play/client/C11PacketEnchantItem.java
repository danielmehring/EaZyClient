package net.minecraft.network.play.client;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C11PacketEnchantItem implements Packet {

public static final int EaZy = 1391;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

    public int id;
	public int button;
	// private static final String __OBFID = "http://https://fuckuskid00001352";

	public C11PacketEnchantItem() {}

	public C11PacketEnchantItem(final int p_i45245_1_, final int p_i45245_2_) {
		id = p_i45245_1_;
		button = p_i45245_2_;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processEnchantItem(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		id = data.readByte();
		button = data.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(id);
		data.writeByte(button);
	}

	public int getId() {
		return id;
	}

	public int getButton() {
		return button;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
