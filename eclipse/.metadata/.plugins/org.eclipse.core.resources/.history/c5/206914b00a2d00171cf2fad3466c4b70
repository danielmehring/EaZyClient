package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiScreenOptionsSounds extends GuiScreen {

public static final int EaZy = 509;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_146505_f;

	/** Reference to the GameSettings object. */
	private final GameSettings game_settings_4;
	protected String field_146507_a = "Options";
	private String field_146508_h;
	// private static final String __OBFID = "http://https://fuckuskid00000716";

	public GuiScreenOptionsSounds(final GuiScreen p_i45025_1_, final GameSettings p_i45025_2_) {
		field_146505_f = p_i45025_1_;
		game_settings_4 = p_i45025_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		final byte var1 = 0;
		field_146507_a = I18n.format("options.sounds.title", new Object[0]);
		field_146508_h = I18n.format("options.off", new Object[0]);
		buttonList.add(new GuiScreenOptionsSounds.Button(SoundCategory.MASTER.getCategoryId(),
				width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
		int var6 = var1 + 2;
		final SoundCategory[] var2 = SoundCategory.values();
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final SoundCategory var5 = var2[var4];

			if (var5 != SoundCategory.MASTER) {
				buttonList.add(new GuiScreenOptionsSounds.Button(var5.getCategoryId(), width / 2 - 155 + var6 % 2 * 160,
						height / 6 - 12 + 24 * (var6 >> 1), var5, false));
				++var6;
			}
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(field_146505_f);
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
		GuiScreen.targetY = height / 6 - 20;
		GuiScreen.targetX2 = width / 2 + 160;
		GuiScreen.targetY2 = height / 6 + 196;

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	protected String getSoundVolume(final SoundCategory p_146504_1_) {
		final float var2 = game_settings_4.getSoundLevel(p_146504_1_);
		return var2 == 0.0F ? field_146508_h : (int) (var2 * 100.0F) + "%";
	}

	class Button extends GuiButton {
		private final SoundCategory field_146153_r;
		private final String field_146152_s;
		public float field_146156_o = 1.0F;
		public boolean field_146155_p;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000717";

		public Button(final int p_i45024_2_, final int p_i45024_3_, final int p_i45024_4_,
				final SoundCategory p_i45024_5_, final boolean p_i45024_6_) {
			super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
			field_146153_r = p_i45024_5_;
			field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
			displayString = field_146152_s + ": " + getSoundVolume(p_i45024_5_);
			field_146156_o = game_settings_4.getSoundLevel(p_i45024_5_);
		}

		@Override
		protected int getHoverState(final boolean mouseOver) {
			return 0;
		}

		@Override
		protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
			if (visible) {
				if (field_146155_p) {
					field_146156_o = (float) (mouseX - (xPosition + 4)) / (float) (width - 8);
					field_146156_o = MathHelper.clamp_float(field_146156_o, 0.0F, 1.0F);
					Minecraft.gameSettings.setSoundLevel(field_146153_r, field_146156_o);
					Minecraft.gameSettings.saveOptions();
					displayString = field_146152_s + ": " + getSoundVolume(field_146153_r);
				}

				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				drawTexturedModalRect(xPosition + (int) (field_146156_o * (width - 8)), yPosition, 0, 66, 4, 20);
				drawTexturedModalRect(xPosition + (int) (field_146156_o * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
			}
		}

		@Override
		public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
			if (super.mousePressed(mc, mouseX, mouseY)) {
				field_146156_o = (float) (mouseX - (xPosition + 4)) / (float) (width - 8);
				field_146156_o = MathHelper.clamp_float(field_146156_o, 0.0F, 1.0F);
				Minecraft.gameSettings.setSoundLevel(field_146153_r, field_146156_o);
				Minecraft.gameSettings.saveOptions();
				displayString = field_146152_s + ": " + getSoundVolume(field_146153_r);
				field_146155_p = true;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void playPressSound(final SoundHandler soundHandlerIn) {
		}

		@Override
		public void mouseReleased(final int mouseX, final int mouseY) {
			if (field_146155_p) {
				if (field_146153_r == SoundCategory.MASTER) {
				} else {
					game_settings_4.getSoundLevel(field_146153_r);
				}

				GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound(PositionedSoundRecord
						.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
			}

			field_146155_p = false;
		}
	}
}
