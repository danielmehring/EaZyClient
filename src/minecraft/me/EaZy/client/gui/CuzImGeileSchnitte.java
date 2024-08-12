package me.EaZy.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.main.Client;
import me.EaZy.client.utils.MiscUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class CuzImGeileSchnitte extends GuiScreen {

    public static final int EaZy = 63;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);

        GuiScreen.targetX = width / 2 - 160;
        GuiScreen.targetY = height / 2 - 215;
        GuiScreen.targetX2 = width / 2 + 160;
        GuiScreen.targetY2 = height / 2 + 120;

        if (!Client.isHidden) {
            Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
        }
        drawCenteredString(fontRendererObj, "§nCoder", width / 2, 25 + 25, Client.getColor(0l).getRGB());
        drawCenteredString(fontRendererObj, "EaZyCode", width / 2, 40 + 25, Client.getColor(0l).getRGB());
        drawCenteredString(fontRendererObj, "XYZERTUBE", width / 2, 55 + 25, Client.getColor(0l).getRGB());

        drawCenteredString(fontRendererObj, "§nHelpers", width / 2, 95 + 40, Client.getColor(0l).getRGB());
        drawCenteredString(fontRendererObj, "HeroCode", width / 2, 110 + 40, Client.getColor(0l).getRGB());
        drawCenteredString(fontRendererObj, "AXCII", width / 2, 125 + 40, Client.getColor(0l).getRGB());
        
        drawCenteredString(fontRendererObj, "-> YouTube <-", width / 2, height / 2 + 90, Client.getColor(0l).getRGB());
        drawCenteredString(fontRendererObj, "-> Twitter <-", width / 2, height / 2 + 100, Client.getColor(0l).getRGB());
        super.drawScreen(par1, par2, par3);
    }

    public CuzImGeileSchnitte(final GuiScreen prevMultiplayerMenu) {
        prevMenu = prevMultiplayerMenu;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButton(100, width / 2 + 160 - 30, height / 2 + 100 - 2, 30, 20, "Back"));
        buttonList.add(new GuiInvisButton(101, width / 2 - 38, height / 2 + 88, 75, 11, ""));
        buttonList.add(new GuiInvisButton(102, width / 2 - 34, height / 2 + 100, 67, 11, ""));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            switch (clickedButton.id) {
                case 100:
                    mc.displayGuiScreen(prevMenu);
                    break;
                case 101:
                    // YouTube
                    MiscUtils.openLink("https://youtube.com/EaZyClient");
                    break;
                case 102:
                    // Twitter
                    MiscUtils.openLink("https://twitter.com/EaZyClientHack");
                    break;
                default:
                    break;
            }
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
