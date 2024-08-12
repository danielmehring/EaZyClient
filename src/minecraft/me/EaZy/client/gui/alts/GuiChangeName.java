/*
 * Decompiled with CFR 0_117.
 */
package me.EaZy.client.gui.alts;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.alts.AltRenderer;
import me.EaZy.client.alts.GuiEmailField;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiChangeName extends GuiScreen {

	public static final int EaZy = 2049;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	protected GuiScreen prevMenu;
	protected GuiEmailField nameBox;

	public GuiChangeName(final GuiScreen par1GuiScreen) {
		prevMenu = par1GuiScreen;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72 + 12 + 40, "Change"));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12 + 40, "Cancel"));
		nameBox = new GuiEmailField(0, fontRendererObj, width / 2 - 100, 60, 200, 20);
		nameBox.setMaxStringLength(48);
		nameBox.setFocused(true);
		nameBox.setText(mc.session.getUsername());
	}

	@Override
	public void updateScreen() {
		((GuiButton) buttonList.get(0)).enabled = nameBox.getText().equalsIgnoreCase(mc.session.getUsername());
		super.updateScreen();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
		if (button.enabled) {
			if (button.id == 1) {
				mc.displayGuiScreen(prevMenu);
			} else if (button.id == 0) {
				String s = "";
				if ((s = nameBox.getText()).equalsIgnoreCase(mc.session.getUsername())) {
					mc.session.username = s;
				}
				mc.displayGuiScreen(prevMenu);
			}
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		nameBox.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		nameBox.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		drawDefaultBackground(par1, par2);
		drawCenteredString(fontRendererObj, "Change Name", width / 2, 20, 16777215);
		drawCenteredString(fontRendererObj, "Current: " + mc.session.getUsername(), width / 2, 30, 0xaaaaaa);
		drawString(fontRendererObj, "Name", width / 2 - 100, 47, 10526880);
		nameBox.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
}
