package me.EaZy.client.tabgui;

import java.util.List;

import me.EaZy.client.Category;
import me.EaZy.client.events.KeyPressEvent;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.hud.Appearance;
import me.EaZy.client.modules.hud.Other;
import me.EaZy.client.tabgui.tab.GuiItem;
import me.EaZy.client.tabgui.tab.GuiTab;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.common.collect.Lists;

public class GuiTabHandler {

	public static final int EaZy = 201;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private List<GuiTab> tabs;
	private int selected;
	private boolean opened;
	public static int width;

	public void setup() {
		EventManager.register(this);
		tabs = Lists.newArrayList();
		selected = 0;
		GuiTabHandler.width = 0;
		for (final Category type : Category.values()) {
			if (type.name().equalsIgnoreCase("HIDDEN") || type.name().equalsIgnoreCase("HUD")) {
				continue;
			}
			final String catNamelower = type.name().toLowerCase();
			final String catName = Character.toUpperCase(catNamelower.charAt(0)) + catNamelower.substring(1);
			final GuiTab tab = new GuiTab(catName);
			Client.getModules().stream().filter((
					mod) -> !(!mod.isCategory(type) || mod.isCategory(Category.HIDDEN) || mod.isCategory(Category.HUD)))
					.forEachOrdered((mod) -> {
						tab.addButton(new GuiItem(mod));
					});
			tabs.add(tab);
		}
		tabs.sort((final GuiTab o1, final GuiTab o2) -> o1.getTabName().compareTo(o2.getTabName()));
	}

	// TODO
	public void drawGui(final int startX, final int startY) {

		final FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
		tabs.stream().filter((tab) -> !(font.getStringWidth(tab.getTabName()) < GuiTabHandler.width))
				.forEachOrdered((tab) -> {
					GuiTabHandler.width = font.getStringWidth(" " + tab.getTabName());
				});
		int height = startY;
		boolean hasSelected = false;
		for (final GuiTab tab2 : tabs) {
			boolean selected = tabs.get(this.selected) == tab2 && opened;
			boolean hovered = tabs.get(this.selected) == tab2;
			if (selected) {
				hasSelected = true;
			}
			tab2.drawTab(startX + 2 + 3, height + 2, GuiTabHandler.width, selected, hovered);
			int ye = tab2.drawButtons(height, GuiTabHandler.width, selected, hovered);

			

			height += 12;
		}
		

	}

	public EventTarget onEventCalled(final KeyPressEvent event) {
		if (!Client.setmgr.getSettingByName(Other.mod, "TabGui").getValBoolean()) {
			return null;
		}
		if (event.getKeyCode() == 203) {
			opened = false;
		}
		tabs.stream().filter((final GuiTab tab) -> opened && tabs.get(selected) == tab).forEach((final GuiTab tab) -> {
			tab.keyboard(event.getKeyCode());
		});
		if (opened) {
			return null;
		}
		if (event.getKeyCode() == 208) {
			++selected;
			if (selected > tabs.size() - 1) {
				selected = 0;
			}
		}
		if (event.getKeyCode() == 200) {
			--selected;
			if (selected < 0) {
				selected = tabs.size() - 1;
			}
		}
		if (event.getKeyCode() == 205) {
			opened = true;
		}
		return null;
	}

	public int getTabHeight() {
		return 12;
	}
}
