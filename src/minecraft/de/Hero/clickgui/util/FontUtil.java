package de.Hero.clickgui.util;

import me.EaZy.client.main.Client;
import net.minecraft.util.StringUtils;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class FontUtil {

    public static final int EaZy = 20;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static int getStringWidth(final String text) {
        return Client.font.getStringWidth(StringUtils.stripControlCodes(text));
    }

    public static int getFontHeight() {
        return Client.mc.fontRendererObj.FONT_HEIGHT;
    }

    public static void drawString(final String text, final float x, final float y, final int color) {
        Client.font.drawString(text, x, y, color);
    }

    public static void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        Client.font.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        drawString(text, x - Client.font.getStringWidth(text) / 2, y, color);
    }

    public static void drawCenteredStringWithShadow(final String text, final double x, final double y,
            final int color) {
        drawStringWithShadow(text, x - Client.font.getStringWidth(text) / 2, y, color);
    }

    public static void drawTotalCenteredString(final String text, final float x, final float y, final int color) {
        drawString(text, x - Client.font.getStringWidth(text) / 2, y - Client.mc.fontRendererObj.FONT_HEIGHT / 2,
                color);
    }

    public static void drawTotalCenteredStringWithShadow(final String text, final double x, final double y,
            final int color) {
        drawStringWithShadow(text, x - Client.font.getStringWidth(text) / 2,
                y - Client.mc.fontRendererObj.FONT_HEIGHT / 2F, color);
    }
}
