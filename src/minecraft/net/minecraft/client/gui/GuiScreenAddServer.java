package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen {

public static final int EaZy = 505;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen parentScreen;
	private final ServerData serverData;
	private GuiTextField serverIPField;
	private GuiTextField serverNameField;
	private GuiButton serverResourcePacks;
	// private static final String __OBFID = "http://https://fuckuskid00000695";

	public GuiScreenAddServer(final GuiScreen p_i1033_1_, final ServerData p_i1033_2_) {
		parentScreen = p_i1033_1_;
		serverData = p_i1033_2_;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		serverNameField.updateCursorCounter();
		serverIPField.updateCursorCounter();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(
				new GuiButton(0, width / 2 - 100, height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
		buttonList.add(
				new GuiButton(1, width / 2 - 100, height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
		buttonList.add(serverResourcePacks = new GuiButton(2, width / 2 - 100, height / 4 + 72,
				I18n.format("addServer.resourcePack", new Object[0]) + ": "
						+ serverData.getResourceMode().getMotd().getFormattedText()));
		serverNameField = new GuiTextField(0, fontRendererObj, width / 2 - 100, 66, 200, 20);
		serverNameField.setFocused(true);
		serverNameField.setText(serverData.serverName);
		serverIPField = new GuiTextField(1, fontRendererObj, width / 2 - 100, 106, 200, 20);
		serverIPField.setMaxStringLength(128);
		serverIPField.setText(serverData.serverIP);
		((GuiButton) buttonList.get(0)).enabled = serverIPField.getText().length() > 0
				&& serverIPField.getText().split(":").length > 0 && serverNameField.getText().length() > 0;
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				serverData.setResourceMode(
						ServerData.ServerResourceMode.values()[(serverData.getResourceMode().ordinal() + 1)
								% ServerData.ServerResourceMode.values().length]);
				serverResourcePacks.displayString = I18n.format("addServer.resourcePack", new Object[0]) + ": "
						+ serverData.getResourceMode().getMotd().getFormattedText();
			} else if (button.id == 1) {
				parentScreen.confirmClicked(false, 0);
			} else if (button.id == 0) {
				serverData.serverName = serverNameField.getText();
				serverData.serverIP = serverIPField.getText();
				parentScreen.confirmClicked(true, 0);
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
		serverNameField.textboxKeyTyped(typedChar, keyCode);
		serverIPField.textboxKeyTyped(typedChar, keyCode);

		if (keyCode == 15) {
			serverNameField.setFocused(!serverNameField.isFocused());
			serverIPField.setFocused(!serverIPField.isFocused());
		}

		if (keyCode == 28 || keyCode == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}

		((GuiButton) buttonList.get(0)).enabled = serverIPField.getText().length() > 0
				&& serverIPField.getText().split(":").length > 0 && serverNameField.getText().length() > 0;
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
		serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("addServer.title", new Object[0]), width / 2, 17, 16777215);
		drawString(fontRendererObj, I18n.format("addServer.enterName", new Object[0]), width / 2 - 100, 53, 10526880);
		drawString(fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 94, 10526880);
		serverNameField.drawTextBox();
		serverIPField.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
