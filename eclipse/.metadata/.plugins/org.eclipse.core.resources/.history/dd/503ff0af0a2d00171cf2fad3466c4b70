package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {

public static final int EaZy = 470;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiScreen field_175361_a;
	private String field_175360_f;
	// private static final String __OBFID = "http://https://fuckuskid00001932";

	public GuiCustomizeSkin(final GuiScreen p_i45516_1_) {
		field_175361_a = p_i45516_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		int var1 = 0;
		field_175360_f = I18n.format("options.skinCustomisation.title", new Object[0]);
		final EnumPlayerModelParts[] var2 = EnumPlayerModelParts.values();
		final int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			final EnumPlayerModelParts var5 = var2[var4];
			buttonList.add(new GuiCustomizeSkin.ButtonPart(var5.func_179328_b(), width / 2 - 155 + var1 % 2 * 160,
					height / 6 + 24 * (var1 >> 1), 150, 20, var5, null));
			++var1;
		}

		if (var1 % 2 == 1) {
			++var1;
		}

		buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 24 * (var1 >> 1),
				I18n.format("gui.done", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 200) {
				Minecraft.gameSettings.saveOptions();
				mc.displayGuiScreen(field_175361_a);
			} else if (button instanceof GuiCustomizeSkin.ButtonPart) {
				final EnumPlayerModelParts var2 = ((GuiCustomizeSkin.ButtonPart) button).field_175234_p;
				Minecraft.gameSettings.func_178877_a(var2);
				button.displayString = func_175358_a(var2);
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

		GuiScreen.targetX = width / 2 - 160;
		GuiScreen.targetY = height / 6 + 24 - 30;
		GuiScreen.targetX2 = width / 2 + 160;
		GuiScreen.targetY2 = height / 6 + 24 + 178 - 78;

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private String func_175358_a(final EnumPlayerModelParts p_175358_1_) {
		String var2;

		if (Minecraft.gameSettings.func_178876_d().contains(p_175358_1_)) {
			var2 = I18n.format("options.on", new Object[0]);
		} else {
			var2 = I18n.format("options.off", new Object[0]);
		}

		return p_175358_1_.func_179326_d().getFormattedText() + ": " + var2;
	}

	class ButtonPart extends GuiButton {
		private final EnumPlayerModelParts field_175234_p;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001930";

		private ButtonPart(final int p_i45514_2_, final int p_i45514_3_, final int p_i45514_4_, final int p_i45514_5_,
				final int p_i45514_6_, final EnumPlayerModelParts p_i45514_7_) {
			super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, func_175358_a(p_i45514_7_));
			field_175234_p = p_i45514_7_;
		}

		ButtonPart(final int p_i45515_2_, final int p_i45515_3_, final int p_i45515_4_, final int p_i45515_5_,
				final int p_i45515_6_, final EnumPlayerModelParts p_i45515_7_, final Object p_i45515_8_) {
			this(p_i45515_2_, p_i45515_3_, p_i45515_4_, p_i45515_5_, p_i45515_6_, p_i45515_7_);
		}
	}
}
