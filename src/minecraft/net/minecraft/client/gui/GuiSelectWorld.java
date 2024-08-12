package net.minecraft.client.gui;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {

public static final int EaZy = 514;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final DateFormat field_146633_h = new SimpleDateFormat();
	protected GuiScreen field_146632_a;
	protected String field_146628_f = "Select world";
	private boolean field_146634_i;
	private int field_146640_r;
	private java.util.List field_146639_s;
	private GuiSelectWorld.List field_146638_t;
	private String field_146637_u;
	private String field_146636_v;
	private final String[] field_146635_w = new String[4];
	private boolean field_146643_x;
	private GuiButton field_146642_y;
	private GuiButton field_146641_z;
	private GuiButton field_146630_A;
	private GuiButton field_146631_B;
	// private static final String __OBFID = "http://https://fuckuskid00000711";

	public GuiSelectWorld(final GuiScreen p_i1054_1_) {
		field_146632_a = p_i1054_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		field_146628_f = I18n.format("selectWorld.title", new Object[0]);

		try {
			func_146627_h();
		} catch (final AnvilConverterException var2) {
			logger.error("Couldn\'t load level list", var2);
			mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
			return;
		}

		field_146637_u = I18n.format("selectWorld.world", new Object[0]);
		field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
		field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
		field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
		field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
		field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
		field_146638_t = new GuiSelectWorld.List(mc);
		field_146638_t.registerScrollButtons(4, 5);
		func_146618_g();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_146638_t.func_178039_p();
	}

	private void func_146627_h() throws AnvilConverterException {
		final ISaveFormat var1 = mc.getSaveLoader();
		field_146639_s = var1.getSaveList();
		Collections.sort(field_146639_s);
		field_146640_r = -1;
	}

	protected String func_146621_a(final int p_146621_1_) {
		return ((SaveFormatComparator) field_146639_s.get(p_146621_1_)).getFileName();
	}

	protected String func_146614_d(final int p_146614_1_) {
		String var2 = ((SaveFormatComparator) field_146639_s.get(p_146614_1_)).getDisplayName();

		if (StringUtils.isEmpty(var2)) {
			var2 = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
		}

		return var2;
	}

	public void func_146618_g() {
		buttonList.add(field_146641_z = new GuiButton(1, width / 2 - 154, height - 52, 150, 20,
				I18n.format("selectWorld.select", new Object[0])));
		buttonList.add(new GuiButton(3, width / 2 + 4, height - 52, 150, 20,
				I18n.format("selectWorld.create", new Object[0])));
		buttonList.add(field_146630_A = new GuiButton(6, width / 2 - 154, height - 28, 72, 20,
				I18n.format("selectWorld.rename", new Object[0])));
		buttonList.add(field_146642_y = new GuiButton(2, width / 2 - 76, height - 28, 72, 20,
				I18n.format("selectWorld.delete", new Object[0])));
		buttonList.add(field_146631_B = new GuiButton(7, width / 2 + 4, height - 28, 72, 20,
				I18n.format("selectWorld.recreate", new Object[0])));
		buttonList.add(new GuiButton(0, width / 2 + 82, height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
		field_146641_z.enabled = false;
		field_146642_y.enabled = false;
		field_146630_A.enabled = false;
		field_146631_B.enabled = false;
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				final String var2 = func_146614_d(field_146640_r);

				if (var2 != null) {
					field_146643_x = true;
					final GuiYesNo var3 = func_152129_a(this, var2, field_146640_r);
					mc.displayGuiScreen(var3);
				}
			} else if (button.id == 1) {
				func_146615_e(field_146640_r);
			} else if (button.id == 3) {
				mc.displayGuiScreen(new GuiCreateWorld(this));
			} else if (button.id == 6) {
				mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(field_146640_r)));
			} else if (button.id == 0) {
				mc.displayGuiScreen(field_146632_a);
			} else if (button.id == 7) {
				final GuiCreateWorld var5 = new GuiCreateWorld(this);
				final ISaveHandler var6 = mc.getSaveLoader().getSaveLoader(func_146621_a(field_146640_r), false);
				final WorldInfo var4 = var6.loadWorldInfo();
				var6.flush();
				var5.func_146318_a(var4);
				mc.displayGuiScreen(var5);
			} else {
				field_146638_t.actionPerformed(button);
			}
		}
	}

	public void func_146615_e(final int p_146615_1_) {
		mc.displayGuiScreen((GuiScreen) null);

		if (!field_146634_i) {
			field_146634_i = true;
			String var2 = func_146621_a(p_146615_1_);

			if (var2 == null) {
				var2 = "World" + p_146615_1_;
			}

			String var3 = func_146614_d(p_146615_1_);

			if (var3 == null) {
				var3 = "World" + p_146615_1_;
			}

			if (mc.getSaveLoader().canLoadWorld(var2)) {
				mc.launchIntegratedServer(var2, var3, (WorldSettings) null);
			}
		}
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		if (field_146643_x) {
			field_146643_x = false;

			if (result) {
				final ISaveFormat var3 = mc.getSaveLoader();
				var3.flushCache();
				var3.deleteWorldDirectory(func_146621_a(id));

				try {
					func_146627_h();
				} catch (final AnvilConverterException var5) {
					logger.error("Couldn\'t load level list", var5);
				}
			}

			mc.displayGuiScreen(this);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		field_146638_t.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, field_146628_f, width / 2, 20, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public static GuiYesNo func_152129_a(final GuiYesNoCallback p_152129_0_, final String p_152129_1_,
			final int p_152129_2_) {
		final String var3 = I18n.format("selectWorld.deleteQuestion", new Object[0]);
		final String var4 = "\'" + p_152129_1_ + "\' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
		final String var5 = I18n.format("selectWorld.deleteButton", new Object[0]);
		final String var6 = I18n.format("gui.cancel", new Object[0]);
		final GuiYesNo var7 = new GuiYesNo(p_152129_0_, var3, var4, var5, var6, p_152129_2_);
		return var7;
	}

	class List extends GuiSlot {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000712";

		public List(final Minecraft mcIn) {
			super(mcIn, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
		}

		@Override
		protected int getSize() {
			return field_146639_s.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
			field_146640_r = slotIndex;
			final boolean var5 = field_146640_r >= 0 && field_146640_r < getSize();
			field_146641_z.enabled = var5;
			field_146642_y.enabled = var5;
			field_146630_A.enabled = var5;
			field_146631_B.enabled = var5;

			if (isDoubleClick && var5) {
				func_146615_e(slotIndex);
			}
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return slotIndex == field_146640_r;
		}

		@Override
		protected int getContentHeight() {
			return field_146639_s.size() * 36;
		}

		@Override
		protected void drawBackground() {
			drawDefaultBackground(mouseX, mouseY);
		}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final SaveFormatComparator var7 = (SaveFormatComparator) field_146639_s.get(p_180791_1_);
			String var8 = var7.getDisplayName();

			if (StringUtils.isEmpty(var8)) {
				var8 = field_146637_u + " " + (p_180791_1_ + 1);
			}

			String var9 = var7.getFileName();
			var9 = var9 + " (" + field_146633_h.format(new Date(var7.getLastTimePlayed()));
			var9 = var9 + ")";
			String var10 = "";

			if (var7.requiresConversion()) {
				var10 = field_146636_v + " " + var10;
			} else {
				var10 = field_146635_w[var7.getEnumGameType().getID()];

				if (var7.isHardcoreModeEnabled()) {
					var10 = EnumChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0])
							+ EnumChatFormatting.RESET;
				}

				if (var7.getCheatsEnabled()) {
					var10 = var10 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
				}
			}

			drawString(GuiSelectWorld.this.fontRendererObj, var8, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
			drawString(GuiSelectWorld.this.fontRendererObj, var9, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
			drawString(GuiSelectWorld.this.fontRendererObj, var10, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
		}
	}
}
