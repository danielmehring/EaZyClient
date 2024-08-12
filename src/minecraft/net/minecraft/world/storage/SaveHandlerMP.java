package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

import java.io.File;

public class SaveHandlerMP implements ISaveHandler {

public static final int EaZy = 1849;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000602";

	/**
	 * Loads and returns the world info
	 */
	@Override
	public WorldInfo loadWorldInfo() {
		return null;
	}

	/**
	 * Checks the session lock to prevent save collisions
	 */
	@Override
	public void checkSessionLock() throws MinecraftException {}

	/**
	 * initializes and returns the chunk loader for the specified world provider
	 */
	@Override
	public IChunkLoader getChunkLoader(final WorldProvider p_75763_1_) {
		return null;
	}

	/**
	 * Saves the given World Info with the given NBTTagCompound as the Player.
	 */
	@Override
	public void saveWorldInfoWithPlayer(final WorldInfo p_75755_1_, final NBTTagCompound p_75755_2_) {}

	/**
	 * used to update level.dat from old format to MCRegion format
	 */
	@Override
	public void saveWorldInfo(final WorldInfo p_75761_1_) {}

	@Override
	public IPlayerFileData getPlayerNBTManager() {
		return null;
	}

	/**
	 * Called to flush all changes to disk, waiting for them to complete.
	 */
	@Override
	public void flush() {}

	/**
	 * Gets the file location of the given map
	 */
	@Override
	public File getMapFileFromName(final String p_75758_1_) {
		return null;
	}

	/**
	 * Returns the name of the directory where world information is saved.
	 */
	@Override
	public String getWorldDirectoryName() {
		return "none";
	}

	/**
	 * Gets the File object corresponding to the base directory of this world.
	 */
	@Override
	public File getWorldDirectory() {
		return null;
	}
}
