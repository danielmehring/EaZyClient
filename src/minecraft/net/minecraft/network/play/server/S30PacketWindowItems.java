package net.minecraft.network.play.server;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.List;

public class S30PacketWindowItems implements Packet {

public static final int EaZy = 1447;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int field_148914_a;
	private ItemStack[] field_148913_b;
	// private static final String __OBFID = "http://https://fuckuskid00001294";

	public S30PacketWindowItems() {}

	public S30PacketWindowItems(final int p_i45186_1_, final List p_i45186_2_) {
		field_148914_a = p_i45186_1_;
		field_148913_b = new ItemStack[p_i45186_2_.size()];

		for (int var3 = 0; var3 < field_148913_b.length; ++var3) {
			final ItemStack var4 = (ItemStack) p_i45186_2_.get(var3);
			field_148913_b[var3] = var4 == null ? null : var4.copy();
		}
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		field_148914_a = data.readUnsignedByte();
		final short var2 = data.readShort();
		field_148913_b = new ItemStack[var2];

		for (int var3 = 0; var3 < var2; ++var3) {
			field_148913_b[var3] = data.readItemStackFromBuffer();
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(field_148914_a);
		data.writeShort(field_148913_b.length);
		final ItemStack[] var2 = field_148913_b;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final ItemStack var5 = var2[var4];
			data.writeItemStackToBuffer(var5);
		}
	}

	public void func_180732_a(final INetHandlerPlayClient p_180732_1_) {
		p_180732_1_.handleWindowItems(this);
	}

	public int func_148911_c() {
		return field_148914_a;
	}

	public ItemStack[] func_148910_d() {
		return field_148913_b;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180732_a((INetHandlerPlayClient) handler);
	}
}
