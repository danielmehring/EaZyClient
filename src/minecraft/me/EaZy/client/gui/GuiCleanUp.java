package me.EaZy.client.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;

public class GuiCleanUp extends GuiScreen {

	public static final int EaZy = 65;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private boolean Unknown = true;
	private boolean Outdated = true;
	private boolean Failed = true;
	private boolean MOTD = false;
	private boolean OnlyBungee = false;
	private boolean Vanilla18 = false;
	private boolean online = false;

	private final GuiMultiplayer prevMenu;
	private boolean removeAll;
	private final String[] toolTips = new String[] { "",
			"§2Start the Clean Up with\n§2the settings above!\n§eIt might seem, your game\n§edoesn\'t react for a couple of seconds!",
			"§eServers, that clearly don\'t exist.", "§eServers, that are not running\n§ethe same MC Version as you!",
			"§eAll Servers that failed last ping.\n§cWait untill all Servers are pinged!",
			"§cThis will clear all Servers from §e§nServerFinder!\n§4Use with caution!",
			"§eAll servers which have\n§ea not colored MOTD.", "§eOnly keeps Bungee-Servers.\n§4Use with caution!",
			"§eOnly keeps §aVanilla 1.8 Servers.\n§4Use with caution!",
			"§eOnly keep Servers where\n§eplayers are online." };

	public GuiCleanUp(final GuiMultiplayer prevMultiplayerMenu) {
		prevMenu = prevMultiplayerMenu;
	}

	@Override
	public void updateScreen() {}

	@Override
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 145 + 22 + 22, "Cancel"));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 22 + 22, "Clean Up"));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 - 22 + 12 - 10,
				"Unknown Hosts: " + removeOrKeep(Unknown)));
		buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 0 + 12 - 10,
				"Outdated Servers: " + removeOrKeep(Outdated)));
		buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 34 - 10, "Failed Ping: " + removeOrKeep(Failed)));
		buttonList.add(new GuiButton(6, width / 2 - 100, height / 4 + 56 - 10, "MOTD-Colors: " + removeOrKeep(MOTD)));
		buttonList.add(
				new GuiButton(7, width / 2 - 100, height / 4 + 78 - 10, "§6Keep Only Bungee: " + yesOrNo(OnlyBungee)));
		buttonList.add(new GuiButton(8, width / 2 - 100, height / 4 + 100 - 10,
				"§6Keep Only Vanilla 1.8: " + yesOrNo(Vanilla18)));
		buttonList
				.add(new GuiButton(9, width / 2 - 100, height / 4 + 122 - 10, "§6Players online: " + yesOrNo(online)));
		buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 144 - 10,
				"§cRemove all Servers: " + yesOrNo(removeAll)));
	}

	private String yesOrNo(final boolean bool) {
		return bool ? "Yes" : "No";
	}

	private String removeOrKeep(final boolean bool) {
		return bool ? "Remove" : "Keep";
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(final GuiButton clickedButton) {
		if (clickedButton.enabled) {
			switch (clickedButton.id) {
			case 0:
				mc.displayGuiScreen(prevMenu);
				break;
			case 1:
				ServerData server;
				if (removeAll) {

					int i = prevMenu.savedServerList.countServers() - 1;
					while (i >= 0) {
						server = prevMenu.savedServerList.getServerData(i);
						if (server.serverName.startsWith("Grief me")) {
							prevMenu.savedServerList.removeServerData(i);
							prevMenu.savedServerList.saveServerList();
							prevMenu.serverListSelector.func_148192_c(-1);
							prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
						}
						--i;
					}

					prevMenu.savedServerList.saveServerList();
					prevMenu.serverListSelector.func_148192_c(-1);
					prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
					mc.displayGuiScreen(prevMenu);
					return;
				} else {
					int i = prevMenu.savedServerList.countServers() - 1;
					while (i >= 0) {
						server = prevMenu.savedServerList.getServerData(i);
						if ((Unknown && server.serverMOTD != null
								&& server.serverMOTD.equals(EnumChatFormatting.DARK_RED + "Can't resolve hostname")
								|| Outdated && server.version != 47
								|| Failed && server.pingToServer != -2 && server.pingToServer < 0
								|| MOTD && server.serverMOTD.startsWith("§")
								|| online && getOnlinePlayers(server) <= 0
								|| ((Vanilla18 && OnlyBungee)
										? (!server.gameVersion.contains("Bungee") && !server.gameVersion.equals("1.8"))
										: (OnlyBungee && !server.gameVersion.contains("Bungee")
												|| Vanilla18 && !server.gameVersion.equals("1.8"))))
								&& server.serverName.startsWith("Grief me")) {
							prevMenu.savedServerList.removeServerData(i);
							prevMenu.savedServerList.saveServerList();
							prevMenu.serverListSelector.func_148192_c(-1);
							prevMenu.serverListSelector.func_148195_a(prevMenu.savedServerList);
						}
						--i;
					}
					mc.displayGuiScreen(prevMenu);
				}
				break;
			case 2:
				Unknown = !Unknown;
				clickedButton.displayString = "Unknown Hosts: " + removeOrKeep(Unknown);
				break;
			case 3:
				Outdated = !Outdated;
				clickedButton.displayString = "Outdated Servers: " + removeOrKeep(Outdated);
				break;
			case 4:
				Failed = !Failed;
				clickedButton.displayString = "Failed Ping: " + removeOrKeep(Failed);
				break;
			case 5:
				removeAll = !removeAll;
				clickedButton.displayString = "§cRemove ALL §cServers: " + yesOrNo(removeAll);
				break;
			case 6:
				MOTD = !MOTD;
				clickedButton.displayString = "MOTD-Colors: " + removeOrKeep(MOTD);
				break;
			case 7:
				OnlyBungee = !OnlyBungee;
				clickedButton.displayString = "§6Keep Only Bungee: " + yesOrNo(OnlyBungee);
				break;
			case 8:
				Vanilla18 = !Vanilla18;
				clickedButton.displayString = "§6Keep Only Vanilla 1.8: " + yesOrNo(Vanilla18);
				break;
			case 9:
				online = !online;
				clickedButton.displayString = "§6Players online: " + yesOrNo(online);
				break;
			default:
				break;
			}
		}
	}
	
	public int getOnlinePlayers(ServerData server) {
		try {
			return Integer.parseInt(EnumChatFormatting.getTextWithoutFormattingCodes(server.populationInfo).split("/")[0]);
		} catch (Exception e) {
			return 1;
		}
	}

	@Override
	protected void keyTyped(final char par1, final int par2) {
		if (par2 == 28 || par2 == 156) {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	@Override
	protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	public void drawScreen(final int par1, final int par2, final float par3) {
		drawDefaultBackground(par1, par2);
		drawCenteredString(fontRendererObj, "Clean Up", width / 2, 20, 16777215);
		drawCenteredString(fontRendererObj, "§2Select the Servers you want to remove:", width / 2, 30, 10526880);
		drawCenteredString(fontRendererObj, "§6Only removes Servers from ServerFinder!", width / 2, 40, 10526880);
		super.drawScreen(par1, par2, par3);
		int i = 0;
		while (i < buttonList.size()) {
			final GuiButton button = (GuiButton) buttonList.get(i);
			if (button.isMouseOver() && !toolTips[button.id].isEmpty()) {
				final ArrayList toolTip = Lists.newArrayList((Object[]) toolTips[button.id].split("\n"));
				drawHoveringText(toolTip, par1, par2);
				break;
			}
			++i;
		}
	}
}
