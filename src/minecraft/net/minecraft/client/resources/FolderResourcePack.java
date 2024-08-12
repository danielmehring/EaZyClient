package net.minecraft.client.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.google.common.collect.Sets;

public class FolderResourcePack extends AbstractResourcePack {

public static final int EaZy = 874;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001076";

	public FolderResourcePack(final File p_i1291_1_) {
		super(p_i1291_1_);
	}

	@Override
	protected InputStream getInputStreamByName(final String p_110591_1_) throws IOException {
		return new BufferedInputStream(new FileInputStream(new File(resourcePackFile, p_110591_1_)));
	}

	@Override
	protected boolean hasResourceName(final String p_110593_1_) {
		return new File(resourcePackFile, p_110593_1_).isFile();
	}

	@Override
	public Set getResourceDomains() {
		final HashSet var1 = Sets.newHashSet();
		final File var2 = new File(resourcePackFile, "assets/");

		if (var2.isDirectory()) {
			final File[] var3 = var2.listFiles((java.io.FileFilter) DirectoryFileFilter.DIRECTORY);
			final int var4 = var3.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				final File var6 = var3[var5];
				final String var7 = getRelativeName(var2, var6);

				if (!var7.equals(var7.toLowerCase())) {
					logNameNotLowercase(var7);
				} else {
					var1.add(var7.substring(0, var7.length() - 1));
				}
			}
		}

		return var1;
	}
}
