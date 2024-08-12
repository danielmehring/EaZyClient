package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.EaZy.client.main.Client;

public class GuiDisconnected extends GuiScreen {

	public static final int EaZy = 472;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final String reason;
	private final IChatComponent message;
	private List multilineMessage;
	public final GuiScreen parentScreen;
	private int field_175353_i;

	// private static final String __OBFID = "http://https://fuckuskid00000693";

	public GuiDisconnected(final GuiScreen p_i45020_1_, final String p_i45020_2_, final IChatComponent p_i45020_3_) {
		parentScreen = p_i45020_1_;
		reason = I18n.format(p_i45020_2_, new Object[0]);
		message = p_i45020_3_;
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		if (mc.thePlayer != null) {
			mc.displayGuiScreen(null);
			return;
		}
		buttonList.clear();
		multilineMessage = fontRendererObj.listFormattedStringToWidth(message.getFormattedText(), width - 50);
		field_175353_i = multilineMessage.size() * fontRendererObj.FONT_HEIGHT;
		buttonList.add(new GuiButton(0, width / 2 - 100,
				Math.min(height / 2 + field_175353_i / 2 + fontRendererObj.FONT_HEIGHT, height - 44),
				I18n.format("gui.toMenu", new Object[0])));
		buttonList.add(new GuiButton(1, width / 2 - 100,
				Math.min(height / 2 + field_175353_i / 2 + fontRendererObj.FONT_HEIGHT + 24, height - 22), "Reconnect"));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 0) {
			mc.displayGuiScreen(parentScreen);
		}
		if (button.id == 1) {
			try {
				mc.displayGuiScreen(new GuiConnecting(parentScreen, mc,
						new ServerData(Client.getServer(), Client.getServer())));
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, reason, width / 2,
				height / 2 - field_175353_i / 2 - fontRendererObj.FONT_HEIGHT * 2, 11184810);
		int var4 = height / 2 - field_175353_i / 2;

		if (multilineMessage != null) {
			for (final Iterator var5 = multilineMessage.iterator(); var5
					.hasNext(); var4 += fontRendererObj.FONT_HEIGHT) {
				final String var6 = (String) var5.next();
				drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
