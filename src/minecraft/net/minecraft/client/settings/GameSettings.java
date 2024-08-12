package net.minecraft.client.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import optifine.ClearWater;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lang;
import optifine.NaturalTextures;
import optifine.RandomMobs;
import optifine.Reflector;
import shadersmod.client.Shaders;

public class GameSettings {

public static final int EaZy = 902;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private static final Gson gson = new Gson();
	private static final ParameterizedType typeListString = new ParameterizedType() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00000651";

		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class };
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};

	/** GUI scale values */
	private static final String[] GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small",
			"options.guiScale.normal", "options.guiScale.large" };
	private static final String[] PARTICLES = new String[] { "options.particles.all", "options.particles.decreased",
			"options.particles.minimal" };
	private static final String[] AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min",
			"options.ao.max" };
	private static final String[] STREAM_COMPRESSIONS = new String[] { "options.stream.compression.low",
			"options.stream.compression.medium", "options.stream.compression.high" };
	private static final String[] STREAM_CHAT_MODES = new String[] { "options.stream.chat.enabled.streaming",
			"options.stream.chat.enabled.always", "options.stream.chat.enabled.never" };
	private static final String[] STREAM_CHAT_FILTER_MODES = new String[] { "options.stream.chat.userFilter.all",
			"options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods" };
	private static final String[] STREAM_MIC_MODES = new String[] { "options.stream.mic_toggle.mute",
			"options.stream.mic_toggle.talk" };
	public float mouseSensitivity = 0.5F;
	public boolean invertMouse;
	public int renderDistanceChunks = -1;
	public boolean viewBobbing = true;
	public boolean anaglyph;
	public boolean fboEnable = true;
	public int limitFramerate = 120;

	/** Clouds flag */
	public boolean clouds = true;
	public boolean fancyGraphics = true;

	/** Smooth Lighting */
	public int ambientOcclusion = 2;
	public List resourcePacks = Lists.newArrayList();
	public EntityPlayer.EnumChatVisibility chatVisibility;
	public boolean chatColours;
	public boolean chatLinks;
	public boolean chatLinksPrompt;
	public float chatOpacity;
	public boolean snooperEnabled;
	public boolean fullScreen;
	public boolean enableVsync;
	public boolean field_178881_t;
	public boolean field_178880_u;
	public boolean field_178879_v;
	public boolean hideServerAddress;

	/**
	 * Whether to show advanced information on item tooltips, toggled by F3+H
	 */
	public boolean advancedItemTooltips;

	/** Whether to pause when the game loses focus, toggled by F3+P */
	public boolean pauseOnLostFocus;
	private final Set field_178882_aU;
	public boolean touchscreen;
	public int overrideWidth;
	public int overrideHeight;
	public boolean heldItemTooltips;
	public float chatScale;
	public float chatWidth;
	public float chatHeightUnfocused;
	public float chatHeightFocused;
	public boolean showInventoryAchievementHint;
	public int mipmapLevels;
	private final Map mapSoundLevels;
	public float streamBytesPerPixel;
	public float streamMicVolume;
	public float streamGameVolume;
	public float streamKbps;
	public float streamFps;
	public int streamCompression;
	public boolean streamSendMetadata;
	public String streamPreferredServer;
	public int streamChatEnabled;
	public int streamChatUserFilter;
	public int streamMicToggleBehavior;
	public KeyBinding keyBindForward;
	public KeyBinding keyBindLeft;
	public KeyBinding keyBindBack;
	public KeyBinding keyBindRight;
	public KeyBinding keyBindJump;
	public KeyBinding keyBindSneak;
	public KeyBinding keyBindInventory;
	public KeyBinding keyBindUseItem;
	public KeyBinding keyBindDrop;
	public KeyBinding keyBindAttack;
	public KeyBinding keyBindPickBlock;
	public KeyBinding keyBindSprint;
	public KeyBinding keyBindChat;
	public KeyBinding keyBindPlayerList;
	public KeyBinding keyBindCommand;
	public KeyBinding keyBindScreenshot;
	public KeyBinding keyBindTogglePerspective;
	public KeyBinding keyBindSmoothCamera;
	public KeyBinding keyBindFullscreen;
	public KeyBinding keyBindSpectatorOutlines;
	public KeyBinding keyBindStreamStartStop;
	public KeyBinding keyBindStreamPauseUnpause;
	public KeyBinding keyBindStreamCommercials;
	public KeyBinding keyBindStreamToggleMic;
	public KeyBinding[] keyBindsHotbar;
	public KeyBinding[] keyBindings;
	protected Minecraft mc;
	private File optionsFile;
	public EnumDifficulty difficulty;
	public boolean hideGUI;
	public int thirdPersonView;

	/** true if debug info should be displayed instead of version */
	public boolean showDebugInfo;
	public boolean showDebugProfilerChart;

	/** The lastServer string. */
	public String lastServer;

	/** Smooth Camera Toggle */
	public boolean smoothCamera;
	public boolean debugCamEnable;
	public float fovSetting;
	public float gammaSetting;
	public float saturation;

	/** GUI scale */
	public int guiScale;

	/** Determines amount of particles. 0 = All, 1 = Decreased, 2 = Minimal */
	public int particleSetting;

	/** Game settings language */
	public String language;
	public boolean forceUnicodeFont;
	// private static final String __OBFID = "http://https://fuckuskid00000650";
	public int ofFogType = 1;
	public float ofFogStart = 0.8F;
	public int ofMipmapType = 0;
	public boolean ofOcclusionFancy = false;
	public boolean ofSmoothFps = false;
	public boolean ofSmoothWorld = Config.isSingleProcessor();
	public boolean ofLazyChunkLoading = Config.isSingleProcessor();
	public float ofAoLevel = 1.0F;
	public int ofAaLevel = 0;
	public int ofAfLevel = 1;
	public int ofClouds = 0;
	public float ofCloudsHeight = 0.0F;
	public int ofTrees = 0;
	public int ofRain = 0;
	public int ofDroppedItems = 0;
	public int ofBetterGrass = 3;
	public int ofAutoSaveTicks = 4000;
	public boolean ofLagometer = false;
	public boolean ofProfiler = false;
	public boolean ofShowFps = false;
	public boolean ofWeather = true;
	public boolean ofSky = true;
	public boolean ofStars = true;
	public boolean ofSunMoon = true;
	public int ofVignette = 0;
	public int ofChunkUpdates = 1;
	public boolean ofChunkUpdatesDynamic = false;
	public int ofTime = 0;
	public boolean ofClearWater = false;
	public boolean ofBetterSnow = false;
	public String ofFullscreenMode = "Default";
	public boolean ofSwampColors = true;
	public boolean ofRandomMobs = true;
	public boolean ofSmoothBiomes = true;
	public boolean ofCustomFonts = true;
	public boolean ofCustomColors = true;
	public boolean ofCustomSky = true;
	public boolean ofShowCapes = true;
	public int ofConnectedTextures = 2;
	public boolean ofCustomItems = true;
	public boolean ofNaturalTextures = false;
	public boolean ofFastMath = false;
	public boolean ofFastRender = false;
	public int ofTranslucentBlocks = 0;
	public boolean ofDynamicFov = true;
	public int ofDynamicLights = 3;
	public int ofAnimatedWater = 0;
	public int ofAnimatedLava = 0;
	public boolean ofAnimatedFire = true;
	public boolean ofAnimatedPortal = true;
	public boolean ofAnimatedRedstone = true;
	public boolean ofAnimatedExplosion = true;
	public boolean ofAnimatedFlame = true;
	public boolean ofAnimatedSmoke = true;
	public boolean ofVoidParticles = true;
	public boolean ofWaterParticles = true;
	public boolean ofRainSplash = true;
	public boolean ofPortalParticles = true;
	public boolean ofPotionParticles = true;
	public boolean ofFireworkParticles = true;
	public boolean ofDrippingWaterLava = true;
	public boolean ofAnimatedTerrain = true;
	public boolean ofAnimatedTextures = true;
	public static final int DEFAULT = 0;
	public static final int FAST = 1;
	public static final int FANCY = 2;
	public static final int OFF = 3;
	public static final int SMART = 4;
	public static final int ANIM_ON = 0;
	public static final int ANIM_GENERATED = 1;
	public static final int ANIM_OFF = 2;
	public static final String DEFAULT_STR = "Default";
	private static final int[] OF_TREES_VALUES = new int[] { 0, 1, 4, 2 };
	private static final int[] OF_DYNAMIC_LIGHTS = new int[] { 3, 1, 2 };
	private static final String[] KEYS_DYNAMIC_LIGHTS = new String[] { "options.off", "options.graphics.fast",
			"options.graphics.fancy" };
	public KeyBinding ofKeyBindZoom;
	private File optionsFileOF;

	public GameSettings(final Minecraft mcIn, final File p_i46326_2_) {
		chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		chatColours = true;
		chatLinks = true;
		chatLinksPrompt = true;
		chatOpacity = 1.0F;
		snooperEnabled = true;
		enableVsync = true;
		field_178881_t = false;
		field_178880_u = true;
		field_178879_v = false;
		pauseOnLostFocus = true;
		field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
		heldItemTooltips = true;
		chatScale = 1.0F;
		chatWidth = 1.0F;
		chatHeightUnfocused = 0.44366196F;
		chatHeightFocused = 1.0F;
		showInventoryAchievementHint = true;
		mipmapLevels = 4;
		mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
		streamBytesPerPixel = 0.5F;
		streamMicVolume = 1.0F;
		streamGameVolume = 1.0F;
		streamKbps = 0.5412844F;
		streamFps = 0.31690142F;
		streamCompression = 1;
		streamSendMetadata = true;
		streamPreferredServer = "";
		streamChatEnabled = 0;
		streamChatUserFilter = 0;
		streamMicToggleBehavior = 0;
		keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
		keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
		keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
		keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
		keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
		keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
		keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
		keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
		keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
		keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
		keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
		keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
		keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
		keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
		keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
		keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
		keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
		keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
		keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
		keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
		keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
		keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
		keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
		keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
		keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
				new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
				new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
				new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
				new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
				new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
				new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
				new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
				new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
		keyBindings = ArrayUtils.addAll(new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft,
				keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat,
				keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective,
				keyBindSmoothCamera, keyBindSprint, keyBindStreamStartStop, keyBindStreamPauseUnpause,
				keyBindStreamCommercials, keyBindStreamToggleMic, keyBindFullscreen, keyBindSpectatorOutlines }, keyBindsHotbar);
		difficulty = EnumDifficulty.NORMAL;
		lastServer = "";
		fovSetting = 70.0F;
		language = "en_US";
		forceUnicodeFont = false;
		mc = mcIn;
		optionsFile = new File(p_i46326_2_, "options.txt");
		optionsFileOF = new File(p_i46326_2_, "optionsof.txt");
		limitFramerate = (int) GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
		ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
		keyBindings = ArrayUtils.add(keyBindings, ofKeyBindZoom);
		GameSettings.Options.RENDER_DISTANCE.setValueMax(32.0F);
		renderDistanceChunks = 8;
		loadOptions();
		Config.initGameSettings(this);
	}

	public GameSettings() {
		chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		chatColours = true;
		chatLinks = true;
		chatLinksPrompt = true;
		chatOpacity = 1.0F;
		snooperEnabled = true;
		enableVsync = true;
		field_178881_t = false;
		field_178880_u = true;
		field_178879_v = false;
		pauseOnLostFocus = true;
		field_178882_aU = Sets.newHashSet(EnumPlayerModelParts.values());
		heldItemTooltips = true;
		chatScale = 1.0F;
		chatWidth = 1.0F;
		chatHeightUnfocused = 0.44366196F;
		chatHeightFocused = 1.0F;
		showInventoryAchievementHint = true;
		mipmapLevels = 4;
		mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
		streamBytesPerPixel = 0.5F;
		streamMicVolume = 1.0F;
		streamGameVolume = 1.0F;
		streamKbps = 0.5412844F;
		streamFps = 0.31690142F;
		streamCompression = 1;
		streamSendMetadata = true;
		streamPreferredServer = "";
		streamChatEnabled = 0;
		streamChatUserFilter = 0;
		streamMicToggleBehavior = 0;
		keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
		keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
		keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
		keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
		keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
		keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
		keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
		keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
		keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
		keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
		keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
		keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
		keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
		keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
		keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
		keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
		keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
		keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
		keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
		keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
		keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
		keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
		keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
		keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
		keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
				new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
				new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
				new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
				new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
				new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
				new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
				new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
				new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
		keyBindings = ArrayUtils.addAll(new KeyBinding[] { keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft,
				keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat,
				keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective,
				keyBindSmoothCamera, keyBindSprint, keyBindStreamStartStop, keyBindStreamPauseUnpause,
				keyBindStreamCommercials, keyBindStreamToggleMic, keyBindFullscreen, keyBindSpectatorOutlines }, keyBindsHotbar);
		difficulty = EnumDifficulty.NORMAL;
		lastServer = "";
		fovSetting = 70.0F;
		language = "en_US";
		forceUnicodeFont = false;
	}

	/**
	 * Represents a key or mouse button as a string. Args: key
	 */
	public static String getKeyDisplayString(final int p_74298_0_) {
		return p_74298_0_ < 0 ? I18n.format("key.mouseButton", new Object[] { p_74298_0_ + 101})
				: p_74298_0_ < 256 ? Keyboard.getKeyName(p_74298_0_)
						: String.format("%c", new Object[] { (char) (p_74298_0_ - 256)})
								.toUpperCase();
	}

	/**
	 * Returns whether the specified key binding is currently being pressed.
	 */
	public static boolean isKeyDown(final KeyBinding p_100015_0_) {
		final int keyCode = p_100015_0_.getKeyCode();
		return keyCode >= -100 && keyCode <= 255 ? p_100015_0_.getKeyCode() == 0 ? false
				: p_100015_0_.getKeyCode() < 0 ? Mouse.isButtonDown(p_100015_0_.getKeyCode() + 100)
						: Keyboard.isKeyDown(p_100015_0_.getKeyCode())
				: false;
	}

	/**
	 * Sets a key binding and then saves all settings.
	 */
	public void setOptionKeyBinding(final KeyBinding p_151440_1_, final int p_151440_2_) {
		p_151440_1_.setKeyCode(p_151440_2_);
		saveOptions();
	}

	/**
	 * If the specified option is controlled by a slider (float value), this
	 * will set the float value.
	 */
	public void setOptionFloatValue(final GameSettings.Options p_74304_1_, final float p_74304_2_) {
		setOptionFloatValueOF(p_74304_1_, p_74304_2_);

		if (p_74304_1_ == GameSettings.Options.SENSITIVITY) {
			mouseSensitivity = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.FOV) {
			fovSetting = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.GAMMA) {
			gammaSetting = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.FRAMERATE_LIMIT) {
			limitFramerate = (int) p_74304_2_;
			enableVsync = false;

			if (limitFramerate <= 0) {
				limitFramerate = (int) GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
				enableVsync = true;
			}

			updateVSync();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_OPACITY) {
			chatOpacity = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
			chatHeightFocused = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
			chatHeightUnfocused = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_WIDTH) {
			chatWidth = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_SCALE) {
			chatScale = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.MIPMAP_LEVELS) {
			final int var3 = mipmapLevels;
			mipmapLevels = (int) p_74304_2_;

			if (var3 != p_74304_2_) {
				mc.getTextureMapBlocks().setMipmapLevels(mipmapLevels);
				Minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
				mc.getTextureMapBlocks().func_174937_a(false, mipmapLevels > 0);
				mc.func_175603_A();
			}
		}

		if (p_74304_1_ == GameSettings.Options.BLOCK_ALTERNATIVES) {
			field_178880_u = !field_178880_u;
			mc.renderGlobal.loadRenderers();
		}

		if (p_74304_1_ == GameSettings.Options.RENDER_DISTANCE) {
			renderDistanceChunks = (int) p_74304_2_;
			mc.renderGlobal.func_174979_m();
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL) {
			streamBytesPerPixel = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_VOLUME_MIC) {
			streamMicVolume = p_74304_2_;
			mc.getTwitchStream().func_152915_s();
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_VOLUME_SYSTEM) {
			streamGameVolume = p_74304_2_;
			mc.getTwitchStream().func_152915_s();
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_KBPS) {
			streamKbps = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_FPS) {
			streamFps = p_74304_2_;
		}
	}

	/**
	 * For non-float options. Toggles the option on/off, or cycles through the
	 * list i.e. render distances.
	 */
	public void setOptionValue(final GameSettings.Options p_74306_1_, final int p_74306_2_) {
		setOptionValueOF(p_74306_1_, p_74306_2_);

		if (p_74306_1_ == GameSettings.Options.INVERT_MOUSE) {
			invertMouse = !invertMouse;
		}

		if (p_74306_1_ == GameSettings.Options.GUI_SCALE) {
			guiScale = guiScale + p_74306_2_ & 3;
		}

		if (p_74306_1_ == GameSettings.Options.PARTICLES) {
			particleSetting = (particleSetting + p_74306_2_) % 3;
		}

		if (p_74306_1_ == GameSettings.Options.VIEW_BOBBING) {
			viewBobbing = !viewBobbing;
		}

		if (p_74306_1_ == GameSettings.Options.RENDER_CLOUDS) {
			clouds = !clouds;
		}

		if (p_74306_1_ == GameSettings.Options.FORCE_UNICODE_FONT) {
			forceUnicodeFont = !forceUnicodeFont;
			mc.fontRendererObj.setUnicodeFlag(mc.getLanguageManager().isCurrentLocaleUnicode() || forceUnicodeFont);
		}

		if (p_74306_1_ == GameSettings.Options.FBO_ENABLE) {
			fboEnable = !fboEnable;
		}

		if (p_74306_1_ == GameSettings.Options.ANAGLYPH) {
			anaglyph = !anaglyph;
			mc.refreshResources();
		}

		if (p_74306_1_ == GameSettings.Options.GRAPHICS) {
			fancyGraphics = !fancyGraphics;
			updateRenderClouds();
			mc.renderGlobal.loadRenderers();
		}

		if (p_74306_1_ == GameSettings.Options.AMBIENT_OCCLUSION) {
			ambientOcclusion = (ambientOcclusion + p_74306_2_) % 3;
			mc.renderGlobal.loadRenderers();
		}

		if (p_74306_1_ == GameSettings.Options.CHAT_VISIBILITY) {
			chatVisibility = EntityPlayer.EnumChatVisibility
					.getEnumChatVisibility((chatVisibility.getChatVisibility() + p_74306_2_) % 3);
		}

		if (p_74306_1_ == GameSettings.Options.STREAM_COMPRESSION) {
			streamCompression = (streamCompression + p_74306_2_) % 3;
		}

		if (p_74306_1_ == GameSettings.Options.STREAM_SEND_METADATA) {
			streamSendMetadata = !streamSendMetadata;
		}

		if (p_74306_1_ == GameSettings.Options.STREAM_CHAT_ENABLED) {
			streamChatEnabled = (streamChatEnabled + p_74306_2_) % 3;
		}

		if (p_74306_1_ == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
			streamChatUserFilter = (streamChatUserFilter + p_74306_2_) % 3;
		}

		if (p_74306_1_ == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
			streamMicToggleBehavior = (streamMicToggleBehavior + p_74306_2_) % 2;
		}

		if (p_74306_1_ == GameSettings.Options.CHAT_COLOR) {
			chatColours = !chatColours;
		}

		if (p_74306_1_ == GameSettings.Options.CHAT_LINKS) {
			chatLinks = !chatLinks;
		}

		if (p_74306_1_ == GameSettings.Options.CHAT_LINKS_PROMPT) {
			chatLinksPrompt = !chatLinksPrompt;
		}

		if (p_74306_1_ == GameSettings.Options.SNOOPER_ENABLED) {
			snooperEnabled = !snooperEnabled;
		}

		if (p_74306_1_ == GameSettings.Options.TOUCHSCREEN) {
			touchscreen = !touchscreen;
		}

		if (p_74306_1_ == GameSettings.Options.USE_FULLSCREEN) {
			fullScreen = !fullScreen;

			if (mc.isFullScreen() != fullScreen) {
				mc.toggleFullscreen();
			}
		}

		if (p_74306_1_ == GameSettings.Options.ENABLE_VSYNC) {
			enableVsync = !enableVsync;
			Display.setVSyncEnabled(enableVsync);
		}

		if (p_74306_1_ == GameSettings.Options.USE_VBO) {
			field_178881_t = !field_178881_t;
			mc.renderGlobal.loadRenderers();
		}

		if (p_74306_1_ == GameSettings.Options.BLOCK_ALTERNATIVES) {
			field_178880_u = !field_178880_u;
			mc.renderGlobal.loadRenderers();
		}

		if (p_74306_1_ == GameSettings.Options.REDUCED_DEBUG_INFO) {
			field_178879_v = !field_178879_v;
		}

		saveOptions();
	}

	public float getOptionFloatValue(final GameSettings.Options p_74296_1_) {
		return p_74296_1_ == GameSettings.Options.CLOUD_HEIGHT ? ofCloudsHeight
				: p_74296_1_ == GameSettings.Options.AO_LEVEL ? ofAoLevel
						: p_74296_1_ == GameSettings.Options.AA_LEVEL ? (float) ofAaLevel
								: p_74296_1_ == GameSettings.Options.AF_LEVEL ? (float) ofAfLevel
										: p_74296_1_ == GameSettings.Options.MIPMAP_TYPE ? (float) ofMipmapType
												: p_74296_1_ == GameSettings.Options.FRAMERATE_LIMIT
														? limitFramerate == GameSettings.Options.FRAMERATE_LIMIT
																.getValueMax() && enableVsync ? 0.0F
																		: (float) limitFramerate
														: p_74296_1_ == GameSettings.Options.FOV ? fovSetting
																: p_74296_1_ == GameSettings.Options.GAMMA
																		? gammaSetting
																		: p_74296_1_ == GameSettings.Options.SATURATION
																				? saturation
																				: p_74296_1_ == GameSettings.Options.SENSITIVITY
																						? mouseSensitivity
																						: p_74296_1_ == GameSettings.Options.CHAT_OPACITY
																								? chatOpacity
																								: p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED
																										? chatHeightFocused
																										: p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED
																												? chatHeightUnfocused
																												: p_74296_1_ == GameSettings.Options.CHAT_SCALE
																														? chatScale
																														: p_74296_1_ == GameSettings.Options.CHAT_WIDTH
																																? chatWidth
																																: p_74296_1_ == GameSettings.Options.FRAMERATE_LIMIT
																																		? (float) limitFramerate
																																		: p_74296_1_ == GameSettings.Options.MIPMAP_LEVELS
																																				? (float) mipmapLevels
																																				: p_74296_1_ == GameSettings.Options.RENDER_DISTANCE
																																						? (float) renderDistanceChunks
																																						: p_74296_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL
																																								? streamBytesPerPixel
																																								: p_74296_1_ == GameSettings.Options.STREAM_VOLUME_MIC
																																										? streamMicVolume
																																										: p_74296_1_ == GameSettings.Options.STREAM_VOLUME_SYSTEM
																																												? streamGameVolume
																																												: p_74296_1_ == GameSettings.Options.STREAM_KBPS
																																														? streamKbps
																																														: p_74296_1_ == GameSettings.Options.STREAM_FPS
																																																? streamFps
																																																: 0.0F;
	}

	public boolean getOptionOrdinalValue(final GameSettings.Options p_74308_1_) {
		switch (GameSettings.SwitchOptions.optionIds[p_74308_1_.ordinal()]) {
			case 1:
				return invertMouse;

			case 2:
				return viewBobbing;

			case 3:
				return anaglyph;

			case 4:
				return fboEnable;

			case 5:
				return clouds;

			case 6:
				return chatColours;

			case 7:
				return chatLinks;

			case 8:
				return chatLinksPrompt;

			case 9:
				return snooperEnabled;

			case 10:
				return fullScreen;

			case 11:
				return enableVsync;

			case 12:
				return field_178881_t;

			case 13:
				return touchscreen;

			case 14:
				return streamSendMetadata;

			case 15:
				return forceUnicodeFont;

			case 16:
				return field_178880_u;

			case 17:
				return field_178879_v;

			default:
				return false;
		}
	}

	/**
	 * Returns the translation of the given index in the given String array. If
	 * the index is smaller than 0 or greater than/equal to the length of the
	 * String array, it is changed to 0.
	 */
	private static String getTranslation(final String[] p_74299_0_, int p_74299_1_) {
		if (p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length) {
			p_74299_1_ = 0;
		}

		return I18n.format(p_74299_0_[p_74299_1_], new Object[0]);
	}

	/**
	 * Gets a key binding.
	 */
	public String getKeyBinding(final GameSettings.Options p_74297_1_) {
		final String strOF = getKeyBindingOF(p_74297_1_);

		if (strOF != null) {
			return strOF;
		} else {
			final String var2 = I18n.format(p_74297_1_.getEnumString(), new Object[0]) + ": ";

			if (p_74297_1_.getEnumFloat()) {
				final float var32 = getOptionFloatValue(p_74297_1_);
				final float var4 = p_74297_1_.normalizeValue(var32);
				return p_74297_1_ == GameSettings.Options.SENSITIVITY
						? var4 == 0.0F ? var2 + I18n.format("options.sensitivity.min", new Object[0])
								: var4 == 1.0F ? var2 + I18n.format("options.sensitivity.max", new Object[0])
										: var2 + (int) (var4 * 200.0F) + "%"
						: p_74297_1_ == GameSettings.Options.FOV
								? var32 == 70.0F ? var2 + I18n.format("options.fov.min", new Object[0])
										: var32 == 110.0F ? var2 + I18n.format("options.fov.max", new Object[0])
												: var2 + (int) var32
								: p_74297_1_ == GameSettings.Options.FRAMERATE_LIMIT
										? var32 == p_74297_1_.valueMax
												? var2 + I18n.format("options.framerateLimit.max", new Object[0])
												: var2 + (int) var32 + " fps"
										: p_74297_1_ == GameSettings.Options.RENDER_CLOUDS
												? var32 == p_74297_1_.valueMin
														? var2 + I18n.format("options.cloudHeight.min", new Object[0])
														: var2 + ((int) var32 + 128)
												: p_74297_1_ == GameSettings.Options.GAMMA
														? var4 == 0.0F
																? var2 + I18n.format("options.gamma.min", new Object[0])
																: var4 == 1.0F
																		? var2 + I18n.format("options.gamma.max",
																				new Object[0])
																		: var2 + "+" + (int) (var4 * 100.0F) + "%"
														: p_74297_1_ == GameSettings.Options.SATURATION
																? var2 + (int) (var4 * 400.0F) + "%"
																: p_74297_1_ == GameSettings.Options.CHAT_OPACITY
																		? var2 + (int) (var4 * 90.0F + 10.0F) + "%"
																		: p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED
																				? var2 + GuiNewChat
																						.calculateChatboxHeight(var4)
																						+ "px"
																				: p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED
																						? var2 + GuiNewChat
																								.calculateChatboxHeight(
																										var4)
																								+ "px"
																						: p_74297_1_ == GameSettings.Options.CHAT_WIDTH
																								? var2 + GuiNewChat
																										.calculateChatboxWidth(
																												var4)
																										+ "px"
																								: p_74297_1_ == GameSettings.Options.RENDER_DISTANCE
																										? var2 + (int) var32
																												+ " chunks"
																										: p_74297_1_ == GameSettings.Options.MIPMAP_LEVELS
																												? var32 == 0.0F
																														? var2 + I18n
																																.format("options.off",
																																		new Object[0])
																														: var2 + (int) var32
																												: p_74297_1_ == GameSettings.Options.STREAM_FPS
																														? var2 + TwitchStream
																																.func_152948_a(
																																		var4)
																																+ " fps"
																														: p_74297_1_ == GameSettings.Options.STREAM_KBPS
																																? var2 + TwitchStream
																																		.func_152946_b(
																																				var4)
																																		+ " Kbps"
																																: p_74297_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL
																																		? var2 + String
																																				.format("%.3f bpp",
																																						new Object[] {
                                                                                                                                                                                                                                                                                                                    TwitchStream
                                                                                                                                                                                                                                                                                                                            .func_152947_c(
                                                                                                                                                                                                                                                                                                                                    var4) })
																																		: var4 == 0.0F
																																				? var2 + I18n
																																						.format("options.off",
																																								new Object[0])
																																				: var2 + (int) (var4
																																						* 100.0F)
																																						+ "%";
			} else if (p_74297_1_.getEnumBoolean()) {
				final boolean var31 = getOptionOrdinalValue(p_74297_1_);
				return var31 ? var2 + I18n.format("options.on", new Object[0])
						: var2 + I18n.format("options.off", new Object[0]);
			} else if (p_74297_1_ == GameSettings.Options.GUI_SCALE) {
				return var2 + getTranslation(GUISCALES, guiScale);
			} else if (p_74297_1_ == GameSettings.Options.CHAT_VISIBILITY) {
				return var2 + I18n.format(chatVisibility.getResourceKey(), new Object[0]);
			} else if (p_74297_1_ == GameSettings.Options.PARTICLES) {
				return var2 + getTranslation(PARTICLES, particleSetting);
			} else if (p_74297_1_ == GameSettings.Options.AMBIENT_OCCLUSION) {
				return var2 + getTranslation(AMBIENT_OCCLUSIONS, ambientOcclusion);
			} else if (p_74297_1_ == GameSettings.Options.STREAM_COMPRESSION) {
				return var2 + getTranslation(STREAM_COMPRESSIONS, streamCompression);
			} else if (p_74297_1_ == GameSettings.Options.STREAM_CHAT_ENABLED) {
				return var2 + getTranslation(STREAM_CHAT_MODES, streamChatEnabled);
			} else if (p_74297_1_ == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
				return var2 + getTranslation(STREAM_CHAT_FILTER_MODES, streamChatUserFilter);
			} else if (p_74297_1_ == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
				return var2 + getTranslation(STREAM_MIC_MODES, streamMicToggleBehavior);
			} else if (p_74297_1_ == GameSettings.Options.GRAPHICS) {
				if (fancyGraphics) {
					return var2 + I18n.format("options.graphics.fancy", new Object[0]);
				} else {
					return var2 + I18n.format("options.graphics.fast", new Object[0]);
				}
			} else {
				return var2;
			}
		}
	}

	/**
	 * Loads the options from the options file. It appears that this has
	 * replaced the previous 'loadOptions'
	 */
	public void loadOptions() {
		try {
			if (!optionsFile.exists()) {
				return;
			}

			final BufferedReader var9 = new BufferedReader(new FileReader(optionsFile));
			String var2 = "";
			mapSoundLevels.clear();

			while ((var2 = var9.readLine()) != null) {
				try {
					final String[] var8 = var2.split(":");

					if (var8[0].equals("mouseSensitivity")) {
						mouseSensitivity = parseFloat(var8[1]);
					}

					if (var8[0].equals("fov")) {
						fovSetting = parseFloat(var8[1]) * 40.0F + 70.0F;
					}

					if (var8[0].equals("gamma")) {
						gammaSetting = parseFloat(var8[1]);
					}

					if (var8[0].equals("saturation")) {
						saturation = parseFloat(var8[1]);
					}

					if (var8[0].equals("invertYMouse")) {
						invertMouse = var8[1].equals("true");
					}

					if (var8[0].equals("renderDistance")) {
						renderDistanceChunks = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("guiScale")) {
						guiScale = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("particles")) {
						particleSetting = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("bobView")) {
						viewBobbing = var8[1].equals("true");
					}

					if (var8[0].equals("anaglyph3d")) {
						anaglyph = var8[1].equals("true");
					}

					if (var8[0].equals("maxFps")) {
						limitFramerate = Integer.parseInt(var8[1]);
						enableVsync = false;

						if (limitFramerate <= 0) {
							limitFramerate = (int) GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
							enableVsync = true;
						}

						updateVSync();
					}

					if (var8[0].equals("fboEnable")) {
						fboEnable = var8[1].equals("true");
					}

					if (var8[0].equals("difficulty")) {
						difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var8[1]));
					}

					if (var8[0].equals("fancyGraphics")) {
						fancyGraphics = var8[1].equals("true");
						updateRenderClouds();
					}

					if (var8[0].equals("ao")) {
						if (var8[1].equals("true")) {
							ambientOcclusion = 2;
						} else if (var8[1].equals("false")) {
							ambientOcclusion = 0;
						} else {
							ambientOcclusion = Integer.parseInt(var8[1]);
						}
					}

					if (var8[0].equals("renderClouds")) {
						clouds = var8[1].equals("true");
					}

					if (var8[0].equals("resourcePacks")) {
						resourcePacks = (List) gson.fromJson(var2.substring(var2.indexOf(58) + 1), typeListString);

						if (resourcePacks == null) {
							resourcePacks = Lists.newArrayList();
						}
					}

					if (var8[0].equals("lastServer") && var8.length >= 2) {
						lastServer = var2.substring(var2.indexOf(58) + 1);
					}

					if (var8[0].equals("lang") && var8.length >= 2) {
						language = var8[1];
					}

					if (var8[0].equals("chatVisibility")) {
						chatVisibility = EntityPlayer.EnumChatVisibility
								.getEnumChatVisibility(Integer.parseInt(var8[1]));
					}

					if (var8[0].equals("chatColors")) {
						chatColours = var8[1].equals("true");
					}

					if (var8[0].equals("chatLinks")) {
						chatLinks = var8[1].equals("true");
					}

					if (var8[0].equals("chatLinksPrompt")) {
						chatLinksPrompt = var8[1].equals("true");
					}

					if (var8[0].equals("chatOpacity")) {
						chatOpacity = parseFloat(var8[1]);
					}

					if (var8[0].equals("snooperEnabled")) {
						snooperEnabled = var8[1].equals("true");
					}

					if (var8[0].equals("fullscreen")) {
						fullScreen = var8[1].equals("true");
					}

					if (var8[0].equals("enableVsync")) {
						enableVsync = var8[1].equals("true");
						updateVSync();
					}

					if (var8[0].equals("useVbo")) {
						field_178881_t = var8[1].equals("true");
					}

					if (var8[0].equals("hideServerAddress")) {
						hideServerAddress = var8[1].equals("true");
					}

					if (var8[0].equals("advancedItemTooltips")) {
						advancedItemTooltips = var8[1].equals("true");
					}

					if (var8[0].equals("pauseOnLostFocus")) {
						pauseOnLostFocus = var8[1].equals("true");
					}

					if (var8[0].equals("touchscreen")) {
						touchscreen = var8[1].equals("true");
					}

					if (var8[0].equals("overrideHeight")) {
						overrideHeight = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("overrideWidth")) {
						overrideWidth = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("heldItemTooltips")) {
						heldItemTooltips = var8[1].equals("true");
					}

					if (var8[0].equals("chatHeightFocused")) {
						chatHeightFocused = parseFloat(var8[1]);
					}

					if (var8[0].equals("chatHeightUnfocused")) {
						chatHeightUnfocused = parseFloat(var8[1]);
					}

					if (var8[0].equals("chatScale")) {
						chatScale = parseFloat(var8[1]);
					}

					if (var8[0].equals("chatWidth")) {
						chatWidth = parseFloat(var8[1]);
					}

					if (var8[0].equals("showInventoryAchievementHint")) {
						showInventoryAchievementHint = var8[1].equals("true");
					}

					if (var8[0].equals("mipmapLevels")) {
						mipmapLevels = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("streamBytesPerPixel")) {
						streamBytesPerPixel = parseFloat(var8[1]);
					}

					if (var8[0].equals("streamMicVolume")) {
						streamMicVolume = parseFloat(var8[1]);
					}

					if (var8[0].equals("streamSystemVolume")) {
						streamGameVolume = parseFloat(var8[1]);
					}

					if (var8[0].equals("streamKbps")) {
						streamKbps = parseFloat(var8[1]);
					}

					if (var8[0].equals("streamFps")) {
						streamFps = parseFloat(var8[1]);
					}

					if (var8[0].equals("streamCompression")) {
						streamCompression = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("streamSendMetadata")) {
						streamSendMetadata = var8[1].equals("true");
					}

					if (var8[0].equals("streamPreferredServer") && var8.length >= 2) {
						streamPreferredServer = var2.substring(var2.indexOf(58) + 1);
					}

					if (var8[0].equals("streamChatEnabled")) {
						streamChatEnabled = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("streamChatUserFilter")) {
						streamChatUserFilter = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("streamMicToggleBehavior")) {
						streamMicToggleBehavior = Integer.parseInt(var8[1]);
					}

					if (var8[0].equals("forceUnicodeFont")) {
						forceUnicodeFont = var8[1].equals("true");
					}

					if (var8[0].equals("allowBlockAlternatives")) {
						field_178880_u = var8[1].equals("true");
					}

					if (var8[0].equals("reducedDebugInfo")) {
						field_178879_v = var8[1].equals("true");
					}

					final KeyBinding[] var4 = keyBindings;
					int var5 = var4.length;
					int var6;

					for (var6 = 0; var6 < var5; ++var6) {
						final KeyBinding var10 = var4[var6];

						if (var8[0].equals("key_" + var10.getKeyDescription())) {
							var10.setKeyCode(Integer.parseInt(var8[1]));
						}
					}

					final SoundCategory[] var12 = SoundCategory.values();
					var5 = var12.length;

					for (var6 = 0; var6 < var5; ++var6) {
						final SoundCategory var11 = var12[var6];

						if (var8[0].equals("soundCategory_" + var11.getCategoryName())) {
							mapSoundLevels.put(var11, parseFloat(var8[1]));
						}
					}

					final EnumPlayerModelParts[] var131 = EnumPlayerModelParts.values();
					var5 = var131.length;

					for (var6 = 0; var6 < var5; ++var6) {
						final EnumPlayerModelParts var13 = var131[var6];

						if (var8[0].equals("modelPart_" + var13.func_179329_c())) {
							func_178878_a(var13, var8[1].equals("true"));
						}
					}
				} catch (final Exception var101) {
					logger.warn("Skipping bad option: " + var2);
					var101.printStackTrace();
				}
			}

			KeyBinding.resetKeyBindingArrayAndHash();
			var9.close();
		} catch (final Exception var111) {
			logger.error("Failed to load options", var111);
		}

		loadOfOptions();
	}

	/**
	 * Parses a string into a float.
	 */
	private float parseFloat(final String p_74305_1_) {
		return p_74305_1_.equals("true") ? 1.0F : p_74305_1_.equals("false") ? 0.0F : Float.parseFloat(p_74305_1_);
	}

	/**
	 * Saves the options to the options file.
	 */
	public void saveOptions() {
		if (Reflector.FMLClientHandler.exists()) {
			final Object var6 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);

			if (var6 != null && Reflector.callBoolean(var6, Reflector.FMLClientHandler_isLoading, new Object[0])) {
				return;
			}
		}

		try {
			final PrintWriter var9 = new PrintWriter(new FileWriter(optionsFile));
			var9.println("invertYMouse:" + invertMouse);
			var9.println("mouseSensitivity:" + mouseSensitivity);
			var9.println("fov:" + (fovSetting - 70.0F) / 40.0F);
			var9.println("gamma:" + gammaSetting);
			var9.println("saturation:" + saturation);
			var9.println("renderDistance:" + renderDistanceChunks);
			var9.println("guiScale:" + guiScale);
			var9.println("particles:" + particleSetting);
			var9.println("bobView:" + viewBobbing);
			var9.println("anaglyph3d:" + anaglyph);
			var9.println("maxFps:" + limitFramerate);
			var9.println("fboEnable:" + fboEnable);
			var9.println("difficulty:" + difficulty.getDifficultyId());
			var9.println("fancyGraphics:" + fancyGraphics);
			var9.println("ao:" + ambientOcclusion);
			var9.println("renderClouds:" + clouds);
			var9.println("resourcePacks:" + gson.toJson(resourcePacks));
			var9.println("lastServer:" + lastServer);
			var9.println("lang:" + language);
			var9.println("chatVisibility:" + chatVisibility.getChatVisibility());
			var9.println("chatColors:" + chatColours);
			var9.println("chatLinks:" + chatLinks);
			var9.println("chatLinksPrompt:" + chatLinksPrompt);
			var9.println("chatOpacity:" + chatOpacity);
			var9.println("snooperEnabled:" + snooperEnabled);
			var9.println("fullscreen:" + fullScreen);
			var9.println("enableVsync:" + enableVsync);
			var9.println("useVbo:" + field_178881_t);
			var9.println("hideServerAddress:" + hideServerAddress);
			var9.println("advancedItemTooltips:" + advancedItemTooltips);
			var9.println("pauseOnLostFocus:" + pauseOnLostFocus);
			var9.println("touchscreen:" + touchscreen);
			var9.println("overrideWidth:" + overrideWidth);
			var9.println("overrideHeight:" + overrideHeight);
			var9.println("heldItemTooltips:" + heldItemTooltips);
			var9.println("chatHeightFocused:" + chatHeightFocused);
			var9.println("chatHeightUnfocused:" + chatHeightUnfocused);
			var9.println("chatScale:" + chatScale);
			var9.println("chatWidth:" + chatWidth);
			var9.println("showInventoryAchievementHint:" + showInventoryAchievementHint);
			var9.println("mipmapLevels:" + mipmapLevels);
			var9.println("streamBytesPerPixel:" + streamBytesPerPixel);
			var9.println("streamMicVolume:" + streamMicVolume);
			var9.println("streamSystemVolume:" + streamGameVolume);
			var9.println("streamKbps:" + streamKbps);
			var9.println("streamFps:" + streamFps);
			var9.println("streamCompression:" + streamCompression);
			var9.println("streamSendMetadata:" + streamSendMetadata);
			var9.println("streamPreferredServer:" + streamPreferredServer);
			var9.println("streamChatEnabled:" + streamChatEnabled);
			var9.println("streamChatUserFilter:" + streamChatUserFilter);
			var9.println("streamMicToggleBehavior:" + streamMicToggleBehavior);
			var9.println("forceUnicodeFont:" + forceUnicodeFont);
			var9.println("allowBlockAlternatives:" + field_178880_u);
			var9.println("reducedDebugInfo:" + field_178879_v);
			final KeyBinding[] var2 = keyBindings;
			int var3 = var2.length;
			int var4;

			for (var4 = 0; var4 < var3; ++var4) {
				final KeyBinding var7 = var2[var4];
				var9.println("key_" + var7.getKeyDescription() + ":" + var7.getKeyCode());
			}

			final SoundCategory[] var101 = SoundCategory.values();
			var3 = var101.length;

			for (var4 = 0; var4 < var3; ++var4) {
				final SoundCategory var8 = var101[var4];
				var9.println("soundCategory_" + var8.getCategoryName() + ":" + getSoundLevel(var8));
			}

			final EnumPlayerModelParts[] var11 = EnumPlayerModelParts.values();
			var3 = var11.length;

			for (var4 = 0; var4 < var3; ++var4) {
				final EnumPlayerModelParts var10 = var11[var4];
				var9.println("modelPart_" + var10.func_179329_c() + ":" + field_178882_aU.contains(var10));
			}

			var9.close();
		} catch (final Exception var81) {
			logger.error("Failed to save options", var81);
		}

		saveOfOptions();
		sendSettingsToServer();
	}

	public float getSoundLevel(final SoundCategory p_151438_1_) {
		return mapSoundLevels.containsKey(p_151438_1_) ? ((Float) mapSoundLevels.get(p_151438_1_)) : 1.0F;
	}

	public void setSoundLevel(final SoundCategory p_151439_1_, final float p_151439_2_) {
		mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
		mapSoundLevels.put(p_151439_1_, p_151439_2_);
	}

	/**
	 * Send a client info packet with settings information to the server
	 */
	public void sendSettingsToServer() {
		if (Minecraft.thePlayer != null) {
			int var1 = 0;
			EnumPlayerModelParts var3;

			for (final Iterator var2 = field_178882_aU.iterator(); var2.hasNext(); var1 |= var3.func_179327_a()) {
				var3 = (EnumPlayerModelParts) var2.next();
			}

			Minecraft.thePlayer.sendQueue.addToSendQueue(
					new C15PacketClientSettings(language, renderDistanceChunks, chatVisibility, chatColours, var1));
		}
	}

	public Set func_178876_d() {
		return ImmutableSet.copyOf(field_178882_aU);
	}

	public void func_178878_a(final EnumPlayerModelParts p_178878_1_, final boolean p_178878_2_) {
		if (p_178878_2_) {
			field_178882_aU.add(p_178878_1_);
		} else {
			field_178882_aU.remove(p_178878_1_);
		}

		sendSettingsToServer();
	}

	public void func_178877_a(final EnumPlayerModelParts p_178877_1_) {
		if (!func_178876_d().contains(p_178877_1_)) {
			field_178882_aU.add(p_178877_1_);
		} else {
			field_178882_aU.remove(p_178877_1_);
		}

		sendSettingsToServer();
	}

	/**
	 * Should render clouds
	 */
	public boolean shouldRenderClouds() {
		return renderDistanceChunks >= 4 && clouds;
	}

	private void setOptionFloatValueOF(final GameSettings.Options option, final float val) {
		if (option == GameSettings.Options.CLOUD_HEIGHT) {
			ofCloudsHeight = val;
			mc.renderGlobal.resetClouds();
		}

		if (option == GameSettings.Options.AO_LEVEL) {
			ofAoLevel = val;
			mc.renderGlobal.loadRenderers();
		}

		int valInt;

		if (option == GameSettings.Options.AA_LEVEL) {
			valInt = (int) val;

			if (valInt > 0 && Config.isShaders()) {
				Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
				return;
			}

			final int[] aaLevels = new int[] { 0, 2, 4, 6, 8, 12, 16 };
			ofAaLevel = 0;

			for (final int aaLevel : aaLevels) {
				if (valInt >= aaLevel) {
					ofAaLevel = aaLevel;
				}
			}

			ofAaLevel = Config.limit(ofAaLevel, 0, 16);
		}

		if (option == GameSettings.Options.AF_LEVEL) {
			valInt = (int) val;

			if (valInt > 1 && Config.isShaders()) {
				Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
				return;
			}

			for (ofAfLevel = 1; ofAfLevel * 2 <= valInt; ofAfLevel *= 2) {
			}

			ofAfLevel = Config.limit(ofAfLevel, 1, 16);
			mc.refreshResources();
		}

		if (option == GameSettings.Options.MIPMAP_TYPE) {
			valInt = (int) val;
			ofMipmapType = Config.limit(valInt, 0, 3);
			mc.refreshResources();
		}
	}

	private void setOptionValueOF(final GameSettings.Options par1EnumOptions, final int par2) {
		if (par1EnumOptions == GameSettings.Options.FOG_FANCY) {
			switch (ofFogType) {
				case 1:
					ofFogType = 2;

					if (!Config.isFancyFogAvailable()) {
						ofFogType = 3;
					}

					break;

				case 2:
					ofFogType = 3;
					break;

				case 3:
					ofFogType = 1;
					break;

				default:
					ofFogType = 1;
			}
		}

		if (par1EnumOptions == GameSettings.Options.FOG_START) {
			ofFogStart += 0.2F;

			if (ofFogStart > 0.81F) {
				ofFogStart = 0.2F;
			}
		}

		if (par1EnumOptions == GameSettings.Options.SMOOTH_FPS) {
			ofSmoothFps = !ofSmoothFps;
		}

		if (par1EnumOptions == GameSettings.Options.SMOOTH_WORLD) {
			ofSmoothWorld = !ofSmoothWorld;
			Config.updateThreadPriorities();
		}

		if (par1EnumOptions == GameSettings.Options.CLOUDS) {
			++ofClouds;

			if (ofClouds > 3) {
				ofClouds = 0;
			}

			updateRenderClouds();
			mc.renderGlobal.resetClouds();
		}

		if (par1EnumOptions == GameSettings.Options.TREES) {
			ofTrees = nextValue(ofTrees, OF_TREES_VALUES);
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.DROPPED_ITEMS) {
			++ofDroppedItems;

			if (ofDroppedItems > 2) {
				ofDroppedItems = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.RAIN) {
			++ofRain;

			if (ofRain > 3) {
				ofRain = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_WATER) {
			++ofAnimatedWater;

			if (ofAnimatedWater == 1) {
				++ofAnimatedWater;
			}

			if (ofAnimatedWater > 2) {
				ofAnimatedWater = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_LAVA) {
			++ofAnimatedLava;

			if (ofAnimatedLava == 1) {
				++ofAnimatedLava;
			}

			if (ofAnimatedLava > 2) {
				ofAnimatedLava = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_FIRE) {
			ofAnimatedFire = !ofAnimatedFire;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_PORTAL) {
			ofAnimatedPortal = !ofAnimatedPortal;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_REDSTONE) {
			ofAnimatedRedstone = !ofAnimatedRedstone;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_EXPLOSION) {
			ofAnimatedExplosion = !ofAnimatedExplosion;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_FLAME) {
			ofAnimatedFlame = !ofAnimatedFlame;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_SMOKE) {
			ofAnimatedSmoke = !ofAnimatedSmoke;
		}

		if (par1EnumOptions == GameSettings.Options.VOID_PARTICLES) {
			ofVoidParticles = !ofVoidParticles;
		}

		if (par1EnumOptions == GameSettings.Options.WATER_PARTICLES) {
			ofWaterParticles = !ofWaterParticles;
		}

		if (par1EnumOptions == GameSettings.Options.PORTAL_PARTICLES) {
			ofPortalParticles = !ofPortalParticles;
		}

		if (par1EnumOptions == GameSettings.Options.POTION_PARTICLES) {
			ofPotionParticles = !ofPotionParticles;
		}

		if (par1EnumOptions == GameSettings.Options.FIREWORK_PARTICLES) {
			ofFireworkParticles = !ofFireworkParticles;
		}

		if (par1EnumOptions == GameSettings.Options.DRIPPING_WATER_LAVA) {
			ofDrippingWaterLava = !ofDrippingWaterLava;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_TERRAIN) {
			ofAnimatedTerrain = !ofAnimatedTerrain;
		}

		if (par1EnumOptions == GameSettings.Options.ANIMATED_TEXTURES) {
			ofAnimatedTextures = !ofAnimatedTextures;
		}

		if (par1EnumOptions == GameSettings.Options.RAIN_SPLASH) {
			ofRainSplash = !ofRainSplash;
		}

		if (par1EnumOptions == GameSettings.Options.LAGOMETER) {
			ofLagometer = !ofLagometer;
		}

		if (par1EnumOptions == GameSettings.Options.SHOW_FPS) {
			ofShowFps = !ofShowFps;
		}

		if (par1EnumOptions == GameSettings.Options.AUTOSAVE_TICKS) {
			ofAutoSaveTicks *= 10;

			if (ofAutoSaveTicks > 40000) {
				ofAutoSaveTicks = 40;
			}
		}

		if (par1EnumOptions == GameSettings.Options.BETTER_GRASS) {
			++ofBetterGrass;

			if (ofBetterGrass > 3) {
				ofBetterGrass = 1;
			}

			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.CONNECTED_TEXTURES) {
			++ofConnectedTextures;

			if (ofConnectedTextures > 3) {
				ofConnectedTextures = 1;
			}

			if (ofConnectedTextures != 2) {
				mc.refreshResources();
			}
		}

		if (par1EnumOptions == GameSettings.Options.WEATHER) {
			ofWeather = !ofWeather;
		}

		if (par1EnumOptions == GameSettings.Options.SKY) {
			ofSky = !ofSky;
		}

		if (par1EnumOptions == GameSettings.Options.STARS) {
			ofStars = !ofStars;
		}

		if (par1EnumOptions == GameSettings.Options.SUN_MOON) {
			ofSunMoon = !ofSunMoon;
		}

		if (par1EnumOptions == GameSettings.Options.VIGNETTE) {
			++ofVignette;

			if (ofVignette > 2) {
				ofVignette = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.CHUNK_UPDATES) {
			++ofChunkUpdates;

			if (ofChunkUpdates > 5) {
				ofChunkUpdates = 1;
			}
		}

		if (par1EnumOptions == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
			ofChunkUpdatesDynamic = !ofChunkUpdatesDynamic;
		}

		if (par1EnumOptions == GameSettings.Options.TIME) {
			++ofTime;

			if (ofTime > 2) {
				ofTime = 0;
			}
		}

		if (par1EnumOptions == GameSettings.Options.CLEAR_WATER) {
			ofClearWater = !ofClearWater;
			updateWaterOpacity();
		}

		if (par1EnumOptions == GameSettings.Options.PROFILER) {
			ofProfiler = !ofProfiler;
		}

		if (par1EnumOptions == GameSettings.Options.BETTER_SNOW) {
			ofBetterSnow = !ofBetterSnow;
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.SWAMP_COLORS) {
			ofSwampColors = !ofSwampColors;
			CustomColors.updateUseDefaultGrassFoliageColors();
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.RANDOM_MOBS) {
			ofRandomMobs = !ofRandomMobs;
			RandomMobs.resetTextures();
		}

		if (par1EnumOptions == GameSettings.Options.SMOOTH_BIOMES) {
			ofSmoothBiomes = !ofSmoothBiomes;
			CustomColors.updateUseDefaultGrassFoliageColors();
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.CUSTOM_FONTS) {
			ofCustomFonts = !ofCustomFonts;
			mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
			mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
		}

		if (par1EnumOptions == GameSettings.Options.CUSTOM_COLORS) {
			ofCustomColors = !ofCustomColors;
			CustomColors.update();
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.CUSTOM_ITEMS) {
			ofCustomItems = !ofCustomItems;
			mc.refreshResources();
		}

		if (par1EnumOptions == GameSettings.Options.CUSTOM_SKY) {
			ofCustomSky = !ofCustomSky;
			CustomSky.update();
		}

		if (par1EnumOptions == GameSettings.Options.SHOW_CAPES) {
			ofShowCapes = !ofShowCapes;
		}

		if (par1EnumOptions == GameSettings.Options.NATURAL_TEXTURES) {
			ofNaturalTextures = !ofNaturalTextures;
			NaturalTextures.update();
			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.FAST_MATH) {
			ofFastMath = !ofFastMath;
			MathHelper.fastMath = ofFastMath;
		}

		if (par1EnumOptions == GameSettings.Options.FAST_RENDER) {
			if (!ofFastRender && Config.isShaders()) {
				Config.showGuiMessage(Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
				return;
			}

			ofFastRender = !ofFastRender;

			if (ofFastRender) {
				Minecraft.entityRenderer.stopUseShader();
			}

			Config.updateFramebufferSize();
		}

		if (par1EnumOptions == GameSettings.Options.TRANSLUCENT_BLOCKS) {
			if (ofTranslucentBlocks == 0) {
				ofTranslucentBlocks = 1;
			} else if (ofTranslucentBlocks == 1) {
				ofTranslucentBlocks = 2;
			} else if (ofTranslucentBlocks == 2) {
				ofTranslucentBlocks = 0;
			} else {
				ofTranslucentBlocks = 0;
			}

			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.LAZY_CHUNK_LOADING) {
			ofLazyChunkLoading = !ofLazyChunkLoading;
			Config.updateAvailableProcessors();

			if (!Config.isSingleProcessor()) {
				ofLazyChunkLoading = false;
			}

			mc.renderGlobal.loadRenderers();
		}

		if (par1EnumOptions == GameSettings.Options.FULLSCREEN_MODE) {
			final List modeList = Arrays.asList(Config.getFullscreenModes());

			if (ofFullscreenMode.equals("Default")) {
				ofFullscreenMode = (String) modeList.get(0);
			} else {
				int index = modeList.indexOf(ofFullscreenMode);

				if (index < 0) {
					ofFullscreenMode = "Default";
				} else {
					++index;

					if (index >= modeList.size()) {
						ofFullscreenMode = "Default";
					} else {
						ofFullscreenMode = (String) modeList.get(index);
					}
				}
			}
		}

		if (par1EnumOptions == GameSettings.Options.DYNAMIC_FOV) {
			ofDynamicFov = !ofDynamicFov;
		}

		if (par1EnumOptions == GameSettings.Options.DYNAMIC_LIGHTS) {
			ofDynamicLights = nextValue(ofDynamicLights, OF_DYNAMIC_LIGHTS);
			DynamicLights.removeLights(mc.renderGlobal);
		}

		if (par1EnumOptions == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
			heldItemTooltips = !heldItemTooltips;
		}
	}

	@SuppressWarnings("unused")
	private String getKeyBindingOF(final GameSettings.Options par1EnumOptions) {
		String var2 = I18n.format(par1EnumOptions.getEnumString(), new Object[0]) + ": ";

		if (var2 == null) {
			var2 = par1EnumOptions.getEnumString();
		}

		int var61;

		if (par1EnumOptions == GameSettings.Options.RENDER_DISTANCE) {
			var61 = (int) getOptionFloatValue(par1EnumOptions);
			String str = I18n.format("options.renderDistance.tiny", new Object[0]);
			byte baseDist = 2;

			if (var61 >= 4) {
				str = I18n.format("options.renderDistance.short", new Object[0]);
				baseDist = 4;
			}

			if (var61 >= 8) {
				str = I18n.format("options.renderDistance.normal", new Object[0]);
				baseDist = 8;
			}

			if (var61 >= 16) {
				str = I18n.format("options.renderDistance.far", new Object[0]);
				baseDist = 16;
			}

			if (var61 >= 32) {
				str = Lang.get("of.options.renderDistance.extreme");
				baseDist = 32;
			}

			final int diff = renderDistanceChunks - baseDist;
			String descr = str;

			if (diff > 0) {
				descr = str + "+";
			}

			return var2 + var61 + " " + descr + "";
		} else if (par1EnumOptions == GameSettings.Options.FOG_FANCY) {
			switch (ofFogType) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				case 3:
					return var2 + Lang.getOff();

				default:
					return var2 + Lang.getOff();
			}
		} else if (par1EnumOptions == GameSettings.Options.FOG_START) {
			return var2 + ofFogStart;
		} else if (par1EnumOptions == GameSettings.Options.MIPMAP_TYPE) {
			switch (ofMipmapType) {
				case 0:
					return var2 + Lang.get("of.options.mipmap.nearest");

				case 1:
					return var2 + Lang.get("of.options.mipmap.linear");

				case 2:
					return var2 + Lang.get("of.options.mipmap.bilinear");

				case 3:
					return var2 + Lang.get("of.options.mipmap.trilinear");

				default:
					return var2 + "of.options.mipmap.nearest";
			}
		} else if (par1EnumOptions == GameSettings.Options.SMOOTH_FPS) {
			return ofSmoothFps ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SMOOTH_WORLD) {
			return ofSmoothWorld ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.CLOUDS) {
			switch (ofClouds) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				case 3:
					return var2 + Lang.getOff();

				default:
					return var2 + Lang.getDefault();
			}
		} else if (par1EnumOptions == GameSettings.Options.TREES) {
			switch (ofTrees) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				case 3:
				default:
					return var2 + Lang.getDefault();

				case 4:
					return var2 + Lang.get("of.general.smart");
			}
		} else if (par1EnumOptions == GameSettings.Options.DROPPED_ITEMS) {
			switch (ofDroppedItems) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				default:
					return var2 + Lang.getDefault();
			}
		} else if (par1EnumOptions == GameSettings.Options.RAIN) {
			switch (ofRain) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				case 3:
					return var2 + Lang.getOff();

				default:
					return var2 + Lang.getDefault();
			}
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_WATER) {
			switch (ofAnimatedWater) {
				case 1:
					return var2 + Lang.get("of.options.animation.dynamic");

				case 2:
					return var2 + Lang.getOff();

				default:
					return var2 + Lang.getOn();
			}
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_LAVA) {
			switch (ofAnimatedLava) {
				case 1:
					return var2 + Lang.get("of.options.animation.dynamic");

				case 2:
					return var2 + Lang.getOff();

				default:
					return var2 + Lang.getOn();
			}
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_FIRE) {
			return ofAnimatedFire ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_PORTAL) {
			return ofAnimatedPortal ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_REDSTONE) {
			return ofAnimatedRedstone ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_EXPLOSION) {
			return ofAnimatedExplosion ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_FLAME) {
			return ofAnimatedFlame ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_SMOKE) {
			return ofAnimatedSmoke ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.VOID_PARTICLES) {
			return ofVoidParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.WATER_PARTICLES) {
			return ofWaterParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.PORTAL_PARTICLES) {
			return ofPortalParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.POTION_PARTICLES) {
			return ofPotionParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.FIREWORK_PARTICLES) {
			return ofFireworkParticles ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.DRIPPING_WATER_LAVA) {
			return ofDrippingWaterLava ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_TERRAIN) {
			return ofAnimatedTerrain ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.ANIMATED_TEXTURES) {
			return ofAnimatedTextures ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.RAIN_SPLASH) {
			return ofRainSplash ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.LAGOMETER) {
			return ofLagometer ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SHOW_FPS) {
			return ofShowFps ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.AUTOSAVE_TICKS) {
			return ofAutoSaveTicks <= 40 ? var2 + Lang.get("of.options.save.default")
					: ofAutoSaveTicks <= 400 ? var2 + Lang.get("of.options.save.20s")
							: ofAutoSaveTicks <= 4000 ? var2 + Lang.get("of.options.save.3min")
									: var2 + Lang.get("of.options.save.30min");
		} else if (par1EnumOptions == GameSettings.Options.BETTER_GRASS) {
			switch (ofBetterGrass) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				default:
					return var2 + Lang.getOff();
			}
		} else if (par1EnumOptions == GameSettings.Options.CONNECTED_TEXTURES) {
			switch (ofConnectedTextures) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				default:
					return var2 + Lang.getOff();
			}
		} else if (par1EnumOptions == GameSettings.Options.WEATHER) {
			return ofWeather ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SKY) {
			return ofSky ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.STARS) {
			return ofStars ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SUN_MOON) {
			return ofSunMoon ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.VIGNETTE) {
			switch (ofVignette) {
				case 1:
					return var2 + Lang.getFast();

				case 2:
					return var2 + Lang.getFancy();

				default:
					return var2 + Lang.getDefault();
			}
		} else if (par1EnumOptions == GameSettings.Options.CHUNK_UPDATES) {
			return var2 + ofChunkUpdates;
		} else if (par1EnumOptions == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
			return ofChunkUpdatesDynamic ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.TIME) {
			return ofTime == 1 ? var2 + Lang.get("of.options.time.dayOnly")
					: ofTime == 2 ? var2 + Lang.get("of.options.time.nightOnly") : var2 + Lang.getDefault();
		} else if (par1EnumOptions == GameSettings.Options.CLEAR_WATER) {
			return ofClearWater ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.AA_LEVEL) {
			String var62 = "";

			if (ofAaLevel != Config.getAntialiasingLevel()) {
				var62 = " (" + Lang.get("of.general.restart") + ")";
			}

			return ofAaLevel == 0 ? var2 + Lang.getOff() + var62 : var2 + ofAaLevel + var62;
		} else if (par1EnumOptions == GameSettings.Options.AF_LEVEL) {
			return ofAfLevel == 1 ? var2 + Lang.getOff() : var2 + ofAfLevel;
		} else if (par1EnumOptions == GameSettings.Options.PROFILER) {
			return ofProfiler ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.BETTER_SNOW) {
			return ofBetterSnow ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SWAMP_COLORS) {
			return ofSwampColors ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.RANDOM_MOBS) {
			return ofRandomMobs ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SMOOTH_BIOMES) {
			return ofSmoothBiomes ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.CUSTOM_FONTS) {
			return ofCustomFonts ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.CUSTOM_COLORS) {
			return ofCustomColors ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.CUSTOM_SKY) {
			return ofCustomSky ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.SHOW_CAPES) {
			return ofShowCapes ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.CUSTOM_ITEMS) {
			return ofCustomItems ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.NATURAL_TEXTURES) {
			return ofNaturalTextures ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.FAST_MATH) {
			return ofFastMath ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.FAST_RENDER) {
			return ofFastRender ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.TRANSLUCENT_BLOCKS) {
			return ofTranslucentBlocks == 1 ? var2 + Lang.getFast()
					: ofTranslucentBlocks == 2 ? var2 + Lang.getFancy() : var2 + Lang.getDefault();
		} else if (par1EnumOptions == GameSettings.Options.LAZY_CHUNK_LOADING) {
			return ofLazyChunkLoading ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.DYNAMIC_FOV) {
			return ofDynamicFov ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.DYNAMIC_LIGHTS) {
			var61 = indexOf(ofDynamicLights, OF_DYNAMIC_LIGHTS);
			return var2 + getTranslation(KEYS_DYNAMIC_LIGHTS, var61);
		} else if (par1EnumOptions == GameSettings.Options.FULLSCREEN_MODE) {
			return ofFullscreenMode.equals("Default") ? var2 + Lang.getDefault() : var2 + ofFullscreenMode;
		} else if (par1EnumOptions == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
			return heldItemTooltips ? var2 + Lang.getOn() : var2 + Lang.getOff();
		} else if (par1EnumOptions == GameSettings.Options.FRAMERATE_LIMIT) {
			final float var6 = getOptionFloatValue(par1EnumOptions);
			return var6 == 0.0F ? var2 + Lang.get("of.options.framerateLimit.vsync")
					: var6 == par1EnumOptions.valueMax ? var2 + I18n.format("options.framerateLimit.max", new Object[0])
							: var2 + (int) var6 + " fps";
		} else {
			return null;
		}
	}

	public void loadOfOptions() {
		try {
			File exception = optionsFileOF;

			if (!exception.exists()) {
				exception = optionsFile;
			}

			if (!exception.exists()) {
				return;
			}

			final BufferedReader bufferedreader = new BufferedReader(new FileReader(exception));
			String s = "";

			while ((s = bufferedreader.readLine()) != null) {
				try {
					final String[] exception1 = s.split(":");

					if (exception1[0].equals("ofRenderDistanceChunks") && exception1.length >= 2) {
						renderDistanceChunks = Integer.valueOf(exception1[1]);
						renderDistanceChunks = Config.limit(renderDistanceChunks, 2, 32);
					}

					if (exception1[0].equals("ofFogType") && exception1.length >= 2) {
						ofFogType = Integer.valueOf(exception1[1]);
						ofFogType = Config.limit(ofFogType, 1, 3);
					}

					if (exception1[0].equals("ofFogStart") && exception1.length >= 2) {
						ofFogStart = Float.valueOf(exception1[1]);

						if (ofFogStart < 0.2F) {
							ofFogStart = 0.2F;
						}

						if (ofFogStart > 0.81F) {
							ofFogStart = 0.8F;
						}
					}

					if (exception1[0].equals("ofMipmapType") && exception1.length >= 2) {
						ofMipmapType = Integer.valueOf(exception1[1]);
						ofMipmapType = Config.limit(ofMipmapType, 0, 3);
					}

					if (exception1[0].equals("ofOcclusionFancy") && exception1.length >= 2) {
						ofOcclusionFancy = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSmoothFps") && exception1.length >= 2) {
						ofSmoothFps = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSmoothWorld") && exception1.length >= 2) {
						ofSmoothWorld = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAoLevel") && exception1.length >= 2) {
						ofAoLevel = Float.valueOf(exception1[1]);
						ofAoLevel = Config.limit(ofAoLevel, 0.0F, 1.0F);
					}

					if (exception1[0].equals("ofClouds") && exception1.length >= 2) {
						ofClouds = Integer.valueOf(exception1[1]);
						ofClouds = Config.limit(ofClouds, 0, 3);
						updateRenderClouds();
					}

					if (exception1[0].equals("ofCloudsHeight") && exception1.length >= 2) {
						ofCloudsHeight = Float.valueOf(exception1[1]);
						ofCloudsHeight = Config.limit(ofCloudsHeight, 0.0F, 1.0F);
					}

					if (exception1[0].equals("ofTrees") && exception1.length >= 2) {
						ofTrees = Integer.valueOf(exception1[1]);
						ofTrees = limit(ofTrees, OF_TREES_VALUES);
					}

					if (exception1[0].equals("ofDroppedItems") && exception1.length >= 2) {
						ofDroppedItems = Integer.valueOf(exception1[1]);
						ofDroppedItems = Config.limit(ofDroppedItems, 0, 2);
					}

					if (exception1[0].equals("ofRain") && exception1.length >= 2) {
						ofRain = Integer.valueOf(exception1[1]);
						ofRain = Config.limit(ofRain, 0, 3);
					}

					if (exception1[0].equals("ofAnimatedWater") && exception1.length >= 2) {
						ofAnimatedWater = Integer.valueOf(exception1[1]);
						ofAnimatedWater = Config.limit(ofAnimatedWater, 0, 2);
					}

					if (exception1[0].equals("ofAnimatedLava") && exception1.length >= 2) {
						ofAnimatedLava = Integer.valueOf(exception1[1]);
						ofAnimatedLava = Config.limit(ofAnimatedLava, 0, 2);
					}

					if (exception1[0].equals("ofAnimatedFire") && exception1.length >= 2) {
						ofAnimatedFire = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedPortal") && exception1.length >= 2) {
						ofAnimatedPortal = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedRedstone") && exception1.length >= 2) {
						ofAnimatedRedstone = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedExplosion") && exception1.length >= 2) {
						ofAnimatedExplosion = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedFlame") && exception1.length >= 2) {
						ofAnimatedFlame = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedSmoke") && exception1.length >= 2) {
						ofAnimatedSmoke = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofVoidParticles") && exception1.length >= 2) {
						ofVoidParticles = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofWaterParticles") && exception1.length >= 2) {
						ofWaterParticles = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofPortalParticles") && exception1.length >= 2) {
						ofPortalParticles = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofPotionParticles") && exception1.length >= 2) {
						ofPotionParticles = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofFireworkParticles") && exception1.length >= 2) {
						ofFireworkParticles = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofDrippingWaterLava") && exception1.length >= 2) {
						ofDrippingWaterLava = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedTerrain") && exception1.length >= 2) {
						ofAnimatedTerrain = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAnimatedTextures") && exception1.length >= 2) {
						ofAnimatedTextures = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofRainSplash") && exception1.length >= 2) {
						ofRainSplash = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofLagometer") && exception1.length >= 2) {
						ofLagometer = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofShowFps") && exception1.length >= 2) {
						ofShowFps = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofAutoSaveTicks") && exception1.length >= 2) {
						ofAutoSaveTicks = Integer.valueOf(exception1[1]);
						ofAutoSaveTicks = Config.limit(ofAutoSaveTicks, 40, 40000);
					}

					if (exception1[0].equals("ofBetterGrass") && exception1.length >= 2) {
						ofBetterGrass = Integer.valueOf(exception1[1]);
						ofBetterGrass = Config.limit(ofBetterGrass, 1, 3);
					}

					if (exception1[0].equals("ofConnectedTextures") && exception1.length >= 2) {
						ofConnectedTextures = Integer.valueOf(exception1[1]);
						ofConnectedTextures = Config.limit(ofConnectedTextures, 1, 3);
					}

					if (exception1[0].equals("ofWeather") && exception1.length >= 2) {
						ofWeather = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSky") && exception1.length >= 2) {
						ofSky = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofStars") && exception1.length >= 2) {
						ofStars = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSunMoon") && exception1.length >= 2) {
						ofSunMoon = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofVignette") && exception1.length >= 2) {
						ofVignette = Integer.valueOf(exception1[1]);
						ofVignette = Config.limit(ofVignette, 0, 2);
					}

					if (exception1[0].equals("ofChunkUpdates") && exception1.length >= 2) {
						ofChunkUpdates = Integer.valueOf(exception1[1]);
						ofChunkUpdates = Config.limit(ofChunkUpdates, 1, 5);
					}

					if (exception1[0].equals("ofChunkUpdatesDynamic") && exception1.length >= 2) {
						ofChunkUpdatesDynamic = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofTime") && exception1.length >= 2) {
						ofTime = Integer.valueOf(exception1[1]);
						ofTime = Config.limit(ofTime, 0, 2);
					}

					if (exception1[0].equals("ofClearWater") && exception1.length >= 2) {
						ofClearWater = Boolean.valueOf(exception1[1]);
						updateWaterOpacity();
					}

					if (exception1[0].equals("ofAaLevel") && exception1.length >= 2) {
						ofAaLevel = Integer.valueOf(exception1[1]);
						ofAaLevel = Config.limit(ofAaLevel, 0, 16);
					}

					if (exception1[0].equals("ofAfLevel") && exception1.length >= 2) {
						ofAfLevel = Integer.valueOf(exception1[1]);
						ofAfLevel = Config.limit(ofAfLevel, 1, 16);
					}

					if (exception1[0].equals("ofProfiler") && exception1.length >= 2) {
						ofProfiler = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofBetterSnow") && exception1.length >= 2) {
						ofBetterSnow = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSwampColors") && exception1.length >= 2) {
						ofSwampColors = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofRandomMobs") && exception1.length >= 2) {
						ofRandomMobs = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofSmoothBiomes") && exception1.length >= 2) {
						ofSmoothBiomes = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofCustomFonts") && exception1.length >= 2) {
						ofCustomFonts = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofCustomColors") && exception1.length >= 2) {
						ofCustomColors = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofCustomItems") && exception1.length >= 2) {
						ofCustomItems = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofCustomSky") && exception1.length >= 2) {
						ofCustomSky = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofShowCapes") && exception1.length >= 2) {
						ofShowCapes = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofNaturalTextures") && exception1.length >= 2) {
						ofNaturalTextures = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofLazyChunkLoading") && exception1.length >= 2) {
						ofLazyChunkLoading = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofDynamicFov") && exception1.length >= 2) {
						ofDynamicFov = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofDynamicLights") && exception1.length >= 2) {
						ofDynamicLights = Integer.valueOf(exception1[1]);
						ofDynamicLights = limit(ofDynamicLights, OF_DYNAMIC_LIGHTS);
					}

					if (exception1[0].equals("ofFullscreenMode") && exception1.length >= 2) {
						ofFullscreenMode = exception1[1];
					}

					if (exception1[0].equals("ofFastMath") && exception1.length >= 2) {
						ofFastMath = Boolean.valueOf(exception1[1]);
						MathHelper.fastMath = ofFastMath;
					}

					if (exception1[0].equals("ofFastRender") && exception1.length >= 2) {
						ofFastRender = Boolean.valueOf(exception1[1]);
					}

					if (exception1[0].equals("ofTranslucentBlocks") && exception1.length >= 2) {
						ofTranslucentBlocks = Integer.valueOf(exception1[1]);
						ofTranslucentBlocks = Config.limit(ofTranslucentBlocks, 0, 2);
					}

					if (exception1[0].equals("key_" + ofKeyBindZoom.getKeyDescription())) {
						ofKeyBindZoom.setKeyCode(Integer.parseInt(exception1[1]));
					}
				} catch (final Exception var5) {
					Config.dbg("Skipping bad option: " + s);
					var5.printStackTrace();
				}
			}

			KeyBinding.resetKeyBindingArrayAndHash();
			bufferedreader.close();
		} catch (final Exception var6) {
			Config.warn("Failed to load options");
			var6.printStackTrace();
		}
	}

	public void saveOfOptions() {
		try {
			final PrintWriter exception = new PrintWriter(new FileWriter(optionsFileOF));
			exception.println("ofRenderDistanceChunks:" + renderDistanceChunks);
			exception.println("ofFogType:" + ofFogType);
			exception.println("ofFogStart:" + ofFogStart);
			exception.println("ofMipmapType:" + ofMipmapType);
			exception.println("ofOcclusionFancy:" + ofOcclusionFancy);
			exception.println("ofSmoothFps:" + ofSmoothFps);
			exception.println("ofSmoothWorld:" + ofSmoothWorld);
			exception.println("ofAoLevel:" + ofAoLevel);
			exception.println("ofClouds:" + ofClouds);
			exception.println("ofCloudsHeight:" + ofCloudsHeight);
			exception.println("ofTrees:" + ofTrees);
			exception.println("ofDroppedItems:" + ofDroppedItems);
			exception.println("ofRain:" + ofRain);
			exception.println("ofAnimatedWater:" + ofAnimatedWater);
			exception.println("ofAnimatedLava:" + ofAnimatedLava);
			exception.println("ofAnimatedFire:" + ofAnimatedFire);
			exception.println("ofAnimatedPortal:" + ofAnimatedPortal);
			exception.println("ofAnimatedRedstone:" + ofAnimatedRedstone);
			exception.println("ofAnimatedExplosion:" + ofAnimatedExplosion);
			exception.println("ofAnimatedFlame:" + ofAnimatedFlame);
			exception.println("ofAnimatedSmoke:" + ofAnimatedSmoke);
			exception.println("ofVoidParticles:" + ofVoidParticles);
			exception.println("ofWaterParticles:" + ofWaterParticles);
			exception.println("ofPortalParticles:" + ofPortalParticles);
			exception.println("ofPotionParticles:" + ofPotionParticles);
			exception.println("ofFireworkParticles:" + ofFireworkParticles);
			exception.println("ofDrippingWaterLava:" + ofDrippingWaterLava);
			exception.println("ofAnimatedTerrain:" + ofAnimatedTerrain);
			exception.println("ofAnimatedTextures:" + ofAnimatedTextures);
			exception.println("ofRainSplash:" + ofRainSplash);
			exception.println("ofLagometer:" + ofLagometer);
			exception.println("ofShowFps:" + ofShowFps);
			exception.println("ofAutoSaveTicks:" + ofAutoSaveTicks);
			exception.println("ofBetterGrass:" + ofBetterGrass);
			exception.println("ofConnectedTextures:" + ofConnectedTextures);
			exception.println("ofWeather:" + ofWeather);
			exception.println("ofSky:" + ofSky);
			exception.println("ofStars:" + ofStars);
			exception.println("ofSunMoon:" + ofSunMoon);
			exception.println("ofVignette:" + ofVignette);
			exception.println("ofChunkUpdates:" + ofChunkUpdates);
			exception.println("ofChunkUpdatesDynamic:" + ofChunkUpdatesDynamic);
			exception.println("ofTime:" + ofTime);
			exception.println("ofClearWater:" + ofClearWater);
			exception.println("ofAaLevel:" + ofAaLevel);
			exception.println("ofAfLevel:" + ofAfLevel);
			exception.println("ofProfiler:" + ofProfiler);
			exception.println("ofBetterSnow:" + ofBetterSnow);
			exception.println("ofSwampColors:" + ofSwampColors);
			exception.println("ofRandomMobs:" + ofRandomMobs);
			exception.println("ofSmoothBiomes:" + ofSmoothBiomes);
			exception.println("ofCustomFonts:" + ofCustomFonts);
			exception.println("ofCustomColors:" + ofCustomColors);
			exception.println("ofCustomItems:" + ofCustomItems);
			exception.println("ofCustomSky:" + ofCustomSky);
			exception.println("ofShowCapes:" + ofShowCapes);
			exception.println("ofNaturalTextures:" + ofNaturalTextures);
			exception.println("ofLazyChunkLoading:" + ofLazyChunkLoading);
			exception.println("ofDynamicFov:" + ofDynamicFov);
			exception.println("ofDynamicLights:" + ofDynamicLights);
			exception.println("ofFullscreenMode:" + ofFullscreenMode);
			exception.println("ofFastMath:" + ofFastMath);
			exception.println("ofFastRender:" + ofFastRender);
			exception.println("ofTranslucentBlocks:" + ofTranslucentBlocks);
			exception.println("key_" + ofKeyBindZoom.getKeyDescription() + ":" + ofKeyBindZoom.getKeyCode());
			exception.close();
		} catch (final Exception var2) {
			Config.warn("Failed to save options");
			var2.printStackTrace();
		}
	}

	private void updateRenderClouds() {
		switch (ofClouds) {
			case 1:
			case 2:
			default:
				clouds = true;
				break;

			case 3:
				clouds = false;
		}
	}

	public void resetSettings() {
		renderDistanceChunks = 8;
		viewBobbing = true;
		anaglyph = false;
		limitFramerate = (int) GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
		enableVsync = false;
		updateVSync();
		mipmapLevels = 4;
		fancyGraphics = true;
		ambientOcclusion = 2;
		clouds = true;
		fovSetting = 70.0F;
		gammaSetting = 0.0F;
		guiScale = 0;
		particleSetting = 0;
		heldItemTooltips = true;
		field_178881_t = false;
		field_178880_u = true;
		forceUnicodeFont = false;
		ofFogType = 1;
		ofFogStart = 0.8F;
		ofMipmapType = 0;
		ofOcclusionFancy = false;
		ofSmoothFps = false;
		Config.updateAvailableProcessors();
		ofSmoothWorld = Config.isSingleProcessor();
		ofLazyChunkLoading = Config.isSingleProcessor();
		ofFastMath = false;
		ofFastRender = false;
		ofTranslucentBlocks = 0;
		ofDynamicFov = true;
		ofDynamicLights = 3;
		ofAoLevel = 1.0F;
		ofAaLevel = 0;
		ofAfLevel = 1;
		ofClouds = 0;
		ofCloudsHeight = 0.0F;
		ofTrees = 0;
		ofRain = 0;
		ofBetterGrass = 3;
		ofAutoSaveTicks = 4000;
		ofLagometer = false;
		ofShowFps = false;
		ofProfiler = false;
		ofWeather = true;
		ofSky = true;
		ofStars = true;
		ofSunMoon = true;
		ofVignette = 0;
		ofChunkUpdates = 1;
		ofChunkUpdatesDynamic = false;
		ofTime = 0;
		ofClearWater = false;
		ofBetterSnow = false;
		ofFullscreenMode = "Default";
		ofSwampColors = true;
		ofRandomMobs = true;
		ofSmoothBiomes = true;
		ofCustomFonts = true;
		ofCustomColors = true;
		ofCustomItems = true;
		ofCustomSky = true;
		ofShowCapes = true;
		ofConnectedTextures = 2;
		ofNaturalTextures = false;
		ofAnimatedWater = 0;
		ofAnimatedLava = 0;
		ofAnimatedFire = true;
		ofAnimatedPortal = true;
		ofAnimatedRedstone = true;
		ofAnimatedExplosion = true;
		ofAnimatedFlame = true;
		ofAnimatedSmoke = true;
		ofVoidParticles = true;
		ofWaterParticles = true;
		ofRainSplash = true;
		ofPortalParticles = true;
		ofPotionParticles = true;
		ofFireworkParticles = true;
		ofDrippingWaterLava = true;
		ofAnimatedTerrain = true;
		ofAnimatedTextures = true;
		Shaders.setShaderPack(Shaders.packNameNone);
		Shaders.configAntialiasingLevel = 0;
		Shaders.uninit();
		Shaders.storeConfig();
		updateWaterOpacity();
		mc.refreshResources();
		saveOptions();
	}

	public void updateVSync() {
		Display.setVSyncEnabled(enableVsync);
	}

	private void updateWaterOpacity() {
		if (mc.isIntegratedServerRunning() && mc.getIntegratedServer() != null) {
			Config.waterOpacityChanged = true;
		}

		ClearWater.updateWaterOpacity(this, Minecraft.theWorld);
	}

	public void setAllAnimations(final boolean flag) {
		final int animVal = flag ? 0 : 2;
		ofAnimatedWater = animVal;
		ofAnimatedLava = animVal;
		ofAnimatedFire = flag;
		ofAnimatedPortal = flag;
		ofAnimatedRedstone = flag;
		ofAnimatedExplosion = flag;
		ofAnimatedFlame = flag;
		ofAnimatedSmoke = flag;
		ofVoidParticles = flag;
		ofWaterParticles = flag;
		ofRainSplash = flag;
		ofPortalParticles = flag;
		ofPotionParticles = flag;
		ofFireworkParticles = flag;
		particleSetting = flag ? 0 : 2;
		ofDrippingWaterLava = flag;
		ofAnimatedTerrain = flag;
		ofAnimatedTextures = flag;
	}

	private static int nextValue(final int val, final int[] vals) {
		int index = indexOf(val, vals);

		if (index < 0) {
			return vals[0];
		} else {
			++index;

			if (index >= vals.length) {
				index = 0;
			}

			return vals[index];
		}
	}

	private static int limit(final int val, final int[] vals) {
		final int index = indexOf(val, vals);
		return index < 0 ? vals[0] : val;
	}

	private static int indexOf(final int val, final int[] vals) {
		for (int i = 0; i < vals.length; ++i) {
			if (vals[i] == val) {
				return i;
			}
		}

		return -1;
	}

	public static enum Options {
		INVERT_MOUSE("INVERT_MOUSE", 0, "INVERT_MOUSE", 0, "options.invertMouse", false, true), SENSITIVITY(
				"SENSITIVITY", 1, "SENSITIVITY", 1, "options.sensitivity", true, false), FOV("FOV", 2, "FOV", 2,
						"options.fov", true, false, 30.0F, 110.0F,
						1.0F), GAMMA("GAMMA", 3, "GAMMA", 3, "options.gamma", true, false), SATURATION("SATURATION", 4,
								"SATURATION", 4, "options.saturation", true, false), RENDER_DISTANCE("RENDER_DISTANCE",
										5, "RENDER_DISTANCE", 5, "options.renderDistance", true, false, 2.0F, 16.0F,
										1.0F), VIEW_BOBBING("VIEW_BOBBING", 6, "VIEW_BOBBING", 6, "options.viewBobbing",
												false, true), ANAGLYPH("ANAGLYPH", 7, "ANAGLYPH", 7, "options.anaglyph",
														false, true), FRAMERATE_LIMIT("FRAMERATE_LIMIT", 8,
																"FRAMERATE_LIMIT", 8, "options.framerateLimit", true,
																false, 0.0F, 260.0F, 5.0F), FBO_ENABLE("FBO_ENABLE", 9,
																		"FBO_ENABLE", 9, "options.fboEnable", false,
																		true), RENDER_CLOUDS("RENDER_CLOUDS", 10,
																				"RENDER_CLOUDS", 10,
																				"options.renderClouds", false,
																				true), GRAPHICS("GRAPHICS", 11,
																						"GRAPHICS", 11,
																						"options.graphics", false,
																						false), AMBIENT_OCCLUSION(
																								"AMBIENT_OCCLUSION", 12,
																								"AMBIENT_OCCLUSION", 12,
																								"options.ao", false,
																								false), GUI_SCALE(
																										"GUI_SCALE", 13,
																										"GUI_SCALE", 13,
																										"options.guiScale",
																										false,
																										false), PARTICLES(
																												"PARTICLES",
																												14,
																												"PARTICLES",
																												14,
																												"options.particles",
																												false,
																												false), CHAT_VISIBILITY(
																														"CHAT_VISIBILITY",
																														15,
																														"CHAT_VISIBILITY",
																														15,
																														"options.chat.visibility",
																														false,
																														false), CHAT_COLOR(
																																"CHAT_COLOR",
																																16,
																																"CHAT_COLOR",
																																16,
																																"options.chat.color",
																																false,
																																true), CHAT_LINKS(
																																		"CHAT_LINKS",
																																		17,
																																		"CHAT_LINKS",
																																		17,
																																		"options.chat.links",
																																		false,
																																		true), CHAT_OPACITY(
																																				"CHAT_OPACITY",
																																				18,
																																				"CHAT_OPACITY",
																																				18,
																																				"options.chat.opacity",
																																				true,
																																				false), CHAT_LINKS_PROMPT(
																																						"CHAT_LINKS_PROMPT",
																																						19,
																																						"CHAT_LINKS_PROMPT",
																																						19,
																																						"options.chat.links.prompt",
																																						false,
																																						true), SNOOPER_ENABLED(
																																								"SNOOPER_ENABLED",
																																								20,
																																								"SNOOPER_ENABLED",
																																								20,
																																								"options.snooper",
																																								false,
																																								true), USE_FULLSCREEN(
																																										"USE_FULLSCREEN",
																																										21,
																																										"USE_FULLSCREEN",
																																										21,
																																										"options.fullscreen",
																																										false,
																																										true), ENABLE_VSYNC(
																																												"ENABLE_VSYNC",
																																												22,
																																												"ENABLE_VSYNC",
																																												22,
																																												"options.vsync",
																																												false,
																																												true), USE_VBO(
																																														"USE_VBO",
																																														23,
																																														"USE_VBO",
																																														23,
																																														"options.vbo",
																																														false,
																																														true), TOUCHSCREEN(
																																																"TOUCHSCREEN",
																																																24,
																																																"TOUCHSCREEN",
																																																24,
																																																"options.touchscreen",
																																																false,
																																																true), CHAT_SCALE(
																																																		"CHAT_SCALE",
																																																		25,
																																																		"CHAT_SCALE",
																																																		25,
																																																		"options.chat.scale",
																																																		true,
																																																		false), CHAT_WIDTH(
																																																				"CHAT_WIDTH",
																																																				26,
																																																				"CHAT_WIDTH",
																																																				26,
																																																				"options.chat.width",
																																																				true,
																																																				false), CHAT_HEIGHT_FOCUSED(
																																																						"CHAT_HEIGHT_FOCUSED",
																																																						27,
																																																						"CHAT_HEIGHT_FOCUSED",
																																																						27,
																																																						"options.chat.height.focused",
																																																						true,
																																																						false), CHAT_HEIGHT_UNFOCUSED(
																																																								"CHAT_HEIGHT_UNFOCUSED",
																																																								28,
																																																								"CHAT_HEIGHT_UNFOCUSED",
																																																								28,
																																																								"options.chat.height.unfocused",
																																																								true,
																																																								false), MIPMAP_LEVELS(
																																																										"MIPMAP_LEVELS",
																																																										29,
																																																										"MIPMAP_LEVELS",
																																																										29,
																																																										"options.mipmapLevels",
																																																										true,
																																																										false,
																																																										0.0F,
																																																										4.0F,
																																																										1.0F), FORCE_UNICODE_FONT(
																																																												"FORCE_UNICODE_FONT",
																																																												30,
																																																												"FORCE_UNICODE_FONT",
																																																												30,
																																																												"options.forceUnicodeFont",
																																																												false,
																																																												true), STREAM_BYTES_PER_PIXEL(
																																																														"STREAM_BYTES_PER_PIXEL",
																																																														31,
																																																														"STREAM_BYTES_PER_PIXEL",
																																																														31,
																																																														"options.stream.bytesPerPixel",
																																																														true,
																																																														false), STREAM_VOLUME_MIC(
																																																																"STREAM_VOLUME_MIC",
																																																																32,
																																																																"STREAM_VOLUME_MIC",
																																																																32,
																																																																"options.stream.micVolumne",
																																																																true,
																																																																false), STREAM_VOLUME_SYSTEM(
																																																																		"STREAM_VOLUME_SYSTEM",
																																																																		33,
																																																																		"STREAM_VOLUME_SYSTEM",
																																																																		33,
																																																																		"options.stream.systemVolume",
																																																																		true,
																																																																		false), STREAM_KBPS(
																																																																				"STREAM_KBPS",
																																																																				34,
																																																																				"STREAM_KBPS",
																																																																				34,
																																																																				"options.stream.kbps",
																																																																				true,
																																																																				false), STREAM_FPS(
																																																																						"STREAM_FPS",
																																																																						35,
																																																																						"STREAM_FPS",
																																																																						35,
																																																																						"options.stream.fps",
																																																																						true,
																																																																						false), STREAM_COMPRESSION(
																																																																								"STREAM_COMPRESSION",
																																																																								36,
																																																																								"STREAM_COMPRESSION",
																																																																								36,
																																																																								"options.stream.compression",
																																																																								false,
																																																																								false), STREAM_SEND_METADATA(
																																																																										"STREAM_SEND_METADATA",
																																																																										37,
																																																																										"STREAM_SEND_METADATA",
																																																																										37,
																																																																										"options.stream.sendMetadata",
																																																																										false,
																																																																										true), STREAM_CHAT_ENABLED(
																																																																												"STREAM_CHAT_ENABLED",
																																																																												38,
																																																																												"STREAM_CHAT_ENABLED",
																																																																												38,
																																																																												"options.stream.chat.enabled",
																																																																												false,
																																																																												false), STREAM_CHAT_USER_FILTER(
																																																																														"STREAM_CHAT_USER_FILTER",
																																																																														39,
																																																																														"STREAM_CHAT_USER_FILTER",
																																																																														39,
																																																																														"options.stream.chat.userFilter",
																																																																														false,
																																																																														false), STREAM_MIC_TOGGLE_BEHAVIOR(
																																																																																"STREAM_MIC_TOGGLE_BEHAVIOR",
																																																																																40,
																																																																																"STREAM_MIC_TOGGLE_BEHAVIOR",
																																																																																40,
																																																																																"options.stream.micToggleBehavior",
																																																																																false,
																																																																																false), BLOCK_ALTERNATIVES(
																																																																																		"BLOCK_ALTERNATIVES",
																																																																																		41,
																																																																																		"BLOCK_ALTERNATIVES",
																																																																																		41,
																																																																																		"options.blockAlternatives",
																																																																																		false,
																																																																																		true), REDUCED_DEBUG_INFO(
																																																																																				"REDUCED_DEBUG_INFO",
																																																																																				42,
																																																																																				"REDUCED_DEBUG_INFO",
																																																																																				42,
																																																																																				"options.reducedDebugInfo",
																																																																																				false,
																																																																																				true), FOG_FANCY(
																																																																																						"FOG_FANCY",
																																																																																						43,
																																																																																						"",
																																																																																						999,
																																																																																						"of.options.FOG_FANCY",
																																																																																						false,
																																																																																						false), FOG_START(
																																																																																								"FOG_START",
																																																																																								44,
																																																																																								"",
																																																																																								999,
																																																																																								"of.options.FOG_START",
																																																																																								false,
																																																																																								false), MIPMAP_TYPE(
																																																																																										"MIPMAP_TYPE",
																																																																																										45,
																																																																																										"",
																																																																																										999,
																																																																																										"of.options.MIPMAP_TYPE",
																																																																																										true,
																																																																																										false,
																																																																																										0.0F,
																																																																																										3.0F,
																																																																																										1.0F), SMOOTH_FPS(
																																																																																												"SMOOTH_FPS",
																																																																																												46,
																																																																																												"",
																																																																																												999,
																																																																																												"of.options.SMOOTH_FPS",
																																																																																												false,
																																																																																												false), CLOUDS(
																																																																																														"CLOUDS",
																																																																																														47,
																																																																																														"",
																																																																																														999,
																																																																																														"of.options.CLOUDS",
																																																																																														false,
																																																																																														false), CLOUD_HEIGHT(
																																																																																																"CLOUD_HEIGHT",
																																																																																																48,
																																																																																																"",
																																																																																																999,
																																																																																																"of.options.CLOUD_HEIGHT",
																																																																																																true,
																																																																																																false), TREES(
																																																																																																		"TREES",
																																																																																																		49,
																																																																																																		"",
																																																																																																		999,
																																																																																																		"of.options.TREES",
																																																																																																		false,
																																																																																																		false), RAIN(
																																																																																																				"RAIN",
																																																																																																				50,
																																																																																																				"",
																																																																																																				999,
																																																																																																				"of.options.RAIN",
																																																																																																				false,
																																																																																																				false), ANIMATED_WATER(
																																																																																																						"ANIMATED_WATER",
																																																																																																						51,
																																																																																																						"",
																																																																																																						999,
																																																																																																						"of.options.ANIMATED_WATER",
																																																																																																						false,
																																																																																																						false), ANIMATED_LAVA(
																																																																																																								"ANIMATED_LAVA",
																																																																																																								52,
																																																																																																								"",
																																																																																																								999,
																																																																																																								"of.options.ANIMATED_LAVA",
																																																																																																								false,
																																																																																																								false), ANIMATED_FIRE(
																																																																																																										"ANIMATED_FIRE",
																																																																																																										53,
																																																																																																										"",
																																																																																																										999,
																																																																																																										"of.options.ANIMATED_FIRE",
																																																																																																										false,
																																																																																																										false), ANIMATED_PORTAL(
																																																																																																												"ANIMATED_PORTAL",
																																																																																																												54,
																																																																																																												"",
																																																																																																												999,
																																																																																																												"of.options.ANIMATED_PORTAL",
																																																																																																												false,
																																																																																																												false), AO_LEVEL(
																																																																																																														"AO_LEVEL",
																																																																																																														55,
																																																																																																														"",
																																																																																																														999,
																																																																																																														"of.options.AO_LEVEL",
																																																																																																														true,
																																																																																																														false), LAGOMETER(
																																																																																																																"LAGOMETER",
																																																																																																																56,
																																																																																																																"",
																																																																																																																999,
																																																																																																																"of.options.LAGOMETER",
																																																																																																																false,
																																																																																																																false), SHOW_FPS(
																																																																																																																		"SHOW_FPS",
																																																																																																																		57,
																																																																																																																		"",
																																																																																																																		999,
																																																																																																																		"of.options.SHOW_FPS",
																																																																																																																		false,
																																																																																																																		false), AUTOSAVE_TICKS(
																																																																																																																				"AUTOSAVE_TICKS",
																																																																																																																				58,
																																																																																																																				"",
																																																																																																																				999,
																																																																																																																				"of.options.AUTOSAVE_TICKS",
																																																																																																																				false,
																																																																																																																				false), BETTER_GRASS(
																																																																																																																						"BETTER_GRASS",
																																																																																																																						59,
																																																																																																																						"",
																																																																																																																						999,
																																																																																																																						"of.options.BETTER_GRASS",
																																																																																																																						false,
																																																																																																																						false), ANIMATED_REDSTONE(
																																																																																																																								"ANIMATED_REDSTONE",
																																																																																																																								60,
																																																																																																																								"",
																																																																																																																								999,
																																																																																																																								"of.options.ANIMATED_REDSTONE",
																																																																																																																								false,
																																																																																																																								false), ANIMATED_EXPLOSION(
																																																																																																																										"ANIMATED_EXPLOSION",
																																																																																																																										61,
																																																																																																																										"",
																																																																																																																										999,
																																																																																																																										"of.options.ANIMATED_EXPLOSION",
																																																																																																																										false,
																																																																																																																										false), ANIMATED_FLAME(
																																																																																																																												"ANIMATED_FLAME",
																																																																																																																												62,
																																																																																																																												"",
																																																																																																																												999,
																																																																																																																												"of.options.ANIMATED_FLAME",
																																																																																																																												false,
																																																																																																																												false), ANIMATED_SMOKE(
																																																																																																																														"ANIMATED_SMOKE",
																																																																																																																														63,
																																																																																																																														"",
																																																																																																																														999,
																																																																																																																														"of.options.ANIMATED_SMOKE",
																																																																																																																														false,
																																																																																																																														false), WEATHER(
																																																																																																																																"WEATHER",
																																																																																																																																64,
																																																																																																																																"",
																																																																																																																																999,
																																																																																																																																"of.options.WEATHER",
																																																																																																																																false,
																																																																																																																																false), SKY(
																																																																																																																																		"SKY",
																																																																																																																																		65,
																																																																																																																																		"",
																																																																																																																																		999,
																																																																																																																																		"of.options.SKY",
																																																																																																																																		false,
																																																																																																																																		false), STARS(
																																																																																																																																				"STARS",
																																																																																																																																				66,
																																																																																																																																				"",
																																																																																																																																				999,
																																																																																																																																				"of.options.STARS",
																																																																																																																																				false,
																																																																																																																																				false), SUN_MOON(
																																																																																																																																						"SUN_MOON",
																																																																																																																																						67,
																																																																																																																																						"",
																																																																																																																																						999,
																																																																																																																																						"of.options.SUN_MOON",
																																																																																																																																						false,
																																																																																																																																						false), VIGNETTE(
																																																																																																																																								"VIGNETTE",
																																																																																																																																								68,
																																																																																																																																								"",
																																																																																																																																								999,
																																																																																																																																								"of.options.VIGNETTE",
																																																																																																																																								false,
																																																																																																																																								false), CHUNK_UPDATES(
																																																																																																																																										"CHUNK_UPDATES",
																																																																																																																																										69,
																																																																																																																																										"",
																																																																																																																																										999,
																																																																																																																																										"of.options.CHUNK_UPDATES",
																																																																																																																																										false,
																																																																																																																																										false), CHUNK_UPDATES_DYNAMIC(
																																																																																																																																												"CHUNK_UPDATES_DYNAMIC",
																																																																																																																																												70,
																																																																																																																																												"",
																																																																																																																																												999,
																																																																																																																																												"of.options.CHUNK_UPDATES_DYNAMIC",
																																																																																																																																												false,
																																																																																																																																												false), TIME(
																																																																																																																																														"TIME",
																																																																																																																																														71,
																																																																																																																																														"",
																																																																																																																																														999,
																																																																																																																																														"of.options.TIME",
																																																																																																																																														false,
																																																																																																																																														false), CLEAR_WATER(
																																																																																																																																																"CLEAR_WATER",
																																																																																																																																																72,
																																																																																																																																																"",
																																																																																																																																																999,
																																																																																																																																																"of.options.CLEAR_WATER",
																																																																																																																																																false,
																																																																																																																																																false), SMOOTH_WORLD(
																																																																																																																																																		"SMOOTH_WORLD",
																																																																																																																																																		73,
																																																																																																																																																		"",
																																																																																																																																																		999,
																																																																																																																																																		"of.options.SMOOTH_WORLD",
																																																																																																																																																		false,
																																																																																																																																																		false), VOID_PARTICLES(
																																																																																																																																																				"VOID_PARTICLES",
																																																																																																																																																				74,
																																																																																																																																																				"",
																																																																																																																																																				999,
																																																																																																																																																				"of.options.VOID_PARTICLES",
																																																																																																																																																				false,
																																																																																																																																																				false), WATER_PARTICLES(
																																																																																																																																																						"WATER_PARTICLES",
																																																																																																																																																						75,
																																																																																																																																																						"",
																																																																																																																																																						999,
																																																																																																																																																						"of.options.WATER_PARTICLES",
																																																																																																																																																						false,
																																																																																																																																																						false), RAIN_SPLASH(
																																																																																																																																																								"RAIN_SPLASH",
																																																																																																																																																								76,
																																																																																																																																																								"",
																																																																																																																																																								999,
																																																																																																																																																								"of.options.RAIN_SPLASH",
																																																																																																																																																								false,
																																																																																																																																																								false), PORTAL_PARTICLES(
																																																																																																																																																										"PORTAL_PARTICLES",
																																																																																																																																																										77,
																																																																																																																																																										"",
																																																																																																																																																										999,
																																																																																																																																																										"of.options.PORTAL_PARTICLES",
																																																																																																																																																										false,
																																																																																																																																																										false), POTION_PARTICLES(
																																																																																																																																																												"POTION_PARTICLES",
																																																																																																																																																												78,
																																																																																																																																																												"",
																																																																																																																																																												999,
																																																																																																																																																												"of.options.POTION_PARTICLES",
																																																																																																																																																												false,
																																																																																																																																																												false), FIREWORK_PARTICLES(
																																																																																																																																																														"FIREWORK_PARTICLES",
																																																																																																																																																														79,
																																																																																																																																																														"",
																																																																																																																																																														999,
																																																																																																																																																														"of.options.FIREWORK_PARTICLES",
																																																																																																																																																														false,
																																																																																																																																																														false), PROFILER(
																																																																																																																																																																"PROFILER",
																																																																																																																																																																80,
																																																																																																																																																																"",
																																																																																																																																																																999,
																																																																																																																																																																"of.options.PROFILER",
																																																																																																																																																																false,
																																																																																																																																																																false), DRIPPING_WATER_LAVA(
																																																																																																																																																																		"DRIPPING_WATER_LAVA",
																																																																																																																																																																		81,
																																																																																																																																																																		"",
																																																																																																																																																																		999,
																																																																																																																																																																		"of.options.DRIPPING_WATER_LAVA",
																																																																																																																																																																		false,
																																																																																																																																																																		false), BETTER_SNOW(
																																																																																																																																																																				"BETTER_SNOW",
																																																																																																																																																																				82,
																																																																																																																																																																				"",
																																																																																																																																																																				999,
																																																																																																																																																																				"of.options.BETTER_SNOW",
																																																																																																																																																																				false,
																																																																																																																																																																				false), FULLSCREEN_MODE(
																																																																																																																																																																						"FULLSCREEN_MODE",
																																																																																																																																																																						83,
																																																																																																																																																																						"",
																																																																																																																																																																						999,
																																																																																																																																																																						"of.options.FULLSCREEN_MODE",
																																																																																																																																																																						false,
																																																																																																																																																																						false), ANIMATED_TERRAIN(
																																																																																																																																																																								"ANIMATED_TERRAIN",
																																																																																																																																																																								84,
																																																																																																																																																																								"",
																																																																																																																																																																								999,
																																																																																																																																																																								"of.options.ANIMATED_TERRAIN",
																																																																																																																																																																								false,
																																																																																																																																																																								false), SWAMP_COLORS(
																																																																																																																																																																										"SWAMP_COLORS",
																																																																																																																																																																										85,
																																																																																																																																																																										"",
																																																																																																																																																																										999,
																																																																																																																																																																										"of.options.SWAMP_COLORS",
																																																																																																																																																																										false,
																																																																																																																																																																										false), RANDOM_MOBS(
																																																																																																																																																																												"RANDOM_MOBS",
																																																																																																																																																																												86,
																																																																																																																																																																												"",
																																																																																																																																																																												999,
																																																																																																																																																																												"of.options.RANDOM_MOBS",
																																																																																																																																																																												false,
																																																																																																																																																																												false), SMOOTH_BIOMES(
																																																																																																																																																																														"SMOOTH_BIOMES",
																																																																																																																																																																														87,
																																																																																																																																																																														"",
																																																																																																																																																																														999,
																																																																																																																																																																														"of.options.SMOOTH_BIOMES",
																																																																																																																																																																														false,
																																																																																																																																																																														false), CUSTOM_FONTS(
																																																																																																																																																																																"CUSTOM_FONTS",
																																																																																																																																																																																88,
																																																																																																																																																																																"",
																																																																																																																																																																																999,
																																																																																																																																																																																"of.options.CUSTOM_FONTS",
																																																																																																																																																																																false,
																																																																																																																																																																																false), CUSTOM_COLORS(
																																																																																																																																																																																		"CUSTOM_COLORS",
																																																																																																																																																																																		89,
																																																																																																																																																																																		"",
																																																																																																																																																																																		999,
																																																																																																																																																																																		"of.options.CUSTOM_COLORS",
																																																																																																																																																																																		false,
																																																																																																																																																																																		false), SHOW_CAPES(
																																																																																																																																																																																				"SHOW_CAPES",
																																																																																																																																																																																				90,
																																																																																																																																																																																				"",
																																																																																																																																																																																				999,
																																																																																																																																																																																				"of.options.SHOW_CAPES",
																																																																																																																																																																																				false,
																																																																																																																																																																																				false), CONNECTED_TEXTURES(
																																																																																																																																																																																						"CONNECTED_TEXTURES",
																																																																																																																																																																																						91,
																																																																																																																																																																																						"",
																																																																																																																																																																																						999,
																																																																																																																																																																																						"of.options.CONNECTED_TEXTURES",
																																																																																																																																																																																						false,
																																																																																																																																																																																						false), CUSTOM_ITEMS(
																																																																																																																																																																																								"CUSTOM_ITEMS",
																																																																																																																																																																																								92,
																																																																																																																																																																																								"",
																																																																																																																																																																																								999,
																																																																																																																																																																																								"of.options.CUSTOM_ITEMS",
																																																																																																																																																																																								false,
																																																																																																																																																																																								false), AA_LEVEL(
																																																																																																																																																																																										"AA_LEVEL",
																																																																																																																																																																																										93,
																																																																																																																																																																																										"",
																																																																																																																																																																																										999,
																																																																																																																																																																																										"of.options.AA_LEVEL",
																																																																																																																																																																																										true,
																																																																																																																																																																																										false,
																																																																																																																																																																																										0.0F,
																																																																																																																																																																																										16.0F,
																																																																																																																																																																																										1.0F), AF_LEVEL(
																																																																																																																																																																																												"AF_LEVEL",
																																																																																																																																																																																												94,
																																																																																																																																																																																												"",
																																																																																																																																																																																												999,
																																																																																																																																																																																												"of.options.AF_LEVEL",
																																																																																																																																																																																												true,
																																																																																																																																																																																												false,
																																																																																																																																																																																												1.0F,
																																																																																																																																																																																												16.0F,
																																																																																																																																																																																												1.0F), ANIMATED_TEXTURES(
																																																																																																																																																																																														"ANIMATED_TEXTURES",
																																																																																																																																																																																														95,
																																																																																																																																																																																														"",
																																																																																																																																																																																														999,
																																																																																																																																																																																														"of.options.ANIMATED_TEXTURES",
																																																																																																																																																																																														false,
																																																																																																																																																																																														false), NATURAL_TEXTURES(
																																																																																																																																																																																																"NATURAL_TEXTURES",
																																																																																																																																																																																																96,
																																																																																																																																																																																																"",
																																																																																																																																																																																																999,
																																																																																																																																																																																																"of.options.NATURAL_TEXTURES",
																																																																																																																																																																																																false,
																																																																																																																																																																																																false), HELD_ITEM_TOOLTIPS(
																																																																																																																																																																																																		"HELD_ITEM_TOOLTIPS",
																																																																																																																																																																																																		97,
																																																																																																																																																																																																		"",
																																																																																																																																																																																																		999,
																																																																																																																																																																																																		"of.options.HELD_ITEM_TOOLTIPS",
																																																																																																																																																																																																		false,
																																																																																																																																																																																																		false), DROPPED_ITEMS(
																																																																																																																																																																																																				"DROPPED_ITEMS",
																																																																																																																																																																																																				98,
																																																																																																																																																																																																				"",
																																																																																																																																																																																																				999,
																																																																																																																																																																																																				"of.options.DROPPED_ITEMS",
																																																																																																																																																																																																				false,
																																																																																																																																																																																																				false), LAZY_CHUNK_LOADING(
																																																																																																																																																																																																						"LAZY_CHUNK_LOADING",
																																																																																																																																																																																																						99,
																																																																																																																																																																																																						"",
																																																																																																																																																																																																						999,
																																																																																																																																																																																																						"of.options.LAZY_CHUNK_LOADING",
																																																																																																																																																																																																						false,
																																																																																																																																																																																																						false), CUSTOM_SKY(
																																																																																																																																																																																																								"CUSTOM_SKY",
																																																																																																																																																																																																								100,
																																																																																																																																																																																																								"",
																																																																																																																																																																																																								999,
																																																																																																																																																																																																								"of.options.CUSTOM_SKY",
																																																																																																																																																																																																								false,
																																																																																																																																																																																																								false), FAST_MATH(
																																																																																																																																																																																																										"FAST_MATH",
																																																																																																																																																																																																										101,
																																																																																																																																																																																																										"",
																																																																																																																																																																																																										999,
																																																																																																																																																																																																										"of.options.FAST_MATH",
																																																																																																																																																																																																										false,
																																																																																																																																																																																																										false), FAST_RENDER(
																																																																																																																																																																																																												"FAST_RENDER",
																																																																																																																																																																																																												102,
																																																																																																																																																																																																												"",
																																																																																																																																																																																																												999,
																																																																																																																																																																																																												"of.options.FAST_RENDER",
																																																																																																																																																																																																												false,
																																																																																																																																																																																																												false), TRANSLUCENT_BLOCKS(
																																																																																																																																																																																																														"TRANSLUCENT_BLOCKS",
																																																																																																																																																																																																														103,
																																																																																																																																																																																																														"",
																																																																																																																																																																																																														999,
																																																																																																																																																																																																														"of.options.TRANSLUCENT_BLOCKS",
																																																																																																																																																																																																														false,
																																																																																																																																																																																																														false), DYNAMIC_FOV(
																																																																																																																																																																																																																"DYNAMIC_FOV",
																																																																																																																																																																																																																104,
																																																																																																																																																																																																																"",
																																																																																																																																																																																																																999,
																																																																																																																																																																																																																"of.options.DYNAMIC_FOV",
																																																																																																																																																																																																																false,
																																																																																																																																																																																																																false), DYNAMIC_LIGHTS(
																																																																																																																																																																																																																		"DYNAMIC_LIGHTS",
																																																																																																																																																																																																																		105,
																																																																																																																																																																																																																		"",
																																																																																																																																																																																																																		999,
																																																																																																																																																																																																																		"of.options.DYNAMIC_LIGHTS",
																																																																																																																																																																																																																		false,
																																																																																																																																																																																																																		false);
		private final boolean enumFloat;
		private final boolean enumBoolean;
		private final String enumString;
		private final float valueStep;
		private float valueMin;
		private float valueMax;

		public static GameSettings.Options getEnumOptions(final int p_74379_0_) {
			final GameSettings.Options[] var1 = values();
			final int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				final GameSettings.Options var4 = var1[var3];

				if (var4.returnEnumOrdinal() == p_74379_0_) {
					return var4;
				}
			}

			return null;
		}

		private Options(final String p_i46375_1_, final int p_i46375_2_, final String p_i1015_1_, final int p_i1015_2_,
				final String p_i1015_3_, final boolean p_i1015_4_, final boolean p_i1015_5_) {
			this(p_i46375_1_, p_i46375_2_, p_i1015_1_, p_i1015_2_, p_i1015_3_, p_i1015_4_, p_i1015_5_, 0.0F, 1.0F,
					0.0F);
		}

		private Options(final String p_i46376_1_, final int p_i46376_2_, final String p_i45004_1_,
				final int p_i45004_2_, final String p_i45004_3_, final boolean p_i45004_4_, final boolean p_i45004_5_,
				final float p_i45004_6_, final float p_i45004_7_, final float p_i45004_8_) {
			enumString = p_i45004_3_;
			enumFloat = p_i45004_4_;
			enumBoolean = p_i45004_5_;
			valueMin = p_i45004_6_;
			valueMax = p_i45004_7_;
			valueStep = p_i45004_8_;
		}

		public boolean getEnumFloat() {
			return enumFloat;
		}

		public boolean getEnumBoolean() {
			return enumBoolean;
		}

		public int returnEnumOrdinal() {
			return ordinal();
		}

		public String getEnumString() {
			return enumString;
		}

		public float getValueMax() {
			return valueMax;
		}

		public void setValueMax(final float p_148263_1_) {
			valueMax = p_148263_1_;
		}

		public float normalizeValue(final float p_148266_1_) {
			return MathHelper.clamp_float((snapToStepClamp(p_148266_1_) - valueMin) / (valueMax - valueMin), 0.0F,
					1.0F);
		}

		public float denormalizeValue(final float p_148262_1_) {
			return snapToStepClamp(valueMin + (valueMax - valueMin) * MathHelper.clamp_float(p_148262_1_, 0.0F, 1.0F));
		}

		public float snapToStepClamp(float p_148268_1_) {
			p_148268_1_ = snapToStep(p_148268_1_);
			return MathHelper.clamp_float(p_148268_1_, valueMin, valueMax);
		}

		protected float snapToStep(float p_148264_1_) {
			if (valueStep > 0.0F) {
				p_148264_1_ = valueStep * Math.round(p_148264_1_ / valueStep);
			}

			return p_148264_1_;
		}
	}

	static final class SwitchOptions {
		static final int[] optionIds = new int[GameSettings.Options.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00000652";

		static {
			try {
				optionIds[GameSettings.Options.INVERT_MOUSE.ordinal()] = 1;
			} catch (final NoSuchFieldError var17) {
			}

			try {
				optionIds[GameSettings.Options.VIEW_BOBBING.ordinal()] = 2;
			} catch (final NoSuchFieldError var16) {
			}

			try {
				optionIds[GameSettings.Options.ANAGLYPH.ordinal()] = 3;
			} catch (final NoSuchFieldError var15) {
			}

			try {
				optionIds[GameSettings.Options.FBO_ENABLE.ordinal()] = 4;
			} catch (final NoSuchFieldError var14) {
			}

			try {
				optionIds[GameSettings.Options.RENDER_CLOUDS.ordinal()] = 5;
			} catch (final NoSuchFieldError var13) {
			}

			try {
				optionIds[GameSettings.Options.CHAT_COLOR.ordinal()] = 6;
			} catch (final NoSuchFieldError var12) {
			}

			try {
				optionIds[GameSettings.Options.CHAT_LINKS.ordinal()] = 7;
			} catch (final NoSuchFieldError var11) {
			}

			try {
				optionIds[GameSettings.Options.CHAT_LINKS_PROMPT.ordinal()] = 8;
			} catch (final NoSuchFieldError var10) {
			}

			try {
				optionIds[GameSettings.Options.SNOOPER_ENABLED.ordinal()] = 9;
			} catch (final NoSuchFieldError var9) {
			}

			try {
				optionIds[GameSettings.Options.USE_FULLSCREEN.ordinal()] = 10;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				optionIds[GameSettings.Options.ENABLE_VSYNC.ordinal()] = 11;
			} catch (final NoSuchFieldError var7) {
			}

			try {
				optionIds[GameSettings.Options.USE_VBO.ordinal()] = 12;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				optionIds[GameSettings.Options.TOUCHSCREEN.ordinal()] = 13;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				optionIds[GameSettings.Options.STREAM_SEND_METADATA.ordinal()] = 14;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				optionIds[GameSettings.Options.FORCE_UNICODE_FONT.ordinal()] = 15;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				optionIds[GameSettings.Options.BLOCK_ALTERNATIVES.ordinal()] = 16;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				optionIds[GameSettings.Options.REDUCED_DEBUG_INFO.ordinal()] = 17;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
