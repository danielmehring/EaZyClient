package me.EaZy.client.alts;

import org.lwjgl.opengl.GL11;

import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ChatAllowedCharacters;

public class GuiPasswordFieldLogin extends Gui {

	public static final int EaZy = 31;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private final FontRenderer field_146211_a;
	private final int field_146209_f;
	private final int field_146210_g;
	private final int field_146218_h;
	private final int field_146219_i;
	private String field_146216_j = "";
	private int field_146217_k = 32;
	private int field_146214_l;
	private boolean field_146215_m = true;
	private boolean field_146212_n = true;
	private boolean field_146213_o;
	private boolean field_146226_p = true;
	private int field_146225_q;
	private int field_146224_r;
	private int field_146223_s;
	private boolean field_146220_v = true;

	public GuiPasswordFieldLogin(final FontRenderer par1FontRenderer, final int par2, final int par3, final int par4,
			final int par5) {
		field_146211_a = par1FontRenderer;
		field_146209_f = par2;
		field_146210_g = par3;
		field_146218_h = par4;
		field_146219_i = par5;
	}

	/**
	 * Increments the cursor counter
	 */
	public void updateCursorCounter() {
		++field_146214_l;
	}

	/**
	 * Sets the text of the textbox
	 */
	public void setText(final String p_146180_1_) {
		if (p_146180_1_.length() > field_146217_k) {
			field_146216_j = p_146180_1_.substring(0, field_146217_k);
		} else {
			field_146216_j = p_146180_1_;
		}

		func_146202_e();
	}

	/**
	 * Returns the contents of the textbox
	 */
	public String getText() {
		return field_146216_j;
	}

	private String func_146207_c() {
		final int var1 = field_146224_r < field_146223_s ? field_146224_r : field_146223_s;
		final int var2 = field_146224_r < field_146223_s ? field_146223_s : field_146224_r;
		return field_146216_j.substring(var1, var2);
	}

	private void func_146191_b(final String p_146191_1_) {
		String var2 = "";
		final String var3 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
		final int var4 = field_146224_r < field_146223_s ? field_146224_r : field_146223_s;
		final int var5 = field_146224_r < field_146223_s ? field_146223_s : field_146224_r;
		final int var6 = field_146217_k - field_146216_j.length() - (var4 - field_146223_s);
		if (field_146216_j.length() > 0) {
			var2 = var2 + field_146216_j.substring(0, var4);
		}

		int var8;

		if (var6 < var3.length()) {
			var2 = var2 + var3.substring(0, var6);
			var8 = var6;
		} else {
			var2 = var2 + var3;
			var8 = var3.length();
		}

		if (field_146216_j.length() > 0 && var5 < field_146216_j.length()) {
			var2 = var2 + field_146216_j.substring(var5);
		}

		field_146216_j = var2;
		func_146182_d(var4 - field_146223_s + var8);
	}

	private void func_146177_a(final int p_146177_1_) {
		if (field_146216_j.length() != 0) {
			if (field_146223_s != field_146224_r) {
				func_146191_b("");
			} else {
				func_146175_b(func_146187_c(p_146177_1_) - field_146224_r);
			}
		}
	}

	private void func_146175_b(final int p_146175_1_) {
		if (field_146216_j.length() != 0) {
			if (field_146223_s != field_146224_r) {
				func_146191_b("");
			} else {
				final boolean var2 = p_146175_1_ < 0;
				final int var3 = var2 ? field_146224_r + p_146175_1_ : field_146224_r;
				final int var4 = var2 ? field_146224_r : field_146224_r + p_146175_1_;
				String var5 = "";

				if (var3 >= 0) {
					var5 = field_146216_j.substring(0, var3);
				}

				if (var4 < field_146216_j.length()) {
					var5 = var5 + field_146216_j.substring(var4);
				}

				field_146216_j = var5;

				if (var2) {
					func_146182_d(p_146175_1_);
				}
			}
		}
	}

	private int func_146187_c(final int p_146187_1_) {
		return func_146183_a(p_146187_1_, func_146198_h());
	}

	private int func_146183_a(final int p_146183_1_, final int p_146183_2_) {
		return func_146197_a(p_146183_1_, func_146198_h(), true);
	}

	private int func_146197_a(final int p_146197_1_, final int p_146197_2_, final boolean p_146197_3_) {
		int var4 = p_146197_2_;
		final boolean var5 = p_146197_1_ < 0;
		final int var6 = Math.abs(p_146197_1_);

		for (int var7 = 0; var7 < var6; ++var7) {
			if (var5) {
				while (p_146197_3_ && var4 > 0 && field_146216_j.charAt(var4 - 1) == 32) {
					--var4;
				}

				while (var4 > 0 && field_146216_j.charAt(var4 - 1) != 32) {
					--var4;
				}
			} else {
				final int var8 = field_146216_j.length();
				var4 = field_146216_j.indexOf(32, var4);

				if (var4 == -1) {
					var4 = var8;
				} else {
					while (p_146197_3_ && var4 < var8 && field_146216_j.charAt(var4) == 32) {
						++var4;
					}
				}
			}
		}

		return var4;
	}

	private void func_146182_d(final int p_146182_1_) {
		func_146190_e(field_146223_s + p_146182_1_);
	}

	private void func_146190_e(final int p_146190_1_) {
		field_146224_r = p_146190_1_;
		final int var2 = field_146216_j.length();

		if (field_146224_r < 0) {
			field_146224_r = 0;
		}

		if (field_146224_r > var2) {
			field_146224_r = var2;
		}

		func_146199_i(field_146224_r);
	}

	private void func_146196_d() {
		func_146190_e(0);
	}

	private void func_146202_e() {
		func_146190_e(field_146216_j.length());
	}

	/**
	 * Call this method from your GuiScreen to process the keys into the textbox
	 */
	public boolean textboxKeyTyped(final char p_146201_1_, final int p_146201_2_) {
		if (!field_146213_o) {
			return false;
		} else {
			switch (p_146201_1_) {
			case 1:
				func_146202_e();
				func_146199_i(0);
				return true;

			case 3:
				GuiScreen.setClipboardString(func_146207_c());
				return true;

			case 22:
				if (field_146226_p) {
					func_146191_b(GuiScreen.getClipboardString());
				}

				return true;

			case 24:
				GuiScreen.setClipboardString(func_146207_c());

				if (field_146226_p) {
					func_146191_b("");
				}

				return true;

			default:
				switch (p_146201_2_) {
				case 14:
					if (GuiScreen.isCtrlKeyDown()) {
						if (field_146226_p) {
							func_146177_a(-1);
						}
					} else if (field_146226_p) {
						func_146175_b(-1);
					}

					return true;

				case 199:
					if (GuiScreen.isShiftKeyDown()) {
						func_146199_i(0);
					} else {
						func_146196_d();
					}

					return true;

				case 203:
					if (GuiScreen.isShiftKeyDown()) {
						if (GuiScreen.isCtrlKeyDown()) {
							func_146199_i(func_146183_a(-1, func_146186_n()));
						} else {
							func_146199_i(func_146186_n() - 1);
						}
					} else if (GuiScreen.isCtrlKeyDown()) {
						func_146190_e(func_146187_c(-1));
					} else {
						func_146182_d(-1);
					}

					return true;

				case 205:
					if (GuiScreen.isShiftKeyDown()) {
						if (GuiScreen.isCtrlKeyDown()) {
							func_146199_i(func_146183_a(1, func_146186_n()));
						} else {
							func_146199_i(func_146186_n() + 1);
						}
					} else if (GuiScreen.isCtrlKeyDown()) {
						func_146190_e(func_146187_c(1));
					} else {
						func_146182_d(1);
					}

					return true;

				case 207:
					if (GuiScreen.isShiftKeyDown()) {
						func_146199_i(field_146216_j.length());
					} else {
						func_146202_e();
					}

					return true;

				case 211:
					if (GuiScreen.isCtrlKeyDown()) {
						if (field_146226_p) {
							func_146177_a(1);
						}
					} else if (field_146226_p) {
						func_146175_b(1);
					}

					return true;

				default:
					if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
						if (field_146226_p) {
							func_146191_b(Character.toString(p_146201_1_));
						}

						return true;
					} else {
						return false;
					}
				}
			}
		}
	}

	/**
	 * Args: x, y, buttonClicked
	 */
	public void mouseClicked(final int p_146192_1_, final int p_146192_2_, final int p_146192_3_) {
		final boolean var4 = p_146192_1_ >= field_146209_f && p_146192_1_ < field_146209_f + field_146218_h
				&& p_146192_2_ >= field_146210_g && p_146192_2_ < field_146210_g + field_146219_i;

		if (field_146212_n) {
			setFocused(var4);
		}

		if (field_146213_o && p_146192_3_ == 0) {
			int var5 = p_146192_1_ - field_146209_f;

			if (field_146215_m) {
				var5 -= 4;
			}

			final String var6 = field_146211_a.trimStringToWidth(field_146216_j.substring(field_146225_q),
					func_146200_o());
			func_146190_e(field_146211_a.trimStringToWidth(var6, var5).length() + field_146225_q);
		}
	}

	/**
	 * Draws the textbox
	 */
	public void drawTextBox() {

		if (func_146176_q()) {
			final int var2 = field_146224_r - field_146225_q;
			int var3 = field_146223_s - field_146225_q;
			final String var4 = field_146211_a.trimStringToWidth(field_146216_j.substring(field_146225_q),
					func_146200_o());
			final boolean var5 = var2 >= 0 && var2 <= var4.length();
			final boolean var6 = field_146213_o && field_146214_l / 6 % 2 == 0 && var5;
			final int var7 = field_146215_m ? field_146209_f + 4 : field_146209_f;
			final int var8 = field_146215_m ? field_146210_g + (field_146219_i - 8) / 2 : field_146210_g;
			int var9 = var7;

			if (func_146181_i()) {

				// Gui.drawRect(field_146209_f, field_146210_g + field_146219_i
				// - 1, field_146209_f + field_146218_h,
				// field_146210_g + field_146219_i, 0x2000face);

				// Gui.drawRect(xfloat1, field_146210_g + field_146219_i - 1,
				// xfloat2, field_146210_g + field_146219_i,
				// 0x3000face);

				RenderUtils.drawAbgerundetRect(field_146209_f + 3, field_146210_g,
						field_146209_f
								+ Minecraft.getMinecraft().fontRendererObj.getStringWidth(var4.substring(0, var2))
								+ Minecraft.getMinecraft().fontRendererObj.getStringWidth(var4.substring(var2)) - 11,
						field_146210_g + field_146219_i - 1, 2, 0x7f000000);

			}

			if (var3 > var4.length()) {
				var3 = var4.length();
			}

			if (var4.length() > 0) {
				final String var10 = var5 ? var4.substring(0, var2) : var4;
				String stars = "";
				for (int i = 0; i < var10.length(); i++) {
					stars = stars.concat("*");
				}
				var9 = field_146211_a.drawString(stars, var7, var8, 0xffffffff);
			}

			final boolean var13 = field_146224_r < field_146216_j.length()
					|| field_146216_j.length() >= func_146208_g();
			int var11 = var9;

			if (!var5) {
				var11 = var2 > 0 ? var7 + field_146218_h : var7;
			} else if (var13) {
				var11 = var9 - 1;
				--var9;
			}

			if (var4.length() > 0 && var5 && var2 < var4.length()) {
				String stars = "";
				for (int i = 0; i < var4.substring(var2).length(); i++) {
					stars = stars.concat("*");
				}
				field_146211_a.drawString(stars, var9, var8, 0xffffffff);
			}

			if (var6) {
				if (var13) {
					Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + field_146211_a.FONT_HEIGHT, 0xffffffff);
				} else {
					field_146211_a.drawString("_", var11, var8, 0xffffffff);
				}
			}

			if (var3 != var2) {
				String stars = "";
				for (int i = 0; i < var4.substring(0, var3).length(); i++) {
					stars = stars.concat("*");
				}
				final int var12 = var7 + field_146211_a.getStringWidth(stars);
				func_146188_c(var11, var8 - 1, var12 - 1, var8 + 1 + field_146211_a.FONT_HEIGHT);
			}
		}
	}

	private void func_146188_c(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
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

		if (p_146188_3_ > field_146209_f + field_146218_h) {
			p_146188_3_ = field_146209_f + field_146218_h;
		}

		if (p_146188_1_ > field_146209_f + field_146218_h) {
			p_146188_1_ = field_146209_f + field_146218_h;
		}

		final WorldRenderer var6 = Tessellator.getInstance().getWorldRenderer();
		GlStateManager.color(0, 250, 206, 255);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glLogicOp(GL11.GL_OR_REVERSE);
		var6.startDrawingQuads();
		var6.addVertex(p_146188_1_, p_146188_4_, 0.0D);
		var6.addVertex(p_146188_3_, p_146188_4_, 0.0D);
		var6.addVertex(p_146188_3_, p_146188_2_, 0.0D);
		var6.addVertex(p_146188_1_, p_146188_2_, 0.0D);
		var6.draw();
		GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void func_146203_f(final int p_146203_1_) {
		field_146217_k = p_146203_1_;

		if (field_146216_j.length() > p_146203_1_) {
			field_146216_j = field_146216_j.substring(0, p_146203_1_);
		}
	}

	private int func_146208_g() {
		return field_146217_k;
	}

	private int func_146198_h() {
		return field_146224_r;
	}

	private boolean func_146181_i() {
		return field_146215_m;
	}

	public void func_146185_a(final boolean p_146185_1_) {
		field_146215_m = p_146185_1_;
	}

	public void func_146193_g(final int p_146193_1_) {
	}

	public void func_146204_h(final int p_146204_1_) {
	}

	/**
	 * Sets focus to this gui element
	 */
	public void setFocused(final boolean p_146195_1_) {
		if (p_146195_1_ && !field_146213_o) {
			field_146214_l = 0;
		}

		field_146213_o = p_146195_1_;
	}

	/**
	 * Getter for the focused field
	 */
	public boolean isFocused() {
		return field_146213_o;
	}

	public void func_146184_c(final boolean p_146184_1_) {
		field_146226_p = p_146184_1_;
	}

	private int func_146186_n() {
		return field_146223_s;
	}

	private int func_146200_o() {
		return func_146181_i() ? field_146218_h - 8 : field_146218_h;
	}

	private void func_146199_i(int p_146199_1_) {
		final int var2 = field_146216_j.length();

		if (p_146199_1_ > var2) {
			p_146199_1_ = var2;
		}

		if (p_146199_1_ < 0) {
			p_146199_1_ = 0;
		}

		field_146223_s = p_146199_1_;

		if (field_146211_a != null) {
			if (field_146225_q > var2) {
				field_146225_q = var2;
			}

			final int var3 = func_146200_o();
			final String var4 = field_146211_a.trimStringToWidth(field_146216_j.substring(field_146225_q), var3);
			final int var5 = var4.length() + field_146225_q;

			if (p_146199_1_ == field_146225_q) {
				field_146225_q -= field_146211_a.trimStringToWidth(field_146216_j, var3, true).length();
			}

			if (p_146199_1_ > var5) {
				field_146225_q += p_146199_1_ - var5;
			} else if (p_146199_1_ <= field_146225_q) {
				field_146225_q -= field_146225_q - p_146199_1_;
			}

			if (field_146225_q < 0) {
				field_146225_q = 0;
			}

			if (field_146225_q > var2) {
				field_146225_q = var2;
			}
		}
	}

	public void func_146205_d(final boolean p_146205_1_) {
		field_146212_n = p_146205_1_;
	}

	private boolean func_146176_q() {
		return field_146220_v;
	}

	public void func_146189_e(final boolean p_146189_1_) {
		field_146220_v = p_146189_1_;
	}
}
