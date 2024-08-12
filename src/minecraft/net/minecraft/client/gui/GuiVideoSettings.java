package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import optifine.Config;
import optifine.GuiAnimationSettingsOF;
import optifine.GuiDetailSettingsOF;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.GuiOtherSettingsOF;
import optifine.GuiPerformanceSettingsOF;
import optifine.GuiQualitySettingsOF;
import optifine.Lang;
import optifine.TooltipManager;
import shadersmod.client.GuiShaders;

public class GuiVideoSettings extends GuiScreen {

public static final int EaZy = 526;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen parentGuiScreen;
	protected String screenTitle = "Video Settings";
	private final GameSettings guiGameSettings;

	/** An array of all of GameSettings.Options's video options. */
	private static GameSettings.Options[] videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS,
			GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION,
			GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING,
			GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA,
			GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START };
	private final TooltipManager tooltipManager = new TooltipManager(this);

	public GuiVideoSettings(final GuiScreen par1GuiScreen, final GameSettings par2GameSettings) {
		parentGuiScreen = par1GuiScreen;
		guiGameSettings = par2GameSettings;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		screenTitle = I18n.format("options.videoTitle", new Object[0]);
		buttonList.clear();
		int y;

		for (y = 0; y < videoOptions.length; ++y) {
			final GameSettings.Options x = videoOptions[y];

			if (x != null) {
				final int x1 = width / 2 - 155 + y % 2 * 160;
				final int y1 = height / 6 + 21 * (y / 2) - 12;

				if (x.getEnumFloat()) {
					buttonList.add(new GuiOptionSliderOF(x.returnEnumOrdinal(), x1, y1, x));
				} else {
					buttonList.add(
							new GuiOptionButtonOF(x.returnEnumOrdinal(), x1, y1, x, guiGameSettings.getKeyBinding(x)));
				}
			}
		}

		y = height / 6 + 21 * (videoOptions.length / 2) - 12;
		int var6 = width / 2 - 155 + 0;
		buttonList.add(new GuiOptionButton(231, var6, y, Lang.get("of.options.shaders")));
		var6 = width / 2 - 155 + 160;
		buttonList.add(new GuiOptionButton(202, var6, y, Lang.get("of.options.quality")));
		y += 21;
		var6 = width / 2 - 155 + 0;
		buttonList.add(new GuiOptionButton(201, var6, y, Lang.get("of.options.details")));
		var6 = width / 2 - 155 + 160;
		buttonList.add(new GuiOptionButton(212, var6, y, Lang.get("of.options.performance")));
		y += 21;
		var6 = width / 2 - 155 + 0;
		buttonList.add(new GuiOptionButton(211, var6, y, Lang.get("of.options.animations")));
		var6 = width / 2 - 155 + 160;
		buttonList.add(new GuiOptionButton(222, var6, y, Lang.get("of.options.other")));
		y += 21;
		buttonList.add(
				new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			final int guiScale = guiGameSettings.guiScale;

			if (button.id < 200 && button instanceof GuiOptionButton) {
				guiGameSettings.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
				button.displayString = guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
			}

			if (button.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(parentGuiScreen);
			}

			if (guiGameSettings.guiScale != guiScale) {
				final ScaledResolution scr = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
				final int var4 = scr.getScaledWidth();
				final int var5 = scr.getScaledHeight();
				setWorldAndResolution(mc, var4, var5);
			}

			if (button.id == 201) {
				Minecraft.gameSettings.saveOptions();
				final GuiDetailSettingsOF scr1 = new GuiDetailSettingsOF(this, guiGameSettings);
				mc.displayGuiScreen(scr1);
			}

			if (button.id == 202) {
				Minecraft.gameSettings.saveOptions();
				final GuiQualitySettingsOF scr2 = new GuiQualitySettingsOF(this, guiGameSettings);
				mc.displayGuiScreen(scr2);
			}

			if (button.id == 211) {
				Minecraft.gameSettings.saveOptions();
				final GuiAnimationSettingsOF scr3 = new GuiAnimationSettingsOF(this, guiGameSettings);
				mc.displayGuiScreen(scr3);
			}

			if (button.id == 212) {
				Minecraft.gameSettings.saveOptions();
				final GuiPerformanceSettingsOF scr4 = new GuiPerformanceSettingsOF(this, guiGameSettings);
				mc.displayGuiScreen(scr4);
			}

			if (button.id == 222) {
				Minecraft.gameSettings.saveOptions();
				final GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, guiGameSettings);
				mc.displayGuiScreen(scr5);
			}

			if (button.id == 231) {
				if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
					Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
					return;
				}

				if (Config.isAnisotropicFiltering()) {
					Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
					return;
				}

				if (Config.isFastRender()) {
					Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
					return;
				}

				Minecraft.gameSettings.saveOptions();
				final GuiShaders scr6 = new GuiShaders(this, guiGameSettings);
				mc.displayGuiScreen(scr6);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int x, final int y, final float z) {
		drawDefaultBackground(x, y);

		String ver = Config.getVersion();
		final String ed = "HD_U";

		if (ed.equals("HD")) {
			ver = "OptiFine HD H6";
		}

		if (ed.equals("HD_U")) {
			ver = "OptiFine HD H6 Ultra";
		}

		if (ed.equals("L")) {
			ver = "OptiFine H6 Light";
		}

		drawString(fontRendererObj, ver, 2, height - 10, 8421504);
		final String verMc = Client.isHidden ? "Minecraft 1.8" : Client.version;
		final int lenMc = fontRendererObj.getStringWidth(verMc);
		drawString(fontRendererObj, verMc, width - lenMc - 2, height - 10, 8421504);

		// height / 6 + 21 * (videoOptions.length / 2) - 12

		GuiScreen.targetX = width / 2 - 160;
		GuiScreen.targetY = height / 6 + 21 * (videoOptions.length / 2) - 12 - 188 + 54;
		GuiScreen.targetX2 = width / 2 + 160;
		GuiScreen.targetY2 = height / 6 + 21 * (videoOptions.length / 2) - 12 + 40 + 54;

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}

		super.drawScreen(x, y, z);
		tooltipManager.drawTooltips(x, y, buttonList);
	}

	public static int getButtonWidth(final GuiButton btn) {
		return btn.width;
	}

	public static int getButtonHeight(final GuiButton btn) {
		return btn.height;
	}

	public static void drawGradientRect(final GuiScreen guiScreen, final int left, final int top, final int right,
			final int bottom, final int startColor, final int endColor) {
		guiScreen.drawGradientRect(left, top, right, bottom, startColor, endColor);
	}
}
