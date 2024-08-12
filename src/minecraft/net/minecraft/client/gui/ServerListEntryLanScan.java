package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {

public static final int EaZy = 547;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Minecraft field_148288_a = Minecraft.getMinecraft();
	// private static final String __OBFID = "http://https://fuckuskid00000815";

	@Override
	public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight,
			final int mouseX, final int mouseY, final boolean isSelected) {
		final int var9 = y + slotHeight / 2 - field_148288_a.fontRendererObj.FONT_HEIGHT / 2;
		field_148288_a.fontRendererObj.drawString(I18n.format("lanServer.scanning", new Object[0]),
				Minecraft.currentScreen.width / 2 - field_148288_a.fontRendererObj
						.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2,
				var9, 16777215);
		String var10;

		switch ((int) (Minecraft.getSystemTime() / 300L % 4L)) {
			case 0:
			default:
				var10 = "O o o";
				break;

			case 1:
			case 3:
				var10 = "o O o";
				break;

			case 2:
				var10 = "o o O";
		}

		field_148288_a.fontRendererObj.drawString(var10,
				Minecraft.currentScreen.width / 2 - field_148288_a.fontRendererObj.getStringWidth(var10) / 2,
				var9 + field_148288_a.fontRendererObj.FONT_HEIGHT, 8421504);
	}

	@Override
	public void setSelected(final int p_178011_1_, final int p_178011_2_, final int p_178011_3_) {}

	/**
	 * Returns true if the mouse has been pressed on this control.
	 */
	@Override
	public boolean mousePressed(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_,
			final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
		return false;
	}

	/**
	 * Fired when the mouse button is released. Arguments: index, x, y,
	 * mouseEvent, relativeX, relativeY
	 */
	@Override
	public void mouseReleased(final int slotIndex, final int x, final int y, final int mouseEvent, final int relativeX,
			final int relativeY) {}
}
