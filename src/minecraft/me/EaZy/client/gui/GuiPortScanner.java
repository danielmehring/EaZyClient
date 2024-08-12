package me.EaZy.client.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.FileManager;
import me.EaZy.client.utils.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiPortScanner extends GuiScreen {

	public static final int EaZy = 74;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final GuiScreen prevScreen;

	private GuiTextField ipBox;
	private GuiTextField fromBox;
	private GuiTextField toBox;

	private String ipaddrfromlol = "";

	private int failCounter = 0;
	private boolean scanning = false;

	private String status = "Please enter IP!";
	private int failedOpenCounter = 0;

	public GuiPortScanner(final GuiScreen prevScreen) {
		this.prevScreen = prevScreen;
	}

	public GuiPortScanner(final GuiScreen prevScreen, final String ip) {
		this.prevScreen = prevScreen;
		ipaddrfromlol = ip;
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {

		drawDefaultBackground(par1, par2);

		ipBox.drawTextBox();
		fromBox.drawTextBox();
		toBox.drawTextBox();

		mc.fontRendererObj.drawCenteredString("§7" + status, width / 2, 5, 0xffffffff);

		mc.fontRendererObj.drawString("§7IP-Address", width / 2 - 100, height / 2 - 50 - 10, 0xffffffff);
		mc.fontRendererObj.drawString("§7From", width / 2 - 70, height / 2 + 5 - 10 - 10, 0xffffffff);
		mc.fontRendererObj.drawString("§7To", width / 2 + 10, height / 2 + 5 - 10 - 10, 0xffffffff);

		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		if (failCounter > 0) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1.0f, 0.0f, 0.0f, failCounter / 16.0f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2d(0.0, 0.0);
			GL11.glVertex2d(width, 0.0);
			GL11.glVertex2d(width, height);
			GL11.glVertex2d(0.0, height);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			failCounter--;
		}
		if (failedOpenCounter > 0) {
			failedOpenCounter--;
		}
		ipBox.updateCursorCounter();
		fromBox.updateCursorCounter();
		toBox.updateCursorCounter();
		boolean isReady = false;
		if (!scanning && failedOpenCounter == 0) {
			if (ipBox.getText().isEmpty()) {
				status = "Please enter IP!";
			} else {
				if (!ipBox.getText().contains(" ")) {
					if (!fromBox.getText().isEmpty() && !toBox.getText().isEmpty()) {
						if (MiscUtils.isInteger(fromBox.getText()) && MiscUtils.isInteger(toBox.getText())) {
							if (Integer.parseInt(fromBox.getText()) < Integer.parseInt(toBox.getText())) {
								if (Integer.parseInt(fromBox.getText()) >= 0
										&& Integer.parseInt(toBox.getText()) <= 65535) {
									status = "Ready!";
									isReady = true;
								} else {
									status = "From and to must be numbers between 0 and 65535!";
								}
							} else {
								status = "From must be lower then to!";
							}
						} else {
							status = "From and to must be numbers between 0 and 65535!";
						}
					} else {
						status = "Please enter from and to!";
					}
				} else {
					status = "Please enter a vaild IP!";
				}
			}
		}
		((GuiButton) buttonList.get(0)).enabled = isReady;
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiButton(3, width / 2 - 100, height / 2 + 31, "Start"));
		buttonList.add(new GuiButton(4, width / 2 - 100, height / 2 + 31 + 24, "Back"));

		ipBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, height / 2 - 50, 200, 20);
		ipBox.setMaxStringLength(100);
		ipBox.setFocused(true);
		ipBox.setText(ipaddrfromlol.isEmpty() ? "" : ipaddrfromlol);
		fromBox = new GuiTextField(1, fontRendererObj, width / 2 - 70, height / 2 + 5 - 10, 50, 10);
		fromBox.setMaxStringLength(5);
		fromBox.setFocused(false);
		fromBox.setText("25500");
		toBox = new GuiTextField(2, fontRendererObj, width / 2 + 10, height / 2 + 5 - 10, 50, 10);
		toBox.setMaxStringLength(5);
		toBox.setFocused(false);
		toBox.setText("25600");
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.id == 3) {
			// Checks
			try {
				new Thread(() -> {
					scanning = true;
					((GuiButton) buttonList.get(0)).displayString = "Scanning...";
					status = "Scanning...";
					final List<String> ports = new ArrayList<>();
					String ipcheck = ipBox.getText().split(":")[0];
					if (!MiscUtils.isLong(ipcheck.replace(".", ""))) {
						// Convert to number ip
						try {
							ipcheck = InetAddress.getByName(ipcheck).getHostAddress();
						} catch (final UnknownHostException e) {}
					}
					final String ip = ipcheck;
					System.out.println("Checking Ports from " + fromBox.getText() + " to " + toBox.getText()
							+ " on IP: " + (ip.equals(ipBox.getText().split(":")[0]) ? ip
									: ip + " (" + ipBox.getText().split(":")[0] + ")"));
					for (int i = Integer.parseInt(fromBox.getText()); i <= Integer.parseInt(toBox.getText()); i++) {
						final int port = i;
						final Thread th = new Thread(() -> {
							try {
								try (Socket target = new Socket(ip, port)) {
									ports.add(Integer.toString(port));
								}
							} catch (final Exception e) {}
						});
						th.setName("PortScanner #" + port);
						th.start();
						try {
							Thread.sleep(1);
						} catch (final InterruptedException ex) {}
					}
					status = "Confirming...";
					((GuiButton) buttonList.get(0)).displayString = "Confirming...";
					int count = 0;
					while ((count = countCheckThreads()) != 0) {
						try {
							status = "Waiting for Ports: " + count;
							((GuiButton) buttonList.get(0)).displayString = "Waiting for Ports: " + count;
							Thread.currentThread();
							Thread.sleep(10l);
						} catch (final InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					try {
						if (!new File(FileManager.eazyDir.getAbsolutePath(), "PortScans").exists()) {
							new File(FileManager.eazyDir.getAbsolutePath(), "PortScans").mkdir();
						}
						try (PrintWriter save = new PrintWriter(
								new FileWriter(new File(new File(FileManager.eazyDir.getAbsolutePath(), "PortScans"),
										ipBox.getText().split(":")[0] + ".txt")))) {
							if (ports.isEmpty()) {
								save.println("## NO Ports found on \"" + ipBox.getText().split(":")[0] + "\" ##");
							} else {
								save.println("## Ports found on \"" + ipBox.getText().split(":")[0] + "\" ##");
							}
							ports.forEach((p) -> {
								save.println(p);
							});
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop()
									.open(new File(new File(FileManager.eazyDir.getAbsolutePath(), "PortScans"),
											ipBox.getText().split(":")[0] + ".txt"));
						} catch (final IOException e) {
							e.printStackTrace();
						}
					} else {
						status = "Go to your MC folder, then EaZy and look in PortScans!";
					}
					scanning = false;
					((GuiButton) buttonList.get(0)).displayString = "Start";
				}).start();

			} catch (final Exception e) {
				e.printStackTrace();
				failCounter = 8;
				scanning = false;
			}
		}
		if (clickedButton.id == 4) {
			mc.displayGuiScreen(prevScreen);
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		ipBox.textboxKeyTyped(par1, par2);
		fromBox.textboxKeyTyped(par1, par2);
		toBox.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		if (par2 == Keyboard.KEY_F5) {
			mc.displayGuiScreen(this);
		}
	}

	private int countCheckThreads() {
		int counts = 0;
		counts = Thread.getAllStackTraces().keySet().stream().filter((lol) -> (lol.getName().contains("PortScanner #")))
				.map((_item) -> 1).reduce(counts, Integer::sum);
		return counts;
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		ipBox.mouseClicked(par1, par2, par3);
		fromBox.mouseClicked(par1, par2, par3);
		toBox.mouseClicked(par1, par2, par3);
	}
}
