package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;

public class Plugins extends Module {

	public static Plugins mod;

	public static final int EaZy = 154;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public Plugins() {
		super("Plugins", 0, "pl", Category.HIDDEN);
		mod = this;
	}

	public EventTarget onReceivePacket(final EventReceivePacket event) {
		if (event.getPacket() instanceof S3APacketTabComplete) {
			final S3APacketTabComplete packet = (S3APacketTabComplete) event.getPacket();
			final String[] commands = packet.func_149630_c();
			String message = "";
			int size = 0;
			final String[] arrstring = commands;
			final int n = arrstring.length;
			int n2 = 0;
			while (n2 < n) {
				final String command = arrstring[n2];
				final String pluginName = command.split(":")[0].substring(1);
				if (!message.contains(pluginName) && command.contains(":") && !pluginName.equalsIgnoreCase("minecraft")
						&& !pluginName.equalsIgnoreCase("bukkit")) {
					++size;
					message = message.isEmpty() ? String.valueOf(String.valueOf(message))
							+ (pluginName.contains("cheat") || pluginName.equals("aac") || pluginName.equals("pac")
									|| pluginName.equals("minesecure") || pluginName.equals("spartan") ? "§c" : "§a")
							+ pluginName
							: String.valueOf(String.valueOf(message)) + "§8, "
									+ (pluginName.contains("cheat") || pluginName.equals("aac")
											|| pluginName.equals("pac") || pluginName.equals("minesecure")
											|| pluginName.equals("spartan") ? "§c" : "§a")
									+ pluginName;
				}
				++n2;
			}
			if (!message.isEmpty()) {
				msg("§aPlugins (" + size + (message.contains("§c") ? ", §4§lANTICHEAT!§a)" : ")") + ": " + message
						+ "§8.");
			} else {
				msg("Plugins: none/not detectable.");
			}
			event.setCancelled(true);
			EventManager.unregister(this);
			toggle();
		}
		return null;
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		Minecraft.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete("/"));
		super.onEnable();
	}
}
