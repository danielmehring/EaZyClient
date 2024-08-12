package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class ServerSelectionList extends GuiListExtended {

public static final int EaZy = 549;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiMultiplayer owner;
	private final List field_148198_l = Lists.newArrayList();
	private final List field_148199_m = Lists.newArrayList();
	private final GuiListExtended.IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
	private int field_148197_o = -1;
	// private static final String __OBFID = "http://https://fuckuskid00000819";

	public ServerSelectionList(final GuiMultiplayer p_i45049_1_, final Minecraft mcIn, final int p_i45049_3_,
			final int p_i45049_4_, final int p_i45049_5_, final int p_i45049_6_, final int p_i45049_7_) {
		super(mcIn, p_i45049_3_, p_i45049_4_, p_i45049_5_, p_i45049_6_, p_i45049_7_);
		owner = p_i45049_1_;
	}

	/**
	 * Gets the IGuiListEntry object for the given index
	 */
	@Override
	public GuiListExtended.IGuiListEntry getListEntry(int p_148180_1_) {
		if (p_148180_1_ < field_148198_l.size()) {
			return (GuiListExtended.IGuiListEntry) field_148198_l.get(p_148180_1_);
		} else {
			p_148180_1_ -= field_148198_l.size();

			if (p_148180_1_ == 0) {
				return lanScanEntry;
			} else {
				--p_148180_1_;
				return (GuiListExtended.IGuiListEntry) field_148199_m.get(p_148180_1_);
			}
		}
	}

	@Override
	protected int getSize() {
		return field_148198_l.size() + 1 + field_148199_m.size();
	}

	public void func_148192_c(final int p_148192_1_) {
		field_148197_o = p_148192_1_;
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(final int slotIndex) {
		return slotIndex == field_148197_o;
	}

	public int func_148193_k() {
		return field_148197_o;
	}

	public void func_148195_a(final ServerList p_148195_1_) {
		field_148198_l.clear();

		for (int var2 = 0; var2 < p_148195_1_.countServers(); ++var2) {
			field_148198_l.add(new ServerListEntryNormal(owner, p_148195_1_.getServerData(var2)));
		}
	}

	public void func_148194_a(final List p_148194_1_) {
		field_148199_m.clear();
		final Iterator var2 = p_148194_1_.iterator();

		while (var2.hasNext()) {
			final LanServerDetector.LanServer var3 = (LanServerDetector.LanServer) var2.next();
			field_148199_m.add(new ServerListEntryLanDetected(owner, var3));
		}
	}

	@Override
	protected int getScrollBarX() {
		return super.getScrollBarX() + 30;
	}

	/**
	 * Gets the width of the list
	 */
	@Override
	public int getListWidth() {
		return super.getListWidth() + 85;
	}
}
