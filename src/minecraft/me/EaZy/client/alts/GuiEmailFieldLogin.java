package me.EaZy.client.alts;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.MathHelper;

public class GuiEmailFieldLogin extends Gui {

	public static final int EaZy = 29;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private final int field_175208_g;
	private final FontRenderer fontRendererInstance;
	public int xPosition;
	public int yPosition;

	/**
	 * The width of this text field.
	 */
	public int width;
	public int height;

	/**
	 * Has the current text being edited on the textbox.
	 */
	private String text = "";
	private int maxStringLength = 32;
	private int cursorCounter;
	private boolean enableBackgroundDrawing = true;

	/**
	 * if true the textbox can lose focus by clicking elsewhere on the screen
	 */
	private boolean canLoseFocus = true;

	/**
	 * If this value is true along with isEnabled, keyTyped will process the
	 * keys.
	 */
	private boolean isFocused;

	/**
	 * If this value is true along with isFocused, keyTyped will process the
	 * keys.
	 */
	public boolean isEnabled = true;

	/**
	 * The current character index that should be used as start of the rendered
	 * text.
	 */
	private int lineScrollOffset;
	private int cursorPosition;

	/**
	 * other selection position, maybe the same as the cursor
	 */
	private int selectionEnd;
	/**
	 * True if this textbox is visible
	 */
	public boolean visible = true;
	private GuiPageButtonList.GuiResponder field_175210_x;
	private Predicate field_175209_y = Predicates.alwaysTrue();

	public GuiEmailFieldLogin(final int p_i45542_1_, final FontRenderer p_i45542_2_, final int p_i45542_3_,
			final int p_i45542_4_, final int p_i45542_5_, final int p_i45542_6_) {
		field_175208_g = p_i45542_1_;
		fontRendererInstance = p_i45542_2_;
		xPosition = p_i45542_3_;
		yPosition = p_i45542_4_;
		width = p_i45542_5_;
		height = p_i45542_6_;
	}

	public void func_175207_a(final GuiPageButtonList.GuiResponder p_175207_1_) {
		field_175210_x = p_175207_1_;
	}

	/**
	 * Increments the cursor counter
	 */
	public void updateCursorCounter() {
		++cursorCounter;
	}

	/**
	 * Sets the text of the textbox
	 */
	public void setText(final String p_146180_1_) {
		if (field_175209_y.apply(p_146180_1_)) {
			if (p_146180_1_.length() > maxStringLength) {
				text = p_146180_1_.substring(0, maxStringLength);
			} else {
				text = p_146180_1_;
			}

			setCursorPositionEnd();
		}
	}

	/**
	 * Returns the contents of the textbox
	 */
	public String getText() {
		return text;
	}

	/**
	 * returns the text between the cursor and selectionEnd
	 */
	public String getSelectedText() {
		final int var1 = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
		final int var2 = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
		return text.substring(var1, var2);
	}

	public void func_175205_a(final Predicate p_175205_1_) {
		field_175209_y = p_175205_1_;
	}

	/**
	 * replaces selected text, or inserts text at the position on the cursor
	 */
	private void writeText(final String p_146191_1_) {
		String var2 = "";
		final String var3 = EmailAllowedCharacters.filterAllowedCharacters(p_146191_1_);
		final int var4 = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
		final int var5 = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
		final int var6 = maxStringLength - text.length() - (var4 - var5);
		if (text.length() > 0) {
			var2 = var2 + text.substring(0, var4);
		}

		int var8;

		if (var6 < var3.length()) {
			var2 = var2 + var3.substring(0, var6);
			var8 = var6;
		} else {
			var2 = var2 + var3;
			var8 = var3.length();
		}

		if (text.length() > 0 && var5 < text.length()) {
			var2 = var2 + text.substring(var5);
		}

		if (field_175209_y.apply(var2)) {
			text = var2;
			moveCursorBy(var4 - selectionEnd + var8);

			if (field_175210_x != null) {
				field_175210_x.func_175319_a(field_175208_g, text);
			}
		}
	}

	/**
	 * Deletes the specified number of words starting at the cursor position.
	 * Negative numbers will delete words left of the cursor.
	 */
	private void deleteWords(final int p_146177_1_) {
		if (text.length() != 0) {
			if (selectionEnd != cursorPosition) {
				writeText("");
			} else {
				deleteFromCursor(getNthWordFromCursor(p_146177_1_) - cursorPosition);
			}
		}
	}

	/**
	 * delete the selected text, otherwsie deletes characters from either side
	 * of the cursor. params: delete num
	 */
	private void deleteFromCursor(final int p_146175_1_) {
		if (text.length() != 0) {
			if (selectionEnd != cursorPosition) {
				writeText("");
			} else {
				final boolean var2 = p_146175_1_ < 0;
				final int var3 = var2 ? cursorPosition + p_146175_1_ : cursorPosition;
				final int var4 = var2 ? cursorPosition : cursorPosition + p_146175_1_;
				String var5 = "";

				if (var3 >= 0) {
					var5 = text.substring(0, var3);
				}

				if (var4 < text.length()) {
					var5 = var5 + text.substring(var4);
				}

				text = var5;

				if (var2) {
					moveCursorBy(p_146175_1_);
				}

				if (field_175210_x != null) {
					field_175210_x.func_175319_a(field_175208_g, text);
				}
			}
		}
	}

	public int func_175206_d() {
		return field_175208_g;
	}

	/**
	 * see @getNthNextWordFromPos() params: N, position
	 */
	private int getNthWordFromCursor(final int p_146187_1_) {
		return getNthWordFromPos(p_146187_1_, getCursorPosition());
	}

	/**
	 * gets the position of the nth word. N may be negative, then it looks
	 * backwards. params: N, position
	 */
	private int getNthWordFromPos(final int p_146183_1_, final int p_146183_2_) {
		return func_146197_a(p_146183_1_, p_146183_2_, true);
	}

	private int func_146197_a(final int p_146197_1_, final int p_146197_2_, final boolean p_146197_3_) {
		int var4 = p_146197_2_;
		final boolean var5 = p_146197_1_ < 0;
		final int var6 = Math.abs(p_146197_1_);

		for (int var7 = 0; var7 < var6; ++var7) {
			if (var5) {
				while (p_146197_3_ && var4 > 0 && text.charAt(var4 - 1) == 32) {
					--var4;
				}

				while (var4 > 0 && text.charAt(var4 - 1) != 32) {
					--var4;
				}
			} else {
				final int var8 = text.length();
				var4 = text.indexOf(32, var4);

				if (var4 == -1) {
					var4 = var8;
				} else {
					while (p_146197_3_ && var4 < var8 && text.charAt(var4) == 32) {
						++var4;
					}
				}
			}
		}

		return var4;
	}

	/**
	 * Moves the text cursor by a specified number of characters and clears the
	 * selection
	 */
	private void moveCursorBy(final int p_146182_1_) {
		setCursorPosition(selectionEnd + p_146182_1_);
	}

	/**
	 * sets the position of the cursor to the provided index
	 */
	public void setCursorPosition(final int p_146190_1_) {
		cursorPosition = p_146190_1_;
		final int var2 = text.length();
		cursorPosition = MathHelper.clamp_int(cursorPosition, 0, var2);
		setSelectionPos(cursorPosition);
	}

	/**
	 * sets the cursors position to the beginning
	 */
	private void setCursorPositionZero() {
		setCursorPosition(0);
	}

	/**
	 * sets the cursors position to after the text
	 */
	private void setCursorPositionEnd() {
		setCursorPosition(text.length());
	}

	/**
	 * Call this method from your GuiScreen to process the keys into the textbox
	 */
	public boolean textboxKeyTyped(final char p_146201_1_, final int p_146201_2_) {
		if (!isFocused) {
			return false;
		} else if (GuiScreen.func_175278_g(p_146201_2_)) {
			setCursorPositionEnd();
			setSelectionPos(0);
			return true;
		} else if (GuiScreen.func_175280_f(p_146201_2_)) {
			GuiScreen.setClipboardString(getSelectedText());
			return true;
		} else if (GuiScreen.func_175279_e(p_146201_2_)) {
			if (isEnabled) {
				writeText(GuiScreen.getClipboardString());
			}

			return true;
		} else if (GuiScreen.func_175277_d(p_146201_2_)) {
			GuiScreen.setClipboardString(getSelectedText());

			if (isEnabled) {
				writeText("");
			}

			return true;
		} else {
			switch (p_146201_2_) {
			case 14:
				if (GuiScreen.isCtrlKeyDown()) {
					if (isEnabled) {
						deleteWords(-1);
					}
				} else if (isEnabled) {
					deleteFromCursor(-1);
				}

				return true;

			case 199:
				if (GuiScreen.isShiftKeyDown()) {
					setSelectionPos(0);
				} else {
					setCursorPositionZero();
				}

				return true;

			case 203:
				if (GuiScreen.isShiftKeyDown()) {
					if (GuiScreen.isCtrlKeyDown()) {
						setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
					} else {
						setSelectionPos(getSelectionEnd() - 1);
					}
				} else if (GuiScreen.isCtrlKeyDown()) {
					setCursorPosition(getNthWordFromCursor(-1));
				} else {
					moveCursorBy(-1);
				}

				return true;

			case 205:
				if (GuiScreen.isShiftKeyDown()) {
					if (GuiScreen.isCtrlKeyDown()) {
						setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
					} else {
						setSelectionPos(getSelectionEnd() + 1);
					}
				} else if (GuiScreen.isCtrlKeyDown()) {
					setCursorPosition(getNthWordFromCursor(1));
				} else {
					moveCursorBy(1);
				}

				return true;

			case 207:
				if (GuiScreen.isShiftKeyDown()) {
					setSelectionPos(text.length());
				} else {
					setCursorPositionEnd();
				}

				return true;

			case 211:
				if (GuiScreen.isCtrlKeyDown()) {
					if (isEnabled) {
						deleteWords(1);
					}
				} else if (isEnabled) {
					deleteFromCursor(1);
				}

				return true;

			default:
				if (EmailAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
					if (isEnabled) {
						writeText(Character.toString(p_146201_1_));
					}

					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Args: x, y, buttonClicked
	 */
	public void mouseClicked(final int p_146192_1_, final int p_146192_2_, final int p_146192_3_) {
		final boolean var4 = p_146192_1_ >= xPosition && p_146192_1_ < xPosition + width && p_146192_2_ >= yPosition
				&& p_146192_2_ < yPosition + height;

		if (canLoseFocus) {
			setFocused(var4);
		}

		if (isFocused && var4 && p_146192_3_ == 0) {
			int var5 = p_146192_1_ - xPosition;

			if (enableBackgroundDrawing) {
				var5 -= 4;
			}

			final String var6 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), getWidth());
			setCursorPosition(fontRendererInstance.trimStringToWidth(var6, var5).length() + lineScrollOffset);
		}
	}

	/**
	 * Draws the textbox
	 */
	public void drawTextBox() {
		if (getVisible()) {

			final int var2 = cursorPosition - lineScrollOffset;
			int var3 = selectionEnd - lineScrollOffset;
			final String var4 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), getWidth());
			final boolean var5 = var2 >= 0 && var2 <= var4.length();
			final boolean var6 = isFocused && cursorCounter / 6 % 2 == 0 && var5;
			final int var7 = enableBackgroundDrawing ? xPosition + 4 : xPosition;
			final int var8 = enableBackgroundDrawing ? yPosition + (height - 8) / 2 : yPosition;
			int var9 = var7;

			if (getEnableBackgroundDrawing()) {

				// Gui.drawRect(xPosition, yPosition + height - 1, xPosition +
				// width, yPosition + height, 0x2000face);

				// Gui.drawRect(xfloat1, yPosition + height - 1, xfloat2,
				// yPosition + height, 0x3000face);

				RenderUtils.drawAbgerundetRect(xPosition + 3, yPosition,
						xPosition + fontRendererInstance.getStringWidth(var4.substring(0, var2)) + fontRendererInstance.getStringWidth(var4.substring(var2)) + 5,
						yPosition + height - 1, 2, 0x7f000000);

			}

			if (var3 > var4.length()) {
				var3 = var4.length();
			}

			if (var4.length() > 0) {
				final String var10 = var5 ? var4.substring(0, var2) : var4;
				var9 = fontRendererInstance.drawString(var10, var7, var8, 0xffffffff);
			}

			final boolean var13 = cursorPosition < text.length() || text.length() >= getMaxStringLength();
			int var11 = var9;

			if (!var5) {
				var11 = var2 > 0 ? var7 + width : var7;
			} else if (var13) {
				var11 = var9 - 1;
				--var9;
			}

			if (var4.length() > 0 && var5 && var2 < var4.length()) {
				var9 = fontRendererInstance.drawString(var4.substring(var2), var9, var8, 0xffffffff);
			}

			if (var6) {
				if (var13) {
					Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + fontRendererInstance.FONT_HEIGHT, 0xffffffff);
				} else {
					fontRendererInstance.drawString("_", var11, var8, 0xffffffff);
				}
			}

			if (var3 != var2) {
				final int var12 = var7 + fontRendererInstance.getStringWidth(var4.substring(0, var3));
				drawCursorVertical(var11, var8 - 1, var12 - 1, var8 + 1 + fontRendererInstance.FONT_HEIGHT);
			}
		}
	}

	/**
	 * draws the vertical line cursor in the textbox
	 */
	private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
		int var5;

		if (p_146188_1_ < p_146188_3_) {
			var5 = p_146188_1_;
			p_146188_1_ = p_146188_3_;
			p_146188_3_ = var5;
		}

		if (p_146188_2_ < p_146188_4_) {
			var5 = p_146188_2_;
			p_146188_2_ = p_146188_4_;
			p_146188_4_ = var5;
		}

		if (p_146188_3_ > xPosition + width) {
			p_146188_3_ = xPosition + width;
		}

		if (p_146188_1_ > xPosition + width) {
			p_146188_1_ = xPosition + width;
		}

		final Tessellator var7 = Tessellator.getInstance();
		final WorldRenderer var6 = var7.getWorldRenderer();
		GlStateManager.color(0, 250, 206, 255);
		GlStateManager.disableTexture2D();
		GlStateManager.enableColorLogic();
		GlStateManager.colorLogicOp(5387);
		var6.startDrawingQuads();
		var6.addVertex(p_146188_1_, p_146188_4_, 0.0D);
		var6.addVertex(p_146188_3_, p_146188_4_, 0.0D);
		var6.addVertex(p_146188_3_, p_146188_2_, 0.0D);
		var6.addVertex(p_146188_1_, p_146188_2_, 0.0D);
		var7.draw();
		GlStateManager.disableColorLogic();
		GlStateManager.enableTexture2D();
	}

	public void setMaxStringLength(final int p_146203_1_) {
		maxStringLength = p_146203_1_;

		if (text.length() > p_146203_1_) {
			text = text.substring(0, p_146203_1_);
		}
	}

	/**
	 * returns the maximum number of character that can be contained in this
	 * textbox
	 */
	public int getMaxStringLength() {
		return maxStringLength;
	}

	/**
	 * returns the current position of the cursor
	 */
	public int getCursorPosition() {
		return cursorPosition;
	}

	/**
	 * get enable drawing background and outline
	 */
	public boolean getEnableBackgroundDrawing() {
		return enableBackgroundDrawing;
	}

	/**
	 * enable drawing background and outline
	 */
	public void setEnableBackgroundDrawing(final boolean p_146185_1_) {
		enableBackgroundDrawing = p_146185_1_;
	}

	/**
	 * Sets the text colour for this textbox (disabled text will not use this
	 * colour)
	 */
	public void setTextColor(final int p_146193_1_) {
	}

	public void setDisabledTextColour(final int p_146204_1_) {
	}

	/**
	 * Sets focus to this gui element
	 */
	public void setFocused(final boolean p_146195_1_) {
		if (p_146195_1_ && !isFocused) {
			cursorCounter = 0;
		}

		isFocused = p_146195_1_;
	}

	/**
	 * Getter for the focused field
	 */
	public boolean isFocused() {
		return isFocused;
	}

	public void setEnabled(final boolean p_146184_1_) {
		isEnabled = p_146184_1_;
	}

	/**
	 * the side of the selection that is not the cursor, may be the same as the
	 * cursor
	 */
	public int getSelectionEnd() {
		return selectionEnd;
	}

	/**
	 * returns the width of the textbox depending on if background drawing is
	 * enabled
	 */
	public int getWidth() {
		return getEnableBackgroundDrawing() ? width - 8 : width;
	}

	/**
	 * Sets the position of the selection anchor (i.e. position the selection
	 * was started at)
	 */
	public void setSelectionPos(int p_146199_1_) {
		final int var2 = text.length();

		if (p_146199_1_ > var2) {
			p_146199_1_ = var2;
		}

		if (p_146199_1_ < 0) {
			p_146199_1_ = 0;
		}

		selectionEnd = p_146199_1_;

		if (fontRendererInstance != null) {
			if (lineScrollOffset > var2) {
				lineScrollOffset = var2;
			}

			final int var3 = getWidth();
			final String var4 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), var3);
			final int var5 = var4.length() + lineScrollOffset;

			if (p_146199_1_ == lineScrollOffset) {
				lineScrollOffset -= fontRendererInstance.trimStringToWidth(text, var3, true).length();
			}

			if (p_146199_1_ > var5) {
				lineScrollOffset += p_146199_1_ - var5;
			} else if (p_146199_1_ <= lineScrollOffset) {
				lineScrollOffset -= lineScrollOffset - p_146199_1_;
			}

			lineScrollOffset = MathHelper.clamp_int(lineScrollOffset, 0, var2);
		}
	}

	/**
	 * if true the textbox can lose focus by clicking elsewhere on the screen
	 */
	public void setCanLoseFocus(final boolean p_146205_1_) {
		canLoseFocus = p_146205_1_;
	}

	/**
	 * returns true if this textbox is visible
	 */
	public boolean getVisible() {
		return visible;
	}

	/**
	 * Sets whether or not this textbox is visible
	 */
	public void setVisible(final boolean p_146189_1_) {
		visible = p_146189_1_;
	}
}
