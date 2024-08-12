package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo extends WorldInfo {

public static final int EaZy = 1838;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Instance of WorldInfo. */
	private final WorldInfo theWorldInfo;
	// private static final String __OBFID = "http://https://fuckuskid00000584";

	public DerivedWorldInfo(final WorldInfo p_i2145_1_) {
		theWorldInfo = p_i2145_1_;
	}

	/**
	 * Gets the NBTTagCompound for the worldInfo
	 */
	@Override
	public NBTTagCompound getNBTTagCompound() {
		return theWorldInfo.getNBTTagCompound();
	}

	/**
	 * Creates a new NBTTagCompound for the world, with the given NBTTag as the
	 * "Player"
	 */
	@Override
	public NBTTagCompound cloneNBTCompound(final NBTTagCompound nbt) {
		return theWorldInfo.cloneNBTCompound(nbt);
	}

	/**
	 * Returns the seed of current world.
	 */
	@Override
	public long getSeed() {
		return theWorldInfo.getSeed();
	}

	/**
	 * Returns the x spawn position
	 */
	@Override
	public int getSpawnX() {
		return theWorldInfo.getSpawnX();
	}

	/**
	 * Return the Y axis spawning point of the player.
	 */
	@Override
	public int getSpawnY() {
		return theWorldInfo.getSpawnY();
	}

	/**
	 * Returns the z spawn position
	 */
	@Override
	public int getSpawnZ() {
		return theWorldInfo.getSpawnZ();
	}

	@Override
	public long getWorldTotalTime() {
		return theWorldInfo.getWorldTotalTime();
	}

	/**
	 * Get current world time
	 */
	@Override
	public long getWorldTime() {
		return theWorldInfo.getWorldTime();
	}

	@Override
	public long getSizeOnDisk() {
		return theWorldInfo.getSizeOnDisk();
	}

	/**
	 * Returns the player's NBTTagCompound to be loaded
	 */
	@Override
	public NBTTagCompound getPlayerNBTTagCompound() {
		return theWorldInfo.getPlayerNBTTagCompound();
	}

	/**
	 * Get current world name
	 */
	@Override
	public String getWorldName() {
		return theWorldInfo.getWorldName();
	}

	/**
	 * Returns the save version of this world
	 */
	@Override
	public int getSaveVersion() {
		return theWorldInfo.getSaveVersion();
	}

	/**
	 * Return the last time the player was in this world.
	 */
	@Override
	public long getLastTimePlayed() {
		return theWorldInfo.getLastTimePlayed();
	}

	/**
	 * Returns true if it is thundering, false otherwise.
	 */
	@Override
	public boolean isThundering() {
		return theWorldInfo.isThundering();
	}

	/**
	 * Returns the number of ticks until next thunderbolt.
	 */
	@Override
	public int getThunderTime() {
		return theWorldInfo.getThunderTime();
	}

	/**
	 * Returns true if it is raining, false otherwise.
	 */
	@Override
	public boolean isRaining() {
		return theWorldInfo.isRaining();
	}

	/**
	 * Return the number of ticks until rain.
	 */
	@Override
	public int getRainTime() {
		return theWorldInfo.getRainTime();
	}

	/**
	 * Gets the GameType.
	 */
	@Override
	public WorldSettings.GameType getGameType() {
		return theWorldInfo.getGameType();
	}

	/**
	 * Set the x spawn position to the passed in value
	 */
	@Override
	public void setSpawnX(final int p_76058_1_) {}

	/**
	 * Sets the y spawn position
	 */
	@Override
	public void setSpawnY(final int p_76056_1_) {}

	/**
	 * Set the z spawn position to the passed in value
	 */
	@Override
	public void setSpawnZ(final int p_76087_1_) {}

	@Override
	public void incrementTotalWorldTime(final long p_82572_1_) {}

	/**
	 * Set current world time
	 */
	@Override
	public void setWorldTime(final long p_76068_1_) {}

	@Override
	public void setSpawn(final BlockPos spawnPoint) {}

	@Override
	public void setWorldName(final String p_76062_1_) {}

	/**
	 * Sets the save version of the world
	 */
	@Override
	public void setSaveVersion(final int p_76078_1_) {}

	/**
	 * Sets whether it is thundering or not.
	 */
	@Override
	public void setThundering(final boolean p_76069_1_) {}

	/**
	 * Defines the number of ticks until next thunderbolt.
	 */
	@Override
	public void setThunderTime(final int p_76090_1_) {}

	/**
	 * Sets whether it is raining or not.
	 */
	@Override
	public void setRaining(final boolean p_76084_1_) {}

	/**
	 * Sets the number of ticks until rain.
	 */
	@Override
	public void setRainTime(final int p_76080_1_) {}

	/**
	 * Get whether the map features (e.g. strongholds) generation is enabled or
	 * disabled.
	 */
	@Override
	public boolean isMapFeaturesEnabled() {
		return theWorldInfo.isMapFeaturesEnabled();
	}

	/**
	 * Returns true if hardcore mode is enabled, otherwise false
	 */
	@Override
	public boolean isHardcoreModeEnabled() {
		return theWorldInfo.isHardcoreModeEnabled();
	}

	@Override
	public WorldType getTerrainType() {
		return theWorldInfo.getTerrainType();
	}

	@Override
	public void setTerrainType(final WorldType p_76085_1_) {}

	/**
	 * Returns true if commands are allowed on this World.
	 */
	@Override
	public boolean areCommandsAllowed() {
		return theWorldInfo.areCommandsAllowed();
	}

	@Override
	public void setAllowCommands(final boolean allow) {}

	/**
	 * Returns true if the World is initialized.
	 */
	@Override
	public boolean isInitialized() {
		return theWorldInfo.isInitialized();
	}

	/**
	 * Sets the initialization status of the World.
	 */
	@Override
	public void setServerInitialized(final boolean initializedIn) {}

	/**
	 * Gets the GameRules class Instance.
	 */
	@Override
	public GameRules getGameRulesInstance() {
		return theWorldInfo.getGameRulesInstance();
	}

	@Override
	public EnumDifficulty getDifficulty() {
		return theWorldInfo.getDifficulty();
	}

	@Override
	public void setDifficulty(final EnumDifficulty newDifficulty) {}

	@Override
	public boolean isDifficultyLocked() {
		return theWorldInfo.isDifficultyLocked();
	}

	@Override
	public void setDifficultyLocked(final boolean locked) {}
}
