package optifine;

import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResUtils {

public static final int EaZy = 1963;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static String[] collectFiles(final String prefix, final String suffix) {
		return collectFiles(new String[] { prefix }, new String[] { suffix });
	}

	public static String[] collectFiles(final String[] prefixes, final String[] suffixes) {
		final LinkedHashSet setPaths = new LinkedHashSet();
		final IResourcePack[] rps = Config.getResourcePacks();

		for (final IResourcePack rp : rps) {
			final String[] ps = collectFiles(rp, prefixes, suffixes, (String[]) null);
			setPaths.addAll(Arrays.asList(ps));
		}

		final String[] var7 = (String[]) setPaths.toArray(new String[setPaths.size()]);
		return var7;
	}

	public static String[] collectFiles(final IResourcePack rp, final String prefix, final String suffix,
			final String[] defaultPaths) {
		return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
	}

	public static String[] collectFiles(final IResourcePack rp, final String[] prefixes, final String[] suffixes) {
		return collectFiles(rp, prefixes, suffixes, (String[]) null);
	}

	public static String[] collectFiles(final IResourcePack rp, final String[] prefixes, final String[] suffixes,
			final String[] defaultPaths) {
		if (rp instanceof DefaultResourcePack) {
			return collectFilesFixed(rp, defaultPaths);
		} else if (!(rp instanceof AbstractResourcePack)) {
			return new String[0];
		} else {
			final AbstractResourcePack arp = (AbstractResourcePack) rp;
			final File tpFile = arp.resourcePackFile;
			return tpFile == null ? new String[0]
					: tpFile.isDirectory() ? collectFilesFolder(tpFile, "", prefixes, suffixes)
							: tpFile.isFile() ? collectFilesZIP(tpFile, prefixes, suffixes) : new String[0];
		}
	}

	private static String[] collectFilesFixed(final IResourcePack rp, final String[] paths) {
		if (paths == null) {
			return new String[0];
		} else {
			final ArrayList list = new ArrayList();

			for (final String path : paths) {
				final ResourceLocation loc = new ResourceLocation(path);

				if (rp.resourceExists(loc)) {
					list.add(path);
				}
			}

			final String[] var6 = (String[]) list.toArray(new String[list.size()]);
			return var6;
		}
	}

	private static String[] collectFilesFolder(final File tpFile, final String basePath, final String[] prefixes,
			final String[] suffixes) {
		final ArrayList list = new ArrayList();
		final String prefixAssets = "assets/minecraft/";
		final File[] files = tpFile.listFiles();

		if (files == null) {
			return new String[0];
		} else {
			for (final File file : files) {
				String dirPath;

				if (file.isFile()) {
					dirPath = basePath + file.getName();

					if (dirPath.startsWith(prefixAssets)) {
						dirPath = dirPath.substring(prefixAssets.length());

						if (StrUtils.startsWith(dirPath, prefixes) && StrUtils.endsWith(dirPath, suffixes)) {
							list.add(dirPath);
						}
					}
				} else if (file.isDirectory()) {
					dirPath = basePath + file.getName() + "/";
					final String[] names1 = collectFilesFolder(file, dirPath, prefixes, suffixes);

					for (final String name : names1) {
						list.add(name);
					}
				}
			}

			final String[] var13 = (String[]) list.toArray(new String[list.size()]);
			return var13;
		}
	}

	private static String[] collectFilesZIP(final File tpFile, final String[] prefixes, final String[] suffixes) {
		final ArrayList list = new ArrayList();
		final String prefixAssets = "assets/minecraft/";

		try {
			final ZipFile e = new ZipFile(tpFile);
			final Enumeration en = e.entries();

			while (en.hasMoreElements()) {
				final ZipEntry names = (ZipEntry) en.nextElement();
				String name = names.getName();

				if (name.startsWith(prefixAssets)) {
					name = name.substring(prefixAssets.length());

					if (StrUtils.startsWith(name, prefixes) && StrUtils.endsWith(name, suffixes)) {
						list.add(name);
					}
				}
			}

			e.close();
			final String[] names1 = (String[]) list.toArray(new String[list.size()]);
			return names1;
		} catch (final IOException var9) {
			var9.printStackTrace();
			return new String[0];
		}
	}
}
