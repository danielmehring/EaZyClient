package me.EaZy.client.gui;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.main.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.geom.Rectangle;

public class GuiCookieClicker extends GuiScreen {

    public static final int EaZy = 67;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    private static final ResourceLocation cookie = new ResourceLocation("EaZy/cookie.png");
    Rectangle rect = new Rectangle(157, 46, 168, 145);

    private int saveDelay = 0;

    public GuiCookieClicker(final GuiScreen prevMenu) {
        this.prevMenu = prevMenu;
    }

    @Override
    public void updateScreen() {
        if (saveDelay < 300) {
            saveDelay++;
        } else {
            FileManager.saveMain();
            saveDelay = 0;
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 30, "Cancel"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            if (clickedButton.id == 0) {
                FileManager.saveMain();
                mc.displayGuiScreen(prevMenu);
            }
        }
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 28 || par2 == 156) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int button) throws IOException {

        if (button == 0) {

            if (rect.contains(x, y)) {
                Configs.cookies++;
            }

        }

        super.mouseClicked(x, y, button);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {

        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
                Minecraft.displayHeight);
        drawDefaultBackground(par1, par2);

        drawRect(0, 0, sr.getScaledWidth() * 2, 20, 0xb7111111);
        // drawRect(0, 0, sr.getScaledWidth() * 2, 20, 0xb7111111);
        // TODO Draw down rect
        drawRect(0, sr.getScaledHeight(), sr.getScaledWidth() * 2, sr.getScaledHeight() - 20, 0xb7111111);
        // drawRect(0, sr.getScaledHeight(), sr.getScaledWidth() * 2,
        // sr.getScaledHeight() - 20, 0xb7111111);

        drawRect(0, +20, sr.getScaledWidth(), +21, Client.getColor(0l).getRGB());
        drawRect(0, sr.getScaledHeight() - 20, sr.getScaledWidth(), sr.getScaledHeight() - 21,
                Client.getColor(0l).getRGB());

        GL11.glPushMatrix();
        GL11.glTranslated(sr.getScaledWidth() / 2 - 70, 0, 42);
        GL11.glScaled(2.5f, 2.5f, 42);
        drawString(fontRendererObj, "CohkieKliehcka", 0, 0, 0xFFFFFFFF);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(width / 2 - 242, height / 2 - 170, 0);

        if (!Mouse.isButtonDown(0)) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColorMask(true, true, true, false);

            GlStateManager.color(1.0f, 1.0f, 0.0f, 1.0f);

            Minecraft.getTextureManager().bindTexture(cookie);

            GL11.glPushMatrix();
            {
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                drawTexturedModalRect(193, 30, 0, 0, 256, 256);

            }
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_BLEND);
        } else if (Mouse.isButtonDown(0) && rect.contains(par1, par2)) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColorMask(true, true, true, false);

            GlStateManager.color(1.0f, 1.0f, 0.0f, 1.0f);

            Minecraft.getTextureManager().bindTexture(cookie);

            GL11.glPushMatrix();
            {
                GL11.glScalef(0.7f, 0.7f, 0.7f);
                drawTexturedModalRect(215, 39, 0, 0, 256, 256);

            }
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_BLEND);
        } else {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColorMask(true, true, true, false);

            GlStateManager.color(1.0f, 1.0f, 0.0f, 1.0f);

            Minecraft.getTextureManager().bindTexture(cookie);

            GL11.glPushMatrix();
            {
                GL11.glScalef(0.75f, 0.75f, 0.75f);
                drawTexturedModalRect(193, 30, 0, 0, 256, 256);

            }
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glPopMatrix();

        if (saveDelay < 10) {
            drawString(fontRendererObj, "btw im saving" + "\u00af\\\u005f\u0028\u30c4\u0029\u005f\u002f\u00af", 2,
                    height - 10, 0xffffffff);
        }
        drawCenteredString(fontRendererObj, "Cohkiez: " + Configs.cookies, width / 2, 30, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
