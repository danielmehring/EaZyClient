package me.EaZy.client.utils;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.awt.Color;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.EnumFadeDirection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class RenderUtils {

	public static final int EaZy = 241;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static void drawFadeRect(final double x, final double y, final double toX, final double toY, final int color,
			final int alphaStart, final int alphaEnd, final int extension, final EnumFadeDirection dir) {
		if (alphaStart > 255 || alphaStart < 0) {
			System.out.println("RenderUtils.drawFadeRect(): alphaStart must be between 0 and 255!");
			return;
		}
		if (alphaEnd > 255 || alphaEnd < 0) {
			System.out.println("RenderUtils.drawFadeRect(): alphaEnd must be between 0 and 255!");
			return;
		}
		if (alphaEnd - alphaStart < 0) {
			System.out.println("RenderUtils.drawFadeRect(): alphaStart is lower then alphaEnd!");
			return;
		}
		final Tessellator var6 = Tessellator.getInstance();
		final WorldRenderer var7 = var6.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
		GlStateManager.disableAlpha();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableTexture2D();
		if (null != dir) {
			switch (dir) {
			case DOWN:
				var7.startDrawingQuads();
				var7.setColorRGBA_I(color, alphaStart);
				var7.addVertexWithUV(x - extension, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(toX + extension, toY, 0.0D, 1.0D, 1.0D);
				var7.setColorRGBA_I(color, alphaEnd);
				var7.addVertexWithUV(toX, y, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
				var6.draw();
				break;
			case UP:
				var7.startDrawingQuads();
				var7.setColorRGBA_I(color, alphaStart);
				var7.addVertexWithUV(x - extension, y, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(toX + extension, y, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA_I(color, alphaEnd);
				var7.addVertexWithUV(toX, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(x, toY, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			case RIGHT:
				var7.startDrawingQuads();
				var7.setColorRGBA_I(color, alphaStart);
				var7.addVertexWithUV(toX, y - extension, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(toX, toY + extension, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA_I(color, alphaEnd);
				var7.addVertexWithUV(x, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(x, y, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			case LEFT:
				var7.startDrawingQuads();
				var7.setColorRGBA_I(color, alphaStart);
				var7.addVertexWithUV(x, y - extension, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(x, toY + extension, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA_I(color, alphaEnd);
				var7.addVertexWithUV(toX, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(toX, y, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			default:
				break;
			}
		}
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawFadeRect(final double x, final double y, final double toX, final double toY,
			final int colorStart, final int colorEnd, final int extension, final EnumFadeDirection dir) {
		final Tessellator var6 = Tessellator.getInstance();
		final WorldRenderer var7 = var6.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
		GlStateManager.disableAlpha();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableTexture2D();
		if (null != dir) {
			switch (dir) {
			case DOWN:
				var7.startDrawingQuads();
				var7.setColorRGBA(colorStart);
				var7.addVertexWithUV(x - extension, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(toX + extension, toY, 0.0D, 1.0D, 1.0D);
				var7.setColorRGBA(colorEnd);
				var7.addVertexWithUV(toX, y, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
				var6.draw();
				break;
			case UP:
				var7.startDrawingQuads();
				var7.setColorRGBA(colorStart);
				var7.addVertexWithUV(x - extension, y, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(toX + extension, y, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA(colorEnd);
				var7.addVertexWithUV(toX, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(x, toY, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			case RIGHT:
				var7.startDrawingQuads();
				var7.setColorRGBA(colorStart);
				var7.addVertexWithUV(toX, y - extension, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(toX, toY + extension, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA(colorEnd);
				var7.addVertexWithUV(x, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(x, y, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			case LEFT:
				var7.startDrawingQuads();
				var7.setColorRGBA(colorStart);
				var7.addVertexWithUV(x, y - extension, 0.0D, 1.0D, 0.0D);
				var7.addVertexWithUV(x, toY + extension, 0.0D, 0.0D, 0.0D);
				var7.setColorRGBA(colorEnd);
				var7.addVertexWithUV(toX, toY, 0.0D, 0.0D, 1.0D);
				var7.addVertexWithUV(toX, y, 0.0D, 1.0D, 1.0D);
				var6.draw();
				break;
			default:
				break;
			}
		}
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static String getLogInfo(final int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
	}

	public static void drawBorderedCircle(double x2, double y2, float radius, final int outsideC, final int insideC) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		final float scale = 0.1f;
		GL11.glScalef(scale, scale, scale);
		x2 = (int) (x2 * (1.0f / scale));
		y2 = (int) (y2 * (1.0f / scale));
		RenderUtils.drawCircle(x2, y2, radius *= 1.0f / scale, insideC);
		RenderUtils.drawHollowCircle(x2, y2, radius, 1.0f, outsideC);
		GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawSplitBorderedCircle(double x2, double y2, float radius, final int rot1, final int rot2,
			final int outsideC, final int insideC) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glPushMatrix();
		final float scale = 0.1f;
		GL11.glScalef(scale, scale, scale);
		x2 = (int) (x2 * (1.0f / scale));
		y2 = (int) (y2 * (1.0f / scale));
		RenderUtils.drawSplitCircle(x2, y2, radius *= 1.01f / scale, rot1, rot2, insideC);
		GL11.glScalef(1.0f / scale, 1.0f / scale, 1.0f / scale);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawHollowCircle(final double x2, final double y2, final float radius, final float lineWidth,
			final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(lineWidth);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		int i2 = 0;
		while (i2 <= 360) {
			GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * radius,
					y2 + Math.cos(i2 * 3.1415926 / 180.0) * radius);
			++i2;
		}
		GL11.glEnd();
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawSplitCircle(final double x2, final double y2, final float radius, final int rot1,
			final int rot2, final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_POLYGON);
		int i2 = rot1;
		while (i2 <= rot2) {
			GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * radius,
					y2 + Math.cos(i2 * 3.1415926 / 180.0) * radius);
			++i2;
		}
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
	}

	public static void drawLoadingCircle(final double x2, final double y2, final float radius, int rot1, int rot2,
			final float lineSize, final float lineWidth, final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;

		rot1 += 180;
		rot2 += 180;

		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		int i2 = rot1;
		GL11.glVertex2d(x2 + Math.sin(rot1 * 3.1415926 / 180.0) * (radius - lineSize),
				y2 + Math.cos(rot1 * 3.1415926 / 180.0) * (radius - lineSize));
		while (i2 <= rot2) {
			GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * radius,
					y2 + Math.cos(i2 * 3.1415926 / 180.0) * radius);
			++i2;
		}
		GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * radius, y2 + Math.cos(i2 * 3.1415926 / 180.0) * radius);
		GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * (radius - lineSize),
				y2 + Math.cos(i2 * 3.1415926 / 180.0) * (radius - lineSize));
		while (i2 > rot1) {
			GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * (radius - lineSize),
					y2 + Math.cos(i2 * 3.1415926 / 180.0) * (radius - lineSize));
			--i2;
		}
		GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * (radius - lineSize),
				y2 + Math.cos(i2 * 3.1415926 / 180.0) * (radius - lineSize));

		GL11.glEnd();
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawLine(final double x, final double y, final double x2, final double y2, final float lineWidth,
			final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glLineWidth(lineWidth);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawCircle(final double x2, final double y2, final float radius, final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_POLYGON);
		int i2 = 0;
		while (i2 <= 360) {
			GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * radius,
					y2 + Math.cos(i2 * 3.1415926 / 180.0) * radius);
			++i2;
		}
		GL11.glEnd();
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawMoreCircles(final double x2, final double y2, final float radius, final int linesAbstand,
			final float lineWidth, final int count, final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GL11.glPushMatrix();
		GL11.glEnable(GL_BLEND);
		GL11.glEnable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		int i = 0;
		while (i < count) {
			GL11.glBegin(GL11.GL_LINE_LOOP);
			int i2 = 0;
			while (i2 <= 360) {
				GL11.glVertex2d(x2 + Math.sin(i2 * 3.1415926 / 180.0) * (radius - (linesAbstand * i)),
						y2 + Math.cos(i2 * 3.1415926 / 180.0) * (radius - (linesAbstand * i)));
				++i2;
			}
			GL11.glEnd();
			i++;
		}
		GL11.glEnable(GL_TEXTURE_2D);
		GL11.glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(GL_BLEND);
		GL11.glPopMatrix();
	}

	public static ScaledResolution newScaledResolution() {
		return new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth, Minecraft.displayHeight);
	}

	public static void drawVerticalLine(final double x, final double y, final double endY, final int color) {
		// RenderUtils.drawRect(x, y, x + 1, height, color);

		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		var10.startDrawing(2);
		var10.addVertex(x, y, 0.0);
		var10.addVertex(x, endY, 0.0);
		var9.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

	}

	public static void drawHorizontalLine(final double x, final double y, final double width, final int color) {
		RenderUtils.drawRect(x, y, width, y + 1, color);
	}

	public static void drawFineBorderedRect(double x, double y, double x1, double y1, final int bord, final int color) {
		GL11.glScaled(0.5, 0.5, 0.5);
		RenderUtils.drawRect((x *= 2) + 1, (y *= 2) + 1, x1 *= 2, y1 *= 2, color);
		RenderUtils.drawVerticalLine(x, y, y1, bord);
		RenderUtils.drawVerticalLine(x1, y, y1, bord);
		RenderUtils.drawHorizontalLine(x + 1, y, x1, bord);
		RenderUtils.drawHorizontalLine(x, y1, x1 + 1, bord);
		GL11.glScaled(2.0, 2.0, 2.0);
	}

	public static void drawHollowRect(final double left, final double top, final double right, final double bottom,
			final float borderWidth, final int borderColor) {
		final float alpha = (borderColor >> 24 & 255) / 255.0f;
		final float red = (borderColor >> 16 & 255) / 255.0f;
		final float green = (borderColor >> 8 & 255) / 255.0f;
		final float blue = (borderColor & 255) / 255.0f;
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(borderWidth);
		final Tessellator tessellator = Tessellator.getInstance();
		final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.startDrawing(1);
		worldRenderer.addVertex(left, top, 0.0);
		worldRenderer.addVertex(left, bottom, 0.0);
		worldRenderer.addVertex(right, bottom, 0.0);
		worldRenderer.addVertex(right, top, 0.0);
		worldRenderer.addVertex(left, top, 0.0);
		worldRenderer.addVertex(right, top, 0.0);
		worldRenderer.addVertex(left, bottom, 0.0);
		worldRenderer.addVertex(right, bottom, 0.0);
		tessellator.draw();
		GL11.glLineWidth(2.0f);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void tracerLine(final Entity entity, final int mode) {
		GlStateManager.loadIdentity();
		final boolean temp = Minecraft.gameSettings.viewBobbing;
		Minecraft.gameSettings.viewBobbing = false;
		Minecraft.entityRenderer.setupCameraTransform(Minecraft.timer.renderPartialTicks, 2);
		Minecraft.gameSettings.viewBobbing = temp;
		final Tessellator t = Tessellator.getInstance();
		final WorldRenderer wr = t.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1.0f);
		switch (mode) {
		case 0:
			GL11.glColor4d(1.0f - Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f,
					Minecraft.thePlayer.getDistanceToEntity(entity) / 40.0f, 0.0, 0.5);
			break;
		case 1:
			GL11.glColor4d(0.0, 0.0, 1.0, 0.5);
			break;
		case 2:
			GL11.glColor4d(1.0, 1.0, 0.0, 0.5);
			break;
		case 3:
			GL11.glColor4d(1.0, 0.0, 0.0, 0.5);
			break;
		case 4:
			GL11.glColor4d(0.0, 1.0, 0.0, 0.5);
			break;
		default:
			break;
		}
		final double[] pos = EntityUtil.getEntityRenderPos(entity);
		final double x = pos[0];
		final double y = pos[1] + entity.height / 2.0f;
		final double z = pos[2];
		final int draw = 1;
		wr.startDrawing(draw);
		wr.addVertex(0.0, Minecraft.thePlayer.getEyeHeight(), 0.0);
		wr.addVertex(x, y, z);
		t.draw();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawColorBox(final AxisAlignedBB axisalignedbb) {
		final Tessellator ts = Tessellator.getInstance();
		final WorldRenderer wr = ts.getWorldRenderer();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		ts.draw();
		wr.startDrawingQuads();
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
		wr.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		ts.draw();
	}

	public static void drawRect(final double left, final double top, final double right, final double bottom,
			final int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		final Tessellator var9 = Tessellator.getInstance();
		final WorldRenderer var10 = var9.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		var10.startDrawingQuads();
		var10.addVertex(left, bottom, 0.0);
		var10.addVertex(right, bottom, 0.0);
		var10.addVertex(right, top, 0.0);
		var10.addVertex(left, top, 0.0);
		var9.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	public static void drawBorderedRect(final double left, final double top, final double right, final double bottom,
			final float borderWidth, final int borderColor, final int color) {
		final float alpha = (borderColor >> 24 & 255) / 255.0f;
		final float red = (borderColor >> 16 & 255) / 255.0f;
		final float green = (borderColor >> 8 & 255) / 255.0f;
		final float blue = (borderColor & 255) / 255.0f;
		GlStateManager.pushMatrix();
		RenderUtils.drawRect(left, top, right, bottom, color);
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		if (borderWidth == 1.0f) {
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
		}
		GL11.glLineWidth(borderWidth);
		final Tessellator tessellator = Tessellator.getInstance();
		final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.startDrawing(1);
		worldRenderer.addVertex(left, top, 0.0);
		worldRenderer.addVertex(left, bottom, 0.0);
		worldRenderer.addVertex(right, bottom, 0.0);
		worldRenderer.addVertex(right, top, 0.0);
		worldRenderer.addVertex(left, top, 0.0);
		worldRenderer.addVertex(right, top, 0.0);
		worldRenderer.addVertex(left, bottom, 0.0);
		worldRenderer.addVertex(right, bottom, 0.0);
		tessellator.draw();
		GL11.glLineWidth(2.0f);
		if (borderWidth == 1.0f) {
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
		}
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void drawAbgerundetRect(final double left, final double top, final double right, final double bottom,
			final int rounding, final int color) {
		if (rounding > right - left || rounding > bottom - top) {
			System.out.println("drawAbgerundetRect: rounding can not be greater then right - left or bottom - top");
			return;
		}
		final double ileft = left + rounding;
		final double itop = top + rounding;
		final double iright = right - rounding;
		final double ibottom = bottom - rounding;
		GlStateManager.pushMatrix();
		RenderUtils.drawSplitBorderedCircle((int) ileft, (int) itop, rounding - 0.5f, 180, 270, color, color);
		RenderUtils.drawSplitBorderedCircle((int) iright, (int) itop, rounding - 0.5f, 90, 180, color, color);
		RenderUtils.drawSplitBorderedCircle((int) ileft, (int) ibottom, rounding - 0.5f, 270, 360, color, color);
		RenderUtils.drawSplitBorderedCircle((int) iright, (int) ibottom, rounding - 0.5f, 0, 90, color, color);
		RenderUtils.drawRect(ileft, itop, iright, ibottom, color);
		RenderUtils.drawRect(left, itop, ileft, ibottom, color);
		RenderUtils.drawRect(iright, itop, right, ibottom, color);
		RenderUtils.drawRect(ileft, top, iright, itop, color);
		RenderUtils.drawRect(ileft, ibottom, iright, bottom, color);
		GlStateManager.popMatrix();
	}

	public static void drawBorderedRect(final double left, final double top, final double right, final double bottom,
			final int borderColor, final int color) {
		RenderUtils.drawBorderedRect(left, top, right, bottom, 1.0f, borderColor, color);
	}

	public static void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
		final Tessellator tessellator = Tessellator.getInstance();
		final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.startDrawing(3);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		tessellator.draw();
		worldRenderer.startDrawing(3);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		tessellator.draw();
		worldRenderer.startDrawing(1);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.draw();
	}

	public static void drawBoundingBox(final AxisAlignedBB aa) {
		final Tessellator tessellator = Tessellator.getInstance();
		final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		tessellator.draw();
		worldRenderer.startDrawingQuads();
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
		worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
		worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
		worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
		tessellator.draw();
	}

	public static void drawOutlinedBlockESP(final double x, final double y, final double z, final float red,
			final float green, final float blue, final float alpha, final float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(final double x, final double y, final double z, final double width,
			final double height, final Color color, final float alpha, final float lineRed, final float lineGreen,
			final float lineBlue, final float lineAlpha, final float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255,
				alpha);
		RenderUtils.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		RenderUtils
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(final double x, final double y, final double z, final double width,
			final double height, final Color color, final float alpha, final Color lineColor, final float lineAlpha,
			final float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255,
				alpha);
		RenderUtils.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f((float) lineColor.getRed() / 255, (float) lineColor.getGreen() / 255,
				(float) lineColor.getBlue() / 255, lineAlpha);
		RenderUtils
				.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, int color) {
		final float alpha = (color >> 24 & 255) / 255.0f;
		final float red = (color >> 16 & 255) / 255.0f;
		final float green = (color >> 8 & 255) / 255.0f;
		final float blue = (color & 255) / 255.0f;
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(red, green, blue, alpha);
		GL11.glBegin(5);
		GL11.glVertex2d(x1, y1);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x3, y3);
		GL11.glVertex2d(x1, y1);
		GL11.glEnd();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
}
