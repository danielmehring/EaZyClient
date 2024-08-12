package net.minecraft.realms;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveFormatComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class RealmsAnvilLevelStorageSource {

public static final int EaZy = 1502;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ISaveFormat levelStorageSource;
	// private static final String __OBFID = "http://https://fuckuskid00001856";

	public RealmsAnvilLevelStorageSource(final ISaveFormat p_i1106_1_) {
		levelStorageSource = p_i1106_1_;
	}

	public String getName() {
		return levelStorageSource.func_154333_a();
	}

	public boolean levelExists(final String p_levelExists_1_) {
		return levelStorageSource.canLoadWorld(p_levelExists_1_);
	}

	public boolean convertLevel(final String p_convertLevel_1_, final IProgressUpdate p_convertLevel_2_) {
		return levelStorageSource.convertMapFormat(p_convertLevel_1_, p_convertLevel_2_);
	}

	public boolean requiresConversion(final String p_requiresConversion_1_) {
		return levelStorageSource.isOldMapFormat(p_requiresConversion_1_);
	}

	public boolean isNewLevelIdAcceptable(final String p_isNewLevelIdAcceptable_1_) {
		return levelStorageSource.func_154335_d(p_isNewLevelIdAcceptable_1_);
	}

	public boolean deleteLevel(final String p_deleteLevel_1_) {
		return levelStorageSource.deleteWorldDirectory(p_deleteLevel_1_);
	}

	public boolean isConvertible(final String p_isConvertible_1_) {
		return levelStorageSource.func_154334_a(p_isConvertible_1_);
	}

	public void renameLevel(final String p_renameLevel_1_, final String p_renameLevel_2_) {
		levelStorageSource.renameWorld(p_renameLevel_1_, p_renameLevel_2_);
	}

	public void clearAll() {
		levelStorageSource.flushCache();
	}

	public List getLevelList() throws AnvilConverterException {
		final ArrayList var1 = Lists.newArrayList();
		final Iterator var2 = levelStorageSource.getSaveList().iterator();

		while (var2.hasNext()) {
			final SaveFormatComparator var3 = (SaveFormatComparator) var2.next();
			var1.add(new RealmsLevelSummary(var3));
		}

		return var1;
	}
}
