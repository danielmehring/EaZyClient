package optifine;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class Lang {

public static final int EaZy = 1928;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Splitter splitter = Splitter.on('=').limit(2);
	private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

	public static void resourcesReloaded() {
		final Map localeProperties = I18n.getLocaleProperties();
		final ArrayList listFiles = new ArrayList();
		final String PREFIX = "optifine/lang/";
		final String EN_US = "en_US";
		final String SUFFIX = ".lang";
		listFiles.add(PREFIX + EN_US + SUFFIX);

		if (!Config.getGameSettings().language.equals(EN_US)) {
			listFiles.add(PREFIX + Config.getGameSettings().language + SUFFIX);
		}

		final String[] files = (String[]) listFiles.toArray(new String[listFiles.size()]);
		loadResources(Config.getDefaultResourcePack(), files, localeProperties);
		final IResourcePack[] resourcePacks = Config.getResourcePacks();

		for (final IResourcePack rp : resourcePacks) {
			loadResources(rp, files, localeProperties);
		}
	}

	private static void loadResources(final IResourcePack rp, final String[] files, final Map localeProperties) {
		try {
			for (final String file : files) {
				final ResourceLocation loc = new ResourceLocation(file);

				if (rp.resourceExists(loc)) {
					final InputStream in = rp.getInputStream(loc);

					if (in != null) {
						loadLocaleData(in, localeProperties);
					}
				}
			}
		} catch (final IOException var7) {
			var7.printStackTrace();
		}
	}

	public static void loadLocaleData(final InputStream is, final Map localeProperties) throws IOException {
		final Iterator it = IOUtils.readLines(is, Charsets.UTF_8).iterator();

		while (it.hasNext()) {
			final String line = (String) it.next();

			if (!line.isEmpty() && line.charAt(0) != 35) {
				final String[] parts = Iterables.toArray(splitter.split(line), String.class);

				if (parts != null && parts.length == 2) {
					final String key = parts[0];
					final String value = pattern.matcher(parts[1]).replaceAll("%$1s");
					localeProperties.put(key, value);
				}
			}
		}
	}

	public static String get(final String key) {
		return I18n.format(key, new Object[0]);
	}

	public static String get(final String key, final String def) {
		final String str = I18n.format(key, new Object[0]);
		return str != null && !str.equals(key) ? str : def;
	}

	public static String getOn() {
		return I18n.format("options.on", new Object[0]);
	}

	public static String getOff() {
		return I18n.format("options.off", new Object[0]);
	}

	public static String getFast() {
		return I18n.format("options.graphics.fast", new Object[0]);
	}

	public static String getFancy() {
		return I18n.format("options.graphics.fancy", new Object[0]);
	}

	public static String getDefault() {
		return I18n.format("generator.default", new Object[0]);
	}
}
