package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen {

public static final int EaZy = 521;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_146608_a;

	/** Reference to the GameSettings object. */
	private final GameSettings game_settings_2;
	private final java.util.List field_146604_g = Lists.newArrayList();
	private final java.util.List field_146609_h = Lists.newArrayList();
	private String field_146610_i;
	private String[] field_146607_r;
	private GuiSnooper.List field_146606_s;
	private GuiButton field_146605_t;
	// private static final String __OBFID = "http://https://fuckuskid00000714";

	public GuiSnooper(final GuiScreen p_i1061_1_, final GameSettings p_i1061_2_) {
		field_146608_a = p_i1061_1_;
		game_settings_2 = p_i1061_2_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		field_146610_i = I18n.format("options.snooper.title", new Object[0]);
		final String var1 = I18n.format("options.snooper.desc", new Object[0]);
		final ArrayList var2 = Lists.newArrayList();
		final Iterator var3 = fontRendererObj.listFormattedStringToWidth(var1, width - 30).iterator();

		while (var3.hasNext()) {
			final String var4 = (String) var3.next();
			var2.add(var4);
		}

		field_146607_r = (String[]) var2.toArray(new String[0]);
		field_146604_g.clear();
		field_146609_h.clear();
		buttonList.add(field_146605_t = new GuiButton(1, width / 2 - 152, height - 30, 150, 20,
				game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
		buttonList.add(new GuiButton(2, width / 2 + 2, height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
		final boolean var6 = mc.getIntegratedServer() != null
				&& mc.getIntegratedServer().getPlayerUsageSnooper() != null;
		Iterator var7 = new TreeMap(mc.getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
		Entry var5;

		while (var7.hasNext()) {
			var5 = (Entry) var7.next();
			field_146604_g.add((var6 ? "C " : "") + (String) var5.getKey());
			field_146609_h.add(fontRendererObj.trimStringToWidth((String) var5.getValue(), width - 220));
		}

		if (var6) {
			var7 = new TreeMap(mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet()
					.iterator();

			while (var7.hasNext()) {
				var5 = (Entry) var7.next();
				field_146604_g.add("S " + (String) var5.getKey());
				field_146609_h.add(fontRendererObj.trimStringToWidth((String) var5.getValue(), width - 220));
			}
		}

		field_146606_s = new GuiSnooper.List();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_146606_s.func_178039_p();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				game_settings_2.saveOptions();
				game_settings_2.saveOptions();
				mc.displayGuiScreen(field_146608_a);
			}

			if (button.id == 1) {
				game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
				field_146605_t.displayString = game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		field_146606_s.drawScreen(mouseX, mouseY, partialTicks);
		int var4 = 22;
		final String[] var5 = field_146607_r;
		final int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			final String var8 = var5[var7];
			drawCenteredString(fontRendererObj, var8, width / 2, var4, 8421504);
			var4 += fontRendererObj.FONT_HEIGHT;
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	class List extends GuiSlot {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000715";

		public List() {
			super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40,
					GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
		}

		@Override
		protected int getSize() {
			return field_146604_g.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return false;
		}

		@Override
		protected void drawBackground() {
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {

		}

		@Override
		protected int getScrollBarX() {
			return width - 10;
		}
	}
}
