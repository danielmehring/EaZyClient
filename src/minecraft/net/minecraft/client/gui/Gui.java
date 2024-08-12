package net.minecraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class Gui {

	public static final int EaZy = 459;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static final ResourceLocation optionsBackground = new ResourceLocation(
			"textures/gui/options_background.png");
	public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
	public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
	protected float zLevel;

	/**
	 * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
	 */
	protected void drawHorizontalLine(int startX, int endX, final int y, final int color) {
		if (endX < startX) {
			final int var5 = startX;
			startX = endX;
			endX = var5;
		}

		drawRect(startX, y, endX + 1, y + 1, color);
	}

	/**
	 * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
	 */
	protected void drawVerticalLine(final int x, int startY, int endY, final int color) {
		if (endY < startY) {
			final int var5 = startY;
			startY = endY;
			endY = var5;
		}

		drawRect(x, startY + 1, x + 1, endY, color);
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color
	 * (ARGB format). Args: x1, y1, x2, y2, color
	 */
	public static void drawRect(int left, int top, int right, int bottom, final int color) {
		int var5;

		if (left < right) {
			var5 = left;
			left = right;
			right = var5;
		}

		if (top < bottom) {
			var5 = top;
			top = bottom;
			bottom = var5;
		}

		final float var11 = (color >> 24 & 255) / 255.0F;
		final float var6 = (color >> 16 & 255) / 255.0F;
		final float var7 = (color >> 8 & 255) / 255.0F;
		final float var8 = (color & 255) / 255.0F;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var6, var7, var8, var11);
		var10.startDrawingQuads();
		var10.addVertex(left, bottom, 0.0D);
		var10.addVertex(right, bottom, 0.0D);
		var10.addVertex(right, top, 0.0D);
		var10.addVertex(left, top, 0.0D);
		var9.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawRect(double left, double top, double right, double bottom, final int color) {
		double var5;

		if (left < right) {
			var5 = left;
			left = right;
			right = var5;
		}

		if (top < bottom) {
			var5 = top;
			top = bottom;
			bottom = var5;
		}

		final float var11 = (color >> 24 & 255) / 255.0F;
		final float var6 = (color >> 16 & 255) / 255.0F;
		final float var7 = (color >> 8 & 255) / 255.0F;
		final float var8 = (color & 255) / 255.0F;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(var6, var7, var8, var11);
		var10.startDrawingQuads();
		var10.addVertex(left, bottom, 0.0D);
		var10.addVertex(right, bottom, 0.0D);
		var10.addVertex(right, top, 0.0D);
		var10.addVertex(left, top, 0.0D);
		var9.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors
	 * (ARGB format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	protected void drawGradientRect(final int left, final int top, final int right, final int bottom,
			final int startColor, final int endColor) {
		final float var7 = (startColor >> 24 & 255) / 255.0F;
		final float var8 = (startColor >> 16 & 255) / 255.0F;
		final float var9 = (startColor >> 8 & 255) / 255.0F;
		final float var10 = (startColor & 255) / 255.0F;
		final float var11 = (endColor >> 24 & 255) / 255.0F;
		final float var12 = (endColor >> 16 & 255) / 255.0F;
		final float var13 = (endColor >> 8 & 255) / 255.0F;
		final float var14 = (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		final Tessellator var15 = Tessellator.getInstance();
		final WorldRenderer var16 = var15.getWorldRenderer();
		var16.startDrawingQuads();
		var16.func_178960_a(var8, var9, var10, var7);
		var16.addVertex(right, top, zLevel);
		var16.addVertex(left, top, zLevel);
		var16.func_178960_a(var12, var13, var14, var11);
		var16.addVertex(left, bottom, zLevel);
		var16.addVertex(right, bottom, zLevel);
		var15.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	/**
	 * Renders the specified text to the screen, center-aligned. Args :
	 * renderer, string, x, y, color
	 */
	public void drawCenteredString(final FontRenderer fontRendererIn, final String text, final int x, final int y,
			final int color) {
		fontRendererIn.func_175063_a(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
	}

	/**
	 * Renders the specified text to the screen. Args : renderer, string, x, y,
	 * color
	 */
	public static void drawString(final FontRenderer fontRendererIn, final String text, final int x, final int y,
			final int color) {
		fontRendererIn.func_175063_a(text, x, y, color);
	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v,
	 * width, height
	 */
	public void drawTexturedModalRect(final int x, final int y, final int textureX, final int textureY, final int width,
			final int height) {
		final float var7 = 0.00390625F;
		final float var8 = 0.00390625F;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		var10.startDrawingQuads();
		var10.addVertexWithUV(x + 0, y + height, zLevel, (textureX + 0) * var7, (textureY + height) * var8);
		var10.addVertexWithUV(x + width, y + height, zLevel, (textureX + width) * var7, (textureY + height) * var8);
		var10.addVertexWithUV(x + width, y + 0, zLevel, (textureX + width) * var7, (textureY + 0) * var8);
		var10.addVertexWithUV(x + 0, y + 0, zLevel, (textureX + 0) * var7, (textureY + 0) * var8);
		var9.draw();
	}

	public void func_175174_a(final float p_175174_1_, final float p_175174_2_, final int p_175174_3_,
			final int p_175174_4_, final int p_175174_5_, final int p_175174_6_) {
		final float var7 = 0.00390625F;
		final float var8 = 0.00390625F;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		var10.startDrawingQuads();
		var10.addVertexWithUV(p_175174_1_ + 0.0F, p_175174_2_ + p_175174_6_, zLevel, (p_175174_3_ + 0) * var7,
				(p_175174_4_ + p_175174_6_) * var8);
		var10.addVertexWithUV(p_175174_1_ + p_175174_5_, p_175174_2_ + p_175174_6_, zLevel,
				(p_175174_3_ + p_175174_5_) * var7, (p_175174_4_ + p_175174_6_) * var8);
		var10.addVertexWithUV(p_175174_1_ + p_175174_5_, p_175174_2_ + 0.0F, zLevel, (p_175174_3_ + p_175174_5_) * var7,
				(p_175174_4_ + 0) * var8);
		var10.addVertexWithUV(p_175174_1_ + 0.0F, p_175174_2_ + 0.0F, zLevel, (p_175174_3_ + 0) * var7,
				(p_175174_4_ + 0) * var8);
		var9.draw();
	}

	public void func_175175_a(final int p_175175_1_, final int p_175175_2_, final TextureAtlasSprite p_175175_3_,
			final int p_175175_4_, final int p_175175_5_) {
		final Tessellator var6 = Tessellator.getInstance();
		final WorldRenderer var7 = var6.getWorldRenderer();
		var7.startDrawingQuads();
		var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + p_175175_5_, zLevel, p_175175_3_.getMinU(),
				p_175175_3_.getMaxV());
		var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + p_175175_5_, zLevel, p_175175_3_.getMaxU(),
				p_175175_3_.getMaxV());
		var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + 0, zLevel, p_175175_3_.getMaxU(),
				p_175175_3_.getMinV());
		var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + 0, zLevel, p_175175_3_.getMinU(), p_175175_3_.getMinV());
		var6.draw();
	}

	/**
	 * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height,
	 * textureWidth, textureHeight
	 */
	public static void drawModalRectWithCustomSizedTexture(final int x, final int y, final float u, final float v,
			final int width, final int height, final float textureWidth, final float textureHeight) {
		final float var8 = 1.0F / textureWidth;
		final float var9 = 1.0F / textureHeight;
		final Tessellator var10 = Tessellator.getInstance();
		final WorldRenderer var11 = var10.getWorldRenderer();
		var11.startDrawingQuads();
		var11.addVertexWithUV(x, y + height, 0.0D, u * var8, (v + height) * var9);
		var11.addVertexWithUV(x + width, y + height, 0.0D, (u + width) * var8, (v + height) * var9);
		var11.addVertexWithUV(x + width, y, 0.0D, (u + width) * var8, v * var9);
		var11.addVertexWithUV(x, y, 0.0D, u * var8, v * var9);
		var10.draw();
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't
	 * used anywhere in vanilla code.
	 */
	public static void drawScaledCustomSizeModalRect(final int x, final int y, final float u, final float v,
			final int uWidth, final int vHeight, final int width, final int height, final float tileWidth,
			final float tileHeight) {
		final float var10 = 1.0F / tileWidth;
		final float var11 = 1.0F / tileHeight;
		final Tessellator var12 = Tessellator.getInstance();
		final WorldRenderer var13 = var12.getWorldRenderer();
		var13.startDrawingQuads();
		var13.addVertexWithUV(x, y + height, 0.0D, u * var10, (v + vHeight) * var11);
		var13.addVertexWithUV(x + width, y + height, 0.0D, (u + uWidth) * var10, (v + vHeight) * var11);
		var13.addVertexWithUV(x + width, y, 0.0D, (u + uWidth) * var10, v * var11);
		var13.addVertexWithUV(x, y, 0.0D, u * var10, v * var11);
		var12.draw();
	}
	
	public static void drawScaledCustomSizeModalRectMoving(final int x, final int y, final float u, final float v,
			final int uWidth, final int vHeight, final int width, final int height, final float tileWidth,
			final float tileHeight, final int mouseX, final int mouseY) {
		double maxMovementPercent = 0.005;
		final float var10 = 1.0F / tileWidth;
		final float var11 = 1.0F / tileHeight;
		final Tessellator var12 = Tessellator.getInstance();
		final WorldRenderer var13 = var12.getWorldRenderer();
		double maxMovementX = (width - x) * maxMovementPercent;
		double maxMovementY = (height - y) * maxMovementPercent;
		double moveX = (mouseX - ((x + width) / 2.0D)) * maxMovementPercent;
		double moveY = (mouseY - ((y + height) / 2.0D)) * maxMovementPercent;
		var13.startDrawingQuads();
		var13.addVertexWithUV(x - maxMovementX + moveX, y + height + maxMovementY + moveY, 0.0D, u * var10, (v + vHeight) * var11);
		var13.addVertexWithUV(x + width + maxMovementX + moveX, y + height + maxMovementY + moveY, 0.0D, (u + uWidth) * var10, (v + vHeight) * var11);
		var13.addVertexWithUV(x + width + maxMovementX + moveX, y - maxMovementY + moveY, 0.0D, (u + uWidth) * var10, v * var11);
		var13.addVertexWithUV(x - maxMovementX + moveX, y - maxMovementY + moveY, 0.0D, u * var10, v * var11);
		var12.draw();
	}
	
}
