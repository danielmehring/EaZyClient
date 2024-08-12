package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback {

public static final int EaZy = 1908;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen prevScreen;
	protected String title;
	private final GameSettings settings;
	private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.LAGOMETER,
			GameSettings.Options.PROFILER, GameSettings.Options.WEATHER, GameSettings.Options.TIME,
			GameSettings.Options.USE_FULLSCREEN, GameSettings.Options.FULLSCREEN_MODE, GameSettings.Options.SHOW_FPS,
			GameSettings.Options.AUTOSAVE_TICKS, GameSettings.Options.ANAGLYPH };
	private final TooltipManager tooltipManager = new TooltipManager(this);

	public GuiOtherSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
		prevScreen = guiscreen;
		settings = gamesettings;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		title = I18n.format("of.options.otherTitle", new Object[0]);
		buttonList.clear();

		for (int i = 0; i < enumOptions.length; ++i) {
			final GameSettings.Options enumoptions = enumOptions[i];
			final int x = width / 2 - 155 + i % 2 * 160;
			final int y = height / 6 + 21 * (i / 2) - 12;

			if (!enumoptions.getEnumFloat()) {
				buttonList.add(new GuiOptionButtonOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions,
						settings.getKeyBinding(enumoptions)));
			} else {
				buttonList.add(new GuiOptionSliderOF(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
			}
		}

		buttonList.add(new GuiButton(210, width / 2 - 100, height / 6 + 168 + 11 - 44,
				I18n.format("of.options.other.reset", new Object[0])));
		buttonList.add(
				new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton guibutton) {
		if (guibutton.enabled) {
			if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
				settings.setOptionValue(((GuiOptionButton) guibutton).returnEnumOptions(), 1);
				guibutton.displayString = settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
			}

			if (guibutton.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(prevScreen);
			}

			if (guibutton.id == 210) {
				Minecraft.gameSettings.saveOptions();
				final GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "",
						9999);
				mc.displayGuiScreen(guiyesno);
			}
		}
	}

	@Override
	public void confirmClicked(final boolean flag, final int i) {
		if (flag) {
			Minecraft.gameSettings.resetSettings();
		}

		mc.displayGuiScreen(this);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int x, final int y, final float f) {
		drawDefaultBackground(x, y);
		drawCenteredString(fontRendererObj, title, width / 2, 15, 16777215);
		super.drawScreen(x, y, f);
		tooltipManager.drawTooltips(x, y, buttonList);
	}
}
