package net.minecraft.server.integrated;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;

import optifine.Reflector;
import optifine.WorldServerOF;

public class IntegratedServer extends MinecraftServer {

public static final int EaZy = 1532;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/** The Minecraft instance. */
	private final Minecraft mc;
	private final WorldSettings theWorldSettings;
	private boolean isGamePaused;
	private boolean isPublic;
	private ThreadLanServerPing lanServerPing;

	public IntegratedServer(final Minecraft mcIn) {
		super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
		mc = mcIn;
		theWorldSettings = null;
	}

	public IntegratedServer(final Minecraft mcIn, final String folderName, final String worldName,
			final WorldSettings settings) {
		super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
		setServerOwner(mcIn.getSession().getUsername());
		setFolderName(folderName);
		setWorldName(worldName);
		setDemo(mcIn.isDemo());
		canCreateBonusChest(settings.isBonusChestEnabled());
		setBuildLimit(256);
		setConfigManager(new IntegratedPlayerList(this));
		mc = mcIn;
		theWorldSettings = isDemo() ? DemoWorldServer.demoWorldSettings : settings;
	}

	@Override
	protected ServerCommandManager createNewCommandManager() {
		return new IntegratedServerCommandManager();
	}

	@Override
	protected void loadAllWorlds(final String p_71247_1_, final String p_71247_2_, final long seed,
			final WorldType type, final String p_71247_6_) {
		convertMapIfNeeded(p_71247_1_);
		final ISaveHandler var7 = getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
		setResourcePackFromWorld(getFolderName(), var7);
		WorldInfo var8 = var7.loadWorldInfo();

		if (Reflector.DimensionManager.exists()) {
			final WorldServer var9 = isDemo()
					? (WorldServer) (WorldServer) new DemoWorldServer(this, var7, var8, 0, theProfiler).init()
					: (WorldServer) new WorldServerOF(this, var7, var8, 0, theProfiler).init();
			var9.initialize(theWorldSettings);
			final Integer[] var10 = (Integer[]) Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs,
					new Object[0]);
			final Integer[] arr$ = var10;
			final int len$ = var10.length;

			for (int i$ = 0; i$ < len$; ++i$) {
				final int dim = arr$[i$];
				final WorldServer world = dim == 0 ? var9
						: (WorldServer) (WorldServer) new WorldServerMulti(this, var7, dim, var9, theProfiler).init();
				world.addWorldAccess(new WorldManager(this, world));

				if (!isSinglePlayer()) {
					world.getWorldInfo().setGameType(getGameType());
				}

				if (Reflector.EventBus.exists()) {
					Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { world });
				}
			}

			getConfigurationManager().setPlayerManager(new WorldServer[] { var9 });

			if (var9.getWorldInfo().getDifficulty() == null) {
				setDifficultyForAllWorlds(Minecraft.gameSettings.difficulty);
			}
		} else {
			worldServers = new WorldServer[3];
			timeOfLastDimensionTick = new long[worldServers.length][100];
			setResourcePackFromWorld(getFolderName(), var7);

			if (var8 == null) {
				var8 = new WorldInfo(theWorldSettings, p_71247_2_);
			} else {
				var8.setWorldName(p_71247_2_);
			}

			for (int var16 = 0; var16 < worldServers.length; ++var16) {
				byte var17 = 0;

				if (var16 == 1) {
					var17 = -1;
				}

				if (var16 == 2) {
					var17 = 1;
				}

				if (var16 == 0) {
					if (isDemo()) {
						worldServers[var16] = (WorldServer) new DemoWorldServer(this, var7, var8, var17, theProfiler)
								.init();
					} else {
						worldServers[var16] = (WorldServer) new WorldServerOF(this, var7, var8, var17, theProfiler)
								.init();
					}

					worldServers[var16].initialize(theWorldSettings);
				} else {
					worldServers[var16] = (WorldServer) new WorldServerMulti(this, var7, var17, worldServers[0],
							theProfiler).init();
				}

				worldServers[var16].addWorldAccess(new WorldManager(this, worldServers[var16]));
			}

			getConfigurationManager().setPlayerManager(worldServers);

			if (worldServers[0].getWorldInfo().getDifficulty() == null) {
				setDifficultyForAllWorlds(Minecraft.gameSettings.difficulty);
			}
		}

		initialWorldChunkLoad();
	}

	/**
	 * Initialises the server and starts it.
	 */
	@Override
	protected boolean startServer() throws IOException {
		logger.info("Starting integrated minecraft server version 1.8");
		setOnlineMode(true);
		setCanSpawnAnimals(true);
		setCanSpawnNPCs(true);
		setAllowPvp(true);
		setAllowFlight(true);
		logger.info("Generating keypair");
		setKeyPair(CryptManager.generateKeyPair());
		Object inst;

		if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
			inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);

			if (!Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerAboutToStart,
					new Object[] { this })) {
				return false;
			}
		}

		loadAllWorlds(getFolderName(), getWorldName(), theWorldSettings.getSeed(), theWorldSettings.getTerrainType(),
				theWorldSettings.getWorldName());
		setMOTD(getServerOwner() + " - " + worldServers[0].getWorldInfo().getWorldName());

		if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
			inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);

			if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE) {
				return Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerStarting,
						new Object[] { this });
			}

			Reflector.callVoid(inst, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
		}

		return true;
	}

	/**
	 * Main function called by run() every loop.
	 */
	@Override
	public void tick() {
		final boolean var1 = isGamePaused;

		isGamePaused = Minecraft.getNetHandler() != null && Minecraft.getMinecraft().isGamePaused();

		if (!var1 && isGamePaused) {
			logger.info("Saving and pausing game...");
			getConfigurationManager().saveAllPlayerData();
			saveAllWorlds(false);
		}

		if (isGamePaused) {
			synchronized (futureTaskQueue) {
				while (!futureTaskQueue.isEmpty()) {
					try {
						if (Reflector.FMLCommonHandler_callFuture.exists()) {
							Reflector.callVoid(Reflector.FMLCommonHandler_callFuture,
									new Object[] { (FutureTask) futureTaskQueue.poll() });
						} else {
							((FutureTask) futureTaskQueue.poll()).run();
						}
					} catch (final Throwable var8) {
						logger.fatal(var8);
					}
				}
			}
		} else {
			super.tick();

			if (Minecraft.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance()) {
				logger.info("Changing view distance to {}, from {}",
						new Object[] { Minecraft.gameSettings.renderDistanceChunks,getConfigurationManager().getViewDistance()});
				getConfigurationManager().setViewDistance(Minecraft.gameSettings.renderDistanceChunks);
			}

			if (Minecraft.theWorld != null) {
				final WorldInfo var101 = worldServers[0].getWorldInfo();
				final WorldInfo var11 = Minecraft.theWorld.getWorldInfo();

				if (!var101.isDifficultyLocked() && var11.getDifficulty() != var101.getDifficulty()) {
					logger.info("Changing difficulty to {}, from {}",
							new Object[] { var11.getDifficulty(), var101.getDifficulty() });
					setDifficultyForAllWorlds(var11.getDifficulty());
				} else if (var11.isDifficultyLocked() && !var101.isDifficultyLocked()) {
					logger.info("Locking difficulty to {}", new Object[] { var11.getDifficulty() });
					final WorldServer[] var4 = worldServers;
					final int var5 = var4.length;

					for (int var6 = 0; var6 < var5; ++var6) {
						final WorldServer var7 = var4[var6];

						if (var7 != null) {
							var7.getWorldInfo().setDifficultyLocked(true);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canStructuresSpawn() {
		return false;
	}

	@Override
	public WorldSettings.GameType getGameType() {
		return theWorldSettings.getGameType();
	}

	/**
	 * Get the server's difficulty
	 */
	@Override
	public EnumDifficulty getDifficulty() {
		return Minecraft.theWorld == null ? Minecraft.gameSettings.difficulty
				: Minecraft.theWorld.getWorldInfo().getDifficulty();
	}

	/**
	 * Defaults to false.
	 */
	@Override
	public boolean isHardcore() {
		return theWorldSettings.getHardcoreEnabled();
	}

	@Override
	public File getDataDirectory() {
		return mc.mcDataDir;
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

	/**
	 * Called on exit from the main run() loop.
	 */
	@Override
	protected void finalTick(final CrashReport report) {
		mc.crashed(report);
	}

	/**
	 * Adds the server info, including from theWorldServer, to the crash report.
	 */
	@Override
	public CrashReport addServerInfoToCrashReport(CrashReport report) {
		report = super.addServerInfoToCrashReport(report);
		report.getCategory().addCrashSectionCallable("Type", new Callable() {
			@Override
			public String call() {
				return "Integrated Server (map_client.txt)";
			}
		});
		report.getCategory().addCrashSectionCallable("Is Modded", new Callable() {
			@Override
			public String call() {
				String var1 = ClientBrandRetriever.getClientModName();

				if (!var1.equals("vanilla")) {
					return "Definitely; Client brand changed to \'" + var1 + "\'";
				} else {
					var1 = IntegratedServer.this.getServerModName();
					return !var1.equals("vanilla") ? "Definitely; Server brand changed to \'" + var1 + "\'"
							: Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated"
									: "Probably not. Jar signature remains and both client + server brands are untouched.";
				}
			}
		});
		return report;
	}

	@Override
	public void setDifficultyForAllWorlds(final EnumDifficulty difficulty) {
		super.setDifficultyForAllWorlds(difficulty);

		if (Minecraft.theWorld != null) {
			Minecraft.theWorld.getWorldInfo().setDifficulty(difficulty);
		}
	}

	@Override
	public void addServerStatsToSnooper(final PlayerUsageSnooper playerSnooper) {
		super.addServerStatsToSnooper(playerSnooper);
		playerSnooper.addClientStat("snooper_partner", mc.getPlayerUsageSnooper().getUniqueID());
	}

	/**
	 * Returns whether snooping is enabled or not.
	 */
	@Override
	public boolean isSnooperEnabled() {
		return Minecraft.getMinecraft().isSnooperEnabled();
	}

	/**
	 * On dedicated does nothing. On integrated, sets commandsAllowedForAll,
	 * gameType and allows external connections.
	 */
	@Override
	public String shareToLAN(final WorldSettings.GameType type, final boolean allowCheats) {
		try {
			int var6 = -1;

			try {
				var6 = HttpUtil.getSuitableLanPort();
			} catch (final IOException var5) {
			}

			if (var6 <= 0) {
				var6 = 25564;
			}

			getNetworkSystem().addLanEndpoint((InetAddress) null, var6);
			logger.info("Started on " + var6);
			isPublic = true;
			lanServerPing = new ThreadLanServerPing(getMOTD(), var6 + "");
			lanServerPing.start();
			getConfigurationManager().func_152604_a(type);
			getConfigurationManager().setCommandsAllowedForAll(allowCheats);
			return var6 + "";
		} catch (final IOException var61) {
			return null;
		}
	}

	/**
	 * Saves all necessary data as preparation for stopping the server.
	 */
	@Override
	public void stopServer() {
		super.stopServer();

		if (lanServerPing != null) {
			lanServerPing.interrupt();
			lanServerPing = null;
		}
	}

	/**
	 * Sets the serverRunning variable to false, in order to get the server to
	 * shut down.
	 */
	@Override
	public void initiateShutdown() {
		Futures.getUnchecked(addScheduledTask(new Runnable() {
			@Override
			public void run() {
				final ArrayList var1 = Lists
						.newArrayList(IntegratedServer.this.getConfigurationManager().playerEntityList);
				final Iterator var2 = var1.iterator();

				while (var2.hasNext()) {
					final EntityPlayerMP var3 = (EntityPlayerMP) var2.next();
					IntegratedServer.this.getConfigurationManager().playerLoggedOut(var3);
				}
			}
		}));
		super.initiateShutdown();

		if (lanServerPing != null) {
			lanServerPing.interrupt();
			lanServerPing = null;
		}
	}

	public void func_175592_a() {
		func_175585_v();
	}

	/**
	 * Returns true if this integrated server is open to LAN
	 */
	public boolean getPublic() {
		return isPublic;
	}

	/**
	 * Sets the game type for all worlds.
	 */
	@Override
	public void setGameType(final WorldSettings.GameType gameMode) {
		getConfigurationManager().func_152604_a(gameMode);
	}

	/**
	 * Return whether command blocks are enabled.
	 */
	@Override
	public boolean isCommandBlockEnabled() {
		return true;
	}

	@Override
	public int getOpPermissionLevel() {
		return 4;
	}
}
