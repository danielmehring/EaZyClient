package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.settings.GameSettings;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuiLanguage extends GuiScreen {

public static final int EaZy = 483;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected GuiScreen field_146453_a;
	private GuiLanguage.List field_146450_f;

	/** Reference to the GameSettings object. */
	private final GameSettings game_settings_3;
	private final LanguageManager field_146454_h;
	private GuiOptionButton field_146455_i;
	private GuiOptionButton field_146452_r;
	// private static final String __OBFID = "http://https://fuckuskid00000698";

	public GuiLanguage(final GuiScreen p_i1043_1_, final GameSettings p_i1043_2_, final LanguageManager p_i1043_3_) {
		field_146453_a = p_i1043_1_;
		game_settings_3 = p_i1043_2_;
		field_146454_h = p_i1043_3_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.add(field_146455_i = new GuiOptionButton(100, width / 2 - 155, height - 38,
				GameSettings.Options.FORCE_UNICODE_FONT,
				game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
		buttonList.add(field_146452_r = new GuiOptionButton(6, width / 2 - 155 + 160, height - 38,
				I18n.format("gui.done", new Object[0])));
		field_146450_f = new GuiLanguage.List(mc);
		field_146450_f.registerScrollButtons(7, 8);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_146450_f.func_178039_p();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
				case 5:
					break;

				case 6:
					mc.displayGuiScreen(field_146453_a);
					break;

				case 100:
					if (button instanceof GuiOptionButton) {
						game_settings_3.setOptionValue(((GuiOptionButton) button).returnEnumOptions(), 1);
						button.displayString = game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
						final ScaledResolution var2 = new ScaledResolution(mc, Minecraft.displayWidth,
								Minecraft.displayHeight);
						final int var3 = var2.getScaledWidth();
						final int var4 = var2.getScaledHeight();
						setWorldAndResolution(mc, var3, var4);
					}

					break;

				default:
					field_146450_f.actionPerformed(button);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		field_146450_f.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, I18n.format("options.language", new Object[0]), width / 2, 16, 16777215);
		drawCenteredString(fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")",
				width / 2, height - 56, 8421504);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	class List extends GuiSlot {
		private final java.util.List field_148176_l = Lists.newArrayList();
		private final Map field_148177_m = Maps.newHashMap();
		// private static final String __OBFID =
		// "http://https://fuckuskid00000699";

		public List(final Minecraft mcIn) {
			super(mcIn, GuiLanguage.this.width, GuiLanguage.this.height, 32, GuiLanguage.this.height - 65 + 4, 18);
			final Iterator var3 = field_146454_h.getLanguages().iterator();

			while (var3.hasNext()) {
				final Language var4 = (Language) var3.next();
				field_148177_m.put(var4.getLanguageCode(), var4);
				field_148176_l.add(var4.getLanguageCode());
			}
		}

		@Override
		protected int getSize() {
			return field_148176_l.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
			final Language var5 = (Language) field_148177_m.get(field_148176_l.get(slotIndex));
			field_146454_h.setCurrentLanguage(var5);
			game_settings_3.language = var5.getLanguageCode();
			mc.refreshResources();
			GuiLanguage.this.fontRendererObj
					.setUnicodeFlag(field_146454_h.isCurrentLocaleUnicode() || game_settings_3.forceUnicodeFont);
			GuiLanguage.this.fontRendererObj.setBidiFlag(field_146454_h.isCurrentLanguageBidirectional());
			field_146452_r.displayString = I18n.format("gui.done", new Object[0]);
			field_146455_i.displayString = game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
			game_settings_3.saveOptions();
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return ((String) field_148176_l.get(slotIndex))
					.equals(field_146454_h.getCurrentLanguage().getLanguageCode());
		}

		@Override
		protected int getContentHeight() {
			return getSize() * 18;
		}

		@Override
		protected void drawBackground() {
			drawDefaultBackground(mouseX, mouseY);
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			GuiLanguage.this.fontRendererObj.setBidiFlag(true);
			drawCenteredString(GuiLanguage.this.fontRendererObj,
					((Language) field_148177_m.get(field_148176_l.get(p_180791_1_))).toString(), width / 2,
					p_180791_3_ + 1, 16777215);
			GuiLanguage.this.fontRendererObj.setBidiFlag(field_146454_h.getCurrentLanguage().isBidirectional());
		}
	}
}
