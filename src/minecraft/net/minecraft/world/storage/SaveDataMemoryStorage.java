package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

public class SaveDataMemoryStorage extends MapStorage {

public static final int EaZy = 1845;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001963";

	public SaveDataMemoryStorage() {
		super((ISaveHandler) null);
	}

	/**
	 * Loads an existing MapDataBase corresponding to the given String id from
	 * disk, instantiating the given Class, or returns null if none such file
	 * exists. args: Class to instantiate, String dataid
	 */
	@Override
	public WorldSavedData loadData(final Class p_75742_1_, final String p_75742_2_) {
		return (WorldSavedData) loadedDataMap.get(p_75742_2_);
	}

	/**
	 * Assigns the given String id to the given MapDataBase, removing any
	 * existing ones of the same id.
	 */
	@Override
	public void setData(final String p_75745_1_, final WorldSavedData p_75745_2_) {
		loadedDataMap.put(p_75745_1_, p_75745_2_);
	}

	/**
	 * Saves all dirty loaded MapDataBases to disk.
	 */
	@Override
	public void saveAllData() {}

	/**
	 * Returns an unique new data id for the given prefix and saves the idCounts
	 * map to the 'idcounts' file.
	 */
	@Override
	public int getUniqueDataId(final String p_75743_1_) {
		return 0;
	}
}
