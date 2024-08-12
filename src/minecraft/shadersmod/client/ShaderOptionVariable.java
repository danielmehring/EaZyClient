package shadersmod.client;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import optifine.Config;
import optifine.StrUtils;

public class ShaderOptionVariable extends ShaderOption {

public static final int EaZy = 2008;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Pattern PATTERN_VARIABLE = Pattern
			.compile("^\\s*#define\\s+([A-Za-z0-9_]+)\\s+(-?[0-9\\.]*)F?f?\\s*(//.*)?$");

	public ShaderOptionVariable(final String name, final String description, final String value, final String[] values,
			final String path) {
		super(name, description, value, values, value, path);
		setVisible(getValues().length > 1);
	}

	@Override
	public String getSourceLine() {
		return "#define " + getName() + " " + getValue() + " // Shader option " + getValue();
	}

	@Override
	public String getValueColor(final String val) {
		return "\u00a7a";
	}

	@Override
	public boolean matchesLine(final String line) {
		final Matcher m = PATTERN_VARIABLE.matcher(line);

		if (!m.matches()) {
			return false;
		} else {
			final String defName = m.group(1);
			return defName.matches(getName());
		}
	}

	public static ShaderOption parseOption(final String line, String path) {
		final Matcher m = PATTERN_VARIABLE.matcher(line);

		if (!m.matches()) {
			return null;
		} else {
			final String name = m.group(1);
			final String value = m.group(2);
			String description = m.group(3);
			final String vals = StrUtils.getSegment(description, "[", "]");

			if (vals != null && vals.length() > 0) {
				description = description.replace(vals, "").trim();
			}

			final String[] values = parseValues(value, vals);

			if (name != null && name.length() > 0) {
				path = StrUtils.removePrefix(path, "/shaders/");
				final ShaderOptionVariable so = new ShaderOptionVariable(name, description, value, values, path);
				return so;
			} else {
				return null;
			}
		}
	}

	public static String[] parseValues(final String value, String valuesStr) {
		final String[] values = new String[] { value };

		if (valuesStr == null) {
			return values;
		} else {
			valuesStr = valuesStr.trim();
			valuesStr = StrUtils.removePrefix(valuesStr, "[");
			valuesStr = StrUtils.removeSuffix(valuesStr, "]");
			valuesStr = valuesStr.trim();

			if (valuesStr.length() <= 0) {
				return values;
			} else {
				String[] parts = Config.tokenize(valuesStr, " ");

				if (parts.length <= 0) {
					return values;
				} else {
					if (!Arrays.asList(parts).contains(value)) {
						parts = (String[]) Config.addObjectToArray(parts, value, 0);
					}

					return parts;
				}
			}
		}
	}
}
