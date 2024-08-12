package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen {

public static final int EaZy = 499;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_146585_a;
	private GuiTextField field_146583_f;
	private final String field_146584_g;
	// private static final String __OBFID = "http://https://fuckuskid00000709";

	public GuiRenameWorld(final GuiScreen p_i46317_1_, final String p_i46317_2_) {
		field_146585_a = p_i46317_1_;
		field_146584_g = p_i46317_2_;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		field_146583_f.updateCursorCounter();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12,
				I18n.format("selectWorld.renameButton", new Object[0])));
		buttonList.add(
				new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
		final ISaveFormat var1 = mc.getSaveLoader();
		final WorldInfo var2 = var1.getWorldInfo(field_146584_g);
		final String var3 = var2.getWorldName();
		field_146583_f = new GuiTextField(2, fontRendererObj, width / 2 - 100, 60, 200, 20);
		field_146583_f.setFocused(true);
		field_146583_f.setText(var3);
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
			if (button.id == 1) {
				mc.displayGuiScreen(field_146585_a);
			} else if (button.id == 0) {
				final ISaveFormat var2 = mc.getSaveLoader();
				var2.renameWorld(field_146584_g, field_146583_f.getText().trim());
				mc.displayGuiScreen(field_146585_a);
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
		field_146583_f.textboxKeyTyped(typedChar, keyCode);
		((GuiButton) buttonList.get(0)).enabled = field_146583_f.getText().trim().length() > 0;

		if (keyCode == 28 || keyCode == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), width / 2, 20,
				16777215);
		drawString(fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, 10526880);
		field_146583_f.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
