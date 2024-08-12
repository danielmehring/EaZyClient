package me.EaZy.client;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

public class FontUtils {

	public static final int EaZy = 53;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final Minecraft mc = Minecraft.getMinecraft();

	private final UnicodeFont unicodeFont;
	private final int[] colorCodes = new int[32];

	private final float kerning;

	public boolean override;

	public FontUtils(final String fontName, final int fontType, final int size) {
		this(fontName, fontType, size, 0);
	}

	public FontUtils(final Font font) {
		this(font, 0);
	}

	public FontUtils(final Font font, final float kerning, final boolean override) {
		this(font, kerning);
		this.override = override;
	}

	public FontUtils(final Font font, final float kerning) {
		this.unicodeFont = new UnicodeFont(font);
		this.kerning = kerning;

		this.unicodeFont.addAsciiGlyphs();
		this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));

		try {
			this.unicodeFont.loadGlyphs();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 32; i++) {
			final int shadow = (i >> 3 & 1) * 85;
			int red = (i >> 2 & 1) * 170 + shadow;
			int green = (i >> 1 & 1) * 170 + shadow;
			int blue = (i & 1) * 170 + shadow;

			if (i == 6) {
				red += 85;
			}

			if (i >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
		}

	}

	public FontUtils(final String fontName, final int fontType, final int size, final float kerning) {
		this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));
		this.kerning = kerning;

		this.unicodeFont.addAsciiGlyphs();
		this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));

		try {
			this.unicodeFont.loadGlyphs();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 32; i++) {
			final int shadow = (i >> 3 & 1) * 85;
			int red = (i >> 2 & 1) * 170 + shadow;
			int green = (i >> 1 & 1) * 170 + shadow;
			int blue = (i & 1) * 170 + shadow;

			if (i == 6) {
				red += 85;
			}

			if (i >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
		}

	}

	public int drawString(String text, float x, float y, final int color) {
		if (override || (Configs.customFont && !Client.isHidden)) {
			final float originalX = x;
			GL11.glPushMatrix();

			float[] prevColor = GlStateManager.getColor();

			boolean alpha = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
			boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
			boolean text2d = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
			boolean lineSmooth = GL11.glIsEnabled(GL11.GL_LINE_SMOOTH);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glTranslated(x, y, 42);
			GL11.glScaled(0.125, 0.125, 0.125);

			text = text.replace("§l", "");

			int currentColor = color;
			final char[] characters = text.toCharArray();
			x = 0;
			y = -10;
			int index = 0;
			for (final char c : characters) {
				if (c == '\r') {
					x = originalX;
				}
				if (c == '\n') {
					y += getHeight(Character.toString(c)) * 2.0F;
				}
				if (c != '\247' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '\247')) {
					unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
					x += (getWidth(Character.toString(c)) * 2.0F);
				} else if (c == ' ') {
					x += unicodeFont.getSpaceWidth();
				} else if (c == '\247' && index != characters.length - 1) {
					final int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
					if (codeIndex < 0) {
						continue;
					}

					final int col = this.colorCodes[codeIndex];
					currentColor = col;
				} else if (c == '\247' && index != characters.length - 1) {
					final int codeIndex = "r".indexOf(text.charAt(index + 1));
					if (codeIndex < 0) {
						continue;
					}

					final int col = this.colorCodes[1];
					currentColor = col;
				}

				index++;
			}
			GL11.glScaled(8.0, 8.0, 8.0);
			GL11.glShadeModel(GL11.GL_FLAT);
			if (!alpha)
				GL11.glDisable(GL11.GL_ALPHA_TEST);
			if (!blend)
				GL11.glDisable(GL11.GL_BLEND);
			if (!lineSmooth)
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
			if (text2d)
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(prevColor[0], prevColor[1], prevColor[2], prevColor[3]);
			GL11.glPopMatrix();
			return (int) (originalX + x / 16);
		} else {
			return Client.mc.fontRendererObj.drawString(text, x, y, color);
		}
	}

	public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
		if (override || (Configs.customFont && !Client.isHidden)) {
			drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0x000000);
			return drawString(text, x, y, color);
		} else {
			return Client.mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
		}
	}

	public void drawCenteredString(final String text, final float x, final float y, final int color) {
		if (override || (Configs.customFont && !Client.isHidden)) {
			drawString(text, x - getStringWidth(text) / 2, y, color);
		} else {
			Client.mc.fontRendererObj.drawCenteredString(text, x, y, color);
		}
	}

	public void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
		if (override || (Configs.customFont && !Client.isHidden)) {
			drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0xff000000);
			drawCenteredString(text, x, y, color);
		} else {
			Client.mc.fontRendererObj.drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F,
					0xff000000);
			Client.mc.fontRendererObj.drawCenteredString(text, x, y, color);
		}
	}

	public int getWidth(final String s) {
		float width = 0.0F;

		final String str = StringUtils.stripControlCodes(s);
		for (final char c : str.toCharArray()) {
			width += unicodeFont.getWidth(Character.toString(c)) + this.kerning;
		}

		return (int) (width / 2);
	}

	public int getStringWidth(final String s) {
		if (override || (Configs.customFont && !Client.isHidden)) {
			float width = 0.0F;

			final String str = StringUtils.stripControlCodes(s);
			for (final char c : str.toCharArray()) {
				width += unicodeFont.getWidth(Character.toString(c)) + this.kerning;
			}

			return (int) (width / 8);
		} else {
			return Client.mc.fontRendererObj.getStringWidth(s);
		}
	}

	public float getCharWidth(final char c) {
		return unicodeFont.getWidth(String.valueOf(c));
	}

	public float getHeight(final String s) {
		return unicodeFont.getHeight(s) / 2.0F;
	}

	public UnicodeFont getFont() {
		return this.unicodeFont;
	}

	public String trimStringToWidth(final String par1Str, final int par2) {
		final StringBuilder var4 = new StringBuilder();
		float var5 = 0.0F;
		final int var6 = 0;
		final int var7 = 1;
		boolean var8 = false;
		boolean var9 = false;

		for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
			final char var11 = par1Str.charAt(var10);
			final float var12 = this.getCharWidth(var11);

			if (var8) {
				var8 = false;

				if (var11 != 108 && var11 != 76) {
					if (var11 == 114 || var11 == 82) {
						var9 = false;
					}
				} else {
					var9 = true;
				}
			} else if (var12 < 0.0F) {
				var8 = true;
			} else {
				var5 += var12;

				if (var9) {
					++var5;
				}
			}

			if (var5 > par2) {
				break;
			} else {
				var4.append(var11);
			}
		}

		return var4.toString();
	}

}
