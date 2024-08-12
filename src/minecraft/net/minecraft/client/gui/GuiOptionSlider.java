package net.minecraft.client.gui;

import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MathHelper;

import java.awt.Color;

public class GuiOptionSlider extends GuiButton {

public static final int EaZy = 494;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float sliderValue;
	public boolean dragging;
	private final GameSettings.Options options;
	private int fad3;

	public GuiOptionSlider(final int p_i45016_1_, final int p_i45016_2_, final int p_i45016_3_,
			final GameSettings.Options p_i45016_4_) {
		this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0F, 1.0F);
	}

	public GuiOptionSlider(final int p_i45017_1_, final int p_i45017_2_, final int p_i45017_3_,
			final GameSettings.Options p_i45017_4_, final float p_i45017_5_, final float p_i45017_6_) {
		super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
		sliderValue = 1.0F;
		options = p_i45017_4_;

		sliderValue = p_i45017_4_.normalizeValue(Minecraft.gameSettings.getOptionFloatValue(p_i45017_4_));
		displayString = Minecraft.gameSettings.getKeyBinding(p_i45017_4_);
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	@Override
	protected int getHoverState(final boolean mouseOver) {
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	@Override
	protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
		if (visible) {
			if (dragging) {
				sliderValue = (float) (mouseX - (xPosition + 4)) / (float) (width - 8);
				sliderValue = MathHelper.clamp_float(sliderValue, 0.0F, 1.0F);
				final float var4 = options.denormalizeValue(sliderValue);
				Minecraft.gameSettings.setOptionFloatValue(options, var4);
				sliderValue = options.normalizeValue(var4);
				displayString = Minecraft.gameSettings.getKeyBinding(options);
			}

			Minecraft.getTextureManager().bindTexture(buttonTextures);
			if (Client.isHidden) {
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
				drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
				drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
			} else {
				if (Configs.buttonMode == 1) {
					final Color a = new Color(1, 1, 1, fad3);
					Gui.drawRect(xPosition + (int) (sliderValue * (width - 8)), yPosition,
							xPosition + (int) (sliderValue * (width - 8)) + 8, yPosition + height, a.getRGB());
				} else if (Configs.buttonMode == 0) {
					GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
					drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)), yPosition, 0, 66, 4, 20);
					drawTexturedModalRect(xPosition + (int) (sliderValue * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
				} else if (Configs.buttonMode == 2) {
					final Color a = new Color(0, 0, 0, fad3);
					Gui.drawRect(xPosition + (int) (sliderValue * (width - 8)), yPosition,
							xPosition + (int) (sliderValue * (width - 8)) + 8, yPosition + height, a.getRGB());
				}
				if (hovered && (Configs.buttonMode == 2 || Configs.buttonMode == 1)) {
					if (fad3 <= 40) {
						return;
					}
					if (fad3 != 70) {
						fad3 -= 5;
					}
				}
				if (!hovered && (Configs.buttonMode == 2 || Configs.buttonMode == 1)) {
					if (fad3 != 100) {
						fad3 += 5;
					}
				}
			}
		}
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	@Override
	public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			sliderValue = (float) (mouseX - (xPosition + 4)) / (float) (width - 8);
			sliderValue = MathHelper.clamp_float(sliderValue, 0.0F, 1.0F);
			Minecraft.gameSettings.setOptionFloatValue(options, options.denormalizeValue(sliderValue));
			displayString = Minecraft.gameSettings.getKeyBinding(options);
			dragging = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	@Override
	public void mouseReleased(final int mouseX, final int mouseY) {
		dragging = false;
	}
}
