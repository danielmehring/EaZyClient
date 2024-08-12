package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import java.util.concurrent.Callable;

public class WorldInfo {

public static final int EaZy = 1851;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;

	/** Holds the seed of the currently world. */
	private long randomSeed;
	private WorldType terrainType;
	private String generatorOptions;

	/** The spawn zone position X coordinate. */
	private int spawnX;

	/** The spawn zone position Y coordinate. */
	private int spawnY;

	/** The spawn zone position Z coordinate. */
	private int spawnZ;

	/** Total time for this world. */
	private long totalTime;

	/** The current world time in ticks, ranging from 0 to 23999. */
	private long worldTime;

	/** The last time the player was in this world. */
	private long lastTimePlayed;

	/** The size of entire save of current world on the disk, isn't exactly. */
	private long sizeOnDisk;
	private NBTTagCompound playerTag;
	private int dimension;

	/** The name of the save defined at world creation. */
	private String levelName;

	/** Introduced in beta 1.3, is the save version for future control. */
	private int saveVersion;
	private int cleanWeatherTime;

	/** True if it's raining, false otherwise. */
	private boolean raining;

	/** Number of ticks until next rain. */
	private int rainTime;

	/** Is thunderbolts failing now? */
	private boolean thundering;

	/** Number of ticks untils next thunderbolt. */
	private int thunderTime;

	/** The Game Type. */
	private WorldSettings.GameType theGameType;

	/**
	 * Whether the map features (e.g. strongholds) generation is enabled or
	 * disabled.
	 */
	private boolean mapFeaturesEnabled;

	/** Hardcore mode flag */
	private boolean hardcore;
	private boolean allowCommands;
	private boolean initialized;
	private EnumDifficulty difficulty;
	private boolean difficultyLocked;
	private double borderCenterX;
	private double borderCenterZ;
	private double borderSize;
	private long borderSizeLerpTime;
	private double borderSizeLerpTarget;
	private double borderSafeZone;
	private double borderDamagePerBlock;
	private int borderWarningDistance;
	private int borderWarningTime;
	private GameRules theGameRules;
	// private static final String __OBFID = "http://https://fuckuskid00000587";

	protected WorldInfo() {
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		borderCenterX = 0.0D;
		borderCenterZ = 0.0D;
		borderSize = 6.0E7D;
		borderSizeLerpTime = 0L;
		borderSizeLerpTarget = 0.0D;
		borderSafeZone = 5.0D;
		borderDamagePerBlock = 0.2D;
		borderWarningDistance = 5;
		borderWarningTime = 15;
		theGameRules = new GameRules();
	}

	public WorldInfo(final NBTTagCompound nbt) {
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		borderCenterX = 0.0D;
		borderCenterZ = 0.0D;
		borderSize = 6.0E7D;
		borderSizeLerpTime = 0L;
		borderSizeLerpTarget = 0.0D;
		borderSafeZone = 5.0D;
		borderDamagePerBlock = 0.2D;
		borderWarningDistance = 5;
		borderWarningTime = 15;
		theGameRules = new GameRules();
		randomSeed = nbt.getLong("RandomSeed");

		if (nbt.hasKey("generatorName", 8)) {
			final String var2 = nbt.getString("generatorName");
			terrainType = WorldType.parseWorldType(var2);

			if (terrainType == null) {
				terrainType = WorldType.DEFAULT;
			} else if (terrainType.isVersioned()) {
				int var3 = 0;

				if (nbt.hasKey("generatorVersion", 99)) {
					var3 = nbt.getInteger("generatorVersion");
				}

				terrainType = terrainType.getWorldTypeForGeneratorVersion(var3);
			}

			if (nbt.hasKey("generatorOptions", 8)) {
				generatorOptions = nbt.getString("generatorOptions");
			}
		}

		theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));

		if (nbt.hasKey("MapFeatures", 99)) {
			mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
		} else {
			mapFeaturesEnabled = true;
		}

		spawnX = nbt.getInteger("SpawnX");
		spawnY = nbt.getInteger("SpawnY");
		spawnZ = nbt.getInteger("SpawnZ");
		totalTime = nbt.getLong("Time");

		if (nbt.hasKey("DayTime", 99)) {
			worldTime = nbt.getLong("DayTime");
		} else {
			worldTime = totalTime;
		}

		lastTimePlayed = nbt.getLong("LastPlayed");
		sizeOnDisk = nbt.getLong("SizeOnDisk");
		levelName = nbt.getString("LevelName");
		saveVersion = nbt.getInteger("version");
		cleanWeatherTime = nbt.getInteger("clearWeatherTime");
		rainTime = nbt.getInteger("rainTime");
		raining = nbt.getBoolean("raining");
		thunderTime = nbt.getInteger("thunderTime");
		thundering = nbt.getBoolean("thundering");
		hardcore = nbt.getBoolean("hardcore");

		if (nbt.hasKey("initialized", 99)) {
			initialized = nbt.getBoolean("initialized");
		} else {
			initialized = true;
		}

		if (nbt.hasKey("allowCommands", 99)) {
			allowCommands = nbt.getBoolean("allowCommands");
		} else {
			allowCommands = theGameType == WorldSettings.GameType.CREATIVE;
		}

		if (nbt.hasKey("Player", 10)) {
			playerTag = nbt.getCompoundTag("Player");
			dimension = playerTag.getInteger("Dimension");
		}

		if (nbt.hasKey("GameRules", 10)) {
			theGameRules.readGameRulesFromNBT(nbt.getCompoundTag("GameRules"));
		}

		if (nbt.hasKey("Difficulty", 99)) {
			difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
		}

		if (nbt.hasKey("DifficultyLocked", 1)) {
			difficultyLocked = nbt.getBoolean("DifficultyLocked");
		}

		if (nbt.hasKey("BorderCenterX", 99)) {
			borderCenterX = nbt.getDouble("BorderCenterX");
		}

		if (nbt.hasKey("BorderCenterZ", 99)) {
			borderCenterZ = nbt.getDouble("BorderCenterZ");
		}

		if (nbt.hasKey("BorderSize", 99)) {
			borderSize = nbt.getDouble("BorderSize");
		}

		if (nbt.hasKey("BorderSizeLerpTime", 99)) {
			borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
		}

		if (nbt.hasKey("BorderSizeLerpTarget", 99)) {
			borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
		}

		if (nbt.hasKey("BorderSafeZone", 99)) {
			borderSafeZone = nbt.getDouble("BorderSafeZone");
		}

		if (nbt.hasKey("BorderDamagePerBlock", 99)) {
			borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
		}

		if (nbt.hasKey("BorderWarningBlocks", 99)) {
			borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
		}

		if (nbt.hasKey("BorderWarningTime", 99)) {
			borderWarningTime = nbt.getInteger("BorderWarningTime");
		}
	}

	public WorldInfo(final WorldSettings settings, final String name) {
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		borderCenterX = 0.0D;
		borderCenterZ = 0.0D;
		borderSize = 6.0E7D;
		borderSizeLerpTime = 0L;
		borderSizeLerpTarget = 0.0D;
		borderSafeZone = 5.0D;
		borderDamagePerBlock = 0.2D;
		borderWarningDistance = 5;
		borderWarningTime = 15;
		theGameRules = new GameRules();
		populateFromWorldSettings(settings);
		levelName = name;
		difficulty = DEFAULT_DIFFICULTY;
		initialized = false;
	}

	public void populateFromWorldSettings(final WorldSettings settings) {
		randomSeed = settings.getSeed();
		theGameType = settings.getGameType();
		mapFeaturesEnabled = settings.isMapFeaturesEnabled();
		hardcore = settings.getHardcoreEnabled();
		terrainType = settings.getTerrainType();
		generatorOptions = settings.getWorldName();
		allowCommands = settings.areCommandsAllowed();
	}

	public WorldInfo(final WorldInfo p_i2159_1_) {
		terrainType = WorldType.DEFAULT;
		generatorOptions = "";
		borderCenterX = 0.0D;
		borderCenterZ = 0.0D;
		borderSize = 6.0E7D;
		borderSizeLerpTime = 0L;
		borderSizeLerpTarget = 0.0D;
		borderSafeZone = 5.0D;
		borderDamagePerBlock = 0.2D;
		borderWarningDistance = 5;
		borderWarningTime = 15;
		theGameRules = new GameRules();
		randomSeed = p_i2159_1_.randomSeed;
		terrainType = p_i2159_1_.terrainType;
		generatorOptions = p_i2159_1_.generatorOptions;
		theGameType = p_i2159_1_.theGameType;
		mapFeaturesEnabled = p_i2159_1_.mapFeaturesEnabled;
		spawnX = p_i2159_1_.spawnX;
		spawnY = p_i2159_1_.spawnY;
		spawnZ = p_i2159_1_.spawnZ;
		totalTime = p_i2159_1_.totalTime;
		worldTime = p_i2159_1_.worldTime;
		lastTimePlayed = p_i2159_1_.lastTimePlayed;
		sizeOnDisk = p_i2159_1_.sizeOnDisk;
		playerTag = p_i2159_1_.playerTag;
		dimension = p_i2159_1_.dimension;
		levelName = p_i2159_1_.levelName;
		saveVersion = p_i2159_1_.saveVersion;
		rainTime = p_i2159_1_.rainTime;
		raining = p_i2159_1_.raining;
		thunderTime = p_i2159_1_.thunderTime;
		thundering = p_i2159_1_.thundering;
		hardcore = p_i2159_1_.hardcore;
		allowCommands = p_i2159_1_.allowCommands;
		initialized = p_i2159_1_.initialized;
		theGameRules = p_i2159_1_.theGameRules;
		difficulty = p_i2159_1_.difficulty;
		difficultyLocked = p_i2159_1_.difficultyLocked;
		borderCenterX = p_i2159_1_.borderCenterX;
		borderCenterZ = p_i2159_1_.borderCenterZ;
		borderSize = p_i2159_1_.borderSize;
		borderSizeLerpTime = p_i2159_1_.borderSizeLerpTime;
		borderSizeLerpTarget = p_i2159_1_.borderSizeLerpTarget;
		borderSafeZone = p_i2159_1_.borderSafeZone;
		borderDamagePerBlock = p_i2159_1_.borderDamagePerBlock;
		borderWarningTime = p_i2159_1_.borderWarningTime;
		borderWarningDistance = p_i2159_1_.borderWarningDistance;
	}

	/**
	 * Gets the NBTTagCompound for the worldInfo
	 */
	public NBTTagCompound getNBTTagCompound() {
		final NBTTagCompound var1 = new NBTTagCompound();
		updateTagCompound(var1, playerTag);
		return var1;
	}

	/**
	 * Creates a new NBTTagCompound for the world, with the given NBTTag as the
	 * "Player"
	 */
	public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbt) {
		final NBTTagCompound var2 = new NBTTagCompound();
		updateTagCompound(var2, nbt);
		return var2;
	}

	private void updateTagCompound(final NBTTagCompound nbt, final NBTTagCompound playerNbt) {
		nbt.setLong("RandomSeed", randomSeed);
		nbt.setString("generatorName", terrainType.getWorldTypeName());
		nbt.setInteger("generatorVersion", terrainType.getGeneratorVersion());
		nbt.setString("generatorOptions", generatorOptions);
		nbt.setInteger("GameType", theGameType.getID());
		nbt.setBoolean("MapFeatures", mapFeaturesEnabled);
		nbt.setInteger("SpawnX", spawnX);
		nbt.setInteger("SpawnY", spawnY);
		nbt.setInteger("SpawnZ", spawnZ);
		nbt.setLong("Time", totalTime);
		nbt.setLong("DayTime", worldTime);
		nbt.setLong("SizeOnDisk", sizeOnDisk);
		nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
		nbt.setString("LevelName", levelName);
		nbt.setInteger("version", saveVersion);
		nbt.setInteger("clearWeatherTime", cleanWeatherTime);
		nbt.setInteger("rainTime", rainTime);
		nbt.setBoolean("raining", raining);
		nbt.setInteger("thunderTime", thunderTime);
		nbt.setBoolean("thundering", thundering);
		nbt.setBoolean("hardcore", hardcore);
		nbt.setBoolean("allowCommands", allowCommands);
		nbt.setBoolean("initialized", initialized);
		nbt.setDouble("BorderCenterX", borderCenterX);
		nbt.setDouble("BorderCenterZ", borderCenterZ);
		nbt.setDouble("BorderSize", borderSize);
		nbt.setLong("BorderSizeLerpTime", borderSizeLerpTime);
		nbt.setDouble("BorderSafeZone", borderSafeZone);
		nbt.setDouble("BorderDamagePerBlock", borderDamagePerBlock);
		nbt.setDouble("BorderSizeLerpTarget", borderSizeLerpTarget);
		nbt.setDouble("BorderWarningBlocks", borderWarningDistance);
		nbt.setDouble("BorderWarningTime", borderWarningTime);

		if (difficulty != null) {
			nbt.setByte("Difficulty", (byte) difficulty.getDifficultyId());
		}

		nbt.setBoolean("DifficultyLocked", difficultyLocked);
		nbt.setTag("GameRules", theGameRules.writeGameRulesToNBT());

		if (playerNbt != null) {
			nbt.setTag("Player", playerNbt);
		}
	}

	/**
	 * Returns the seed of current world.
	 */
	public long getSeed() {
		return randomSeed;
	}

	/**
	 * Returns the x spawn position
	 */
	public int getSpawnX() {
		return spawnX;
	}

	/**
	 * Return the Y axis spawning point of the player.
	 */
	public int getSpawnY() {
		return spawnY;
	}

	/**
	 * Returns the z spawn position
	 */
	public int getSpawnZ() {
		return spawnZ;
	}

	public long getWorldTotalTime() {
		return totalTime;
	}

	/**
	 * Get current world time
	 */
	public long getWorldTime() {
		return worldTime;
	}

	public long getSizeOnDisk() {
		return sizeOnDisk;
	}

	/**
	 * Returns the player's NBTTagCompound to be loaded
	 */
	public NBTTagCompound getPlayerNBTTagCompound() {
		return playerTag;
	}

	/**
	 * Set the x spawn position to the passed in value
	 */
	public void setSpawnX(final int p_76058_1_) {
		spawnX = p_76058_1_;
	}

	/**
	 * Sets the y spawn position
	 */
	public void setSpawnY(final int p_76056_1_) {
		spawnY = p_76056_1_;
	}

	/**
	 * Set the z spawn position to the passed in value
	 */
	public void setSpawnZ(final int p_76087_1_) {
		spawnZ = p_76087_1_;
	}

	public void incrementTotalWorldTime(final long p_82572_1_) {
		totalTime = p_82572_1_;
	}

	/**
	 * Set current world time
	 */
	public void setWorldTime(final long p_76068_1_) {
		worldTime = p_76068_1_;
	}

	public void setSpawn(final BlockPos spawnPoint) {
		spawnX = spawnPoint.getX();
		spawnY = spawnPoint.getY();
		spawnZ = spawnPoint.getZ();
	}

	/**
	 * Get current world name
	 */
	public String getWorldName() {
		return levelName;
	}

	public void setWorldName(final String p_76062_1_) {
		levelName = p_76062_1_;
	}

	/**
	 * Returns the save version of this world
	 */
	public int getSaveVersion() {
		return saveVersion;
	}

	/**
	 * Sets the save version of the world
	 */
	public void setSaveVersion(final int p_76078_1_) {
		saveVersion = p_76078_1_;
	}

	/**
	 * Return the last time the player was in this world.
	 */
	public long getLastTimePlayed() {
		return lastTimePlayed;
	}

	public int func_176133_A() {
		return cleanWeatherTime;
	}

	public void func_176142_i(final int p_176142_1_) {
		cleanWeatherTime = p_176142_1_;
	}

	/**
	 * Returns true if it is thundering, false otherwise.
	 */
	public boolean isThundering() {
		return thundering;
	}

	/**
	 * Sets whether it is thundering or not.
	 */
	public void setThundering(final boolean p_76069_1_) {
		thundering = p_76069_1_;
	}

	/**
	 * Returns the number of ticks until next thunderbolt.
	 */
	public int getThunderTime() {
		return thunderTime;
	}

	/**
	 * Defines the number of ticks until next thunderbolt.
	 */
	public void setThunderTime(final int p_76090_1_) {
		thunderTime = p_76090_1_;
	}

	/**
	 * Returns true if it is raining, false otherwise.
	 */
	public boolean isRaining() {
		return raining;
	}

	/**
	 * Sets whether it is raining or not.
	 */
	public void setRaining(final boolean p_76084_1_) {
		raining = p_76084_1_;
	}

	/**
	 * Return the number of ticks until rain.
	 */
	public int getRainTime() {
		return rainTime;
	}

	/**
	 * Sets the number of ticks until rain.
	 */
	public void setRainTime(final int p_76080_1_) {
		rainTime = p_76080_1_;
	}

	/**
	 * Gets the GameType.
	 */
	public WorldSettings.GameType getGameType() {
		return theGameType;
	}

	/**
	 * Get whether the map features (e.g. strongholds) generation is enabled or
	 * disabled.
	 */
	public boolean isMapFeaturesEnabled() {
		return mapFeaturesEnabled;
	}

	public void setMapFeaturesEnabled(final boolean enabled) {
		mapFeaturesEnabled = enabled;
	}

	/**
	 * Sets the GameType.
	 */
	public void setGameType(final WorldSettings.GameType type) {
		theGameType = type;
	}

	/**
	 * Returns true if hardcore mode is enabled, otherwise false
	 */
	public boolean isHardcoreModeEnabled() {
		return hardcore;
	}

	public void setHardcore(final boolean hardcoreIn) {
		hardcore = hardcoreIn;
	}

	public WorldType getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(final WorldType p_76085_1_) {
		terrainType = p_76085_1_;
	}

	public String getGeneratorOptions() {
		return generatorOptions;
	}

	/**
	 * Returns true if commands are allowed on this World.
	 */
	public boolean areCommandsAllowed() {
		return allowCommands;
	}

	public void setAllowCommands(final boolean allow) {
		allowCommands = allow;
	}

	/**
	 * Returns true if the World is initialized.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the initialization status of the World.
	 */
	public void setServerInitialized(final boolean initializedIn) {
		initialized = initializedIn;
	}

	/**
	 * Gets the GameRules class Instance.
	 */
	public GameRules getGameRulesInstance() {
		return theGameRules;
	}

	public double func_176120_C() {
		return borderCenterX;
	}

	public double func_176126_D() {
		return borderCenterZ;
	}

	public double func_176137_E() {
		return borderSize;
	}

	public void func_176145_a(final double p_176145_1_) {
		borderSize = p_176145_1_;
	}

	public long func_176134_F() {
		return borderSizeLerpTime;
	}

	public void func_176135_e(final long p_176135_1_) {
		borderSizeLerpTime = p_176135_1_;
	}

	public double func_176132_G() {
		return borderSizeLerpTarget;
	}

	public void func_176118_b(final double p_176118_1_) {
		borderSizeLerpTarget = p_176118_1_;
	}

	public void func_176141_c(final double p_176141_1_) {
		borderCenterZ = p_176141_1_;
	}

	public void func_176124_d(final double p_176124_1_) {
		borderCenterX = p_176124_1_;
	}

	public double func_176138_H() {
		return borderSafeZone;
	}

	public void func_176129_e(final double p_176129_1_) {
		borderSafeZone = p_176129_1_;
	}

	public double func_176140_I() {
		return borderDamagePerBlock;
	}

	public void func_176125_f(final double p_176125_1_) {
		borderDamagePerBlock = p_176125_1_;
	}

	public int func_176131_J() {
		return borderWarningDistance;
	}

	public int func_176139_K() {
		return borderWarningTime;
	}

	public void func_176122_j(final int p_176122_1_) {
		borderWarningDistance = p_176122_1_;
	}

	public void func_176136_k(final int p_176136_1_) {
		borderWarningTime = p_176136_1_;
	}

	public EnumDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(final EnumDifficulty newDifficulty) {
		difficulty = newDifficulty;
	}

	public boolean isDifficultyLocked() {
		return difficultyLocked;
	}

	public void setDifficultyLocked(final boolean locked) {
		difficultyLocked = locked;
	}

	/**
	 * Adds this WorldInfo instance to the crash report.
	 */
	public void addToCrashReport(final CrashReportCategory category) {
		category.addCrashSectionCallable("Level seed", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000588";
			@Override
			public String call() {
				return String.valueOf(WorldInfo.this.getSeed());
			}
		});
		category.addCrashSectionCallable("Level generator", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000589";
			@Override
			public String call() {
				return String.format("ID %02d - %s, ver %d. Features enabled: %b",
						new Object[] { terrainType.getWorldTypeID(), terrainType.getWorldTypeName(),
                                                    terrainType.getGeneratorVersion(), mapFeaturesEnabled});
			}
		});
		category.addCrashSectionCallable("Level generator options", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000590";
			@Override
			public String call() {
				return generatorOptions;
			}
		});
		category.addCrashSectionCallable("Level spawn location", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000591";
			@Override
			public String call() {
				return CrashReportCategory.getCoordinateInfo(spawnX, spawnY, spawnZ);
			}
		});
		category.addCrashSectionCallable("Level time", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000592";
			@Override
			public String call() {
				return String.format("%d game time, %d day time",
						new Object[] { totalTime, worldTime});
			}
		});
		category.addCrashSectionCallable("Level dimension", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000593";
			@Override
			public String call() {
				return String.valueOf(dimension);
			}
		});
		category.addCrashSectionCallable("Level storage version", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000594";
			@Override
			public String call() {
				String var1 = "Unknown?";

				try {
					switch (saveVersion) {
						case 19132:
							var1 = "McRegion";
							break;

						case 19133:
							var1 = "Anvil";
					}
				} catch (final Throwable var3) {
				}

				return String.format("0x%05X - %s", new Object[] { saveVersion, var1 });
			}
		});
		category.addCrashSectionCallable("Level weather", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000595";
			@Override
			public String call() {
				return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)",
						new Object[] { rainTime, raining, thunderTime, thundering});
			}
		});
		category.addCrashSectionCallable("Level game mode", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000597";
			@Override
			public String call() {
				return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b",
						new Object[] { theGameType.getName(), theGameType.getID(), hardcore, allowCommands});
			}
		});
	}
}
