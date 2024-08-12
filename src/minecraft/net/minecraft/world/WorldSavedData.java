package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public abstract class WorldSavedData {

public static final int EaZy = 1859;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The name of the map data nbt */
	public final String mapName;

	/** Whether this MapDataBase needs saving to disk. */
	private boolean dirty;
	// private static final String __OBFID = "http://https://fuckuskid00000580";

	public WorldSavedData(final String name) {
		mapName = name;
	}

	/**
	 * reads in data from the NBTTagCompound into this MapDataBase
	 */
	public abstract void readFromNBT(NBTTagCompound var1);

	/**
	 * write data to NBTTagCompound from this MapDataBase, similar to Entities
	 * and TileEntities
	 */
	public abstract void writeToNBT(NBTTagCompound var1);

	/**
	 * Marks this MapDataBase dirty, to be saved to disk when the level next
	 * saves.
	 */
	public void markDirty() {
		setDirty(true);
	}

	/**
	 * Sets the dirty state of this MapDataBase, whether it needs saving to
	 * disk.
	 */
	public void setDirty(final boolean isDirty) {
		dirty = isDirty;
	}

	/**
	 * Whether this MapDataBase needs saving to disk.
	 */
	public boolean isDirty() {
		return dirty;
	}
}
