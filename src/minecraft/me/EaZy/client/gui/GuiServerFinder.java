/*
 * Decompiled with CFR 0_117. Could not load the following classes:
 * org.lwjgl.input.Keyboard
 */
package me.EaZy.client.gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.EaZy.client.utils.ServerPinger;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;

public class GuiServerFinder extends GuiScreen {

	public static final int EaZy = 75;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final String[] stateStrings = new String[] { "", "§2Searching...", "§2Retrying...",
			"§4Unknown Host!", "§4Stopped!", "§2Done!", "§4Something bad happened! :/" };
	private final GuiMultiplayer prevMenu;
	private GuiTextField ipBox;
	private int checked;
	private int working;
	private ServerFinderState state;

	public GuiServerFinder(final GuiMultiplayer prevMultiplayerMenu) {
		prevMenu = prevMultiplayerMenu;
	}

	@Override
	public void updateScreen() {
		ipBox.updateCursorCounter();
		((GuiButton) buttonList.get(0)).displayString = state.isRunning() ? "Stop" : "Search";
		ipBox.setEnabled(!state.isRunning());
		((GuiButton) buttonList.get(0)).enabled = !ipBox.getText().isEmpty();
	}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Search"));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 120 + 12, "Back"));
		ipBox = new GuiTextField(0, fontRendererObj, width / 2 - 100, height / 4 + 34, 200, 20);
		ipBox.setMaxStringLength(200);
		ipBox.setFocused(true);
		state = ServerFinderState.NOT_RUNNING;
	}

	@Override
	public void onGuiClosed() {
		state = ServerFinderState.CANCELLED;
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.enabled) {
			if (clickedButton.id == 0) {
				if (state.isRunning()) {
					state = ServerFinderState.CANCELLED;
				} else {
					state = ServerFinderState.RESOLVING;
					checked = 0;
					working = 0;
					new Thread("Server Finder") {

						@Override
						public void run() {
							try {
								final InetAddress addr = InetAddress.getByName(ipBox.getText().split(":")[0].trim());
								final int[] ipParts = new int[4];
								int i = 0;
								while (i < 4) {
									ipParts[i] = addr.getAddress()[i] & 255;
									++i;
								}
								GuiServerFinder.this.state = ServerFinderState.SEARCHING;
								final ArrayList<ServerPinger> pingers = new ArrayList<>();
								final int[] arrn = new int[7];
								arrn[1] = 1;
								arrn[2] = -1;
								arrn[3] = 2;
								arrn[4] = -2;
								arrn[5] = 3;
								arrn[6] = -3;
								final int[] arrayOfInt1 = arrn;
								final int j = arrayOfInt1.length;
								int i2 = 0;
								while (i2 < j) {
									final int change = arrayOfInt1[i2];
									int i22 = 0;
									while (i22 <= 255) {
										if (state == ServerFinderState.CANCELLED) {
											return;
										}
										final int[] ipParts2 = ipParts.clone();
										ipParts2[2] = ipParts[2] + change & 255;
										ipParts2[3] = i22;
										final String ip = String.valueOf(ipParts2[0]) + "." + ipParts2[1] + "."
												+ ipParts2[2] + "." + ipParts2[3];
										final ServerPinger pinger = new ServerPinger();
										pinger.ping(ip);
										pingers.add(pinger);
										while (pingers.size() >= 128) {
											if (state == ServerFinderState.CANCELLED) {
												return;
											}
											GuiServerFinder.this.updatePingers(pingers);
										}
										++i22;
									}
									++i2;
								}
								while (pingers.size() > 0) {
									if (state == ServerFinderState.CANCELLED) {
										return;
									}
									GuiServerFinder.this.updatePingers(pingers);
								}
								GuiServerFinder.this.state = ServerFinderState.DONE;
							} catch (final UnknownHostException e) {
								GuiServerFinder.this.state = ServerFinderState.UNKNOWN_HOST;
							} catch (final Exception e) {
								e.printStackTrace();
								GuiServerFinder.this.state = ServerFinderState.ERROR;
							}
						}
					}.start();
				}
			} else if (clickedButton.id == 2) {
				mc.displayGuiScreen(prevMenu);
			}
		}
	}

	private boolean serverInList(final String ip) {
		int i = 0;
		while (i < prevMenu.savedServerList.countServers()) {
			if (prevMenu.savedServerList.getServerData(i).serverIP.equals(ip)) {
				return true;
			}
			++i;
		}
		return false;
	}

	private void updatePingers(final ArrayList<ServerPinger> pingers) {
		int i = 0;
		while (i < pingers.size()) {
			if (!pingers.get(i).isStillPinging()) {
				++checked;
				if (pingers.get(i).isWorking()) {
					++working;
					if (!serverInList(pingers.get(i).server.serverIP)) {
						prevMenu.savedServerList
								.addServerData(new ServerData("Grief me #" + working, pingers.get(i).server.serverIP));
						prevMenu.savedServerList.saveServerList();
						prevMenu.serverListSelector.func_148192_c(-1);
						prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
					}
				}
				pingers.remove(i);
			}
			++i;
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		ipBox.textboxKeyTyped(par1, par2);
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
		ipBox.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		drawDefaultBackground(par1, par2);
		drawCenteredString(fontRendererObj, "Server Finder", width / 2, 20, 16777215);
		drawCenteredString(fontRendererObj, "This will search for servers, that have", width / 2, 40, 10526880);
		drawCenteredString(fontRendererObj, "a similar IP to the given.", width / 2, 50, 10526880);
		drawCenteredString(fontRendererObj, "All servers will be added to your server-list!", width / 2, 60, 10526880);
		Gui.drawString(fontRendererObj, "Server-IP:", width / 2 - 100, height / 4 + 24, 10526880);
		ipBox.drawTextBox();
		drawCenteredString(fontRendererObj, state.toString(), width / 2, height / 4 + 73, 10526880);
		Gui.drawString(fontRendererObj, "Checked: " + checked + " / 1792", width / 2 - 100, height / 4 + 84, 10526880);
		Gui.drawString(fontRendererObj, "Working: " + working, width / 2 - 100, height / 4 + 94, 10526880);
		super.drawScreen(par1, par2, par3);
	}

	private static enum ServerFinderState {
		NOT_RUNNING, SEARCHING, RESOLVING, UNKNOWN_HOST, CANCELLED, DONE, ERROR;

		public boolean isRunning() {
			return !(this != SEARCHING && this != RESOLVING);
		}

		@Override
		public String toString() {
			return stateStrings[ordinal()];
		}
	}

}
