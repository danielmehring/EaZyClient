package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class GuiYesNo extends GuiScreen {

	public static final int EaZy = 528;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	GuiScreen prevScreen = null;
	/**
	 * A reference to the screen object that created this. Used for navigating
	 * between screens.
	 */
	protected GuiYesNoCallback parentScreen;
	protected String messageLine1;
	private final String messageLine2;
	private final List field_175298_s = Lists.newArrayList();

	/** The text shown for the first button in GuiYesNo */
	protected String confirmButtonText;

	/** The text shown for the second button in GuiYesNo */
	protected String cancelButtonText;
	protected int parentButtonClickedId;
	private int ticksUntilEnable;
	// private static final String __OBFID = "http://https://fuckuskid00000684";

	public GuiYesNo(final GuiYesNoCallback p_i1082_1_, final String p_i1082_2_, final String p_i1082_3_,
			final int p_i1082_4_) {
		parentScreen = p_i1082_1_;
		messageLine1 = p_i1082_2_;
		messageLine2 = p_i1082_3_;
		parentButtonClickedId = p_i1082_4_;
		confirmButtonText = I18n.format("gui.yes", new Object[0]);
		cancelButtonText = I18n.format("gui.no", new Object[0]);
	}

	public GuiYesNo(final GuiScreen prev, final GuiYesNoCallback p_i1083_1_, final String p_i1083_2_,
			final String p_i1083_3_, final String p_i1083_4_, final String p_i1083_5_, final int p_i1083_6_) {
		this(p_i1083_1_, p_i1083_2_, p_i1083_3_, p_i1083_4_, p_i1083_5_, p_i1083_6_);
		prevScreen = prev;
	}

	public GuiYesNo(final GuiYesNoCallback p_i1083_1_, final String p_i1083_2_, final String p_i1083_3_,
			final String p_i1083_4_, final String p_i1083_5_, final int p_i1083_6_) {
		parentScreen = p_i1083_1_;
		messageLine1 = p_i1083_2_;
		messageLine2 = p_i1083_3_;
		confirmButtonText = p_i1083_4_;
		cancelButtonText = p_i1083_5_;
		parentButtonClickedId = p_i1083_6_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 6 + 96, confirmButtonText));
		buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 6 + 96, cancelButtonText));
		field_175298_s.clear();
		field_175298_s.addAll(fontRendererObj.listFormattedStringToWidth(messageLine2, width - 50));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		parentScreen.confirmClicked(button.id == 0, parentButtonClickedId);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, messageLine1, width / 2, 70, 16777215);
		int var4 = 90;

		for (final Iterator var5 = field_175298_s.iterator(); var5.hasNext(); var4 += fontRendererObj.FONT_HEIGHT) {
			final String var6 = (String) var5.next();
			drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Sets the number of ticks to wait before enabling the buttons.
	 */
	public void setButtonDelay(final int p_146350_1_) {
		ticksUntilEnable = p_146350_1_;
		GuiButton var3;

		for (final Iterator var2 = buttonList.iterator(); var2.hasNext(); var3.enabled = false) {
			var3 = (GuiButton) var2.next();
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1) {
			mc.displayGuiScreen(prevScreen);
		} else if (keyCode == 28) {
			parentScreen.confirmClicked(true, parentButtonClickedId);
		} else
			super.keyTyped(typedChar, keyCode);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
		GuiButton var2;

		if (--ticksUntilEnable == 0) {
			for (final Iterator var1 = buttonList.iterator(); var1.hasNext(); var2.enabled = true) {
				var2 = (GuiButton) var1.next();
			}
		}
	}
}
