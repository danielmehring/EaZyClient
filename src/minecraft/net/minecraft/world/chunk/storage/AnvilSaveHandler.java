package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class AnvilSaveHandler extends SaveHandler {

public static final int EaZy = 1707;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000581";

	public AnvilSaveHandler(final File p_i2142_1_, final String p_i2142_2_, final boolean p_i2142_3_) {
		super(p_i2142_1_, p_i2142_2_, p_i2142_3_);
	}

	/**
	 * initializes and returns the chunk loader for the specified world provider
	 */
	@Override
	public IChunkLoader getChunkLoader(final WorldProvider p_75763_1_) {
		final File var2 = getWorldDirectory();
		File var3;

		if (p_75763_1_ instanceof WorldProviderHell) {
			var3 = new File(var2, "DIM-1");
			var3.mkdirs();
			return new AnvilChunkLoader(var3);
		} else if (p_75763_1_ instanceof WorldProviderEnd) {
			var3 = new File(var2, "DIM1");
			var3.mkdirs();
			return new AnvilChunkLoader(var3);
		} else {
			return new AnvilChunkLoader(var2);
		}
	}

	/**
	 * Saves the given World Info with the given NBTTagCompound as the Player.
	 */
	@Override
	public void saveWorldInfoWithPlayer(final WorldInfo p_75755_1_, final NBTTagCompound p_75755_2_) {
		p_75755_1_.setSaveVersion(19133);
		super.saveWorldInfoWithPlayer(p_75755_1_, p_75755_2_);
	}

	/**
	 * Called to flush all changes to disk, waiting for them to complete.
	 */
	@Override
	public void flush() {
		try {
			ThreadedFileIOBase.func_178779_a().waitForFinish();
		} catch (final InterruptedException var2) {
			var2.printStackTrace();
		}

		RegionFileCache.clearRegionFileReferences();
	}
}
