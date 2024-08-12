package net.minecraft.network.play.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;
import java.util.Arrays;

public class C15PacketClientSettings implements Packet {

public static final int EaZy = 1395;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String lang;
	private int view;
	private EntityPlayer.EnumChatVisibility chatVisibility;
	private boolean enableColors;
	private int field_179711_e;
	// private static final String __OBFID = "http://https://fuckuskid00001350";

	public C15PacketClientSettings() {}

	public C15PacketClientSettings(final String p_i45946_1_, final int p_i45946_2_,
			final EntityPlayer.EnumChatVisibility p_i45946_3_, final boolean p_i45946_4_, final int p_i45946_5_) {
		lang = p_i45946_1_;
		view = p_i45946_2_;
		chatVisibility = p_i45946_3_;
		enableColors = p_i45946_4_;
		field_179711_e = p_i45946_5_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		lang = data.readStringFromBuffer(7);
		view = data.readByte();
		chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(data.readByte());
		enableColors = data.readBoolean();
		field_179711_e = data.readUnsignedByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeString(lang);
		data.writeByte(view);
		data.writeByte(chatVisibility.getChatVisibility());
		data.writeBoolean(enableColors);
		data.writeByte(field_179711_e);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processClientSettings(this);
	}

	public String getLang() {
		return lang;
	}

	public EntityPlayer.EnumChatVisibility getChatVisibility() {
		return chatVisibility;
	}

	public boolean isColorsEnabled() {
		return enableColors;
	}

	public int getView() {
		return field_179711_e;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
