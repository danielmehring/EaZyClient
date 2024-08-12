package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.gui.GuiEaZySettings;
import me.EaZy.client.gui.GuiInvisButton;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback {

public static final int EaZy = 493;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[] {
			GameSettings.Options.FOV };
	private final GuiScreen field_146441_g;

	/** Reference to the GameSettings object. */
	private final GameSettings game_settings_1;
	private GuiButton field_175357_i;
	private GuiLockIconButton field_175356_r;
	protected String field_146442_a = "Options";

	public GuiOptions(final GuiScreen p_i1046_1_, final GameSettings p_i1046_2_) {
		field_146441_g = p_i1046_1_;
		game_settings_1 = p_i1046_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		int var1 = 0;
		field_146442_a = I18n.format("options.title", new Object[0]);
		final GameSettings.Options[] var2 = field_146440_f;
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final GameSettings.Options var5 = var2[var4];

			if (var5.getEnumFloat()) {
				buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), width / 2 - 155 + var1 % 2 * 160,
						height / 6 + 17, var5));
			} else {
				final GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(),
						width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), var5,
						game_settings_1.getKeyBinding(var5));
				buttonList.add(var6);
			}

			++var1;
		}

		if (Minecraft.theWorld != null) {
			final EnumDifficulty var7 = Minecraft.theWorld.getDifficulty();
			field_175357_i = new GuiButton(108, width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1),
					150, 20, func_175355_a(var7));
			buttonList.add(field_175357_i);

			if (mc.isSingleplayer() && !Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
				field_175357_i.func_175211_a(field_175357_i.getButtonWidth() - 20);
				field_175356_r = new GuiLockIconButton(109, field_175357_i.xPosition + field_175357_i.getButtonWidth(),
						field_175357_i.yPosition);
				buttonList.add(field_175356_r);
				field_175356_r.func_175229_b(Minecraft.theWorld.getWorldInfo().isDifficultyLocked());
				field_175356_r.enabled = !field_175356_r.func_175230_c();
				field_175357_i.enabled = !field_175356_r.func_175230_c();
			} else {
				field_175357_i.enabled = false;
			}
		}

		buttonList.add(new GuiButton(110, width / 2 - 155, height / 6 + 48 - 6, 150, 20,
				I18n.format("options.skinCustomisation", new Object[0])));
		buttonList.add(new GuiButton(8675309, width / 2 + 5, height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
			@Override
			public void playPressSound(final SoundHandler soundHandlerIn) {
				final SoundEventAccessorComposite var2 = soundHandlerIn
						.getRandomSoundFromCategories(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS,
								SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });

				if (var2 != null) {
					soundHandlerIn.playSound(
							PositionedSoundRecord.createPositionedSoundRecord(var2.getSoundEventLocation(), 0.5F));
				}
			}
		});
		buttonList.add(new GuiButton(106, width / 2 - 155, height / 6 + 72 - 6, 150, 20,
				I18n.format("options.sounds", new Object[0])));
		buttonList.add(new GuiButton(107, width / 2 + 5, height / 6 + 72 - 6, 150, 20,
				I18n.format("options.stream", new Object[0])));
		buttonList.add(new GuiButton(101, width / 2 - 155, height / 6 + 96 - 6, 150, 20,
				I18n.format("options.video", new Object[0])));
		buttonList.add(new GuiButton(100, width / 2 + 5, height / 6 + 96 - 6, 150, 20,
				I18n.format("options.controls", new Object[0])));
		buttonList.add(new GuiButton(102, width / 2 - 155, height / 6 + 120 - 6, 150, 20,
				I18n.format("options.language", new Object[0])));
		buttonList.add(new GuiButton(103, width / 2 + 5, height / 6 + 120 - 6, 150, 20,
				I18n.format("options.multiplayer.title", new Object[0])));
		buttonList.add(new GuiButton(105, width / 2 - 155, height / 6 + 144 - 6, 150, 20,
				I18n.format("options.resourcepack", new Object[0])));
		buttonList.add(new GuiButton(104, width / 2 + 5, height / 6 + 144 - 6, 150, 20,
				I18n.format("options.snooper.view", new Object[0])));
		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
		if (!Client.isHidden) {
			buttonList.add(new GuiButton(123456789, width / 2 + 5, height / 6 + 17, 150, 20, "EaZy Options"));
		}
		if (Client.isHidden) {
			buttonList.add(new GuiInvisButton(123456781, width - 50, 0, 50, 20, ""));
		} else {
			buttonList.add(new GuiButton(123456782, width - 50, 0, 50, 20, "Hide EaZy"));
		}
	}

	public String func_175355_a(final EnumDifficulty p_175355_1_) {
		final ChatComponentText var2 = new ChatComponentText("");
		var2.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
		var2.appendText(": ");
		var2.appendSibling(new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
		return var2.getFormattedText();
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		mc.displayGuiScreen(this);

		if (id == 109 && result && Minecraft.theWorld != null) {
			Minecraft.theWorld.getWorldInfo().setDifficultyLocked(true);
			field_175356_r.func_175229_b(true);
			field_175356_r.enabled = false;
			field_175357_i.enabled = false;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 123456789) {
				if (!Client.isHidden) {
					mc.displayGuiScreen(new GuiEaZySettings(this));
				}
			}
			if (button.id == 123456781) {
				Client.show();
			}
			if (button.id == 123456782) {
				Client.hide();
			}
			if (button.id < 100 && button instanceof GuiOptionButton) {
				final GameSettings.Options var2 = ((GuiOptionButton) button).returnEnumOptions();
				game_settings_1.setOptionValue(var2, 1);
				button.displayString = game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
			}

			if (button.id == 108) {
				Minecraft.theWorld.getWorldInfo().setDifficulty(
						EnumDifficulty.getDifficultyEnum(Minecraft.theWorld.getDifficulty().getDifficultyId() + 1));
				field_175357_i.displayString = func_175355_a(Minecraft.theWorld.getDifficulty());
			}

			if (button.id == 109) {
				mc.displayGuiScreen(
						new GuiYesNo(this,
								new ChatComponentTranslation("difficulty.lock.title", new Object[0]).getFormattedText(),
								new ChatComponentTranslation("difficulty.lock.question",
										new Object[] { new ChatComponentTranslation(Minecraft.theWorld.getWorldInfo()
												.getDifficulty().getDifficultyResourceKey(), new Object[0]) })
														.getFormattedText(),
								109));
			}

			if (button.id == 110) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiCustomizeSkin(this));
			}

			if (button.id == 8675309) {
				Minecraft.entityRenderer.activateNextShader();
			}

			if (button.id == 101) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiVideoSettings(this, game_settings_1));
			}

			if (button.id == 100) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiControls(this, game_settings_1));
			}

			if (button.id == 102) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiLanguage(this, game_settings_1, mc.getLanguageManager()));
			}

			if (button.id == 103) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new ScreenChatOptions(this, game_settings_1));
			}

			if (button.id == 104) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiSnooper(this, game_settings_1));
			}

			if (button.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(field_146441_g);
			}

			if (button.id == 105) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiScreenResourcePacks(this));
			}

			if (button.id == 106) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(new GuiScreenOptionsSounds(this, game_settings_1));
			}

			if (button.id == 107) {
				Minecraft.gameSettings.saveOptions();
				final IStream var3 = mc.getTwitchStream();

				if (var3.func_152936_l() && var3.func_152928_D()) {
					mc.displayGuiScreen(new GuiStreamOptions(this, game_settings_1));
				} else {
					GuiStreamUnavailable.func_152321_a(this);
				}
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		// width / 2 - 155

		drawDefaultBackground(mouseX, mouseY);

		GuiScreen.targetX = width / 2 - 159;
		GuiScreen.targetY = Minecraft.thePlayer == null ? height / 6 - 0 : height / 6 - (0 + 30);
		GuiScreen.targetX2 = width / 2 + 159;
		GuiScreen.targetY2 = height / 6 + 200;

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
