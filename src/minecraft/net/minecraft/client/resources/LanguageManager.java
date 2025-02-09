package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class LanguageManager implements IResourceManagerReloadListener {

public static final int EaZy = 884;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final IMetadataSerializer theMetadataSerializer;
	private String currentLanguage;
	protected static final Locale currentLocale = new Locale();
	private final Map languageMap = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00001096";

	public LanguageManager(final IMetadataSerializer p_i1304_1_, final String p_i1304_2_) {
		theMetadataSerializer = p_i1304_1_;
		currentLanguage = p_i1304_2_;
		I18n.setLocale(currentLocale);
	}

	public void parseLanguageMetadata(final List p_135043_1_) {
		languageMap.clear();
		final Iterator var2 = p_135043_1_.iterator();

		while (var2.hasNext()) {
			final IResourcePack var3 = (IResourcePack) var2.next();

			try {
				final LanguageMetadataSection var4 = (LanguageMetadataSection) var3
						.getPackMetadata(theMetadataSerializer, "language");

				if (var4 != null) {
					final Iterator var5 = var4.getLanguages().iterator();

					while (var5.hasNext()) {
						final Language var6 = (Language) var5.next();

						if (!languageMap.containsKey(var6.getLanguageCode())) {
							languageMap.put(var6.getLanguageCode(), var6);
						}
					}
				}
			} catch (final RuntimeException var7) {
				logger.warn("Unable to parse metadata section of resourcepack: " + var3.getPackName(), var7);
			} catch (final IOException var8) {
				logger.warn("Unable to parse metadata section of resourcepack: " + var3.getPackName(), var8);
			}
		}
	}

	@Override
	public void onResourceManagerReload(final IResourceManager p_110549_1_) {
		final ArrayList var2 = Lists.newArrayList(new String[] { "en_US" });

		if (!"en_US".equals(currentLanguage)) {
			var2.add(currentLanguage);
		}

		currentLocale.loadLocaleDataFiles(p_110549_1_, var2);
		StringTranslate.replaceWith(currentLocale.field_135032_a);
	}

	public boolean isCurrentLocaleUnicode() {
		return currentLocale.isUnicode();
	}

	public boolean isCurrentLanguageBidirectional() {
		return getCurrentLanguage() != null && getCurrentLanguage().isBidirectional();
	}

	public void setCurrentLanguage(final Language p_135045_1_) {
		currentLanguage = p_135045_1_.getLanguageCode();
	}

	public Language getCurrentLanguage() {
		return languageMap.containsKey(currentLanguage) ? (Language) languageMap.get(currentLanguage)
				: (Language) languageMap.get("en_US");
	}

	public SortedSet getLanguages() {
		return Sets.newTreeSet(languageMap.values());
	}
}
