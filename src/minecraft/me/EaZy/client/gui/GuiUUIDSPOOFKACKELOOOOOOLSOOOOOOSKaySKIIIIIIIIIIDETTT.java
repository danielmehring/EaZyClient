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
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.MiscUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiUUIDSPOOFKACKELOOOOOOLSOOOOOOSKaySKIIIIIIIIIIDETTT extends GuiScreen {

	public static final int EaZy = 2064;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final GuiScreen prevScreen;

	private GuiTextField ipBox;

	public GuiUUIDSPOOFKACKELOOOOOOLSOOOOOOSKaySKIIIIIIIIIIDETTT(final GuiScreen prevScreen) {
		this.prevScreen = prevScreen;
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		drawDefaultBackground(par1, par2);

		ipBox.drawTextBox();

		mc.fontRendererObj.drawString("§7IP-Address", width / 2 - 100, height / 2 - 50 - 10, 0xffffffff);

		super.drawScreen(par1, par2, par3);
	}

	@Override
	public void updateScreen() {
		ipBox.updateCursorCounter();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiButton(3, width / 2 - 100, height / 2 + 31, "Set"));
		buttonList.add(new GuiButton(4, width / 2 - 100, height / 2 + 31 + 24, "Back"));
		buttonList.add(new GuiButton(5, width / 2 - 100, height / 2 + 31 - 24, Client.faker ? "ON" : "OFF"));

		ipBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, height / 2 - 50, 200, 20);
		ipBox.setMaxStringLength(100);
		ipBox.setFocused(true);
		ipBox.setText(Client.fakedIP);
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.id == 3) {
			Client.fakedIP = ipBox.getText();
			mc.displayGuiScreen(prevScreen);
		}
		if (clickedButton.id == 4) {
			mc.displayGuiScreen(prevScreen);
		}
		if (clickedButton.id == 5) {
			Client.faker = !Client.faker;
			((GuiButton) buttonList.get(2)).displayString = Client.faker ? "ON" : "OFF";
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		ipBox.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
		if (par2 == Keyboard.KEY_F5) {
			mc.displayGuiScreen(this);
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		ipBox.mouseClicked(par1, par2, par3);
	}
}
