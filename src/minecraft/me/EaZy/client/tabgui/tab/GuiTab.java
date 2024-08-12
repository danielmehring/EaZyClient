package me.EaZy.client.tabgui.tab;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.hud.Appearance;
import me.EaZy.client.tabgui.GuiTabHandler;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.gui.Gui;

public class GuiTab {

	public static final int EaZy = 203;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private final List<GuiItem> buttons = Lists.newArrayList();
	private final String label;
	public int width;
	private int selected = 0;

	private static int y2withfade;

	private static int ywithfade;

	public GuiTab(final String label) {
		this.label = label;
	}

	public void drawTab(final int x, final int y, final int widest, final boolean selected, final boolean hovered) {

		String prefix;

		prefix = hovered ? "§f" : "§7";

		if (hovered) {

			if (ywithfade < y) {
				ywithfade++;
			}
			if (ywithfade > y) {
				ywithfade--;

			}

			Gui.drawRect(x - 6, ywithfade + 9, x + GuiTabHandler.width - (selected ? 1 : 0), ywithfade + 10,
					Client.getColor(0l).getRGB());
			Gui.drawRect(x - 6, ywithfade - 2, x + GuiTabHandler.width - (selected ? 1 : 0), ywithfade - 1,
					Client.getColor(0l).getRGB());

		}
		// TODO

		Client.font.drawStringWithShadow(prefix + label, x - 1, y, 16777215);

	}

	public int drawButtons(final int startY, final int width, final boolean selected, final boolean hovered) {
		if (!selected) {
			return -1;
		}

		int y = startY;

		for (final GuiItem button : buttons) {

			y += 12;
			if (this.width == 2) {
				this.width = Client.font.getStringWidth(button.getName() + " ");
			}
			if (Client.font.getStringWidth(button.getName() + " ") < this.width) {
				continue;
			}
			this.width = Client.font.getStringWidth(button.getName() + " ");
		}

		Gui.drawRect(width + 6, startY, width + this.width + 10, y, new Color(0.1f, 0.1f, 0.1f, 0.6f).getRGB());

		int height = startY;
		for (final GuiItem button2 : buttons) {
			if (buttons.get(this.selected) == button2) {

				if (y2withfade > height) {
					y2withfade--;
				}

				if (y2withfade < height) {
					y2withfade++;
				}

				Gui.drawRect(width + 6, y2withfade, width + this.width + 11, y2withfade + 1,
						Client.getColor(0l).getRGB());
				Gui.drawRect(width + 6, y2withfade + 11, width + this.width + 11, y2withfade + 12,
						Client.getColor(0l).getRGB());

			}

			final String prefix = button2.getMod().isToggled() ? "§f" : "§7";
			Client.font.drawStringWithShadow(prefix + button2.getName(), width + 10, height + 2,
					button2.getMod().isToggled() ? -3495936 : -5723992);
			height += 12;
		}
		return height;
	}

	public void keyboard(final int keyCode) {
		switch (keyCode) {
		case 208: {
			++selected;
			if (selected <= buttons.size() - 1) {
				break;
			}
			selected = 0;
			break;
		}
		case 200: {
			--selected;
			if (selected >= 0) {
				break;
			}
			selected = buttons.size() - 1;
			break;
		}
		case 28:
		case 205: {
			Client.toggle(buttons.get(selected).getMod().getName());
		}
		}
	}

	public String getTabName() {
		return label;
	}

	public void addButton(final GuiItem button) {

		buttons.add(button);
		buttons.sort((final GuiItem o1, final GuiItem o2) -> o1.getName().compareTo(o2.getName()));
	}

}
