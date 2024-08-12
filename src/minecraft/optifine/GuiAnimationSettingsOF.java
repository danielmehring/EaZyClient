package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiAnimationSettingsOF extends GuiScreen {

public static final int EaZy = 1903;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen prevScreen;
	protected String title;
	private final GameSettings settings;
	private static GameSettings.Options[] enumOptions = new GameSettings.Options[] {
			GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE,
			GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE,
			GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME,
			GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES,
			GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH,
			GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES,
			GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN,
			GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.FIREWORK_PARTICLES,
			GameSettings.Options.PARTICLES };

	public GuiAnimationSettingsOF(final GuiScreen guiscreen, final GameSettings gamesettings) {
		prevScreen = guiscreen;
		settings = gamesettings;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		title = I18n.format("of.options.animationsTitle", new Object[0]);
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

		buttonList.add(new GuiButton(210, width / 2 - 155, height / 6 + 168 + 11, 70, 20,
				Lang.get("of.options.animation.allOn")));
		buttonList.add(new GuiButton(211, width / 2 - 155 + 80, height / 6 + 168 + 11, 70, 20,
				Lang.get("of.options.animation.allOff")));
		buttonList.add(
				new GuiOptionButton(200, width / 2 + 5, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
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
				Minecraft.gameSettings.setAllAnimations(true);
			}

			if (guibutton.id == 211) {
				Minecraft.gameSettings.setAllAnimations(false);
			}

			final ScaledResolution sr = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
			setWorldAndResolution(mc, sr.getScaledWidth(), sr.getScaledHeight());
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
	}
}
