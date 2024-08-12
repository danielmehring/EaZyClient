package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiMemoryErrorScreen extends GuiScreen {

	public static final int EaZy = 488;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	// private static final String __OBFID = "http://https://fuckuskid00000702";

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiOptionButton(1, width / 2 - 155, height / 4 + 120 + 12, 320, 20,
				I18n.format("menu.quit", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 1) {
			mc.shutdown();
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, "Out of memory!", width / 2, height / 4 - 60 + 20, 16777215);
		drawString(fontRendererObj, "Minecraft has run out of memory.", width / 2 - 140, height / 4 - 60 + 60 + 0,
				10526880);
		drawString(fontRendererObj, "This could be caused by a bug in the game or by the", width / 2 - 140,
				height / 4 - 60 + 60 + 18, 10526880);
		drawString(fontRendererObj, "Java Virtual Machine not being allocated enough", width / 2 - 140,
				height / 4 - 60 + 60 + 27, 10526880);
		drawString(fontRendererObj, "memory.", width / 2 - 140, height / 4 - 60 + 60 + 36, 10526880);
		drawString(fontRendererObj, "To prevent level corruption, the current game has quit.", width / 2 - 140,
				height / 4 - 60 + 60 + 54, 10526880);
		drawString(fontRendererObj, "We\'ve tried to free up enough memory to let you go back to", width / 2 - 140,
				height / 4 - 60 + 60 + 63, 10526880);
		drawString(fontRendererObj, "the main menu and back to playing, but this may not have worked.", width / 2 - 140,
				height / 4 - 60 + 60 + 72, 10526880);
		drawString(fontRendererObj, "Please restart the game if you see this message again.", width / 2 - 140,
				height / 4 - 60 + 60 + 81, 10526880);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
