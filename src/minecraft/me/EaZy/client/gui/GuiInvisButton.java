package me.EaZy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiInvisButton extends GuiButton {

    public static final int EaZy = 73;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static String Variable1;

    public GuiInvisButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiInvisButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn,
            final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (visible) {

            final FontRenderer var4 = mc.fontRendererObj;

            drawCenteredString(var4, Variable1 + displayString, xPosition + width / 2, yPosition + (height - 8) / 2,
                    enabled ? 0xFFFFFFFF : 0xFFAAAAAA);

            if (hovered) {
                Variable1 = " ";
            } else {
                Variable1 = "";
            }
        }
    }
}
