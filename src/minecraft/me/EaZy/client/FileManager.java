package me.EaZy.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.settings.Setting;
import me.EaZy.client.alts.Alt;
import me.EaZy.client.gui.alts.GuiAltList;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.Spammer;
import me.EaZy.client.modules.Speed;
import me.EaZy.client.modules.YesCheat;
import me.EaZy.client.utils.FileUtils;
import me.EaZy.client.utils.Friend;
import me.EaZy.client.utils.Friends;
import me.EaZy.client.utils.JsonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;

public class FileManager {

	public static final int EaZy = 85;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static final File eazyDir = new File(Minecraft.getMinecraft().mcDataDir, "EaZy");
	public static final File configsDir = new File(eazyDir, "Configs");
	private static final File friends = new File(eazyDir, "friends.json");
	private static final File altsManager = new File(eazyDir, "altManager.json");
	private static final File spammer = new File(eazyDir, "spammer.json");
	private static final File keyBinds = new File(eazyDir, "keyBinds.json");
	private static final File main = new File(eazyDir, "main.json");
	private static final File modules = new File(eazyDir, "modules.json");
	private static final File configs = new File(eazyDir, "configs.txt");
	public static final File clickgui = new File(eazyDir, "clickGui.json");
	private static final File loginsave = new File(eazyDir, "loginSave.json");
	private static final File soundFile = new File(FileManager.eazyDir.getAbsolutePath(), "MitteBekommtTritteXD.wav");
	private static final File gta5soundFile = new File(FileManager.eazyDir.getAbsolutePath(), "GTA5Wasted.wav");

	public static void saveAll() {
		FileManager.saveFriends();
		FileManager.saveAltsManager();
		FileManager.saveSpammer();
		FileManager.saveKeybinds();
		FileManager.saveMain();
		FileManager.saveConfigs();
		FileManager.saveClickGui();
	}

	public static void init() {
		if (!eazyDir.exists()) {
			eazyDir.mkdir();
		}
		if (!configsDir.exists()) {
			configsDir.mkdir();
		}
		if (!friends.exists()) {
			FileManager.saveFriends();
		} else {
			FileManager.loadFriends();
		}
		if (!altsManager.exists()) {
			FileManager.saveAltsManager();
		} else {
			FileManager.loadAltsManager();
		}
		if (!spammer.exists()) {
			FileManager.saveSpammer();
		} else {
			FileManager.loadSpammer();
		}
		if (!keyBinds.exists()) {
			FileManager.saveKeybinds();
		} else {
			FileManager.loadKeybinds();
		}
		if (!main.exists()) {
			FileManager.saveMain();
		} else {
			FileManager.loadMain();
		}
		if (!modules.exists()) {
			FileManager.saveMods();
		} else {
			FileManager.loadMods();
		}
		if (!configs.exists()) {
			FileManager.saveConfigs();
		} else {
			FileManager.loadConfigs();
		}
		if (!loginsave.exists()) {
			FileManager.saveLogin();
		} else {
			FileManager.loadLogin();
		}
		if (!soundFile.exists()) {
			try {
				System.out.println("Downloading MitteBekommtTritteXD.wav file...");
				final URL url = new URL("http://www.ni368223_2.vweb18.nitrado.net/MitteBekommtTritteXD.wav");
				org.apache.commons.io.FileUtils.copyURLToFile(url, soundFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		if (!gta5soundFile.exists()) {
			try {
				System.out.println("Downloading GTA5Wasted.wav file...");
				final URL url = new URL("http://www.ni368223_2.vweb18.nitrado.net/GTA5Wasted.wav");
				org.apache.commons.io.FileUtils.copyURLToFile(url, gta5soundFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveClickGui() {
		try {
			final JsonObject json = new JsonObject();
			for (final Panel p : ClickGUI.panels) {
				final JsonObject jsonMod = new JsonObject();
				jsonMod.addProperty(p.title, Double.toString(p.x) + "_" + Double.toString(p.y) + "_"
						+ Boolean.toString(p.extended) + "_" + Boolean.toString(p.pinned));
				json.add(p.title, jsonMod);
			}
			final PrintWriter save = new PrintWriter(new FileWriter(FileManager.clickgui));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadClickGui() {
		try {
			// Mal in die datei schauen nach dem format
			final BufferedReader load = new BufferedReader(new FileReader(FileManager.clickgui));
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
			load.close();
			for (final Map.Entry entry : json.entrySet()) {

				final String forVals = entry.getValue().toString()
						.replace("{\"" + entry.getKey().toString() + "\":\"", "").replace("\"}", "");

				final String[] vals = forVals.split("_");

				final double x = Double.parseDouble(vals[0]);
				final double y = Double.parseDouble(vals[1]);
				final boolean extended = Boolean.parseBoolean(vals[2]);
				final Panel p = ClickGUI.getPanelByTitle(entry.getKey().toString());
				if (p == null) {
					continue;
				}

				p.x = x;
				p.y = y;
				p.extended = extended;
				if (vals.length == 4) {
					if (p.pinnable)
						p.pinned = Boolean.parseBoolean(vals[3]);
				}

			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveConfigs(final String... presets) {
		final ArrayList<String> toPrint = new ArrayList<>();
		final String[] arrstring = presets;
		final int n = arrstring.length;
		int n2 = 0;
		while (n2 < n) {
			final String line = arrstring[n2];
			toPrint.add(line);
			++n2;
		}
		for (final Setting value : Client.setmgr.getSettings()) {
			if (value.isSlider()) {
				toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
						+ (value.onlyInt() ? (double) (int) value.getValFloat() : value.getValFloat()));
				continue;
			}
			if (value.isCheck()) {
				toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
						+ value.getValBoolean());
				continue;
			}
			if (value.isCombo()) {
				toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
						+ value.getValString());
				continue;
			}
			toPrint.add(value.getName());
		}
		FileUtils.print(toPrint, FileManager.configs, true);
	}

	public static void loadConfigs() {
		final ArrayList<String> lines = FileUtils.read(FileManager.configs, true);
		for (final String s : lines) {
			final String[] args = s.split(":");
			for (final Setting value : Client.setmgr.getSettings()) {
				try {
					if (!args[0].equals(value.getParentMod().getName() + "_" + value.getName())) {
						continue;
					}
					if (value.isSlider()) {
						Client.setmgr
								.getSettingByName(Client.getModule(args[0].split("_")[0]), args[0].split("_")[1].trim())
								.setValFloat(Float.parseFloat(args[1]));
					}
					if (value.isCheck()) {
						Client.setmgr
								.getSettingByName(Client.getModule(args[0].split("_")[0]), args[0].split("_")[1].trim())
								.setValBoolean(Boolean.parseBoolean(args[1]));
					}
					if (value.isCombo()) {
						Client.setmgr
								.getSettingByName(Client.getModule(args[0].split("_")[0]), args[0].split("_")[1].trim())
								.setValString(args[1]);

					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveMods() {
		try {
			final JsonObject json = new JsonObject();
			for (final Module mod : Client.getModules()) {
				final JsonObject jsonMod = new JsonObject();
				jsonMod.addProperty("enabled", mod.isToggled());
				json.add(mod.getName(), jsonMod);
			}
			final PrintWriter save = new PrintWriter(new FileWriter(FileManager.modules));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadMods() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(FileManager.modules));
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
			load.close();
			for (final Map.Entry entry : json.entrySet()) {
				final Module mod = Client.getModule((String) entry.getKey());
				if (mod == null || !((JsonObject) entry.getValue()).get("enabled").getAsBoolean()
						|| mod.isCategory(Category.HIDDEN)) {
					continue;
				}
				mod.enableOnStartup();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveLogin() {
		try {
			final JsonObject json = new JsonObject();

			json.addProperty("name", Configs.loginName);
			json.addProperty("password", Configs.loginPassword);

			final PrintWriter save = new PrintWriter(new FileWriter(FileManager.loginsave));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadLogin() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(FileManager.loginsave));
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
			load.close();
			for (final Map.Entry entry : json.entrySet()) {

				if (entry.getKey().equals("name")) {
					Configs.loginName = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}
				if (entry.getKey().equals("password")) {
					Configs.loginPassword = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}

			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveMain() {
		try {
			final JsonObject json = new JsonObject();

			json.addProperty("commandPrefix", Configs.commandPrefix);
			json.addProperty("buttonMode", Configs.buttonMode);
			json.addProperty("blurredBackground", Configs.blurredBackground);
			json.addProperty("buttonModeName", Configs.buttonModeName);
			json.addProperty("toggleSounds", Configs.toggleSounds);
			json.addProperty("advancedHotbar", Configs.advancedHotbar);
			json.addProperty("cookies", Configs.cookies);
			json.addProperty("tritte", Configs.tritte);
			json.addProperty("noBob", Configs.noBob);
			json.addProperty("smoothZoom", Configs.smoothZoom);
			json.addProperty("saveEaZyLogin", Configs.saveEaZyLogin);
			json.addProperty("itemPhysics", Configs.itemPhysics);
			json.addProperty("betterChat", Configs.betterChat);
			json.addProperty("suffix", Configs.suffix);
			json.addProperty("otherSwing", Configs.otherSwing);
			json.addProperty("customFont", Configs.customFont);
			json.addProperty("useClientColor", Configs.useClientColor);
			json.addProperty("f5PositionXD", Configs.f5PositionXD);
			json.addProperty("bgPath", Configs.bgPath);
			json.addProperty("bgMoving", Configs.bgMoving);
			json.addProperty("invParticles", Configs.invParticles);
			json.addProperty("antiLag", Configs.antiLag);
			json.addProperty("gta5Death", Configs.gta5Death);
			json.addProperty("gta5DeathSound", Configs.gta5DeathSound);
			json.addProperty("gta5Text", Configs.gta5Text);

			final PrintWriter save = new PrintWriter(new FileWriter(FileManager.main));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadMain() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(FileManager.main));
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
			load.close();
			for (final Map.Entry entry : json.entrySet()) {

				if (entry.getKey().equals("commandPrefix")) {
					Configs.commandPrefix = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}
				if (entry.getKey().equals("buttonMode")) {
					Configs.buttonMode = Integer.parseInt(entry.getValue().toString());
				}
				if (entry.getKey().equals("blurredBackground")) {
					Configs.blurredBackground = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("buttonModeName")) {
					Configs.buttonModeName = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}
				if (entry.getKey().equals("toggleSounds")) {
					Configs.toggleSounds = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("advancedHotbar")) {
					Configs.advancedHotbar = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("noBob")) {
					Configs.noBob = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("smoothZoom")) {
					Configs.smoothZoom = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("saveEaZyLogin")) {
					Configs.saveEaZyLogin = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("itemPhysics")) {
					Configs.itemPhysics = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("betterChat")) {
					Configs.betterChat = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("cookies")) {
					Configs.cookies = Long.parseLong(entry.getValue().toString());
				}
				if (entry.getKey().equals("tritte")) {
					Configs.tritte = Long.parseLong(entry.getValue().toString());
				}
				if (entry.getKey().equals("suffix")) {
					Configs.suffix = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("otherSwing")) {
					Configs.otherSwing = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("customFont")) {
					Configs.customFont = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("useClientColor")) {
					Configs.useClientColor = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("f5PositionXD")) {
					Configs.f5PositionXD = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("bgPath")) {
					Configs.bgPath = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}
				if (entry.getKey().equals("bgMoving")) {
					Configs.bgMoving = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("invParticles")) {
					Configs.invParticles = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("antiLag")) {
					Configs.antiLag = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("gta5Death")) {
					Configs.gta5Death = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("gta5DeathSound")) {
					Configs.gta5DeathSound = Boolean.parseBoolean(entry.getValue().toString());
				}
				if (entry.getKey().equals("gta5Text")) {
					Configs.gta5Text = entry.getValue().toString().replace("\"", "").replace("\\\\", "\\");
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean saveConfig(final String name) {
		try {
			new File(configsDir + File.separator + name).getAbsoluteFile().mkdir();
			// Keybinds
			final JsonObject json = new JsonObject();
			for (final Module mod : Client.getModules()) {
				json.addProperty(mod.getName(), Integer.toString(mod.keyCode));
			}
			final PrintWriter save = new PrintWriter(
					new FileWriter(new File(configsDir + File.separator + name, "Keys.json").getAbsolutePath()));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();

			// Modules
			final JsonObject json1 = new JsonObject();
			for (final Module mod : Client.getModules()) {
				final JsonObject jsonMod = new JsonObject();
				jsonMod.addProperty("enabled", mod.isToggled());
				json1.add(mod.getName(), jsonMod);
			}
			final PrintWriter save1 = new PrintWriter(
					new FileWriter(new File(configsDir + File.separator + name, "Mods.json").getAbsolutePath()));
			save1.println(JsonUtils.prettyGson.toJson(json1));
			save1.close();

			// Configs
			final ArrayList<String> toPrint = new ArrayList<>();
			for (final Setting value : Client.setmgr.getSettings()) {
				if (value.isSlider()) {
					toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
							+ (value.onlyInt() ? (double) (int) value.getValFloat() : value.getValFloat()));
					continue;
				}
				if (value.isCheck()) {
					toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
							+ value.getValBoolean());
					continue;
				}
				if (value.isCombo()) {
					toPrint.add(value.getParentMod().getName() + "_" + String.valueOf(value.getName()) + ":"
							+ value.getValString());
					continue;
				}
				toPrint.add(value.getName());
			}
			FileUtils.print(toPrint, new File(configsDir + File.separator + name, "Configs.json").getAbsoluteFile(),
					true);

			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean loadConfig(final String name) {
		try {
			for (final Category asd : Category.values()) {
				Client.disable(asd);
			}

			// Mods
			final BufferedReader load1 = new BufferedReader(
					new FileReader(new File(configsDir + File.separator + name, "Mods.json").getAbsolutePath()));
			final JsonObject json1 = (JsonObject) JsonUtils.jsonParser.parse(load1);
			load1.close();
			for (final Map.Entry entry : json1.entrySet()) {
				final Module mod = Client.getModule((String) entry.getKey());
				if (mod == null || !((JsonObject) entry.getValue()).get("enabled").getAsBoolean()
						|| mod.isCategory(Category.HIDDEN)) {
					continue;
				}
				mod.enableOnStartup();
			}

			// Configs
			final ArrayList<String> lines = FileUtils
					.read(new File(configsDir + File.separator + name, "Configs.json").getAbsoluteFile(), true);
			for (final String s : lines) {
				final String[] args = s.split(":");
				for (final Setting value : Client.setmgr.getSettings()) {
					try {
						if (!args[0].equals(value.getParentMod().getName() + "_" + value.getName())) {
							continue;
						}
						if (args[0].split("_")[0].equals("Appearance") || args[0].split("_")[0].equals("Other")) {
							continue;
						}
						if (value.isSlider()) {
							Client.setmgr.getSettingByName(Client.getModule(args[0].split("_")[0]),
									args[0].split("_")[1].trim()).setValFloat(Float.parseFloat(args[1]));
						}
						if (value.isCheck()) {
							Client.setmgr.getSettingByName(Client.getModule(args[0].split("_")[0]),
									args[0].split("_")[1].trim()).setValBoolean(Boolean.parseBoolean(args[1]));
						}
						if (value.isCombo()) {
							Client.setmgr.getSettingByName(Client.getModule(args[0].split("_")[0]),
									args[0].split("_")[1].trim()).setValString(args[1]);

						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
				}
			}

			// Keybinds
			try {
				final BufferedReader load = new BufferedReader(
						new FileReader(new File(configsDir + File.separator + name, "Keys.json").getAbsolutePath()));
				final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
				load.close();
				for (final Map.Entry entry : json.entrySet()) {
					if (Client.getModule((String) entry.getKey()) != null) {
						Client.getModule((String) entry.getKey()).keyCode = Integer
								.parseInt(entry.getValue().toString().replace("\"", ""));
					}
				}
			} catch (final Exception e) {

			}
			YesCheat.updateMode();
			Speed.updateMode();
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void saveKeybinds() {
		try {
			final JsonObject json = new JsonObject();
			for (final Module mod : Client.getModules()) {
				json.addProperty(mod.getName(), Integer.toString(mod.keyCode));
			}
			final PrintWriter save = new PrintWriter(new FileWriter(FileManager.keyBinds));
			save.println(JsonUtils.prettyGson.toJson(json));
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadKeybinds() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(FileManager.keyBinds));
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
			load.close();
			for (final Map.Entry entry : json.entrySet()) {
				if (Client.getModule((String) entry.getKey()) != null) {
					Client.getModule((String) entry.getKey()).keyCode = Integer
							.parseInt(entry.getValue().toString().replace("\"", ""));
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveSpammer() {
		try {
			final PrintWriter save = new PrintWriter(new FileWriter(spammer));
			save.println(Spammer.msg);
			save.println(Spammer.delaySetting);
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadSpammer() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(spammer));
			Spammer.msg = load.readLine();
			Spammer.delaySetting = Integer.parseInt(load.readLine());
			load.close();
		} catch (final Exception load) {}
	}

	public static void saveAltsManager() {
		try {
			final JsonObject json = new JsonObject();
			for (final Alt alt : GuiAltList.alts) {
				final JsonObject jsonAlt = new JsonObject();
				jsonAlt.addProperty("password", alt.getPassword());
				jsonAlt.addProperty("name", alt.getName());
				jsonAlt.addProperty("starred", alt.isStarred());
				json.add(alt.getEmail(), jsonAlt);
			}
			Files.write(altsManager.toPath(),
					/* Encryption.encrypt( */ JsonUtils.prettyGson.toJson(json)/* ) */.getBytes("UTF-8"),
					new OpenOption[0]);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadAltsManager() {
		try {
			final JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(
					/* Encryption.decrypt( */new String(Files.readAllBytes(altsManager.toPath()), "UTF-8")/* ) */);
			GuiAltList.alts.clear();
			for (final Map.Entry entry : json.entrySet()) {
				final JsonObject jsonAlt = ((JsonElement) entry.getValue()).getAsJsonObject();
				final String email = (String) entry.getKey();
				final String password = jsonAlt.get("password") == null ? "" : jsonAlt.get("password").getAsString();
				final String name = jsonAlt.get("name") == null ? "" : jsonAlt.get("name").getAsString();
				final boolean starred = jsonAlt.get("starred") == null ? false : jsonAlt.get("starred").getAsBoolean();
				GuiAltList.alts.add(new Alt(email, password, name, starred));
			}
			GuiAltList.sortAlts();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveFriends() {
		try {
			final PrintWriter save = new PrintWriter(new FileWriter(friends));
			for (final Friend friend : Friends.getFriends()) {
				save.println(friend.getUsername() + ":" + friend.getNick());
			}
			save.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadFriends() {
		try {
			final BufferedReader load = new BufferedReader(new FileReader(friends));
			final BufferedReader test = new BufferedReader(new FileReader(friends));
			while (test.readLine() != null) {
				final String asd = load.readLine();
				if (asd.contains(":")) {
					Friends.add(asd.split(":")[0], asd.split(":")[1]);
				} else {
					Friends.add(asd);
				}
			}
			load.close();
			test.close();
		} catch (final Exception load) {
			load.printStackTrace();
			// empty catch block
		}
	}

	@SuppressWarnings("unused")
	public static List<String> read(final File inputFile) {
		ArrayList<String> readContent;
		readContent = new ArrayList<>();
		BufferedReader reader = null;
		try {
			try {
				String currentReadLine2;
				reader = new BufferedReader(new FileReader(inputFile));
				while ((currentReadLine2 = reader.readLine()) != null) {
					readContent.add(currentReadLine2);
				}
			} catch (final FileNotFoundException currentReadLine2) {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (final IOException var5_6) {}
			} catch (final IOException currentReadLine2) {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (final IOException var5_7) {}
			}
		}
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (final IOException var5_9) {
				// empty catch block
			}
		}
		return readContent;
	}

	public static void write(final File outputFile, final List<String> writeContent, final boolean overrideContent) {
		BufferedWriter writer = null;
		try {
			try {
				writer = new BufferedWriter(new FileWriter(outputFile, !overrideContent));
				for (final String outputLine : writeContent) {
					writer.write(outputLine);
					writer.flush();
					writer.newLine();
				}
			} catch (final IOException outputLine) {
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (final IOException var7_7) {}
			}
		}
		finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (final IOException var7_9) {}
		}
	}
}
