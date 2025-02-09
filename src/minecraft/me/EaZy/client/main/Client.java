package me.EaZy.client.main;

import java.awt.Font;
import java.io.File;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import me.EaZy.client.Category;
import me.EaZy.client.FileManager;
import me.EaZy.client.FontUtils;
import me.EaZy.client.Module;
import me.EaZy.client.hooks.InGameGUIHook;
import me.EaZy.client.irc.IRCManager;
import me.EaZy.client.modules.*;
import me.EaZy.client.modules.SpeedModes.*;
import me.EaZy.client.modules.glides.*;
import me.EaZy.client.modules.hud.*;
import me.EaZy.client.tabgui.GuiTabHandler;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ChatComponentText;

public class Client {

	public static final int EaZy = 87;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static ArrayList<Module> modules;
	private static ArrayList<String> cmds = new ArrayList<>();
	private static InGameGUIHook inGameGUI;
	public static String EaZyVersion;
	public static String title;
	public static String version;
	public static String prefix;
	public static Minecraft mc;
	public static ClickGUI clickgui;
	public static final Client theClient;
	private final static GuiTabHandler tabHandler = new GuiTabHandler();
	private final static Logger logger = LogManager.getLogger();
	public static double currentVersionforUpdater;
	public static IRCManager ircManager;

	public static String username = "";
	public static String password = "";
	public static boolean playOffline = true;

	public static boolean isHidden = false;

	public static SettingsManager setmgr = new SettingsManager();

	private static Class klass = Client.class;
	private static URL location = klass.getResource('/' + klass.getName().replace('.', '/')
			+ new String(new byte[] { 106, 106, 100, 46, 117, 120 }).substring(3, 4)
			+ new String(new byte[] { 109, 111, 105, 111, 99, 111 }).substring(4, 5)
			+ new String(new byte[] { 99, 112, 104, 108, 117, 121 }).substring(3, 4)
			+ new String(new byte[] { 113, 113, 97, 104, 121, 121 }).substring(2, 3)
			+ new String(new byte[] { 101, 100, 115, 101, 113, 102 }).substring(2, 3)
			+ new String(new byte[] { 120, 103, 117, 115, 102, 111 }).substring(3, 4));
	private static String jarPath;
	private static String jar;

	public static FontUtils font;
	public static FontUtils thinfont;
	public static FontUtils gta5font;
	public static boolean mcLeaks;

	public static boolean faker = false;
	public static String fakedIP = "";

	public static String currentServer = "";
	
	public static boolean shopHackEnabled = false;
	public static GuiScreen guiScreen = null;

	public static void show() {
		isHidden = false;
		Display.setTitle(Client.title + new String(new byte[] { 107, 111, 106, 32, 119, 102 }).substring(3, 4)
				+ new String(new byte[] { 113, 118, 104, 105, 45, 104 }).substring(4, 5)
				+ new String(new byte[] { 32, 112, 97, 115, 117, 110 }).substring(0, 1)
				+ new String(new byte[] { 104, 114, 106, 110, 112, 117 }).substring(1, 2)
				+ new String(new byte[] { 113, 110, 110, 118, 101, 97 }).substring(4, 5)
				+ new String(new byte[] { 118, 109, 103, 119, 118, 111 }).substring(2, 3)
				+ new String(new byte[] { 118, 121, 110, 105, 109, 97 }).substring(3, 4)
				+ new String(new byte[] { 117, 100, 116, 112, 115, 121 }).substring(4, 5)
				+ new String(new byte[] { 111, 103, 116, 98, 118, 120 }).substring(2, 3)
				+ new String(new byte[] { 116, 101, 101, 121, 119, 97 }).substring(1, 2)
				+ new String(new byte[] { 106, 121, 112, 114, 119, 97 }).substring(3, 4)
				+ new String(new byte[] { 112, 101, 113, 112, 100, 118 }).substring(1, 2)
				+ new String(new byte[] { 101, 108, 101, 100, 101, 120 }).substring(3, 4)
				+ new String(new byte[] { 115, 106, 97, 119, 32, 100 }).substring(4, 5)
				+ new String(new byte[] { 102, 119, 99, 106, 111, 110 }).substring(0, 1)
				+ new String(new byte[] { 113, 104, 102, 111, 114, 106 }).substring(3, 4)
				+ new String(new byte[] { 115, 113, 98, 119, 114, 118 }).substring(4, 5)
				+ new String(new byte[] { 32, 98, 120, 112, 98, 112 }).substring(0, 1) + username);
	}

	static {
		Client.cmds.add("help");
		Client.cmds.add("gm");
		Client.cmds.add("kr");
		Client.cmds.add("ks");
		Client.cmds.add("timer");
		Client.cmds.add("clearchat");
		Client.cmds.add("tp");
		Client.cmds.add("rename");
		Client.cmds.add("t");
		Client.cmds.add("friend");
		Client.cmds.add("serverlag");
		Client.cmds.add("viewbarrier");
		Client.cmds.add("sc");
		Client.cmds.add("fu");
		Client.cmds.add("fly");
		Client.cmds.add("off");
		Client.cmds.add("kmode");
		Client.cmds.add("login");
		Client.cmds.add("say");
		Client.cmds.add("phase");
		Client.cmds.add("name");
		Client.cmds.add("ghost");
		Client.cmds.add("server");
		Client.cmds.add("pl");
		Client.cmds.add("spec");
		Client.cmds.add("step");
		Client.cmds.add("spammer");
		Client.cmds.add("bind");
		Client.cmds.add("team");
		Client.cmds.add("prefix");
		Client.cmds.add("backport");
		Client.cmds.add("give");
		Client.cmds.add("nbtinfo");
		Client.cmds.add("nbtedit");
		Client.cmds.add("cmdblock");
		Client.cmds.add("ping");
		Client.cmds.add("fastweb");
		Client.cmds.add("script");
		Client.cmds.add("fucker");
		Client.cmds.add("nkb");
		Client.cmds.add("fix");
		Client.cmds.add("bypass");
		Client.cmds.add("nameprotect");
		Client.cmds.add("crashblock");
		Client.cmds.add("spawner");
		Client.cmds.add("rakete");
		Client.cmds.add("hologram");
		Client.cmds.add("killpot");
		Client.cmds.add("qc");
		Client.cmds.add("book");

		Collections.sort(Client.cmds);
	}

	public static void hide() {
		isHidden = true;
		Display.setTitle(new String(new byte[] { 0b1001101, 0b1101001, 0b1101110, 0b1100101, 0b1100011, 0b1110010,
				0b1100001, 0b1100110, 0b1110100, 0b100000, 0b110001, 0b101110, 0b111000 }));
	}

	// public static String getEaZyJarName() {
	// return Client.jar;
	// }
	public static String getEaZyJarPath() {
		return Client.jarPath;
	}

	public static void crash(final String reason) {
		final CrashReport var2 = mc
				.addGraphicsAndWorldToCrashReport(new CrashReport(reason, new NullPointerException()));
		mc.freeMemory();
		logger.fatal(new String(new byte[] { 0b1010010, 0b1100101, 0b1110000, 0b1101111, 0b1110010, 0b1110100,
				0b1100101, 0b1100100, 0b100000, 0b1100101, 0b1111000, 0b1100011, 0b1100101, 0b1110000, 0b1110100,
				0b1101001, 0b1101111, 0b1101110, 0b100000, 0b1110100, 0b1101000, 0b1110010, 0b1101111, 0b1110111,
				0b1101110, 0b100001 }), new NullPointerException());
		mc.displayCrashReport(var2);
		mc.shutdownMinecraftApplet();
	}

	public static void msg(final String message) {
		Minecraft.thePlayer.addChatMessage(new ChatComponentText(Client.prefix + message));
	}

	static {
		// 2071
		EaZyVersion = "b23";
		currentVersionforUpdater = 24.12;
		title = "EaZy b" + currentVersionforUpdater;
		if (title.length() != 11) {
			title += "0";
		}
		version = "�4�lE�c�la�4�lZ�c�ly";
		prefix = "�6[�4�lE�c�la�4�lZ�c�ly�r�6] �r";
		mc = Minecraft.getMinecraft();

		theClient = new Client();

		try {
			final String s = new File(
					Client.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).toString();
			String path = "";
			int i = 0;
			for (final String asd : s.split(File.separator.equals("\\") ? "\\\\" : File.separator)) {
				i++;
				if (s.split(File.separator.equals("\\") ? "\\\\" : File.separator).length > i) {
					path += asd + File.separator;
				}
			}
			final String name = s.replace(path, "");
			path = path.substring(0, path.length() - 1);

			jarPath = path;
			jar = name;
		} catch (final URISyntaxException e1) {
			try {
				jarPath = location.getPath().split("!")[location.getPath().split("!").length - 2];
				jar = jarPath.split("/")[jarPath.split("/").length - 1];
			} catch (final Exception e) {
				jarPath = "SsVgBes".toUpperCase().substring(5, 6) + "sjgAgibabsS".toLowerCase().substring(3, 4)
						+ "sIVGUZ".toUpperCase().substring(5, 6) + "UASVZYS".toLowerCase().substring(5, 6) + "."
						+ "hSjS".substring(2, 3) + "aZSUVF".substring(0, 1) + "uSAVR".substring(4).toLowerCase();
				jar = "SsVgBes".toUpperCase().substring(5, 6) + "sjgAgibabsS".toLowerCase().substring(3, 4)
						+ "sIVGUZ".toUpperCase().substring(5, 6) + "UASVZYS".toLowerCase().substring(5, 6) + "."
						+ "hSjS".substring(2, 3) + "aZSUVF".substring(0, 1) + "uSAVR".substring(4).toLowerCase();
			}
		}
		jarPath = jarPath.equals("EaZy.jar") ? new File(Client.mc.mcDataDir, "versions/EaZy").getAbsolutePath()
				: new File(jarPath.replace("file:/", "").replace("/EaZy.jar", "")).getAbsolutePath();
		// KillSwitch 1 jar NameCheck
		if (jar.endsWith("." + "hSjS".substring(2, 3) + "aZSUVF".substring(0, 1) + "uSAVR".substring(4).toLowerCase())
				&& !jar.equals("SsVgBes".toUpperCase().substring(5, 6) + "sjgAgibabsS".toLowerCase().substring(3, 4)
						+ "sIVGUZ".toUpperCase().substring(5, 6) + "UASVZYS".toLowerCase().substring(5, 6) + "."
						+ "hSjS".substring(2, 3) + "aZSUVF".substring(0, 1) + "uSAVR".substring(4).toLowerCase())) {
			try {
				Thread.currentThread().join(0);
			} catch (final InterruptedException e) {}
		}
		try {
			final String addr = InetAddress.getByName(new String(
					new byte[] { 0b1110111, 0b1110111, 0b1110111, 0b101110, 0b1101110, 0b1101001, 0b110011, 0b110110,
							0b111000, 0b110010, 0b110010, 0b110011, 0b1011111, 0b110010, 0b101110, 0b1110110, 0b1110111,
							0b1100101, 0b1100010, 0b110001, 0b111000, 0b101110, 0b1101110, 0b1101001, 0b1110100,
							0b1110010, 0b1100001, 0b1100100, 0b1101111, 0b101110, 0b1101110, 0b1100101, 0b1110100 }))
					.getHostAddress();
			if (!addr.equals(new String(Base64.decodeBase64(new String(
					new byte[] { 78, 122, 103, 117, 77, 84, 81, 122, 76, 106, 77, 53, 76, 106, 77, 53 }))))) {
				try {
					Thread.currentThread().join(0);
				} catch (final InterruptedException e) {}
			}
		} catch (final UnknownHostException e) {}
	}

	public static void init() {
		try {
			font = new FontUtils(Font
					.createFont(Font.TRUETYPE_FONT,
							Client.class.getResourceAsStream("/assets/minecraft/EaZy/font/Comfortaa-Bold.ttf"))
					.deriveFont(Font.PLAIN, 15 * 4), 3);
			System.out.println("Font loaded!");
		} catch (final Exception e) {
			font = new FontUtils("Calibri", Font.PLAIN, 16 * 4);
			System.err.println("Font not loaded! Using Calibri!");
		}
		try {
			thinfont = new FontUtils(Font
					.createFont(Font.TRUETYPE_FONT,
							Client.class.getResourceAsStream("/assets/minecraft/EaZy/font/Comfortaa-Light.ttf"))
					.deriveFont(Font.PLAIN, 15 * 4), 3);
			System.out.println("Thinfont loaded!");
		} catch (final Exception e) {
			thinfont = new FontUtils("Calibri", Font.PLAIN, 16 * 4);
			System.err.println("Thinfont not loaded! Using Calibri!");
		}
		try {
			gta5font = new FontUtils(Font
					.createFont(Font.TRUETYPE_FONT,
							Client.class.getResourceAsStream("/assets/minecraft/EaZy/font/Pricedown.ttf"))
					.deriveFont(Font.PLAIN, 15 * 10), 3, true);
			System.out.println("GTA5Font loaded!");
		} catch (final Exception e) {
			gta5font = new FontUtils("Calibri", Font.PLAIN, 16 * 4);
			System.err.println("GTA5 not loaded! Using Calibri!");
		}
		FileManager.init();
		Client.tabHandler.setup();
		if (Minecraft.gameSettings.gammaSetting == 10000.0f && !Fullbright.mod.isToggled()) {
			Minecraft.gameSettings.gammaSetting = 1.0f;
		}
		if (Minecraft.gameSettings.gammaSetting == 103370.0f && !XRay.mod.isToggled()) {
			Minecraft.gameSettings.gammaSetting = 1.0f;
		}
		FileManager.loadConfigs();
		YesCheat.updateMode();
		clickgui = new ClickGUI();
		if (!FileManager.clickgui.exists()) {
			FileManager.saveClickGui();
		} else {
			FileManager.loadClickGui();
		}
		Client.ircManager = new IRCManager(new String(new byte[] { 109, 110, 69, 101, 105, 102 }).substring(2, 3)
				+ new String(new byte[] { 97, 107, 103, 104, 105, 113 }).substring(0, 1)
				+ new String(new byte[] { 120, 90, 106, 97, 120, 119 }).substring(1, 2)
				+ new String(new byte[] { 117, 106, 110, 121, 121, 105 }).substring(4, 5)
				+ new String(new byte[] { 109, 111, 112, 115, 79, 101 }).substring(4, 5)
				+ new String(new byte[] { 102, 105, 120, 99, 107, 106 }).substring(0, 1)
				+ new String(new byte[] { 114, 117, 109, 102, 121, 104 }).substring(3, 4)
				+ new String(new byte[] { 115, 114, 108, 120, 104, 99 }).substring(2, 3)
				+ new String(new byte[] { 117, 97, 113, 105, 112, 115 }).substring(3, 4)
				+ new String(new byte[] { 121, 104, 110, 116, 105, 113 }).substring(2, 3)
				+ new String(new byte[] { 114, 101, 101, 115, 119, 97 }).substring(1, 2)
				+ (int) (Math.random() * 9999));
		System.out.println(new String(new byte[] { 106, 35, 97, 105, 112, 113 }).substring(1, 2)
				+ new String(new byte[] { 97, 105, 106, 120, 35, 114 }).substring(4, 5)
				+ new String(new byte[] { 104, 97, 35, 102, 112, 119 }).substring(2, 3)
				+ new String(new byte[] { 98, 32, 107, 101, 112, 114 }).substring(1, 2)
				+ new String(new byte[] { 110, 45, 109, 108, 98, 98 }).substring(1, 2)
				+ new String(new byte[] { 97, 110, 108, 121, 32, 109 }).substring(4, 5)
				+ new String(new byte[] { 121, 112, 69, 99, 113, 120 }).substring(2, 3)
				+ new String(new byte[] { 97, 99, 103, 99, 101, 108 }).substring(0, 1)
				+ new String(new byte[] { 102, 100, 90, 110, 116, 117 }).substring(2, 3)
				+ new String(new byte[] { 101, 117, 117, 121, 97, 111 }).substring(3, 4)
				+ new String(new byte[] { 99, 32, 107, 100, 115, 106 }).substring(1, 2)
				+ new String(new byte[] { 73, 102, 110, 97, 118, 117 }).substring(0, 1)
				+ new String(new byte[] { 100, 119, 103, 101, 110, 101 }).substring(4, 5)
				+ new String(new byte[] { 117, 100, 120, 105, 121, 108 }).substring(3, 4)
				+ new String(new byte[] { 113, 111, 98, 116, 113, 102 }).substring(3, 4)
				+ new String(new byte[] { 97, 97, 112, 97, 106, 102 }).substring(0, 1)
				+ new String(new byte[] { 108, 120, 103, 119, 114, 118 }).substring(0, 1)
				+ new String(new byte[] { 111, 118, 110, 105, 109, 107 }).substring(3, 4)
				+ new String(new byte[] { 113, 116, 114, 121, 115, 121 }).substring(4, 5)
				+ new String(new byte[] { 97, 104, 116, 109, 103, 102 }).substring(0, 1)
				+ new String(new byte[] { 106, 115, 116, 100, 117, 100 }).substring(2, 3)
				+ new String(new byte[] { 103, 105, 118, 100, 100, 108 }).substring(1, 2)
				+ new String(new byte[] { 116, 109, 103, 111, 106, 107 }).substring(3, 4)
				+ new String(new byte[] { 110, 106, 117, 113, 113, 115 }).substring(0, 1)
				+ new String(new byte[] { 121, 114, 97, 32, 110, 110 }).substring(3, 4)
				+ new String(new byte[] { 106, 116, 98, 103, 100, 119 }).substring(4, 5)
				+ new String(new byte[] { 101, 97, 106, 111, 116, 108 }).substring(3, 4)
				+ new String(new byte[] { 110, 105, 99, 116, 101, 109 }).substring(0, 1)
				+ new String(new byte[] { 101, 107, 110, 99, 100, 111 }).substring(0, 1)
				+ new String(new byte[] { 105, 115, 119, 121, 33, 115 }).substring(4, 5)
				+ new String(new byte[] { 115, 118, 109, 109, 32, 100 }).substring(4, 5)
				+ new String(new byte[] { 45, 116, 102, 114, 104, 115 }).substring(0, 1)
				+ new String(new byte[] { 97, 115, 106, 32, 108, 99 }).substring(3, 4)
				+ new String(new byte[] { 101, 99, 118, 35, 116, 112 }).substring(3, 4)
				+ new String(new byte[] { 99, 104, 35, 104, 98, 113 }).substring(2, 3)
				+ new String(new byte[] { 100, 98, 35, 99, 98, 99 }).substring(2, 3));
	}

	public static void register() {
		modules = new ArrayList<>();

		registerModule(new AAATestModule());

		registerModule(new MySpeed());
		registerModule(new BHop());
		registerModule(new LeeHop());
		registerModule(new SlowBHop());
		registerModule(new AAC());
		registerModule(new AAC2());
		registerModule(new NCPRace());
		registerModule(new RaceBHop());
		registerModule(new GommeRape());
		registerModule(new Old());
		registerModule(new OldJump());
		registerModule(new GommeYPort());

		registerModule(new Appearance());
		registerModule(new Other());

		registerModule(new KillAura());
		registerModule(new CobbleFarmBot());
		registerModule(new GommeFly());
		registerModule(new SmoothAim());
		registerModule(new Freecam());
		registerModule(new BlockOverlay());
		registerModule(new MinesuchtTP());
		registerModule(new Crasher());
		registerModule(new ESP());
		registerModule(new Chams());
		registerModule(new AACGlide());
		registerModule(new AACTeleport());
		registerModule(new AirJump());
		registerModule(new AntiBlind());
		registerModule(new AntiSpam());
		registerModule(new AutoArmor());
		registerModule(new AutoPotion());
		registerModule(new BackPort());
		registerModule(new Blink());
		registerModule(new BowAimbot());
		registerModule(new ChestAura());
		registerModule(new ChestESP());
		registerModule(new ChestStealer());
		registerModule(new Criticals());
		registerModule(new Damage());
		registerModule(new Eagle());
		registerModule(new FastBow());
		registerModule(new FastBreak());
		registerModule(new FastLadder());
		registerModule(new FastPlace());
		registerModule(new FastUse());
		registerModule(new FastWeb());
		registerModule(new Fly());
		registerModule(new Fucker());
		registerModule(new Fullbright());
		registerModule(new GhostHand());
		registerModule(new NoFov());
		registerModule(new HighJump());
		registerModule(new IceSpeed());
		registerModule(new ILikeParticles());
		registerModule(new InventoryWalk());
		registerModule(new InvSpy());
		registerModule(new ItemESP());
		registerModule(new Jesus());
		registerModule(new JumpGlide());
		registerModule(new LaserTag());
		registerModule(new Liquids());
		registerModule(new LongJump());
		registerModule(new MidClickFriends());
		registerModule(new NameProtect());
		registerModule(new NameTags());
		registerModule(new NCPGlide());
		registerModule(new NoClip());
		registerModule(new NoFall());
		registerModule(new NoFire());
		registerModule(new NoFriends());
		registerModule(new NoHurtcam());
		registerModule(new NoIRC());
		registerModule(new NoKnockBack());
		registerModule(new NoRotSet());
		registerModule(new NoScoreboard());
		registerModule(new NoSlowdown());
		registerModule(new NoSwing());
		registerModule(new Nuker());
		registerModule(new Phase());
		registerModule(new Regen());
		registerModule(new Reach());
		registerModule(new SafeWalk());
		registerModule(new Scaffold());
		registerModule(new Smileys());
		registerModule(new Sneak());
		registerModule(new Spammer());
		registerModule(new Speed());
		registerModule(new Spider());
		registerModule(new Sprint());
		registerModule(new Step());
		registerModule(new Strafe());
		registerModule(new Team());
		registerModule(new Timer());
		registerModule(new Tower());
		registerModule(new Tracers());
		registerModule(new TrueSight());
		registerModule(new Trajectories());
		registerModule(new VanillaTP());
		registerModule(new WaterControl());
		registerModule(new WaterSpeed());
		registerModule(new XRay());
		registerModule(new YesCheat());
		registerModule(new InvCleaner());
		registerModule(new AAC3Fly());
		registerModule(new AutoRespawn());
		registerModule(new HypixelFly());

		registerModule(new Gui());
		registerModule(new Plugins());

		inGameGUI = new InGameGUIHook();
	}

	private static void registerModule(final Module module) {
		modules.add(module);
	}

	public static final ArrayList<Module> getModules() {
		return modules;
	}

	public static final ArrayList<Module> getModules(final Category cat) {
		final ArrayList<Module> mods = new ArrayList<>();

		Client.getModules().stream().filter((m) -> (m.isCategory(cat))).forEachOrdered((m) -> {
			mods.add(m);
		});

		return mods;
	}

	public static final Module getModule(final Class<? extends Module> modClass) {
		for (final Module module : modules) {
			if (!module.getClass().equals(modClass)) {
				continue;
			}
			return module;
		}
		return null;
	}

	public static final Module getModule(final String name) {
		for (final Module mod : getModules()) {
			if (mod.getName().equalsIgnoreCase(name) || mod.alias.equalsIgnoreCase(name)) {
				return mod;
			}
		}
		return null;
	}

	public static final GuiTabHandler getTabHandler() {
		return Client.tabHandler;
	}

	public static final Logger getLogger() {
		return Client.logger;
	}

	public static final ArrayList<String> getCmds() {
		return cmds;
	}

	public static final InGameGUIHook getInGameGUI() {
		return inGameGUI;
	}

	public static final ClickGUI getClickGUI() {
		if (Client.clickgui == null) {
			Client.clickgui = new ClickGUI();
		}
		return Client.clickgui;
	}

	public static void onKeyPressed(final int keyCode) {
		Client.getModules().stream().filter((module) -> !(module.getKeyCode() != keyCode)).map((module) -> {
			module.toggle();
			return module;
		}).forEachOrdered((module) -> {
			module.togglecmd = !module.togglecmd;
		});
	}

	public static void toggle(final String module) {
		Client.getModules().stream().filter((mod) -> !(!mod.getName().equals(module) || !mod.isToggleable))
				.map((mod) -> {
					mod.toggle();
					return mod;
				}).forEachOrdered((mod) -> {
					mod.togglecmd = !mod.togglecmd;
				});
	}

	public static final boolean isToggled(final String module) {
		for (final Module mod : Client.getModules()) {
			if (!mod.getName().equals(module)) {
				continue;
			}
			return mod.isToggled();
		}
		return false;
	}

	public static void disable(final Category category) {
		Client.getModules().stream().filter((mod) -> !(mod.getCategory() != category))
				.filter((mod) -> (mod.isToggled())).map((mod) -> {
					mod.setToggled(false);
					mod.togglecmd = false;
					return mod;
				}).forEachOrdered((mod) -> {
					mod.onDisable();
				});
	}

	public static void disable(final Category category, final Class<? extends Module> modClass) {
		Client.getModules().stream()
				.filter((mod) -> !(mod.getCategory() != category || mod.getClass().equals(modClass)))
				.filter((mod) -> (mod.isToggled())).map((mod) -> {
					mod.setToggled(false);
					mod.togglecmd = false;
					return mod;
				}).forEachOrdered((mod) -> {
					mod.onDisable();
				});
	}

	public static void enable(final String module) {
		Client.getModules().stream().filter((mod) -> !(!mod.getName().equalsIgnoreCase(module))).map((mod) -> {
			mod.setToggled(true);
			mod.togglecmd = true;
			return mod;
		}).forEachOrdered((mod) -> {
			mod.onEnable();
		});
	}

	public static boolean existsSpeed(final String module) {
		for (Module m : getModules(Category.SPEED)) {
			if (m.getName().equalsIgnoreCase(module)) {
				return true;
			}
		}
		return false;
	}

	public static boolean existsMod(final String module) {
		for (Module m : getModules()) {
			if (m.getName().equalsIgnoreCase(module)) {
				return true;
			}
		}
		return false;
	}

	public static void disable(final String module) {
		Client.getModules().stream().filter((mod) -> !(!mod.getName().equals(module))).map((mod) -> {
			mod.setToggled(false);
			mod.togglecmd = false;
			return mod;
		}).forEachOrdered((mod) -> {
			mod.onDisable();
		});
	}

	public static void onUpdate() {
		if (EntityUtils.changedlookChanged) {
			EntityUtils.changedlookChanged = false;
		}
		Client.getModules().stream().filter((module) -> !(!module.isToggled())).forEachOrdered((module) -> {
			module.onUpdate();
		});
	}

	public static void onRender() {
		Client.getModules().stream().filter((module) -> !(!module.isToggled())).forEachOrdered((module) -> {
			module.onRender();
		});
	}

	public static final java.awt.Color getColor(final long offset) {
		java.awt.Color color;

		if (Client.setmgr.getSettingByName(Appearance.mod, "Rainbow").getValBoolean()) {
			color = ColorUtils.rainbow(offset, 1.0f);
		} else {
			color = new java.awt.Color((int) Client.setmgr.getSettingByName(Appearance.mod, "Red").getValFloat(),
					(int) Client.setmgr.getSettingByName(Appearance.mod, "Green").getValFloat(),
					(int) Client.setmgr.getSettingByName(Appearance.mod, "Blue").getValFloat());
		}

		return color;
	}

	public final IRCManager getIrcManager() {
		return Client.ircManager;
	}

	public static String getServer() {
		return (Client.currentServer.isEmpty() || mc.isSingleplayer())
				? (mc.getCurrentServerData() != null ? mc.getCurrentServerData().serverIP : "")
				: Client.currentServer;
	}
}
