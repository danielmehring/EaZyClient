package shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import optifine.StrUtils;

public class ShaderOptionSwitchConst extends ShaderOptionSwitch {

public static final int EaZy = 2007;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Pattern PATTERN_CONST = Pattern
			.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");

	public ShaderOptionSwitchConst(final String name, final String description, final String value, final String path) {
		super(name, description, value, path);
	}

	@Override
	public String getSourceLine() {
		return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
	}

	public static ShaderOption parseOption(final String line, String path) {
		final Matcher m = PATTERN_CONST.matcher(line);

		if (!m.matches()) {
			return null;
		} else {
			final String name = m.group(1);
			final String value = m.group(2);
			final String description = m.group(3);

			if (name != null && name.length() > 0) {
				path = StrUtils.removePrefix(path, "/shaders/");
				final ShaderOptionSwitchConst so = new ShaderOptionSwitchConst(name, description, value, path);
				so.setVisible(false);
				return so;
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean matchesLine(final String line) {
		final Matcher m = PATTERN_CONST.matcher(line);

		if (!m.matches()) {
			return false;
		} else {
			final String defName = m.group(1);
			return defName.matches(getName());
		}
	}

	@Override
	public boolean checkUsed() {
		return false;
	}
}
