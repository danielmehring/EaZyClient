package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

public class RegionFileCache {

public static final int EaZy = 1713;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** A map containing Files as keys and RegionFiles as values */
	private static final Map regionsByFilename = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000383";

	public static synchronized RegionFile createOrLoadRegionFile(final File worldDir, final int chunkX,
			final int chunkZ) {
		final File var3 = new File(worldDir, "region");
		final File var4 = new File(var3, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
		final RegionFile var5 = (RegionFile) regionsByFilename.get(var4);

		if (var5 != null) {
			return var5;
		} else {
			if (!var3.exists()) {
				var3.mkdirs();
			}

			if (regionsByFilename.size() >= 256) {
				clearRegionFileReferences();
			}

			final RegionFile var6 = new RegionFile(var4);
			regionsByFilename.put(var4, var6);
			return var6;
		}
	}

	/**
	 * clears region file references
	 */
	public static synchronized void clearRegionFileReferences() {
		final Iterator var0 = regionsByFilename.values().iterator();

		while (var0.hasNext()) {
			final RegionFile var1 = (RegionFile) var0.next();

			try {
				if (var1 != null) {
					var1.close();
				}
			} catch (final IOException var3) {
				var3.printStackTrace();
			}
		}

		regionsByFilename.clear();
	}

	/**
	 * Returns an input stream for the specified chunk. Args: worldDir, chunkX,
	 * chunkZ
	 */
	public static DataInputStream getChunkInputStream(final File worldDir, final int chunkX, final int chunkZ) {
		final RegionFile var3 = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
		return var3.getChunkDataInputStream(chunkX & 31, chunkZ & 31);
	}

	/**
	 * Returns an output stream for the specified chunk. Args: worldDir, chunkX,
	 * chunkZ
	 */
	public static DataOutputStream getChunkOutputStream(final File worldDir, final int chunkX, final int chunkZ) {
		final RegionFile var3 = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
		return var3.getChunkDataOutputStream(chunkX & 31, chunkZ & 31);
	}
}
