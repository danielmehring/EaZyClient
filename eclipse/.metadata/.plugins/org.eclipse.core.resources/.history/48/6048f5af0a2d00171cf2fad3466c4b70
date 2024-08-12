package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen {

	public static final int EaZy = 480;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		final byte var1 = -16;
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + var1,
				I18n.format("menu.returnToMenu", new Object[0])));

		if (!mc.isIntegratedServerRunning()) {
			((GuiButton) buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
		}

		buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + var1,
				I18n.format("menu.returnToGame", new Object[0])));
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + var1, 98, 20,
				I18n.format("menu.options", new Object[0])));
		GuiButton var3;
		buttonList.add(var3 = new GuiButton(7, width / 2 + 2, height / 4 + 96 + var1, 98, 20,
				I18n.format("menu.shareToLan", new Object[0])));
		buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + var1, 98, 20,
				I18n.format("gui.achievements", new Object[0])));
		buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + var1, 98, 20,
				I18n.format("gui.stats", new Object[0])));
		var3.enabled = mc.isSingleplayer() && !mc.getIntegratedServer().getPublic();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			mc.displayGuiScreen(new GuiOptions(this, Minecraft.gameSettings));
			break;

		case 1:
			button.enabled = false;
			Minecraft.theWorld.sendQuittingDisconnectingPacket();
			mc.loadWorld((WorldClient) null);
			mc.displayGuiScreen(new GuiMainMenu());
			break;
		case 2:
		case 3:
		default:
			break;

		case 4:
			mc.displayGuiScreen((GuiScreen) null);
			mc.setIngameFocus();
			break;

		case 5:
			mc.displayGuiScreen(new GuiAchievements(this, Minecraft.thePlayer.getStatFileWriter()));
			break;

		case 6:
			mc.displayGuiScreen(new GuiStats(this, Minecraft.thePlayer.getStatFileWriter()));
			break;

		case 7:
			mc.displayGuiScreen(new GuiShareToLan(this));
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);

		if (!Client.isHidden) {
			GuiScreen.targetX = width / 2 - 102;
			GuiScreen.targetY = height / 4 - 4;
			GuiScreen.targetX2 = width / 2 + 102;
			GuiScreen.targetY2 = height / 4 + 136;

			if (!Client.isHidden) {
				Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
