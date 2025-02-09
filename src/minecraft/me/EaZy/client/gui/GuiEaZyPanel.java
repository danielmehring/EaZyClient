package me.EaZy.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.games.GuiGames;
import me.EaZy.client.gui.alts.GuiAlts;
import me.EaZy.client.hooks.FrameHook;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GuiEaZyPanel extends GuiScreen {

	public static final int EaZy = 70;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private GuiMainMenu prevMenu;

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		drawDefaultBackground(par1, par2);

		GuiScreen.targetX = sr.getScaledWidth() / 2 - 200;
		GuiScreen.targetY = sr.getScaledHeight() / 2 - 122;
		GuiScreen.targetX2 = sr.getScaledWidth() / 2 + 200;
		GuiScreen.targetY2 = sr.getScaledHeight() / 2 + 100;

		((GuiButton) buttonList.get(8)).displayString = (Configs.bgMoving ? "Disable " : "Enable ")
				+ "BackGround Movement";

		if (!Client.isHidden) {
			Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
		}

		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {}

	@Override
	public void initGui() {

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		Keyboard.enableRepeatEvents(true);

		buttonList.add(new GuiButton(99, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 13, 98 * 2, 20,
				"AltManager"));

		buttonList.add(
				new GuiButton(100, sr.getScaledWidth() / 2 - 196 / 2, height / 2 - 7 + 16 + 20 + 2, 196, 20, "Back"));

		buttonList.add(new GuiButton(101, width / 2 - 98, height / 2 - 51 + 16, 97, 20, "Credits"));
		buttonList.add(new GuiButton(102, width / 2 + 1, height / 2 - 51 + 16, 97, 20, "Changelog"));

		buttonList.add(new GuiButton(103, width / 2 - 98, height / 2 - 7 + 16, 97, 20, "Games"));
		buttonList.add(new GuiButton(104, width / 2 + 1, height / 2 - 7 + 16, 97, 20, "EaZy Options"));
		buttonList.add(new GuiButton(142, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 13 - 44, 97, 20,
				"Change BackGround"));
		buttonList.add(new GuiButton(143, sr.getScaledWidth() / 2 + 1, sr.getScaledHeight() / 2 - 13 - 44, 97, 20,
				"Default BackGround"));
		buttonList.add(new GuiButton(144, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() / 2 - 13 - 66, 98 * 2, 20,
				(Configs.bgMoving ? "Disable " : "Enable ") + "BackGround Movement"));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.id == 100) {
			mc.displayGuiScreen(prevMenu);
		}

		if (clickedButton.id == 144) {
			Configs.bgMoving = !Configs.bgMoving;
		}

		if (clickedButton.id == 99) {
			mc.displayGuiScreen(new GuiAlts(prevMenu));
		}

		if (clickedButton.id == 101) {
			mc.displayGuiScreen(new CuzImGeileSchnitte(this));
		}

		if (clickedButton.id == 102) {
			mc.displayGuiScreen(new GuiChangelog(this));
		}

		if (clickedButton.id == 103) {
			mc.displayGuiScreen(new GuiGames(this));
		}

		if (clickedButton.id == 104) {
			mc.displayGuiScreen(new GuiEaZySettings(this));
		}

		if (clickedButton.id == 142) {
			final JFileChooser fileChooser = new JFileChooser() {

				@Override
				protected JDialog createDialog(final Component parent) throws HeadlessException {
					final JDialog dialog = super.createDialog(parent);
					dialog.setAlwaysOnTop(true);
					return dialog;
				}
			};
			fileChooser.setFileSelectionMode(0);
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Picture (png, jpg)", "png", "jpg", "jpeg"));
			final int action = fileChooser.showOpenDialog(FrameHook.getFrame());
			if (action == 0) {
				try {
					final File file = fileChooser.getSelectedFile();
					Configs.bgPath = file.getAbsolutePath();
					FileManager.saveMain();
					GuiMainMenu.doTheFcnkShit();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (clickedButton.id == 143) {
			Configs.bgPath = "";
			FileManager.saveMain();
			GuiMainMenu.doTheFcnkShit();
		}

	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		if (par2 == 1) {
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
	}
}
