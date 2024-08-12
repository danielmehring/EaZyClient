/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.opengl.GL11
 */
package me.EaZy.client.utils;

import java.awt.Color;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class R2DUtils {

    public static final int EaZy = 238;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static ScaledResolution scaledResolution;

    private static void enableGL2D() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
    }

    private static void disableGL2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
    }

    public static void drawRect(final Rectangle rectangle, final int color) {
        R2DUtils.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height,
                color);
    }

    private static void drawRect(final float x2, final float y2, final float x1, final float y1, final int color) {
        R2DUtils.enableGL2D();
        R2DUtils.glColor(color);
        R2DUtils.drawRect(x2, y2, x1, y1);
        R2DUtils.disableGL2D();
    }

    public static void drawBorderedRect(final float x2, final float y2, final float x1, final float y1,
            final float width, final int internalColor, final int borderColor) {
        R2DUtils.enableGL2D();
        R2DUtils.glColor(internalColor);
        R2DUtils.drawRect(x2 + width, y2 + width, x1 - width, y1 - width);
        R2DUtils.glColor(borderColor);
        R2DUtils.drawRect(x2 + width, y2, x1 - width, y2 + width);
        R2DUtils.drawRect(x2, y2, x2 + width, y1);
        R2DUtils.drawRect(x1 - width, y2, x1, y1);
        R2DUtils.drawRect(x2 + width, y1 - width, x1 - width, y1);
        R2DUtils.disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x1, float y1, final int insideC, final int borderC) {
        R2DUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        R2DUtils.drawVLine(x2 *= 2.0f, y2 *= 2.0f, y1 *= 2.0f, borderC);
        R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y2, y1, borderC);
        R2DUtils.drawHLine(x2, x1 - 1.0f, y2, borderC);
        R2DUtils.drawHLine(x2, x1 - 2.0f, y1 - 1.0f, borderC);
        R2DUtils.drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        R2DUtils.disableGL2D();
    }

    public static void drawBorderedRectReliant(final float x2, final float y2, final float x1, final float y1,
            final float lineWidth, final int inside, final int border) {
        R2DUtils.enableGL2D();
        R2DUtils.drawRect(x2, y2, x1, y1, inside);
        R2DUtils.glColor(border);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        R2DUtils.disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(final float x2, final float y2, final float x1, final float y1,
            final float lineWidth, final int border, final int bottom, final int top) {
        R2DUtils.enableGL2D();
        R2DUtils.drawGradientRect(x2, y2, x1, y1, top, bottom);
        R2DUtils.glColor(border);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        R2DUtils.disableGL2D();
    }

    public static void drawRoundedRect(float x2, float y2, float x1, float y1, final int borderC, final int insideC) {
        R2DUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        R2DUtils.drawVLine(x2 *= 2.0f, (y2 *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y2 + 1.0f, y1 - 2.0f, borderC);
        R2DUtils.drawHLine(x2 + 2.0f, x1 - 3.0f, y2, borderC);
        R2DUtils.drawHLine(x2 + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        R2DUtils.drawHLine(x2 + 1.0f, x2 + 1.0f, y2 + 1.0f, borderC);
        R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y2 + 1.0f, borderC);
        R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        R2DUtils.drawHLine(x2 + 1.0f, x2 + 1.0f, y1 - 2.0f, borderC);
        R2DUtils.drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        R2DUtils.disableGL2D();
    }

    public static void drawBorderedRect(final Rectangle rectangle, final float width, final int internalColor,
            final int borderColor) {
        final float x2 = rectangle.x;
        final float y2 = rectangle.y;
        final float x22 = rectangle.x + rectangle.width;
        final float y22 = rectangle.y + rectangle.height;
        R2DUtils.enableGL2D();
        R2DUtils.glColor(internalColor);
        R2DUtils.drawRect(x2 + width, y2 + width, x22 - width, y22 - width);
        R2DUtils.glColor(borderColor);
        R2DUtils.drawRect(x2 + 1.0f, y2, x22 - 1.0f, y2 + width);
        R2DUtils.drawRect(x2, y2, x2 + width, y22);
        R2DUtils.drawRect(x22 - width, y2, x22, y22);
        R2DUtils.drawRect(x2 + 1.0f, y22 - width, x22 - 1.0f, y22);
        R2DUtils.disableGL2D();
    }

    private static void drawGradientRect(final float x2, final float y2, final float x1, final float y1,
            final int topColor, final int bottomColor) {
        R2DUtils.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(GL11.GL_QUADS);
        R2DUtils.glColor(topColor);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        R2DUtils.glColor(bottomColor);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        R2DUtils.disableGL2D();
    }

    public static void drawGradientHRect(final float x2, final float y2, final float x1, final float y1,
            final int topColor, final int bottomColor) {
        R2DUtils.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(GL11.GL_QUADS);
        R2DUtils.glColor(topColor);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        R2DUtils.glColor(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        R2DUtils.disableGL2D();
    }

    private static void drawGradientRect(final double x2, final double y2, final double x22, final double y22,
            final int col1, final int col2) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_QUADS);
        R2DUtils.glColor(col1);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        R2DUtils.glColor(col2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(7424);
    }

    public static void drawGradientBorderedRect(final double x2, final double y2, final double x22, final double y22,
            final float l1, final int col1, final int col2, final int col3) {
        R2DUtils.enableGL2D();
        GL11.glPushMatrix();
        R2DUtils.glColor(col1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        R2DUtils.drawGradientRect(x2, y2, x22, y22, col2, col3);
        R2DUtils.disableGL2D();
    }

    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f);
    }

    public static void glColor(final int hex) {
        final float alpha = (hex >> 24 & 255) / 255.0f;
        final float red = (hex >> 16 & 255) / 255.0f;
        final float green = (hex >> 8 & 255) / 255.0f;
        final float blue = (hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(final float alpha, final int redRGB, final int greenRGB, final int blueRGB) {
        final float red = 0.003921569f * redRGB;
        final float green = 0.003921569f * greenRGB;
        final float blue = 0.003921569f * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawStrip(final int x2, final int y2, final float width, final double angle, final float points,
            final float radius, final int color) {
        float xc2;
        int i2;
        float yc2;
        float a2;
        final float f1 = (color >> 24 & 255) / 255.0f;
        final float f2 = (color >> 16 & 255) / 255.0f;
        final float f3 = (color >> 8 & 255) / 255.0f;
        final float f4 = (color & 255) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated(x2, y2, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            i2 = 0;
            while (i2 < angle) {
                a2 = (float) (i2 * (angle * 3.141592653589793 / points));
                xc2 = (float) (Math.cos(a2) * radius);
                yc2 = (float) (Math.sin(a2) * radius);
                GL11.glVertex2f(xc2, yc2);
                ++i2;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            i2 = 0;
            while (i2 > angle) {
                a2 = (float) (i2 * (angle * 3.141592653589793 / points));
                xc2 = (float) (Math.cos(a2) * -radius);
                yc2 = (float) (Math.sin(a2) * -radius);
                GL11.glVertex2f(xc2, yc2);
                --i2;
            }
            GL11.glEnd();
        }
        R2DUtils.disableGL2D();
        GL11.glDisable(GL11.GL_MAP1_VERTEX_3);
        GL11.glPopMatrix();
    }

    private static void drawHLine(float x2, float y2, final float x1, final int y1) {
        if (y2 < x2) {
            final float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        R2DUtils.drawRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1);
    }

    private static void drawVLine(final float x2, float y2, float x1, final int y1) {
        if (x1 < y2) {
            final float var5 = y2;
            y2 = x1;
            x1 = var5;
        }
        R2DUtils.drawRect(x2, y2 + 1.0f, x2 + 1.0f, x1, y1);
    }

    public static void drawHLine(float x2, float y2, final float x1, final int y1, final int y22) {
        if (y2 < x2) {
            final float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        R2DUtils.drawGradientRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1, y22);
    }

    public static void drawRect(final float x2, final float y2, final float x1, final float y1, final float r2,
            final float g2, final float b2, final float a2) {
        R2DUtils.enableGL2D();
        GL11.glColor4f(r2, g2, b2, a2);
        R2DUtils.drawRect(x2, y2, x1, y1);
        R2DUtils.disableGL2D();
    }

    private static void drawRect(final float x2, final float y2, final float x1, final float y1) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
    }

    public static void drawCircle(float cx, float cy2, float r2, final int num_segments, final int c2) {
        cx *= 2.0f;
        cy2 *= 2.0f;
        final float f2 = (c2 >> 24 & 255) / 255.0f;
        final float f22 = (c2 >> 16 & 255) / 255.0f;
        final float f3 = (c2 >> 8 & 255) / 255.0f;
        final float f4 = (c2 & 255) / 255.0f;
        final float theta = (float) (6.2831852 / num_segments);
        final float p2 = (float) Math.cos(theta);
        final float s = (float) Math.sin(theta);
        float x2 = r2 *= 2.0f;
        float y2 = 0.0f;
        R2DUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        int ii2 = 0;
        while (ii2 < num_segments) {
            GL11.glVertex2f(x2 + cx, y2 + cy2);
            final float t = x2;
            x2 = p2 * x2 - s * y2;
            y2 = s * t + p2 * y2;
            ++ii2;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        R2DUtils.disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy2, double r2, final int c2) {
        r2 *= 2.0;
        cx *= 2;
        cy2 *= 2;
        final float f2 = (c2 >> 24 & 255) / 255.0f;
        final float f22 = (c2 >> 16 & 255) / 255.0f;
        final float f3 = (c2 >> 8 & 255) / 255.0f;
        final float f4 = (c2 & 255) / 255.0f;
        R2DUtils.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        int i2 = 0;
        while (i2 <= 360) {
            final double x2 = Math.sin(i2 * 3.141592653589793 / 180.0) * r2;
            final double y2 = Math.cos(i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d(cx + x2, cy2 + y2);
            ++i2;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        R2DUtils.disableGL2D();
    }

    public static void drawSmallString(final String s, final int x2, final int y2, final int color) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(s, x2 * 2, y2 * 2, color);
        GL11.glPopMatrix();
    }

    public static void drawLargeString(final String text, int x2, final int y2, final int color) {
        GL11.glPushMatrix();
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x2 *= 2, y2, color);
        GL11.glPopMatrix();
    }

    public static ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}
