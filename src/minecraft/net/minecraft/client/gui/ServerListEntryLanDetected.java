package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry {

public static final int EaZy = 546;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiMultiplayer field_148292_c;
	protected final Minecraft field_148293_a;
	protected final LanServerDetector.LanServer field_148291_b;
	private long field_148290_d = 0L;
	// private static final String __OBFID = "http://https://fuckuskid00000816";

	protected ServerListEntryLanDetected(final GuiMultiplayer p_i45046_1_,
			final LanServerDetector.LanServer p_i45046_2_) {
		field_148292_c = p_i45046_1_;
		field_148291_b = p_i45046_2_;
		field_148293_a = Minecraft.getMinecraft();
	}

	@Override
	public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
			final int mouseX, final int mouseY, final boolean isSelected) {
		field_148293_a.fontRendererObj.drawString(I18n.format("lanServer.title", new Object[0]), x + 32 + 3, y + 1,
				16777215);
		field_148293_a.fontRendererObj.drawString(field_148291_b.getServerMotd(), x + 32 + 3, y + 12, 8421504);

		if (Minecraft.gameSettings.hideServerAddress) {
			field_148293_a.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]),
					x + 32 + 3, y + 12 + 11, 3158064);
		} else {
			field_148293_a.fontRendererObj.drawString(field_148291_b.getServerIpPort(), x + 32 + 3, y + 12 + 11,
					3158064);
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control.
	 */
	@Override
	public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
			final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
		field_148292_c.selectServer(p_148278_1_);

		if (Minecraft.getSystemTime() - field_148290_d < 250L) {
			field_148292_c.connectToSelected();
		}

		field_148290_d = Minecraft.getSystemTime();
		return false;
	}

	@Override
	public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {}

	/**
	 * Fired when the mouse button is released. Arguments: index, x, y,
	 * mouseEvent, relativeX, relativeY
	 */
	@Override
	public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX,
			final int relativeY) {}

	public LanServerDetector.LanServer getLanServer() {
		return field_148291_b;
	}
}
