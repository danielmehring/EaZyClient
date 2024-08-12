package net.minecraft.network.play.server;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class S0CPacketSpawnPlayer implements Packet {

public static final int EaZy = 1414;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148957_a;
	private UUID field_179820_b;
	private int field_148956_c;
	private int field_148953_d;
	private int field_148954_e;
	private byte field_148951_f;
	private byte field_148952_g;
	private int field_148959_h;
	private DataWatcher field_148960_i;
	private List field_148958_j;
	// private static final String __OBFID = "http://https://fuckuskid00001281";

	public S0CPacketSpawnPlayer() {}

	public S0CPacketSpawnPlayer(final EntityPlayer p_i45171_1_) {
		field_148957_a = p_i45171_1_.getEntityId();
		field_179820_b = p_i45171_1_.getGameProfile().getId();
		field_148956_c = MathHelper.floor_double(p_i45171_1_.posX * 32.0D);
		field_148953_d = MathHelper.floor_double(p_i45171_1_.posY * 32.0D);
		field_148954_e = MathHelper.floor_double(p_i45171_1_.posZ * 32.0D);
		field_148951_f = (byte) (int) (p_i45171_1_.rotationYaw * 256.0F / 360.0F);
		field_148952_g = (byte) (int) (p_i45171_1_.rotationPitch * 256.0F / 360.0F);
		final ItemStack var2 = p_i45171_1_.inventory.getCurrentItem();
		field_148959_h = var2 == null ? 0 : Item.getIdFromItem(var2.getItem());
		field_148960_i = p_i45171_1_.getDataWatcher();
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148957_a = data.readVarIntFromBuffer();
		field_179820_b = data.readUuid();
		field_148956_c = data.readInt();
		field_148953_d = data.readInt();
		field_148954_e = data.readInt();
		field_148951_f = data.readByte();
		field_148952_g = data.readByte();
		field_148959_h = data.readShort();
		field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(data);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeVarIntToBuffer(field_148957_a);
		data.writeUuid(field_179820_b);
		data.writeInt(field_148956_c);
		data.writeInt(field_148953_d);
		data.writeInt(field_148954_e);
		data.writeByte(field_148951_f);
		data.writeByte(field_148952_g);
		data.writeShort(field_148959_h);
		field_148960_i.writeTo(data);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayClient handler) {
		handler.handleSpawnPlayer(this);
	}

	public List func_148944_c() {
		if (field_148958_j == null) {
			field_148958_j = field_148960_i.getAllWatched();
		}

		return field_148958_j;
	}

	public int func_148943_d() {
		return field_148957_a;
	}

	public UUID func_179819_c() {
		return field_179820_b;
	}

	public int func_148942_f() {
		return field_148956_c;
	}

	public int func_148949_g() {
		return field_148953_d;
	}

	public int func_148946_h() {
		return field_148954_e;
	}

	public byte func_148941_i() {
		return field_148951_f;
	}

	public byte func_148945_j() {
		return field_148952_g;
	}

	public int func_148947_k() {
		return field_148959_h;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayClient) handler);
	}
}
