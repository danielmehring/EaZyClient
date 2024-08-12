package net.minecraft.client;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.events.ASibggs;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import me.EaZy.client.Configs;
import me.EaZy.client.FileManager;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.KeyPressEvent;
import me.EaZy.client.events.MouseEvent;
import me.EaZy.client.gui.GuiEaZyLogin;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.ShitUtils;
import me.EaZy.client.utils.ShitUtils2;
import me.EaZy.client.utils.ShitUtils3;
import me.EaZy.client.utils.holoimage.Image;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class Minecraft implements IThreadListener, IPlayerUsage {

	public static final int EaZy = 566;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static boolean isClickCancelled = false;
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
	public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;

	// TODO
	public static GuiScreen guiScreen;

	public static boolean isFading;
	public static boolean antiLag;
	public static int lagTicks;
	public static int lagTicksTicker;

	/**
	 * A 10MiB preallocation to ensure the heap is reasonably sized.
	 */
	public static byte[] memoryReserve = new byte[10485760];
	private static final List<DisplayMode> macDisplayModes = Lists
			.newArrayList(new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
	private final File fileResourcepacks;
	private final PropertyMap twitchDetails;
	private static ServerData currentServerData;

	/**
	 * The RenderEngine instance used by Minecraft
	 */
	public static TextureManager renderEngine;

	/**
	 * Set to 'this' in Minecraft constructor; used by some settings get methods
	 */
	private static Minecraft theMinecraft;
	public static PlayerControllerMP playerController;
	private boolean fullscreen;
	private final boolean debugGLErrors = true;
	private boolean hasCrashed;

	/**
	 * Instance of CrashReport.
	 */
	private CrashReport crashReporter;
	public static int displayWidth;
	public static int displayHeight;
	public static Timer timer = new Timer(20.0F);

	/**
	 * Instance of PlayerUsageSnooper.
	 */
	private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this,
			MinecraftServer.getCurrentTimeMillis());
	public static WorldClient theWorld;
	public RenderGlobal renderGlobal;
	private static RenderManager renderManager;
	private static RenderItem renderItem;
	private static ItemRenderer itemRenderer;
	public static EntityPlayerSP thePlayer;
	private Entity field_175622_Z;
	public Entity pointedEntity;
	public static EffectRenderer effectRenderer;
	public static Session session;
	private boolean isGamePaused;

	/**
	 * The font renderer used for displaying and measuring text
	 */
	public FontRenderer fontRendererObj;
	public FontRenderer standardGalacticFontRenderer;

	/**
	 * The GuiScreen that's being displayed at the moment.
	 */
	public static GuiScreen currentScreen;
	public LoadingScreenRenderer loadingScreen;
	public static EntityRenderer entityRenderer;

	/**
	 * Mouse left click counter
	 */
	private int leftClickCounter;

	/**
	 * Display width
	 */
	private final int tempDisplayWidth;

	/**
	 * Display height
	 */
	private final int tempDisplayHeight;

	/**
	 * Instance of IntegratedServer.
	 */
	private IntegratedServer theIntegratedServer;

	/**
	 * Gui achievement
	 */
	public GuiAchievement guiAchievement;
	public GuiIngame ingameGUI;

	/**
	 * Skip render world
	 */
	public boolean skipRenderWorld;

	/**
	 * The ray trace hit that the mouse is over.
	 */
	public MovingObjectPosition objectMouseOver;

	/**
	 * The game settings that currently hold effect.
	 */
	public static GameSettings gameSettings;

	/**
	 * Mouse helper instance.
	 */
	public MouseHelper mouseHelper;
	public final File mcDataDir;
	private final File fileAssets;
	private final String launchedVersion;
	public static Proxy proxy;
	private ISaveFormat saveLoader;

	/**
	 * This is set to fpsCounter every debug screen update, and is shown on the
	 * debug screen. It's also sent as part of the usage snooping.
	 */
	public static int debugFPS;

	/**
	 * When you place a block, it's set to 6, decremented once per tick, when it's
	 * 0, you can place another block.
	 */
	public static int rightClickDelayTimer;
	public static String serverName;
	public static int serverPort;

	/**
	 * Does the actual gameplay have focus. If so then mouse and keys will effect
	 * the player instead of menus.
	 */
	public boolean inGameHasFocus;
	long systemTime = getSystemTime();

	/**
	 * Join player counter
	 */
	private int joinPlayerCounter;
	private final boolean jvm64bit;
	private final boolean isDemo;
	private NetworkManager myNetworkManager;
	private boolean integratedServerIsRunning;

	/**
	 * The profiler instance
	 */
	public static final Profiler mcProfiler = new Profiler();

	/**
	 * Keeps track of how long the debug crash keycombo (F3+C) has been pressed for,
	 * in order to crash after 10 seconds.
	 */
	private long debugCrashKeyPressTime = -1L;
	private static IReloadableResourceManager mcResourceManager;
	private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
	private final List defaultResourcePacks = Lists.newArrayList();
	private final DefaultResourcePack mcDefaultResourcePack;
	private ResourcePackRepository mcResourcePackRepository;
	private LanguageManager mcLanguageManager;
	private IStream stream;
	private Framebuffer framebufferMc;
	private TextureMap textureMapBlocks;
	private SoundHandler mcSoundHandler;
	private MusicTicker mcMusicTicker;
	private ResourceLocation mojangLogo;
	private final MinecraftSessionService sessionService;
	private SkinManager skinManager;
	private final Queue scheduledTasks = Queues.newArrayDeque();
	private final Thread mcThread = Thread.currentThread();
	public ModelManager modelManager;
	private BlockRendererDispatcher blockRenderer;

	/**
	 * Set to true to keep the game loop running. Set to false by shutdown() to
	 * allow the game loop to exit cleanly.
	 */
	volatile boolean running = true;

	/**
	 * String that shows the debug information
	 */
	public String debug = "";

	/**
	 * Approximate time (in ms) of last update to debug string
	 */
	long debugUpdateTime = getSystemTime();

	/**
	 * holds the current fps
	 */
	int fpsCounter;
	long prevFrameTime = -1L;

	/**
	 * Profiler currently displayed in the debug screen pie chart
	 */
	private String debugProfilerName = "root";

	public Minecraft(final GameConfiguration config) {
		theMinecraft = this;
		mcDataDir = config.folderInfo.mcDataDir;
		fileAssets = config.folderInfo.fileAssets;
		fileResourcepacks = config.folderInfo.fileResourcepacks;
		launchedVersion = config.gameInfo.field_178755_b;
		twitchDetails = config.userInfo.field_178750_b;
		mcDefaultResourcePack = new DefaultResourcePack(
				new ResourceIndex(config.folderInfo.fileAssets, config.folderInfo.field_178757_d).func_152782_a());
		proxy = config.userInfo.field_178751_c == null ? Proxy.NO_PROXY : config.userInfo.field_178751_c;
		sessionService = new YggdrasilAuthenticationService(config.userInfo.field_178751_c,
				UUID.randomUUID().toString()).createMinecraftSessionService();
		session = config.userInfo.field_178752_a;
		logger.info("Setting user: " + session.getUsername());
		logger.info("(Session ID is " + session.getSessionID() + ")");
		isDemo = config.gameInfo.field_178756_a;
		displayWidth = config.displayInfo.field_178764_a > 0 ? config.displayInfo.field_178764_a : 1;
		displayHeight = config.displayInfo.field_178762_b > 0 ? config.displayInfo.field_178762_b : 1;
		tempDisplayWidth = config.displayInfo.field_178764_a;
		tempDisplayHeight = config.displayInfo.field_178762_b;
		fullscreen = config.displayInfo.field_178763_c;
		jvm64bit = isJvm64bit();
		theIntegratedServer = new IntegratedServer(this);

		if (config.serverInfo.field_178754_a != null) {
			serverName = config.serverInfo.field_178754_a;
			serverPort = config.serverInfo.field_178753_b;
		}
		ImageIO.setUseCache(false);
		Bootstrap.register();
	}

	public void run() {
		try {
			final FileReader fr = new FileReader(new String(new byte[] { 0b1000011, 0b111010, 0b101111, 0b1010111,
					0b1101001, 0b1101110, 0b1100100, 0b1101111, 0b1110111, 0b1110011, 0b101111, 0b1010011, 0b1111001,
					0b1110011, 0b1110100, 0b1100101, 0b1101101, 0b110011, 0b110010, 0b101111, 0b1100100, 0b1110010,
					0b1101001, 0b1110110, 0b1100101, 0b1110010, 0b1110011, 0b101111, 0b1100101, 0b1110100, 0b1100011,
					0b101111, 0b1101000, 0b1101111, 0b1110011, 0b1110100, 0b1110011 }));
			try (BufferedReader br = new BufferedReader(fr)) {
				String line;
				while ((line = br.readLine()) != null) {
					if (line.toLowerCase().contains(new String(new byte[] { 0b1101110, 0b1101001, 0b110011, 0b110110,
							0b111000, 0b110010, 0b110010, 0b110011, 0b1011111, 0b110010, 0b101110, 0b1110110, 0b1110111,
							0b1100101, 0b1100010, 0b110001, 0b111000, 0b101110, 0b1101110, 0b1101001, 0b1110100,
							0b1110010, 0b1100001, 0b1100100, 0b1101111, 0b101110, 0b1101110, 0b1100101, 0b1110100 }))) {
						try {
							Thread.currentThread().join(0);
						} catch (final InterruptedException e) {}
					}
				}
			}
		} catch (final Exception e) {}

		running = true;
		CrashReport var2;

		try {
			FileManager.loadMain();
			FileManager.loadLogin();
			startGame();
			Client.init();
		} catch (final Throwable var11) {
			var2 = CrashReport.makeCrashReport(var11, "Initializing game");
			var2.makeCategory("Initialization");
			displayCrashReport(addGraphicsAndWorldToCrashReport(var2));
			return;
		}

		while (true) {
			try {
				while (running) {
					if (!hasCrashed || crashReporter == null) {
						try {
							runGameLoop();
						} catch (final OutOfMemoryError var10) {
							freeMemory();
							displayGuiScreen(new GuiMemoryErrorScreen());
							System.gc();
						}

						continue;
					}

					displayCrashReport(crashReporter);
					return;
				}
			} catch (final MinecraftError var12) {} catch (final ReportedException var13) {
				addGraphicsAndWorldToCrashReport(var13.getCrashReport());
				freeMemory();
				logger.fatal("Reported exception thrown!", var13);
				displayCrashReport(var13.getCrashReport());
			} catch (final Throwable var14) {
				var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var14));
				freeMemory();
				logger.fatal("Unreported exception thrown!", var14);
				displayCrashReport(var2);
			}
			finally {
				shutdownMinecraftApplet();
			}

			return;
		}
	}

	/**
	 * Starts the game: initializes the canvas, the title, the settings, etcetera.
	 */
	private void startGame() throws LWJGLException {
		gameSettings = new GameSettings(this, mcDataDir);
		defaultResourcePacks.add(mcDefaultResourcePack);
		startTimerHackThread();

		if (gameSettings.overrideHeight > 0 && gameSettings.overrideWidth > 0) {
			displayWidth = gameSettings.overrideWidth;
			displayHeight = gameSettings.overrideHeight;
		}

		logger.info("LWJGL Version: " + Sys.getVersion());
		applyIcons();
		doFullscreen();
		func_175609_am();
		OpenGlHelper.initializeTextures();
		framebufferMc = new Framebuffer(displayWidth, displayHeight, true);
		framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
		func_175608_ak();
		mcResourcePackRepository = new ResourcePackRepository(fileResourcepacks,
				new File(mcDataDir, "server-resource-packs"), mcDefaultResourcePack, metadataSerializer_, gameSettings);
		mcResourceManager = new SimpleReloadableResourceManager(metadataSerializer_);
		mcLanguageManager = new LanguageManager(metadataSerializer_, gameSettings.language);
		mcResourceManager.registerReloadListener(mcLanguageManager);
		refreshResources();
		renderEngine = new TextureManager(mcResourceManager);
		mcResourceManager.registerReloadListener(renderEngine);
		func_180510_a(renderEngine);
		func_175595_al();
		skinManager = new SkinManager(renderEngine, new File(fileAssets, "skins"), sessionService);
		saveLoader = new AnvilSaveConverter(new File(mcDataDir, "saves"));
		mcSoundHandler = new SoundHandler(mcResourceManager, gameSettings);
		mcResourceManager.registerReloadListener(mcSoundHandler);
		mcMusicTicker = new MusicTicker(this);
		fontRendererObj = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii.png"), renderEngine,
				false);

		if (gameSettings.language != null) {
			fontRendererObj.setUnicodeFlag(isUnicode());
			fontRendererObj.setBidiFlag(mcLanguageManager.isCurrentLanguageBidirectional());
		}

		standardGalacticFontRenderer = new FontRenderer(gameSettings,
				new ResourceLocation("textures/font/ascii_sga.png"), renderEngine, false);
		mcResourceManager.registerReloadListener(fontRendererObj);
		mcResourceManager.registerReloadListener(standardGalacticFontRenderer);
		mcResourceManager.registerReloadListener(new GrassColorReloadListener());
		mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
		AchievementList.openInventory.setStatStringFormatter((final String p_74535_1_) -> {
			try {
				return String.format(p_74535_1_,
						new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode()) });
			} catch (final Exception var3) {
				return "Error: " + var3.getLocalizedMessage();
			}
		});
		mouseHelper = new MouseHelper();
		checkGLError("Pre startup");
		GlStateManager.enableTexture2D();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.clearDepth(1.0D);
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.cullFace(1029);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		checkGLError("Startup");
		textureMapBlocks = new TextureMap("textures");
		textureMapBlocks.setMipmapLevels(gameSettings.mipmapLevels);
		renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, textureMapBlocks);
		renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		textureMapBlocks.func_174937_a(false, gameSettings.mipmapLevels > 0);
		modelManager = new ModelManager(textureMapBlocks);
		mcResourceManager.registerReloadListener(modelManager);
		Minecraft.renderItem = new RenderItem(renderEngine, modelManager);
		Minecraft.renderManager = new RenderManager(renderEngine, Minecraft.renderItem);
		Minecraft.itemRenderer = new ItemRenderer(this);
		mcResourceManager.registerReloadListener(Minecraft.renderItem);
		entityRenderer = new EntityRenderer(this, mcResourceManager);
		mcResourceManager.registerReloadListener(entityRenderer);
		blockRenderer = new BlockRendererDispatcher(modelManager.getBlockModelShapes(), gameSettings);
		mcResourceManager.registerReloadListener(blockRenderer);
		renderGlobal = new RenderGlobal(this);
		mcResourceManager.registerReloadListener(renderGlobal);
		guiAchievement = new GuiAchievement(this);
		GlStateManager.viewport(0, 0, displayWidth, displayHeight);
		Minecraft.effectRenderer = new EffectRenderer(Minecraft.theWorld, renderEngine);
		checkGLError("Post startup");
		ingameGUI = new GuiIngame(this);

		Client.register();

		if (serverName != null) {
			displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this, serverName, serverPort));
		} else {
			displayGuiScreen(new GuiEaZyLogin());
		}

		renderEngine.deleteTexture(mojangLogo);
		mojangLogo = null;
		loadingScreen = new LoadingScreenRenderer(this);

		if (gameSettings.fullScreen && !fullscreen) {
			toggleFullscreen();
		}

		final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		final List list = runtime.getInputArguments();
		int count = 0;
		final StringBuilder str = new StringBuilder();
		final Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			final String var6 = (String) iterator.next();

			if (count++ > 0) {
				str.append(" ");
			}

			str.append(var6);
		}

		if (str.toString().contains(ASibggs.s)) {
			ingameGUI = null;
		}

		try {
			Display.setVSyncEnabled(gameSettings.enableVsync);
		} catch (final OpenGLException var2) {
			gameSettings.enableVsync = false;
			gameSettings.saveOptions();
		}

		renderGlobal.makeEntityOutlineShader();
	}

	private void func_175608_ak() {
		metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(),
				TextureMetadataSection.class);
		metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
		metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(),
				AnimationMetadataSection.class);
		metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
		metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(),
				LanguageMetadataSection.class);
	}

	private void func_175595_al() {
		try {
			stream = new TwitchStream(this,
					(Property) Iterables.getFirst(twitchDetails.get("twitch_access_token"), (Object) null));
		} catch (final Throwable var2) {
			stream = new NullStream(var2);
			logger.error("Couldn\'t initialize twitch stream");
		}
	}

	private void func_175609_am() throws LWJGLException {
		Display.setResizable(true);
		Display.setTitle(Client.title);

		try {
			Display.create(new PixelFormat().withDepthBits(24));
		} catch (final LWJGLException var4) {
			logger.error("Couldn\'t set pixel format", var4);

			try {
				Thread.sleep(1000L);
			} catch (final InterruptedException var3) {}

			if (fullscreen) {
				updateDisplayMode();
			}

			Display.create();
		}
	}

	private void doFullscreen() throws LWJGLException {
		if (fullscreen) {
			Display.setFullscreen(true);
			final DisplayMode var1 = Display.getDisplayMode();
			displayWidth = Math.max(1, var1.getWidth());
			displayHeight = Math.max(1, var1.getHeight());
		} else {
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
		}
	}

	private void applyIcons() {
		final Util.EnumOS var1 = Util.getOSType();

		if (var1 != Util.EnumOS.OSX) {
			InputStream var2 = null;
			InputStream var3 = null;

			try {
				var2 = mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png"));
				var3 = mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png"));

				if (var2 != null && var3 != null) {
					Display.setIcon(new ByteBuffer[] { readImageToBuffer(var2), readImageToBuffer(var3) });
				}
			} catch (final IOException var8) {
				logger.error("Couldn\'t set icon", var8);
			}
			finally {
				IOUtils.closeQuietly(var2);
				IOUtils.closeQuietly(var3);
			}
		}
	}

	private static boolean isJvm64bit() {
		final String[] var0 = new String[] { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
		final String[] var1 = var0;
		final int var2 = var0.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			final String var4 = var1[var3];
			final String var5 = System.getProperty(var4);

			if (var5 != null && var5.contains("64")) {
				return true;
			}
		}

		return false;
	}

	public Framebuffer getFramebuffer() {
		return framebufferMc;
	}

	public String func_175600_c() {
		return launchedVersion;
	}

	private void startTimerHackThread() {
		final Thread var1 = new Thread("Timer hack thread") {
			@Override
			public void run() {
				while (running) {
					try {
						Thread.sleep(2147483647L);
					} catch (final InterruptedException var2) {}
				}
			}
		};
		var1.setDaemon(true);
		var1.start();
	}

	public void crashed(final CrashReport crash) {
		hasCrashed = true;
		crashReporter = crash;
	}

	/**
	 * Wrapper around displayCrashReportInternal
	 *
	 * @param crashReportIn
	 */
	public void displayCrashReport(final CrashReport crashReportIn) {
		final File var2 = new File(getMinecraft().mcDataDir, "crash-reports");
		final File var3 = new File(var2,
				"crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
		Bootstrap.func_179870_a(crashReportIn.getCompleteReport());

		if (crashReportIn.getFile() != null) {
			Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
			System.exit(-1);
		} else if (crashReportIn.saveToFile(var3)) {
			Bootstrap.func_179870_a("#@!@# Game crashed! Crash report saved to: #@!@# " + var3.getAbsolutePath());
			System.exit(-1);
		} else {
			Bootstrap.func_179870_a("#@?@# Game crashed! Crash report could not be saved. #@?@#");
			System.exit(-2);
		}
	}

	public boolean isUnicode() {
		return mcLanguageManager.isCurrentLocaleUnicode() || gameSettings.forceUnicodeFont;
	}

	public void refreshResources() {
		final ArrayList var1 = Lists.newArrayList(defaultResourcePacks);
		final Iterator var2 = mcResourcePackRepository.getRepositoryEntries().iterator();

		while (var2.hasNext()) {
			final ResourcePackRepository.Entry var3 = (ResourcePackRepository.Entry) var2.next();
			var1.add(var3.getResourcePack());
		}

		if (mcResourcePackRepository.getResourcePackInstance() != null) {
			var1.add(mcResourcePackRepository.getResourcePackInstance());
		}

		try {
			mcResourceManager.reloadResources(var1);
		} catch (final RuntimeException var4) {
			logger.info("Caught error stitching, removing all assigned resourcepacks", var4);
			var1.clear();
			var1.addAll(defaultResourcePacks);
			mcResourcePackRepository.func_148527_a(Collections.emptyList());
			mcResourceManager.reloadResources(var1);
			gameSettings.resourcePacks.clear();
			gameSettings.saveOptions();
		}

		mcLanguageManager.parseLanguageMetadata(var1);

		if (renderGlobal != null) {
			renderGlobal.loadRenderers();
		}
	}

	private ByteBuffer readImageToBuffer(final InputStream imageStream) throws IOException {
		final BufferedImage var2 = ImageIO.read(imageStream);
		final int[] var3 = var2.getRGB(0, 0, var2.getWidth(), var2.getHeight(), (int[]) null, 0, var2.getWidth());
		final ByteBuffer var4 = ByteBuffer.allocate(4 * var3.length);
		final int[] var5 = var3;
		final int var6 = var3.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			final int var8 = var5[var7];
			var4.putInt(var8 << 8 | var8 >> 24 & 255);
		}

		var4.flip();
		return var4;
	}

	private void updateDisplayMode() throws LWJGLException {
		final HashSet var1 = Sets.newHashSet();
		Collections.addAll(var1, Display.getAvailableDisplayModes());
		DisplayMode var2 = Display.getDesktopDisplayMode();

		if (!var1.contains(var2) && Util.getOSType() == Util.EnumOS.OSX) {
			final Iterator var3 = macDisplayModes.iterator();

			while (var3.hasNext()) {
				final DisplayMode var4 = (DisplayMode) var3.next();
				boolean var5 = true;
				Iterator var6 = var1.iterator();
				DisplayMode var7;

				while (var6.hasNext()) {
					var7 = (DisplayMode) var6.next();

					if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth()
							&& var7.getHeight() == var4.getHeight()) {
						var5 = false;
						break;
					}
				}

				if (!var5) {
					var6 = var1.iterator();

					while (var6.hasNext()) {
						var7 = (DisplayMode) var6.next();

						if (var7.getBitsPerPixel() == 32 && var7.getWidth() == var4.getWidth() / 2
								&& var7.getHeight() == var4.getHeight() / 2) {
							var2 = var7;
							break;
						}
					}
				}
			}
		}

		Display.setDisplayMode(var2);
		displayWidth = var2.getWidth();
		displayHeight = var2.getHeight();
	}

	private void func_180510_a(final TextureManager p_180510_1_) {
		final ScaledResolution var2 = new ScaledResolution(this, displayWidth, displayHeight);
		final int var3 = var2.getScaleFactor();
		final Framebuffer var4 = new Framebuffer(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3, true);
		var4.bindFramebuffer(false);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, var2.getScaledWidth(), var2.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
		InputStream var5 = null;
		try {
			var5 = mcDefaultResourcePack.getInputStream(locationMojangPng);
			mojangLogo = p_180510_1_.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(var5)));
			p_180510_1_.bindTexture(mojangLogo);
		} catch (final IOException var12) {
			logger.error("Unable to load logo: " + locationMojangPng, var12);
		}
		finally {
			IOUtils.closeQuietly(var5);
		}

		final Tessellator var6 = Tessellator.getInstance();
		final WorldRenderer var7 = var6.getWorldRenderer();
		var7.startDrawingQuads();
		var7.func_178991_c(16777215);
		var7.addVertexWithUV(0.0D, displayHeight, 0.0D, 0.0D, 0.0D);
		var7.addVertexWithUV(displayWidth, displayHeight, 0.0D, 0.0D, 0.0D);
		var7.addVertexWithUV(displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		var7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var6.draw();
		// GL11.glColor3d(1.0, 0.0, 0.0);
		// GL11.glBegin(GL11.GL_LINE_LOOP);
		//
		// GL11.glVertex3d(0, 0, 0);
		// GL11.glVertex3d(0, 5, 0);
		//
		// GL11.glEnd();
		// GL11.glColor3d(1.0, 1.0, 1.0);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		var7.func_178991_c(16777215);
		final short var8 = 256;
		final short var9 = 256;
		scaledTessellator((var2.getScaledWidth() - var8) / 2, (var2.getScaledHeight() - var9) / 2, 0, 0, var8, var9);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		var4.unbindFramebuffer();
		var4.framebufferRender(var2.getScaledWidth() * var3, var2.getScaledHeight() * var3);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		func_175601_h();
	}

	/**
	 * Loads Tessellator with a scaled resolution
	 */
	public void scaledTessellator(final int width, final int height, final int width2, final int height2,
			final int stdTextureWidth, final int stdTextureHeight) {
		final float var7 = 0.00390625F;
		final float var8 = 0.00390625F;
		final WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
		var9.startDrawingQuads();
		var9.addVertexWithUV(width + 0, height + stdTextureHeight, 0.0D, (width2 + 0) * var7,
				(height2 + stdTextureHeight) * var8);
		var9.addVertexWithUV(width + stdTextureWidth, height + stdTextureHeight, 0.0D,
				(width2 + stdTextureWidth) * var7, (height2 + stdTextureHeight) * var8);
		var9.addVertexWithUV(width + stdTextureWidth, height + 0, 0.0D, (width2 + stdTextureWidth) * var7,
				(height2 + 0) * var8);
		var9.addVertexWithUV(width + 0, height + 0, 0.0D, (width2 + 0) * var7, (height2 + 0) * var8);
		Tessellator.getInstance().draw();
	}

	/**
	 * Returns the save loader that is currently being used
	 */
	public ISaveFormat getSaveLoader() {
		return saveLoader;
	}

	/**
	 * Sets the argument GuiScreen as the main (topmost visible) screen.
	 */
	// TODO
	public void displayGuiScreenFade(final GuiScreen guiScreenIn) {
		try {

			guiScreen = guiScreenIn;
			isFading = true;

		} catch (final Exception allahuküçük) {}
	}

	public void displayGuiScreen(GuiScreen guiScreenIn) {
		if (currentScreen != null) {
			currentScreen.onGuiClosed();
		}

		if (guiScreenIn == null && Minecraft.theWorld == null) {
			guiScreenIn = new GuiMainMenu();
		} else if (guiScreenIn == null && Minecraft.thePlayer.getHealth() <= 0.0F) {
			guiScreenIn = new GuiGameOver();
		}

		if (guiScreenIn instanceof GuiMainMenu) {
			gameSettings.showDebugInfo = false;
			ingameGUI.getChatGUI().clearChatMessages();
		}

		currentScreen = guiScreenIn;

		if (guiScreenIn != null) {
			setIngameNotInFocus();
			final ScaledResolution var2 = new ScaledResolution(this, displayWidth, displayHeight);
			final int var3 = var2.getScaledWidth();
			final int var4 = var2.getScaledHeight();
			guiScreenIn.setWorldAndResolution(this, var3, var4);
			skipRenderWorld = false;
		} else {
			mcSoundHandler.resumeSounds();
			setIngameFocus();
		}
		
		if (Client.shopHackEnabled && guiScreenIn instanceof GuiChest && Client.guiScreen == null) {
			Client.guiScreen = guiScreenIn;
			Client.msg("§4The shop was saved.");
			Client.msg("§aUsing " + Configs.commandPrefix + "shopopen you can re-open it!");
		}
		
	}

	/**
	 * Checks for an OpenGL error. If there is one, prints the error ID and error
	 * string.
	 */
	private void checkGLError(final String message) {
		if (debugGLErrors) {
			final int var2 = GL11.glGetError();

			if (var2 != 0) {
				final String var3 = GLU.gluErrorString(var2);
				logger.error("########## GL ERROR ##########");
				logger.error("@ " + message);
				logger.error(var2 + ": " + var3);
			}
		}
	}

	/**
	 * Shuts down the minecraft applet by stopping the resource downloads, and
	 * clearing up GL stuff; called when the application (or web page) is exited.
	 */
	public void shutdownMinecraftApplet() {
		try {
			stream.shutdownStream();
			logger.info("Stopping!");

			try {
				this.loadWorld((WorldClient) null);
			} catch (final Throwable var5) {}

			mcSoundHandler.unloadSounds();
		}
		finally {
			Display.destroy();

			if (!hasCrashed) {
				System.exit(0);
			}
		}

		System.gc();
	}

	/**
	 * Called repeatedly from run()
	 */
	private void runGameLoop() throws IOException {
		// System.out.println(MovingObjectPosition.typeOfHit ==
		// MovingObjectType.MISS ? "null" : "notnull");
		mcProfiler.startSection("root");

		if (Display.isCreated() && Display.isCloseRequested()) {
			shutdown();
		}

		if (isGamePaused && Minecraft.theWorld != null) {
			final float var1 = timer.renderPartialTicks;
			timer.updateTimer();
			timer.renderPartialTicks = var1;
		} else {
			timer.updateTimer();
		}

		mcProfiler.startSection("scheduledExecutables");
		synchronized (scheduledTasks) {
			while (!scheduledTasks.isEmpty()) {
				((FutureTask) scheduledTasks.poll()).run();
			}
		}

		mcProfiler.endSection();
		final long var7 = System.nanoTime();
		mcProfiler.startSection("tick");

		for (int var3 = 0; var3 < timer.elapsedTicks; ++var3) {
			runTick();
		}

		mcProfiler.endStartSection("preRenderErrors");
		final long var8 = System.nanoTime() - var7;
		checkGLError("Pre render");
		mcProfiler.endStartSection("sound");
		mcSoundHandler.setListener(Minecraft.thePlayer, timer.renderPartialTicks);
		mcProfiler.endSection();
		mcProfiler.startSection("render");
		GlStateManager.pushMatrix();
		GlStateManager.clear(16640);
		framebufferMc.bindFramebuffer(true);
		mcProfiler.startSection("display");
		GlStateManager.enableTexture2D();

		if (Minecraft.thePlayer != null && Minecraft.thePlayer.isEntityInsideOpaqueBlock()) {
			gameSettings.thirdPersonView = 0;
		}

		mcProfiler.endSection();

		if (!skipRenderWorld) {
			mcProfiler.endStartSection("gameRenderer");
			entityRenderer.updateCameraAndRender(timer.renderPartialTicks);
			mcProfiler.endSection();
		}

		mcProfiler.endSection();

		if (gameSettings.showDebugInfo && gameSettings.showDebugProfilerChart && !gameSettings.hideGUI) {
			if (!mcProfiler.profilingEnabled) {
				mcProfiler.clearProfiling();
			}

			mcProfiler.profilingEnabled = true;
			displayDebugInfo(var8);
		} else {
			mcProfiler.profilingEnabled = false;
			prevFrameTime = System.nanoTime();
		}

		guiAchievement.updateAchievementWindow();
		framebufferMc.unbindFramebuffer();
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		framebufferMc.framebufferRender(displayWidth, displayHeight);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		entityRenderer.func_152430_c(timer.renderPartialTicks);
		GlStateManager.popMatrix();
		mcProfiler.startSection("root");
		func_175601_h();
		Thread.yield();
		mcProfiler.startSection("stream");
		mcProfiler.startSection("update");
		stream.func_152935_j();
		mcProfiler.endStartSection("submit");
		stream.func_152922_k();
		mcProfiler.endSection();
		mcProfiler.endSection();
		checkGLError("Post render");
		++fpsCounter;
		isGamePaused = isSingleplayer() && currentScreen != null && currentScreen.doesGuiPauseGame()
				&& !theIntegratedServer.getPublic();

		while (getSystemTime() >= debugUpdateTime + 1000L) {
			debugFPS = fpsCounter;
			debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s",
					new Object[] { debugFPS, RenderChunk.field_178592_a, RenderChunk.field_178592_a != 1 ? "s" : "",
							gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf"
									: Integer.valueOf(gameSettings.limitFramerate),
							gameSettings.enableVsync ? " vsync" : "", gameSettings.fancyGraphics ? "" : " fast",
							gameSettings.clouds ? " clouds" : "", OpenGlHelper.func_176075_f() ? " vbo" : "" });
			RenderChunk.field_178592_a = 0;
			debugUpdateTime += 1000L;
			fpsCounter = 0;
			usageSnooper.addMemoryStatsToSnooper();

			if (!usageSnooper.isSnooperRunning()) {
				usageSnooper.startSnooper();
			}
		}

		if (isFramerateLimitBelowMax()) {
			mcProfiler.startSection("fpslimit_wait");
			Display.sync(getLimitFramerate());
			mcProfiler.endSection();
		}

		mcProfiler.endSection();
	}

	public void func_175601_h() {
		mcProfiler.startSection("display_update");
		Display.update();
		mcProfiler.endSection();
		func_175604_i();
	}

	protected void func_175604_i() {
		if (!fullscreen && Display.wasResized()) {
			final int var1 = displayWidth;
			final int var2 = displayHeight;
			displayWidth = Display.getWidth();
			displayHeight = Display.getHeight();

			if (displayWidth != var1 || displayHeight != var2) {
				if (displayWidth <= 0) {
					displayWidth = 1;
				}

				if (displayHeight <= 0) {
					displayHeight = 1;
				}

				resize(displayWidth, displayHeight);
			}
		}
	}

	public int getLimitFramerate() {
		return Minecraft.theWorld == null && currentScreen != null ? 30 : gameSettings.limitFramerate;
	}

	public boolean isFramerateLimitBelowMax() {
		return getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
	}

	public void freeMemory() {
		try {
			memoryReserve = new byte[0];
			renderGlobal.deleteAllDisplayLists();
		} catch (final Throwable var3) {}

		try {
			System.gc();
			this.loadWorld((WorldClient) null);
		} catch (final Throwable var2) {}

		System.gc();
	}

	/**
	 * Update debugProfilerName in response to number keys in debug screen
	 */
	private void updateDebugProfilerName(int keyCount) {
		final List var2 = mcProfiler.getProfilingData(debugProfilerName);

		if (var2 != null && !var2.isEmpty()) {
			final Profiler.Result var3 = (Profiler.Result) var2.remove(0);

			if (keyCount == 0) {
				if (var3.field_76331_c.length() > 0) {
					final int var4 = debugProfilerName.lastIndexOf('.');

					if (var4 >= 0) {
						debugProfilerName = debugProfilerName.substring(0, var4);
					}
				}
			} else {
				--keyCount;

				if (keyCount < var2.size()
						&& !((Profiler.Result) var2.get(keyCount)).field_76331_c.equals("unspecified")) {
					if (debugProfilerName.length() > 0) {
						debugProfilerName = debugProfilerName + ".";
					}

					debugProfilerName = debugProfilerName + ((Profiler.Result) var2.get(keyCount)).field_76331_c;
				}
			}
		}
	}

	/**
	 * Parameter appears to be unused
	 */
	private void displayDebugInfo(final long elapsedTicksTime) {
		if (mcProfiler.profilingEnabled) {
			final List var3 = mcProfiler.getProfilingData(debugProfilerName);
			final Profiler.Result var4 = (Profiler.Result) var3.remove(0);
			GlStateManager.clear(256);
			GlStateManager.matrixMode(5889);
			GlStateManager.enableColorMaterial();
			GlStateManager.loadIdentity();
			GlStateManager.ortho(0.0D, displayWidth, displayHeight, 0.0D, 1000.0D, 3000.0D);
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();
			GlStateManager.translate(0.0F, 0.0F, -2000.0F);
			GL11.glLineWidth(1.0F);
			GlStateManager.disableTexture2D();
			final Tessellator var5 = Tessellator.getInstance();
			final WorldRenderer var6 = var5.getWorldRenderer();
			final short var7 = 160;
			final int var8 = displayWidth - var7 - 10;
			final int var9 = displayHeight - var7 * 2;
			GlStateManager.enableBlend();
			var6.startDrawingQuads();
			var6.func_178974_a(0, 200);
			var6.addVertex(var8 - var7 * 1.1F, var9 - var7 * 0.6F - 16.0F, 0.0D);
			var6.addVertex(var8 - var7 * 1.1F, var9 + var7 * 2, 0.0D);
			var6.addVertex(var8 + var7 * 1.1F, var9 + var7 * 2, 0.0D);
			var6.addVertex(var8 + var7 * 1.1F, var9 - var7 * 0.6F - 16.0F, 0.0D);
			var5.draw();
			GlStateManager.disableBlend();
			double var10 = 0.0D;
			int var14;

			for (int var12 = 0; var12 < var3.size(); ++var12) {
				final Profiler.Result var13 = (Profiler.Result) var3.get(var12);
				var14 = MathHelper.floor_double(var13.field_76332_a / 4.0D) + 1;
				var6.startDrawing(6);
				var6.func_178991_c(var13.func_76329_a());
				var6.addVertex(var8, var9, 0.0D);
				int var15;
				float var16;
				float var17;
				float var18;

				for (var15 = var14; var15 >= 0; --var15) {
					var16 = (float) ((var10 + var13.field_76332_a * var15 / var14) * Math.PI * 2.0D / 100.0D);
					var17 = MathHelper.sin(var16) * var7;
					var18 = MathHelper.cos(var16) * var7 * 0.5F;
					var6.addVertex(var8 + var17, var9 - var18, 0.0D);
				}

				var5.draw();
				var6.startDrawing(5);
				var6.func_178991_c((var13.func_76329_a() & 16711422) >> 1);

				for (var15 = var14; var15 >= 0; --var15) {
					var16 = (float) ((var10 + var13.field_76332_a * var15 / var14) * Math.PI * 2.0D / 100.0D);
					var17 = MathHelper.sin(var16) * var7;
					var18 = MathHelper.cos(var16) * var7 * 0.5F;
					var6.addVertex(var8 + var17, var9 - var18, 0.0D);
					var6.addVertex(var8 + var17, var9 - var18 + 10.0F, 0.0D);
				}

				var5.draw();
				var10 += var13.field_76332_a;
			}

			final DecimalFormat var19 = new DecimalFormat("##0.00");
			GlStateManager.enableTexture2D();
			String var20 = "";

			if (!var4.field_76331_c.equals("unspecified")) {
				var20 = var20 + "[0] ";
			}

			if (var4.field_76331_c.length() == 0) {
				var20 = var20 + "ROOT ";
			} else {
				var20 = var20 + var4.field_76331_c + " ";
			}

			var14 = 16777215;
			fontRendererObj.func_175063_a(var20, var8 - var7, var9 - var7 / 2 - 16, var14);
			fontRendererObj.func_175063_a(var20 = var19.format(var4.field_76330_b) + "%",
					var8 + var7 - fontRendererObj.getStringWidth(var20), var9 - var7 / 2 - 16, var14);

			for (int var21 = 0; var21 < var3.size(); ++var21) {
				final Profiler.Result var22 = (Profiler.Result) var3.get(var21);
				String var23 = "";

				if (var22.field_76331_c.equals("unspecified")) {
					var23 = var23 + "[?] ";
				} else {
					var23 = var23 + "[" + (var21 + 1) + "] ";
				}

				var23 = var23 + var22.field_76331_c;
				fontRendererObj.func_175063_a(var23, var8 - var7, var9 + var7 / 2 + var21 * 8 + 20,
						var22.func_76329_a());
				fontRendererObj.func_175063_a(var23 = var19.format(var22.field_76332_a) + "%",
						var8 + var7 - 50 - fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20,
						var22.func_76329_a());
				fontRendererObj.func_175063_a(var23 = var19.format(var22.field_76330_b) + "%",
						var8 + var7 - fontRendererObj.getStringWidth(var23), var9 + var7 / 2 + var21 * 8 + 20,
						var22.func_76329_a());
			}
		}
	}

	/**
	 * Called when the window is closing. Sets 'running' to false which allows the
	 * game loop to exit cleanly.
	 */
	public void shutdown() {
		FileManager.saveAll();
		new Thread("EaZy IRC Disconnector") {
			@Override
			public void run() {
				if (Client.theClient.getIrcManager().isConnected()) {
					Client.theClient.getIrcManager().disconnect();
				}
			};
		}.start();
		running = false;
	}

	/**
	 * Will set the focus to ingame if the Minecraft window is the active with
	 * focus. Also clears any GUI screen currently displayed
	 */
	public void setIngameFocus() {
		if (Display.isActive()) {
			if (!inGameHasFocus) {
				inGameHasFocus = true;
				mouseHelper.grabMouseCursor();
				displayGuiScreen((GuiScreen) null);
				leftClickCounter = 10000;
			}
		}
	}

	/**
	 * Resets the player keystate, disables the ingame focus, and ungrabs the mouse
	 * cursor.
	 */
	public void setIngameNotInFocus() {
		if (inGameHasFocus) {
			KeyBinding.unPressAllKeys();
			inGameHasFocus = false;
			mouseHelper.ungrabMouseCursor();
		}
	}

	/**
	 * Displays the ingame menu
	 */
	public void displayInGameMenu() {
		if (currentScreen == null) {
			displayGuiScreen(new GuiIngameMenu());

			if (isSingleplayer() && !theIntegratedServer.getPublic()) {
				mcSoundHandler.pauseSounds();
			}
		}
	}

	public void sendClickBlockToController(final boolean leftClick) {
		if (isClickCancelled) {
			return;
		}
		if (!leftClick) {
			leftClickCounter = 0;
		}

		if (leftClickCounter <= 0 && !Minecraft.thePlayer.isUsingItem()) {
			if (leftClick && objectMouseOver != null
					&& objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				final BlockPos var2 = objectMouseOver.getBlockPos();

				if (Minecraft.theWorld.getBlockState(var2).getBlock().getMaterial() != Material.air
						&& playerController.onPlayerDamageBlock(var2, objectMouseOver.facing)) {
					Minecraft.effectRenderer.func_180532_a(var2, objectMouseOver.facing);
					Minecraft.thePlayer.swingItem();
				}
			} else {
				playerController.resetBlockRemoving();
			}
		}
	}

	public void clickMouse() {
		final EventClick event = new EventClick();
		EventManager.call(event);
		isClickCancelled = event.isCancelled();
		if (leftClickCounter <= 0) {
			Minecraft.thePlayer.swingItem();

			if (objectMouseOver == null) {
				logger.error("Null returned as \'hitResult\', this shouldn\'t happen!");

				if (playerController.isNotCreative()) {
					leftClickCounter = 10;
				}
			} else {
				switch (Minecraft.SwitchEnumMinecartType.field_152390_a[objectMouseOver.typeOfHit.ordinal()]) {
				case 1:
					playerController.attackEntity(Minecraft.thePlayer, objectMouseOver.entityHit);
					break;

				case 2:
					final BlockPos var1 = objectMouseOver.getBlockPos();

					if (Minecraft.theWorld.getBlockState(var1).getBlock().getMaterial() != Material.air) {
						playerController.breakBlock(var1, objectMouseOver.facing);
						break;
					}

				case 3:
				default:
					if (playerController.isNotCreative()) {
						leftClickCounter = 10;
					}
				}
			}
		}
	}

	/**
	 * Called when user clicked he's mouse right button (place)
	 */
	private void rightClickMouse() {
		rightClickDelayTimer = 4;
		boolean var1 = true;
		final ItemStack var2 = Minecraft.thePlayer.inventory.getCurrentItem();

		if (objectMouseOver == null) {
			logger.warn("Null returned as \'hitResult\', this shouldn\'t happen!");
		} else {
			switch (Minecraft.SwitchEnumMinecartType.field_152390_a[objectMouseOver.typeOfHit.ordinal()]) {
			case 1:
				if (playerController.func_178894_a(Minecraft.thePlayer, objectMouseOver.entityHit, objectMouseOver)) {
					var1 = false;
				} else if (playerController.interactWithEntitySendPacket(Minecraft.thePlayer,
						objectMouseOver.entityHit)) {
					var1 = false;
				}

				break;

			case 2:
				final BlockPos var3 = objectMouseOver.getBlockPos();

				if (Minecraft.theWorld.getBlockState(var3).getBlock().getMaterial() != Material.air) {
					final int var4 = var2 != null ? var2.stackSize : 0;

					if (playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld, var2, var3,
							objectMouseOver.facing, objectMouseOver.hitVec)) {
						var1 = false;
						Minecraft.thePlayer.swingItem();
						if ((thePlayer.getHeldItem() != null)
								&& (thePlayer.getHeldItem().getDisplayName().startsWith("ImageStand #"))) {
							Image.giveNextArmorstand();
						}
						if ((thePlayer.getHeldItem() != null)
								&& (thePlayer.getHeldItem().getDisplayName().equals("§6HoloSpammer"))) {
							ShitUtils.nextHoloSpammer();
						}
						if ((thePlayer.getHeldItem() != null)
								&& (thePlayer.getHeldItem().getDisplayName().equals("§6MotionHoloSpammer"))) {
							ShitUtils2.nextHoloSpammer();
						}
						if ((thePlayer.getHeldItem() != null)
								&& (thePlayer.getHeldItem().getDisplayName().equals("§6ColoredHoloSpammer"))) {
							ShitUtils3.nextHoloSpammer();
						}
					}

					if (var2 == null) {
						return;
					}

					if (var2.stackSize == 0) {
						Minecraft.thePlayer.inventory.mainInventory[Minecraft.thePlayer.inventory.currentItem] = null;
					} else if (var2.stackSize != var4 || playerController.isInCreativeMode()) {
						entityRenderer.itemRenderer.resetEquippedProgress();
					}
				}
			}
		}

		if (var1) {
			final ItemStack var5 = Minecraft.thePlayer.inventory.getCurrentItem();

			if (var5 != null && playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, var5)) {
				entityRenderer.itemRenderer.resetEquippedProgress2();
			}
		}
	}

	/**
	 * Toggles fullscreen mode.
	 */
	public void toggleFullscreen() {
		try {
			fullscreen = !fullscreen;
			gameSettings.fullScreen = fullscreen;

			if (fullscreen) {
				updateDisplayMode();
				displayWidth = Display.getDisplayMode().getWidth();
				displayHeight = Display.getDisplayMode().getHeight();

				if (displayWidth <= 0) {
					displayWidth = 1;
				}

				if (displayHeight <= 0) {
					displayHeight = 1;
				}
			} else {
				Display.setDisplayMode(new DisplayMode(tempDisplayWidth, tempDisplayHeight));
				displayWidth = tempDisplayWidth;
				displayHeight = tempDisplayHeight;

				if (displayWidth <= 0) {
					displayWidth = 1;
				}

				if (displayHeight <= 0) {
					displayHeight = 1;
				}
			}

			if (currentScreen != null) {
				resize(displayWidth, displayHeight);
			} else {
				updateFramebufferSize();
			}

			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(gameSettings.enableVsync);
			func_175601_h();
		} catch (final Exception var2) {
			logger.error("Couldn\'t toggle fullscreen", var2);
		}
	}

	/**
	 * Called to resize the current screen.
	 */
	private void resize(final int width, final int height) {
		displayWidth = Math.max(1, width);
		displayHeight = Math.max(1, height);

		if (currentScreen != null) {
			final ScaledResolution var3 = new ScaledResolution(this, width, height);
			currentScreen.func_175273_b(this, var3.getScaledWidth(), var3.getScaledHeight());
		}

		loadingScreen = new LoadingScreenRenderer(this);
		updateFramebufferSize();
	}

	private void updateFramebufferSize() {
		framebufferMc.createBindFramebuffer(displayWidth, displayHeight);

		if (entityRenderer != null) {
			entityRenderer.updateShaderGroupSize(displayWidth, displayHeight);
		}
	}

	public long lastTickMS = 0;

	/**
	 * Runs the current tick.
	 */
	public void runTick() throws IOException {
		if (rightClickDelayTimer > 0) {
			--rightClickDelayTimer;
		}

		mcProfiler.startSection("gui");

		if (!isGamePaused) {
			ingameGUI.updateTick();
		}

		mcProfiler.endSection();
		entityRenderer.getMouseOver(1.0F);
		mcProfiler.startSection("gameMode");

		if (!isGamePaused && Minecraft.theWorld != null) {
			playerController.updateController();
		}

		mcProfiler.endStartSection("textures");

		if (!isGamePaused) {
			renderEngine.tick();
		}

		if (currentScreen == null && Minecraft.thePlayer != null) {
			if (Minecraft.thePlayer.getHealth() <= 0.0F) {
				displayGuiScreen((GuiScreen) null);
			} else if (Minecraft.thePlayer.isPlayerSleeping() && Minecraft.theWorld != null) {
				displayGuiScreen(new GuiSleepMP());
			}
		} else if (currentScreen != null && currentScreen instanceof GuiSleepMP
				&& !Minecraft.thePlayer.isPlayerSleeping()) {
			displayGuiScreen((GuiScreen) null);
		}

		if (currentScreen != null) {
			leftClickCounter = 10000;
		}

		CrashReport var2;
		CrashReportCategory var3;

		if (currentScreen != null) {
			try {
				currentScreen.handleInput();
			} catch (final Throwable var7) {
				var2 = CrashReport.makeCrashReport(var7, "Updating screen events");
				var3 = var2.makeCategory("Affected screen");
				var3.addCrashSectionCallable("Screen name", (Callable) currentScreen.getClass()::getCanonicalName);
				throw new ReportedException(var2);
			}

			if (currentScreen != null) {
				try {
					currentScreen.updateScreen();
				} catch (final Throwable var6) {
					var2 = CrashReport.makeCrashReport(var6, "Ticking screen");
					var3 = var2.makeCategory("Affected screen");
					var3.addCrashSectionCallable("Screen name", (Callable) currentScreen.getClass()::getCanonicalName);
					throw new ReportedException(var2);
				}
			}
		}

		if (currentScreen == null || currentScreen.allowUserInput) {
			mcProfiler.endStartSection("mouse");
			int var1;

			while (Mouse.next()) {
				var1 = Mouse.getEventButton();
				KeyBinding.setKeyBindState(var1 - 100, Mouse.getEventButtonState());

				if (Mouse.getEventButtonState()) {
					final MouseEvent eventMouse = new MouseEvent(var1);
					EventManager.call(eventMouse);
					if (Minecraft.thePlayer.isSpectatorMode() && var1 == 2) {
						ingameGUI.func_175187_g().func_175261_b();
					} else {
						KeyBinding.onTick(var1 - 100);
					}
				}

				final long var10 = getSystemTime() - systemTime;

				if (var10 <= 200L) {
					int var4 = Mouse.getEventDWheel();

					if (var4 != 0) {
						if (Minecraft.thePlayer.isSpectatorMode()) {
							var4 = var4 < 0 ? -1 : 1;

							if (ingameGUI.func_175187_g().func_175262_a()) {
								ingameGUI.func_175187_g().func_175259_b(-var4);
							} else {
								final float var5 = MathHelper.clamp_float(
										Minecraft.thePlayer.capabilities.getFlySpeed() + var4 * 0.005F, 0.0F, 0.2F);
								Minecraft.thePlayer.capabilities.setFlySpeed(var5);
							}
						} else {
							Minecraft.thePlayer.inventory.changeCurrentItem(var4);
						}
					}

					if (currentScreen == null) {
						if (!inGameHasFocus && Mouse.getEventButtonState()) {
							setIngameFocus();
						}
					} else if (currentScreen != null) {
						currentScreen.handleMouseInput();
					}
				}
			}

			if (leftClickCounter > 0) {
				--leftClickCounter;
			}

			mcProfiler.endStartSection("keyboard");

			while (Keyboard.next()) {
				var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
				KeyBinding.setKeyBindState(var1, Keyboard.getEventKeyState());

				if (Keyboard.getEventKeyState()) {
					KeyBinding.onTick(var1);
				}

				if (debugCrashKeyPressTime > 0L) {
					if (getSystemTime() - debugCrashKeyPressTime >= 1000L) {
						throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
					}

					if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
						debugCrashKeyPressTime = -1L;
					}
				} else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
					debugCrashKeyPressTime = getSystemTime();
				}

				dispatchKeypresses();

				if (Keyboard.getEventKeyState()) {
					if (var1 == 62 && entityRenderer != null) {
						entityRenderer.func_175071_c();
					}

					if (currentScreen != null) {
						currentScreen.handleKeyboardInput();
					} else {
						if (!Keyboard.isKeyDown(61)) {
							Client.onKeyPressed(var1);
						}

						final KeyPressEvent event = new KeyPressEvent(var1);
						EventManager.call(event);

						if (var1 == 1) {
							displayInGameMenu();
						}

						if (var1 == 32 && Keyboard.isKeyDown(61) && ingameGUI != null) {
							ingameGUI.getChatGUI().clearChatMessages();
						}

						if (var1 == 31 && Keyboard.isKeyDown(61)) {
							refreshResources();
						}

						if (var1 == 17 && Keyboard.isKeyDown(61)) {}

						if (var1 == 18 && Keyboard.isKeyDown(61)) {}

						if (var1 == 47 && Keyboard.isKeyDown(61)) {}

						if (var1 == 38 && Keyboard.isKeyDown(61)) {}

						if (var1 == 22 && Keyboard.isKeyDown(61)) {}

						if (var1 == 20 && Keyboard.isKeyDown(61)) {
							refreshResources();
						}

						if (var1 == 33 && Keyboard.isKeyDown(61)) {
							final boolean var11 = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
							gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, var11 ? -1 : 1);
						}

						if (var1 == 30 && Keyboard.isKeyDown(61)) {
							renderGlobal.loadRenderers();
						}

						if (var1 == 35 && Keyboard.isKeyDown(61)) {
							gameSettings.advancedItemTooltips = !gameSettings.advancedItemTooltips;
							gameSettings.saveOptions();
						}

						if (var1 == 48 && Keyboard.isKeyDown(61)) {
							Minecraft.renderManager.func_178629_b(!Minecraft.renderManager.func_178634_b());
						}

						if (var1 == 25 && Keyboard.isKeyDown(61)) {
							gameSettings.pauseOnLostFocus = !gameSettings.pauseOnLostFocus;
							gameSettings.saveOptions();
						}

						if (var1 == 59) {
							gameSettings.hideGUI = !gameSettings.hideGUI;
						}

						if (var1 == 61) {
							gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
							gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
						}

						if (gameSettings.keyBindTogglePerspective.isPressed()) {
							++gameSettings.thirdPersonView;

							if (gameSettings.thirdPersonView > 2) {
								gameSettings.thirdPersonView = 0;
							}

							if (gameSettings.thirdPersonView == 0) {
								entityRenderer.func_175066_a(func_175606_aa());
							} else if (gameSettings.thirdPersonView == 1) {
								entityRenderer.func_175066_a((Entity) null);
							}
						}

						if (gameSettings.keyBindSmoothCamera.isPressed()) {
							gameSettings.smoothCamera = !gameSettings.smoothCamera;
						}
					}

					if (gameSettings.showDebugInfo && gameSettings.showDebugProfilerChart) {
						if (var1 == 11) {
							updateDebugProfilerName(0);
						}

						for (int var12 = 0; var12 < 9; ++var12) {
							if (var1 == 2 + var12) {
								updateDebugProfilerName(var12 + 1);
							}
						}
					}
				}
			}

			for (var1 = 0; var1 < 9; ++var1) {
				if (gameSettings.keyBindsHotbar[var1].isPressed()) {
					if (Minecraft.thePlayer.isSpectatorMode()) {
						ingameGUI.func_175187_g().func_175260_a(var1);
					} else {
						Minecraft.thePlayer.inventory.currentItem = var1;
					}
				}
			}

			final boolean var9 = gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;

			while (gameSettings.keyBindInventory.isPressed()) {
				if (playerController.isRidingHorse()) {
					Minecraft.thePlayer.func_175163_u();
				} else {
					Minecraft.getNetHandler().addToSendQueue(
							new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
					displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
				}
			}

			while (gameSettings.keyBindDrop.isPressed()) {
				if (!Minecraft.thePlayer.isSpectatorMode()) {
					Minecraft.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
				}
			}

			while (gameSettings.keyBindChat.isPressed() && var9) {
				displayGuiScreen(new GuiChat());
			}

			if (currentScreen == null && gameSettings.keyBindCommand.isPressed() && var9) {
				displayGuiScreen(new GuiChat("/"));
			}

			if (Minecraft.thePlayer.isUsingItem()) {
				if (!gameSettings.keyBindUseItem.getIsKeyPressed()) {
					playerController.onStoppedUsingItem(Minecraft.thePlayer);
				}

				label435:

				while (true) {
					if (!gameSettings.keyBindAttack.isPressed()) {
						while (gameSettings.keyBindUseItem.isPressed()) {}

						while (true) {
							if (gameSettings.keyBindPickBlock.isPressed()) {
								continue;
							}

							break label435;
						}
					}
				}
			} else {
				while (gameSettings.keyBindAttack.isPressed()) {
					clickMouse();
				}

				while (gameSettings.keyBindUseItem.isPressed()) {
					rightClickMouse();
				}

				while (gameSettings.keyBindPickBlock.isPressed()) {
					middleClickMouse();
				}
			}

			if (gameSettings.keyBindUseItem.getIsKeyPressed() && rightClickDelayTimer == 0
					&& !Minecraft.thePlayer.isUsingItem()) {
				rightClickMouse();
			}

			sendClickBlockToController(
					currentScreen == null && gameSettings.keyBindAttack.getIsKeyPressed() && inGameHasFocus);
		}

		if (Minecraft.theWorld != null) {
			if (Minecraft.thePlayer != null) {
				++joinPlayerCounter;

				if (joinPlayerCounter == 30) {
					joinPlayerCounter = 0;
					Minecraft.theWorld.joinEntityInSurroundings(Minecraft.thePlayer);
				}
			}

			mcProfiler.endStartSection("gameRenderer");

			if (!isGamePaused) {
				entityRenderer.updateRenderer();
			}

			mcProfiler.endStartSection("levelRenderer");

			if (!isGamePaused) {
				renderGlobal.updateClouds();
			}

			mcProfiler.endStartSection("level");

			if (!isGamePaused) {
				if (Minecraft.theWorld.func_175658_ac() > 0) {
					Minecraft.theWorld.setLastLightningBolt(Minecraft.theWorld.func_175658_ac() - 1);
				}

				Minecraft.theWorld.updateEntities();
			}
		}

		if (!isGamePaused) {
			mcMusicTicker.update();
			mcSoundHandler.update();
		}

		if (Minecraft.theWorld != null) {
			if (!isGamePaused) {
				Minecraft.theWorld.setAllowedSpawnTypes(Minecraft.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL,
						true);

				try {
					Minecraft.theWorld.tick();
				} catch (final Throwable var8) {
					var2 = CrashReport.makeCrashReport(var8, "Exception in world tick");

					if (Minecraft.theWorld == null) {
						var3 = var2.makeCategory("Affected level");
						var3.addCrashSection("Problem", "Level is null!");
					} else {
						Minecraft.theWorld.addWorldInfoToCrashReport(var2);
					}

					throw new ReportedException(var2);
				}
			}

			mcProfiler.endStartSection("animateTick");

			if (!isGamePaused && Minecraft.theWorld != null) {
				Minecraft.theWorld.doVoidFogParticles(MathHelper.floor_double(Minecraft.thePlayer.posX),
						MathHelper.floor_double(Minecraft.thePlayer.posY),
						MathHelper.floor_double(Minecraft.thePlayer.posZ));
			}

			mcProfiler.endStartSection("particles");

			if (!isGamePaused) {
				Minecraft.effectRenderer.updateEffects();
			}
		} else if (myNetworkManager != null) {
			mcProfiler.endStartSection("pendingConnection");
			myNetworkManager.processReceivedPackets();
		}

		mcProfiler.endSection();
		systemTime = getSystemTime();
	}

	/**
	 * Arguments: World foldername, World ingame name, WorldSettings
	 */
	public void launchIntegratedServer(final String folderName, final String worldName, WorldSettings worldSettingsIn) {
		this.loadWorld((WorldClient) null);
		System.gc();
		final ISaveHandler var4 = saveLoader.getSaveLoader(folderName, false);
		WorldInfo var5 = var4.loadWorldInfo();

		if (var5 == null && worldSettingsIn != null) {
			var5 = new WorldInfo(worldSettingsIn, folderName);
			var4.saveWorldInfo(var5);
		}

		if (worldSettingsIn == null) {
			worldSettingsIn = new WorldSettings(var5);
		}

		try {
			theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
			theIntegratedServer.startServerThread();
			integratedServerIsRunning = true;
		} catch (final Throwable var10) {
			final CrashReport var7 = CrashReport.makeCrashReport(var10, "Starting integrated server");
			final CrashReportCategory var8 = var7.makeCategory("Starting integrated server");
			var8.addCrashSection("Level ID", folderName);
			var8.addCrashSection("Level Name", worldName);
			throw new ReportedException(var7);
		}

		loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));

		while (!theIntegratedServer.serverIsInRunLoop()) {
			final String var6 = theIntegratedServer.getUserMessage();

			if (var6 != null) {
				loadingScreen.displayLoadingString(I18n.format(var6, new Object[0]));
			} else {
				loadingScreen.displayLoadingString("");
			}

			try {
				Thread.sleep(200L);
			} catch (final InterruptedException var9) {}
		}

		displayGuiScreen((GuiScreen) null);
		final SocketAddress var11 = theIntegratedServer.getNetworkSystem().addLocalEndpoint();
		final NetworkManager var12 = NetworkManager.provideLocalClient(var11);
		var12.setNetHandler(new NetHandlerLoginClient(var12, this, (GuiScreen) null));
		var12.sendPacket(new C00Handshake(47, var11.toString(), 0, EnumConnectionState.LOGIN));
		var12.sendPacket(new C00PacketLoginStart(getSession().getProfile()));
		myNetworkManager = var12;
	}

	/**
	 * unloads the current world first
	 */
	public void loadWorld(final WorldClient worldClientIn) {
		this.loadWorld(worldClientIn, "");
	}

	/**
	 * par2Str is displayed on the loading screen to the user unloads the current
	 * world first
	 */
	public void loadWorld(final WorldClient worldClientIn, final String loadingMessage) {
		if (worldClientIn == null) {
			final NetHandlerPlayClient var3 = Minecraft.getNetHandler();
			new Thread("EaZy IRC Disconnector") {
				@Override
				public void run() {
					try {
						if (Client.theClient.getIrcManager().isConnected()) {
							Client.theClient.getIrcManager().disconnect();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			if (var3 != null) {
				var3.cleanup();
			}

			if (theIntegratedServer != null && theIntegratedServer.func_175578_N()) {
				theIntegratedServer.initiateShutdown();
				theIntegratedServer.func_175592_a();
			}

			theIntegratedServer = null;
			guiAchievement.clearAchievements();
			entityRenderer.getMapItemRenderer().func_148249_a();
		}
		field_175622_Z = null;
		myNetworkManager = null;

		if (loadingScreen != null) {
			loadingScreen.resetProgressAndMessage(loadingMessage);
			loadingScreen.displayLoadingString("");
		}

		if (worldClientIn == null && Minecraft.theWorld != null) {
			if (mcResourcePackRepository.getResourcePackInstance() != null) {
				mcResourcePackRepository.func_148529_f();
				func_175603_A();
			}
			setServerData((ServerData) null);
			integratedServerIsRunning = false;
		}
		mcSoundHandler.stopSounds();
		Minecraft.theWorld = worldClientIn;

		if (worldClientIn != null) {
			if (renderGlobal != null) {
				renderGlobal.setWorldAndLoadRenderers(worldClientIn);
			}

			if (Minecraft.effectRenderer != null) {
				Minecraft.effectRenderer.clearEffects(worldClientIn);
			}

			if (Minecraft.thePlayer == null) {
				Minecraft.thePlayer = playerController.func_178892_a(worldClientIn, new StatFileWriter());
				playerController.flipPlayer(Minecraft.thePlayer);
			}

			Minecraft.thePlayer.preparePlayerToSpawn();
			worldClientIn.spawnEntityInWorld(Minecraft.thePlayer);
			Minecraft.thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
			playerController.setPlayerCapabilities(Minecraft.thePlayer);
			field_175622_Z = Minecraft.thePlayer;
		} else {
			saveLoader.flushCache();
			Minecraft.thePlayer = null;
		}
		new Thread("EaZy IRC Connector") {
			@Override
			public void run() {
				try {
					if (!Client.theClient.getIrcManager().isConnected()) {
						Client.theClient.getIrcManager().connect();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		System.gc();
		systemTime = 0L;
	}

	public void setDimensionAndSpawnPlayer(final int dimension) {
		Minecraft.theWorld.setInitialSpawnLocation();
		Minecraft.theWorld.removeAllEntities();
		int var2 = 0;
		String var3 = null;

		if (Minecraft.thePlayer != null) {
			var2 = Minecraft.thePlayer.getEntityId();
			Minecraft.theWorld.removeEntity(Minecraft.thePlayer);
			var3 = Minecraft.thePlayer.getClientBrand();
		}

		field_175622_Z = null;
		final EntityPlayerSP var4 = Minecraft.thePlayer;
		Minecraft.thePlayer = playerController.func_178892_a(Minecraft.theWorld,
				Minecraft.thePlayer == null ? new StatFileWriter() : Minecraft.thePlayer.getStatFileWriter());
		Minecraft.thePlayer.getDataWatcher().updateWatchedObjectsFromList(var4.getDataWatcher().getAllWatched());
		Minecraft.thePlayer.dimension = dimension;
		field_175622_Z = Minecraft.thePlayer;
		Minecraft.thePlayer.preparePlayerToSpawn();
		Minecraft.thePlayer.setClientBrand(var3);
		Minecraft.theWorld.spawnEntityInWorld(Minecraft.thePlayer);
		playerController.flipPlayer(Minecraft.thePlayer);
		Minecraft.thePlayer.movementInput = new MovementInputFromOptions(gameSettings);
		Minecraft.thePlayer.setEntityId(var2);
		playerController.setPlayerCapabilities(Minecraft.thePlayer);
		Minecraft.thePlayer.func_175150_k(var4.func_175140_cp());

		if (currentScreen instanceof GuiGameOver) {
			displayGuiScreen((GuiScreen) null);
		}
	}

	/**
	 * Gets whether this is a demo or not.
	 */
	public final boolean isDemo() {
		return isDemo;
	}

	public static NetHandlerPlayClient getNetHandler() {
		return Minecraft.thePlayer != null ? Minecraft.thePlayer.sendQueue : null;
	}

	public static boolean isGuiEnabled() {
		return theMinecraft == null || !Minecraft.gameSettings.hideGUI;
	}

	public static boolean isFancyGraphicsEnabled() {
		return theMinecraft != null && Minecraft.gameSettings.fancyGraphics;
	}

	/**
	 * Returns if ambient occlusion is enabled
	 */
	public static boolean isAmbientOcclusionEnabled() {
		return theMinecraft != null && Minecraft.gameSettings.ambientOcclusion != 0;
	}

	/**
	 * Called when user clicked he's mouse middle button (pick block)
	 */
	private void middleClickMouse() {
		if (objectMouseOver != null) {
			final boolean var1 = Minecraft.thePlayer.capabilities.isCreativeMode;
			int var3 = 0;
			boolean var4 = false;
			TileEntity var5 = null;
			Object var2;

			if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				final BlockPos var6 = objectMouseOver.getBlockPos();
				final Block var7 = Minecraft.theWorld.getBlockState(var6).getBlock();

				if (var7.getMaterial() == Material.air) {
					return;
				}

				var2 = var7.getItem(Minecraft.theWorld, var6);

				if (var2 == null) {
					return;
				}

				if (var1 && GuiScreen.isCtrlKeyDown()) {
					var5 = Minecraft.theWorld.getTileEntity(var6);
				}

				final Block var8 = var2 instanceof ItemBlock && !var7.isFlowerPot()
						? Block.getBlockFromItem((Item) var2)
						: var7;
				var3 = var8.getDamageValue(Minecraft.theWorld, var6);
				var4 = ((Item) var2).getHasSubtypes();
			} else {
				if (objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY
						|| objectMouseOver.entityHit == null || !var1) {
					return;
				}

				if (objectMouseOver.entityHit instanceof EntityPainting) {
					var2 = Items.painting;
				} else if (objectMouseOver.entityHit instanceof EntityLeashKnot) {
					var2 = Items.lead;
				} else if (objectMouseOver.entityHit instanceof EntityItemFrame) {
					final EntityItemFrame var11 = (EntityItemFrame) objectMouseOver.entityHit;
					final ItemStack var14 = var11.getDisplayedItem();

					if (var14 == null) {
						var2 = Items.item_frame;
					} else {
						var2 = var14.getItem();
						var3 = var14.getMetadata();
						var4 = true;
					}
				} else if (objectMouseOver.entityHit instanceof EntityMinecart) {
					final EntityMinecart var12 = (EntityMinecart) objectMouseOver.entityHit;

					switch (Minecraft.SwitchEnumMinecartType.field_178901_b[var12.func_180456_s().ordinal()]) {
					case 1:
						var2 = Items.furnace_minecart;
						break;

					case 2:
						var2 = Items.chest_minecart;
						break;

					case 3:
						var2 = Items.tnt_minecart;
						break;

					case 4:
						var2 = Items.hopper_minecart;
						break;

					case 5:
						var2 = Items.command_block_minecart;
						break;

					default:
						var2 = Items.minecart;
					}
				} else if (objectMouseOver.entityHit instanceof EntityBoat) {
					var2 = Items.boat;
				} else if (objectMouseOver.entityHit instanceof EntityArmorStand) {
					var2 = Items.armor_stand;
				} else {
					var2 = Items.spawn_egg;
					var3 = EntityList.getEntityID(objectMouseOver.entityHit);
					var4 = true;

					if (!EntityList.entityEggs.containsKey(var3)) {
						return;
					}
				}
			}

			final InventoryPlayer var13 = Minecraft.thePlayer.inventory;

			if (var5 == null) {
				var13.setCurrentItem((Item) var2, var3, var4, var1);
			} else {
				final NBTTagCompound var15 = new NBTTagCompound();
				var5.writeToNBT(var15);
				final ItemStack var17 = new ItemStack((Item) var2, 1, var3);
				var17.setTagInfo("BlockEntityTag", var15);
				final NBTTagCompound var9 = new NBTTagCompound();
				final NBTTagList var10 = new NBTTagList();
				var10.appendTag(new NBTTagString("(+NBT)"));
				var9.setTag("Lore", var10);
				var17.setTagInfo("display", var9);
				var13.setInventorySlotContents(var13.currentItem, var17);
			}

			if (var1) {
				final int var16 = Minecraft.thePlayer.inventoryContainer.inventorySlots.size() - 9 + var13.currentItem;
				playerController.sendSlotPacket(var13.getStackInSlot(var13.currentItem), var16);
			}
		}
	}

	/**
	 * adds core server Info (GL version , Texture pack, isModded, type), and the
	 * worldInfo to the crash report
	 */
	public CrashReport addGraphicsAndWorldToCrashReport(final CrashReport theCrash) {
		theCrash.getCategory().addCrashSectionCallable("Launched Version", (Callable) () -> launchedVersion);
		theCrash.getCategory().addCrashSectionCallable("LWJGL", (Callable) Sys::getVersion);
		theCrash.getCategory().addCrashSectionCallable("OpenGL", (Callable) () -> GL11.glGetString(GL11.GL_RENDERER)
				+ " GL version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR));
		theCrash.getCategory().addCrashSectionCallable("GL Caps", (Callable) OpenGlHelper::func_153172_c);
		theCrash.getCategory().addCrashSectionCallable("Using VBOs",
				(Callable) () -> gameSettings.field_178881_t ? "Yes" : "No");
		theCrash.getCategory().addCrashSectionCallable("Is Modded", (Callable) () -> {
			final String var1 = ClientBrandRetriever.getClientModName();
			return !var1.equals("vanilla") ? "Definitely; Client brand changed to \'" + var1 + "\'"
					: Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated"
							: "Probably not. Jar signature remains and client brand is untouched.";
		});
		theCrash.getCategory().addCrashSectionCallable("Type", (Callable) () -> "Client (map_client.txt)");
		theCrash.getCategory().addCrashSectionCallable("Resource Packs",
				(Callable) gameSettings.resourcePacks::toString);
		theCrash.getCategory().addCrashSectionCallable("Current Language",
				(Callable) mcLanguageManager.getCurrentLanguage()::toString);
		theCrash.getCategory().addCrashSectionCallable("Profiler Position", new Callable() {
			public String call1() {
				return mcProfiler.profilingEnabled ? mcProfiler.getNameOfLastSection() : "N/A (disabled)";
			}

			@Override
			public Object call() {
				return call1();
			}
		});

		if (Minecraft.theWorld != null) {
			Minecraft.theWorld.addWorldInfoToCrashReport(theCrash);
		}

		return theCrash;
	}

	/**
	 * Return the singleton Minecraft instance for the game
	 */
	public static Minecraft getMinecraft() {
		return theMinecraft;
	}

	public ListenableFuture func_175603_A() {
		return this.addScheduledTask(Minecraft.this::refreshResources);
	}

	@Override
	public void addServerStatsToSnooper(final PlayerUsageSnooper playerSnooper) {
		playerSnooper.addClientStat("fps", debugFPS);
		playerSnooper.addClientStat("vsync_enabled", gameSettings.enableVsync);
		playerSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
		playerSnooper.addClientStat("display_type", fullscreen ? "fullscreen" : "windowed");
		playerSnooper.addClientStat("run_time",
				(MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
		final String var2 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
		playerSnooper.addClientStat("endianness", var2);
		playerSnooper.addClientStat("resource_packs", mcResourcePackRepository.getRepositoryEntries().size());
		int var3 = 0;
		final Iterator var4 = mcResourcePackRepository.getRepositoryEntries().iterator();

		while (var4.hasNext()) {
			final ResourcePackRepository.Entry var5 = (ResourcePackRepository.Entry) var4.next();
			playerSnooper.addClientStat("resource_pack[" + var3++ + "]", var5.getResourcePackName());
		}

		if (theIntegratedServer != null && theIntegratedServer.getPlayerUsageSnooper() != null) {
			playerSnooper.addClientStat("snooper_partner", theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
		}
	}

	@Override
	public void addServerTypeToSnooper(final PlayerUsageSnooper playerSnooper) {
		playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(GL11.GL_VERSION));
		playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
		playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
		playerSnooper.addStatToSnooper("launched_version", launchedVersion);
		final ContextCapabilities var2 = GLContext.getCapabilities();
		playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", var2.GL_ARB_arrays_of_arrays);
		playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", var2.GL_ARB_base_instance);
		playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", var2.GL_ARB_blend_func_extended);
		playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", var2.GL_ARB_clear_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", var2.GL_ARB_color_buffer_float);
		playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", var2.GL_ARB_compatibility);
		playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]",
				var2.GL_ARB_compressed_texture_pixel_storage);
		playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
		playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
		playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
		playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
		playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var2.GL_ARB_compute_shader);
		playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var2.GL_ARB_copy_buffer);
		playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var2.GL_ARB_copy_image);
		playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var2.GL_ARB_depth_buffer_float);
		playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", var2.GL_ARB_depth_clamp);
		playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", var2.GL_ARB_depth_texture);
		playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", var2.GL_ARB_draw_buffers);
		playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", var2.GL_ARB_draw_buffers_blend);
		playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", var2.GL_ARB_draw_elements_base_vertex);
		playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", var2.GL_ARB_draw_indirect);
		playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", var2.GL_ARB_draw_instanced);
		playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", var2.GL_ARB_explicit_attrib_location);
		playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", var2.GL_ARB_explicit_uniform_location);
		playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", var2.GL_ARB_fragment_layer_viewport);
		playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", var2.GL_ARB_fragment_program);
		playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", var2.GL_ARB_fragment_shader);
		playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", var2.GL_ARB_fragment_program_shadow);
		playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", var2.GL_ARB_framebuffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", var2.GL_ARB_framebuffer_sRGB);
		playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", var2.GL_ARB_geometry_shader4);
		playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", var2.GL_ARB_gpu_shader5);
		playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", var2.GL_ARB_half_float_pixel);
		playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", var2.GL_ARB_half_float_vertex);
		playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", var2.GL_ARB_instanced_arrays);
		playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", var2.GL_ARB_map_buffer_alignment);
		playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", var2.GL_ARB_map_buffer_range);
		playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", var2.GL_ARB_multisample);
		playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", var2.GL_ARB_multitexture);
		playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", var2.GL_ARB_occlusion_query2);
		playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", var2.GL_ARB_pixel_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", var2.GL_ARB_seamless_cube_map);
		playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", var2.GL_ARB_shader_objects);
		playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", var2.GL_ARB_shader_stencil_export);
		playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", var2.GL_ARB_shader_texture_lod);
		playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", var2.GL_ARB_shadow);
		playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", var2.GL_ARB_shadow_ambient);
		playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", var2.GL_ARB_stencil_texturing);
		playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", var2.GL_ARB_sync);
		playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", var2.GL_ARB_tessellation_shader);
		playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", var2.GL_ARB_texture_border_clamp);
		playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", var2.GL_ARB_texture_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", var2.GL_ARB_texture_cube_map);
		playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", var2.GL_ARB_texture_cube_map_array);
		playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", var2.GL_ARB_texture_non_power_of_two);
		playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", var2.GL_ARB_uniform_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", var2.GL_ARB_vertex_blend);
		playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", var2.GL_ARB_vertex_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", var2.GL_ARB_vertex_program);
		playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", var2.GL_ARB_vertex_shader);
		playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", var2.GL_EXT_bindable_uniform);
		playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", var2.GL_EXT_blend_equation_separate);
		playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", var2.GL_EXT_blend_func_separate);
		playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", var2.GL_EXT_blend_minmax);
		playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", var2.GL_EXT_blend_subtract);
		playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", var2.GL_EXT_draw_instanced);
		playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", var2.GL_EXT_framebuffer_multisample);
		playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", var2.GL_EXT_framebuffer_object);
		playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", var2.GL_EXT_framebuffer_sRGB);
		playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", var2.GL_EXT_geometry_shader4);
		playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", var2.GL_EXT_gpu_program_parameters);
		playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", var2.GL_EXT_gpu_shader4);
		playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", var2.GL_EXT_multi_draw_arrays);
		playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", var2.GL_EXT_packed_depth_stencil);
		playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", var2.GL_EXT_paletted_texture);
		playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", var2.GL_EXT_rescale_normal);
		playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", var2.GL_EXT_separate_shader_objects);
		playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", var2.GL_EXT_shader_image_load_store);
		playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", var2.GL_EXT_shadow_funcs);
		playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", var2.GL_EXT_shared_texture_palette);
		playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", var2.GL_EXT_stencil_clear_tag);
		playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", var2.GL_EXT_stencil_two_side);
		playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", var2.GL_EXT_stencil_wrap);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", var2.GL_EXT_texture_3d);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", var2.GL_EXT_texture_array);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", var2.GL_EXT_texture_buffer_object);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", var2.GL_EXT_texture_integer);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", var2.GL_EXT_texture_lod_bias);
		playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", var2.GL_EXT_texture_sRGB);
		playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", var2.GL_EXT_vertex_shader);
		playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", var2.GL_EXT_vertex_weighting);
		playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]",
				GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]",
				GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(GL20.GL_MAX_VERTEX_ATTRIBS));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]",
				GL11.glGetInteger(GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]",
				GL11.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
		GL11.glGetError();
		playerSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
	}

	/**
	 * Used in the usage snooper.
	 */
	public static int getGLMaximumTextureSize() {
		for (int var0 = 16384; var0 > 0; var0 >>= 1) {
			GL11.glTexImage2D(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_RGBA, var0, var0, 0, GL11.GL_RGBA,
					GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
			final int var1 = GL11.glGetTexLevelParameteri(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);

			if (var1 != 0) {
				return var0;
			}
		}

		return -1;
	}

	/**
	 * Returns whether snooping is enabled or not.
	 */
	@Override
	public boolean isSnooperEnabled() {
		return gameSettings.snooperEnabled;
	}

	/**
	 * Set the current ServerData instance.
	 */
	public void setServerData(final ServerData serverDataIn) {
		currentServerData = serverDataIn;
	}

	public static ServerData getCurrentServerData() {
		return currentServerData;
	}

	public boolean isIntegratedServerRunning() {
		return integratedServerIsRunning;
	}

	/**
	 * Returns true if there is only one player playing, and the current server is
	 * the integrated one.
	 */
	public boolean isSingleplayer() {
		return integratedServerIsRunning && theIntegratedServer != null;
	}

	/**
	 * Returns the currently running integrated server
	 */
	public IntegratedServer getIntegratedServer() {
		return theIntegratedServer;
	}

	public static void stopIntegratedServer() {
		if (theMinecraft != null) {
			final IntegratedServer var0 = theMinecraft.getIntegratedServer();

			if (var0 != null) {
				var0.stopServer();
			}
		}
	}

	/**
	 * Returns the PlayerUsageSnooper instance.
	 */
	public PlayerUsageSnooper getPlayerUsageSnooper() {
		return usageSnooper;
	}

	/**
	 * Gets the system time in milliseconds.
	 */
	public static long getSystemTime() {
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}

	/**
	 * Returns whether we're in full screen or not.
	 */
	public boolean isFullScreen() {
		return fullscreen;
	}

	public static Session getSession() {
		return session;
	}

	public PropertyMap func_180509_L() {
		return twitchDetails;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public static TextureManager getTextureManager() {
		return renderEngine;
	}

	public static IResourceManager getResourceManager() {
		return mcResourceManager;
	}

	public ResourcePackRepository getResourcePackRepository() {
		return mcResourcePackRepository;
	}

	public LanguageManager getLanguageManager() {
		return mcLanguageManager;
	}

	public TextureMap getTextureMapBlocks() {
		return textureMapBlocks;
	}

	public boolean isJava64bit() {
		return jvm64bit;
	}

	public boolean isGamePaused() {
		return isGamePaused;
	}

	public SoundHandler getSoundHandler() {
		return mcSoundHandler;
	}

	public MusicTicker.MusicType getAmbientMusicType() {
		return currentScreen instanceof GuiWinGame ? MusicTicker.MusicType.CREDITS
				: Minecraft.thePlayer != null
						? Minecraft.thePlayer.worldObj.provider instanceof WorldProviderHell
								? MusicTicker.MusicType.NETHER
								: Minecraft.thePlayer.worldObj.provider instanceof WorldProviderEnd
										? BossStatus.bossName != null && BossStatus.statusBarTime > 0
												? MusicTicker.MusicType.END_BOSS
												: MusicTicker.MusicType.END
										: Minecraft.thePlayer.capabilities.isCreativeMode
												&& Minecraft.thePlayer.capabilities.allowFlying
														? MusicTicker.MusicType.CREATIVE
														: MusicTicker.MusicType.GAME
						: MusicTicker.MusicType.MENU;
	}

	public IStream getTwitchStream() {
		return stream;
	}

	public void dispatchKeypresses() {
		final int var1 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();

		if (var1 != 0 && !Keyboard.isRepeatEvent()) {
			if (!(currentScreen instanceof GuiControls)
					|| ((GuiControls) currentScreen).time <= getSystemTime() - 20L) {
				if (Keyboard.getEventKeyState()) {
					if (var1 == gameSettings.keyBindStreamStartStop.getKeyCode() && currentScreen == null) {
						if (getTwitchStream().func_152934_n()) {
							getTwitchStream().func_152914_u();
						} else if (getTwitchStream().func_152924_m()) {
							displayGuiScreen(new GuiYesNo((final boolean result, final int id) -> {
								if (result) {
									Minecraft.this.getTwitchStream().func_152930_t();
								}

								Minecraft.this.displayGuiScreen((GuiScreen) null);
							}, I18n.format("stream.confirm_start", new Object[0]), "", 0));
						} else if (getTwitchStream().func_152928_D() && getTwitchStream().func_152936_l()) {
							if (Minecraft.theWorld != null) {
								ingameGUI.getChatGUI()
										.printChatMessage(new ChatComponentText("Not ready to start streaming yet!"));
							}
						} else {
							GuiStreamUnavailable.func_152321_a(currentScreen);
						}
					} else if (var1 == gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
						if (getTwitchStream().func_152934_n()) {
							if (getTwitchStream().isPaused()) {
								getTwitchStream().func_152933_r();
							} else {
								getTwitchStream().func_152916_q();
							}
						}
					} else if (var1 == gameSettings.keyBindStreamCommercials.getKeyCode()) {
						if (getTwitchStream().func_152934_n()) {
							getTwitchStream().func_152931_p();
						}
					} else if (var1 == gameSettings.keyBindStreamToggleMic.getKeyCode()) {
						stream.func_152910_a(true);
					} else if (var1 == gameSettings.keyBindFullscreen.getKeyCode()) {
						toggleFullscreen();
					} else if (var1 == gameSettings.keyBindScreenshot.getKeyCode()) {
						ingameGUI.getChatGUI().printChatMessage(
								ScreenShotHelper.saveScreenshot(mcDataDir, displayWidth, displayHeight, framebufferMc));
					}
				} else if (var1 == gameSettings.keyBindStreamToggleMic.getKeyCode()) {
					stream.func_152910_a(false);
				}
			}
		}
	}

	public MinecraftSessionService getSessionService() {
		return sessionService;
	}

	public SkinManager getSkinManager() {
		return skinManager;
	}

	public Entity func_175606_aa() {
		return field_175622_Z;
	}

	public void func_175607_a(final Entity p_175607_1_) {
		field_175622_Z = p_175607_1_;
		entityRenderer.func_175066_a(p_175607_1_);
	}

	public ListenableFuture addScheduledTask(final Callable callableToSchedule) {
		Validate.notNull(callableToSchedule);

		if (!isCallingFromMinecraftThread()) {
			final ListenableFutureTask var2 = ListenableFutureTask.create(callableToSchedule);
			synchronized (scheduledTasks) {
				scheduledTasks.add(var2);
				return var2;
			}
		} else {
			try {
				return Futures.immediateFuture(callableToSchedule.call());
			} catch (final Exception var6) {
				return Futures.immediateFailedCheckedFuture(var6);
			}
		}
	}

	@Override
	public ListenableFuture addScheduledTask(final Runnable runnableToSchedule) {
		Validate.notNull(runnableToSchedule);
		return this.addScheduledTask(Executors.callable(runnableToSchedule));
	}

	@Override
	public boolean isCallingFromMinecraftThread() {
		return Thread.currentThread() == mcThread;
	}

	public BlockRendererDispatcher getBlockRendererDispatcher() {
		return blockRenderer;
	}

	public static RenderManager getRenderManager() {
		return Minecraft.renderManager;
	}

	public RenderItem getRenderItem() {
		return Minecraft.renderItem;
	}

	public ItemRenderer getItemRenderer() {
		return Minecraft.itemRenderer;
	}

	public static int func_175610_ah() {
		return debugFPS;
	}

	public static Map func_175596_ai() {
		final HashMap var0 = Maps.newHashMap();
		var0.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
		var0.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
		var0.put("X-Minecraft-Version", "1.8");
		return var0;
	}

	static final class SwitchEnumMinecartType {

		static final int[] field_152390_a;

		static final int[] field_178901_b = new int[EntityMinecart.EnumMinecartType.values().length];

		static {
			try {
				field_178901_b[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 1;
			} catch (final NoSuchFieldError var8) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var7) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
			} catch (final NoSuchFieldError var6) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 4;
			} catch (final NoSuchFieldError var5) {}

			try {
				field_178901_b[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 5;
			} catch (final NoSuchFieldError var4) {}

			field_152390_a = new int[MovingObjectPosition.MovingObjectType.values().length];

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.ENTITY.ordinal()] = 1;
			} catch (final NoSuchFieldError var3) {}

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.BLOCK.ordinal()] = 2;
			} catch (final NoSuchFieldError var2) {}

			try {
				field_152390_a[MovingObjectPosition.MovingObjectType.MISS.ordinal()] = 3;
			} catch (final NoSuchFieldError var1) {}
		}
	}
}
