package de.Hero.clickgui.elements;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class Element {

    public static final int EaZy = 14;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public ClickGUI clickgui;
    public ModuleButton parent;
    public Setting set;
    public double offset;
    public double x;
    public double y;

    public double xofft;
    public double xoff;

    public int ydelay;
    public int ydelaytarget;

    public double width;
    public double height;

    public String setstrg;

    public boolean comboextended;

    public void setup() {
        clickgui = parent.parent.clickgui;
    }

    public void update() {
        /*
		 * Richtig positionieren! Offset wird von ClickGUI aus bestimmt, sodass
		 * nichts ineinander gerendert wird
         */
        x = parent.x + parent.width + 2;
        y = parent.y + offset;
        width = parent.width + 10;
        height = 15;

        /*
		 * Title der Box bestimmen und falls nötig die Breite der CheckBox
		 * erweitern
         */
        final String sname = set.getName();
        if (set.isCheck()) {
            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            final double textx = x + width - FontUtil.getStringWidth(setstrg);
            if (textx < x + 13) {
                width += x + 13 - textx + 1;
            }
        } else if (set.isCombo()) {
            height = comboextended ? set.getOptions().size() * (FontUtil.getFontHeight() + 2) + 15 : 15;

            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            int longest = FontUtil.getStringWidth(setstrg);
            for (final String s : set.getOptions()) {
                final int temp = FontUtil.getStringWidth(s);
                if (temp > longest) {
                    longest = temp;
                }
            }
            final double textx = x + width - longest;
            if (textx < x) {
                width += x - textx + 1;
            }
        } else if (set.isSlider()) {
            setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
            Math.round(set.getValFloat() * 100D);
            final String displaymax = "" + Math.round(set.getMax() * 100D) / 100D;
            final double textx = x + width - FontUtil.getStringWidth(setstrg) - FontUtil.getStringWidth(displaymax) - 4;
            if (textx < x) {
                width += x - textx + 1;
            }
        }
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

    }

    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        return isHovered(mouseX, mouseY);
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }

    public boolean isHovered(final int mouseX, final int mouseY) {

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
