package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

public class Locale {

public static final int EaZy = 885;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Splits on "=" */
	private static final Splitter splitter = Splitter.on('=').limit(2);
	private static final Pattern field_135031_c = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	Map field_135032_a = Maps.newHashMap();
	private boolean field_135029_d;
	// private static final String __OBFID = "http://https://fuckuskid00001097";

	/**
	 * par2 is a list of languages. For each language $L and domain $D, attempts
	 * to load the resource $D:lang/$L.lang
	 */
	public synchronized void loadLocaleDataFiles(final IResourceManager p_135022_1_, final List p_135022_2_) {
		field_135032_a.clear();
		final Iterator var3 = p_135022_2_.iterator();

		while (var3.hasNext()) {
			final String var4 = (String) var3.next();
			final String var5 = String.format("lang/%s.lang", new Object[] { var4 });
			final Iterator var6 = p_135022_1_.getResourceDomains().iterator();

			while (var6.hasNext()) {
				final String var7 = (String) var6.next();

				try {
					this.loadLocaleData(p_135022_1_.getAllResources(new ResourceLocation(var7, var5)));
				} catch (final IOException var9) {
				}
			}
		}

		checkUnicode();
	}

	public boolean isUnicode() {
		return field_135029_d;
	}

	private void checkUnicode() {
		field_135029_d = false;
		int var1 = 0;
		int var2 = 0;
		final Iterator var3 = field_135032_a.values().iterator();

		while (var3.hasNext()) {
			final String var4 = (String) var3.next();
			final int var5 = var4.length();
			var2 += var5;

			for (int var6 = 0; var6 < var5; ++var6) {
				if (var4.charAt(var6) >= 256) {
					++var1;
				}
			}
		}

		final float var7 = (float) var1 / (float) var2;
		field_135029_d = var7 > 0.1D;
	}

	/**
	 * par1 is a list of Resources
	 */
	private void loadLocaleData(final List p_135028_1_) throws IOException {
		final Iterator var2 = p_135028_1_.iterator();

		while (var2.hasNext()) {
			final IResource var3 = (IResource) var2.next();
			final InputStream var4 = var3.getInputStream();

			try {
				this.loadLocaleData(var4);
			}
			finally {
				IOUtils.closeQuietly(var4);
			}
		}
	}

	private void loadLocaleData(final InputStream p_135021_1_) throws IOException {
		final Iterator var2 = IOUtils.readLines(p_135021_1_, Charsets.UTF_8).iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();

			if (!var3.isEmpty() && var3.charAt(0) != 35) {
				final String[] var4 = Iterables.toArray(splitter.split(var3), String.class);

				if (var4 != null && var4.length == 2) {
					final String var5 = var4[0];
					final String var6 = field_135031_c.matcher(var4[1]).replaceAll("%$1s");
					field_135032_a.put(var5, var6);
				}
			}
		}
	}

	/**
	 * Returns the translation, or the key itself if the key could not be
	 * translated.
	 */
	private String translateKeyPrivate(final String p_135026_1_) {
		final String var2 = (String) field_135032_a.get(p_135026_1_);
		return var2 == null ? p_135026_1_ : var2;
	}

	/**
	 * Calls String.format(translateKey(key), params)
	 */
	public String formatMessage(final String p_135023_1_, final Object[] p_135023_2_) {
		final String var3 = translateKeyPrivate(p_135023_1_);

		try {
			return String.format(var3, p_135023_2_);
		} catch (final IllegalFormatException var5) {
			return "Format error: " + var3;
		}
	}
}
