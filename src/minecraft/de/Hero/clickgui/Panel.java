package de.Hero.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.clickgui.elements.ModuleButton;
import me.EaZy.client.Configs;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.Gui;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class Panel {

	public static final int EaZy = 19;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public String title;

	public double x;
	public double y;

	public double x2;
	public double y2;
	public double width;
	public double height;
	public boolean dragging;
	public boolean extended;
	public boolean visible;
	public boolean pinned;
	public boolean pinnable;

	public double ylt1 = 3;
	public double yl1 = 3;

	public double ylt2 = 12;
	public double yl2 = 12;

	public ArrayList<ModuleButton> Elements = new ArrayList<>();
	public ClickGUI clickgui;

	public int count = 0;

	/*
	 * Konstrukor
	 */
	public Panel(final String ititle, final double ix, final double iy, final double iwidth, final double iheight,
			final boolean iextended, final ClickGUI parent) {
		title = ititle;
		x = ix;
		y = iy;
		width = iwidth;
		height = iheight;
		extended = iextended;
		dragging = false;
		visible = true;
		clickgui = parent;
		pinnable = false;
		setup();
	}

	public Panel(final String ititle, final double ix, final double iy, final double iwidth, final double iheight,
			final boolean iextended, final ClickGUI parent, final boolean ipinnable) {
		title = ititle;
		x = ix;
		y = iy;
		width = iwidth;
		height = iheight;
		extended = iextended;
		dragging = false;
		visible = true;
		clickgui = parent;
		pinnable = ipinnable;
		setup();
	}

	/*
	 * Wird in ClickGUI überschrieben, sodass auch ModuleButtons hinzugefügt
	 * werden können :3
	 */
	public void setup() {}

	/*
	 * Rendern des Elements.
	 */
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		if (!visible) {
			return;
		}

		if (dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
		}

		final Color temp = Client.getColor(0l).darker().darker();

		Gui.drawRect(x - 1, y, x + width + 1, y + height, Configs.useClientColor ? temp.getRGB() : 0xff0198E3);

		if (yl1 < ylt1) {
			yl1 += 1;
		}

		if (yl1 > ylt1) {
			yl1 -= 1;
		}

		if (yl2 < ylt2) {
			yl2 += 1;
		}

		if (yl2 > ylt2) {
			yl2 -= 1;
		}

		if (extended) {
			ylt1 = 3;
			ylt2 = 12;
		} else {
			ylt1 = 12;
			ylt2 = 3;
		}

		RenderUtils.drawLine(x + width - 10 + 3, y + yl1, x + width - 5 + 3, y + yl2, 1, 0xffffffff);
		RenderUtils.drawLine(x + width - 10 + 3, y + yl1, x + width - 15 + 3, y + yl2, 1, 0xffffffff);

		GL11.glPushMatrix();
		GL11.glTranslated((int) x + (int) width / 2 - 37, (int) y + (int) height / 2 - 5, 0);
		GL11.glScaled(1.5, 1.5, 0);
		Client.font.drawString(title, 0, 0, 0xffefefef);
		GL11.glPopMatrix();

		if (extended && !Elements.isEmpty()) {
			double startY = y + height;

			for (final ModuleButton et : Elements) {

				Gui.drawRect(x, startY, x + width, startY + et.height + 1, 0x8f151515);
				et.x = x + 2;
				et.y = startY;
				et.yt = startY + et.height;
				et.width = width - 4;
				startY += et.height + 1;

				et.drawScreen(mouseX, mouseY, partialTicks);

			}

		}
	}

	/*
	 * Zum Bewegen und Extenden des Panels usw.
	 */
	public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
		if (!visible) {
			return false;
		}
		if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
			x2 = x - mouseX;
			y2 = y - mouseY;
			dragging = true;
			return true;
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			extended = !extended;
			return true;
		} else if (mouseButton == 2 && pinnable && isHovered(mouseX, mouseY)) {
			pinned = !pinned;
			return true;
		} else if (extended) {
			if (Elements.stream().anyMatch((et) -> (et.mouseClicked(mouseX, mouseY, mouseButton)))) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Damit das Panel auch losgelassen werden kann
	 */
	public void mouseReleased(final int mouseX, final int mouseY, final int state) {
		if (!visible) {
			return;
		}
		if (state == 0) {
			dragging = false;
		}
	}

	/*
	 * HoverCheck
	 */
	public boolean isHovered(final int mouseX, final int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
