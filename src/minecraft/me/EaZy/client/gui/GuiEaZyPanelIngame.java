package me.EaZy.client.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.games.GuiGames;
import me.EaZy.client.gui.alts.GuiAlts;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiEaZyPanelIngame extends GuiScreen {

    public static final int EaZy = 71;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private GuiMainMenu prevMenu;

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(),
                Minecraft.displayWidth, Minecraft.displayHeight);
        drawDefaultBackground(par1, par2);

        final Color color = Client.getColor(0l);

        drawRect(0, 0, sr.getScaledWidth() * 2, 20, 0xb7111111);
        drawRect(0, sr.getScaledHeight(), sr.getScaledWidth() * 2, sr.getScaledHeight() - 20, 0xb7111111);

        drawRect(sr.getScaledWidth() - 61, sr.getScaledHeight() - 20, sr.getScaledWidth() - 60, sr.getScaledHeight(),
                color.getRGB());

        drawRect(0, +20, sr.getScaledWidth(), +21, color.getRGB());
        drawRect(0, sr.getScaledHeight() - 20, sr.getScaledWidth(), sr.getScaledHeight() - 21, color.getRGB());

        GL11.glPushMatrix();
        GL11.glTranslated(sr.getScaledWidth() / 2 - 28, 0, 42);
        GL11.glScaled(2.5f, 2.5f, 42);
        drawString(fontRendererObj, "Panel", 0, 0, color.getRGB());
        GL11.glPopMatrix();

        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(),
                Minecraft.displayWidth, Minecraft.displayHeight);
        Keyboard.enableRepeatEvents(true);

        buttonList.add(new GuiButton(99, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 13, 98 * 2, 20,
                "AltManager"));

        buttonList.add(new GuiInvisButton(100, sr.getScaledWidth() - 60, sr.getScaledHeight() - 20, 60, 20, "Exit"));

        buttonList.add(new GuiButton(101, width / 2 - 98, height / 2 - 51 + 16, 97, 20, "Credits"));
        buttonList.add(new GuiButton(102, width / 2 + 1, height / 2 - 51 + 16, 97, 20, "Changelog"));

        buttonList.add(new GuiButton(103, width / 2 - 98, height / 2 - 7 + 16, 97, 20, "Games"));
        buttonList.add(new GuiButton(104, width / 2 + 1, height / 2 - 7 + 16, 97, 20, "EaZy Options"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.id == 100) {
            mc.displayGuiScreen(prevMenu);
        }

        if (clickedButton.id == 99) {
            mc.displayGuiScreen(new GuiAlts(this));
        }

        if (clickedButton.id == 101) {
            mc.displayGuiScreen(new CuzImGeileSchnitte(this));
        }

        if (clickedButton.id == 102) {
            mc.displayGuiScreen(new GuiChangelog(this));
        }

        if (clickedButton.id == 103) {
            mc.displayGuiScreen(new GuiGames(this));
        }

        if (clickedButton.id == 104) {
            mc.displayGuiScreen(new GuiEaZySettings(this));
        }
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 28 || par2 == 156) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
        if (par2 == 1) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
    }
}
