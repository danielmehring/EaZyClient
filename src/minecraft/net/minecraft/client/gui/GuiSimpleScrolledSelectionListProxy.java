package net.minecraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
import net.minecraft.util.MathHelper;

public class GuiSimpleScrolledSelectionListProxy extends GuiSlot {

	public static final int EaZy = 516;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final RealmsSimpleScrolledSelectionList field_178050_u;
	// private static final String __OBFID = "http://https://fuckuskid00001938";

	public GuiSimpleScrolledSelectionListProxy(final RealmsSimpleScrolledSelectionList p_i45525_1_,
			final int p_i45525_2_, final int p_i45525_3_, final int p_i45525_4_, final int p_i45525_5_,
			final int p_i45525_6_) {
		super(Minecraft.getMinecraft(), p_i45525_2_, p_i45525_3_, p_i45525_4_, p_i45525_5_, p_i45525_6_);
		field_178050_u = p_i45525_1_;
	}

	@Override
	protected int getSize() {
		return field_178050_u.getItemCount();
	}

	/**
	 * The element in the slot that was clicked, boolean for whether it was
	 * double clicked or not
	 */
	@Override
	protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
			final int mouseY) {
		field_178050_u.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
	}

	/**
	 * Returns true if the element passed in is currently selected
	 */
	@Override
	protected boolean isSelected(final int slotIndex) {
		return field_178050_u.isSelectedItem(slotIndex);
	}

	@Override
	protected void drawBackground() {
		field_178050_u.renderBackground();
	}

	@Override
	protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_, final int p_180791_4_,
			final int p_180791_5_, final int p_180791_6_) {
		field_178050_u.renderItem(p_180791_1_, p_180791_2_, p_180791_3_, p_180791_4_, p_180791_5_, p_180791_6_);
	}

	public int func_178048_e() {
		return super.width;
	}

	public int func_178047_f() {
		return super.mouseY;
	}

	public int func_178049_g() {
		return super.mouseX;
	}

	/**
	 * Return the height of the content being scrolled
	 */
	@Override
	protected int getContentHeight() {
		return field_178050_u.getMaxPosition();
	}

	@Override
	protected int getScrollBarX() {
		return field_178050_u.getScrollbarPosition();
	}

	@Override
	public void func_178039_p() {
		super.func_178039_p();
	}

	@Override
	public void drawScreen(final int p_148128_1_, final int p_148128_2_, final float p_148128_3_) {
		if (field_178041_q) {
			mouseX = p_148128_1_;
			mouseY = p_148128_2_;
			drawBackground();
			final int var4 = getScrollBarX();
			final int var5 = var4 + 6;
			bindAmountScrolled();
			GlStateManager.disableLighting();
			GlStateManager.disableFog();
			final Tessellator var6 = Tessellator.getInstance();
			final WorldRenderer var7 = var6.getWorldRenderer();
			final int var8 = left + width / 2 - getListWidth() / 2 + 2;
			final int var9 = top + 4 - (int) amountScrolled;

			if (hasListHeader) {
				drawListHeader(var8, var9, var6);
			}

			drawSelectionBox(var8, var9, p_148128_1_, p_148128_2_);
			GlStateManager.disableDepth();
			overlayBackground(0, top, 255, 255);
			overlayBackground(bottom, height, 255, 255);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO,
					GL11.GL_ONE);
			GlStateManager.disableAlpha();
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
			GlStateManager.disableTexture2D();
			final int var11 = func_148135_f();

			if (var11 > 0) {
				int var12 = (bottom - top) * (bottom - top) / getContentHeight();
				var12 = MathHelper.clamp_int(var12, 32, bottom - top - 8);
				int var13 = (int) amountScrolled * (bottom - top - var12) / var11 + top;

				if (var13 < top) {
					var13 = top;
				}

				var7.startDrawingQuads();
				var7.func_178974_a(0, 255);
				var7.addVertexWithUV(var4, bottom, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(var5, bottom, 0.0D, 1.0D, 1.0D);
				var7.addVertexWithUV(var5, top, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(var4, top, 0.0D, 0.0D, 0.0D);
				var6.draw();
				var7.startDrawingQuads();
				var7.func_178974_a(8421504, 255);
				var7.addVertexWithUV(var4, var13 + var12, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(var5, var13 + var12, 0.0D, 1.0D, 1.0D);
				var7.addVertexWithUV(var5, var13, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(var4, var13, 0.0D, 0.0D, 0.0D);
				var6.draw();
				var7.startDrawingQuads();
				var7.func_178974_a(12632256, 255);
				var7.addVertexWithUV(var4, var13 + var12 - 1, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(var5 - 1, var13 + var12 - 1, 0.0D, 1.0D, 1.0D);
				var7.addVertexWithUV(var5 - 1, var13, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(var4, var13, 0.0D, 0.0D, 0.0D);
				var6.draw();
			}

			func_148142_b(p_148128_1_, p_148128_2_);
			GlStateManager.enableTexture2D();
			GlStateManager.shadeModel(7424);
			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
		}
	}
}
