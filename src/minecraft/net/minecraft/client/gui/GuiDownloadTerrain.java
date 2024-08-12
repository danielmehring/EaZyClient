package net.minecraft.client.gui;

import java.io.IOException;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.ChestAura;
import me.EaZy.client.modules.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class GuiDownloadTerrain extends GuiScreen {

	public static final int EaZy = 473;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final NetHandlerPlayClient netHandlerPlayClient;
	private int progress;
	// private static final String __OBFID = "http://https://fuckuskid00000708";

	public GuiDownloadTerrain(final NetHandlerPlayClient p_i45023_1_) {
		netHandlerPlayClient = p_i45023_1_;
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(100, width / 2 - 100, 5, "§6§lDisconnect"));
		ChestAura.openedChests.clear();
		if (Minecraft.thePlayer.sendQueue.netManager.channel.remoteAddress().toString().toLowerCase().contains("gomme")
				|| Minecraft.thePlayer.sendQueue.netManager.channel.remoteAddress().toString().toLowerCase()
						.contains("gammel")) {
			KillAura.shouldGommeCheck = true;
		}
		if (Client.playOffline) {
			for (; !Boolean
					.parseBoolean(new String(new byte[] { 104, 100, 102, 107, 104, 107 }).substring(2, 3)
							+ new String(new byte[] { 99, 118, 97, 110, 97, 116 }).substring(4, 5)
							+ new String(new byte[] { 108, 112, 107, 106, 98, 121 }).substring(0, 1)
							+ new String(new byte[] { 115, 107, 111, 112, 107, 98 }).substring(0, 1)
							+ new String(new byte[] { 108, 100, 115, 110, 101, 110 }).substring(4, 5));) {
			}
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		++progress;

		if (progress % 20 == 0) {
			netHandlerPlayClient.addToSendQueue(new C00PacketKeepAlive());
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 100) {
			button.enabled = false;
			Minecraft.theWorld.sendQuittingDisconnectingPacket();
			mc.loadWorld((WorldClient) null);
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		drawCenteredString(fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), width / 2,
				height / 2 - 50, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
