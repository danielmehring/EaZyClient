package me.EaZy.client.utils;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public abstract class GuiSlot {

	public static final int EaZy = 217;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	protected int width;
	/**
	 * The top of the slot container. Affects the overlays and scrolling.
	 */
	private int top;

	/**
	 * The bottom of the slot container. Affects the overlays and scrolling.
	 */
	private int bottom;
	private int right;
	protected int left;

	/**
	 * The height of a slot.
	 */
	private final int slotHeight;

	/**
	 * The buttonID of the button used to scroll up
	 */
	private int scrollUpButtonID;

	/**
	 * The buttonID of the button used to scroll down
	 */
	private int scrollDownButtonID;

	/**
	 * Where the mouse was in the window when you first clicked to scroll
	 */
	private float initialClickY = -2.0F;

	/**
	 * What to multiply the amount you moved your mouse by (used for slowing
	 * down scrolling when over the items and not on the scroll bar)
	 */
	private float scrollMultiplier;

	/**
	 * How far down this slot has been scrolled
	 */
	private float amountScrolled;

	/**
	 * The element in the list that was selected
	 */
	private int selectedElement = -1;

	private long lastClicked;

	/**
	 * Set to true if a selected element in this gui will show an outline box
	 */
	private boolean showSelectionBox = true;

	private boolean hasListHeader;
	private int headerPadding;
	private boolean enabled = true;

	public GuiSlot(final Minecraft par1Minecraft, final int par2, final int par3, final int par4, final int par5,
			final int par6) {
		width = par2;
		top = par4;
		bottom = par5;
		slotHeight = par6;
		left = 0;
		right = par2;
	}

	protected abstract int getSize();

	protected abstract void elementClicked(int var1, boolean var2, int var3, int var4);

	protected abstract boolean isSelected(int var1);

	private int func_148138_e() {
		return getSize() * slotHeight + headerPadding;
	}

	protected abstract void drawBackground();

	protected abstract void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6);

	private void func_148129_a(final int p_148129_1_, final int p_148129_2_, final Tessellator p_148129_3_) {}

	private void func_148132_a(final int p_148132_1_, final int p_148132_2_) {}

	private void func_148142_b(final int p_148142_1_, final int p_148142_2_) {}

	public void registerScrollButtons(final int p_148134_1_, final int p_148134_2_) {
		scrollUpButtonID = p_148134_1_;
		scrollDownButtonID = p_148134_2_;
	}

	private void func_148121_k() {
		int var1 = func_148135_f();

		if (var1 < 0) {
			var1 /= 2;
		}

		if (amountScrolled < 0.0F) {
			amountScrolled = 0.0F;
		}

		if (amountScrolled > var1) {
			amountScrolled = var1;
		}
	}

	private int func_148135_f() {
		return func_148138_e() - (bottom - top - 4);
	}

	public void drawScreen(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
		drawBackground();
		final int var4 = getSize();
		final ScaledResolution sr = RenderUtils.newScaledResolution();
		final int var5 = sr.getScaledWidth() - 20;
		final int var6 = var5 + 6;
		int var9;
		int var10;
		int var13;
		int var19;

		if (p_148128_1_ > left && p_148128_1_ < right && p_148128_2_ > top && p_148128_2_ < bottom) {
			if (Mouse.isButtonDown(0) && func_148125_i()) {
				if (initialClickY == -1.0F) {
					boolean var15 = true;

					if (p_148128_2_ >= top && p_148128_2_ <= bottom) {
						final int var8 = width / 2 - func_148139_c() / 2;
						var9 = width / 2 + func_148139_c() / 2;
						var10 = p_148128_2_ - top - headerPadding + (int) amountScrolled - 4;
						final int var11 = var10 / slotHeight;

						if (p_148128_1_ >= var8 && p_148128_1_ <= var9 && var11 >= 0 && var10 >= 0 && var11 < var4) {
							final boolean var12 = var11 == selectedElement
									&& Minecraft.getSystemTime() - lastClicked < 250L;
							elementClicked(var11, var12, p_148128_1_, p_148128_2_);
							selectedElement = var11;
							lastClicked = Minecraft.getSystemTime();
						} else if (p_148128_1_ >= var8 && p_148128_1_ <= var9 && var10 < 0) {
							func_148132_a(p_148128_1_ - var8, p_148128_2_ - top + (int) amountScrolled - 4);
							var15 = false;
						}

						if (p_148128_1_ >= var5 && p_148128_1_ <= var6) {
							scrollMultiplier = -1.0F;
							var19 = func_148135_f();

							if (var19 < 1) {
								var19 = 1;
							}

							var13 = (int) ((float) ((bottom - top) * (bottom - top)) / (float) func_148138_e());

							if (var13 < 32) {
								var13 = 32;
							}

							if (var13 > bottom - top - 8) {
								var13 = bottom - top - 8;
							}

							scrollMultiplier /= (float) (bottom - top - var13) / (float) var19;
						} else {
							scrollMultiplier = 1.0F;
						}

						if (var15) {
							initialClickY = p_148128_2_;
						} else {
							initialClickY = -2.0F;
						}
					} else {
						initialClickY = -2.0F;
					}
				} else if (initialClickY >= 0.0F) {
					amountScrolled -= (p_148128_2_ - initialClickY) * scrollMultiplier;
					initialClickY = p_148128_2_;
				}
			} else {
				try {
					for (; !Minecraft.gameSettings.touchscreen && Mouse.next(); Minecraft.currentScreen
							.handleMouseInput()) {
						int var7 = Mouse.getEventDWheel();

						if (var7 != 0) {
							if (var7 > 0) {
								var7 = -1;
							} else if (var7 < 0) {
								var7 = 1;
							}

							amountScrolled += var7 * slotHeight / 2;
						}
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}

				initialClickY = -1.0F;
			}
		}

		func_148121_k();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		// Hintergrund dunkler
		// mc.getTextureManager().bindTexture(Gui.optionsBackground);
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// float var16 = 32.0F;
		// wr.startDrawingQuads();
		// wr.setColorOpaque_I(2105376);
		// wr.addVertexWithUV(left, bottom, 0.0D, left / var16, (bottom + (int)
		// amountScrolled) / var16);
		// wr.addVertexWithUV(right, bottom, 0.0D, right / var16, (bottom +
		// (int) amountScrolled) / var16);
		// wr.addVertexWithUV(right, top, 0.0D, right / var16, (top + (int)
		// amountScrolled) / var16);
		// wr.addVertexWithUV(left, top, 0.0D, left / var16, (top + (int)
		// amountScrolled) / var16);
		// ts.draw();
		var9 = left + width / 2 - func_148139_c() / 2 + 2;
		var10 = top + 4 - (int) amountScrolled;

		if (hasListHeader) {
			func_148129_a(var9, var10, Tessellator.getInstance());
		}

		func_148120_b(var9, var10, p_148128_1_, p_148128_2_);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 0, 1);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		// wr.startDrawingQuads();
		// wr.setColorRGBA_I(0, 0);
		// wr.addVertexWithUV(left, top + var18, 0.0D, 0.0D, 1.0D);
		// wr.addVertexWithUV(right, top + var18, 0.0D, 1.0D, 1.0D);
		// wr.setColorRGBA_I(0, 255);
		// wr.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
		// wr.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
		// ts.draw();
		// wr.startDrawingQuads();
		// wr.setColorRGBA_I(0, 255);
		// wr.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
		// wr.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
		// wr.setColorRGBA_I(0, 0);
		// wr.addVertexWithUV(right, bottom - var18, 0.0D, 1.0D, 0.0D);
		// wr.addVertexWithUV(left, bottom - var18, 0.0D, 0.0D, 0.0D);
		// ts.draw();
		var19 = func_148135_f();

		if (var19 > 0) {
			var13 = (bottom - top) * (bottom - top) / func_148138_e();

			if (var13 < 32) {
				var13 = 32;
			}

			if (var13 > bottom - top - 8) {
				var13 = bottom - top - 8;
			}

			int var14 = (int) amountScrolled * (bottom - top - var13) / var19 + top;

			if (var14 < top) {
				var14 = top;
			}

			// Scrollbar
			RenderUtils.drawAbgerundetRect(var5, var14, var6, var14 + var13, 3, 0x34000000);
		}

		func_148142_b(p_148128_1_, p_148128_2_);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private boolean func_148125_i() {
		return enabled;
	}

	public int func_148139_c() {
		return 250;
	}

	private void func_148120_b(final int p_148120_1_, final int p_148120_2_, final int p_148120_3_,
			final int p_148120_4_) {
		final int var5 = getSize();
		final Tessellator ts = Tessellator.getInstance();
		ts.getWorldRenderer();

		for (int var7 = 0; var7 < var5; ++var7) {
			final int slotY = p_148120_2_ + var7 * slotHeight + headerPadding;
			final int slotHeightCalc = slotHeight - 4;

			if (slotY <= bottom && slotY + slotHeightCalc >= top) {
				if (showSelectionBox && isSelected(var7)) {
					final int sideLeft = left + width / 2 - func_148139_c() / 2;
					final int sideRight = left + width / 2 + func_148139_c() / 2;
					// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					// GL11.glDisable(GL11.GL_TEXTURE_2D);
					// wr.startDrawingQuads();
					// wr.setColorOpaque_I(Client.getColor(0l).getRGB());
					// wr.addVertexWithUV(sideLeft, slotY + slotHeightCalc + 2,
					// 0.0D, 0.0D, 1.0D);
					// wr.addVertexWithUV(sideRight, slotY + slotHeightCalc + 2,
					// 0.0D, 1.0D, 1.0D);
					// wr.addVertexWithUV(sideRight, slotY - 2, 0.0D, 1.0D,
					// 0.0D);
					// wr.addVertexWithUV(sideLeft, slotY - 2, 0.0D, 0.0D,
					// 0.0D);
					// wr.setColorOpaque_I(0);
					// wr.addVertexWithUV(sideLeft + 1, slotY + slotHeightCalc +
					// 1, 0.0D, 0.0D, 1.0D);
					// wr.addVertexWithUV(sideRight - 1, slotY + slotHeightCalc
					// + 1, 0.0D, 1.0D, 1.0D);
					// wr.addVertexWithUV(sideRight - 1, slotY - 1, 0.0D, 1.0D,
					// 0.0D);
					// wr.addVertexWithUV(sideLeft + 1, slotY - 1, 0.0D, 0.0D,
					// 0.0D);
					// ts.draw();
					// GL11.glEnable(GL11.GL_TEXTURE_2D);
					// Selection
					RenderUtils.drawHollowRect(sideLeft + 50, slotY - 2 + 3, sideRight - 50,
							slotY + slotHeightCalc + 2 - 3, 1.0f, Client.getColor(0l).getRGB());
				}

				drawSlot(var7, p_148120_1_, slotY, slotHeightCalc, p_148120_3_, p_148120_4_);
			}
		}
	}

	private int func_148137_d() {
		return width / 2 + 134;
	}
}
