package net.minecraft.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen {

public static final int EaZy = 512;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_146303_a;
	private final ServerData field_146301_f;
	private GuiTextField field_146302_g;
	// private static final String __OBFID = "http://https://fuckuskid00000692";

	public GuiScreenServerList(final GuiScreen p_i1031_1_, final ServerData p_i1031_2_) {
		field_146303_a = p_i1031_1_;
		field_146301_f = p_i1031_2_;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		field_146302_g.updateCursorCounter();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12,
				I18n.format("selectServer.select", new Object[0])));
		buttonList.add(
				new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
		field_146302_g = new GuiTextField(2, fontRendererObj, width / 2 - 100, 116, 200, 20);
		field_146302_g.setMaxStringLength(128);
		field_146302_g.setFocused(true);
		field_146302_g.setText(Minecraft.gameSettings.lastServer);
		((GuiButton) buttonList.get(0)).enabled = field_146302_g.getText().length() > 0
				&& field_146302_g.getText().split(":").length > 0;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		Minecraft.gameSettings.lastServer = field_146302_g.getText();
		Minecraft.gameSettings.saveOptions();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 1) {
				field_146303_a.confirmClicked(false, 0);
			} else if (button.id == 0) {
				field_146301_f.serverIP = field_146302_g.getText();
				field_146303_a.confirmClicked(true, 0);
			}
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (field_146302_g.textboxKeyTyped(typedChar, keyCode)) {
			((GuiButton) buttonList.get(0)).enabled = field_146302_g.getText().length() > 0
					&& field_146302_g.getText().split(":").length > 0;
		} else if (keyCode == 28 || keyCode == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		field_146302_g.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("selectServer.direct", new Object[0]), width / 2, 20, 16777215);
		drawString(fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 100, 10526880);
		field_146302_g.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
