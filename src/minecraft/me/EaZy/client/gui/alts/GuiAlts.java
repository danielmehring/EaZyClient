package me.EaZy.client.gui.alts;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.FileManager;
import me.EaZy.client.alts.Alt;
import me.EaZy.client.alts.AltRenderer;
import me.EaZy.client.alts.GuiAltsLogin;
import me.EaZy.client.alts.GuiMcLeaks;
import me.EaZy.client.alts.LoginManager;
import me.EaZy.client.gui.GuiCleanUpAltz;
import me.EaZy.client.hooks.FrameHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ScaledResolution;

public class GuiAlts extends GuiScreen {

	public static final int EaZy = 60;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final GuiScreen prevMenu;
	private static GuiAltList altList;

	private int errorTimer;

	public GuiAlts(final GuiScreen par1GuiScreen) {
		prevMenu = par1GuiScreen;
	}

	@Override
	public void initGui() {
		altList = new GuiAltList(mc, this);
		altList.registerScrollButtons(7, 8);
		altList.elementClicked(-1, false, 0, 0);
		buttonList.clear();

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		Keyboard.enableRepeatEvents(true);

		buttonList
				.add(new GuiButton(1, sr.getScaledWidth() / 2 - 28, sr.getScaledHeight() - 20, 60, 20, "Direct Login"));

		buttonList.add(new GuiButton(0, sr.getScaledWidth() / 2 - 13 - 45, sr.getScaledHeight() - 20, 30, 20, "Use"));
		buttonList.add(new GuiButton(4, sr.getScaledWidth() / 2 - 13 - 15, sr.getScaledHeight() - 20, 30, 20, "Edit"));
		buttonList.add(new GuiButton(3, sr.getScaledWidth() / 2 - 13 + 15, sr.getScaledHeight() - 20, 30, 20, "Star"));
		buttonList
				.add(new GuiButton(5, sr.getScaledWidth() / 2 - 13 + 45, sr.getScaledHeight() - 20, 36, 20, "Delete"));

		buttonList.add(new GuiButton(6, sr.getScaledWidth() / 2 + 170, sr.getScaledHeight() - 20, 30, 20, "Exit"));
		buttonList.add(new GuiButton(2, sr.getScaledWidth() / 2 - 200, sr.getScaledHeight() - 20, 30, 20, "Add"));

		// TODO draw the other buttons
		buttonList.add(new GuiButton(7, sr.getScaledWidth() / 2 - 170, sr.getScaledHeight() - 20, 36, 20, "Import"));
		buttonList.add(new GuiButton(8, sr.getScaledWidth() / 2 + 140, sr.getScaledHeight() - 20, 30, 20, "Clean"));
		buttonList
				.add(new GuiButton(9, sr.getScaledWidth() / 2 + 80, sr.getScaledHeight() - 20, 60, 20, "Change Name"));
		buttonList.add(new GuiButton(10, sr.getScaledWidth() / 2 - 134, sr.getScaledHeight() - 20, 36, 20, "Export"));
		buttonList.add(new GuiButton(11, sr.getScaledWidth() / 2 - 98, sr.getScaledHeight() - 20, 36, 20, "MCL"));
	}

	@Override
	public void updateScreen() {
		if (!GuiAltList.alts.isEmpty() && altList.getSelectedSlot() != -1) { // Selected
			((GuiButton) buttonList.get(0)).visible = false;
			((GuiButton) buttonList.get(1)).visible = true;
			((GuiButton) buttonList.get(2)).visible = true;
			((GuiButton) buttonList.get(3)).visible = true;
			((GuiButton) buttonList.get(4)).visible = true;
			((GuiButton) buttonList.get(0)).enabled = false;
			((GuiButton) buttonList.get(1)).enabled = true;
			((GuiButton) buttonList.get(2)).enabled = true;
			((GuiButton) buttonList.get(3)).enabled = true;
			((GuiButton) buttonList.get(4)).enabled = true;
		} else { // Nothing Selected
			((GuiButton) buttonList.get(1)).visible = false;
			((GuiButton) buttonList.get(2)).visible = false;
			((GuiButton) buttonList.get(3)).visible = false;
			((GuiButton) buttonList.get(4)).visible = false;
			((GuiButton) buttonList.get(0)).visible = true;
			((GuiButton) buttonList.get(0)).enabled = true;
			((GuiButton) buttonList.get(1)).enabled = false;
			((GuiButton) buttonList.get(2)).enabled = false;
			((GuiButton) buttonList.get(3)).enabled = false;
			((GuiButton) buttonList.get(4)).enabled = false;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.enabled) {
			switch (clickedButton.id) {
			case 0: {
				final Alt alt = GuiAltList.alts.get(altList.getSelectedSlot());
				if (alt.isCracked()) {
					LoginManager.changeCrackedName(alt.getEmail());
					mc.displayGuiScreen(prevMenu);
				} else {
					final String reply = LoginManager.login(alt.getEmail(), alt.getPassword());
					if (reply.isEmpty()) {
						mc.displayGuiScreen(prevMenu);
						alt.setChecked(Minecraft.session.getUsername());
						FileManager.saveAltsManager();
					} else {
						if (reply.equals("�4�lWrong password!")) {
							final String deleteQuestion = "This Alt doesn\'t work or you are Mojang Banned! You wanna remove?";
							final String deleteWarning = "\"" + alt.getNameOrEmail()
									+ "\" will be lost forever! (A long time!)";
							mc.displayGuiScreen(
									new GuiYesNo(this, deleteQuestion, deleteWarning, "Delete", "Cancel", 1));
						}
					}
				}
				break;
			}
			case 1:
				mc.displayGuiScreen(new GuiAltsLogin(this));
				break;
			case 2:
				mc.displayGuiScreen(new GuiAltAdd(this));
				break;
			case 3: {
				Alt alt = GuiAltList.alts.get(altList.getSelectedSlot());
				alt.setStarred(!(alt = GuiAltList.alts.get(altList.getSelectedSlot())).isStarred());
				GuiAltList.sortAlts();
				FileManager.saveAltsManager();
				break;
			}
			case 4: {
				final Alt alt = GuiAltList.alts.get(altList.getSelectedSlot());
				mc.displayGuiScreen(new GuiAltEdit(this, alt));
				break;
			}
			case 5: {
				final Alt alt = GuiAltList.alts.get(altList.getSelectedSlot());
				final String deleteQuestion = "Are you sure you want to remove this alt?";
				final String deleteWarning = "\"" + alt.getNameOrEmail() + "\" will be lost forever! (A long time!)";
				mc.displayGuiScreen(new GuiYesNo(this, deleteQuestion, deleteWarning, "Delete", "Cancel", 1));
				break;
			}
			case 6:
				mc.displayGuiScreen(prevMenu);
				break;
			case 7:
				new Thread(() -> {
					final JFileChooser fileChooser = new JFileChooser(FileManager.eazyDir) {

						@Override
						protected JDialog createDialog(final Component parent) throws HeadlessException {
							final JDialog dialog = super.createDialog(parent);
							dialog.setAlwaysOnTop(true);
							return dialog;
						}
					};
					fileChooser.setFileSelectionMode(0);
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser
							.addChoosableFileFilter(new FileNameExtensionFilter("E-Mail:Password format (TXT)", "txt"));
					final int action = fileChooser.showOpenDialog(FrameHook.getFrame());
					if (action == 0) {
						try {
							final File file = fileChooser.getSelectedFile();
							try (BufferedReader load = new BufferedReader(new FileReader(file))) {
								String line = "";
								while ((line = load.readLine()) != null) {
									final String[] data = line.split(":");
									if (data.length != 2) {
										continue;
									}
									GuiAltList.alts.add(new Alt(data[0], data[1], null));
								}
							}
							GuiAltList.sortAlts();
							FileManager.saveAltsManager();
						} catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				break;
			case 8:
				mc.displayGuiScreen(new GuiCleanUpAltz(this));
				break;
			case 9:
				mc.displayGuiScreen(new GuiChangeName(this));
				break;
			case 10:
				new Thread(() -> {
					final JFileChooser fileChooser = new JFileChooser(FileManager.eazyDir) {

						@Override
						protected JDialog createDialog(final Component parent) throws HeadlessException {
							final JDialog dialog = super.createDialog(parent);
							dialog.setAlwaysOnTop(true);
							return dialog;
						}
					};
					fileChooser.setFileSelectionMode(0);
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT-File", "txt"));
					final int action = fileChooser.showSaveDialog(FrameHook.getFrame());
					if (action == 0) {
						try {
							final File file = fileChooser.getSelectedFile();
							FileWriter wr = new FileWriter(file);
							boolean first = true;
							for (Alt alt : GuiAltList.alts) {
								if (first) {
									first = false;
								} else {
									wr.append("\n");
								}
								if (!alt.isCracked())
									wr.append(alt.getEmail() + ":" + alt.getPassword());
							}
							wr.flush();
							wr.close();
						} catch (final IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				break;
			case 11:
				mc.displayGuiScreen(new GuiMcLeaks(this));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void confirmClicked(final boolean par1, final int par2) {
		if (par2 == 1 && par1) {
			GuiAltList.alts.remove(altList.getSelectedSlot());
			GuiAltList.sortAlts();
			FileManager.saveAltsManager();
		}
		if (par2 == 2 && par1) {
			GuiAltList.deleteUnchecked();
			GuiAltList.sortAlts();
			FileManager.saveAltsManager();
		}
		mc.displayGuiScreen(this);
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		if (par2 >= 36 && par2 <= height - 57 && (par1 >= width / 2 + 140 || par1 <= width / 2 - 126)) {
			altList.elementClicked(-1, false, 0, 0);
		}
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		drawDefaultBackground(par1, par2);

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);

		altList.drawScreen(par1, par2, par3);

		GuiScreen.targetX = sr.getScaledWidth() / 2 - 200;
		GuiScreen.targetY = 0;
		GuiScreen.targetX2 = sr.getScaledWidth() / 2 + 200;
		GuiScreen.targetY2 = sr.getScaledHeight() + 1;

		Gui.drawRect(width - 40, 0, width, height, 0x7c000000);

		Gui.drawRect(0, 0, 40, height, 0x7c000000);

		// Gui.drawRect(90, 0, 0, height, 0x7c000000);
		Gui.drawRect(
				sr.getScaledWidth() / 2 - 170 + 72 + 36, sr.getScaledHeight(), altList.getSelectedSlot() != -1
						? sr.getScaledWidth() / 2 - 170 + 36 + 76 : sr.getScaledWidth() / 2 - 170 + 36 + 106,
				sr.getScaledHeight() - 20, 0x62000000);

		Gui.drawRect(sr.getScaledWidth() / 2 - 200, 0, sr.getScaledWidth() / 2 + 200, 20, 0x42000000);

		Gui.drawRect(altList.getSelectedSlot() != -1 ? sr.getScaledWidth() / 2 + 68 : sr.getScaledWidth() / 2 + 32,
				sr.getScaledHeight(), sr.getScaledWidth() / 2 + 80, sr.getScaledHeight() - 20, 0x62000000);

		Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);

		// AltRenderer.drawAltFace(mc.getSession().getUsername(),
		// sr.getScaledWidth() / 4 + sr.getScaledWidth() / 17, 1,
		// 18, 18, false);
		if (altList.getSelectedSlot() != -1 && altList.getSelectedSlot() < GuiAltList.alts.size()) {
			final Alt alt = GuiAltList.alts.get(altList.getSelectedSlot());
			AltRenderer.drawAltBody(alt.getNameOrEmail(), sr.getScaledWidth() - sr.getScaledWidth() / 4,
					height / 2 - 64 - 9, 64, 128);
			mc.fontRendererObj.drawString("Info:", 2, sr.getScaledHeight() / 4, 0x66ff00);
			mc.fontRendererObj.drawString(
					"OptiFine-Cape: " + (alt.hasOFCape == 0 ? "loading..."
							: alt.hasOFCape == 1 ? "false" : alt.hasOFCape == 2 ? "true" : "IDK, itz uncheckd"),
					2, sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT, 0x66ff00);
			mc.fontRendererObj.drawString(
					"LabyMod-Cape: " + (alt.hasLabyModCape == 0 ? "loading..."
							: alt.hasLabyModCape == 1 ? "false"
									: alt.hasLabyModCape == 2 ? "true" : "IDK, itz uncheckd"),
					2, sr.getScaledHeight() / 4 + mc.fontRendererObj.FONT_HEIGHT * 2, 0x66ff00);
		}

		GL11.glPushMatrix();
		GL11.glTranslated(sr.getScaledWidth() / 2, 2, 0);
		GL11.glScaled(1.9, 1.9, 42);
		drawCenteredString(fontRendererObj, Minecraft.session.getUsername(), 0, 0, 0x00face);
		GL11.glPopMatrix();

		if (errorTimer > 0) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0f, 0.0f, 0.0f, errorTimer / 16.0f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(0.0, 0.0);
			GL11.glVertex2d(width, 0.0);
			GL11.glVertex2d(width, height);
			GL11.glVertex2d(0.0, height);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			--errorTimer;
		}

		super.drawScreen(par1, par2, par3);
	}

}
