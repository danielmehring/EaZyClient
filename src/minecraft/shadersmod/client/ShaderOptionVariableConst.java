package shadersmod.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import optifine.StrUtils;

public class ShaderOptionVariableConst extends ShaderOptionVariable {

public static final int EaZy = 2009;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String type = null;
	private static final Pattern PATTERN_CONST = Pattern
			.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");

	public ShaderOptionVariableConst(final String name, final String type, final String description, final String value,
			final String[] values, final String path) {
		super(name, description, value, values, path);
		this.type = type;
	}

	@Override
	public String getSourceLine() {
		return "const " + type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
	}

	@Override
	public boolean matchesLine(final String line) {
		final Matcher m = PATTERN_CONST.matcher(line);

		if (!m.matches()) {
			return false;
		} else {
			final String defName = m.group(2);
			return defName.matches(getName());
		}
	}

	public static ShaderOption parseOption(final String line, String path) {
		final Matcher m = PATTERN_CONST.matcher(line);

		if (!m.matches()) {
			return null;
		} else {
			final String type = m.group(1);
			final String name = m.group(2);
			final String value = m.group(3);
			String description = m.group(4);
			final String vals = StrUtils.getSegment(description, "[", "]");

			if (vals != null && vals.length() > 0) {
				description = description.replace(vals, "").trim();
			}

			final String[] values = parseValues(value, vals);

			if (name != null && name.length() > 0) {
				path = StrUtils.removePrefix(path, "/shaders/");
				final ShaderOptionVariableConst so = new ShaderOptionVariableConst(name, type, description, value,
						values, path);
				return so;
			} else {
				return null;
			}
		}
	}
}
