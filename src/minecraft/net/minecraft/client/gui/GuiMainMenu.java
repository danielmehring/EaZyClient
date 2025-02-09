package net.minecraft.client.gui;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.google.common.collect.Lists;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.Updater;
import me.EaZy.client.games.CookieClicker;
import me.EaZy.client.gui.CuzImGeileSchnitte;
import me.EaZy.client.gui.GuiChangelog;
import me.EaZy.client.gui.GuiEaZyLogin;
import me.EaZy.client.gui.GuiEaZyPanel;
import me.EaZy.client.gui.alts.GuiAlts;
import me.EaZy.client.main.Client;
import me.EaZy.client.particle.ParticleSystem;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

	public static final int EaZy = 487;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final AtomicInteger field_175373_f = new AtomicInteger(0);
	private static final Logger logger = LogManager.getLogger();
	private static final Random field_175374_h = new Random();

	int fading;

	public static boolean ersterapril;

	private int fileSize = -1;

	int updateDelay;

	int atem;

	int atemdelay = 0;

	float tollesache;

	/** Counts the number of screen updates. */
	private final float updateCounter;

	/** The splash message. */
	private String splashText;
	private GuiButton buttonResetDemo;

	/** Timer used to rotate the panorama, increases every tick. */
	private int panoramaTimer;

	/**
	 * Texture allocated for the current viewport of the main menu's panorama
	 * background.
	 */
	private DynamicTexture viewportTexture;
	private final Object field_104025_t = new Object();
	private String field_92025_p;
	private String field_146972_A;
	private String field_104024_v;
	private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");

	private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("EaZy/MainMenu/EaZy.png");

	public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here"
			+ EnumChatFormatting.RESET + " for more information.";
	private int field_92024_r;
	private int field_92023_s;
	private int field_92022_t;
	private int field_92021_u;
	private int field_92020_v;
	private int field_92019_w;
	private ResourceLocation field_110351_G;
	public ParticleSystem ps = new ParticleSystem(100, false);

	public GuiMainMenu() {
		field_146972_A = field_96138_a;
		splashText = "missingno";
		BufferedReader var1 = null;

		try {
			final ArrayList var2 = Lists.newArrayList();

			var1 = new BufferedReader(new InputStreamReader(
					Minecraft.getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
			String var3;

			while ((var3 = var1.readLine()) != null) {
				var3 = var3.trim();

				if (!var3.isEmpty()) {
					var2.add(var3);
				}
			}

			if (!var2.isEmpty()) {
				do {
					splashText = (String) var2.get(field_175374_h.nextInt(var2.size()));
				}
				while (splashText.hashCode() == 125780783);
			}
		} catch (final IOException var12) {}
		finally {
			if (var1 != null) {
				try {
					var1.close();
				} catch (final IOException var11) {}
			}
		}

		updateCounter = field_175374_h.nextFloat();
		field_92025_p = "";

		try {
			if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
				field_92025_p = I18n.format("title.oldgl1", new Object[0]);
				field_146972_A = I18n.format("title.oldgl2", new Object[0]);
				field_104024_v = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
			}
		} catch (Exception e) {}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */

	@Override
	public void updateScreen() {

		// if (((0 + (float) new File(FileManager.eazyDir, "EaZy.zip").length()
		// / (float) fileSize * 100) == 100)) {
		// ((GuiButton) buttonList.get(0)).visible = true;
		// ((GuiButton) buttonList.get(0)).enabled = true;
		// } else {
		// ((GuiButton) buttonList.get(0)).visible = false;
		// ((GuiButton) buttonList.get(0)).enabled = false;
		// }

		++panoramaTimer;
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_F5) {
			mc.displayGuiScreen(this);
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.displayWidth,
				Minecraft.displayHeight);
		panoramaTimer = 0;
		viewportTexture = new DynamicTexture(256, 256);
		field_110351_G = Minecraft.getTextureManager().getDynamicTextureLocation("background", viewportTexture);
		final Calendar var1 = Calendar.getInstance();
		var1.setTime(new Date());

		GuiScreen.fadeX = width / 2;
		GuiScreen.fadeY = height / 2;
		GuiScreen.fadeX2 = width / 2;
		GuiScreen.fadeY2 = height / 2;

		ersterapril = var1.get(2) + 1 == 4 && var1.get(5) == 1;

		if (var1.get(2) + 1 == 11 && var1.get(5) == 9) {
			splashText = "Happy birthday, ez!";
		} else if (var1.get(2) + 1 == 6 && var1.get(5) == 1) {
			splashText = "Happy birthday, Notch!";
		} else if (var1.get(2) + 1 == 12 && var1.get(5) == 24) {
			splashText = "Merry X-mas!";
		} else if (var1.get(2) + 1 == 1 && var1.get(5) == 1) {
			splashText = "Happy new year!";
		} else if (var1.get(2) + 1 == 10 && var1.get(5) == 31) {
			splashText = "OOoooOOOoooo! Spooky!";
		}

		final int var3 = sr.getScaledHeight() / 4 + 48;

		if (mc.isDemo()) {
			addDemoButtons(var3, 24);
		} else {
			if (!Updater.needsUpdate) {
				addSingleplayerMultiplayerButtons(var3, 24);
			}
		}

		if (!Updater.needsUpdate) {
			buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, 98, 20,
					I18n.format("menu.options", new Object[0])));
		}

		if (!Updater.needsUpdate) {
			buttonList.add(
					new GuiButton(4, width / 2 + 2, var3 + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
		} else {
			buttonList.add(
					new GuiButton(4, width / 2 - 180 / 2, var3 + 72, 180, 20, I18n.format("menu.quit", new Object[0])));
		}

		synchronized (field_104025_t) {
			field_92023_s = fontRendererObj.getStringWidth(field_92025_p);
			field_92024_r = fontRendererObj.getStringWidth(field_146972_A);
			final int var5 = Math.max(field_92023_s, field_92024_r);
			field_92022_t = (width - var5) / 2;
			field_92021_u = ((GuiButton) buttonList.get(0)).yPosition - 24;
			field_92020_v = field_92022_t + var5;
			field_92019_w = field_92021_u + 24;
		}

		if (Client.username.contains(new String(new byte[] { 111, 110, 73, 97, 112, 97 }).substring(2, 3)
				+ new String(new byte[] { 110, 120, 103, 110, 100, 112 }).substring(3, 4)
				+ new String(new byte[] { 109, 114, 108, 118, 104, 114 }).substring(3, 4)
				+ new String(new byte[] { 111, 114, 97, 113, 99, 115 }).substring(2, 3)
				+ new String(new byte[] { 108, 121, 97, 101, 98, 111 }).substring(0, 1)
				+ new String(new byte[] { 97, 116, 105, 115, 106, 102 }).substring(2, 3)
				+ new String(new byte[] { 110, 101, 117, 105, 100, 110 }).substring(4, 5)
				+ new String(new byte[] { 109, 114, 97, 107, 32, 110 }).substring(4, 5)
				+ new String(new byte[] { 113, 120, 107, 113, 78, 121 }).substring(4, 5)
				+ new String(new byte[] { 97, 104, 112, 102, 108, 104 }).substring(0, 1)
				+ new String(new byte[] { 117, 109, 114, 117, 98, 97 }).substring(1, 2)
				+ new String(new byte[] { 104, 97, 97, 101, 102, 114 }).substring(3, 4)
				+ new String(new byte[] { 112, 111, 120, 113, 32, 108 }).substring(4, 5)
				+ new String(new byte[] { 100, 117, 109, 111, 117, 111 }).substring(3, 4)
				+ new String(new byte[] { 113, 105, 114, 112, 112, 103 }).substring(2, 3)
				+ new String(new byte[] { 113, 106, 32, 105, 116, 121 }).substring(2, 3)
				+ new String(new byte[] { 102, 80, 121, 109, 112, 100 }).substring(1, 2)
				+ new String(new byte[] { 97, 109, 107, 97, 98, 107 }).substring(3, 4)
				+ new String(new byte[] { 101, 112, 115, 110, 101, 100 }).substring(2, 3)
				+ new String(new byte[] { 115, 119, 115, 99, 116, 105 }).substring(0, 1)
				+ new String(new byte[] { 119, 101, 112, 103, 105, 106 }).substring(0, 1)
				+ new String(new byte[] { 118, 106, 111, 115, 113, 111 }).substring(2, 3)
				+ new String(new byte[] { 114, 106, 107, 98, 106, 101 }).substring(0, 1)
				+ new String(new byte[] { 112, 100, 103, 103, 101, 119 }).substring(1, 2))) {
			new Minecraft(null).run();
		}

	}

	/**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who
	 * have bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(final int p_73969_1_, final int p_73969_2_) {
		buttonList.add(new GuiButton(1, width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
		buttonList.add(new GuiButton(2, width / 2 - 100, p_73969_1_ + p_73969_2_ * 1,
				I18n.format("menu.multiplayer", new Object[0])));
		if (Client.playOffline) {
			while (true) {}
		}
		if (!Client.isHidden) {
			buttonList.add(new GuiButton(100, width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, "EaZy"));
			buttonList.add(new GuiButton(1234567, width - mc.fontRendererObj.getStringWidth("AltManager") - 3,
					height - 12, 43, 12, "HIDETHISBUTTON"));
		}
	}

	/**
	 * Adds Demo buttons on Main Menu for players who are playing Demo.
	 */
	private void addDemoButtons(final int p_73972_1_, final int p_73972_2_) {
		buttonList.add(new GuiButton(11, width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
		buttonList.add(buttonResetDemo = new GuiButton(12, width / 2 - 100, p_73972_1_ + p_73972_2_ * 1,
				I18n.format("menu.resetdemo", new Object[0])));
		final ISaveFormat var3 = mc.getSaveLoader();
		final WorldInfo var4 = var3.getWorldInfo("Demo_World");

		if (var4 == null) {
			buttonResetDemo.enabled = false;
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {

		if (panoramaTimer < 4) {
			return;
		}

		if (button.id == 100) {
			mc.displayGuiScreen(new GuiEaZyPanel());
		}

		if (button.id == 1234567) {
			mc.displayGuiScreen(new GuiAlts(this));
		}

		if (button.id == 101) {
			mc.displayGuiScreen(new CuzImGeileSchnitte(this));
		}

		if (button.id == 102) {
			mc.displayGuiScreen(new CookieClicker(this));
		}

		if (button.id == 103) {
			mc.displayGuiScreen(new GuiChangelog(this));
		}

		if (button.id == 0) {
			mc.displayGuiScreen(new GuiOptions(this, Minecraft.gameSettings));
		}

		if (button.id == 1) {
			mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if (button.id == 2) {
			if (((GuiButton) buttonList.get(1)).displayString.equals("�c" + "bqNhei".substring(2, 3)
					+ "kfoipp".substring(2, 3) + "ripfit".substring(2, 3) + "chexsx".substring(2, 3)
					+ "qc!wmm".substring(2, 3) + " " + "�4" + "ybYxjh".substring(2, 3) + "dvoami".substring(2, 3)
					+ "jiuprn".substring(2, 3) + "pa yid".substring(2, 3) + "gwmiof".substring(2, 3)
					+ "mqusvk".substring(2, 3) + "bdsumt".substring(2, 3) + "frtyro".substring(2, 3)
					+ "op xfy".substring(2, 3) + "bpluqj".substring(2, 3) + "xhoqsd".substring(2, 3)
					+ "aygwsw".substring(2, 3) + "tt asv".substring(2, 3) + "keimxs".substring(2, 3)
					+ "knnrfe".substring(2, 3) + "um!niq".substring(2, 3))) {
				mc.displayGuiScreen(new GuiEaZyLogin());
			} else {
				mc.displayGuiScreen(new GuiMultiplayer(this));
			}
		}

		if (button.id == 4) {
			mc.shutdown();
		}

		if (button.id == 11) {
			mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		}

		if (button.id == 12) {
			final ISaveFormat var2 = mc.getSaveLoader();
			final WorldInfo var3 = var2.getWorldInfo("Demo_World");

			if (var3 != null) {
				final GuiYesNo var4 = GuiSelectWorld.func_152129_a(this, var3.getWorldName(), 12);
				mc.displayGuiScreen(var4);
			}
		}
	}

	@Override
	public void confirmClicked(final boolean result, final int id) {
		if (result && id == 12) {
			final ISaveFormat var6 = mc.getSaveLoader();
			var6.flushCache();
			var6.deleteWorldDirectory("Demo_World");
			mc.displayGuiScreen(this);
		} else if (id == 13) {
			if (result) {
				try {
					final Class var3 = Class.forName("java.awt.Desktop");
					final Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var3.getMethod("browse", new Class[] { URI.class }).invoke(var4,
							new Object[] { new URI(field_104024_v) });
				} catch (final Throwable var5) {
					logger.error("Couldn\'t open link", var5);
				}
			}

			mc.displayGuiScreen(this);
		}
	}

	public static ResourceLocation bg = null;

	static {
		doTheFcnkShit();
	}

	public static void doTheFcnkShit() {
		if (Configs.bgPath.isEmpty()) {
			bg = new ResourceLocation("EaZy/MainMenu/aha.jpg");
		} else {
			BufferedImage img = null;
			File file = new File(Configs.bgPath);
			try {
				img = ImageIO.read(file);
				bg = Minecraft.getRenderManager().renderEngine.getDynamicTextureLocation(file.getName(),
						new DynamicTexture(img));
			} catch (IOException e) {
				e.printStackTrace();
				bg = new ResourceLocation("EaZy/MainMenu/aha.jpg");
			}
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		final int var7 = width / 2 - 137;
		drawGradientRect(0, 0, width, height, -2130706433, 16777215);
		drawGradientRect(0, 0, width, height, 0, Integer.MIN_VALUE);
		final ScaledResolution scaledRes = new ScaledResolution(mc, Minecraft.displayWidth, Minecraft.displayHeight);
		GlStateManager.color(0.75f, 0.75f, 0.75f, 1.0f);
		Minecraft.getTextureManager().bindTexture(bg);
		if (Configs.bgMoving) {
			Gui.drawScaledCustomSizeModalRectMoving(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
					scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(),
					scaledRes.getScaledHeight(), mouseX, mouseY);
		} else {
			Gui.drawScaledCustomSizeModalRect(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(),
					scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(),
					scaledRes.getScaledHeight());
		}

		ps.render(mouseX, mouseY);
		int var3 = scaledRes.getScaledHeight() / 4 + 48;

		if (!Client.isHidden) {
			GuiScreen.targetX = width / 2 - 104;
			GuiScreen.targetY = var3 - 64;
			GuiScreen.targetX2 = width / 2 + 105;
			GuiScreen.targetY2 = var3 + 110;

			if (!Client.isHidden && !Updater.needsUpdate && !((int) (0
					+ (float) new File(FileManager.eazyDir, "EaZy.zip").length() / (float) fileSize * 100) == 100)) {
				Gui.drawRect(GuiScreen.fadeX, GuiScreen.fadeY, GuiScreen.fadeX2, GuiScreen.fadeY2, 0x7c000000);
			}
			final String s = Client.version + " �6�l" + Client.EaZyVersion;
			Gui.drawString(fontRendererObj, s, 2, height - fontRendererObj.FONT_HEIGHT, -1);

			if (Client.playOffline) {
				while (Boolean.parseBoolean(new String(new byte[] { 97, 109, 111, 116, 98, 121 }).substring(3, 4)
						+ new String(new byte[] { 116, 112, 101, 113, 114, 121 }).substring(4, 5)
						+ new String(new byte[] { 97, 117, 101, 104, 102, 115 }).substring(1, 2)
						+ new String(new byte[] { 106, 97, 101, 97, 110, 113 }).substring(2, 3))) {}
			} else {
				Gui.drawString(fontRendererObj, "�6Welcome", 2, 2, -1);
				Gui.drawString(fontRendererObj, "�2" + Client.username, 2, 2 + 10, -1);
			}
			Gui.drawString(fontRendererObj, "AltManager", width - mc.fontRendererObj.getStringWidth("AltManager") - 1,
					height - fontRendererObj.FONT_HEIGHT, ColorUtils.rainbow(0l, 1.0f).getRGB());
		}

		GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColorMask(true, true, true, false);

		Minecraft.getTextureManager().bindTexture(minecraftTitleTextures);
		drawTexturedModalRect(var7 + 41, var3 - 70, 0, 0, 193, 80);

		GL11.glDisable(GL11.GL_BLEND);

		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColorMask(true, true, true, false);

		Minecraft.getTextureManager().bindTexture(minecraftTitleTextures);
		drawTexturedModalRect(var7 + 40, var3 - 71, 0, 0, 193, 80);

		GL11.glDisable(GL11.GL_BLEND);

		if (Updater.needsUpdate) {
			if (fileSize == -1) {
				try {
					// http://ni368223_2.vweb18.nitrado.net/filesize.php
					final URL url = new URL(new String(new byte[] { 106, 120, 104, 98, 115, 114 }).substring(2, 3)
							+ new String(new byte[] { 101, 115, 103, 116, 109, 98 }).substring(3, 4)
							+ new String(new byte[] { 116, 98, 120, 104, 117, 105 }).substring(0, 1)
							+ new String(new byte[] { 114, 115, 97, 112, 121, 100 }).substring(3, 4)
							+ new String(new byte[] { 119, 101, 58, 108, 110, 102 }).substring(2, 3)
							+ new String(new byte[] { 117, 101, 47, 109, 117, 98 }).substring(2, 3)
							+ new String(new byte[] { 97, 108, 120, 47, 114, 114 }).substring(3, 4)
							+ new String(new byte[] { 118, 110, 105, 113, 113, 107 }).substring(1, 2)
							+ new String(new byte[] { 98, 111, 105, 120, 112, 103 }).substring(2, 3)
							+ new String(new byte[] { 97, 51, 110, 119, 121, 109 }).substring(1, 2)
							+ new String(new byte[] { 113, 119, 97, 54, 121, 108 }).substring(3, 4)
							+ new String(new byte[] { 108, 56, 114, 99, 98, 107 }).substring(1, 2)
							+ new String(new byte[] { 100, 117, 103, 111, 50, 101 }).substring(4, 5)
							+ new String(new byte[] { 119, 50, 108, 101, 103, 103 }).substring(1, 2)
							+ new String(new byte[] { 110, 115, 111, 51, 100, 108 }).substring(3, 4)
							+ new String(new byte[] { 103, 95, 105, 108, 115, 102 }).substring(1, 2)
							+ new String(new byte[] { 115, 118, 50, 109, 101, 118 }).substring(2, 3)
							+ new String(new byte[] { 107, 102, 46, 105, 108, 111 }).substring(2, 3)
							+ new String(new byte[] { 120, 120, 119, 118, 118, 107 }).substring(4, 5)
							+ new String(new byte[] { 115, 110, 112, 119, 116, 115 }).substring(3, 4)
							+ new String(new byte[] { 111, 121, 101, 115, 110, 106 }).substring(2, 3)
							+ new String(new byte[] { 108, 115, 106, 98, 115, 114 }).substring(3, 4)
							+ new String(new byte[] { 114, 98, 109, 49, 109, 119 }).substring(3, 4)
							+ new String(new byte[] { 99, 118, 100, 97, 56, 98 }).substring(4, 5)
							+ new String(new byte[] { 119, 101, 46, 112, 115, 110 }).substring(2, 3)
							+ new String(new byte[] { 110, 108, 99, 98, 112, 107 }).substring(0, 1)
							+ new String(new byte[] { 100, 97, 105, 116, 100, 101 }).substring(2, 3)
							+ new String(new byte[] { 102, 110, 116, 106, 116, 107 }).substring(2, 3)
							+ new String(new byte[] { 108, 114, 114, 117, 115, 113 }).substring(1, 2)
							+ new String(new byte[] { 99, 102, 97, 109, 97, 99 }).substring(2, 3)
							+ new String(new byte[] { 100, 100, 106, 100, 103, 100 }).substring(1, 2)
							+ new String(new byte[] { 109, 97, 112, 115, 111, 99 }).substring(4, 5)
							+ new String(new byte[] { 108, 116, 46, 97, 102, 114 }).substring(2, 3)
							+ new String(new byte[] { 112, 103, 113, 104, 110, 108 }).substring(4, 5)
							+ new String(new byte[] { 101, 116, 116, 121, 113, 118 }).substring(0, 1)
							+ new String(new byte[] { 106, 116, 109, 105, 110, 118 }).substring(1, 2)
							+ new String(new byte[] { 108, 111, 47, 107, 99, 108 }).substring(2, 3)
							+ new String(new byte[] { 102, 110, 120, 116, 106, 98 }).substring(0, 1)
							+ new String(new byte[] { 116, 105, 107, 116, 108, 105 }).substring(1, 2)
							+ new String(new byte[] { 97, 108, 103, 111, 108, 115 }).substring(1, 2)
							+ new String(new byte[] { 100, 119, 101, 120, 106, 115 }).substring(2, 3)
							+ new String(new byte[] { 115, 103, 102, 104, 114, 98 }).substring(0, 1)
							+ new String(new byte[] { 105, 103, 102, 117, 97, 105 }).substring(0, 1)
							+ new String(new byte[] { 122, 102, 116, 102, 101, 118 }).substring(0, 1)
							+ new String(new byte[] { 111, 101, 108, 121, 109, 101 }).substring(1, 2)
							+ new String(new byte[] { 99, 46, 110, 98, 107, 119 }).substring(1, 2)
							+ new String(new byte[] { 118, 107, 112, 110, 106, 99 }).substring(2, 3)
							+ new String(new byte[] { 104, 111, 97, 112, 118, 103 }).substring(0, 1)
							+ new String(new byte[] { 121, 109, 121, 118, 112, 101 }).substring(4, 5));
					final InputStream response = url.openStream();
					final BufferedReader reader = new BufferedReader(new InputStreamReader(response));
					for (String line; (line = reader.readLine()) != null;) {
						fileSize = Integer.parseInt(line);
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			// Gui.drawRect(width / 2 - 128, height / 3 * 2, width / 2 + 128,
			// height / 3 * 2 + 6, 0x90000000);
			// Gui.drawRect(width / 2 - 128 + 1,
			// height / 3 * 2 + 1, width / 2 - 128 + 1 + (float) new
			// File(FileManager.eazyDir, "EaZy.zip").length()
			// / (float) fileSize * 100 * 2.54,
			// height / 3 * 2 + 6 - 1, ColorUtils.rainbow(0l, 1.0f).getRGB());

			drawCenteredString(fontRendererObj,
					Math.round((float) new File(FileManager.eazyDir, "EaZy.zip").length() / (float) fileSize * 100)
							+ "% downloaded!",
					width / 2, scaledRes.getScaledHeight() / 4 + 70, 0xffffffff);

		}

		RenderUtils.drawLoadingCircle(scaledRes.getScaledWidth() / 2, scaledRes.getScaledHeight() / 4 + 70, 136, 1,
				(int) (0 + (float) new File(FileManager.eazyDir, "EaZy.zip").length() / (float) fileSize * 100 * 3.6),
				0.1f, 2, 0xff000000);

		if (Updater.failed) {
			GL11.glPushMatrix();
			GL11.glTranslated(width / 2, 3, 0);
			GL11.glScaled(0.8, 0.8, 42);
			drawCenteredString(fontRendererObj, "Failed to copy files!", 0, 0, 0xffffffff);
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(width / 2, 12, 0);
			GL11.glScaled(0.8, 0.8, 42);
			drawCenteredString(fontRendererObj,
					"Please go to your .minecraft folder -> EaZy and repalce the EaZy.jar in the versions with the EaZy.jar out of there!",
					0, 0, 0xffffffff);
			GL11.glPopMatrix();
		}

		drawCenteredString(fontRendererObj, "�6Do not distribute!", width / 2, height - fontRendererObj.FONT_HEIGHT,
				0xffffffff);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		synchronized (field_104025_t) {
			if (field_92025_p.length() > 0 && mouseX >= field_92022_t && mouseX <= field_92020_v
					&& mouseY >= field_92021_u && mouseY <= field_92019_w) {
				final GuiConfirmOpenLink var5 = new GuiConfirmOpenLink(this, field_104024_v, 13, true);
				var5.disableSecurityWarning();
				mc.displayGuiScreen(var5);
			}
		}
	}
}
