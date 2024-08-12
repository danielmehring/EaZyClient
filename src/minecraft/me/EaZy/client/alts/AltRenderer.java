/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.opengl.GL11
 */
package me.EaZy.client.alts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

public class AltRenderer {

    public static final int EaZy = 25;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static void drawAltFace(final String name, final int x, final int y, final int w, final int h,
            final boolean selected) {
        try {
            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name)
                    .loadTexture(Minecraft.getResourceManager());

            Minecraft.getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
            final Tessellator var3 = Tessellator.getInstance();
            final WorldRenderer var4 = var3.getWorldRenderer();
            GL11.glEnable(GL11.GL_BLEND);
            if (selected) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            } else {
                GL11.glColor4f(0.9f, 0.9f, 0.9f, 1.0f);
            }
            double fw = 32.0;
            double fh = 32.0;
            double u = 32.0;
            double v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV(x + 0.0, (double) y + (double) h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV((double) x + (double) w, (double) y + (double) h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV((double) x + (double) w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            fw = 32.0;
            fh = 32.0;
            u = 160.0;
            v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV(x + 0.0, (double) y + (double) h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV((double) x + (double) w, (double) y + (double) h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV((double) x + (double) w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            GL11.glDisable(GL11.GL_BLEND);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawAltBody(final String name, int x, int y, final int width, final int height) {
        try {

            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name)
                    .loadTexture(Minecraft.getResourceManager());

            Minecraft.getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
            final boolean slim = DefaultPlayerSkin.getModelNameFromUUID(EntityPlayer.getUUIDFromPlayerName(name))
                    .equals("slim");
            final Tessellator var3 = Tessellator.getInstance();
            final WorldRenderer var4 = var3.getWorldRenderer();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            double w = width / 2;
            double h = height / 4;
            double fw = 32.0;
            double fh = 32.0;
            double u = 32.0;
            double v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 4) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 4;
            fw = 32.0;
            fh = 32.0;
            u = 160.0;
            v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 8 * 3;
            fw = 32.0;
            fh = 48.0;
            u = 80.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += height / 4) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 8 * 3;
            fw = 32.0;
            fh = 48.0;
            u = 80.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = 176.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x -= width / 16 * (slim ? 3 : 4)) + 0.0, (y += slim ? height / 32 : 0) + h, 0.0,
                    (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = 176.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = 176.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 16 * (slim ? 11 : 12)) + 0.0, (y += 0) + h, 0.0,
                    (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = 176.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 16.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x -= width / 2) + 0.0, (y += height / 32 * (slim ? 11 : 12)) + h, 0.0,
                    (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 16.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 16.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 4) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 16.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            GL11.glDisable(GL11.GL_BLEND);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawAltBack(final String name, int x, int y, final int width, final int height) {
        try {

            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name)
                    .loadTexture(Minecraft.getResourceManager());

            Minecraft.getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
            final boolean slim = DefaultPlayerSkin.getModelNameFromUUID(EntityPlayer.getUUIDFromPlayerName(name))
                    .equals("slim");
            final Tessellator var3 = Tessellator.getInstance();
            final WorldRenderer var4 = var3.getWorldRenderer();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            double w = width / 2;
            double h = height / 4;
            double fw = 32.0;
            double fh = 32.0;
            double u = 96.0;
            double v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 4) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 4;
            fw = 32.0;
            fh = 32.0;
            u = 224.0;
            v = 32.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 8 * 3;
            fw = 32.0;
            fh = 48.0;
            u = 128.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += height / 4) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 2;
            h = height / 8 * 3;
            fw = 32.0;
            fh = 48.0;
            u = 128.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = slim ? 204 : 208;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x -= width / 16 * (slim ? 3 : 4)) + 0.0, (y += slim ? height / 32 : 0) + h, 0.0,
                    (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = slim ? 204 : 208;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = slim ? 204 : 208;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 16 * (slim ? 11 : 12)) + 0.0, (y += 0) + h, 0.0,
                    (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 16 * (slim ? 3 : 4);
            h = height / 8 * 3;
            fw = slim ? 12 : 16;
            fh = 48.0;
            u = slim ? 204 : 208;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 48.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x -= width / 2) + 0.0, (y += height / 32 * (slim ? 11 : 12)) + h, 0.0,
                    (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 48.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 48.0;
            v = 80.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += width / 4) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            w = width / 4;
            h = height / 8 * 3;
            fw = 16.0;
            fh = 48.0;
            u = 48.0;
            v = 144.0;
            var4.startDrawingQuads();
            var4.addVertexWithUV((x += 0) + 0.0, (y += 0) + h, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + h, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + fh) * 0.00390625f);
            var4.addVertexWithUV(x + w, y + 0.0, 0.0, (float) (u + 0.0) * 0.00390625f, (float) (v + 0.0) * 0.00390625f);
            var4.addVertexWithUV(x + 0.0, y + 0.0, 0.0, (float) (u + fw) * 0.00390625f,
                    (float) (v + 0.0) * 0.00390625f);
            var3.draw();
            GL11.glDisable(GL11.GL_BLEND);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
