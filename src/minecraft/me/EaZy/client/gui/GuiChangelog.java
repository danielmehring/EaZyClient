package me.EaZy.client.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiChangelog extends GuiScreen {

    public static final int EaZy = 64;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final GuiScreen prevMenu;

    public GuiChangelog(final GuiScreen prevMultiplayerMenu) {
        prevMenu = prevMultiplayerMenu;
    }

    private static final ArrayList<String> changelogs = new ArrayList();
    private int updateTime = 0;

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        final ScaledResolution res = RenderUtils.newScaledResolution();
        buttonList.add(new GuiButton(0, res.getScaledWidth() / 4, height / 4 + 120, res.getScaledWidth() / 4 * 2, 20,
                "Cancel"));

        loadChangelog();
    }

    private void loadChangelog() {
        changelogs.clear();
        new Thread("EaZy Changelog Getter") {

            @Override
            public void run() {
                try {
                    final ArrayList<String> asd = new ArrayList<>();
                    String inputLine;
                    final URL changelog = new URL(new String(new byte[]{111, 101, 121, 103, 104, 99})
                            .substring(4, 5)
                            + new String(new byte[]{117, 121, 109, 118, 116, 121}).substring(4, 5)
                            + new String(new byte[]{116, 104, 103, 106, 121, 118}).substring(0, 1)
                            + new String(new byte[]{100, 112, 115, 104, 112, 107}).substring(4, 5)
                            + new String(new byte[]{117, 110, 118, 58, 107, 112}).substring(3, 4)
                            + new String(new byte[]{110, 120, 112, 99, 47, 118}).substring(4, 5)
                            + new String(new byte[]{98, 118, 113, 118, 47, 101}).substring(4, 5)
                            + new String(new byte[]{106, 110, 105, 118, 110, 118}).substring(4, 5)
                            + new String(new byte[]{112, 99, 118, 105, 98, 103}).substring(3, 4)
                            + new String(new byte[]{101, 51, 108, 119, 111, 114}).substring(1, 2)
                            + new String(new byte[]{97, 120, 107, 54, 114, 98}).substring(3, 4)
                            + new String(new byte[]{115, 109, 108, 107, 56, 102}).substring(4, 5)
                            + new String(new byte[]{120, 110, 109, 101, 50, 100}).substring(4, 5)
                            + new String(new byte[]{113, 112, 97, 50, 121, 116}).substring(3, 4)
                            + new String(new byte[]{105, 117, 99, 109, 51, 103}).substring(4, 5)
                            + new String(new byte[]{110, 104, 105, 95, 104, 98}).substring(3, 4)
                            + new String(new byte[]{50, 120, 117, 115, 114, 102}).substring(0, 1)
                            + new String(new byte[]{46, 101, 118, 103, 104, 107}).substring(0, 1)
                            + new String(new byte[]{118, 115, 119, 105, 117, 108}).substring(0, 1)
                            + new String(new byte[]{112, 101, 101, 100, 119, 115}).substring(4, 5)
                            + new String(new byte[]{119, 111, 101, 106, 107, 110}).substring(2, 3)
                            + new String(new byte[]{98, 103, 117, 107, 107, 107}).substring(0, 1)
                            + new String(new byte[]{118, 102, 49, 113, 118, 104}).substring(2, 3)
                            + new String(new byte[]{113, 100, 56, 110, 102, 97}).substring(2, 3)
                            + new String(new byte[]{119, 97, 120, 112, 46, 109}).substring(4, 5)
                            + new String(new byte[]{107, 114, 119, 110, 105, 109}).substring(3, 4)
                            + new String(new byte[]{114, 106, 119, 107, 105, 121}).substring(4, 5)
                            + new String(new byte[]{110, 116, 106, 107, 109, 97}).substring(1, 2)
                            + new String(new byte[]{113, 121, 105, 117, 114, 101}).substring(4, 5)
                            + new String(new byte[]{104, 97, 97, 102, 106, 119}).substring(1, 2)
                            + new String(new byte[]{97, 100, 116, 107, 116, 116}).substring(1, 2)
                            + new String(new byte[]{110, 117, 111, 100, 112, 113}).substring(2, 3)
                            + new String(new byte[]{108, 114, 118, 116, 46, 98}).substring(4, 5)
                            + new String(new byte[]{109, 110, 108, 103, 102, 112}).substring(1, 2)
                            + new String(new byte[]{111, 115, 101, 119, 100, 106}).substring(2, 3)
                            + new String(new byte[]{117, 116, 97, 98, 104, 111}).substring(1, 2)
                            + new String(new byte[]{103, 113, 47, 116, 120, 104}).substring(2, 3)
                            + new String(new byte[]{108, 97, 104, 67, 105, 119}).substring(3, 4)
                            + new String(new byte[]{121, 120, 107, 104, 121, 103}).substring(3, 4)
                            + new String(new byte[]{119, 109, 108, 107, 97, 100}).substring(4, 5)
                            + new String(new byte[]{113, 107, 110, 116, 97, 117}).substring(2, 3)
                            + new String(new byte[]{110, 114, 108, 112, 103, 110}).substring(4, 5)
                            + new String(new byte[]{101, 119, 98, 121, 115, 113}).substring(0, 1)
                            + new String(new byte[]{101, 108, 105, 113, 97, 104}).substring(1, 2)
                            + new String(new byte[]{108, 101, 109, 111, 97, 101}).substring(3, 4)
                            + new String(new byte[]{121, 103, 117, 107, 101, 112}).substring(1, 2)
                            + new String(new byte[]{114, 46, 109, 103, 98, 112}).substring(1, 2)
                            + new String(new byte[]{116, 115, 117, 105, 102, 109}).substring(0, 1)
                            + new String(new byte[]{100, 120, 120, 101, 97, 114}).substring(2, 3)
                            + new String(new byte[]{117, 116, 109, 103, 108, 115}).substring(1, 2));
                    try (BufferedReader changelogInput = new BufferedReader(
                            new InputStreamReader(changelog.openStream()))) {
                        while ((inputLine = changelogInput.readLine()) != null) {
                            if (asd.size() > 12 || asd.contains(inputLine)) {
                                continue;
                            }
                            asd.add(inputLine);
                        }
                    }
                    asd.forEach((xD) -> {
                        changelogs.add(xD);
                    });
                } catch (final Exception changelog) {
                    // empty catch block
                }
            }
        }.start();
    }

    @Override
    protected void actionPerformed(final GuiButton clickedButton) {
        if (clickedButton.enabled) {
            if (clickedButton.id == 0) {
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
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        ++updateTime;
        drawDefaultBackground(par1, par2);

        if (updateTime >= 600 && changelogs.isEmpty()) {
            loadChangelog();
            updateTime = 0;
        }

        final ScaledResolution res = RenderUtils.newScaledResolution();
        GL11.glPushMatrix();
        try {
            if (changelogs.isEmpty()) {

                GuiScreen.targetX = res.getScaledWidth() / 4;
                GuiScreen.targetY = 50 - mc.fontRendererObj.FONT_HEIGHT - 3;
                GuiScreen.targetX2 = res.getScaledWidth() / 4 * 3;
                GuiScreen.targetY2 = 50 - mc.fontRendererObj.FONT_HEIGHT + 7;

                if (!Client.isHidden) {
                    Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
                }
                // RenderUtils.drawRect(res.getScaledWidth() / 4, 50 -
                // mc.fontRendererObj.FONT_HEIGHT - 3,
                // res.getScaledWidth() / 4 * 3, 50 -
                // mc.fontRendererObj.FONT_HEIGHT + 7,
                // new Color(0, 0, 0, 110).getRGB());
                drawCenteredString(fontRendererObj,
                        new String(new byte[]{121, 119, 121, 76, 120, 115}).substring(3, 4)
                        + new String(new byte[]{117, 103, 113, 120, 111, 114}).substring(4, 5)
                        + new String(new byte[]{103, 117, 97, 119, 112, 106}).substring(2, 3)
                        + new String(new byte[]{111, 117, 99, 97, 100, 108}).substring(4, 5)
                        + new String(new byte[]{101, 105, 112, 104, 117, 112}).substring(1, 2)
                        + new String(new byte[]{99, 98, 110, 105, 111, 99}).substring(2, 3)
                        + new String(new byte[]{112, 103, 117, 117, 112, 106}).substring(1, 2)
                        + new String(new byte[]{103, 46, 100, 110, 97, 115}).substring(1, 2)
                        + new String(new byte[]{120, 113, 108, 46, 110, 101}).substring(3, 4)
                        + new String(new byte[]{120, 106, 46, 101, 116, 106}).substring(2, 3),
                        res.getScaledWidth() / 2, 50 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 2, -1);
            } else {
                int offset = 5;
                for (final String s : changelogs) {
                    final int height = 10 * offset;

                    GuiScreen.targetX = res.getScaledWidth() / 4;
                    GuiScreen.targetY = height - mc.fontRendererObj.FONT_HEIGHT - 3;
                    GuiScreen.targetX2 = res.getScaledWidth() / 4 * 3;
                    GuiScreen.targetY2 = height - mc.fontRendererObj.FONT_HEIGHT + 7;

                    // Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY,
                    // GuiScreen.targetX2, GuiScreen.fadeY2, 0x7c000000);
                    RenderUtils.drawRect(res.getScaledWidth() / 4, height - mc.fontRendererObj.FONT_HEIGHT - 3,
                            res.getScaledWidth() / 4 * 3, height - mc.fontRendererObj.FONT_HEIGHT + 7,
                            new Color(0, 0, 0, 110).getRGB());
                    drawCenteredString(fontRendererObj, s, res.getScaledWidth() / 2,
                            height - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 2, -1);
                    ++offset;
                }

            }
        } catch (final Exception offset) {
            // empty catch block
        }
        GL11.glPopMatrix();

        super.drawScreen(par1, par2, par3);
    }
}
