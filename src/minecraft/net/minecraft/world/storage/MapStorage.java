package net.minecraft.world.storage;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MapStorage {

public static final int EaZy = 1844;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ISaveHandler saveHandler;

	/** Map of item data String id to loaded MapDataBases */
	protected Map loadedDataMap = Maps.newHashMap();

	/** List of loaded MapDataBases. */
	private final List loadedDataList = Lists.newArrayList();

	/**
	 * Map of MapDataBase id String prefixes ('map' etc) to max known unique
	 * Short id (the 0 part etc) for that prefix
	 */
	private final Map idCounts = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000604";

	public MapStorage(final ISaveHandler p_i2162_1_) {
		saveHandler = p_i2162_1_;
		loadIdCounts();
	}

	/**
	 * Loads an existing MapDataBase corresponding to the given String id from
	 * disk, instantiating the given Class, or returns null if none such file
	 * exists. args: Class to instantiate, String dataid
	 */
	public WorldSavedData loadData(final Class p_75742_1_, final String p_75742_2_) {
		WorldSavedData var3 = (WorldSavedData) loadedDataMap.get(p_75742_2_);

		if (var3 != null) {
			return var3;
		} else {
			if (saveHandler != null) {
				try {
					final File var4 = saveHandler.getMapFileFromName(p_75742_2_);

					if (var4 != null && var4.exists()) {
						try {
							var3 = (WorldSavedData) p_75742_1_.getConstructor(new Class[] { String.class })
									.newInstance(new Object[] { p_75742_2_ });
						} catch (final Exception var7) {
							throw new RuntimeException("Failed to instantiate " + p_75742_1_.toString(), var7);
						}

						final FileInputStream var5 = new FileInputStream(var4);
						final NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
						var5.close();
						var3.readFromNBT(var6.getCompoundTag("data"));
					}
				} catch (final Exception var8) {
					var8.printStackTrace();
				}
			}

			if (var3 != null) {
				loadedDataMap.put(p_75742_2_, var3);
				loadedDataList.add(var3);
			}

			return var3;
		}
	}

	/**
	 * Assigns the given String id to the given MapDataBase, removing any
	 * existing ones of the same id.
	 */
	public void setData(final String p_75745_1_, final WorldSavedData p_75745_2_) {
		if (loadedDataMap.containsKey(p_75745_1_)) {
			loadedDataList.remove(loadedDataMap.remove(p_75745_1_));
		}

		loadedDataMap.put(p_75745_1_, p_75745_2_);
		loadedDataList.add(p_75745_2_);
	}

	/**
	 * Saves all dirty loaded MapDataBases to disk.
	 */
	public void saveAllData() {
		for (int var1 = 0; var1 < loadedDataList.size(); ++var1) {
			final WorldSavedData var2 = (WorldSavedData) loadedDataList.get(var1);

			if (var2.isDirty()) {
				saveData(var2);
				var2.setDirty(false);
			}
		}
	}

	/**
	 * Saves the given MapDataBase to disk.
	 */
	private void saveData(final WorldSavedData p_75747_1_) {
		if (saveHandler != null) {
			try {
				final File var2 = saveHandler.getMapFileFromName(p_75747_1_.mapName);

				if (var2 != null) {
					final NBTTagCompound var3 = new NBTTagCompound();
					p_75747_1_.writeToNBT(var3);
					final NBTTagCompound var4 = new NBTTagCompound();
					var4.setTag("data", var3);
					final FileOutputStream var5 = new FileOutputStream(var2);
					CompressedStreamTools.writeCompressed(var4, var5);
					var5.close();
				}
			} catch (final Exception var6) {
				var6.printStackTrace();
			}
		}
	}

	/**
	 * Loads the idCounts Map from the 'idcounts' file.
	 */
	private void loadIdCounts() {
		try {
			idCounts.clear();

			if (saveHandler == null) {
				return;
			}

			final File var1 = saveHandler.getMapFileFromName("idcounts");

			if (var1 != null && var1.exists()) {
				final DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
				final NBTTagCompound var3 = CompressedStreamTools.read(var2);
				var2.close();
				final Iterator var4 = var3.getKeySet().iterator();

				while (var4.hasNext()) {
					final String var5 = (String) var4.next();
					final NBTBase var6 = var3.getTag(var5);

					if (var6 instanceof NBTTagShort) {
						final NBTTagShort var7 = (NBTTagShort) var6;
						final short var9 = var7.getShort();
						idCounts.put(var5, var9);
					}
				}
			}
		} catch (final Exception var10) {
			var10.printStackTrace();
		}
	}

	/**
	 * Returns an unique new data id for the given prefix and saves the idCounts
	 * map to the 'idcounts' file.
	 */
	public int getUniqueDataId(final String p_75743_1_) {
		Short var2 = (Short) idCounts.get(p_75743_1_);

		if (var2 == null) {
			var2 = (short) 0;
		} else {
			var2 = (short) (var2 + 1);
		}

		idCounts.put(p_75743_1_, var2);

		if (saveHandler == null) {
			return var2;
		} else {
			try {
				final File var3 = saveHandler.getMapFileFromName("idcounts");

				if (var3 != null) {
					final NBTTagCompound var4 = new NBTTagCompound();
					final Iterator var5 = idCounts.keySet().iterator();

					while (var5.hasNext()) {
						final String var6 = (String) var5.next();
						final short var7 = ((Short) idCounts.get(var6));
						var4.setShort(var6, var7);
					}

					final DataOutputStream var9 = new DataOutputStream(new FileOutputStream(var3));
					CompressedStreamTools.write(var4, var9);
					var9.close();
				}
			} catch (final Exception var8) {
				var8.printStackTrace();
			}

			return var2;
		}
	}
}
