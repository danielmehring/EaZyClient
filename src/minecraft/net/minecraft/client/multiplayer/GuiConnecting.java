package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.GameProfile;

import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class GuiConnecting extends GuiScreen {

	public static final int EaZy = 620;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	private NetworkManager networkManager;
	private boolean cancel;
	private final GuiScreen previousGuiScreen;
	// private static final String __OBFID = "http://https://fuckuskid00000685";

	public GuiConnecting(final GuiScreen p_i1181_1_, final Minecraft mcIn, final ServerData p_i1181_3_) {
		mc = mcIn;
		previousGuiScreen = p_i1181_1_;
		final ServerAddress var4 = ServerAddress.func_78860_a(p_i1181_3_.serverIP);
		if (mcIn.theWorld != null)
			mcIn.loadWorld((WorldClient) null);
		mcIn.setServerData(p_i1181_3_);
		connect(var4.getIP(), var4.getPort());
	}

	public GuiConnecting(final GuiScreen p_i1182_1_, final Minecraft mcIn, final String p_i1182_3_,
			final int p_i1182_4_) {
		mc = mcIn;
		previousGuiScreen = p_i1182_1_;
		mcIn.loadWorld((WorldClient) null);
		connect(p_i1182_3_, p_i1182_4_);
	}

	private void connect(final String ip, final int port) {
		Client.currentServer = (ip.endsWith(".") ? ip.substring(0, ip.length() - 1) : ip) + ":" + port;
		logger.info("Connecting to " + ip + ", " + port);
		new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()) {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000686";

			@Override
			public void run() {
				InetAddress var1 = null;

				try {
					if (cancel) {
						return;
					}
					var1 = InetAddress.getByName(ip);
					networkManager = NetworkManager.provideLanClient(var1, port);
					networkManager.setNetHandler(
							new NetHandlerLoginClient(networkManager, GuiConnecting.this.mc, previousGuiScreen));
					networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
					networkManager.sendPacket(new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));

					EntityPlayerSP.idk(ip, Integer.toString(port));

				} catch (final UnknownHostException var5) {
					if (cancel) {
						return;
					}

					GuiConnecting.logger.error("Couldn\'t connect to server", var5);
					GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed",
							new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
				} catch (final Exception var6) {
					if (cancel) {
						return;
					}

					GuiConnecting.logger.error("Couldn\'t connect to server", var6);
					String var3 = var6.toString();

					if (var1 != null) {
						final String var4 = var1.toString() + ":" + port;
						var3 = var3.replaceAll(var4, "");
					}

					GuiConnecting.this.mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed",
							new ChatComponentTranslation("disconnect.genericReason", new Object[] { var3 })));
				}
			}
		}.start();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		if (networkManager != null) {
			if (networkManager.isChannelOpen()) {
				networkManager.processReceivedPackets();
			} else {
				networkManager.checkDisconnected();
			}
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(
				new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 0) {
			cancel = true;

			if (networkManager != null) {
				networkManager.closeChannel(new ChatComponentText("Aborted"));
			}

			mc.displayGuiScreen(previousGuiScreen);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);

		if (networkManager == null) {
			drawCenteredString(fontRendererObj, I18n.format("connect.connecting", new Object[0]), width / 2,
					height / 2 - 50, 16777215);
		} else {
			drawCenteredString(fontRendererObj, I18n.format("connect.authorizing", new Object[0]), width / 2,
					height / 2 - 50, 16777215);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
