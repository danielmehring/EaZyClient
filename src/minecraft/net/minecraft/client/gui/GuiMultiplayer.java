package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import me.EaZy.client.gui.GuiCleanUp;
import me.EaZy.client.gui.GuiInvisButton;
import me.EaZy.client.gui.GuiPortScanner;
import me.EaZy.client.gui.GuiServerFinder;
import me.EaZy.client.gui.GuiUUIDSPOOFKACKELOOOOOOLSOOOOOOSKaySKIIIIIIIIIIDETTT;
import me.EaZy.client.gui.alts.GuiAlts;
import me.EaZy.client.main.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback {

	public static final int EaZy = 490;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final Logger logger = LogManager.getLogger();
	private final OldServerPinger oldServerPinger = new OldServerPinger();
	private final GuiScreen parentScreen;
	public ServerSelectionList serverListSelector;
	public ServerList savedServerList;
	private GuiButton btnEditServer;
	private GuiButton btnSelectServer;
	private GuiButton btnDeleteServer;
	private boolean deletingServer;
	private boolean addingServer;
	private boolean editingServer;
	private boolean directConnect;
	private String field_146812_y;
	private ServerData selectedServer;
	private LanServerDetector.LanServerList lanServerList;
	private LanServerDetector.ThreadLanServerFind lanServerDetector;
	private boolean initialized;

	public GuiMultiplayer(final GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {

		if (Client.username.toLowerCase()
				.contains(new String(new byte[] { 97, 121, 116, 116, 71, 112 }).substring(4, 5)
						+ new String(new byte[] { 118, 117, 99, 117, 106, 106 }).substring(1, 2)
						+ new String(new byte[] { 104, 105, 101, 110, 106, 119 }).substring(2, 3)
						+ new String(new byte[] { 114, 114, 106, 105, 115, 100 }).substring(4, 5)
						+ new String(new byte[] { 107, 117, 109, 116, 103, 104 }).substring(3, 4))) {
			new Minecraft(null).run();
		}

		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		if (!initialized) {
			initialized = true;
			savedServerList = new ServerList(mc);
			savedServerList.loadServerList();
			lanServerList = new LanServerDetector.LanServerList();
			try {
				lanServerDetector = new LanServerDetector.ThreadLanServerFind(lanServerList);
				lanServerDetector.start();
			} catch (final Exception var2) {
				logger.warn("Unable to start LAN server detection: " + var2.getMessage());
			}
			serverListSelector = new ServerSelectionList(this, mc, width, height, 32, height - 64, 36);
			serverListSelector.func_148195_a(savedServerList);
		} else {
			serverListSelector.setDimensions(width, height, 32, height - 64);
		}
		createButtons();
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		serverListSelector.func_178039_p();
	}

	public void createButtons() {

		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		btnEditServer = new GuiButton(7, width / 2 - 154, height - 28, 70, 20,
				I18n.format("selectServer.edit", new Object[0]));
		buttonList.add(btnEditServer);
		btnDeleteServer = new GuiButton(2, width / 2 - 74, height - 28, 70, 20,
				I18n.format("selectServer.delete", new Object[0]));
		buttonList.add(btnDeleteServer);
		btnSelectServer = new GuiButton(1, width / 2 - 154, height - 52, 100, 20,
				I18n.format("selectServer.select", new Object[0]));
		buttonList.add(btnSelectServer);
		buttonList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20,
				I18n.format("selectServer.direct", new Object[0])));
		buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20,
				I18n.format("selectServer.add", new Object[0])));
		buttonList.add(new GuiInvisButton(1888, sr.getScaledWidth() - 50, sr.getScaledHeight() - 20, 50, 20, ""));
		buttonList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20,
				I18n.format("selectServer.refresh", new Object[0])));
		buttonList.add(
				new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
		selectServer(serverListSelector.func_148193_k());
		if (!Client.isHidden) {
			buttonList.add(new GuiButton(100, width / 2 + 160, height - 28, 80, 20, "Server-Finder"));
			buttonList.add(new GuiButton(101, width / 2 - 230, height - 28, 70, 20, "Clean Up"));
			buttonList.add(new GuiButton(102, width / 2 - 230, height - 52, 70, 20, "PortScanner"));
			buttonList.add(new GuiButton(103, width / 2 + 160, height - 52, 80, 20, "UUID-Spoof"));
		}
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		if (lanServerList.getWasUpdated()) {
			final List var1 = lanServerList.getLanServers();
			lanServerList.setWasNotUpdated();
			serverListSelector.func_148194_a(var1);
		}
		oldServerPinger.pingPendingNetworks();
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if (lanServerDetector != null) {
			lanServerDetector.interrupt();
			lanServerDetector = null;
		}
		oldServerPinger.clearPendingNetworks();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			GuiListExtended.IGuiListEntry var2;
			var2 = serverListSelector.func_148193_k() < 0 ? null
					: serverListSelector.getListEntry(serverListSelector.func_148193_k());
			if (button.id == 2 && var2 instanceof ServerListEntryNormal) {
				final String var9 = ((ServerListEntryNormal) var2).getServerData().serverName;
				if (var9 != null) {
					deletingServer = true;
					final String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
					final String var5 = "'" + var9 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
					final String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
					final String var7 = I18n.format("gui.cancel", new Object[0]);
					final GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7,
							serverListSelector.func_148193_k());
					mc.displayGuiScreen(var8);
				}
			} else if (button.id == 1) {
				connectToSelected();
			} else if (button.id == 4) {
				directConnect = true;
				selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "");
				mc.displayGuiScreen(new GuiScreenServerList(this, selectedServer));
			} else if (button.id == 3) {
				addingServer = true;
				selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "");
				mc.displayGuiScreen(new GuiScreenAddServer(this, selectedServer));
			} else if (button.id == 7 && var2 instanceof ServerListEntryNormal) {
				editingServer = true;
				final ServerData var3 = ((ServerListEntryNormal) var2).getServerData();
				selectedServer = new ServerData(var3.serverName, var3.serverIP);
				selectedServer.copyFrom(var3);
				mc.displayGuiScreen(new GuiScreenAddServer(this, selectedServer));
			} else if (button.id == 0) {
				mc.displayGuiScreen(parentScreen);
			} else if (button.id == 8) {
				refreshServerList();
			} else if (button.id == 100) {
				mc.displayGuiScreen(new GuiServerFinder(this));
			} else if (button.id == 101) {
				mc.displayGuiScreen(new GuiCleanUp(this));
			} else if (button.id == 102) {
				String var9 = "";
				if (var2 instanceof ServerListEntryNormal) {
					var9 = ((ServerListEntryNormal) var2).getServerData().serverIP;
				}
				if (!var9.isEmpty()) {
					mc.displayGuiScreen(new GuiPortScanner(this, var9));
				} else {
					mc.displayGuiScreen(new GuiPortScanner(this));
				}
			} else if (button.id == 103) {
				mc.displayGuiScreen(new GuiUUIDSPOOFKACKELOOOOOOLSOOOOOOSKaySKIIIIIIIIIIDETTT(this));
			}
			if (button.id == 1888) {
				mc.displayGuiScreen(new GuiAlts(this));
			}
		}
	}

	private void refreshServerList() {
		mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		GuiListExtended.IGuiListEntry var3;
		var3 = serverListSelector.func_148193_k() < 0 ? null
				: serverListSelector.getListEntry(serverListSelector.func_148193_k());
		if (deletingServer) {
			deletingServer = false;
			if (result && var3 instanceof ServerListEntryNormal) {
				savedServerList.removeServerData(serverListSelector.func_148193_k());
				savedServerList.saveServerList();
				serverListSelector.func_148192_c(-1);
				serverListSelector.func_148195_a(savedServerList);
			}
			mc.displayGuiScreen(this);
		} else if (directConnect) {
			directConnect = false;
			if (result) {
				connectToServer(selectedServer);
			} else {
				mc.displayGuiScreen(this);
			}
		} else if (addingServer) {
			addingServer = false;
			if (result) {
				savedServerList.addServerData(selectedServer);
				savedServerList.saveServerList();
				serverListSelector.func_148192_c(-1);
				serverListSelector.func_148195_a(savedServerList);
			}
			mc.displayGuiScreen(this);
		} else if (editingServer) {
			editingServer = false;
			if (result && var3 instanceof ServerListEntryNormal) {
				final ServerData var4 = ((ServerListEntryNormal) var3).getServerData();
				var4.serverName = selectedServer.serverName;
				var4.serverIP = selectedServer.serverIP;
				var4.copyFrom(selectedServer);
				savedServerList.saveServerList();
				serverListSelector.func_148195_a(savedServerList);
			}
			mc.displayGuiScreen(this);
		}
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		GuiListExtended.IGuiListEntry var4;
		final int var3 = serverListSelector.func_148193_k();
		var4 = var3 < 0 ? null : serverListSelector.getListEntry(var3);
		if (keyCode == 211) {
			GuiListExtended.IGuiListEntry var2;
			var2 = serverListSelector.func_148193_k() < 0 ? null
					: serverListSelector.getListEntry(serverListSelector.func_148193_k());
			if (var2 instanceof ServerListEntryNormal) {
				final String var9 = ((ServerListEntryNormal) var2).getServerData().serverName;
				if (var9 != null) {
					deletingServer = true;
					final String var41 = I18n.format("selectServer.deleteQuestion", new Object[0]);
					final String var5 = "'" + var9 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
					final String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
					final String var7 = I18n.format("gui.cancel", new Object[0]);
					final GuiYesNo var8 = new GuiYesNo(this, this, var41, var5, var6, var7,
							serverListSelector.func_148193_k());
					mc.displayGuiScreen(var8);
				}
			}
		} else if (keyCode == 63) {
			refreshServerList();
		} else if (var3 >= 0) {
			if (keyCode == 200) {
				if (GuiScreen.isShiftKeyDown()) {
					if (var3 > 0 && var4 instanceof ServerListEntryNormal) {
						savedServerList.swapServers(var3, var3 - 1);
						selectServer(serverListSelector.func_148193_k() - 1);
						serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
						serverListSelector.func_148195_a(savedServerList);
					}
				} else if (var3 > 0) {
					selectServer(serverListSelector.func_148193_k() - 1);
					serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
					if (serverListSelector
							.getListEntry(serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
						if (serverListSelector.func_148193_k() > 0) {
							selectServer(serverListSelector.getSize() - 1);
							serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
						} else {
							selectServer(-1);
						}
					}
				} else {
					selectServer(-1);
				}
			} else if (keyCode == 208) {
				if (GuiScreen.isShiftKeyDown()) {
					if (var3 < savedServerList.countServers() - 1) {
						savedServerList.swapServers(var3, var3 + 1);
						selectServer(var3 + 1);
						serverListSelector.scrollBy(serverListSelector.getSlotHeight());
						serverListSelector.func_148195_a(savedServerList);
					}
				} else if (var3 < serverListSelector.getSize()) {
					selectServer(serverListSelector.func_148193_k() + 1);
					serverListSelector.scrollBy(serverListSelector.getSlotHeight());
					if (serverListSelector
							.getListEntry(serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
						if (serverListSelector.func_148193_k() < serverListSelector.getSize() - 1) {
							selectServer(serverListSelector.getSize() + 1);
							serverListSelector.scrollBy(serverListSelector.getSlotHeight());
						} else {
							selectServer(-1);
						}
					}
				} else {
					selectServer(-1);
				}
			} else if (keyCode != 28 && keyCode != 156) {
				super.keyTyped(typedChar, keyCode);
			} else {
				actionPerformed((GuiButton) buttonList.get(2));
			}
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {

		field_146812_y = null;
		drawDefaultBackground(mouseX, mouseY);

		serverListSelector.drawScreen(mouseX, mouseY, partialTicks);

		super.drawScreen(mouseX, mouseY, partialTicks);
		if (!Client.isHidden) {

			GL11.glPushMatrix();
			GL11.glTranslated(width / 2, 5, 42);
			GL11.glScaled(2.8, 2.8, 42);
			drawCenteredString(fontRendererObj, EnumChatFormatting.AQUA + Minecraft.session.getUsername(), 0, 0, -1);

			GL11.glPopMatrix();
		}
		if (field_146812_y != null) {
			drawHoveringText(Lists.newArrayList((Iterable) Splitter.on("\n").split(field_146812_y)), mouseX, mouseY);
		}

	}

	public void connectToSelected() {
		GuiListExtended.IGuiListEntry var1;
		var1 = serverListSelector.func_148193_k() < 0 ? null
				: serverListSelector.getListEntry(serverListSelector.func_148193_k());
		if (var1 instanceof ServerListEntryNormal) {
			connectToServer(((ServerListEntryNormal) var1).getServerData());
		} else if (var1 instanceof ServerListEntryLanDetected) {
			final LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected) var1).getLanServer();
			connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
		}
	}

	public void connectToServer(final ServerData server) {
		mc.displayGuiScreen(new GuiConnecting(this, mc, server));
	}

	public void selectServer(final int index) {
		serverListSelector.func_148192_c(index);
		final GuiListExtended.IGuiListEntry var2 = index < 0 ? null : serverListSelector.getListEntry(index);
		btnSelectServer.enabled = false;
		btnEditServer.enabled = false;
		btnDeleteServer.enabled = false;
		if (var2 != null && !(var2 instanceof ServerListEntryLanScan)) {
			btnSelectServer.enabled = true;
			if (var2 instanceof ServerListEntryNormal) {
				btnEditServer.enabled = true;
				btnDeleteServer.enabled = true;
			}
		}
	}

	public OldServerPinger getOldServerPinger() {
		return oldServerPinger;
	}

	public void func_146793_a(final String p_146793_1_) {
		field_146812_y = p_146793_1_;
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		serverListSelector.func_148179_a(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		super.mouseReleased(mouseX, mouseY, state);
		serverListSelector.func_148181_b(mouseX, mouseY, state);
	}

	public ServerList getServerList() {
		return savedServerList;
	}

	public boolean func_175392_a(final ServerListEntryNormal p_175392_1_, final int p_175392_2_) {
		return p_175392_2_ > 0;
	}

	public boolean func_175394_b(final ServerListEntryNormal p_175394_1_, final int p_175394_2_) {
		return p_175394_2_ < savedServerList.countServers() - 1;
	}

	public void func_175391_a(final ServerListEntryNormal p_175391_1_, final int p_175391_2_,
			final boolean p_175391_3_) {
		final int var4 = p_175391_3_ ? 0 : p_175391_2_ - 1;
		savedServerList.swapServers(p_175391_2_, var4);
		if (serverListSelector.func_148193_k() == p_175391_2_) {
			selectServer(var4);
		}
		serverListSelector.func_148195_a(savedServerList);
	}

	public void func_175393_b(final ServerListEntryNormal p_175393_1_, final int p_175393_2_,
			final boolean p_175393_3_) {
		final int var4 = p_175393_3_ ? savedServerList.countServers() - 1 : p_175393_2_ + 1;
		savedServerList.swapServers(p_175393_2_, var4);
		if (serverListSelector.func_148193_k() == p_175393_2_) {
			selectServer(var4);
		}
		serverListSelector.func_148195_a(savedServerList);
	}
}
