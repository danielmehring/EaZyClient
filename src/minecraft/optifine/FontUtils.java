package optifine;

import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FontUtils {

public static final int EaZy = 1902;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static Properties readFontProperties(final ResourceLocation locationFontTexture) {
		final String fontFileName = locationFontTexture.getResourcePath();
		final Properties props = new Properties();
		final String suffix = ".png";

		if (!fontFileName.endsWith(suffix)) {
			return props;
		} else {
			final String fileName = fontFileName.substring(0, fontFileName.length() - suffix.length()) + ".properties";

			try {
				final ResourceLocation e = new ResourceLocation(locationFontTexture.getResourceDomain(), fileName);
				final InputStream in = Config.getResourceStream(Config.getResourceManager(), e);

				if (in == null) {
					return props;
				}

				Config.log("Loading " + fileName);
				props.load(in);
			} catch (final FileNotFoundException var7) {
			} catch (final IOException var8) {
				var8.printStackTrace();
			}

			return props;
		}
	}

	public static void readCustomCharWidths(final Properties props, final float[] charWidth) {
		final Set keySet = props.keySet();
		final Iterator iter = keySet.iterator();

		while (iter.hasNext()) {
			final String key = (String) iter.next();
			final String prefix = "width.";

			if (key.startsWith(prefix)) {
				final String numStr = key.substring(prefix.length());
				final int num = Config.parseInt(numStr, -1);

				if (num >= 0 && num < charWidth.length) {
					final String value = props.getProperty(key);
					final float width = Config.parseFloat(value, -1.0F);

					if (width >= 0.0F) {
						charWidth[num] = width;
					}
				}
			}
		}
	}

	public static float readFloat(final Properties props, final String key, final float defOffset) {
		final String str = props.getProperty(key);

		if (str == null) {
			return defOffset;
		} else {
			final float offset = Config.parseFloat(str, Float.MIN_VALUE);

			if (offset == Float.MIN_VALUE) {
				Config.warn("Invalid value for " + key + ": " + str);
				return defOffset;
			} else {
				return offset;
			}
		}
	}

	public static ResourceLocation getHdFontLocation(final ResourceLocation fontLoc) {
		if (!Config.isCustomFonts()) {
			return fontLoc;
		} else if (fontLoc == null) {
			return fontLoc;
		} else {
			String fontName = fontLoc.getResourcePath();
			final String texturesStr = "textures/";
			final String mcpatcherStr = "mcpatcher/";

			if (!fontName.startsWith(texturesStr)) {
				return fontLoc;
			} else {
				fontName = fontName.substring(texturesStr.length());
				fontName = mcpatcherStr + fontName;
				final ResourceLocation fontLocHD = new ResourceLocation(fontLoc.getResourceDomain(), fontName);
				return Config.hasResource(Config.getResourceManager(), fontLocHD) ? fontLocHD : fontLoc;
			}
		}
	}
}
