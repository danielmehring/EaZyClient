package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.Gui;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ElementCheckBox extends Element {

	public static final int EaZy = 15;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/*
	 * Konstrukor
	 */
	public ElementCheckBox(final ModuleButton iparent, final Setting iset) {
		parent = iparent;
		set = iset;
		super.setup();
	}

	/*
	 * Rendern des Elements
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

		/*
		 * Die Box und Umrandung rendern
		 */
		Gui.drawRect(x, y, x + width, y + height, 0x8f151515);

		/*
		 * Titel und Checkbox rendern.
		 */
		FontUtil.drawString(setstrg, (float) x + (float) width - FontUtil.getStringWidth(setstrg),
				(float) y + FontUtil.getFontHeight() / 2 - 0.5f, 0xffffffff);
		Gui.drawRect(x + 2, y + 12, x + 11, y + 3, 0x3fdfdfdf);

		if (set.getValBoolean()) {

			ydelaytarget = 5;

			Gui.drawRect(x + 2, y + 12, x + 11, y + 3, 0x1f010101);

		} else {
			ydelaytarget = 0;
		}

		if (ydelay < ydelaytarget) {
			ydelay += 1;
		}

		if (ydelay > ydelaytarget) {
			ydelay -= 1;
		}

		if (ydelay != 0) {
			final int temp = Client.getColor(0l).getRGB();
			RenderUtils.drawLine(x + 1 + 1, y + height / 3 + 1 + 2, x + 4 + 1, y + 10 + 2 + ydelay - 5, 1,
					Configs.useClientColor ? temp : 0xff00face);
			RenderUtils.drawLine(x + 4 + 1, y + 10 + 2 + ydelay - 5, x + 11 + 1, y + height / 5 - 2 + 2 - ydelay + 5, 1,
					Configs.useClientColor ? temp : 0xff00face);
		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen
	 * alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	@Override
	public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
			set.setValBoolean(!set.getValBoolean());
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, benötigt damit die Value geändert werden kann
	 */
	public boolean isCheckHovered(final int mouseX, final int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}
