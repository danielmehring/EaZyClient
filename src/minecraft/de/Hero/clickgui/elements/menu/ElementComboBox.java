package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.ESP;
import me.EaZy.client.modules.Speed;
import me.EaZy.client.modules.YesCheat;
import net.minecraft.client.gui.Gui;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ElementComboBox extends Element {

    public static final int EaZy = 16;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    /*
	 * Konstrukor
     */
    public ElementComboBox(final ModuleButton iparent, final Setting iset) {
        parent = iparent;
        set = iset;
        super.setup();
    }

    /*
	 * Rendern des Elements
     */
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

        final int color = Configs.useClientColor ? Client.getColor(0l).getRGB() : 0x3fdfdfdf;

        /*
		 * Die Box und Umrandung rendern
         */
        Gui.drawRect(x, y, x + width, y + height, 0x8f151515);

        FontUtil.drawTotalCenteredString(setstrg, (float) x + (float) width / 2, (float) y + 15 / 2, 0xffffffff);

        Gui.drawRect(x, y + 14, x + width, y + 15, 0x4f151515);
        if (comboextended) {
            Gui.drawRect(x, y + 15, x + width, y + height, 0x4f151515);
            double ay = y + 15;
            for (final String sld : set.getOptions()) {
                final String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
                FontUtil.drawCenteredString(elementtitle, (float) x + (float) width / 2, (float) ay + 2, 0xffffffff);

                /*
				 * Ist das Element ausgewählt, wenn ja dann markiere das Element
				 * in der ComboBox
                 */
                if (sld.equalsIgnoreCase(set.getValString())) {
                    Gui.drawRect(x, ay, x + 1.5, ay + FontUtil.getFontHeight() + 2, color);
                }
                /*
				 * Wie bei mouseClicked 'is hovered', wenn ja dann markiere das
				 * Element in der ComboBox
                 */
                if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY < ay + FontUtil.getFontHeight() + 2) {
                    Gui.drawRect(x + width - 1.2, ay, x + width, ay + FontUtil.getFontHeight() + 2, color);
                }
                ay += FontUtil.getFontHeight() + 2;
            }
        }
    }

    /*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen
	 * alle anderen Versuche der Interaktion abgebrochen werden?
     */
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0) {
            if (isButtonHovered(mouseX, mouseY)) {
                comboextended = !comboextended;
                return true;
            }

            /*
			 * Also wenn die Box ausgefahren ist, dann wird für jede mögliche
			 * Options überprüft, ob die Maus auf diese zeigt, wenn ja dann
			 * global jeder weitere call an mouseClicked gestoppt und die Values
			 * werden aktualisiert
             */
            if (!comboextended) {
                return false;
            }
            double ay = y + 15;
            for (final String slcd : set.getOptions()) {
                if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY <= ay + FontUtil.getFontHeight() + 2) {
                    if (clickgui != null && clickgui.setmgr != null) {
                        clickgui.setmgr.getSettingByName(parent.mod, set.getName()).setValString(slcd);
                        if (parent.mod.getName().equals("Speed") && set.getName().equals("Mode")) {
                            Speed.updateMode();
                        }
                        if (parent.mod.getName().equalsIgnoreCase("ESP") && set.getName().equalsIgnoreCase("Mode")) {
                            ESP.updateMode();
                        }
                        if (parent.mod.getName().equals("YesCheat") && set.getName().equals("Mode")) {
                            YesCheat.updateMode();
                        }
                    }
                    return true;
                }
                ay += FontUtil.getFontHeight() + 2;
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /*
	 * Einfacher HoverCheck, benötigt damit die Combobox geöffnet und
	 * geschlossen werden kann
     */
    public boolean isButtonHovered(final int mouseX, final int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
    }
}
