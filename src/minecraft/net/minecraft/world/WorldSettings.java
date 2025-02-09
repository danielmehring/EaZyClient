package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;

public final class WorldSettings {

public static final int EaZy = 1862;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The seed for the map. */
	private final long seed;

	/** The EnumGameType. */
	private final WorldSettings.GameType theGameType;

	/**
	 * Switch for the map features. 'true' for enabled, 'false' for disabled.
	 */
	private final boolean mapFeaturesEnabled;

	/** True if hardcore mode is enabled */
	private final boolean hardcoreEnabled;
	private final WorldType terrainType;

	/** True if Commands (cheats) are allowed. */
	private boolean commandsAllowed;

	/** True if the Bonus Chest is enabled. */
	private boolean bonusChestEnabled;
	private String worldName;
	// private static final String __OBFID = "http://https://fuckuskid00000147";

	public WorldSettings(final long seedIn, final WorldSettings.GameType gameType, final boolean enableMapFeatures,
			final boolean hardcoreMode, final WorldType worldTypeIn) {
		worldName = "";
		seed = seedIn;
		theGameType = gameType;
		mapFeaturesEnabled = enableMapFeatures;
		hardcoreEnabled = hardcoreMode;
		terrainType = worldTypeIn;
	}

	public WorldSettings(final WorldInfo info) {
		this(info.getSeed(), info.getGameType(), info.isMapFeaturesEnabled(), info.isHardcoreModeEnabled(),
				info.getTerrainType());
	}

	/**
	 * Enables the bonus chest.
	 */
	public WorldSettings enableBonusChest() {
		bonusChestEnabled = true;
		return this;
	}

	/**
	 * Enables Commands (cheats).
	 */
	public WorldSettings enableCommands() {
		commandsAllowed = true;
		return this;
	}

	public WorldSettings setWorldName(final String name) {
		worldName = name;
		return this;
	}

	/**
	 * Returns true if the Bonus Chest is enabled.
	 */
	public boolean isBonusChestEnabled() {
		return bonusChestEnabled;
	}

	/**
	 * Returns the seed for the world.
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Gets the game type.
	 */
	public WorldSettings.GameType getGameType() {
		return theGameType;
	}

	/**
	 * Returns true if hardcore mode is enabled, otherwise false
	 */
	public boolean getHardcoreEnabled() {
		return hardcoreEnabled;
	}

	/**
	 * Get whether the map features (e.g. strongholds) generation is enabled or
	 * disabled.
	 */
	public boolean isMapFeaturesEnabled() {
		return mapFeaturesEnabled;
	}

	public WorldType getTerrainType() {
		return terrainType;
	}

	/**
	 * Returns true if Commands (cheats) are allowed.
	 */
	public boolean areCommandsAllowed() {
		return commandsAllowed;
	}

	/**
	 * Gets the GameType by ID
	 */
	public static WorldSettings.GameType getGameTypeById(final int id) {
		return WorldSettings.GameType.getByID(id);
	}

	public String getWorldName() {
		return worldName;
	}

	public static enum GameType {
		NOT_SET("NOT_SET", 0, -1, ""), SURVIVAL("SURVIVAL", 1, 0, "survival"), CREATIVE("CREATIVE", 2, 1,
				"creative"), ADVENTURE("ADVENTURE", 3, 2, "adventure"), SPECTATOR("SPECTATOR", 4, 3, "spectator");
		int id;
		String name;

		private GameType(final String p_i1956_1_, final int p_i1956_2_, final int typeId, final String nameIn) {
			id = typeId;
			name = nameIn;
		}

		public int getID() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void configurePlayerCapabilities(final PlayerCapabilities capabilities) {
			if (this == CREATIVE) {
				capabilities.allowFlying = true;
				capabilities.isCreativeMode = true;
				capabilities.disableDamage = true;
			} else if (this == SPECTATOR) {
				capabilities.allowFlying = true;
				capabilities.isCreativeMode = false;
				capabilities.disableDamage = true;
				capabilities.isFlying = true;
			} else {
				capabilities.allowFlying = false;
				capabilities.isCreativeMode = false;
				capabilities.disableDamage = false;
				capabilities.isFlying = false;
			}

			capabilities.allowEdit = !isAdventure();
		}

		public boolean isAdventure() {
			return this == ADVENTURE || this == SPECTATOR;
		}

		public boolean isCreative() {
			return this == CREATIVE;
		}

		public boolean isSurvivalOrAdventure() {
			return this == SURVIVAL || this == ADVENTURE;
		}

		public static WorldSettings.GameType getByID(final int idIn) {
			final WorldSettings.GameType[] var1 = values();
			final int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				final WorldSettings.GameType var4 = var1[var3];

				if (var4.id == idIn) {
					return var4;
				}
			}

			return SURVIVAL;
		}

		public static WorldSettings.GameType getByName(final String p_77142_0_) {
			final WorldSettings.GameType[] var1 = values();
			final int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				final WorldSettings.GameType var4 = var1[var3];

				if (var4.name.equals(p_77142_0_)) {
					return var4;
				}
			}

			return SURVIVAL;
		}
	}
}
