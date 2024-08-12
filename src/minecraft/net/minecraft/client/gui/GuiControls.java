package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import java.io.IOException;

public class GuiControls extends GuiScreen {

public static final int EaZy = 467;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final GameSettings.Options[] optionsArr = new GameSettings.Options[] {
			GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };

	/**
	 * A reference to the screen object that created this. Used for navigating
	 * between screens.
	 */
	private final GuiScreen parentScreen;
	protected String screenTitle = "Controls";

	/** Reference to the GameSettings object. */
	private final GameSettings options;

	/** The ID of the button that has been pressed. */
	public KeyBinding buttonId = null;
	public long time;
	private GuiKeyBindingList keyBindingList;
	private GuiButton buttonReset;
	// private static final String __OBFID = "http://https://fuckuskid00000736";

	public GuiControls(final GuiScreen p_i1027_1_, final GameSettings p_i1027_2_) {
		parentScreen = p_i1027_1_;
		options = p_i1027_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		keyBindingList = new GuiKeyBindingList(this, mc);
		buttonList
				.add(new GuiButton(200, width / 2 - 155, height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(buttonReset = new GuiButton(201, width / 2 - 155 + 160, height - 29, 150, 20,
				I18n.format("controls.resetAll", new Object[0])));
		screenTitle = I18n.format("controls.title", new Object[0]);
		int var1 = 0;
		final GameSettings.Options[] var2 = optionsArr;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final GameSettings.Options var5 = var2[var4];

			if (var5.getEnumFloat()) {
				buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160,
						18 + 24 * (var1 >> 1), var5));
			} else {
				buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160,
						18 + 24 * (var1 >> 1), var5, options.getKeyBinding(var5)));
			}

			++var1;
		}
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		keyBindingList.func_178039_p();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 200) {
			mc.displayGuiScreen(parentScreen);
		} else if (button.id == 201) {
			final KeyBinding[] var2 = Minecraft.gameSettings.keyBindings;
			final int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				final KeyBinding var5 = var2[var4];
				var5.setKeyCode(var5.getKeyCodeDefault());
			}

			KeyBinding.resetKeyBindingArrayAndHash();
		} else if (button.id < 100 && button instanceof GuiOptionButton) {
			options.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
			button.displayString = options.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		if (buttonId != null) {
			options.setOptionKeyBinding(buttonId, -100 + mouseButton);
			buttonId = null;
			KeyBinding.resetKeyBindingArrayAndHash();
		} else if (mouseButton != 0 || !keyBindingList.func_148179_a(mouseX, mouseY, mouseButton)) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		if (state != 0 || !keyBindingList.func_148181_b(mouseX, mouseY, state)) {
			super.mouseReleased(mouseX, mouseY, state);
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (buttonId != null) {
			if (keyCode == 1) {
				options.setOptionKeyBinding(buttonId, 0);
			} else if (keyCode != 0) {
				options.setOptionKeyBinding(buttonId, keyCode);
			} else if (typedChar > 0) {
				options.setOptionKeyBinding(buttonId, typedChar + 256);
			}

			buttonId = null;
			time = Minecraft.getSystemTime();
			KeyBinding.resetKeyBindingArrayAndHash();
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, screenTitle, width / 2, 8, 16777215);
		boolean var4 = true;
		final KeyBinding[] var5 = options.keyBindings;
		final int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			final KeyBinding var8 = var5[var7];

			if (var8.getKeyCode() != var8.getKeyCodeDefault()) {
				var4 = false;
				break;
			}
		}

		buttonReset.enabled = !var4;
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
