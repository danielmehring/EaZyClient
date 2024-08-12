package me.EaZy.client.modules;

import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class Spammer extends Module {

	public static Spammer mod;

	public static final int EaZy = 161;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static int delaySetting = 100;
	private int delay;
	public static boolean aacbypass = false;
	public static String msg = "EaZy Client | YT/Twitter: EaZyClient | Skype (Shop): live:EaZyClient";
	ArrayList<String> names = new ArrayList<>();

	public Spammer() {
		super("Spammer", 0, "spam", Category.OTHER, "Spam server's chat.");
		ArrayList<String> modes = new ArrayList<>();
		modes.add("Normal");
		modes.add("MSG");
		modes.add("Friend add");
		modes.add("tpa");
		modes.add("%all%");
		Client.setmgr.rSetting(new Setting("Mode", this, "Normal", modes));
		mod = this;
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "Massennachichter";
		} else {
			return super.getRenderName();
		}
	}

	@Override
	public void onUpdate() {

		if (!isToggled()) {
			if (togglecmd) {
				setToggled(true);
				onEnable();
			}
			return;
		}
		if (isToggled() && !togglecmd) {
			setToggled(false);
			onDisable();
			return;
		}

		if (msg.startsWith("*")) {
			msg("§cDon't use Spammer to spam EaZy-Chat!");
			return;
		}

		delay++;

		if (delay > delaySetting) {

			if (Client.setmgr.getSettingByName(this, "Mode").getValString().equals("Normal")) {
				final Random rn = new Random();
				String str = "";
				for (int i = 0; i <= 10; i++) {
					final int random = rn.nextInt(25) + 97;
					str = str + (char) random;
				}
				Minecraft.thePlayer.sendChatMessage(msg + (aacbypass ? " " + str : ""));
			} else if (Client.setmgr.getSettingByName(this, "Mode").getValString().equals("MSG")) {
				if (names.isEmpty()) {
					for (String s : PlayerUtil.getTabListReal()) {
						names.add(s);
					}
				}
				String name = names.get((new Random()).nextInt(names.size()));

				final Random rn = new Random();
				String str = "";
				for (int i = 0; i <= 10; i++) {
					final int random = rn.nextInt(25) + 97;
					str = str + (char) random;
				}

				Minecraft.thePlayer.sendChatMessage("/msg " + name + " " + msg + (aacbypass ? " " + str : ""));

				names.remove(name);
			} else if (Client.setmgr.getSettingByName(this, "Mode").getValString().equals("Friend add")) {
				if (names.isEmpty()) {
					for (String s : PlayerUtil.getTabListReal()) {
						names.add(s);
					}
				}
				String name = names.get((new Random()).nextInt(names.size()));

				Minecraft.thePlayer.sendChatMessage("/friend add " + name);

				names.remove(name);
			} else if (Client.setmgr.getSettingByName(this, "Mode").getValString().equals("tpa")) {
				if (names.isEmpty()) {
					for (String s : PlayerUtil.getTabListReal()) {
						names.add(s);
					}
				}
				String name = names.get((new Random()).nextInt(names.size()));

				Minecraft.thePlayer.sendChatMessage("/tpa " + name);

				names.remove(name);
			} else {
				if (names.isEmpty()) {
					for (String s : PlayerUtil.getTabListReal()) {
						names.add(s);
					}
				}
				String name = names.get((new Random()).nextInt(names.size()));

				final Random rn = new Random();
				String str = "";
				for (int i = 0; i <= 10; i++) {
					final int random = rn.nextInt(25) + 97;
					str = str + (char) random;
				}

				Minecraft.thePlayer.sendChatMessage(msg.replace("%all%", name) + (aacbypass ? " " + str : ""));

				names.remove(name);
			}

			delay = 0;
		}

		super.onUpdate();
	}

	@Override
	public void onEnable() {
		names.clear();
		super.onEnable();
	}

}
