package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class ScreenChatOptions extends GuiScreen {

public static final int EaZy = 545;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[] {
			GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS,
			GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE,
			GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED,
			GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
	private final GuiScreen field_146396_g;
	private final GameSettings game_settings;
	private String field_146401_i;
	private String field_146398_r;

	public ScreenChatOptions(final GuiScreen p_i1023_1_, final GameSettings p_i1023_2_) {
		field_146396_g = p_i1023_1_;
		game_settings = p_i1023_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		int var1 = 0;
		field_146401_i = I18n.format("options.chat.title", new Object[0]);
		field_146398_r = I18n.format("options.multiplayer.title", new Object[0]);
		final GameSettings.Options[] var2 = field_146399_a;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final GameSettings.Options var5 = var2[var4];

			if (var5.getEnumFloat()) {
				buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160,
						height / 6 + 24 * (var1 >> 1), var5));
			} else {
				buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160,
						height / 6 + 24 * (var1 >> 1), var5, game_settings.getKeyBinding(var5)));
			}

			++var1;
		}

		if (var1 % 2 == 1) {
			++var1;
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 120, I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id < 100 && button instanceof GuiOptionButton) {
				game_settings.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
				button.displayString = game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
			}

			if (button.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(field_146396_g);
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

		GuiScreen.targetX = width / 2 - 160;
		GuiScreen.targetY = height / 6 - 10;
		GuiScreen.targetX2 = width / 2 + 160;
		GuiScreen.targetY2 = height / 6 + 150;

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
