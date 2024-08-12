package me.EaZy.client.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class GuiDarkButton extends GuiButton {

    public static final int EaZy = 68;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private int fad3;

    public GuiDarkButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn,
            final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);

    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (visible) {
            hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width
                    && mouseY < yPosition + height;
            if (!hovered) {
                if (fad3 != 100) {
                    fad3 += 5;
                }
            } else {
                if (fad3 <= 40) {
                    return;
                }
                if (fad3 != 70) {
                    fad3 -= 5;
                }
            }
            final Color a = new Color(0, 0, 0, fad3);
            final FontRenderer var4 = mc.fontRendererObj;
            Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, a.getRGB());
            drawCenteredString(var4, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, 0xffffffff);
        }
    }

}
