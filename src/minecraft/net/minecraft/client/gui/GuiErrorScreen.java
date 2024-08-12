package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

import java.io.IOException;

public class GuiErrorScreen extends GuiScreen {

public static final int EaZy = 475;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_146313_a;
	private final String field_146312_f;
	// private static final String __OBFID = "http://https://fuckuskid00000696";

	public GuiErrorScreen(final String p_i46319_1_, final String p_i46319_2_) {
		field_146313_a = p_i46319_1_;
		field_146312_f = p_i46319_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		super.initGui();
		buttonList.add(new GuiButton(0, width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawGradientRect(0, 0, width, height, -12574688, -11530224);
		drawCenteredString(fontRendererObj, field_146313_a, width / 2, 90, 16777215);
		drawCenteredString(fontRendererObj, field_146312_f, width / 2, 110, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		mc.displayGuiScreen((GuiScreen) null);
	}
}
