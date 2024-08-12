package me.EaZy.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.main.Client;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiEaZySettings extends GuiScreen {

    public static final int EaZy = 72;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    public GuiEaZySettings(final GuiScreen prevMultiplayerMenu) {
        prevMenu = prevMultiplayerMenu;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 6 + 168 + 24, I18n.format("gui.done", new Object[0])));
        buttonList.add(new GuiButton(1, width / 2 - 155, height / 6, 150, 20, "NoBob: " + (Configs.noBob
                ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(2, width / 2 + 5, height / 6, 150, 20, "Buttons: " + Configs.buttonModeName));
        buttonList.add(new GuiButton(3, width / 2 - 155, height / 6 + 24, 150, 20,
                "Background Blur: " + (Configs.blurredBackground ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
        buttonList.add(
                new GuiButton(4, width / 2 + 5, height / 6 + 24, 150, 20, "Custom Hotbar: " + (Configs.advancedHotbar
                        ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));
        buttonList.add(
                new GuiButton(5, width / 2 - 155, height / 6 + 48, 150, 20, "Toggle Sounds: " + (Configs.toggleSounds
                        ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));

        buttonList
                .add(new GuiButton(6, width / 2 + 5, height / 6 + 72, 150, 20, "Smooth Zooming: " + (Configs.smoothZoom
                        ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));

        buttonList
                .add(new GuiButton(7, width / 2 - 155, height / 6 + 72, 150, 20, "Item Physics: " + (Configs.itemPhysics
                        ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(8, width / 2 + 5, height / 6 + 48, 150, 20, "Better Chat: " + (Configs.betterChat
                ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));

        buttonList.add(new GuiButton(9, width / 2 - 155, height / 6 + 96, 150, 20, "Suffix: " + (Configs.suffix
                ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));

        buttonList.add(new GuiButton(10, width / 2 + 5, height / 6 + 96, 150, 20, "OtherSwing: " + (Configs.otherSwing
                ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));
        buttonList.add(
                new GuiButton(11, width / 2 - 155, height / 6 + 96 + 24, 150, 20, "Custom Font: " + (Configs.customFont
                        ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(12, width / 2 + 5, height / 6 + 96 + 24, 150, 20,
                "F5 Server Rotations: " + (Configs.f5PositionXD ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(13, width / 2 - 155, height / 6 + 96 + 24 + 24, 150, 20,
                "Inventory Particles: " + (Configs.invParticles ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(14, width / 2 + 5, height / 6 + 96 + 24 + 24, 150, 20,
                "AntiLag: " + (Configs.antiLag ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(15, width / 2 - 155, height / 6 + 96 + 24 + 24 + 24, 150, 20,
                "GTA5Death: " + (Configs.gta5Death ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
        buttonList.add(new GuiButton(16, width / 2 + 5, height / 6 + 96 + 24 + 24 + 24, 150, 20,
                "GTA5Death Sound: " + (Configs.gta5DeathSound ? I18n.format("options.on", new Object[0])
                        : I18n.format("options.off", new Object[0]))));
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            switch (clickedButton.id) {
                case 0:
                    mc.displayGuiScreen(prevMenu);
                    break;
                case 1:
                    Configs.noBob = !Configs.noBob;
                    mc.displayGuiScreen(this);
                    break;
                case 2:
                    Configs.nextButtonMode();
                    mc.displayGuiScreen(this);
                    break;
                case 3:
                    Configs.blurredBackground = !Configs.blurredBackground;
                    mc.displayGuiScreen(this);
                    break;
                case 4:
                    Configs.advancedHotbar = !Configs.advancedHotbar;
                    mc.displayGuiScreen(this);
                    break;
                case 5:
                    Configs.toggleSounds = !Configs.toggleSounds;
                    mc.displayGuiScreen(this);
                    break;
                case 6:
                    Configs.smoothZoom = !Configs.smoothZoom;
                    mc.displayGuiScreen(this);
                    break;
                case 7:
                    Configs.itemPhysics = !Configs.itemPhysics;
                    mc.displayGuiScreen(this);
                    break;
                case 8:
                    Configs.betterChat = !Configs.betterChat;
                    mc.displayGuiScreen(this);
                    break;
                case 9:
                    Configs.suffix = !Configs.suffix;
                    mc.displayGuiScreen(this);
                    break;
                case 10:
                    Configs.otherSwing = !Configs.otherSwing;
                    mc.displayGuiScreen(this);
                    break;
                case 11:
                    Configs.customFont = !Configs.customFont;
                    mc.displayGuiScreen(this);
                    break;
                case 12:
                    Configs.f5PositionXD = !Configs.f5PositionXD;
                    mc.displayGuiScreen(this);
                    break;
                case 13:
                    Configs.invParticles = !Configs.invParticles;
                    mc.displayGuiScreen(this);
                    break;
                case 14:
                    Configs.antiLag = !Configs.antiLag;
                    mc.displayGuiScreen(this);
                    break;
                case 15:
                    Configs.gta5Death = !Configs.gta5Death;
                    mc.displayGuiScreen(this);
                    break;
                case 16:
                    Configs.gta5DeathSound = !Configs.gta5DeathSound;
                    mc.displayGuiScreen(this);
                    break;
                default:
                    break;
            }
            FileManager.saveMain();
        }
    }

    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 1) {
            actionPerformed((GuiButton) buttonList.get(0));
        }
        if (par2 == Keyboard.KEY_F5) {
            mc.displayGuiScreen(this);
        }
    }

    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground(par1, par2);

        GuiScreen.targetX = width / 2 - 160;
        GuiScreen.targetY = height / 6 - 4;
        GuiScreen.targetX2 = width / 2 + 160;
        GuiScreen.targetY2 = height / 6 + 192 + 24;

        if (!Client.isHidden) {
            Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
        }
        super.drawScreen(par1, par2, par3);
    }
}
