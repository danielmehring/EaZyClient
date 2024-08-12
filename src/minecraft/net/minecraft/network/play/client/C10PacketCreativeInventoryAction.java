package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C10PacketCreativeInventoryAction implements Packet {

public static final int EaZy = 1390;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int slotId;
	private ItemStack stack;
	// private static final String __OBFID = "http://https://fuckuskid00001369";

	public C10PacketCreativeInventoryAction() {}

	public C10PacketCreativeInventoryAction(final int p_i45263_1_, final ItemStack p_i45263_2_) {
		slotId = p_i45263_1_;
		stack = p_i45263_2_ != null ? p_i45263_2_.copy() : null;
	}

	public void func_180767_a(final INetHandlerPlayServer p_180767_1_) {
		p_180767_1_.processCreativeInventoryAction(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		slotId = data.readShort();
		stack = data.readItemStackFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeShort(slotId);
		data.writeItemStackToBuffer(stack);
	}

	public int getSlotId() {
		return slotId;
	}

	public ItemStack getStack() {
		return stack;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		func_180767_a((INetHandlerPlayServer) handler);
	}
}
