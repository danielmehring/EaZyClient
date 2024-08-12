package me.EaZy.client.games;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.gui.GuiInvisButton;
import me.EaZy.client.main.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class MittebekommtTritte extends GuiScreen {

    public static final int EaZy = 56;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    private static final ResourceLocation schuh = new ResourceLocation("EaZy/schuh.png");
    private static final ResourceLocation schuhTritt = new ResourceLocation("EaZy/schuhTritt.png");
    private static final ResourceLocation mittebekommttrittebild = new ResourceLocation("EaZy/MittebekommtTritte.png");

    private int saveDelay = 0;

    public MittebekommtTritte(final GuiScreen prevMenu) {
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
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
                Minecraft.displayHeight);
        buttonList.add(new GuiInvisButton(0, sr.getScaledWidth() - 60, sr.getScaledHeight() - 20, 60, 20, "Exit"));
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

            Configs.tritte++;
            try {
                final File soundFile = new File(FileManager.eazyDir.getAbsolutePath(), "MitteBekommtTritteXD.wav");
                final AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                final Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (final Exception e) {
            }

        }

        super.mouseClicked(x, y, button);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);

        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
                Minecraft.displayHeight);

        final Color color = Client.getColor(0l);

        drawRect(0, 0, sr.getScaledWidth() * 2, 20, 0xb7111111);
        drawRect(0, sr.getScaledHeight(), sr.getScaledWidth() * 2, sr.getScaledHeight() - 20, 0xb7111111);

        drawRect(sr.getScaledWidth() - 61, sr.getScaledHeight() - 20, sr.getScaledWidth() - 60, sr.getScaledHeight(),
                color.getRGB());

        drawRect(0, +20, sr.getScaledWidth(), +21, color.getRGB());
        drawRect(0, sr.getScaledHeight() - 20, sr.getScaledWidth(), sr.getScaledHeight() - 21, color.getRGB());

        GL11.glPushMatrix();
        {

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColorMask(true, true, true, false);

            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

            Minecraft.getTextureManager().bindTexture(mittebekommttrittebild);

            GL11.glPushMatrix();
            {
                drawTexturedModalRect(sr.getScaledWidth() / 2 - 128, 30, 0, 0, 256, 256);

            }
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_BLEND);

            GL11.glTranslated(width / 2 - 242, height / 2 - 130, 0);
            if (!Mouse.isButtonDown(0)) {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColorMask(true, true, true, false);

                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

                Minecraft.getTextureManager().bindTexture(schuhTritt);

                GL11.glPushMatrix();
                {
                    GL11.glScalef(0.7f, 0.7f, 0.7f);
                    drawTexturedModalRect(sr.getScaledWidth() / 2 - 30, sr.getScaledHeight() / 2, 0, 0, 256, 256);

                }
                GL11.glPopMatrix();

                GL11.glDisable(GL11.GL_BLEND);
            } else if (Mouse.isButtonDown(0)) {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColorMask(true, true, true, false);

                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

                Minecraft.getTextureManager().bindTexture(schuh);

                GL11.glPushMatrix();
                {
                    GL11.glScalef(0.75f, 0.5f, 0.75f);
                    drawTexturedModalRect(sr.getScaledWidth() / 2 - 42, sr.getScaledHeight() / 2 - 5, 0, 0, 256, 256);

                }
                GL11.glPopMatrix();

                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        GL11.glPopMatrix();

        if (saveDelay < 10) {
            drawString(fontRendererObj, "Saving...", 2, height - 10, 0xffffffff);
        }

        GL11.glPushMatrix();
        GL11.glTranslated(sr.getScaledWidth() / 2 - 10 - fontRendererObj.getStringWidth("Tritte: " + Configs.tritte), 0,
                42);
        GL11.glScaled(2.5f, 2.5f, 42);
        drawString(fontRendererObj, "Tritte: " + Configs.tritte, 0, 0, color.getRGB());
        GL11.glPopMatrix();

        super.drawScreen(par1, par2, par3);
    }
}
