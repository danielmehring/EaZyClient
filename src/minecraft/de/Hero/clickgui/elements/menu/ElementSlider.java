package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ElementSlider extends Element {

	public static final int EaZy = 17;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public boolean dragging;

	int opendelay;

	/*
	 * Konstrukor
	 */
	public ElementSlider(final ModuleButton iparent, final Setting iset) {
		parent = iparent;
		set = iset;
		dragging = false;
		super.setup();
	}

	/*
	 * Rendern des Elements
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		final String displayval = ""
				+ (set.onlyInt() ? (int) set.getValFloat() : Math.round(set.getValFloat() * 100D) / 100D);

		final int color = Configs.useClientColor ? Client.getColor(0l).getRGB() : 0x3fdfdfdf;

		// selected = iset.getValFloat() / iset.getMax();
		final double percentBar = (set.getValFloat() - set.getMin()) / (set.getMax() - set.getMin());

		/*
		 * Die Box und Umrandung rendern
		 */
		if (xoff < xofft) {
			xoff += 1;
		}

		if (xoff > xofft) {
			xoff -= 1;
		}

		Gui.drawRect(x, y, x + width, y + height, 0x8f151515);

		/*
		 * Den Text rendern
		 */
		FontUtil.drawString(setstrg, (float) x + 1, (float) y + 2, 0xffffffff);
		FontUtil.drawString(displayval, (float) ((float) x + width - FontUtil.getStringWidth(displayval)),
				(float) y + 2, 0xffffffff);

		/*
		 * Den Slider rendern
		 */
		Gui.drawRect(x, y + 12, x + width, y + 13.5, 0x3f151515);
		Gui.drawRect(x, y + 12, x + percentBar * width, y + 13.5, color);

		if (percentBar > 0 && percentBar < 1) {
			RenderUtils.drawCircle(x + percentBar * width - 1, y + 12.5, 1.75f,
					Configs.useClientColor ? Client.getColor(0l).darker().getRGB() : 0xff2f2f2f);
			Gui.drawRect(x + percentBar * width - 1, y + 12, x + Math.min(percentBar * width, width), y + 13.5, color);
		}

		/*
		 * Neue Value berechnen, wenn dragging
		 */
		if (dragging) {
			final float diff = set.getMax() - set.getMin();
			float val = set.getMin() + MathHelper.clamp_float((float) ((mouseX - x) / width), 0, 1) * diff;
			val = (float) (Math.round(val * 10) / 10.0);
			set.setValFloat(val); // Die Value im Setting
			// updaten
		}

		if (opendelay < 1000) {
			++opendelay;
		}

	}

	public static float roundFloat(final float number, final int decimalPlaces) {
		float precision = 1.0F;
		for (int i = 0; i < decimalPlaces; i++, precision *= 10) {}
		return (int) (number * precision + 0.5) / precision;
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen
	 * alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	@Override
	public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
			dragging = true;
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Wenn die Maus losgelassen wird soll aufgehört werden die Slidervalue zu
	 * verändern
	 */
	@Override
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
		dragging = false;
	}

	/*
	 * Einfacher HoverCheck, benötigt damit dragging auf true gesetzt werden
	 * kann
	 */
	public boolean isSliderHovered(final int mouseX, final int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 14;
	}
}
