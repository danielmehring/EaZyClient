package net.minecraft.network.play.client;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;
import java.util.Arrays;

public class C0EPacketClickWindow implements Packet {

public static final int EaZy = 1388;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The id of the window which was clicked. 0 for player inventory. */
	private int windowId;

	/** Id of the clicked slot */
	private int slotId;

	/** Button used */
	private int usedButton;

	/** A unique number for the action, used for transaction handling */
	private short actionNumber;

	/** The item stack present in the slot */
	private ItemStack clickedItem;

	/** Inventory operation mode */
	private int mode;
	// private static final String __OBFID = "http://https://fuckuskid00001353";

	public C0EPacketClickWindow() {}

	public C0EPacketClickWindow(final int window, final int slot, final int _usedButton,
			final int _mode, final ItemStack item, final short actionNum) {
		windowId = window;
		slotId = slot;
		usedButton = _usedButton;
		clickedItem = item != null ? item : null;
		actionNumber = actionNum;
		mode = _mode;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(final INetHandlerPlayServer handler) {
		handler.processClickWindow(this);
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(final PacketBuffer data) throws IOException {
		windowId = data.readByte();
		slotId = data.readShort();
		usedButton = data.readByte();
		actionNumber = data.readShort();
		mode = data.readByte();
		clickedItem = data.readItemStackFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(final PacketBuffer data) throws IOException {
		data.writeByte(windowId);
		data.writeShort(slotId);
		data.writeByte(usedButton);
		data.writeShort(actionNumber);
		data.writeByte(mode);
		data.writeItemStackToBuffer(clickedItem);
	}

	public int getWindowId() {
		return windowId;
	}

	public int getSlotId() {
		return slotId;
	}

	public int getUsedButton() {
		return usedButton;
	}

	public short getActionNumber() {
		return actionNumber;
	}

	public ItemStack getClickedItem() {
		return clickedItem;
	}

	public int getMode() {
		return mode;
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(final INetHandler handler) {
		this.processPacket((INetHandlerPlayServer) handler);
	}
}
