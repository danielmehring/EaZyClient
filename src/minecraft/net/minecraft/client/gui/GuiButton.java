package net.minecraft.client.gui;

import java.awt.Color;

import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {

	public static final int EaZy = 460;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

	/** Button width in pixels */
	protected int width;

	/** Button height in pixels */
	protected int height;

	/** The x position of this control. */
	public int xPosition;

	/** The y position of this control. */
	public int yPosition;

	/** The string displayed on this control. */
	public String displayString;
	public int id;

	/** True if this control is enabled, false to disable. */
	public boolean enabled;

	/** Hides the button completely if false. */
	public boolean visible;
	protected boolean hovered;
	private int fad3;

	public GuiButton(final int buttonId, final int x, final int y, final String buttonText) {
		this(buttonId, x, y, 200, 20, buttonText);
	}

	public GuiButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn,
			final String buttonText) {
		width = 200;
		height = 20;
		enabled = true;
		visible = true;
		id = buttonId;
		xPosition = x;
		yPosition = y;
		width = widthIn;
		height = heightIn;
		displayString = buttonText;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	protected int getHoverState(final boolean mouseOver) {
		byte var2 = 1;

		if (!enabled) {
			var2 = 0;
		} else if (mouseOver) {
			var2 = 2;
		}

		return var2;
	}

	/**
	 * Draws this button to the screen.
	 */
	public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
		if (visible && !displayString.equals("HIDETHISBUTTON")) {
			final FontRenderer var4 = mc.fontRendererObj;
			Minecraft.getTextureManager().bindTexture(buttonTextures);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
					&& mouseY < yPosition + height;
			final int var5 = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			if (Client.isHidden) {
				drawTexturedModalRect(xPosition, yPosition, 0, 46 + var5 * 20, width / 2, height);
				drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + var5 * 20, width / 2,
						height);
			} else {
				if (Configs.buttonMode == 1) {
					// // TODO: EaZy Buttons xD
					// RenderUtils.drawRect(this.xPosition + 1, this.yPosition +
					// 1,
					// this.xPosition + this.width - 1,
					// this.yPosition + this.height - 1,
					// ColorUtils.transparency(-1,
					// 0.25f));
					final Color a = new Color(255, 255, 255, fad3);
					Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, a.getRGB());
					drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2,
							0xffffffff);
				} else if (Configs.buttonMode == 0) {
					drawTexturedModalRect(xPosition, yPosition, 0, 46 + var5 * 20, width / 2, height);
					drawTexturedModalRect(xPosition + width / 2, yPosition, 200 - width / 2, 46 + var5 * 20, width / 2,
							height);
				} else if (Configs.buttonMode == 2) {
					final Color a = new Color(0, 0, 0, fad3);
					Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, a.getRGB());
					drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2,
							0xffffffff);
				}
			}
			mouseDragged(mc, mouseX, mouseY);
			int var6 = 14737632;

			if (!enabled) {
				if (Client.isHidden) {
					var6 = 10526880;
				} else {
					if (Configs.buttonMode == 1) {
						// RenderUtils.drawRect(this.xPosition + 1,
						// this.yPosition +
						// 1, this.xPosition + this.width - 1,
						// this.yPosition + this.height - 1,
						// ColorUtils.transparency(0, 0.5f));
						var6 = 10526880;
					} else if (Configs.buttonMode == 0) {
						var6 = 10526880;
					} else if (Configs.buttonMode == 2) {
						var6 = 10526880;
					}
				}
			} else if (hovered) {
				// if (Configs.buttonMode == 1) {
				// RenderUtils.drawRect(this.xPosition + 1, this.yPosition + 1,
				// this.xPosition + this.width - 1,
				// this.yPosition + this.height - 1, ColorUtils.transparency(-1,
				// 0.3f));
				// } else
				if (Client.isHidden) {
					var6 = 16777120;
				} else {
					if (Configs.buttonMode == 0) {
						var6 = 16777120;
					} else if (Configs.buttonMode == 2 || Configs.buttonMode == 1) {
						if (fad3 <= 40) {
							return;
						}
						if (fad3 != 70) {
							fad3 -= 5;
						}
					}
				}
			}
			if (!hovered && (Configs.buttonMode == 2 || Configs.buttonMode == 1) && !Client.isHidden) {
				if (fad3 != 100) {
					fad3 += 5;
				}
			}

			drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, var6);
		}

	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(final int mouseX, final int mouseY) {}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
		return enabled && visible && mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
				&& mouseY < yPosition + height;
	}

	/**
	 * Whether the mouse cursor is currently over the button.
	 */
	public boolean isMouseOver() {
		return hovered;
	}

	public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {}

	public void playPressSound(final SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(
				PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
	}

	public int getButtonWidth() {
		return width;
	}

	public int getButtonHeight() {
		return height;
	}

	public void func_175211_a(final int p_175211_1_) {
		width = p_175211_1_;
	}
}
