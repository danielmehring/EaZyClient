package me.EaZy.client.games;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiGames extends GuiScreen {

    public static final int EaZy = 55;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    public GuiGames(final GuiScreen par1GuiScreen) {
        prevMenu = par1GuiScreen;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
                Minecraft.displayHeight);
        Keyboard.enableRepeatEvents(true);

        buttonList.add(new GuiButton(0, sr.getScaledWidth() / 2 + 73, sr.getScaledHeight() / 2 - 29 + 30 - 22 + 6 + 62,
                25, 20, "Exit"));

        buttonList.add(new GuiButton(101, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 29 - 30, 98 * 2, 20,
                "CookieClicker"));
        buttonList.add(new GuiButton(102, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 29 - 8, 98 * 2, 20,
                "Mitte bekommt Tritte"));

    }

    @Override
    public void updateScreen() {

    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            if (clickedButton.id == 0) {
                mc.displayGuiScreen(prevMenu);
            }
            if (clickedButton.id == 101) {
                mc.displayGuiScreen(new CookieClicker(this));
            }
            if (clickedButton.id == 102) {
                mc.displayGuiScreen(new MittebekommtTritte(this));
            }
        }
    }

    @Override
    public void confirmClicked(final boolean par1, final int par2) {
        mc.displayGuiScreen(this);
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 28 || par2 == 156) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {

        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);

        if (!Client.isHidden) {
            GuiScreen.targetX = width / 2 - 100;
            GuiScreen.targetY = height / 2 - 123;
            GuiScreen.targetX2 = width / 2 + 100;
            GuiScreen.targetY2 = height / 2 + 69;

            Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
        }
        super.drawScreen(par1, par2, par3);
    }

}
