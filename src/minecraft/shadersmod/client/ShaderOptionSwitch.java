package shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import optifine.Config;
import optifine.Lang;
import optifine.StrUtils;

public class ShaderOptionSwitch extends ShaderOption {

public static final int EaZy = 2006;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Pattern PATTERN_DEFINE = Pattern
			.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
	private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");

	public ShaderOptionSwitch(final String name, final String description, final String value, final String path) {
		super(name, description, value, new String[] { "true", "false" }, value, path);
	}

	@Override
	public String getSourceLine() {
		return isTrue(getValue()) ? "#define " + getName() + " // Shader option ON"
				: "//#define " + getName() + " // Shader option OFF";
	}

	@Override
	public String getValueText(final String val) {
		return isTrue(val) ? Lang.getOn() : Lang.getOff();
	}

	@Override
	public String getValueColor(final String val) {
		return isTrue(val) ? "\u00a7a" : "\u00a7c";
	}

	public static ShaderOption parseOption(final String line, String path) {
		final Matcher m = PATTERN_DEFINE.matcher(line);

		if (!m.matches()) {
			return null;
		} else {
			final String comment = m.group(1);
			final String name = m.group(2);
			final String description = m.group(3);

			if (name != null && name.length() > 0) {
				final boolean commented = Config.equals(comment, "//");
				final boolean enabled = !commented;
				path = StrUtils.removePrefix(path, "/shaders/");
				final ShaderOptionSwitch so = new ShaderOptionSwitch(name, description, String.valueOf(enabled), path);
				return so;
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean matchesLine(final String line) {
		final Matcher m = PATTERN_DEFINE.matcher(line);

		if (!m.matches()) {
			return false;
		} else {
			final String defName = m.group(2);
			return defName.matches(getName());
		}
	}

	@Override
	public boolean checkUsed() {
		return true;
	}

	@Override
	public boolean isUsedInLine(final String line) {
		final Matcher mif = PATTERN_IFDEF.matcher(line);

		if (mif.matches()) {
			final String name = mif.group(2);

			if (name.equals(getName())) {
				return true;
			}
		}

		return false;
	}

	public static boolean isTrue(final String val) {
		return Boolean.valueOf(val);
	}
}
