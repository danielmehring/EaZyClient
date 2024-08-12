package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiDetailSettingsOF extends GuiScreen {

public static final int EaZy = 1904;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen prevScreen;
	protected String title;
	private final GameSettings settings;
	private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.CLOUDS,
			GameSettings.Options.CLOUD_HEIGHT, GameSettings.Options.TREES, GameSettings.Options.RAIN,
			GameSettings.Options.SKY, GameSettings.Options.STARS, GameSettings.Options.SUN_MOON,
			GameSettings.Options.SHOW_CAPES, GameSettings.Options.TRANSLUCENT_BLOCKS,
			GameSettings.Options.HELD_ITEM_TOOLTIPS, GameSettings.Options.DROPPED_ITEMS, GameSettings.Options.VIGNETTE,
			GameSettings.Options.DYNAMIC_FOV };
	private final TooltipManager tooltipManager = new TooltipManager(this);

	public GuiDetailSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
		prevScreen = guiscreen;
		settings = gamesettings;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		title = I18n.format("of.options.detailsTitle", new Object[0]);
		buttonList.clear();

		for (int i = 0; i < enumOptions.length; ++i) {
			final GameSettings.Options opt = enumOptions[i];
			final int x = width / 2 - 155 + i % 2 * 160;
			final int y = height / 6 + 21 * (i / 2) - 12;

			if (!opt.getEnumFloat()) {
				buttonList.add(new GuiOptionButtonOF(opt.returnEnumOrdinal(), x, y, opt, settings.getKeyBinding(opt)));
			} else {
				buttonList.add(new GuiOptionSliderOF(opt.returnEnumOrdinal(), x, y, opt));
			}
		}

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
		}
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
